package com.hzjbbis.fk.model;

public class MeasuredPoint {
    private static final String TYPE_METER = "01";
    private static final int DEFAULT_CT_PT = 1;
    private String rtuId;
    private String customerNo;
    private String tn;
    private String stationNo;
    private String stationType;
    private String atrAddress;
    private String atrPort;
    private String atrProtocol;
    private int ct = 1;

    private int pt = 1;
    private String dataSaveID;

    public boolean isMeterType() {
        return "01".equals(this.stationType);
    }

    public void setCtStr(String ctStr) {
        if (ctStr == null) this.ct = 1;
        else try {
            this.ct = Integer.parseInt(ctStr);
        } catch (Exception ex) {
            this.ct = 1;
        }
    }

    public void setPtStr(String ptStr) {
        if (ptStr == null) this.pt = 1;
        else try {
            this.pt = Integer.parseInt(ptStr);
        } catch (Exception ex) {
            this.pt = 1;
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[rtuId=").append(this.rtuId).append(", tn=").append(this.tn).append(", stationNo=").append(this.stationNo).append(", type=").append(this.stationType).append(", address=").append(this.atrAddress).append(", port=").append(this.atrPort).append(", protocol=").append(this.atrProtocol).append("]");
        return sb.toString();
    }

    public String getRtuId() {
        return this.rtuId;
    }

    public void setRtuId(String rtuId) {
        this.rtuId = rtuId;
    }

    public String getTn() {
        return this.tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }

    public String getAtrAddress() {
        return this.atrAddress;
    }

    public void setAtrAddress(String atrAddress) {
        this.atrAddress = atrAddress;
    }

    public String getAtrPort() {
        return this.atrPort;
    }

    public void setAtrPort(String atrPort) {
        this.atrPort = atrPort;
    }

    public String getAtrProtocol() {
        return this.atrProtocol;
    }

    public void setAtrProtocol(String atrProtocol) {
        this.atrProtocol = atrProtocol;
    }

    public String getCustomerNo() {
        return this.customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getDataSaveID() {
        return this.dataSaveID;
    }

    public void setDataSaveID(String dataSaveID) {
        this.dataSaveID = dataSaveID;
    }

    public String getStationType() {
        return this.stationType;
    }

    public void setStationType(String stationType) {
        this.stationType = stationType;
    }

    public String getStationNo() {
        return this.stationNo;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo;
    }

    public int getCt() {
        return this.ct;
    }

    public void setCt(int ct) {
        this.ct = ct;
    }

    public int getPt() {
        return this.pt;
    }

    public void setPt(int pt) {
        this.pt = pt;
    }
}