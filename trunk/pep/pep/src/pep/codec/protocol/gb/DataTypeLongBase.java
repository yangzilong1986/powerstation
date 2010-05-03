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
public abstract class DataTypeLongBase {
    protected long value;

    protected DataTypeLongBase(){
        super();
    }

    protected void setArray(byte[] array, int beginPosition, int len){
        if (array.length-beginPosition<len)
            throw new IllegalArgumentException();
        else
            this.value = BcdUtils.bcdToInt(array, beginPosition, len);
    }

    protected byte[] getArray(int len){
        return BcdUtils.intTobcd(this.value, len);
    }

    public abstract void setArray(byte[] array, int beginPosition);

    public void setArray(byte[] array) {
        setArray(array, 0);
    }
}
