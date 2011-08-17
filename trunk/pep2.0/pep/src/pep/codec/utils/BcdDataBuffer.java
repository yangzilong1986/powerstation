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
    protected IoBuffer dataBuff;
    private int reserveBitCount = 0;
    private long lastByte;
    
    public BcdDataBuffer(){
        super();
        dataBuff = IoBuffer.allocate(20, false);
        dataBuff.setAutoExpand(true);
    }

    public BcdDataBuffer(IoBuffer rowIoBuffer){
        super();
        dataBuff = rowIoBuffer;
    }

    //获取IoBuffer
    public IoBuffer getRowIoBuffer(){
        return dataBuff;
    }

    /**
     * 清除原来的数据
     */
    public BcdDataBuffer clear(){
        this.reserveBitCount = 0;
        dataBuff.clear();
        dataBuff.limit(dataBuff.position());
        return this;
    }

    /**
     * 回绕到开头，当一系列put或setValue后要从头读取时需要回绕到开头
     * @return
     */
    public BcdDataBuffer rewind(){
        this.reserveBitCount = 0;
        dataBuff.rewind();
        return this;
    }

    public byte[] getValue(){
        if (dataBuff.position()<=0) return new byte[0];
        
        byte[] rslt = new byte[dataBuff.position()];
        if (rslt==null) return new byte[0];
        dataBuff.rewind().get(rslt);
        return rslt;
    }

    public BcdDataBuffer setValue(byte[] bytes){
        this.reserveBitCount = 0;
        dataBuff.clear().put(bytes);
        dataBuff.limit(dataBuff.position());
        return this;
    }

    public BcdDataBuffer putByte(byte b){
        dataBuff.put(b);
        return this;
    }

    public int getByte(){
        this.reserveBitCount = 0;
        return dataBuff.get();
    }

    private BcdDataBuffer putBytes(byte[] bytes){
        dataBuff.put(bytes);
        return this;
    }

    public byte[] getBytes(int len){
        this.reserveBitCount = 0;
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

    public int restBytes(){
        return this.dataBuff.limit()-this.dataBuff.position();
    }
    
    public long getBits(int bitCount){ //bitCount bigin with 1
        long bits;
        if (bitCount<=this.reserveBitCount){
            this.reserveBitCount -= bitCount;
            bits = this.lastByte & (~(-1L << bitCount));
            this.lastByte = this.lastByte >>> (8-this.reserveBitCount);
            return bits;
        }
        
        bits = this.lastByte;
        int bytes = (bitCount-this.reserveBitCount-1)/8+1;
        long base = 1<<this.reserveBitCount;
        this.reserveBitCount = 8-((bitCount-this.reserveBitCount)%8); 
        
        for (int i=0; i<bytes;i++){
            this.lastByte = (this.dataBuff.get()+0x100)%0x100;
            bits = bits+this.lastByte*base;
            base *= 0x100;
        }
        
        bits = bits & (~(-1L << bitCount));
        this.lastByte = this.lastByte >>> (8-this.reserveBitCount);
        return bits;
    }
}
