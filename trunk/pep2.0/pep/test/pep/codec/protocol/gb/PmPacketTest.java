/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.codec.protocol.gb;

import pep.codec.protocol.gb.gb376.PmPacket376;
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

    private int byteToUnsigned(byte b) {
        if (b >= 0) {
            return b;
        } else {
            return 256 + b;
        }
    }

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
        PmPacket instance = new PmPacket376();
        byte[] expResult = BcdUtils.stringToByteArray("68, 42, 00, 42, 00, 68, 4B, 74, 05, 09, 00, 02, 0A, 6E, 00, 00, 02, 01, 01, 00, 16, 00, 61, 16");
        instance.getControlCode().setIsUpDirect(false).setIsOrgniger(true).setIsDownDirectFrameCountAvaliable(false);
        instance.getControlCode().setFunctionKey((byte) 11);
        instance.getAddress().setRtua("05740009").setIsGroupAddress(false).setMastStationId((byte) 1);
        instance.setAfn((byte) 0x0A);
        instance.getSeq().setIsTpvAvalibe(false).setIsFirstFrame(true).setIsFinishFrame(true).setIsNeedCountersign(false);
        instance.getSeq().setSeq((byte) 14);
        instance.getDataBuffer().setValue(BcdUtils.stringToByteArray("00, 00, 02, 01, 01, 00, 16, 00"));
        byte[] result = instance.getValue();
        System.out.println(BcdUtils.binArrayToString(expResult));
        System.out.println(BcdUtils.binArrayToString(result));
        assertTrue(TestUtils.byteArrayEquals(expResult, result));
    }

    @Test
    public void testGetPacket() {
        byte[] msg = BcdUtils.stringToByteArray("68, 42, 00, 42, 00, 68, 68, 42, 00, 42, 00, 68, 4B, 74, 05, 09, 00, 02, 0A, 6E, 00, 00, 02, 01, 01, 00, 16, 00, 61, 16"
                + "68, 42, 00, 42, 00, 68, 68, 42, 00, 42, 00, 68, 4B, 74, 05, 09, 01, 02, 0A, 6E, 00, 00, 02, 01, 01, 00, 16, 00, 62, 16");
        int head = PmPacket376.getMsgHeadOffset(msg, 0);
        assertEquals(head, 6);
        PmPacket pack = new PmPacket376();
        pack.setValue(msg, 0);
        assertTrue(!pack.getControlCode().getIsUpDirect());
        assertTrue(pack.getControlCode().getIsOrgniger());
        assertTrue(!pack.getControlCode().getIsDownDirectFrameCountAvaliable());
        assertEquals(pack.getControlCode().getFunctionKey(), 11);
        assertEquals(pack.getAddress().getRtua(), "05740009");
        assertTrue(!pack.getAddress().getIsGoupAddress());
        assertEquals(pack.getAddress().getMastStationId(), 1);
        assertEquals(pack.getAfn(), 0x0a);
        assertTrue(!pack.getSeq().getIsTpvAvalibe());
        assertTrue(pack.getSeq().getIsFirstFrame());
        assertTrue(pack.getSeq().getIsFinishFrame());
        assertTrue(!pack.getSeq().getIsNeedCountersign());
        assertEquals(pack.getSeq().getSeq(), 14);
        assertTrue(TestUtils.byteArrayEquals(pack.getDataBuffer().getValue(), BcdUtils.stringToByteArray("00, 00, 02, 01, 01, 00, 16, 00")));

        PmPacket pack2 = new PmPacket376();
        pack2.setValue(msg, head + pack.length());
        assertTrue(!pack2.getControlCode().getIsUpDirect());
        assertTrue(pack2.getControlCode().getIsOrgniger());
        assertTrue(!pack2.getControlCode().getIsDownDirectFrameCountAvaliable());
        assertEquals(pack2.getControlCode().getFunctionKey(), 11);
        assertEquals(pack2.getAddress().getRtua(), "05740109");
        assertTrue(!pack2.getAddress().getIsGoupAddress());
        assertEquals(pack2.getAddress().getMastStationId(), 1);
        assertEquals(pack2.getAfn(), 0x0a);
        assertTrue(!pack2.getSeq().getIsTpvAvalibe());
        assertTrue(pack2.getSeq().getIsFirstFrame());
        assertTrue(pack2.getSeq().getIsFinishFrame());
        assertTrue(!pack2.getSeq().getIsNeedCountersign());
        assertEquals(pack2.getSeq().getSeq(), 14);
        assertTrue(TestUtils.byteArrayEquals(pack2.getDataBuffer().getValue(), BcdUtils.stringToByteArray("00, 00, 02, 01, 01, 00, 16, 00")));
    }

    @Test
    public void testGetPacket2(){
        byte[] msg = BcdUtils.stringToByteArray("55 68 32 01 32 01 68 98 12 96 56 34 00 0C 62 00 00 01 03 11 19 25 07 10 FE FE FE FE FE FE FE"+
                "FE FE FE FE FE 00 00 00 00 00 00 00 00 00 00 00 00 00 10 00 10 00 10 00 10 00 00 00 00 90 21 00 00");
           int head = PmPacket376.getMsgHeadOffset(msg, 0);
        assertEquals(head, -1);

    }

    @Test
    public void testByteToUnsigned() {
        byte b;
        for (int a = 0x79; a <= 0xFF; a++) {
            b = (byte) a;
            int c = byteToUnsigned(b);
            assertEquals(a, c);
        }
    }

     @Test
    public void testToString() {
        PmPacket376 packet = new PmPacket376();
        String packStr1 = packet.getDataBuffer().toString();
            packet.setAfn((byte)4);//AFN
            packet.getAddress().setRtua("96123456"); //逻辑地址
            packet.getControlCode().setIsUpDirect(false);
            packet.getControlCode().setIsOrgniger(true);
            packet.getControlCode().setFunctionKey((byte)1);
            packet.getControlCode().setIsDownDirectFrameCountAvaliable(true);
            packet.getControlCode().setDownDirectFrameCount((byte) 0);
            packet.getSeq().setIsTpvAvalibe(true);
        
        String packStr2 = packet.getDataBuffer().toString();
        assertEquals(packStr1, packStr2);
        System.out.println("packStr1:"+packStr1);
        System.out.println("packStr2:"+packStr2);
    }

     @Test
    public void testPmPacketDataToString() {
        PmPacketData databuf = new PmPacketData();
        databuf.put(new byte[] {(byte)0x01,(byte)0x02,(byte)0xf1,(byte)0x80,(byte)0x7f});
        String packStr1 = databuf.toString();

        String packStr2 = databuf.toString();
        assertEquals(packStr1, packStr2);
        System.out.println("packStr1:"+packStr1);
        System.out.println("packStr2:"+packStr2);
    }

}
