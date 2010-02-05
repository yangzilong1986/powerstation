package com.hisun.client;

import com.hisun.message.HiETF;
import com.hisun.message.HiETFFactory;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class HiInvokeService {
    protected static HiConfigInfo info = new HiConfigInfo();
    private static final String SERVICE_CONFIG = "service_config.xml";
    protected HiETF etf = HiETFFactory.createETF();
    protected Logger logger = Logger.getLogger("invokeservice");
    protected String code;

    public static HiInvokeService createService() throws Exception {
        if ("TCP".equalsIgnoreCase(info.getMode())) return createTCPService();
        if ("POJO".equalsIgnoreCase(info.getMode())) {
            return createPOJOService();
        }
        return createPOJOService();
    }

    public static HiInvokeService createService(String code) throws Exception {
        if ("TCP".equalsIgnoreCase(info.getMode())) return createTCPService(code);
        if ("POJO".equalsIgnoreCase(info.getMode())) {
            return createPOJOService(code);
        }
        return createPOJOService(code);
    }

    public static HiInvokeService createPOJOService() {
        return new HiPOJOInvokeService();
    }

    public static HiInvokeService createTCPService() throws Exception {
        return new HiTCPInvokeService();
    }

    public static HiInvokeService createPOJOService(String code) {
        return new HiPOJOInvokeService(code);
    }

    public static HiInvokeService createTCPService(String code) throws Exception {
        return new HiTCPInvokeService(code);
    }

    public HiInvokeService() {
    }

    public HiInvokeService(String code) {
        this.code = code;
    }

    public void setETF(HiETF etf) {
        this.etf = etf;
    }

    public HiETF getETF() {
        return this.etf;
    }

    public String toString() {
        return this.etf.toString();
    }

    public void clear() {
        this.etf = HiETFFactory.createETF();
    }

    public void put(String key, String value) {
        if ((key == null) || (value == null)) {
            throw new RuntimeException("key or value is null");
        }
        this.etf.setChildValue(key, value);
    }

    public void putArray(String grpNam, List objectList) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        putArray(this.etf, objectList, grpNam);
    }

    public void putObject(String key, Object object) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if ((key == null) || (object == null)) {
            throw new RuntimeException("key or object is null");
        }
        HiETF grp = this.etf.addNode(key);
        putObject(grp, object);
    }

    public void putObject(Object object) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (object == null) {
            throw new RuntimeException("object is null");
        }
        putObject(this.etf, object);
    }

    public HashMap invoke(String code) throws Exception {
        this.etf = doInvoke(code, this.etf);
        HiETF2HashMapList etf2HashMapList = new HiETF2HashMapList(this.etf);

        return etf2HashMapList.map();
    }

    public HashMap invoke() throws Exception {
        return invoke(this.code);
    }

    public HiETF invokeRetETF(String code) throws Exception {
        if (this.logger.isInfoEnabled()) {
            this.logger.info("request data:[" + code + ":" + this.etf + "]");
        }
        return (this.etf = doInvoke(code, this.etf));
    }

    public HiETF invokeRetETF() throws Exception {
        return invokeRetETF(this.code);
    }

    private void putObject(HiETF etf, Object object) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (object == null) {
            throw new RuntimeException("object is null");
        }
        if (object instanceof HashMap) {
            putHashMap(etf, (HashMap) object);
            return;
        }

        Map map = PropertyUtils.describe(object);
        Iterator iter = map.keySet().iterator();
        while (iter.hasNext()) {
            String name = (String) iter.next();
            if ("class".equals(name)) {
                continue;
            }
            Object value = map.get(name);
            if (value == null) {
                continue;
            }
            if (value instanceof String) {
                etf.setChildValue(name, value.toString());
            } else if (value instanceof List) {
                putArray(etf, (List) value, name);
            } else if (value instanceof HashMap) {
                HiETF node = etf.addNode(name);
                putHashMap(node, (HashMap) value);
            }
        }
    }

    public void putArray(HiETF node, List objectList, String grpNam) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if ((grpNam == null) || (objectList == null)) {
            throw new RuntimeException("grpNam or objectList is null");
        }

        for (int i = 0; i < objectList.size(); ++i) {
            HiETF grp = node.addNode(grpNam + "_" + (i + 1));
            putObject(grp, objectList.get(i));
        }
        node.setChildValue(grpNam + "_NUM", String.valueOf(objectList.size()));
    }

    public void putHashMap(HiETF node, HashMap map) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
            String key = (String) iter.next();
            Object obj = map.get(key);
            if (obj instanceof String) {
                node.setChildValue(key, (String) obj);
            } else if (obj instanceof HashMap) {
                HiETF node1 = node.addNode(key);
                putHashMap(node1, (HashMap) obj);
            } else if (obj instanceof List) {
                putArray(node, (List) obj, key);
            }
        }
    }

    protected abstract HiETF doInvoke(String paramString, HiETF paramHiETF) throws Exception;

    static {
        try {
            info.load("service_config.xml");
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}