package com.hisun.ccb.handler;

import com.hisun.exception.HiException;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import com.hisun.pubinterface.IHandler;
import com.hisun.util.HiByteBuffer;
import com.hisun.util.HiStringUtils;

public class HiCalcToaLength implements IHandler {
    private int _offset;
    private int _length;

    public HiCalcToaLength() {
        this._offset = 283;
        this._length = 6;
    }

    public void process(HiMessageContext ctx) throws HiException {
        HiMessage msg = ctx.getCurrentMsg();
        byte[] data = ((HiByteBuffer) msg.getBody()).getBytes();

        int fillLength = data.length - (this._offset + this._length);

        if (fillLength < 0) ;
        String tmpstr = HiStringUtils.leftPad(fillLength, this._length);

        System.arraycopy(tmpstr.getBytes(), 0, data, this._offset, this._length);

        msg.setBody(new HiByteBuffer(data));
    }

    public void setLength(int length) {
        this._length = length;
    }

    public int getLength() {
        return this._length;
    }

    public void setOffset(int offset) {
        this._offset = offset;
    }

    public int getOffset() {
        return this._offset;
    }
}