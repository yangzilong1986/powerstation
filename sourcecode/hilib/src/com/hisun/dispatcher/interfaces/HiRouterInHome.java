package com.hisun.dispatcher.interfaces;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public abstract interface HiRouterInHome extends EJBHome
{
  public static final String COMP_NAME = "java:comp/env/ejb/HiRouterIn";
  public static final String JNDI_NAME = "ejb/HiRouterInBean";

  public abstract HiRouterIn create()
    throws CreateException, RemoteException;
}