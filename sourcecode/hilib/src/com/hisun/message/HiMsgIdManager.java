package com.hisun.message;


import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;


class HiMsgIdManager {
    private static Map requestIdMap = new HashMap(100);


    public static synchronized String getRequestId(String server) {

        int pos = 0;

        if (requestIdMap.containsKey(server)) {

            pos = ((Integer) requestIdMap.get(server)).intValue();

        }

        requestIdMap.put(server, new Integer(++pos));


        return server + StringUtils.leftPad(String.valueOf(pos), 12, '0');

    }

}