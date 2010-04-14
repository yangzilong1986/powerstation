package javacommon.xsqlbuilder;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class SafeSqlProcesserUtils {
	
	public void processAll(Properties map,SafeSqlProcesser processer) {
		for(Iterator it = map.keySet().iterator();it.hasNext();) {
			String key = (String)it.next();
			String value = map.getProperty(key);
			map.put(key,processer.process(value));
		}
	}
	
}
