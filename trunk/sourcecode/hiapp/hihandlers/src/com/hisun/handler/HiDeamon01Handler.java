package com.hisun.handler;

import com.hisun.exception.HiException;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import com.hisun.pubinterface.IHandler;
import com.hisun.util.HiByteBuffer;

public class HiDeamon01Handler implements IHandler {
    private int _code_begin;
    private int _code_end;
    private int _tmOut;

    public HiDeamon01Handler() {
        this._code_begin = 0;
        this._code_end = 0;
        this._tmOut = 0;
    }

    public void setTmOut(int tmOut) {
        this._tmOut = tmOut;
    }

    public void setCode_begin(int code_begin) {
        this._code_begin = code_begin;
    }

    public void setCode_end(int code_end) {
        this._code_end = code_end;
    }

    public void process(HiMessageContext ctx) throws HiException {
        HiMessage msg1 = ctx.getCurrentMsg();

        if (this._tmOut != 0) {
            try {
                Thread.currentThread();
                Thread.sleep(this._tmOut);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        HiByteBuffer byteBuffer = new HiByteBuffer(300);
        byteBuffer.append("00000000NSC00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000 ");
        msg1.setBody(byteBuffer);
        msg1.setHeadItem("SCH", "rp");
        ctx.setCurrentMsg(msg1);
    }
}