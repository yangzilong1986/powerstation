/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.processor;

import pep.codec.protocol.gb.PmPacket;
import pep.codec.protocol.gb.gb376.PmPacket376;
import pep.codec.protocol.gb.gb376.PmPacket376Factroy;
import pep.meter645.Gb645MeterPacket;

import pep.meter645.qianlong.QianlongPackageFactroy;
import pep.mina.protocolcodec.gb.PepGbCommunicator;
import pep.mina.protocolcodec.gb.RtuCommunicationInfo;

/**
 *
 * @author luxiaochung
 */
public class SmsRespProcessor {
    private static PepGbCommunicator rtuMap;
    
    public static void setRtuMap(PepGbCommunicator rtumap){
       rtuMap = rtumap; 
    }
    public static void receiveRtuPacket(PmPacket pack){
        //do nothing
    }
    
    public static void receiveLoubaoOperateMsg(long id, String rtua, String lbAddress){
        Gb645MeterPacket lbPacket = QianlongPackageFactroy.makeSetPacket(lbAddress, 0xC036);
        lbPacket.getData().putByte((byte)0x5F);
        
        PmPacket376 pack = PmPacket376Factroy.makeDirectCommunicationPacket(
               RtuCommunicationInfo.LOUBAO_OPRATE_HOSTID, rtua, 
               (byte)1, (byte)0, (byte)0, (byte)0, lbPacket.getValue());
       rtuMap.SendPacket(0, pack,0);
    }
}
