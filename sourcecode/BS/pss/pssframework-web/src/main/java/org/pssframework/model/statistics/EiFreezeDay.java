package org.pssframework.model.statistics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 * 用于放冻结数据（日冻结，保留7天或者1月，可配），电量数据按年自动归档 。
 * 数据产生：由采集平台直接写上送数据进来；业务平台负责使用及转到历史表；
 *     
*/
public class EiFreezeDay implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -605631433879538917L;

	/** identifier field */
	private BigDecimal gpId;

	/** identifier field */
	private Date dataTime;

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
	private BigDecimal IReactValley;

	/** nullable persistent field */
	private BigDecimal IReactLevel;

	/** nullable persistent field */
	private BigDecimal IReactPeak;

	/** nullable persistent field */
	private BigDecimal PActMaxDemand;

	/** nullable persistent field */
	private Date PActMaxDemandTime;

	/** nullable persistent field */
	private BigDecimal IActMaxDemand;

	/** nullable persistent field */
	private Date IActMaxDemandTime;

	/** nullable persistent field */
	private BigDecimal PReactMaxDemand;

	/** nullable persistent field */
	private Date PReactMaxDemandTime;

	/** nullable persistent field */
	private BigDecimal IReactMaxDemand;

	/** nullable persistent field */
	private Date IReactMaxDemandTime;

	/** nullable persistent field */
	private String dataFlag;

	/** nullable persistent field */
	private String dataSource;

	/** default constructor */
	public EiFreezeDay() {
	}

	public Date getAcceptTime() {
		return this.acceptTime;
	}

	public String getAssetNo() {
		return this.assetNo;
	}

	public String getDataFlag() {
		return this.dataFlag;
	}

	public String getDataSource() {
		return this.dataSource;
	}

	public Date getDataTime() {
		return this.dataTime;
	}

	public String getDdate() {
		return this.ddate;
	}

	public BigDecimal getGpId() {
		return this.gpId;
	}

	public BigDecimal getIActLevel() {
		return this.IActLevel;
	}

	public BigDecimal getIActMaxDemand() {
		return this.IActMaxDemand;
	}

	public Date getIActMaxDemandTime() {
		return this.IActMaxDemandTime;
	}

	public BigDecimal getIActPeak() {
		return this.IActPeak;
	}

	public BigDecimal getIActSharp() {
		return this.IActSharp;
	}

	public BigDecimal getIActTotal() {
		return this.IActTotal;
	}

	public BigDecimal getIActValley() {
		return this.IActValley;
	}

	public BigDecimal getIReactLevel() {
		return this.IReactLevel;
	}

	public BigDecimal getIReactMaxDemand() {
		return this.IReactMaxDemand;
	}

	public Date getIReactMaxDemandTime() {
		return this.IReactMaxDemandTime;
	}

	public BigDecimal getIReactPeak() {
		return this.IReactPeak;
	}

	public BigDecimal getIReactSharp() {
		return this.IReactSharp;
	}

	public BigDecimal getIReactTotal() {
		return this.IReactTotal;
	}

	public BigDecimal getIReactValley() {
		return this.IReactValley;
	}

	public String getOrgNo() {
		return this.orgNo;
	}

	public BigDecimal getPActLevel() {
		return this.PActLevel;
	}

	public BigDecimal getPActMaxDemand() {
		return this.PActMaxDemand;
	}

	public Date getPActMaxDemandTime() {
		return this.PActMaxDemandTime;
	}

	public BigDecimal getPActPeak() {
		return this.PActPeak;
	}

	public BigDecimal getPActSharp() {
		return this.PActSharp;
	}

	public BigDecimal getPActTotal() {
		return this.PActTotal;
	}

	public BigDecimal getPActValley() {
		return this.PActValley;
	}

	public BigDecimal getPReactLevel() {
		return this.PReactLevel;
	}

	public BigDecimal getPReactMaxDemand() {
		return this.PReactMaxDemand;
	}

	public Date getPReactMaxDemandTime() {
		return this.PReactMaxDemandTime;
	}

	public BigDecimal getPReactPeak() {
		return this.PReactPeak;
	}

	public BigDecimal getPReactSharp() {
		return this.PReactSharp;
	}

	public BigDecimal getPReactTotal() {
		return this.PReactTotal;
	}

	public BigDecimal getPReactValley() {
		return this.PReactValley;
	}

	public BigDecimal getReactQ1() {
		return this.reactQ1;
	}

	public BigDecimal getReactQ2() {
		return this.reactQ2;
	}

	public BigDecimal getReactQ3() {
		return this.reactQ3;
	}

	public BigDecimal getReactQ4() {
		return this.reactQ4;
	}

	public BigDecimal getTotalTimes() {
		return this.totalTimes;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}

	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}

	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}

	public void setDdate(String ddate) {
		this.ddate = ddate;
	}

	public void setGpId(BigDecimal gpId) {
		this.gpId = gpId;
	}

	public void setIActLevel(BigDecimal IActLevel) {
		this.IActLevel = IActLevel;
	}

	public void setIActMaxDemand(BigDecimal IActMaxDemand) {
		this.IActMaxDemand = IActMaxDemand;
	}

	public void setIActMaxDemandTime(Date IActMaxDemandTime) {
		this.IActMaxDemandTime = IActMaxDemandTime;
	}

	public void setIActPeak(BigDecimal IActPeak) {
		this.IActPeak = IActPeak;
	}

	public void setIActSharp(BigDecimal IActSharp) {
		this.IActSharp = IActSharp;
	}

	public void setIActTotal(BigDecimal IActTotal) {
		this.IActTotal = IActTotal;
	}

	public void setIActValley(BigDecimal IActValley) {
		this.IActValley = IActValley;
	}

	public void setIReactLevel(BigDecimal IReactLevel) {
		this.IReactLevel = IReactLevel;
	}

	public void setIReactMaxDemand(BigDecimal IReactMaxDemand) {
		this.IReactMaxDemand = IReactMaxDemand;
	}

	public void setIReactMaxDemandTime(Date IReactMaxDemandTime) {
		this.IReactMaxDemandTime = IReactMaxDemandTime;
	}

	public void setIReactPeak(BigDecimal IReactPeak) {
		this.IReactPeak = IReactPeak;
	}

	public void setIReactSharp(BigDecimal IReactSharp) {
		this.IReactSharp = IReactSharp;
	}

	public void setIReactTotal(BigDecimal IReactTotal) {
		this.IReactTotal = IReactTotal;
	}

	public void setIReactValley(BigDecimal IReactValley) {
		this.IReactValley = IReactValley;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public void setPActLevel(BigDecimal PActLevel) {
		this.PActLevel = PActLevel;
	}

	public void setPActMaxDemand(BigDecimal PActMaxDemand) {
		this.PActMaxDemand = PActMaxDemand;
	}

	public void setPActMaxDemandTime(Date PActMaxDemandTime) {
		this.PActMaxDemandTime = PActMaxDemandTime;
	}

	public void setPActPeak(BigDecimal PActPeak) {
		this.PActPeak = PActPeak;
	}

	public void setPActSharp(BigDecimal PActSharp) {
		this.PActSharp = PActSharp;
	}

	public void setPActTotal(BigDecimal PActTotal) {
		this.PActTotal = PActTotal;
	}

	public void setPActValley(BigDecimal PActValley) {
		this.PActValley = PActValley;
	}

	public void setPReactLevel(BigDecimal PReactLevel) {
		this.PReactLevel = PReactLevel;
	}

	public void setPReactMaxDemand(BigDecimal PReactMaxDemand) {
		this.PReactMaxDemand = PReactMaxDemand;
	}

	public void setPReactMaxDemandTime(Date PReactMaxDemandTime) {
		this.PReactMaxDemandTime = PReactMaxDemandTime;
	}

	public void setPReactPeak(BigDecimal PReactPeak) {
		this.PReactPeak = PReactPeak;
	}

	public void setPReactSharp(BigDecimal PReactSharp) {
		this.PReactSharp = PReactSharp;
	}

	public void setPReactTotal(BigDecimal PReactTotal) {
		this.PReactTotal = PReactTotal;
	}

	public void setPReactValley(BigDecimal PReactValley) {
		this.PReactValley = PReactValley;
	}

	public void setReactQ1(BigDecimal reactQ1) {
		this.reactQ1 = reactQ1;
	}

	public void setReactQ2(BigDecimal reactQ2) {
		this.reactQ2 = reactQ2;
	}

	public void setReactQ3(BigDecimal reactQ3) {
		this.reactQ3 = reactQ3;
	}

	public void setReactQ4(BigDecimal reactQ4) {
		this.reactQ4 = reactQ4;
	}

	public void setTotalTimes(BigDecimal totalTimes) {
		this.totalTimes = totalTimes;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
