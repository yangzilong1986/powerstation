/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb;

/**
 *
 * @author luxiaochung
 */

import java.nio.ByteBuffer;
import pep.codec.utils.BcdUtils;

abstract public class PmPacket {
    private ControlCode controlCode;
    private Address address;
    private Seq seq;
    private byte afn;
    private byte[] data;
    private EventCountor eventCountor;
    private Authorize authorize;
    private TimeProtectValue tpv;

    public PmPacket(){
        super();
        controlCode = new ControlCode();
        address = new Address();
        seq = new Seq();
        eventCountor = new EventCountor();
        data = new byte[0];
        authorize = new Authorize();
        tpv = new TimeProtectValue();
    }

    public ControlCode getControlCode(){
        return controlCode;
    }

    public Address getAddress(){
        return address;
    }

    public Seq getSeq(){
        return seq;
    }

    public EventCountor getEC(){
        return this.eventCountor;
    }

    abstract protected byte getProtocolVersion();

    public byte[] getData(){
        return data;
    }

    public byte getAfn(){
        return afn;
    }

    public PmPacket setAfn(byte afn){
        this.afn = afn;

        return this;
    }

    public PmPacket setData(byte[] data){
        this.data = data;

        return this;
    }

    public Authorize getAuthorize(){
        return this.authorize;
    }

    public PmPacket setAuthorize(Authorize authorize){
       this.authorize=authorize;

        return this;
    }

    public TimeProtectValue getTpv(){
        return this.tpv;
    }

    public PmPacket setTpv(TimeProtectValue tpv){
        this.tpv = tpv;
        return this;
    }
    
    public byte[] getValue(){
        int len = 1+5+2+data.length; //controlcode,address,afn,seq
        if ((!controlCode.getIsUpDirect())&&(PmPacket.isNeedAuthorize(afn)))
            len = len+authorize.getValue().length;
        else if ((controlCode.getIsUpDirect()) && (controlCode.getUpDirectIsAppealCall()))
            len = len+2;

        if (seq.getIsTpvAvalibe()) len += 6;

        int lenfield = (len<<2)+(this.getProtocolVersion() & 0x0003);

        byte[] result = new byte[len+8];
        ByteBuffer buff = ByteBuffer.wrap(result); //0
        buff.put((byte)0x68);
        buff.put((byte)(lenfield & 0x00FF));
        buff.put((byte)((lenfield >> 8) &0x00FF));
        buff.put((byte)(lenfield & 0x00FF));
        buff.put((byte)((lenfield >> 8) &0x00FF));
        buff.put((byte)0x68);
        buff.put(controlCode.getValue());  //7
        buff.put(address.getValue());
        buff.put((byte)afn);
        buff.put(this.seq.getValue());
        buff.put(data);
        if ((!controlCode.getIsUpDirect()) && PmPacket.isNeedAuthorize(afn))
            buff.put(authorize.getValue());
        if ((controlCode.getIsUpDirect()) && (controlCode.getUpDirectIsAppealCall()))
            buff.put(eventCountor.getValue());
        if (seq.getIsTpvAvalibe()) 
            buff.put(tpv.getValue());

        buff.put(PmPacket.calcCs(result,6,len+6));
        buff.put((byte)0x16);

        return result;
    }

    public PmPacket setValue(byte[] msg){
        int head = PmPacket.getMsgHeadOffset(msg,getProtocolVersion());
        if (head!=-1) {
            int len = (msg[head+1]+msg[head+2]*0x10)>>2;
            controlCode.setValue(msg[head+6]);
            address.setValue(msg,head+7);
            afn = msg[head+12];
            seq.setValue(msg[head+13]);
            len -= 8;
            if (controlCode.getIsUpDirect()&&controlCode.getUpDirectIsAppealCall())
                len -= 2;
            if ((!controlCode.getIsUpDirect())&&(PmPacket.isNeedAuthorize(afn)))
                len -= Authorize.length();
            if (seq.getIsTpvAvalibe()) len -= 6;
            data = new byte[len];
            for (int i=0; i<len; i++) data[i] = msg[head+14+i];

            if (controlCode.getIsUpDirect()&&controlCode.getUpDirectIsAppealCall())
                eventCountor.setValue(msg, 14+len);
            if ((!controlCode.getIsUpDirect())&&(PmPacket.isNeedAuthorize(afn)))
                authorize.setValue(msg,16+len);
            if (seq.getIsTpvAvalibe())
                tpv.setValue(msg,16+len+Authorize.length());
        }
        return this;
    }

    public int length(){
        return this.getValue().length;
    }
    
    private static byte calcCs(byte[] bytes, int beginIndex, int endIndex){
        byte cs = 0;

        for (int i=beginIndex; i<endIndex; i++)
             cs = (byte)(cs+bytes[i]);
        return cs;
    }

    private static boolean isNeedAuthorize(byte afn){
        boolean result;
        switch (afn) {
            case 0x01:
            case 0x04:
            case 0x05:
            case 0x06:
            case 0x0F:
            case 0x10: return true;
            default: return false;
        }
    }

    @Override
    public String toString(){
        StringBuffer buff = new StringBuffer();
        buff.append("控制域: ").append(controlCode.toString()).append("\n");
        buff.append("地址域: ").append(address.toString()).append("\n");
        buff.append("数据域: ").append(BcdUtils.binArrayToString(data)).append("\n");
        if ((!controlCode.getIsUpDirect()) && PmPacket.isNeedAuthorize(afn))
            buff.append("认证信息: ").append(authorize.toString()).append("\n");
        if (controlCode.getIsUpDirect()&&(seq.getIsNeedCountersign()))
            buff.append("事件计数器: ").append(eventCountor.toString()).append("\n");
        if (seq.getIsTpvAvalibe())
            buff.append("时间标签: ").append(tpv.toString());
        return buff.toString();
    }

    protected static int getMsgHeadOffset(byte[] msg, byte protocolVersion){
        int headOffset =-1;

        int head = PmPacket.getHeadOffset(msg, 0,protocolVersion);
        while (head!=-1){
            int len = (msg[head+1] + msg[head+2]*0x0100)>>2;

            if (msg[head+len+7]==0x16){
                if (PmPacket.calcCs(msg, head+6, head+len+6)==msg[head+len+6]){
                    headOffset = head;
                    break;
                }
            }

            head = PmPacket.getHeadOffset(msg, head+1,protocolVersion);
        }

        return headOffset;
    }

    private static int getHeadOffset(byte[] msg, int beginIndex, byte protocolVersion){
        int head = -1;

        for (int i=beginIndex; i<msg.length-(1+2+2+1+1+5+1+1+1+1); i++){//68 L L L L 68 C R R R R R A S C 16 最短
            if ((msg[i]==0x68) && (msg[i+1]==msg[i+3]) && (msg[i+2]==msg[i+4]) && 
                (msg[i+5]==0x68) && ((msg[i+1]&0x03)==protocolVersion)){
                head = i;
                break;
            }
        }
        return head;
    }
}
