package com.hzjbbis.fas.protocol.gw.codec;

import com.hzjbbis.exception.MessageEncodeException;
import com.hzjbbis.fas.model.FaalGWNoParamRequest;
import com.hzjbbis.fas.model.FaalRequest;
import com.hzjbbis.fas.model.FaalRequestParam;
import com.hzjbbis.fas.model.FaalRequestRtuParam;
import com.hzjbbis.fas.protocol.conf.ProtocolDataItemConfig;
import com.hzjbbis.fas.protocol.gw.parse.DataItemCoder;
import com.hzjbbis.fas.protocol.zj.parse.ParseTool;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.gw.MessageGw;
import com.hzjbbis.fk.message.gw.MessageGwHead;
import com.hzjbbis.fk.model.BizRtu;
import com.hzjbbis.fk.model.RtuManage;
import com.hzjbbis.util.HexDump;

import java.util.ArrayList;
import java.util.List;

public class C0CMessageEncoder extends AbstractMessageEncoder {
    public IMessage[] encode(Object obj) {
        FaalGWNoParamRequest request;
        String sdata;
        String tp;
        List rt = new ArrayList();
        try {
            if (obj instanceof FaalRequest) {
                request = (FaalGWNoParamRequest) obj;
                List rtuParams = request.getRtuParams();
                sdata = "";
                tp = "";

                if ((request.getTpSendTime() != null) && (request.getTpTimeout() > 0)) {
                    tp = "00" + DataItemCoder.constructor(request.getTpSendTime(), "A16") + DataItemCoder.constructor(new StringBuilder().append("").append(request.getTpTimeout()).toString(), "HTB1");
                }
                for (FaalRequestRtuParam rp : rtuParams) {
                    int[] tn = rp.getTn();
                    List params = rp.getParams();
                    String codes = "";
                    for (FaalRequestParam pm : params) {
                        ProtocolDataItemConfig pdc = this.dataConfig.getDataItemConfig(pm.getName());
                        if (pdc == null) {
                            if ((!(pm.getName().substring(0, 2).equals("0C"))) && (pm.getName().length() == 10))
                                pm.setName(pm.getName().substring(0, 8) + "XX");
                            pdc = this.dataConfig.getDataItemConfig(pm.getName());
                            if (pdc == null) throw new MessageEncodeException("can not find cmd:" + pm.getName());
                        }
                        if (codes.indexOf(pdc.getParentCode()) < 0) codes = codes + "," + pdc.getParentCode();
                    }
                    if (codes.startsWith(",")) codes = codes.substring(1);
                    String[] codeList = codes.split(",");
                    String[] sDADTList = DataItemCoder.getCodeFromNToN(tn, codeList);
                    for (int i = 0; i < sDADTList.length; ++i)
                        sdata = sdata + sDADTList[i];
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