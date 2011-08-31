/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.mina.protocolcodec.gb.gb376;

/**
 *
 * @author luxiaochung
 */
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import pep.codec.protocol.gb.gb376.PmPacket376;

public class PmPacket376Encoder implements ProtocolEncoder {

    final private byte[] PREBYTES = {(byte) 0xfe, (byte) 0xfe, (byte) 0xfe, (byte) 0xfe};
    final private int PREBYTES_COUNT = PREBYTES.length;

    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        if (message != null) {
            PmPacket376 pack = (PmPacket376) message;
            IoBuffer buffer = IoBuffer.allocate(pack.length() + PREBYTES_COUNT, false);
            buffer.put(PREBYTES);
            buffer.put(pack.getValue());
            buffer.flip();
            out.write(buffer);
        } else {
            out.write(new byte[0]);
        }
    }

    public void dispose(IoSession session) throws Exception {
        // nothing to dispose
    }
}
