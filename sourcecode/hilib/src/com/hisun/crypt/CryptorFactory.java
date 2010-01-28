package com.hisun.crypt;

public abstract interface CryptorFactory {
    public abstract Decryptor getDecryptor();

    public abstract Encryptor getEncryptor();

    public abstract Key getDefaultDecryptKey();

    public abstract Key getDefaultEncryptKey();

    public abstract String getAlgorithmName();
}