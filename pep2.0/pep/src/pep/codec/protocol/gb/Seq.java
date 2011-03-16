/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb;

/**
 *
 * @author luxiaochung
 */
public class Seq {
    private byte value;

    public boolean getIsTpvAvalibe() {
        return (value & 0x80)==0x80;
    }

    public Seq setIsTpvAvalibe(boolean isTpvAvalibe) {
        if (isTpvAvalibe) value = (byte)(value | 0x80);
        else value = (byte)(value & 0x7F);

        return this;
    }

    public boolean getIsFirstFrame() {
        return (value & 0x40)==0x40;
    }

    public Seq setIsFirstFrame(boolean isFirstFrame) {
        if (isFirstFrame) value = (byte)(value | 0x40);
        else value = (byte)(value & 0xBF);

        return this;
    }

    public boolean getIsFinishFrame() {
        return (value & 0x20)==0x20;
    }

    public Seq setIsFinishFrame(boolean isFinishFrame) {
        if (isFinishFrame) value = (byte)(value | 0x20);
        else value = (byte)(value &0xDF);

        return this;
    }

    public boolean getIsNeedCountersign() {
        return (value & 0x10)==0x10;
    }

    public Seq setIsNeedCountersign(boolean isNeedCountersign) {
        if (isNeedCountersign) value = (byte)(value | 0x10);
        else value = (byte)(value & 0xEF);

        return this;
    }

    public byte getSeq() {
        return (byte)(value & 0x0F);
    }

    public Seq setSeq(byte seq) {
        value = (byte)((value & 0xF0) + (seq &0x0F));

        return this;
    }

    public byte getValue(){
        return value;
    }

    public Seq setValue(byte value){
        this.value = value;

        return this;
    }

    @Override
    public String toString(){
        StringBuilder buff = new StringBuilder();
        buff.append("TpV=").append(this.getIsTpvAvalibe());
        buff.append(", FIR=").append(this.getIsFirstFrame());
        buff.append(", FIN=").append(this.getIsFinishFrame());
        buff.append(", CON=").append(this.getIsNeedCountersign());
        buff.append(", Seq=").append(this.getSeq());

        return buff.toString();
    }
}
