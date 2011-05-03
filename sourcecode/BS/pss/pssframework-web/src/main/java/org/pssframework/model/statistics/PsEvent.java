package org.pssframework.model.statistics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PsEvent implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1191218069819208203L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	private BigDecimal lbsjId;
	private BigDecimal gpId;
	private BigDecimal psId;
	private String psName;
	private String assetNo;
	private BigDecimal tgId;
	private String tgNo;
	private String tgName;
	private String eventCode;
	private String eventName;
	private Date trigTime;
	private Date receiveTime;
	private String closed;
	private String locked;
	private String phase;
	private BigDecimal currentValue;
	private String resetSent;

	/**
	 * @return the lbsjId
	 */
	public BigDecimal getLbsjId() {
		return lbsjId;
	}

	/**
	 * @param lbsjId the lbsjId to set
	 */
	public void setLbsjId(BigDecimal lbsjId) {
		this.lbsjId = lbsjId;
	}

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
	 * @return the psId
	 */
	public BigDecimal getPsId() {
		return psId;
	}

	/**
	 * @param psId the psId to set
	 */
	public void setPsId(BigDecimal psId) {
		this.psId = psId;
	}

	/**
	 * @return the psName
	 */
	public String getPsName() {
		return psName;
	}

	/**
	 * @param psName the psName to set
	 */
	public void setPsName(String psName) {
		this.psName = psName;
	}

	/**
	 * @return the assetNo
	 */
	public String getAssetNo() {
		return assetNo;
	}

	/**
	 * @param assetNo the assetNo to set
	 */
	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}

	/**
	 * @return the tgId
	 */
	public BigDecimal getTgId() {
		return tgId;
	}

	/**
	 * @param tgId the tgId to set
	 */
	public void setTgId(BigDecimal tgId) {
		this.tgId = tgId;
	}

	/**
	 * @return the tgNo
	 */
	public String getTgNo() {
		return tgNo;
	}

	/**
	 * @param tgNo the tgNo to set
	 */
	public void setTgNo(String tgNo) {
		this.tgNo = tgNo;
	}

	/**
	 * @return the tgName
	 */
	public String getTgName() {
		return tgName;
	}

	/**
	 * @param tgName the tgName to set
	 */
	public void setTgName(String tgName) {
		this.tgName = tgName;
	}

	/**
	 * @return the eventCode
	 */
	public String getEventCode() {
		return eventCode;
	}

	/**
	 * @param eventCode the eventCode to set
	 */
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	/**
	 * @return the eventName
	 */
	public String getEventName() {
		return eventName;
	}

	/**
	 * @param eventName the eventName to set
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	/**
	 * @return the trigTime
	 */
	public Date getTrigTime() {
		return trigTime;
	}

	/**
	 * @param trigTime the trigTime to set
	 */
	public void setTrigTime(Date trigTime) {
		this.trigTime = trigTime;
	}

	/**
	 * @return the receiveTime
	 */
	public Date getReceiveTime() {
		return receiveTime;
	}

	/**
	 * @param receiveTime the receiveTime to set
	 */
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	/**
	 * @return the closed
	 */
	public String getClosed() {
		return closed;
	}

	/**
	 * @param closed the closed to set
	 */
	public void setClosed(String closed) {
		this.closed = closed;
	}

	/**
	 * @return the locked
	 */
	public String getLocked() {
		return locked;
	}

	/**
	 * @param locked the locked to set
	 */
	public void setLocked(String locked) {
		this.locked = locked;
	}

	/**
	 * @return the phase
	 */
	public String getPhase() {
		return phase;
	}

	/**
	 * @param phase the phase to set
	 */
	public void setPhase(String phase) {
		this.phase = phase;
	}

	/**
	 * @return the currentValue
	 */
	public BigDecimal getCurrentValue() {
		return currentValue;
	}

	/**
	 * @param currentValue the currentValue to set
	 */
	public void setCurrentValue(BigDecimal currentValue) {
		this.currentValue = currentValue;
	}

	/**
	 * @return the resetSent
	 */
	public String getResetSent() {
		return resetSent;
	}

	/**
	 * @param resetSent the resetSent to set
	 */
	public void setResetSent(String resetSent) {
		this.resetSent = resetSent;
	}

}
