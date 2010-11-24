/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.bp.realinterface.conf;

import java.io.IOException;
import java.util.List;
import java.util.Map;


import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import pep.codec.utils.CastorUtil;

/**
 *
 * @author Thinkpad
 */
public class ProtocolConfig {

	private static String PROTOCOL_DATA_CONFIG_MAPPING;
	private static String PROTOCOL_DATA_CONFIG;
	private static ProtocolConfig instance = null;
	private static ProtocolCommandItems commandItems;


	public ProtocolConfig(final String str1, final String str2) throws IOException {
            if (instance == null) {
                try {
                    PROTOCOL_DATA_CONFIG_MAPPING = str1;
                    PROTOCOL_DATA_CONFIG = str2;
                    ResourceLoader resourceLoader = new DefaultResourceLoader();
                    Resource resource1 = resourceLoader.getResource(str1);
                    Resource resource2 = resourceLoader.getResource(str2);
                    commandItems = (ProtocolCommandItems) CastorUtil.unmarshal(resource1.getURL(), resource2.getURI());
                    commandItems.FillMap();
                } catch (IOException iOException) {
                    iOException.printStackTrace();
                }
            }
	}

	public static ProtocolConfig getInstance() {
		if (instance == null) {
			try {
				instance = new ProtocolConfig(PROTOCOL_DATA_CONFIG_MAPPING, PROTOCOL_DATA_CONFIG);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			commandItems.FillMap();
		}
		return instance;
	}

	public String getFormat(String DataItemCode) {
		ProtocolDataItem dataItem = commandItems.getDataItem(DataItemCode);
		if (null != dataItem) {
			return dataItem.getFormat();

		} else {
			return "";

		}
	}

	public int getLength(String DataItemCode) {
		ProtocolDataItem dataItem = commandItems.getDataItem(DataItemCode);
		if (null != dataItem) {
			return dataItem.getLength();
		} else {
			return -1;

		}
	}

    @SuppressWarnings("static-access")
	public Map<String, ProtocolDataItem> getDataItemMap(String CommandItemCode) {
		return this.commandItems.getCommandItem(CommandItemCode).getDataItemMap();
	}

        public List<ProtocolDataItem> getDataItemList(String CommandItemCode){
            return commandItems.getCommandItem(CommandItemCode).getDataItems();
        }

}
