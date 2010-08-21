/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.meter645.qianlong;

import pep.meter645.Gb645MeterPacket;

/**
 *
 * @author luxiaochung
 */
public class QianlongPackageFactroy {
    public static Gb645MeterPacket makeReadPacket(String address, int dataId){
        Gb645MeterPacket pack = new Gb645MeterPacket(address);
        pack.getControlCode().isFromMast(true).isHasHouxuzhen(false).isYichang(false).setFuncCode((byte)1);
        pack.getData().putWord(dataId);
        return pack;
    }
    
    public static Gb645MeterPacket makeSetPacket(String address, int dataId){
        Gb645MeterPacket pack = new Gb645MeterPacket(address);
        pack.getControlCode().isFromMast(true).isHasHouxuzhen(false).isYichang(false).setFuncCode((byte)4);
        pack.getData().putWord(dataId);
        return pack;
    }
}
