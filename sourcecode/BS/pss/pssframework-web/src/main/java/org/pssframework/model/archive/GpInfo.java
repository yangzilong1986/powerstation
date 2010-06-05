/**
 * 
 */
package org.pssframework.model.archive;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "g_tran")
@SequenceGenerator(sequenceName = "SEQ_G_TRAN", name = "SEQ_G_TRAN")
public class GpInfo extends BaseEntity {

	private static final long serialVersionUID = -3795917072464107754L;

	@Column(name = "EQUIP_ID", unique = true, nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_G_TRAN")
	//EQUIP_ID           NUMBER(16) not null, 设备的唯一标识， 变更的时候用于对应线损模型中的变压器唯一标识
	private Long equipId;

	//TG_ID              NUMBER(16), 台区标识
	@ManyToOne(targetEntity = org.pssframework.model.archive.TgInfo.class, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "TG_ID", nullable = false)
	private Long tgId;

	@Column(name = "ORG_NO", length = 16, nullable = false)
	// ORG_NO          VARCHAR2(16) not null, 部门
	private String orgNo;

	@Column(name = "CONS_ID", length = 16)
	//CONS_ID            NUMBER(16),用电客户的内部唯一标识
	private Long consId;

	@Column(name = "TYPE_CODE", length = 8)
	//TYPE_CODE          VARCHAR2(8), 区分是变压器还是高压电动
	private String typeCode;

	@Column(name = "TRAN_NAME", length = 256)
	//TRAN_NAME          VARCHAR2(256),设备的名称
	private String tranName;

	@Column(name = "INST_ADDR", length = 256)
	//INST_ADDR          VARCHAR2(256),设备的安装地址
	private String instAddr;

	@Column(name = "INST_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	//INST_DATE          DATE,设备的安装日期
	private Date instDate;

	@Column(name = "PLATE_CAP", precision = 16, scale = 6)
	//PLATE_CAP          NUMBER(16,6),设备铭牌上登记的容量
	private Double plateCap;

	@Column(name = "MS_FLAG", length = 8)
	//MS_FLAG            VARCHAR2(8),引用国家电网公司营销管理代码类集 :5110.17 电源用途分类与代码
	private String msFlag;

	@Column(name = "RUN_STATUS_CODE", length = 8)
	//RUN_STATUS_CODE    VARCHAR2(8),本次变更前的运行状态 01 运行、 02 停用、 03 拆除
	private String runStatusCode;

	@Column(name = "PUB_PRIV_FLAG", length = 8)
	//PUB_PRIV_FLAG      VARCHAR2(8),
	private String pubPrivFlag;

	@Column(name = "PROTECT_MODE", length = 8)
	//PROTECT_MODE       VARCHAR2(8),受电设备的保护方式， 引用代码 变压器保护方式分类
	private String protectMode;

	@Column(name = "FRSTSIDE_VOLT_CODE", length = 8)
	//FRSTSIDE_VOLT_CODE VARCHAR2(8),设备的一侧电压
	private String frstsideVoltCode;

	@Column(name = "SNDSIDE_VOLT_CODE", length = 8)
	//SNDSIDE_VOLT_CODE  VARCHAR2(8),设备的二侧电压
	private String sndsideVoltCode;

	@Column(name = "MODEL_NO", length = 8)
	//MODEL_NO           VARCHAR2(8),设备的型号
	private String modelNo;

	@Column(name = "RV_HV", length = 8)
	//RV_HV              VARCHAR2(8),额定电压 _ 高压
	private String rvHv;

	@Column(name = "RC_HV", length = 8)
	//RC_HV              VARCHAR2(8),额定电流 _ 高压
	private String rcHv;

	@Column(name = "RV_MV", length = 8)
	//RV_MV              VARCHAR2(8),额定电压 _ 中压
	private String rvMv;

	@Column(name = "RC_MV", length = 8)
	//RC_MV              VARCHAR2(8),额定电流 _ 中压
	private String rcMv;

	@Column(name = "RV_LV", length = 8)
	//RV_LV              VARCHAR2(8),额定电压 _ 低压
	private String rvLv;

	@Column(name = "RC_LV", length = 8)
	//RC_LV              VARCHAR2(8),额定电流 _ 低压
	private String rcLv;

	@Column(name = "PR_CODE", length = 8)
	//PR_CODE            VARCHAR2(8),设备的产权说明 01 局属、 02 用户
	private String prCode;

	@Column(name = "LASTTIME_STAMP")
	@Temporal(TemporalType.TIMESTAMP)
	//LASTTIME_STAMP  DATE default SYSDATE ,最后表结构修改时间戳
	private Date lasttimeStamp;

	/**
	 * @return the consId
	 */
	public Long getConsId() {
		return consId;
	}

	/**
	 * @return the equipId
	 */
	public Long getEquipId() {
		return equipId;
	}

	/**
	 * @return the frstsideVoltCode
	 */
	public String getFrstsideVoltCode() {
		return frstsideVoltCode;
	}

	/**
	 * @return the instAddr
	 */
	public String getInstAddr() {
		return instAddr;
	}

	/**
	 * @return the instDate
	 */
	public Date getInstDate() {
		return instDate;
	}

	/**
	 * @return the lasttimeStamp
	 */
	public Date getLasttimeStamp() {
		return lasttimeStamp;
	}

	/**
	 * @return the modelNo
	 */
	public String getModelNo() {
		return modelNo;
	}

	/**
	 * @return the msFlag
	 */
	public String getMsFlag() {
		return msFlag;
	}

	/**
	 * @return the orgNo
	 */
	public String getOrgNo() {
		return orgNo;
	}

	/**
	 * @return the plateCap
	 */
	public Double getPlateCap() {
		return plateCap;
	}

	/**
	 * @return the prCode
	 */
	public String getPrCode() {
		return prCode;
	}

	/**
	 * @return the protectMode
	 */
	public String getProtectMode() {
		return protectMode;
	}

	/**
	 * @return the pubPrivFlag
	 */
	public String getPubPrivFlag() {
		return pubPrivFlag;
	}

	/**
	 * @return the rcHv
	 */
	public String getRcHv() {
		return rcHv;
	}

	/**
	 * @return the rcLv
	 */
	public String getRcLv() {
		return rcLv;
	}

	/**
	 * @return the rcMv
	 */
	public String getRcMv() {
		return rcMv;
	}

	/**
	 * @return the runStatusCode
	 */
	public String getRunStatusCode() {
		return runStatusCode;
	}

	/**
	 * @return the rvHv
	 */
	public String getRvHv() {
		return rvHv;
	}

	/**
	 * @return the rvLv
	 */
	public String getRvLv() {
		return rvLv;
	}

	/**
	 * @return the rvMv
	 */
	public String getRvMv() {
		return rvMv;
	}

	/**
	 * @return the sndsideVoltCode
	 */
	public String getSndsideVoltCode() {
		return sndsideVoltCode;
	}

	/**
	 * @return the tgId
	 */
	public Long getTgId() {
		return tgId;
	}

	/**
	 * @return the tranName
	 */
	public String getTranName() {
		return tranName;
	}

	/**
	 * @return the typeCode
	 */
	public String getTypeCode() {
		return typeCode;
	}

	/**
	 * @param consId the consId to set
	 */
	public void setConsId(Long consId) {
		this.consId = consId;
	}

	/**
	 * @param equipId the equipId to set
	 */
	public void setEquipId(Long equipId) {
		this.equipId = equipId;
	}

	/**
	 * @param frstsideVoltCode the frstsideVoltCode to set
	 */
	public void setFrstsideVoltCode(String frstsideVoltCode) {
		this.frstsideVoltCode = frstsideVoltCode;
	}

	/**
	 * @param instAddr the instAddr to set
	 */
	public void setInstAddr(String instAddr) {
		this.instAddr = instAddr;
	}

	/**
	 * @param instDate the instDate to set
	 */
	public void setInstDate(Date instDate) {
		this.instDate = instDate;
	}

	/**
	 * @param lasttimeStamp the lasttimeStamp to set
	 */
	public void setLasttimeStamp(Date lasttimeStamp) {
		this.lasttimeStamp = lasttimeStamp;
	}

	/**
	 * @param modelNo the modelNo to set
	 */
	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	/**
	 * @param msFlag the msFlag to set
	 */
	public void setMsFlag(String msFlag) {
		this.msFlag = msFlag;
	}

	/**
	 * @param orgNo the orgNo to set
	 */
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	/**
	 * @param plateCap the plateCap to set
	 */
	public void setPlateCap(Double plateCap) {
		this.plateCap = plateCap;
	}

	/**
	 * @param prCode the prCode to set
	 */
	public void setPrCode(String prCode) {
		this.prCode = prCode;
	}

	/**
	 * @param protectMode the protectMode to set
	 */
	public void setProtectMode(String protectMode) {
		this.protectMode = protectMode;
	}

	/**
	 * @param pubPrivFlag the pubPrivFlag to set
	 */
	public void setPubPrivFlag(String pubPrivFlag) {
		this.pubPrivFlag = pubPrivFlag;
	}

	/**
	 * @param rcHv the rcHv to set
	 */
	public void setRcHv(String rcHv) {
		this.rcHv = rcHv;
	}

	/**
	 * @param rcLv the rcLv to set
	 */
	public void setRcLv(String rcLv) {
		this.rcLv = rcLv;
	}

	/**
	 * @param rcMv the rcMv to set
	 */
	public void setRcMv(String rcMv) {
		this.rcMv = rcMv;
	}

	/**
	 * @param runStatusCode the runStatusCode to set
	 */
	public void setRunStatusCode(String runStatusCode) {
		this.runStatusCode = runStatusCode;
	}

	/**
	 * @param rvHv the rvHv to set
	 */
	public void setRvHv(String rvHv) {
		this.rvHv = rvHv;
	}

	/**
	 * @param rvLv the rvLv to set
	 */
	public void setRvLv(String rvLv) {
		this.rvLv = rvLv;
	}

	/**
	 * @param rvMv the rvMv to set
	 */
	public void setRvMv(String rvMv) {
		this.rvMv = rvMv;
	}

	/**
	 * @param sndsideVoltCode the sndsideVoltCode to set
	 */
	public void setSndsideVoltCode(String sndsideVoltCode) {
		this.sndsideVoltCode = sndsideVoltCode;
	}

	/**
	 * @param tgId the tgId to set
	 */
	public void setTgId(Long tgId) {
		this.tgId = tgId;
	}

	/**
	 * @param tranName the tranName to set
	 */
	public void setTranName(String tranName) {
		this.tranName = tranName;
	}

	/**
	 * @param typeCode the typeCode to set
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
