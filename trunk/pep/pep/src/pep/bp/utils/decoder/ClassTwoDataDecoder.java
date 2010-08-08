/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.utils.decoder;

import pep.bp.model.Dto;
import pep.codec.protocol.gb.gb376.PmPacket376;

/**
 *
 * @author luxiaochung
 */
public class ClassTwoDataDecoder {
    public static Dto Decode(PmPacket376 packet){
        Dto classTwodto = new Dto(packet.getAddress().getRtua(),packet.getAfn());


        
        return classTwodto;
    }
}
