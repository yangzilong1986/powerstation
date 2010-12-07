package org.pssframework.model.statistics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author Nick
 * 
 */
public class ImbStatisDay implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2632857820862310860L;

    /** identifier field */
    private BigDecimal gpId;

    /** identifier field */
    private Date dataTime;

    /** nullable persistent field */
    private String assetNo;

    /** nullable persistent field */
    private Long orgId;

    /** nullable persistent field */
    private String orgNo;

    /** persistent field */
    private String ddate;

    /** persistent field */
    private Date acceptTime;

    /** nullable persistent field */
    private BigDecimal ctTimes;

    /** nullable persistent field */
    private BigDecimal ptTimes;

    /** nullable persistent field */
    private BigDecimal ecurOverImbalTime;

    /** nullable persistent field */
    private BigDecimal voltOverImbalTime;

    /** nullable persistent field */
    private BigDecimal ecurImbalMax;

    /** nullable persistent field */
    private String ecurImbalMaxTime;

    /** nullable persistent field */
    private BigDecimal voltImbalMax;

    /** nullable persistent field */
    private String voltImbalMaxTime;

    /** nullable persistent field */
    private String dataFlag;

    /** nullable persistent field */
    private String dataSource;

    /** default constructor */
    public ImbStatisDay() {
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public BigDecimal getGpId() {
        return gpId;
    }

    public void setGpId(BigDecimal gpId) {
        this.gpId = gpId;
    }

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public String getAssetNo() {
        return assetNo;
    }

    public void setAssetNo(String assetNo) {
        this.assetNo = assetNo;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getDdate() {
        return ddate;
    }

    public void setDdate(String ddate) {
        this.ddate = ddate;
    }

    public Date getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }

    public BigDecimal getCtTimes() {
        return ctTimes;
    }

    public void setCtTimes(BigDecimal ctTimes) {
        this.ctTimes = ctTimes;
    }

    public BigDecimal getPtTimes() {
        return ptTimes;
    }

    public void setPtTimes(BigDecimal ptTimes) {
        this.ptTimes = ptTimes;
    }

    public BigDecimal getEcurOverImbalTime() {
        return ecurOverImbalTime;
    }

    public void setEcurOverImbalTime(BigDecimal ecurOverImbalTime) {
        this.ecurOverImbalTime = ecurOverImbalTime;
    }

    public BigDecimal getVoltOverImbalTime() {
        return voltOverImbalTime;
    }

    public void setVoltOverImbalTime(BigDecimal voltOverImbalTime) {
        this.voltOverImbalTime = voltOverImbalTime;
    }

    public BigDecimal getEcurImbalMax() {
        return ecurImbalMax;
    }

    public void setEcurImbalMax(BigDecimal ecurImbalMax) {
        this.ecurImbalMax = ecurImbalMax;
    }

    public String getEcurImbalMaxTime() {
        return ecurImbalMaxTime;
    }

    public void setEcurImbalMaxTime(String ecurImbalMaxTime) {
        this.ecurImbalMaxTime = ecurImbalMaxTime;
    }

    public BigDecimal getVoltImbalMax() {
        return voltImbalMax;
    }

    public void setVoltImbalMax(BigDecimal voltImbalMax) {
        this.voltImbalMax = voltImbalMax;
    }

    public String getVoltImbalMaxTime() {
        return voltImbalMaxTime;
    }

    public void setVoltImbalMaxTime(String voltImbalMaxTime) {
        this.voltImbalMaxTime = voltImbalMaxTime;
    }

    public String getDataFlag() {
        return dataFlag;
    }

    public void setDataFlag(String dataFlag) {
        this.dataFlag = dataFlag;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }
}
