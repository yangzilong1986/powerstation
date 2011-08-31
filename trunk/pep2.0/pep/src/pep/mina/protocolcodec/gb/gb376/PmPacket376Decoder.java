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
import pep.codec.protocol.gb.PmPacket;
import pep.codec.protocol.gb.gb376.PmPacket376;
import pep.codec.utils.BcdUtils;

/**
 *
 * @author luxiaochung
 */
public class PmPacket376Decoder extends CumulativeProtocolDecoder  {
    private final static Logger LOGGER = LoggerFactory.getLogger(PmPacket376Decoder.class);

    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        int position = in.position();   //开始位置
        int len = in.remaining();       //有效byte数
        byte[] recieve = new byte[len];
        in.get(recieve);
        LOGGER.info("====>"+BcdUtils.binArrayToString(recieve));
        int firstIndex = 0;
        int head = PmPacket376.getMsgHeadOffset(recieve,firstIndex);
        if (head!=-1) {
            PmPacket376 pack = new PmPacket376();
            pack.setValue(recieve,firstIndex);
            position = position+PmPacket.getMsgEndOffset(recieve, head);
            in.position(position);
            out.write(pack);

            return true;
        }

        in.position(position);
        return false;
    }
}
