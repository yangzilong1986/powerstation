/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import pep.codec.utils.BcdUtils;

/**
 *
 * @author luxiaochung
 */
public class DataTypeDateBase {
    protected int year = 0;
    protected int month = 1;
    protected int day = 1;
    protected int hour =0;
    protected int minitue=0;
    protected int second=0;
    
    protected DataTypeDateBase(){
    }

    public DataTypeDateBase(String dateStr) {
        DateFormat df = DateFormat.getDateInstance();
        Date date;
        try {
            date = df.parse(dateStr);
            setDate(date);
        } catch (ParseException ex) {
            Logger.getLogger(DataTypeDateBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setDate(Date date){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        this.year = calendar.get(GregorianCalendar.YEAR) % 100;
        this.month = calendar.get(GregorianCalendar.MONTH)+1;
        this.day = calendar.get(GregorianCalendar.DAY_OF_MONTH);
        this.hour = calendar.get(GregorianCalendar.HOUR_OF_DAY);
        this.minitue = calendar.get(GregorianCalendar.MINUTE);
        this.second = calendar.get(GregorianCalendar.SECOND);
    }

    public Date getDate(){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(GregorianCalendar.YEAR, 2000+this.year);
        calendar.set(GregorianCalendar.MONTH, this.month-1);
        calendar.set(GregorianCalendar.DAY_OF_MONTH, day);
        calendar.set(GregorianCalendar.HOUR_OF_DAY, this.hour);
        calendar.set(GregorianCalendar.MINUTE, this.minitue);
        calendar.set(GregorianCalendar.SECOND, this.second);
        
        return calendar.getTime();
    }

    protected int getYear(){
        return this.year;
    }

    protected int getMonth(){
        return this.month;
    }

    protected int getDay() {
        return this.day;
    }

    protected int getHour() {
        return this.hour;
    }

    protected int getMinute() {
        return this.minitue;
    }

    protected int getSecond() {
        return this.second;
    }

    protected DataTypeDateBase setYear(byte year){
        this.year = year;
        return this;
    }

    protected DataTypeDateBase setMonth(byte month){
        this.month = month;
        return this;
    }

    protected DataTypeDateBase setDay(byte day) {
        this.day = day;
        return this;
    }

    protected DataTypeDateBase setHour(byte hour) {
        this.hour = hour;
        return this;
    }

    protected DataTypeDateBase setMinute(byte minute) {
        this.minitue = minute;
        return this;
    }

    protected DataTypeDateBase setSecond(byte second) {
        this.second = second;
        return this;
    }

    protected byte[] getArray(String format){
        return BcdUtils.dateToBcd(this.getDate(), format);
    }

    protected void setArray(byte[] array, int beginPosition, String format){
        if (array.length-beginPosition<format.length()/2)
            throw new IllegalArgumentException();
        else
            setDate(BcdUtils.bcdToDate(array, format, beginPosition));
    }
}
