/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.bp.realinterface.conf;

import java.io.IOException;
import java.util.Map;

import org.omg.CORBA.CTX_RESTRICT_SCOPE;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
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
	private static ProtocolCommandItems CommandItems;

	public ProtocolConfig(final String str1, final String str2) throws IOException {
		PROTOCOL_DATA_CONFIG_MAPPING = str1;
		PROTOCOL_DATA_CONFIG = str2;
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource resource1 = resourceLoader.getResource(str1);
		Resource resource2 = resourceLoader.getResource(str2);
		CommandItems = (ProtocolCommandItems) CastorUtil.unmarshal(resource1.getURL(), resource2.getURI());
	}

	public static ProtocolConfig getInstance() {
		if (instance == null) {
			try {
				instance = new ProtocolConfig(PROTOCOL_DATA_CONFIG_MAPPING, PROTOCOL_DATA_CONFIG);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	public int getLength(String DataItemCode) {
		ProtocolDataItem dataItem = CommandItems.getDataItem(DataItemCode);
		if (null != dataItem) {
			return dataItem.getLength();
		} else {
			return -1;

		}
	}

	public Map<String, ProtocolDataItem> getDataItemMap(String CommandItemCode) {
		return this.CommandItems.getCommandItem(CommandItemCode).getDataItemMap();
	}

}
