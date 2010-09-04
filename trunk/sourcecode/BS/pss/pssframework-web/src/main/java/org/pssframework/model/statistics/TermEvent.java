package org.pssframework.model.statistics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class TermEvent implements Serializable {
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

	//ED_ID
	private BigDecimal edId;

	//LOGICAL_ADDR
	private String logicalAddr;

	//GP_SN
	private BigDecimal gpSn;

	//GP_CHAR
	private String gpChar;

	//EX_CODE_FEP
	private String exCode;

	//EX_TIME
	private Date exTime;

	//ACCEPT_TIME
	private Date acceptTime;

	/**
	 * @return the edId
	 */
	public BigDecimal getEdId() {
		return edId;
	}

	/**
	 * @param edId the edId to set
	 */
	public void setEdId(BigDecimal edId) {
		this.edId = edId;
	}

	/**
	 * @return the logicalAddr
	 */
	public String getLogicalAddr() {
		return logicalAddr;
	}

	/**
	 * @param logicalAddr the logicalAddr to set
	 */
	public void setLogicalAddr(String logicalAddr) {
		this.logicalAddr = logicalAddr;
	}

	/**
	 * @return the gpSn
	 */
	public BigDecimal getGpSn() {
		return gpSn;
	}

	/**
	 * @param gpSn the gpSn to set
	 */
	public void setGpSn(BigDecimal gpSn) {
		this.gpSn = gpSn;
	}

	/**
	 * @return the gpChar
	 */
	public String getGpChar() {
		return gpChar;
	}

	/**
	 * @param gpChar the gpChar to set
	 */
	public void setGpChar(String gpChar) {
		this.gpChar = gpChar;
	}

	/**
	 * @return the exCode
	 */
	public String getExCode() {
		return exCode;
	}

	/**
	 * @param exCode the exCode to set
	 */
	public void setExCode(String exCode) {
		this.exCode = exCode;
	}

	/**
	 * @return the exTime
	 */
	public Date getExTime() {
		return exTime;
	}

	/**
	 * @param exTime the exTime to set
	 */
	public void setExTime(Date exTime) {
		this.exTime = exTime;
	}

	/**
	 * @return the acceptTime
	 */
	public Date getAcceptTime() {
		return acceptTime;
	}

	/**
	 * @param acceptTime the acceptTime to set
	 */
	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}
}
