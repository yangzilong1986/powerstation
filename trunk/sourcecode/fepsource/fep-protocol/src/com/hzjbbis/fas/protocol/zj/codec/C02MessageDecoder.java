package com.hzjbbis.fas.protocol.zj.codec;

import com.hzjbbis.exception.MessageDecodeException;
import com.hzjbbis.fas.model.RtuData;
import com.hzjbbis.fas.model.RtuDataItem;
import com.hzjbbis.fas.protocol.codec.MessageCodecContext;
import com.hzjbbis.fas.protocol.conf.ProtocolDataItemConfig;
import com.hzjbbis.fas.protocol.data.DataItem;
import com.hzjbbis.fas.protocol.meter.IMeterParser;
import com.hzjbbis.fas.protocol.meter.MeterParserFactory;
import com.hzjbbis.fas.protocol.zj.parse.DataItemParser;
import com.hzjbbis.fas.protocol.zj.parse.ParseTool;
import com.hzjbbis.fas.protocol.zj.parse.TaskSetting;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.zj.MessageZj;
import com.hzjbbis.fk.model.MeasuredPoint;

import java.util.*;

public class C02MessageDecoder extends AbstractMessageDecoder {
    public Object decode(IMessage message) {
        List datas = new ArrayList();
        try {
            if (getOrientation(message) == 1) {
                int rtype = getErrCode(message);
                if (rtype == 0) {
                    byte[] data = getData(message);

                    if (data.length > 9) {
                        String tasknum = String.valueOf(data[0] & 0xFF);
                        TaskSetting ts = new TaskSetting(((MessageZj) message).head.rtua, data[0] & 0xFF, this.dataConfig);
                        if ((ts != null) && (ts.getRtask() != null) && (ts.getDataNum() > 0)) {
                            Calendar time = getTime(data, 1);
                            if (time == null) {
                                throw new MessageDecodeException("帧中包含的时间数据错误:" + ParseTool.BytesToHex(data, 1, 5));
                            }

                            int pnum = data[6] & 0xFF;
                            int ti = getTimeInterval(data[7]);
                            int tn = data[8] & 0xFF;

                            if (ts.getTT() == 2) {
                                parseRelayTask(data, 9, time, tasknum, ts, datas);
                            } else if (data.length - 9 == ts.getDataLength() * pnum) {
                                List datacs = ts.getDI();
                                int loc = 9;
                                Hashtable keys = new Hashtable();
                                for (int i = 0; i < pnum; ++i) {
                                    RtuData bean = new RtuData();
                                    RtuDataItem beanItem = null;
                                    keys.clear();
                                    for (int j = 0; j < ts.getDataNum(); ++j) {
                                        Iterator iterc;
                                        ProtocolDataItemConfig pdc = (ProtocolDataItemConfig) datacs.get(j);
                                        List childs = pdc.getChildItems();
                                        if ((childs != null) && (childs.size() > 0)) {
                                            for (iterc = childs.iterator(); iterc.hasNext();) {
                                                ProtocolDataItemConfig cpdc = (ProtocolDataItemConfig) iterc.next();
                                                Object di = DataItemParser.parsevalue(data, loc, cpdc.getLength(), cpdc.getFraction(), cpdc.getParserno());
                                                loc += cpdc.getLength();
                                                if (!(keys.containsKey(cpdc.getCode()))) {
                                                    beanItem = new RtuDataItem();
                                                    beanItem.setCode(cpdc.getCode());
                                                    beanItem.setValue((di == null) ? null : di.toString());
                                                    bean.addDataList(beanItem);
                                                    keys.put(cpdc.getCode(), cpdc.getCode());
                                                }
                                                bean.setLogicAddress(ts.getRtu().getLogicAddress());
                                                bean.setTaskNum(tasknum);
                                                bean.setTime(time.getTime());
                                            }

                                        } else {
                                            Object di = DataItemParser.parsevalue(data, loc, pdc.getLength(), pdc.getFraction(), pdc.getParserno());
                                            loc += pdc.getLength();
                                            if (!(keys.containsKey(pdc.getCode()))) {
                                                beanItem = new RtuDataItem();
                                                beanItem.setCode(pdc.getCode());
                                                beanItem.setValue((di == null) ? null : di.toString());
                                                bean.addDataList(beanItem);
                                                keys.put(pdc.getCode(), pdc.getCode());
                                            }
                                            bean.setLogicAddress(ts.getRtu().getLogicAddress());
                                            bean.setTime(time.getTime());
                                            bean.setTaskNum(tasknum);
                                        }

                                    }

                                    datas.add(bean);
                                    if (ti <= 1440) {
                                        time.add(12, ti * tn);
                                    } else time.add(2, tn);
                                }
                            } else {
                                MessageCodecContext.setTaskNum(tasknum);
                                String msg = "终端逻辑地址：" + ParseTool.IntToHex4(((MessageZj) message).head.rtua) + "，任务号：" + tasknum;
                                msg = msg + "\r\n数据长度不对,期望数据长度：" + (ts.getDataLength() * pnum + 9) + "  上报数据长度：" + data.length;
                                throw new MessageDecodeException(msg);
                            }
                        } else {
                            MessageCodecContext.setTaskNum(tasknum);
                            throw new MessageDecodeException("未知的任务");
                        }
                    } else {
                        String msg = "终端逻辑地址：" + ParseTool.IntToHex4(((MessageZj) message).head.rtua) + "任务数据长度非法";
                        throw new MessageDecodeException(msg);
                    }

                }

            } else {
                String msg = "终端逻辑地址：" + ParseTool.IntToHex4(((MessageZj) message).head.rtua) + "任务数据格式非法";
                throw new MessageDecodeException(msg);
            }
        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }

        return datas;
    }

    private int getOrientation(IMessage message) {
        return ParseTool.getOrientation(message);
    }

    private int getErrCode(IMessage message) {
        return ParseTool.getErrCode(message);
    }

    private byte[] getData(IMessage message) {
        return ParseTool.getData(message);
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
            e.printStackTrace();
        }
        return rt;
    }

    private int getTimeInterval(byte type) {
        int rt = 0;
        switch (type) {
            case 2:
                rt = 1;
                break;
            case 3:
                rt = 60;
                break;
            case 4:
                rt = 1440;
                break;
            case 5:
                rt = 43200;
        }

        return rt;
    }

    private void parseRelayTask(byte[] data, int pos, Calendar time, String tasknum, TaskSetting ts, List<RtuData> result) {
        if (data == null) return;
        MeasuredPoint mp = ts.getRtu().getMeasuredPoint(String.valueOf(ts.getTN()));
        if (mp == null) {
            throw new MessageDecodeException("指定测量点不存在！终端--" + ts.getRtu().getLogicAddress() + "  测量点--" + ts.getTN());
        }
        String pm = ParseTool.getMeterProtocol(mp.getAtrProtocol());
        if (pm == null) {
            throw new MessageDecodeException("不支持的表规约：" + mp.getAtrProtocol());
        }
        IMeterParser mparser = MeterParserFactory.getMeterParser(pm);
        if (mparser == null) {
            throw new MessageDecodeException("不支持的表规约：" + mp.getAtrProtocol());
        }

        Object[] dis = mparser.parser(data, pos, data.length - pos);
        if (dis != null) {
            RtuData bean = new RtuData();
            RtuDataItem beanItem = null;
            for (int i = 0; i < dis.length; ++i) {
                DataItem di = (DataItem) dis[i];
                beanItem = new RtuDataItem();
                beanItem.setCode((String) di.getProperty("datakey"));
                beanItem.setValue((di == null) ? null : di.toString());
                bean.addDataList(beanItem);
                bean.setLogicAddress(ts.getRtu().getLogicAddress());
                bean.setTaskNum(tasknum);
                bean.setTime(time.getTime());

                result.add(bean);
            }
        }
    }
}