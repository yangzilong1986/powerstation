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

	private static final long serialVersionUID = -2589564770513599171L;

	//测量点id
	private Long gpId;

	// 部门
	private Long orgId;

	// 对象Id
	private Long objId;

	// 对象编号
	private String objNo;

	// 对象名称
	private String objName;

	// 对象类型
	private String objType;

	// 终端地址
	private String logicalAddr;

	// 电表局号
	private String mpNo;

	// 测量点序号
	private Long gpSn;

	public Long getGpId() {
		return gpId;
	}

	/**
	 * @return the gpSn
	 */
	public Long getGpSn() {
		return gpSn;
	}

	/**
	 * @return the logicalAddr
	 */
	public String getLogicalAddr() {
		return logicalAddr;
	}

	/**
	 * @return the meterNo
	 */
	public String getMpNo() {
		return mpNo;
	}

	/**
	 * @return the objId
	 */
	public Long getObjId() {
		return objId;
	}

	/**
	 * @return the objName
	 */
	public String getObjName() {
		return objName;
	}

	/**
	 * @return the objNo
	 */
	public String getObjNo() {
		return objNo;
	}

	/**
	 * @return the objType
	 */
	public String getObjType() {
		return objType;
	}

	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public void setGpId(Long gpId) {
		this.gpId = gpId;
	}

	/**
	 * @param gpSn the gpSn to set
	 */
	public void setGpSn(Long gpSn) {
		this.gpSn = gpSn;
	}

	/**
	 * @param logicalAddr the logicalAddr to set
	 */
	public void setLogicalAddr(String logicalAddr) {
		this.logicalAddr = logicalAddr;
	}

	/**
	 * @param meterNo the meterNo to set
	 */
	public void setMpNo(String mpNo) {
		this.mpNo = mpNo;
	}

	/**
	 * @param objId the objId to set
	 */
	public void setObjId(Long objId) {
		this.objId = objId;
	}

	/**
	 * @param objName the objName to set
	 */
	public void setObjName(String objName) {
		this.objName = objName;
	}

	/**
	 * @param objNo the objNo to set
	 */
	public void setObjNo(String objNo) {
		this.objNo = objNo;
	}

	/**
	 * @param objType the objType to set
	 */
	public void setObjType(String objType) {
		this.objType = objType;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
