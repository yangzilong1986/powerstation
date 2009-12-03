package com.hisun.protocol.tcp;

import java.net.Socket;

public abstract interface HiTcpConnectionHandler
{
  public abstract Object[] initParam();

  public abstract void processConnection(Socket paramSocket, Object[] paramArrayOfObject);
}