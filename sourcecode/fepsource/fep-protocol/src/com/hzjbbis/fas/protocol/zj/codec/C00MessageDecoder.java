package com.hzjbbis.fas.protocol.zj.codec;

import com.hzjbbis.exception.MessageDecodeException;
import com.hzjbbis.fas.model.HostCommand;
import com.hzjbbis.fas.model.HostCommandResult;
import com.hzjbbis.fas.protocol.data.DataItem;
import com.hzjbbis.fas.protocol.meter.BbMeterFrame;
import com.hzjbbis.fas.protocol.meter.IMeterParser;
import com.hzjbbis.fas.protocol.meter.MeterParserFactory;
import com.hzjbbis.fas.protocol.meter.ZjMeterFrame;
import com.hzjbbis.fas.protocol.zj.parse.ParseTool;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.zj.MessageZj;
import com.hzjbbis.fk.model.BizRtu;
import com.hzjbbis.fk.model.RtuManage;

import java.util.ArrayList;
import java.util.List;

public class C00MessageDecoder extends AbstractMessageDecoder {
    public Object decode(IMessage message) {
        HostCommand hc = new HostCommand();
        List value = new ArrayList();
        try {
            if (ParseTool.getOrientation(message) == 1) {
                int rtype = ParseTool.getErrCode(message);
                if (0 == rtype) {
                    byte[] data = ParseTool.getData(message);
                    if ((data != null) && (data.length > 1)) {
                        int rtua = ((MessageZj) message).head.rtua;
                        BizRtu rtu = RtuManage.getInstance().getBizRtuInCache(rtua);
                        if (rtu == null) {
                            throw new MessageDecodeException("终端信息未在缓存列表：" + ParseTool.IntToHex4(rtua));
                        }

                        String pm = getMeterProtocol(data, 1, data.length - 1);
                        IMeterParser mparser = MeterParserFactory.getMeterParser(pm);
                        if (mparser == null) {
                            throw new MessageDecodeException("不支持的表规约：" + pm);
                        }
                        Object[] dis = mparser.parser(data, 1, data.length - 1);
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
                } else {
                    hc.setStatus("2");
                    hc.setResults(null);
                }
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