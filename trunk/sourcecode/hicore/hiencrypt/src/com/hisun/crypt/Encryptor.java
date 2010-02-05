package com.hisun.crypt;

import java.io.InputStream;
import java.io.OutputStream;

public abstract interface Encryptor
{
  public abstract byte[] encrypt(byte[] paramArrayOfByte)
    throws Exception;

  public abstract byte[] encrypt(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws Exception;

  public abstract void encrypt(InputStream paramInputStream, OutputStream paramOutputStream)
    throws Exception;

  public abstract void setKey(Key paramKey);
}