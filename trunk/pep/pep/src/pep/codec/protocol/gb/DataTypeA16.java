/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb;

import java.util.Date;

/**
 *
 * @author luxiaochung
 */

public class DataTypeA16 extends DataTypeDateBase{
    public DataTypeA16(){
        super();
    }

    public DataTypeA16(byte day, byte hour, byte minute, byte second){
        this();
        this.setDay(day).setHour(hour).setMinute(minute).setSecond(second);
    }

    public DataTypeA16(String dateStr,String format) {
        super(dateStr,format);
    }

    public DataTypeA16(byte[] array){
        this();
        setArray(array);
    }

    @Override
    public int getSecond(){
        return super.getSecond();
    }

    @Override
    public DataTypeA16 setSecond(byte second){
        super.setSecond(second);
        return this;
    }

    @Override
    public int getMinute(){
        return super.getMinute();
    }

    @Override
    public DataTypeA16 setMinute(byte minute){
        super.setMinute(minute);
        return this;
    }

    @Override
    public int getHour(){
        return super.getHour();
    }

    @Override
    public DataTypeA16 setHour(byte hour){
        super.setHour(hour);
        return this;
    }

    @Override
    public int getDay(){
        return super.getDay();
    }

    @Override
    public final DataTypeA16 setDay(byte day){
        super.setDay(day);
        return this;
    }

    public byte[] getArray(){
        return getArray("SSMIHHDD");
    }

    public DataTypeA16 setArray(byte[] array, int beginPosition){
        setArray(array,beginPosition,"SSMIHHDD");
        return this;
    }

    public final DataTypeA16 setArray(byte[] value){
        return this.setArray(value, 0);
    }

    public Date getDate(byte year, byte month){
        super.setYear(year);
        super.setMonth(month);
        return super.getDate();
    }

    @Override
    public String toString(){
        StringBuilder buff = new StringBuilder();
        buff.append("day=").append(this.getDay());
        buff.append(", hour=").append(this.getHour());
        buff.append(", minute=").append(this.getMinute());
        buff.append(", second=").append(this.getSecond());

        return buff.toString();
    }
}
