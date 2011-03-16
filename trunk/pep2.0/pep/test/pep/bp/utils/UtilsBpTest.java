/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.utils;

import java.util.Date;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Thinkpad
 */
public class UtilsBpTest {

    public UtilsBpTest() {
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

    @Test
    public void testGetNow() {
        System.out.println("getNow");
        String expResult = "";
        String result = UtilsBp.getNow();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetYeasterday() {
        System.out.println("getYeasterday");
        String expResult = "";
        String result = UtilsBp.getYeasterday();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testDate2String() {
        System.out.println("Date2String");
        Date date = null;
        String expResult = "";
        String result = UtilsBp.Date2String(date);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testString2DateArray() throws Exception {
        System.out.println("String2DateArray");
        String dateStr = "2010-08-15 16:39:30";
        String format = "yyyy-MM-dd HH:mm:ss";
        byte[] expResult = null;
        byte[] result = UtilsBp.String2DateArray(dateStr, format);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testDateArray2String() {
        System.out.println("DateArray2String");
        byte[] date = null;
        String expResult = "";
        String result = UtilsBp.DateArray2String(date);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testTrimLeft() {
        System.out.println("trimLeft");
        String value = "";
        String expResult = "";
        String result = UtilsBp.trimLeft(value);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testTrimRight() {
        System.out.println("trimRight");
        String value = "";
        String expResult = "";
        String result = UtilsBp.trimRight(value);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testReplace() {
        System.out.println("replace");
        String mainString = "";
        String oldString = "";
        String newString = "";
        String expResult = "";
        String result = UtilsBp.replace(mainString, oldString, newString);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testStrAdd() {
        System.out.println("strAdd");
        String chr = "";
        int len = 0;
        String expResult = "";
        String result = UtilsBp.strAdd(chr, len);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testLPad() {
        System.out.println("lPad");
        String source = "";
        String chr = "";
        int len = 0;
        String expResult = "";
        String result = UtilsBp.lPad(source, chr, len);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testRPad() {
        System.out.println("rPad");
        String source = "";
        String chr = "";
        int len = 0;
        String expResult = "";
        String result = UtilsBp.rPad(source, chr, len);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testgetThisDay_YYYYMMDD(){
        String result = UtilsBp.getThisDay_YYYYMMDD();
        assertEquals("20101124", result);
    }

}