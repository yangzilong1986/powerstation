package com.hzjbbis.fk.fe.ums;

import com.hzjbbis.db.batch.AsyncService;
import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.IEventHandler;
import com.hzjbbis.fk.common.spi.IMessageQueue;
import com.hzjbbis.fk.fe.filecache.RtuCommFlowCache;
import com.hzjbbis.fk.fe.filecache.RtuParamsCache;
import com.hzjbbis.fk.fe.userdefine.UserDefineMessageQueue;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.model.ComRtu;
import com.hzjbbis.fk.model.RtuManage;
import com.hzjbbis.fk.sockserver.event.MessageSendFailEvent;
import com.hzjbbis.fk.sockserver.event.ReceiveMessageEvent;
import com.hzjbbis.fk.sockserver.event.SendMessageEvent;
import com.hzjbbis.fk.utils.HexDump;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class SmsMessageEventHandler implements IEventHandler {
    private static final Logger log = Logger.getLogger(SmsMessageEventHandler.class);
    private IMessageQueue msgQueue;
    private AsyncService asyncDbService;

    public void handleEvent(IEvent event) {
        if (event.getType().equals(EventType.MSG_RECV)) onRecvMessage((ReceiveMessageEvent) event);
        else if (event.getType().equals(EventType.MSG_SENT)) onSendMessage((SendMessageEvent) event);
        else if (event.getType().equals(EventType.MSG_SEND_FAIL)) onSendFailMessage((MessageSendFailEvent) event);
    }

    private void onRecvMessage(ReceiveMessageEvent event) {
        IMessage msg = event.getMessage();
        if (log.isDebugEnabled()) {
            log.debug("UMS短信网关上行报文:" + msg);
        }

        ComRtu rtu = RtuManage.getInstance().getComRtuInCache(msg.getRtua());
        if (rtu == null) {
            String strRtua = HexDump.toHex(msg.getRtua());
            log.warn("短信通道上行，找不到对应终端。appid=" + msg.getPeerAddr() + ",msg=" + msg.getRawPacketString());
            rtu = new ComRtu();
            rtu.setLogicAddress(strRtua);
            rtu.setRtua(msg.getRtua());
            RtuManage.getInstance().putComRtuToCache(rtu);
        }

        rtu.setLastSmsTime(System.currentTimeMillis());
        rtu.setLastIoTime(rtu.getLastSmsTime());

        rtu.incUpSmsCount();

        boolean channelChanged = false;
        String serverAddr = msg.getServerAddress();
        String appid = event.getClient().getPeerAddr();
        int index = serverAddr.indexOf(44);
        try {
            String upMobile = serverAddr.substring(0, index);
            String receiver = serverAddr.substring(index + 1);

            boolean updateRtuCache = false;
            if (!(upMobile.equals(rtu.getSimNum()))) {
                rtu.setUpMobile(upMobile);
                rtu.setSimNum(upMobile);
                updateRtuCache = true;
            }

            index = receiver.indexOf("95598");
            if (index >= 0) receiver = receiver.substring(index + 5);
            if (!(appid.equals(rtu.getActiveUms()))) {
                rtu.setActiveUms(appid);
                channelChanged = true;
                updateRtuCache = true;
            }

            if (appid.startsWith("95598")) appid = appid.substring(5);
            String subAppId = null;
            if (receiver.length() > appid.length()) {
                subAppId = receiver.substring(appid.length());
                if (!(subAppId.equals(rtu.getActiveSubAppId()))) {
                    rtu.setActiveSubAppId(subAppId);
                    updateRtuCache = true;
                }

            } else if ((rtu.getActiveSubAppId() != null) && (rtu.getActiveSubAppId().length() > 0)) {
                rtu.setActiveSubAppId(null);
                updateRtuCache = true;
            }

            if (updateRtuCache) RtuParamsCache.getInstance().addRtu(rtu);
        } catch (Exception e) {
            log.error("update RTU:(simNum activeUms activeSubAppId) exception:" + e.getLocalizedMessage(), e);
        }

        if (channelChanged) {
            try {
                ArrayList smsAddrs = new ArrayList();
                String addr = null;
                if (appid.startsWith("95598")) appid = appid.substring(5);
                if ("01".equals(rtu.getCommType())) {
                    addr = rtu.getCommAddress();
                    if (addr != null) {
                        addr = filterUmsAppId(addr);
                        if (addr.length() >= appid.length()) addr = addr.substring(0, appid.length());
                        smsAddrs.add(addr);
                    }
                }
                if ("01".equals(rtu.getB1CommType())) {
                    addr = rtu.getB1CommAddress();
                    if (addr != null) {
                        addr = filterUmsAppId(addr);
                        if (addr.length() >= appid.length()) addr = addr.substring(0, appid.length());
                        smsAddrs.add(addr);
                    }
                }
                if ("01".equals(rtu.getB2CommType())) {
                    addr = rtu.getB2CommAddress();
                    if (addr != null) {
                        addr = filterUmsAppId(addr);
                        if (addr.length() >= appid.length()) addr = addr.substring(0, appid.length());
                        smsAddrs.add(addr);
                    }
                }
                boolean same = smsAddrs.size() == 0;
                for (String smsAddr : smsAddrs) {
                    if (smsAddr.startsWith(appid)) {
                        same = true;
                        break;
                    }
                }

                if (!(same)) rtu.setMisSmsAddress(appid);
            } catch (Exception smsAddrs) {
                log.error("search discord SMS params exp:" + e.getLocalizedMessage(), e);
            }

        }

        if (msg.isTask()) rtu.incTaskCount();
        else {
            msg.isHeartbeat();
        }

        RtuCommFlowCache.getInstance().addRtu(rtu);

        if (this.asyncDbService != null) {
            this.asyncDbService.log2Db(msg);
        }

        this.msgQueue.offer(msg);
    }

    public String filterUmsAppId(String ums) {
        if (ums != null) {
            int index = ums.indexOf("95598");
            if (index >= 0) ums = ums.substring(index + 5);
        }
        return ums;
    }

    private void onSendMessage(SendMessageEvent event) {
        IMessage msg = event.getMessage();
        if (log.isDebugEnabled()) {
            log.debug("UMS短信网关下行报文:" + msg);
        }

        ComRtu rtu = RtuManage.getInstance().getComRtuInCache(msg.getRtua());

        if (rtu == null) {
            return;
        }

        rtu.setLastIoTime(System.currentTimeMillis());

        rtu.incDownSmsCount();
        try {
            String appid = event.getClient().getPeerAddr();

            if (!(appid.equals(rtu.getActiveUms()))) rtu.setActiveUms(appid);
        } catch (Exception err) {
            log.error(err.getLocalizedMessage(), err);
        }

        RtuCommFlowCache.getInstance().addRtu(rtu);

        if (this.asyncDbService != null) this.asyncDbService.log2Db(msg);
    }

    private void onSendFailMessage(MessageSendFailEvent event) {
        IMessage msg = event.getMessage();
        msg.setStatus("1");
        if (log.isDebugEnabled()) {
            log.debug("UMS短信网关下行报文:" + msg);
        }

        if (this.asyncDbService != null) this.asyncDbService.log2Db(msg);
    }

    public void setMsgQueue(IMessageQueue msgQueue) {
        this.msgQueue = msgQueue;
    }

    public void setUdefQueue(UserDefineMessageQueue udefQueue) {
    }

    public final void setAsyncDbService(AsyncService asyncDbService) {
        this.asyncDbService = asyncDbService;
    }
}