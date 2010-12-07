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
public class EcurStatisDay implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2796097718178105448L;

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
    private BigDecimal ecurPeakA;

    /** nullable persistent field */
    private String ecurPeakATime;

    /** nullable persistent field */
    private BigDecimal ecurPeakB;

    /** nullable persistent field */
    private String ecurPeakBTime;

    /** nullable persistent field */
    private BigDecimal ecurPeakC;

    /** nullable persistent field */
    private String ecurPeakCTime;

    /** nullable persistent field */
    private BigDecimal ecurPeakO;

    /** nullable persistent field */
    private String ecurPeakOTime;

    /** nullable persistent field */
    private BigDecimal ecurOverUplimitTimeA;

    /** nullable persistent field */
    private BigDecimal ecurOverUpuplimitTimeA;

    /** nullable persistent field */
    private BigDecimal ecurOverUplimitTimeB;

    /** nullable persistent field */
    private BigDecimal ecurOverUpuplimitTimeB;

    /** nullable persistent field */
    private BigDecimal ecurOverUplimitTimeC;

    /** nullable persistent field */
    private BigDecimal ecurOverUpuplimitTimeC;

    /** nullable persistent field */
    private BigDecimal ecurOverUplimitTimeO;

    /** nullable persistent field */
    private BigDecimal ecurOverUpuplimitTimeO;

    /** nullable persistent field */
    private BigDecimal ecurOverImbalTime;

    /** nullable persistent field */
    private String dataFlag;

    /** nullable persistent field */
    private String dataSource;

    /** default constructor */
    public EcurStatisDay() {
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

    public BigDecimal getEcurPeakA() {
        return ecurPeakA;
    }

    public void setEcurPeakA(BigDecimal ecurPeakA) {
        this.ecurPeakA = ecurPeakA;
    }

    public String getEcurPeakATime() {
        return ecurPeakATime;
    }

    public void setEcurPeakATime(String ecurPeakATime) {
        this.ecurPeakATime = ecurPeakATime;
    }

    public BigDecimal getEcurPeakB() {
        return ecurPeakB;
    }

    public void setEcurPeakB(BigDecimal ecurPeakB) {
        this.ecurPeakB = ecurPeakB;
    }

    public String getEcurPeakBTime() {
        return ecurPeakBTime;
    }

    public void setEcurPeakBTime(String ecurPeakBTime) {
        this.ecurPeakBTime = ecurPeakBTime;
    }

    public BigDecimal getEcurPeakC() {
        return ecurPeakC;
    }

    public void setEcurPeakC(BigDecimal ecurPeakC) {
        this.ecurPeakC = ecurPeakC;
    }

    public String getEcurPeakCTime() {
        return ecurPeakCTime;
    }

    public void setEcurPeakCTime(String ecurPeakCTime) {
        this.ecurPeakCTime = ecurPeakCTime;
    }

    public BigDecimal getEcurPeakO() {
        return ecurPeakO;
    }

    public void setEcurPeakO(BigDecimal ecurPeakO) {
        this.ecurPeakO = ecurPeakO;
    }

    public String getEcurPeakOTime() {
        return ecurPeakOTime;
    }

    public void setEcurPeakOTime(String ecurPeakOTime) {
        this.ecurPeakOTime = ecurPeakOTime;
    }

    public BigDecimal getEcurOverUplimitTimeA() {
        return ecurOverUplimitTimeA;
    }

    public void setEcurOverUplimitTimeA(BigDecimal ecurOverUplimitTimeA) {
        this.ecurOverUplimitTimeA = ecurOverUplimitTimeA;
    }

    public BigDecimal getEcurOverUpuplimitTimeA() {
        return ecurOverUpuplimitTimeA;
    }

    public void setEcurOverUpuplimitTimeA(BigDecimal ecurOverUpuplimitTimeA) {
        this.ecurOverUpuplimitTimeA = ecurOverUpuplimitTimeA;
    }

    public BigDecimal getEcurOverUplimitTimeB() {
        return ecurOverUplimitTimeB;
    }

    public void setEcurOverUplimitTimeB(BigDecimal ecurOverUplimitTimeB) {
        this.ecurOverUplimitTimeB = ecurOverUplimitTimeB;
    }

    public BigDecimal getEcurOverUpuplimitTimeB() {
        return ecurOverUpuplimitTimeB;
    }

    public void setEcurOverUpuplimitTimeB(BigDecimal ecurOverUpuplimitTimeB) {
        this.ecurOverUpuplimitTimeB = ecurOverUpuplimitTimeB;
    }

    public BigDecimal getEcurOverUplimitTimeC() {
        return ecurOverUplimitTimeC;
    }

    public void setEcurOverUplimitTimeC(BigDecimal ecurOverUplimitTimeC) {
        this.ecurOverUplimitTimeC = ecurOverUplimitTimeC;
    }

    public BigDecimal getEcurOverUpuplimitTimeC() {
        return ecurOverUpuplimitTimeC;
    }

    public void setEcurOverUpuplimitTimeC(BigDecimal ecurOverUpuplimitTimeC) {
        this.ecurOverUpuplimitTimeC = ecurOverUpuplimitTimeC;
    }

    public BigDecimal getEcurOverUplimitTimeO() {
        return ecurOverUplimitTimeO;
    }

    public void setEcurOverUplimitTimeO(BigDecimal ecurOverUplimitTimeO) {
        this.ecurOverUplimitTimeO = ecurOverUplimitTimeO;
    }

    public BigDecimal getEcurOverUpuplimitTimeO() {
        return ecurOverUpuplimitTimeO;
    }

    public void setEcurOverUpuplimitTimeO(BigDecimal ecurOverUpuplimitTimeO) {
        this.ecurOverUpuplimitTimeO = ecurOverUpuplimitTimeO;
    }

    public BigDecimal getEcurOverImbalTime() {
        return ecurOverImbalTime;
    }

    public void setEcurOverImbalTime(BigDecimal ecurOverImbalTime) {
        this.ecurOverImbalTime = ecurOverImbalTime;
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
