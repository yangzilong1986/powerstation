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

    public Gb645Address(byte[] data, int beginposition){
        super();
        if (data.length>=beginposition+6){
            for (int i=0; i<6; i++)
                value[i] = data[beginposition+i];
        }
    }

    public Gb645Address(String address){
        super();
        setAddress(address);
    }

    public String getAddress(){
        return Gb645Address.meterAddressToString(value);
    }

    public void setAddress(String address){
        this.value = Gb645Address.stringToMeterAddress(address);
    }

    public byte[] getShrinkedMeterAddr(int shrinkBytes, byte shrinkToken){
        if ((shrinkBytes>=0) && (shrinkBytes<=6)){
            byte [] temp = new byte[6];
            for (int i=0; i<6-shrinkBytes; i++){
                temp[i] = this.value[i];
            }
            for (int i=6-shrinkBytes; i<6;i++){
                temp[i] = (byte) shrinkToken;
            }
            return temp;
        }
        else
            return this.value;
    }

    public byte[] getShrinkedMeterAddr(int shrinkBytes){
        return getShrinkedMeterAddr(shrinkBytes,(byte)0xAA);
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
