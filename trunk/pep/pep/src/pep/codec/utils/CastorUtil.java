/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.codec.utils;

import java.io.*;
import java.net.URI;
import java.net.URL;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.*;
import pep.bp.realinterface.conf.ProtocolCommandItem;
import pep.bp.realinterface.conf.ProtocolCommandItems;
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

    public static Object unmarshal(URL mappingResource, URI dataResource) {
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
        CommandItem4.AddDataItem(new ProtocolDataItem("1004000502", 2, "HEX", 0,"消息认证方案参数","1"));

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
        CommandItem6.AddDataItem(new ProtocolDataItem("1004000803", 1, "GROUP_BS8", 2,"终端工作模式","01","0"));
        CommandItem6.AddDataItem(new ProtocolDataItem("1004000804", 1, "GROUP_BS8", 2,"备用","00","0"));
        CommandItem6.AddDataItem(new ProtocolDataItem("1004000805", 1, "GROUP_BS8", 2,"终端工作在客户机模式下的三种在线模式","01","1"));
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
        CommandItem10.AddDataItem(new ProtocolDataItem("1004001008", 6, "HEX", 0,"电能表/交流采样装置通信密码","0"));
        CommandItem10.AddDataItem(new ProtocolDataItem("1004001009", 1, "GROUP_BS8", 2,"备用","00","0"));
        CommandItem10.AddDataItem(new ProtocolDataItem("1004001010", 1, "GROUP_BS8", 6,"电能表/交流采样装置电能费率个数","000001","1"));
        CommandItem10.AddDataItem(new ProtocolDataItem("1004001011", 1, "GROUP_BS8", 4,"备用","0000","0"));
        CommandItem10.AddDataItem(new ProtocolDataItem("1004001012", 1, "GROUP_BS8", 2,"电能表/交流采样装置有功电能示值的整数位个数","00","0"));
        CommandItem10.AddDataItem(new ProtocolDataItem("1004001013", 1, "GROUP_BS8", 2,"电能表/交流采样装置有功电能示值的小数位个数","00","1"));
        CommandItem10.AddDataItem(new ProtocolDataItem("1004001014", 6, "A12", 0,"电能表/交流采样装置所属采集器通信地址","1"));
        CommandItem10.AddDataItem(new ProtocolDataItem("1004001015", 1, "GROUP_BS8", 4,"用户大类号","0000","0"));
        CommandItem10.AddDataItem(new ProtocolDataItem("1004001016", 1, "GROUP_BS8", 4,"及用户小类号","0000","1"));


        ProtocolCommandItem CommandItem8 = new ProtocolCommandItem();
        CommandItem8.setCommandCode("10040012");
        CommandItem8.AddDataItem(new ProtocolDataItem("1004001201", 1, "BS8", 0,"状态量接入标志位","11111111"));
        CommandItem8.AddDataItem(new ProtocolDataItem("1004001202", 1, "BS8", 0,"状态量属性标志位","11111111"));

        ProtocolCommandItem CommandItem9 = new ProtocolCommandItem();
        CommandItem9.setCommandCode("10040016");
        CommandItem9.AddDataItem(new ProtocolDataItem("1004001601", 32, "ASCII", 0,"虚拟专网用户名","cmdz"));
        CommandItem9.AddDataItem(new ProtocolDataItem("1004001602", 32, "ASCII", 0,"虚拟专网密码","cmdz.zj"));

        ProtocolCommandItem CommandItem17 = new ProtocolCommandItem();
        CommandItem17.setCommandCode("10040017");
        CommandItem17.AddDataItem(new ProtocolDataItem("1004001701", 2, "A2", 0,"终端保安定值","100"));


        ProtocolCommandItem CommandItem11 = new ProtocolCommandItem();
        CommandItem11.setCommandCode("10040025");
        CommandItem11.AddDataItem(new ProtocolDataItem("1004002501", 2, "BIN", 0,"电压互感器倍率","1"));
        CommandItem11.AddDataItem(new ProtocolDataItem("1004002502", 2, "BIN", 0,"电流互感器倍率","1"));
        CommandItem11.AddDataItem(new ProtocolDataItem("1004002503", 2, "A7", 0,"额定电压","220"));
        CommandItem11.AddDataItem(new ProtocolDataItem("1004002504", 1, "A22", 0,"额定电流","50"));
        CommandItem11.AddDataItem(new ProtocolDataItem("1004002505", 3, "A23", 0,"额定负荷","500"));
        CommandItem11.AddDataItem(new ProtocolDataItem("1004002506", 1, "GROUP_BS8", 4,"备用","0000","0"));
        CommandItem11.AddDataItem(new ProtocolDataItem("1004002507", 1, "GROUP_BS8", 2,"单相表接线相","00","0"));
        CommandItem11.AddDataItem(new ProtocolDataItem("1004002508", 1, "GROUP_BS8", 2,"电源接线方式","01","1"));

        ProtocolCommandItem CommandItem12 = new ProtocolCommandItem();
        CommandItem12.setCommandCode("10040026");
        CommandItem12.AddDataItem(new ProtocolDataItem("1004002601", 2, "A7", 0,"电压合格上限","100"));
        CommandItem12.AddDataItem(new ProtocolDataItem("1004002602", 2, "A7", 0,"电压合格下限","80"));
        CommandItem12.AddDataItem(new ProtocolDataItem("1004002603", 2, "A7", 0,"电压断相门限","1"));
        CommandItem12.AddDataItem(new ProtocolDataItem("1004002604", 2, "A7", 0,"电压上上限（过压门限）","1"));
        CommandItem12.AddDataItem(new ProtocolDataItem("1004002605", 1, "BIN", 0,"越限持续时间","1"));
        CommandItem12.AddDataItem(new ProtocolDataItem("1004002606", 2, "A5", 0,"越限恢复系数","1"));
        CommandItem12.AddDataItem(new ProtocolDataItem("1004002607", 2, "A7", 0,"电压下下限（欠压门限）","1"));
        CommandItem12.AddDataItem(new ProtocolDataItem("1004002608", 1, "BIN", 0,"越限持续时间","1"));
        CommandItem12.AddDataItem(new ProtocolDataItem("1004002609", 2, "A5", 0,"越限恢复系数","1"));

        CommandItem12.AddDataItem(new ProtocolDataItem("1004002610", 3, "A25", 0,"相电流上上限（过流门限）","1"));
        CommandItem12.AddDataItem(new ProtocolDataItem("1004002611", 1, "BIN", 0,"越限持续时间","1"));
        CommandItem12.AddDataItem(new ProtocolDataItem("1004002612", 2, "A5", 0,"越限恢复系数","1"));

        CommandItem12.AddDataItem(new ProtocolDataItem("1004002613", 3, "A25", 0,"相电流上限（额定电流门限）","1"));
        CommandItem12.AddDataItem(new ProtocolDataItem("1004002614", 1, "BIN", 0,"越限持续时间","1"));
        CommandItem12.AddDataItem(new ProtocolDataItem("1004002615", 2, "A5", 0,"越限恢复系数","1"));

        CommandItem12.AddDataItem(new ProtocolDataItem("1004002616", 3, "A25", 0,"零序电流上限","1"));
        CommandItem12.AddDataItem(new ProtocolDataItem("1004002617", 1, "BIN", 0,"越限持续时间","1"));
        CommandItem12.AddDataItem(new ProtocolDataItem("1004002618", 2, "A5", 0,"越限恢复系数","1"));

        CommandItem12.AddDataItem(new ProtocolDataItem("1004002619", 3, "A23", 0,"视在功率上上限","1"));
        CommandItem12.AddDataItem(new ProtocolDataItem("1004002620", 1, "BIN", 0,"越限持续时间","1"));
        CommandItem12.AddDataItem(new ProtocolDataItem("1004002621", 2, "A5", 0,"越限恢复系数","1"));

        CommandItem12.AddDataItem(new ProtocolDataItem("1004002622", 3, "A23", 0,"视在功率上限","1"));
        CommandItem12.AddDataItem(new ProtocolDataItem("1004002623", 1, "BIN", 0,"越限持续时间","1"));
        CommandItem12.AddDataItem(new ProtocolDataItem("1004002624", 2, "A5", 0,"越限恢复系数","1"));

        CommandItem12.AddDataItem(new ProtocolDataItem("1004002625", 2, "A5", 0,"三相电压不平衡限值","1"));
        CommandItem12.AddDataItem(new ProtocolDataItem("1004002626", 1, "BIN", 0,"越限持续时间","1"));
        CommandItem12.AddDataItem(new ProtocolDataItem("1004002627", 2, "A5", 0,"越限恢复系数","1"));

        CommandItem12.AddDataItem(new ProtocolDataItem("1004002628", 2, "A5", 0,"三相电流不平衡限值","1"));
        CommandItem12.AddDataItem(new ProtocolDataItem("1004002629", 1, "BIN", 0,"越限持续时间","1"));
        CommandItem12.AddDataItem(new ProtocolDataItem("1004002630", 2, "A5", 0,"越限恢复系数","1"));
        CommandItem12.AddDataItem(new ProtocolDataItem("1004002631", 1, "BIN", 0,"连续失压时间限值","1"));

        ProtocolCommandItem CommandItem13 = new ProtocolCommandItem();
        CommandItem13.setCommandCode("10040061");
        CommandItem13.AddDataItem(new ProtocolDataItem("1004006101", 1, "BS8", 0,"直流模拟量接入标志位","11111111"));

        ProtocolCommandItem CommandItem14 = new ProtocolCommandItem();
        CommandItem14.setCommandCode("10040081");
        CommandItem14.AddDataItem(new ProtocolDataItem("1004008101", 2, "A2", 0,"直流模拟量量程起始值","11111111"));
        CommandItem14.AddDataItem(new ProtocolDataItem("1004008102", 2, "A2", 0,"直流模拟量量程终止值","11111111"));

        ProtocolCommandItem CommandItem15 = new ProtocolCommandItem();
        CommandItem15.setCommandCode("10040082");
        CommandItem15.AddDataItem(new ProtocolDataItem("1004008201", 2, "A2", 0,"直流模拟量上限","100"));
        CommandItem15.AddDataItem(new ProtocolDataItem("1004008202", 2, "A2", 0,"直流模拟量下限","1"));

        ProtocolCommandItem CommandItem16 = new ProtocolCommandItem();
        CommandItem16.setCommandCode("10040083");
        CommandItem16.AddDataItem(new ProtocolDataItem("1004008301", 1, "BIN", 0,"直流模拟量冻结密度","24"));

        ProtocolCommandItem CommandItem22 = new ProtocolCommandItem();
        CommandItem22.setCommandCode("100C0002");
        CommandItem22.AddDataItem(new ProtocolDataItem("100C000201", 6, "A1", 0,"终端日历时钟",""));

        ProtocolCommandItem CommandItem23 = new ProtocolCommandItem();
        CommandItem23.setCommandCode("100C0003");
        CommandItem23.AddDataItem(new ProtocolDataItem("100C000301", 31, "BS248", 0,"终端参数状态",""));

        ProtocolCommandItem CommandItem24 = new ProtocolCommandItem();
        CommandItem24.setCommandCode("100C0004");
        CommandItem24.AddDataItem(new ProtocolDataItem("100C000401", 1, "GROUP_BS8", 2,"备用","00","0"));
        CommandItem24.AddDataItem(new ProtocolDataItem("100C000402", 1, "GROUP_BS8", 2,"备用","00","0"));
        CommandItem24.AddDataItem(new ProtocolDataItem("100C000403", 1, "GROUP_BS8", 2,"允许∕禁止通话","01","0"));
        CommandItem24.AddDataItem(new ProtocolDataItem("100C000404", 1, "GROUP_BS8", 2,"允许∕禁止主动上报","01","1"));

        ProtocolCommandItem CommandItem25 = new ProtocolCommandItem();
        CommandItem25.setCommandCode("100C0007");
        CommandItem25.AddDataItem(new ProtocolDataItem("100C000701", 1, "BIN", 0,"重要事件计数器EC1值",""));
        CommandItem25.AddDataItem(new ProtocolDataItem("100C000702", 1, "BIN", 0,"一般事件计数器EC2值",""));

        ProtocolCommandItem CommandItem26 = new ProtocolCommandItem();
        CommandItem26.setCommandCode("100C0008");
        CommandItem26.AddDataItem(new ProtocolDataItem("100C000801", 8, "BS64", 0,"终端事件标志状态",""));

        ProtocolCommandItem CommandItem28 = new ProtocolCommandItem();
        CommandItem28.setCommandCode("100C0010");
        CommandItem28.AddDataItem(new ProtocolDataItem("100C001001", 4, "BIN", 0,"终端与主站当日通信流量",""));
        CommandItem28.AddDataItem(new ProtocolDataItem("100C001002", 4, "BIN", 0,"终端与主站当月通信流量",""));

        ProtocolCommandItem CommandItem29 = new ProtocolCommandItem();
        CommandItem29.setCommandCode("100C0025");
        CommandItem29.AddDataItem(new ProtocolDataItem("F000", 5, "A15", 0,"终端抄表时间","","1"));

        CommandItem29.AddDataItem(new ProtocolDataItem("2300", 3, "A9", 0,"当前总有功功率",""));
        CommandItem29.AddDataItem(new ProtocolDataItem("2301", 3, "A9", 0,"当前A相有功功率",""));
        CommandItem29.AddDataItem(new ProtocolDataItem("2302", 3, "A9", 0,"当前B相有功功率",""));
        CommandItem29.AddDataItem(new ProtocolDataItem("2303", 3, "A9", 0,"当前C相有功功率",""));
        CommandItem29.AddDataItem(new ProtocolDataItem("2400", 3, "A9", 0,"当前总无功功率",""));
        CommandItem29.AddDataItem(new ProtocolDataItem("2401", 3, "A9", 0,"当前A相无功功率",""));
        CommandItem29.AddDataItem(new ProtocolDataItem("2402", 3, "A9", 0,"当前B相无功功率",""));
        CommandItem29.AddDataItem(new ProtocolDataItem("2403", 3, "A9", 0,"当前C相无功功率",""));

        CommandItem29.AddDataItem(new ProtocolDataItem("2600", 2, "A5", 0,"当前总功率因数",""));
        CommandItem29.AddDataItem(new ProtocolDataItem("2601", 2, "A5", 0,"当前A相功率因数",""));
        CommandItem29.AddDataItem(new ProtocolDataItem("2602", 2, "A5", 0,"当前B相功率因数",""));
        CommandItem29.AddDataItem(new ProtocolDataItem("2603", 2, "A5", 0,"当前C相功率因数",""));

        CommandItem29.AddDataItem(new ProtocolDataItem("2101", 2, "A7", 0,"当前A相电压",""));
        CommandItem29.AddDataItem(new ProtocolDataItem("2102", 2, "A7", 0,"当前B相电压",""));
        CommandItem29.AddDataItem(new ProtocolDataItem("2103", 2, "A7", 0,"当前C相电压",""));

        CommandItem29.AddDataItem(new ProtocolDataItem("2201", 3, "A25", 0,"当前A相电流",""));
        CommandItem29.AddDataItem(new ProtocolDataItem("2202", 3, "A25", 0,"当前B相电流",""));
        CommandItem29.AddDataItem(new ProtocolDataItem("2203", 3, "A25", 0,"当前C相电流",""));
        CommandItem29.AddDataItem(new ProtocolDataItem("2204", 3, "A25", 0,"当前零序电流",""));

        CommandItem29.AddDataItem(new ProtocolDataItem("2500", 3, "A9", 0,"当前总视在功率",""));
        CommandItem29.AddDataItem(new ProtocolDataItem("2501", 3, "A9", 0,"当前A相视在功率",""));
        CommandItem29.AddDataItem(new ProtocolDataItem("2502", 3, "A9", 0,"当前B相视在功率",""));
        CommandItem29.AddDataItem(new ProtocolDataItem("2503", 3, "A9", 0,"当前C相视在功率",""));

        ProtocolCommandItem CommandItem30 = new ProtocolCommandItem();
        CommandItem30.setCommandCode("100C0073");
        CommandItem30.AddDataItem(new ProtocolDataItem("100C007301", 2, "A2", 0,"直流模拟量实时数据",""));

        ProtocolCommandItem CommandItem31 = new ProtocolCommandItem();
        CommandItem31.setCommandCode("100C0129");
        CommandItem31.AddDataItem(new ProtocolDataItem("F000", 5, "A15", 0,"终端抄表时间","","1"));
        CommandItem31.AddDataItem(new ProtocolDataItem("F001", 1, "BIN", 0,"费率数M",""));
        CommandItem31.AddDataItem(new ProtocolDataItem("0100", 5, "A14", 0,"正向有功总电能示值",""));
        CommandItem31.AddDataItem(new ProtocolDataItem("0101", 5, "A14", 0,"费率1正向有功总电能示值",""));
        CommandItem31.AddDataItem(new ProtocolDataItem("0102", 5, "A14", 0,"费率2正向有功总电能示值",""));
        CommandItem31.AddDataItem(new ProtocolDataItem("0103", 5, "A14", 0,"费率3正向有功总电能示值",""));
        CommandItem31.AddDataItem(new ProtocolDataItem("0104", 5, "A14", 0,"费率4正向有功总电能示值",""));
//        CommandItem31.AddDataItem(new ProtocolDataItem("0105", 5, "A14", 0,"费率5正向有功总电能示值",""));
//        CommandItem31.AddDataItem(new ProtocolDataItem("0106", 5, "A14", 0,"费率6正向有功总电能示值",""));
//        CommandItem31.AddDataItem(new ProtocolDataItem("0107", 5, "A14", 0,"费率7正向有功总电能示值",""));
//        CommandItem31.AddDataItem(new ProtocolDataItem("0108", 5, "A14", 0,"费率8正向有功总电能示值",""));
//        CommandItem31.AddDataItem(new ProtocolDataItem("0109", 5, "A14", 0,"费率9正向有功总电能示值",""));
//        CommandItem31.AddDataItem(new ProtocolDataItem("010A", 5, "A14", 0,"费率10正向有功总电能示值",""));
//        CommandItem31.AddDataItem(new ProtocolDataItem("010B", 5, "A14", 0,"费率11正向有功总电能示值",""));
//        CommandItem31.AddDataItem(new ProtocolDataItem("010C", 5, "A14", 0,"费率12正向有功总电能示值",""));

        ProtocolCommandItem CommandItem68 = new ProtocolCommandItem();
        CommandItem68.setCommandCode("100C0130");
        CommandItem68.AddDataItem(new ProtocolDataItem("F000", 5, "A15", 0,"终端抄表时间","","1"));
        CommandItem68.AddDataItem(new ProtocolDataItem("F001", 1, "BIN", 0,"费率数M",""));
        CommandItem68.AddDataItem(new ProtocolDataItem("A000", 5, "A11", 0,"正向无功（组合无功1）总电能示值",""));
        CommandItem68.AddDataItem(new ProtocolDataItem("A001", 5, "A11", 0,"费率1正向无功（组合无功1）总电能示值",""));
        CommandItem68.AddDataItem(new ProtocolDataItem("A002", 5, "A11", 0,"费率2正向无功（组合无功1）总电能示值",""));
        CommandItem68.AddDataItem(new ProtocolDataItem("A003", 5, "A11", 0,"费率3正向无功（组合无功1）总电能示值",""));
        CommandItem68.AddDataItem(new ProtocolDataItem("A004", 5, "A11", 0,"费率4正向无功（组合无功1）总电能示值",""));
//        CommandItem68.AddDataItem(new ProtocolDataItem("A005", 5, "A11", 0,"费率5正向无功（组合无功1）总电能示值",""));
//        CommandItem68.AddDataItem(new ProtocolDataItem("A006", 5, "A11", 0,"费率6正向无功（组合无功1）总电能示值",""));
//        CommandItem68.AddDataItem(new ProtocolDataItem("A007", 5, "A11", 0,"费率7正向无功（组合无功1）总电能示值",""));
//        CommandItem68.AddDataItem(new ProtocolDataItem("A008", 5, "A11", 0,"费率8正向无功（组合无功1）总电能示值",""));
//        CommandItem68.AddDataItem(new ProtocolDataItem("A009", 5, "A11", 0,"费率9正向无功（组合无功1）总电能示值",""));
//        CommandItem68.AddDataItem(new ProtocolDataItem("A00A", 5, "A11", 0,"费率10正向无功（组合无功1）总电能示值",""));
//        CommandItem68.AddDataItem(new ProtocolDataItem("A00B", 5, "A11", 0,"费率11正向无功（组合无功1）总电能示值",""));
//        CommandItem68.AddDataItem(new ProtocolDataItem("A00C", 5, "A11", 0,"费率12正向无功（组合无功1）总电能示值",""));


        ProtocolCommandItem CommandItem72 = new ProtocolCommandItem();
        CommandItem72.setCommandCode("100C0132");
        CommandItem72.AddDataItem(new ProtocolDataItem("F000", 5, "A15", 0,"终端抄表时间","","1"));
        CommandItem72.AddDataItem(new ProtocolDataItem("F001", 1, "BIN", 0,"费率数M",""));
        CommandItem72.AddDataItem(new ProtocolDataItem("A000", 5, "A11", 0,"反向无功总电能示值",""));
        CommandItem72.AddDataItem(new ProtocolDataItem("A001", 5, "A11", 0,"费率1反向无功总电能示值",""));
        CommandItem72.AddDataItem(new ProtocolDataItem("A002", 5, "A11", 0,"费率2反向无功总电能示值",""));
        CommandItem72.AddDataItem(new ProtocolDataItem("A003", 5, "A11", 0,"费率3反向无功总电能示值",""));
        CommandItem72.AddDataItem(new ProtocolDataItem("A004", 5, "A11", 0,"费率4反向无功总电能示值",""));
//        CommandItem72.AddDataItem(new ProtocolDataItem("A005", 5, "A11", 0,"费率5反向无功总电能示值",""));
//        CommandItem72.AddDataItem(new ProtocolDataItem("A006", 5, "A11", 0,"费率6反向无功总电能示值",""));
//        CommandItem72.AddDataItem(new ProtocolDataItem("A007", 5, "A11", 0,"费率7反向无功总电能示值",""));
//        CommandItem72.AddDataItem(new ProtocolDataItem("A008", 5, "A11", 0,"费率8反向功总电能示值",""));
//        CommandItem72.AddDataItem(new ProtocolDataItem("A009", 5, "A11", 0,"费率9反向无功总电能示值",""));
//        CommandItem72.AddDataItem(new ProtocolDataItem("A00A", 5, "A11", 0,"费率10反向无功总电能示值",""));
//        CommandItem72.AddDataItem(new ProtocolDataItem("A00B", 5, "A11", 0,"费率11反向无功总电能示值",""));
//        CommandItem72.AddDataItem(new ProtocolDataItem("A00C", 5, "A11", 0,"费率12反向无功总电能示值",""));


        ProtocolCommandItem CommandItem73 = new ProtocolCommandItem();
        CommandItem73.setCommandCode("100C0131");
        CommandItem73.AddDataItem(new ProtocolDataItem("F000", 5, "A15", 0,"终端抄表时间","","1"));
        CommandItem73.AddDataItem(new ProtocolDataItem("F001", 1, "BIN", 0,"费率数M",""));
        CommandItem73.AddDataItem(new ProtocolDataItem("0200", 5, "A11", 0,"反向有功总电能示值",""));
        CommandItem73.AddDataItem(new ProtocolDataItem("0201", 5, "A11", 0,"费率1反向有功总电能示值",""));
        CommandItem73.AddDataItem(new ProtocolDataItem("0202", 5, "A11", 0,"费率2反向有功总电能示值",""));
        CommandItem73.AddDataItem(new ProtocolDataItem("0203", 5, "A11", 0,"费率3反向有功总电能示值",""));
        CommandItem73.AddDataItem(new ProtocolDataItem("0204", 5, "A11", 0,"费率4反向有功总电能示值",""));
//        CommandItem73.AddDataItem(new ProtocolDataItem("0205", 5, "A11", 0,"费率5反向有功总电能示值",""));
//        CommandItem73.AddDataItem(new ProtocolDataItem("0206", 5, "A11", 0,"费率6反向有功总电能示值",""));
//        CommandItem73.AddDataItem(new ProtocolDataItem("0207", 5, "A11", 0,"费率7反向有功总电能示值",""));
//        CommandItem73.AddDataItem(new ProtocolDataItem("0208", 5, "A11", 0,"费率8反向有功总电能示值",""));
//        CommandItem73.AddDataItem(new ProtocolDataItem("0209", 5, "A11", 0,"费率9反向有功总电能示值",""));
//        CommandItem73.AddDataItem(new ProtocolDataItem("020A", 5, "A11", 0,"费率10反向有功总电能示值",""));
//        CommandItem73.AddDataItem(new ProtocolDataItem("020B", 5, "A11", 0,"费率11反向有功总电能示值",""));
//        CommandItem73.AddDataItem(new ProtocolDataItem("020C", 5, "A11", 0,"费率12反向有功总电能示值",""));

        ProtocolCommandItem CommandItem74 = new ProtocolCommandItem();
        CommandItem74.setCommandCode("100D0081");
        CommandItem74.AddDataItem(new ProtocolDataItem("F011", 5, "A15", 0,"曲线类数据时标","","1"));
        CommandItem74.AddDataItem(new ProtocolDataItem("F002", 1, "BIN", 0,"曲线类数据密度",""));
        CommandItem74.AddDataItem(new ProtocolDataItem("F003", 1, "BIN", 0,"曲线类数据点数",""));
        CommandItem74.AddDataItem(new ProtocolDataItem("2300", 3, "A9", 0,"有功功率",""));

        ProtocolCommandItem CommandItem75 = new ProtocolCommandItem();
        CommandItem75.setCommandCode("100D0082");
        CommandItem75.AddDataItem(new ProtocolDataItem("F011", 5, "A15", 0,"曲线类数据时标","","1"));
        CommandItem75.AddDataItem(new ProtocolDataItem("F002", 1, "BIN", 0,"曲线类数据密度",""));
        CommandItem75.AddDataItem(new ProtocolDataItem("F003", 1, "BIN", 0,"曲线类数据点数",""));
        CommandItem75.AddDataItem(new ProtocolDataItem("2301", 3, "A9", 0,"A相有功功率",""));

        ProtocolCommandItem CommandItem76 = new ProtocolCommandItem();
        CommandItem76.setCommandCode("100D0083");
        CommandItem76.AddDataItem(new ProtocolDataItem("F011", 5, "A15", 0,"曲线类数据时标","","1"));
        CommandItem76.AddDataItem(new ProtocolDataItem("F002", 1, "BIN", 0,"曲线类数据密度",""));
        CommandItem76.AddDataItem(new ProtocolDataItem("F003", 1, "BIN", 0,"曲线类数据点数",""));
        CommandItem76.AddDataItem(new ProtocolDataItem("2302", 3, "A9", 0,"B相有功功率",""));

        ProtocolCommandItem CommandItem77 = new ProtocolCommandItem();
        CommandItem77.setCommandCode("100D0084");
        CommandItem77.AddDataItem(new ProtocolDataItem("F011", 5, "A15", 0,"曲线类数据时标","","1"));
        CommandItem77.AddDataItem(new ProtocolDataItem("F002", 1, "BIN", 0,"曲线类数据密度",""));
        CommandItem77.AddDataItem(new ProtocolDataItem("F003", 1, "BIN", 0,"曲线类数据点数",""));
        CommandItem77.AddDataItem(new ProtocolDataItem("2303", 3, "A9", 0,"C相有功功率",""));

        ProtocolCommandItem CommandItem78 = new ProtocolCommandItem();
        CommandItem78.setCommandCode("100D0085");
        CommandItem78.AddDataItem(new ProtocolDataItem("F011", 5, "A15", 0,"曲线类数据时标","","1"));
        CommandItem78.AddDataItem(new ProtocolDataItem("F002", 1, "BIN", 0,"曲线类数据密度",""));
        CommandItem78.AddDataItem(new ProtocolDataItem("F003", 1, "BIN", 0,"曲线类数据点数",""));
        CommandItem78.AddDataItem(new ProtocolDataItem("2400", 3, "A9", 0,"无功功率",""));

        ProtocolCommandItem CommandItem79 = new ProtocolCommandItem();
        CommandItem79.setCommandCode("100D0086");
        CommandItem79.AddDataItem(new ProtocolDataItem("F011", 5, "A15", 0,"曲线类数据时标","","1"));
        CommandItem79.AddDataItem(new ProtocolDataItem("F002", 1, "BIN", 0,"曲线类数据密度",""));
        CommandItem79.AddDataItem(new ProtocolDataItem("F003", 1, "BIN", 0,"曲线类数据点数",""));
        CommandItem79.AddDataItem(new ProtocolDataItem("2401", 3, "A9", 0,"A相无功功率",""));

        ProtocolCommandItem CommandItem80 = new ProtocolCommandItem();
        CommandItem80.setCommandCode("100D0087");
        CommandItem80.AddDataItem(new ProtocolDataItem("F011", 5, "A15", 0,"曲线类数据时标","","1"));
        CommandItem80.AddDataItem(new ProtocolDataItem("F002", 1, "BIN", 0,"曲线类数据密度",""));
        CommandItem80.AddDataItem(new ProtocolDataItem("F003", 1, "BIN", 0,"曲线类数据点数",""));
        CommandItem80.AddDataItem(new ProtocolDataItem("2401", 3, "A9", 0,"B相无功功率",""));

        ProtocolCommandItem CommandItem81 = new ProtocolCommandItem();
        CommandItem81.setCommandCode("100D0088");
        CommandItem81.AddDataItem(new ProtocolDataItem("F011", 5, "A15", 0,"曲线类数据时标","","1"));
        CommandItem81.AddDataItem(new ProtocolDataItem("F002", 1, "BIN", 0,"曲线类数据密度",""));
        CommandItem81.AddDataItem(new ProtocolDataItem("F003", 1, "BIN", 0,"曲线类数据点数",""));
        CommandItem81.AddDataItem(new ProtocolDataItem("2401", 3, "A9", 0,"C相无功功率",""));

        ProtocolCommandItem CommandItem82 = new ProtocolCommandItem();
        CommandItem82.setCommandCode("100D0089");
        CommandItem82.AddDataItem(new ProtocolDataItem("F011", 5, "A15", 0,"曲线类数据时标","","1"));
        CommandItem82.AddDataItem(new ProtocolDataItem("F002", 1, "BIN", 0,"曲线类数据密度",""));
        CommandItem82.AddDataItem(new ProtocolDataItem("F003", 1, "BIN", 0,"曲线类数据点数",""));
        CommandItem82.AddDataItem(new ProtocolDataItem("2101", 3, "A7", 0,"A相电压",""));

        ProtocolCommandItem CommandItem83 = new ProtocolCommandItem();
        CommandItem83.setCommandCode("100D0090");
        CommandItem83.AddDataItem(new ProtocolDataItem("F011", 5, "A15", 0,"曲线类数据时标","","1"));
        CommandItem83.AddDataItem(new ProtocolDataItem("F002", 1, "BIN", 0,"曲线类数据密度",""));
        CommandItem83.AddDataItem(new ProtocolDataItem("F003", 1, "BIN", 0,"曲线类数据点数",""));
        CommandItem83.AddDataItem(new ProtocolDataItem("2102", 3, "A7", 0,"B相电压",""));

        ProtocolCommandItem CommandItem84 = new ProtocolCommandItem();
        CommandItem84.setCommandCode("100D0091");
        CommandItem84.AddDataItem(new ProtocolDataItem("F011", 5, "A15", 0,"曲线类数据时标","","1"));
        CommandItem84.AddDataItem(new ProtocolDataItem("F002", 1, "BIN", 0,"曲线类数据密度",""));
        CommandItem84.AddDataItem(new ProtocolDataItem("F003", 1, "BIN", 0,"曲线类数据点数",""));
        CommandItem84.AddDataItem(new ProtocolDataItem("2103", 3, "A7", 0,"C相电压",""));

        ProtocolCommandItem CommandItem85 = new ProtocolCommandItem();
        CommandItem85.setCommandCode("100D0092");
        CommandItem85.AddDataItem(new ProtocolDataItem("F011", 5, "A15", 0,"曲线类数据时标","","1"));
        CommandItem85.AddDataItem(new ProtocolDataItem("F002", 1, "BIN", 0,"曲线类数据密度",""));
        CommandItem85.AddDataItem(new ProtocolDataItem("F003", 1, "BIN", 0,"曲线类数据点数",""));
        CommandItem85.AddDataItem(new ProtocolDataItem("2201", 3, "A25", 0,"A相电流",""));

        ProtocolCommandItem CommandItem86 = new ProtocolCommandItem();
        CommandItem86.setCommandCode("100D0093");
        CommandItem86.AddDataItem(new ProtocolDataItem("F011", 5, "A15", 0,"曲线类数据时标","","1"));
        CommandItem86.AddDataItem(new ProtocolDataItem("F002", 1, "BIN", 0,"曲线类数据密度",""));
        CommandItem86.AddDataItem(new ProtocolDataItem("F003", 1, "BIN", 0,"曲线类数据点数",""));
        CommandItem86.AddDataItem(new ProtocolDataItem("2202", 3, "A25", 0,"B相电流",""));

        ProtocolCommandItem CommandItem87 = new ProtocolCommandItem();
        CommandItem87.setCommandCode("100D0094");
        CommandItem87.AddDataItem(new ProtocolDataItem("F011", 5, "A15", 0,"曲线类数据时标","","1"));
        CommandItem87.AddDataItem(new ProtocolDataItem("F002", 1, "BIN", 0,"曲线类数据密度",""));
        CommandItem87.AddDataItem(new ProtocolDataItem("F003", 1, "BIN", 0,"曲线类数据点数",""));
        CommandItem87.AddDataItem(new ProtocolDataItem("2203", 3, "A25", 0,"C相电流",""));

        ProtocolCommandItem CommandItem88 = new ProtocolCommandItem();
        CommandItem88.setCommandCode("100D0095");
        CommandItem88.AddDataItem(new ProtocolDataItem("F011", 5, "A15", 0,"曲线类数据时标","","1"));
        CommandItem88.AddDataItem(new ProtocolDataItem("F002", 1, "BIN", 0,"曲线类数据密度",""));
        CommandItem88.AddDataItem(new ProtocolDataItem("F003", 1, "BIN", 0,"曲线类数据点数",""));
        CommandItem88.AddDataItem(new ProtocolDataItem("2204", 3, "A25", 0,"零序电流",""));

        ProtocolCommandItem CommandItem89 = new ProtocolCommandItem();
        CommandItem89.setCommandCode("100D0097");
        CommandItem89.AddDataItem(new ProtocolDataItem("F011", 5, "A15", 0,"曲线类数据时标","","1"));
        CommandItem89.AddDataItem(new ProtocolDataItem("F002", 1, "BIN", 0,"曲线类数据密度",""));
        CommandItem89.AddDataItem(new ProtocolDataItem("F003", 1, "BIN", 0,"曲线类数据点数",""));
        CommandItem89.AddDataItem(new ProtocolDataItem("A200", 3, "A13", 0,"正向有功总电能量",""));

        ProtocolCommandItem CommandItem90 = new ProtocolCommandItem();
        CommandItem90.setCommandCode("100D0098");
        CommandItem90.AddDataItem(new ProtocolDataItem("F011", 5, "A15", 0,"曲线类数据时标","","1"));
        CommandItem90.AddDataItem(new ProtocolDataItem("F002", 1, "BIN", 0,"曲线类数据密度",""));
        CommandItem90.AddDataItem(new ProtocolDataItem("F003", 1, "BIN", 0,"曲线类数据点数",""));
        CommandItem90.AddDataItem(new ProtocolDataItem("A400", 3, "A13", 0,"正向无功总电能量",""));

        ProtocolCommandItem CommandItem91 = new ProtocolCommandItem();
        CommandItem91.setCommandCode("100D0099");
        CommandItem91.AddDataItem(new ProtocolDataItem("F011", 5, "A15", 0,"曲线类数据时标","","1"));
        CommandItem91.AddDataItem(new ProtocolDataItem("F002", 1, "BIN", 0,"曲线类数据密度",""));
        CommandItem91.AddDataItem(new ProtocolDataItem("F003", 1, "BIN", 0,"曲线类数据点数",""));
        CommandItem91.AddDataItem(new ProtocolDataItem("A300", 3, "A13", 0,"反向有功总电能量",""));

        ProtocolCommandItem CommandItem92 = new ProtocolCommandItem();
        CommandItem92.setCommandCode("100D0100");
        CommandItem92.AddDataItem(new ProtocolDataItem("F011", 5, "A15", 0,"曲线类数据时标","","1"));
        CommandItem92.AddDataItem(new ProtocolDataItem("F002", 1, "BIN", 0,"曲线类数据密度",""));
        CommandItem92.AddDataItem(new ProtocolDataItem("F003", 1, "BIN", 0,"曲线类数据点数",""));
        CommandItem92.AddDataItem(new ProtocolDataItem("A500", 3, "A13", 0,"反向无功总电能量",""));

        //漏保数据项
        ProtocolCommandItem CommandItem32 = new ProtocolCommandItem("8000B611",new ProtocolDataItem("B611", 2, "A8", 0,"当前A相电压",""));
        ProtocolCommandItem CommandItem33 = new ProtocolCommandItem("8000B612",new ProtocolDataItem("B612", 2, "A8", 0,"当前B相电压",""));
        ProtocolCommandItem CommandItem34 = new ProtocolCommandItem("8000B613",new ProtocolDataItem("B613", 2, "A8", 0,"当前C相电压",""));
        ProtocolCommandItem CommandItem35 = new ProtocolCommandItem("8000B621",new ProtocolDataItem("B621", 2, "A8", 0,"当前A相电流",""));
        ProtocolCommandItem CommandItem36 = new ProtocolCommandItem("8000B622",new ProtocolDataItem("B622", 2, "A8", 0,"当前B相电流",""));
        ProtocolCommandItem CommandItem37 = new ProtocolCommandItem("8000B623",new ProtocolDataItem("B623", 2, "A8", 0,"当前C相电流",""));
        ProtocolCommandItem CommandItem38 = new ProtocolCommandItem("8000B660",new ProtocolDataItem("B660", 2, "A8", 0,"当前剩余电流",""));

        ProtocolCommandItem CommandItem39 = new ProtocolCommandItem();
        CommandItem39.setCommandCode("8000B66F");
        CommandItem39.AddDataItem(new ProtocolDataItem("B611", 2, "A8", 0,"当前A相电压",""));
        CommandItem39.AddDataItem(new ProtocolDataItem("B612", 2, "A8", 0,"当前B相电压",""));
        CommandItem39.AddDataItem(new ProtocolDataItem("B613", 2, "A8", 0,"当前C相电压",""));
        CommandItem39.AddDataItem(new ProtocolDataItem("B621", 2, "A8", 0,"当前A相电流",""));
        CommandItem39.AddDataItem(new ProtocolDataItem("B622", 2, "A8", 0,"当前B相电流",""));
        CommandItem39.AddDataItem(new ProtocolDataItem("B623", 2, "A8", 0,"当前C相电流",""));
        CommandItem39.AddDataItem(new ProtocolDataItem("B660", 2, "A8", 0,"当前剩余电流",""));

        ProtocolCommandItem CommandItem40 = new ProtocolCommandItem("8000C012",new ProtocolDataItem("C012", 2, "DATE_LOUBAO", 0,"漏电保护装置校时",""));

        ProtocolCommandItem CommandItem69 = new ProtocolCommandItem();
        CommandItem69.setCommandCode("8000C036");
        CommandItem69.AddDataItem(new ProtocolDataItem("8000C03601", 1, "HEX", 1,"跳合闸操作","","0"));

        ProtocolCommandItem CommandItem70 = new ProtocolCommandItem();
        CommandItem70.setCommandCode("8000C037");

        ProtocolCommandItem CommandItem41 = new ProtocolCommandItem();
        CommandItem41.setCommandCode("8000C040");
        CommandItem41.AddDataItem(new ProtocolDataItem("C04001", 1, "GROUP_BS8", 1,"分合闸状态","","0"));
        CommandItem41.AddDataItem(new ProtocolDataItem("C04002", 1, "GROUP_BS8", 1,"锁死状态","","0"));
        CommandItem41.AddDataItem(new ProtocolDataItem("C04003", 1, "GROUP_BS8", 2,"相位","","0"));
        CommandItem41.AddDataItem(new ProtocolDataItem("C04004", 1, "GROUP_BS8", 4,"动作类型","","1"));
        CommandItem41.AddDataItem(new ProtocolDataItem("C04005", 2, "A8", 4,"动作值",""));
        CommandItem41.AddDataItem(new ProtocolDataItem("C04006", 4, "A1", 4,"动作时间",""));

        ProtocolCommandItem CommandItem42 = new ProtocolCommandItem("8000C041",new ProtocolDataItem("C041", 2, "A8", 0,"负载电流动作档位",""));
        ProtocolCommandItem CommandItem43 = new ProtocolCommandItem("8000C042",new ProtocolDataItem("C042", 2, "A8", 0,"剩余电流动作档位",""));
        ProtocolCommandItem CommandItem44 = new ProtocolCommandItem("8000C043",new ProtocolDataItem("C043", 2, "A8", 0,"剩余电流动作延时档位",""));
        ProtocolCommandItem CommandItem45 = new ProtocolCommandItem("8000C044",new ProtocolDataItem("C044", 2, "A8", 0,"开关功能启用设定字",""));
        ProtocolCommandItem CommandItem46 = new ProtocolCommandItem("8000C045",new ProtocolDataItem("C045", 2, "A8", 0,"产品类ID",""));

        ProtocolCommandItem CommandItem47 = new ProtocolCommandItem();
        CommandItem47.setCommandCode("8000C04F");
        CommandItem47.AddDataItem(new ProtocolDataItem("8000C04F01", 1, "GROUP_BS8", 1,"合闸/分闸","","0"));
        CommandItem47.AddDataItem(new ProtocolDataItem("8000C04F02", 1, "GROUP_BS8", 1,"是否锁死","","0"));
        CommandItem47.AddDataItem(new ProtocolDataItem("8000C04F03", 1, "GROUP_BS8", 2,"相位","","0"));
        CommandItem47.AddDataItem(new ProtocolDataItem("8000C04F04", 1, "GROUP_BS8", 4,"动作类型","","1"));
        CommandItem47.AddDataItem(new ProtocolDataItem("8000C04F05", 2, "A8", 0,"额定负载电流档位值",""));
        CommandItem47.AddDataItem(new ProtocolDataItem("8000C04F06", 1, "A29", 0,"剩余电流档位",""));
        CommandItem47.AddDataItem(new ProtocolDataItem("8000C04F07", 2, "A8", 0,"剩余电流当前档位值",""));
        CommandItem47.AddDataItem(new ProtocolDataItem("8000C04F08", 1, "A29", 0,"漏电分断延迟档位",""));
        CommandItem47.AddDataItem(new ProtocolDataItem("8000C04F09", 2, "A8", 0,"漏电分断延迟时间值",""));
        CommandItem47.AddDataItem(new ProtocolDataItem("8000C04F10", 2, "BS8", 0,"开关功能设定字",""));
        CommandItem47.AddDataItem(new ProtocolDataItem("8000C04F11", 1, "A29", 0,"保护器型号ID",""));
        CommandItem47.AddDataItem(new ProtocolDataItem("8000C04F12", 1, "BIN", 0,"保留字节",""));


        ProtocolCommandItem CommandItem71 = new ProtocolCommandItem();
        CommandItem71.setCommandCode("8001C04F");
        CommandItem71.AddDataItem(new ProtocolDataItem("8001C04F01", 1, "A29", 0,"保护器型号ID",""));
        CommandItem71.AddDataItem(new ProtocolDataItem("8001C04F02", 1, "BS8", 0,"有效定义",""));
        CommandItem71.AddDataItem(new ProtocolDataItem("8001C04F03", 2, "A8", 0,"额定负载电流档位值",""));
        CommandItem71.AddDataItem(new ProtocolDataItem("8001C04F04", 1, "A29", 0,"剩余电流档位",""));
        CommandItem71.AddDataItem(new ProtocolDataItem("8001C04F05", 1, "A29", 0,"漏电分断延迟档位",""));
        CommandItem71.AddDataItem(new ProtocolDataItem("8001C04F06", 1, "BS8", 0,"开关功能设定字",""));
        CommandItem71.AddDataItem(new ProtocolDataItem("8001C04F07", 1, "BIN", 0,"保留字节",""));


        //最近一次跳闸类型动作值及时间
        ProtocolCommandItem CommandItem48 = new ProtocolCommandItem();
        CommandItem48.setCommandCode("8000E510");
        CommandItem48.AddDataItem(new ProtocolDataItem("8000E51001", 1, "GROUP_BS8", 1,"分合闸状态","","0"));
        CommandItem48.AddDataItem(new ProtocolDataItem("8000E51002", 1, "GROUP_BS8", 1,"锁死状态","","0"));
        CommandItem48.AddDataItem(new ProtocolDataItem("8000E51003", 1, "GROUP_BS8", 2,"相位","","0"));
        CommandItem48.AddDataItem(new ProtocolDataItem("8000E51004", 1, "GROUP_BS8", 4,"动作类型","","1"));
        CommandItem48.AddDataItem(new ProtocolDataItem("8000E51005", 2, "A8", 4,"动作值",""));
        CommandItem48.AddDataItem(new ProtocolDataItem("8000E51006", 4, "A1", 4,"动作时间",""));

        ProtocolCommandItem CommandItem49 = new ProtocolCommandItem();
        CommandItem49.setCommandCode("8000E511");
        CommandItem49.AddDataItem(new ProtocolDataItem("8000E51101", 1, "GROUP_BS8", 1,"分合闸状态","","0"));
        CommandItem49.AddDataItem(new ProtocolDataItem("8000E51102", 1, "GROUP_BS8", 1,"锁死状态","","0"));
        CommandItem49.AddDataItem(new ProtocolDataItem("8000E51103", 1, "GROUP_BS8", 2,"相位","","0"));
        CommandItem49.AddDataItem(new ProtocolDataItem("8000E51104", 1, "GROUP_BS8", 4,"动作类型","","1"));
        CommandItem49.AddDataItem(new ProtocolDataItem("8000E51105", 2, "A8", 4,"动作值",""));
        CommandItem49.AddDataItem(new ProtocolDataItem("8000E51106", 4, "A1", 4,"动作时间",""));

        ProtocolCommandItem CommandItem50 = new ProtocolCommandItem();
        CommandItem50.setCommandCode("8000E512");
        CommandItem50.AddDataItem(new ProtocolDataItem("8000E51201", 1, "GROUP_BS8", 1,"分合闸状态","","0"));
        CommandItem50.AddDataItem(new ProtocolDataItem("8000E51202", 1, "GROUP_BS8", 1,"锁死状态","","0"));
        CommandItem50.AddDataItem(new ProtocolDataItem("8000E51203", 1, "GROUP_BS8", 2,"相位","","0"));
        CommandItem50.AddDataItem(new ProtocolDataItem("8000E51204", 1, "GROUP_BS8", 4,"动作类型","","1"));
        CommandItem50.AddDataItem(new ProtocolDataItem("8000E51205", 2, "A8", 4,"动作值",""));
        CommandItem50.AddDataItem(new ProtocolDataItem("8000E51206", 4, "A1", 4,"动作时间",""));

        ProtocolCommandItem CommandItem51 = new ProtocolCommandItem();
        CommandItem51.setCommandCode("8000E513");
        CommandItem51.AddDataItem(new ProtocolDataItem("8000E51301", 1, "GROUP_BS8", 1,"分合闸状态","","0"));
        CommandItem51.AddDataItem(new ProtocolDataItem("8000E51002", 1, "GROUP_BS8", 1,"锁死状态","","0"));
        CommandItem51.AddDataItem(new ProtocolDataItem("8000E51003", 1, "GROUP_BS8", 2,"相位","","0"));
        CommandItem51.AddDataItem(new ProtocolDataItem("8000E51004", 1, "GROUP_BS8", 4,"动作类型","","1"));
        CommandItem51.AddDataItem(new ProtocolDataItem("8000E51005", 2, "A8", 4,"动作值",""));
        CommandItem51.AddDataItem(new ProtocolDataItem("8000E51006", 4, "A1", 4,"动作时间",""));

        ProtocolCommandItem CommandItem52 = new ProtocolCommandItem();
        CommandItem52.setCommandCode("8000E514");
        CommandItem52.AddDataItem(new ProtocolDataItem("8000E51401", 1, "GROUP_BS8", 1,"分合闸状态","","0"));
        CommandItem52.AddDataItem(new ProtocolDataItem("8000E51402", 1, "GROUP_BS8", 1,"锁死状态","","0"));
        CommandItem52.AddDataItem(new ProtocolDataItem("8000E51403", 1, "GROUP_BS8", 2,"相位","","0"));
        CommandItem52.AddDataItem(new ProtocolDataItem("8000E51404", 1, "GROUP_BS8", 4,"动作类型","","1"));
        CommandItem52.AddDataItem(new ProtocolDataItem("8000E51405", 2, "A8", 4,"动作值",""));
        CommandItem52.AddDataItem(new ProtocolDataItem("8000E51406", 4, "A1", 4,"动作时间",""));

        ProtocolCommandItem CommandItem53 = new ProtocolCommandItem();
        CommandItem53.setCommandCode("8000E515");
        CommandItem53.AddDataItem(new ProtocolDataItem("8000E51501", 1, "GROUP_BS8", 1,"分合闸状态","","0"));
        CommandItem53.AddDataItem(new ProtocolDataItem("8000E51502", 1, "GROUP_BS8", 1,"锁死状态","","0"));
        CommandItem53.AddDataItem(new ProtocolDataItem("8000E51503", 1, "GROUP_BS8", 2,"相位","","0"));
        CommandItem53.AddDataItem(new ProtocolDataItem("8000E51504", 1, "GROUP_BS8", 4,"动作类型","","1"));
        CommandItem53.AddDataItem(new ProtocolDataItem("8000E51505", 2, "A8", 4,"动作值",""));
        CommandItem53.AddDataItem(new ProtocolDataItem("8000E51506", 4, "A1", 4,"动作时间",""));

        ProtocolCommandItem CommandItem54 = new ProtocolCommandItem();
        CommandItem54.setCommandCode("8000E516");
        CommandItem54.AddDataItem(new ProtocolDataItem("8000E51601", 1, "GROUP_BS8", 1,"分合闸状态","","0"));
        CommandItem54.AddDataItem(new ProtocolDataItem("8000E51602", 1, "GROUP_BS8", 1,"锁死状态","","0"));
        CommandItem54.AddDataItem(new ProtocolDataItem("8000E51603", 1, "GROUP_BS8", 2,"相位","","0"));
        CommandItem54.AddDataItem(new ProtocolDataItem("8000E51604", 1, "GROUP_BS8", 4,"动作类型","","1"));
        CommandItem54.AddDataItem(new ProtocolDataItem("8000E51605", 2, "A8", 4,"动作值",""));
        CommandItem54.AddDataItem(new ProtocolDataItem("8000E51606", 4, "A1", 4,"动作时间",""));

        ProtocolCommandItem CommandItem55 = new ProtocolCommandItem();
        CommandItem55.setCommandCode("8000E517");
        CommandItem55.AddDataItem(new ProtocolDataItem("8000E51701", 1, "GROUP_BS8", 1,"分合闸状态","","0"));
        CommandItem55.AddDataItem(new ProtocolDataItem("8000E51702", 1, "GROUP_BS8", 1,"锁死状态","","0"));
        CommandItem55.AddDataItem(new ProtocolDataItem("8000E51703", 1, "GROUP_BS8", 2,"相位","","0"));
        CommandItem55.AddDataItem(new ProtocolDataItem("8000E51704", 1, "GROUP_BS8", 4,"动作类型","","1"));
        CommandItem55.AddDataItem(new ProtocolDataItem("8000E51705", 2, "A8", 4,"动作值",""));
        CommandItem55.AddDataItem(new ProtocolDataItem("8000E51706", 4, "A1", 4,"动作时间",""));

        ProtocolCommandItem CommandItem56 = new ProtocolCommandItem();
        CommandItem56.setCommandCode("8000E518");
        CommandItem56.AddDataItem(new ProtocolDataItem("8000E51801", 1, "GROUP_BS8", 1,"分合闸状态","","0"));
        CommandItem56.AddDataItem(new ProtocolDataItem("8000E51802", 1, "GROUP_BS8", 1,"锁死状态","","0"));
        CommandItem56.AddDataItem(new ProtocolDataItem("8000E51803", 1, "GROUP_BS8", 2,"相位","","0"));
        CommandItem56.AddDataItem(new ProtocolDataItem("8000E51804", 1, "GROUP_BS8", 4,"动作类型","","1"));
        CommandItem56.AddDataItem(new ProtocolDataItem("8000E51805", 2, "A8", 4,"动作值",""));
        CommandItem56.AddDataItem(new ProtocolDataItem("8000E51806", 4, "A1", 4,"动作时间",""));

        ProtocolCommandItem CommandItem57 = new ProtocolCommandItem();
        CommandItem57.setCommandCode("8000E519");
        CommandItem57.AddDataItem(new ProtocolDataItem("8000E51901", 1, "GROUP_BS8", 1,"分合闸状态","","0"));
        CommandItem57.AddDataItem(new ProtocolDataItem("8000E51902", 1, "GROUP_BS8", 1,"锁死状态","","0"));
        CommandItem57.AddDataItem(new ProtocolDataItem("8000E51903", 1, "GROUP_BS8", 2,"相位","","0"));
        CommandItem57.AddDataItem(new ProtocolDataItem("8000E51904", 1, "GROUP_BS8", 4,"动作类型","","1"));
        CommandItem57.AddDataItem(new ProtocolDataItem("8000E51905", 2, "A8", 4,"动作值",""));
        CommandItem57.AddDataItem(new ProtocolDataItem("8000E51906", 4, "A1", 4,"动作时间",""));

        ProtocolCommandItem CommandItem58 = new ProtocolCommandItem();
        CommandItem58.setCommandCode("8000E51A");
        CommandItem58.AddDataItem(new ProtocolDataItem("8000E51A01", 1, "GROUP_BS8", 1,"分合闸状态","","0"));
        CommandItem58.AddDataItem(new ProtocolDataItem("8000E51A02", 1, "GROUP_BS8", 1,"锁死状态","","0"));
        CommandItem58.AddDataItem(new ProtocolDataItem("8000E51A03", 1, "GROUP_BS8", 2,"相位","","0"));
        CommandItem58.AddDataItem(new ProtocolDataItem("8000E51A04", 1, "GROUP_BS8", 4,"动作类型","","1"));
        CommandItem58.AddDataItem(new ProtocolDataItem("8000E51A05", 2, "A8", 4,"动作值",""));
        CommandItem58.AddDataItem(new ProtocolDataItem("8000E51A06", 4, "A1", 4,"动作时间",""));

        ProtocolCommandItem CommandItem59 = new ProtocolCommandItem();
        CommandItem59.setCommandCode("8000E51B");
        CommandItem59.AddDataItem(new ProtocolDataItem("8000E51B01", 1, "GROUP_BS8", 1,"分合闸状态","","0"));
        CommandItem59.AddDataItem(new ProtocolDataItem("8000E51B02", 1, "GROUP_BS8", 1,"锁死状态","","0"));
        CommandItem59.AddDataItem(new ProtocolDataItem("8000E51B03", 1, "GROUP_BS8", 2,"相位","","0"));
        CommandItem59.AddDataItem(new ProtocolDataItem("8000E51B04", 1, "GROUP_BS8", 4,"动作类型","","1"));
        CommandItem59.AddDataItem(new ProtocolDataItem("8000E51B05", 2, "A8", 4,"动作值",""));
        CommandItem59.AddDataItem(new ProtocolDataItem("8000E51B06", 4, "A1", 4,"动作时间",""));

        ProtocolCommandItem CommandItem60 = new ProtocolCommandItem();
        CommandItem60.setCommandCode("8000E51C");
        CommandItem60.AddDataItem(new ProtocolDataItem("8000E51C01", 1, "GROUP_BS8", 1,"分合闸状态","","0"));
        CommandItem60.AddDataItem(new ProtocolDataItem("8000E51C02", 1, "GROUP_BS8", 1,"锁死状态","","0"));
        CommandItem60.AddDataItem(new ProtocolDataItem("8000E51C03", 1, "GROUP_BS8", 2,"相位","","0"));
        CommandItem60.AddDataItem(new ProtocolDataItem("8000E51C04", 1, "GROUP_BS8", 4,"动作类型","","1"));
        CommandItem60.AddDataItem(new ProtocolDataItem("8000E51C05", 2, "A8", 4,"动作值",""));
        CommandItem60.AddDataItem(new ProtocolDataItem("8000E51C06", 4, "A1", 4,"动作时间",""));

        ProtocolCommandItem CommandItem61 = new ProtocolCommandItem();
        CommandItem61.setCommandCode("8000E51D");
        CommandItem61.AddDataItem(new ProtocolDataItem("8000E51D01", 1, "GROUP_BS8", 1,"分合闸状态","","0"));
        CommandItem61.AddDataItem(new ProtocolDataItem("8000E51D02", 1, "GROUP_BS8", 1,"锁死状态","","0"));
        CommandItem61.AddDataItem(new ProtocolDataItem("8000E51D03", 1, "GROUP_BS8", 2,"相位","","0"));
        CommandItem61.AddDataItem(new ProtocolDataItem("8000E51D04", 1, "GROUP_BS8", 4,"动作类型","","1"));
        CommandItem61.AddDataItem(new ProtocolDataItem("8000E51D05", 2, "A8", 4,"动作值",""));
        CommandItem61.AddDataItem(new ProtocolDataItem("8000E51D06", 4, "A1", 4,"动作时间",""));

        ProtocolCommandItem CommandItem62 = new ProtocolCommandItem();
        CommandItem62.setCommandCode("8000E51E");
        CommandItem62.AddDataItem(new ProtocolDataItem("8000E51E01", 1, "GROUP_BS8", 1,"分合闸状态","","0"));
        CommandItem62.AddDataItem(new ProtocolDataItem("8000E51E02", 1, "GROUP_BS8", 1,"锁死状态","","0"));
        CommandItem62.AddDataItem(new ProtocolDataItem("8000E51E03", 1, "GROUP_BS8", 2,"相位","","0"));
        CommandItem62.AddDataItem(new ProtocolDataItem("8000E51E04", 1, "GROUP_BS8", 4,"动作类型","","1"));
        CommandItem62.AddDataItem(new ProtocolDataItem("8000E51E05", 2, "A8", 4,"动作值",""));
        CommandItem62.AddDataItem(new ProtocolDataItem("8000E51E06", 4, "A1", 4,"动作时间",""));

        ProtocolCommandItem CommandItem63 = new ProtocolCommandItem();
        CommandItem63.setCommandCode("8000E520");
        CommandItem63.AddDataItem(new ProtocolDataItem("8000E52001", 1, "GROUP_BS8", 1,"分合闸状态","","0"));
        CommandItem63.AddDataItem(new ProtocolDataItem("8000E52002", 1, "GROUP_BS8", 1,"锁死状态","","0"));
        CommandItem63.AddDataItem(new ProtocolDataItem("8000E52003", 1, "GROUP_BS8", 2,"相位","","0"));
        CommandItem63.AddDataItem(new ProtocolDataItem("8000E52004", 1, "GROUP_BS8", 4,"动作类型","","1"));
        CommandItem63.AddDataItem(new ProtocolDataItem("8000E52005", 2, "A8", 4,"动作值",""));
        CommandItem63.AddDataItem(new ProtocolDataItem("8000E52006", 4, "A1", 4,"动作时间",""));

        ProtocolCommandItem CommandItem64 = new ProtocolCommandItem();
        CommandItem64.setCommandCode("8000E521");
        CommandItem64.AddDataItem(new ProtocolDataItem("8000E52101", 1, "GROUP_BS8", 1,"分合闸状态","","0"));
        CommandItem64.AddDataItem(new ProtocolDataItem("8000E52102", 1, "GROUP_BS8", 1,"锁死状态","","0"));
        CommandItem64.AddDataItem(new ProtocolDataItem("8000E52103", 1, "GROUP_BS8", 2,"相位","","0"));
        CommandItem64.AddDataItem(new ProtocolDataItem("8000E52104", 1, "GROUP_BS8", 4,"动作类型","","1"));
        CommandItem64.AddDataItem(new ProtocolDataItem("8000E52105", 2, "A8", 4,"动作值",""));
        CommandItem64.AddDataItem(new ProtocolDataItem("8000E52106", 4, "A1", 4,"动作时间",""));

        ProtocolCommandItem CommandItem65 = new ProtocolCommandItem();
        CommandItem65.setCommandCode("8000E522");
        CommandItem65.AddDataItem(new ProtocolDataItem("8000E52201", 1, "GROUP_BS8", 1,"分合闸状态","","0"));
        CommandItem65.AddDataItem(new ProtocolDataItem("8000E52202", 1, "GROUP_BS8", 1,"锁死状态","","0"));
        CommandItem65.AddDataItem(new ProtocolDataItem("8000E52203", 1, "GROUP_BS8", 2,"相位","","0"));
        CommandItem65.AddDataItem(new ProtocolDataItem("8000E52204", 1, "GROUP_BS8", 4,"动作类型","","1"));
        CommandItem65.AddDataItem(new ProtocolDataItem("8000E52205", 2, "A8", 4,"动作值",""));
        CommandItem65.AddDataItem(new ProtocolDataItem("8000E52206", 4, "A1", 4,"动作时间",""));

        ProtocolCommandItem CommandItem66 = new ProtocolCommandItem();
        CommandItem66.setCommandCode("8000E523");
        CommandItem66.AddDataItem(new ProtocolDataItem("8000E52301", 1, "GROUP_BS8", 1,"分合闸状态","","0"));
        CommandItem66.AddDataItem(new ProtocolDataItem("8000E52302", 1, "GROUP_BS8", 1,"锁死状态","","0"));
        CommandItem66.AddDataItem(new ProtocolDataItem("8000E52303", 1, "GROUP_BS8", 2,"相位","","0"));
        CommandItem66.AddDataItem(new ProtocolDataItem("8000E52304", 1, "GROUP_BS8", 4,"动作类型","","1"));
        CommandItem66.AddDataItem(new ProtocolDataItem("8000E52305", 2, "A8", 4,"动作值",""));
        CommandItem66.AddDataItem(new ProtocolDataItem("8000E52306", 4, "A1", 4,"动作时间",""));

        ProtocolCommandItem CommandItem67 = new ProtocolCommandItem();
        CommandItem67.setCommandCode("8000E524");
        CommandItem67.AddDataItem(new ProtocolDataItem("8000E52401", 1, "GROUP_BS8", 1,"分合闸状态","","0"));
        CommandItem67.AddDataItem(new ProtocolDataItem("8000E52402", 1, "GROUP_BS8", 1,"锁死状态","","0"));
        CommandItem67.AddDataItem(new ProtocolDataItem("8000E52403", 1, "GROUP_BS8", 2,"相位","","0"));
        CommandItem67.AddDataItem(new ProtocolDataItem("8000E52404", 1, "GROUP_BS8", 4,"动作类型","","1"));
        CommandItem67.AddDataItem(new ProtocolDataItem("8000E52405", 2, "A8", 4,"动作值",""));
        CommandItem67.AddDataItem(new ProtocolDataItem("8000E52406", 4, "A1", 4,"动作时间",""));

        CommandItems.AddCommandItem(CommandItem1);
        CommandItems.AddCommandItem(CommandItem2);
        CommandItems.AddCommandItem(CommandItem3);
        CommandItems.AddCommandItem(CommandItem4);
        CommandItems.AddCommandItem(CommandItem5);
        CommandItems.AddCommandItem(CommandItem6);
        CommandItems.AddCommandItem(CommandItem7);
        CommandItems.AddCommandItem(CommandItem8);
        CommandItems.AddCommandItem(CommandItem9);
        CommandItems.AddCommandItem(CommandItem17);
        CommandItems.AddCommandItem(CommandItem10);
        CommandItems.AddCommandItem(CommandItem11);
        CommandItems.AddCommandItem(CommandItem12);
        CommandItems.AddCommandItem(CommandItem13);
        CommandItems.AddCommandItem(CommandItem14);
        CommandItems.AddCommandItem(CommandItem15);
        CommandItems.AddCommandItem(CommandItem16);
        CommandItems.AddCommandItem(CommandItem22);
        CommandItems.AddCommandItem(CommandItem23);
        CommandItems.AddCommandItem(CommandItem24);
        CommandItems.AddCommandItem(CommandItem25);
        CommandItems.AddCommandItem(CommandItem26);
        
        CommandItems.AddCommandItem(CommandItem28);
        CommandItems.AddCommandItem(CommandItem29);
        CommandItems.AddCommandItem(CommandItem30);

        CommandItems.AddCommandItem(CommandItem31);
        CommandItems.AddCommandItem(CommandItem32);
        CommandItems.AddCommandItem(CommandItem33);
        CommandItems.AddCommandItem(CommandItem34);
        CommandItems.AddCommandItem(CommandItem35);
        CommandItems.AddCommandItem(CommandItem36);
        CommandItems.AddCommandItem(CommandItem37);
        CommandItems.AddCommandItem(CommandItem38);
        CommandItems.AddCommandItem(CommandItem39);
        CommandItems.AddCommandItem(CommandItem40);
        CommandItems.AddCommandItem(CommandItem41);
        CommandItems.AddCommandItem(CommandItem42);
        CommandItems.AddCommandItem(CommandItem43);
        CommandItems.AddCommandItem(CommandItem44);
        CommandItems.AddCommandItem(CommandItem45);
        CommandItems.AddCommandItem(CommandItem46);
        CommandItems.AddCommandItem(CommandItem47);

         CommandItems.AddCommandItem(CommandItem48);
        CommandItems.AddCommandItem(CommandItem49);
        CommandItems.AddCommandItem(CommandItem50);
        CommandItems.AddCommandItem(CommandItem51);
        CommandItems.AddCommandItem(CommandItem52);
        CommandItems.AddCommandItem(CommandItem53);
        CommandItems.AddCommandItem(CommandItem54);
        CommandItems.AddCommandItem(CommandItem55);
        CommandItems.AddCommandItem(CommandItem56);
        CommandItems.AddCommandItem(CommandItem57);
        CommandItems.AddCommandItem(CommandItem58);
        CommandItems.AddCommandItem(CommandItem59);
        CommandItems.AddCommandItem(CommandItem60);
        CommandItems.AddCommandItem(CommandItem61);
        CommandItems.AddCommandItem(CommandItem62);
        CommandItems.AddCommandItem(CommandItem63);
        CommandItems.AddCommandItem(CommandItem64);
        CommandItems.AddCommandItem(CommandItem65);
        CommandItems.AddCommandItem(CommandItem66);
        CommandItems.AddCommandItem(CommandItem67);
        CommandItems.AddCommandItem(CommandItem68);
        CommandItems.AddCommandItem(CommandItem69);
        CommandItems.AddCommandItem(CommandItem70);
        CommandItems.AddCommandItem(CommandItem71);
        CommandItems.AddCommandItem(CommandItem72);
        CommandItems.AddCommandItem(CommandItem73);
        CommandItems.AddCommandItem(CommandItem74);
        CommandItems.AddCommandItem(CommandItem75);
        CommandItems.AddCommandItem(CommandItem76);
        CommandItems.AddCommandItem(CommandItem77);
        CommandItems.AddCommandItem(CommandItem78);
        CommandItems.AddCommandItem(CommandItem79);
        CommandItems.AddCommandItem(CommandItem80);
        CommandItems.AddCommandItem(CommandItem81);
        CommandItems.AddCommandItem(CommandItem82);
        CommandItems.AddCommandItem(CommandItem83);
        CommandItems.AddCommandItem(CommandItem84);
        CommandItems.AddCommandItem(CommandItem85);
        CommandItems.AddCommandItem(CommandItem86);
        CommandItems.AddCommandItem(CommandItem87);
        CommandItems.AddCommandItem(CommandItem88);
        CommandItems.AddCommandItem(CommandItem89);
        CommandItems.AddCommandItem(CommandItem90);
        CommandItems.AddCommandItem(CommandItem91);
        CommandItems.AddCommandItem(CommandItem92);
        try {
// write it out as XML
            Mapping map = new Mapping();
           // String classPath = ProtocolConfig.class.getResource("protocol-data-config-mapping.xml").getPath();
            String classPath = "protocol-data-config-mapping.xml";
            map.loadMapping(classPath);
            File file = new File("protocol-data-config.xml");
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
