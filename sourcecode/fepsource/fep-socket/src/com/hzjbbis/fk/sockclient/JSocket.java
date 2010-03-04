package com.hzjbbis.fk.sockclient;

import com.hzjbbis.fk.common.spi.socket.abstra.BaseClientChannel;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.IMessageCreator;
import com.hzjbbis.fk.message.gate.MessageGateCreator;
import com.hzjbbis.fk.tracelog.TraceLog;
import com.hzjbbis.fk.utils.HexDump;
import com.hzjbbis.fk.utils.State;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;

public class JSocket extends BaseClientChannel {
    private static final Logger log = Logger.getLogger(JSocket.class);
    private static final TraceLog trace = TraceLog.getTracer(JSocket.class);

    private String hostIp = "127.0.0.1";
    private int hostPort = 10001;
    private int bufLength = 256;
    private IMessageCreator messageCreator = new MessageGateCreator();
    private int timeout = 10;
    private JSocketListener listener = new DumyJSocketListener();
    private String txfs;
    private volatile Socket socket;
    private final Object sendLock = new Object();
    private ByteBuffer sendBuffer;
    private ByteBuffer readBuffer;
    private volatile State _state = State.STOPPED;
    private ReadThread reader;
    private long lastReadTime = System.currentTimeMillis();
    private int connectWaitTime = 2;
    private long lastConnectTime = System.currentTimeMillis() - 10000L;
    private String peerAddr;

    public void init() {
        if (State.STOPPED != this._state) return;
        this._state = State.STARTING;
        this.readBuffer = ByteBuffer.allocate(this.bufLength);
        this.sendBuffer = ByteBuffer.allocate(this.bufLength);
        this.peerAddr = this.hostIp + ":" + this.hostPort;
        this.reader = new ReadThread();
        this.reader.start();
    }

    public void close() {
        this._state = State.STOPPING;
        this.reader.interrupt();
        int cnt = 100;
        while ((State.STOPPED != this._state) && (cnt-- > 0)) {
            Thread.yield();
            try {
                Thread.sleep(10L);
            } catch (Exception localException) {
            }
        }
    }

    public void reConnect() {
        try {
            if (trace.isEnabled()) trace.trace("do reConnect. " + getPeerAddr());
            _closeSocket();
        } catch (Exception e) {
            log.warn("reConnect exception:" + e.getLocalizedMessage(), e);
        }
    }

    public boolean isActive() {
        return this._state.isRunning();
    }

    public boolean isConnected() {
        return ((!(isActive())) || (this.socket == null) || (!(this.socket.isConnected())));
    }

    private boolean _connect() {
        try {
            trace.trace("client socket is connecting to server :" + getPeerAddr());
            this.lastConnectTime = System.currentTimeMillis();
            this.socket = new Socket();
            InetSocketAddress ar = new InetSocketAddress(this.hostIp, this.hostPort);
            this.socket.setSoTimeout(this.timeout * 1000);
            this.socket.connect(ar, this.timeout * 1000);
            this.connectWaitTime = 2;
        } catch (ConnectException e) {
            log.error("不能连接到:" + this.hostIp + "@" + this.hostPort + ",reason=" + e.getLocalizedMessage(), e);
            this.socket = null;
            return false;
        } catch (Exception e) {
            log.error("不能连接到:" + this.hostIp + "@" + this.hostPort, e);
            if (this.socket != null) try {
                this.socket.close();
            } catch (Exception localException1) {
            }
            this.socket = null;
            return false;
        }
        log.info("client socket connect to server successfully:" + getPeerAddr());
        return true;
    }

    private void _closeSocket() {
        synchronized (this.sendLock) {
            long interval = System.currentTimeMillis() - this.lastConnectTime;
            if (interval < this.connectWaitTime * 1000) {
                trace.trace("can not close socket within " + this.connectWaitTime + " sec");
                return;
            }
            try {
                try {
                    this.socket.shutdownInput();
                    this.socket.shutdownOutput();
                } catch (Exception localException) {
                }
                this.socket.close();
            } catch (Exception localException1) {
            } finally {
                this.socket = null;
                this.listener.onClose(this);
                this.readBuffer.clear();
                this.sendBuffer.clear();
                if ((State.STOPPING == this._state) || (State.STOPPED == this._state)) {
                    this.reader = null;
                    return;
                }
                this._state = State.STARTING;
            }
        }
    }

    // ERROR //
    public boolean send(byte[] output) { // Byte code:
        //   0: aload_1
        //   1: ifnull +10 -> 11
        //   4: aload_0
        //   5: invokevirtual 280	com/hzjbbis/fk/sockclient/JSocket:isConnected	()Z
        //   8: ifne +5 -> 13
        //   11: iconst_0
        //   12: ireturn
        //   13: aload_0
        //   14: getfield 83	com/hzjbbis/fk/sockclient/JSocket:sendLock	Ljava/lang/Object;
        //   17: dup
        //   18: astore_2
        //   19: monitorenter
        //   20: aload_0
        //   21: getfield 212	com/hzjbbis/fk/sockclient/JSocket:socket	Ljava/net/Socket;
        //   24: invokevirtual 281	java/net/Socket:getOutputStream	()Ljava/io/OutputStream;
        //   27: aload_1
        //   28: iconst_0
        //   29: aload_1
        //   30: arraylength
        //   31: invokevirtual 285	java/io/OutputStream:write	([BII)V
        //   34: aload_0
        //   35: getfield 212	com/hzjbbis/fk/sockclient/JSocket:socket	Ljava/net/Socket;
        //   38: invokevirtual 281	java/net/Socket:getOutputStream	()Ljava/io/OutputStream;
        //   41: invokevirtual 291	java/io/OutputStream:flush	()V
        //   44: goto +65 -> 109
        //   47: astore_3
        //   48: new 122	java/lang/StringBuilder
        //   51: dup
        //   52: ldc_w 294
        //   55: invokespecial 130	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
        //   58: aload_3
        //   59: invokevirtual 196	java/lang/Exception:getLocalizedMessage	()Ljava/lang/String;
        //   62: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   65: ldc_w 296
        //   68: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   71: aload_0
        //   72: invokevirtual 186	com/hzjbbis/fk/sockclient/JSocket:getPeerAddr	()Ljava/lang/String;
        //   75: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   78: invokevirtual 142	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   81: astore 4
        //   83: getstatic 45	com/hzjbbis/fk/sockclient/JSocket:log	Lorg/apache/log4j/Logger;
        //   86: aload 4
        //   88: aload_3
        //   89: invokevirtual 244	org/apache/log4j/Logger:error	(Ljava/lang/Object;Ljava/lang/Throwable;)V
        //   92: getstatic 53	com/hzjbbis/fk/sockclient/JSocket:trace	Lcom/hzjbbis/fk/tracelog/TraceLog;
        //   95: aload 4
        //   97: aload_3
        //   98: invokevirtual 298	com/hzjbbis/fk/tracelog/TraceLog:trace	(Ljava/lang/String;Ljava/lang/Exception;)V
        //   101: aload_0
        //   102: invokespecial 191	com/hzjbbis/fk/sockclient/JSocket:_closeSocket	()V
        //   105: aload_2
        //   106: monitorexit
        //   107: iconst_0
        //   108: ireturn
        //   109: aload_2
        //   110: monitorexit
        //   111: goto +6 -> 117
        //   114: aload_2
        //   115: monitorexit
        //   116: athrow
        //   117: iconst_1
        //   118: ireturn
        //
        // Exception table:
        //   from	to	target	type
        //   20	44	47	java/lang/Exception
        //   20	107	114	finally
        //   109	111	114	finally
        //   114	116	114	finally }
        public boolean sendMessage (IMessage msg){
            boolean ret = false;
            synchronized (this.sendLock) {
                ret = _send(msg);
            }
            if (ret) {
                msg.setSource(this);
                msg.setIoTime(System.currentTimeMillis());
                msg.setTxfs(this.txfs);
                this.listener.onSend(this, msg);
            }
            return ret;
        }

    private boolean _send(IMessage msg) {
        if (this.socket == null) return false;
        boolean done = false;
        this.sendBuffer.clear();
        int cnt = 100;
        while ((!(done)) && (--cnt > 0)) {
            done = msg.write(this.sendBuffer);
            this.sendBuffer.flip();
            byte[] out = this.sendBuffer.array();
            int len = this.sendBuffer.remaining();
            int off = this.sendBuffer.position();
            try {
                this.socket.getOutputStream().write(out, off, len);
                this.socket.getOutputStream().flush();
            } catch (Exception e) {
                String info = "client closed in '_send' reason is" + e.getLocalizedMessage() + ", peer=" + getPeerAddr();
                log.error(info, e);
                trace.trace(info, e);
                _closeSocket();
                return false;
            }
        }
        return true;
    }

    public IMessageCreator getMessageCreator() {
        return this.messageCreator;
    }

    public void setMessageCreator(IMessageCreator messageCreator) {
        this.messageCreator = messageCreator;
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

    public JSocketListener getListener() {
        return this.listener;
    }

    public void setListener(JSocketListener listener) {
        this.listener = listener;
    }

    public long getLastReadTime() {
        return this.lastReadTime;
    }

    public String getTxfs() {
        return this.txfs;
    }

    public void setTxfs(String txfs) {
        this.txfs = txfs;
    }

    public String getPeerAddr() {
        if (this.peerAddr != null) {
            return this.peerAddr;
        }
        return this.hostIp + ":" + this.hostPort;
    }

    public String getPeerIp() {
        return this.hostIp;
    }

    public int getPeerPort() {
        return this.hostPort;
    }

    public SocketAddress getSocketAddress() {
        return new InetSocketAddress(this.hostIp, this.hostPort);
    }

    public boolean send(IMessage msg) {
        return sendMessage(msg);
    }

    public long getLastIoTime() {
        return this.lastReadTime;
    }

    class ReadThread extends Thread {
        IMessage message = null;

        public ReadThread() {
            super("sock-" + JSocket.this.hostIp + "@" + JSocket.this.hostPort);
        }

        public void run() {
            break label278:

            if (System.currentTimeMillis() - JSocket.this.lastConnectTime < JSocket.this.connectWaitTime * 1000) ;
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                JSocket.trace.trace("thread sleep interrupted");
                break label216:

                if (JSocket.this._connect() != 0) {
                    if (JSocket.trace.isEnabled())
                        JSocket.trace.trace("connect to server ok. " + JSocket.this.getPeerAddr());
                    JSocket.this._state = State.RUNNING;

                    JSocket.this.listener.onConnected(JSocket.this);
                } else {
                    JSocket.this.connectWaitTime *= 2;
                    if (JSocket.this.connectWaitTime > 300) JSocket.this.connectWaitTime = 300;
                    JSocket.trace.trace("client socket cannot connected to server :" + JSocket.this.hostIp + "@" + JSocket.this.hostPort);
                }
            }
            do {
                if (JSocket.this.socket != null) ;
                label216:
                int ret = _doReceive();
                if (ret >= 0) continue;
                if (JSocket.trace.isEnabled())
                    JSocket.trace.trace("client closed by _doReceive return=" + ret + ",peer=" + JSocket.this.getPeerAddr());
                label278:
                JSocket.this._closeSocket();
            } while ((State.STOPPING != JSocket.this._state) && (State.STOPPED != JSocket.this._state));
            try {
                if (JSocket.this.socket != null) JSocket.this._closeSocket();
            } catch (Exception localException) {
            }
            JSocket.this._state = State.STOPPED;
            JSocket.trace.trace("client socket stoped :" + JSocket.this.hostIp + "@" + JSocket.this.hostPort);
        }

        private int _doReceive() {
            try {
                int len = JSocket.this.readBuffer.remaining();
                if (len == 0) {
                    JSocket.this.readBuffer.position(0);
                    JSocket.log.warn("缓冲区满，不能接收数据。dump=" + HexDump.hexDumpCompact(JSocket.this.readBuffer));
                    JSocket.this.readBuffer.clear();
                    len = JSocket.this.readBuffer.remaining();
                }
                byte[] in = JSocket.this.readBuffer.array();
                int off = JSocket.this.readBuffer.position();
                int n = JSocket.this.socket.getInputStream().read(in, off, len);
                if (n <= 0) return -1;
                JSocket.this.lastReadTime = System.currentTimeMillis();
                JSocket.this.readBuffer.position(off + n);
                JSocket.this.readBuffer.flip();
            } catch (SocketTimeoutException te) {
                return 0;
            } catch (IOException ioe) {
                JSocket.log.warn("client IO exception," + ioe.getLocalizedMessage() + ",peer=" + JSocket.this.getPeerAddr());
                return -2;
            } catch (Exception e) {
                JSocket.log.warn("client socket[" + JSocket.this.getPeerAddr() + "] _doReceive:" + e.getLocalizedMessage(), e);
                return -3;
            }
            try {
                _handleBuffer();
            } catch (Exception e) {
                JSocket.log.error(e.getLocalizedMessage(), e);
                return 0;
            }
            return 0;
        }

        private void _handleBuffer() {
            while (JSocket.this.readBuffer.hasRemaining()) {
                if (this.message == null) {
                    this.message = JSocket.this.messageCreator.create();
                }
                boolean down = false;
                try {
                    down = this.message.read(JSocket.this.readBuffer);
                } catch (Exception e) {
                    JSocket.log.warn("消息对象读取数据异常:" + e.getLocalizedMessage(), e);
                    this.message = null;
                }
                if (!(down)) break;
                try {
                    this.message.setSource(JSocket.this);
                    this.message.setIoTime(System.currentTimeMillis());
                    this.message.setTxfs(JSocket.this.txfs);

                    if ((this.message.getPeerAddr() == null) || (this.message.getPeerAddr().length() == 0)) {
                        this.message.setPeerAddr(JSocket.this.peerAddr);
                    }
                    JSocket.this.listener.onReceive(JSocket.this, this.message);
                } catch (Exception e) {
                    JSocket.log.error(e.getLocalizedMessage(), e);
                }
                this.message = null;
            }

            if (JSocket.this.readBuffer.hasRemaining()) label225:JSocket.this.readBuffer.compact();
            else JSocket.this.readBuffer.clear();
        }
    }
}