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
public class DataTypeA4 {
    private byte value;

    public DataTypeA4(byte value){
        this.value = value;
    }

    public DataTypeA4(byte[] array){
        setArray(array,0);
    }

    public byte getValue(){
        return this.value;
    }

    public void setValue(byte value){
        this.value = value;
    }

    public void setArray(byte[] array){
        setArray(array,0);
    }

    public void setArray(byte[] array, int beginPosition){
        if (array.length-beginPosition<1)
            throw new IllegalArgumentException();
        else{
            boolean s = (array[beginPosition]&0x80)==0x80;
            this.value = (byte) BcdUtils.bcdToInt(array[beginPosition]&0x7F);
            if (s) this.value *= -1;
        }
    }

    public byte[] getArray(){
        byte[] val = new byte[1];

        boolean s = (this.value<0);
        int base = s ? -1*this.value : this.value;
        val[0] = BcdUtils.intToBcd(base);
        if (s) val[0] |= 0x80;
        return val;
    }

    @Override
    public String toString(){
        return (new Byte(this.value)).toString();
    }
}
