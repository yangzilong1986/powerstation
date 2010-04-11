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
import static org.junit.Assert.*;

/**
 *
 * @author luxiaochung
 */
public class ControlCodeTest {

    public ControlCodeTest() {
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
     * Test of getIsUpDirect method, of class ControlCode.
     */
    @Test
    public void testGetIsUpDirect() {
        System.out.println("getIsUpDirect");
        ControlCode instance = new ControlCode((byte)0xE1);
        boolean expResult = true;
        boolean result = instance.getIsUpDirect();
        assertEquals(expResult, result);
        assertEquals(instance.getFunctionKey(),(byte)1);
        assertTrue(instance.getIsUpDirect());
        assertTrue(instance.getIsOrgniger());
        assertTrue(instance.getUpDirectIsAppealCall());
        assertEquals(instance.getValue(),(byte)0xE1);

        instance.setIsUpDirect(false);
        assertTrue(!instance.getIsUpDirect());
        assertEquals(instance.getValue(),0x61);
        instance.setIsOrgniger(false);
        assertTrue(!instance.getIsOrgniger());
        assertEquals(instance.getValue(),0x21);
        instance.setDownDirectFrameCount((byte)0);
        assertEquals(instance.getDownDirectFrameCount(),(byte)0);
        assertEquals(instance.getValue(),0x01);
        instance.setIsDownDirectFrameCountAvaliable(true);
        assertTrue(instance.getIsDownDirectFrameCountAvaliable());
        assertEquals(instance.getValue(),0x11);
    }

}