package org.pssframework.controller.psmanage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pssframework.util.ConverterUtils;

import pep.bp.realinterface.mto.MessageTranObject;

public class PSMonitorControllerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_pstimeSetup() {
        String dtoJSONString = "{\"collectObjects_Transmit\":[{\"terminalAddr\":\"96123456\",\"equipProtocol\":\"100\",\"meterAddr\":\"000000000000\",\"meterType\":\"100\",\"funcode\":\"1\",\"port\":1,\"serialPortPara\":{\"baudrate\":\"1\",\"stopbit\":\"1\",\"checkbit\":\"1\",\"odd_even_bit\":\"1\",\"databit\":\"5\"},\"waitforPacket\":2,\"waitforByte\":3,\"commandItems\":[{\"identifier\":\"8000C012\"}]}]}";
        System.out.println("dtoJSONString : " + dtoJSONString);
        String mtoType = "100";
        try {
            MessageTranObject mto = ConverterUtils.jsonString2MessageTranObject(mtoType, dtoJSONString);

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_pstimeRead() {
        // fail("Not yet implemented");
    }

}
