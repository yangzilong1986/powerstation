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
        setValue(value);
    }

    public DataTypeA12(byte[] array){
        setArray(array,0);
    }

    public void setValue(long value){
        this.value = value;
    }

    public long getValue(){
        return this.value;
    }

    public void setArray(byte[] array, int beginPosition){
        setArray(array,beginPosition,6);
    }

    public byte[] getArray(){
        return getArray(6);
    }

    @Override
    public String toString(){
        return (new Long(getValue())).toString();
    }
}
