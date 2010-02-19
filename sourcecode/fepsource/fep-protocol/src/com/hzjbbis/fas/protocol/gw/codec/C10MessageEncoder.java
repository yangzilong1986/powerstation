package com.hzjbbis.fas.protocol.gw.codec;

import com.hzjbbis.exception.MessageEncodeException;
import com.hzjbbis.fas.model.FaalGWAFN10Request;
import com.hzjbbis.fas.model.FaalRequest;
import com.hzjbbis.fas.model.FaalRequestParam;
import com.hzjbbis.fas.model.FaalRequestRtuParam;
import com.hzjbbis.fas.protocol.data.DataItem;
import com.hzjbbis.fas.protocol.gw.parse.DataItemCoder;
import com.hzjbbis.fas.protocol.gw.parse.DataSwitch;
import com.hzjbbis.fas.protocol.meter.IMeterParser;
import com.hzjbbis.fas.protocol.meter.MeterParserFactory;
import com.hzjbbis.fas.protocol.zj.parse.ParseTool;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.gw.MessageGw;
import com.hzjbbis.fk.message.gw.MessageGwHead;
import com.hzjbbis.fk.model.BizRtu;
import com.hzjbbis.fk.model.MeasuredPoint;
import com.hzjbbis.fk.model.RtuManage;
import com.hzjbbis.util.HexDump;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class C10MessageEncoder extends AbstractMessageEncoder {
    private static Log log = LogFactory.getLog(C10MessageEncoder.class);

    public IMessage[] encode(Object obj) {
        FaalGWAFN10Request request;
        String sdata;
        String tp;
        String param;
        String portstr;
        String pw;
        String sDADT;
        List rt = new ArrayList();
        try {
            if (obj instanceof FaalRequest) {
                request = (FaalGWAFN10Request) obj;
                List rtuParams = request.getRtuParams();
                sdata = "";
                tp = "";
                param = "";
                portstr = "";
                pw = "";

                if ((request.getTpSendTime() != null) && (request.getTpTimeout() > 0)) {
                    tp = "00" + DataItemCoder.constructor(request.getTpSendTime(), "A16") + DataItemCoder.constructor(new StringBuilder().append("").append(request.getTpTimeout()).toString(), "HTB1");
                }
                param = DataItemCoder.constructor("" + request.getPort(), "HTB1");
                param = param + DataItemCoder.constructor(new StringBuilder().append("").append(request.getKzz()).toString(), "BS1");
                param = param + DataItemCoder.constructor(new StringBuilder().append("").append(request.getMsgTimeout()).toString(), "BS1");
                param = param + DataItemCoder.constructor(new StringBuilder().append("").append(request.getByteTimeout()).toString(), "HTB1");

                sDADT = DataItemCoder.getCodeFrom1To1(0, "10F001");
                for (FaalRequestRtuParam rp : rtuParams) {
                    int[] tn = rp.getTn();
                    BizRtu rtu = RtuManage.getInstance().getBizRtuInCache(rp.getRtuId());
                    if (rtu == null) {
                        throw new MessageEncodeException("终端信息未在缓存列表：" + ParseTool.IntToHex4(rtu.getRtua()));
                    }
                    if ((rtu.getHiAuthPassword() != null) && (rtu.getHiAuthPassword().length() == 32))
                        pw = DataSwitch.ReverseStringByByte(rtu.getHiAuthPassword());
                    else throw new MessageEncodeException("rtu HiAuthPassword is null");
                    String[] codes = new String[rp.getParams().size()];
                    for (int i = 0; i < rp.getParams().size(); ++i) {
                        FaalRequestParam pm = (FaalRequestParam) rp.getParams().get(i);
                        codes[i] = pm.getName();
                    }
                    for (i = 0; i < tn.length; ++i) {
                        if (request.getPort() < 0) {
                            portstr = getPortWithMpPara(rtu, "" + tn[i]);

                            param = DataItemCoder.constructor(portstr, "HTB1") + param.substring(2);
                        }
                        List meterFrames = createMeterFrame("" + tn[i], rtu, codes, request.getFixProto(), request.getFixAddre(), request.getBroadcastAddress(), request.getBroadcast());
                        for (int j = 0; j < meterFrames.size(); ++j) {
                            sdata = sDADT + param + DataItemCoder.constructor(new StringBuilder().append("").append(((String) meterFrames.get(j)).length() / 2).toString(), "HTB2") + ((String) meterFrames.get(j));
                            MessageGwHead head = new MessageGwHead();

                            head.rtua = rtu.getRtua();

                            MessageGw msg = new MessageGw();
                            msg.head = head;
                            msg.setAFN((byte) request.getType());
                            msg.data = HexDump.toByteBuffer(sdata + pw);
                            if (!(tp.equals(""))) msg.setAux(HexDump.toByteBuffer(tp), true);
                            msg.setCmdId(rp.getCmdId());
                            msg.setMsgCount(meterFrames.size() * tn.length);
                            rt.add(msg);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new MessageEncodeException(e);
        }
        if ((rt != null) && (rt.size() > 0)) {
            IMessage[] msgs = new IMessage[rt.size()];

            rt.toArray(msgs);
            return msgs;
        }

        return null;
    }

    private List<String> createMeterFrame(String tn, BizRtu rtu, String[] codes, String fixProto, String fixAddre, String broadcastAddress, boolean broadcast) {
        List meterFrame = new ArrayList();
        IMeterParser mparser = null;
        String pm = null;
        String maddr = null;
        if (fixProto == null) pm = getProtoWithMpPara(rtu, tn);
        else {
            pm = ParseTool.getMeterProtocol(fixProto);
        }
        if (fixAddre == null) maddr = getAddrWithMpPara(rtu, tn, broadcastAddress, broadcast);
        else {
            maddr = fixAddre;
        }

        mparser = MeterParserFactory.getMeterParser(pm);
        if (mparser == null) {
            throw new MessageEncodeException("不支持的表规约");
        }

        if ((pm.equalsIgnoreCase("20")) && (maddr.length() > 2)) {
            String xxa = maddr.substring(maddr.length() - 2);
            if (xxa.equalsIgnoreCase("AA")) {
                maddr = maddr.substring(0, 2);
            } else maddr = xxa;

        }

        DataItem dipara = new DataItem();
        dipara.addProperty("point", maddr);
        String[] dks = null;
        if (fixProto.equals("30")) {
            dks = mparser.getMeterCode(codes);
        } else {
            dks = mparser.convertDataKey(codes);
        }
        int msgcount = 0;
        for (int k = 0; (k < dks.length) && (dks[k] != null); ++k) {
            if (dks[k].length() <= 0) {
                break;
            }
            byte[] cmd = mparser.constructor(new String[]{dks[k]}, dipara);
            if (cmd == null) {
                StringBuffer se = new StringBuffer();
                for (int j = 0; j < codes.length; ++j) {
                    se.append(codes[j]);
                    se.append(" ");
                }
                throw new MessageEncodeException("不支持召测的表规约数据：" + se.toString() + "  RTU:" + ParseTool.IntToHex4(rtu.getRtua()));
            }

            String frame = HexDump.hexDumpCompact(cmd, 0, cmd.length);
            if (!(fixProto.equals("20"))) frame = "FEFEFEFE" + frame;
            meterFrame.add(frame);

            ++msgcount;
        }
        return meterFrame;
    }

    private String getProtoWithMpPara(BizRtu rtu, String tn) {
        String proto = null;

        MeasuredPoint mp = rtu.getMeasuredPoint(tn);
        if (mp == null) {
            throw new MessageEncodeException("指定测量点不存在！终端--" + ParseTool.IntToHex4(rtu.getRtua()) + "  测量点--" + tn);
        }

        if (mp.getAtrProtocol() == null) {
            log.error("表规约缺失，将使用默认规约类型------RTU:" + ParseTool.IntToHex4(rtu.getRtua()));
            proto = ParseTool.getMeterProtocol("20");
        } else {
            proto = ParseTool.getMeterProtocol(mp.getAtrProtocol());
        }

        return proto;
    }

    private String getPortWithMpPara(BizRtu rtu, String tn) {
        String port = null;
        MeasuredPoint mp = rtu.getMeasuredPoint(tn);
        if (mp == null) {
            throw new MessageEncodeException("指定测量点不存在！终端--" + ParseTool.IntToHex4(rtu.getRtua()) + "  测量点--" + tn);
        }
        port = mp.getAtrPort();
        return port;
    }

    private String getAddrWithMpPara(BizRtu rtu, String tn, String broadcastAddress, boolean broadcast) {
        MeasuredPoint mp;
        String maddr = null;
        if (broadcast) {
            if (broadcastAddress != null) {
                maddr = broadcastAddress;
            } else {
                mp = rtu.getMeasuredPoint(tn);
                if (mp == null) {
                    throw new MessageEncodeException("指定测量点不存在！终端--" + ParseTool.IntToHex4(rtu.getRtua()) + "  测量点--" + tn);
                }
                maddr = getBroadcastAddress(mp);
            }
        } else {
            mp = rtu.getMeasuredPoint(tn);
            if (mp == null) {
                throw new MessageEncodeException("指定测量点不存在！终端--" + ParseTool.IntToHex4(rtu.getRtua()) + "  测量点--" + tn);
            }
            if (mp.getAtrAddress() == null) maddr = getBroadcastAddress(mp);
            else {
                maddr = mp.getAtrAddress();
            }
        }
        return maddr;
    }

    private String getBroadcastAddress(MeasuredPoint mp) {
        String maddr = null;
        if (mp.getAtrProtocol() == null) {
            maddr = "FF";
        } else {
            if (mp.getAtrProtocol().equalsIgnoreCase("10")) {
                maddr = "999999999999";
            }
            if (mp.getAtrProtocol().equalsIgnoreCase("20")) {
                maddr = "FF";
            }
            if (mp.getAtrProtocol().equalsIgnoreCase("40")) {
                maddr = "FF";
            }
        }
        return maddr;
    }
}