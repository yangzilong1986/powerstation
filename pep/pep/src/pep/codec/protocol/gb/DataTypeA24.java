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
public class DataTypeA24 extends DataTypeDateBase{
    public DataTypeA24(Date date){
        super();
        super.setDate(date);
    }

    public DataTypeA24(byte day, byte hour){
        super();
        super.setDay(day).setHour(hour);
    }

    public DataTypeA24(String dateStr,String format){
        super(dateStr,format);
    }

    public DataTypeA24(byte[] array){
        super();
        setArray(array,0);
    }

    public void setArray(byte[] array){
        setArray(array,0);
    }

    public final void setArray(byte[] array, int beginPosition){
        super.setArray(array, beginPosition, "HHDD");
    }

    public byte[] getArray(){
        return super.getArray("HHDD");
    }

    public Date getDate(byte year, byte month){
        super.setYear(year).setMonth(month);
        return super.getDate();
    }

    @Override
    public DataTypeA24 setDay(byte day){
        super.setDay(day);
        return this;
    }

    @Override
    public int getDay(){
        return super.getDay();
    }

    @Override
    public DataTypeA24 setHour(byte hour){
        super.setHour(hour);
        return this;
    }

    @Override
    public int getHour(){
        return super.getHour();
    }

    @Override
    public String toString(){
        if (this.isNull) return "";
        
        StringBuilder buff = new StringBuilder();
        buff.append(this.day).append("日");
        buff.append(this.hour).append("时");

        //StringBuilder buff = new StringBuilder();
        //buff.append("day=").append(this.getDay());
        //buff.append(", hour=").append(this.getHour());
        return buff.toString();
    }
}
