package com.hisun.atmp.tcp;

import com.hisun.exception.HiException;
import com.hisun.framework.event.IServerInitListener;
import com.hisun.framework.event.ServerEvent;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import com.hisun.protocol.tcp.HiMessageInOut;
import com.hisun.protocol.tcp.parser.HiHostIPXMLParser;
import com.hisun.protocol.tcp.parser.HiHostIpItem;
import com.hisun.protocol.tcp.parser.HiHostIpTable;
import com.hisun.pubinterface.IHandler;
import com.hisun.util.HiByteBuffer;
import com.hisun.util.HiResource;
import com.hisun.util.HiStringManager;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;

public class HiTcpNotifyConnector implements IHandler, IServerInitListener {
    private static final HiStringManager sm = HiStringManager.getManager();
    private String _ip;
    private int _preLen;
    private int _port;
    private Logger _log;
    private HiHostIpTable _hostIpTable;
    private String _ipFile;
    private int _tmOut;
    private HiMessageInOut _msginout;
    private static final String HOST_NAME = "HST";

    public HiTcpNotifyConnector() {
        this._hostIpTable = null;

        this._tmOut = 30;
        this._msginout = new HiMessageInOut();
    }

    public void notify(HiMessageContext ctx) throws HiException {
        HiMessage msg = ctx.getCurrentMsg();
        Socket socket = new Socket();
        try {
            try {
                getHostIp(msg);
                if (this._log.isInfoEnabled()) {
                    this._log.info("preLen:[" + this._preLen + "];ip:[" + this._ip + "];port:[" + this._port + "]");
                }

                this._msginout.setPreLen(this._preLen);
                socket.connect(new InetSocketAddress(this._ip, this._port), this._tmOut * 1000);

                socket.setSoTimeout(this._tmOut * 1000);
            } catch (IOException e) {
                throw new HiException("231204", "connector connect error", e);
            }

            if (this._log.isInfoEnabled()) {
                HiByteBuffer byteBuffer = (HiByteBuffer) msg.getBody();
                this._log.info(sm.getString("HiPoolTcpConnector.send00", msg.getRequestId(), this._ip, String.valueOf(this._port), String.valueOf(byteBuffer.length()), byteBuffer));
            }

            try {
                socket.close();
            } catch (IOException byteBuffer) {
                throw new HiException("231207", "connector close error", e);
            }
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                throw new HiException("231207", "connector close error", e);
            }
        }
    }

    public void serverInit(ServerEvent arg0) throws HiException {
        this._log = arg0.getLog();
    }

    public String getHost() {
        return this._ip;
    }

    public void setHost(String host) {
        this._ip = host;
    }

    public int getPreLen() {
        return this._preLen;
    }

    public void setPreLen(int preLen) {
        this._preLen = preLen;
    }

    public int getPort() {
        return this._port;
    }

    public void setPort(int port) {
        this._port = port;
    }

    public int getTmOut() {
        return this._tmOut;
    }

    public void setTmOut(int tmOut) {
        this._tmOut = tmOut;
    }

    public void process(HiMessageContext arg0) throws HiException {
        notify(arg0);
    }

    private void getHostIp(HiMessage msg) {
        if (msg.hasHeadItem("OIP")) {
            this._ip = msg.getHeadItem("OIP");
        }

        if (StringUtils.isEmpty(this._ip)) {
            this._ip = msg.getHeadItem("SIP");
        }

        if (msg.hasHeadItem("OPT")) {
            this._port = NumberUtils.toInt(msg.getHeadItem("OPT"));
        }

        String hostName = msg.getHeadItem("HST");
        if ((this._hostIpTable != null) && (!(StringUtils.isEmpty(hostName)))) {
            HiHostIpItem item = this._hostIpTable.getHostIpItem(hostName);
            this._ip = item.ip;
            this._port = item.port;
        }
    }

    public String getIpFile() {
        return this._ipFile;
    }

    public void setIpFile(String ipFile) throws HiException {
        this._ipFile = ipFile;
        HiHostIPXMLParser parser = new HiHostIPXMLParser();
        URL url = HiResource.getResource(ipFile);
        if (url == null) throw new HiException("213302", ipFile);
        try {
            this._hostIpTable = ((HiHostIpTable) parser.parse(HiResource.getResource(ipFile)));
        } catch (Exception e) {
            throw new HiException("212005", ipFile, e);
        }
    }
}