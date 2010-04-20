/*
 * 记录每个终端的通讯状态,管理终端通讯过程
 */

package pep.mina.protocolcodec.gb;

import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.mina.core.session.IoSession;
import pep.codec.protocol.gb.PmPacket;
import pep.codec.protocol.gb.EventCountor;

/**
 *
 * @author luxiaochung
 */
public class RtuCommunicationInfo {
    private String rtua;
    private IoSession session;
    private Queue<PmPacket> unsendPacket;   //待发送帧队列
    private byte nextSeq;       //下一个主动发送帧的帧序号
    private boolean idle;       //是否可以发送下行帧
    private byte currentSeq;    //当前等待回复的帧序号
    private byte maxRetryTimes; //当没有收到终端回应帧时最大重复发送次数
    private byte currentSendTimes;
    private Date currentSendTicket;
    private PmPacket currentPacket;
    private Queue<PmPacket> receivePacketQueue;  //接收到的报文或不在线/超时错误返回报文
    
    public RtuCommunicationInfo(String rtua){
        super();
        this.rtua = rtua;
        nextSeq = 0;
        maxRetryTimes = 3;
        session = null;
        idle = true;
        unsendPacket = new ConcurrentLinkedQueue<PmPacket>();
    }
    
    public RtuCommunicationInfo(String rtua, IoSession session){
        this(rtua);
        this.session = session;
    }

    public RtuCommunicationInfo setMaxRetryTimes(byte maxRetryTimes){
        this.maxRetryTimes = maxRetryTimes;
        return this;
    }

    public IoSession getSession(){
        return this.session;
    }

    public RtuCommunicationInfo setSession(IoSession session){
        this.session = session;
        return this;
    }

    public RtuCommunicationInfo setReceivePacketQueue(Queue<PmPacket> receivePacketQueue){
        this.receivePacketQueue = receivePacketQueue;
        return this;
    }

    public void connectted(IoSession session){
        this.session = session;
    }

    public synchronized void disconnected(){
        this.session = null;
    }

    public synchronized void receiveRtuUploadPacket(PmPacket packet){

    }

    public synchronized void callRtuEventRecord(EventCountor ec){

    }

    public void sendPacket(int sequence, PmPacket packet){
       
    }

    private void packetSend(PmPacket packet){
        //not online
    }

    private void packetResend(){

    }

    /**
     * 到达重复发送检查点，重复发送是由外部定时器发起的，到达一个检查节拍时
     * 向所有Rtu通讯对象发送到达重复检查点消息
     */
    public void checkResponed(){
        //timeout
    }

}
