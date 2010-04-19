/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.codec.protocol.gb.gb376;

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
        controlCode.setFunctionKey(funKey).setIsOrgniger(false).setIsUpDirect(!originControlCode.getIsUpDirect());
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
}
