package com.hisun.crypt.mac;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class Digest {
    public static final String ENCODE = "UTF-8";


    public static String hmacSign(String aValue, String aKey) {

        return hmacSign(aValue, aKey, "UTF-8");

    }


    public static String hmacSign(String aValue, String aKey, String encoding) {

        byte[] keyb;

        byte[] value;

        byte[] k_ipad = new byte[64];

        byte[] k_opad = new byte[64];

        try {

            keyb = aKey.getBytes(encoding);

            value = aValue.getBytes(encoding);

        } catch (UnsupportedEncodingException e) {

            keyb = aKey.getBytes();

            value = aValue.getBytes();

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

            e.printStackTrace();

            return null;

        }

        md.update(k_ipad);

        md.update(value);

        byte[] dg = md.digest();

        md.reset();

        md.update(k_opad);

        md.update(dg, 0, 16);

        dg = md.digest();

        return ConvertUtils.toHex(dg);

    }


    public static String hmacSHASign(String aValue, String aKey, String encoding) {

        byte[] keyb;

        byte[] value;

        byte[] k_ipad = new byte[64];

        byte[] k_opad = new byte[64];

        try {

            keyb = aKey.getBytes(encoding);

            value = aValue.getBytes(encoding);

        } catch (UnsupportedEncodingException e) {

            keyb = aKey.getBytes();

            value = aValue.getBytes();

        }

        Arrays.fill(k_ipad, keyb.length, 64, 54);

        Arrays.fill(k_opad, keyb.length, 64, 92);

        for (int i = 0; i < keyb.length; ++i) {

            k_ipad[i] = (byte) (keyb[i] ^ 0x36);

            k_opad[i] = (byte) (keyb[i] ^ 0x5C);

        }


        MessageDigest md = null;

        try {

            md = MessageDigest.getInstance("SHA");

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();

            return null;

        }

        md.update(k_ipad);

        md.update(value);

        byte[] dg = md.digest();

        md.reset();

        md.update(k_opad);

        md.update(dg, 0, 20);

        dg = md.digest();

        return ConvertUtils.toHex(dg);

    }


    public static String digest(String aValue) {

        return digest(aValue, "UTF-8");

    }


    public static String digest(String aValue, String encoding) {

        byte[] value;

        aValue = aValue.trim();

        try {

            value = aValue.getBytes(encoding);

        } catch (UnsupportedEncodingException e) {

            value = aValue.getBytes();

        }

        MessageDigest md = null;

        try {

            md = MessageDigest.getInstance("SHA");

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();

            return null;

        }

        return ConvertUtils.toHex(md.digest(value));

    }


    public static String digest(String aValue, String alg, String encoding) {

        byte[] value;

        aValue = aValue.trim();

        try {

            value = aValue.getBytes(encoding);

        } catch (UnsupportedEncodingException e) {

            value = aValue.getBytes();

        }

        MessageDigest md = null;

        try {

            md = MessageDigest.getInstance(alg);

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();

            return null;

        }

        return ConvertUtils.toHex(md.digest(value));

    }

}