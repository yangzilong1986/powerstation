/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.bp.realinterface.conf;

import java.io.IOException;
import java.rmi.MarshalException;
import java.util.Map;
import javax.xml.bind.ValidationException;
import pep.codec.utils.CastorUtil;

/**
 *
 * @author Thinkpad
 */
public class ProtocolConfig {

    private final String PROTOCOL_DATA_CONFIG_MAPPING = "protocol-data-config-mapping.xml";
    private final String PROTOCOL_DATA_CONFIG = "protocol-data-config.xml";
    private static ProtocolConfig instance = null;
    private static ProtocolCommandItems CommandItems;

    protected ProtocolConfig() {
        CommandItems = (ProtocolCommandItems) CastorUtil.unmarshal(PROTOCOL_DATA_CONFIG_MAPPING, PROTOCOL_DATA_CONFIG);
    }

    public static ProtocolConfig getInstance() {
        if (instance == null) {
            instance = new ProtocolConfig();
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
}
