package com.hisun.crypt.mac;


public class Des3Encryption {
    public static final String CHAR_ENCODING = "UTF-8";

    public static byte[] encode(byte[] key, byte[] data) throws Exception {

        return MessageAuthenticationCode.des3Encryption(key, data);
    }

    public static byte[] decode(byte[] key, byte[] value) throws Exception {

        return MessageAuthenticationCode.des3Decryption(key, value);
    }

    public static String encode(String key, String data) {
        try {

            byte[] keyByte = key.getBytes("UTF-8");

            byte[] dataByte = data.getBytes("UTF-8");

            byte[] valueByte = MessageAuthenticationCode.des3Encryption(keyByte, dataByte);


            String value = new String(Base64.encode(valueByte), "UTF-8");

            return value;
        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    public static String decode(String key, String value) {
        try {

            byte[] keyByte = key.getBytes("UTF-8");

            byte[] valueByte = Base64.decode(value.getBytes("UTF-8"));

            byte[] dataByte = MessageAuthenticationCode.des3Decryption(keyByte, valueByte);


            String data = new String(dataByte, "UTF-8");

            return data;
        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    public static String encode(String value) {

        return encode("a1b2c3d4e5f6g7h8i9j0klmn", value);
    }

    public static String decode(String value) {

        return decode("a1b2c3d4e5f6g7h8i9j0klmn", value);
    }

    public static void main(String[] args) {

        String value = "test111";

        String key = "123456781234567812345678";

        System.out.println(key);

        System.out.println(encode(key, value));

        System.out.println(decode(key, encode(key, value)));
    }
}