package com.hzjbbis.fk.sockserver.io;

import com.hzjbbis.fk.common.spi.socket.IClientIO;
import com.hzjbbis.fk.common.spi.socket.IServerSideChannel;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class SocketIoThreadPool {
    private static final Logger log = Logger.getLogger(SocketIoThreadPool.class);
    private int ioThreadSize;
    private int port;
    private IClientIO ioHandler;
    private ArrayList<SocketIoThread> threads = new ArrayList();

    public SocketIoThreadPool(int port, int ioSize, IClientIO ioHandler) {
        this.port = port;
        this.ioThreadSize = ioSize;
        this.ioHandler = ioHandler;
    }

    public void start() {
        for (int i = 0; i < this.ioThreadSize; ++i) {
            this.threads.add(new SocketIoThread(this.port, this.ioHandler, i));
        }
        Thread.yield();
    }

    public void stop() {
        for (int i = 0; i < this.ioThreadSize; ++i) {
            ((SocketIoThread) this.threads.get(i)).stopThread();
        }
        this.threads.clear();
    }

    public void acceptNewClient(IServerSideChannel client) {
        scheduleClient(client, true);
    }

    public void addConnectedClient(IServerSideChannel client) {
        scheduleClient(client, false);
    }

    private void scheduleClient(IServerSideChannel client, boolean acceptMode) {
        SocketIoThread cur = null;
        SocketIoThread q = null;
        int min = 2147483647;
        for (int i = 0; i < this.ioThreadSize; ++i) {
            q = (SocketIoThread) this.threads.get(i);
            if (q.getClientSize() < min) {
                cur = q;
                min = cur.getClientSize();
            }
        }
        if (cur == null) {
            log.error("scheduleClient failed. cur thread == null.");
            return;
        }
        if (acceptMode) cur.acceptClient(client);
        else cur.addConnectedClient(client);
    }
}