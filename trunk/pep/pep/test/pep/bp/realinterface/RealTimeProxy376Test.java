/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.realinterface;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pep.bp.db.RTTaskService;
import pep.bp.model.RealTimeTask;
import pep.bp.realinterface.mto.CollectObject;
import pep.bp.realinterface.mto.CommandItem;
import pep.bp.realinterface.mto.MTO_376;
import static org.junit.Assert.*;
import pep.bp.realinterface.mto.MessageTranObject;
import pep.codec.protocol.gb.gb376.PmPacket376;
import pep.codec.utils.BcdUtils;

/**
 *
 * @author Thinkpad
 */
public class RealTimeProxy376Test {

    private static RealTimeProxy376 proxy;
    private RTTaskService taskService;
    public RealTimeProxy376Test() {
        ApplicationContext cxt = new ClassPathXmlApplicationContext("beans.xml");
        taskService = (RTTaskService) cxt.getBean("taskService");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        proxy = new RealTimeProxy376();
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
     * Test of writeEquipmentParameters method, of class RealTimeProxy376.
     */
    @Test
    public void testWriteEquipmentParameters() throws Exception {
        MTO_376 MTO = new MTO_376();
        CollectObject object = new CollectObject();
        CommandItem citem = new CommandItem();
        Map datacellParams = new TreeMap();
        datacellParams.put("1004000101", "10");//终端数传机延时时间RTS
        datacellParams.put("1004000102", "20");//终端作为启动站允许发送传输延时时间
        datacellParams.put("1004000103", "30");//终端等待从动站响应的超时时间
        datacellParams.put("1004000104", "3");//终端等待从动站响应的重发次数
        datacellParams.put("1004000106", "11100000");//需要主站确认的通信服务（CON=1）的标志
        datacellParams.put("1004000107", "15");//心跳周期
        citem.setDatacellParam(datacellParams);

        citem.setIdentifier("10040001");//终端上行通信口通信参数设置
        object.AddCommandItem(citem);
        object.setLogicalAddr("96123456");
        object.setEquipProtocol("01");
        object.setMpSn(new int[]{0});

        MTO.getCollectObjects().add(object);
        RealTimeProxy376 instance = new RealTimeProxy376();
        long SequenceCode = instance.writeEquipmentParameters(MTO);
        RealTimeTask task = taskService.getTask(SequenceCode);
        PmPacket376 packet = new PmPacket376();
        packet.setValue(BcdUtils.stringToByteArray(task.getSendmsg()),0);
        assertTrue(packet.getAfn()==4);
        byte[] dateItems = packet.getDataBuffer().getValue();
        assertTrue(dateItems[4]==10);
        assertTrue(dateItems[5]==20);
        assertTrue(dateItems[6]==30);
        assertTrue(dateItems[7]==3);


    }

    /**
     * Test of readEquipmentParameters method, of class RealTimeProxy376.
     */
    @Test
    public void testReadEquipmentParameters() throws Exception {
        System.out.println("readEquipmentParameters");
        MessageTranObject MTO = null;
        RealTimeProxy376 instance = new RealTimeProxy376();
        long expResult = 0L;
        long result = instance.readEquipmentParameters(MTO);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeResetCommands method, of class RealTimeProxy376.
     */
    @Test
    public void testWriteResetCommands() throws Exception {
        System.out.println("writeResetCommands");
        MessageTranObject MTO = null;
        RealTimeProxy376 instance = new RealTimeProxy376();
        long expResult = 0L;
        long result = instance.writeResetCommands(MTO);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeControlCommands method, of class RealTimeProxy376.
     */
    @Test
    public void testWriteControlCommands() throws Exception {
        System.out.println("writeControlCommands");
        MessageTranObject MTO = null;
        RealTimeProxy376 instance = new RealTimeProxy376();
        long expResult = 0L;
        long result = instance.writeControlCommands(MTO);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readRealtimeData method, of class RealTimeProxy376.
     */
    @Test
    public void testReadRealtimeData() throws Exception {
        System.out.println("readRealtimeData");
        MessageTranObject MTO = null;
        RealTimeProxy376 instance = new RealTimeProxy376();
        long expResult = 0L;
        long result = instance.readRealtimeData(MTO);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of transmitMsg method, of class RealTimeProxy376.
     */
    @Test
    public void testTransmitMsg() throws Exception {
        System.out.println("transmitMsg");
        MessageTranObject MTO = null;
        RealTimeProxy376 instance = new RealTimeProxy376();
        long expResult = 0L;
        long result = instance.transmitMsg(MTO);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReturnByWEP method, of class RealTimeProxy376.
     */
    @Test
    public void testGetReturnByWEP() throws Exception {
        System.out.println("getReturnByWEP");
        long appId = 0L;
        RealTimeProxy376 instance = new RealTimeProxy376();
        Map expResult = null;
        Map result = instance.getReturnByWEP(appId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReturnByWEP_Json method, of class RealTimeProxy376.
     */
    @Test
    public void testGetReturnByWEP_Json() throws Exception {
        System.out.println("getReturnByWEP_Json");
        long appId = 0L;
        RealTimeProxy376 instance = new RealTimeProxy376();
        String expResult = "";
        String result = instance.getReturnByWEP_Json(appId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReturnByREP method, of class RealTimeProxy376.
     */
    @Test
    public void testGetReturnByREP() throws Exception {
        System.out.println("getReturnByREP");
        long appId = 0L;
        RealTimeProxy376 instance = new RealTimeProxy376();
        Map expResult = null;
        Map result = instance.getReturnByREP(appId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReturnByREP_Json method, of class RealTimeProxy376.
     */
    @Test
    public void testGetReturnByREP_Json() throws Exception {
        System.out.println("getReturnByREP_Json");
        long appId = 0L;
        RealTimeProxy376 instance = new RealTimeProxy376();
        String expResult = "";
        String result = instance.getReturnByREP_Json(appId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReturnByWRC method, of class RealTimeProxy376.
     */
    @Test
    public void testGetReturnByWRC() throws Exception {
        System.out.println("getReturnByWRC");
        long appId = 0L;
        RealTimeProxy376 instance = new RealTimeProxy376();
        Map expResult = null;
        Map result = instance.getReturnByWRC(appId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReturnByWCC method, of class RealTimeProxy376.
     */
    @Test
    public void testGetReturnByWCC() throws Exception {
        System.out.println("getReturnByWCC");
        long appId = 0L;
        RealTimeProxy376 instance = new RealTimeProxy376();
        Map expResult = null;
        Map result = instance.getReturnByWCC(appId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReturnByRRD method, of class RealTimeProxy376.
     */
    @Test
    public void testGetReturnByRRD() throws Exception {
        System.out.println("getReturnByRRD");
        long appId = 0L;
        RealTimeProxy376 instance = new RealTimeProxy376();
        Map expResult = null;
        Map result = instance.getReturnByRRD(appId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}