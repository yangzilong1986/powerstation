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
import pep.bp.model.RTTaskRecv;
import pep.bp.model.RealTimeTask;
import pep.bp.realinterface.mto.CircleDataItems;
import pep.bp.realinterface.mto.CollectObject;
import pep.bp.realinterface.mto.CommandItem;
import pep.bp.realinterface.mto.DataItem;
import pep.bp.realinterface.mto.DataItemGroup;
import pep.bp.realinterface.mto.MTO_376;
import static org.junit.Assert.*;
import pep.bp.realinterface.mto.MessageTranObject;
import pep.codec.protocol.gb.PmPacketData;
import pep.codec.protocol.gb.TimeProtectValue;
import pep.codec.protocol.gb.gb376.PmPacket376;
import pep.codec.protocol.gb.gb376.PmPacket376DA;
import pep.codec.protocol.gb.gb376.PmPacket376DT;
import pep.codec.utils.BcdUtils;

/**
 *
 * @author Thinkpad
 */
public class RealTimeProxy376Test {

    private static RealTimeProxy376 proxy;
    private RTTaskService taskService;

    private MTO_376 PutInCommandItem(Map datacellParams,CircleDataItems circleDataItems, String commandItemCode,String LogicAddress){
         MTO_376 MTO = new MTO_376();
        CollectObject object = new CollectObject();
        CommandItem citem = new CommandItem();
        if(datacellParams != null)
            citem.setDatacellParam(datacellParams);
        if(circleDataItems != null){
            citem.setCircleDataItems(circleDataItems);
            citem.setCircleLevel(1);
        }

        citem.setIdentifier(commandItemCode);//终端上行通信口通信参数设置
        object.AddCommandItem(citem);
        object.setLogicalAddr(LogicAddress);
        object.setEquipProtocol("01");
        object.setMpSn(new int[]{0});

        MTO.getCollectObjects().add(object);

        return MTO;
    }

    private Map<String, String> getTestResults_WEP(MTO_376 MTO,String commandItemCode) throws Exception{
        RealTimeProxy376 instance = new RealTimeProxy376();
        long SequenceCode = instance.writeEquipmentParameters(MTO);
        RealTimeTask task = taskService.getTask(SequenceCode);
        PmPacket376 packet = new PmPacket376();
        packet.setValue(BcdUtils.stringToByteArray(task.getSendmsg()),0);
        assertTrue(packet.getAfn()==4);
        PmPacketData databuf = packet.getDataBuffer();
        databuf.rewind();
        return instance.decodeData(databuf,commandItemCode);
    }


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
       //测试F1

        Map datacellParams1 = new TreeMap();
        datacellParams1.put("1004000101", "10");//终端数传机延时时间RTS
        datacellParams1.put("1004000102", "20");//终端作为启动站允许发送传输延时时间
        datacellParams1.put("1004000103", "30");//终端等待从动站响应的超时时间
        datacellParams1.put("1004000104", "3");//终端等待从动站响应的重发次数
        datacellParams1.put("1004000106", "11100000");//需要主站确认的通信服务（CON=1）的标志
        datacellParams1.put("1004000107", "15");//心跳周期
        MTO_376 MTO1  = PutInCommandItem(datacellParams1,null,"10040001","96123456");

        Map<String,String> resultMap1 = getTestResults_WEP(MTO1,"10040001");
        assertTrue(resultMap1.get("1004000101").equals("10"));
        assertTrue(resultMap1.get("1004000102").equals("20"));
        assertTrue(resultMap1.get("1004000103").equals("30"));
        assertTrue(resultMap1.get("1004000104").equals("3"));
        assertTrue(resultMap1.get("1004000106").equals("11100000"));
        assertTrue(resultMap1.get("1004000107").equals("15"));

        //测试F3
        Map datacellParams3 = new TreeMap();
        datacellParams3.put("1004000301", "192.168.0.1:8080");//主用IP地址和端口
        datacellParams3.put("1004000302", "192.168.0.2:8080");//备用IP地址和端口
        datacellParams3.put("1004000303", "ZJDL.ZJ");//APN
        MTO_376 MTO3  = PutInCommandItem(datacellParams3,null,"10040003","96123456");

        Map<String,String> resultMap3 = getTestResults_WEP(MTO3,"10040003");
        assertTrue(resultMap3.get("1004000301").equals("192.168.0.1:8080"));
        assertTrue(resultMap3.get("1004000302").equals("192.168.0.2:8080"));
        assertTrue(resultMap3.get("1004000303").equals("ZJDL.ZJ"));

        //测试F4
        Map datacellParams4 = new TreeMap();
        datacellParams4.put("1004000401", "13675834792");//主站电话号码或主站手机号码
        datacellParams4.put("1004000402", "8613010360500");//短信中心号码
        MTO_376 MTO4  = PutInCommandItem(datacellParams4,null,"10040004","96123456");

        Map<String,String> resultMap4 = getTestResults_WEP(MTO4,"10040004");
        assertTrue(resultMap4.get("1004000401").equals("13675834792"));
        assertTrue(resultMap4.get("1004000402").equals("8613010360500"));

        //测试F5
        Map datacellParams5 = new TreeMap();
        datacellParams5.put("1004000501", "0");//消息认证方案号
        datacellParams5.put("1004000502", "21");//消息认证方案参数
        MTO_376 MTO5  = PutInCommandItem(datacellParams5,null,"10040005","96123456");

        Map<String,String> resultMap5 = getTestResults_WEP(MTO5,"10040005");
        assertTrue(resultMap5.get("1004000501").equals("0"));
        assertTrue(resultMap5.get("1004000502").equals("21"));

        //测试F7
        Map datacellParams7 = new TreeMap();
        datacellParams7.put("1004000701", "50.120.56.123");//终端IP地址
        datacellParams7.put("1004000702", "255.255.255.0");//子网掩码地址
        datacellParams7.put("1004000703", "218.108.248.200");//网关地址
        datacellParams7.put("1004000704", "0");//代理服务器代理类型
        datacellParams7.put("1004000705", "108.215.0.3:9988");//代理服务器地址和端口
        datacellParams7.put("1004000706", "1");//代理服务器连接方式
        datacellParams7.put("1004000707", "2");//代理服务器用户名长度
        datacellParams7.put("1004000708", "cm");//代理服务器用户名
        datacellParams7.put("1004000709", "3");//代理服务器密码长度
        datacellParams7.put("1004000710", "123");//代理服务器密码
        datacellParams7.put("1004000711", "9988");//终端侦听端口
        MTO_376 MTO7  = PutInCommandItem(datacellParams7,null,"10040007","96123456");

        Map<String,String> resultMap7 = getTestResults_WEP(MTO7,"10040007");
        assertTrue(resultMap7.get("1004000701").equals("50.120.56.123"));
        assertTrue(resultMap7.get("1004000702").equals("255.255.255.0"));
        assertTrue(resultMap7.get("1004000703").equals("218.108.248.200"));
        assertTrue(resultMap7.get("1004000704").equals("0"));
        assertTrue(resultMap7.get("1004000705").equals("108.215.0.3:9988"));
        assertTrue(resultMap7.get("1004000706").equals("1"));
        assertTrue(resultMap7.get("1004000707").equals("2"));
        assertTrue(resultMap7.get("1004000708").equals("cm"));
        assertTrue(resultMap7.get("1004000709").equals("3"));
        assertTrue(resultMap7.get("1004000710").equals("123"));
        assertTrue(resultMap7.get("1004000711").equals("9988"));

        //测试F8
        Map datacellParams8 = new TreeMap();
        datacellParams8.put("1004000801", "0");//TCP
        datacellParams8.put("1004000803", "01");//工作模式：客户机
        datacellParams8.put("1004000805", "01");//终端工作在客户机模式下的三种在线模式:永久在线
        datacellParams8.put("1004000806", "30");//重拨间隔：30秒
        datacellParams8.put("1004000807", "3");//重拨次数：3
        datacellParams8.put("1004000808", "30");//连续无通信自动断线时间：30分钟
        datacellParams8.put("1004000809", "111111111111111111111111");//在线时段标志
        MTO_376 MTO8  = PutInCommandItem(datacellParams8,null,"10040008","96123456");

        Map<String,String> resultMap8 = getTestResults_WEP(MTO8,"10040008");
        assertTrue(resultMap8.get("1004000801").equals("0"));
        assertTrue(resultMap8.get("1004000803").equals("01"));
        assertTrue(resultMap8.get("1004000805").equals("01"));
        assertTrue(resultMap8.get("1004000806").equals("30"));
        assertTrue(resultMap8.get("1004000807").equals("3"));
        assertTrue(resultMap8.get("1004000808").equals("30"));
        assertTrue(resultMap8.get("1004000809").equals("111111111111111111111111"));

        //测试F9
        Map datacellParams9 = new TreeMap();
        datacellParams9.put("1004000901", "1111111111111111111111111111111111111111111111111111111111111111");//事件记录有效标志位
        datacellParams9.put("1004000902", "1111111111111111111111111111111111111111111111111111111111111111");//事件重要性等级标志位
        MTO_376 MTO9  = PutInCommandItem(datacellParams9,null,"10040009","96123456");
        Map<String,String> resultMap9 = getTestResults_WEP(MTO9,"10040009");
        assertTrue(resultMap9.get("1004000901").equals("1111111111111111111111111111111111111111111111111111111111111111"));
        assertTrue(resultMap9.get("1004000902").equals("1111111111111111111111111111111111111111111111111111111111111111"));

        //测试F10
        CircleDataItems circleDataItems = new CircleDataItems();
        circleDataItems.setNumberItem(new DataItem("1004001001","1"));//本次电能表/交流采样装置配置数量
        DataItemGroup diGroup1 = new DataItemGroup();
        diGroup1.AddDataItem(new DataItem("10040010020001","1"));//本次配置第0001块电能表/交流采样装置序号
        diGroup1.AddDataItem(new DataItem("10040010030001","1"));//本次配置第0001块电能表/交流采样装置所属测量点号
        diGroup1.AddDataItem(new DataItem("10040010040001","6"));//本次配置第0001块电能表/交流采样装置通信波特率
        diGroup1.AddDataItem(new DataItem("10040010050001","1"));//本次配置第0001块电能表/交流采样装置通信端口号
        diGroup1.AddDataItem(new DataItem("10040010060001","100"));//本次配置第0001块电能表/交流采样装置通信协议类型
        diGroup1.AddDataItem(new DataItem("10040010070001","0"));//本次配置第0001块电能表/交流采样装置通信地址
        diGroup1.AddDataItem(new DataItem("10040010080001","0"));//本次配置第0001块电能表/交流采样装置通信密码
        diGroup1.AddDataItem(new DataItem("10040010100001","000001"));//本次配置第0001块电能表/交流采样装置电能费率个数
        diGroup1.AddDataItem(new DataItem("10040010120001","00"));//本次配置第0001块电能表/交流采样装置有功电能示值的整数位个数
        diGroup1.AddDataItem(new DataItem("10040010130001","00"));//本次配置第0001块电能表/交流采样装置有功电能示值的小数位个数
        diGroup1.AddDataItem(new DataItem("10040010140001","1"));//本次配置第0001块电能表/交流采样装置所属采集器通信地址
        diGroup1.AddDataItem(new DataItem("10040010150001","0000"));//本次配置第0001块电能表/交流采样装置所属的用户大类号
        diGroup1.AddDataItem(new DataItem("10040010160001","0000"));//本次配置第0001块电能表/交流采样装置所属的用户小类号
        circleDataItems.AddDataItemGroup(diGroup1);


        MTO_376 MTO10  = PutInCommandItem(null,circleDataItems,"10040010","96123456");
        Map<String,String> resultMap10 = getTestResults_WEP(MTO10,"10040010");
        assertTrue(resultMap10.get("1004001001").equals("1"));
        assertTrue(resultMap10.get("1004001002").equals("1"));
        assertTrue(resultMap10.get("1004001003").equals("1"));
        assertTrue(resultMap10.get("1004001004").equals("6"));
        assertTrue(resultMap10.get("1004001005").equals("1"));
        assertTrue(resultMap10.get("1004001006").equals("100"));
        assertTrue(resultMap10.get("1004001007").equals("0"));
        assertTrue(resultMap10.get("1004001008").equals("0"));
        assertTrue(resultMap10.get("1004001010").equals("000001"));
        assertTrue(resultMap10.get("1004001012").equals("00"));
        assertTrue(resultMap10.get("1004001013").equals("00"));
        assertTrue(resultMap10.get("1004001014").equals("1"));
        assertTrue(resultMap10.get("1004001015").equals("0000"));
        assertTrue(resultMap10.get("1004001016").equals("0000"));

       //测试F16
        Map datacellParams16 = new TreeMap();
        datacellParams16.put("1004001601", "cmdz");//虚拟专网用户名
        datacellParams16.put("1004001602", "cmdz.zj");//虚拟专网密码

        MTO_376 MTO16  = PutInCommandItem(datacellParams16,null,"10040016","96123456");

        Map<String,String> resultMap16 = getTestResults_WEP(MTO16,"10040016");
        assertTrue(resultMap16.get("1004001601").equals("cmdz"));
        assertTrue(resultMap16.get("1004001602").equals("cmdz.zj"));

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
        Map datacellParams3 = new TreeMap();
        datacellParams3.put("1004000301", "192.168.0.1:8080");//主用IP地址和端口
        datacellParams3.put("1004000302", "192.168.0.2:8080");//备用IP地址和端口
        datacellParams3.put("1004000303", "ZJDL.ZJ");//APN
        MTO_376 MTO3  = PutInCommandItem(datacellParams3,null,"10040003","96123456");
        RealTimeProxy376 instance = new RealTimeProxy376();
        long SequenceCode = instance.writeEquipmentParameters(MTO3);

        //模拟一条设置确认帧
        PmPacket376 packet = new PmPacket376();
        packet.setAfn((byte)0x00);//AFN
        packet.getAddress().setRtua("96123456"); //逻辑地址
        packet.getControlCode().setIsUpDirect(true);
        packet.getControlCode().setIsOrgniger(false);
        packet.getControlCode().setFunctionKey((byte)1);
        packet.getControlCode().setIsDownDirectFrameCountAvaliable(false);
        packet.getSeq().setIsTpvAvalibe(false);
        PmPacket376DA da = new PmPacket376DA(0);
        PmPacket376DT dt = new PmPacket376DT(1);
        packet.getDataBuffer().putDA(da);
        packet.getDataBuffer().putDT(dt);
        packet.setTpv(new TimeProtectValue());//时间标签

        RTTaskRecv recv = new RTTaskRecv();
        recv.setLogicAddress("96123456");
        recv.setRecvMsg(BcdUtils.binArrayToString(packet.getValue()));
        recv.setSequenceCode(SequenceCode);

        taskService.insertRecvMsg(SequenceCode, "96123456", BcdUtils.binArrayToString(packet.getValue()));
        Map expResult = new HashMap();
        expResult.put("96123456#0#10040003","1");
        Map result = instance.getReturnByWEP(SequenceCode);
        assertEquals(expResult, result);

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