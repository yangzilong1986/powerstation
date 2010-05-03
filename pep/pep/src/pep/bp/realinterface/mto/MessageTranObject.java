/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.realinterface.mto;
import pep.bp.realinterface.IRealMessage;
/**
 *
 * @author Thinkpad
 */
public abstract class MessageTranObject implements IRealMessage{
    private int ID;
    public int getID(){
        return this.ID;
    }

    public void setID(int value){
        this.ID = value;
    }

    public abstract String toJson();

    public abstract MTOType getType();
}
