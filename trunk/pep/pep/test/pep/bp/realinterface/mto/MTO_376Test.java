/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.realinterface.mto;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Thinkpad
 */
public class MTO_376Test {

    public MTO_376Test() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of toJson method, of class MTO_376.
     */
    @Test
    public void testToJson() {
        System.out.println("toJson");
        MTO_376 instance = new MTO_376();
        CollectObject cobj = new CollectObject();
        instance.addCollectObject(cobj);
        StringBuffer sb = new StringBuffer();
        sb.append("{\"ID\":0,\"CollectObjects\":");
        sb.append("[{\"channelType\":\"\",\"commandItems\":[],");
        sb.append("\"equipProtocol\":\"\",\"logicalAddr\":\"\",\"mpExpressMode\":0,\"mpSn\":[],");
        sb.append("\"pwAlgorith\":\"\",\"pwContent\":\"\"}]}");
        String expResult = sb.toString();
        String result = instance.toJson();
        assertEquals(expResult, result);

    }

}