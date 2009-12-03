package com.hisun.dispatcher.interfaces;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public abstract interface HiRouterInLocalHome extends EJBLocalHome
{
  public static final String COMP_NAME = "java:comp/env/ejb/HiRouterIn";
  public static final String JNDI_NAME = "ejb/HiRouterInBean";

  public abstract HiRouterLocalIn create()
    throws CreateException;
}