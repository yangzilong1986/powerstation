package com.hzjbbis.fk.rtu;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RtuAddressManager {
    private static final Map<Integer, RtuAddress> map = Collections.synchronizedMap(new HashMap(51207));

    public static void put(int rtu, String peerAddr) {
        RtuAddress addr = (RtuAddress) map.get(Integer.valueOf(rtu));
        if (addr != null) {
            addr.setPeerAddr(peerAddr);
        } else {
            addr = new RtuAddress(peerAddr);
            map.put(Integer.valueOf(rtu), addr);
        }
    }

    public static RtuAddress get(int rtua) {
        return ((RtuAddress) map.get(Integer.valueOf(rtua)));
    }
}