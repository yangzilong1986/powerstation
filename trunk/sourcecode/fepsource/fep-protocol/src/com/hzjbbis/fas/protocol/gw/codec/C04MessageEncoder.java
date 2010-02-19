package com.hzjbbis.fas.protocol.gw.codec;

import com.hzjbbis.exception.MessageEncodeException;
import com.hzjbbis.fas.model.FaalGWNoParamRequest;
import com.hzjbbis.fas.model.FaalRequest;
import com.hzjbbis.fas.model.FaalRequestParam;
import com.hzjbbis.fas.model.FaalRequestRtuParam;
import com.hzjbbis.fas.protocol.conf.ProtocolDataItemConfig;
import com.hzjbbis.fas.protocol.gw.parse.DataItemCoder;
import com.hzjbbis.fas.protocol.gw.parse.DataSwitch;
import com.hzjbbis.fas.protocol.zj.parse.ParseTool;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.gw.MessageGw;
import com.hzjbbis.fk.message.gw.MessageGwHead;
import com.hzjbbis.fk.model.BizRtu;
import com.hzjbbis.fk.model.RtuManage;
import com.hzjbbis.util.HexDump;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class C04MessageEncoder extends AbstractMessageEncoder {
    private static Log log = LogFactory.getLog(C04MessageEncoder.class);

    public IMessage[] encode(Object obj) {
        FaalGWNoParamRequest request;
        String sDADT;
        String sValue;
        String sdata;
        String tp;
        String pw;
        List rt = new ArrayList();
        try {
            if (obj instanceof FaalRequest) {
                request = (FaalGWNoParamRequest) obj;
                List rtuParams = request.getRtuParams();
                sDADT = "";
                sValue = "";
                sdata = "";
                tp = "";
                pw = "";

                if ((request.getTpSendTime() != null) && (request.getTpTimeout() > 0)) {
                    tp = "00" + DataItemCoder.constructor(request.getTpSendTime(), "A16") + DataItemCoder.constructor(new StringBuilder().append("").append(request.getTpTimeout()).toString(), "HTB1");
                }
                for (FaalRequestRtuParam rp : rtuParams) {
                    int[] tn = rp.getTn();
                    List params = rp.getParams();
                    for (int i = 0; i < tn.length; ++i) {
                        for (FaalRequestParam pm : params) {
                            sDADT = DataItemCoder.getCodeFrom1To1(tn[i], pm.getName());
                            ProtocolDataItemConfig pdc = this.dataConfig.getDataItemConfig(pm.getName());
                            sValue = DataItemCoder.coder(pm.getValue(), pdc.getFormat());
                            sdata = sdata + sDADT + sValue;
                        }
                    }
                    BizRtu rtu = RtuManage.getInstance().getBizRtuInCache(rp.getRtuId());
                    if (rtu == null) {
                        throw new MessageEncodeException("终端信息未在缓存列表：" + ParseTool.IntToHex4(rtu.getRtua()));
                    }
                    if ((rtu.getHiAuthPassword() != null) && (rtu.getHiAuthPassword().length() == 32))
                        pw = DataSwitch.ReverseStringByByte(rtu.getHiAuthPassword());
                    else throw new MessageEncodeException("rtu HiAuthPassword is null");
                    MessageGwHead head = new MessageGwHead();

                    head.rtua = rtu.getRtua();

                    MessageGw msg = new MessageGw();
                    msg.head = head;
                    msg.setAFN((byte) request.getType());
                    msg.data = HexDump.toByteBuffer(sdata + pw);
                    if (!(tp.equals(""))) msg.setAux(HexDump.toByteBuffer(tp), true);
                    msg.setCmdId(rp.getCmdId());
                    msg.setMsgCount(1);
                    rt.add(msg);
                }
            }
        } catch (MessageEncodeException e) {
            throw e;
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