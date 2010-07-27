/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.bp.utils.decoder;

import java.util.Map;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.bp.model.Dto;

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
public class Decoder376 extends Decoder {
    private final static Logger log = LoggerFactory.getLogger(Decoder376.class);

    @Override
    public void decode(Object pack, Map<String, Map<String, String>> results) {
        try {
            PmPacket376 packet = (PmPacket376) pack;
            String key = "";
            String logicAddress = packet.getAddress().getRtua();
            PmPacketData dataBuffer = packet.getDataBuffer();
            dataBuffer.rewind();
            while (dataBuffer.HaveDate()) {
                PmPacket376DA da = new PmPacket376DA();
                PmPacket376DT dt = new PmPacket376DT();
                dataBuffer.getDA(da);
                dataBuffer.getDT(dt);
                byte afn = packet.getAfn();
                String commandItemCode = "10" + BcdUtils.byteToString(afn) + String.format("%04d", dt.getFn());
                if (afn == 0x0A) {
                    afn = 0x04;
                }
                key = logicAddress + "#" + String.valueOf(da.getPn()) + "#" + commandItemCode;
                Map<String, String> dataItems = new TreeMap();
                this.DecodeData2Map(commandItemCode, dataItems, dataBuffer);
                if (!results.containsKey(key)) {
                    results.put(key, dataItems);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void decode(Object pack, Dto dto) {
        PmPacket376 packet = (PmPacket376) pack;
        String key = "";
        String logicAddress = packet.getAddress().getRtua();
        dto.setLogicAddress(logicAddress);
        PmPacketData dataBuffer = packet.getDataBuffer();
        dataBuffer.rewind();
        while (dataBuffer.HaveDate()) {
            PmPacket376DA da = new PmPacket376DA();
            PmPacket376DT dt = new PmPacket376DT();
            dataBuffer.getDA(da);
            dataBuffer.getDT(dt);
            byte afn = packet.getAfn();
            dto.setAfn(afn);
            dto.AddGP(da.getPn());
            String commandItemCode = "10" + String.format("%02d", afn) + String.format("%04d", dt.getFn());
            this.DecodeData2Dto(commandItemCode, dto, dataBuffer);
        }
    }

    @Override
    public void decode_TransMit(Object pack, Map<String, Map<String, String>> results) {
        PmPacket376 packet = (PmPacket376) pack;
        String key = "";
        String logicAddress = packet.getAddress().getRtua();
        PmPacketData dataBuffer = packet.getDataBuffer();
        PmPacketData dataBuffer645 = null;
        dataBuffer.rewind();
        PmPacket376DA da = new PmPacket376DA();
        PmPacket376DT dt = new PmPacket376DT();
        dataBuffer.getDA(da);
        dataBuffer.getDT(dt);
        byte afn = packet.getAfn();
        if (afn == 0X10) {  //透明转发，特殊处理
            long port = dataBuffer.getBin(1);//终端通信端口号
            long len = dataBuffer.getBin(2);//透明转发内容字节数k
            byte[] databuff = new byte[(int) len];
            dataBuffer.getRowIoBuffer().get(databuff);
            int head = Gb645MeterPacket.getMsgHeadOffset(databuff, 0);
            Gb645MeterPacket packet645 = Gb645MeterPacket.getPacket(databuff, head);
            dataBuffer645 = packet645.getDataAsPmPacketData();
        }
        dataBuffer645.rewind();

        byte[] commandItem = new byte[2];
        dataBuffer645.getRowIoBuffer().get(commandItem);
        String commandItemCode = "8000" + BcdUtils.binArrayToString(BcdUtils.reverseBytes(commandItem));
        if(!commandItemCode.equals("8000C040"))//除读保护器状态
            dataBuffer645.get();//将附带的开关信息状态读掉
        key = logicAddress + "#" + String.valueOf(da.getPn()) + "#" + commandItemCode;
        Map<String, String> dataItems = new TreeMap();
        this.DecodeData2Map(commandItemCode, dataItems, dataBuffer645);
        if (!results.containsKey(key)) {
            results.put(key, dataItems);
        }

    }
}
