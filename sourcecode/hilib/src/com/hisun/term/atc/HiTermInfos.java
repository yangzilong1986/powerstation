package com.hisun.term.atc;


import com.hisun.message.HiContext;

import java.util.concurrent.ConcurrentHashMap;

public class HiTermInfos {
    private static final String AGREEMENT_TERM_INFOS_KEY = "AGREEMENT_TERM_INFOS_KEY";
    private ConcurrentHashMap termInfoHashMap;

    public HiTermInfos() {

        this.termInfoHashMap = new ConcurrentHashMap(10000);
    }

    public HiTermInfos(ConcurrentHashMap termInfoHashMap) {

        this.termInfoHashMap = termInfoHashMap;
    }

    public static synchronized HiTermInfos getInstance() {

        HiTermInfos instance = null;

        ConcurrentHashMap tihm = (ConcurrentHashMap) HiContext.getRootContext().getProperty("AGREEMENT_TERM_INFOS_KEY");

        if (tihm == null) {

            instance = new HiTermInfos();

            HiContext.getRootContext().setProperty("AGREEMENT_TERM_INFOS_KEY", instance.termInfoHashMap);
        } else {

            instance = new HiTermInfos(tihm);
        }

        return instance;
    }

    public void add(HiTermInfo termInfo) {

        if ((termInfo != null) && (termInfo.getLOGICAL_ADDR() != null))
            this.termInfoHashMap.put(termInfo.getLOGICAL_ADDR(), termInfo);
    }

    public void clear() {

        this.termInfoHashMap.clear();
    }

    public HiTermInfo get(String logicalAddr) {

        return ((HiTermInfo) this.termInfoHashMap.get(logicalAddr));
    }

    public static HiTermInfo getTermInfo(String logicalAddr) {

        return getInstance().get(logicalAddr);
    }
}