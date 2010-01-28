package com.hisun.task;


import com.hisun.dispatcher.HiRouterOut;
import com.hisun.exception.HiException;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiETF;
import com.hisun.message.HiETFFactory;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;


public class HiTask extends HiAbstractTask implements Runnable {
    private Logger log = HiLog.getLogger("BATCH_TASK.trc");
    private HiMessage reqMsg;
    private HiMessage rspMsg;
    private String outParam;
    private String inParam;
    private String srn;
    private String txnCod;
    private String objSvr;
    private boolean sync = true;

    private long total = 0L;


    public HiTask(String taskId) {
        setId(taskId);

        setTaskInit();

    }


    public HiTask(String taskId, boolean sync) {

        setId(taskId);

        this.sync = sync;

        setTaskInit();

    }


    public HiTask() {

        setTaskInit();

    }


    public HiMessage getReqMsg() {

        return this.reqMsg;

    }


    public void setReqMsg(HiMessage reqMsg) {

        this.reqMsg = createReqMsg();

        this.reqMsg.setBody(buildBody(reqMsg.getETFBody(), this.inParam));

    }


    public HiETF getRspData() {

        return this.rspMsg.getETFBody();

    }


    public HiMessage getRspMsg() {

        return this.rspMsg;

    }


    public void setRspMsg(HiMessage rspMsg) throws HiException {

        this.rspMsg = rspMsg.cloneNoBody();

        HiETF rootETF = HiETFFactory.createETF();

        this.rspMsg.setBody(buildBody(rspMsg.getETFBody(), this.outParam));

    }


    public void run() {

        try {

            long l1 = System.currentTimeMillis();

            setTaskRun();

            HiMessageContext ctx = new HiMessageContext();

            HiMessageContext.setCurrentMessageContext(ctx);

            ctx.setCurrentMsg(this.reqMsg);

            HiRouterOut.process(ctx);

            this.total += System.currentTimeMillis() - l1;

            if (!(isSync())) {

                return;

            }

            setEndTm(System.currentTimeMillis());

            if (ctx.getCurrentMsg().getBody() instanceof HiETF) {

                this.rspMsg = ctx.getCurrentMsg();

                this.rspMsg.setBody(buildBody(ctx.getCurrentMsg().getETFBody(), this.outParam));

            }

            setTaskSucc();

            this.reqMsg = null;

        } catch (Throwable e) {

            this.msg = e.toString();

            this.log.error("task:[" + this.id + "] failure", e);

            setTaskFail();

        }

    }


    public boolean isSync() {

        return this.sync;

    }


    public void setSync(boolean sync) {

        this.sync = sync;

    }


    public String getOutParam() {

        return this.outParam;

    }


    public void setOutParam(String outParam) {

        this.outParam = outParam;

    }


    public HiMessage createReqMsg() {

        HiMessage msg = new HiMessage(this.id, "PLTOUT");

        msg.setHeadItem("STM", new Long(System.currentTimeMillis()));

        if (msg.hasHeadItem("plain_type")) {

            msg.setHeadItem("plain_type", "byte");

        }


        msg.setHeadItem("STC", this.txnCod);

        msg.setHeadItem("SCH", "rq");

        msg.setHeadItem("ECT", "text/etf");

        if (StringUtils.isNotBlank(this.objSvr)) {

            msg.setHeadItem("SDT", this.objSvr);

        }


        if (StringUtils.isNotBlank(this.srn)) {

            msg.setHeadItem("SRN", this.srn);

        }

        return msg;

    }


    public static HiETF buildBody(HiETF inRoot, String param) {

        HiETF outRoot = null;

        if (param == null) {

            outRoot = inRoot.cloneRootNode();

            return outRoot;

        }

        outRoot = HiETFFactory.createETF();

        if (StringUtils.isBlank(param)) {

            return outRoot;

        }


        String[] tmps = param.split("\\|");

        for (int i = 0; i < tmps.length; ++i) {

            if (StringUtils.isBlank(tmps[i])) {

                continue;

            }

            int idx = StringUtils.indexOf(tmps[i], "[]");

            if (idx == -1) {

                HiETF node1 = inRoot.getChildNode(tmps[i]);

                if (node1 == null) {

                    continue;

                }

                outRoot.appendNode(node1.cloneNode());

            } else {

                String groupName = StringUtils.substring(tmps[i], 0, idx - 1);

                String tmp = inRoot.getChildValue(groupName + "_NUM");

                int groupNum = -1;

                if (tmp != null) {

                    groupNum = NumberUtils.toInt(tmp);

                    outRoot.setChildValue(groupName + "_NUM", tmp);

                }

                for (int j = 1; ; ++j) {

                    HiETF node1 = inRoot.getChildNode(groupName + "_" + j);

                    if (((groupNum != -1) && (j > groupNum)) || (node1 == null)) break;
                    if (node1.isEndNode()) {

                        break;

                    }

                    HiETF node2 = outRoot.getChildNode(groupName + "_" + j);

                    outRoot.appendNode(node1.cloneNode());

                }

            }

        }

        return outRoot;

    }


    public String getInParam() {

        return this.inParam;

    }


    public void setInParam(String inParam) {

        this.inParam = inParam;

    }


    public String getSrn() {

        return this.srn;

    }


    public void setSrn(String srn) {

        this.srn = srn;

    }


    public String getTxnCod() {

        return this.txnCod;

    }


    public void setTxnCod(String txnCod) {

        this.txnCod = txnCod;

    }


    public String getObjSvr() {

        return this.objSvr;

    }


    public void setObjSvr(String objSvr) {

        this.objSvr = objSvr;

    }


    public void destroy() {

    }

}