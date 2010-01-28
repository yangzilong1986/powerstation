package com.hisun.mon;


import org.apache.commons.lang.time.DateUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;


public class HiRunStatInfoPool {
    private static int MAX_LAST_RUN_TIMES = 20;
    private static int MAX_LAST_RUN_DAYS = 7;
    private HiRunStatInfo totalRunStatInfo;
    private HiRunStatInfo curRunStatInfo;
    private ConcurrentLinkedQueue last7DRunStatInfos;
    private ConcurrentLinkedQueue last100RunTimeInfos;
    private static HiRunStatInfoPool instance;


    public HiRunStatInfoPool() {

        this.totalRunStatInfo = new HiRunStatInfo();


        this.curRunStatInfo = new HiRunStatInfo();


        this.last7DRunStatInfos = new ConcurrentLinkedQueue();


        this.last100RunTimeInfos = new ConcurrentLinkedQueue();

    }


    public static synchronized HiRunStatInfoPool getInstance() {

        if (instance == null) {

            instance = new HiRunStatInfoPool();

        }

        return instance;

    }


    public HiRunStatInfo totalRunStatInfo() {

        return this.totalRunStatInfo;

    }


    public HiRunStatInfo curRunStatInfo() {

        return this.curRunStatInfo;

    }


    public Iterator last7DRunStatInfos() {

        return this.last7DRunStatInfos.iterator();

    }


    public Iterator last100RunTimeInfos() {

        return this.last100RunTimeInfos.iterator();

    }


    public void once(String msgId, String id, int elapseTm, long sysTm, String msgTyp, String msgCod, String msg) {

        once(msgId, id, elapseTm, sysTm, msgTyp, msgCod, msg, null);

    }


    public void once(String msgId, String id, int elapseTm, long sysTm, String msgTyp, String msgCod, String msg, HashMap extMap) {

        if (msgTyp == null) {

            msgTyp = "E";

        }


        if ("000000".equals(msgCod)) {

            msgTyp = "N";

        }


        this.totalRunStatInfo.once(elapseTm, sysTm, msgTyp);

        if (!(DateUtils.isSameDay(new Date(sysTm), new Date(this.curRunStatInfo.lastSysTm)))) {

            if (this.last7DRunStatInfos.size() >= MAX_LAST_RUN_DAYS) {

                this.last7DRunStatInfos.poll();

            }

            this.last7DRunStatInfos.add(this.curRunStatInfo);

            this.curRunStatInfo = new HiRunStatInfo();

        }

        this.curRunStatInfo.once(elapseTm, sysTm, msgTyp);

        if (this.last100RunTimeInfos.size() >= MAX_LAST_RUN_TIMES) {

            this.last100RunTimeInfos.poll();

        }

        this.last100RunTimeInfos.add(new HiRunTimeInfo(msgId, id, elapseTm, sysTm, msgTyp, msgCod, msg, extMap));

    }

}