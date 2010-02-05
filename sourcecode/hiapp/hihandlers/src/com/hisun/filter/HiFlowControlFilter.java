package com.hisun.filter;

import com.hisun.exception.HiException;
import com.hisun.framework.event.IServerInitListener;
import com.hisun.framework.event.ServerEvent;
import com.hisun.message.HiContext;
import com.hisun.message.HiMessageContext;
import com.hisun.pubinterface.IHandler;
import com.hisun.pubinterface.IHandlerFilter;
import com.hisun.util.HiDBSemaphore;
import com.hisun.util.HiThreadSemaphore;

import java.util.ArrayList;

public class HiFlowControlFilter implements IHandlerFilter, IServerInitListener {
    private int maxThreadNum;
    private int acquireThreadTimeOut;
    private int maxDBConnNum;
    private int acquireDBConnTimeOut;
    private ArrayList msgTypeList;
    private static final String DEFAULT_MSG_TYPE = "DEFAULT";

    public HiFlowControlFilter() {
        this.maxThreadNum = 0;

        this.acquireThreadTimeOut = -1;

        this.maxDBConnNum = 0;

        this.acquireDBConnTimeOut = -1;
        this.msgTypeList = new ArrayList();
    }

    public void process(HiMessageContext arg0, IHandler arg1) throws HiException {
        HiThreadSemaphore threadCtrl = (HiThreadSemaphore) arg0.getProperty("_SVR_THREAD_NUM_CTRL");

        String msgType = arg0.getCurrentMsg().getType();
        if ((!(this.msgTypeList.contains(msgType.toUpperCase()))) && (!(this.msgTypeList.contains("DEFAULT")))) {
            arg1.process(arg0);
            return;
        }

        if (threadCtrl != null) threadCtrl.acquire();
        try {
            arg1.process(arg0);
        } finally {
            if (threadCtrl != null) threadCtrl.release();
        }
    }

    public void serverInit(ServerEvent event) throws HiException {
        HiContext ctx = event.getServerContext();
        if (this.maxDBConnNum != 0) {
            HiDBSemaphore dBConnCtrl = new HiDBSemaphore(this.maxDBConnNum, this.acquireDBConnTimeOut);

            ctx.setProperty("_SVR_DB_CONN_NUM_CTRL", dBConnCtrl);
        }
        if (this.maxThreadNum != 0) {
            HiThreadSemaphore threadCtrl = new HiThreadSemaphore(this.maxThreadNum, this.acquireThreadTimeOut);

            ctx.setProperty("_SVR_THREAD_NUM_CTRL", threadCtrl);
        }
    }

    public int getAcquireDBConnTimeOut() {
        return this.acquireDBConnTimeOut;
    }

    public void setAcquireDBConnTimeOut(int acquireDBConnTimeOut) {
        this.acquireDBConnTimeOut = acquireDBConnTimeOut;
    }

    public int getAcquireThreadTimeOut() {
        return this.acquireThreadTimeOut;
    }

    public void setAcquireThreadTimeOut(int acquireThreadTimeOut) {
        this.acquireThreadTimeOut = acquireThreadTimeOut;
    }

    public int getMaxDBConnNum() {
        return this.maxDBConnNum;
    }

    public void setMaxDBConnNum(int maxDBConnNum) {
        this.maxDBConnNum = maxDBConnNum;
    }

    public int getMaxThreadNum() {
        return this.maxThreadNum;
    }

    public void setMaxThreadNum(int maxThreadNum) {
        this.maxThreadNum = maxThreadNum;
    }

    public void setMsgType(String msgType) {
        this.msgTypeList.add(msgType.toUpperCase());
    }
}