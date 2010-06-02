/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.codec.utils;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.exolab.castor.mapping.MappingException;
import org.xml.sax.InputSource;
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
        ProtocolCommandItem CommandItem = new ProtocolCommandItem();
        CommandItem.setCommandCode("10040001");
        CommandItem.AddDataItem(new ProtocolDataItem("1004000101", 1, "BIN", "","终端数传机延时时间RTS"));
        CommandItem.AddDataItem(new ProtocolDataItem("1004000102", 1, "BIN", "","终端作为启动站允许发送传输延时时间"));
        CommandItem.AddDataItem(new ProtocolDataItem("1004000103", 1, "BIN", "","终端等待从动站响应的超时时间"));
        CommandItem.AddDataItem(new ProtocolDataItem("1004000104", 1, "BIN", "","终端等待从动站响应的重发次数"));
        CommandItem.AddDataItem(new ProtocolDataItem("1004000106", 1, "BS8", "","需要主站确认的通信服务（CON=1）的标志"));
        CommandItem.AddDataItem(new ProtocolDataItem("1004000107", 1, "BIN", "","心跳周期"));

        CommandItem.AddDataItem(new ProtocolDataItem("1004000301", 6, "BIN", "","主用IP地址和端口"));
        CommandItem.AddDataItem(new ProtocolDataItem("1004000302", 6, "BIN", "","备用IP地址和端口"));
        CommandItem.AddDataItem(new ProtocolDataItem("1004000303", 16, "ASCII", "","APN"));

        CommandItem.AddDataItem(new ProtocolDataItem("1004000401", 8, "BIN", "","主站电话号码或主站手机号码"));
        CommandItem.AddDataItem(new ProtocolDataItem("1004000402", 8, "BIN", "","短信中心号码"));

        CommandItem.AddDataItem(new ProtocolDataItem("1004000501", 1, "BIN", "","消息认证方案号"));
        CommandItem.AddDataItem(new ProtocolDataItem("1004000502", 2, "BIN", "","消息认证方案参数"));

        CommandItem.AddDataItem(new ProtocolDataItem("1004000701", 4, "BIN", "","终端IP地址"));
        CommandItem.AddDataItem(new ProtocolDataItem("1004000702", 4, "BIN", "","子网掩码地址"));
        CommandItem.AddDataItem(new ProtocolDataItem("1004000703", 4, "BIN", "","网关地址"));
        CommandItem.AddDataItem(new ProtocolDataItem("1004000704", 1, "BIN", "","代理服务器代理类型"));
        CommandItem.AddDataItem(new ProtocolDataItem("1004000705", 6, "BIN", "","代理服务器地址和端口"));
        CommandItem.AddDataItem(new ProtocolDataItem("1004000706", 1, "BIN", "","代理服务器连接方式"));
        CommandItem.AddDataItem(new ProtocolDataItem("1004000707", 1, "BIN", "","代理服务器用户名长度"));
        CommandItem.AddDataItem(new ProtocolDataItem("1004000708", -1, "BIN", "","代理服务器用户名"));
        CommandItem.AddDataItem(new ProtocolDataItem("1004000709", 1, "BIN", "","代理服务器密码长度"));
        CommandItem.AddDataItem(new ProtocolDataItem("1004000710", -1, "ASCII", "","代理服务器密码"));
        CommandItem.AddDataItem(new ProtocolDataItem("1004000711", 2, "BIN", "","终端侦听端口"));
        CommandItems.AddCommandItem(CommandItem);
        try {
// write it out as XML
            Mapping map = new Mapping();
            map.loadMapping("protocol-data-config-mapping.xml");
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
