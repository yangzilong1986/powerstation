/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb;

/**
 *
 * @author luxiaochung
 */
public class DataTypeA12 extends DataTypeLongBase{
    public DataTypeA12(long value){
        super();
        setValue(value);
    }

    public DataTypeA12(byte[] array){
        super();
        setArray(array,0);
    }

    public final void setValue(long value){
        this.value = value;
    }

    public long getValue(){
        return this.value;
    }

    public final void setArray(byte[] array, int beginPosition){
        setArray(array,beginPosition,6);
    }

    public byte[] getArray(){
        return getArray(6);
    }

    @Override
    public String toString(){
        if (this.isNull) return "";
        return (new Long(getValue())).toString();
    }
}
