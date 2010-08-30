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
public class PfCruv implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3809389862669039227L;

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
    private BigDecimal totalTimes;

    /** nullable persistent field */
    private BigDecimal powerFactor;

    /** nullable persistent field */
    private BigDecimal powerFactorA;

    /** nullable persistent field */
    private BigDecimal powerFactorB;

    /** nullable persistent field */
    private BigDecimal powerFactorC;

    /** nullable persistent field */
    private String dataFlag;

    /** nullable persistent field */
    private String dataSource;

    /** default constructor */
    public PfCruv() {
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

    public BigDecimal getTotalTimes() {
        return totalTimes;
    }

    public void setTotalTimes(BigDecimal totalTimes) {
        this.totalTimes = totalTimes;
    }

    public BigDecimal getPowerFactor() {
        return powerFactor;
    }

    public void setPowerFactor(BigDecimal powerFactor) {
        this.powerFactor = powerFactor;
    }

    public BigDecimal getPowerFactorA() {
        return powerFactorA;
    }

    public void setPowerFactorA(BigDecimal powerFactorA) {
        this.powerFactorA = powerFactorA;
    }

    public BigDecimal getPowerFactorB() {
        return powerFactorB;
    }

    public void setPowerFactorB(BigDecimal powerFactorB) {
        this.powerFactorB = powerFactorB;
    }

    public BigDecimal getPowerFactorC() {
        return powerFactorC;
    }

    public void setPowerFactorC(BigDecimal powerFactorC) {
        this.powerFactorC = powerFactorC;
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
