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
public class DataTypeA20 extends DataTypeDateBase{
    public DataTypeA20(Date date){
        super();
        super.setDate(date);
    }

    public DataTypeA20(byte year, byte month, byte day){
        super();
        super.setYear(year).setMonth(month).setDay(day);
    }

    public DataTypeA20(byte[] array){
        super();
        setArray(array,0);
    }

    public void setArray(byte[] array){
        setArray(array,0);
    }

    public DataTypeA20(String dateStr,String format){
        super(dateStr,format);
    }

    public final void setArray(byte[] array, int beginPosition){
        super.setArray(array, beginPosition, "DDMMYY");
    }

    public byte[] getArray(){
        return super.getArray("DDMMYY");
    }

    @Override
    public DataTypeA20 setYear(byte year){
        super.setYear(year);
        return this;
    }

    @Override
    public int getYear(){
        return super.getYear();
    }

    @Override
    public DataTypeA20 setMonth(byte month){
        super.setMonth(month);
        return this;
    }

    @Override
    public int getMonth(){
        return super.getMonth();
    }

    @Override
    public DataTypeA20 setDay(byte day){
        super.setDay(day);
        return this;
    }

    @Override
    public int getDay(){
        return super.getDay();
    }

    @Override
    public String toString(){
        if (this.isNull) return "";
        return super.getDate().toString();
    }
}
