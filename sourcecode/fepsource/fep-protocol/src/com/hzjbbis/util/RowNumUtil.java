package com.hzjbbis.util;

import java.util.HashMap;
import java.util.Map;

public class RowNumUtil {
    public static Map calcRowNum(int pageNum, int pageSize) {
        int startRowNum = (pageNum - 1) * pageSize;

        int endRowNum = startRowNum + pageSize;
        if (pageSize < 0) {
            startRowNum = 0;
            endRowNum = 2147483647;
        }

        Map params = new HashMap();
        params.put("startRowNum", new Integer(startRowNum));
        params.put("endRowNum", new Integer(endRowNum));

        return params;
    }
}