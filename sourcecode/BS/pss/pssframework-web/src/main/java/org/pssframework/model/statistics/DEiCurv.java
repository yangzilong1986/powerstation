package org.pssframework.model.statistics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        用于放（15）分钟（日以内）数据（当前日的/或者前7日，可配）
 * 数据产生：由采集平台直接写上送数据进来；业务平台负责使用及转到历史表；
 *     
*/
public class DEiCurv implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9187568713882390716L;

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/** identifier field */
	private BigDecimal gpId;

	/** identifier field */
	private Date dataTime;

	/**
	 * @return the gpId
	 */
	public BigDecimal getGpId() {
		return gpId;
	}

	/**
	 * @param gpId the gpId to set
	 */
	public void setGpId(BigDecimal gpId) {
		this.gpId = gpId;
	}

	/**
	 * @return the dataTime
	 */
	public Date getDataTime() {
		return dataTime;
	}

	/**
	 * @param dataTime the dataTime to set
	 */
	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}

	/** nullable persistent field */
	private String assetNo;

	/** nullable persistent field */
	private String orgNo;

	/** persistent field */
	private String ddate;

	/** persistent field */
	private Date acceptTime;

	/** nullable persistent field */
	private BigDecimal totalTimes;

	/** nullable persistent field */
	private BigDecimal PActTotal;

	/** nullable persistent field */
	private BigDecimal PActSharp;

	/** nullable persistent field */
	private BigDecimal PActPeak;

	/** nullable persistent field */
	private BigDecimal PActLevel;

	/** nullable persistent field */
	private BigDecimal PActValley;

	/** nullable persistent field */
	private BigDecimal IActTotal;

	/** nullable persistent field */
	private BigDecimal IActSharp;

	/** nullable persistent field */
	private BigDecimal IActPeak;

	/** nullable persistent field */
	private BigDecimal IActLevel;

	/** nullable persistent field */
	private BigDecimal IActValley;

	/** nullable persistent field */
	private BigDecimal reactQ1;

	/** nullable persistent field */
	private BigDecimal reactQ2;

	/** nullable persistent field */
	private BigDecimal reactQ3;

	/** nullable persistent field */
	private BigDecimal reactQ4;

	/** nullable persistent field */
	private BigDecimal PReactTotal;

	/** nullable persistent field */
	private BigDecimal PReactSharp;

	/** nullable persistent field */
	private BigDecimal PReactPeak;

	/** nullable persistent field */
	private BigDecimal PReactLevel;

	/** nullable persistent field */
	private BigDecimal PReactValley;

	/** nullable persistent field */
	private BigDecimal IReactTotal;

	/** nullable persistent field */
	private BigDecimal IReactSharp;

	/** nullable persistent field */
	private String dataFlag;

	/** nullable persistent field */
	private String dataSource;

	/** nullable persistent field */
	private BigDecimal IReactValley;

	/** nullable persistent field */
	private BigDecimal IReactLevel;

	/** nullable persistent field */
	private BigDecimal IReactPeak;

	/** default constructor */
	public DEiCurv() {
	}

	public String getAssetNo() {
		return this.assetNo;
	}

	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getDdate() {
		return this.ddate;
	}

	public void setDdate(String ddate) {
		this.ddate = ddate;
	}

	public Date getAcceptTime() {
		return this.acceptTime;
	}

	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}

	public BigDecimal getTotalTimes() {
		return this.totalTimes;
	}

	public void setTotalTimes(BigDecimal totalTimes) {
		this.totalTimes = totalTimes;
	}

	public BigDecimal getPActTotal() {
		return this.PActTotal;
	}

	public void setPActTotal(BigDecimal PActTotal) {
		this.PActTotal = PActTotal;
	}

	public BigDecimal getPActSharp() {
		return this.PActSharp;
	}

	public void setPActSharp(BigDecimal PActSharp) {
		this.PActSharp = PActSharp;
	}

	public BigDecimal getPActPeak() {
		return this.PActPeak;
	}

	public void setPActPeak(BigDecimal PActPeak) {
		this.PActPeak = PActPeak;
	}

	public BigDecimal getPActLevel() {
		return this.PActLevel;
	}

	public void setPActLevel(BigDecimal PActLevel) {
		this.PActLevel = PActLevel;
	}

	public BigDecimal getPActValley() {
		return this.PActValley;
	}

	public void setPActValley(BigDecimal PActValley) {
		this.PActValley = PActValley;
	}

	public BigDecimal getIActTotal() {
		return this.IActTotal;
	}

	public void setIActTotal(BigDecimal IActTotal) {
		this.IActTotal = IActTotal;
	}

	public BigDecimal getIActSharp() {
		return this.IActSharp;
	}

	public void setIActSharp(BigDecimal IActSharp) {
		this.IActSharp = IActSharp;
	}

	public BigDecimal getIActPeak() {
		return this.IActPeak;
	}

	public void setIActPeak(BigDecimal IActPeak) {
		this.IActPeak = IActPeak;
	}

	public BigDecimal getIActLevel() {
		return this.IActLevel;
	}

	public void setIActLevel(BigDecimal IActLevel) {
		this.IActLevel = IActLevel;
	}

	public BigDecimal getIActValley() {
		return this.IActValley;
	}

	public void setIActValley(BigDecimal IActValley) {
		this.IActValley = IActValley;
	}

	public BigDecimal getReactQ1() {
		return this.reactQ1;
	}

	public void setReactQ1(BigDecimal reactQ1) {
		this.reactQ1 = reactQ1;
	}

	public BigDecimal getReactQ2() {
		return this.reactQ2;
	}

	public void setReactQ2(BigDecimal reactQ2) {
		this.reactQ2 = reactQ2;
	}

	public BigDecimal getReactQ3() {
		return this.reactQ3;
	}

	public void setReactQ3(BigDecimal reactQ3) {
		this.reactQ3 = reactQ3;
	}

	public BigDecimal getReactQ4() {
		return this.reactQ4;
	}

	public void setReactQ4(BigDecimal reactQ4) {
		this.reactQ4 = reactQ4;
	}

	public BigDecimal getPReactTotal() {
		return this.PReactTotal;
	}

	public void setPReactTotal(BigDecimal PReactTotal) {
		this.PReactTotal = PReactTotal;
	}

	public BigDecimal getPReactSharp() {
		return this.PReactSharp;
	}

	public void setPReactSharp(BigDecimal PReactSharp) {
		this.PReactSharp = PReactSharp;
	}

	public BigDecimal getPReactPeak() {
		return this.PReactPeak;
	}

	public void setPReactPeak(BigDecimal PReactPeak) {
		this.PReactPeak = PReactPeak;
	}

	public BigDecimal getPReactLevel() {
		return this.PReactLevel;
	}

	public void setPReactLevel(BigDecimal PReactLevel) {
		this.PReactLevel = PReactLevel;
	}

	public BigDecimal getPReactValley() {
		return this.PReactValley;
	}

	public void setPReactValley(BigDecimal PReactValley) {
		this.PReactValley = PReactValley;
	}

	public BigDecimal getIReactTotal() {
		return this.IReactTotal;
	}

	public void setIReactTotal(BigDecimal IReactTotal) {
		this.IReactTotal = IReactTotal;
	}

	public BigDecimal getIReactSharp() {
		return this.IReactSharp;
	}

	public void setIReactSharp(BigDecimal IReactSharp) {
		this.IReactSharp = IReactSharp;
	}

	public String getDataFlag() {
		return this.dataFlag;
	}

	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}

	public String getDataSource() {
		return this.dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public BigDecimal getIReactValley() {
		return this.IReactValley;
	}

	public void setIReactValley(BigDecimal IReactValley) {
		this.IReactValley = IReactValley;
	}

	public BigDecimal getIReactLevel() {
		return this.IReactLevel;
	}

	public void setIReactLevel(BigDecimal IReactLevel) {
		this.IReactLevel = IReactLevel;
	}

	public BigDecimal getIReactPeak() {
		return this.IReactPeak;
	}

	public void setIReactPeak(BigDecimal IReactPeak) {
		this.IReactPeak = IReactPeak;
	}

}
