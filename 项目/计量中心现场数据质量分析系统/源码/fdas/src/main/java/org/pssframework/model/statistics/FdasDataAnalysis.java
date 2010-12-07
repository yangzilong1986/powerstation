package org.pssframework.model.statistics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class FdasDataAnalysis implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 8362064822712230666L;

    private String dbjh;
    private int xiangxian;
    private Date jyrq;
    private String jysbbm;
    private BigDecimal axdl;
    private BigDecimal bxdl;
    private BigDecimal cxdl;
    private BigDecimal glys;
    private BigDecimal pjwc;
    private BigDecimal njwc;

    public String getDbjh() {
        return dbjh;
    }

    public void setDbjh(String dbjh) {
        this.dbjh = dbjh;
    }

    public int getXiangxian() {
        return xiangxian;
    }

    public void setXiangxian(int xiangxian) {
        this.xiangxian = xiangxian;
    }

    public Date getJyrq() {
        return jyrq;
    }

    public void setJyrq(Date jyrq) {
        this.jyrq = jyrq;
    }

    public String getJysbbm() {
        return jysbbm;
    }

    public void setJysbbm(String jysbbm) {
        this.jysbbm = jysbbm;
    }

    public BigDecimal getAxdl() {
        return axdl;
    }

    public void setAxdl(BigDecimal axdl) {
        this.axdl = axdl;
    }

    public BigDecimal getBxdl() {
        return bxdl;
    }

    public void setBxdl(BigDecimal bxdl) {
        this.bxdl = bxdl;
    }

    public BigDecimal getCxdl() {
        return cxdl;
    }

    public void setCxdl(BigDecimal cxdl) {
        this.cxdl = cxdl;
    }

    public BigDecimal getGlys() {
        return glys;
    }

    public void setGlys(BigDecimal glys) {
        this.glys = glys;
    }

    public BigDecimal getPjwc() {
        return pjwc;
    }

    public void setPjwc(BigDecimal pjwc) {
        this.pjwc = pjwc;
    }

    public BigDecimal getNjwc() {
        return njwc;
    }

    public void setNjwc(BigDecimal njwc) {
        this.njwc = njwc;
    }
}
