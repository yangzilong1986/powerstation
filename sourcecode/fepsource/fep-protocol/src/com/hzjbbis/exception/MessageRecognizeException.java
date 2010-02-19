package com.hzjbbis.exception;

import com.hzjbbis.util.HexDump;

import java.nio.ByteBuffer;

public class MessageRecognizeException extends Exception {
    private static final long serialVersionUID = 200603141603L;
    private ByteBuffer buffer;

    public MessageRecognizeException(String msg, ByteBuffer buff) {
        super(msg);
        if (null == buff) return;
        if (buff.position() > 0) buff.rewind();
        this.buffer = buff.slice();
    }

    public MessageRecognizeException(ByteBuffer buff) {
        super("消息不能识别。");
        if (null == buff) return;
        if (buff.position() > 0) buff.rewind();
        this.buffer = buff.slice();
    }

    public String getMessage() {
        String message = super.getMessage();

        if (this.buffer != null) {
            return message + ((message.length() > 0) ? " " : "") + "(Hexdump: " + HexDump.hexDump(this.buffer) + ')';
        }

        return message;
    }
}