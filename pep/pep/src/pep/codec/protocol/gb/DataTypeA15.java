/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb;

import java.util.Date;

/**
 *
 * @author luxiaochung
 */
public class DataTypeA15 extends DataTypeDateBase {
    public DataTypeA15(Date date){
        setDate(date);
    }

    public DataTypeA15(byte[] array){
        setArray(array,0);
    }

    public void setArray(byte[] array){
        setArray(array,0);
    }

    public void setArray(byte[] array, int beginPosition){
        setArray(array, beginPosition, "MIHHDDMMYY");
    }

    public byte[] getArray(){
        return getArray("MIHHDDMMYY");
    }

    @Override
    public Date getDate(){
        return super.getDate();
    }

    @Override
    public void setDate(Date date){
        super.setDate(date);
    }

    @Override
    public String toString(){
        return this.getDate().toString();
    }
}
