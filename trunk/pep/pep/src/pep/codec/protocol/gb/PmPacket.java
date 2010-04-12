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

public class PmPacket {
    private ControlCode controlCode;
    private Address address;
    private Seq seq;
    private EventCountor eventCountor;
    private byte protocolVersion;
    private byte afn;
    private byte[] data;
    private byte[] authorizeData;

    public PmPacket(){
        super();
        controlCode = new ControlCode();
        address = new Address();
        seq = new Seq();
        eventCountor = new EventCountor();
        data = new byte[0];
        authorizeData = new byte[0];
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

    public byte getProtocolVersion(){
        return protocolVersion;
    }

    public PmPacket setProtocolVersion(byte version){
        protocolVersion = version;

        return this;
    }

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

    public byte[] getAuthorizeData(){
        return this.authorizeData;
    }

    public PmPacket setAuthorizeData(byte[] authorizeData){
        if ((authorizeData.length==16)||(authorizeData.length==0))
            this.authorizeData=authorizeData;

        return this;
    }

    public byte[] getValue(){
        int len = 1+5+2+data.length; //controlcode,address,afn,seq
        if (!this.controlCode.getIsUpDirect())
            len = len+this.authorizeData.length;
        else if ((this.controlCode.getIsUpDirect()) && (this.controlCode.getUpDirectIsAppealCall()))
            len = len+2;

        int lenfield = (len<<2)+(protocolVersion & 0x0003);

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
        if ((!this.controlCode.getIsUpDirect()) && (this.authorizeData.length!=0))
            buff.put(this.authorizeData);
        if ((this.controlCode.getIsUpDirect()) && (this.controlCode.getUpDirectIsAppealCall()))
            buff.put(this.eventCountor.getValue());

        buff.put(calcCs(result,6,len+6));
        buff.put((byte)0x16);

        return result;
    }

    private static byte calcCs(byte[] bytes, int beginIndex, int endIndex){
        byte cs = 0;

        for (int i=beginIndex; i<endIndex; i++)
             cs = (byte)(cs+bytes[i]);
        return cs;
    }
}
