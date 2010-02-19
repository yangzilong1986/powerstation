package com.hzjbbis.fas.protocol.zj.codec;

import com.hzjbbis.exception.MessageEncodeException;
import com.hzjbbis.fas.model.FaalReadCurrentDataRequest;
import com.hzjbbis.fas.model.FaalRequestParam;
import com.hzjbbis.fas.protocol.conf.ProtocolDataItemConfig;
import com.hzjbbis.fas.protocol.zj.parse.DataItemCoder;
import com.hzjbbis.fas.protocol.zj.parse.ParseTool;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.zj.MessageZj;
import com.hzjbbis.fk.message.zj.MessageZjHead;
import com.hzjbbis.fk.model.BizRtu;
import com.hzjbbis.fk.model.RtuManage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class C01MessageEncoder extends AbstractMessageEncoder {
    private static Log log = LogFactory.getLog(C01MessageEncoder.class);

    public IMessage[] encode(Object obj) {
        List rt = null;
        try {
            if (obj instanceof FaalReadCurrentDataRequest) {
                FaalReadCurrentDataRequest para = (FaalReadCurrentDataRequest) obj;

                String[] sps = para.getTn();
                if (sps == null) {
                    throw new MessageEncodeException("未指定测量点");
                }
                byte[] points = new byte[sps.length];
                for (int i = 0; i < sps.length; ++i) {
                    points[i] = Byte.parseByte(sps[i]);
                }

                List dks = para.getParams();
                if ((dks == null) || (dks.size() <= 0)) {
                    throw new MessageEncodeException("未指定召测数据项");
                }
                int[] datakeys = new int[dks.size()];
                int[] itemlen = new int[dks.size()];

                for (int i = 0; i < dks.size(); ++i) {
                    FaalRequestParam frp = (FaalRequestParam) dks.get(i);
                    datakeys[i] = ParseTool.HexToDecimal(frp.getName());
                    try {
                        itemlen[i] = getDataItemConfig(frp.getName()).getLength();
                    } catch (Exception e) {
                        throw new MessageEncodeException("召测不支持的参数--" + frp.getName());
                    }
                }
                dks = null;

                int len = datakeys.length * 2 + 8;

                byte[] frame = new byte[len];
                for (int i = 0; i < points.length; ++i) {
                    int index = 0;
                    int flag = 1;
                    index = (points[i] & 0xFF) / 8;
                    flag <<= (points[i] & 0xFF) % 8;
                    frame[index] = (byte) ((frame[index] & 0xFF | flag) & 0xFF);
                }
                int loc = 8;
                int fdlen = 0;
                int ntnum = 0;
                List titems = new ArrayList();
                for (int j = 0; j < datakeys.length; ++j) {
                    if (ParseTool.isTask(datakeys[j])) {
                        titems.add(new byte[]{(byte) (datakeys[j] & 0xFF), (byte) ((datakeys[j] & 0xFF00) >>> 8)});
                    } else {
                        frame[loc] = (byte) (datakeys[j] & 0xFF);
                        frame[(loc + 1)] = (byte) ((datakeys[j] & 0xFF00) >>> 8);
                        loc += 2;
                        fdlen += 2;
                        fdlen += itemlen[j] * sps.length;
                        ++ntnum;
                    }
                }

                List rtuid = para.getRtuIds();
                if (rtuid == null) {
                    throw new MessageEncodeException("未指定召测终端");
                }
                List cmdIds = para.getCmdIds();
                if (cmdIds == null) {
                    throw new MessageEncodeException("命令ID缺失");
                }
                rt = new ArrayList();
                for (int i = 0; i < rtuid.size(); ++i) {
                    String id = (String) rtuid.get(i);
                    BizRtu rtu = RtuManage.getInstance().getBizRtuInCache(id);
                    if (rtu == null) {
                        log.info("终端信息未在缓存列表：" + id);
                    } else {
                        int datamax = DataItemCoder.getDataMax(rtu);

                        int msgcount = 0;

                        if (titems.size() > 0) {
                            for (int k = 0; k < titems.size(); ++k) {
                                MessageZjHead head = createHead(rtu);
                                head.dlen = 10;

                                byte[] frameA = new byte[10];
                                System.arraycopy(frame, 0, frameA, 0, 8);
                                System.arraycopy((byte[]) (byte[]) titems.get(k), 0, frameA, 8, 2);
                                MessageZj msg = new MessageZj();

                                msg.setCmdId((Long) cmdIds.get(i));

                                ++msgcount;
                                msg.data = ByteBuffer.wrap(frameA);
                                msg.head = head;

                                rt.add(msg);
                            }
                        }

                        if ((datamax >= fdlen + 8) || (datakeys.length == 1)) {
                            if (ntnum > 0) {
                                len = 8 + ntnum * 2;
                                MessageZjHead head = createHead(rtu);
                                head.dlen = (short) len;

                                byte[] frameA = new byte[len];
                                System.arraycopy(frame, 0, frameA, 0, len);

                                MessageZj msg = new MessageZj();

                                msg.setCmdId((Long) cmdIds.get(i));

                                ++msgcount;
                                msg.data = ByteBuffer.wrap(frameA);
                                msg.head = head;

                                rt.add(msg);
                            }
                        } else {
                            int dnum = 0;
                            int pos = 0;
                            int curlen = 0;
                            for (int j = 0; j < ntnum; ++j) {
                                ++dnum;
                                curlen += 2 + itemlen[j] * sps.length;
                                if ((curlen + 8 <= datamax) && (j != ntnum - 1)) continue;
                                MessageZjHead head = createHead(rtu);
                                head.dlen = (short) (8 + dnum * 2);

                                byte[] frameA = new byte[head.dlen];
                                System.arraycopy(frame, 0, frameA, 0, 8);
                                System.arraycopy(frame, 8 + pos * 2, frameA, 8, head.dlen - 8);

                                MessageZj msg = new MessageZj();

                                msg.setCmdId((Long) cmdIds.get(i));

                                ++msgcount;
                                msg.data = ByteBuffer.wrap(frameA);
                                msg.head = head;
                                rt.add(msg);
                                pos += dnum;
                                dnum = 0;
                                curlen = 0;
                            }

                        }

                        setMsgcount(rt, msgcount);
                    }
                }
            }
        } catch (Exception e) {
            throw new MessageEncodeException(e);
        }
        if (rt != null) {
            IMessage[] msgs = new IMessage[rt.size()];
            rt.toArray(msgs);
            return msgs;
        }
        return null;
    }

    private MessageZjHead createHead(BizRtu rtu) {
        MessageZjHead head = new MessageZjHead();
        head.c_dir = 0;
        head.c_expflag = 0;
        head.c_func = 1;

        head.rtua = rtu.getRtua();

        head.iseq = 0;

        return head;
    }

    private ProtocolDataItemConfig getDataItemConfig(String datakey) {
        return this.dataConfig.getDataItemConfig(datakey);
    }

    private void setMsgcount(List msgs, int msgcount) {
        for (Iterator iter = msgs.iterator(); iter.hasNext();) {
            MessageZj msg = (MessageZj) iter.next();
            if (msg.getMsgCount() == 0) msg.setMsgCount(msgcount);
        }
    }
}