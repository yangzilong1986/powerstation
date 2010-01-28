package com.hisun.crypt.mac;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class CryptUtilImpl {

    public String cryptDes(String source, String key) {

        return Des3Encryption.encode(key, source);

    }


    public String decryptDes(String des, String key) {

        return Des3Encryption.decode(key, des);

    }


    public String cryptDes(String source) {

        return Des3Encryption.encode(source);

    }


    public String decryptDes(String des) {

        return Des3Encryption.decode(des);

    }


    public String cryptMd5(String source, String key) {

        byte[] keyb;

        byte[] value;

        byte[] k_ipad = new byte[64];

        byte[] k_opad = new byte[64];

        try {

            keyb = key.getBytes("UTF-8");

            value = source.getBytes("UTF-8");

        } catch (UnsupportedEncodingException e) {

            keyb = key.getBytes();

            value = source.getBytes();

        }

        Arrays.fill(k_ipad, keyb.length, 64, 54);

        Arrays.fill(k_opad, keyb.length, 64, 92);

        for (int i = 0; i < keyb.length; ++i) {

            k_ipad[i] = (byte) (keyb[i] ^ 0x36);

            k_opad[i] = (byte) (keyb[i] ^ 0x5C);

        }

        MessageDigest md = null;

        try {

            md = MessageDigest.getInstance("MD5");

        } catch (NoSuchAlgorithmException e) {

            return null;

        }

        md.update(k_ipad);

        md.update(value);

        byte[] dg = md.digest();

        md.reset();

        md.update(k_opad);

        md.update(dg, 0, 16);

        dg = md.digest();

        return toHex(dg);

    }


    public String cryptMd5(String source) {

        byte[] value;

        try {

            value = source.getBytes("UTF-8");

        } catch (UnsupportedEncodingException e) {

            value = source.getBytes();

        }

        MessageDigest md = null;

        try {

            md = MessageDigest.getInstance("MD5");

        } catch (NoSuchAlgorithmException e) {

            return null;

        }

        md.update(value);

        byte[] dg = md.digest();

        return toHex(dg);

    }


    public static String toHex(byte[] input) {

        if (input == null) {

            return null;

        }

        StringBuffer output = new StringBuffer(input.length * 2);

        for (int i = 0; i < input.length; ++i) {

            int current = input[i] & 0xFF;

            if (current < 16) output.append("0");

            output.append(Integer.toString(current, 16));

        }


        return output.toString();

    }


    public static void main(String[] args) {

        String s1;

        StringBuffer source = new StringBuffer();

        for (int i = 0; i < 1; ++i) {

            source.append("GenericServerServiceCallbackHandler Callback class, Users can extend this class and implement");

        }

        String key = "123456781234567812345678";

        CryptUtilImpl impl = new CryptUtilImpl();

        String des = null;


        long l1 = System.currentTimeMillis();

        for (int i = 0; i < 1000; ++i) {

            des = impl.cryptDes(source.toString(), key);

            s1 = impl.decryptDes(des, key);

        }

        long l2 = System.currentTimeMillis();

        System.out.println(l2 - l1);

    }

}