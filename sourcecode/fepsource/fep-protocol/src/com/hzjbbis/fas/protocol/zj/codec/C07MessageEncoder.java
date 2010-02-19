package com.hzjbbis.fas.protocol.zj.codec;

import com.hzjbbis.exception.MessageEncodeException;
import com.hzjbbis.fas.model.FaalRealTimeWriteParamsRequest;
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
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class C07MessageEncoder extends AbstractMessageEncoder {
    private final Log log;

    public C07MessageEncoder() {
        this.log = LogFactory.getLog(C07MessageEncoder.class);
    }

    public IMessage[] encode(Object obj) {
        List rt = null;
        try {
            if (obj instanceof FaalRealTimeWriteParamsRequest) {
                FaalRealTimeWriteParamsRequest para = (FaalRealTimeWriteParamsRequest) obj;

                int point = Integer.parseInt(para.getTn());
                byte rights = 17;

                List paras = para.getParams();
                if ((paras == null) || (paras.size() == 0)) {
                    throw new MessageEncodeException("空配置，请指定设置参数");
                }

                List nparas = paras;

                int[] itemlen = new int[nparas.size()];
                int[] keysinpara = new int[nparas.size()];
                String[] valsinpara = new String[nparas.size()];

                byte[] rowdata = new byte[2048];
                byte[] rowdataHL = new byte[2048];
                byte[] rowdataHLi = new byte[2048];

                int loc = 0;
                Calendar time = para.getCmdTime();
                if (time == null) {
                    time = Calendar.getInstance();
                }
                int wt = para.getTimeout();
                rowdata[0] = (byte) point;
                rowdata[1] = rights;
                rowdata[5] = ParseTool.IntToBcd(time.get(1) % 100);
                rowdata[6] = ParseTool.IntToBcd(time.get(2) + 1);
                rowdata[7] = ParseTool.IntToBcd(time.get(5));
                rowdata[8] = ParseTool.IntToBcd(time.get(11));
                rowdata[9] = ParseTool.IntToBcd(time.get(12));
                rowdata[10] = ParseTool.IntToBcd(wt);

                rowdataHL[0] = (byte) point;
                rowdataHL[1] = rights;
                rowdataHL[5] = ParseTool.IntToBcd(time.get(1) % 100);
                rowdataHL[6] = ParseTool.IntToBcd(time.get(2) + 1);
                rowdataHL[7] = ParseTool.IntToBcd(time.get(5));
                rowdataHL[8] = ParseTool.IntToBcd(time.get(11));
                rowdataHL[9] = ParseTool.IntToBcd(time.get(12));
                rowdataHL[10] = ParseTool.IntToBcd(wt);

                rowdataHLi[0] = (byte) point;
                rowdataHLi[1] = rights;
                rowdataHLi[5] = ParseTool.IntToBcd(time.get(1) % 100);
                rowdataHLi[6] = ParseTool.IntToBcd(time.get(2) + 1);
                rowdataHLi[7] = ParseTool.IntToBcd(time.get(5));
                rowdataHLi[8] = ParseTool.IntToBcd(time.get(11));
                rowdataHLi[9] = ParseTool.IntToBcd(time.get(12));
                rowdataHLi[10] = ParseTool.IntToBcd(wt);

                loc = 11;
                int index = 0;
                for (Iterator iter = nparas.iterator(); iter.hasNext();) {
                    FaalRequestParam fp = (FaalRequestParam) iter.next();
                    ProtocolDataItemConfig pdc = this.dataConfig.getDataItemConfig(fp.getName());
                    if (pdc != null) {
                        int zi;
                        int si;
                        int k;
                        rowdata[loc] = (byte) (pdc.getDataKey() & 0xFF);
                        rowdata[(loc + 1)] = (byte) ((pdc.getDataKey() & 0xFF00) >>> 8);

                        rowdataHL[loc] = (byte) (pdc.getDataKey() & 0xFF);
                        rowdataHL[(loc + 1)] = (byte) ((pdc.getDataKey() & 0xFF00) >>> 8);

                        rowdataHLi[loc] = (byte) (pdc.getDataKey() & 0xFF);
                        rowdataHLi[(loc + 1)] = (byte) ((pdc.getDataKey() & 0xFF00) >>> 8);

                        loc += 2;
                        int dlen = DataItemCoder.coder(rowdata, loc, fp, pdc);
                        if (dlen <= 0) {
                            throw new MessageEncodeException(fp.getName(), "错误的参数:" + fp.getName() + "---" + fp.getValue());
                        }

                        if ((pdc.getDataKey() & 0xFFFF) == 32789) {
                            System.arraycopy(rowdata, loc, rowdataHLi, loc, dlen);
                            zi = 16;
                            si = loc + 15;
                            for (k = 0; (k < 16) && ((rowdata[si] & 0xFF) == 0); ++k) {
                                rowdataHL[(loc + k)] = 0;
                                rowdataHLi[si] = -86;
                                --si;
                                --zi;
                            }

                            if (zi > 0) System.arraycopy(rowdata, loc, rowdataHL, loc + 16 - zi, zi);
                        } else if ((pdc.getDataKey() & 0xFFFF) == 35074) {
                            System.arraycopy(rowdata, loc, rowdataHL, loc, dlen);
                            System.arraycopy(rowdata, loc, rowdataHLi, loc, dlen);
                            zi = 6;
                            si = loc + 5;
                            for (k = 0; (k < 6) && ((rowdata[si] & 0xFF) == 170); ++k) {
                                rowdataHLi[si] = 0;
                                --si;
                                --zi;
                            }

                        } else {
                            System.arraycopy(rowdata, loc, rowdataHL, loc, dlen);
                            System.arraycopy(rowdata, loc, rowdataHLi, loc, dlen);
                        }

                        itemlen[index] = dlen;
                        keysinpara[index] = pdc.getDataKey();
                        valsinpara[index] = fp.getValue();
                        ++index;
                        loc += dlen;
                    } else {
                        throw new MessageEncodeException(fp.getName(), "配置无法获取，数据项：" + fp.getName());
                    }
                }

                List rtuid = para.getRtuIds();
                List cmdIds = para.getCmdIds();
                rt = new ArrayList();
                for (int iter = 0; iter < rtuid.size(); ++iter) {
                    String id = (String) rtuid.get(iter);
                    BizRtu rtu = RtuManage.getInstance().getBizRtuInCache(id);
                    byte[] fdata = null;

                    if (rtu == null) {
                        this.log.info("终端信息缺失，终端ID--" + rtu.getRtuId());
                    } else {
                        if ((rtu.getManufacturer() != null) && (((rtu.getManufacturer().equalsIgnoreCase("0087")) || (rtu.getManufacturer().equalsIgnoreCase("0112")) || (rtu.getManufacturer().equalsIgnoreCase("0094")) || (rtu.getManufacturer().equalsIgnoreCase("0117")))))
                            fdata = rowdataHL;
                        else if ((rtu.getManufacturer() != null) && (((rtu.getManufacturer().equalsIgnoreCase("0061")) || (rtu.getManufacturer().equalsIgnoreCase("0098"))))) {
                            fdata = rowdataHLi;
                        } else {
                            fdata = rowdata;
                        }
                        String pwd = rtu.getHiAuthPassword();
                        if (pwd == null) {
                            this.log.info("终端密码缺失，终端ID--" + rtu.getRtuId());
                        } else {
                            int datamax = DataItemCoder.getDataMax(rtu);

                            int msgcount = 0;
                            int dnum = 0;
                            int pos = 0;
                            int curlen = 0;

                            for (int j = 0; j < itemlen.length; ++j) {
                                MessageZj msg;
                                if (curlen + 11 + 2 + itemlen[j] > datamax) {
                                    msg = createMessageZj(fdata, rtu, pos, curlen, cmdIds.get(iter));
                                    if (msg != null) {
                                        ++msgcount;
                                        rt.add(msg);
                                    }
                                    pos += curlen;
                                    dnum = 1;
                                    curlen = 2 + itemlen[j];
                                } else {
                                    ++dnum;
                                    curlen += 2 + itemlen[j];

                                    if ((keysinpara[j] > 33024) && (keysinpara[j] <= 33278)) {
                                        msg = createMessageZj(fdata, rtu, pos, curlen, cmdIds.get(iter));
                                        if (msg != null) {
                                            ++msgcount;
                                            rt.add(msg);
                                        }
                                        dnum = 0;
                                        pos += curlen;
                                        curlen = 0;
                                    }
                                }
                            }

                            if (dnum > 0) {
                                MessageZj msg = createMessageZj(fdata, rtu, pos, curlen, cmdIds.get(iter));
                                if (msg != null) {
                                    ++msgcount;
                                    rt.add(msg);
                                }
                            }

                            setMsgcount(rt, msgcount);
                        }
                    }
                }
            }
        } catch (MessageEncodeException e) {
            throw e;
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
        head.c_func = 7;

        head.rtua = rtu.getRtua();

        head.iseq = 0;

        return head;
    }

    private MessageZj createMessageZj(byte[] rowdata, BizRtu rtu, int pos, int dlen, Object cmdid) {
        MessageZjHead head = createHead(rtu);
        head.dlen = (short) (dlen + 11);

        byte[] frameA = new byte[head.dlen];
        System.arraycopy(rowdata, 0, frameA, 0, 11);
        System.arraycopy(rowdata, 11 + pos, frameA, 11, dlen);

        String pwd = rtu.getHiAuthPassword();
        ParseTool.HexsToBytesAA(frameA, 2, pwd, 3, -86);

        MessageZj msg = new MessageZj();
        msg.setCmdId((Long) cmdid);

        msg.data = ByteBuffer.wrap(frameA);
        msg.head = head;
        return msg;
    }

    private void setMsgcount(List msgs, int msgcount) {
        for (Iterator iter = msgs.iterator(); iter.hasNext();) {
            MessageZj msg = (MessageZj) iter.next();
            if (msg.getMsgCount() == 0) msg.setMsgCount(msgcount);
        }
    }
}