package com.hisun.dispatcher.interfaces;

import com.hisun.message.HiMessage;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public abstract interface HiRouterIn extends EJBObject
{
  public abstract HiMessage process(HiMessage paramHiMessage)
    throws RemoteException;

  public abstract HiMessage manage(HiMessage paramHiMessage)
    throws RemoteException;
}