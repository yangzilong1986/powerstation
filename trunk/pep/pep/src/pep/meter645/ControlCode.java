/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.meter645;

/**
 *
 * @author luxiaochung
 */
public class ControlCode {

    private byte value;

    public ControlCode(){
        value = 0;
    }

    public ControlCode(byte value){
        this.value = value;
    }

    public void setValue(byte value){
        this.value = value;
    }

    public byte getValue(){
        return value;
    }

    public boolean isFromMast(){
        return (value & 0x80)==0;
    }

    public void isFromMast(boolean value){
        if (value)
            this.value &= 0x3F;  //主站发出没有异常位
        else
            this.value |= 0x80;
    }

    public boolean isYichang() {
        return (value & 0x40)==0x40;
    }

    public void isYichang(boolean value){
        if (value)
            this.value |= 0x40;
        else
            this.value &= 0xBF;
    }

    public boolean isHasHouxuzhen(){
        return (this.value & 0x20) == 0x20;
    }

    public void isHasHouxuzhen(boolean value){
        if (value)
            this.value |= 0x20;
        else
            this.value &= 0xDF;
    }

    public byte getFuncCode(){
        return (byte) (this.value & 0x1F);
    }

    public void setFuncCode(byte code){
        this.value = (byte) (this.value & 0xE0);
        this.value |= code &0x1F;
    }
}
