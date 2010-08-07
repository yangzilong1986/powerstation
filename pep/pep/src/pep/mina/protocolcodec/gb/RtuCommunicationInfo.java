/*
 * 记录每个终端的通讯状态,管理终端通讯过程
 */
package pep.mina.protocolcodec.gb;

import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pep.codec.protocol.gb.ControlCode;
import pep.codec.protocol.gb.PmPacket;
import pep.codec.protocol.gb.EventCountor;
import pep.codec.protocol.gb.Seq;
import pep.codec.protocol.gb.gb376.PmPacket376;
import pep.codec.protocol.gb.gb376.PmPacket376Factroy;
import pep.mina.common.RtuRespPacketQueue;
import pep.mina.common.SequencedPmPacket;

/**
 *
 * @author luxiaochung
 */
public class RtuCommunicationInfo {

    private String rtua;
    private IoSession session;
    private Queue<SeqPacket> unsendPacket;   //待发送帧队列
    private byte currentSeq;        //下一个主动发送帧的帧序号
    private boolean idle;           //是否可以发送下行帧
    private byte currentRespSeq;    //当前等待回复的帧序号
    private byte maxRetryTimes;     //当没有收到终端回应帧时最大重复发送次数
    private byte currentSendTimes;
    private Date currentSendTicket;
    private PmPacket currentPacket;
    private int currentSequence;
    private boolean isTcp;
    private byte lastEc1;
    private byte lastEc2;
    private static final byte EC_CALL_HOST_ID = 3;   //读取3类数据时使用的主站ID
    private static final long TIME_OUT = 30 * 1000;
    private final static Logger LOGGER = LoggerFactory.getLogger(RtuCommunicationInfo.class);

    private class SeqPacket {
        private int sequence;
        private PmPacket pack;

        private SeqPacket(int sequence, PmPacket pack) {
            this.sequence = sequence;
            this.pack = pack;
        }
    }

    public RtuCommunicationInfo(String rtua) {
        super();
        this.rtua = rtua;
        currentSeq = 0;
        maxRetryTimes = 3;
        session = null;
        idle = true;
        lastEc1 = 0;
        lastEc2 = 0;
        unsendPacket = new ConcurrentLinkedQueue<SeqPacket>();
    }

    public RtuCommunicationInfo(String rtua, IoSession session) {
        this(rtua);
        this.session = session;
    }

    public synchronized RtuCommunicationInfo setMaxRetryTimes(byte maxRetryTimes) {
        this.maxRetryTimes = maxRetryTimes;
        return this;
    }

    public synchronized IoSession getSession() {
        return this.session;
    }

    public synchronized RtuCommunicationInfo setTcpSession(IoSession session) {
        this.isTcp = true;
        this.session = session;
        this.sendNextPacket();
        return this;
    }

    public synchronized void connectted(IoSession session) {
        this.session = session;
    }

    public synchronized void disconnected() {
        this.session = null;
    }

    public synchronized void receiveRtuUploadPacket(PmPacket packet) {
        ControlCode ctrlCode = packet.getControlCode();
        Seq seq = packet.getSeq();
        if ((!ctrlCode.getIsOrgniger()) && ctrlCode.getIsUpDirect()
                && (seq.getSeq() == this.currentRespSeq)) { //上行响应帧
            if (seq.getIsFinishFrame()) {
                RtuRespPacketQueue.instance().addPacket(
                        new SequencedPmPacket(this.currentSequence,
                        packet, SequencedPmPacket.Status.SUSSESS));
                this.idle = true;
                sendNextPacket();
            } else {
                RtuRespPacketQueue.instance().addPacket(
                        new SequencedPmPacket(this.currentSequence,
                        packet, SequencedPmPacket.Status.TO_BE_CONTINUE));
                this.currentRespSeq++;
                this.currentRespSeq &= 0x0F;
                this.currentSendTicket = new Date();
            }
        }
    }

    public synchronized void callRtuEventRecord(EventCountor ec) {
        PmPacket376 pack1 = PmPacket376Factroy.makeCallEventRecordPacket(RtuCommunicationInfo.EC_CALL_HOST_ID,
                this.rtua, 1, this.lastEc1, ec.getEc1());
        sendPacket(0, pack1);

        PmPacket376 pack2 = PmPacket376Factroy.makeCallEventRecordPacket(RtuCommunicationInfo.EC_CALL_HOST_ID,
                this.rtua, 2, this.lastEc2, ec.getEc2());
        sendPacket(0, pack2);
    }

    public void sendPacket(int sequence, PmPacket packet) {
        if (this.getSession() == null) {
            LOGGER.info("Send packet: "+this.rtua+" not online");
            RtuRespPacketQueue.instance().addPacket(new SequencedPmPacket(sequence, packet, SequencedPmPacket.Status.NOT_ONLINE));
        } else {
            this.unsendPacket.add(new SeqPacket(sequence, packet));
            if (this.idle) {
                sendNextPacket();
            } else{
               LOGGER.info("Send packet: "+this.rtua+" not idle"); 
            }
        }
    }

    private void sendNextPacket() {
        if (this.idle) {
            SeqPacket seqPacket = unsendPacket.poll();
            if (seqPacket != null) {
                this.idle = false;
                this.currentSequence = seqPacket.sequence;
                this.currentSendTimes = 0;
                this.currentPacket = seqPacket.pack;
                this.currentPacket.getSeq().setSeq(this.currentSeq);
                this.currentRespSeq = this.currentSeq;
                this.currentSeq = (byte) ((++this.currentSeq) & 0x0F);
                doSendPacket();
            } else {
                this.idle = true;
            }
        }
    }

    private synchronized void doSendPacket() {
        this.currentSendTicket = new Date();
        this.currentSendTimes++;
        if (this.currentSendTimes < maxRetryTimes) {
            if (this.session!=null)
                this.session.write(this.currentPacket);
            else
                LOGGER.info("DoSend: "+rtua+" not online");
        } else {
            this.sendNextPacket();
        }
    }

    /**
     * 到达重复发送检查点，重复发送是由外部定时器发起的，到达一个检查节拍时
     * 向所有Rtu通讯对象发送到达重复检查点消息
     */
    public void checkNotResponed(Date checkTime) {
        if (this.currentSendTicket==null) return;
        if (checkTime.getTime() - this.currentSendTicket.getTime() >= RtuCommunicationInfo.TIME_OUT) {
            if (this.currentSendTimes == this.maxRetryTimes) {
                RtuRespPacketQueue.instance().addPacket(
                        new SequencedPmPacket(this.currentSequence, this.currentPacket,
                        SequencedPmPacket.Status.TIME_OUT));
                this.idle = true;
                sendNextPacket();
            } else {
                doSendPacket();
            }
        }
    }
}
