package org.pssframework.model.statistics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        系统当前功率数据（当前日/或者前7日以前的），历史自动归档为历史数据。
 * 数据产生：由采集平台直接写上送数据进来；业务平台负责使用及转到历史表；
 *     
*/
public class DPowerCruv implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8878651237523881166L;

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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

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
	private BigDecimal actPower;

	/** nullable persistent field */
	private BigDecimal reactPower;

	/** nullable persistent field */
	private BigDecimal actPowerA;

	/** nullable persistent field */
	private BigDecimal actPowerB;

	/** nullable persistent field */
	private BigDecimal actPowerC;

	/** nullable persistent field */
	private BigDecimal reactPowerA;

	/** nullable persistent field */
	private BigDecimal reactPowerB;

	/** nullable persistent field */
	private BigDecimal reactPowerC;

	/** nullable persistent field */
	private String dataFlag;

	/** nullable persistent field */
	private String dataSource;

	/** default constructor */
	public DPowerCruv() {
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

	public BigDecimal getActPower() {
		return this.actPower;
	}

	public void setActPower(BigDecimal actPower) {
		this.actPower = actPower;
	}

	public BigDecimal getReactPower() {
		return this.reactPower;
	}

	public void setReactPower(BigDecimal reactPower) {
		this.reactPower = reactPower;
	}

	public BigDecimal getActPowerA() {
		return this.actPowerA;
	}

	public void setActPowerA(BigDecimal actPowerA) {
		this.actPowerA = actPowerA;
	}

	public BigDecimal getActPowerB() {
		return this.actPowerB;
	}

	public void setActPowerB(BigDecimal actPowerB) {
		this.actPowerB = actPowerB;
	}

	public BigDecimal getActPowerC() {
		return this.actPowerC;
	}

	public void setActPowerC(BigDecimal actPowerC) {
		this.actPowerC = actPowerC;
	}

	public BigDecimal getReactPowerA() {
		return this.reactPowerA;
	}

	public void setReactPowerA(BigDecimal reactPowerA) {
		this.reactPowerA = reactPowerA;
	}

	public BigDecimal getReactPowerB() {
		return this.reactPowerB;
	}

	public void setReactPowerB(BigDecimal reactPowerB) {
		this.reactPowerB = reactPowerB;
	}

	public BigDecimal getReactPowerC() {
		return this.reactPowerC;
	}

	public void setReactPowerC(BigDecimal reactPowerC) {
		this.reactPowerC = reactPowerC;
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

}
