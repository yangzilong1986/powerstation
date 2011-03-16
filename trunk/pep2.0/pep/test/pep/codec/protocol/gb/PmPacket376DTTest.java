/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb;

import pep.codec.protocol.gb.gb376.PmPacket376DT;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pep.codec.utils.TestUtils;
import static org.junit.Assert.*;

/**
 *
 * @author luxiaochung
 */
public class PmPacket376DTTest {

    public PmPacket376DTTest() {
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
     * Test of setValue method, of class PmPacket376DT.
     */
    @Test
    public void testPmPacket376DT() {
        PmPacket376DT dt = new PmPacket376DT(1);
        assertEquals(dt.getFn(),1);
        assertTrue(TestUtils.byteArrayEquals(dt.getValue(),new byte[]{1,0}));
        dt.setFn(9);
        assertEquals(dt.getFn(),9);
        assertTrue(TestUtils.byteArrayEquals(dt.getValue(),new byte[] {(byte)0x01,(byte)0x01}));
        dt.setValue(new byte[]{0x04,0x02});
        assertEquals(dt.getFn(),19);
    }
}