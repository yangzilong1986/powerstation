/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb;

/**
 *
 * @author luxiaochung
 */
public class DataTypeA25 extends DataTypeA9A25Base {
    public DataTypeA25(long value){
        setValue(value);
    }

    public DataTypeA25(byte[] array){
        setArray(array,0);
    }

    public final void setValue(long value){
        this.value = value;
    }

    public long getValue(){
        return this.value;
    }

    @Override
    public String toString() {
        return (new Double(value/ 10.0)).toString();
    }
}
