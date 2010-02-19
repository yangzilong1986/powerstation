package com.hzjbbis.fas.protocol.conf;

import com.hzjbbis.fas.protocol.data.DataItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ProtocolDataConfig implements IDataSets {
    private static final Log log = LogFactory.getLog(ProtocolDataConfig.class);
    private List dataItems;
    private Map dataset;
    private HashMap sdataset;
    private HashMap parsers;

    public ProtocolDataItemConfig getDataItemConfig(String code) {
        if (this.dataset == null) {
            synchronized (this) {
                if (this.dataset == null) {
                    this.dataset = new HashMap();
                    this.sdataset = new HashMap();
                    this.parsers = new HashMap();
                    addToItemMap(this.dataItems);
                }
            }
        }
        return ((ProtocolDataItemConfig) this.dataset.get(code));
    }

    public synchronized void fillMap() {
        this.dataset = new HashMap();
        this.sdataset = new HashMap();
        this.parsers = new HashMap();
        addToItemMap(this.dataItems);
    }

    private void addToItemMap(List items) {
        if ((items == null) || (items.isEmpty())) {
            return;
        }

        for (int i = 0; i < items.size(); ++i) {
            Iterator iter;
            ProtocolDataItemConfig item = (ProtocolDataItemConfig) items.get(i);
            this.dataset.put(item.getCode(), item);
            if (item.getBean() != null) {
                loadParser(item.getCode(), item.getBean());
            }
            if (item.getItems() != null) {
                List sitems = item.getItems();
                for (iter = sitems.iterator(); iter.hasNext();) {
                    String si = (String) iter.next();
                    String keychar = item.getKeychar();
                    if (keychar != null) {
                        int index = keychar.indexOf(":");
                        if (index > 0) {
                            String[] keys = keychar.split(":");
                            for (int j = 0; j < keys.length; ++j) {
                                String key = si + "|" + keys[j];
                                this.sdataset.put(key, item.getCode());
                            }
                        } else {
                            String key = si + "|" + item.getKeychar();
                            this.sdataset.put(key, item.getCode());
                        }
                    }
                }
            }
            addToItemMap(item.getChildItems());
        }
    }

    private void loadParser(String key, String name) {
        if (name == null) return;
        try {
            if (!(this.parsers.containsKey(name))) {
                Class clazz = Class.forName(name);
                this.parsers.put(key, clazz.newInstance());
            }
        } catch (Exception e) {
            log.error("load parser", e);
        }
    }

    public List getDataItems() {
        return this.dataItems;
    }

    public void setDataItems(List dataItems) {
        this.dataItems = dataItems;
    }

    public String getLocal(String key, Object para) {
        String local = null;
        if (para instanceof DataItem) {
            DataItem di = (DataItem) para;
            String pt = (String) di.getProperty("point");
            if (pt != null) {
                String skey = key + "|" + pt;
                local = (String) this.sdataset.get(skey);
            }
        } else if (para instanceof String) {
            String skey = key + "|" + ((String) para);
            local = (String) this.sdataset.get(skey);
        }
        return local;
    }

    public IItemParser getParser(String key) {
        IItemParser parser = null;
        if (key != null) {
            parser = (IItemParser) this.parsers.get(key);
        }
        return parser;
    }

    public List getMConf(String key) {
        List conf = null;
        if (key != null) ;
        return conf;
    }
}