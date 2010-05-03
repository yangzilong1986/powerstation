/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb;

/**
 *
 * @author luxiaochung
 */
public class DataTypeA9 extends DataTypeA9A25Base {
    public DataTypeA9(double value){
        setValue(value);
    }
    
    public DataTypeA9(byte[] array){
        setArray(array,0);
    }
    
    public void setValue(double value){
        this.value = java.lang.Math.round(java.lang.Math.floor(value*10000));
    }

    public double getValue(){
        return this.value/10000.0;
    }
}
