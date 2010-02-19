package com.hzjbbis.fas.protocol.zj.codec;

import com.hzjbbis.exception.MessageDecodeException;
import com.hzjbbis.fas.model.RtuAlert;
import com.hzjbbis.fas.model.RtuAlertArg;
import com.hzjbbis.fas.protocol.conf.ProtocolDataItemConfig;
import com.hzjbbis.fas.protocol.zj.parse.DataItemParser;
import com.hzjbbis.fas.protocol.zj.parse.ParseTool;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.zj.MessageZj;
import com.hzjbbis.fk.model.BizRtu;
import com.hzjbbis.fk.model.RtuManage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.NumberFormat;
import java.util.*;

public class C09MessageDecoder extends AbstractMessageDecoder {
    private static final Log log = LogFactory.getLog(C09MessageDecoder.class);

    public Object decode(IMessage message) {
        List rt = null;
        try {
            if (ParseTool.getOrientation(message) == 1) {
                int rtype = ParseTool.getErrCode(message);
                if (rtype == 0) {
                    byte[] data = ParseTool.getData(message);

                    if (data != null) {
                        int num = data[0] & 0xFF;
                        int loc = 1;

                        int rtu = ((MessageZj) message).head.rtua;
                        BizRtu ortu = RtuManage.getInstance().getBizRtuInCache(rtu);
                        if (ortu == null) {
                            throw new MessageDecodeException("无法获取终端信息--" + ParseTool.IntToHex4(rtu));
                        }
                        NumberFormat nf = NumberFormat.getInstance();
                        nf.setMaximumFractionDigits(4);
                        rt = new ArrayList();
                        Calendar date = Calendar.getInstance();

                        date.setTimeInMillis(((MessageZj) message).getIoTime());
                        do {
                            if (loc >= data.length) break label1236;
                            if (8 <= data.length - loc) {
                                int point = data[loc] & 0xFF;
                                Calendar stime = getTime(data, loc + 1);
                                int alr = (data[(loc + 6)] & 0xFF) + ((data[(loc + 7)] & 0xFF) << 8);
                                loc += 8;
                                List rac = getRtuAlertCode(alr);
                                if (rac != null) {
                                    RtuAlert ra = new RtuAlert();
                                    ra.setRtuId(ortu.getRtuId());
                                    ra.setCorpNo(ortu.getDeptCode());
                                    String tn = String.valueOf(point);
                                    ra.setTn(tn);
                                    if (ortu.getMeasuredPoint(tn) != null) {
                                        ra.setDataSaveID(ortu.getMeasuredPoint(tn).getDataSaveID());
                                        ra.setCustomerNo(ortu.getMeasuredPoint(tn).getCustomerNo());
                                        ra.setStationNo(ortu.getMeasuredPoint(tn).getCustomerNo());
                                    } else {
                                        log.warn("rtu=" + ParseTool.IntToHex4(rtu) + ",tn=" + tn + "未建档！");
                                    }
                                    ra.setAlertCode(alr);
                                    ra.setAlertTime(stime.getTime());

                                    ra.setReceiveTime(new Date(((MessageZj) message).getIoTime()));
                                    List alertdatas = new ArrayList();
                                    String olddi = "";
                                    Hashtable databag = new Hashtable();
                                    for (Iterator iter = rac.iterator(); iter.hasNext();) {
                                        String di = (String) iter.next();
                                        boolean is8ffe = false;
                                        if ((di.equals("8FFE")) || (di.equals("8ffe"))) {
                                            if (olddi.length() <= 0) {
                                                throw new MessageDecodeException("告警配置错误：终端--" + ParseTool.IntToHex4(rtu) + " 告警编码--" + ParseTool.IntToHex(alr));
                                            }
                                            is8ffe = true;
                                            di = olddi;
                                        } else {
                                            is8ffe = false;
                                            olddi = di;
                                        }
                                        ProtocolDataItemConfig pdc = this.dataConfig.getDataItemConfig(di);
                                        if (pdc != null) {
                                            if (pdc.getLength() <= data.length - loc) {
                                                Iterator iterc;
                                                List childs = pdc.getChildItems();
                                                if ((childs != null) && (childs.size() > 0)) {
                                                    for (iterc = childs.iterator(); iterc.hasNext();) {
                                                        ProtocolDataItemConfig cpdc = (ProtocolDataItemConfig) iterc.next();
                                                        Object dt = null;
                                                        try {
                                                            dt = DataItemParser.parsevalue(data, loc, cpdc.getLength(), cpdc.getFraction(), cpdc.getParserno());
                                                        } catch (Exception e) {
                                                            log.error("告警数据解析错误", e);
                                                        }
                                                        RtuAlertArg arg = null;
                                                        if (dt != null) {
                                                            if (is8ffe) {
                                                                arg = (RtuAlertArg) databag.get(cpdc.getCode());
                                                                arg.setCorrelValue(dt.toString());
                                                            } else {
                                                                arg = new RtuAlertArg();
                                                                arg.setCode(cpdc.getCode());
                                                                arg.setValue(dt.toString());
                                                                databag.put(cpdc.getCode(), arg);
                                                                alertdatas.add(arg);
                                                            }

                                                        } else if (!(is8ffe)) {
                                                            arg = new RtuAlertArg();
                                                            arg.setCode(cpdc.getCode());
                                                            arg.setValue(null);
                                                            alertdatas.add(arg);
                                                            databag.put(cpdc.getCode(), arg);
                                                        }

                                                        loc += cpdc.getLength();
                                                    }
                                                } else {
                                                    Object dt = DataItemParser.parsevalue(data, loc, pdc.getLength(), pdc.getFraction(), pdc.getParserno());
                                                    RtuAlertArg arg = null;
                                                    if (dt != null) {
                                                        if (is8ffe) {
                                                            arg = (RtuAlertArg) databag.get(pdc.getCode());
                                                            arg.setCorrelValue(dt.toString());
                                                        } else {
                                                            arg = new RtuAlertArg();
                                                            arg.setCode(pdc.getCode());
                                                            arg.setValue(dt.toString());
                                                            alertdatas.add(arg);
                                                            databag.put(pdc.getCode(), arg);
                                                        }
                                                    } else if (!(is8ffe)) {
                                                        arg = new RtuAlertArg();
                                                        arg.setCode(pdc.getCode());
                                                        arg.setValue(null);
                                                        alertdatas.add(arg);
                                                        databag.put(pdc.getCode(), arg);
                                                    }

                                                    loc += pdc.getLength();
                                                }
                                                break label1107:
                                            }
                                            log.info("数据长度不对,数据项：" + pdc.getCode() + " 期望数据长度：" + pdc.getLength() + " 解析长度：" + (data.length - loc));
                                            loc = data.length;
                                            break;
                                        }

                                        throw new MessageDecodeException("无法识别的数据项");
                                    }

                                    label1107:
                                    ra.setArgs(alertdatas);
                                    rt.add(ra);
                                } else {
                                    throw new MessageDecodeException("无法获取告警配置 告警编码--" + ParseTool.IntToHex(alr));
                                }
                            } else {
                                throw new MessageDecodeException("数据长度不对");
                            }
                            --num;
                        } while ((num > 0) || (loc >= data.length));

                        log.info("数据长度不对,预期长度：" + loc + " 解析长度：" + data.length);
                        label1236:
                        loc = data.length;
                    } else {
                        throw new MessageDecodeException("数据长度不对");
                    }
                }
            }

        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }

        if (rt != null) {
            for (int i = 0; i < rt.size(); ++i) {
                RtuAlert rtuAlert = (RtuAlert) rt.get(i);
                rtuAlertSetSbcs(rtuAlert);
            }
        }
        return rt;
    }

    private void rtuAlertSetSbcs(RtuAlert rtuAlert) {
        try {
            StringBuffer sb = new StringBuffer();
            List args = rtuAlert.getArgs();
            if ((args != null) && (args.size() > 0)) {
                RtuAlertArg arg = (RtuAlertArg) args.get(0);
                sb.append(arg.getCode());
                sb.append("=");
                if (arg.getValue() != null) {
                    sb.append(arg.getValue());
                }
                sb.append("@");
                if (arg.getCorrelValue() != null) {
                    sb.append(arg.getCorrelValue());
                }
                for (int i = 1; i < args.size(); ++i) {
                    arg = (RtuAlertArg) args.get(i);
                    sb.append(";");
                    sb.append(arg.getCode());
                    sb.append("=");
                    if (arg.getValue() != null) {
                        sb.append(arg.getValue());
                    }
                    sb.append("@");
                    if (arg.getCorrelValue() != null) {
                        sb.append(arg.getCorrelValue());
                    }
                }
                rtuAlert.setSbcs(sb.toString());
            }
        } catch (Exception ex) {
        }
    }

    private Calendar getTime(byte[] data, int offset) {
        Calendar rt = null;
        try {
            int month = ParseTool.BCDToDecimal(data[(1 + offset)]);
            int year = ParseTool.BCDToDecimal(data[offset]);
            if ((month > 0) && (year >= 0) && (ParseTool.isValidMonth(data[(1 + offset)])) && (ParseTool.isValidDay(data[(2 + offset)], month, year + 2000)) && (ParseTool.isValidHHMMSS(data[(3 + offset)])) && (ParseTool.isValidHHMMSS(data[(4 + offset)]))) {
                rt = Calendar.getInstance();
                rt.set(1, year + 2000);
                rt.set(2, month - 1);
                int num = ParseTool.BCDToDecimal((byte) (data[(2 + offset)] & 0x3F));
                rt.set(5, num);
                num = ParseTool.BCDToDecimal((byte) (data[(3 + offset)] & 0x3F));
                rt.set(11, num);
                num = ParseTool.BCDToDecimal((byte) (data[(4 + offset)] & 0x7F));
                if (num >= 60) {
                    rt.add(11, 1);
                    num = 0;
                }
                rt.set(12, num);
                rt.set(13, 0);
                rt.set(14, 0);
            }
        } catch (Exception e) {
            log.error("alert decode", e);
        }
        return rt;
    }

    private List getRtuAlertCode(int alert) {
        List rt = null;
        try {
            rt = RtuManage.getInstance().getRtuAlertCode(alert).getArgs();
        } catch (Exception e) {
            log.error("get alert code", e);
        }
        return rt;
    }
}