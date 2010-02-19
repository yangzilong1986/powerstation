package com.hzjbbis.fk.fe.rawmsg2db;

import com.hzjbbis.db.batch.AsyncService;
import com.hzjbbis.db.batch.event.adapt.BaseLog2DbHandler;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.MessageType;
import com.hzjbbis.fk.message.gw.MessageGw;
import com.hzjbbis.fk.message.zj.MessageZj;
import com.hzjbbis.fk.utils.HexDump;
import org.apache.log4j.Logger;

import java.util.Date;

public class RawMessage2DbHandler extends BaseLog2DbHandler {
    private static final Logger log = Logger.getLogger(RawMessage2DbHandler.class);

    public void handleLog2Db(AsyncService service, IMessage msg) {
        try {
            MessageLog msgLog = new MessageLog();
            msgLog.setLogicAddress(HexDump.toHex(msg.getRtua()));
            msgLog.setQym(msgLog.getLogicAddress().substring(0, 2));
            boolean dirUp = false;
            byte appFunctionCode = 0;
            if (msg.getMessageType() == MessageType.MSG_ZJ) {
                MessageZj zjmsg = (MessageZj) msg;
                if (33 == zjmsg.head.c_func) zjmsg.head.c_dir = 1;
                dirUp = zjmsg.head.c_dir == 1;
                appFunctionCode = zjmsg.head.c_func;
            } else if (msg.getMessageType() == MessageType.MSG_GW_10) {
                MessageGw gwmsg = (MessageGw) msg;
                dirUp = gwmsg.head.c_dir == 1;
                appFunctionCode = gwmsg.afn();
            }
            msgLog.setKzm(HexDump.toHex(appFunctionCode));

            msgLog.setTxfs(msg.getTxfs());
            if ("01".equals(msg.getTxfs())) {
                boolean hasServerAddr = false;
                if (msg.getServerAddress() != null) {
                    int index = msg.getServerAddress().indexOf(44);
                    if (index > 0) {
                        msgLog.setSrcAddr(msg.getServerAddress().substring(0, index));
                        msgLog.setDestAddr(msg.getServerAddress().substring(index + 1));
                        hasServerAddr = true;
                    }
                }
                if (!(hasServerAddr)) msgLog.setSrcAddr(msg.getPeerAddr());
            } else if ("02".equals(msg.getTxfs())) {
                msgLog.setSrcAddr(msg.getPeerAddr());

                if (msg.getSource() != null) msgLog.setDestAddr(msg.getSource().getPeerAddr());
            } else {
                log.warn("msg txfs is error:" + msg.getTxfs() + "msg=" + msg);
                msgLog.setSrcAddr(msg.getPeerAddr());
            }

            msgLog.setTime(new Date(System.currentTimeMillis()));
            msgLog.setBody(msg.getRawPacketString());
            msgLog.setSize(msgLog.getBody().length());
            if (dirUp) {
                service.addToDao(msgLog, Integer.parseInt("5000"));
                return;
            }
            if ((msg.getStatus() != null) && (msg.getStatus().equals("1"))) msgLog.setResult("1");
            else msgLog.setResult("0");
            service.addToDao(msgLog, Integer.parseInt("5001"));
        } catch (Exception ex) {
            log.error("Error to processing message log:" + msg, ex);
        }
    }
}