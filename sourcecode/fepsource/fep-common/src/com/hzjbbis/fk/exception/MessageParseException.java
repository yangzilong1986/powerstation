package com.hzjbbis.fk.exception;

import com.hzjbbis.fk.utils.HexDump;

import java.nio.ByteBuffer;

public class MessageParseException extends Exception {
    private static final long serialVersionUID = -5985134647725926736L;
    private ByteBuffer buffer;

    public MessageParseException(String message) {
        super(message);
    }

    public MessageParseException(String message, ByteBuffer buff) {
        super(message);
        if (buff == null) return;
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

    public String getLocalizedMessage() {
        return getMessage();
    }
}