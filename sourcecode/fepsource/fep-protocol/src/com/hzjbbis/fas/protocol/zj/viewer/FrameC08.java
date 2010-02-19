package com.hzjbbis.fas.protocol.zj.viewer;

import com.hzjbbis.exception.MessageDecodeException;
import com.hzjbbis.fas.protocol.conf.ProtocolDataItemConfig;
import com.hzjbbis.fas.protocol.zj.parse.DataItemParser;
import com.hzjbbis.fas.protocol.zj.parse.ParseTool;

import java.util.List;

public class FrameC08 extends AbstractFrame {
    public static final String FUNC_NAME = "设置终端参数";

    public FrameC08() {
    }

    public FrameC08(byte[] frame) {
        super(frame);
    }

    public FrameC08(String data) {
        super(data);
    }

    public String getDescription() {
        if (this.frame != null) {
            StringBuffer sb = new StringBuffer();
            sb.append(super.getBase());
            sb.append("命令类型--").append("设置终端参数");
            sb.append("\n");
            if (this.direction > 0) descRtuReply(sb);
            else {
                descMastCmd(sb);
            }
            return sb.toString();
        }
        return null;
    }

    private void descRtuReply(StringBuffer buffer) {
        try {
            if (this.fexp > 0) {
                if (this.length > 1) parseErr(buffer);
                else buffer.append("异常应答--").append(errCode(this.frame[11]));
            } else parseErr(buffer);
        } catch (Exception e) {
        }
    }

    private void parseErr(StringBuffer buffer) {
        buffer.append("设置的测量点--");
        int point = this.frame[11] & 0xFF;
        buffer.append(point).append("\n");

        int index = 12;
        int tail = this.length + 11;

        while ((index < tail) && (2 < tail - index)) {
            buffer.append(ParseTool.BytesToHexC(this.frame, index, 2));
            buffer.append("设置结果:").append(errCode(this.frame[(index + 2)])).append("\n");
            index += 3;
        }
    }

    private void descMastCmd(StringBuffer buffer) {
        try {
            buffer.append("设置的测量点--");
            int point = this.frame[11] & 0xFF;
            buffer.append(point).append("    ");
            buffer.append("使用的权限等级--");
            buffer.append(((this.frame[12] & 0xFF) == 17) ? "高级" : "低级").append("    ");
            buffer.append("密码--");
            buffer.append(ParseTool.BytesToHexC(this.frame, 13, 3)).append("\n");
            buffer.append("设置的数据项---");
            int index = 16;
            int tail = this.length + 11;

            while ((index < tail) && (2 < tail - index)) {
                int datakey = ((this.frame[(index + 1)] & 0xFF) << 8) + (this.frame[index] & 0xFF);
                ProtocolDataItemConfig dic = DataConfigZj.getInstance().getDataConfig().getDataItemConfig(ParseTool.IntToHex(datakey));
                if (dic == null) {
                    ProtocolDataItemConfig di = DataConfigZjpb.getInstance().getDataConfig().getDataItemConfig(ParseTool.IntToHex(datakey));
                    if (di != null) dic = di;
                }
                if (dic != null) {
                    int loc = index + 2;
                    int itemlen = 0;

                    itemlen = parseBlockData(this.frame, loc, dic, point, buffer);
                    loc += itemlen;
                    if (ParseTool.isTask(datakey)) {
                        loc = tail;
                        break;
                    }

                    index = loc;
                } else {
                    buffer.append("\n");
                    buffer.append("不支持的数据:" + ParseTool.IntToHex(datakey));
                    break;
                }
            }
        } catch (Exception e) {
        }
    }

    private int parseBlockData(byte[] data, int loc, ProtocolDataItemConfig pdc, int point, StringBuffer buffer) {
        int rt = 0;
        try {
            List children = pdc.getChildItems();
            int index = loc;
            if ((children != null) && (children.size() > 0)) {
                for (int i = 0; i < children.size(); ++i) {
                    ProtocolDataItemConfig cpdc = (ProtocolDataItemConfig) children.get(i);
                    int dlen = parseBlockData(data, index, cpdc, point, buffer);
                    if (dlen <= 0) {
                        return -1;
                    }
                    index += dlen;
                    rt += dlen;
                }
            } else {
                int dlen = parseItem(data, loc, pdc, point, buffer);
                if (dlen <= 0) {
                    return -1;
                }
                rt += dlen;
            }
        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }
        return rt;
    }

    private int parseItem(byte[] data, int loc, ProtocolDataItemConfig pdc, int point, StringBuffer buffer) {
        int rt = 0;
        try {
            int datakey = pdc.getDataKey();
            int itemlen = 0;
            if ((33024 < datakey) && (33278 > datakey)) {
                int tasktype = data[loc] & 0xFF;
                if (tasktype == 1) {
                    if (16 < data.length - loc) {
                        itemlen = ParseTool.BCDToDecimal(data[(loc + 15)]) * 2 + 16;
                    } else {
                        buffer.append("错误数据长度，数据项：" + pdc.getCode() + " 期望数据长度：>16" + " 解析长度：" + (data.length - loc));
                        return -1;
                    }
                }
                if (tasktype == 2) {
                    if (21 < data.length - loc) {
                        itemlen = ParseTool.BCDToDecimal(data[(loc + 20)]) + 21;
                    } else {
                        buffer.append("错误数据长度，数据项：" + pdc.getCode() + " 期望数据长度：>21" + " 解析长度：" + (data.length - loc));
                        return -1;
                    }
                }
                if (tasktype == 4) if (7 < data.length - loc) {
                    itemlen = ParseTool.BCDToDecimal(data[(loc + 6)]) * 3 + 8;
                } else {
                    buffer.append("错误数据长度，数据项：" + pdc.getCode() + " 期望数据长度：>7" + " 解析长度：" + (data.length - loc));
                    return -1;
                }
            } else {
                itemlen = pdc.getLength();
            }
            if (itemlen <= data.length - loc) {
                Object di = DataItemParser.parsevalue(data, loc, itemlen, pdc.getFraction(), pdc.getParserno());
                buffer.append(pdc.getCode()).append("=");
                if (di != null) {
                    buffer.append(di.toString());
                }
                buffer.append("\n");
                rt = itemlen;
            } else if (data.length - loc != 0) {
                buffer.append("错误数据长度，数据项：" + pdc.getCode() + " 期望数据长度：" + itemlen + " 解析长度：" + (data.length - loc));
                return -1;
            }
        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }
        return rt;
    }
}