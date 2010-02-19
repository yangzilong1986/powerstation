package com.hzjbbis.fas.protocol.zj.codec;

import com.hzjbbis.exception.MessageEncodeException;
import com.hzjbbis.fas.model.FaalReadTaskDataRequest;
import com.hzjbbis.fas.protocol.conf.ProtocolDataItemConfig;
import com.hzjbbis.fas.protocol.zj.parse.DataItemCoder;
import com.hzjbbis.fas.protocol.zj.parse.ParseTool;
import com.hzjbbis.fas.protocol.zj.parse.TaskSetting;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.zj.MessageZj;
import com.hzjbbis.fk.message.zj.MessageZjHead;
import com.hzjbbis.fk.model.BizRtu;
import com.hzjbbis.fk.model.RtuManage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class C02MessageEncoder extends AbstractMessageEncoder {
    private static Log log = LogFactory.getLog(C02MessageEncoder.class);

    public IMessage[] encode(Object obj) {
        List rt = null;
        try {
            if (obj instanceof FaalReadTaskDataRequest) {
                FaalReadTaskDataRequest para = (FaalReadTaskDataRequest) obj;

                Calendar ptime = Calendar.getInstance();
                ptime.setTime(para.getStartTime());

                int num = para.getCount();
                int taskno = Integer.parseInt(para.getTaskNum());
                int rate = para.getFrequence();

                int len = 8;

                List rtuid = para.getRtuIds();
                List cmdIds = para.getCmdIds();
                rt = new ArrayList();
                for (int iter = 0; iter < rtuid.size(); ++iter) {
                    Calendar stime = Calendar.getInstance();
                    stime.setTime(ptime.getTime());

                    String id = (String) rtuid.get(iter);
                    BizRtu rtu = RtuManage.getInstance().getBizRtuInCache(id);

                    if (rtu == null) {
                        log.info("终端信息未在缓存列表：" + id);
                    } else {
                        TaskSetting ts = new TaskSetting(rtu.getRtua(), taskno, this.dataConfig);
                        if ((ts == null) || (ts.getRtu() == null)) {
                            log.info("终端未建档！终端--" + ParseTool.IntToHex4(rtu.getRtua()) + " 任务号--" + String.valueOf(taskno));
                        } else if (ts.getRtask() == null) {
                            log.info("终端任务未配置！终端--" + ParseTool.IntToHex4(rtu.getRtua()) + " 任务号--" + String.valueOf(taskno));
                        } else {
                            int tsu = ts.getTIUnit();
                            int tsn = ts.getTI();

                            List datacs = ts.getDI();
                            int tsbytes = 0;
                            for (int guard = 0; guard < datacs.size(); ++guard) {
                                ProtocolDataItemConfig pdc = (ProtocolDataItemConfig) datacs.get(guard);
                                if (pdc == null) {
                                    throw new MessageEncodeException("错误的终端任务配置--" + ts.getDataCodes());
                                }
                                tsbytes += pdc.getLength();
                            }
                            if (tsbytes <= 0) {
                                log.info("数据描述错误，请检查浙江规约数据集！");
                            } else {
                                int datamax = DataItemCoder.getDataMax(rtu);

                                int pointnum = datamax / tsbytes;
                                if (pointnum <= 0) {
                                    pointnum = 1;
                                }
                                int curnum = num;

                                int msgcount = 0;

                                while (curnum > 0) {
                                    int realp = 0;
                                    if (curnum > pointnum) realp = pointnum;
                                    else {
                                        realp = curnum;
                                    }

                                    MessageZjHead head = createHead(rtu);
                                    byte[] frame = new byte[len];
                                    frame[0] = (byte) taskno;
                                    frame[1] = ParseTool.IntToBcd(stime.get(1) % 100);
                                    frame[2] = ParseTool.IntToBcd(stime.get(2) + 1);
                                    frame[3] = ParseTool.IntToBcd(stime.get(5));
                                    frame[4] = ParseTool.IntToBcd(stime.get(11));
                                    frame[5] = ParseTool.IntToBcd(stime.get(12));
                                    frame[6] = (byte) realp;
                                    frame[7] = (byte) rate;

                                    MessageZj msg = new MessageZj();
                                    msg.data = ByteBuffer.wrap(frame);
                                    msg.head = head;
                                    rt.add(msg);

                                    msg.setCmdId((Long) cmdIds.get(iter));

                                    ++msgcount;
                                    int ti = getTimeInterval((byte) tsu);
                                    if (ti <= 1440) {
                                        stime.add(12, ti * tsn * realp * rate);
                                    } else {
                                        stime.add(2, realp * tsn * rate);
                                    }
                                    curnum -= pointnum;
                                }

                                setMsgcount(rt, msgcount);
                            }
                        }
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
        head.c_func = 2;
        head.rtua = rtu.getRtua();

        head.iseq = 0;

        return head;
    }

    private int getTimeInterval(byte type) {
        int rt = 0;
        switch (type) {
            case 2:
                rt = 1;
                break;
            case 3:
                rt = 60;
                break;
            case 4:
                rt = 1440;
                break;
            case 5:
                rt = 43200;
        }

        return rt;
    }

    private void setMsgcount(List msgs, int msgcount) {
        for (Iterator iter = msgs.iterator(); iter.hasNext();) {
            MessageZj msg = (MessageZj) iter.next();
            if (msg.getMsgCount() == 0) msg.setMsgCount(msgcount);
        }
    }
}