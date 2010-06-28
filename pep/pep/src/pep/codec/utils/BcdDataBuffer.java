/*
 * 用于从IoBuffer中取和写基础数据
 */

package pep.codec.utils;

import java.util.Date;
import org.apache.mina.core.buffer.IoBuffer;

/**
 *
 * @author luxiaochung
 */
public class BcdDataBuffer {
    private IoBuffer dataBuff;

    public BcdDataBuffer(){
        super();
        dataBuff = IoBuffer.allocate(20, false);
        dataBuff.setAutoExpand(true);
    }

    /**
     * 清除原来的数据
     */
    public BcdDataBuffer clear(){
        dataBuff.clear();
        dataBuff.limit(dataBuff.position());
        return this;
    }

    /**
     * 回绕到开头，当一系列put或setValue后要从头读取时需要回绕到开头
     * @return
     */
    public BcdDataBuffer rewind(){
        dataBuff.rewind();
        return this;
    }

    public byte[] getValue(){
        byte[] rslt = new byte[dataBuff.position()];
        dataBuff.rewind().get(rslt);
        return rslt;
    }

    public BcdDataBuffer setValue(byte[] bytes){
        dataBuff.clear().put(bytes);
        dataBuff.limit(dataBuff.position());
        return this;
    }

    public BcdDataBuffer putByte(byte b){
        dataBuff.put(b);
        return this;
    }

    public int getByte(){
        return dataBuff.get();
    }

    private BcdDataBuffer putBytes(byte[] bytes){
        dataBuff.put(bytes);
        return this;
    }

    private byte[] getBytes(int len){
        byte[] bytes = new byte[len];
        dataBuff.get(bytes);
        return bytes;
    }
    
    public Date getDate(String format){
        int len = format.length() /2 ;
        byte[] dateBytes = getBytes(len);
        return BcdUtils.bcdToDate(dateBytes, format, 0);
    }
    
    public BcdDataBuffer putDate(Date date, String format){
        byte[] dateBytes = BcdUtils.dateToBcd(date, format);
        return putBytes(dateBytes);
    }

    public long getBcdInt(int len){
        byte [] intBytes = getBytes(len);
        return BcdUtils.bcdToInt(intBytes, 0, len);
    }

    public BcdDataBuffer putBcdInt(long value, int len){
        return this.putBytes(BcdUtils.intTobcd(value, len));
    }

    //dec 比表示小数位
    public double getBcdFloat(int len, int dec){
        long intValue = this.getBcdInt(len);
        double floatValue = intValue;
        for (int i=0; i<dec; i++)
            floatValue /= 10;
        return floatValue;
    }
    
    public BcdDataBuffer putBcdFloat(double floatValue, int len, int dec){
        long intValue = Math.round(floatValue*Math.pow(10, dec));
        return putBcdInt(intValue, len);
    }
    
    public int getWord(){
        int lByte = this.getByte();
        int hByte = this.getByte();
        return lByte+hByte*0x100;
    }
    
    public BcdDataBuffer putWord(int word){
        this.putByte((byte) (word % 0x100));
        return this.putByte((byte) (word / 0x100));
    }
    
    public long getLongWord(){
        int lword = this.getWord();
        int hword = this.getWord();
        return lword+hword*0x10000;
    }
    
    public BcdDataBuffer putLongWord(long longWord){
        putWord((int) (longWord % 0x10000));
        return putWord((int) (longWord / 0x10000));
    }
}
