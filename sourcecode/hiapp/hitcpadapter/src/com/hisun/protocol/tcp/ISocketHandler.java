package com.hisun.protocol.tcp;

import com.hisun.exception.HiException;
import com.hisun.message.HiMessageContext;
import java.net.Socket;

public abstract interface ISocketHandler
{
  public abstract void process(Socket paramSocket, HiMessageContext paramHiMessageContext)
    throws HiException;
}