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
public class DataTypeA18 extends DataTypeDateBase{
    public DataTypeA18(Date date){
        super();
        super.setDate(date);
    }

    public DataTypeA18(byte month,byte day,byte hour,byte minute){
        super();
        this.setDay(day).setHour(hour).setMinute(minute);
    }

    public DataTypeA18(String dateStr,String format){
        super(dateStr,format);
    }

    public DataTypeA18(byte[] array){
        super();
        setArray(array);
    }

    public final void setArray(byte[] array){
        setArray(array,0);
    }

    public void setArray(byte[] array,int beginPosition){
        super.setArray(array, beginPosition, "MIHHDD");
    }

    public byte[] getArray(){
        return super.getArray("MIHHDD");
    }

    public Date getDate(byte year,byte month){
        super.setYear(year).setMonth(month);
        return super.getDate();
    }

    @Override
    public int getDay(){
        return super.getDay();
    }

    @Override
    public final DataTypeA18 setDay(byte day){
        super.setDay(day);
        return this;
    }

    @Override
    public int getHour(){
        return super.getHour();
    }

    @Override
    public DataTypeA18 setHour(byte hour){
        super.setHour(hour);
        return this;
    }

    @Override
    public int getMinute(){
        return super.getMinute();
    }

    @Override
    public DataTypeA18 setMinute(byte minute){
        super.setMinute(minute);
        return this;
    }

    @Override
    public String toString(){
        StringBuilder buff = new StringBuilder();
        buff.append("day=").append(this.day);
        buff.append(", hour=").append(this.hour);
        buff.append(", minute=").append(this.minitue);

        return buff.toString();
    }
}
