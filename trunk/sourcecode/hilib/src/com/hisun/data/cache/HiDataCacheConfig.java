package com.hisun.data.cache;


import com.hisun.exception.HiException;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.parse.HiPretreatment;
import com.hisun.util.HiResource;
import org.apache.commons.digester.Digester;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;


public class HiDataCacheConfig {
    private Logger log;
    private HashMap<String, HiDataCacheConfigItem> configItems;
    private HashMap<String, HiDataCache> dataItems;
    private String dsName;
    private static HiDataCacheConfig dataCacheConfig = null;


    public HiDataCacheConfig() {

        this.log = HiLog.getLogger("SYS.trc");

        this.configItems = new HashMap();

        this.dataItems = new HashMap();

    }


    public static HiDataCacheConfig getInstance() {

        if (dataCacheConfig == null) {

            dataCacheConfig = new HiDataCacheConfig();

        }

        return dataCacheConfig;

    }


    public static void setInstance(HiDataCacheConfig dataCacheConfig1) {

        dataCacheConfig = dataCacheConfig1;

    }


    public void addItem(HiDataCacheConfigItem item) {

        if (this.log.isDebugEnabled()) {

            this.log.debug("addItem:[" + item.toString() + "]");

        }

        this.configItems.put(item.getId(), item);

    }


    public void validate(String id, HiDBUtil dbUtil) throws HiException {

        HiDataCacheConfigItem item = (HiDataCacheConfigItem) this.configItems.get(id);

        if (item == null) {

            throw new HiException("id:[ " + id + "] not existed");

        }


        if (this.log.isInfoEnabled()) {

            this.log.info("validate:[" + id + "]");

        }


        HiDataCacheDBImpl dataCache = new HiDataCacheDBImpl();

        dbUtil.setDsName(this.dsName);

        dataCache.setDbUtil(dbUtil);

        dataCache.setConfigItem(item);

        dataCache.load();

        this.dataItems.put(id, dataCache);

        if (this.log.isInfoEnabled()) this.log.info("validate:[" + id + "] succeed");

    }


    public HiDataCache getDataCache(String id) {

        return ((HiDataCache) this.dataItems.get(id));

    }


    public HashMap<String, HiDataCache> getDataMap() {

        return this.dataItems;

    }


    public void process(HiDBUtil dbUtil) throws HiException {

        Iterator iter = this.configItems.keySet().iterator();

        while (iter.hasNext()) {

            HiDataCacheConfigItem item = (HiDataCacheConfigItem) this.configItems.get(iter.next());

            HiDataCacheDBImpl dataCache = new HiDataCacheDBImpl();

            dbUtil.setDsName(this.dsName);

            dataCache.setDbUtil(dbUtil);

            dataCache.setConfigItem(item);

            dataCache.load();

            this.dataItems.put(item.getId(), dataCache);

            this.log.info("load:[" + item.getId() + "] succeed");
        }

    }


    public static HiDataCacheConfig loadFile(String configFile) throws HiException {

        InputStream is = HiResource.getResourceAsStream(configFile);

        if (is == null) {

            throw new HiException("文件:[" + configFile + "]不存在!");

        }

        return loadStream(is);

    }


    public static HiDataCacheConfig loadStream(InputStream is) throws HiException {

        Document document;

        SAXReader saxReader = new SAXReader();

        try {

            document = saxReader.read(is);

        } catch (DocumentException e) {

            throw new HiException(e);

        }

        Element rootNode = document.getRootElement();

        HashMap allElements = HiPretreatment.getAllElements(rootNode, null);

        HiPretreatment.parseInclude(allElements, document);

        String strXML = document.asXML();

        ByteArrayInputStream inFile = new ByteArrayInputStream(strXML.getBytes());


        InputStreamReader in = new InputStreamReader(inFile);

        Digester digester = new Digester();

        digester.setValidating(false);

        digester.addObjectCreate("Config", "com.hisun.data.cache.HiDataCacheConfig");


        digester.addSetProperties("Config");


        digester.addObjectCreate("Config/Item", "com.hisun.data.cache.HiDataCacheConfigItem");


        digester.addSetProperties("Config/Item");

        digester.addCallMethod("Config/Item/Col", "setColInfo", 2);

        digester.addCallParam("Config/Item/Col", 0, "SrcCol");

        digester.addCallParam("Config/Item/Col", 1, "DstCol");

        digester.addSetNext("Config/Item", "addItem", "com.hisun.data.cache.HiDataCacheConfigItem");

        try {

            return ((HiDataCacheConfig) digester.parse(in));

        } catch (Exception e) {

            e.printStackTrace();

            throw new HiException(e);

        }

    }


    public void clear() {

        if (this.configItems != null) {

            this.configItems.clear();

        }

        if (this.dataItems == null) return;

        this.dataItems.clear();

    }


    public String getDsName() {

        return this.dsName;

    }


    public void setDsName(String dsName) {

        this.dsName = dsName;

    }

}