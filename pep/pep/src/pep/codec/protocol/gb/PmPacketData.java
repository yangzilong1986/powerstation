/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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

    public PmPacketData clear(){
        dataBuff.clear();
        dataBuff.limit(dataBuff.position());
        return this;
    }

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
