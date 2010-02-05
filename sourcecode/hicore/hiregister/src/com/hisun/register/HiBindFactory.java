package com.hisun.register;

import org.apache.commons.lang.StringUtils;

public class HiBindFactory {
    public static HiBind getBind(String type, String id) {
        HiBind returnHiBind;
        if (StringUtils.equalsIgnoreCase(type, "EJB")) {
            returnHiBind = new HiEJBBind(id);
            return returnHiBind;
        }
        if (StringUtils.equalsIgnoreCase(type, "TCP")) {
            returnHiBind = new HiTCPBind();
            return returnHiBind;
        }
        if (StringUtils.equalsIgnoreCase(type, "JMS")) {
            returnHiBind = new HiJMSBind();
            return returnHiBind;
        }
        if (StringUtils.equalsIgnoreCase(type, "POJO")) {
            returnHiBind = new HiPOJOBind(id);
            return returnHiBind;
        }

        return null;
    }
}