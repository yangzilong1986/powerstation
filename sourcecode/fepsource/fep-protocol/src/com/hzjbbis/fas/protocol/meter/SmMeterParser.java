package com.hzjbbis.fas.protocol.meter;

import com.hzjbbis.fas.protocol.data.DataItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class SmMeterParser implements IMeterParser {
    private final Log log;

    public SmMeterParser() {
        this.log = LogFactory.getLog(SmMeterParser.class);
    }

    public byte[] constructor(String[] datakey, DataItem para) {
        return new byte[]{47, 63, 33, 13, 10};
    }

    public String[] convertDataKey(String[] datakey) {
        return datakey;
    }

    public Object[] parser(byte[] data, int loc, int len) {
        List result = null;
        try {
            SmMeterFrame frame = new SmMeterFrame();
            frame.parse(data, loc, len);
            if (frame.getLen() > 0) {
                String dstring;
                String val;
                String dbuf = new String(frame.getData(), "iso-8859-1");
                result = new ArrayList();
                int sindex = 0;
                int eindex = 0;

                sindex = dbuf.indexOf("4.1(");
                if (sindex >= 0) {
                    sindex += 4;
                    eindex = dbuf.indexOf(")", sindex);
                    if (eindex > 0) {
                        dstring = dbuf.substring(sindex, eindex);

                        val = fixValue(dstring);
                        addItem(val, "9011", result);
                    }
                }
                sindex = dbuf.indexOf("4.2(");
                if (sindex >= 0) {
                    sindex += 4;
                    eindex = dbuf.indexOf(")", sindex);
                    if (eindex > 0) {
                        dstring = dbuf.substring(sindex, eindex);

                        val = fixValue(dstring);
                        addItem(val, "9012", result);
                    }
                }
                sindex = dbuf.indexOf("4.3(");
                if (sindex >= 0) {
                    sindex += 4;
                    eindex = dbuf.indexOf(")", sindex);
                    if (eindex > 0) {
                        dstring = dbuf.substring(sindex, eindex);

                        val = fixValue(dstring);
                        addItem(val, "9013", result);
                    }
                }

                sindex = dbuf.indexOf("5.1(");
                if (sindex >= 0) {
                    sindex += 4;
                    eindex = dbuf.indexOf(")", sindex);
                    if (eindex > 0) {
                        dstring = dbuf.substring(sindex, eindex);

                        val = fixValue(dstring);
                        addItem(val, "9021", result);
                    }
                }
                sindex = dbuf.indexOf("5.2(");
                if (sindex >= 0) {
                    sindex += 4;
                    eindex = dbuf.indexOf(")", sindex);
                    if (eindex > 0) {
                        dstring = dbuf.substring(sindex, eindex);

                        val = fixValue(dstring);
                        addItem(val, "9022", result);
                    }
                }
                sindex = dbuf.indexOf("5.3(");
                if (sindex >= 0) {
                    sindex += 4;
                    eindex = dbuf.indexOf(")", sindex);
                    if (eindex > 0) {
                        dstring = dbuf.substring(sindex, eindex);

                        val = fixValue(dstring);
                        addItem(val, "9023", result);
                    }
                }

                sindex = dbuf.indexOf("6(");
                if (sindex >= 0) {
                    sindex += 2;
                    eindex = dbuf.indexOf(")", sindex);
                    if (eindex > 0) {
                        dstring = dbuf.substring(sindex, eindex);

                        val = fixValue(dstring);
                        addItem(val, "9010", result);
                    }
                }

                sindex = dbuf.indexOf("7(");
                if (sindex >= 0) {
                    sindex += 2;
                    eindex = dbuf.indexOf(")", sindex);
                    if (eindex > 0) {
                        dstring = dbuf.substring(sindex, eindex);

                        val = fixValue(dstring);
                        addItem(val, "9020", result);
                    }
                }

                sindex = dbuf.indexOf("8(");
                if (sindex >= 0) {
                    sindex += 2;
                    eindex = dbuf.indexOf(")", sindex);
                    if (eindex > 0) {
                        dstring = dbuf.substring(sindex, eindex);

                        val = fixValue(dstring);
                        addItem(val, "9110", result);
                    }
                }
                sindex = dbuf.indexOf("9(");
                if (sindex >= 0) {
                    sindex += 2;
                    eindex = dbuf.indexOf(")", sindex);
                    if (eindex > 0) {
                        dstring = dbuf.substring(sindex, eindex);

                        val = fixValue(dstring);
                        addItem(val, "9120", result);
                    }
                }

                sindex = dbuf.indexOf("12(");
                if (sindex >= 0) {
                    sindex += 3;
                    eindex = dbuf.indexOf(")", sindex);
                    if (eindex > 0) {
                        dstring = dbuf.substring(sindex, eindex);

                        val = fixValue(dstring);
                        addItem(val, "A010", result);

                        if (dbuf.substring(eindex + 1, eindex + 2).equals("(")) {
                            sindex = eindex + 2;
                            eindex = dbuf.indexOf(")", sindex + 1);
                            if (eindex > 0) {
                                dstring = dbuf.substring(sindex, eindex);
                                addItem("20" + dstring, "B010", result);
                            }
                        }
                    }
                }

                sindex = dbuf.indexOf("13(");
                if (sindex >= 0) {
                    sindex += 3;
                    eindex = dbuf.indexOf(")", sindex);
                    if (eindex > 0) {
                        dstring = dbuf.substring(sindex, eindex);

                        val = fixValue(dstring);
                        addItem(val, "A020", result);

                        if (dbuf.substring(eindex + 1, eindex + 2).equals("(")) {
                            sindex = eindex + 2;
                            eindex = dbuf.indexOf(")", sindex + 1);
                            if (eindex > 0) {
                                dstring = dbuf.substring(sindex, eindex);
                                addItem("20" + dstring, "B020", result);
                            }
                        }
                    }
                }

                sindex = dbuf.indexOf("L.1(");
                if (sindex >= 0) {
                    sindex += 4;
                    eindex = dbuf.indexOf(")", sindex);
                    if (eindex > 0) {
                        dstring = dbuf.substring(sindex, eindex);

                        val = fixValue(dstring);
                        addItem(val, "B611", result);
                    }
                }
                sindex = dbuf.indexOf("L.2(");
                if (sindex >= 0) {
                    sindex += 4;
                    eindex = dbuf.indexOf(")", sindex);
                    if (eindex > 0) {
                        dstring = dbuf.substring(sindex, eindex);

                        val = fixValue(dstring);
                        addItem(val, "B612", result);
                    }
                }
                sindex = dbuf.indexOf("L.3(");
                if (sindex >= 0) {
                    sindex += 4;
                    eindex = dbuf.indexOf(")", sindex);
                    if (eindex > 0) {
                        dstring = dbuf.substring(sindex, eindex);

                        val = fixValue(dstring);
                        addItem(val, "B613", result);
                    }
                }
            }
        } catch (Exception e) {
            this.log.error("解析西门子表规约", e);
        }

        if (result != null) {
            return result.toArray();
        }
        return null;
    }

    private String fixValue(String val) {
        int index = val.indexOf("*");
        if (index > 0) {
            return val.substring(0, index);
        }
        return val;
    }

    private void addItem(String val, String key, List result) {
        DataItem item = new DataItem();
        item.addProperty("value", val);
        item.addProperty("datakey", key);
        result.add(item);
    }

    public String[] getMeterCode(String[] codes) {
        return codes;
    }
}