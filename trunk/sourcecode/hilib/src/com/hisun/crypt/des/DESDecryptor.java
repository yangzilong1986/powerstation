package com.hisun.crypt.des;


import com.hisun.crypt.Decryptor;
import com.hisun.crypt.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;


public class DESDecryptor implements Decryptor {
    private Key key;
    private Cipher dcipher;


    public DESDecryptor() {

    }


    public DESDecryptor(Key key) {

        this.key = key;

        createCipher();

    }


    public byte[] decrypt(byte[] data, int offset, int length) throws Exception {

        byte[] deCryptedData = null;

        try {

            deCryptedData = this.dcipher.doFinal(data, offset, length);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return deCryptedData;

    }


    public byte[] decrypt(byte[] data) throws Exception {

        byte[] deCryptedData = null;

        try {

            deCryptedData = this.dcipher.doFinal(data);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return deCryptedData;

    }


    public void decrypt(InputStream in, OutputStream out) throws Exception {

        try {

            int blockSize = this.dcipher.getBlockSize();

            int outputSize = this.dcipher.getOutputSize(blockSize);

            byte[] inBytes = new byte[blockSize];

            byte[] outBytes = new byte[outputSize];

            int inLength = 0;

            boolean more = true;

            while (more) {

                inLength = in.read(inBytes);

                if (inLength == blockSize) {

                    int outLength = this.dcipher.update(inBytes, 0, blockSize, outBytes);


                    out.write(outBytes, 0, outLength);

                }


                more = false;

            }

            if (inLength > 0) {

                outBytes = this.dcipher.doFinal(inBytes, 0, inLength);

            } else outBytes = this.dcipher.doFinal();

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

        if (this.key == null) return;

        try {

            DESKeySpec dks = new DESKeySpec(this.key.getKey());

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

            SecretKey sk = keyFactory.generateSecret(dks);


            SecureRandom sr = new SecureRandom();

            this.dcipher = Cipher.getInstance("DES");

            this.dcipher.init(2, sk, sr);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}