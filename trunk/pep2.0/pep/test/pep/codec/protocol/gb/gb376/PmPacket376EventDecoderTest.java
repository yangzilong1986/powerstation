/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb.gb376;

import pep.codec.utils.BcdDataBuffer;
import java.util.List;
import pep.codec.utils.BcdUtils;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author luxiaochung
 */
public class PmPacket376EventDecoderTest {

    @Test
    public void testSomeMethod() {
        String msg ="68A600A60068C412965234000E7B000001000C0000012417322131081100016680950001008CFFFF493221033108115116";
        //String msg ="68, 82, 00, 82, 00, 68, C4, 12, 96, 56, 34, 00, 0E, 75, 00, 00, 01, 00, 6E, 00, 00, 01, 18, 0E, 09, 22, 07, 12, 10, 01, 00, 00, 90, 21, 00, 22, 00, 22, 59, 16";
        byte[] msgBytes = BcdUtils.stringToByteArray(msg);
        int head = PmPacket376.getMsgHeadOffset(msgBytes, 0);
        PmPacket376 pack = new PmPacket376();
        pack.setValue(msgBytes,head);
        pack.getDataBuffer().rewind();
        System.out.println(pack.toString());
        List<PmPacket376EventBase> events = PmPacket376EventDecoder.decode(new BcdDataBuffer(pack.getDataBuffer().getRowIoBuffer()));

    }

}