/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.mina.protocolcodec.gb;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 *
 * @author luxiaochung
 */
public class PmPacket376CodecFactory  implements ProtocolCodecFactory {
    private ProtocolEncoder encoder;
    private ProtocolDecoder decoder;

    public PmPacket376CodecFactory(){
        encoder = new PmPacket376Encoder();
        decoder = new PmPacket376Decoder();
    }
    
    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
        return encoder;
    }

    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
        return decoder;
    }
}
