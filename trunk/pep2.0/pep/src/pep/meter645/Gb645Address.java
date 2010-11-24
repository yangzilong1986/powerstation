/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.meter645;

import pep.codec.utils.BcdUtils;

/**
 *
 * @author luxiaochung
 */
public class Gb645Address {
    private byte[] value = new byte[6];
    private int shrinkBytes;
    private byte shrinkToken;
    
    public Gb645Address(byte[] data, int beginposition){
        super();
        if (data.length>=beginposition+6){
            for (int i=0; i<6; i++)
                value[i] = data[beginposition+i];
        }
    }

    public Gb645Address(String address,int shrinkBytes, byte shrinkToken){
        super();
        setAddress(address);
        this.shrinkBytes = shrinkBytes;
        this.shrinkToken = shrinkToken;
    }
    
    public Gb645Address(String address,int shrinkBytes){
        this(address,shrinkBytes,(byte)0xAA);
    }

    public Gb645Address(String address){
        this(address,0);
    }

    public void setShrinkBytes(int shrinkBytes){
        this.shrinkBytes = shrinkBytes;
    }
    
    public void setShrinkToken(byte shrinkToken){
        this.shrinkToken = shrinkToken;
    }
    
    public String getAddress(){
        return Gb645Address.meterAddressToString(value);
    }

    public final void setAddress(String address){
        this.value = Gb645Address.stringToMeterAddress(address);
    }

    public byte[] getValue(){
        return this.getShrinkedMeterAddr();
    }

    public void setValue(byte[] msg, int beginIndex){
        for (int i=0; i<6; i++)
            this.value[i] = msg[beginIndex+i];
    }

    private byte[] getShrinkedMeterAddr(){
        if ((shrinkBytes>=0) && (shrinkBytes<=6)){
            byte [] temp = new byte[6];
            System.arraycopy(this.value, 0, temp, 0, 6 - shrinkBytes);
            for (int i=6-shrinkBytes; i<6;i++){
                temp[i] = (byte) shrinkToken;
            }
            return temp;
        }
        else
            return this.value;
    }

    public static String meterAddressToString(byte[] addr){
        if (addr.length==6)
            return BcdUtils.binArrayToString(BcdUtils.reverseBytes(addr));
        else
            return "000000000000";
    }
    
    public static byte[] stringToMeterAddress(String address){
        String temp;
        if (address.length()>12)
            temp = address.substring(address.length()-12);
        else {
            temp = BcdUtils.dupeString("0", 12-address.length())+address;
        }

        return BcdUtils.reverseBytes(BcdUtils.stringToByteArray(temp));
    }
}
