package com.hzjbbis.exception;

import com.hzjbbis.util.HexDump;

import java.io.IOException;
import java.nio.ByteBuffer;

public class MessageParseException extends IOException {
    private static final long serialVersionUID = 200603141603L;
    private ByteBuffer buffer;

    public MessageParseException(String message, ByteBuffer buff) {
        super(message);
        if (null == buff) return;
        if (buff.position() > 0) buff.rewind();
        this.buffer = buff.slice();
    }

    public String getMessage() {
        String message = super.getMessage();

        if (message == null) {
            message = "";
        }

        if (this.buffer != null) {
            return message + ((message.length() > 0) ? " " : "") + "(Hexdump: " + HexDump.hexDump(this.buffer) + ')';
        }

        return message;
    }
}