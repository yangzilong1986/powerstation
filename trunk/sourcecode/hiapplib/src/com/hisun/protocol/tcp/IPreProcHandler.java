package com.hisun.protocol.tcp;

import java.io.IOException;
import java.net.Socket;

public abstract interface IPreProcHandler
{
  public abstract boolean process(Socket paramSocket)
    throws IOException;
}