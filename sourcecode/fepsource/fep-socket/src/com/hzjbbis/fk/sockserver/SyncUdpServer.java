package com.hzjbbis.fk.sockserver;

import com.hzjbbis.fk.common.events.GlobalEventHandler;
import com.hzjbbis.fk.common.simpletimer.ITimerFunctor;
import com.hzjbbis.fk.common.simpletimer.TimerData;
import com.hzjbbis.fk.common.simpletimer.TimerScheduler;
import com.hzjbbis.fk.common.spi.IModule;
import com.hzjbbis.fk.common.spi.socket.IServerSideChannel;
import com.hzjbbis.fk.common.spi.socket.abstra.BaseSocketServer;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.sockserver.event.ModuleProfileEvent;
import com.hzjbbis.fk.utils.HexDump;
import com.hzjbbis.fk.utils.State;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.HashMap;

public class SyncUdpServer extends BaseSocketServer implements IModule, ITimerFunctor {
    private static final Logger log = Logger.getLogger(SyncUdpServer.class);

    private boolean oneIpLimit = false;

    private volatile State state = State.STOPPED;
    private DatagramChannel dgc;
    private Object channelLock = new Object();
    private boolean channelReady = false;
    private UdpIoThread ioThread;
    private ByteBuffer readBuffer = null;
    private ByteBuffer writeBuffer = null;

    private final HashMap<String, UdpClient> clients = new HashMap(5120);

    public String getModuleType() {
        return "socketServer";
    }

    public boolean isActive() {
        return this.state.isActive();
    }

    public boolean send(IMessage msg, UdpClient client) {
        Object Ljava/lang / Object;
        ;
        monitorenter;
        try {
            SocketAddress sa = client.getSocketAddress();
            this.writeBuffer.clear();
            msg.write(this.writeBuffer);
            this.writeBuffer.flip();
            this.dgc.send(this.writeBuffer, sa);
            client.setLastIoTime();
            return true;
        } catch (Exception e) {
            log.error("UDP[" + this.port + "]发送报文异常:" + e.getLocalizedMessage(), e);
        } finally {
            monitorexit;
        }

        return false;
    }

    public boolean start() {
        if (this.state.isActive()) return false;
        this.state = State.STARTING;
        this.readBuffer = ByteBuffer.allocateDirect(this.bufLength);
        this.writeBuffer = ByteBuffer.allocateDirect(this.bufLength);

        TimerScheduler.getScheduler().addTimer(new TimerData(this, 0, 60L));
        TimerScheduler.getScheduler().addTimer(new TimerData(this, 1, this.timeout));
        try {
            this.ioThread = new UdpIoThread();
            this.ioThread.start();
            while (this.state != State.RUNNING) {
                Thread.yield();
                Thread.sleep(10L);
            }
        } catch (Exception exp) {
            log.fatal(exp.getLocalizedMessage());
            return false;
        }
        if (log.isInfoEnabled()) log.info("UDP服务【" + this.port + "】已经启动!");
        return true;
    }

    public void onTimer(int timerID) {
        if (timerID == 0) {
            long now = System.currentTimeMillis();
            if ((now - this.lastReceiveTime < 60000L) || (now - this.lastSendTime < 60000L))
                GlobalEventHandler.postEvent(new ModuleProfileEvent(this));
            this.msgRecvPerMinute = 0;
            this.msgSendPerMinute = 0;
        } else if (1 == timerID) {
            ArrayList list = new ArrayList(this.clients.values());
            long now = System.currentTimeMillis();
            for (UdpClient client : list)
                if (now - client.getLastReadTime() > this.timeout * 1000) synchronized (this.clients) {
                    String clientKey = client.getPeerIp() + ":" + client.getPeerPort();
                    if (this.oneIpLimit) clientKey = client.getPeerIp();
                    this.clients.remove(clientKey);
                }
        }
    }

    private boolean openChannel() {
        synchronized (this.channelLock) {
            try {
                this.dgc = DatagramChannel.open();
                this.dgc.socket().setReceiveBufferSize(this.bufLength);
                this.dgc.socket().setSendBufferSize(this.bufLength);
                this.dgc.socket().setReuseAddress(true);
                this.dgc.configureBlocking(true);
                InetSocketAddress addr = null;
                if (this.ip == null) addr = new InetSocketAddress(this.port);
                else addr = new InetSocketAddress(this.ip, this.port);
                this.dgc.socket().bind(addr);
            } catch (Exception exp) {
                log.fatal("UDP服务器启动失败[" + this.port + "]", exp);
                this.channelReady = false;
            }
            this.channelReady = true;
            return this.channelReady;
        }
    }

    private void closeChannel() {
        synchronized (this.channelLock) {
            try {
                if (this.dgc == null) break label22;
                label22:
                this.dgc.close();
            } catch (IOException e) {
                log.warn(e.getMessage());
            } finally {
                this.dgc = null;
            }
            this.channelReady = false;
        }
    }

    public void stop() {
        this.state = State.STOPPING;
        if (this.ioThread != null) {
            this.ioThread.interrupt();
            try {
                if (this.ioThread.isAlive()) this.ioThread.join(200L);
            } catch (InterruptedException localInterruptedException) {
            }
            this.ioThread = null;
        }

        this.readBuffer = null;
        this.writeBuffer = null;

        TimerScheduler.getScheduler().removeTimer(this, 0);
        TimerScheduler.getScheduler().removeTimer(this, 1);

        if (log.isInfoEnabled()) log.info("UDP服务器[" + this.port + "] 停止运行！");
        this.state = State.STOPPED;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("S-UDP[").append(this.port).append("]");
        return sb.toString();
    }

    public boolean isOneIpLimit() {
        return this.oneIpLimit;
    }

    public void setOneIpLimit(boolean oneIpLimit) {
        this.oneIpLimit = oneIpLimit;
    }

    public int getClientSize() {
        synchronized (this.clients) {
            return this.clients.size();
        }
    }

    public IServerSideChannel[] getClients() {
        synchronized (this.clients) {
            return ((IServerSideChannel[]) this.clients.values().toArray(new IServerSideChannel[this.clients.size()]));
        }
    }

    public void removeClient(IServerSideChannel client) {
        synchronized (this.clients) {
            String clientKey = client.getPeerIp() + ":" + client.getPeerPort();
            if (this.oneIpLimit) clientKey = client.getPeerIp();
            this.clients.remove(clientKey);
        }
        super.removeClient(client);
    }

    private class UdpIoThread extends Thread {
        public UdpIoThread() {
            super("UDP[" + SyncUdpServer.access$0(SyncUdpServer.this) + "]-IO-Thread");
        }

        public void run() {
            if (SyncUdpServer.log.isDebugEnabled()) SyncUdpServer.log.debug(getName() + "运行...");
            SyncUdpServer.this.state = State.RUNNING;
            while (SyncUdpServer.this.state != State.STOPPING) {
                if ((!(SyncUdpServer.this.channelReady)) && (SyncUdpServer.this.openChannel() == 0)) {
                    SyncUdpServer.this.closeChannel();
                    try {
                        Thread.sleep(60000L);
                    } catch (Exception localException1) {
                    }
                } else {
                    try {
                        drainChannel();
                    } catch (ClosedByInterruptException exp) {
                        SyncUdpServer.log.error("UDP服务器[" + SyncUdpServer.access$0(SyncUdpServer.this) + "]接收数据异常:" + exp.getLocalizedMessage(), exp);
                        Thread.interrupted();
                        SyncUdpServer.this.closeChannel();
                    } catch (AsynchronousCloseException exp) {
                        SyncUdpServer.log.error("UDP服务器[" + SyncUdpServer.access$0(SyncUdpServer.this) + "]接收数据异常:" + exp.getLocalizedMessage(), exp);
                        Thread.interrupted();
                        SyncUdpServer.this.closeChannel();
                    } catch (ClosedChannelException exp) {
                        SyncUdpServer.log.error("UDP服务器[" + SyncUdpServer.access$0(SyncUdpServer.this) + "]接收数据异常:" + exp.getLocalizedMessage(), exp);
                        Thread.interrupted();
                        SyncUdpServer.this.closeChannel();
                    } catch (Exception exp) {
                        SyncUdpServer.log.error("UDP服务器[" + SyncUdpServer.access$0(SyncUdpServer.this) + "]接收数据异常:" + exp.getLocalizedMessage(), exp);
                    }
                }
            }
            SyncUdpServer.this.closeChannel();
            SyncUdpServer.this.state = State.STOPPED;
        }

        void drainChannel() throws Exception {
            SyncUdpServer.this.readBuffer.clear();
            SocketAddress sa = SyncUdpServer.this.dgc.receive(SyncUdpServer.this.readBuffer);
            if (sa == null) {
                SyncUdpServer.log.error("UDP服务器接收数据异常，dgc.receive(readBuffer)返回NULL");
                return;
            }
            if (SyncUdpServer.this.readBuffer.position() == 0) {
                return;
            }

            SyncUdpServer.this.readBuffer.flip();

            String peerip = ((InetSocketAddress) sa).getAddress().getHostAddress();
            int port = ((InetSocketAddress) sa).getPort();
            String key = peerip;
            if (!(SyncUdpServer.this.oneIpLimit)) key = key + ":" + port;
            UdpClient client = (UdpClient) SyncUdpServer.this.clients.get(key);
            if (client == null) {
                client = new UdpClient(sa, SyncUdpServer.this);
                synchronized (SyncUdpServer.this.clients) {
                    SyncUdpServer.this.clients.put(key, client);
                }
            }
            if (SyncUdpServer.log.isDebugEnabled()) {
                SyncUdpServer.log.debug("UDP-" + port + "-收到[" + sa.toString() + "]上行报文:" + HexDump.hexDump(SyncUdpServer.this.readBuffer));
            }

            if (client.getBufRead().remaining() < SyncUdpServer.this.readBuffer.remaining())
                client.getBufRead().clear();
            client.getBufRead().put(SyncUdpServer.this.readBuffer);
            client.getBufRead().flip();
            client.setLastReadTime();
            client.getServer().setLastReceiveTime(System.currentTimeMillis());
            SyncUdpServer.access$11(SyncUdpServer.this).onReceive(client);
        }
    }
}