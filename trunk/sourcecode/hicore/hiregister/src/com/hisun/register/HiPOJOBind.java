package com.hisun.register;

import com.hisun.exception.HiException;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiMessage;
import com.hisun.util.HiServiceLocator;
import org.apache.commons.beanutils.MethodUtils;

import java.io.Serializable;

public class HiPOJOBind implements HiBind, Serializable {
    private static final long serialVersionUID = 7884551804140140767L;
    String jndi;

    HiPOJOBind(String id) {
        this.jndi = "ibs/ejb/" + id;
    }

    public HiMessage process(HiMessage message) throws HiException {
        Logger logger = HiLog.getLogger(message);
        HiMessage msg2 = null;
        if (logger.isDebugEnabled()) {
            logger.debug("HiEJBBind:process(HiMessage) - start");
        }
        HiServiceLocator locator = HiServiceLocator.getInstance();
        try {
            Object o = locator.lookup(this.jndi);
            o = MethodUtils.invokeExactMethod(o, "process", message);
            msg2 = (HiMessage) o;
            if (logger.isDebugEnabled()) {
                logger.debug("HiEJBBind:process(HiMessage) - end = " + msg2);
            }
        } catch (Exception e) {
            throw HiException.makeException(e);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("HiEJBBind:process(HiMessage) - end");
        }
        return msg2;
    }

    public String toString() {
        return this.jndi;
    }
}