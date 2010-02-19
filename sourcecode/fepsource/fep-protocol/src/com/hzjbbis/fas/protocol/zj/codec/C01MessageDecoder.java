package com.hzjbbis.fas.protocol.zj.codec;

import com.hzjbbis.exception.MessageDecodeException;
import com.hzjbbis.fas.model.HostCommand;
import com.hzjbbis.fas.model.HostCommandResult;
import com.hzjbbis.fas.protocol.conf.ProtocolDataItemConfig;
import com.hzjbbis.fas.protocol.zj.ErrorCode;
import com.hzjbbis.fas.protocol.zj.parse.DataItemParser;
import com.hzjbbis.fas.protocol.zj.parse.ParseTool;
import com.hzjbbis.fk.message.IMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class C01MessageDecoder extends AbstractMessageDecoder {
    private static Log log = LogFactory.getLog(C01MessageDecoder.class);

    public Object decode(IMessage message) {
        HostCommand hc = new HostCommand();
        List value = new ArrayList();
        try {
            if (ParseTool.getOrientation(message) == 1) {
                byte[] data;
                int rtype = ParseTool.getErrCode(message);

                Long cmdid = new Long(0L);

                if (rtype == 0) {
                    hc.setStatus("1");

                    data = ParseTool.getData(message);
                    if ((data != null) && (data.length > 10)) {
                        byte[] points = new byte[64];
                        byte pn = 0;
                        byte pnum = 0;
                        for (int i = 0; i < 8; ++i) {
                            int flag = 1;
                            int tnm = data[i] & 0xFF;
                            for (int j = 0; j < 8; ++j) {
                                if ((tnm & flag << j) > 0) {
                                    points[pnum] = pn;
                                    pnum = (byte) (pnum + 1);
                                }
                                pn = (byte) (pn + 1);
                            }
                        }
                        if (pnum > 0) {
                            int index = 8;
                            while (true) {
                                if (index >= data.length) break label348;
                                if (2 >= data.length - index) break;
                                int datakey = ((data[(index + 1)] & 0xFF) << 8) + (data[index] & 0xFF);
                                ProtocolDataItemConfig dic = getDataItemConfig(datakey);
                                if (dic != null) {
                                    int loc = index + 2;
                                    int itemlen = 0;

                                    for (int j = 0; j < pnum; ++j) {
                                        itemlen = parseBlockData(data, loc, dic, points[j], cmdid, value);
                                        loc += itemlen;
                                        if (ParseTool.isTask(datakey)) {
                                            loc = data.length;
                                            break;
                                        }
                                    }
                                    index = loc;
                                } else {
                                    log.info("不支持的数据:" + ParseTool.IntToHex(datakey));
                                    break label348:
                                }
                            }

                            label348:
                            throw new MessageDecodeException("帧数据太少");
                        } else {
                            throw new MessageDecodeException("帧内容错误，未指定测量点");
                        }
                    } else {
                        hc.setStatus("2");
                    }
                } else {
                    data = ParseTool.getData(message);
                    if ((data != null) && (data.length > 0)) {
                        if (data.length == 1) hc.setStatus(ErrorCode.toHostCommandStatus(data[0]));
                        else if (data.length == 9) hc.setStatus(ErrorCode.toHostCommandStatus(data[8]));
                        else hc.setStatus("2");
                    } else {
                        hc.setStatus("2");
                    }
                }
            }

        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }
        hc.setResults(value);
        return hc;
    }

    private int parseBlockData(byte[] data, int loc, ProtocolDataItemConfig pdc, byte point, Long cmdid, List<HostCommandResult> result) {
        int rt = 0;
        try {
            List children = pdc.getChildItems();
            int index = loc;
            if ((children != null) && (children.size() > 0)) {
                for (int i = 0; i < children.size(); ++i) {
                    ProtocolDataItemConfig cpdc = (ProtocolDataItemConfig) children.get(i);
                    int dlen = parseBlockData(data, index, cpdc, point, cmdid, result);
                    index += dlen;
                    rt += dlen;
                }
            } else {
                int dlen = parseItem(data, loc, pdc, point, cmdid, result);
                rt += dlen;
            }
        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }
        return rt;
    }

    private int parseItem(byte[] data, int loc, ProtocolDataItemConfig pdc, byte point, Long cmdid, List<HostCommandResult> result) {
        int rt = 0;
        try {
            int datakey = pdc.getDataKey();
            int itemlen = 0;
            if ((33024 < datakey) && (33278 > datakey)) {
                int tasktype = data[loc] & 0xFF;
                if (tasktype == 1) {
                    if (16 < data.length - loc) itemlen = ParseTool.BCDToDecimal(data[(loc + 15)]) * 2 + 16;
                    else {
                        throw new MessageDecodeException("错误数据长度，数据项：" + pdc.getCode() + " 期望数据长度：>16" + " 解析长度：" + (data.length - loc));
                    }
                }

                if (tasktype == 2) {
                    if (21 < data.length - loc) itemlen = ParseTool.BCDToDecimal(data[(loc + 20)]) + 21;
                    else {
                        throw new MessageDecodeException("错误数据长度，数据项：" + pdc.getCode() + " 期望数据长度：>21" + " 解析长度：" + (data.length - loc));
                    }
                }

                if (tasktype == 4) {
                    if (7 < data.length - loc) itemlen = ParseTool.BCDToDecimal(data[(loc + 6)]) * 3 + 8;
                    else
                        throw new MessageDecodeException("错误数据长度，数据项：" + pdc.getCode() + " 期望数据长度：>7" + " 解析长度：" + (data.length - loc));
                }
            } else {
                itemlen = pdc.getLength();
            }
            if (itemlen <= data.length - loc) {
                Object di = DataItemParser.parsevalue(data, loc, itemlen, pdc.getFraction(), pdc.getParserno());
                HostCommandResult hcr = new HostCommandResult();
                hcr.setCode(pdc.getCode());
                if (di != null) {
                    hcr.setValue(di.toString());
                }
                hcr.setCommandId(cmdid);
                hcr.setTn(point + "");
                result.add(hcr);
                rt = itemlen;
            } else if (data.length - loc != 0) {
                throw new MessageDecodeException("错误数据长度，数据项：" + pdc.getCode() + " 期望数据长度：" + itemlen + " 解析长度：" + (data.length - loc));
            }
        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }
        return rt;
    }

    private ProtocolDataItemConfig getDataItemConfig(int datakey) {
        return this.dataConfig.getDataItemConfig(ParseTool.IntToHex(datakey));
    }
}