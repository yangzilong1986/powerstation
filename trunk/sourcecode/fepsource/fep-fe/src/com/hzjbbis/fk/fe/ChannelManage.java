package com.hzjbbis.fk.fe;

import com.hzjbbis.fk.clientmod.ClientModule;
import com.hzjbbis.fk.common.spi.socket.IChannel;
import com.hzjbbis.fk.common.spi.socket.abstra.BaseClientChannel;
import com.hzjbbis.fk.fe.ums.UmsModule;
import com.hzjbbis.fk.model.ComRtu;
import com.hzjbbis.fk.model.RtuManage;
import com.hzjbbis.fk.utils.HexDump;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ChannelManage {
    private static final Logger log = Logger.getLogger(ChannelManage.class);

    private int rtuHeartbeatInterval = 900;
    private int rtuTransferInterval = 60;

    private long hbInterval =
    ???.rtuHeartbeatInterval*1000;
    private long tfInterval =
    ???.rtuTransferInterval*1000;

    private static ChannelManage cm = new ChannelManage();

    private final Map<String, BaseClientChannel> mapGates = new HashMap();
    private final Map<String, BaseClientChannel> mapUmsClients = new HashMap();
    private final Map<String, BaseClientChannel> mapGprsClients = new HashMap();
    public boolean testMode = false;

    public static ChannelManage getInstance() {
        return cm;
    }

    public void setRtuHeartbeatInterval(int interval) {
        this.rtuHeartbeatInterval = interval;
        this.hbInterval = (this.rtuHeartbeatInterval * 1000);
    }

    public void setRtuTransferInterval(int rtuTransferInterval) {
        this.rtuTransferInterval = rtuTransferInterval;
        this.tfInterval = (rtuTransferInterval * 1000);
    }

    public void addGprsClient(ClientModule gprsClient) {
        this.mapGates.put(gprsClient.getSocket().getPeerAddr(), gprsClient.getSocket());
        this.mapGprsClients.put(gprsClient.getSocket().getPeerAddr(), gprsClient.getSocket());
    }

    public void addUmsClient(UmsModule umsClient) {
        this.mapGates.put(umsClient.getPeerAddr(), umsClient);
        this.mapUmsClients.put(umsClient.getPeerAddr(), umsClient);
    }

    public BaseClientChannel getActiveUmsChannel() {
        for (BaseClientChannel channel : this.mapUmsClients.values())
            if (channel.isActive()) return channel;
        return null;
    }

    public BaseClientChannel getActiveGprsChannel() {
        for (BaseClientChannel channel : this.mapGprsClients.values())
            if (channel.isActive()) return channel;
        return null;
    }

    public IChannel getChannel(String key) {
        return ((IChannel) this.mapGates.get(key));
    }

    private static int communicationType(String commType) {
        if (commType == null) return -1;
        if ((commType.equals("02")) || (commType.equals("09")) || (commType.equals("04"))) return 2;
        if (commType.equals("01")) {
            return 1;
        }
        return 0;
    }

    public IChannel getChannel(int rtua) {
        IChannel channel = getGPRSChannel(rtua);
        if (channel != null) return channel;
        channel = getUmsChannel(null, rtua);
        if (channel != null) {
            return channel;
        }

        return getActiveUmsChannel();
    }

    public IChannel getGPRSChannel(int rtua) {
        ComRtu rtu = RtuManage.getInstance().getComRtuInCache(rtua);
        if (rtu == null) {
            log.warn("终端资料没有加载。rtua=" + HexDump.toHex(rtua));
            return null;
        }

        long timeSpan = System.currentTimeMillis() - rtu.getLastGprsTime();
        if ((timeSpan < this.hbInterval * 2L) && (rtu.getActiveGprs() != null)) {
            long lastReq = rtu.getLastReqTime();

            long tspan = Math.abs(System.currentTimeMillis() - lastReq);
            if ((tspan > this.tfInterval) && (rtu.getLastGprsTime() < lastReq)) return null;
            IChannel channel = getChannel(rtu.getActiveGprs());
            return channel;
        }
        return null;
    }

    public IChannel getUmsChannel(String appid, int rtua) {
        IChannel channel = null;
        if ((appid != null) && (appid.length() > 0)) {
            channel = getChannel(appid);
            if (channel != null) return channel;
        }
        if (rtua == 0) {
            return channel;
        }

        ComRtu rtu = RtuManage.getInstance().getComRtuInCache(rtua);

        String activeUms = rtu.getActiveUms();
        if ((activeUms != null) && (activeUms.length() >= 4)) {
            return getChannel(activeUms);
        }

        int cType = communicationType(rtu.getCommType());
        if (cType == 1) {
            activeUms = filterUmsAppId(rtu.getCommAddress());
            if (activeUms.length() >= 4) channel = getChannel(activeUms.substring(0, 4));
            if (channel != null) {
                if ((rtu.getActiveSubAppId() == null) && (activeUms.length() == 6))
                    rtu.setActiveSubAppId(activeUms.substring(4, 6));
                return channel;
            }

        }

        cType = communicationType(rtu.getB1CommType());
        if (cType == 1) {
            activeUms = filterUmsAppId(rtu.getB1CommAddress());
            if (activeUms.length() >= 4) channel = getChannel(activeUms.substring(0, 4));
            if (channel != null) {
                if ((rtu.getActiveSubAppId() == null) && (activeUms.length() == 6))
                    rtu.setActiveSubAppId(activeUms.substring(4, 6));
                return channel;
            }

        }

        cType = communicationType(rtu.getB2CommType());
        if (cType == 1) {
            activeUms = filterUmsAppId(rtu.getB2CommAddress());
            if (activeUms.length() >= 4) channel = getChannel(activeUms.substring(0, 4));
            if (channel != null) {
                if ((rtu.getActiveSubAppId() == null) && (activeUms.length() == 6))
                    rtu.setActiveSubAppId(activeUms.substring(4, 6));
                return channel;
            }

        }

        log.warn("终端短信通道配置不正确。RTUA=" + rtu.getLogicAddress());

        return null;
    }

    public static final String filterUmsAppId(String ums) {
        if (ums != null) {
            int index = ums.indexOf("95598");
            if (index >= 0) ums = ums.substring(index + 5);
        }
        return ums;
    }

    public static final String getUmsAppId(int rtua) {
        ComRtu rtu = RtuManage.getInstance().getComRtuInCache(rtua);

        int cType = communicationType(rtu.getCommType());
        if (cType <= 0) {
            log.error("终端主通道不是GPRS/CDMA，或者短信。RTUA＝" + rtu.getLogicAddress());
            return null;
        }

        String activeUms = rtu.getActiveUms();
        if ((activeUms != null) && (activeUms.length() > 2)) {
            return activeUms;
        }

        if (cType == 1) {
            activeUms = rtu.getCommAddress();
            if ((activeUms != null) && (activeUms.length() > 2)) {
                return activeUms;
            }
        }

        cType = communicationType(rtu.getB1CommType());
        if (cType == 1) {
            activeUms = rtu.getB1CommAddress();
            if ((activeUms != null) && (activeUms.length() > 2)) {
                return activeUms;
            }
        }
        cType = communicationType(rtu.getB2CommType());
        if (cType == 1) {
            activeUms = rtu.getB2CommAddress();
            if ((activeUms != null) && (activeUms.length() > 2)) {
                return activeUms;
            }
        }
        log.warn("终端短信通道配置不正确。RTUA=" + rtu.getLogicAddress());
        return null;
    }
}