/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb;

/**
 *
 * @author luxiaochung
 */
public class ControlCode {

    private byte value;

    public ControlCode(){
        super();
    }

    public ControlCode(byte value) {
        this();
        this.value = value;
    }

    public boolean getIsUpDirect(){
        return (value & 0x80)==0x80;
    }

    public ControlCode setIsUpDirect(boolean isUpDirect){
        if (isUpDirect) value = (byte) (getValue() | 0x80);
        else value = (byte) (getValue() & 0x7F);

        return this;
    }

    public boolean getIsOrgniger(){
        return (value &0x40)==0x40;
    }

    public ControlCode setIsOrgniger(boolean isOrgniger){
        if (isOrgniger) value = (byte) (getValue() | 0x40);
        else value = (byte) (getValue() & 0xBF);

        return this;
    }

    public byte getDownDirectFrameCount(){
        return (byte) ((value >> 5) & 1);
    }

    public ControlCode setDownDirectFrameCount(byte countor){
        value = (byte) ((value & 0xDF) | ((countor >> 5) & 1));

        return this;
    }

    public boolean getIsDownDirectFrameCountAvaliable(){
        return (value & 0x10)==0x10;
    }

    public ControlCode setIsDownDirectFrameCountAvaliable(boolean isAvaliable){
        if (isAvaliable) value = (byte) (getValue() | 0x10);
        else value = (byte) (getValue() & 0xEF);

        return this;
    }

    public byte getFunctionKey(){
        return (byte) (value & 0x0F);
    }

    public ControlCode setFunctionKey(byte functionKey){
        value = (byte) ((value & 0xF0) | (functionKey & 0x0F));

        return this;
    }

    public boolean getUpDirectIsAppealCall(){
        return (value & 0x20)==0x20;
    }

    public ControlCode setUpDirectIsAppealCall(boolean appealCall){
        if (appealCall) value = (byte)(value | 0x20);
        else value = (byte)(value & 0xDF);

        return this;
    }

    public byte getValue() {
        return value;
    }

    public ControlCode setValue(byte value) {
        this.value = value;

        return this;
    }

    @Override
    public String toString(){
        StringBuffer buff = new StringBuffer();

        if (this.getIsUpDirect()) buff.append("上行帧; ");
        else buff.append("下行帧; ");

        if (this.getIsOrgniger()) buff.append("启动帧; ");
        else buff.append("从动帧; ");

        if (this.getIsUpDirect()) {
            buff.append("帧计数位=").append(this.getDownDirectFrameCount());
            if (this.getUpDirectIsAppealCall()) buff.append("帧计数有效");
            else buff.append("帧计数无效");
        }
        else {
            if (this.getUpDirectIsAppealCall()) buff.append("要求访问; ");
            else buff.append("不要求访问; ");
        }

        buff.append("功能码=").append(this.getFunctionKey());
        
        return buff.toString();
    }
}
