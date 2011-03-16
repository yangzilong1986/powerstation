/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.meter645;

import pep.codec.utils.TestUtils;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author luxiaochung
 */
public class Gb645MeterPacketTest {

    public Gb645MeterPacketTest() {
    }

    @Test
    public void testGetMsgHeadOffset() {
        byte[] databuff = {(byte)0x11,(byte)0x68,(byte)0x68,(byte)0x12,(byte)0x34,(byte)0x56,(byte)0x78,(byte)0x90,(byte)0x12,(byte)0x68,(byte)0x01,(byte)0x01,(byte)0x00,(byte)0x88,(byte)0x16,(byte)0x16};
        int head = Gb645MeterPacket.getMsgHeadOffset(databuff, 0);

        Gb645MeterPacket pack = Gb645MeterPacket.getPacket(databuff, head);

        assertEquals(head,2);
        String meteraddr = pack.getAddress().getAddress();
        assertTrue(meteraddr.equals("129078563412"));
        byte ctrl = pack.getControlCode().getValue();
        assertEquals(ctrl,1);
        byte[] data = pack.getData().getValue();
        byte[] fdata = {(byte)0xcd};
        assertTrue(TestUtils.byteArrayEquals(fdata, data));

        byte[] value = pack.getValue();
        final byte[] fvalue = {(byte)0x68,(byte)0x12,(byte)0x34,(byte)0x56,(byte)0x78,(byte)0x90,(byte)0x12,(byte)0x68,(byte)0x01,(byte)0x01,(byte)0x00,(byte)0x88,(byte)0x16};
        assertTrue(TestUtils.byteArrayEquals(fvalue, value));
    }

}