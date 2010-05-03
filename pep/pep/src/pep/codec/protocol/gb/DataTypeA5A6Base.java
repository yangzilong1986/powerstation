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
public abstract class DataTypeA5A6Base {
    protected int value;

    public DataTypeA5A6Base() {
    }

    public byte[] getArray() {
        boolean s = this.value < 0;
        long base = s ? -this.value : this.value;
        byte[] rslt = BcdUtils.intTobcd(base, 2);
        if (s) {
            rslt[1] |= 128;
        }
        return rslt;
    }

    public abstract float getValue();

    public void setArray(byte[] array) {
        setArray(array, 0);
    }

    public void setArray(byte[] array, int beginPosition){
        if (array.length-beginPosition<2)
            throw new IllegalArgumentException();
        else{
            boolean s = (array[beginPosition+1]&0x80)==0x80;
            this.value = BcdUtils.bcdToInt(array[beginPosition]);
            this.value += BcdUtils.bcdToInt(array[beginPosition+1] & 0x7F)*100;
            if (s) this.value *= -1;
        }
    }

    public abstract void setValue(float value);
}
