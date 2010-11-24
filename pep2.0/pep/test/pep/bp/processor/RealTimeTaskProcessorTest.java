/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.processor;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pep.mina.common.PepCommunicatorInterface;
import pep.mina.protocolcodec.gb.PepGbCommunicator;
import static org.junit.Assert.*;

/**
 *
 * @author Thinkpad
 */
public class RealTimeTaskProcessorTest {
    private static RealTimeSender processor;
    public RealTimeTaskProcessorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        processor = new RealTimeSender(new PepGbCommunicator());
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
     * Test of run method, of class RealTimeTaskProcessor.
     */
    @Test
    public void testRun() {
        processor.run();
    }

}