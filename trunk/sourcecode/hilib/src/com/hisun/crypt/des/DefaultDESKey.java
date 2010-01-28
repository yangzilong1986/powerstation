package com.hisun.crypt.des;

import com.hisun.crypt.Key;

public class DefaultDESKey implements Key {
    public static final String KEYFILE = "deskey";
    private byte[] key;

    public DefaultDESKey() {
        try {

            this.key = new byte[]{-71, -98, 49, 22, 84, 107, -83, -88};
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public byte[] getKey() {

        return this.key;
    }

    public static void main(String[] sd) {

        DefaultDESKey key = new DefaultDESKey();
    }
}