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
public class VoltStatisDay implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1104724755394350670L;

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
    private BigDecimal voltPeakA;

    /** nullable persistent field */
    private String voltPeakATime;

    /** nullable persistent field */
    private BigDecimal voltValleyA;

    /** nullable persistent field */
    private String voltValleyATime;

    /** nullable persistent field */
    private BigDecimal voltPeakB;

    /** nullable persistent field */
    private String voltPeakBTime;

    /** nullable persistent field */
    private BigDecimal voltValleyB;

    /** nullable persistent field */
    private String voltValleyBTime;

    /** nullable persistent field */
    private BigDecimal voltPeakC;

    /** nullable persistent field */
    private String voltPeakCTime;

    /** nullable persistent field */
    private BigDecimal voltValleyC;

    /** nullable persistent field */
    private String voltValleyCTime;

    /** nullable persistent field */
    private BigDecimal voltOverUplimitTimeA;

    /** nullable persistent field */
    private BigDecimal voltOverUpuplimitTimeA;

    /** nullable persistent field */
    private BigDecimal voltOverDownlimitTimeA;

    /** nullable persistent field */
    private BigDecimal voltOverDowndownlimitTimeA;

    /** nullable persistent field */
    private BigDecimal voltQualifyTimeA;

    /** nullable persistent field */
    private BigDecimal voltOverUplimitTimeB;

    /** nullable persistent field */
    private BigDecimal voltOverUpuplimitTimeB;

    /** nullable persistent field */
    private BigDecimal voltOverDownlimitTimeB;

    /** nullable persistent field */
    private BigDecimal voltOverDowndownlimitTimeB;

    /** nullable persistent field */
    private BigDecimal voltQualifyTimeB;

    /** nullable persistent field */
    private BigDecimal voltOverUplimitTimeC;

    /** nullable persistent field */
    private BigDecimal voltOverUpuplimitTimeC;

    /** nullable persistent field */
    private BigDecimal voltOverDownlimitTimeC;

    /** nullable persistent field */
    private BigDecimal voltOverDowndownlimitTimeC;

    /** nullable persistent field */
    private BigDecimal voltQualifyTimeC;

    /** nullable persistent field */
    private BigDecimal averageVoltA;

    /** nullable persistent field */
    private BigDecimal averageVoltB;

    /** nullable persistent field */
    private BigDecimal averageVoltC;

    /** nullable persistent field */
    private BigDecimal voltMonitorTimeA;

    /** nullable persistent field */
    private BigDecimal voltQualifyRatA;

    /** nullable persistent field */
    private BigDecimal voltOverUplimitRatA;

    /** nullable persistent field */
    private BigDecimal voltOverDownlimitRatA;

    /** nullable persistent field */
    private BigDecimal voltMonitorTimeB;

    /** nullable persistent field */
    private BigDecimal voltQualifyRatB;

    /** nullable persistent field */
    private BigDecimal voltOverUplimitRatB;

    /** nullable persistent field */
    private BigDecimal voltOverDownlimitRatB;

    /** nullable persistent field */
    private BigDecimal voltMonitorTimeC;

    /** nullable persistent field */
    private BigDecimal voltQualifyRatC;

    /** nullable persistent field */
    private BigDecimal voltOverUplimitRatC;

    /** nullable persistent field */
    private BigDecimal voltOverDownlimitRatC;

    /** nullable persistent field */
    private BigDecimal voltMonitorTime;

    /** nullable persistent field */
    private BigDecimal voltOverDownlimitCnt;

    /** nullable persistent field */
    private BigDecimal voltOverUplimitTime;

    /** nullable persistent field */
    private BigDecimal voltOverDownlimitTime;

    /** nullable persistent field */
    private BigDecimal voltQualifyRat;

    /** nullable persistent field */
    private BigDecimal voltOverUplimitRat;

    /** nullable persistent field */
    private BigDecimal voltOverDownlimitRat;

    /** nullable persistent field */
    private BigDecimal voltOverDownlimitCntA;

    /** nullable persistent field */
    private BigDecimal voltOverDownlimitCntB;

    /** nullable persistent field */
    private BigDecimal voltOverDownlimitCntC;

    /** nullable persistent field */
    private String dataFlag;

    /** nullable persistent field */
    private String dataSource;

    /** default constructor */
    public VoltStatisDay() {
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

    public BigDecimal getVoltPeakA() {
        return voltPeakA;
    }

    public void setVoltPeakA(BigDecimal voltPeakA) {
        this.voltPeakA = voltPeakA;
    }

    public String getVoltPeakATime() {
        return voltPeakATime;
    }

    public void setVoltPeakATime(String voltPeakATime) {
        this.voltPeakATime = voltPeakATime;
    }

    public BigDecimal getVoltValleyA() {
        return voltValleyA;
    }

    public void setVoltValleyA(BigDecimal voltValleyA) {
        this.voltValleyA = voltValleyA;
    }

    public String getVoltValleyATime() {
        return voltValleyATime;
    }

    public void setVoltValleyATime(String voltValleyATime) {
        this.voltValleyATime = voltValleyATime;
    }

    public BigDecimal getVoltPeakB() {
        return voltPeakB;
    }

    public void setVoltPeakB(BigDecimal voltPeakB) {
        this.voltPeakB = voltPeakB;
    }

    public String getVoltPeakBTime() {
        return voltPeakBTime;
    }

    public void setVoltPeakBTime(String voltPeakBTime) {
        this.voltPeakBTime = voltPeakBTime;
    }

    public BigDecimal getVoltValleyB() {
        return voltValleyB;
    }

    public void setVoltValleyB(BigDecimal voltValleyB) {
        this.voltValleyB = voltValleyB;
    }

    public String getVoltValleyBTime() {
        return voltValleyBTime;
    }

    public void setVoltValleyBTime(String voltValleyBTime) {
        this.voltValleyBTime = voltValleyBTime;
    }

    public BigDecimal getVoltPeakC() {
        return voltPeakC;
    }

    public void setVoltPeakC(BigDecimal voltPeakC) {
        this.voltPeakC = voltPeakC;
    }

    public String getVoltPeakCTime() {
        return voltPeakCTime;
    }

    public void setVoltPeakCTime(String voltPeakCTime) {
        this.voltPeakCTime = voltPeakCTime;
    }

    public BigDecimal getVoltValleyC() {
        return voltValleyC;
    }

    public void setVoltValleyC(BigDecimal voltValleyC) {
        this.voltValleyC = voltValleyC;
    }

    public String getVoltValleyCTime() {
        return voltValleyCTime;
    }

    public void setVoltValleyCTime(String voltValleyCTime) {
        this.voltValleyCTime = voltValleyCTime;
    }

    public BigDecimal getVoltOverUplimitTimeA() {
        return voltOverUplimitTimeA;
    }

    public void setVoltOverUplimitTimeA(BigDecimal voltOverUplimitTimeA) {
        this.voltOverUplimitTimeA = voltOverUplimitTimeA;
    }

    public BigDecimal getVoltOverUpuplimitTimeA() {
        return voltOverUpuplimitTimeA;
    }

    public void setVoltOverUpuplimitTimeA(BigDecimal voltOverUpuplimitTimeA) {
        this.voltOverUpuplimitTimeA = voltOverUpuplimitTimeA;
    }

    public BigDecimal getVoltOverDownlimitTimeA() {
        return voltOverDownlimitTimeA;
    }

    public void setVoltOverDownlimitTimeA(BigDecimal voltOverDownlimitTimeA) {
        this.voltOverDownlimitTimeA = voltOverDownlimitTimeA;
    }

    public BigDecimal getVoltOverDowndownlimitTimeA() {
        return voltOverDowndownlimitTimeA;
    }

    public void setVoltOverDowndownlimitTimeA(BigDecimal voltOverDowndownlimitTimeA) {
        this.voltOverDowndownlimitTimeA = voltOverDowndownlimitTimeA;
    }

    public BigDecimal getVoltQualifyTimeA() {
        return voltQualifyTimeA;
    }

    public void setVoltQualifyTimeA(BigDecimal voltQualifyTimeA) {
        this.voltQualifyTimeA = voltQualifyTimeA;
    }

    public BigDecimal getVoltOverUplimitTimeB() {
        return voltOverUplimitTimeB;
    }

    public void setVoltOverUplimitTimeB(BigDecimal voltOverUplimitTimeB) {
        this.voltOverUplimitTimeB = voltOverUplimitTimeB;
    }

    public BigDecimal getVoltOverUpuplimitTimeB() {
        return voltOverUpuplimitTimeB;
    }

    public void setVoltOverUpuplimitTimeB(BigDecimal voltOverUpuplimitTimeB) {
        this.voltOverUpuplimitTimeB = voltOverUpuplimitTimeB;
    }

    public BigDecimal getVoltOverDownlimitTimeB() {
        return voltOverDownlimitTimeB;
    }

    public void setVoltOverDownlimitTimeB(BigDecimal voltOverDownlimitTimeB) {
        this.voltOverDownlimitTimeB = voltOverDownlimitTimeB;
    }

    public BigDecimal getVoltOverDowndownlimitTimeB() {
        return voltOverDowndownlimitTimeB;
    }

    public void setVoltOverDowndownlimitTimeB(BigDecimal voltOverDowndownlimitTimeB) {
        this.voltOverDowndownlimitTimeB = voltOverDowndownlimitTimeB;
    }

    public BigDecimal getVoltQualifyTimeB() {
        return voltQualifyTimeB;
    }

    public void setVoltQualifyTimeB(BigDecimal voltQualifyTimeB) {
        this.voltQualifyTimeB = voltQualifyTimeB;
    }

    public BigDecimal getVoltOverUplimitTimeC() {
        return voltOverUplimitTimeC;
    }

    public void setVoltOverUplimitTimeC(BigDecimal voltOverUplimitTimeC) {
        this.voltOverUplimitTimeC = voltOverUplimitTimeC;
    }

    public BigDecimal getVoltOverUpuplimitTimeC() {
        return voltOverUpuplimitTimeC;
    }

    public void setVoltOverUpuplimitTimeC(BigDecimal voltOverUpuplimitTimeC) {
        this.voltOverUpuplimitTimeC = voltOverUpuplimitTimeC;
    }

    public BigDecimal getVoltOverDownlimitTimeC() {
        return voltOverDownlimitTimeC;
    }

    public void setVoltOverDownlimitTimeC(BigDecimal voltOverDownlimitTimeC) {
        this.voltOverDownlimitTimeC = voltOverDownlimitTimeC;
    }

    public BigDecimal getVoltOverDowndownlimitTimeC() {
        return voltOverDowndownlimitTimeC;
    }

    public void setVoltOverDowndownlimitTimeC(BigDecimal voltOverDowndownlimitTimeC) {
        this.voltOverDowndownlimitTimeC = voltOverDowndownlimitTimeC;
    }

    public BigDecimal getVoltQualifyTimeC() {
        return voltQualifyTimeC;
    }

    public void setVoltQualifyTimeC(BigDecimal voltQualifyTimeC) {
        this.voltQualifyTimeC = voltQualifyTimeC;
    }

    public BigDecimal getAverageVoltA() {
        return averageVoltA;
    }

    public void setAverageVoltA(BigDecimal averageVoltA) {
        this.averageVoltA = averageVoltA;
    }

    public BigDecimal getAverageVoltB() {
        return averageVoltB;
    }

    public void setAverageVoltB(BigDecimal averageVoltB) {
        this.averageVoltB = averageVoltB;
    }

    public BigDecimal getAverageVoltC() {
        return averageVoltC;
    }

    public void setAverageVoltC(BigDecimal averageVoltC) {
        this.averageVoltC = averageVoltC;
    }

    public BigDecimal getVoltMonitorTimeA() {
        return voltMonitorTimeA;
    }

    public void setVoltMonitorTimeA(BigDecimal voltMonitorTimeA) {
        this.voltMonitorTimeA = voltMonitorTimeA;
    }

    public BigDecimal getVoltQualifyRatA() {
        return voltQualifyRatA;
    }

    public void setVoltQualifyRatA(BigDecimal voltQualifyRatA) {
        this.voltQualifyRatA = voltQualifyRatA;
    }

    public BigDecimal getVoltOverUplimitRatA() {
        return voltOverUplimitRatA;
    }

    public void setVoltOverUplimitRatA(BigDecimal voltOverUplimitRatA) {
        this.voltOverUplimitRatA = voltOverUplimitRatA;
    }

    public BigDecimal getVoltOverDownlimitRatA() {
        return voltOverDownlimitRatA;
    }

    public void setVoltOverDownlimitRatA(BigDecimal voltOverDownlimitRatA) {
        this.voltOverDownlimitRatA = voltOverDownlimitRatA;
    }

    public BigDecimal getVoltMonitorTimeB() {
        return voltMonitorTimeB;
    }

    public void setVoltMonitorTimeB(BigDecimal voltMonitorTimeB) {
        this.voltMonitorTimeB = voltMonitorTimeB;
    }

    public BigDecimal getVoltQualifyRatB() {
        return voltQualifyRatB;
    }

    public void setVoltQualifyRatB(BigDecimal voltQualifyRatB) {
        this.voltQualifyRatB = voltQualifyRatB;
    }

    public BigDecimal getVoltOverUplimitRatB() {
        return voltOverUplimitRatB;
    }

    public void setVoltOverUplimitRatB(BigDecimal voltOverUplimitRatB) {
        this.voltOverUplimitRatB = voltOverUplimitRatB;
    }

    public BigDecimal getVoltOverDownlimitRatB() {
        return voltOverDownlimitRatB;
    }

    public void setVoltOverDownlimitRatB(BigDecimal voltOverDownlimitRatB) {
        this.voltOverDownlimitRatB = voltOverDownlimitRatB;
    }

    public BigDecimal getVoltMonitorTimeC() {
        return voltMonitorTimeC;
    }

    public void setVoltMonitorTimeC(BigDecimal voltMonitorTimeC) {
        this.voltMonitorTimeC = voltMonitorTimeC;
    }

    public BigDecimal getVoltQualifyRatC() {
        return voltQualifyRatC;
    }

    public void setVoltQualifyRatC(BigDecimal voltQualifyRatC) {
        this.voltQualifyRatC = voltQualifyRatC;
    }

    public BigDecimal getVoltOverUplimitRatC() {
        return voltOverUplimitRatC;
    }

    public void setVoltOverUplimitRatC(BigDecimal voltOverUplimitRatC) {
        this.voltOverUplimitRatC = voltOverUplimitRatC;
    }

    public BigDecimal getVoltOverDownlimitRatC() {
        return voltOverDownlimitRatC;
    }

    public void setVoltOverDownlimitRatC(BigDecimal voltOverDownlimitRatC) {
        this.voltOverDownlimitRatC = voltOverDownlimitRatC;
    }

    public BigDecimal getVoltMonitorTime() {
        return voltMonitorTime;
    }

    public void setVoltMonitorTime(BigDecimal voltMonitorTime) {
        this.voltMonitorTime = voltMonitorTime;
    }

    public BigDecimal getVoltOverDownlimitCnt() {
        return voltOverDownlimitCnt;
    }

    public void setVoltOverDownlimitCnt(BigDecimal voltOverDownlimitCnt) {
        this.voltOverDownlimitCnt = voltOverDownlimitCnt;
    }

    public BigDecimal getVoltOverUplimitTime() {
        return voltOverUplimitTime;
    }

    public void setVoltOverUplimitTime(BigDecimal voltOverUplimitTime) {
        this.voltOverUplimitTime = voltOverUplimitTime;
    }

    public BigDecimal getVoltOverDownlimitTime() {
        return voltOverDownlimitTime;
    }

    public void setVoltOverDownlimitTime(BigDecimal voltOverDownlimitTime) {
        this.voltOverDownlimitTime = voltOverDownlimitTime;
    }

    public BigDecimal getVoltQualifyRat() {
        return voltQualifyRat;
    }

    public void setVoltQualifyRat(BigDecimal voltQualifyRat) {
        this.voltQualifyRat = voltQualifyRat;
    }

    public BigDecimal getVoltOverUplimitRat() {
        return voltOverUplimitRat;
    }

    public void setVoltOverUplimitRat(BigDecimal voltOverUplimitRat) {
        this.voltOverUplimitRat = voltOverUplimitRat;
    }

    public BigDecimal getVoltOverDownlimitRat() {
        return voltOverDownlimitRat;
    }

    public void setVoltOverDownlimitRat(BigDecimal voltOverDownlimitRat) {
        this.voltOverDownlimitRat = voltOverDownlimitRat;
    }

    public BigDecimal getVoltOverDownlimitCntA() {
        return voltOverDownlimitCntA;
    }

    public void setVoltOverDownlimitCntA(BigDecimal voltOverDownlimitCntA) {
        this.voltOverDownlimitCntA = voltOverDownlimitCntA;
    }

    public BigDecimal getVoltOverDownlimitCntB() {
        return voltOverDownlimitCntB;
    }

    public void setVoltOverDownlimitCntB(BigDecimal voltOverDownlimitCntB) {
        this.voltOverDownlimitCntB = voltOverDownlimitCntB;
    }

    public BigDecimal getVoltOverDownlimitCntC() {
        return voltOverDownlimitCntC;
    }

    public void setVoltOverDownlimitCntC(BigDecimal voltOverDownlimitCntC) {
        this.voltOverDownlimitCntC = voltOverDownlimitCntC;
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
