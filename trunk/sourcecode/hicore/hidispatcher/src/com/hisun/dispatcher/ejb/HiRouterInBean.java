package com.hisun.dispatcher.ejb;

import com.hisun.exception.HiException;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiMessage;
import com.hisun.startup.HiStartup;
import com.hisun.util.HiStringManager;
import org.apache.commons.lang.StringUtils;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import java.rmi.RemoteException;

public class HiRouterInBean implements SessionBean {
    private static final long serialVersionUID = 9008942480923232951L;
    private HiStartup startup = null;
    private static Logger log = HiLog.getErrorLogger("SYS.log");
    private static HiStringManager sm = HiStringManager.getManager();

    public void ejbCreate() throws CreateException {
        initialize();
    }

    public void ejbActivate() throws EJBException, RemoteException {
    }

    public void ejbPassivate() throws EJBException, RemoteException {
    }

    public void ejbRemove() throws EJBException, RemoteException {
        if (this.startup == null) return;
        try {
            this.startup.setFailured(false);
            this.startup.destory();
        } catch (HiException e) {
            System.out.println(sm.getString("HiRouterInBean.remove00", this.startup.getServerName()));
        }
        this.startup = null;
    }

    public void setSessionContext(SessionContext newContext) throws EJBException {
    }

    public HiMessage process(HiMessage message) {
        if (this.startup == null) {
            message.setStatus("E");
            message.setHeadItem("SSC", "212010");
            return message;
        }
        try {
            return this.startup.process(message);
        } catch (Throwable e) {
            message.setStatus("E");
            HiLog.logServiceError(message, HiException.makeException(e));
            if (e instanceof HiException) message.setHeadItem("SSC", ((HiException) e).getCode());
            else message.setHeadItem("SSC", "212012");
        }
        return message;
    }

    public HiMessage manage(HiMessage message) throws EJBException {
        if (this.startup == null) {
            message.setStatus("E");
            message.setHeadItem("SSC", "211007");
            message.setHeadItem("SEM", sm.getString("211007"));
            return message;
        }
        try {
            return this.startup.manage(message);
        } catch (Throwable e) {
            message.setStatus("E");
            if (e instanceof HiException) {
                message.setHeadItem("SSC", ((HiException) e).getCode());
                message.setHeadItem("SEM", ((HiException) e).getAppMessage());
            } else {
                message.setHeadItem("SSC", "211007");
                message.setHeadItem("SEM", sm.getString("211007"));
            }
            HiLog.logSysError("initializ00", e);
        }
        return message;
    }

    private void initialize() {
        String serverName = null;
        try {
            InitialContext ic = new InitialContext();
            serverName = (String) ic.lookup("java:comp/env/ServerName");

            if (StringUtils.isEmpty(serverName)) {
                System.out.println(sm.getString("HiRouterInBean.initialize00"));
                log.error(sm.getString("HiRouterInBean.initialize00"));
                return;
            }
            this.startup = HiStartup.getInstance(serverName);
            HiStartup.initialize(serverName);
        } catch (Throwable e) {
            System.out.println(sm.getString("HiRouterInBean.initialize01", serverName));
            HiLog.logSysError(sm.getString("HiRouterInBean.initialize01", serverName), e);
        }
    }
}