package com.hzjbbis.fk.sockserver;

import com.hzjbbis.fk.common.events.GlobalEventHandler;
import com.hzjbbis.fk.common.simpletimer.ITimerFunctor;
import com.hzjbbis.fk.common.simpletimer.TimerData;
import com.hzjbbis.fk.common.simpletimer.TimerScheduler;
import com.hzjbbis.fk.common.spi.IModule;
import com.hzjbbis.fk.common.spi.socket.IServerSideChannel;
import com.hzjbbis.fk.common.spi.socket.abstra.BaseSocketServer;
import com.hzjbbis.fk.sockserver.event.AcceptEvent;
import com.hzjbbis.fk.sockserver.event.ModuleProfileEvent;
import com.hzjbbis.fk.sockserver.event.ServerStartedEvent;
import com.hzjbbis.fk.sockserver.event.ServerStoppedEvent;
import com.hzjbbis.fk.sockserver.io.SocketIoThreadPool;
import com.hzjbbis.fk.utils.State;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.*;

public class TcpSocketServer extends BaseSocketServer implements IModule, ITimerFunctor {
    private static final Logger log = Logger.getLogger(TcpSocketServer.class);

    private boolean oneIpLimit = false;
    protected ServerSocketChannel ssc;
    protected Selector selector;
    private volatile State state = State.STOPPED;
    protected Map<String, AsyncSocketClient> map = Collections.synchronizedMap(new HashMap(51200));
    private AcceptThread acceptThread = null;
    private SocketIoThreadPool ioPool = null;

    public String getModuleType() {
        return "socketServer";
    }

    public boolean isActive() {
        return this.state.isActive();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("A-TCP(").append(this.port).append(")");
        return sb.toString();
    }

    public boolean start() {
        if (!(this.state.isStopped())) {
            log.warn("socket server[" + this.port + "]非停止状态，不能启动服务。");
            return false;
        }
        if (this.ioThreadSize <= 0) this.ioThreadSize = (Runtime.getRuntime().availableProcessors() * 2);
        this.state = State.STARTING;

        this.ioPool = new SocketIoThreadPool(this.port, this.ioThreadSize, this.ioHandler);
        this.ioPool.start();

        this.acceptThread = new AcceptThread();
        this.acceptThread.start();
        int cnt = 1000;
        while ((this.state.isStarting()) && (cnt-- > 0)) {
            Thread.yield();
            try {
                Thread.sleep(10L);
            } catch (InterruptedException localInterruptedException) {
            }
        }
        TimerScheduler.getScheduler().addTimer(new TimerData(this, 0, 60L));
        TimerScheduler.getScheduler().addTimer(new TimerData(this, 1, this.timeout));

        log.info("TCP服务启动成功【" + this.port + "】");
        GlobalEventHandler.postEvent(new ServerStartedEvent(this));
        return true;
    }

    public void onTimer(int timerID) {
        if (timerID == 0) {
            long now = System.currentTimeMillis();
            if ((now - this.lastReceiveTime < 60000L) || (now - this.lastSendTime < 60000L))
                GlobalEventHandler.postEvent(new ModuleProfileEvent(this));
            synchronized (this.statisticsRecv) {
                this.msgRecvPerMinute = 0;
            }
            synchronized (this.statisticsSend) {
                this.msgSendPerMinute = 0;
            }
        } else {
            if (1 != timerID) return;
            ArrayList list = new ArrayList(this.map.values());
            long now = System.currentTimeMillis();
            int closedCount = 0;
            for (AsyncSocketClient client : list) {
                if (now - client.getLastReadTime() > this.timeout * 1000) {
                    forceCloseClient(client);
                    ++closedCount;
                }
            }
            if (closedCount > 0) log.warn("TCP服务[" + this.name + "]超时关闭客户端连接数=" + closedCount);
        }
    }

    public void stop() {
        if (!(this.state.isRunning())) return;
        this.state = State.STOPPING;

        this.acceptThread.interrupt();
        int cnt = 500;
        while ((this.acceptThread.isAlive()) && (cnt-- > 0)) {
            Thread.yield();
            try {
                this.acceptThread.join(20L);
            } catch (InterruptedException localInterruptedException) {
            }
        }
        this.acceptThread = null;

        this.ioPool.stop();

        TimerScheduler.getScheduler().removeTimer(this, 0);
        TimerScheduler.getScheduler().removeTimer(this, 1);

        this.state = State.STOPPED;
        log.info("TCP服务停止【" + this.port + "】");
        GlobalEventHandler.postEvent(new ServerStoppedEvent(this));
    }

    public void removeClient(IServerSideChannel client) {
        String clientKey = client.getPeerAddr();
        if (this.oneIpLimit) clientKey = client.getPeerIp();
        this.map.remove(clientKey);
        super.removeClient(client);
    }

    public void forceCloseClient(AsyncSocketClient client) {
        removeClient(client);
        client.getIoThread().closeClientRequest(client);
    }

    private void checkTimeout() {
        ArrayList list = new ArrayList(this.map.values());
        long now = System.currentTimeMillis();
        for (AsyncSocketClient client : list)
            if (now - client.getLastReadTime() > this.timeout * 1000) forceCloseClient(client);
    }

    public int getClientSize() {
        return this.map.size();
    }

    public IServerSideChannel[] getClients() {
        return ((IServerSideChannel[]) this.map.values().toArray(new IServerSideChannel[0]));
    }

    public boolean isOneIpLimit() {
        return this.oneIpLimit;
    }

    public void setOneIpLimit(boolean oneIpLimit) {
        this.oneIpLimit = oneIpLimit;
    }

    private class AcceptThread extends Thread {
        public AcceptThread() {
            super("TcpServer-" + TcpSocketServer.access$0(TcpSocketServer.this) + "-AcceptThread");
        }

        public void run() {
            try {
                TcpSocketServer.this.ssc = ServerSocketChannel.open();
                TcpSocketServer.this.ssc.socket().setReuseAddress(true);
                InetSocketAddress addr = null;
                if (TcpSocketServer.access$1(TcpSocketServer.this) == null)
                    addr = new InetSocketAddress(TcpSocketServer.access$0(TcpSocketServer.this));
                else
                    addr = new InetSocketAddress(TcpSocketServer.access$1(TcpSocketServer.this), TcpSocketServer.access$0(TcpSocketServer.this));
                TcpSocketServer.this.ssc.socket().bind(addr);
                TcpSocketServer.this.ssc.configureBlocking(false);
            } catch (Exception exp) {
                TcpSocketServer.log.fatal("TCPServer start failed. " + exp.getLocalizedMessage() + ",port=" + TcpSocketServer.access$0(TcpSocketServer.this));
                return;
            }

            TcpSocketServer.log.info("server[" + TcpSocketServer.access$0(TcpSocketServer.this) + "]listen thread is running");
            try {
                TcpSocketServer.this.selector = Selector.open();
                TcpSocketServer.this.ssc.register(TcpSocketServer.this.selector, 16);
            } catch (Exception e) {
                TcpSocketServer.log.error("socketserver 侦听线程异常（selectorOpen)" + e.getMessage());
                TcpSocketServer.this.state = State.STOPPED;
                return;
            }
            TcpSocketServer.this.state = State.RUNNING;

            long sign = System.currentTimeMillis();
            int cnt = 0;
            int times = 0;

            while (TcpSocketServer.this.state != State.STOPPING) try {
                tryAccept();

                ++cnt;
                if (cnt >= 200) {
                    long now = System.currentTimeMillis();
                    if (now - sign < 1000L) {
                        TcpSocketServer.log.warn("server[" + TcpSocketServer.access$0(TcpSocketServer.this) + "]Accept thread可能进入死循环。");
                    }
                    cnt = 0;
                    sign = System.currentTimeMillis();
                }

                if (times++ >= 10) TcpSocketServer.this.checkTimeout();
            } catch (Exception e) {
                TcpSocketServer.log.error("server[" + TcpSocketServer.access$0(TcpSocketServer.this) + "]AcceptThread异常:" + e.getLocalizedMessage(), e);
            }
            try {
                TcpSocketServer.this.ssc.close();
            } catch (IOException ioe) {
                TcpSocketServer.log.warn("ssc.close异常：" + ioe.getLocalizedMessage());
            }
            TcpSocketServer.this.ssc = null;
            try {
                TcpSocketServer.this.selector.close();
            } catch (IOException ioe) {
                TcpSocketServer.log.warn("selector.close异常：" + ioe.getLocalizedMessage());
            }
            TcpSocketServer.this.selector = null;
            TcpSocketServer.log.info("server[" + TcpSocketServer.access$0(TcpSocketServer.this) + "]listen thread is stopping");
        }

        private void tryAccept() throws IOException, ClosedSelectorException {
            int n = TcpSocketServer.this.selector.select(50L);

            Set set = TcpSocketServer.this.selector.selectedKeys();
            for (SelectionKey key : set) {
                if (key.isAcceptable()) {
                    try {
                        doAccept();
                    } catch (Exception e) {
                        TcpSocketServer.log.warn("doAccept()异常：" + e.getLocalizedMessage(), e);
                        key.cancel();
                    }
                } else {
                    TcpSocketServer.log.warn("在Accept时候，SelectionKey非法：" + key);
                    key.cancel();
                }
            }
            set.clear();
        }

        private void doAccept() throws IOException {
            SocketChannel channel = TcpSocketServer.this.ssc.accept();
            channel.socket().setReceiveBufferSize(TcpSocketServer.access$6(TcpSocketServer.this));
            channel.socket().setSendBufferSize(TcpSocketServer.access$6(TcpSocketServer.this));
            channel.configureBlocking(false);
            AsyncSocketClient client = new AsyncSocketClient(channel, TcpSocketServer.this);
            String clientKey = client.getPeerAddr();
            if (TcpSocketServer.this.oneIpLimit) clientKey = client.getPeerIp();
            TcpSocketServer.this.map.put(clientKey, client);

            TcpSocketServer.this.ioPool.acceptNewClient(client);

            GlobalEventHandler.postEvent(new AcceptEvent(client));
        }
    }
}