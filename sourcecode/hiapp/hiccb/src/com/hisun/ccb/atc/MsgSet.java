package com.hisun.ccb.atc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MsgSet {
    private List<MsgBase> msgList;
    private final Lock lock;

    public MsgSet() {
        this.msgList = new ArrayList();

        this.lock = new ReentrantLock(true);
    }

    public void addMsg(MsgBase msg) {
        this.lock.lock();
        try {
            this.msgList.add(msg);
        } finally {
            this.lock.unlock();
        }
    }

    public Collection<MsgBase> getAllMsg() {
        List list = new ArrayList();
        this.lock.lock();
        try {
            list.addAll(this.msgList);
        } finally {
            this.lock.unlock();
        }
        return list;
    }

    public String getAllMsgByStr() {
        return "";
    }

    public String getMsgError() {
        StringBuilder sb = new StringBuilder();
        this.lock.lock();
        try {
            for (MsgBase msg : this.msgList) {
                sb.append(msg.getMsg());
            }
        } finally {
            this.lock.unlock();
        }
        return sb.toString();
    }
}