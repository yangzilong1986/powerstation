/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.utils;
import pep.common.exception.BPException;

/**
 *
 * @author Thinkpad
 */


public class SerialPortPara {
    private BaudRate baudrate; //波特率
    private int stopbit;//停止位
    private int checkbit;//校验位
    private int odd_even_bit;//奇偶位
    private int databit;//数据位

    /**
     * @return the baudrate
     */
    public BaudRate getBaudrate() {
        return baudrate;
    }

    /**
     * @param baudrate the baudrate to set
     */
    public void setBaudrate(BaudRate baudrate) {
        this.baudrate = baudrate;
    }

    /**
     * @return the stopbit
     */
    public int getStopbit() {
        return stopbit;
    }

    /**
     * @param stopbit the stopbit to set
     */
    public void setStopbit(int stopbit) throws BPException {
        if((stopbit!=1)&&(stopbit!=2))
            throw new BPException("输入停止位非法，必须为1或2");
        this.stopbit = stopbit;
    }

    /**
     * @return the checkbit
     */
    public int getCheckbit() {
        return checkbit;
    }

    /**
     * @param checkbit the checkbit to set
     */
    public void setCheckbit(int checkbit) throws BPException {
        if((checkbit!=0)&&(checkbit!=1))
            throw new BPException("输入校验位非法，必须是0或1");
        this.checkbit = checkbit;
    }

    /**
     * @return the odd_even_bit
     */
    public int getOdd_even_bit() {
        return odd_even_bit;
    }

    /**
     * @param odd_even_bit the odd_even_bit to set
     */
    public void setOdd_even_bit(int odd_even_bit) throws BPException {
        if((odd_even_bit!=0)&&(odd_even_bit!=1))
            throw new BPException("输入奇偶位非法，必须是0或1");
        this.odd_even_bit = odd_even_bit;
    }

    /**
     * @return the databit
     */
    public int getDatabit() {
        return databit;
    }

    /**
     * @param databit the databit to set
     */
    public void setDatabit(int databit) throws BPException {
        if((odd_even_bit< 5)||(odd_even_bit > 8))
            throw new BPException("输入数据位非法，必须是5-8范围内");
        this.databit = databit;
    }


    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append(this.baudrate);//波特率
        sb.append(this.stopbit-1);//停止位
        sb.append(this.checkbit);//校验位
        sb.append(this.odd_even_bit);//奇偶位
        sb.append(this.databit-5);//数据位
        sb.reverse();
        return sb.toString();
    }


}
