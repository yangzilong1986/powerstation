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
public class DataTypeA7A8Base {
    protected int value;

    public void setArray(byte[] array){
        setArray(array,0);
    }

    public void setArray(byte[] array, int beginPosition){
        if (array.length-beginPosition<2)
            throw new IllegalArgumentException();
        else{
            this.value = BcdUtils.bcdToInt(array[beginPosition]);
            this.value += BcdUtils.bcdToInt(array[beginPosition+1])*100;
        }
    }

    public byte[] getArray(){
        byte[] rslt = BcdUtils.intTobcd(this.value, 2);
        return rslt;
    }
}
