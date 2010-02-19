package com.hzjbbis.fas.protocol.zj.codec;

import com.hzjbbis.exception.MessageDecodeException;
import com.hzjbbis.fas.model.HostCommand;
import com.hzjbbis.fas.model.HostCommandResult;
import com.hzjbbis.fas.protocol.conf.ProtocolDataItemConfig;
import com.hzjbbis.fas.protocol.zj.ErrorCode;
import com.hzjbbis.fas.protocol.zj.parse.DataItemParser;
import com.hzjbbis.fas.protocol.zj.parse.ParseTool;
import com.hzjbbis.fk.message.IMessage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class C04MessageDecoder extends AbstractMessageDecoder {
    public Object decode(IMessage message) {
        List datas = null;
        HostCommand hc = new HostCommand();
        try {
            if (ParseTool.getOrientation(message) == 1) {
                byte[] data;
                int rtype = ParseTool.getErrCode(message);

                if (0 == rtype) {
                    data = ParseTool.getData(message);
                    if ((data != null) && (data.length > 17)) {
                        datas = new ArrayList();

                        hc.setStatus("1");
                        int point = ParseTool.BCDToDecimal(data[0]);
                        Calendar optime = ParseTool.getTime(data, 1);
                        Object comm = DataItemParser.parsevalue(data, 6, 5, 0, 14);

                        int loc = 15;
                        while (loc < data.length) {
                            int datakey = ((data[(loc + 1)] & 0xFF) << 8) + (data[loc] & 0xFF);
                            loc += 2;
                            ProtocolDataItemConfig dic = getDataItemConfig(datakey);
                            if (dic != null) {
                                int itemlen = 0;
                                if ((33024 < datakey) && (33278 > datakey)) {
                                    int tasktype = data[loc] & 0xFF;
                                    if (tasktype == 1) {
                                        if (16 < data.length - loc) itemlen = (data[(loc + 15)] & 0xFF) * 2 + 16;
                                        else {
                                            throw new MessageDecodeException("帧数据太少");
                                        }
                                    }
                                    if (tasktype == 2) {
                                        if (21 < data.length - loc) itemlen = (data[(loc + 20)] & 0xFF) + 21;
                                        else {
                                            throw new MessageDecodeException("帧数据太少");
                                        }
                                    }
                                    if (tasktype == 4)
                                        if (7 < data.length - loc) itemlen = (data[(loc + 6)] & 0xFF) * 3 + 8;
                                        else throw new MessageDecodeException("帧数据太少");
                                } else {
                                    itemlen = dic.getLength();
                                }
                                if (itemlen <= data.length - loc) {
                                    Object di = DataItemParser.parsevalue(data, loc, itemlen, dic.getFraction(), dic.getParserno());

                                    HostCommandResult hcr = new HostCommandResult();
                                    hcr.setChannel((String) comm);
                                    hcr.setCode(dic.getCode());
                                    hcr.setCommandId(new Long(0L));
                                    hcr.setProgramTime(optime.getTime());
                                    hcr.setTn(String.valueOf(point));
                                    if (di != null) {
                                        hcr.setValue(di.toString());
                                    }
                                    datas.add(hcr);

                                    loc += itemlen;
                                } else {
                                    throw new MessageDecodeException("帧数据太少");
                                }
                            } else {
                                throw new MessageDecodeException("未配置的数据项");
                            }
                        }
                    } else {
                        if (data.length > 0) {
                            throw new MessageDecodeException("帧数据太少");
                        }
                        datas = null;
                    }
                } else {
                    data = ParseTool.getData(message);
                    if ((data != null) && (data.length > 0)) hc.setStatus(ErrorCode.toHostCommandStatus(data[0]));
                    else {
                        hc.setStatus("2");
                    }
                }
            }
        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }
        hc.setResults(datas);
        return hc;
    }

    private ProtocolDataItemConfig getDataItemConfig(int datakey) {
        return this.dataConfig.getDataItemConfig(ParseTool.IntToHex(datakey));
    }
}