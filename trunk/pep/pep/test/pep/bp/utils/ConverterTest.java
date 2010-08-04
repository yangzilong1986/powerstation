/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.*;
import pep.bp.model.Dto;
import pep.bp.realinterface.mto.CollectObject;
import pep.bp.realinterface.mto.CommandItem;
import pep.codec.protocol.gb.PmPacketData;
import pep.codec.protocol.gb.gb376.PmPacket376;
import pep.codec.utils.BcdUtils;

/**
 *
 * @author Thinkpad
 */
public class ConverterTest {
    private Converter converter;

    public ConverterTest() {
         ApplicationContext cxt =  new ClassPathXmlApplicationContext("beans.xml");
         converter = (Converter) cxt.getBean("converter");
    }


    /**
     * Test of CollectObject2Packet method, of class Converter.
     */
   // @Test
    public void testCollectObject2Packet() {
        System.out.println("CollectObject2Packet");
        CollectObject obj = null;
        PmPacket376 packet = null;
        byte AFN = 0;
        StringBuffer gpMark = null;
        StringBuffer commandMark = null;
        Converter instance = new Converter();
        instance.CollectObject2Packet(obj, packet, AFN, gpMark, commandMark);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of CollectObject2PacketList method, of class Converter.
     */
   // @Test
    public void testCollectObject2PacketList() {
        System.out.println("CollectObject2PacketList");
        CollectObject obj = null;
        byte AFN = 0;
        StringBuffer gpMark = null;
        StringBuffer commandMark = null;
        int CmdItemNum = 0;
        Converter instance = new Converter();
        List expResult = null;
        List result = instance.CollectObject2PacketList(obj, AFN, gpMark, commandMark, CmdItemNum);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of CollectObject_TransMit2PacketList method, of class Converter.
     */
  //  @Test
    public void testCollectObject_TransMit2PacketList() {
        System.out.println("CollectObject_TransMit2PacketList");
        CollectObject obj = null;
        StringBuffer commandMark = null;
        Converter instance = new Converter();
        List expResult = null;
        List result = instance.CollectObject_TransMit2PacketList(obj, commandMark);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of putDataBuf method, of class Converter.
     */
  //  @Test
    public void testPutDataBuf() {
        System.out.println("putDataBuf");
        PmPacket376 packet = null;
        CommandItem commandItem = null;
        Converter instance = new Converter();
        instance.putDataBuf(packet, commandItem);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of FillDataBuffer method, of class Converter.
     */
 //   @Test
    public void testFillDataBuffer() {
        System.out.println("FillDataBuffer");
        PmPacketData packetdata = null;
        String Format = "";
        String DataItemValue = "";
        String IsGroupEnd = "";
        int Length = 0;
        int bitnumber = 0;
        Converter instance = new Converter();
        instance.FillDataBuffer(packetdata, Format, DataItemValue, IsGroupEnd, Length, bitnumber);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of decodeData method, of class Converter.
     */
    @Test
    public void testDecodeData() {
        String Msg = "68, A6, 00, A6, 00, 68, 88, 12, 96, 56, 34, 02, 10, 76, 00, 00, 01, 00, 00, 1A, 00, 68, 01, 00, 00, 00, 00, 00, 68, 81, 0E, 82, F3, C0, A3, 33, 34, 33, 34, 35, 33, 38, 33, 35, 33, 41, 16, F5, 16";
        byte[] msg = BcdUtils.stringToByteArray(Msg);
        
        PmPacket376 packet = new PmPacket376();
        packet.setValue(msg, 0);
        Map<String,Map<String,String>> resultMap = new HashMap<String,Map<String,String>>();
        this.converter.decodeData_TransMit(packet, resultMap);
        Iterator iterator1 = resultMap.keySet().iterator();
        while (iterator1.hasNext()) {
            String key = (String) iterator1.next();
            assertTrue(key.equals("96123456#0#8000C04F"));
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
     * Test of decodeDataDB method, of class Converter.
     */
 //   @Test
    public void testDecodeDataDB() {
        System.out.println("decodeDataDB");
        PmPacket376 packet = null;
        Dto postData = null;
        Converter instance = new Converter();
        instance.decodeDataDB(packet, postData);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}