package com.hzjbbis.fas.protocol.zj.viewer;

import com.hzjbbis.fas.protocol.conf.ProtocolDataConfig;
import com.hzjbbis.util.CastorUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DataConfigZj {
    private static final Log log = LogFactory.getLog(DataConfigZj.class);

    private static String DATA_MAP_FILE = "com/hzjbbis/fas/protocol/zj/conf/protocol-data-config-mapping.xml";
    private static String DATA_CONFIG_FILE = "com/hzjbbis/fas/protocol/zj/conf/protocol-data-config.xml";
    private static DataConfigZj _instance;
    private ProtocolDataConfig dataConfig;

    private DataConfigZj() {
        dataIni();
    }

    public static DataConfigZj getInstance() {
        if (_instance == null) {
            synchronized (DataConfigZj.class) {
                _instance = new DataConfigZj();
            }
        }
        return _instance;
    }

    private void dataIni() {
        try {
            this.dataConfig = ((ProtocolDataConfig) CastorUtil.unmarshal(DATA_MAP_FILE, DATA_CONFIG_FILE));

            this.dataConfig.fillMap();
        } catch (Exception e) {
            log.error("data config ini", e);
        }
    }

    public ProtocolDataConfig getDataConfig() {
        return this.dataConfig;
    }
}