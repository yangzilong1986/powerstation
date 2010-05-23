/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.bussinessprocess;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Thinkpad
 */
public class MainProcessTest {

    private static MainProcess processor;
    public MainProcessTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        processor = new MainProcess();
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
     * Test of run method, of class MainProcess.
     */
    @Test
    public void testRun() {
        processor.run();
    }

}