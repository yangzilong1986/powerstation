package com.hzjbbis.util;

import java.util.HashMap;
import java.util.Map;

public class IspConfig {
    public static final String ISP_CODE_UNICOM = "01";
    public static final String ISP_CODE_MOBILE = "02";
    private static final String PROP_PFEFIX = "fas.isp.";
    private static final String PROP_MAX_SMSCOUNT = ".maxSmsCount";
    private static final String PROP_MAX_THROUGHPUT = ".maxThroughput";
    private static IspConfig instance;
    private Map limits = new HashMap(4);

    private IspConfig() {
        CommLimit limit = readCommLimit("01");
        if (limit != null) {
            this.limits.put("01", limit);
        }

        limit = readCommLimit("02");
        if (limit != null) this.limits.put("02", limit);
    }

    public static IspConfig getInstance() {
        synchronized (IspConfig.class) {
            if (instance == null) {
                instance = new IspConfig();
            }
        }
        return instance;
    }

    public long getMaxSmsCount(String ispCode) {
        CommLimit limit = (CommLimit) this.limits.get(ispCode);
        return ((limit == null) ? 0L : limit.getMaxSmsCount());
    }

    public long getMaxThroughput(String ispCode) {
        CommLimit limit = (CommLimit) this.limits.get(ispCode);
        return ((limit == null) ? 0L : limit.getMaxThroughput());
    }

    private CommLimit readCommLimit(String ispCode) {
        CommLimit limit = new CommLimit();
        long l = FasProperties.getSize("fas.isp." + ispCode + ".maxSmsCount", 0L);
        limit.setMaxSmsCount(l);
        l = FasProperties.getSize("fas.isp." + ispCode + ".maxThroughput", 0L);
        limit.setMaxThroughput(l);

        return limit;
    }
}