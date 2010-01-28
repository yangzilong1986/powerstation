package com.hisun.dispatcher.interfaces;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;

public abstract interface HiRouterInHome extends EJBHome {
    public static final String COMP_NAME = "java:comp/env/ejb/HiRouterIn";
    public static final String JNDI_NAME = "ejb/HiRouterInBean";

    public abstract HiRouterIn create() throws CreateException, RemoteException;
}