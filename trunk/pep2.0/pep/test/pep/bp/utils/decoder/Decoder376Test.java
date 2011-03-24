/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.utils.decoder;

import java.util.Iterator;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.*;
import pep.bp.model.Dto;
import pep.codec.protocol.gb.gb376.PmPacket376;
import pep.common.test.testUtils;

/**
 *
 * @author Thinkpad
 */
public class Decoder376Test {

    private  Decoder376  decoder376;

    public Decoder376Test() {

    }

    @BeforeClass
    public static void setUpClass() throws Exception {
       
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
       ApplicationContext app =    new  ClassPathXmlApplicationContext("beans.xml");
       decoder376 = (Decoder376)app.getBean("decoder");
    }

    @Test
    public void testDecode2dto() {
        String Msg = "683E013E01689812965634040C641001010326140108107311012537002537002437000001000000000000000000009909001000100010902100229021001700901600901600EFEFEFEFEFEFEFEFEFEFEFEFEFEFEFF916";
        PmPacket376 packet = testUtils.getPacket(Msg);
        Dto dto = new Dto(packet.getAddress().getRtua(),packet.getAfn());
        this.decoder376.decode2dto(packet, dto);
        assertTrue(dto.getDataItems().size() > 0);
      
    }


    @Test
    public void testDecode2dto_TransMit() {
        String Msg = "68B200B20068981296523400106C00000100001D0068127072000100688111A2E933733533333333333333333333963384167E16";
        PmPacket376 packet = testUtils.getPacket(Msg);
        Dto dto = new Dto(packet.getAddress().getRtua(),packet.getAfn());
        this.decoder376.decode2dto_TransMit(packet, dto);
        assertTrue(dto.getDataItems().size() > 0);
    }

    /**
     * Test of decode_TransMit method, of class Decoder376.
     */
    @Test
    public void testDecode2Map_TransMit() {
        String Msg = "68, A6, 00, A6, 00, 68, 88, 12, 96, 56, 34, 02, 10, 76, 00, 00, 01, 00, 00, 1A, 00, 68, 01, 00, 00, 00, 00, 00, 68, 81, 0E, 82, F3, C0, A3, 33, 34, 33, 34, 35, 33, 38, 33, 35, 33, 41, 16, F5, 16";
        PmPacket376 packet = testUtils.getPacket(Msg);      
        Map<String,Map<String,String>> resultMap = this.decoder376.decode2Map_TransMit(packet);
        Iterator iterator1 = resultMap.keySet().iterator();
        while (iterator1.hasNext()) {
            String key = (String) iterator1.next();
            assertTrue(key.equals("96123456#000000000001#8000C04F"));
            Map<String,String> dataMap = resultMap.get(key);
            Iterator iterator2 = resultMap.keySet().iterator();
             while (iterator2.hasNext())
             {
                 String keyTemp = (String) iterator2.next();
                 String value = dataMap.get(key);
                 if(keyTemp.equals("8000C041"))
                    assertTrue(value.equals("70"));
                 if(keyTemp.equals("8000C042"))
                    assertTrue(value.equals("1"));
                 if(keyTemp.equals("8000C043"))
                    assertTrue(value.equals("201"));
                 if(keyTemp.equals("8000C044"))
                    assertTrue(value.equals("500"));
                 if(keyTemp.equals("8000C045"))
                    assertTrue(value.equals("200"));
             }
        }
    }

    /**
     * Test of decode2Map method, of class Decoder376.
     */
    @Test
    public void testDecode2Map() {
        String Msg = "683E013E01689812965634040C641001010326140108107311012537002537002437000001000000000000000000009909001000100010902100229021001700901600901600EFEFEFEFEFEFEFEFEFEFEFEFEFEFEFF916";
        PmPacket376 packet = testUtils.getPacket(Msg);
        Map<String,Map<String,String>> resultMap = this.decoder376.decode2Map(packet);
         assertTrue(resultMap.size() > 0);
    }




}