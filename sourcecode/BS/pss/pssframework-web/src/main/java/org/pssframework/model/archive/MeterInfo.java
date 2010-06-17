/**
 * 
 */
package org.pssframework.model.archive;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.pssframework.base.BaseEntity;

/**
1)用于记录计量点下安装的电能表运行设备信息，定义了电能表的运行属性，本实体主要包括电能表资产编号、综合倍率、安装日期、安装位置、是否参考表、参考表资产编号等属性。
2)通过新装、增容及变更用电归档、关口计量点新装及变更归档等业务，由实体转入产生记录。
3)该实体主要由查询计量点相关信息等业务使用。
 *
 * @author baocj
 * @since 1.0.0
 */
@Entity
@Table(name = "C_METER")
@SequenceGenerator(sequenceName = "SEQ_C_METER", name = "SEQ_C_METER")
public class MeterInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6414145806175327349L;

	@OneToOne(mappedBy = "meterInfo", targetEntity = MeterMpRelaInfo.class)
	private MeterMpRelaInfo meterMpRelaInfo;

	@Column(name = "METER_ID", unique = true, nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_C_METER")
	// METER_ID NUMBER(16) not null,is '本实体记录的唯一标识号，取自营销设备域的电能表信息实体。';
	private Long meterId;

	@Column(name = "INST_LOC", length = 256)
	// INST_LOC VARCHAR2(256),is '电能表安装的物理位置';
	private String instLoc;

	@Column(name = "INST_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	// INST_DATE DATE,is '安装日期';
	private Date instDate;

	@Column(name = "T_FACTOR", precision = 10, scale = 2)
	// T_FACTOR NUMBER(10,2),is '电能表综合倍率=电能表自身倍率*同一计量点下电流互感器的倍率*同一计量点下电压互感器的倍率';
	private Long tFactor;

	@Column(name = "REF_METER_FLAG", length = 8)
	// REF_METER_FLAG VARCHAR2(8),is '标明电能表是否是参考表，包括01是、02否';
	private String refMeterFlag;

	@Column(name = "REF_METER_ID", length = 16)
	// REF_METER_ID NUMBER(16),is '标明当前电能表用于结算用途时，参考那块电能表计量结算。';
	private Long refMeterId;

	@Column(name = "MODULE_NO", length = 32)
	// MODULE_NO VARCHAR2(32),is '定义电能表是集抄时，对应的模块编号';
	private String moduleNo;

	@Column(name = "ORG_ID", length = 16)
	// ORG_ID NUMBER,is '供电管理单位的代码';
	private Long orgId;

	@Column(name = "COMM_ADDR1", length = 16)
	// COMM_ADDR1 VARCHAR2(16),is '通讯地址1';
	private String conmAddr1;

	@Column(name = "COMM_ADDR2", length = 16)
	// COMM_ADDR2 VARCHAR2(16),is '通讯地址2';
	private String conmAddr2;

	@Column(name = "COMM_NO", length = 8)
	// COMM_NO VARCHAR2(8),is '通讯规约：645……';
	private String commNo;

	@Column(name = "BAUDRATE", length = 16)
	// BAUDRATE VARCHAR2(16),is '电能表的波特率';
	private String baudrate;

	@Column(name = "COMM_MODE", length = 16)
	// COMM_MODE VARCHAR2(8)is '通讯方式：485、gprs、红外……';
	private String commMode;

	/**
	 * @return the baudrate
	 */
	public String getBaudrate() {
		return baudrate;
	}

	/**
	 * @return the commMode
	 */
	public String getCommMode() {
		return commMode;
	}

	/**
	 * @return the commNo
	 */
	public String getCommNo() {
		return commNo;
	}

	/**
	 * @return the conmAddr1
	 */
	public String getConmAddr1() {
		return conmAddr1;
	}

	/**
	 * @return the conmAddr2
	 */
	public String getConmAddr2() {
		return conmAddr2;
	}

	/**
	 * @return the instDate
	 */
	public Date getInstDate() {
		return instDate;
	}

	/**
	 * @return the instLoc
	 */
	public String getInstLoc() {
		return instLoc;
	}

	/**
	 * @return the meterId
	 */
	public Long getMeterId() {
		return meterId;
	}

	/**
	 * @return the meterMpRelaInfo
	 */
	public MeterMpRelaInfo getMeterMpRelaInfo() {
		return meterMpRelaInfo;
	}

	/**
	 * @return the moduleNo
	 */
	public String getModuleNo() {
		return moduleNo;
	}

	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @return the refMeterFlag
	 */
	public String getRefMeterFlag() {
		return refMeterFlag;
	}

	/**
	 * @return the refMeterId
	 */
	public Long getRefMeterId() {
		return refMeterId;
	}

	/**
	 * @return the tFactor
	 */
	public Long gettFactor() {
		return tFactor;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/**
	 * @param baudrate the baudrate to set
	 */
	public void setBaudrate(String baudrate) {
		this.baudrate = baudrate;
	}

	/**
	 * @param commMode the commMode to set
	 */
	public void setCommMode(String commMode) {
		this.commMode = commMode;
	}

	/**
	 * @param commNo the commNo to set
	 */
	public void setCommNo(String commNo) {
		this.commNo = commNo;
	}

	/**
	 * @param conmAddr1 the conmAddr1 to set
	 */
	public void setConmAddr1(String conmAddr1) {
		this.conmAddr1 = conmAddr1;
	}

	/**
	 * @param conmAddr2 the conmAddr2 to set
	 */
	public void setConmAddr2(String conmAddr2) {
		this.conmAddr2 = conmAddr2;
	}

	/**
	 * @param instDate the instDate to set
	 */
	public void setInstDate(Date instDate) {
		this.instDate = instDate;
	}

	/**
	 * @param instLoc the instLoc to set
	 */
	public void setInstLoc(String instLoc) {
		this.instLoc = instLoc;
	}

	/**
	 * @param meterId the meterId to set
	 */
	public void setMeterId(Long meterId) {
		this.meterId = meterId;
	}

	/**
	 * @param meterMpRelaInfo the meterMpRelaInfo to set
	 */
	public void setMeterMpRelaInfo(MeterMpRelaInfo meterMpRelaInfo) {
		this.meterMpRelaInfo = meterMpRelaInfo;
	}

	/**
	 * @param moduleNo the moduleNo to set
	 */
	public void setModuleNo(String moduleNo) {
		this.moduleNo = moduleNo;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * @param refMeterFlag the refMeterFlag to set
	 */
	public void setRefMeterFlag(String refMeterFlag) {
		this.refMeterFlag = refMeterFlag;
	}

	/**
	 * @param refMeterId the refMeterId to set
	 */
	public void setRefMeterId(Long refMeterId) {
		this.refMeterId = refMeterId;
	}

	/**
	 * @param tFactor the tFactor to set
	 */
	public void settFactor(Long tFactor) {
		this.tFactor = tFactor;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
