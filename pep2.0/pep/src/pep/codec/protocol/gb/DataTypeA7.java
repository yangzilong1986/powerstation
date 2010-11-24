/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb;

/**
 *
 * @author luxiaochung
 */
public class DataTypeA7 extends DataTypeA7A8Base {
    public DataTypeA7(byte[] array){
        super.setArray(array);
    }

    public DataTypeA7(float value){
        setValue(value);
    }

    public final void setValue(float value){
        this.value = (int) java.lang.Math.round(java.lang.Math.floor(value*10));
    }

    public float getValue(){
        return (float) (this.value / 10.0);
    }

    @Override
    public String toString() {
        if (this.isNull) return "";
        return (new Double(value/ 10.0)).toString();
    }
}
