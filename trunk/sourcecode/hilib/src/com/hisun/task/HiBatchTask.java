package com.hisun.task;


import com.hisun.exception.HiException;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiETF;
import com.hisun.message.HiETFFactory;
import com.hisun.workload.HiWorkLoad;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.RejectedExecutionException;


public class HiBatchTask extends HiAbstractTask {
    private static final int WAIT_NONE = 0;
    private static final int WAIT_ALL = 1;
    private static final int WAIT_ANY = 2;
    private Logger log = HiLog.getLogger("BATCH_TASK.trc");
    private HashMap taskMap = new HashMap();
    private HiWorkLoad workLoad = null;


    public HiBatchTask(String batchId) {
        setId(batchId);

        setTaskInit();

    }


    public HiBatchTask() {

        setTaskInit();

    }


    public void sumbit(HiTask task) {

        task.setStartTm(System.currentTimeMillis());

        task.setTaskWait();

        while (true) try {

            if ((this.workLoad.getThreadPool().isShutdown()) || (Thread.currentThread().isInterrupted())) {

                return;

            }


            this.workLoad.getThreadPool().execute(task);

        } catch (RejectedExecutionException e) {

            while (true) {

                this.log.warn("Batch Task:[ " + this.id + "] Please increase maxThreads!");


                Thread.yield();

                try {

                    Thread.sleep(100L);

                } catch (InterruptedException e1) {

                    return;

                }

            }

        }

    }


    public HiTask addTask(String taskId) throws HiException {

        if (this.taskMap.containsKey(taskId)) {

            throw new HiException("241147", taskId);

        }

        HiTask task = new HiTask(taskId);

        this.taskMap.put(taskId, task);

        return task;

    }


    public HiTask getTask(String taskId) {

        return ((HiTask) this.taskMap.get(taskId));

    }


    public HiTask addTask(String taskId, boolean sync) throws HiException {

        if (this.taskMap.containsKey(taskId)) {

            throw new HiException("241147", taskId);

        }

        HiTask task = new HiTask(taskId, sync);

        this.taskMap.put(taskId, task);

        return task;

    }


    public HiWorkLoad getWorkLoad() {

        return this.workLoad;

    }


    public void setWorkLoad(HiWorkLoad workLoad) {

        this.workLoad = workLoad;

    }


    public boolean check() {

        if (isTimeOut()) {

            setTaskTmOut();

            return true;

        }


        Iterator iter = this.taskMap.keySet().iterator();

        boolean isFin = false;

        while (iter.hasNext()) {

            String name = (String) iter.next();

            HiTask task = (HiTask) this.taskMap.get(name);

            if (task.isTimeOut()) {

                if (this.log.isInfoEnabled()) {

                    this.log.info("TASK:{" + this.id + "}{" + task.getTmOut() + "} time out");

                }

                setTaskTmOut();

            }

            if (!(task.isEnd())) isFin = false;

            else {

                isFin = true;

            }

        }

        if (isFin) {

            setTaskFin();

        }

        return true;

    }


    public HiETF collectResultWaitAny() {

        return collectResult(2);

    }


    public HiETF collectResultWaitAny(String grpNam) {

        return collectResult(2, grpNam);

    }


    public HiETF collectResultWaitAll() {

        return collectResult(1);

    }


    public HiETF collectResultWaitAll(String grpNam) {

        return collectResult(1, grpNam);

    }


    public HiETF collectResult() {

        return collectResult(0);

    }


    public HiETF collectResult(String grpNam) {

        return collectResult(0, grpNam);

    }


    public HiETF collectResult(int waitType) {

        waitTask(waitType);

        Iterator iter = this.taskMap.keySet().iterator();

        HiETF root = HiETFFactory.createETF();

        while (iter.hasNext()) {

            String name = (String) iter.next();

            HiTask task = (HiTask) this.taskMap.get(name);

            HiETF grpNode = root.addNode(task.getId());

            grpNode.setChildValue("ID", task.getId());

            grpNode.setChildValue("STS", task.getTaskSts());

            grpNode.setChildValue("MSG", task.getMsg());

            if (!(task.isSucc())) {

                continue;

            }

            root.combine(task.getRspData(), true);

        }

        return root;

    }


    public HiETF collectResult(int waitType, String grpNam) {

        waitTask(waitType);

        int k = 1;

        Iterator iter = this.taskMap.keySet().iterator();

        HiETF root = HiETFFactory.createETF();

        while (iter.hasNext()) {

            String name = (String) iter.next();

            HiTask task = (HiTask) this.taskMap.get(name);

            HiETF grpNode = root.addNode(grpNam + "_" + k);

            grpNode.setChildValue("TASK_ID", task.getId());

            grpNode.setChildValue("TASK_STS", task.getTaskSts());

            grpNode.setChildValue("TASK_MSG", task.getMsg());

            ++k;

            if (!(task.isSucc())) {

                continue;

            }

            grpNode.combine(task.getRspData(), true);

        }

        return root;

    }


    public void waitTask(int waitType) {

        if (waitType == 0) {

            return;

        }

        while (true) {

            if (((waitType == 1) && (isAllEnd())) || ((waitType == 2) && (isAnyEnd()))) {

                return;

            }

            try {

                Thread.sleep(1000L);

            } catch (InterruptedException e) {

                e.printStackTrace();

            }

        }

    }


    public boolean isAnyEnd() {

        Iterator iter = this.taskMap.keySet().iterator();

        while (iter.hasNext()) {

            HiTask task = (HiTask) this.taskMap.get(iter.next());

            if (task.isEnd()) {

                return true;

            }

        }

        return false;

    }


    public boolean isAllEnd() {

        Iterator iter = this.taskMap.keySet().iterator();

        while (iter.hasNext()) {

            HiTask task = (HiTask) this.taskMap.get(iter.next());

            if (!(task.isEnd())) {

                return false;

            }

        }

        setTaskFin();

        return true;

    }


    public boolean contains(String taskId) {

        return this.taskMap.containsKey(taskId);

    }


    public void destroy() {

        Iterator iter = this.taskMap.keySet().iterator();

        while (iter.hasNext()) {

            ((HiTask) this.taskMap.get(iter.next())).destroy();

        }

        this.taskMap.clear();

    }


    public static int getWaitType(String waitTyp) {

        if ("ALL".equals(waitTyp)) return 1;

        if ("ANY".equals(waitTyp)) return 2;

        if ("NONE".equals(waitTyp)) {

            return 0;

        }

        return 0;

    }

}