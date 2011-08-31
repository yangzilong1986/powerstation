/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.mina.protocolcodec.gb;

import java.util.Date;
import pep.mina.common.RtuAutoUploadPacketQueue;
import java.util.Map;
import java.util.TreeMap;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pep.codec.protocol.gb.PmPacket;
import pep.mina.common.PepCommunicatorInterface;
import pep.mina.common.RtuConnectEventHandler;
import pep.mina.common.RtuConnectListener;
import pep.mina.common.RtuRespPacketQueue;

/**
 *
 * @author luxiaochung
 */
public class PepGbCommunicator implements PepCommunicatorInterface {

    private Map<String, RtuCommunicationInfo> rtuSessionMap;
    private RtuConnectEventHandler rtuConnectEventHandler;
    private RtuAutoUploadPacketQueue autoUploadPacketQueue;  //接收到的报文或不在线/超时错误返回报文
    private final static Logger LOGGER = LoggerFactory.getLogger(PepGbCommunicator.class);

    public PepGbCommunicator() {
        rtuSessionMap = new TreeMap<String, RtuCommunicationInfo>();
        rtuConnectEventHandler = new RtuConnectEventHandler();
        autoUploadPacketQueue = RtuAutoUploadPacketQueue.instance();
    }

    public void addRtuConnectListener(RtuConnectListener listener) {
        rtuConnectEventHandler.addRtuConnectListener(listener);
    }

    public void removeRtuConnectListener(RtuConnectListener listener) {
        rtuConnectEventHandler.removeRtuConnectListener(listener);
    }

    @Override
    public RtuAutoUploadPacketQueue getRtuAutoUploadPacketQueueInstance() {
        return this.autoUploadPacketQueue;
    }

    @Override
    public RtuRespPacketQueue getRtuRespPacketQueueInstance() {
        return RtuRespPacketQueue.instance();
    }

    @Override
    public void SendPacket(int sequence, PmPacket packet, int priorityLevel) {
        String rtua = packet.getAddress().getRtua();
        RtuCommunicationInfo rtu = getRtuCommunictionInfo(rtua);
        if (rtu == null) {
            rtu = new RtuCommunicationInfo(rtua);
            synchronized(this){
                rtuSessionMap.put(rtua, rtu);
            }
        }
        rtu.sendPacket(sequence, packet, priorityLevel);
    }

    @Override
    public void checkUndespPackets() {
        Date checkTime;
        checkTime = new Date();
        synchronized (this) {
            for (RtuCommunicationInfo rtu : rtuSessionMap.values()) {
                rtu.checkNotResponed(checkTime);
            }
        }
    }

    private  void putRtuCommunicationInfo(String rtua, RtuCommunicationInfo rtuInfo) {
        synchronized(this){
            rtuSessionMap.put(rtua, rtuInfo);
        }
    }

    private RtuCommunicationInfo getRtuCommunictionInfo(String rtua) {
        RtuCommunicationInfo result;
        synchronized(this){
            result = rtuSessionMap.get(rtua);
        }
        return result;
    }

    public void rtuDisconnectted(String rtua) {
        RtuCommunicationInfo rtu = getRtuCommunictionInfo(rtua);
        if (rtu != null) {
            rtu.disconnected();
        }
        rtuConnectEventHandler.fireRtuDisconnect(rtua);
    }

    public void rtuReceiveTcpPacket(String rtua, IoSession session, PmPacket pack) {
        RtuCommunicationInfo rtu = getRtuCommunictionInfo(rtua);
        if (rtu == null) {
            rtu = new RtuCommunicationInfo(rtua, null);
            putRtuCommunicationInfo(rtua, rtu);
        }
        rtu.setTcpSession(session);

        if (!pack.getControlCode().getIsOrgniger()) {
            rtu.receiveRtuUploadPacket(pack);
        } else{
            addAutoUploadPacket(pack);
        }

        if ((pack.getControlCode().getIsUpDirect()) && (pack.getControlCode().getUpDirectIsAppealCall())) {//要求访问
            rtu.callRtuEventRecord(pack.getEC());
        }
    }
    
    private void addAutoUploadPacket(PmPacket pack){
        if (pack.getAfn() != 2) //主动上送
        {
         //   LOGGER.info("向业务层发送收到的终端主动上送报文："+pack.toString());
            autoUploadPacketQueue.addPacket(pack);
        }
    }
}
