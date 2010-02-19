package com.hzjbbis.fas.protocol.zj.codec;

import com.hzjbbis.fas.model.FaalReadProgramLogRequest;
import com.hzjbbis.fas.model.FaalRequest;
import com.hzjbbis.fas.model.HostCommand;
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

public class C04MessageEncoder extends AbstractMessageEncoder {
    public IMessage[] encode(Object obj) {
        List rt = null;
        try {
            if (obj instanceof FaalRequest) {
                FaalReadProgramLogRequest para = (FaalReadProgramLogRequest) obj;

                Calendar stime = para.getStartTime();
                if (stime == null) {
                    stime = Calendar.getInstance();
                }
                int point = Integer.parseInt(para.getTn());
                int num = para.getCount();

                int len = 7;

                List rtuid = para.getRtuIds();
                rt = new ArrayList();
                List cmdIds = para.getCmdIds();
                for (int iter = 0; iter < rtuid.size(); ++iter) {
                    String id = (String) rtuid.get(iter);

                    BizRtu rtu = RtuManage.getInstance().getBizRtuInCache(id);

                    MessageZjHead head = new MessageZjHead();
                    head.c_dir = 0;
                    head.c_expflag = 0;
                    head.c_func = 4;

                    head.rtua = rtu.getRtua();

                    head.iseq = 0;

                    head.dlen = (short) len;

                    byte[] frame = new byte[len];
                    frame[0] = (byte) point;
                    frame[1] = ParseTool.IntToBcd(stime.get(1) % 100);
                    frame[2] = ParseTool.IntToBcd(stime.get(2) + 1);
                    frame[3] = ParseTool.IntToBcd(stime.get(5));
                    frame[4] = ParseTool.IntToBcd(stime.get(11));
                    frame[5] = ParseTool.IntToBcd(stime.get(12));
                    frame[6] = (byte) num;

                    MessageZj msg = new MessageZj();
                    msg.data = ByteBuffer.wrap(frame);
                    HostCommand hcmd = new HostCommand();
                    hcmd.setId((Long) cmdIds.get(iter));
                    hcmd.setMessageCount(1);

                    msg.setCmdId(hcmd.getId());
                    msg.head = head;

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