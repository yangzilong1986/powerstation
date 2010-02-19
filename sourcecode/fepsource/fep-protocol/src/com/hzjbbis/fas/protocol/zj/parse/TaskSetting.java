package com.hzjbbis.fas.protocol.zj.parse;

import com.hzjbbis.exception.MessageDecodeException;
import com.hzjbbis.fas.protocol.conf.ProtocolDataConfig;
import com.hzjbbis.fas.protocol.conf.ProtocolDataItemConfig;
import com.hzjbbis.fk.model.BizRtu;
import com.hzjbbis.fk.model.RtuManage;
import com.hzjbbis.fk.model.TaskTemplate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TaskSetting {
    private final Log log = LogFactory.getLog(TaskSetting.class);
    public static final int TIME_UNIT_MINUTE = 2;
    public static final int TIME_UNIT_HOUR = 3;
    public static final int TIME_UNIT_DAY = 4;
    public static final int TIME_UNIT_MONTH = 5;
    private int TT;
    private int TS;
    private int TSUnit;
    private int TI;
    private int TIUnit;
    private int RS;
    private int RSUnit;
    private int RI;
    private int RIUnit;
    private int RDI;
    private int TN;
    private int SP;
    private int RT;
    private int DIN;
    private List DI;
    private BizRtu rtu = null;
    private TaskTemplate rtask = null;

    public TaskSetting() {
    }

    public TaskSetting(int rtua, int taskid, ProtocolDataConfig pdc) {
        this.TT = 0;
        this.TS = 0;
        this.TSUnit = 2;
        this.TI = 15;
        this.TIUnit = 2;
        this.RS = 5;
        this.RSUnit = 2;
        this.RI = 1;
        this.RIUnit = 4;
        this.RDI = 1;
        this.TN = 0;
        this.SP = 96;
        this.RT = 0;
        this.DIN = 1;
        this.DI = new ArrayList();
        try {
            this.rtu = RtuManage.getInstance().getBizRtuInCache(rtua);
            if (this.rtu == null) return;
            this.rtask = this.rtu.getTaskTemplate(String.valueOf(taskid));
            if (this.rtask == null) return;
            List dids = this.rtask.getDataCodes();
            this.log.debug("任务配置：终端--" + ParseTool.IntToHex4(rtua) + "，任务号--" + taskid + "，数据项个数--" + dids.size());

            for (int iter = 0; iter < dids.size(); ++iter) {
                this.DI.add(iter, pdc.getDataItemConfig((String) dids.get(iter)));
            }
            this.TS = this.rtask.getSampleStartTime();
            this.TSUnit = Integer.parseInt(this.rtask.getSampleStartTimeUnit(), 16);
            this.TI = this.rtask.getSampleInterval();
            this.TIUnit = Integer.parseInt(this.rtask.getSampleIntervalUnit(), 16);
            this.RS = this.rtask.getUploadStartTime();
            this.RSUnit = Integer.parseInt(this.rtask.getUploadStartTimeUnit(), 16);
            this.RI = this.rtask.getUploadInterval();
            this.RIUnit = Integer.parseInt(this.rtask.getUploadIntervalUnit(), 16);
            this.RDI = this.rtask.getFrequence();
        } catch (Exception e) {
            throw new MessageDecodeException("无法获取终端任务配置，终端逻辑地址：" + ParseTool.IntToHex4(rtua) + "，任务号：" + taskid);
        }
    }

    public int getDataLength() {
        int rt = 0;
        for (Iterator iter = this.DI.iterator(); iter.hasNext();) {
            ProtocolDataItemConfig dc = (ProtocolDataItemConfig) iter.next();
            rt += dc.getLength();
        }
        return rt;
    }

    public String getDataCodes() {
        StringBuffer sb = new StringBuffer();
        for (Iterator iter = this.DI.iterator(); iter.hasNext();) {
            ProtocolDataItemConfig dc = (ProtocolDataItemConfig) iter.next();
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(dc.getCode());
        }
        return sb.toString();
    }

    public int getDataNum() {
        int rt = 0;
        if (this.DI != null) {
            rt = this.DI.size();
        }
        return rt;
    }

    public List getDI() {
        return this.DI;
    }

    public void setDI(List di) {
        this.DI = di;
    }

    public int getDIN() {
        return this.DIN;
    }

    public void setDIN(int din) {
        this.DIN = din;
    }

    public int getRDI() {
        return this.RDI;
    }

    public void setRDI(int rdi) {
        this.RDI = rdi;
    }

    public int getRI() {
        return this.RI;
    }

    public void setRI(int ri) {
        this.RI = ri;
    }

    public int getRIUnit() {
        return this.RIUnit;
    }

    public void setRIUnit(int unit) {
        this.RIUnit = unit;
    }

    public int getRS() {
        return this.RS;
    }

    public void setRS(int rs) {
        this.RS = rs;
    }

    public int getRSUnit() {
        return this.RSUnit;
    }

    public void setRSUnit(int unit) {
        this.RSUnit = unit;
    }

    public int getRT() {
        return this.RT;
    }

    public void setRT(int rt) {
        this.RT = rt;
    }

    public int getSP() {
        return this.SP;
    }

    public void setSP(int sp) {
        this.SP = sp;
    }

    public int getTI() {
        return this.TI;
    }

    public void setTI(int ti) {
        this.TI = ti;
    }

    public int getTIUnit() {
        return this.TIUnit;
    }

    public void setTIUnit(int unit) {
        this.TIUnit = unit;
    }

    public int getTN() {
        return this.TN;
    }

    public void setTN(int tn) {
        this.TN = tn;
    }

    public int getTS() {
        return this.TS;
    }

    public void setTS(int ts) {
        this.TS = ts;
    }

    public int getTSUnit() {
        return this.TSUnit;
    }

    public void setTSUnit(int unit) {
        this.TSUnit = unit;
    }

    public int getTT() {
        return this.TT;
    }

    public void setTT(int tt) {
        this.TT = tt;
    }

    public BizRtu getRtu() {
        return this.rtu;
    }

    public TaskTemplate getRtask() {
        return this.rtask;
    }

    public void setRtask(TaskTemplate rtask) {
        this.rtask = rtask;
    }
}