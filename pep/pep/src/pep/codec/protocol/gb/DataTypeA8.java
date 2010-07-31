/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb;

/**
 *
 * @author luxiaochung
 */
public class DataTypeA8 extends DataTypeA7A8Base {
    public DataTypeA8(byte[] array){
        super.setArray(array);
    }

    public DataTypeA8(int value){
        setValue(value);
    }

    public final void setValue(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

    @Override
    public String toString() {
        if (this.isNull) return "";
        return (new Integer(value)).toString();
    }
}
