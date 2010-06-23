/**
 * 
 */
package org.pssframework.model.archive;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.pssframework.base.BaseEntity;

/**
*1) 描述变压器的运行信息及铭牌参数，包括变压器编码、变压器型号、变压器铭牌容量、当前状态等信息
*2) 通过线损基础信息管理业务中录入产生，或新装增容与变更用电归档过程产生；或通过与生产系统接口过程产生。
*3) 该实体主要由线损基础信息管理业务、考核单元管理业务使用，在电费计算用户专线线路损耗也需要使用。
 *
 * @author baocj
 * @since 1.0.0
 */
@Entity
@Table(name = "C_TERMINAL")
@SequenceGenerator(sequenceName = "SEQ_C_TERMINAL", name = "SEQ_C_TERMINAL", allocationSize = 1)
public class TerminalInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2619191629996760464L;

	@OneToMany(mappedBy = "terminalInfo", targetEntity = GpInfo.class)
	private List<GpInfo> gpInfos = new ArrayList<GpInfo>();

	@OneToMany(mappedBy = "terminalInfo", targetEntity = TermObjRelaInfo.class, cascade = CascadeType.ALL)
	private List<TermObjRelaInfo> termObjRelas;

	@Column(name = "TERM_ID", unique = true, nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_C_TERMINAL")
	// TERM_ID NUMBER not null,
	private Long termId;

	@Column(name = "ORG_ID")
	// ORG_ID NUMBER not null, 部门
	private Long orgId;

	@Column(name = "ASSET_NO", length = 20)
	// ASSET_NO VARCHAR2(20),
	private String assetNo;

	@Column(name = "LOGICAL_ADDR", length = 20)
	// LOGICAL_ADDR VARCHAR2(20),
	private String logicalAddr;

	@Column(name = "RUN_STATUS", length = 5)
	// RUN_STATUS VARCHAR2(5),
	private String runStatusAddr;

	@Column(name = "CUR_STATUS", length = 5)
	// CUR_STATUS VARCHAR2(5),
	private String curStatus;

	@Column(name = "SIM_NO", length = 20)
	// SIM_NO VARCHAR2(20),
	private String simNo;

	@Column(name = "TERM_TYPE", length = 5)
	// TERM_TYPE VARCHAR2(5),
	private String termType;

	@Column(name = "WIRING_MODE", length = 5)
	// WIRING_MODE VARCHAR2(5),
	private String wiringMode;

	@Column(name = "MODEL_CODE", length = 5)
	// MODEL_CODE VARCHAR2(5),
	private String modelCode;

	@Column(name = "LEAVE_FAC_NO", length = 20)
	// LEAVE_FAC_NO VARCHAR2(20),
	private String leaveFacNo;

	@Column(name = "BATCH_ID", length = 20)
	// BATCH_ID VARCHAR2(20),
	private String batchId;

	@Column(name = "MADE_FAC", length = 5)
	// MADE_FAC VARCHAR2(5),
	private String madeFac;

	@Column(name = "LEAVE_FAC_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	// LEAVE_FAC_DATE DATE
	private Date leaveFacDate;

	@Column(name = "INSTALL_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	// INSTALL_DATE DATE
	private Date installDate;

	@Column(name = "COMM_MODE", length = 5)
	// COMM_MODE VARCHAR2(5),
	private String commMode;

	@Column(name = "CHANNEL_TYPE", length = 5)
	// CHANNEL_TYPE VARCHAR2(5),
	private String channelType;

	@Column(name = "PROTOCOL_NO", length = 5)
	// PROTOCOL_NO VARCHAR2(5),
	private String protocolNo;

	@Column(name = "PR", length = 5)
	// PR VARCHAR2(5),
	private String pr;

	@Column(name = "ISAC", length = 5)
	// ISAC VARCHAR2(5),
	private String isac;

	@Column(name = "PHYSICS_ADDR", length = 30)
	// PHYSICS_ADDR VARCHAR2(30),
	private String physicsAddr;

	@Column(name = "MACH_NO", length = 10)
	// MACH_NO VARCHAR2(10),
	private String machNo;

	@Column(name = "FEP_CNL", length = 30)
	// FEP_CNL VARCHAR2(30),
	private String fepCnl;

	@Column(name = "CONSTR_GANG", length = 50)
	// CONSTR_GANG VARCHAR2(50),
	private String constrGang;

	@Column(name = "COMM_PATTERN")
	// COMM_PATTERN NUMBER,
	private Long commPattern;

	@Column(name = "LASTTIME_STAMP")
	@Temporal(TemporalType.TIMESTAMP)
	// LASTTIME_STAMP DATE default SYSDATE ,最后表结构修改时间戳
	private Date lasttimeStamp;

	@Column(name = "PINYIN_CODE", length = 16)
	// PINYIN_CODE VARCHAR2(16)
	private String pinyinCode;

	/**
	 * @return the assetNo
	 */
	public String getAssetNo() {
		return assetNo;
	}

	/**
	 * @return the batchId
	 */
	public String getBatchId() {
		return batchId;
	}

	/**
	 * @return the channelType
	 */
	public String getChannelType() {
		return channelType;
	}

	/**
	 * @return the commMode
	 */
	public String getCommMode() {
		return commMode;
	}

	/**
	 * @return the commPattern
	 */
	public Long getCommPattern() {
		return commPattern;
	}

	/**
	 * @return the constrGang
	 */
	public String getConstrGang() {
		return constrGang;
	}

	/**
	 * @return the curStatus
	 */
	public String getCurStatus() {
		return curStatus;
	}

	/**
	 * @return the fepCnl
	 */
	public String getFepCnl() {
		return fepCnl;
	}

	/**
	 * @return the gpInfos
	 */
	public List<GpInfo> getGpInfos() {
		return gpInfos;
	}

	/**
	 * @return the installDate
	 */
	public Date getInstallDate() {
		return installDate;
	}

	/**
	 * @return the isac
	 */
	public String getIsac() {
		return isac;
	}

	/**
	 * @return the lasttimeStamp
	 */
	public Date getLasttimeStamp() {
		return lasttimeStamp;
	}

	/**
	 * @return the leaveFacDate
	 */
	public Date getLeaveFacDate() {
		return leaveFacDate;
	}

	/**
	 * @return the leaveFacNo
	 */
	public String getLeaveFacNo() {
		return leaveFacNo;
	}

	/**
	 * @return the logicalAddr
	 */
	public String getLogicalAddr() {
		return logicalAddr;
	}

	/**
	 * @return the machNo
	 */
	public String getMachNo() {
		return machNo;
	}

	/**
	 * @return the madeFac
	 */
	public String getMadeFac() {
		return madeFac;
	}

	/**
	 * @return the modelCode
	 */
	public String getModelCode() {
		return modelCode;
	}

	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @return the physicsAddr
	 */
	public String getPhysicsAddr() {
		return physicsAddr;
	}

	/**
	 * @return the pinyinCode
	 */
	public String getPinyinCode() {
		return pinyinCode;
	}

	/**
	 * @return the pr
	 */
	public String getPr() {
		return pr;
	}

	/**
	 * @return the protocolNo
	 */
	public String getProtocolNo() {
		return protocolNo;
	}

	/**
	 * @return the psInfos
	 */
	// public List<PsInfo> getPsInfos() {
	// return psInfos;
	// }

	/**
	 * @return the runStatusAddr
	 */
	public String getRunStatusAddr() {
		return runStatusAddr;
	}

	/**
	 * @return the simNo
	 */
	public String getSimNo() {
		return simNo;
	}

	/**
	 * @return the termId
	 */
	public Long getTermId() {
		return termId;
	}

	/**
	 * @return the termType
	 */
	public String getTermType() {
		return termType;
	}

	/**
	 * @return the wiringMode
	 */
	public String getWiringMode() {
		return wiringMode;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/**
	 * @param assetNo the assetNo to set
	 */
	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}

	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	/**
	 * @param channelType the channelType to set
	 */
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	/**
	 * @param commMode the commMode to set
	 */
	public void setCommMode(String commMode) {
		this.commMode = commMode;
	}

	/**
	 * @param commPattern the commPattern to set
	 */
	public void setCommPattern(Long commPattern) {
		this.commPattern = commPattern;
	}

	/**
	 * @param constrGang the constrGang to set
	 */
	public void setConstrGang(String constrGang) {
		this.constrGang = constrGang;
	}

	/**
	 * @param curStatus the curStatus to set
	 */
	public void setCurStatus(String curStatus) {
		this.curStatus = curStatus;
	}

	/**
	 * @param fepCnl the fepCnl to set
	 */
	public void setFepCnl(String fepCnl) {
		this.fepCnl = fepCnl;
	}

	/**
	 * @param gpInfos the gpInfos to set
	 */
	public void setGpInfos(List<GpInfo> gpInfos) {
		this.gpInfos = gpInfos;
	}

	/**
	 * @param installDate the installDate to set
	 */
	public void setInstallDate(Date installDate) {
		this.installDate = installDate;
	}

	/**
	 * @param isac the isac to set
	 */
	public void setIsac(String isac) {
		this.isac = isac;
	}

	/**
	 * @param lasttimeStamp the lasttimeStamp to set
	 */
	public void setLasttimeStamp(Date lasttimeStamp) {
		this.lasttimeStamp = lasttimeStamp;
	}

	/**
	 * @param leaveFacDate the leaveFacDate to set
	 */
	public void setLeaveFacDate(Date leaveFacDate) {
		this.leaveFacDate = leaveFacDate;
	}

	/**
	 * @param leaveFacNo the leaveFacNo to set
	 */
	public void setLeaveFacNo(String leaveFacNo) {
		this.leaveFacNo = leaveFacNo;
	}

	/**
	 * @param logicalAddr the logicalAddr to set
	 */
	public void setLogicalAddr(String logicalAddr) {
		this.logicalAddr = logicalAddr;
	}

	/**
	 * @param machNo the machNo to set
	 */
	public void setMachNo(String machNo) {
		this.machNo = machNo;
	}

	/**
	 * @param madeFac the madeFac to set
	 */
	public void setMadeFac(String madeFac) {
		this.madeFac = madeFac;
	}

	/**
	 * @param modelCode the modelCode to set
	 */
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * @param physicsAddr the physicsAddr to set
	 */
	public void setPhysicsAddr(String physicsAddr) {
		this.physicsAddr = physicsAddr;
	}

	/**
	 * @param pinyinCode the pinyinCode to set
	 */
	public void setPinyinCode(String pinyinCode) {
		this.pinyinCode = pinyinCode;
	}

	/**
	 * @param pr the pr to set
	 */
	public void setPr(String pr) {
		this.pr = pr;
	}

	/**
	 * @param protocolNo the protocolNo to set
	 */
	public void setProtocolNo(String protocolNo) {
		this.protocolNo = protocolNo;
	}

	/**
	 * @param psInfos the psInfos to set
	 */
	// public void setPsInfos(List<PsInfo> psInfos) {
	// this.psInfos = psInfos;
	// }

	/**
	 * @param runStatusAddr the runStatusAddr to set
	 */
	public void setRunStatusAddr(String runStatusAddr) {
		this.runStatusAddr = runStatusAddr;
	}

	/**
	 * @param simNo the simNo to set
	 */
	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}

	/**
	 * @param termId the termId to set
	 */
	public void setTermId(Long termId) {
		this.termId = termId;
	}

	/**
	 * @param termType the termType to set
	 */
	public void setTermType(String termType) {
		this.termType = termType;
	}

	/**
	 * @param wiringMode the wiringMode to set
	 */
	public void setWiringMode(String wiringMode) {
		this.wiringMode = wiringMode;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * @param termObjRelas the termObjRelas to set
	 */
	public void setTermObjRelas(List<TermObjRelaInfo> termObjRelas) {
		this.termObjRelas = termObjRelas;
	}

	/**
	 * @return the termObjRelas
	 */
	public List<TermObjRelaInfo> getTermObjRelas() {
		return termObjRelas;
	}
}
