package com.hisun.dispatcher.ejb;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import java.rmi.RemoteException;

public class HiRouterInSession extends HiRouterInBean implements SessionBean {
    public void ejbActivate() throws EJBException, RemoteException {
        super.ejbActivate();
    }

    public void ejbPassivate() throws EJBException, RemoteException {
        super.ejbPassivate();
    }

    public void setSessionContext(SessionContext ctx) throws EJBException {
        super.setSessionContext(ctx);
    }

    public void unsetSessionContext() {
    }

    public void ejbRemove() throws EJBException, RemoteException {
        super.ejbRemove();
    }
}