/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.meter645;

import org.apache.mina.core.buffer.IoBuffer;
import pep.codec.protocol.gb.PmPacketData;
import pep.codec.utils.BcdDataBuffer;
import pep.codec.utils.BcdUtils;

/**
 *
 * @author luxiaochung
 */
public class Gb645MeterPacket {
    private Gb645Address address;
    private Gb645ControlCode controlCode;
    private BcdDataBuffer data; //总是保存未进行任何加33减33处理的值

    public Gb645MeterPacket(String meterAddress){
        super();
        address = new Gb645Address(meterAddress);
        controlCode = new Gb645ControlCode();
        data = new BcdDataBuffer();
    }
    
    public Gb645MeterPacket setAddress(String meterAddress){
        address.setAddress(meterAddress);
        return this;
    }
    
    public Gb645Address getAddress(){
        return address;
    }
    
    public Gb645MeterPacket setControlCode(boolean isFromMast, boolean hasHouxuzhen,
            boolean isYichang, byte gongnengma){
        controlCode.isFromMast(isFromMast).isHasHouxuzhen(hasHouxuzhen).isYichang(isYichang).setFuncCode(gongnengma);
        
        return this;
    }
    
    public Gb645ControlCode getControlCode(){
        return controlCode;
    }

    public  BcdDataBuffer getData(){
        return this.data;
    }

    public PmPacketData getDataAsPmPacketData(){
        return new PmPacketData(this.data.getRowIoBuffer());
    }

    private void setDataBytes(byte[] msg, int beginIndex, int len){ //将接收到的序列转换为去掉33
        byte[] dataBytes = new byte[len];
        for (int i=0; i<len; i++)
            dataBytes[i] = (byte) (msg[beginIndex+i]+0x0100 - 0x0033);
        this.data.setValue(dataBytes);
    }

    private byte[] getDataBytes(){ //转换为发送序列
        byte[] dataBytes = this.data.getValue();
        for (int i=0; i<dataBytes.length; i++)
            dataBytes[i] = (byte) (dataBytes[i] + 0x0133);
        return dataBytes;
    }

    //获取实际的发送报文
    public byte[] getValue(){
        byte[] dataBytes = this.getDataBytes();
        IoBuffer bf = IoBuffer.allocate(20,false);
        bf.put((byte)0x68);
        bf.put(this.address.getValue());
        bf.put((byte)0x68);
        bf.put(this.controlCode.getValue());
        bf.put((byte) dataBytes.length);
        bf.put(dataBytes);
        bf.put(Gb645MeterPacket.calcCs(bf.array()));
        bf.put((byte)0x16);
        
        byte[] rslt = new byte[bf.position()];
        bf.rewind().get(rslt);
        return rslt;
    }

    private static byte calcCs(byte[] dataBytes){
        return calcCs(dataBytes,0,dataBytes.length);
    }

    private static byte calcCs(byte[] dataBytes, int beginIdx, int length){
        int cs = 0;
        for (int i=0; i<length; i++)
            cs = cs+(dataBytes[beginIdx+i]+0x0100);
        return (byte) cs;
    }

    private static int getHeadOffset(byte[] msg, int beginIndex) {
        int head = -1;

        for (int i = beginIndex; i < msg.length - 11; i++) {//68 R R R R R R 68 C L C 16 最短
            if ((msg[i] == 0x68) && (msg[i + 7] == 0x68) ) {
                head = i;
                break;
            }
        }
        return head;
    }

    public static int getMsgHeadOffset(byte[] msg,int firstIndex) {
        int head = Gb645MeterPacket.getHeadOffset(msg, firstIndex);
        while (head != -1) {
            int len = BcdUtils.byteToUnsigned(msg[head+9]);

            if (msg[head + len + 11] == 0x16) {
                if (Gb645MeterPacket.calcCs(msg, head, len + 10) == msg[head + len + 10]) {
                    return head;
                }
            }
            firstIndex = head+1;
            head = Gb645MeterPacket.getHeadOffset(msg, firstIndex);
        }

        return -1;
    }

    public static Gb645MeterPacket getPacket(byte[] msg, int headIndex) {
        if (Gb645MeterPacket.getMsgHeadOffset(msg, headIndex) == headIndex) {
            Gb645MeterPacket pack = new Gb645MeterPacket("0");
            pack.address.setValue(msg, headIndex + 1);
            pack.controlCode.setValue(msg[headIndex + 8]);
            int len = BcdUtils.byteToUnsigned(msg[headIndex + 9]);
            pack.setDataBytes(msg, headIndex + 10, len);
            return pack;
        } else {
            return null;
        }
    }
}
