/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb;

/**
 *
 * @author luxiaochung
 */

import java.nio.ByteBuffer;

public class TimeProtectValue {
    private byte pfc;
    private DataTypeA16 baseTime;
    private byte limit;
    
    public TimeProtectValue(){
        super();
        baseTime = new DataTypeA16();
    }
    
    public TimeProtectValue(byte pfc, DataTypeA16 baseTime, byte limit){
        super();
        this.baseTime = baseTime;
        this.pfc = pfc;
        this.limit = limit;
    }
    
    public byte getPfc(){
        return pfc;
    }
    
    public TimeProtectValue setPfc(byte pfc){
        this.pfc = pfc;
        return this;
    }
    
    public byte getLimit(){
        return limit;
    }
    
    public TimeProtectValue setLimit(byte limit){
        this.limit = limit;
        return this;
    }
    
    public DataTypeA16 getBaseTime(){
        return baseTime;
    }
    
    public TimeProtectValue setBaseTime(DataTypeA16 baseTime){
        this.baseTime = baseTime;
        return this;
    }

    public byte[] getValue(){
        byte[] result = new byte[6];
        ByteBuffer buff = ByteBuffer.wrap(result);
        buff.put(this.pfc).put(this.baseTime.getValue()).put(this.limit);
        return result;
    }

    public TimeProtectValue setValue(byte[] value, int firstIndex){
        if (value.length-firstIndex>=6){
            this.pfc = value[firstIndex];
            this.baseTime.setValue(value,firstIndex+1);
            this.limit = value[firstIndex+5];
        }
        else {
            throw new IllegalArgumentException();
        }
        return this;
    }

    public TimeProtectValue setValue(byte[] value){
        return this.setValue(value, 0);
    }

    @Override
    public String toString(){
        StringBuffer buff = new StringBuffer();
        buff.append("pfc=").append(pfc);
        buff.append(", baseTime=(").append(this.baseTime.toString()).append(")");
        buff.append(", limit=").append(limit);
        return buff.toString();
    }
}
