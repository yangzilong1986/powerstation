package com.hisun.atmp.tcp;

import com.hisun.exception.HiException;
import com.hisun.framework.event.ServerEvent;
import com.hisun.hilog4j.HiLog;
import com.hisun.message.HiMessageContext;
import com.hisun.protocol.tcp.ISocketHandler;
import com.hisun.protocol.tcp.filters.MultiReaderPoolHandler;
import com.hisun.protocol.tcp.filters.SocketHandlers;
import com.hisun.protocol.tcp.imp.HiTcpListener;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class HiPOSALMListener extends HiTcpListener {
    private MultiReaderPoolHandler readerhandler;

    public HiPOSALMListener() {
        this.msginout = new HiPOSMessageInOut(null);
    }

    public void serverInit(ServerEvent arg0) throws HiException {
        HiPOSMessageInOut msginout1 = (HiPOSMessageInOut) this.msginout;
        msginout1.setLog(arg0.getLog());
        super.serverInit(arg0);
    }

    public void serverStop(ServerEvent arg0) throws HiException {
        super.serverStop(arg0);
        this.readerhandler.stop();
    }

    public ISocketHandler buildSocketHandler() {
        List filters = new ArrayList();

        filters.add(SocketHandlers.createContextFilter(getServer().getName(), getMsgType()));

        filters.add(SocketHandlers.receiveMessageFilter(this.log, this.msginout));

        filters.add(getPoolhandler());

        filters.add(SocketHandlers.lockSendMessageFilter(this.log, this.msginout));

        ISocketHandler handler = new ISocketHandler() {
            public void process(Socket socket, HiMessageContext ctx) {
                try {
                    HiPOSALMListener.this.getServer().process(ctx);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                } finally {
                    HiLog.close(ctx.getCurrentMsg());
                }
            }
        };
        ISocketHandler ret_handler = buildSocketHandler(filters, handler);
        this.readerhandler = SocketHandlers.multiReaderPool(this.log, ret_handler);
        return this.readerhandler;
    }
}