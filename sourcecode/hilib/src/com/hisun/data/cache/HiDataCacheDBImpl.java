package com.hisun.data.cache;


import com.hisun.exception.HiException;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import org.apache.commons.beanutils.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class HiDataCacheDBImpl implements HiDataCache {
    private HiDBUtil dbUtil;
    private HiDataCacheConfigItem configItem;
    private ArrayList dataList;
    private HashMap dataMap;
    private Logger log;


    public HiDataCacheDBImpl() {

        this.log = HiLog.getLogger("SYS.trc");

    }


    public void validate() throws HiException {

    }


    public void load() throws HiException {

        HashMap record;

        List list = this.dbUtil.execQuery(this.configItem.getSql());

        String className = this.configItem.getClassName();

        if (className == null) {

            this.dataList = ((ArrayList) list);

            return;

        }


        if ("java.util.HashMap".equals(className)) {

            Object o;

            try {

                o = this.configItem.getNewObject();

            } catch (Exception e1) {

                throw new HiException(e1);

            }

            this.dataMap = ((HashMap) o);

            for (int i = 0; i < list.size(); ++i) {

                record = (HashMap) list.get(i);

                Iterator iter = this.configItem.getColMaps().keySet().iterator();

                if (!(iter.hasNext())) continue;

                String key = (String) iter.next();

                String value1 = (String) record.get(key);

                if (!(iter.hasNext())) continue;

                key = (String) iter.next();

                String value2 = (String) record.get(key);

                this.dataMap.put(value1, value2);

            }

            if (this.log.isDebugEnabled()) {

                this.log.debug(this.dataMap);

            }

            return;

        }


        this.dataList = new ArrayList();

        for (int i = 0; i < list.size(); ++i)

            try {

                Object o = this.configItem.getNewObject();

                record = (HashMap) list.get(i);

                HashMap colsMap = this.configItem.getColMaps();

                Iterator iter = colsMap.keySet().iterator();

                while (iter.hasNext()) {

                    String srcCol = (String) iter.next();

                    String dstCol = (String) colsMap.get(srcCol);

                    String value = (String) record.get(srcCol);

                    if (value == null) {

                        continue;

                    }

                    record.remove(srcCol);

                    record.put(dstCol, value);

                }

                BeanUtils.populate(o, record);

                this.dataList.add(o);

            } catch (Exception e) {

                this.log.error(e, e);

                throw new HiException(e);

            }

    }


    public HiDBUtil getDbUtil() {

        return this.dbUtil;

    }


    public void setDbUtil(HiDBUtil dbUtil) {

        this.dbUtil = dbUtil;

    }


    public HiDataCacheConfigItem getConfigItem() {

        return this.configItem;

    }


    public void setConfigItem(HiDataCacheConfigItem configItem) {

        this.configItem = configItem;

    }


    public ArrayList getDataList() {

        return this.dataList;

    }


    public String getValue(String name) {

        if (this.dataMap == null) {

            return null;

        }

        return ((String) this.dataMap.get(name));

    }

}