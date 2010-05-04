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
public class DataTypeA19 extends DataTypeDateBase{
    public DataTypeA19(Date date){
        super();
        super.setDate(date);
    }

    public DataTypeA19(byte hour,byte minute){
        super();
        this.setHour(hour).setMinute(minute);
    }

    public DataTypeA19(byte[] array){
        super();
        setArray(array);
    }

    public void setArray(byte[] array){
        setArray(array,0);
    }

    public void setArray(byte[] array,int beginPosition){
        super.setArray(array, beginPosition, "MIHH");
    }

    public byte[] getArray(){
        return super.getArray("MIHH");
    }

    public Date getDate(byte year,byte month,byte day){
        super.setYear(year).setMonth(month).setDay(day);
        return super.getDate();
    }

    @Override
    public int getHour(){
        return super.getHour();
    }

    @Override
    public DataTypeA19 setHour(byte hour){
        super.setHour(hour);
        return this;
    }

    @Override
    public int getMinute(){
        return super.getMinute();
    }

    @Override
    public DataTypeA19 setMinute(byte minute){
        super.setMinute(minute);
        return this;
    }

    @Override
    public String toString(){
        StringBuffer buff = new StringBuffer();
        buff.append("hour=").append(this.hour);
        buff.append(", minute=").append(this.minitue);

        return buff.toString();
    }
}
