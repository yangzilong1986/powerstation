/**
 * 
 */
package org.pssframework.model.autorm;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.pssframework.base.BaseEntity;

/**
 * @author Administrator
 *
 */
public class RealTimeReadingInfo extends BaseEntity {

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	private static final long serialVersionUID = -2589564770513599171L;

	// 对象编号
	private String objNo;

	// 对象名称
	private String objName;

	// 对象类型
	private String objType;

	// 终端地址
	private String termAddr;

	// 电表局号
	private String meterNo;

	// 测量点序号
	private Long gpSn;

	/**
	 * @return the objNo
	 */
	public String getObjNo() {
		return objNo;
	}

	/**
	 * @param objNo the objNo to set
	 */
	public void setObjNo(String objNo) {
		this.objNo = objNo;
	}

	/**
	 * @return the objName
	 */
	public String getObjName() {
		return objName;
	}

	/**
	 * @param objName the objName to set
	 */
	public void setObjName(String objName) {
		this.objName = objName;
	}

	/**
	 * @return the objType
	 */
	public String getObjType() {
		return objType;
	}

	/**
	 * @param objType the objType to set
	 */
	public void setObjType(String objType) {
		this.objType = objType;
	}

	/**
	 * @return the termAddr
	 */
	public String getTermAddr() {
		return termAddr;
	}

	/**
	 * @param termAddr the termAddr to set
	 */
	public void setTermAddr(String termAddr) {
		this.termAddr = termAddr;
	}

	/**
	 * @return the meterNo
	 */
	public String getMeterNo() {
		return meterNo;
	}

	/**
	 * @param meterNo the meterNo to set
	 */
	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	/**
	 * @return the gpSn
	 */
	public Long getGpSn() {
		return gpSn;
	}

	/**
	 * @param gpSn the gpSn to set
	 */
	public void setGpSn(Long gpSn) {
		this.gpSn = gpSn;
	}

}
