package com.hzjbbis.fk.monitor.client;

import com.hzjbbis.fk.message.IMessageCreator;
import com.hzjbbis.fk.monitor.client.biz.FileCommand;
import com.hzjbbis.fk.monitor.client.biz.ProfileCommand;
import com.hzjbbis.fk.monitor.client.biz.SystemCommand;
import com.hzjbbis.fk.monitor.client.biz.TraceRTUCommand;
import com.hzjbbis.fk.monitor.message.MonitorMessageCreator;
import com.hzjbbis.fk.sockclient.JSocket;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class MonitorClient {
    private JSocket socket = new JSocket();
    private String hostIp = "127.0.0.1";
    private int hostPort = 10006;
    private int bufLength = 32768;
    private IMessageCreator messageCreator = new MonitorMessageCreator();
    private int timeout = 2;
    private MonitorSocketListener listener = new MonitorSocketListener();
    private IMonitorReplyListener replyListener = null;

    private final ProfileCommand profileCommand = new ProfileCommand();
    private final FileCommand fileCommand = new FileCommand();
    private final TraceRTUCommand traceCommand = new TraceRTUCommand();
    private final SystemCommand sysCommand = new SystemCommand();

    public void shutdownApplication() {
        if (!(isConnected())) return;
        this.sysCommand.shutdown(this.socket);
    }

    public void cmdListLog() {
        if (!(isConnected())) return;
        this.fileCommand.listLog(this.socket);
    }

    public void cmdListConfig() {
        if (!(isConnected())) return;
        this.fileCommand.listConfig(this.socket);
    }

    public void cmdGetFile(String path) {
        if (!(isConnected())) return;
        this.fileCommand.getFile(this.socket, path);
    }

    public void cmdPutFile(String path) {
        if (!(isConnected())) return;
        this.fileCommand.putFile(this.socket, path);
    }

    public void cmdGetProfile() {
        if (!(isConnected())) return;
        this.profileCommand.getSystemProfile(this.socket);
    }

    public void cmdGetModuleProfile() {
        if (!(isConnected())) return;
        this.profileCommand.getModuleProfile(this.socket);
    }

    public void cmdGetEventHookProfile() {
        if (!(isConnected())) return;
        this.profileCommand.getEventHookProfile(this.socket);
    }

    public void cmdGatherProfile() {
        if (!(isConnected())) return;
        this.profileCommand.gatherProfile(this.socket);
    }

    public void cmdTraceRTUs(String rtus) {
        if (!(isConnected())) return;
        String[] result = rtus.split(",");
        ArrayList array = new ArrayList();
        for (int i = 0; i < result.length; ++i) {
            if (result[i].length() != 8) continue;
            try {
                int rtua = (int) Long.parseLong(result[i], 16);
                array.add(Integer.valueOf(rtua));
            } catch (Exception e) {
                Logger log = Logger.getLogger(MonitorClient.class);
                log.error(e.getLocalizedMessage(), e);
            }
        }

        if (array.size() == 0) return;
        int[] rtuParams = new int[array.size()];
        for (int i = 0; i < array.size(); ++i)
            rtuParams[i] = ((Integer) array.get(i)).intValue();
        this.traceCommand.startTrace(this.socket, rtuParams);
    }

    public void cmdAbortTrace() {
        if (!(isConnected())) return;
        this.traceCommand.stopTrace(this.socket);
    }

    public MonitorClient() {
    }

    public MonitorClient(String ip, int port) {
        this.hostIp = ip;
        this.hostPort = port;
    }

    public void connect(String ip, int port) {
        this.hostIp = ip;
        this.hostPort = port;
        init();
    }

    public void connect() {
        init();
    }

    public void close() {
        this.socket.close();
    }

    public void init() {
        if (this.replyListener == null) {
            Logger log = Logger.getLogger(MonitorClient.class);
            log.warn("MonitorClient对象必须设置IMonitorReplyListener接口实现.");
            this.replyListener = new MockMonitorReplyListener();
        }
        this.socket.setHostIp(this.hostIp);
        this.socket.setHostPort(this.hostPort);
        this.socket.setBufLength(this.bufLength);
        this.socket.setMessageCreator(this.messageCreator);
        this.socket.setListener(this.listener);
        this.socket.setTimeout(this.timeout);
        this.socket.init();
    }

    public JSocket getSocket() {
        return this.socket;
    }

    public boolean isConnected() {
        return this.socket.isConnected();
    }

    public String getHostIp() {
        return this.hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public int getHostPort() {
        return this.hostPort;
    }

    public void setHostPort(int hostPort) {
        this.hostPort = hostPort;
    }

    public int getBufLength() {
        return this.bufLength;
    }

    public void setBufLength(int bufLength) {
        this.bufLength = bufLength;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public IMonitorReplyListener getReplyListener() {
        return this.replyListener;
    }

    public void setReplyListener(IMonitorReplyListener replyListener) {
        this.replyListener = replyListener;
        this.listener.replyListener = this.replyListener;
    }
}