package com.hzjbbis.fk.fe.config;

import com.hzjbbis.fk.FasSystem;
import com.hzjbbis.fk.clientmod.ClientModule;
import com.hzjbbis.fk.common.events.BasicEventHook;
import com.hzjbbis.fk.common.spi.socket.IClientIO;
import com.hzjbbis.fk.common.spi.socket.abstra.BaseSocketServer;
import com.hzjbbis.fk.fe.ChannelManage;
import com.hzjbbis.fk.fe.fiber.FiberManage;
import com.hzjbbis.fk.fe.gprs.GateMessageEventHandler;
import com.hzjbbis.fk.fe.ums.SmsMessageEventHandler;
import com.hzjbbis.fk.fe.ums.UmsModule;
import com.hzjbbis.fk.fe.ums.protocol.UmsCommands;
import com.hzjbbis.fk.message.IMessageCreator;
import com.hzjbbis.fk.message.gate.MessageGateCreator;
import com.hzjbbis.fk.sockserver.TcpSocketServer;
import com.hzjbbis.fk.sockserver.io.SimpleIoHandler;
import com.hzjbbis.fk.utils.ApplicationContextUtil;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationPropertiesConfig {
    private static final Logger log = Logger.getLogger(ApplicationPropertiesConfig.class);
    private static final ApplicationPropertiesConfig config = new ApplicationPropertiesConfig();

    private int bufLength = 10240;
    private int timeout = 2;
    private int heartInterval = 10;
    private int requestNum = 500;
    private String umsServerAddr;
    private int umsSendSpeed = 1000;
    private int sendUserLimit = 10;
    private int sendRtuLimit = 50;
    private int retrieveMsgLimit = 30;
    private long noUpLogAlertTime = 1800000L;
    private String noUpAlertMobiles = "";
    private String noUpAlertContent = "警告：30分钟没有收到短信上行";
    private long sleepInterval = 10000L;
    private String umsProtocolId = "ums.protocol";
    private String gprsGateClients;
    private String smsGateClients;
    private String bpServer;
    private String monitorServer;
    private GateMessageEventHandler gprsMessageEventHandler;
    private String gprsMessageEventHandlerId = "fe.event.handle.gprs";
    private SmsMessageEventHandler smsMessageEventHandler;
    private String smsMessageEventHandlerId = "fe.event.handle.ums";
    private BasicEventHook bpMessageEventHandler;
    private BasicEventHook monitorEventHandler;
    private String bpMessageEventHandlerId = "bpserver.event.hook";
    private String monitorEventHandlerId = "monitor.event.handler";
    private String monitorMessageCreator = "messageCreator.Monitor";

    private List<ClientModule> gprsClientModules = new ArrayList();
    private List<UmsModule> umsClientModules = new ArrayList();
    private List<BaseSocketServer> socketServers = new ArrayList();
    private List<BasicEventHook> eventHandlers = new ArrayList();

    public static final ApplicationPropertiesConfig getInstance() {
        return config;
    }

    public void setGprsGateClients(String gprsClients) {
        this.gprsGateClients = gprsClients.trim();
    }

    public boolean addGprsGates(String clientsUrl) {
        List gateClients = createGprsGateClients(clientsUrl);
        boolean result = false;
        for (ClientModule gate : gateClients) {
            gate.init();
            ChannelManage.getInstance().addGprsClient(gate);
            FasSystem.getFasSystem().addModule(gate);
            gate.start();
            result = true;
        }
        return result;
    }

    public boolean addGprsGate(String hostIp, int port, String gateName) {
        String url = hostIp + ":" + port;
        if ((gateName != null) && (gateName.length() >= 1)) {
            url = url + "?name=" + gateName;
        }
        return addGprsGates(url);
    }

    public List<ClientModule> createGprsGateClients(String clientsUrl) {
        List clients = new ArrayList();

        if ((clientsUrl == null) || (clientsUrl.length() < 2)) {
            return clients;
        }
        String[] urls = clientsUrl.split(";");
        label606:
        for (String url : urls) {
            Map result = parseUrlConfig(url);
            String ip = null;
            String param = null;
            String gateIpPostFix = null;
            int port = 0;
            try {
                ip = (String) result.get("ip");
                int index = ip.lastIndexOf(".");
                if (index > 0) {
                    gateIpPostFix = ip.substring(index + 1);
                } else {
                    gateIpPostFix = ip;
                }
                param = (String) result.get("port");
                if (param == null) {
                    log.error("TCP Socket Server config miss port");
                    break label606:
                }
                port = Integer.parseInt(param);
            } catch (Exception e) {
                log.error("gprs client config exception,port=" + param, e);
                break label606:
            }

            ClientModule gprsClient = new ClientModule();
            gprsClient.setModuleType("gprsClient");
            gprsClient.setHostIp(ip);
            gprsClient.setHostPort(port);

            param = (String) result.get("name");
            if (param == null) {
                param = "gprs-" + gateIpPostFix;
            }
            gprsClient.setName(param);

            param = (String) result.get("bufLength");
            if (param != null) {
                try {
                    this.bufLength = Integer.parseInt(param);
                } catch (Exception e) {
                    log.error("bufLength config err:" + param);
                }
            }
            gprsClient.setBufLength(this.bufLength);

            IMessageCreator messageCreator = new MessageGateCreator();
            param = (String) result.get("messageCreator");
            if (param != null) {
                IMessageCreator mc = (IMessageCreator) ApplicationContextUtil.getBean(param);
                if (mc == null) try {
                    Class clz = Class.forName(param);
                    mc = (IMessageCreator) clz.newInstance();
                } catch (Exception localException1) {
                }
                if (mc != null) {
                    messageCreator = mc;
                }
            }
            gprsClient.setMessageCreator(messageCreator);

            param = (String) result.get("txfs");
            if (param != null) gprsClient.setTxfs(param);
            else {
                gprsClient.setTxfs("02");
            }
            param = (String) result.get("timeout");
            if (param != null) try {
                this.timeout = Integer.parseInt(param);
            } catch (Exception localException2) {
            }
            gprsClient.setTimeout(this.timeout);

            if (this.gprsMessageEventHandler == null)
                this.gprsMessageEventHandler = ((GateMessageEventHandler) ApplicationContextUtil.getBean(this.gprsMessageEventHandlerId));
            if (this.gprsMessageEventHandler == null) {
                log.error("gprsMessageEventHandler == null.");
                return clients;
            }
            gprsClient.setEventHandler(this.gprsMessageEventHandler);

            param = (String) result.get("heartInterval");
            if (param != null) try {
                this.heartInterval = Integer.parseInt(param);
            } catch (Exception localException3) {
            }
            gprsClient.setHeartInterval(this.heartInterval);

            gprsClient.setRequestNum(this.requestNum);

            clients.add(gprsClient);
        }
        return clients;
    }

    private void parseGprsGateClients() {
        this.gprsClientModules.addAll(createGprsGateClients(this.gprsGateClients));
    }

    public void setSmsGateClients(String smsGateClients) {
        this.smsGateClients = smsGateClients.trim();
    }

    public boolean addUmsClients(String clientsUrl) {
        List clients = createSmsGateClients(clientsUrl);
        boolean result = false;
        for (UmsModule ums : clients) {
            ChannelManage.getInstance().addUmsClient(ums);
            FiberManage.getInstance().schedule(ums);
            FasSystem.getFasSystem().addModule(ums);
            ums.start();
            result = true;
        }
        return result;
    }

    public boolean addUmsClient(String appid, String pwd) {
        return addUmsClients(appid + ":" + pwd);
    }

    private List<UmsModule> createSmsGateClients(String clientsUrl) {
        List clients = new ArrayList();
        if ((clientsUrl == null) || (clientsUrl.length() < 2)) {
            return clients;
        }

        if ((this.umsServerAddr == null) || (this.umsServerAddr.length() < 5)) {
            log.error("UMS服务器IP:PORT没有定义");
            return clients;
        }
        int index = this.umsServerAddr.indexOf(":");
        if (index <= 0) {
            log.error("ums服务器地址错误=" + this.umsServerAddr);
            return clients;
        }

        String hostIp = this.umsServerAddr.substring(0, index).trim();
        String hostPort = this.umsServerAddr.substring(index + 1).trim();
        int port = 0;
        try {
            port = Integer.parseInt(hostPort);
        } catch (Exception e) {
            log.error("hostPort=" + hostPort);
            return clients;
        }

        String[] urls = clientsUrl.split(";");
        for (String url : urls) {
            Map result = parseUrlConfig(url);
            String appid = null;
            String password = null;
            appid = (String) result.get("ip");
            password = (String) result.get("port");
            if (appid == null) {
                appid = password;
            }

            UmsModule umsClient = new UmsModule();
            umsClient.setHostIp(hostIp);
            umsClient.setHostPort(port);
            umsClient.setAppid(appid);
            umsClient.setApppwd(password);
            umsClient.setFiber(true);

            umsClient.setName("ums-" + appid);
            umsClient.setReply("95598" + appid);

            String param = (String) result.get("umsProtocol");
            if (param == null) {
                param = this.umsProtocolId;
            }

            UmsCommands umsProto = null;
            try {
                umsProto = (UmsCommands) ApplicationContextUtil.getBean(param);
                if (umsProto == null) {
                    Class clz = Class.forName(param);
                    umsProto = (UmsCommands) clz.newInstance();
                }
            } catch (Exception e) {
                log.error("appid=" + appid + ",channel's umsProtocol error:" + param);
                return clients;
            }
            umsClient.setUmsProtocol(umsProto);

            param = (String) result.get("txfs");
            if (param != null) {
                umsClient.setTxfs(param);
            }
            if (this.smsMessageEventHandler == null)
                this.smsMessageEventHandler = ((SmsMessageEventHandler) ApplicationContextUtil.getBean(this.smsMessageEventHandlerId));
            if (this.smsMessageEventHandler == null) {
                log.error("smsMessageEventHandler == null.");
                return clients;
            }
            umsClient.setEventHandler(this.smsMessageEventHandler);

            umsClient.setUmsSendSpeed(this.umsSendSpeed);
            umsClient.setSendUserLimit(this.sendUserLimit);
            umsClient.setSendRtuLimit(this.sendRtuLimit);
            umsClient.setRetrieveMsgLimit(this.retrieveMsgLimit);
            umsClient.setNoUpLogAlertTime(this.noUpLogAlertTime);
            umsClient.setSleepInterval(this.sleepInterval);
            if ((this.noUpAlertMobiles != null) && (this.noUpAlertMobiles.length() > 0)) {
                List mobiles = new ArrayList();
                String[] mbs = this.noUpAlertMobiles.split(";");
                for (String m : mbs)
                    mobiles.add(m.trim());
                umsClient.setSimNoList(mobiles);
            }

            umsClient.setAlertContent("UMS Channel:" + appid + ". " + this.noUpAlertContent);
            clients.add(umsClient);
        }
        return clients;
    }

    private void parseSmsGateClients() {
        this.umsClientModules.addAll(createSmsGateClients(this.smsGateClients));
    }

    public void setBpServer(String bpServers) {
        this.bpServer = bpServers.trim();
    }

    private void parseBpServer() {
        Map result = parseUrlConfig(this.bpServer);
        int port = 0;
        int bufLength = 10240;
        String param = null;
        try {
            param = (String) result.get("port");
            if (param == null) {
                log.error("Business Processor TCP Socket Server config miss port");
                return;
            }
            port = Integer.parseInt(param);
        } catch (Exception e) {
            log.error("Business Processor TCP Socket Server config exception,port=" + param, e);
            return;
        }

        TcpSocketServer socketServer = new TcpSocketServer();
        socketServer.setPort(port);
        param = (String) result.get("ip");
        if (param != null) {
            socketServer.setIp(param);
        }
        param = (String) result.get("name");
        if (param == null) {
            param = "bp-" + port;
        }
        socketServer.setName(param);

        param = (String) result.get("bufLength");
        if (param != null) {
            try {
                bufLength = Integer.parseInt(param);
            } catch (Exception e) {
                log.error("bufLength config err:" + param);
            }
        }
        socketServer.setBufLength(bufLength);

        int ioThreadSize = 2;
        param = (String) result.get("ioThreadSize");
        if (param != null) try {
            ioThreadSize = Integer.parseInt(param);
            socketServer.setIoThreadSize(ioThreadSize);
        } catch (Exception localException1) {
        }
        IMessageCreator messageCreator = new MessageGateCreator();
        param = (String) result.get("messageCreator");
        if (param != null) {
            IMessageCreator mc = null;
            try {
                mc = (IMessageCreator) ApplicationContextUtil.getBean(param);
            } catch (Exception localException2) {
            }
            if (mc == null) try {
                Class clz = Class.forName(param);
                mc = (IMessageCreator) clz.newInstance();
            } catch (Exception localException3) {
            }
            if (mc != null) {
                messageCreator = mc;
            }
        }
        socketServer.setMessageCreator(messageCreator);

        IClientIO ioHandler = new SimpleIoHandler();
        param = (String) result.get("ioHandler");
        if (param != null) {
            IClientIO ioh = null;
            try {
                ioh = (IClientIO) ApplicationContextUtil.getBean(param);
            } catch (Exception localException4) {
            }
            if (ioh == null) try {
                Class clz = Class.forName(param);
                ioh = (IClientIO) clz.newInstance();
            } catch (Exception localException5) {
            }
            if (ioh != null) ioHandler = ioh;
        }
        socketServer.setIoHandler(ioHandler);

        param = (String) result.get("timeout");
        int timeout = 180;
        if (param != null) try {
            timeout = Integer.parseInt(param);
        } catch (Exception localException6) {
        }
        socketServer.setTimeout(timeout);

        this.socketServers.add(socketServer);
        if (this.bpMessageEventHandler == null) {
            this.bpMessageEventHandler = ((BasicEventHook) ApplicationContextUtil.getBean(this.bpMessageEventHandlerId));
        }
        this.bpMessageEventHandler.setSource(socketServer);
        this.eventHandlers.add(this.bpMessageEventHandler);
    }

    public void setMonitorServer(String monitorServers) {
        this.monitorServer = monitorServers.trim();
    }

    private void parseMonitorServer() {
        if ((this.monitorServer == null) || (this.monitorServer.length() < 2)) return;
        Map result = parseUrlConfig(this.monitorServer);
        int port = 0;
        int bufLength = 51200;
        String param = null;
        try {
            param = (String) result.get("port");
            if (param == null) {
                log.error("Monitor Socket Server config miss port");
                return;
            }
            port = Integer.parseInt(param);
        } catch (Exception e) {
            log.error("Monitor Socket Server config exception,port=" + param, e);
            return;
        }

        TcpSocketServer mServer = new TcpSocketServer();
        mServer.setPort(port);
        param = (String) result.get("ip");
        if (param != null) {
            mServer.setIp(param);
        }
        param = (String) result.get("name");
        if (param == null) {
            param = "monitor-" + port;
        }
        mServer.setName(param);

        param = (String) result.get("bufLength");
        if (param != null) {
            try {
                bufLength = Integer.parseInt(param);
            } catch (Exception e) {
                log.error("bufLength config err:" + param);
            }
        }
        mServer.setBufLength(bufLength);

        int ioThreadSize = 1;
        param = (String) result.get("ioThreadSize");
        if (param != null) try {
            ioThreadSize = Integer.parseInt(param);
            mServer.setIoThreadSize(ioThreadSize);
        } catch (Exception localException1) {
        }
        IMessageCreator messageCreator = (IMessageCreator) ApplicationContextUtil.getBean(this.monitorMessageCreator);
        param = (String) result.get("messageCreator");
        if (param != null) {
            IMessageCreator mc = null;
            try {
                mc = (IMessageCreator) ApplicationContextUtil.getBean(param);
            } catch (Exception exp) {
                mc = null;
            }
            if (mc == null) {
                try {
                    Class clz = Class.forName(param);
                    mc = (IMessageCreator) clz.newInstance();
                } catch (Exception e) {
                    mc = null;
                }
            }
            if (mc != null) {
                messageCreator = mc;
            }
        }
        mServer.setMessageCreator(messageCreator);

        IClientIO ioHandler = new SimpleIoHandler();
        param = (String) result.get("ioHandler");
        if (param != null) {
            IClientIO ioh = null;
            try {
                ioh = (IClientIO) ApplicationContextUtil.getBean(param);
            } catch (Exception e) {
                ioh = null;
            }
            if (ioh == null) try {
                Class clz = Class.forName(param);
                ioh = (IClientIO) clz.newInstance();
            } catch (Exception localException2) {
            }
            if (ioh != null) ioHandler = ioh;
        }
        mServer.setIoHandler(ioHandler);

        param = (String) result.get("timeout");
        int timeout = 1800;
        if (param != null) try {
            timeout = Integer.parseInt(param);
        } catch (Exception localException3) {
        }
        mServer.setTimeout(timeout);

        this.socketServers.add(mServer);
        if (this.monitorEventHandler == null) {
            this.monitorEventHandler = ((BasicEventHook) ApplicationContextUtil.getBean(this.monitorEventHandlerId));
        }
        this.monitorEventHandler.setSource(mServer);
        this.eventHandlers.add(this.monitorEventHandler);
    }

    private Map<String, String> parseUrlConfig(String url) {
        Map result = new HashMap();

        String hostAddr = null;
        String params = null;
        int index = url.indexOf("?");
        if (index > 0) {
            hostAddr = url.substring(0, index);
            params = url.substring(index + 1);
        } else {
            hostAddr = url;
        }
        if (hostAddr != null) {
            index = hostAddr.indexOf(":");
            if (index > 0) {
                String ip = hostAddr.substring(0, index);
                String port = hostAddr.substring(index + 1);
                result.put("ip", ip);
                result.put("port", port);
            } else {
                result.put("port", hostAddr);
            }
        }
        if (params != null) {
            String[] paramArray = params.split("&");
            if (paramArray != null) for (String param : paramArray) {
                index = param.indexOf("=");
                if (index < 0) {
                    log.error("Server config malformed,miss'=' :" + param);
                } else {
                    String name = param.substring(0, index);
                    String value = param.substring(index + 1);
                    result.put(name, value);
                }
            }
        }
        return result;
    }

    public final List<BaseSocketServer> getSocketServers() {
        return this.socketServers;
    }

    public final List<BasicEventHook> getEventHandlers() {
        return this.eventHandlers;
    }

    public final void setMonitorEventHandler(BasicEventHook monitorEventHandler) {
        this.monitorEventHandler = monitorEventHandler;
    }

    public final void setMonitorEventHandlerId(String monitorEventHandlerId) {
        this.monitorEventHandlerId = monitorEventHandlerId;
    }

    public final void setMonitorMessageCreator(String monitorMessageCreator) {
        this.monitorMessageCreator = monitorMessageCreator;
    }

    public void parseConfig() {
        parseGprsGateClients();
        parseSmsGateClients();
        parseBpServer();
        parseMonitorServer();
    }

    public final void setBufLength(int bufLength) {
        this.bufLength = bufLength;
    }

    public final void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public final void setHeartInterval(int heartInterval) {
        this.heartInterval = heartInterval;
    }

    public final void setRequestNum(int requestNum) {
        this.requestNum = requestNum;
    }

    public final void setUmsSendSpeed(int umsSendSpeed) {
        this.umsSendSpeed = umsSendSpeed;
    }

    public final void setSendUserLimit(int sendUserLimit) {
        this.sendUserLimit = sendUserLimit;
    }

    public final void setSendRtuLimit(int sendRtuLimit) {
        this.sendRtuLimit = sendRtuLimit;
    }

    public final void setRetrieveMsgLimit(int retrieveMsgLimit) {
        this.retrieveMsgLimit = retrieveMsgLimit;
    }

    public final void setNoUpLogAlertTime(long noUpLogAlertTime) {
        this.noUpLogAlertTime = noUpLogAlertTime;
    }

    public final void setNoUpAlertMobiles(String noUpAlertMobiles) {
        this.noUpAlertMobiles = noUpAlertMobiles;
    }

    public final void setNoUpAlertContent(String noUpAlertContent) {
        this.noUpAlertContent = noUpAlertContent;
    }

    public final void setUmsProtocolId(String umsProtocolId) {
        this.umsProtocolId = umsProtocolId;
    }

    public final void setGprsMessageEventHandler(GateMessageEventHandler gprsMessageEventHandler) {
        this.gprsMessageEventHandler = gprsMessageEventHandler;
    }

    public final void setGprsMessageEventHandlerId(String gprsMessageEventHandlerId) {
        this.gprsMessageEventHandlerId = gprsMessageEventHandlerId;
    }

    public final void setSmsMessageEventHandler(SmsMessageEventHandler smsMessageEventHandler) {
        this.smsMessageEventHandler = smsMessageEventHandler;
    }

    public final void setSmsMessageEventHandlerId(String smsMessageEventHandlerId) {
        this.smsMessageEventHandlerId = smsMessageEventHandlerId;
    }

    public final void setBpMessageEventHandler(BasicEventHook bpMessageEventHandler) {
        this.bpMessageEventHandler = bpMessageEventHandler;
    }

    public final void setBpMessageEventHandlerId(String bpMessageEventHandlerId) {
        this.bpMessageEventHandlerId = bpMessageEventHandlerId;
    }

    public final List<ClientModule> getGprsClientModules() {
        return this.gprsClientModules;
    }

    public final List<UmsModule> getUmsClientModules() {
        return this.umsClientModules;
    }

    public final void setUmsServerAddr(String umsServerAddr) {
        this.umsServerAddr = umsServerAddr;
    }

    public void setSleepInterval(long sleepInterval) {
        this.sleepInterval = sleepInterval;
    }
}