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
import pep.codec.utils.TestUtils;
import static org.junit.Assert.*;

/**
 *
 * @author luxiaochung
 */
public class AddressTest {

    public AddressTest() {
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
     * Test of makeBroadcastAddress method, of class Address.
     */
    @Test
    public void testAddress() {
        System.out.println("Address");

        Address addr = new Address();
        addr.setRtua("33010001");
        addr.setMastStationId((byte)1);
        addr.setIsGroupAddress(false);
        assertEquals(addr.getRtua(), "33010001");
        assertTrue(!addr.getIsGoupAddress());
        assertTrue(addr.getMastStationId()==(byte)1);
        byte bs[] = {(byte)0x01,(byte)0x33,(byte)0x01,(byte)0x00,(byte)0x02};
        assertTrue(TestUtils.byteArrayEquals(addr.getValue(),bs));
        addr.setIsGroupAddress(true);
        assertTrue(addr.getIsGoupAddress());
        assertTrue(addr.getMastStationId()==(byte)1);

        addr = Address.makeBroadcastAddress("33010001",(byte)1);
        assertEquals(addr.getRtua(),"3301FFFF");
        assertTrue(addr.getIsGoupAddress());
        assertEquals(addr.getMastStationId(),(byte)1);

        addr = Address.makeGroupAddress("33010001", (byte)1);
        assertEquals(addr.getRtua(),"33010001");
        assertTrue(addr.getIsGoupAddress());
        assertEquals(addr.getMastStationId(),(byte)1);

    }
}