package com.hzjbbis.fk.sockserver.io;

import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.events.EventQueue;
import com.hzjbbis.fk.common.events.GlobalEventHandler;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.socket.IClientIO;
import com.hzjbbis.fk.common.spi.socket.IServerSideChannel;
import com.hzjbbis.fk.exception.SocketClientCloseException;
import com.hzjbbis.fk.sockclient.async.event.ClientConnectedEvent;
import com.hzjbbis.fk.sockserver.event.AcceptEvent;
import com.hzjbbis.fk.sockserver.event.ClientCloseEvent;
import com.hzjbbis.fk.sockserver.event.ClientWriteReqEvent;
import com.hzjbbis.fk.tracelog.TraceLog;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SocketIoThread extends Thread {
    private static final Logger log = Logger.getLogger(SocketIoThread.class);
    private static final TraceLog tracer = TraceLog.getTracer();
    private volatile State state = State.STOPPED;
    private IClientIO ioHandler;
    private Selector selector;
    private List<IServerSideChannel> list = Collections.synchronizedList(new LinkedList());
    private EventQueue queue = new EventQueue();

    public SocketIoThread(int port, IClientIO ioHandler, int index) {
        super("io-" + port + "-" + index);
        this.ioHandler = ioHandler;
        super.start();
    }

    public void stopThread() {
        this.state = State.STOPPING;
        interrupt();
    }

    public boolean isRunning() {
        return (this.state != State.RUNNING);
    }

    public void acceptClient(IServerSideChannel client) {
        client.setIoThread(this);
        this.list.add(client);

        IEvent event = new AcceptEvent(client);
        int cnt = 20;
        while (cnt-- > 0) try {
            this.queue.offer(event);
        } catch (Exception exp) {
            String info = "SocketIoThread can not offer event. reason is " + exp.getLocalizedMessage() + ". event is" + event.toString();
            log.fatal(info, exp);
            tracer.trace(info, exp);
            try {
                Thread.sleep(50L);
            } catch (Exception localException1) {
            }
        }
        this.selector.wakeup();
    }

    public void addConnectedClient(IServerSideChannel client) {
        client.setIoThread(this);
        this.list.add(client);

        IEvent event = new ClientConnectedEvent(client.getServer(), client);
        int cnt = 20;
        while (cnt-- > 0) try {
            this.queue.offer(event);
        } catch (Exception exp) {
            String info = "SocketIoThread can not offer event. reason is " + exp.getLocalizedMessage() + ". event is" + event.toString();
            log.fatal(info, exp);
            tracer.trace(info, exp);
            try {
                Thread.sleep(50L);
            } catch (Exception localException1) {
            }
        }
        this.selector.wakeup();
    }

    public void closeClientRequest(IServerSideChannel client) {
        this.list.remove(client);

        IEvent event = new ClientCloseEvent(client);
        int cnt = 20;
        while (cnt-- > 0) try {
            this.queue.offer(event);
        } catch (Exception exp) {
            String info = "SocketIoThread can not offer event. reason is " + exp.getLocalizedMessage() + ". event is" + event.toString();
            log.fatal(info, exp);
            tracer.trace(info, exp);
            try {
                Thread.sleep(50L);
            } catch (Exception localException1) {
            }
        }
        this.selector.wakeup();
    }

    public void clientWriteRequest(IServerSideChannel client) {
        try {
            this.queue.offer(new ClientWriteReqEvent(client));
            this.selector.wakeup();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    public int getClientSize() {
        return this.list.size();
    }

    public void run() {
        try {
            this.selector = Selector.open();
        } catch (IOException e) {
            log.error("Selector.open()", e);
            return;
        }
        this.state = State.RUNNING;
        while (this.state != State.STOPPING) try {
            int n = this.selector.select(100L);
            if (this.state == State.STOPPING) {
                break label613;
            }
            if (!(handleEvent())) {
                set = this.selector.selectedKeys();
                if (set != null) {
                    if ((n != 0) || (set.size() > 0)) {
                        Iterator it = set.iterator();
                        while (it.hasNext()) {
                            SelectionKey key = (SelectionKey) it.next();
                            it.remove();
                            IServerSideChannel client = (IServerSideChannel) key.attachment();
                            if (client == null) {
                                key.cancel();
                                key.attach(null);
                                label412:
                                label566:
                                log.warn("null==key.attachment()");
                            } else if (!(key.isValid())) {
                                key.cancel();
                                key.attach(null);
                                log.warn("!key.isValid()");
                            } else {
                                if (key.isReadable()) {
                                    client.setLastReadTime();
                                }

                                try {
                                    boolean readDone = this.ioHandler.onReceive(client);
                                    client.getServer().setLastReceiveTime(System.currentTimeMillis());

                                    if (readDone) {
                                        this.ioHandler.onReceive(client);

                                        if (client.sendQueueSize() > 1) {
                                            interest = key.interestOps();
                                            interest &= -2;
                                            interest |= 4;
                                            key.interestOps(interest);
                                            break label412:
                                        }
                                    }

                                    int interest = key.interestOps();
                                    interest &= -2;
                                    interest |= 4;
                                    key.interestOps(interest);
                                } catch (SocketClientCloseException cce) {
                                    client.getServer().removeClient(client);
                                    key.cancel();
                                    client.close();
                                    this.list.remove(client);
                                    GlobalEventHandler.postEvent(new ClientCloseEvent(client));
                                    break label566:
                                } catch (Exception exp) {
                                    log.warn("server.getIoHandler().onReceive(client):" + exp.getLocalizedMessage(), exp);

                                    if (key.isWritable()) try {
                                        doWrite(client);
                                    } catch (SocketClientCloseException exp) {
                                        client.getServer().removeClient(client);
                                        key.cancel();
                                        client.close();
                                        this.list.remove(client);
                                        GlobalEventHandler.postEvent(new ClientCloseEvent(client));
                                        if (log.isDebugEnabled()) {
                                            log.debug("server[" + client.getServer().getPort() + "]doWrite exp. client closed.", exp);
                                        }
                                    } catch (Exception exp) {
                                        log.warn("server.getIoHandler().onSend(client):" + exp.getLocalizedMessage(), exp);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("select(500) exception:" + e, e);
        }
        try {
            label613:
            this.selector.close();
        } catch (IOException e) {
            log.error("Selector.close()", e);
        } finally {
            this.selector = null;
            this.state = State.STOPPED;
        }
    }

    private void doWrite(IServerSideChannel client) throws SocketClientCloseException {
        client.setLastIoTime();
        boolean finishWrite = false;
        finishWrite = this.ioHandler.onSend(client);
        SelectionKey key = client.getChannel().keyFor(this.selector);
        int interest = key.interestOps();

        if ((finishWrite) && (client.sendQueueSize() == 0)) {
            client.setLastingWrite(0);
            interest &= -5;
            interest |= 1;
            client.getServer().setLastSendTime(System.currentTimeMillis());

            this.ioHandler.onReceive(client);
        } else {
            client.setLastingWrite(client.getLastingWrite() + 1);
        }
        if (client.getLastingWrite() > client.getServer().getWriteFirstCount()) {
            interest |= 1;
            client.setLastingWrite(0);
        }
        key.interestOps(interest);
    }

    private boolean handleEvent() {
        IEvent ee = null;
        boolean processed = false;
        while ((ee = this.queue.poll()) != null) {
            IServerSideChannel client;
            processed = true;
            if (ee.getType() == EventType.ACCEPTCLIENT) {
                try {
                    AcceptEvent e = (AcceptEvent) ee;
                    client = e.getClient();
                    client.getChannel().configureBlocking(false);
                    client.getChannel().register(this.selector, 1, client);
                } catch (Exception exp) {
                    log.error("accept client后处理异常:" + exp.getLocalizedMessage(), exp);
                }
            } else {
                SelectionKey key;
                if (ee.getType() == EventType.CLIENTCLOSE) {
                    try {
                        ClientCloseEvent e = (ClientCloseEvent) ee;
                        client = e.getClient();
                        if (client.getChannel() != null) {
                            key = client.getChannel().keyFor(this.selector);
                            if (key != null) key.cancel();
                            client.close();

                            GlobalEventHandler.postEvent(ee);
                        }
                    } catch (Exception e) {
                        log.error("外部调用关闭client处理异常:" + exp.getLocalizedMessage(), exp);
                    }
                } else if (ee.getType() == EventType.CLIENT_WRITE_REQ) {
                    try {
                        ClientWriteReqEvent e = (ClientWriteReqEvent) ee;
                        client = e.getClient();

                        if (client.getChannel() != null) {
                            key = client.getChannel().keyFor(this.selector);
                            int interest = key.interestOps();
                            interest |= 4;
                            key.interestOps(interest);
                        }
                    } catch (Exception e) {
                        log.error("client写请求处理异常:" + exp.getLocalizedMessage(), exp);
                    }
                } else if (ee.getType() == EventType.CLIENT_CONNECTED) {
                    ClientConnectedEvent e = (ClientConnectedEvent) ee;
                    client = (IServerSideChannel) e.getClient();
                    try {
                        client.getChannel().configureBlocking(false);
                        client.getChannel().register(this.selector, 1, client);
                    } catch (Exception exp) {
                        log.error("connected client后处理异常:" + exp.getLocalizedMessage(), exp);
                    }
                }
            }
        }
        return processed;
    }
}