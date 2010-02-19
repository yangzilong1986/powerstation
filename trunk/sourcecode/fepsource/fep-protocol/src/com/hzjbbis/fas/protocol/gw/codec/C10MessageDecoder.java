package com.hzjbbis.fas.protocol.gw.codec;

import com.hzjbbis.exception.MessageDecodeException;
import com.hzjbbis.fas.model.HostCommand;
import com.hzjbbis.fas.model.HostCommandResult;
import com.hzjbbis.fas.protocol.data.DataItem;
import com.hzjbbis.fas.protocol.gw.parse.DataItemParser;
import com.hzjbbis.fas.protocol.gw.parse.ParseTool;
import com.hzjbbis.fas.protocol.meter.BbMeterFrame;
import com.hzjbbis.fas.protocol.meter.IMeterParser;
import com.hzjbbis.fas.protocol.meter.MeterParserFactory;
import com.hzjbbis.fas.protocol.meter.ZjMeterFrame;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.gw.MessageGw;
import com.hzjbbis.fk.model.BizRtu;
import com.hzjbbis.fk.model.RtuManage;
import com.hzjbbis.fk.utils.HexDump;

import java.util.ArrayList;
import java.util.List;

public class C10MessageDecoder extends AbstractMessageDecoder {
    public Object decode(IMessage message) {
        HostCommand hc = new HostCommand();
        List value = new ArrayList();
        try {
            String data = ParseTool.getGwData(message);
            MessageGw msg = (MessageGw) message;
            if (msg.head.seq_tpv == 1) data = data.substring(0, data.length() - 12);
            if (msg.head.c_acd == 1) data = data.substring(0, data.length() - 4);
            data = data.substring(8);
            data = data.substring(2);
            int len = Integer.parseInt(DataItemParser.parseValue(data.substring(0, 4), "HTB2").getValue());
            data = data.substring(4);
            data = data.substring(0, len * 2);
            byte[] datas = HexDump.toByteBuffer(data).array();
            if ((datas != null) && (datas.length > 0)) {
                int rtua = ((MessageGw) message).head.rtua;
                BizRtu rtu = RtuManage.getInstance().getBizRtuInCache(((MessageGw) message).head.rtua);
                if (rtu == null) {
                    throw new MessageDecodeException("终端信息未在缓存列表：" + ParseTool.IntToHex4(rtua));
                }

                String pm = getMeterProtocol(datas, 0, datas.length);
                IMeterParser mparser = MeterParserFactory.getMeterParser(pm);
                if (mparser == null) {
                    throw new MessageDecodeException("不支持的表规约：" + pm);
                }
                Object[] dis = mparser.parser(datas, 0, datas.length);
                if ((dis != null) && (dis.length > 0)) {
                    for (int i = 0; i < dis.length; ++i) {
                        DataItem di = (DataItem) dis[i];
                        String key = (String) di.getProperty("datakey");
                        if (key == null) continue;
                        if (key.length() < 4) {
                            continue;
                        }
                        boolean called = true;
                        if (called) {
                            HostCommandResult hcr = new HostCommandResult();
                            hcr.setCode(key);
                            if (di.getProperty("value") == null) hcr.setValue(null);
                            else {
                                hcr.setValue(di.getProperty("value").toString());
                            }
                            hcr.setCommandId(hc.getId());
                            value.add(hcr);
                        }
                    }
                }
                hc.setStatus("1");
                hc.setResults(value);
            } else {
                hc.setStatus("16");
                hc.setResults(null);
            }
        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }
        return hc;
    }

    private String getMeterProtocol(byte[] data, int loc, int len) {
        String Protocol = "";
        BbMeterFrame bbFrame = new BbMeterFrame();
        bbFrame.parse(data, loc, len);
        if (bbFrame.getDatalen() > 0) {
            Protocol = "BBMeter";
        } else {
            ZjMeterFrame zjFrame = new ZjMeterFrame();
            zjFrame.parse(data, loc, len);
            if (zjFrame.getDatalen() > 0) {
                Protocol = "ZJMeter";
            }
        }
        return Protocol;
    }
}