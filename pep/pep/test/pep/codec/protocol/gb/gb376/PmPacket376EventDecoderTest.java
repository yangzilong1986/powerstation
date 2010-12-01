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
        String msg ="687200720068C410101300000E7000000100030000010E0A14102911105109291110A416";
        byte[] msgBytes = BcdUtils.stringToByteArray(msg);
        int head = PmPacket376.getMsgHeadOffset(msgBytes, 0);
        PmPacket376 pack = new PmPacket376();
        pack.setValue(msgBytes,head);
        pack.getDataBuffer().rewind();
        System.out.println(pack.toString());
        List<PmPacket376EventBase> events = PmPacket376EventDecoder.decode(new BcdDataBuffer(pack.getDataBuffer().getRowIoBuffer()));

    }

}