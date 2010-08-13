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
        //String msg = "68, A6, 00, A6, 00, 68, C4, 12, 96, 56, 34, 00, 0E, 71, 00, 00, 01, "+
        //        "00, 03, 00, 00, 01, 24, 17, 18, 14, 02, 08, 10, 00, 01, 01, 00, 00, 00,"+
        //        " 00, 00, 88, FF, FF, 54, 17, 14, 01, 02, 08, 10, 1D, 16";
        String msg ="68A600A60068C400000000000E7700000100B900000124173818030810000100000000000000FFFF263818020308103C16";
        byte[] msgBytes = BcdUtils.stringToByteArray(msg);
        int head = PmPacket376.getMsgHeadOffset(msgBytes, 0);
        PmPacket376 pack = new PmPacket376();
        pack.setValue(msgBytes,head);
        pack.getDataBuffer().rewind();
        List<PmPacket376EventBase> events = PmPacket376EventDecoder.decode(new BcdDataBuffer(pack.getDataBuffer().getRowIoBuffer()));
    }

}