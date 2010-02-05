package com.hisun.ccb.handler;

import com.hisun.exception.HiException;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import com.hisun.pubinterface.IHandler;
import com.hisun.sw.big5.HiBIG5SwCode;
import com.hisun.sw.gbk.HiGBSwCode;
import com.hisun.util.HiByteBuffer;
import org.apache.commons.lang.StringUtils;

public class HiSwCodeHandler implements IHandler {
    private String _code;

    public void host2client(HiMessageContext ctx) {
        HiMessage msg = ctx.getCurrentMsg();
        HiByteBuffer buf = (HiByteBuffer) msg.getBody();
        buf = host2client(buf);
        msg.setBody(buf);
    }

    public void client2host(HiMessageContext ctx) {
        HiMessage msg = ctx.getCurrentMsg();
        HiByteBuffer buf = (HiByteBuffer) msg.getBody();
        buf = client2host(buf);
        msg.setBody(buf);
    }

    private HiByteBuffer host2client(HiByteBuffer buf) {
        int len;
        byte[] bs1 = buf.getBytes();
        byte[] bs2 = new byte[bs1.length];

        if (StringUtils.equalsIgnoreCase(this._code, "GBK")) len = HiGBSwCode.HostToClient(bs1, bs2);
        else if (StringUtils.equalsIgnoreCase(this._code, "BIG5")) len = HiBIG5SwCode.HostToClient(bs1, bs2);
        else {
            len = HiBIG5SwCode.HostToClient(bs1, bs2);
        }
        buf.clear();
        buf.append(bs2, 0, len);
        return buf;
    }

    private HiByteBuffer client2host(HiByteBuffer buf) {
        int len;
        byte[] bs1 = buf.getBytes();
        byte[] bs2 = new byte[bs1.length];
        for (int i = 0; i < bs2.length; ++i) {
            bs2[i] = 32;
        }

        if (StringUtils.equalsIgnoreCase(this._code, "GBK")) len = HiGBSwCode.ClientToHost(bs1, bs2);
        else if (StringUtils.equalsIgnoreCase(this._code, "BIG5")) len = HiBIG5SwCode.ClientToHost(bs1, bs2);
        else {
            len = HiBIG5SwCode.ClientToHost(bs1, bs2);
        }
        buf.clear();
        buf.append(bs2, 0, len);
        return buf;
    }

    public void process(HiMessageContext ctx) throws HiException {
        HiMessage msg = ctx.getCurrentMsg();
        String sch = msg.getHeadItem("SCH");
        HiByteBuffer buf = (HiByteBuffer) msg.getBody();
        if (StringUtils.equals(sch, "rq")) buf = host2client(buf);
        else {
            buf = client2host(buf);
        }
        msg.setBody(buf);
    }

    public String getCode() {
        return this._code;
    }

    public void setCode(String code) {
        this._code = code;
    }
}