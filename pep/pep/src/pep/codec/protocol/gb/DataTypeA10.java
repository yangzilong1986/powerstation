/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb;

/**
 *
 * @author luxiaochung
 */
public class DataTypeA10 extends DataTypeLongBase{
    public DataTypeA10(long value){
        setValue(value);
    }

    public DataTypeA10(byte[] array){
        setArray(array,0);
    }

    public final void setValue(long value){
        this.value = value;
    }

    public long getValue(){
        return this.value;
    }

    public final void setArray(byte[] array, int beginPosition){
        setArray(array,beginPosition,3);
    }

    public byte[] getArray(){
        return getArray(3);
    }

    @Override
    public String toString(){
        return (new Long(this.value)).toString();
    }
}
