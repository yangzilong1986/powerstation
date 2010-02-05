package com.hisun.crypt;

import java.io.InputStream;
import java.io.OutputStream;

public abstract interface Decryptor
{
  public abstract byte[] decrypt(byte[] paramArrayOfByte)
    throws Exception;

  public abstract byte[] decrypt(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws Exception;

  public abstract void setKey(Key paramKey);

  public abstract void decrypt(InputStream paramInputStream, OutputStream paramOutputStream)
    throws Exception;
}