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
public class DataTypeA17 extends DataTypeDateBase{
    public DataTypeA17(Date date){
        super();
        super.setDate(date);
    }
    
    public DataTypeA17(byte month,byte day,byte hour,byte minute){
        super();
        this.setMonth(month).setDay(day).setHour(hour).setMinute(minute);
    }

    public DataTypeA17(String dateStr,String format){
        super(dateStr,format);
    }
    
    public DataTypeA17(byte[] array){
        super();
        setArray(array);
    }
    
    public final void setArray(byte[] array){
        setArray(array,0);
    }
    
    public void setArray(byte[] array,int beginPosition){
        super.setArray(array, beginPosition, "MIHHDDMM");
    }
    
    public byte[] getArray(){
        return super.getArray("MIHHDDMM");
    }
    
    public Date getDate(byte year){
        super.setYear(year);
        return super.getDate();
    }

    @Override
    public int getMonth(){
        return super.getMonth();
    }

    @Override
    public final DataTypeA17 setMonth(byte month){
        super.setMonth(month);
        return this;
    }

    @Override
    public int getDay(){
        return super.getDay();
    }

    @Override
    public DataTypeA17 setDay(byte day){
        super.setDay(day);
        return this;
    }

    @Override
    public int getHour(){
        return super.getHour();
    }

    @Override
    public DataTypeA17 setHour(byte hour){
        super.setHour(hour);
        return this;
    }

    @Override
    public int getMinute(){
        return super.getMinute();
    }

    @Override
    public DataTypeA17 setMinute(byte minute){
        super.setMinute(minute);
        return this;
    }

    @Override
    public String toString(){
        if (this.isNull) return "";

        StringBuilder buff = new StringBuilder();
        buff.append(this.month).append("月");
        buff.append(this.day).append("日");
        buff.append(this.hour).append("时");
        buff.append(this.minitue).append("分");
        //buff.append("month=").append(this.month);
        //buff.append(",day=").append(this.day);
        //buff.append(", hour=").append(this.hour);
        //buff.append(", minute=").append(this.minitue);

        return buff.toString();
    }
}
