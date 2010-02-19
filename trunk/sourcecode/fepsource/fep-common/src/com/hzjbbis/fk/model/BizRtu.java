package com.hzjbbis.fk.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BizRtu {
    private String rtuId;
    private String deptCode;
    private String rtuProtocol;
    private String rtuType;
    private int rtua;
    private String logicAddress;
    private String manufacturer;
    private String hiAuthPassword;
    private String loAuthPassword;
    private Map<String, MeasuredPoint> measuredPoints = new HashMap();

    private Map<Integer, RtuTask> tasksMap = new HashMap();

    public String getRtuType() {
        return this.rtuType;
    }

    public void setRtuType(String rtuType) {
        this.rtuType = rtuType;
    }

    public MeasuredPoint getMeasuredPoint(String tn) {
        return ((MeasuredPoint) this.measuredPoints.get(tn));
    }

    public void addMeasuredPoint(MeasuredPoint mp) {
        this.measuredPoints.put(mp.getTn(), mp);
    }

    public void addRtuTask(RtuTask rt) {
        this.tasksMap.put(new Integer(rt.getRtuTaskNum()), rt);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[id=").append(this.rtuId).append(", logicAddress=").append(this.logicAddress).append(", protocol=").append(this.rtuProtocol).append(", manufacturer=").append(this.manufacturer).append(", ... ]");
        return sb.toString();
    }

    public RtuTask getRtuTask(String taskNum) {
        if ((this.tasksMap == null) || (taskNum == null)) {
            return null;
        }
        return ((RtuTask) this.tasksMap.get(new Integer(taskNum)));
    }

    public TaskTemplate getTaskTemplate(String taskNum) {
        if ((this.tasksMap == null) || (taskNum == null)) {
            return null;
        }
        RtuTask rt = (RtuTask) this.tasksMap.get(new Integer(taskNum));
        if (rt != null) {
            return RtuManage.getInstance().getTaskPlateInCache(rt.getTaskTemplateID());
        }

        return null;
    }

    public String getTaskNum(List<String> dataCodes) {
        String taskNum = null;
        String codeStr = "";
        for (int i = 0; i < dataCodes.size(); ++i)
            codeStr = codeStr + ((String) dataCodes.get(i)) + ",";
        Iterator it = this.tasksMap.entrySet().iterator();
        while (it.hasNext()) {
            int icount = 0;
            Map.Entry entry = (Map.Entry) it.next();
            RtuTask rt = (RtuTask) entry.getValue();
            TaskTemplate ttp = RtuManage.getInstance().getTaskPlateInCache(rt.getTaskTemplateID());
            List codes = ttp.getDataCodes();
            if (dataCodes.size() >= codes.size()) {
                for (int i = 0; i < codes.size(); ++i) {
                    if (codeStr.indexOf((String) codes.get(i)) < 0) {
                        break;
                    }
                    ++icount;
                }
                if (codes.size() == icount) return entry.getKey();
            }
        }
        return taskNum;
    }

    public String getRtuId() {
        return this.rtuId;
    }

    public void setRtuId(String rtuId) {
        this.rtuId = rtuId;
    }

    public String getRtuProtocol() {
        return this.rtuProtocol;
    }

    public void setRtuProtocol(String rtuProtocol) {
        this.rtuProtocol = rtuProtocol;
    }

    public int getRtua() {
        return this.rtua;
    }

    public void setRtua(int rtua) {
        this.rtua = rtua;
    }

    public String getLogicAddress() {
        return this.logicAddress;
    }

    public void setLogicAddress(String logicAddress) {
        this.logicAddress = logicAddress;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getHiAuthPassword() {
        return this.hiAuthPassword;
    }

    public void setHiAuthPassword(String hiAuthPassword) {
        this.hiAuthPassword = hiAuthPassword;
    }

    public String getLoAuthPassword() {
        return this.loAuthPassword;
    }

    public void setLoAuthPassword(String loAuthPassword) {
        this.loAuthPassword = loAuthPassword;
    }

    public String getDeptCode() {
        return this.deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }
}