/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb;

import pep.codec.utils.BcdUtils;

/**
 *
 * @author luxiaochung
 */
public class PmPacket376DT {
    private byte[] value;

    public PmPacket376DT(byte[] value){
        if (value.length==2) this.value = value;
        else {
            value = new byte[2];
            this.setFn(1);
        }
    }

    public PmPacket376DT(int fn){
        value = new byte[2];
        this.setFn(fn);
    }

    public PmPacket376DT setValue(byte[] value){
        if (value.length==2) this.value = value;
        else throw  new IllegalArgumentException();
        return this;
    }

    public byte[] getValue(){
        return value;
    }

    public PmPacket376DT setFn(int fn){
        if (fn>0){
            value[0] = (byte)(1 << ((fn-1)%8));
            value[1] = (byte)(fn/8);
        }
        else throw  new IllegalArgumentException();
        return this;
    }

    public int getFn(){
        return (BcdUtils.bitSetOfByte(value[0])+8*(value[1]));
    }

    @Override
    public String toString(){
        StringBuffer buff = new StringBuffer();
        buff.append("fn=").append(this.getFn());
        return buff.toString();
    }
}
