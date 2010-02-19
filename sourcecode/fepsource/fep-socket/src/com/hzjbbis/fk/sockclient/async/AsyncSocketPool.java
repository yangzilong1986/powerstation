package com.hzjbbis.fk.sockclient.async;

import com.hzjbbis.fk.common.events.GlobalEventHandler;
import com.hzjbbis.fk.common.spi.socket.IClientIO;
import com.hzjbbis.fk.common.spi.socket.IServerSideChannel;
import com.hzjbbis.fk.common.spi.socket.ISocketServer;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.IMessageCreator;
import com.hzjbbis.fk.message.MultiProtoRecognizer;
import com.hzjbbis.fk.sockclient.async.event.ClientConnectedEvent;
import com.hzjbbis.fk.sockserver.io.SocketIoThreadPool;
import com.hzjbbis.fk.utils.State;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class AsyncSocketPool implements ISocketServer {
    private static final Logger log = Logger.getLogger(AsyncSocketPool.class);
    private static int sequence = 1;

    private String name = "异步socket连接池";
    private String peerIp;
    private int peerPort;
    private int clientSize = 1;
    private int ioThreadSize = 2;
    private int bufLength = 256;

    private IClientIO ioHandler = null;

    private String messageClass = "com.hzjbbis.fk.sockserver.message.SimpleMessage";
    private IMessageCreator messageCreator;
    private int timeout = 1800;

    private String txfs = "02";
    private Class<IMessage> msgClassObject;
    private long lastReceiveTime = 0L;
    private long lastSendTime = 0L;
    private long totalRecvMessages = 0L;
    private long totalSendMessages = 0L;
    private int msgRecvPerMinute = 0;
    private int msgSendPerMinute = 0;

    private volatile State state = State.STOPPED;
    protected List<JAsyncSocket> clients = Collections.synchronizedList(new ArrayList(1024));
    private SocketIoThreadPool ioPool = null;
    private AsyncSocketConnectThread connectThread;

    public boolean start() {
        try {
            if (this.messageCreator == null) {
                this.msgClassObject = Class.forName(this.messageClass);
                this.msgClassObject.newInstance();
                break label129:
            }

            if ((this.messageClass != null) && (this.messageClass.length() > 0)) {
                try {
                    this.msgClassObject = Class.forName(this.messageClass);
                    this.msgClassObject.newInstance();
                } catch (Exception e) {
                    log.error("AsyncSocketPool对象创建错误,检查配置文件：" + e.getLocalizedMessage(), e);
                }
            }
        } catch (Exception exp) {
            log.error("AsyncSocketPool对象创建错误,检查配置文件：" + exp.getLocalizedMessage(), exp);
            System.exit(-1);
        }
        if (!(this.state.isStopped())) {
            label129:
            log.warn("AsyncSocketPool 非停止状态，不能启动服务。");
            return false;
        }
        if (this.ioThreadSize <= 0) this.ioThreadSize = (Runtime.getRuntime().availableProcessors() * 2);
        this.state = State.STARTING;

        this.ioPool = new SocketIoThreadPool(this.peerPort, this.ioThreadSize, this.ioHandler);
        this.ioPool.start();

        this.connectThread = new AsyncSocketConnectThread();
        this.connectThread.start();
        int cnt = 1000;
        while ((!(this.state.isRunning())) && (cnt-- > 0)) {
            Thread.yield();
            try {
                Thread.sleep(10L);
            } catch (InterruptedException localInterruptedException) {
            }
        }
        for (int i = 0; i < this.clientSize; ++i) {
            this.clients.add(new JAsyncSocket(this));
        }
        log.info("AsyncSocketPool启动成功");

        return true;
    }

    public void stop() {
        if (!(this.state.isRunning())) return;
        this.state = State.STOPPING;

        this.connectThread.interrupt();
        int cnt = 500;
        while ((this.connectThread.isAlive()) && (cnt-- > 0)) {
            Thread.yield();
            try {
                this.connectThread.join(20L);
            } catch (InterruptedException localInterruptedException) {
            }
        }
        this.connectThread = null;

        this.ioPool.stop();
        this.ioPool = null;

        this.clients.clear();
        this.state = State.STOPPED;
        log.info("AsyncSocketPool 停止");
    }

    public IMessage createMessage(ByteBuffer buf) {
        if (this.messageCreator != null) {
            IMessage msg = this.messageCreator.create();
            if (msg == null) msg = MultiProtoRecognizer.recognize(buf);
            return msg;
        }
        try {
            return ((IMessage) this.msgClassObject.newInstance());
        } catch (Exception exp) {
        }
        return null;
    }

    public int getBufLength() {
        return this.bufLength;
    }

    public int getClientSize() {
        return this.clientSize;
    }

    public IServerSideChannel[] getClients() {
        return ((IServerSideChannel[]) this.clients.toArray(new IServerSideChannel[0]));
    }

    public long getLastReceiveTime() {
        return this.lastReceiveTime;
    }

    public long getLastSendTime() {
        return this.lastSendTime;
    }

    public int getMaxContinueRead() {
        return 10;
    }

    public int getMsgRecvPerMinute() {
        return this.msgRecvPerMinute;
    }

    public int getMsgSendPerMinute() {
        return this.msgSendPerMinute;
    }

    public int getPort() {
        return 0;
    }

    public long getTotalRecvMessages() {
        return this.totalRecvMessages;
    }

    public long getTotalSendMessages() {
        return this.totalSendMessages;
    }

    public String getTxfs() {
        return this.txfs;
    }

    public void incRecvMessage() {
        this.totalRecvMessages += 1L;
    }

    public void incSendMessage() {
        this.totalSendMessages += 1L;
    }

    public void setTxfs(String txfs) {
        this.txfs = txfs;
    }

    public IClientIO getIoHandler() {
        return this.ioHandler;
    }

    public int getWriteFirstCount() {
        return 0;
    }

    public void removeClient(IServerSideChannel client) {
        boolean found = false;
        for (JAsyncSocket sock : this.clients) {
            if (sock.isConnected()) {
                found = true;
                break;
            }
        }
        if (!(found)) {
            this.totalRecvMessages = 0L;
            this.totalSendMessages = 0L;
        }
    }

    public void setLastReceiveTime(long lastRecv) {
        this.lastReceiveTime = lastRecv;
    }

    public void setLastSendTime(long lastSend) {
        this.lastSendTime = lastSend;
    }

    public int getIoThreadSize() {
        return this.ioThreadSize;
    }

    public void setIoThreadSize(int ioThreadSize) {
        this.ioThreadSize = ioThreadSize;
    }

    public String getMessageClass() {
        return this.messageClass;
    }

    public void setMessageClass(String messageClass) {
        this.messageClass = messageClass;
    }

    public IMessageCreator getMessageCreator() {
        return this.messageCreator;
    }

    public void setMessageCreator(IMessageCreator messageCreator) {
        this.messageCreator = messageCreator;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setClientSize(int clientSize) {
        this.clientSize = clientSize;
    }

    public void setBufLength(int bufLength) {
        this.bufLength = bufLength;
    }

    public void setIoHandler(IClientIO ioHandler) {
        this.ioHandler = ioHandler;
    }

    public String getPeerIp() {
        return this.peerIp;
    }

    public void setPeerIp(String peerIp) {
        this.peerIp = peerIp;
    }

    public int getPeerPort() {
        return this.peerPort;
    }

    public void setPeerPort(int peerPort) {
        this.peerPort = peerPort;
    }

    public boolean isRunning() {
        return this.state.isRunning();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModuleType() {
        return "socketClient";
    }

    public boolean isActive() {
        return false;
    }

    public String profile() {
        return "";
    }

    public String getServerAddress() {
        return "";
    }

    class AsyncSocketConnectThread extends Thread {
        private Selector selector;
        private long lastCheckReConnect = System.currentTimeMillis();
        private long lastCheckTimeout = System.currentTimeMillis();
        private long now = 0L;

        public AsyncSocketConnectThread() {
            super("AsyncSockConnect-" + (AsyncSocketPool.sequence++));
        }

        public void run() {
            try {
                this.selector = Selector.open();
            } catch (Exception e) {
                AsyncSocketPool.log.error("AsyncSocketPool 连接线程异常（selector.open)" + e.getMessage());
                return;
            }
            long time1 = System.currentTimeMillis();
            for (JAsyncSocket client : AsyncSocketPool.this.clients) {
                toConnect(client);
            }
            long milli = System.currentTimeMillis() - time1;
            AsyncSocketPool.log.info(getName() + ",开始侦听异步连接事件...[socket创建时间=" + milli + "]");
            AsyncSocketPool.this.state = State.RUNNING;
            while (AsyncSocketPool.this.state != State.STOPPING) {
                this.now = System.currentTimeMillis();
                try {
                    tryConnect();
                } catch (Exception e) {
                    AsyncSocketPool.log.error("AsyncSocketPool ConnectThread异常:" + e.getLocalizedMessage(), e);
                }

                if (this.now - this.lastCheckTimeout > 60000L) {
                    checkTimeout();
                    this.lastCheckTimeout = this.now;
                }
            }
            try {
                this.selector.close();
            } catch (IOException ioe) {
                AsyncSocketPool.log.warn("selector.close异常：" + ioe.getLocalizedMessage());
            }
            this.selector = null;
            AsyncSocketPool.this.state = State.STOPPED;
        }

        private void tryConnect() throws IOException {
            SocketChannel channel;
            this.selector.select(100L);

            if (this.now - this.lastCheckReConnect > 1000L) {
                this.lastCheckReConnect = this.now;
                for (JAsyncSocket client : AsyncSocketPool.this.clients) {
                    channel = client.getChannel();
                    if (channel == null) {
                        toConnect(client);
                    } else {
                        if (channel.isConnectionPending()) continue;
                        if (channel.isConnected()) {
                            continue;
                        }
                        toConnect(client);
                    }
                }
            }
            Set set = this.selector.selectedKeys();
            if (set.size() > 0) AsyncSocketPool.log.debug("发现连接成功事件");
            for (SelectionKey key : set) {
                if (key.isConnectable()) {
                    doConnect(key);
                } else {
                    AsyncSocketPool.log.warn("在Connect时候，SelectionKey非法：" + key);
                    key.cancel();
                }
            }
            set.clear();
        }

        private void doConnect(SelectionKey key) {
            JAsyncSocket client = (JAsyncSocket) key.attachment();
            try {
                if (!(client.getChannel().finishConnect())) return;
                AsyncSocketPool.this.ioPool.addConnectedClient(client);
                GlobalEventHandler.postEvent(new ClientConnectedEvent(AsyncSocketPool.this, client));
                if (AsyncSocketPool.log.isDebugEnabled()) AsyncSocketPool.log.debug("异步连接成功:" + client);
            } catch (Exception e) {
                toConnect(client);
            }
        }

        private void toConnect(JAsyncSocket client) {
            try {
                long now = System.currentTimeMillis();
                if (now - client.getLastConnectTime() < 15000L) {
                    return;
                }
                client.createChannel();
                client.setLastConnectTime(now);
                SocketChannel channel = client.getChannel();
                channel.configureBlocking(false);
                channel.register(this.selector, 8, client);
                if (!(channel.connect(new InetSocketAddress(AsyncSocketPool.this.peerIp, AsyncSocketPool.this.peerPort))))
                    return;
                try {
                    if (!(client.getChannel().finishConnect())) return;
                    AsyncSocketPool.this.ioPool.addConnectedClient(client);
                    GlobalEventHandler.postEvent(new ClientConnectedEvent(AsyncSocketPool.this, client));
                    if (AsyncSocketPool.log.isDebugEnabled()) AsyncSocketPool.log.debug("异步连接成功:" + client);
                } catch (Exception e) {
                    AsyncSocketPool.log.error(e.getLocalizedMessage(), e);
                }
            } catch (Exception e) {
                AsyncSocketPool.log.error(e.getLocalizedMessage(), e);
            }
        }

        private void checkTimeout() {
        }
    }
}