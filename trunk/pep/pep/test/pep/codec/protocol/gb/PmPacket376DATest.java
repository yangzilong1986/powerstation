/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb;

import pep.codec.protocol.gb.gb376.PmPacket376DA;
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
public class PmPacket376DATest {

    public PmPacket376DATest() {
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

    @Test
    public void testPmPacket376DA() {
        PmPacket376DA da = new PmPacket376DA(0);
        assertEquals(da.getPn(),0);
        assertTrue(TestUtils.byteArrayEquals(da.getValue(),new byte[]{(byte)0,(byte)0}));
        da.setPn(9);
        assertEquals(da.getPn(),9);
        assertTrue(TestUtils.byteArrayEquals(da.getValue(),new byte[] {(byte)0x01,(byte)0x02}));
        da.setValue(new byte[]{0x02,0x02});
        assertEquals(da.getPn(),10);
    }
}