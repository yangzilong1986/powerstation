package com.hzjbbis.fas.protocol.gw.codec;

import com.hzjbbis.exception.MessageEncodeException;
import com.hzjbbis.fas.model.FaalGWAFN0DRequest;
import com.hzjbbis.fas.model.FaalRequest;
import com.hzjbbis.fas.model.FaalRequestParam;
import com.hzjbbis.fas.model.FaalRequestRtuParam;
import com.hzjbbis.fas.protocol.conf.ProtocolDataItemConfig;
import com.hzjbbis.fas.protocol.gw.parse.DataItemCoder;
import com.hzjbbis.fas.protocol.gw.parse.DataItemParser;
import com.hzjbbis.fas.protocol.zj.parse.ParseTool;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.gw.MessageGw;
import com.hzjbbis.fk.message.gw.MessageGwHead;
import com.hzjbbis.fk.model.BizRtu;
import com.hzjbbis.fk.model.RtuManage;
import com.hzjbbis.util.HexDump;

import java.util.ArrayList;
import java.util.List;

public class C0DMessageEncoder extends AbstractMessageEncoder {
    public IMessage[] encode(Object obj) {
        FaalGWAFN0DRequest request;
        String sdata;
        String tp;
        String date;
        List rt = new ArrayList();
        try {
            if (obj instanceof FaalRequest) {
                request = (FaalGWAFN0DRequest) obj;
                List rtuParams = request.getRtuParams();
                sdata = "";
                tp = "";
                date = "";

                if ((request.getTpSendTime() != null) && (request.getTpTimeout() > 0)) {
                    tp = "00" + DataItemCoder.constructor(request.getTpSendTime(), "A16") + DataItemCoder.constructor(new StringBuilder().append("").append(request.getTpTimeout()).toString(), "HTB1");
                }
                if ((request.getInterval() > 0) && (request.getCount() > 1)) {
                    String interval = "";
                    switch (request.getInterval()) {
                        case 1:
                            interval = "FF";
                            break;
                        case 5:
                            interval = "FE";
                            break;
                        case 15:
                            interval = "01";
                            break;
                        case 30:
                            interval = "02";
                            break;
                        case 60:
                            interval = "03";
                            break;
                        default:
                            interval = "00";
                    }
                    date = DataItemCoder.constructor(request.getStartTime(), "A15") + interval + DataItemCoder.constructor(new StringBuilder().append("").append(request.getCount()).toString(), "HTB1");
                } else if (request.getStartTime().trim().length() == 10) {
                    date = DataItemCoder.constructor(request.getStartTime(), "A20");
                } else {
                    date = DataItemCoder.constructor(request.getStartTime(), "A21");
                }
                for (FaalRequestRtuParam rp : rtuParams) {
                    int[] tn = rp.getTn();
                    List params = rp.getParams();
                    String codes = "";
                    for (FaalRequestParam pm : params) {
                        ProtocolDataItemConfig pdc = this.dataConfig.getDataItemConfig(pm.getName());
                        if (pdc == null) {
                            if ((!(pm.getName().substring(0, 2).equals("0D"))) && (pm.getName().length() == 10))
                                pm.setName(pm.getName().substring(0, 8) + "XX");
                            pdc = this.dataConfig.getDataItemConfig(pm.getName());
                            if (pdc == null) throw new MessageEncodeException("can not find cmd:" + pm.getName());
                        }
                        if (codes.indexOf(pdc.getParentCode()) < 0) codes = codes + "," + pdc.getParentCode();
                    }
                    if (codes.startsWith(",")) codes = codes.substring(1);
                    String[] codeList = codes.split(",");
                    String[] sDADTList = DataItemCoder.getCodeFromNToN(tn, codeList);
                    for (int i = 0; i < sDADTList.length; ++i) {
                        int[] mps = DataItemParser.measuredPointParser(sDADTList[i].substring(0, 4));
                        codeList = DataItemParser.dataCodeParser(sDADTList[i].substring(4, 8), "0D");
                        sdata = sdata + sDADTList[i];
                        for (int j = 0; j < mps.length; ++j) {
                            for (int k = 0; k < codeList.length; ++k)
                                sdata = sdata + date;
                        }
                    }
                    BizRtu rtu = RtuManage.getInstance().getBizRtuInCache(rp.getRtuId());
                    if (rtu == null) {
                        throw new MessageEncodeException("终端信息未在缓存列表：" + ParseTool.IntToHex4(rtu.getRtua()));
                    }
                    MessageGwHead head = new MessageGwHead();

                    head.rtua = rtu.getRtua();

                    MessageGw msg = new MessageGw();
                    msg.head = head;
                    msg.setAFN((byte) request.getType());
                    msg.data = HexDump.toByteBuffer(sdata);
                    if (!(tp.equals(""))) msg.setAux(HexDump.toByteBuffer(tp), true);
                    msg.setCmdId(rp.getCmdId());
                    msg.setMsgCount(1);
                    rt.add(msg);
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
}