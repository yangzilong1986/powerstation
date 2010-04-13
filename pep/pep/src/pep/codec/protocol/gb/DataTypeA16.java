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

public class DataTypeA16 {
    private byte[] value;

    public DataTypeA16(){
        super();
        value = new byte[4];
        value[3] = 1;
    }

    public DataTypeA16(byte day, byte hour, byte minute, byte second){
        this();
        this.setDay(day).setHour(hour).setMinute(minute).setSecond(second);
    }

    public byte getSecond(){
        return BcdUtils.bcdToInt(value[0]);
    }

    public DataTypeA16 setSecond(byte second){
        value[0] = BcdUtils.intToBcd(second);
        return this;
    }

    public byte getMinute(){
        return BcdUtils.bcdToInt(value[1]);
    }

    public DataTypeA16 setMinute(byte minute){
        value[1] = BcdUtils.intToBcd(minute);
        return this;
    }

    public byte getHour(){
        return BcdUtils.bcdToInt(value[2]);
    }

    public DataTypeA16 setHour(byte hour){
        value[2] = BcdUtils.intToBcd(hour);
        return this;
    }

    public byte getDay(){
        return BcdUtils.bcdToInt(value[3]);
    }

    public DataTypeA16 setDay(byte day){
        value[3] = BcdUtils.intToBcd(day);
        return this;
    }

    public byte[] getValue(){
        return this.value;
    }

    public DataTypeA16 setValue(byte[] value, int firstIndex){
        if ((value.length-firstIndex)>=4){
            for (int i=0; i<4; i++) this.value[i]=value[i+firstIndex];
        }
        else {
            throw new IllegalArgumentException();
        }
        return this;
    }

    public DataTypeA16 setValue(byte[] value){
        return this.setValue(value, 0);
    }

    @Override
    public String toString(){
        StringBuffer buff = new StringBuffer();
        buff.append("day=").append(this.getDay());
        buff.append(", hour=").append(this.getHour());
        buff.append(", minute=").append(this.getMinute());
        buff.append(", second=").append(this.getSecond());

        return buff.toString();
    }
}
