package com.hzjbbis.fk.fe.gprs;

import com.hzjbbis.db.batch.AsyncService;
import com.hzjbbis.fk.FasSystem;
import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.IEventHandler;
import com.hzjbbis.fk.fe.filecache.HeartbeatPersist;
import com.hzjbbis.fk.fe.filecache.RtuCommFlowCache;
import com.hzjbbis.fk.fe.filecache.RtuParamsCache;
import com.hzjbbis.fk.fe.msgqueue.FEMessageQueue;
import com.hzjbbis.fk.fe.userdefine.UserDefineMessageQueue;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.MessageType;
import com.hzjbbis.fk.message.gate.MessageGate;
import com.hzjbbis.fk.message.gw.MessageGw;
import com.hzjbbis.fk.message.zj.MessageZj;
import com.hzjbbis.fk.model.ComRtu;
import com.hzjbbis.fk.model.RtuManage;
import com.hzjbbis.fk.sockserver.event.ReceiveMessageEvent;
import com.hzjbbis.fk.sockserver.event.SendMessageEvent;
import com.hzjbbis.fk.utils.HexDump;
import org.apache.log4j.Logger;

public class GateMessageEventHandler implements IEventHandler {
    private static final Logger log = Logger.getLogger(GateMessageEventHandler.class);
    private FEMessageQueue msgQueue;
    private UserDefineMessageQueue udefQueue;
    private AsyncService asyncDbService;

    public void handleEvent(IEvent event) {
        if (event.getType().equals(EventType.MSG_RECV)) onRecvMessage((ReceiveMessageEvent) event);
        else if (event.getType().equals(EventType.MSG_SENT)) onSendMessage((SendMessageEvent) event);
    }

    private void onRecvMessage(ReceiveMessageEvent e) {
        IMessage msg = e.getMessage();
        if (msg.getMessageType() == MessageType.MSG_GATE) {
            IMessage imsg;
            MessageZj zjmsg;
            MessageGw gwmsg;
            MessageGate mgate = (MessageGate) msg;

            if (mgate.getHead().getCommand() == 18) {
                log.info(mgate);
                return;
            }
            if (mgate.getHead().getCommand() == 34) {
                imsg = mgate.getInnerMessage();
                if (imsg != null) if (imsg.getMessageType() == MessageType.MSG_ZJ) {
                    zjmsg = (MessageZj) imsg;
                    _handleZjMessage(zjmsg, e);
                } else if (imsg.getMessageType() == MessageType.MSG_GW_10) {
                    gwmsg = (MessageGw) imsg;
                    _handleGwMessage(gwmsg, e);
                }
            } else {
                if (mgate.getHead().getCommand() == 36) {
                    imsg = mgate.getInnerMessage();
                    if (imsg.getMessageType() == MessageType.MSG_ZJ) {
                        gwmsg = (MessageZj) imsg;

                        if ((gwmsg.head.c_func == 15) || (gwmsg.head.c_func == 36)) {
                            return;
                        }

                        if ((gwmsg != null) && (log.isDebugEnabled())) log.debug("网关下行失败报文,转短信通道:" + gwmsg);
                        this.msgQueue.sendMessageByUms(gwmsg);
                        return;
                    }
                    if (imsg.getMessageType() != MessageType.MSG_GW_10) return;
                    gwmsg = (MessageGw) imsg;
                    if ((gwmsg.afn() != 5) && (gwmsg.afn() != 12) && (gwmsg.afn() != 13) && (gwmsg.afn() != 14) && (gwmsg.afn() != 11) && (gwmsg.afn() != 10) && (gwmsg.afn() != 16) && (gwmsg.afn() != 1) && (gwmsg.afn() != 4) && (gwmsg.afn() != 8) && (gwmsg.afn() != 9))
                        return;
                    this.msgQueue.sendMessageByUms(gwmsg);
                    return;
                }

                if (mgate.getHead().getCommand() == 50) {
                    String gateProfile = new String(mgate.getData().array());
                    FasSystem.getFasSystem().addGprsGateProfile(e.getClient().getPeerAddr(), gateProfile);
                    return;
                }

                log.error("其它类型命令。");
            }
        } else if (msg.getMessageType() == MessageType.MSG_ZJ) {
            _handleZjMessage((MessageZj) msg, e);
        }
    }

    private void _handleZjMessage(MessageZj zjmsg, ReceiveMessageEvent event) {
        if (log.isDebugEnabled()) {
            log.debug("网关上行报文:" + zjmsg);
        }

        ComRtu rtu = RtuManage.getInstance().getComRtuInCache(zjmsg.head.rtua);
        if (rtu == null) {
            String strRtua = HexDump.toHex(zjmsg.head.rtua);
            log.warn("终端不在缓存中，需要重新加载。rtua=" + strRtua);

            rtu = new ComRtu();
            rtu.setLogicAddress(strRtua);
            rtu.setRtua(zjmsg.head.rtua);

            RtuManage.getInstance().putComRtuToCache(rtu);
        }

        rtu.setLastGprsTime(System.currentTimeMillis());
        rtu.setLastIoTime(rtu.getLastGprsTime());

        String gprsIpAddr = zjmsg.getPeerAddr();
        if ((gprsIpAddr != null) && (gprsIpAddr.length() > 0)) {
            rtu.setRtuIpAddr(gprsIpAddr);
        }

        int flow = zjmsg.length();
        if ((zjmsg.head.c_func == 36) || (zjmsg.head.c_func == 34)) {
            rtu.addDownGprsFlowmeter(flow);
            rtu.addUpGprsFlowmeter(flow);
            rtu.incUpGprsCount();
            rtu.incDownGprsCount();
        } else if (zjmsg.head.c_func == 33) {
            rtu.addUpGprsFlowmeter(flow);
            rtu.addDownGprsFlowmeter(flow - 3);
            rtu.incUpGprsCount();
            rtu.incDownGprsCount();
        } else if (zjmsg.head.c_func == 2) {
            rtu.incTaskCount();
            rtu.addUpGprsFlowmeter(flow);
            rtu.incUpGprsCount();
        } else {
            rtu.addUpGprsFlowmeter(flow);
            rtu.incUpGprsCount();
        }

        try {
            String gateAddr = event.getClient().getPeerAddr();
            if (!(gateAddr.equals(rtu.getActiveGprs()))) {
                rtu.setActiveGprs(gateAddr);

                RtuParamsCache.getInstance().addRtu(rtu);

                String serverAddr = zjmsg.getServerAddress();
                if ((serverAddr != null) && (rtu.getCommAddress() != null) && ("02".equals(rtu.getCommType())) && (!(serverAddr.equals(rtu.getCommAddress())))) {
                    rtu.setMisGprsAddress(serverAddr);
                    log.warn("终端实际上行地址与资产表不一致：rtua=" + HexDump.toHex(zjmsg.head.rtua) + ",serverAddress=" + serverAddr);
                }
            }
        } catch (Exception err) {
            log.error("update activeGprs exp:" + err.getLocalizedMessage(), err);
        }

        if (zjmsg.head.c_func == 36) {
            rtu.setLastHeartbeat(System.currentTimeMillis());
            rtu.incHeartbeat();
            HeartbeatPersist.getInstance().handleHeartbeat(rtu.getRtua());

            return;
        }

        RtuCommFlowCache.getInstance().addRtu(rtu);

        if (this.asyncDbService != null) {
            this.asyncDbService.log2Db(zjmsg);
        }

        if (zjmsg.head.c_func == 15) this.udefQueue.offer(zjmsg);
        else this.msgQueue.offer(zjmsg);
    }

    private void _handleGwMessage(MessageGw gwmsg, ReceiveMessageEvent event) {
        if (log.isDebugEnabled()) {
            log.debug("网关上行报文:" + gwmsg);
        }

        ComRtu rtu = RtuManage.getInstance().getComRtuInCache(gwmsg.head.rtua);
        if (rtu == null) {
            String strRtua = HexDump.toHex(gwmsg.head.rtua);
            log.warn("终端不在缓存中，需要重新加载。rtua=" + strRtua);

            rtu = new ComRtu();
            rtu.setLogicAddress(strRtua);
            rtu.setRtua(gwmsg.head.rtua);
            RtuManage.getInstance().putComRtuToCache(rtu);
        }

        rtu.setLastGprsTime(System.currentTimeMillis());
        rtu.setLastIoTime(rtu.getLastGprsTime());

        String gprsIpAddr = gwmsg.getPeerAddr();
        if ((gprsIpAddr != null) && (gprsIpAddr.length() > 0)) {
            rtu.setRtuIpAddr(gprsIpAddr);
        }

        int flow = gwmsg.length();
        if (gwmsg.isNeedConfirm()) {
            rtu.addDownGprsFlowmeter(16);
            rtu.addUpGprsFlowmeter(flow);
            rtu.incUpGprsCount();
            rtu.incDownGprsCount();
        } else if (gwmsg.afn() == 11) {
            rtu.incTaskCount();
            rtu.addUpGprsFlowmeter(flow);
            rtu.incUpGprsCount();
        } else {
            rtu.addUpGprsFlowmeter(flow);
            rtu.incUpGprsCount();
        }

        try {
            String gateAddr = event.getClient().getPeerAddr();
            if (!(gateAddr.equals(rtu.getActiveGprs()))) {
                rtu.setActiveGprs(gateAddr);

                RtuParamsCache.getInstance().addRtu(rtu);

                String serverAddr = gwmsg.getServerAddress();
                if ((serverAddr != null) && (rtu.getCommAddress() != null) && ("02".equals(rtu.getCommType())) && (!(serverAddr.equals(rtu.getCommAddress())))) {
                    rtu.setMisGprsAddress(serverAddr);
                    log.warn("终端实际上行地址与资产表不一致：rtua=" + HexDump.toHex(gwmsg.head.rtua) + ",serverAddress=" + serverAddr);
                }
            }
        } catch (Exception err) {
            log.error("update activeGprs exp:" + err.getLocalizedMessage(), err);
        }

        if (gwmsg.afn() == 2) {
            rtu.setLastHeartbeat(System.currentTimeMillis());
            rtu.incHeartbeat();
            HeartbeatPersist.getInstance().handleHeartbeat(rtu.getRtua());
        }

        RtuCommFlowCache.getInstance().addRtu(rtu);

        if (this.asyncDbService != null) {
            this.asyncDbService.log2Db(gwmsg);
        }

        if (gwmsg.afn() == 15) this.udefQueue.offer(gwmsg);
        else this.msgQueue.offer(gwmsg);
    }

    private void onSendMessage(SendMessageEvent e) {
        IMessage message = e.getMessage();
        IMessage rtuMsg = null;
        if (message.getMessageType() == MessageType.MSG_GATE) {
            MessageGate gateMsg = (MessageGate) message;

            if (gateMsg.getHead().getCommand() == 17) {
                return;
            }
            if (gateMsg.getHead().getCommand() == 33) {
                rtuMsg = gateMsg.getInnerMessage();
                rtuMsg.setTxfs(gateMsg.getTxfs());
                rtuMsg.setIoTime(gateMsg.getIoTime());
                rtuMsg.setSource(gateMsg.getSource());
                break label109:
            }

            return;
        }
        if (message.getMessageType() == MessageType.MSG_ZJ) {
            rtuMsg = message;
        }
        if (rtuMsg == null) {
            label109:
            return;
        }

        ComRtu rtu = RtuManage.getInstance().getComRtuInCache(rtuMsg.getRtua());
        if (rtu == null) {
            return;
        }

        int flow = rtuMsg.length();
        rtu.incDownGprsCount();
        rtu.addDownGprsFlowmeter(flow);

        RtuCommFlowCache.getInstance().addRtu(rtu);

        if (this.asyncDbService != null) this.asyncDbService.log2Db(rtuMsg);
    }

    public void setMsgQueue(FEMessageQueue msgQueue) {
        this.msgQueue = msgQueue;
    }

    public void setUdefQueue(UserDefineMessageQueue udefQueue) {
        this.udefQueue = udefQueue;
    }

    public void setHeartBeat(Object heartBeat) {
    }

    public final void setAsyncDbService(AsyncService asyncDbService) {
        this.asyncDbService = asyncDbService;
    }
}