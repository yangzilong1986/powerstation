/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.utils;

import pep.codec.protocol.gb.PmPacketData;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author luxiaochung
 */
public class BcdDataBufferTest {

    public BcdDataBufferTest() {
    }

    @Test
    public void testSomeMethod() {
         BcdDataBuffer buff = new BcdDataBuffer();
         buff.rewind();
         buff.putByte((byte)1);
         buff.putByte((byte)0xfe);
         byte[] bs1 = buff.getValue();
         PmPacketData pd = new PmPacketData(buff.getRowIoBuffer());
         byte[] bs2 = pd.getValue();
         System.out.println(BcdUtils.binArrayToString(bs1));
         System.out.println(BcdUtils.binArrayToString(bs2));
         assertTrue(TestUtils.byteArrayEquals(bs1, bs2));
         
         pd.put((byte)0xaa);
         buff.putByte((byte)0xbb);
         System.out.println(BcdUtils.binArrayToString(pd.getValue()));
         assertTrue(TestUtils.byteArrayEquals(buff.getValue(),pd.getValue()));
    }

    @Test
    public void testGetBits(){
         BcdDataBuffer buff = new BcdDataBuffer();
         buff.rewind();
         buff.putByte((byte)1);
         buff.putByte((byte)11);
         buff.putByte((byte)9);
         buff.rewind();
         int firstByte = buff.getByte();
         long bits3 = buff.getBits(3);
         long bits4 = buff.getBits(1);
         int thirdByte = buff.getByte();
         assertEquals(firstByte,1);
         assertEquals(bits3,3L);
         assertEquals(thirdByte,9);
         assertEquals(bits4,1);
         
         buff.rewind();
         assertEquals(buff.getByte(),1);
         assertEquals(buff.getBits(3),3);
         assertEquals(buff.getBits(5),1);
         
         buff.rewind();
         assertEquals(buff.getByte(),1);
         assertEquals(buff.getBits(3),3);
         assertEquals(buff.getBits(9),0x0121);
         assertEquals(buff.getBits(4),0);
    }
}