package com.hzjbbis.db.batch;

import com.hzjbbis.db.batch.dao.IBatchDao;
import com.hzjbbis.db.batch.event.*;
import com.hzjbbis.db.batch.event.adapt.BatchDelayHandler;
import com.hzjbbis.fk.FasSystem;
import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.events.BasicEventHook;
import com.hzjbbis.fk.common.events.EventQueue;
import com.hzjbbis.fk.common.queue.CacheQueue;
import com.hzjbbis.fk.common.simpletimer.ITimerFunctor;
import com.hzjbbis.fk.common.simpletimer.TimerData;
import com.hzjbbis.fk.common.simpletimer.TimerScheduler;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.IEventHandler;
import com.hzjbbis.fk.common.spi.abstra.BaseModule;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.MessageType;
import com.hzjbbis.fk.message.gw.MessageGw;
import com.hzjbbis.fk.message.zj.MessageZj;
import com.hzjbbis.fk.tracelog.TraceLog;
import org.apache.log4j.Logger;

import java.util.*;

public class AsyncService extends BaseModule implements ITimerFunctor {
    protected static final Logger log = Logger.getLogger(AsyncService.class);
    private static final TraceLog tracer = TraceLog.getTracer();
    private static final int DEFAULT_QUEUE_SIZE = 10000;
    private int maxQueueSize = 10000;
    private int minThreadSize = 4;
    private int maxThreadSize = 20;
    private int delaySecond = 5;
    private String name = "batchService";
    private List<IBatchDao> daoList;
    private Map<EventType, BaseBpEventHandler> bpHandlerMap;
    private EventQueue queue = new EventQueue( ???.maxQueueSize);
    private BasicEventHook eventHook;
    private BatchDelayHandler batchDelayHandler = new BatchDelayHandler();
    private Map<Integer, IBatchDao> daoMap = new HashMap(127);
    private CacheQueue msgLogCacheQueue = null;

    public void init() {
        if (this.eventHook == null) {
            this.eventHook = new BasicEventHook();
            if (!(this.eventHook.isActive())) {
                this.eventHook.setMinSize(this.minThreadSize);
                this.eventHook.setMaxSize(this.maxThreadSize);
                this.eventHook.setName(this.name);
                this.eventHook.setQueue(this.queue);
            }
        }
        if (this.msgLogCacheQueue == null) {
            this.msgLogCacheQueue = new CacheQueue();
            this.msgLogCacheQueue.setKey("rawmsg");
            this.msgLogCacheQueue.setMaxFileSize(100);
            this.msgLogCacheQueue.setFileCount(20);
            this.msgLogCacheQueue.setMaxSize(100);
            this.msgLogCacheQueue.setMinSize(10);
        }
        for (EventType type : this.bpHandlerMap.keySet()) {
            this.eventHook.addHandler(type, (IEventHandler) this.bpHandlerMap.get(type));
        }
        this.eventHook.addHandler(this.batchDelayHandler.type(), this.batchDelayHandler);
        this.eventHook.start();
    }

    public boolean isActive() {
        return ((this.eventHook == null) || (!(this.eventHook.isActive())) || (!(FasSystem.getFasSystem().isDbAvailable())));
    }

    public String getName() {
        return this.name;
    }

    public boolean start() {
        init();
        for (IBatchDao dao : this.daoList) {
            dao.setDelaySecond(this.delaySecond);
        }
        TimerScheduler.getScheduler().addTimer(new TimerData(this, 0, this.delaySecond));
        return true;
    }

    public void stop() {
        TimerScheduler.getScheduler().removeTimer(this, 0);
        if (this.eventHook != null) this.eventHook.stop();
    }

    public String getModuleType() {
        return "dbService";
    }

    public boolean addMessage(IMessage msg) {
        IEvent event;
        if (this.queue.size() >= this.maxQueueSize) return false;
        if (msg.getMessageType() == MessageType.MSG_ZJ) {
            MessageZj zjmsg = (MessageZj) msg;

            if (zjmsg.head.c_func == 2) event = new BpReadTaskEvent(this, zjmsg);
            else if (zjmsg.head.c_func == 9) event = new BpExpAlarmEvent(this, zjmsg);
            else return false;
            try {
                this.queue.offer(event);
            } catch (Exception exp) {
                tracer.trace(exp.getLocalizedMessage(), exp);
                return false;
            }
            return true;
        }
        if (msg.getMessageType() == MessageType.MSG_GW_10) {
            MessageGw gwmsg = (MessageGw) msg;

            if ((gwmsg.afn() == 11) || (gwmsg.isTask())) event = new BpReadTaskEvent(this, msg);
            else if (gwmsg.afn() == 14) event = new BpExpAlarmEvent(this, msg);
            else return false;
            try {
                this.queue.offer(event);
            } catch (Exception exp) {
                tracer.trace(exp.getLocalizedMessage(), exp);
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean addRtu(Object rtu) {
        if (this.queue.size() >= this.maxQueueSize) return false;
        IEvent event = new FeUpdateRtuStatus(this, rtu);
        try {
            this.queue.offer(event);
        } catch (Exception exp) {
            tracer.trace(exp.getLocalizedMessage(), exp);
        }
        return true;
    }

    public boolean log2Db(IMessage msg) {
        if ((!(FasSystem.getFasSystem().isDbAvailable())) || (this.queue.size() >= this.maxQueueSize)) {
            this.msgLogCacheQueue.offer(msg);
            return true;
        }
        try {
            this.queue.offer(new BpLog2DbEvent(this, msg));
            if (this.queue.size() * 2 < this.queue.capacity()) {
                for (int i = 0; i < 10; ++i) {
                    msg = this.msgLogCacheQueue.poll();
                    if (msg != null) {
                        this.queue.offer(new BpLog2DbEvent(this, msg));
                    }
                }
            }
        } catch (Exception exp) {
            tracer.trace(exp.getLocalizedMessage(), exp);
        }
        return true;
    }

    public void addToDao(Object pojo, int key) {
        IBatchDao dao = (IBatchDao) this.daoMap.get(Integer.valueOf(key));
        if (dao == null) {
            log.error("数据保存到DAO错误，对象对应的KEY找不到DAO。key=" + key);
            return;
        }
        dao.add(pojo);
    }

    public int getMaxQueueSize() {
        return this.maxQueueSize;
    }

    public void setMaxQueueSize(int maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
        if (this.maxQueueSize > this.queue.capacity()) this.queue.setCapacity(maxQueueSize);
    }

    public void setDaoList(List<IBatchDao> list) {
        this.daoList = list;
        for (IBatchDao dao : this.daoList)
            this.daoMap.put(Integer.valueOf(dao.getKey()), dao);
    }

    public void setBpHandlerMap(Map<EventType, BaseBpEventHandler> handlers) {
        this.bpHandlerMap = handlers;
        for (BaseBpEventHandler handler : this.bpHandlerMap.values())
            handler.setService(this);
    }

    public void setEventHook(BasicEventHook eventHook) {
        this.eventHook = eventHook;
    }

    public void setMinThreadSize(int minThreadSize) {
        this.minThreadSize = minThreadSize;
    }

    public void setMaxThreadSize(int maxThreadSize) {
        this.maxThreadSize = maxThreadSize;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void onTimer(int id) {
        if (id != 0) return;
        for (IBatchDao dao : this.daoList) {
            if (!(dao.hasDelayData())) continue;
            try {
                this.queue.offer(new BpBatchDelayEvent(dao));
            } catch (Exception exp) {
                tracer.trace(exp.getLocalizedMessage(), exp);
            }
        }
    }

    public void setDelaySecond(int delaySecond) {
        if (delaySecond <= 1) delaySecond = 5;
        this.delaySecond = delaySecond;
    }

    public String toString() {
        return "AsyncService";
    }

    public Collection<IMessage> revokeEventQueue() {
        boolean takable = this.queue.enableTake();
        this.queue.enableTake(false);
        List events = new LinkedList();
        List msgs = new ArrayList();
        this.queue.drainTo(events, this.queue.size(), 0L);
        for (IEvent ev : events) {
            if (ev.getMessage() != null) {
                msgs.add(ev.getMessage());
            }
        }
        this.queue.enableTake(takable);
        if (this.msgLogCacheQueue != null) this.msgLogCacheQueue.asyncSaveQueue();
        return msgs;
    }
}