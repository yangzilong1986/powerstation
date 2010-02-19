package com.hzjbbis.fas.protocol.gw.handler;

import com.hzjbbis.exception.MessageDecodeException;
import com.hzjbbis.exception.MessageEncodeException;
import com.hzjbbis.exception.ProtocolHandleException;
import com.hzjbbis.fas.model.FaalRequest;
import com.hzjbbis.fas.protocol.codec.MessageCodecFactory;
import com.hzjbbis.fas.protocol.codec.MessageDecoder;
import com.hzjbbis.fas.protocol.codec.MessageEncoder;
import com.hzjbbis.fas.protocol.handler.ProtocolHandler;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.gw.MessageGw;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PrototalHandlerImpl implements ProtocolHandler {
    private static final Log log = LogFactory.getLog(PrototalHandlerImpl.class);
    private MessageCodecFactory codecFactory;

    public void setCodecFactory(MessageCodecFactory codecFactory) {
        this.codecFactory = codecFactory;
    }

    public MessageCodecFactory getCodecFactory() {
        return this.codecFactory;
    }

    public Object process(IMessage message) {
        Object value;
        if (!(message instanceof MessageGw)) {
            throw new ProtocolHandleException("Unsupported message type: " + message.getClass());
        }

        MessageGw msg = (MessageGw) message;
        int funCode = msg.getAFN() & 0xFF;

        MessageDecoder decoder = this.codecFactory.getDecoder(funCode);
        if (decoder == null) throw new ProtocolHandleException("Can't find decoder for function code: " + funCode);
        try {
            value = decoder.decode(msg);
            if (log.isDebugEnabled()) {
                log.debug("Message decoded");
            }
        } catch (MessageDecodeException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ProtocolHandleException("Error to process message", ex);
        }
        return value;
    }

    public IMessage[] createMessage(FaalRequest request) {
        MessageEncoder encoder = this.codecFactory.getEncoder(request.getType());
        if (encoder == null) {
            throw new ProtocolHandleException("Can't find encoder for function code: " + request.getType());
        }
        try {
            return encoder.encode(request);
        } catch (MessageEncodeException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ProtocolHandleException("Error to encoding message", ex);
        }
    }
}