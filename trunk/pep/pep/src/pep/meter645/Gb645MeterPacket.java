/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.meter645;

import pep.codec.utils.BcdDataBuffer;

/**
 *
 * @author luxiaochung
 */
public class Gb645MeterPacket {
    private Gb645Address address;
    private Gb645ControlCode controlCode;
    private BcdDataBuffer data; //总是保存已经未经加33减33处理的值

    public Gb645MeterPacket(String meterAddress){
        super();
        address = new Gb645Address(meterAddress);
        controlCode = new Gb645ControlCode();
        data = new BcdDataBuffer();
    }
    
    public Gb645MeterPacket setAddress(String meterAddress){
        address.setAddress(meterAddress);
        return this;
    }
    
    public Gb645Address getAddress(){
        return address;
    }
    
    public Gb645MeterPacket setControlCode(boolean isFromMast, boolean hasHouxuzhen,
            boolean isYichang, byte gongnengma){
        controlCode.isFromMast(isFromMast).isHasHouxuzhen(hasHouxuzhen).isYichang(isYichang).setFuncCode(gongnengma);
        
        return this;
    }
    
    public Gb645ControlCode getControlCode(){
        return controlCode;
    }


}
