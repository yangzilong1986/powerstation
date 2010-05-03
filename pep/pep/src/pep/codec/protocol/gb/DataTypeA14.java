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
public class DataTypeA14 extends DataTypeLongBase{
    public DataTypeA14(double value){
        setValue(value);
    }

    public DataTypeA14(byte[] array){
        setArray(array,0);
    }

    public void setValue(double value){
        this.value = java.lang.Math.round(value*10000);
    }

    public double getValue(){
        return this.value/10000.0;
    }

    public void setArray(byte[] array, int beginPosition){
        setArray(array,beginPosition,5);
    }

    public byte[] getArray(){
        return getArray(5);
    }

    @Override
    public String toString(){
        return (new Double(getValue())).toString();
    }
}
