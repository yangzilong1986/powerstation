/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.utils.decoder;

import java.util.Date;
import pep.bp.model.Dto;
import pep.codec.protocol.gb.PmPacketData;
import pep.codec.protocol.gb.gb376.PmPacket376;
import pep.codec.protocol.gb.gb376.PmPacket376DA;
import pep.codec.protocol.gb.gb376.PmPacket376DT;

/**
 *
 * @author luxiaochung
 */
public class ClassTwoDataDecoder {
    public static Dto Decode(PmPacket376 packet){
        Dto classTwodto = new Dto(packet.getAddress().getRtua(),packet.getAfn());
        PmPacketData data = packet.getDataBuffer();
        PmPacket376DA da = new PmPacket376DA();
        PmPacket376DT dt = new PmPacket376DT();
        data.getDA(da);
        data.getDT(dt);
        int fn = dt.getFn();
        int pn = da.getPn();
        switch (fn) {
            case 27: decodeTd_c(data,classTwodto,fn,pn);
                     break;
            
        }
        return classTwodto;
        
        //81到94，101到108，193，194，195，196，27，28，29
    }
    
    private static void decodeTd_c(PmPacketData data, Dto classTwoDto, int fn, int pn){
        Date dateTime = data.getA15().getDate();
        
    //起始时间ts：分时日月年	见附录A.15	5
//数据冻结密度m	BIN	1
//数据点数n	BIN	1
    }
}
