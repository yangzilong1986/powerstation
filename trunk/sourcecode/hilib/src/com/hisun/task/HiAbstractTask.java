package com.hisun.task;


import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.util.HiStringManager;


public abstract class HiAbstractTask {
    protected static HiStringManager sm = HiStringManager.getManager();
    public static final String TASK_INIT = "I";
    public static final String TASK_WAIT = "W";
    public static final String TASK_RUN = "R";
    public static final String TASK_FAIL = "E";
    public static final String TASK_SUCC = "S";
    public static final String TASK_TMOUT = "T";
    public static final String TASK_FIN = "F";
    private Logger log;
    private long startTm;
    private long endTm;
    private int tmOut;
    private String taskSts;
    protected String id;
    protected String msg;


    public HiAbstractTask() {

        this.log = HiLog.getLogger("BATCH_TASK.trc");


        this.tmOut = 120;


        this.id = "DEFAULT_TASK";

    }


    public String getId() {

        return this.id;

    }


    public void setId(String id) {

        this.id = id;

    }


    public boolean isEnd() {

        return (("S".equals(this.taskSts)) || ("E".equals(this.taskSts)) || (isTimeOut()));

    }


    public boolean isSucc() {

        return "S".equals(this.taskSts);

    }


    public boolean isInit() {

        return "I".equals(this.taskSts);

    }


    public String getTaskSts() {

        return this.taskSts;

    }


    public long getStartTm() {

        return this.startTm;

    }


    public void setStartTm(long startTm) {

        this.startTm = startTm;

    }


    public long getTmOut() {

        return this.tmOut;

    }


    public void setTmOut(int tmOut) {

        this.tmOut = tmOut;

    }


    public boolean isTimeOut() {

        if (isInit()) {

            return false;

        }

        boolean flag = System.currentTimeMillis() > this.startTm + this.tmOut * 1000;

        return flag;

    }


    public void setTaskRun() {

        if ("R".equals(this.taskSts)) {

            return;

        }

        if (this.log.isInfoEnabled()) {

            this.log.info(sm.getString("HiAbstractTask.task.run", this.id));

        }

        this.taskSts = "R";

    }


    public void setTaskInit() {

        if ("I".equals(this.taskSts)) {

            return;

        }


        setStartTm(System.currentTimeMillis());

        this.taskSts = "I";

    }


    public void setTaskFail() {

        if ("E".equals(this.taskSts)) {

            return;

        }


        setEndTm(System.currentTimeMillis());

        this.taskSts = "E";

    }


    public void setTaskSucc() {

        if ("S".equals(this.taskSts)) {

            return;

        }


        setEndTm(System.currentTimeMillis());

        this.taskSts = "S";

    }


    public void setTaskWait() {

        if ("W".equals(this.taskSts)) {

            return;

        }


        this.taskSts = "W";

    }


    public void setTaskTmOut() {

        if ("T".equals(this.taskSts)) {

            return;

        }

        setEndTm(System.currentTimeMillis());

        this.taskSts = "T";

    }


    public void setTaskFin() {

        if ("F".equals(this.taskSts)) {

            return;

        }


        setEndTm(System.currentTimeMillis());

        this.taskSts = "F";

    }


    public long getEndTm() {

        return this.endTm;

    }


    public void setEndTm(long endTm) {

        this.endTm = endTm;

    }


    public String getMsg() {

        return this.msg;

    }


    public void setMsg(String msg) {

        this.msg = msg;

    }

}