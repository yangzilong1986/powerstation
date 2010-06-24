/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb;

/**
 *
 * @author luxiaochung
 */
public class DataTypeA27 extends DataTypeLongBase{
    public DataTypeA27(long value){
        setValue(value);
    }

    public DataTypeA27(byte[] array){
        setArray(array,0);
    }

    public final void setValue(long value){
        this.value = value;
    }

    public long getValue(){
        return this.value;
    }

    @Override
    public final void setArray(byte[] array, int beginPosition) {
        super.setArray(array, beginPosition, 4);
    }

    public byte[] getArray(){
        return super.getArray(4);
    }

    @Override
    public String toString(){
        return (new Long(getValue())).toString();
    }
}
