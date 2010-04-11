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
    private byte protocolVersion;
    private byte[] data;

    public PmPacket(){
        super();
        controlCode = new ControlCode();
        address = new Address();
    }

    public ControlCode getControlCode(){
        return controlCode;
    }

    public Address getAddress(){
        return address;
    }

    public byte getProtocolVersion(){
        return protocolVersion;
    }

    public void setProtocolVersion(byte version){
        protocolVersion = version;
    }

    public byte[] getData(){
        return data;
    }

    public byte[] getValue(){
        int len = 1+5+data.length;
        int lenfield = (len<<2)+(protocolVersion & 0xFFFC);

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
        buff.put(data);
        buff.put(calcCs(result,7,len+7));
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
