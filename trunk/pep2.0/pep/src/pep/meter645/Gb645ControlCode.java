/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.meter645;

/**
 *
 * @author luxiaochung
 */
public class Gb645ControlCode {

    private byte value;

    public Gb645ControlCode(){
        value = 0;
    }

    public Gb645ControlCode(byte value){
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

    public Gb645ControlCode isFromMast(boolean value){
        if (value)
            this.value &= 0x3F;  //主站发出没有异常位
        else
            this.value |= 0x80;
        return this;
    }

    public boolean isYichang() {
        return (value & 0x40)==0x40;
    }

    public Gb645ControlCode isYichang(boolean value){
        if (value)
            this.value |= 0x40;
        else
            this.value &= 0xBF;
        return this;
    }

    public boolean isHasHouxuzhen(){
        return (this.value & 0x20) == 0x20;
    }

    public Gb645ControlCode isHasHouxuzhen(boolean value){
        if (value)
            this.value |= 0x20;
        else
            this.value &= 0xDF;
        return this;
    }

    public byte getFuncCode(){
        return (byte) (this.value & 0x1F);
    }

    public Gb645ControlCode setFuncCode(byte code){
        this.value = (byte) (this.value & 0xE0);
        this.value |= code &0x1F;
        return this;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("value=");
        sb.append(this.value);
        sb.append(" (");
        if (this.isFromMast())
            sb.append("从主站发出");
        else{
            sb.append("从表计返回");
            if(this.isYichang())
                sb.append("异常");
        }
        if (this.isHasHouxuzhen())
            sb.append("有后续帧");
        else
            sb.append("无后续帧");


        sb.append(')');
        return sb.toString();
    }
}
