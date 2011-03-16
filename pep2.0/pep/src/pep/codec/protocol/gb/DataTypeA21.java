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
public class DataTypeA21 extends DataTypeDateBase{
    public DataTypeA21(Date date){
        super();
        super.setDate(date);
    }

    public DataTypeA21(byte year, byte month){
        super();
        super.setYear(year).setMonth(month);
    }

    public DataTypeA21(byte[] array){
        super();
        setArray(array,0);
    }

    public DataTypeA21(String dateStr,String format){
        super(dateStr,format);
    }

    public void setArray(byte[] array){
        setArray(array,0);
    }

    public final void setArray(byte[] array, int beginPosition){
        super.setArray(array, beginPosition, "MMYY");
    }

    public byte[] getArray(){
        return super.getArray("MMYY");
    }

    @Override
    public DataTypeA21 setYear(byte year){
        super.setYear(year);
        return this;
    }

    @Override
    public int getYear(){
        return super.getYear();
    }

    @Override
    public DataTypeA21 setMonth(byte month){
        super.setMonth(month);
        return this;
    }

    @Override
    public int getMonth(){
        return super.getMonth();
    }

    @Override
    public String toString(){
        if (this.isNull) return "";
        
        StringBuilder buff = new StringBuilder();
        buff.append(this.year).append("年");
        buff.append(this.month).append("月");

        //StringBuilder buff = new StringBuilder();
        //buff.append("year=").append(this.getYear());
        //buff.append(", month=").append(this.getMonth());
        return buff.toString();
    }
}
