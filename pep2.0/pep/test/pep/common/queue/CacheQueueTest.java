/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.common.queue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pep.common.message.IMessage;
import pep.common.message.MessageLoader;

/**
 *
 * @author Thinkpad
 */
public class CacheQueueTest {

    public CacheQueueTest() {
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
     * Test of offer method, of class CacheQueue.
     */
    @Test
    public void testOffer() {
        System.out.println("offer");
        IMessage message = null;
        CacheQueue instance = new CacheQueue();
        instance.offer(message);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of poll method, of class CacheQueue.
     */
    @Test
    public void testPoll() {
        System.out.println("poll");
        CacheQueue instance = new CacheQueue();
        IMessage expResult = null;
        IMessage result = instance.poll();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of peek method, of class CacheQueue.
     */
    @Test
    public void testPeek() {
        System.out.println("peek");
        CacheQueue instance = new CacheQueue();
        IMessage expResult = null;
        IMessage result = instance.peek();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of take method, of class CacheQueue.
     */
    @Test
    public void testTake() {
        System.out.println("take");
        CacheQueue instance = new CacheQueue();
        IMessage expResult = null;
        IMessage result = instance.take();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of size method, of class CacheQueue.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        CacheQueue instance = new CacheQueue();
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dispose method, of class CacheQueue.
     */
    @Test
    public void testDispose() {
        System.out.println("dispose");
        CacheQueue instance = new CacheQueue();
        instance.dispose();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of asyncSaveQueue method, of class CacheQueue.
     */
    @Test
    public void testAsyncSaveQueue() {
        System.out.println("asyncSaveQueue");
        CacheQueue instance = new CacheQueue();
        instance.asyncSaveQueue();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of _findReadCacheFileName method, of class CacheQueue.
     */
    @Test
    public void test_findReadCacheFileName() {
        System.out.println("_findReadCacheFileName");
        CacheQueue instance = new CacheQueue();
        String expResult = "";
        String result = instance._findReadCacheFileName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMaxSize method, of class CacheQueue.
     */
    @Test
    public void testGetMaxSize() {
        System.out.println("getMaxSize");
        CacheQueue instance = new CacheQueue();
        int expResult = 0;
        int result = instance.getMaxSize();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMaxSize method, of class CacheQueue.
     */
    @Test
    public void testSetMaxSize() {
        System.out.println("setMaxSize");
        int maxSize = 0;
        CacheQueue instance = new CacheQueue();
        instance.setMaxSize(maxSize);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMinSize method, of class CacheQueue.
     */
    @Test
    public void testGetMinSize() {
        System.out.println("getMinSize");
        CacheQueue instance = new CacheQueue();
        int expResult = 0;
        int result = instance.getMinSize();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMinSize method, of class CacheQueue.
     */
    @Test
    public void testSetMinSize() {
        System.out.println("setMinSize");
        int minSize = 0;
        CacheQueue instance = new CacheQueue();
        instance.setMinSize(minSize);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMaxFileSize method, of class CacheQueue.
     */
    @Test
    public void testGetMaxFileSize() {
        System.out.println("getMaxFileSize");
        CacheQueue instance = new CacheQueue();
        int expResult = 0;
        int result = instance.getMaxFileSize();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMaxFileSize method, of class CacheQueue.
     */
    @Test
    public void testSetMaxFileSize() {
        System.out.println("setMaxFileSize");
        int maxFileSize = 0;
        CacheQueue instance = new CacheQueue();
        instance.setMaxFileSize(maxFileSize);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getKey method, of class CacheQueue.
     */
    @Test
    public void testGetKey() {
        System.out.println("getKey");
        CacheQueue instance = new CacheQueue();
        String expResult = "";
        String result = instance.getKey();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setKey method, of class CacheQueue.
     */
    @Test
    public void testSetKey() {
        System.out.println("setKey");
        String key = "";
        CacheQueue instance = new CacheQueue();
        instance.setKey(key);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMessageLoader method, of class CacheQueue.
     */
    @Test
    public void testGetMessageLoader() {
        System.out.println("getMessageLoader");
        CacheQueue instance = new CacheQueue();
        MessageLoader expResult = null;
        MessageLoader result = instance.getMessageLoader();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMessageLoader method, of class CacheQueue.
     */
    @Test
    public void testSetMessageLoader() {
        System.out.println("setMessageLoader");
        MessageLoader messageLoader = null;
        CacheQueue instance = new CacheQueue();
        instance.setMessageLoader(messageLoader);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCachePath method, of class CacheQueue.
     */
    @Test
    public void testSetCachePath() {
        System.out.println("setCachePath");
        String cachePath = "";
        CacheQueue instance = new CacheQueue();
        instance.setCachePath(cachePath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setFileCount method, of class CacheQueue.
     */
    @Test
    public void testSetFileCount() {
        System.out.println("setFileCount");
        int fileCount = 0;
        CacheQueue instance = new CacheQueue();
        instance.setFileCount(fileCount);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}