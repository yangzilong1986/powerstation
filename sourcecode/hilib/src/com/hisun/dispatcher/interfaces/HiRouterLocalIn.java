package com.hisun.dispatcher.interfaces;

import com.hisun.message.HiMessage;
import javax.ejb.EJBLocalObject;

public abstract interface HiRouterLocalIn extends EJBLocalObject
{
  public abstract HiMessage process(HiMessage paramHiMessage);

  public abstract HiMessage manage(HiMessage paramHiMessage);
}