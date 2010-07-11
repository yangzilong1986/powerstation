package org.pssframework.util;

import net.sf.json.JSONObject;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import pep.bp.realinterface.mto.CollectObject;
import pep.bp.realinterface.mto.MessageTranObject;

public class ConverterUtilsTest {

    @Test
    public void testJsonString2CollectObject() throws Exception {
        String jsonString = "{\"logicalAddr\":\"91010001\",\"equipProtocol\":\"100\",\"pwAlgorith\":\"0\",\"pwContent\":\"8888\",\"mpExpressMode\":\"3\",\"mpSn\":\"0\",\"commandItems\":[{\"identifier\":\"10040001\",\"datacellParam\":[{\"dataItemCode\":\"1004000101\",\"dataItemValue\":\"5\"},{\"dataItemCode\":\"1004000102\",\"dataItemValue\":\"1\"},{\"dataItemCode\":\"1004000103\",\"dataItemValue\":\"10\"},{\"dataItemCode\":\"1004000104\",\"dataItemValue\":\"5\"},{\"dataItemCode\":\"1004000106\",\"dataItemValue\":\"00100000\"},{\"dataItemCode\":\"1004000107\",\"dataItemValue\":\"30\"}]},{\"identifier\":\"10040003\",\"datacellParam\":[{\"dataItemCode\":\"1004000301\",\"dataItemValue\":\"192.168.1.100:1024\"},{\"dataItemCode\":\"1004000302\",\"dataItemValue\":\"192.168.1.101:1024\"},{\"dataItemCode\":\"1004000303\",\"dataItemValue\":\"0000000000000000\"}]}]}";
        CollectObject collectObject = ConverterUtils.jsonString2CollectObject(jsonString);
        System.out.println(collectObject.toString());
    }

    @Test
    public void testJsonString2MessageTranObject() throws Exception {
        String jsonString = "{\"mtoType\":\"100\",\"coList\":[{\"logicalAddr\":\"91010001\",\"equipProtocol\":\"100\",\"channelType\":\"1\",\"pwAlgorith\":\"0\",\"pwContent\":\"8888\",\"mpExpressMode\":\"3\",\"mpSn\":\"0\",\"commandItems\":[{\"identifier\":\"10040001\",\"datacellParam\":[{\"dataItemCode\":\"1004000101\",\"dataItemValue\":\"5\"},{\"dataItemCode\":\"1004000102\",\"dataItemValue\":\"1\"},{\"dataItemCode\":\"1004000103\",\"dataItemValue\":\"10\"},{\"dataItemCode\":\"1004000104\",\"dataItemValue\":\"5\"},{\"dataItemCode\":\"1004000106\",\"dataItemValue\":\"00100000\"},{\"dataItemCode\":\"1004000107\",\"dataItemValue\":\"30\"}]},{\"identifier\":\"10040003\",\"datacellParam\":[{\"dataItemCode\":\"1004000301\",\"dataItemValue\":\"192.168.1.100:1024\"},{\"dataItemCode\":\"1004000302\",\"dataItemValue\":\"192.168.1.101:1024\"},{\"dataItemCode\":\"1004000303\",\"dataItemValue\":\"0000000000000000\"}]}]}]}";
        MessageTranObject mto = ConverterUtils.jsonString2MessageTranObject(jsonString);
        JSONObject jo = JSONObject.fromObject(mto);
        System.out.println(jo.toString());
        
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(mto));
    }
}
