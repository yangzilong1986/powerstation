/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb;

/**
 *
 * @author luxiaochung
 */
public class DataTypeA23 extends DataTypeLongBase{
    public DataTypeA23(float value){
        setValue(value);
    }

    public DataTypeA23(byte[] array){
        setArray(array,0);
    }

    public void setValue(float value){
        this.value = java.lang.Math.round(java.lang.Math.floor(value*10000));
    }

    public float getValue(){
        return (float) (this.value/10000.0);
    }

    @Override
    public void setArray(byte[] array, int beginPosition) {
        super.setArray(array, beginPosition, 3);
    }

    public byte[] getArray(){
        return super.getArray(3);
    }

    @Override
    public String toString(){
        return (new Float(getValue())).toString();
    }
}
