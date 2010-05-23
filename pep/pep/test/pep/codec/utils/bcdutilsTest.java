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
import java.util.Date;
import java.util.GregorianCalendar;

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
        assertTrue(TestUtils.byteArrayEquals(expResult,result));

        str = "1,12,34,56,1a,";
        expResult = new byte[]{(byte)0x01,(byte)0x12,(byte)0x34,(byte)0x56,(byte)0x1A};
        result = BcdUtils.stringToByteArray(str);
        assertTrue(TestUtils.byteArrayEquals(expResult,result));

        str = "1,1234561a";
        expResult = new byte[]{(byte)0x01,(byte)0x12,(byte)0x34,(byte)0x56,(byte)0x1A};
        result = BcdUtils.stringToByteArray(str);
        assertTrue(TestUtils.byteArrayEquals(expResult,result));

        String Str = "011234561A";
        assertEquals(BcdUtils.binArrayToString(expResult),Str);
    }

    @Test
    public void testBcdToInt(){
        assertEquals(BcdUtils.bcdToInt((byte)0x11),11);
        assertEquals(BcdUtils.intToBcd((byte)16),0x16);
        byte[] bytes = {0x16,0x00};
        assertEquals(BcdUtils.bcdToInt(bytes, 0, 2),16);
        assertTrue(TestUtils.byteArrayEquals(BcdUtils.intTobcd(16, 2), bytes));
    }

    @Test
    public void testBcdToDate(){
        byte[] bcds = {0x10,0x01,0x01,0x01,0x59,0x50};
        Date result = BcdUtils.bcdToDate(bcds, "YYMMDDHHMISS",0);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(GregorianCalendar.YEAR, 2010);
        calendar.set(GregorianCalendar.MONTH, 0);
        calendar.set(GregorianCalendar.DAY_OF_MONTH, 1);
        calendar.set(GregorianCalendar.HOUR_OF_DAY, 1);
        calendar.set(GregorianCalendar.MINUTE, 59);
        calendar.set(GregorianCalendar.SECOND, 50);
        calendar.set(GregorianCalendar.MILLISECOND, 0);
        assertEquals(calendar.getTime(),result);

        byte[] bcsdate = BcdUtils.dateToBcd(result, "YYMMDDHHMISS");
        assertTrue(TestUtils.byteArrayEquals(bcsdate,bcds));
    }

     @Test
    public void testbyteToString(){
        byte value = 5;
        String result = BcdUtils.byteToString(value);
        assertEquals(result,"05");
    }
}