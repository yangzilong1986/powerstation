package com.hisun.crypt.des;


import com.hisun.crypt.Encryptor;
import com.hisun.crypt.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;


public class DESEncryptor implements Encryptor {
    private Key key;
    Cipher ecipher;


    public DESEncryptor() {

    }


    public DESEncryptor(Key key) {

        this.key = key;

        createCipher();

    }


    public byte[] encrypt(byte[] data, int offset, int len) throws Exception {

        byte[] encryptedData = null;

        try {

            encryptedData = this.ecipher.doFinal(data, offset, len);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return encryptedData;

    }


    public byte[] encrypt(byte[] data) throws Exception {

        byte[] encryptedData = null;

        try {

            encryptedData = this.ecipher.doFinal(data);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return encryptedData;

    }


    public void encrypt(InputStream in, OutputStream out) throws Exception {

        try {

            int blockSize = this.ecipher.getBlockSize();

            int outputSize = this.ecipher.getOutputSize(blockSize);

            byte[] inBytes = new byte[blockSize];

            byte[] outBytes = new byte[outputSize];

            int inLength = 0;

            boolean more = true;

            while (more) {

                inLength = in.read(inBytes);

                if (inLength == blockSize) {

                    int outLength = this.ecipher.update(inBytes, 0, blockSize, outBytes);

                    out.write(outBytes, 0, outLength);

                }


                more = false;

            }

            if (inLength > 0) {

                outBytes = this.ecipher.doFinal(inBytes, 0, inLength);

            } else outBytes = this.ecipher.doFinal();

            out.write(outBytes);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }


    public void setKey(Key key) {

        this.key = key;

        createCipher();

    }


    private void createCipher() {

        if (this.key == null) {

            return;

        }

        try {

            SecureRandom sr = new SecureRandom();

            DESKeySpec dks = new DESKeySpec(this.key.getKey());

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

            SecretKey sk = keyFactory.generateSecret(dks);


            this.ecipher = Cipher.getInstance("DES");

            this.ecipher.init(1, sk, sr);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}