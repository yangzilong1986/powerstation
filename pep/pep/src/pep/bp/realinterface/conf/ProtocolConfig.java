/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.bp.realinterface.conf;

import java.io.IOException;
import java.rmi.MarshalException;
import java.util.List;
import java.util.Map;
import javax.xml.bind.ValidationException;
import pep.codec.utils.CastorUtil;

/**
 *
 * @author Thinkpad
 */
public class ProtocolConfig {

    private final String PROTOCOL_DATA_CONFIG_MAPPING ;
    private final String PROTOCOL_DATA_CONFIG ;
    private static ProtocolConfig instance = null;
    private static ProtocolCommandItems CommandItems;

    protected ProtocolConfig() {
        PROTOCOL_DATA_CONFIG_MAPPING = ProtocolConfig.class.getResource("protocol-data-config-mapping.xml").getPath();
        PROTOCOL_DATA_CONFIG = ProtocolConfig.class.getResource("protocol-data-config.xml").getPath();
        CommandItems = (ProtocolCommandItems) CastorUtil.unmarshal(PROTOCOL_DATA_CONFIG_MAPPING, PROTOCOL_DATA_CONFIG);
  //      CommandItems = (ProtocolCommandItems) CastorUtil.unmarshal("protocol-data-config-mapping.xml", "protocol-data-config.xml");
    }

    public static ProtocolConfig getInstance() {
        if (instance == null) {
            instance = new ProtocolConfig();
            CommandItems.FillMap();
        }
        return instance;
    }

    public String getFormat(String DataItemCode) {
        ProtocolDataItem dataItem = CommandItems.getDataItem(DataItemCode);
        if (null != dataItem) {
            return dataItem.getFormat();

        } else {
            return "";

        }
    }

    public int getLength(String DataItemCode){
        ProtocolDataItem dataItem = CommandItems.getDataItem(DataItemCode);
        if (null != dataItem) {
            return dataItem.getLength();
        } else {
            return -1;

        }
    }

    public Map<String, ProtocolDataItem> getDataItemMap(String CommandItemCode){
        return this.CommandItems.getCommandItem(CommandItemCode).getDataItemMap();
    }

}
