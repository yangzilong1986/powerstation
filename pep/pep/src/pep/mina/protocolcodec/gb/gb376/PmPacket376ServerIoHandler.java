/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.mina.protocolcodec.gb.gb376;

import java.util.TreeSet;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pep.codec.protocol.gb.gb376.PmPacket376;
import pep.codec.protocol.gb.gb376.PmPacket376Factroy;
import pep.codec.utils.BcdUtils;
import pep.mina.protocolcodec.gb.PepGbCommunicator;

/**
 *
 * @author luxiaochung
 */
public class PmPacket376ServerIoHandler extends IoHandlerAdapter {

    private PepGbCommunicator rtuMap;
    private boolean showActTestPack = true;
    private final static String SESSION_RTUS = PmPacket376ServerIoHandler.class.getName() + ".rtus";
    private final static Logger LOGGER = LoggerFactory.getLogger(PmPacket376ServerIoHandler.class);

    public PmPacket376ServerIoHandler(PepGbCommunicator rtuMap) {
        super();
        this.rtuMap = rtuMap;
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        if (session.getAttribute(SESSION_RTUS) != null) {
            TreeSet<String> rtus = (TreeSet<String>) session.getAttribute(SESSION_RTUS);
            for (String rtua : rtus) {
                rtuMap.rtuDisconnectted(rtua);
                LOGGER.info("rtua<" + rtua + "> disconnect");
            }
            rtus.clear();
        }
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        PmPacket376 pack = (PmPacket376) message;
        String rtua = pack.getAddress().getRtua();
        showReceivePacket(rtua,pack);

        if (!pack.getControlCode().getIsUpDirect()) {
            return;
        }

        registRtua(session, rtua);

        if (pack.getControlCode().getIsOrgniger()) {//主动上送
            PmPacket376 respPack = PmPacket376Factroy.makeAcKnowledgementPack(pack, 3, (byte) 0);
            session.write(respPack);
        }

        rtuMap.rtuReceiveTcpPacket(rtua, session, pack);
    }

    private boolean isActiveTestPack(PmPacket376 pack){
        return pack.getAfn()==2;
    }

    private boolean needNotShow(PmPacket376 pack){
        return isActiveTestPack(pack)&&(!showActTestPack);
    }

    private void showReceivePacket(String rtua, PmPacket376 pack){
        if (!needNotShow(pack)){
            LOGGER.info("Receive from rtua<" + rtua + ">: " + BcdUtils.binArrayToString(pack.getValue()) + '\n' + pack.toString());
        }
    }

    private void registRtua(IoSession session, String rtua) {
        TreeSet<String> rtus;
        if (session.getAttribute(SESSION_RTUS) == null) {
            rtus = new TreeSet<String>();
            session.setAttribute(SESSION_RTUS, rtus);
        } else {
            rtus = (TreeSet<String>) session.getAttribute(SESSION_RTUS);
        }

        rtus.add(rtua);
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        PmPacket376 pack = (PmPacket376) message;
        if (!((pack.getAfn() == 2) && (!showActTestPack))) {
            LOGGER.info("Had Sent to rtua<" + pack.getAddress().getRtua() + ">: "
                    + BcdUtils.binArrayToString(pack.getValue()) + '\n' + pack.toString());
        }
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable thrml){
        LOGGER.info("Catch a exception: "+ thrml.getMessage());
    }
}
