/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb;

/**
 *
 * @author luxiaochung
 */
public class DataTypeA13 extends DataTypeLongBase{
    public DataTypeA13(double value){
        super();
        setValue(value);
    }

    public DataTypeA13(byte[] array){
        super();
        setArray(array,0);
    }

    public final void setValue(double value){
        this.value = java.lang.Math.round(value*10000);
    }

    public double getValue(){
        return this.value/10000.0;
    }

    public final void setArray(byte[] array, int beginPosition){
        setArray(array,beginPosition,4);
    }

    public byte[] getArray(){
        return getArray(4);
    }

    @Override
    public String toString(){
        if (this.isNull) return "";
        return (new Double(getValue())).toString();
    }
}
