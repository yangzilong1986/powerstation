/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.codec.utils;

import java.io.*;
import java.net.URL;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.*;
import pep.bp.realinterface.conf.ProtocolCommandItem;
import pep.bp.realinterface.conf.ProtocolCommandItems;
import pep.bp.realinterface.conf.ProtocolConfig;
import pep.bp.realinterface.conf.ProtocolDataItem;
import pep.common.exception.CastorException;

/**
 * 从 xml 文件中解析一个对象
 * @param mappingResource 映射文件
 * @param dataResource 数据文件
 * @return Java 对象
 */
public class CastorUtil {

    public static Object unmarshal(String mappingResource, String dataResource) {
        try {
            Mapping map = new Mapping();
            map.loadMapping(mappingResource);
            File file = new File(dataResource);
            Reader reader = new FileReader(file);
            Unmarshaller unmarshaller = new Unmarshaller(map);
            try {
                return unmarshaller.unmarshal(reader);
            } finally {
                reader.close();
            }
        } catch (Exception ex) {
            String msg = "Error to unmarshal from xml ["
                    + "mappingResource: " + mappingResource
                    + ", dataResource: " + dataResource + "]";
            throw new CastorException(msg, ex);
        }
    }

    public static void marshal(String mappingResource, String dataResource, Object marshalObj) throws MappingException {
        try {
// write it out as XML
            Mapping map = new Mapping();
            map.loadMapping(mappingResource);
            File file = new File(dataResource);
            Writer writer = new FileWriter(file);
            try {
                Marshaller marshaller = new Marshaller(writer);
                marshaller.setMapping(map);
                marshaller.marshal(marshalObj);
            } finally {
                writer.close();
            }

        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        } catch (MarshalException ex) {
            ex.printStackTrace(System.err);
        } catch (ValidationException ex) {
            ex.printStackTrace(System.err);
        }
    }

    public static void main(String[] argv) throws MappingException {
        ProtocolCommandItems CommandItems = new ProtocolCommandItems();
        ProtocolCommandItem CommandItem1 = new ProtocolCommandItem();
        CommandItem1.setCommandCode("10040001");
        CommandItem1.AddDataItem(new ProtocolDataItem("1004000101", 1, "BIN", 0,"终端数传机延时时间RTS","10"));
        CommandItem1.AddDataItem(new ProtocolDataItem("1004000102", 1, "BIN", 0,"终端作为启动站允许发送传输延时时间","15"));
        CommandItem1.AddDataItem(new ProtocolDataItem("1004000103", 1, "BIN", 0,"终端等待从动站响应的超时时间","10"));
        CommandItem1.AddDataItem(new ProtocolDataItem("1004000104", 1, "BIN", 0,"终端等待从动站响应的重发次数","3"));
        CommandItem1.AddDataItem(new ProtocolDataItem("1004000106", 1, "BS8", 0,"需要主站确认的通信服务（CON=1）的标志","00000111"));
        CommandItem1.AddDataItem(new ProtocolDataItem("1004000107", 1, "BIN", 0,"心跳周期","15"));

        ProtocolCommandItem CommandItem2 = new ProtocolCommandItem();
        CommandItem2.setCommandCode("10040003");
        CommandItem2.AddDataItem(new ProtocolDataItem("1004000301", 6, "IPPORT", 0,"主用IP地址和端口","127.0.0.1:80"));
        CommandItem2.AddDataItem(new ProtocolDataItem("1004000302", 6, "IPPORT", 0,"备用IP地址和端口","127.0.0.1:80"));
        CommandItem2.AddDataItem(new ProtocolDataItem("1004000303", 16, "ASCII", 0,"APN","cmdz.zj"));

        ProtocolCommandItem CommandItem3 = new ProtocolCommandItem();
        CommandItem3.setCommandCode("10040004");
        CommandItem3.AddDataItem(new ProtocolDataItem("1004000401", 8, "TEL", 0,"主站电话号码或主站手机号码","13147863416"));
        CommandItem3.AddDataItem(new ProtocolDataItem("1004000402", 8, "TEL", 0,"短信中心号码","123456"));

        ProtocolCommandItem CommandItem4 = new ProtocolCommandItem();
        CommandItem4.setCommandCode("10040005");
        CommandItem4.AddDataItem(new ProtocolDataItem("1004000501", 1, "BIN", 0,"消息认证方案号","0"));
        CommandItem4.AddDataItem(new ProtocolDataItem("1004000502", 2, "BIN", 0,"消息认证方案参数","1"));

        ProtocolCommandItem CommandItem5 = new ProtocolCommandItem();
        CommandItem5.setCommandCode("10040007");
        CommandItem5.AddDataItem(new ProtocolDataItem("1004000701", 4, "IP", 0,"终端IP地址","127.0.0.1"));
        CommandItem5.AddDataItem(new ProtocolDataItem("1004000702", 4, "IP", 0,"子网掩码地址","255.255.255.0"));
        CommandItem5.AddDataItem(new ProtocolDataItem("1004000703", 4, "IP", 0,"网关地址","127.0.0.1"));
        CommandItem5.AddDataItem(new ProtocolDataItem("1004000704", 1, "BIN", 0,"代理服务器代理类型","1"));
        CommandItem5.AddDataItem(new ProtocolDataItem("1004000705", 6, "IPPORT", 0,"代理服务器地址和端口","127.0.0.1:80"));
        CommandItem5.AddDataItem(new ProtocolDataItem("1004000706", 1, "BIN", 0,"代理服务器连接方式","1"));
        CommandItem5.AddDataItem(new ProtocolDataItem("1004000707", 1, "BIN", 0,"代理服务器用户名长度","10"));
        CommandItem5.AddDataItem(new ProtocolDataItem("1004000708", 20, "ASCII", 0,"代理服务器用户名","admin"));
        CommandItem5.AddDataItem(new ProtocolDataItem("1004000709", 1, "BIN", 0,"代理服务器密码长度","10"));
        CommandItem5.AddDataItem(new ProtocolDataItem("1004000710", 20, "ASCII", 0,"代理服务器密码","test"));
        CommandItem5.AddDataItem(new ProtocolDataItem("1004000711", 2, "BIN", 0,"终端侦听端口","80"));

        ProtocolCommandItem CommandItem6 = new ProtocolCommandItem();
        CommandItem6.setCommandCode("10040008");
        CommandItem6.AddDataItem(new ProtocolDataItem("1004000801", 1, "GROUP_BS8", 1,"工作模式（TCP/UDP）","0","0"));
        CommandItem6.AddDataItem(new ProtocolDataItem("1004000802", 1, "GROUP_BS8", 1,"备用","0","0"));
        CommandItem6.AddDataItem(new ProtocolDataItem("1004000803", 2, "GROUP_BS8", 2,"终端工作模式","01","0"));
        CommandItem6.AddDataItem(new ProtocolDataItem("1004000804", 2, "GROUP_BS8", 2,"备用","00","0"));
        CommandItem6.AddDataItem(new ProtocolDataItem("1004000805", 2, "GROUP_BS8", 2,"终端工作在客户机模式下的三种在线模式","01","1"));
        CommandItem6.AddDataItem(new ProtocolDataItem("1004000806", 2, "BIN", 0,"客户机模式下永久在线、时段在线模式重拨间隔","10"));
        CommandItem6.AddDataItem(new ProtocolDataItem("1004000807", 1, "BIN", 0,"被动激活模式重拨次数","3"));
        CommandItem6.AddDataItem(new ProtocolDataItem("1004000808", 1, "BIN", 0,"客户机模式下被动激活模式连续无通信自动断线时间","30"));
        CommandItem6.AddDataItem(new ProtocolDataItem("1004000809", 3, "BS24", 0,"客户机模式下时段在线模式允许在线时段标志","111111111111111111111111"));

        ProtocolCommandItem CommandItem7 = new ProtocolCommandItem();
        CommandItem7.setCommandCode("10040009");
        CommandItem7.AddDataItem(new ProtocolDataItem("1004000901", 8, "BS64", 0,"事件记录有效标志位","1111111111111111111111111111111111111111111111111111111111111111"));
        CommandItem7.AddDataItem(new ProtocolDataItem("1004000902", 8, "BS64", 0,"事件重要性等级标志位","1111111111111111111111111111111111111111111111111111111111111111"));

        ProtocolCommandItem CommandItem10 = new ProtocolCommandItem();
        CommandItem10.setCommandCode("10040010");

        CommandItem10.AddDataItem(new ProtocolDataItem("1004001001", 2, "BIN", 0,"本次电能表/交流采样装置配置数量","1"));
        CommandItem10.AddDataItem(new ProtocolDataItem("1004001002", 2, "BIN", 0,"电能表/交流采样装置序号","0"));
        CommandItem10.AddDataItem(new ProtocolDataItem("1004001003", 2, "BIN", 0,"所属测量点号","01"));
        CommandItem10.AddDataItem(new ProtocolDataItem("1004001004", 1, "GROUP_BIN", 3,"电能表/交流采样装置通信波特率","6","0"));
        CommandItem10.AddDataItem(new ProtocolDataItem("1004001005", 1, "GROUP_BIN", 5,"电能表/交流采样装置通信端口号","1","1"));
        CommandItem10.AddDataItem(new ProtocolDataItem("1004001006", 1, "BIN", 0,"电能表/交流采样装置通信协议类型","100"));
        CommandItem10.AddDataItem(new ProtocolDataItem("1004001007", 6, "A12", 0,"电能表/交流采样装置通信地址","0"));
        CommandItem10.AddDataItem(new ProtocolDataItem("1004001008", 6, "BIN", 0,"电能表/交流采样装置通信密码","0"));
        CommandItem10.AddDataItem(new ProtocolDataItem("1004001009", 2, "GROUP_BS8", 2,"备用","00","0"));
        CommandItem10.AddDataItem(new ProtocolDataItem("1004001010", 6, "GROUP_BS8", 6,"电能表/交流采样装置电能费率个数","000001","1"));
        CommandItem10.AddDataItem(new ProtocolDataItem("1004001011", 4, "GROUP_BS8", 4,"备用","0000","0"));
        CommandItem10.AddDataItem(new ProtocolDataItem("1004001012", 2, "GROUP_BS8", 2,"电能表/交流采样装置有功电能示值的整数位个数","00","0"));
        CommandItem10.AddDataItem(new ProtocolDataItem("1004001013", 2, "GROUP_BS8", 2,"电能表/交流采样装置有功电能示值的小数位个数","00","1"));
        CommandItem10.AddDataItem(new ProtocolDataItem("1004001014", 6, "A12", 0,"电能表/交流采样装置所属采集器通信地址","1"));
        CommandItem10.AddDataItem(new ProtocolDataItem("1004001015", 4, "GROUP_BS8", 4,"用户大类号","0000","0"));
        CommandItem10.AddDataItem(new ProtocolDataItem("1004001016", 4, "GROUP_BS8", 4,"及用户小类号","0000","1"));


        ProtocolCommandItem CommandItem8 = new ProtocolCommandItem();
        CommandItem8.setCommandCode("10040012");
        CommandItem8.AddDataItem(new ProtocolDataItem("1004001201", 1, "BS8", 0,"状态量接入标志位","11111111"));
        CommandItem8.AddDataItem(new ProtocolDataItem("1004001202", 1, "BS8", 0,"状态量属性标志位","11111111"));

        ProtocolCommandItem CommandItem9 = new ProtocolCommandItem();
        CommandItem9.setCommandCode("10040016");
        CommandItem9.AddDataItem(new ProtocolDataItem("1004001601", 32, "ASCII", 0,"虚拟专网用户名","cmdz"));
        CommandItem9.AddDataItem(new ProtocolDataItem("1004001602", 32, "ASCII", 0,"虚拟专网密码","cmdz.zj"));

        ProtocolCommandItem CommandItem11 = new ProtocolCommandItem();
        CommandItem11.setCommandCode("10040025");
        CommandItem11.AddDataItem(new ProtocolDataItem("1004002501", 2, "BIN", 0,"电压互感器倍率","1"));
        CommandItem11.AddDataItem(new ProtocolDataItem("1004002502", 2, "BIN", 0,"电流互感器倍率","1"));
        CommandItem11.AddDataItem(new ProtocolDataItem("1004002503", 2, "A7", 0,"额定电压","1"));
        CommandItem11.AddDataItem(new ProtocolDataItem("1004002504", 1, "A22", 0,"额定电流","1"));
        CommandItem11.AddDataItem(new ProtocolDataItem("1004002505", 3, "A23", 0,"额定负荷","1"));
        CommandItem11.AddDataItem(new ProtocolDataItem("1004002506", 4, "GROUP_BS8", 4,"备用","0000","0"));
        CommandItem11.AddDataItem(new ProtocolDataItem("1004002505", 2, "GROUP_BS8", 2,"单相表接线相","00","0"));
        CommandItem11.AddDataItem(new ProtocolDataItem("1004002506", 2, "GROUP_BS8", 2,"电源接线方式","01","1"));


        ProtocolCommandItem CommandItem12 = new ProtocolCommandItem();
        CommandItem12.setCommandCode("100C0002");
        CommandItem12.AddDataItem(new ProtocolDataItem("100C000201", 6, "A1", 0,"终端日历时钟",""));

        ProtocolCommandItem CommandItem13 = new ProtocolCommandItem();
        CommandItem13.setCommandCode("100C0003");
        CommandItem13.AddDataItem(new ProtocolDataItem("100C000301", 31, "BS248", 0,"终端参数状态",""));

        ProtocolCommandItem CommandItem14 = new ProtocolCommandItem();
        CommandItem14.setCommandCode("100C0004");
        CommandItem14.AddDataItem(new ProtocolDataItem("100C000401", 2, "GROUP_BS8", 2,"备用","00","0"));
        CommandItem14.AddDataItem(new ProtocolDataItem("100C000402", 2, "GROUP_BS8", 2,"备用","00","0"));
        CommandItem14.AddDataItem(new ProtocolDataItem("100C000403", 2, "GROUP_BS8", 2,"允许∕禁止通话","01","0"));
        CommandItem14.AddDataItem(new ProtocolDataItem("100C000404", 2, "GROUP_BS8", 2,"允许∕禁止主动上报","01","1"));

        ProtocolCommandItem CommandItem15 = new ProtocolCommandItem();
        CommandItem15.setCommandCode("100C0007");
        CommandItem15.AddDataItem(new ProtocolDataItem("100C000701", 1, "BIN", 0,"重要事件计数器EC1值",""));
        CommandItem15.AddDataItem(new ProtocolDataItem("100C000702", 1, "BIN", 0,"一般事件计数器EC2值",""));

        ProtocolCommandItem CommandItem16 = new ProtocolCommandItem();
        CommandItem16.setCommandCode("100C0008");
        CommandItem16.AddDataItem(new ProtocolDataItem("100C000801", 8, "BS64", 0,"终端事件标志状态",""));

        ProtocolCommandItem CommandItem18 = new ProtocolCommandItem();
        CommandItem18.setCommandCode("100C0010");
        CommandItem18.AddDataItem(new ProtocolDataItem("100C001001", 4, "BIN", 0,"终端与主站当日通信流量",""));
        CommandItem18.AddDataItem(new ProtocolDataItem("100C001002", 4, "BIN", 0,"终端与主站当月通信流量",""));

        ProtocolCommandItem CommandItem19 = new ProtocolCommandItem();
        CommandItem19.setCommandCode("100C0025");
        CommandItem19.AddDataItem(new ProtocolDataItem("F000", 5, "A15", 0,"终端抄表时间",""));

        CommandItem19.AddDataItem(new ProtocolDataItem("2300", 3, "A9", 0,"当前总有功功率",""));
        CommandItem19.AddDataItem(new ProtocolDataItem("2301", 3, "A9", 0,"当前A相有功功率",""));
        CommandItem19.AddDataItem(new ProtocolDataItem("2302", 3, "A9", 0,"当前B相有功功率",""));
        CommandItem19.AddDataItem(new ProtocolDataItem("2303", 3, "A9", 0,"当前C相有功功率",""));
        CommandItem19.AddDataItem(new ProtocolDataItem("2400", 3, "A9", 0,"当前总无功功率",""));
        CommandItem19.AddDataItem(new ProtocolDataItem("2401", 3, "A9", 0,"当前A相无功功率",""));
        CommandItem19.AddDataItem(new ProtocolDataItem("2402", 3, "A9", 0,"当前B相无功功率",""));
        CommandItem19.AddDataItem(new ProtocolDataItem("2403", 3, "A9", 0,"当前C相无功功率",""));

        CommandItem19.AddDataItem(new ProtocolDataItem("2600", 2, "A5", 0,"当前总功率因数",""));
        CommandItem19.AddDataItem(new ProtocolDataItem("2601", 2, "A5", 0,"当前A相功率因数",""));
        CommandItem19.AddDataItem(new ProtocolDataItem("2602", 2, "A5", 0,"当前B相功率因数",""));
        CommandItem19.AddDataItem(new ProtocolDataItem("2603", 2, "A5", 0,"当前C相功率因数",""));

        CommandItem19.AddDataItem(new ProtocolDataItem("2101", 2, "A7", 0,"当前A相电压",""));
        CommandItem19.AddDataItem(new ProtocolDataItem("2102", 2, "A7", 0,"当前B相电压",""));
        CommandItem19.AddDataItem(new ProtocolDataItem("2103", 2, "A7", 0,"当前C相电压",""));

        CommandItem19.AddDataItem(new ProtocolDataItem("2201", 2, "A25", 0,"当前A相电流",""));
        CommandItem19.AddDataItem(new ProtocolDataItem("2202", 2, "A25", 0,"当前B相电流",""));
        CommandItem19.AddDataItem(new ProtocolDataItem("2203", 2, "A25", 0,"当前C相电流",""));
        CommandItem19.AddDataItem(new ProtocolDataItem("2204", 2, "A25", 0,"当前零序电流",""));

        CommandItem19.AddDataItem(new ProtocolDataItem("2500", 2, "A9", 0,"当前总视在功率",""));
        CommandItem19.AddDataItem(new ProtocolDataItem("2501", 2, "A9", 0,"当前A相视在功率",""));
        CommandItem19.AddDataItem(new ProtocolDataItem("2502", 2, "A9", 0,"当前B相视在功率",""));
        CommandItem19.AddDataItem(new ProtocolDataItem("2503", 2, "A9", 0,"当前C相视在功率",""));

        ProtocolCommandItem CommandItem20 = new ProtocolCommandItem();
        CommandItem20.setCommandCode("100C0073");
        CommandItem20.AddDataItem(new ProtocolDataItem("100C007301", 2, "A2", 0,"直流模拟量实时数据",""));


        CommandItems.AddCommandItem(CommandItem1);
        CommandItems.AddCommandItem(CommandItem2);
        CommandItems.AddCommandItem(CommandItem3);
        CommandItems.AddCommandItem(CommandItem4);
        CommandItems.AddCommandItem(CommandItem5);
        CommandItems.AddCommandItem(CommandItem6);
        CommandItems.AddCommandItem(CommandItem7);
        CommandItems.AddCommandItem(CommandItem8);
        CommandItems.AddCommandItem(CommandItem9);
        CommandItems.AddCommandItem(CommandItem10);
        CommandItems.AddCommandItem(CommandItem11);
        CommandItems.AddCommandItem(CommandItem12);
        CommandItems.AddCommandItem(CommandItem13);
        CommandItems.AddCommandItem(CommandItem14);
        CommandItems.AddCommandItem(CommandItem15);
        CommandItems.AddCommandItem(CommandItem16);
        CommandItems.AddCommandItem(CommandItem18);
        CommandItems.AddCommandItem(CommandItem19);
        CommandItems.AddCommandItem(CommandItem20);
        try {
// write it out as XML
            Mapping map = new Mapping();
            String classPath = ProtocolConfig.class.getResource("protocol-data-config-mapping.xml").getPath();
            map.loadMapping(classPath);
            File file = new File(ProtocolConfig.class.getResource("protocol-data-config.xml").getPath());
            Writer writer = new FileWriter(file);
            Marshaller marshaller = new Marshaller(writer);
            marshaller.setMapping(map);
            marshaller.marshal(CommandItems);

            Reader reader = new FileReader(file);
            Unmarshaller unmarshaller = new Unmarshaller(map);
            ProtocolCommandItems read = (ProtocolCommandItems) unmarshaller.unmarshal(reader);

            //   Map<String, ProtocolCommandItem> commandItems = read.getCommandItems();
            //    Iterator it = commandItems.entrySet().iterator() ;
            /*
            while(it.hasNext())
            {
            Map.Entry entry = (Map.Entry) it.next() ;
            ProtocolCommandItem  commandItem = (ProtocolCommandItem)entry.getValue();
            System.out.println("CommandItem Code "+commandItem.getCommandCode());

            List<ProtocolDataItem> dataItems = commandItem.getDataItems();

            Iterator it1 = dataItems.entrySet().iterator() ;
            while(it1.hasNext())
            {
            Map.Entry entry1 = (Map.Entry) it1.next() ;
            ProtocolDataItem  dataItem  =(ProtocolDataItem) entry1.getValue();
            System.out.println("DataItem Code: "+dataItem.getDataItemCode()
            +" Length:"+dataItem.getLength()
            +" Format:"+dataItem.getFormat()
            );
            }

            }
             */

        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        } catch (MarshalException ex) {
            ex.printStackTrace(System.err);
        } catch (ValidationException ex) {
            ex.printStackTrace(System.err);
        }
    }
}
