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

    private PmPacket376Factroy() {
        //nothing to do;
    }

    private static byte getFunctionKey(byte afn) {
        return 0;
    }

    private static byte getRespFunctionKey(byte originalityKey) {
        // Todo Add Mapping
        return originalityKey;
    }

    /**
     * 创建链路检测返回帧
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
        packSeq.setSeq(originSeq.getSeq());
        if (originSeq.getIsTpvAvalibe()) {
            pack.setTpv(originalityPack.getTpv());
        }

        PmPacketData data = pack.getDataBuffer();
        PmPacketData orginData = originalityPack.getDataBuffer();
        data.rewind();
        data.putDA(new PmPacket376DA(0)).putDT(new PmPacket376DT(fn));
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
        PmPacket376 pack = new PmPacket376();
        ControlCode ctrlCode = pack.getControlCode();
        ctrlCode.setIsOrgniger(true);
        ctrlCode.setIsUpDirect(false);
        ctrlCode.setIsDownDirectFrameCountAvaliable(false);
        ctrlCode.setFunctionKey(PmPacket376Factroy.getFunctionKey((byte) 0x0E));

        Address address = pack.getAddress();
        address.setIsGroupAddress(false);
        address.setMastStationId(mstId);
        address.setRtua(rtua);

        Seq seq = pack.getSeq();
        seq.setIsFirstFrame(true);
        seq.setIsFinishFrame(true);
        seq.setIsNeedCountersign(false);
        seq.setIsTpvAvalibe(false);

        pack.setAfn((byte) 0x0E);

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
        PmPacket376 pack = new PmPacket376();
        ControlCode ctrlCode = pack.getControlCode();
        ctrlCode.setIsOrgniger(true);
        ctrlCode.setIsUpDirect(false);
        ctrlCode.setIsDownDirectFrameCountAvaliable(false);
        ctrlCode.setFunctionKey(PmPacket376Factroy.getFunctionKey((byte) 0x0E));

        Address address = pack.getAddress();
        address.setIsGroupAddress(false);
        address.setMastStationId(mstId);
        address.setRtua(rtua);

        Seq seq = pack.getSeq();
        seq.setIsFirstFrame(true);
        seq.setIsFinishFrame(true);
        seq.setIsNeedCountersign(false);
        seq.setIsTpvAvalibe(false);

        pack.setAfn((byte) 0x10);

        PmPacketData data = pack.getDataBuffer();
        data.putDA(new PmPacket376DA(0));
        data.putDT(new PmPacket376DT(1));

        data.put(comPort).put(comControlByte).put(timeoutConst).put(timeoutBetweenByte);
        data.putWord(MsgData.length);
        data.put(MsgData);
        
        return pack;
    }
}
