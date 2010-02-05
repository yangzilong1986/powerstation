package com.hisun.atmp.tcp;

import com.hisun.atmp.handler.HiGetTxnCode;
import com.hisun.exception.HiException;
import com.hisun.framework.event.ServerEvent;
import com.hisun.hilog4j.HiLog;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import com.hisun.protocol.tcp.ISocketHandler;
import com.hisun.protocol.tcp.filters.CloseSocketFilter;
import com.hisun.protocol.tcp.filters.ExceptionCloseSocketFilter;
import com.hisun.protocol.tcp.filters.IpCheckFilter;
import com.hisun.protocol.tcp.filters.SocketHandlers;
import com.hisun.protocol.tcp.imp.HiTcpListener;
import com.hisun.util.HiByteBuffer;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class HiTcpSynListener2 extends HiTcpListener {
    private String _DIBAOMsgType;

    public HiTcpSynListener2() {
        this._DIBAOMsgType = "PLTIN2";
    }

    public void serverInit(ServerEvent arg0) throws HiException {
        super.serverInit(arg0);
    }

    public ISocketHandler buildSocketHandler() {
        List filters = new ArrayList();

        filters.add(new ExceptionCloseSocketFilter(this.log));

        if (getIpset() != null) {
            setIpcheck(new IpCheckFilter(getIpset(), this.log));
            filters.add(getIpcheck());
        }

        filters.add(getOpthandler());
        filters.add(getPoolhandler());

        filters.add(new CloseSocketFilter(this.log));
        filters.add(SocketHandlers.createContextFilter(getServer().getName(), getMsgType()));

        filters.add(SocketHandlers.receiveMessageFilter(this.log, this.msginout));

        ISocketHandler handler = new ISocketHandler() {
            public void process(Socket socket, HiMessageContext ctx) {
                HiMessage msg = ctx.getCurrentMsg();
                try {
                    int type = HiGetTxnCode.getType(ctx);
                    switch (type) {
                        case 1:
                            msg.setType(HiTcpSynListener2.this._DIBAOMsgType);
                            socket.close();
                            break;
                        case 0:
                    }

                    if (HiTcpSynListener2.this.log.isInfoEnabled()) {
                        HiTcpSynListener2.this.log.info("recv msg3:[" + msg + "]");
                    }
                    HiTcpSynListener2.this.getServer().process(ctx);

                    switch (type) {
                        case 1:
                            break;
                        case 0:
                            HiTcpSynListener2.this.send(socket, ctx);
                    }

                } catch (Throwable e) {
                    HiTcpSynListener2.this.log.error("[" + msg.getRequestId() + "]:" + socket, e);
                }
                HiLog.close(ctx.getCurrentMsg());
            }
        };
        return buildSocketHandler(filters, handler);
    }

    private void send(Socket socket, HiMessageContext ctx) throws HiException {
        HiMessage msg = ctx.getCurrentMsg();
        try {
            this.msginout.write(socket.getOutputStream(), msg);
            if (this.log.isInfoEnabled()) {
                HiByteBuffer byteBuffer = (HiByteBuffer) msg.getBody();
                String ip = socket.getInetAddress().getHostAddress();
                this.log.info(sm.getString("tcplistener.send", msg.getRequestId(), ip, String.valueOf(byteBuffer.length()), byteBuffer.toString()));
            }
        } catch (IOException e) {
            this.log.error(sm.getString("tcplistener.err.write", socket.toString()), e);
            throw HiException.makeException("231201", e);
        }
    }

    public String getMsgType1() {
        return getMsgType();
    }

    public void setMsgType1(String type) {
        setMsgType(type);
    }

    public String getMsgType2() {
        return this._DIBAOMsgType;
    }

    public void setMsgType2(String type) {
        this._DIBAOMsgType = type;
    }
}