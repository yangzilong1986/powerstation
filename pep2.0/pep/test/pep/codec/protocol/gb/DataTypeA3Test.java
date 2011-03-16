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
public class DataTypeA3Test {
    /**
     * Test of setArray method, of class DataTypeA3.
     */
    @Test
    public void testSetArray_byteArr() {
        DataTypeA3 a3 = new DataTypeA3(9999999);
        byte[] b3 = {(byte)0x99,(byte)0x99,(byte)0x99,(byte)0x09};
        assertTrue(TestUtils.byteArrayEquals(a3.getArray(),b3));
        
        DataTypeA3 a4 = new DataTypeA3(b3);
        assertTrue(TestUtils.byteArrayEquals(a4.getArray(),b3));
        assertEquals(a4.getValue(),9999999);
    }
}