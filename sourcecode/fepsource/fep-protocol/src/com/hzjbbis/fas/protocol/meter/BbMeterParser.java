package com.hzjbbis.fas.protocol.meter;

import com.hzjbbis.fas.protocol.data.DataItem;
import com.hzjbbis.fas.protocol.meter.conf.MeterProtocolDataItem;
import com.hzjbbis.fas.protocol.meter.conf.MeterProtocolDataSet;
import com.hzjbbis.fas.protocol.zj.parse.DataItemParser;
import com.hzjbbis.fas.protocol.zj.parse.ParseTool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class BbMeterParser implements IMeterParser {
    private final Log log = LogFactory.getLog(BbMeterParser.class);
    private MeterProtocolDataSet dataset;

    public BbMeterParser() {
        try {
            this.dataset = MeterProtocolFactory.createMeterProtocolDataSet("BBMeter");
        } catch (Exception e) {
            this.log.error("部颁表规约初始化失败");
        }
    }

    public String[] convertDataKey(String[] datakey) {
        String[] rt = null;
        try {
            if ((datakey != null) && (datakey.length > 0)) {
                rt = new String[datakey.length];
                for (int i = 0; i < datakey.length; ++i)
                    if ((datakey[i] != null) && (datakey[i].equalsIgnoreCase("8902"))) addDataKey(rt, "C034");
                    else addDataKey(rt, datakey[i]);
            }
        } catch (Exception e) {
            this.log.error("部颁表数据标识转换", e);
        }
        return rt;
    }

    private void addDataKey(String[] datakeys, String dkey) {
        for (int i = 0; i < datakeys.length; ++i) {
            if ((datakeys[i] == null) || (datakeys[i].equals(""))) {
                if (dkey.substring(0, 1).equalsIgnoreCase("F")) return;
                if (dkey.substring(1, 2).equalsIgnoreCase("F")) {
                    return;
                }

                datakeys[i] = dkey;
                return;
            }
            String char1 = datakeys[i].substring(0, 1);
            String char2 = datakeys[i].substring(1, 2);
            String char3 = datakeys[i].substring(2, 3);
            if ((!(char1.equalsIgnoreCase(dkey.substring(0, 1)))) || (!(char2.equalsIgnoreCase(dkey.substring(1, 2)))) || (!(char3.equalsIgnoreCase(dkey.substring(2, 3))))) {
                continue;
            }

            StringBuffer sb = new StringBuffer();
            sb.append(char1);
            sb.append(char2);

            sb.append(char3);
            sb.append("F");

            datakeys[i] = sb.toString();
            sb = null;
            return;
        }
    }

    public byte[] constructor(String[] datakey, DataItem para) {
        byte[] frame = null;
        try {
            if ((datakey != null) && (datakey.length > 0) && (para != null) && (para.getProperty("point") != null)) {
                String maddr;
                String dkey;
                if (datakey[0].length() == 4) {
                    frame = new byte[14];
                    maddr = (String) para.getProperty("point");
                    dkey = datakey[0];
                    frame[0] = 104;
                    ParseTool.HexsToBytesAA(frame, 1, maddr, 6, -86);
                    frame[7] = 104;
                    frame[8] = 1;
                    frame[9] = 2;
                    ParseTool.HexsToBytes(frame, 10, dkey);
                    frame[10] = (byte) (frame[10] + 51);
                    frame[11] = (byte) (frame[11] + 51);
                    frame[12] = ParseTool.calculateCS(frame, 0, 12);
                    frame[13] = 22;
                } else {
                    frame = new byte[16];
                    maddr = (String) para.getProperty("point");
                    dkey = datakey[0];
                    frame[0] = 104;
                    ParseTool.HexsToBytesAA(frame, 1, maddr, 6, -86);
                    frame[7] = 104;
                    frame[8] = 17;
                    frame[9] = 4;
                    ParseTool.HexsToBytes(frame, 10, dkey);
                    frame[10] = (byte) (frame[10] + 51);
                    frame[11] = (byte) (frame[11] + 51);
                    frame[12] = (byte) (frame[12] + 51);
                    frame[13] = (byte) (frame[13] + 51);
                    frame[14] = ParseTool.calculateCS(frame, 0, 14);
                    frame[15] = 22;
                }
            }
        } catch (Exception e) {
        }
        return frame;
    }

    public Object[] parser(byte[] data, int loc, int len) {
        List result = null;
        try {
            BbMeterFrame frame = new BbMeterFrame();
            frame.parse(data, loc, len);
            if (frame.getDatalen() > 0) {
                byte[] framedata;
                int pos;
                String datakey;
                MeterProtocolDataItem item;
                result = new ArrayList();

                int datalen = frame.getDatalen();
                String meteraddr = frame.getMeteraddr();
                DataItem ma = new DataItem();
                ma.addProperty("value", meteraddr);
                ma.addProperty("datakey", "8902");
                result.add(ma);

                int ctrl = frame.getCtrl();
                if ((ctrl & 0x10) == 16) {
                    framedata = frame.getData();
                    pos = frame.getPos();
                    switch (ctrl & 0xF) {
                        case 1:
                            datakey = ParseTool.BytesToHexC(framedata, pos, 4);
                            datakey = this.dataset.getConvertCode(datakey);
                            item = this.dataset.getDataItem(datakey);
                            pos += 4;
                            if (item != null) {
                                parseValues(framedata, pos, item, result);
                            }

                    }

                } else if ((ctrl & 0x40) <= 0) {
                    framedata = frame.getData();

                    pos = frame.getPos();
                    switch (ctrl & 0x1F) {
                        case 1:
                            datakey = ParseTool.BytesToHexC(framedata, pos, 2);
                            item = this.dataset.getDataItem(datakey);
                            pos += 2;
                            if (item != null) {
                                parseValues(framedata, pos, item, result);
                            }
                    }
                }
            }

        } catch (Exception e) {
            this.log.error("部颁表规约", e);
        }
        if (result != null) {
            return result.toArray();
        }
        return null;
    }

    public String[] getMeterCode(String[] codes) {
        String[] rtCodes = null;
        if ((codes != null) && (codes.length > 0)) {
            rtCodes = new String[codes.length];
            for (int i = 0; i < codes.length; ++i) {
                MeterProtocolDataItem item = this.dataset.getDataItem(codes[i]);
                rtCodes[i] = item.getZjcode();
            }
        }

        return rtCodes;
    }

    private int parseValues(byte[] data, int pos, MeterProtocolDataItem item, List results) {
        int rt = 0;
        try {
            int loc = pos;
            if ((item.getChildarray() != null) && (item.getChildarray().size() > 0)) {
                List children = item.getChildarray();
                for (int i = 0; i < children.size(); ++i) {
                    if ((data[loc] & 0xFF) == 170) {
                        ++rt;
                        break;
                    }
                    if (loc >= data.length) {
                        break;
                    }
                    int vlen = parseValues(data, loc, (MeterProtocolDataItem) children.get(i), results);
                    if (vlen <= 0) {
                        rt = 0;
                        break;
                    }
                    loc += vlen;
                    rt += vlen;
                }
            } else {
                DataItem di = new DataItem();
                di.addProperty("datakey", item.getCode());
                Object val = parseItem(data, pos, item);
                di.addProperty("value", val);
                results.add(di);
                rt = item.getLength();
            }
        } catch (Exception e) {
            rt = 0;
            this.log.error("解析部颁表数据", e);
        }
        return rt;
    }

    private Object parseItem(byte[] frame, int loc, MeterProtocolDataItem mpd) {
        Object val = DataItemParser.parsevalue(frame, loc, mpd.getLength(), mpd.getFraction(), mpd.getType());
        return val;
    }
}