/*
 * 注意用法，put和setValue会将当前位置后移，而读取get总是从当前位置开始的。
 * rewind方法，将当前位置设置到最前面。
 * getValue方法返回整个序列，而不是从开始到当前位置的值。
 */

package pep.codec.protocol.gb;

import org.apache.mina.core.buffer.IoBuffer;
import pep.codec.utils.BcdUtils;

/**
 *
 * @author luxiaochung
 */
public class PmPacketData{
    private IoBuffer dataBuff;

    public PmPacketData(){
        super();
        dataBuff = IoBuffer.allocate(20, false);
        dataBuff.setAutoExpand(true);
    }

    /**
     * 清除原来的数据
     */
    public PmPacketData clear(){
        dataBuff.clear();
        dataBuff.limit(dataBuff.position());
        return this;
    }

    /**
     * 回绕到开头，当一系列put或setValue后要从头读取时需要回绕到开头
     * @return
     */
    public PmPacketData rewind(){
        dataBuff.rewind();
        return this;
    }

    public byte[] getValue(){
        byte[] rslt = new byte[dataBuff.limit()];
        dataBuff.rewind().get(rslt);
        return rslt;
    }

    public PmPacketData setValue(byte[] bytes){
        dataBuff.clear().put(bytes);
        dataBuff.limit(dataBuff.position());
        return this;
    }

    public PmPacketData put(byte b){
        dataBuff.put(b);
        return this;
    }

    public PmPacketData put(byte[] bytes){
        dataBuff.put(bytes);
        return this;
    }

    public PmPacketData putDA(PmPacketDA da){
        dataBuff.put(da.getValue());
        return this;
    }

    public void getDA(PmPacketDA da){
        byte[] bytes = new byte[2];
        dataBuff.get(bytes);
        da.setValue(bytes);
    }
    
    public PmPacketData putDT(PmPacketDT dt){
        dataBuff.put(dt.getValue());
        return this;
    }
    
    public void getDT(PmPacketDT dt){
        byte[] bytes = new byte[2];
        dataBuff.get(bytes);
        dt.setValue(bytes);
    }

    @Override
    public String toString(){
        return BcdUtils.binArrayToString(dataBuff.array());
    }
}
