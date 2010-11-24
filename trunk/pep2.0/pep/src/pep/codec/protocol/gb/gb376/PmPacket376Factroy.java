/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.codec.protocol.gb.gb376;

import pep.codec.protocol.gb.Address;
import pep.codec.protocol.gb.ControlCode;
import pep.codec.protocol.gb.PmPacketData;
import pep.codec.protocol.gb.Seq;

/**
 *
 * @author luxiaochung
 */
public class PmPacket376Factroy {
    private static final byte DIRECT_CALL_AFN = 0x10;
    private static final byte READ_EVENT_AFN = 0x0E;

    private static PmPacket376 makeDownDirectPacket(byte afn, byte mstId, String rtua) {
        PmPacket376 pack = new PmPacket376();
        ControlCode ctrlCode = pack.getControlCode();
        ctrlCode.setIsOrgniger(true);
        ctrlCode.setIsUpDirect(false);
        ctrlCode.setIsDownDirectFrameCountAvaliable(false);
        ctrlCode.setFunctionKey(PmPacket376Factroy.getFunctionKey(afn));
        
        Address address = pack.getAddress();
        address.setIsGroupAddress(false);
        address.setMastStationId(mstId);
        address.setRtua(rtua);
        
        Seq seq = pack.getSeq();
        seq.setIsFirstFrame(true);
        seq.setIsFinishFrame(true);
        seq.setIsNeedCountersign(false);
        seq.setIsTpvAvalibe(false);
        
        pack.setAfn(afn);
        return pack;
    }

    private PmPacket376Factroy() {
        //nothing to do;
    }

    private static byte getFunctionKey(byte afn) {
        switch (afn) {
            case 0x01:
                return 1;
            case 0x02:
                return 9;
            case 0x04:
            case 0x05:
                return 10;
            case 0x03:
            case 0x06:
            case 0x08:
            case 0x09:
            case 0x0A:
            case 0x0B:
            case 0x0C:
            case 0x0D:
            case 0x0E:
            case 0x0F:
            case 0x10:
                return 11;
            default:
                return 0;
        }
    }

    private static byte getRespFunctionKey(byte originalityKey) {
        switch (originalityKey) {
            case 1:
                return 0;
            case 9:
                return 11;
            default:
                return 8;
        }
    }

    /**
     * 创建主动上送响应帧
     */
    public static PmPacket376 makeAcKnowledgementPack(PmPacket376 originalityPack,
            int fn, byte ackValue) {
        PmPacket376 pack = new PmPacket376();

        ControlCode originControlCode = originalityPack.getControlCode();
        byte funKey = PmPacket376Factroy.getRespFunctionKey(originControlCode.getFunctionKey());
        ControlCode controlCode = pack.getControlCode();
        controlCode.setFunctionKey(funKey).setIsOrgniger(false).setIsUpDirect(false);//永远是下行帧
        if (controlCode.getIsUpDirect()) {
            controlCode.setUpDirectIsAppealCall(false);
        } else {
            controlCode.setIsDownDirectFrameCountAvaliable(false).setDownDirectFrameCount((byte) 0);
        }

        pack.getAddress().setValue(originalityPack.getAddress().getValue());

        pack.setAfn((byte) 0);

        Seq originSeq = originalityPack.getSeq();
        Seq packSeq = pack.getSeq();
        packSeq.setIsFirstFrame(true).setIsFinishFrame(true).setIsNeedCountersign(false);
        packSeq.setIsTpvAvalibe(originSeq.getIsTpvAvalibe());
        packSeq.setSeq(originSeq.getSeq( ));
        if (originSeq.getIsTpvAvalibe()) {
            pack.setTpv(originalityPack.getTpv());
        }

        PmPacketData data = pack.getDataBuffer();
        PmPacketData orginData = originalityPack.getDataBuffer();
        orginData.rewind();
        data.rewind();
        data.putDA(new PmPacket376DA(0));
        data.putDT(new PmPacket376DT(fn));
        if (fn == 3) {
            orginData.rewind();
            PmPacket376DT orginDT = new PmPacket376DT();
            PmPacket376DA orginDA = new PmPacket376DA();
            orginData.getDA(orginDA).getDT(orginDT);
            data.putDA(orginDA).putDT(orginDT);
        }
        data.put(ackValue);

        return pack;
    }

    public static PmPacket376 makeCallEventRecordPacket(byte mstId, String rtua,
            int fn, byte beginPosition, byte endPosition) {
        PmPacket376 pack = makeDownDirectPacket(READ_EVENT_AFN, mstId, rtua);

        PmPacketData data = pack.getDataBuffer();
        data.putDA(new PmPacket376DA(0));
        data.putDT(new PmPacket376DT(fn));
        data.put(beginPosition);
        data.put(endPosition);
        
        return pack;
    }

    public static PmPacket376 makeDirectCommunicationPacket(byte mstId,String rtua,
           byte comPort, byte comControlByte, byte timeoutConst, byte timeoutBetweenByte,
           byte[] MsgData){
        PmPacket376 pack = makeDownDirectPacket(DIRECT_CALL_AFN, mstId, rtua);

        PmPacketData data = pack.getDataBuffer();
        data.putDA(new PmPacket376DA(0));
        data.putDT(new PmPacket376DT(1));

        data.put(comPort).put(comControlByte).put(timeoutConst).put(timeoutBetweenByte);
        data.putWord(MsgData.length);
        data.put(MsgData);
        
        return pack;
    }
}
