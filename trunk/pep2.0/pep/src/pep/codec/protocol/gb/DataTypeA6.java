/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb;

/**
 *
 * @author luxiaochung
 */
public class DataTypeA6 extends DataTypeA5A6Base {
    public DataTypeA6(float value){
        setValue(value);
    }

    public DataTypeA6(byte[] array){
        setArray(array,0);
    }

    @Override
    public float getValue() {
        return (float) (this.value/100.0);
    }

    @Override
    public final void setValue(float value) {
        this.value = (int) Math.round(Math.floor(value * 100));
    }

    @Override
    public String toString() {
        if (this.isNull) return "";
        return (new Double(value/ 100.0)).toString();
    }
}
