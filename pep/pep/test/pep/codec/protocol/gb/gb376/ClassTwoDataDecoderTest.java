/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb.gb376;

import java.util.Map.Entry;
import pep.codec.utils.BcdUtils;
import java.util.Map;
import pep.bp.model.Dto;
import pep.codec.protocol.gb.DataTypeA5;
import pep.codec.protocol.gb.PmPacketData;
import pep.bp.utils.decoder.ClassTwoDataDecoder;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author luxiaochung
 */
public class ClassTwoDataDecoderTest {

    public ClassTwoDataDecoderTest() {
    }

     //@Test
     //public void testNextDate() {
     //   byte[] bs1 = {(byte)0x10,(byte)1,(byte)1};
     //   byte[] bs2 = {(byte)0x10,(byte)1,(byte)1,(byte)0,(byte)5};
     //   Date d1 = BcdUtils.bcdToDate(bs1, "YYMMDD", 0);
     //   Date d2 = BcdUtils.bcdToDate(bs2, "YYMMDDHHMI", 0);
     //   Date d3 = ClassTwoDataDecoder.nextDate(d1, 254);
     //   assertEquals(d3, d2);
     //}

     @Test
     public void testTddClasTwoDecod(){
         PmPacket376 pack = makeTddClassTwoDataPack();
         Dto rsltDto = ClassTwoDataDecoder.Decode(pack);
         assertEquals(rsltDto.getLogicAddress(),"93020011");
         assertEquals(rsltDto.getAfn(),(byte) 0x0D);
         assertEquals(rsltDto.getDataItems().size(),1);
         Dto.DtoItem dItem =  rsltDto.getDataItems().get(0);
         assertEquals(dItem.commandItemCode,"100D0028");
         assertEquals(dItem.gp,0);
         assertEquals(dItem.dataTime,"2010-11-19 00:00:00");
         Map<String,String> dataMap = dItem.dataMap;
         assertTrue(dataMap.containsKey("BE5E"));
         assertEquals(dataMap.get("BE5E"),"199");
         assertTrue(dataMap.containsKey("BE5F"));
         assertEquals(dataMap.get("BE5F"),"200");
         assertTrue(dataMap.containsKey("2D01"));
         assertEquals(dataMap.get("2D01"),"-101.2");
         assertTrue(dataMap.containsKey("2D02"));
         assertEquals(dataMap.get("2D02"),"1日0时20分");
         assertTrue(dataMap.containsKey("2D03"));
         assertEquals(dataMap.get("2D03"),"123.4");
         assertTrue(dataMap.containsKey("2D04"));
         assertEquals(dataMap.get("2D04"),"2日1时30分");
     }
     
     private PmPacket376 makeTddClassTwoDataPack(){
         PmPacket376 pack = new PmPacket376();
         pack.getAddress().setRtua("93020011");
         pack.getAddress().setIsGroupAddress(false);
         pack.setAfn((byte)0x0D);
         pack.getControlCode().setIsUpDirect(true);
         PmPacketData data = pack.getDataBuffer();
         data.putDA(new PmPacket376DA(0));
         data.putDT(new PmPacket376DT(28));
         
         data.put(new byte[] {(byte)0x19,(byte)0x11,(byte)0x10});//10年11月19日
         data.putWord(199);  //电流不平衡累计时间
         data.putWord(200);  //电压不平衡累计时间
         data.putA5(new DataTypeA5((float) -101.2)); //电流不平衡最大值
         data.put(new byte[] {(byte)0x20,(byte)0x00,(byte)0x01});  //出现时间 01：00：20
         data.putA5(new DataTypeA5((float) 123.4)); //电压不平衡最大值
         data.put(new byte[] {(byte)0x30,(byte)0x01,(byte)0x02}); //02：01：30
         return pack;
     }

     @Test
     public void testSth(){
         byte[] data = BcdUtils.stringToByteArray("68 7E 01 7E 01 68 98 12 96 56 34 04 0D 60 01 01 01 0A"+
                 " 00 00 16 08 10 03 18 FE FE FE FE FE FE FE FE FE FE FE FE FE FE FE FE FE FE FE FE FE FE"+
                 " FE FE FE FE FE FE FE FE FE FE FE FE FE FE FE FE FE FE FE FE FE FE FE FE FE FE 85 07 01"+
                 " 21 08 01 04 10 01 63 09 01 91 09 01 FE FE FE FE FE FE FE FE FE 00 00 00 00 F3 16 55");
         PmPacket376 pack = new PmPacket376();
         pack.setValue(data, 0);
         Dto rsltDto = ClassTwoDataDecoder.Decode(pack);
         System.out.print("rtua="+rsltDto.getLogicAddress());
         System.out.println(" ,afn="+new Integer(rsltDto.getAfn()).toString());

         for (Dto.DtoItem ditem : rsltDto.getDataItems()){
            System.out.print("    commandItemCode=");
            System.out.print(ditem.commandItemCode);
            System.out.print(", dataTime=");
            System.out.print(ditem.dataTime);
            System.out.print(", gp=");
            System.out.println(ditem.gp);
            for (Entry<String,String> subItem: ditem.dataMap.entrySet()){
                System.out.println("      "+subItem.getKey()+"="+subItem.getValue());
            }
            System.out.println();
         }
     }
}