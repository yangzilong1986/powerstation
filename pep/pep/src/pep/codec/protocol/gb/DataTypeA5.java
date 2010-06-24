/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb;

/**
 *
 * @author luxiaochung
 */
public class DataTypeA5 extends DataTypeA5A6Base {
    
    public DataTypeA5(float value){
        setValue(value);
    }
    
    public DataTypeA5(byte[] array){
        setArray(array,0);
    }
    
    public float getValue(){
        return (float) (this.value/10.0);
    }

    public final void setValue(float value) {
        this.value = (int) Math.round(Math.floor(value * 10));
    }
    
}
