package com.hzjbbis.fk.fe.msgqueue;

import com.hzjbbis.fk.common.spi.IProfile;
import com.hzjbbis.fk.common.spi.socket.IServerSideChannel;
import com.hzjbbis.fk.tracelog.TraceLog;
import com.hzjbbis.fk.utils.HexDump;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class MessageDispatch2Bp implements IProfile {
    private static final TraceLog tracer = TraceLog.getTracer();
    private static final MessageDispatch2Bp instance = new MessageDispatch2Bp();
    private Map<Byte, BpClient> a12ClientMap = new HashMap();
    private ArrayList<BpClient> clients = new ArrayList();

    public static final MessageDispatch2Bp getInstance() {
        return instance;
    }

    private BpClient findByChannel(IServerSideChannel bpChannel) {
        synchronized (this.a12ClientMap) {
            for (BpClient c : this.clients) {
                if (c.channel == bpChannel) return c;
            }
        }
        return null;
    }

    private boolean removeByChannel(IServerSideChannel bpChannel) {
        synchronized (this.a12ClientMap) {
            for (int i = 0; i < this.clients.size(); ++i) {
                if (((BpClient) this.clients.get(i)).channel == bpChannel) {
                    this.clients.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    public void clearTimeoutChannel() {
        synchronized (this.a12ClientMap) {
            boolean removeTag = false;
            for (int i = 0; i < this.clients.size(); ++i) {
                IServerSideChannel client = ((BpClient) this.clients.get(i)).channel;
                if (System.currentTimeMillis() - client.getLastIoTime() > 180000L) {
                    this.clients.remove(i);
                    removeTag = true;
                    if (tracer.isEnabled()) tracer.trace("garbage client of MessageDispatch2Bp removed:" + client);
                }
            }
            if (removeTag) divideDistrict();
        }
    }

    public void onBpClientConnected(IServerSideChannel bpChannel) {
        tracer.trace("client connected:" + bpChannel);
        if (findByChannel(bpChannel) != null) return;
        BpClient client = new BpClient(null);
        client.channel = bpChannel;
        synchronized (this.a12ClientMap) {
            this.clients.add(client);
            divideDistrict();
        }
    }

    public void onBpClientClosed(IServerSideChannel bpChannel) {
        synchronized (this.a12ClientMap) {
            if (removeByChannel(bpChannel)) {
                tracer.trace("client closed:" + bpChannel);
                divideDistrict();
            }
        }
    }

    private void divideDistrict() {
        synchronized (this.a12ClientMap) {
            for (BpClient client : this.clients) {
                client.a1Array.clear();
                client.factor = 0;
            }

            BpClient minClient = null;
            for (BpBalanceFactor.DistrictFactor df : BpBalanceFactor.getInstance().getAllDistricts()) {
                minClient = getMinFactorClient();
                if (minClient != null) {
                    minClient.a1Array.add(Byte.valueOf(df.districtCode));
                    minClient.factor += df.rtuCount;
                }
            }

            for (BpClient client : this.clients) {
                for (int i = 0; i < client.a1Array.size(); ++i)
                    this.a12ClientMap.put((Byte) client.a1Array.get(i), client);
            }
        }
        if ((tracer.isEnabled()) && (!(this.clients.isEmpty()))) tracer.trace("BP divided by A1. profile=" + profile());
    }

    private BpClient getMinFactorClient() {
        BpClient minClient = null;
        for (BpClient c : this.clients) {
            if (minClient == null) minClient = c;
            else if (c.factor < minClient.factor) minClient = c;
        }
        return minClient;
    }

    public IServerSideChannel getBpChannel(byte a1) {
        BpClient client = null;
        synchronized (this.a12ClientMap) {
            client = (BpClient) this.a12ClientMap.get(Byte.valueOf(a1));
            if (client == null) {
                BpBalanceFactor.getInstance().travelRtus();
                divideDistrict();
                client = (BpClient) this.a12ClientMap.get(Byte.valueOf(a1));
            }
        }
        if ((client != null) && (client.channel.sendQueueSize() == 0) && (client.channel.getRequestNum() != 0)) {
            return client.channel;
        }
        return null;
    }

    public IServerSideChannel getIdleChannel() {
        synchronized (this.a12ClientMap) {
            for (BpClient c : this.clients) {
                if ((c.channel.sendQueueSize() == 0) && (c.channel.getRequestNum() != 0)) return c.channel;
            }
        }
        return null;
    }

    public String profile() {
        StringBuffer sb = new StringBuffer(1024);
        sb.append("\r\n    <bp-citys>");
        synchronized (this.a12ClientMap) {
            for (BpClient c : this.clients) {
                sb.append("\r\n        <bp addr=\"").append(c.channel.getPeerAddr()).append("\" factor=\"");
                sb.append(c.factor).append("\">");
                boolean first = true;
                for (Byte B : c.a1Array)
                    if (first) {
                        sb.append(HexDump.toHex(B.byteValue()));
                        first = false;
                    } else {
                        sb.append(",").append(HexDump.toHex(B.byteValue()));
                    }
                sb.append("</bp>");
            }
        }
        sb.append("\r\n    </bp-citys>");
        return sb.toString();
    }

    private class BpClient {
        IServerSideChannel channel;
        Vector<Byte> a1Array;
        int factor;

        private BpClient() {
            this.channel = null;
            this.a1Array = new Vector();
            this.factor = 0;
        }
    }
}