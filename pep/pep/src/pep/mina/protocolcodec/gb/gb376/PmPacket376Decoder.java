/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.mina.protocolcodec.gb.gb376;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pep.codec.protocol.gb.gb376.PmPacket376;
import pep.codec.utils.BcdUtils;

/**
 *
 * @author luxiaochung
 */
public class PmPacket376Decoder extends CumulativeProtocolDecoder  {
    private final static Logger LOGGER = LoggerFactory.getLogger(PmPacket376Decoder.class);

    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        boolean result = false;
        int position = in.position();
        int len = in.remaining();
        byte[] recieve = new byte[len];
        in.get(recieve);
        LOGGER.info("====>"+BcdUtils.binArrayToString(recieve));
        int firstIndex = 0;
        int head = PmPacket376.getMsgHeadOffset(recieve,firstIndex);
        while (head!=-1) {
            PmPacket376 pack = new PmPacket376();
            pack.setValue(recieve,firstIndex);
            position = position+head+pack.length();
            firstIndex = firstIndex+head+pack.length();
            out.write(pack);
            result = true;
            
            head = PmPacket376.getMsgHeadOffset(recieve,firstIndex);
        }

        in.position(position);
        return result;
    }
}
