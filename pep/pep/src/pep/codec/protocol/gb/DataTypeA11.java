/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb;

/**
 *
 * @author luxiaochung
 */
public class DataTypeA11 extends DataTypeLongBase{
    public DataTypeA11(double value){
        setValue(value);
    }

    public DataTypeA11(byte[] array){
        setArray(array,0);
    }

    public final void setValue(double value){
        this.value = java.lang.Math.round(value*100);
    }

    public double getValue(){
        return this.value/100.0;
    }

    public final void setArray(byte[] array, int beginPosition){
        setArray(array,beginPosition,4);
    }

    public byte[] getArray(){
        return getArray(4);
    }

    @Override
    public String toString(){
        return (new Double(getValue())).toString();
    }
}
