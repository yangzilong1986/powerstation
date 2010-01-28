package com.hisun.dispatcher;


import com.hisun.exception.HiException;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiContext;
import com.hisun.message.HiMessage;
import com.hisun.util.HiJMSHelper;
import com.hisun.util.HiStringManager;
import org.apache.commons.lang.StringUtils;

public class HiJMSProcess {
    private static boolean initialized = false;
    private static Logger log1 = HiLog.getLogger("SYS.trc");
    private static HiStringManager sm = HiStringManager.getManager();
    private static HiJMSHelper jmsHelper = new HiJMSHelper();

    private static void init() throws HiException {

        if (log1.isDebugEnabled()) {

            log1.debug("init() - start");
        }


        HiContext ctx = HiContext.getCurrentContext();

        String factoryName = ctx.getStrProp("@PARA", "_QUEUE_FACTORY");


        if (StringUtils.isEmpty(factoryName)) {

            throw new HiException("212008", "_QUEUE_FACTORY");
        }


        if (log1.isDebugEnabled()) {

            log1.debug(sm.getString("HiJmsHelper.initialize.QueueFactory", factoryName));
        }


        String queueName = ctx.getStrProp("@PARA", "_QUEUE");


        if (StringUtils.isEmpty(queueName)) {

            throw new HiException("212008", "_QUEUE");
        }


        if (log1.isDebugEnabled()) {

            log1.debug(sm.getString("HiJmsHelper.initialize.Queue", queueName));
        }

        jmsHelper.initialize(factoryName, queueName);

        initialized = true;


        if (log1.isDebugEnabled()) log1.debug("HiJMSProcess.init() - end");
    }

    public static void sendMessage(HiMessage message) throws HiException {

        Logger log2 = HiLog.getLogger(message);


        if (log2.isDebugEnabled()) {

            log2.debug("sendMessage(HiMessage) - start");
        }


        if (!(initialized)) {

            init();
        }


        jmsHelper.sendMessage(message);


        if (log2.isDebugEnabled()) log2.debug("sendMessage(HiMessage) - end");
    }

    public static void destroy() {

        jmsHelper.destory();
    }
}