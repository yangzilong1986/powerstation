package com.hisun.dispatcher;

import com.hisun.exception.HiException;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiContext;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import com.hisun.register.HiBind;
import com.hisun.register.HiRegisterService;
import com.hisun.register.HiServiceObject;
import com.hisun.util.HiStringManager;
import org.apache.commons.lang.StringUtils;

public class HiRouterOut {
    static HiStringManager sm = HiStringManager.getManager();

    public void init() {
    }

    public void destroy() {
    }

    public static HiMessage syncProcess(HiMessage message) throws HiException {
        HiMessage msg = null;
        try {
            msg = innerSyncProcess(message);
            HiMessage localHiMessage1 = msg;

            return localHiMessage1;
        } finally {
            if (msg != null) {
                HiLog.close(msg);
            }
            HiLog.close(message);
        }
    }

    public static HiMessage innerSyncProcess(HiMessage message) throws HiException {
        Logger log = HiLog.getLogger(message);

        if (log.isDebugEnabled()) {
            log.debug("HiRouterOut.syncProcess() - start");
        }

        if (log.isDebugEnabled()) {
            log.debug(sm.getString("HiRouterOut.syncProcess", message));
        }
        timeoutCheck(message);
        deadloopCheck(message);
        HiMessage msg1 = doSyncProcess(message);
        timeoutCheck(msg1);
        if (log.isDebugEnabled()) {
            log.debug("HiRouterOut.syncProcess() - end");
        }
        return msg1;
    }

    public static HiMessage asyncProcess(HiMessage message) throws HiException {
        HiMessage msg = null;
        try {
            msg = innerAsyncProcess(message);
            HiMessage localHiMessage1 = msg;

            return localHiMessage1;
        } finally {
            if (msg != null) {
                HiLog.close(msg);
            }
            HiLog.close(message);
        }
    }

    public static HiMessage innerAsyncProcess(HiMessage message) throws HiException {
        Logger log = HiLog.getLogger(message);

        if (log.isDebugEnabled()) {
            log.debug("HiRouterOut.asyncProcess() - start");
        }

        if (log.isDebugEnabled()) {
            log.debug(sm.getString("HiRouterOut.asyncProcess", message));
        }
        timeoutCheck(message);
        deadloopCheck(message);
        HiMessage msg1 = doAsyncProcess(message);

        if (log.isDebugEnabled()) {
            log.debug("HiRouterOut.asyncProcess() - end");
        }
        return msg1;
    }

    public static void process(HiMessageContext ctx) throws HiException {
        try {
            innerProcess(ctx);
        } finally {
            if (ctx.getCurrentMsg() != null) HiLog.close(ctx.getCurrentMsg());
        }
    }

    public static void innerProcess(HiMessageContext ctx) throws HiException {
        HiMessage message = ctx.getCurrentMsg();
        Logger log = HiLog.getLogger(ctx.getCurrentMsg());

        if (log.isDebugEnabled()) {
            log.debug("HiRouterOut.process() - start");
        }

        if (log.isDebugEnabled()) log.debug(sm.getString("HiRouterOut.process", message));
        HiMessage msg1 = null;
        if (isSyncMessage(message)) msg1 = innerSyncProcess(message);
        else {
            msg1 = innerAsyncProcess(message);
        }
        if (StringUtils.equals(msg1.getStatus(), "E")) {
            throw new HiException(msg1.getHeadItem("SSC"));
        }
        ctx.setCurrentMsg(msg1);
        if (log.isDebugEnabled()) log.debug("HiRouterOut.process() - end");
    }

    private static boolean isSyncMessage(HiMessage message) {
        return (!(StringUtils.equalsIgnoreCase(message.getHeadItem("SYN"), "N")));
    }

    private static void timeoutCheck(HiMessage message) throws HiException {
        Logger log = HiLog.getLogger(message);
        if (log.isDebugEnabled()) {
            log.debug("HiRouterOut.timeoutCheck() - start");
        }

        Object o = message.getObjectHeadItem("ETM");
        if (!(o instanceof Long)) {
            if (log.isDebugEnabled()) {
                log.debug("HiRouterOut.timeoutCheck() - end");
            }
            return;
        }
        long etm = ((Long) o).longValue();
        if ((etm > 0L) && (System.currentTimeMillis() > etm)) {
            throw new HiException("212001");
        }

        if (log.isDebugEnabled()) log.debug("HiRouterOut.timeoutCheck() - end");
    }

    private static void deadloopCheck(HiMessage message) throws HiException {
        if (message.getHeadItemValSize("STC") > 5000) throw new HiException("212002");
    }

    private static String getService(HiMessage message) throws HiException {
        Logger log = HiLog.getLogger(message);
        if (log.isDebugEnabled()) {
            log.debug("HiRouterOut.getService() - start");
        }

        String service = null;

        service = message.getHeadItem("SRN");
        String rqType = message.getHeadItem("SCH");
        if (StringUtils.equals(rqType, "rq")) {
            HiContext ctx = HiContext.getCurrentContext();
            String regionName = null;
            if (ctx != null) {
                regionName = ctx.getStrProp("@PARA", "_REGION_NAME");
            }

            service = message.getHeadItem("SRN");
            if ((StringUtils.isNotBlank(service)) && (!(StringUtils.equals(service, regionName)))) {
                service = "S.CONSVR";
                return service;
            }

            service = message.getHeadItem("SDT");
            if (StringUtils.isNotBlank(service)) {
                return service;
            }

            service = message.getHeadItem("STC");
            if (StringUtils.isNotBlank(service)) return service;
        } else {
            service = message.getHeadItem("SRT");
            if (StringUtils.isNotBlank(service)) {
                return service;
            }
        }
        throw new HiException("212003");
    }

    private static HiMessage doSyncProcess(HiMessage message) throws HiException {
        Logger log = HiLog.getLogger(message);
        if (log.isDebugEnabled()) {
            log.debug("HiRouterOut.syncProcess() - start");
        }

        String name = getService(message);
        HiServiceObject serviceObject = HiRegisterService.getService(name);
        if (!(serviceObject.isRunning())) {
            throw new HiException("212103", name);
        }

        if (log.isDebugEnabled()) log.debug(sm.getString("HiRouterOut.syncProcess", serviceObject));
        HiBind bind = serviceObject.getBind();
        HiMessage msg1 = null;
        HiMessageContext ctx = HiMessageContext.getCurrentMessageContext();

        String rqType = message.getHeadItem("SCH");
        if (StringUtils.equals(rqType, "rq")) {
            HiContext parent = ctx.getServerContext();
            if ((parent != null) && (parent.containsProperty("SVR.name"))) {
                message.addHeadItem("SRT", parent.getStrProp("SVR.name"));
            }

        }

        try {
            msg1 = bind.process(message);
        } finally {
            if (msg1 != null) {
                rqType = msg1.getHeadItem("SCH");
                if (StringUtils.equals(rqType, "rp")) {
                    msg1.delHeadItemVal("SRT");
                }
            }
            HiMessageContext.setCurrentMessageContext(ctx);
        }

        if (log.isDebugEnabled()) {
            log.debug("HiRouterOut.syncProcess() - end");
        }
        return msg1;
    }

    private static HiMessage doAsyncProcess(HiMessage message) throws HiException {
        Logger log = HiLog.getLogger(message);
        if (log.isDebugEnabled()) {
            log.debug("HiRouterOut.asyncProcess() - start");
        }

        if (log.isDebugEnabled()) log.debug(sm.getString("HiRouterOut.asyncProcess"));
        message.delHeadItem("SYN");
        HiJMSProcess.sendMessage(message);

        if (log.isDebugEnabled()) {
            log.debug("HiRouterOut.asyncProcess() - end");
        }
        return message;
    }
}