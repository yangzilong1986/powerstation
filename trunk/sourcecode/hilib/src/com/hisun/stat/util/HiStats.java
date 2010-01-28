package com.hisun.stat.util;


import com.hisun.util.HiICSProperty;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HiStats {
    public static Map stats = new HashMap();
    public static IStat dummyStat = new HiDummyStat();

    public static IStat getState(String key) {
        String flag = HiICSProperty.getProperty("sys.stat");

        if (!(StringUtils.equals(flag, "true"))) {

            return dummyStat;
        }


        if (stats.containsKey(key)) {

            stat = (HiStat) stats.get(key);


            return stat;
        }

        HiStat stat = new HiStat(key);

        stats.put(key, stat);

        return stat;
    }

    public static void removeState(String key) {

        stats.remove(key);
    }

    public static void clearState(String key) {

        stats.remove(key);
    }

    public static void clearAllStat() {

        Collection coll = stats.values();

        Iterator iter = coll.iterator();

        while (iter.hasNext()) ((HiStat) iter.next()).reset();
    }

    public static String dumpAllStat() {

        Iterator it = stats.values().iterator();

        StringBuffer ret = new StringBuffer();

        while (it.hasNext()) {

            ((HiStat) (HiStat) it.next()).dump(ret);
        }

        return ret.toString();
    }
}