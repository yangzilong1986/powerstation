package com.hisun.term.atc;

public class HiTermInfo {
    private String TERM_ID;
    private String CUST_ID;
    private String OBJECT_TYPE;
    private String ORG_NO;
    private String ASSET_NO;
    private String LOGICAL_ADDR;
    private String RUN_STATUS;
    private String CUR_STATUS;
    private String SIM_NO;
    private String TERM_TYPE;
    private String WIRING_MODE;
    private String MODEL_CODE;
    private String LEAVT_FAC_NO;
    private String BATCH_ID;
    private String MADE_FAC;
    private String LEAVE_FAC_DATE;
    private String INSTALL_DATE;
    private String COMM_DODE;
    private String CHANNEL_TYPE;
    private String PROTOCOL_NO;
    private String PR;
    private String ISAC;
    private String PHYSICS_ADDR;
    private String MACH_NO;
    private String FEP_CNL;
    private String BEAT_INTV;
    private String OBJ_ADDR;

    public String getCUST_ID() {
        return this.CUST_ID;
    }

    public void setCUST_ID(String cust_id) {
        this.CUST_ID = cust_id;
    }

    public String getOBJECT_TYPE() {
        return this.OBJECT_TYPE;
    }

    public void setOBJECT_TYPE(String object_type) {
        this.OBJECT_TYPE = object_type;
    }

    public String getORG_NO() {
        return this.ORG_NO;
    }

    public void setORG_NO(String org_code) {
        this.ORG_NO = org_code;
    }

    public String getASSET_NO() {
        return this.ASSET_NO;
    }

    public void setASSET_NO(String asset_no) {
        this.ASSET_NO = asset_no;
    }

    public String getLOGICAL_ADDR() {
        return this.LOGICAL_ADDR;
    }

    public void setLOGICAL_ADDR(String logical_addr) {
        this.LOGICAL_ADDR = logical_addr;
    }

    public String getRUN_STATUS() {
        return this.RUN_STATUS;
    }

    public void setRUN_STATUS(String run_status) {
        this.RUN_STATUS = run_status;
    }

    public String getCUR_STATUS() {
        return this.CUR_STATUS;
    }

    public void setCUR_STATUS(String cur_status) {
        this.CUR_STATUS = cur_status;
    }

    public String getSIM_NO() {
        return this.SIM_NO;
    }

    public void setSIM_NO(String sim_no) {
        this.SIM_NO = sim_no;
    }

    public String getTERM_TYPE() {
        return this.TERM_TYPE;
    }

    public void setTERM_TYPE(String term_type) {
        this.TERM_TYPE = term_type;
    }

    public String getWIRING_MODE() {
        return this.WIRING_MODE;
    }

    public void setWIRING_MODE(String wiring_mode) {
        this.WIRING_MODE = wiring_mode;
    }

    public String getMODEL_CODE() {
        return this.MODEL_CODE;
    }

    public void setMODEL_CODE(String model_code) {
        this.MODEL_CODE = model_code;
    }

    public String getLEAVT_FAC_NO() {
        return this.LEAVT_FAC_NO;
    }

    public void setLEAVT_FAC_NO(String leavt_fac_no) {
        this.LEAVT_FAC_NO = leavt_fac_no;
    }

    public String getBATCH_ID() {
        return this.BATCH_ID;
    }

    public void setBATCH_ID(String batch_id) {
        this.BATCH_ID = batch_id;
    }

    public String getMADE_FAC() {
        return this.MADE_FAC;
    }

    public void setMADE_FAC(String made_fac) {
        this.MADE_FAC = made_fac;
    }

    public String getLEAVE_FAC_DATE() {
        return this.LEAVE_FAC_DATE;
    }

    public void setLEAVE_FAC_DATE(String leave_fac_date) {
        this.LEAVE_FAC_DATE = leave_fac_date;
    }

    public String getINSTALL_DATE() {
        return this.INSTALL_DATE;
    }

    public void setINSTALL_DATE(String install_date) {
        this.INSTALL_DATE = install_date;
    }

    public String getCOMM_DODE() {
        return this.COMM_DODE;
    }

    public void setCOMM_DODE(String comm_dode) {
        this.COMM_DODE = comm_dode;
    }

    public String getCHANNEL_TYPE() {
        return this.CHANNEL_TYPE;
    }

    public void setCHANNEL_TYPE(String channel_type) {
        this.CHANNEL_TYPE = channel_type;
    }

    public String getPROTOCOL_NO() {
        return this.PROTOCOL_NO;
    }

    public void setPROTOCOL_NO(String protocol_no) {
        this.PROTOCOL_NO = protocol_no;
    }

    public String getPR() {
        return this.PR;
    }

    public void setPR(String pr) {
        this.PR = pr;
    }

    public String getISAC() {
        return this.ISAC;
    }

    public void setISAC(String isac) {
        this.ISAC = isac;
    }

    public String getPHYSICS_ADDR() {
        return this.PHYSICS_ADDR;
    }

    public void setPHYSICS_ADDR(String physics_addr) {
        this.PHYSICS_ADDR = physics_addr;
    }

    public String getTERM_ID() {
        return this.TERM_ID;
    }

    public void setTERM_ID(String term_id) {
        this.TERM_ID = term_id;
    }

    public String toString() {
        return this.TERM_ID;
    }

    public String getFEP_CNL() {
        return this.FEP_CNL;
    }

    public void setFEP_CNL(String fep_cnl) {
        this.FEP_CNL = fep_cnl;
    }

    public String getMACH_NO() {
        return this.MACH_NO;
    }

    public void setMACH_NO(String mach_no) {
        this.MACH_NO = mach_no;
    }

    public String getBEAT_INTV() {
        return this.BEAT_INTV;
    }

    public void setBEAT_INTV(String beat_intv) {
        this.BEAT_INTV = beat_intv;
    }

    public int getTermBeatInterval() {
        if ((this.BEAT_INTV == null) || (this.BEAT_INTV.trim().length() == 0)) return -1;
        try {
            return Integer.parseInt(this.BEAT_INTV);
        } catch (Exception e) {
        }
        return -1;
    }

    public String getOBJ_ADDR() {
        return this.OBJ_ADDR;
    }

    public void setOBJ_ADDR(String obj_addr) {
        if ((obj_addr == null) || (obj_addr.length() == 0)) this.OBJ_ADDR = null;
        else this.OBJ_ADDR = obj_addr;
    }
}