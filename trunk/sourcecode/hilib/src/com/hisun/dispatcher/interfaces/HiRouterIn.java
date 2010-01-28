package com.hisun.dispatcher.interfaces;

import com.hisun.message.HiMessage;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

public abstract interface HiRouterIn extends EJBObject {
    public abstract HiMessage process(HiMessage paramHiMessage) throws RemoteException;

    public abstract HiMessage manage(HiMessage paramHiMessage) throws RemoteException;
}