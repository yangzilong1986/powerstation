/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pep.codec.utils.BcdUtils;
import pep.codec.utils.TestUtils;
import static org.junit.Assert.*;

/**
 *
 * @author luxiaochung
 */
public class PmPacketTest {

    public PmPacketTest() {
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
     * Test of getValue method, of class PmPacket.
     */
    @Test
    public void testGetValue() {
        System.out.println("getValue");
        PmPacket376 instance = new PmPacket376();
        byte[] expResult = BcdUtils.stringToByteArray("68, 42, 00, 42, 00, 68, 4B, 74, 05, 09, 00, 02, 0A, 6E, 00, 00, 02, 01, 01, 00, 16, 00, 61, 16");
        instance.getControlCode().setIsUpDirect(false).setIsOrgniger(true).setIsDownDirectFrameCountAvaliable(false);
        instance.getControlCode().setFunctionKey((byte)11);
        instance.getAddress().setRtua("05740009").setIsGroupAddress(false).setMastStationId((byte)1);
        instance.setAfn((byte)0x0A);
        instance.getSeq().setIsTpvAvalibe(false).setIsFirstFrame(true).setIsFinishFrame(true).setIsNeedCountersign(false);
        instance.getSeq().setSeq((byte)14);
        instance.setData(BcdUtils.stringToByteArray("00, 00, 02, 01, 01, 00, 16, 00"));
        byte[] result = instance.getValue();
        System.out.println(BcdUtils.binArrayToString(expResult));
        System.out.println(BcdUtils.binArrayToString(result));
        assertTrue(TestUtils.byteArrayEquals(expResult, result));
   }

    @Test
    public void testGetPacket(){
        byte[] msg = BcdUtils.stringToByteArray("68, 42, 00, 42, 00, 68, 68, 42, 00, 42, 00, 68, 4B, 74, 05, 09, 00, 02, 0A, 6E, 00, 00, 02, 01, 01, 00, 16, 00, 61, 16");
        int head = PmPacket376.getMsgHeadOffset(msg,0);
        assertEquals(head,6);
        PmPacket376 pack = new PmPacket376();
        pack.setValue(msg,0);
        assertTrue(!pack.getControlCode().getIsUpDirect());
        assertTrue(pack.getControlCode().getIsOrgniger());
        assertTrue(!pack.getControlCode().getIsDownDirectFrameCountAvaliable());
        assertEquals(pack.getControlCode().getFunctionKey(),11);
        assertEquals(pack.getAddress().getRtua(),"05740009");
        assertTrue(!pack.getAddress().getIsGoupAddress());
        assertEquals(pack.getAddress().getMastStationId(),1);

        //instance.setProtocolVersion((byte)2);
        assertEquals(pack.getAfn(),0x0a);
        assertTrue(!pack.getSeq().getIsTpvAvalibe());
        assertTrue(pack.getSeq().getIsFirstFrame());
        assertTrue(pack.getSeq().getIsFinishFrame());
        assertTrue(!pack.getSeq().getIsNeedCountersign());
        assertEquals(pack.getSeq().getSeq(),14);
        assertTrue(TestUtils.byteArrayEquals(pack.getData(),BcdUtils.stringToByteArray("00, 00, 02, 01, 01, 00, 16, 00")));

    }

}