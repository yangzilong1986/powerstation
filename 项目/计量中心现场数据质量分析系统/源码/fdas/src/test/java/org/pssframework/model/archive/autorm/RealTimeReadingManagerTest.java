package org.pssframework.model.archive.autorm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-7-25
 * Time: 16:41:30
 * To change this template use File | Settings | File Templates.
 */
public class RealTimeReadingManagerTest {

	private Map mapReturn = new HashMap();

	@org.junit.Test
	public void testMap() {
		Map testValueMap1 = new HashMap();
		testValueMap1.put("20100309000000", "0.0001");
		testValueMap1.put("20100309001500", "0.0002");
		testValueMap1.put("20100309003000", "0.0003");
		testValueMap1.put("20100309010000", "0.0004");
		testValueMap1.put("20100309011500", "0.0005");
		testValueMap1.put("20100309013000", "0.0006");
		testValueMap1.put("20100309014500", "0.0007");
		testValueMap1.put("20100309020000", "0.0008");
		testValueMap1.put("20100309021500", "0.0009");
		testValueMap1.put("20100309023000", "0.0010");
		mapReturn.put("8D013099#0#2000A01F#1101", testValueMap1);
		mapReturn.put("8D013099#0#2000A01F#1100", testValueMap1);
		mapReturn.put("8D013099#1#2000A01F#1101", testValueMap1);
		mapReturn.put("8D013099#1#2000A01F#1100", testValueMap1);

		Map result = new HashMap();
		Iterator iterator = mapReturn.keySet().iterator();
		while (iterator.hasNext()) {
			String key = null;
			HashMap value = null;
			key = (String) iterator.next();
			value = (HashMap) mapReturn.get(key);
			Iterator valueiterator = value.keySet().iterator();
			String[] keys = key.split("#");
			if (keys.length == 4) {
				key = keys[0] + "_" + keys[1] + "_" + keys[3];
				while (valueiterator.hasNext()) {
					String sresult = null;
					String timekey = null;
					timekey = (String) valueiterator.next();
					sresult = (String) value.get(timekey);
					result.put(key, sresult);
				}
			}
		}

		System.out.print(result);
	}
}
