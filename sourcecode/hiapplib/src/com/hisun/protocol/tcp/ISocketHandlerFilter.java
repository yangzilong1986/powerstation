package com.hisun.protocol.tcp;

import com.hisun.exception.HiException;
import com.hisun.message.HiMessageContext;
import java.net.Socket;

public abstract interface ISocketHandlerFilter
{
  public abstract void process(Socket paramSocket, HiMessageContext paramHiMessageContext, ISocketHandler paramISocketHandler)
    throws HiException;
}