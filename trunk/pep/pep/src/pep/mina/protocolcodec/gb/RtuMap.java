/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.mina.protocolcodec.gb;

import java.util.Map;
import java.util.TreeMap;
import org.apache.mina.core.session.IoSession;
import pep.codec.protocol.gb.PmPacket;
import pep.mina.common.RtuConnectEventHandler;
import pep.mina.common.RtuConnectListener;

/**
 *
 * @author luxiaochung
 */
public class RtuMap {

    private Map<String, RtuCommunicationInfo> rtuSessionMap;
    private RtuConnectEventHandler rtuConnectEventHandler;

    public RtuMap() {
        rtuSessionMap = new TreeMap<String, RtuCommunicationInfo>();
        rtuConnectEventHandler = new RtuConnectEventHandler();
    }

    public void addRtuConnectListener(RtuConnectListener listener) {
        rtuConnectEventHandler.addRtuConnectListener(listener);
    }

    public void removeRtuConnectListener(RtuConnectListener listener) {
        rtuConnectEventHandler.removeRtuConnectListener(listener);
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

    private RtuCommunicationInfo rtuConnectted(String rtua, IoSession session) {
        RtuCommunicationInfo rtu = getRtuCommunictionInfo(rtua);
        if (rtu == null) {
            rtu = new RtuCommunicationInfo(rtua, session);
            putRtuCommunicationInfo(rtua, rtu);
            rtuConnectEventHandler.fireRtuConnect(rtua);
        } else {
            if (rtu.getSession() == null) {
                rtu.connectted(session);
                rtuConnectEventHandler.fireRtuConnect(rtua);
            }
        }
        return rtu;
    }

    public void rtuReceivePacket(String rtua, IoSession session, PmPacket pack) {
        RtuCommunicationInfo rtu = getRtuCommunictionInfo(rtua);
        if (rtu == null) {
            rtu = new RtuCommunicationInfo(rtua, session);
            putRtuCommunicationInfo(rtua, rtu);
        }

        if (rtu.getSession() == null) {
            rtu.setSession(session);
        }
        if (pack.getAfn() != (byte) 2) {
            rtu.receiveRtuUploadPacket(pack);
        }

        if (pack.getControlCode().getUpDirectIsAppealCall()) {//要求访问
            rtu.callRtuEventRecord(pack.getEC());
        }
    }
}
