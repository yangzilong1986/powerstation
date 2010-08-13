/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.utils.decoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import pep.bp.model.Dto;
import pep.bp.model.Dto.DtoItem;
import pep.codec.protocol.gb.PmPacketData;
import pep.codec.protocol.gb.gb376.PmPacket376;
import pep.codec.protocol.gb.gb376.PmPacket376DA;
import pep.codec.protocol.gb.gb376.PmPacket376DT;
import pep.codec.utils.BcdUtils;

/**
 *
 * @author luxiaochung
 */
public class ClassTwoDataDecoder {
    private static Document codecDefineDoc;

    static{
        SAXBuilder builder = new SAXBuilder();
        try {
            codecDefineDoc = builder.build(new FileInputStream("ClassTwoDataCodec.xml"));
        } catch (JDOMException ex) {
            Logger.getLogger(ClassTwoDataDecoder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClassTwoDataDecoder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Dto Decode(PmPacket376 packet){
        Dto classTwodto = new Dto(packet.getAddress().getRtua(),packet.getAfn());
        PmPacketData data = packet.getDataBuffer();
        PmPacket376DA da = new PmPacket376DA();
        PmPacket376DT dt = new PmPacket376DT();
        data.rewind();
        data.getDA(da);
        data.getDT(dt);
        int fn = dt.getFn();
        int pn = da.getPn();
        
        Element defineNode = ClassTwoDataDecoder.getDefineNode(Integer.toString(fn));
        if (defineNode!=null){
           String timeTag = defineNode.getAttributeValue("timeTag");
           if (timeTag.equals("Td_d"))
                ClassTwoDataDecoder.decodeTd_d(data, classTwodto, pn, defineNode);
           if (timeTag.equals("Td_c"))
                ClassTwoDataDecoder.decodeTd_c(data, classTwodto, pn, defineNode);
           if (timeTag.equals("Td_m"))
                ClassTwoDataDecoder.decodeTd_m(data, classTwodto, pn, defineNode);
        }
        return classTwodto;
    }

    private static Element getDefineNode(String name){
        Element root = ClassTwoDataDecoder.codecDefineDoc.getRootElement();
        List fnNodes = root.getChildren("fn");
        for (int i = 0; i<fnNodes.size(); i++){
            Element fnNode = (Element) fnNodes.get(i);
            if (fnNode.getAttributeValue("id").equals(name))
                return fnNode;
        }
        return null;
    }
    
    private static void decodeTd_d(PmPacketData data, Dto classTwoDto, int pn, Element defineNode){
        String itemId = defineNode.getAttributeValue("itemId");
        Date dateTime = data.getA20().getDate();
        Dto.DtoItem dItem = classTwoDto.addDataItem(pn, BcdUtils.dateToString(dateTime, "yyyy-MM-dd HH:mm:ss"), itemId);
        List subItems = defineNode.getChildren("subitem");
        for (int i = 0; i < subItems.size(); i++) {
            Element subItem = (Element) subItems.get(i);
            String subItemId = subItem.getAttributeValue("subItemId");
            ClassTwoDataDecoder.decodeSubItem(subItem, subItemId, data, dItem);
        }
    }
    
    private static void decodeTd_c(PmPacketData data, Dto classTwoDto, int pn, Element defineNode) {
        String itemId = defineNode.getAttributeValue("itemId");
        Date dateTime = data.getA15().getDate();
        int frequency = (int) data.getBin(1);
        int timePointCount = (int) data.getBin(1);
        for (int timeIdx = 0; timeIdx < timePointCount; timeIdx++) {
            Dto.DtoItem dItem = classTwoDto.addDataItem(pn, BcdUtils.dateToString(dateTime, "yyyy-MM-dd HH:mm:ss"), itemId);

            List subItems = defineNode.getChildren("subitem");
            for (int i = 0; i < subItems.size(); i++) {
                Element subItem = (Element) subItems.get(i);
                String subItemId = subItem.getAttributeValue("subItemId");
                ClassTwoDataDecoder.decodeSubItem(subItem, subItemId, data, dItem);
            }

            dateTime = ClassTwoDataDecoder.nextDate(dateTime, frequency);
        }
    }

    private static void decodeTd_m(PmPacketData data, Dto classTwoDto, int pn, Element defineNode) {
        String itemId = defineNode.getAttributeValue("itemId");
        Date month = data.getA21().getDate(); //空读,不保存
        Date dateTime = data.getA15().getDate();
        int rateCount = (int) (data.getBin(1) + 1);
        Dto.DtoItem dItem = classTwoDto.addDataItem(pn, BcdUtils.dateToString(dateTime, "yyyy-MM-dd HH:mm:ss"), itemId);
        List subItems = defineNode.getChildren("subitem");
        for (int rateIdx = 0; rateIdx < rateCount; rateIdx++) {
            for (int i = 0; i < subItems.size(); i++) {
                Element subItem = (Element) subItems.get(i);
                String subItemId = subItem.getAttributeValue("subItemId");
                subItemId = String.format("%04x", Integer.parseInt(subItemId, 16)+rateIdx).toUpperCase();
                ClassTwoDataDecoder.decodeSubItem(subItem, subItemId, data, dItem);
            }
        }
    }

    private static void decodeSubItem(Element subItem, String subItemId, PmPacketData data, DtoItem dItem) {
        String codec = subItem.getAttributeValue("codec");
        if (codec.equals("BIN")) {
            String len = subItem.getAttributeValue("len");
            String subItemValue = ClassTwoDataDecoder.getBIN(data, len);
            dItem.dataMap.put(subItemId, subItemValue);
        } else {
            String subItemValue = ClassTwoDataDecoder.getGerenalData(data, codec);
            dItem.dataMap.put(subItemId, subItemValue);
        }
    }

    private static String getBIN(PmPacketData data, String len){
        try {
            long value = data.getBin(Integer.parseInt(len));
            return Long.toString(value);
        } catch (Exception ex) {
            return "";
        }
    }
    
    private static String getGerenalData(PmPacketData data, String codec){
        String getFunctionName = "get"+codec;
        try{
            Method getFunc = data.getClass().getMethod(getFunctionName);
            Object value = getFunc.invoke(data);
            return value.toString();
        }catch(Exception ex){
            return "";
        }
    }
    
    private static Date nextDate(Date thisDate, int frequency){
        int delay;
        switch (frequency){
            case 1: delay = 15; break;
            case 2: delay = 30; break;
            case 3: delay = 60; break;
            case 254: delay = 5; break;
            case 255: delay = 1; break;
            default: delay = 0;
        }
        return new Date(thisDate.getTime()+1000L*60L*delay);
    }
}
