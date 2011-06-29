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
    public void SendPacket(int sequence, PmPacket packet) {
        String rtua = packet.getAddress().getRtua();
        RtuCommunicationInfo rtu = getRtuCommunictionInfo(rtua);
        if (rtu == null) {
            rtu = new RtuCommunicationInfo(rtua);
            rtuSessionMap.put(rtua, rtu);
        }
        rtu.sendPacket(sequence, packet);
    }

    @Override
    public synchronized void checkUndespPackets() {
        Date checkTime;
        checkTime = new Date();
        for (RtuCommunicationInfo rtu : rtuSessionMap.values()){
            rtu.checkNotResponed(checkTime);
        }
    }

    private synchronized void putRtuCommunicationInfo(String rtua, RtuCommunicationInfo rtuInfo) {
        rtuSessionMap.put(rtua, rtuInfo);
    }

    private synchronized RtuCommunicationInfo getRtuCommunictionInfo(String rtua) {
        return rtuSessionMap.get(rtua);
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

        if (!pack.getControlCode().getIsOrgniger()) {
            rtu.receiveRtuUploadPacket(pack);
        } else{
            addAutoUploadPacket(pack);
        }

        if ((pack.getControlCode().getIsUpDirect()) && (pack.getControlCode().getUpDirectIsAppealCall())) {//要求访问
            rtu.callRtuEventRecord(pack.getEC());
        }

        //if ((rtu.getSession() == null)||(rtu.getSession()!=session)) {
            rtu.setTcpSession(session);
        //}
    }
    
    private void addAutoUploadPacket(PmPacket pack){
        if (pack.getAfn() != 2) //主动上送
        {
            autoUploadPacketQueue.addPacket(pack);
        }
    }
}
