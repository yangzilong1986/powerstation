package com.hisun.parse;

import com.hisun.exception.HiException;
import com.hisun.util.HiResource;
import org.apache.commons.lang.StringUtils;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;

public class HiResourceRule {
    private static URL ruleCTLRul = null;

    private static URL ruleITFRul = null;

    private static HashMap rules = new HashMap();

    private static Properties pro = null;

    public static URL getCTLRule() {
        if (ruleCTLRul == null) {
            ruleCTLRul = HiResource.getResource("config/CTL_RULES.XML");
            rules.put("config/CTL_RULES.XML", ruleCTLRul);
        }
        return ruleCTLRul;
    }

    public static URL getITFRule() {
        if (ruleITFRul == null) {
            ruleITFRul = HiResource.getResource("config/ITF_RULES.XML");
            rules.put("config/ITF_RULES.XML", ruleITFRul);
        }
        return ruleITFRul;
    }

    public static URL getRule(String strRule) {
        URL rule = null;
        if (strRule == null) return null;
        if (rules.containsKey(strRule)) {
            rule = (URL) rules.get(strRule);
        } else {
            rule = HiResource.getResource(strRule);
            if (rule == null) return null;
            rules.put(strRule, rule);
        }
        return rule;
    }

    public static URL getRuleForFile(String strFileName) throws HiException {
        return getRule(getRuleFile(strFileName));
    }

    private static Properties getProperties() throws HiException {
        try {
            if (pro == null) {
                pro = new Properties();

                InputStream in = HiResource.getResourceAsStream("config/rules.properties");

                if (in == null) {
                    throw new HiException("215028", "config/rules.properties");
                }
                pro.load(in);
            }
        } catch (Exception e) {
            throw new HiException("215028", "config/rules.properties", e);
        }

        return pro;
    }

    public static HiCfgFile getCTLCfgFile(URL urlFilePath) throws HiException {
        HiCfgFile cfg = HiCfgFile.getDefaultCfgFile(urlFilePath, getCTLRule(), "Attribute");

        return cfg;
    }

    public static HiCfgFile getITFCfgFile(URL urlFilePath) throws HiException {
        HiCfgFile cfg = HiCfgFile.getDefaultCfgFile(urlFilePath, getITFRule(), null);

        return cfg;
    }

    private static String getRuleFile(String strFileName) throws HiException {
        Properties pro = getProperties();
        String key = StringUtils.substring(strFileName, -7);

        String rule = pro.getProperty(key.toUpperCase());

        return rule;
    }
}