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
public class DataTypeA2Test {

    /**
     * Test of setValue method, of class DataTypeA2.
     */
    @Test
    public void testSetValue() {
        DataTypeA2 a2 = new DataTypeA2(-999);
        byte[] b2 = {(byte)0x99,(byte)0x99};
        assertTrue(TestUtils.byteArrayEquals(a2.getArray(),b2));

        DataTypeA2 a3 = new DataTypeA2(999);
        byte[] b3 = {(byte)0x99,(byte)0x89};
        assertTrue(TestUtils.byteArrayEquals(a3.getArray(),b3));

        byte[] b4 = {(byte)0x99,(byte)0x89};
        DataTypeA2 a4 = new DataTypeA2(b4);
        assertTrue(TestUtils.byteArrayEquals(a4.getArray(),b4));
    }
}