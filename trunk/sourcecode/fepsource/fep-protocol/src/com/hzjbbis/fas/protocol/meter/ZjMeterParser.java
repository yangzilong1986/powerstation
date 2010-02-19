package com.hzjbbis.fas.protocol.meter;

import com.hzjbbis.fas.protocol.data.DataItem;
import com.hzjbbis.fas.protocol.meter.conf.MeterProtocolDataItem;
import com.hzjbbis.fas.protocol.meter.conf.MeterProtocolDataSet;
import com.hzjbbis.fas.protocol.zj.parse.DataItemParser;
import com.hzjbbis.fas.protocol.zj.parse.ParseTool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

public class ZjMeterParser implements IMeterParser {
    private final Log log = LogFactory.getLog(ZjMeterParser.class);
    private MeterProtocolDataSet dataset;

    public ZjMeterParser() {
        try {
            this.dataset = MeterProtocolFactory.createMeterProtocolDataSet("ZJMeter");
        } catch (Exception e) {
            this.log.error("浙江表规约初始化失败");
        }
    }

    public String[] convertDataKey(String[] datakey) {
        String[] rt = null;
        try {
            if ((datakey != null) && (datakey.length > 0)) {
                List parents = getParents(this.dataset);
                if (parents != null) {
                    rt = new String[datakey.length];
                    for (int i = 0; i < datakey.length; ++i) {
                        String dkprefix = datakey[i].substring(0, 3);
                        for (Iterator iter = parents.iterator(); iter.hasNext();) {
                            MeterProtocolDataItem di = (MeterProtocolDataItem) iter.next();
                            boolean bfind = false;
                            if ((di.getZjcode() != null) && (di.getZjcode().length() > 0)) {
                                if (di.getZjcode().equalsIgnoreCase("B6FF"))
                                    bfind = dkprefix.substring(0, 2).equalsIgnoreCase("B6");
                                else {
                                    bfind = di.getZjcode().substring(0, 3).equals(dkprefix);
                                }
                            }
                            if ((!(bfind)) && (di.getZjcode2() != null) && (di.getZjcode2().length() > 0)) {
                                bfind = di.getZjcode2().substring(0, 3).equals(dkprefix);
                            }
                            if (bfind) {
                                boolean block = false;
                                if (di.getZjcode() != null) {
                                    block = (di.getZjcode().equals(datakey[i])) || (datakey[i].equalsIgnoreCase("B61F")) || (datakey[i].equalsIgnoreCase("B62F"));
                                }

                                if ((!(block)) && (di.getZjcode2() != null)) {
                                    block = di.getZjcode2().equals(datakey[i]);
                                }
                                if (block) {
                                    rt[i] = di.getCode();
                                    break;
                                }
                                List cdks = di.getChildarray();
                                for (Iterator iter1 = cdks.iterator(); iter1.hasNext();) {
                                    MeterProtocolDataItem cdi = (MeterProtocolDataItem) iter1.next();
                                    if ((cdi.getZjcode() != null) && (cdi.getZjcode().equals(datakey[i]))) {
                                        rt[i] = cdi.getCode();
                                        break;
                                    }
                                    if ((cdi.getZjcode2() != null) && (cdi.getZjcode2().equals(datakey[i]))) {
                                        rt[i] = cdi.getCode();
                                        break;
                                    }
                                }

                                break;
                            }
                        }
                        if ((rt[i] == null) || (rt[i].equals(""))) {
                            this.log.info("不支持的数据召测：" + datakey[i]);
                            rt = null;
                            break;
                        }
                    }
                } else {
                    this.log.info("空的表规约集合，请检查表规约定义");
                }
            }
        } catch (Exception e) {
            this.log.error("convert datakey", e);
        }
        return fixCode(rt);
    }

    private String[] fixCode(String[] codes) {
        String[] rt = null;
        try {
            rt = new String[codes.length];
            rt[0] = codes[0];
            int j = 1;
            for (int i = 1; i < codes.length; ++i) {
                boolean fixed = false;
                for (int k = 0; k < j; ++k) {
                    if (rt[k].equalsIgnoreCase(codes[i])) {
                        fixed = true;
                        break;
                    }
                    if (rt[k].substring(0, 3).equalsIgnoreCase(codes[i].substring(0, 3))) {
                        fixed = true;
                        rt[k] = rt[k].substring(0, 3) + "0";
                    }
                }

                if (!(fixed)) {
                    rt[j] = codes[i];
                    ++j;
                }
            }
        } catch (Exception e) {
        }
        return rt;
    }

    private List getParents(MeterProtocolDataSet dataset) {
        List rt = null;
        try {
            Hashtable dks = dataset.getDataset();
            Enumeration dkey = dks.elements();
            rt = new ArrayList();
            while (dkey.hasMoreElements()) {
                MeterProtocolDataItem di = (MeterProtocolDataItem) dkey.nextElement();
                if (di.getChildarray() == null) {
                    continue;
                }
                if (di.getChildarray().size() <= 0) {
                    continue;
                }
                rt.add(di);
            }
        } catch (Exception e) {
            this.log.error("pretreatment protocol", e);
        }
        return rt;
    }

    public byte[] constructor(String[] datakey, DataItem para) {
        String dk = "";
        byte[] frame = null;
        try {
            if ((datakey != null) && (datakey.length > 0) && (para != null) && (para.getProperty("point") != null)) {
                if (datakey.length == 1) {
                    dk = datakey[0];
                } else {
                    dk = datakey[0].substring(0, 3) + "0";
                    for (int i = 1; i < datakey.length; ++i) {
                        if (dk.substring(0, 2).equals(datakey[i].substring(0, 2))) {
                            if ((dk.substring(2, 3).equals(datakey[i].substring(2, 3))) || (dk.substring(2, 3).equals("0")))
                                continue;
                            dk = dk.substring(0, 2) + "00";
                        } else {
                            this.log.info("目前只支持召测同类数据，非同类数据请分别召测！");
                            dk = "";
                            break;
                        }
                    }
                }
                if (dk.length() > 0) {
                    frame = new byte[9];
                    constructFrameCallData(frame, dk, ParseTool.HexToByte((String) para.getProperty("point")));
                }
            }
        } catch (Exception e) {
            this.log.error("Construct ZJ meter frame ", e);
        }
        return frame;
    }

    private void constructFrameCallData(byte[] frame, String datakey, byte maddr) {
        frame[0] = 104;
        frame[1] = 3;
        frame[2] = 3;
        frame[3] = 104;
        frame[4] = maddr;
        ParseTool.HexsToBytes(frame, 5, datakey);
        frame[7] = ParseTool.calculateCS(frame, 4, 3);
        frame[8] = 13;
    }

    public Object[] parser(byte[] data, int loc, int len) {
        List result = null;
        try {
            ZjMeterFrame frame = new ZjMeterFrame();
            frame.parse(data, loc, len);
            if (frame.getDatalen() > 0) {
                result = new ArrayList();

                int datalen = frame.getDatalen();
                if (datalen != 1) {
                    byte[] framedata = frame.getData();
                    String meteraddr = frame.getMeteraddr();
                    DataItem item = new DataItem();
                    item.addProperty("value", meteraddr);
                    item.addProperty("datakey", "8902");
                    result.add(item);
                    if (datalen == 2) {
                        int rtype = framedata[(frame.getPos() + 1)] & 0xFF;
                        if ((rtype == 240) && (rtype != 250)) ;
                    } else {
                        int iloc = frame.getPos();
                        ++iloc;
                        while (iloc < framedata.length - 2) {
                            String datakey = ParseTool.BytesToHexC(framedata, iloc, 2);
                            MeterProtocolDataItem mpd = this.dataset.getDataItem(datakey);
                            iloc += 2;
                            if (mpd == null) break;
                            List children = mpd.getChildarray();
                            if ((children != null) && (children.size() > 0)) {
                                for (int ic = 0; ic < children.size(); ++ic) {
                                    MeterProtocolDataItem cmpd = (MeterProtocolDataItem) children.get(ic);
                                    if ((framedata[iloc] & 0xFF) == 237) {
                                        break;
                                    }
                                    if ((framedata[iloc] & 0xFF) == 186) {
                                        ++iloc;
                                    } else {
                                        Object val = parseItem(framedata, iloc, cmpd);
                                        toZjDataItem(val, cmpd, result);
                                        iloc += cmpd.getLength();
                                    }
                                }
                                if ((framedata[iloc] & 0xFF) == 237) {
                                    ++iloc;
                                }
                            } else if ((framedata[iloc] & 0xFF) == 186) {
                                ++iloc;
                            } else {
                                Object val = parseItem(framedata, iloc, mpd);
                                toZjDataItem(val, mpd, result);
                                iloc += mpd.getLength();
                            }
                        }
                    }

                }

            }

        } catch (Exception e) {
            this.log.error("解析浙江表规约", e);
        }
        if (result != null) {
            return result.toArray();
        }
        return null;
    }

    private Object parseItem(byte[] frame, int loc, MeterProtocolDataItem mpd) {
        Object val = DataItemParser.parsevalue(frame, loc, mpd.getLength(), mpd.getFraction(), mpd.getType());
        return val;
    }

    private void toZjDataItem(Object val, MeterProtocolDataItem mpd, List result) {
        try {
            if ((mpd.getZjcode2() != null) && (mpd.getZjcode2().length() > 0)) {
                String[] vals = ((String) val).split(",");
                DataItem item = new DataItem();
                item.addProperty("value", vals[0]);
                item.addProperty("datakey", mpd.getZjcode());
                result.add(item);
                if (vals.length > 1) {
                    DataItem item2 = new DataItem();
                    item2.addProperty("value", vals[1]);
                    item2.addProperty("datakey", mpd.getZjcode2());
                    result.add(item2);
                }
            } else {
                DataItem item = new DataItem();
                item.addProperty("value", val);
                item.addProperty("datakey", mpd.getZjcode());
                result.add(item);
            }
        } catch (Exception e) {
            this.log.error("convert to zj data", e);
        }
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
}