/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import pep.codec.utils.BcdUtils;

/**
 *
 * @author luxiaochung
 */
public class DataTypeA1 {
    private  GregorianCalendar calendar = new GregorianCalendar();
    
    public DataTypeA1(Date date){
        calendar.setTime(date);
    }

    public DataTypeA1(String dateStr) {
        SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            calendar.setTime(dateformat.parse(dateStr));
        } catch (ParseException ex) {
            Logger.getLogger(DataTypeA1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DataTypeA1(){
        this(new Date());
    }

    public DataTypeA1(byte[] value){
        setArray(value);
    }

    public void setArray(byte[] value, int beginIdx){
        if (value.length-beginIdx<6)
           throw new IllegalArgumentException();
        else{
            calendar.set(GregorianCalendar.SECOND, BcdUtils.bcdToInt(value[beginIdx]));
            calendar.set(GregorianCalendar.MINUTE, BcdUtils.bcdToInt(value[beginIdx+1]));
            calendar.set(GregorianCalendar.HOUR, BcdUtils.bcdToInt(value[beginIdx+2]));
            calendar.set(GregorianCalendar.DAY_OF_MONTH, BcdUtils.bcdToInt(value[beginIdx+4]));
            calendar.set(GregorianCalendar.MONTH, BcdUtils.bcdToInt((byte)((value[beginIdx+4]-1)&0x1F)));
            calendar.set(GregorianCalendar.YEAR,2000+BcdUtils.bcdToInt(value[beginIdx+5]));
        }
    }

    public void setArray(byte[] value){
        setArray(value,0);
    }

    public byte[] getArray(){
        byte[] result = new byte[6];
        result[0] = BcdUtils.intToBcd((byte)calendar.get(GregorianCalendar.SECOND));
        result[1] = BcdUtils.intToBcd((byte)calendar.get(GregorianCalendar.MINUTE));
        result[2] = BcdUtils.intToBcd((byte)calendar.get(GregorianCalendar.HOUR));
        result[3] = BcdUtils.intToBcd((byte)calendar.get(GregorianCalendar.DAY_OF_MONTH));
        result[4] = (byte)(BcdUtils.intToBcd((byte)(calendar.get(GregorianCalendar.MONTH)+1))
                +((calendar.get(GregorianCalendar.DAY_OF_WEEK)+6) % 7)<<5);
        result[5] = BcdUtils.intToBcd((byte)(calendar.get(GregorianCalendar.YEAR) % 100));
        return result;
    }

    public Date getDate(){
        return calendar.getTime();
    }

    public void setDate(Date date){
        calendar.setTime(date);
    }

    @Override
    public String toString(){
        return calendar.toString();
    }
}
