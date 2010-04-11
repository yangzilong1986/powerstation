/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.utils;

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
public class bcdutilsTest {

    public bcdutilsTest() {
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
     * Test of binArrayToString method, of class bcdutils.
     */
    @Test
    public void testBinArrayToString() {
        System.out.println("binArrayToString");
        byte[] bin = {(byte)0x12,(byte)0x34,(byte)0x1a,(byte)0xff};
        String expResult = "12341AFF";
        String result = BcdUtils.binArrayToString(bin);
        assertTrue(expResult.equals(result));
        }

    /**
     * Test of stringToByteArray method, of class bcdutils.
     */
    @Test
    public void testStringToByteArray() {
        System.out.println("stringToBinArray");
        String str = "12,34,56,1a,";
        byte[] expResult = {(byte)0x12,(byte)0x34,(byte)0x56,(byte)0x1A};
        byte[] result = BcdUtils.stringToByteArray(str);
        assertTrue(byteArrayEquals(expResult,result));

        str = "1,12,34,56,1a,";
        expResult = new byte[]{(byte)0x01,(byte)0x12,(byte)0x34,(byte)0x56,(byte)0x1A};
        result = BcdUtils.stringToByteArray(str);
        assertTrue(byteArrayEquals(expResult,result));

        str = "1,1234561a";
        expResult = new byte[]{(byte)0x01,(byte)0x12,(byte)0x34,(byte)0x56,(byte)0x1A};
        result = BcdUtils.stringToByteArray(str);
        assertTrue(byteArrayEquals(expResult,result));
    }



    private boolean byteArrayEquals(byte[] source, byte[] dest){
        boolean result;

        result = source.length==dest.length;
        for (int i=0; i<source.length; i++){
            if (source[i]!=dest[i])
                return false;
        }
        return result;
    }
}