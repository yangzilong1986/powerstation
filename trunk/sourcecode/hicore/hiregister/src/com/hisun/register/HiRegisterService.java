package com.hisun.register;

import com.hisun.exception.HiException;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiContext;
import com.hisun.util.HiICSProperty;
import com.hisun.util.HiServiceLocator;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;

public class HiRegisterService {
    private static Logger log = HiLog.getLogger("register.trc");
    private static Logger sysLog = HiLog.getErrorLogger("SYS.log");

    public static void register(ArrayList services) throws HiException {
        for (int i = 0; i < services.size(); ++i)
            register((HiServiceObject) services.get(i));
    }

    public static void register(HiContext ctx) throws HiException {
        ctx = ctx.getFirstChild();
        String value = null;
        HiServiceObject service = null;
        while (ctx != null) {
            if (!(ctx.getId().startsWith("TRN."))) {
                ctx = ctx.getNextBrother();
            }

            if ((value = ctx.getStrProp("trans_code")) == null) {
                continue;
            }
            service = new HiServiceObject(value);
            if ((value = ctx.getStrProp("app_code")) != null) service.setAppCode(value);
            if (HiICSProperty.isPrdEnv()) {
                service.setLogLevel("0");
            } else if ((value = ctx.getStrProp("log_level")) != null) {
                service.setLogLevel(value);
            }

            if ((value = ctx.getStrProp("mon_switch")) != null) {
                service.setMonSwitch(value);
            }
            if ((value = ctx.getStrProp("trans_switch")) != null) {
                service.setRunning(value);
            }
            if ((value = ctx.getStrProp("app_name")) != null) {
                service.setAppName(value);
            }
            if ((value = ctx.getStrProp("SVR.name")) != null) {
                service.setServerName(value);
            }
            if ((value = ctx.getStrProp("SVR.type")) != null) {
                service.setServerType(value);
            }
            if ((value = ctx.getStrProp("trans_desc")) != null) {
                service.setDesc(value);
            }
            register(service);
            ctx = ctx.getNextBrother();
        }
    }

    public static void register(HiServiceObject service) throws HiException {
        try {
            if (HiICSProperty.isJUnitEnv()) {
                return;
            }
            HiServiceLocator locator = HiServiceLocator.getInstance();
            HiBind bind = HiBindFactory.getBind(service.getBindType(), service.getServerName());

            service.setBind(bind);
            service.setTime();
            locator.bind(getJndiName(service), service);
        } catch (Exception e) {
            sysLog.error("service:[ " + service.getServiceCode() + " ] duplicate");
        }
    }

    public static HiServiceObject getService(String service) throws HiException {
        if (HiICSProperty.isJUnitEnv()) {
            return new HiServiceObject(service);
        }
        HiServiceLocator locator = HiServiceLocator.getInstance();

        HiServiceObject obj = null;
        try {
            obj = (HiServiceObject) locator.lookup(getJndiName(service));
        } catch (Exception e) {
            throw new HiException("212101", service);
        }
        if (obj == null) {
            throw new HiException("212101", service);
        }

        return obj;
    }

    public static void unregister(HiContext ctx) throws HiException {
        ctx = ctx.getFirstChild();
        while (ctx != null) {
            String server = ctx.getStrProp("SVR.name");
            String code = ctx.getStrProp("trans_code");
            unregister(server, code);

            ctx = ctx.getNextBrother();
        }
    }

    public static HiServiceObject getService(String server, String service) throws HiException {
        if (HiICSProperty.isJUnitEnv()) {
            return new HiServiceObject(service);
        }
        HiServiceLocator locator = HiServiceLocator.getInstance();

        HiServiceObject obj = null;
        try {
            obj = (HiServiceObject) locator.lookup(getJndiName(service, server));
        } catch (Exception e) {
            throw new HiException("212101", service);
        }
        if (obj == null) {
            throw new HiException("212101", service);
        }

        return obj;
    }

    public static void unregister(String server, String code) throws HiException {
        HiServiceLocator locator = HiServiceLocator.getInstance();
        locator.unbind(getJndiName(code, server));
    }

    public static void unregister(String service) throws HiException {
        HiServiceLocator locator = HiServiceLocator.getInstance();
        locator.unbind(getJndiName(service));
    }

    public static void setMonSwitch(String service, boolean mon) throws HiException {
        HiServiceLocator locator = HiServiceLocator.getInstance();
        HiServiceObject obj = getService(service);
        obj.setMonSwitch((mon) ? "1" : "0");
        locator.rebind(getJndiName(obj), obj);
    }

    public static void setLogSwitch(String service, String level) throws HiException {
        HiServiceLocator locator = HiServiceLocator.getInstance();
        HiServiceObject obj = getService(service);
        obj.setLogLevel(level);
        locator.rebind(getJndiName(obj), obj);
    }

    public static String getLogSwitch(String service) throws HiException {
        HiServiceObject obj = getService(service);
        return obj.getLogLevel();
    }

    public static boolean getMonSwitch(String service) throws HiException {
        HiServiceObject obj = getService(service);
        return obj.isMonSwitch();
    }

    public static void start(String service) throws HiException {
        HiServiceLocator locator = HiServiceLocator.getInstance();
        HiServiceObject obj = getService(service);
        obj.setRunning("1");
        locator.rebind(getJndiName(obj), obj);
    }

    public static void stop(String service) throws HiException {
        HiServiceLocator locator = HiServiceLocator.getInstance();
        HiServiceObject obj = getService(service);
        obj.setRunning("2");
        locator.rebind(getJndiName(obj), obj);
    }

    public static boolean isRunning(String service) throws HiException {
        HiServiceObject obj = getService(service);
        return obj.isRunning();
    }

    public static String getJndiName(HiServiceObject service) {
        String tmp1 = StringUtils.replaceChars(service.getServiceCode(), '.', '_');

        String tmp2 = StringUtils.replaceChars(service.getServerName(), '.', '_');

        return "ibs_service_" + tmp1 + ";" + tmp2;
    }

    public static String getJndiName(String name1, String name2) {
        return "ibs_service_" + StringUtils.replaceChars(name1, '.', '_') + ";" + StringUtils.replaceChars(name2, '.', '_');
    }

    public static String getJndiName(String name) {
        return "ibs_service_" + StringUtils.replaceChars(name, '.', '_');
    }

    public static ArrayList listServices(String prefix) throws HiException {
        HiServiceLocator locator = HiServiceLocator.getInstance();
        ArrayList nameList = locator.list("ibs_service_" + prefix);
        ArrayList objectList = new ArrayList();
        for (int i = 0; i < nameList.size(); ++i)
            try {
                HiServiceObject obj = (HiServiceObject) locator.lookup((String) nameList.get(i));
                objectList.add(obj);
            } catch (Exception e) {
            }
        return objectList;
    }

    public static ArrayList listServicesByAppName(String appName) throws HiException {
        HiServiceLocator locator = HiServiceLocator.getInstance();
        ArrayList nameList1 = locator.list("ibs_service_");
        ArrayList nameList2 = new ArrayList();
        for (int i = 0; i < nameList1.size(); ++i) {
            HiServiceObject serviceObject = (HiServiceObject) locator.lookup((String) nameList1.get(i));
            if (!(StringUtils.equalsIgnoreCase(serviceObject.getAppName(), appName))) continue;
            nameList2.add(serviceObject);
        }

        return nameList2;
    }

    public static ArrayList listServicesBySvrName(String svrNam) throws HiException {
        HiServiceLocator locator = HiServiceLocator.getInstance();
        ArrayList nameList1 = locator.list("ibs_service_");
        ArrayList nameList2 = new ArrayList();
        for (int i = 0; i < nameList1.size(); ++i) {
            HiServiceObject serviceObject = (HiServiceObject) locator.lookup((String) nameList1.get(i));
            if (!(StringUtils.equalsIgnoreCase(serviceObject.getServerName(), svrNam))) continue;
            nameList2.add(serviceObject);
        }

        return nameList2;
    }

    public static ArrayList listServicesBySvrType(String svrType) throws HiException {
        HiServiceLocator locator = HiServiceLocator.getInstance();
        ArrayList nameList1 = locator.list("ibs_service_");
        ArrayList nameList2 = new ArrayList();
        for (int i = 0; i < nameList1.size(); ++i) {
            HiServiceObject serviceObject = (HiServiceObject) locator.lookup((String) nameList1.get(i));
            if (!(StringUtils.equalsIgnoreCase(serviceObject.getServerType(), svrType))) continue;
            nameList2.add(serviceObject);
        }

        return nameList2;
    }
}