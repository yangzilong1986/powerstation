/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb;

/**
 *
 * @author luxiaochung
 */
public class DataTypeA22 extends DataTypeLongBase{
    public DataTypeA22(float value){
        setValue(value);
    }

    public DataTypeA22(byte[] array){
        setArray(array,0);
    }

    public void setValue(float value){
        this.value = java.lang.Math.round(java.lang.Math.floor(value*10));
    }

    public float getValue(){
        return (float) (this.value/10.0);
    }

    @Override
    public void setArray(byte[] array, int beginPosition) {
        super.setArray(array, beginPosition, 1);
    }

    public byte[] getArray(){
        return super.getArray(1);
    }

    @Override
    public String toString(){
        return (new Float(getValue())).toString();
    }
}
