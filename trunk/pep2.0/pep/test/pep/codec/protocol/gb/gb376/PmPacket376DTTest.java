/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb.gb376;

import pep.codec.utils.TestUtils;
import pep.codec.utils.BcdUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author luxiaochung
 */
public class PmPacket376DTTest {

    public PmPacket376DTTest() {
    }


    @Test
    public void testSetFn() {
        System.out.println("setFn");
        int fn = 88;
        PmPacket376DT instance = new PmPacket376DT();
        instance.setFn(fn);
        byte[] bs = instance.getValue();
        byte[] rslt = new byte[] {(byte)0x80,0x0A};
        System.out.println(BcdUtils.binArrayToString(bs));
        assertTrue(TestUtils.byteArrayEquals(bs,rslt));
        assertEquals(instance.getFn(),fn);
    }

}