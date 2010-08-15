/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.realinterface;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pep.bp.db.RTTaskService;
import pep.bp.model.RealTimeTaskDAO;
import pep.bp.realinterface.mto.CircleDataItems;
import pep.bp.realinterface.mto.CollectObject;
import pep.bp.realinterface.mto.CollectObject_TransMit;
import pep.bp.realinterface.mto.CommandItem;
import pep.bp.realinterface.mto.DataItem;
import pep.bp.realinterface.mto.DataItemGroup;
import pep.bp.realinterface.mto.MTO_376;
import static org.junit.Assert.*;
import pep.bp.utils.BaudRate;
import pep.bp.utils.Converter;
import pep.bp.utils.MeterType;
import pep.bp.utils.SerialPortPara;
import pep.codec.protocol.gb.PmPacketData;
import pep.codec.protocol.gb.gb376.PmPacket376;
import pep.codec.protocol.gb.gb376.PmPacket376DA;
import pep.codec.protocol.gb.gb376.PmPacket376DT;
import pep.codec.utils.BcdUtils;
import pep.meter645.Gb645MeterPacket;

/**
 *
 * @author Thinkpad
 */
public class RealTimeProxy376Test {

    private static String RTUA = "";
    private static RealTimeProxy376 proxy;
    private RTTaskService taskService;
    private Converter converter;

    
    public RealTimeProxy376Test(){
        ApplicationContext app =    new  ClassPathXmlApplicationContext("beans.xml");
        proxy = (RealTimeProxy376)app.getBean("realTimeProxy376");
        this.taskService = (RTTaskService)app.getBean("rtTaskService");
        this.converter = (Converter)app.getBean("converter");
    }
    /**
     * @param aRTUA the RTUA to set
     */
    public static void setRTUA(String aRTUA) {
        RTUA = aRTUA;
    }

    private MTO_376 PutInCommandItem(Map datacellParams,CircleDataItems circleDataItems, String commandItemCode,String LogicAddress,int MpSn){
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
        object.setMpSn(new int[]{MpSn});

        MTO.getCollectObjects().add(object);

        return MTO;
    }

    private Map<String, String> getTestResults(MTO_376 MTO,String key) throws Exception{
        RealTimeProxy376 instance = proxy;
        long SequenceCode = instance.writeParameters(MTO);
        RealTimeTaskDAO task = taskService.getTask(SequenceCode);
        PmPacket376 packet = new PmPacket376();
        packet.setValue(BcdUtils.stringToByteArray(task.getSendmsg()),0);
        Map<String,Map<String, String>> results = new HashMap<String,Map<String, String>>();
        converter.decodeData(packet,results);
        return results.get(key);
    }

    /**
     * Test of writeEquipmentParameters method, of class RealTimeProxy376.
     */
   // @Test
    public void testWriteEquipmentParameters() throws Exception {
      // 测试F1
        Map datacellParams1 = new TreeMap();
        datacellParams1.put("1004000101", "10");//终端数传机延时时间RTS
        datacellParams1.put("1004000102", "20");//终端作为启动站允许发送传输延时时间
        datacellParams1.put("1004000103", "30");//终端等待从动站响应的超时时间
        datacellParams1.put("1004000104", "3");//终端等待从动站响应的重发次数
        datacellParams1.put("1004000106", "11100000");//需要主站确认的通信服务（CON=1）的标志
        datacellParams1.put("1004000107", "15");//心跳周期
        MTO_376 MTO1  = PutInCommandItem(datacellParams1,null,"10040001","96123456",0);

        Map<String,String> resultMap1 = getTestResults(MTO1,"96123456#0#10040001");
        assertTrue(resultMap1.get("1004000101").equals("10"));
        assertTrue(resultMap1.get("1004000102").equals("20"));
        assertTrue(resultMap1.get("1004000103").equals("30"));
        assertTrue(resultMap1.get("1004000104").equals("3"));
        assertTrue(resultMap1.get("1004000106").equals("11100000"));
        assertTrue(resultMap1.get("1004000107").equals("15"));

//       //测试F3
//        Map datacellParams3 = new TreeMap();
//        datacellParams3.put("1004000301", "192.168.0.1:8080");//主用IP地址和端口
//        datacellParams3.put("1004000302", "192.168.0.2:8080");//备用IP地址和端口
//        datacellParams3.put("1004000303", "ZJDL.ZJ");//APN
//        MTO_376 MTO3  = PutInCommandItem(datacellParams3,null,"10040003","96123456",0);
////
//        Map<String,String> resultMap3 = getTestResults(MTO3,"96123456#0#10040003");
//        assertTrue(resultMap3.get("1004000301").equals("192.168.0.1:8080"));
//        assertTrue(resultMap3.get("1004000302").equals("192.168.0.2:8080"));
//        assertTrue(resultMap3.get("1004000303").equals("ZJDL.ZJ"));
//
//        //测试F4
//        Map datacellParams4 = new TreeMap();
//        datacellParams4.put("1004000401", "13675834792");//主站电话号码或主站手机号码
//        datacellParams4.put("1004000402", "8613010360500");//短信中心号码
//        MTO_376 MTO4  = PutInCommandItem(datacellParams4,null,"10040004","96123456",0);
//
//        Map<String,String> resultMap4 = getTestResults(MTO4,"96123456#0#10040004");
//        assertTrue(resultMap4.get("1004000401").equals("13675834792"));
//        assertTrue(resultMap4.get("1004000402").equals("8613010360500"));
//
//        //测试F5
//        Map datacellParams5 = new TreeMap();
//        datacellParams5.put("1004000501", "0");//消息认证方案号
//        datacellParams5.put("1004000502", "21");//消息认证方案参数
//        MTO_376 MTO5  = PutInCommandItem(datacellParams5,null,"10040005","96123456",0);
//
//        Map<String,String> resultMap5 = getTestResults(MTO5,"96123456#0#10040005");
//        assertTrue(resultMap5.get("1004000501").equals("0"));
//        assertTrue(resultMap5.get("1004000502").equals("21"));
//////
//////        //测试F7
//        Map datacellParams7 = new TreeMap();
//        datacellParams7.put("1004000701", "50.120.56.123");//终端IP地址
//        datacellParams7.put("1004000702", "255.255.255.0");//子网掩码地址
//        datacellParams7.put("1004000703", "218.108.248.200");//网关地址
//        datacellParams7.put("1004000704", "0");//代理服务器代理类型
//        datacellParams7.put("1004000705", "108.215.0.3:9988");//代理服务器地址和端口
//        datacellParams7.put("1004000706", "1");//代理服务器连接方式
//        datacellParams7.put("1004000707", "2");//代理服务器用户名长度
//        datacellParams7.put("1004000708", "cm");//代理服务器用户名
//        datacellParams7.put("1004000709", "3");//代理服务器密码长度
//        datacellParams7.put("1004000710", "123");//代理服务器密码
//        datacellParams7.put("1004000711", "9988");//终端侦听端口
//        MTO_376 MTO7  = PutInCommandItem(datacellParams7,null,"10040007","96123456",0);
//
//        Map<String,String> resultMap7 = getTestResults(MTO7,"96123456#0#10040007");
//        assertTrue(resultMap7.get("1004000701").equals("50.120.56.123"));
//        assertTrue(resultMap7.get("1004000702").equals("255.255.255.0"));
//        assertTrue(resultMap7.get("1004000703").equals("218.108.248.200"));
//        assertTrue(resultMap7.get("1004000704").equals("0"));
//        assertTrue(resultMap7.get("1004000705").equals("108.215.0.3:9988"));
//        assertTrue(resultMap7.get("1004000706").equals("1"));
//        assertTrue(resultMap7.get("1004000707").equals("2"));
//        assertTrue(resultMap7.get("1004000708").equals("cm"));
//        assertTrue(resultMap7.get("1004000709").equals("3"));
//        assertTrue(resultMap7.get("1004000710").equals("123"));
//        assertTrue(resultMap7.get("1004000711").equals("9988"));
//
////        //测试F8
//        Map datacellParams8 = new TreeMap();
//        datacellParams8.put("1004000801", "0");//TCP
//        datacellParams8.put("1004000803", "01");//工作模式：客户机
//        datacellParams8.put("1004000805", "01");//终端工作在客户机模式下的三种在线模式:永久在线
//        datacellParams8.put("1004000806", "30");//重拨间隔：30秒
//        datacellParams8.put("1004000807", "3");//重拨次数：3
//        datacellParams8.put("1004000808", "30");//连续无通信自动断线时间：30分钟
//        datacellParams8.put("1004000809", "111111111111111111111111");//在线时段标志
//        MTO_376 MTO8  = PutInCommandItem(datacellParams8,null,"10040008","96123456",0);
//
//        Map<String,String> resultMap8 = getTestResults(MTO8,"96123456#0#10040008");
//        assertTrue(resultMap8.get("1004000801").equals("0"));
//        assertTrue(resultMap8.get("1004000803").equals("01"));
//        assertTrue(resultMap8.get("1004000805").equals("01"));
//        assertTrue(resultMap8.get("1004000806").equals("30"));
//        assertTrue(resultMap8.get("1004000807").equals("3"));
//        assertTrue(resultMap8.get("1004000808").equals("30"));
//        assertTrue(resultMap8.get("1004000809").equals("111111111111111111111111"));
//
//        //测试F9
//        Map datacellParams9 = new TreeMap();
//        datacellParams9.put("1004000901", "1111111111111111111111111111111111111111111111111111111111111111");//事件记录有效标志位
//        datacellParams9.put("1004000902", "1111111111111111111111111111111111111111111111111111111111111111");//事件重要性等级标志位
//        MTO_376 MTO9  = PutInCommandItem(datacellParams9,null,"10040009","96123456",0);
//        Map<String,String> resultMap9 = getTestResults(MTO9,"96123456#0#10040009");
//        assertTrue(resultMap9.get("1004000901").equals("1111111111111111111111111111111111111111111111111111111111111111"));
//        assertTrue(resultMap9.get("1004000902").equals("1111111111111111111111111111111111111111111111111111111111111111"));
//
        //测试F10
//        Map datacellParams10 = new TreeMap();
//        datacellParams10.put("1004001001","1");//本次电能表/交流采样装置配置数量
//        CircleDataItems circleDataItems = new CircleDataItems();
//        for(int i=1;i<=9;i++)
//        {
//            DataItemGroup diGroup1 = new DataItemGroup();
//            diGroup1.AddDataItem(new DataItem("1004001002000"+i,String.valueOf(i)));//本次配置第0001块电能表/交流采样装置序号
//            diGroup1.AddDataItem(new DataItem("1004001003000"+i,String.valueOf(i)));//本次配置第0001块电能表/交流采样装置所属测量点号
//            diGroup1.AddDataItem(new DataItem("1004001004000"+i,"6"));//本次配置第0001块电能表/交流采样装置通信波特率
//            diGroup1.AddDataItem(new DataItem("1004001005000"+i,"1"));//本次配置第0001块电能表/交流采样装置通信端口号
//            diGroup1.AddDataItem(new DataItem("1004001006000"+i,"100"));//本次配置第0001块电能表/交流采样装置通信协议类型
//            diGroup1.AddDataItem(new DataItem("1004001007000"+i,"0"));//本次配置第0001块电能表/交流采样装置通信地址
//            diGroup1.AddDataItem(new DataItem("1004001008000"+i,"0"));//本次配置第0001块电能表/交流采样装置通信密码
//            diGroup1.AddDataItem(new DataItem("1004001010000"+i,"000001"));//本次配置第0001块电能表/交流采样装置电能费率个数
//            diGroup1.AddDataItem(new DataItem("1004001012000"+i,"00"));//本次配置第0001块电能表/交流采样装置有功电能示值的整数位个数
//            diGroup1.AddDataItem(new DataItem("1004001013000"+i,"00"));//本次配置第0001块电能表/交流采样装置有功电能示值的小数位个数
//            diGroup1.AddDataItem(new DataItem("1004001014000"+i,"999999999999"));//本次配置第0001块电能表/交流采样装置所属采集器通信地址
//            diGroup1.AddDataItem(new DataItem("1004001015000"+i,"0000"));//本次配置第0001块电能表/交流采样装置所属的用户大类号
//            diGroup1.AddDataItem(new DataItem("1004001016000"+i,"0000"));//本次配置第0001块电能表/交流采样装置所属的用户小类号
//            circleDataItems.AddDataItemGroup(diGroup1);
//        }
//
//        MTO_376 MTO10  = PutInCommandItem(datacellParams10,circleDataItems,"10040010","96123456",0);
//        RealTimeProxy376 instance = proxy;
//        long SequenceCode = instance.writeParameters(MTO10);
//        List<RealTimeTaskDAO> taskList = taskService.getTasks(SequenceCode);
//        assertTrue(taskList.size()>1);

//        Map<String,String> resultMap10 = getTestResults(MTO10,"96123456#0#10040010");
//        assertTrue(resultMap10.get("1004001001").equals("1"));
//        assertTrue(resultMap10.get("1004001002").equals("1"));
//        assertTrue(resultMap10.get("1004001003").equals("1"));
//        assertTrue(resultMap10.get("1004001004").equals("6"));
//        assertTrue(resultMap10.get("1004001005").equals("1"));
//        assertTrue(resultMap10.get("1004001006").equals("100"));
//        assertTrue(resultMap10.get("1004001007").equals("0"));
//        assertTrue(resultMap10.get("1004001008").equals("0"));
//        assertTrue(resultMap10.get("1004001010").equals("000001"));
//        assertTrue(resultMap10.get("1004001012").equals("00"));
//        assertTrue(resultMap10.get("1004001013").equals("00"));
//        assertTrue(resultMap10.get("1004001014").equals("999999999999"));
//        assertTrue(resultMap10.get("1004001015").equals("0000"));
//        assertTrue(resultMap10.get("1004001016").equals("0000"));
//
////        //测试F12
//        Map datacellParams12 = new TreeMap();
//        datacellParams12.put("1004001201", "11111111");//状态量接入标志位
//        datacellParams12.put("1004001202", "00000000");//状态量属性标志位
//        MTO_376 MTO12  = PutInCommandItem(datacellParams12,null,"10040012","96123456",0);
//        Map<String,String> resultMap12 = getTestResults(MTO12,"96123456#0#10040012");
//        assertTrue(resultMap12.get("1004001201").equals("11111111"));
//        assertTrue(resultMap12.get("1004001202").equals("00000000"));
//
////       //测试F16
//        Map datacellParams16 = new TreeMap();
//        datacellParams16.put("1004001601", "cmdz");//虚拟专网用户名
//        datacellParams16.put("1004001602", "cmdz.zj");//虚拟专网密码
//        MTO_376 MTO16  = PutInCommandItem(datacellParams16,null,"10040016","96123456",0);
//        Map<String,String> resultMap16 = getTestResults(MTO16,"96123456#0#10040016");
//        assertTrue(resultMap16.get("1004001601").equals("cmdz"));
//        assertTrue(resultMap16.get("1004001602").equals("cmdz.zj"));
//
//        //测试F17
//        Map datacellParams17 = new TreeMap();
//        datacellParams17.put("1004001701", "200");//终端保安定值
//        MTO_376 MTO17  = PutInCommandItem(datacellParams17,null,"10040017","96123456",0);
//        Map<String,String> resultMap17 = getTestResults(MTO17,"96123456#0#10040017");
//        assertTrue(resultMap17.get("1004001701").equals("200.0"));
//
//          //测试F25
//        Map datacellParams25 = new TreeMap();
//        datacellParams25.put("1004002501", "50");//电压互感器倍率
//        datacellParams25.put("1004002502", "100");//电流互感器倍率
//        datacellParams25.put("1004002503", "220");//额定电压
//        datacellParams25.put("1004002504", "8");//额定电流
//        datacellParams25.put("1004002505", "50");//额定负荷
//        datacellParams25.put("1004002507", "11");//单相表接线相
//        datacellParams25.put("1004002508", "11");//电源接线方式
//
//        MTO_376 MTO25  = PutInCommandItem(datacellParams25,null,"10040025","96123456",1);
//        Map<String,String> resultMap25 = getTestResults(MTO25,"96123456#1#10040025");
//        assertTrue(resultMap25.get("1004002501").equals("50"));
//        assertTrue(resultMap25.get("1004002502").equals("100"));
//        assertTrue(resultMap25.get("1004002503").equals("220.0"));
//        assertTrue(resultMap25.get("1004002504").equals("8.0"));
//        assertTrue(resultMap25.get("1004002505").equals("50.0"));
//        assertTrue(resultMap25.get("1004002507").equals("11"));
//        assertTrue(resultMap25.get("1004002508").equals("11"));
////
////          //测试F26
//        Map datacellParams26 = new TreeMap();
//        datacellParams26.put("1004002601", "100");//电压合格上限
//        datacellParams26.put("1004002602", "70");//电压合格下限
//        datacellParams26.put("1004002603", "1");//电压断相门限
//        datacellParams26.put("1004002604", "240");//电压上上限（过压门限）
//        datacellParams26.put("1004002605", "1");//过压越限持续时间
//        datacellParams26.put("1004002606", "50");//过压越限恢复系数
//        datacellParams26.put("1004002607", "0");//电压下下限（欠压门限）
//        datacellParams26.put("1004002608", "1");//欠压越限持续时间
//        datacellParams26.put("1004002609", "3");//欠压越限恢复系数
//
//        datacellParams26.put("1004002610", "1");//相电流上上限（过流门限）
//        datacellParams26.put("1004002611", "7");//过流越限持续时间
//        datacellParams26.put("1004002612", "1");//过流越限恢复系数
//        datacellParams26.put("1004002613", "2");//相电流上限（额定电流门限）
//        datacellParams26.put("1004002614", "1");//超额定电流越限持续时间
//        datacellParams26.put("1004002615", "5");//超额定电流越限恢复系数
//        datacellParams26.put("1004002616", "0");//零序电流上限
//        datacellParams26.put("1004002617", "1");//零序电流超限越限持续时间
//        datacellParams26.put("1004002618", "3");//零序电流超限越限恢复系数
//
//        datacellParams26.put("1004002619", "1");//视在功率上上限
//        datacellParams26.put("1004002620", "7");//视在功率超上上限越限持续时间
//        datacellParams26.put("1004002621", "1");//视在功率超上上限越限恢复系数
//        datacellParams26.put("1004002622", "2");//视在功率上限
//        datacellParams26.put("1004002623", "1");//视在功率超上限越限持续时间
//        datacellParams26.put("1004002624", "5");//视在功率超上限越限恢复系数
//        datacellParams26.put("1004002625", "0");//三相电压不平衡限值
//        datacellParams26.put("1004002626", "1");//三相电压不平衡超限越限持续时间
//        datacellParams26.put("1004002627", "3");//三相电压不平衡超限越限恢复系数
//        datacellParams26.put("1004002628", "0");//三相电流不平衡限值
//        datacellParams26.put("1004002629", "1");//三相电流不平衡超限越限持续时间
//        datacellParams26.put("1004002630", "3");//三相电流不平衡超限越限恢复系数
//        datacellParams26.put("1004002630", "3");//连续失压时间限值
//
//        MTO_376 MTO26  = PutInCommandItem(datacellParams26,null,"10040026","96123456",1);
//        Map<String,String> resultMap26 = getTestResults(MTO26,"96123456#1#10040026");
//        assertTrue(resultMap26.get("1004002601").equals("100.0"));
//        assertTrue(resultMap26.get("1004002602").equals("70.0"));
//        assertTrue(resultMap26.get("1004002603").equals("1.0"));
//        assertTrue(resultMap26.get("1004002604").equals("240.0"));
//        assertTrue(resultMap26.get("1004002605").equals("1"));
//        assertTrue(resultMap26.get("1004002606").equals("50.0"));
//        assertTrue(resultMap26.get("1004002607").equals("0.0"));
//        assertTrue(resultMap26.get("1004002608").equals("1"));
//        assertTrue(resultMap26.get("1004002609").equals("3.0"));
////
////        //测试F61
//        Map datacellParams61 = new TreeMap();
//        datacellParams61.put("1004006101", "11111111");//直流模拟量接入参数
//        MTO_376 MTO61  = PutInCommandItem(datacellParams61,null,"10040061","96123456",0);
//        Map<String,String> resultMap61 = getTestResults(MTO61,"96123456#0#10040061");
//        assertTrue(resultMap61.get("1004006101").equals("11111111"));
////
//        //测试F81
//        Map datacellParams81 = new TreeMap();
//        datacellParams81.put("1004008101", "1");//直流模拟量量程起始值
//        datacellParams81.put("1004008102", "99");//直流模拟量量程终止值
//        MTO_376 MTO81  = PutInCommandItem(datacellParams81,null,"10040081","96123456",0);
//        Map<String,String> resultMap81 = getTestResults(MTO81,"96123456#0#10040081");
//        assertTrue(resultMap81.get("1004008101").equals("1.0"));
//        assertTrue(resultMap81.get("1004008102").equals("99.0"));
////
////        //测试F82
//        Map datacellParams82 = new TreeMap();
//        datacellParams82.put("1004008201", "1");//直流模拟量上限
//        datacellParams82.put("1004008202", "99");//直流模拟量下限
//        MTO_376 MTO82  = PutInCommandItem(datacellParams82,null,"10040082","96123456",0);
//        Map<String,String> resultMap82 = getTestResults(MTO82,"96123456#0#10040082");
//        assertTrue(resultMap82.get("1004008201").equals("1.0"));
//        assertTrue(resultMap82.get("1004008202").equals("99.0"));
//
////        //测试F83
//        Map datacellParams83 = new TreeMap();
//        datacellParams83.put("1004008301", "24");//直流模拟量冻结密度
//        MTO_376 MTO83  = PutInCommandItem(datacellParams83,null,"10040083","96123456",0);
//        Map<String,String> resultMap83 = getTestResults(MTO83,"96123456#0#10040083");
//        assertTrue(resultMap83.get("1004008301").equals("24"));
    }

    /**
     * Test of readEquipmentParameters method, of class RealTimeProxy376.
     */
    //@Test
    public void testReadEquipmentParametersF1() throws Exception {
        CommandItem commandItem = new CommandItem();
        commandItem.setIdentifier("10040001");

        CollectObject obj= new CollectObject();
        obj.setLogicalAddr("96123456");
        obj.setMpSn(new int[]{0});
        obj.AddCommandItem(commandItem);

        MTO_376 MTO3 = new MTO_376();
        MTO3.addCollectObject(obj);

        RealTimeProxy376 instance = proxy;
        long SequenceCode = instance.readParameters(MTO3);

        this.taskService.insertRecvMsg(SequenceCode, "96123456", "55 68 4A 00 4A 00 68 98 12 96 56 34 00 0A 60 00 00 01 00 0A 14 1E 03 07 0F 8A 16 55  ");

        Map<String, Map<String, String>> expResult = new HashMap();
        Map<String, String> MapDataItem = new HashMap();

        MapDataItem.put("1004000101", "10");//终端数传机延时时间RTS
        MapDataItem.put("1004000102", "20");//终端作为启动站允许发送传输延时时间
        MapDataItem.put("1004000103", "30");//终端等待从动站响应的超时时间
        MapDataItem.put("1004000104", "3");//终端等待从动站响应的重发次数
        MapDataItem.put("1004000106", "11100000");//需要主站确认的通信服务（CON=1）的标志
        MapDataItem.put("1004000107", "15");//心跳周期

        expResult.put("96123456#0#10040001",MapDataItem);
        Map result = instance.getReturnByReadParameter(SequenceCode);
        assertEquals(expResult, result);
    }


    //@Test
    public void testReadEquipmentParametersF3() throws Exception {
        CommandItem commandItem = new CommandItem();
        commandItem.setIdentifier("10040003");

        CollectObject obj= new CollectObject();
        obj.setLogicalAddr("96123456");
        obj.setMpSn(new int[]{0});
        obj.AddCommandItem(commandItem);

        MTO_376 MTO3 = new MTO_376();
        MTO3.addCollectObject(obj);

        RealTimeProxy376 instance =proxy;
        long SequenceCode = instance.readParameters(MTO3);

        this.taskService.insertRecvMsg(SequenceCode, "96123456", "55 68 A2 00 A2 00 68 98 12 96 56 34 00 0A 60 00 00 04 00 C0 A8 00 01 90 1F C0 A8 00 02 90 1F 5A 4A 44 4C 2E 5A 4A 00 00 00 00 00 00 00 00 00 6F 16 55 ");

        Map<String, Map<String, String>> expResult = new HashMap();
        Map<String, String> MapDataItem = new HashMap();

        MapDataItem.put("1004000301", "192.168.0.1:8080");//主用IP地址和端口
        MapDataItem.put("1004000302", "192.168.0.2:8080");//备用IP地址和端口
        MapDataItem.put("1004000303", "ZJDL.ZJ");//APN

        expResult.put("96123456#0#10040003",MapDataItem);
        Map result = instance.getReturnByReadParameter(SequenceCode);
        assertEquals(expResult, result);
    }

    //@Test
    public void testReadEquipmentParametersF9() throws Exception {
        CommandItem commandItem = new CommandItem();
        commandItem.setIdentifier("10040009");

        CollectObject obj= new CollectObject();
        obj.setLogicalAddr("96123456");
        obj.setMpSn(new int[]{0});
        obj.AddCommandItem(commandItem);

        MTO_376 MTO3 = new MTO_376();
        MTO3.addCollectObject(obj);

        RealTimeProxy376 instance = proxy;
        long SequenceCode = instance.readParameters(MTO3);

        this.taskService.insertRecvMsg(SequenceCode, "96123456", "55 68 72 00 72 00 68 98 12 96 56 34 00 0A 60 00 00 01 01 FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF 26 16 55 ");

        Map<String, Map<String, String>> expResult = new HashMap();
        Map<String, String> MapDataItem = new HashMap();

        MapDataItem.put("1004000901", "1111111111111111111111111111111111111111111111111111111111111111");//主用IP地址和端口
        MapDataItem.put("1004000902", "1111111111111111111111111111111111111111111111111111111111111111");//事件重要性等级标志位

        expResult.put("96123456#0#10040009",MapDataItem);
        Map result = instance.getReturnByReadParameter(SequenceCode);
        assertEquals(expResult, result);
    }

   //@Test
    public void testReadEquipmentParametersF10() throws Exception {
        Map datacellParams10 = new TreeMap();
        datacellParams10.put("1004001001", "1");//主用IP地址和端口

        CircleDataItems circleDataItems = new CircleDataItems();
        for(int i=1;i<=3;i++)
        {
            DataItemGroup group = new DataItemGroup();
            group.AddDataItem(new DataItem("1004001002000"+i,String.valueOf(i)));//本次配置第0001块电能表/交流采样装置序号
            circleDataItems.AddDataItemGroup(group);
        }

        CommandItem commandItem = new CommandItem();
        commandItem.setIdentifier("10040010");
        commandItem.setDatacellParam(datacellParams10);
        commandItem.setCircleDataItems(circleDataItems);

        CollectObject obj= new CollectObject();
        obj.setLogicalAddr("96123456");
        obj.setMpSn(new int[]{0});
        obj.AddCommandItem(commandItem);

        MTO_376 MTO3 = new MTO_376();
        MTO3.addCollectObject(obj);

        RealTimeProxy376 instance = proxy;
        long SequenceCode = instance.readParameters(MTO3);

//        this.taskService.insertRecvMsg(SequenceCode, "96123456", "55 68 A2 00 A2 00 68 98 12 96 56 34 00 0A 60 00 00 04 00 C0 A8 00 01 90 1F C0 A8 00 02 90 1F 5A 4A 44 4C 2E 5A 4A 00 00 00 00 00 00 00 00 00 6F 16 55 ");
//        this.taskService.insertRecvMsg(SequenceCode, "96123456", "55 68 A2 00 A2 00 68 98 12 96 56 34 00 0A 60 00 00 04 00 C0 A8 00 01 90 1F C0 A8 00 02 90 1F 5A 4A 44 4C 2E 5A 4A 00 00 00 00 00 00 00 00 00 6F 16 55 ");

//        Map datacellParams10 = new TreeMap();
//        datacellParams10.put("1004001001","1");//本次电能表/交流采样装置配置数量
//        CircleDataItems circleDataItems = new CircleDataItems();
//        DataItemGroup diGroup1 = new DataItemGroup();
//        diGroup1.AddDataItem(new DataItem("10040010020001","1"));//本次配置第0001块电能表/交流采样装置序号
//        diGroup1.AddDataItem(new DataItem("10040010030001","1"));//本次配置第0001块电能表/交流采样装置所属测量点号
//        diGroup1.AddDataItem(new DataItem("10040010040001","6"));//本次配置第0001块电能表/交流采样装置通信波特率
//        diGroup1.AddDataItem(new DataItem("10040010050001","1"));//本次配置第0001块电能表/交流采样装置通信端口号
//        diGroup1.AddDataItem(new DataItem("10040010060001","100"));//本次配置第0001块电能表/交流采样装置通信协议类型
//        diGroup1.AddDataItem(new DataItem("10040010070001","0"));//本次配置第0001块电能表/交流采样装置通信地址
//        diGroup1.AddDataItem(new DataItem("10040010080001","0"));//本次配置第0001块电能表/交流采样装置通信密码
//        diGroup1.AddDataItem(new DataItem("10040010100001","000001"));//本次配置第0001块电能表/交流采样装置电能费率个数
//        diGroup1.AddDataItem(new DataItem("10040010120001","00"));//本次配置第0001块电能表/交流采样装置有功电能示值的整数位个数
//        diGroup1.AddDataItem(new DataItem("10040010130001","00"));//本次配置第0001块电能表/交流采样装置有功电能示值的小数位个数
//        diGroup1.AddDataItem(new DataItem("10040010140001","999999999999"));//本次配置第0001块电能表/交流采样装置所属采集器通信地址
//        diGroup1.AddDataItem(new DataItem("10040010150001","0000"));//本次配置第0001块电能表/交流采样装置所属的用户大类号
//        diGroup1.AddDataItem(new DataItem("10040010160001","0000"));//本次配置第0001块电能表/交流采样装置所属的用户小类号

//        expResult.put("96123456#0#10040010",MapDataItem);
//        Map result = instance.getReturnByREP(SequenceCode);
//        assertEquals(expResult, result);
    }

    //@Test
    public void testReadEquipmentParametersF26() throws Exception {
        CommandItem commandItem = new CommandItem();
        commandItem.setIdentifier("10040026");

        CollectObject obj= new CollectObject();
        obj.setLogicalAddr("96123455");
        obj.setMpSn(new int[]{0});
        obj.AddCommandItem(commandItem);

        MTO_376 MTO3 = new MTO_376();
        MTO3.addCollectObject(obj);

        RealTimeProxy376 instance = proxy;
    //    long SequenceCode = instance.readParameters(MTO3);

        Map result = instance.getReturnByReadParameter(3616);
        //assertEquals(expResult, result);
    }


    /**
     * Test of writeResetCommands method, of class RealTimeProxy376.
     */
//    @Test
    public void testWriteResetCommands() throws Exception {
        System.out.println("writeResetCommands");
        CommandItem commandItem = new CommandItem();
        commandItem.setIdentifier("10010001");

        CollectObject obj= new CollectObject();
        obj.setLogicalAddr("96123456");
        obj.setMpSn(new int[]{0});
        obj.AddCommandItem(commandItem);

        MTO_376 MTO3 = new MTO_376();
        MTO3.addCollectObject(obj);

        RealTimeProxy376 instance = proxy;
        long SequenceCode = instance.writeResetCommands(MTO3);
        RealTimeTaskDAO task = taskService.getTask(SequenceCode);
        PmPacket376 packet = new PmPacket376();
        packet.setValue(BcdUtils.stringToByteArray(task.getSendmsg()),0);
        assertTrue(packet.getAddress().getRtua().equals("96123456"));
        assertTrue(packet.getAfn()==1);
        PmPacket376DA da = new PmPacket376DA();
        PmPacket376DT dt = new PmPacket376DT();
        packet.getDataBuffer().rewind();
        packet.getDataBuffer().getDA(da);
        packet.getDataBuffer().getDT(dt);
        assertTrue(dt.getFn()==1);
    }

    /**
     * Test of readRealtimeData method, of class RealTimeProxy376.
     */
  // @Test
    public void testReadRealtimeData() throws Exception {
        System.out.println("ReadRealtimeData");

        CommandItem commandItem = new CommandItem();
        commandItem.setIdentifier("100C0025");

        CollectObject obj= new CollectObject();
        obj.setLogicalAddr("96123456");
        obj.setMpSn(new int[]{1});
        obj.AddCommandItem(commandItem);

        MTO_376 MTO3 = new MTO_376();
        MTO3.addCollectObject(obj);


        RealTimeProxy376 instance = proxy;
        long SequenceCode = instance.readData(MTO3);
        RealTimeTaskDAO task = taskService.getTask(SequenceCode);
        PmPacket376 packet = new PmPacket376();
        packet.setValue(BcdUtils.stringToByteArray(task.getSendmsg()),0);
        assertTrue(packet.getAddress().getRtua().equals("96123456"));
        assertTrue(packet.getAfn()==0x0C);
        PmPacket376DA da = new PmPacket376DA();
        PmPacket376DT dt = new PmPacket376DT();
        packet.getDataBuffer().rewind();
        packet.getDataBuffer().getDA(da);
        packet.getDataBuffer().getDT(dt);
        assertTrue(da.getPn()==1);
        assertTrue(dt.getFn()==25);
        
    }

    /**
     * Test of transmitMsg method, of class RealTimeProxy376.
     */
   // @Test
    public void testTransmitMsg() throws Exception {    
//       Map datacellParams1 = new TreeMap();
//       datacellParams1.put("C012", "2010-08-04 20:34:25");//漏电保护装置校时
        
        CommandItem commandItem = new CommandItem();
        commandItem.setIdentifier("8000B66F");
       // commandItem.setDatacellParam(datacellParams1);
        
        CollectObject_TransMit cob = new CollectObject_TransMit();
        cob.setFuncode((byte)1);
        cob.setMeterAddr("1");
        cob.setMeterType(MeterType.Meter645);
        cob.setPort((byte)1);
        SerialPortPara spp = new SerialPortPara();
        spp.setBaudrate(BaudRate.bps_9600);
        spp.setCheckbit(0);
        spp.setStopbit(1);
        spp.setOdd_even_bit(1);
        spp.setDatabit(8);
        cob.setSerialPortPara(spp);
        cob.setTerminalAddr("96123456");
        cob.setWaitforByte((byte)5);
        cob.setWaitforPacket((byte)10);       
        cob.addCommandItem(commandItem);
        
        MTO_376 MTO = new MTO_376();   
        MTO.addCollectObject_Transmit(cob);

        RealTimeProxy376 instance = proxy;
        long expResult = 0L;
        long SequenceCode = instance.transmitMsg(MTO);
        
        RealTimeTaskDAO task = taskService.getTask(SequenceCode);
        PmPacket376 packet = new PmPacket376();
        packet.setValue(BcdUtils.stringToByteArray(task.getSendmsg()),0);
        assertTrue(packet.getAddress().getRtua().equals("96123456"));
        
        byte[] databuff = packet.getDataBuffer().getValue();
        int head = Gb645MeterPacket.getMsgHeadOffset(databuff, 0);
        Gb645MeterPacket pack = Gb645MeterPacket.getPacket(databuff, head);
        assertTrue(pack.getAddress().getAddress().equals("000000000001"));
        assertTrue(pack.getControlCode().getValue()==4);
        PmPacketData databuf = pack.getDataAsPmPacketData();
        databuf.rewind();
        assertTrue(databuf.getA8().getValue()==36);
    }

    @Test
    public void testreadTransmitPara() throws Exception {
        @SuppressWarnings("static-access")
         Map<String, Map<String, String>> resultMap = this.proxy.readTransmitPara(4239);
         assertTrue(resultMap.size() > 0 );
    }



    /**
     * Test of getReturnByWEP method, of class RealTimeProxy376.
     */
   @Test
    public void testGetReturnByWEP() throws Exception {
//        Map datacellParams1 = new TreeMap();
//        datacellParams1.put("1004000101", "10");//终端数传机延时时间RTS
//        datacellParams1.put("1004000102", "20");//终端作为启动站允许发送传输延时时间
//        datacellParams1.put("1004000103", "30");//终端等待从动站响应的超时时间
//        datacellParams1.put("1004000104", "3");//终端等待从动站响应的重发次数
//        datacellParams1.put("1004000106", "11100000");//需要主站确认的通信服务（CON=1）的标志
//        datacellParams1.put("1004000107", "15");//心跳周期
//        MTO_376 MTO1  = PutInCommandItem(datacellParams1,null,"10040001","96123456",0);
        RealTimeProxy376 instance = proxy;
//        long SequenceCode = instance.writeParameters(MTO1);
//
//        taskService.insertRecvMsg(SequenceCode, "96123456", "55 68 4A 00 4A 00 68 98 12 96 56 34 00 0A 60 00 00 01 00 0A 14 1E 03 07 0F 8A 16 55 ");
//        Map expResult = new HashMap();
//        expResult.put("96123456#0#10040001","1");
        Map result = instance.getReturnByWriteParameter(3467);
       // assertEquals(expResult, result);

          

    }

    /**
     * Test of getReturnByWEP_Json method, of class RealTimeProxy376.
     */
   // @Test
    public void testGetReturnByWEP_Json() throws Exception {
        RealTimeProxy376 instance = proxy;
        Map expResult = new HashMap();
        expResult.put("96123456#1#10040001","1");
        Map result = instance.getReturnByReadData(228);
        assertEquals(expResult, result);
    }

    /**
     * Test of getReturnByREP method, of class RealTimeProxy376.
     */
    @Test
    public void testGetReturnByReadParameter() throws Exception {
        Map result = proxy.getReturnByReadParameter(3623);
        assertTrue(result.size() > 0);
    }

    /**
     * Test of getReturnByRRD method, of class RealTimeProxy376.
     */
   // @Test
    public void testGetReturnByRRD() throws Exception {
       RealTimeProxy376 instance = proxy;
        Map expResult = new HashMap();
        expResult.put("96123456#1#10040001","1");
        Map result = instance.getReturnByReadData(1722);
        assertEquals(expResult, result);
    }

    /**
     * @param taskService the taskService to set
     */
    public void setTaskService(RTTaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * @param converter the converter to set
     */
    public void setConverter(Converter converter) {
        this.converter = converter;
    }

}