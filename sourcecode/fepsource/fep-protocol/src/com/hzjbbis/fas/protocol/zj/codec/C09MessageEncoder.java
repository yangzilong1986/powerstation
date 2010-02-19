package com.hzjbbis.fas.protocol.zj.codec;

import com.hzjbbis.fas.model.FaalReadAlertRequest;
import com.hzjbbis.fas.model.FaalRequestParam;
import com.hzjbbis.fas.protocol.zj.parse.ParseTool;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.zj.MessageZj;
import com.hzjbbis.fk.message.zj.MessageZjHead;
import com.hzjbbis.fk.model.BizRtu;
import com.hzjbbis.fk.model.RtuManage;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class C09MessageEncoder extends AbstractMessageEncoder {
    public IMessage[] encode(Object obj) {
        List rt = null;
        try {
            if (obj instanceof FaalReadAlertRequest) {
                int point;
                FaalReadAlertRequest para = (FaalReadAlertRequest) obj;

                Calendar stime = para.getStartTime();

                if (para.getTn().equals("FF")) point = Integer.parseInt(para.getTn(), 16);
                else point = Integer.parseInt(para.getTn());
                int num = para.getCount();
                int alr = ParseTool.HexToDecimal(((FaalRequestParam) para.getParams().get(0)).getName());

                int len = 9;

                List rtuid = para.getRtuIds();
                List cmdIds = para.getCmdIds();
                rt = new ArrayList();
                for (int iter = 0; iter < rtuid.size(); ++iter) {
                    String id = (String) rtuid.get(iter);
                    BizRtu rtu = RtuManage.getInstance().getBizRtuInCache(id);

                    MessageZjHead head = new MessageZjHead();
                    head.c_dir = 0;
                    head.c_expflag = 0;
                    head.c_func = 9;

                    head.rtua = rtu.getRtua();

                    head.iseq = 0;

                    head.dlen = (short) len;

                    byte[] frame = new byte[len];
                    frame[0] = (byte) point;
                    frame[1] = (byte) (alr & 0xFF);
                    frame[2] = (byte) ((alr & 0xFF00) >>> 8);
                    frame[3] = ParseTool.IntToBcd(stime.get(1) % 100);
                    frame[4] = ParseTool.IntToBcd(stime.get(2) + 1);
                    frame[5] = ParseTool.IntToBcd(stime.get(5));
                    frame[6] = ParseTool.IntToBcd(stime.get(11));
                    frame[7] = ParseTool.IntToBcd(stime.get(12));
                    frame[8] = (byte) num;

                    MessageZj msg = new MessageZj();
                    msg.data = ByteBuffer.wrap(frame);

                    msg.setCmdId((Long) cmdIds.get(iter));

                    msg.head = head;
                    msg.setMsgCount(1);

                    rt.add(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (rt != null) {
            IMessage[] msgs = new IMessage[rt.size()];
            rt.toArray(msgs);
            return msgs;
        }
        return null;
    }
}