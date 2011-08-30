/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb.gb376;

import pep.codec.protocol.gb.PmPacket;

/**
 *
 * @author luxiaochung
 */

public class PmPacket376 extends PmPacket {
    private static byte protocolVersion = 2;

    public PmPacket376 clone(){
        PmPacket376 result = new PmPacket376();
        result.setValue(this.getValue(),0);
        result.setRemark1(this.getRemark1());
        result.setRemark2(this.getRemark2());
        
        return result;
    }

    @Override
    protected byte getProtocolVersion(){
        return PmPacket376.protocolVersion;
    }
    
    public static int getMsgHeadOffset(byte[] msg, int firstIndex){
        return PmPacket.getMsgHeadOffset(msg, PmPacket376.protocolVersion, firstIndex);
    }
}
