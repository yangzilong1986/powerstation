package com.hzjbbis.fas.protocol.gw.codec;

import com.hzjbbis.exception.MessageEncodeException;
import com.hzjbbis.fas.model.FaalGWAFN0ARequest;
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
import java.util.Iterator;
import java.util.List;

public class C0AMessageEncoder extends AbstractMessageEncoder {
    public IMessage[] encode(Object obj) {
        FaalGWAFN0ARequest request;
        String sdata;
        String tp;
        String param;
        List rt = new ArrayList();
        try {
            if (obj instanceof FaalRequest) {
                request = (FaalGWAFN0ARequest) obj;
                List rtuParams = request.getRtuParams();
                sdata = "";
                tp = "";
                param = "";

                if ((request.getTpSendTime() != null) && (request.getTpTimeout() > 0)) {
                    tp = "00" + DataItemCoder.constructor(request.getTpSendTime(), "A16") + DataItemCoder.constructor(new StringBuilder().append("").append(request.getTpTimeout()).toString(), "HTB1");
                }
                if ((request.getParam() != null) && (request.getParam().length >= 1)) {
                    param = DataItemCoder.constructor("" + request.getParam().length, "HTB1");
                    for (int i = 0; i < request.getParam().length; ++i) {
                        param = param + DataItemCoder.constructor(new StringBuilder().append("").append(request.getParam()[i]).toString(), "HTB1");
                    }
                }
                for (FaalRequestRtuParam rp : rtuParams) {
                    int[] tn = rp.getTn();
                    List params = rp.getParams();
                    String codes = "";
                    for (Iterator i$ = params.iterator(); i$.hasNext();) {
                        int i;
                        FaalRequestParam pm = (FaalRequestParam) i$.next();
                        ProtocolDataItemConfig pdc = this.dataConfig.getDataItemConfig(pm.getName());
                        if ((pm.getName().equals("04F038")) || (pm.getName().equals("04F039"))) {
                            if (!(param.equals(""))) {
                                param = DataItemCoder.constructor("" + request.getParam()[0], "HTB1");
                                param = param + DataItemCoder.constructor(new StringBuilder().append("").append(request.getParam().length).toString(), "HTB1");
                                for (i = 1; i < request.getParam().length; ++i)
                                    param = param + DataItemCoder.constructor(new StringBuilder().append("").append(request.getParam()[i]).toString(), "HTB1");
                            }
                        } else if ((pm.getName().equals("04F010")) && (!(param.equals("")))) {
                            param = DataItemCoder.constructor("" + request.getParam().length, "HTB2");
                            for (i = 0; i < request.getParam().length; ++i) {
                                param = param + DataItemCoder.constructor(new StringBuilder().append("").append(request.getParam()[i]).toString(), "HTB2");
                            }
                        }
                        if (codes.indexOf(pdc.getParentCode()) < 0) codes = codes + "," + pdc.getParentCode();
                    }
                    if (codes.startsWith(",")) codes = codes.substring(1);
                    String[] codeList = codes.split(",");
                    String[] sDADTList = DataItemCoder.getCodeFromNToN(tn, codeList);
                    for (int i = 0; i < sDADTList.length; ++i)
                        sdata = sdata + sDADTList[i] + param;
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