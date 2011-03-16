/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb;
/**
 *
 * @author luxiaochung
 */

import pep.codec.utils.BcdUtils;

public class Address {
    private byte[] value;

    public Address(){
        value = new byte[5];
        for (byte b:value) b=0;
    }

    public Address(byte[] value){
        value = new byte[5];
        this.setValue(value);
    }

    public Address(String rtua, byte mstId){
        value = new byte[5];
        setRtua(rtua);
        setIsGroupAddress(false);
        setMastStationId(mstId);
    }

    public static Address makeBroadcastAddress(String rtua, byte mstId){
        Address addr = new Address();
        addr.value[1] = BcdUtils.stringToByte(rtua.substring(0,2));
        addr.value[0] = BcdUtils.stringToByte(rtua.substring(2,4));
        addr.value[3] = (byte)0xFF;
        addr.value[2] = (byte)0xFF;
        addr.setIsGroupAddress(true);
        addr.setMastStationId(mstId);

        return addr;
    }

    public static Address makeGroupAddress(String groupAddress, byte mstId){
        Address addr = new Address(groupAddress,mstId);
        addr.setIsGroupAddress(true);

        return addr;
    }

    public byte[] getValue() {
        return value;
    }

    public Address setValue(byte[] value, int firstIndex) {
        if ((value.length-firstIndex)>=5) {
            for (int i=0; i<5; i++) this.value[i] = value[firstIndex+i];
        }
        else {
            throw new IllegalArgumentException();
        }

        return this;
    }

    public final Address setValue(byte[] value) {
        return this.setValue(value, 0);
    }

    public String getRtua(){
        StringBuilder buff = new StringBuilder(8);
        buff.append(BcdUtils.byteToString(value[1]));
        buff.append(BcdUtils.byteToString(value[0]));
        buff.append(BcdUtils.byteToString(value[3]));
        buff.append(BcdUtils.byteToString(value[2]));

        return buff.toString();
    }

    public final Address setRtua(String rtua){
        if (rtua.length()==8) {
            value[1] = BcdUtils.stringToByte(rtua.substring(0,2));
            value[0] = BcdUtils.stringToByte(rtua.substring(2,4));
            value[3] = BcdUtils.stringToByte(rtua.substring(4,6));
            value[2] = BcdUtils.stringToByte(rtua.substring(6,8));
        }
        else{
            throw new IllegalArgumentException();
        }

        return this;
    }

    public boolean getIsGoupAddress(){
        return (byte)(value[4] &1)==(byte) 1;
    }

    public final Address setIsGroupAddress(boolean isGroupAddress){
        if (isGroupAddress) value[4] = (byte)(value[4] | 0x01);
        else value[4] = (byte)(value[4] & 0xFE);

        return this;
    }

    public byte getMastStationId(){
        return (byte)(value[4]>>1);
    }

    public final Address setMastStationId(byte mstId){
        value[4] = (byte)((value[4] & 0x01)|(mstId<<1));

        return this;
    }

    @Override
    public String toString(){
        StringBuilder buff = new StringBuilder();

        if (getIsGoupAddress()){
            buff.append("GroupAdress=").append(getRtua());
            buff.append(" MstId=").append(getMastStationId());
        }
        else {
            buff.append("Rtua=").append(getRtua());
            buff.append(" MstId=").append(getMastStationId());
        }
        return buff.toString();
    }
}
