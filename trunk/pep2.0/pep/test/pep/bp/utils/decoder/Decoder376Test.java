/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.utils.decoder;

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
import pep.codec.utils.BcdUtils;

/**
 *
 * @author Thinkpad
 */
public class Decoder376Test {
    private Decoder376 decoder376;

    public Decoder376Test() {
       ApplicationContext app =    new  ClassPathXmlApplicationContext("beans.xml");
       decoder376 = (Decoder376)app.getBean("decoder");
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

    @Test
    public void testDecode() {
        String Msg = "683E013E01689812965634040C641001010326140108107311012537002537002437000001000000000000000000009909001000100010902100229021001700901600901600EFEFEFEFEFEFEFEFEFEFEFEFEFEFEFF916";
        byte[] msg = BcdUtils.stringToByteArray(Msg);

        PmPacket376 packet = new PmPacket376();
        packet.setValue(msg, 0);
        Dto dto = new Dto(packet.getAddress().getRtua(),packet.getAfn());
        this.decoder376.decode(packet, dto);
        assertTrue(dto.getDataItems().size() > 0);
    }

    @Test
    public void testDecode_Object_Dto() {
        System.out.println("decode");
        Object pack = null;
        Dto dto = null;
        Decoder376 instance = new Decoder376();
        instance.decode(pack, dto);
        fail("The test case is a prototype.");
    }

    @Test
    public void testDecode_TransMit() {
        System.out.println("decode_TransMit");
        Object pack = null;
        Map<String, Map<String, String>> results = null;
        Decoder376 instance = new Decoder376();
        instance.decode_TransMit(pack, results,false);
        fail("The test case is a prototype.");
    }

}