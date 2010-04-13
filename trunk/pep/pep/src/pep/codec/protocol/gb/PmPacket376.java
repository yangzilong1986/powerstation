/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb;

/**
 *
 * @author luxiaochung
 */

public class PmPacket376 extends PmPacket {
    private static byte protocolVersion = 2;

    protected byte getProtocolVersion(){
        return PmPacket376.protocolVersion;
    }
    
    public static int getMsgHeadOffset(byte[] msg){
        return PmPacket.getMsgHeadOffset(msg, PmPacket376.protocolVersion);
    }
}
