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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.pssframework.base.BaseEntity;

/**
1) 依法与供电企业建立供用电关系的组织或个人称为用电客户，简称用户，不同用电地址视为不同用户。
同一客户相邻地址的多个受电点，可以立为多个用户，也可以立为一个用户。
用电客户包含用电地址，用电类别，供电电压，负荷性质，合同容量等用电属性。
2)可以通过新装增容及变更用电归档等业务，由实体转入产生记录。
3)该实体主要由查询客户用电基本信息等业务使用。
 *
 * @author baocj
 * @since 1.0.0
 */
@Entity
@Table(name = "C_CONS")
@SequenceGenerator(sequenceName = "SEQ_C_CONS", name = "SEQ_C_CONS")
public class ConsInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8089830407114743595L;

	@Column(name = "CONS_ID", unique = true, nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_C_CONS")
	// CONS_ID NUMBER(16) not null,is '本实体记录的唯一标识，产生规则为流水号';
	private Long consId;

	// CONS_NO VARCHAR2(16) not null,is '用电客户的外部标识 引用国家电网公司营销管理代码类集:5110.1
	// 用电客户编号规则';
	@Column(name = "CONS_NO", length = 16, nullable = false)
	private String consNo;

	// CONS_NAME VARCHAR2(256) not null,is
	// '用户的名称，一般等于客户实体中的客户名称，但也允许附加上一些非自然的信息。如 XXX（东城），便于通过用户名称直接识别。';
	@Column(name = "CONS_NAME", length = 256, nullable = false)
	private String consName;

	// CONS_SORT_CODE VARCHAR2(8),is '用户一种常用的分类方式，方便用户的管理01 高压，02 低压非居民，03
	// 低压居民';
	@Column(name = "CONS_SORT_CODE", length = 8)
	private String consSortCode;

	// ELEC_ADDR VARCHAR2(256) not null,is '用电客户的用电地址';
	@Column(name = "ELEC_ADDR", length = 256)
	private String elecAddr;

	// TRADE_CODE VARCHAR2(8),is '用电客户的行业分类代码引用国标GB/T 4754-2002';
	@Column(name = "TRADE_CODE", length = 8)
	private String tradeCode;

	// ELEC_TYPE_CODE VARCHAR2(8),is '用电客户的用电类别分类引用国家电网公司营销管理代码类集:5110.4
	// 用电类别大工业用电，中小化肥，居民生活用电，农业生产用电，贫困县农业排灌用电';
	@Column(name = "ELEC_TYPE_CODE", length = 8)
	private String elecTypeCode;

	// CONTRACT_CAP NUMBER(16,6),is '合同约定的本用户的容量';
	@Column(name = "CONTRACT_CAP", precision = 16, scale = 6)
	private Double contractCap;

	// RUN_CAP NUMBER(16,6),is '用电客户正在使用的合同容量，如暂停客户，在暂停期间其运行容量等于合同容量减去已暂停的容量';
	@Column(name = "RUN_CAP", precision = 16, scale = 6)
	private String runCap;

	// SHIFT_NO VARCHAR2(8),is
	// '用电客户的生产班次分类引用国家电网公司营销管理代码类集:5110.6用电客户生产班次代码单班，二班，三班，连续生产;
	@Column(name = "SHIFT_NO", length = 8)
	private String shiftNo;

	// LODE_ATTR_CODE VARCHAR2(8),is
	// '负荷的重要程度分类引用国家电网公司营销管理代码类集:5110.44负荷类别分类与代码一类，二类，三类';
	@Column(name = "LODE_ATTR_CODE", length = 8)
	private String lodeAttrCode;

	// VOLT_CODE VARCHAR2(8),is
	// '用电客户的供电电压等级代码，多路电源时取电压等级最高的供电电压等级代码引用《国家电网公司信息分类与代码体系－综合代码类集－电压等级代码表》';
	@Column(name = "VOLT_CODE", length = 8)
	private String voltCode;

	// HEC_INDUSTRY_CODE VARCHAR2(8),is '依据国家最新的高耗能行业划分';
	@Column(name = "HEC_INDUSTRY_CODE", length = 8)
	private String hecIndustryCode;

	// HOLIDAY VARCHAR2(32),is
	// '周休日通过数字连续表示周休哪几天，类似于飞机航班日期表示，如1.2.3,表示星期一星期二和星期三休息。';
	@Column(name = "HOLIDAY", length = 32)
	private String holiday;

	// BUILD_DATE DATE,is '电子用户档案的首次建立日期';
	@Column(name = "BUILD_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date buildDate;

	// PS_DATE DATE,is '用户的首次送电日期';
	@Column(name = "PS_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date psDate;

	// CANCEL_DATE DATE,is '销户业务信息归档的日期';
	@Column(name = "CANCEL_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date cancelDate;

	// DUE_DATE DATE,is '临时用电客户约定的用电到期日期';
	@Column(name = "DUE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dueDate;

	// STATUS_CODE VARCHAR2(8),is
	// '用电客户的状态说明，说明客户是否处于业扩变更中或已销户引用国家电网公司营销管理代码类集:5110.9
	// 客户状态标志代码正常用电客户，当前新装客户，当前变更客户，已销户客户';
	@Column(name = "STATUS_CODE", length = 16)
	private String statusCode;

	// ORG_ID NUMBER,is '供电单位编码，一般是指的用户的直接供电管理单位，也可以是大客户管理中心等由于管理原因产生的客户管理单位';
	@Column(name = "ORG_ID")
	private Long orgId;

	// RRIO_CODE VARCHAR2(8),is '客户重要性等级：特级、一级、二级';
	@Column(name = "RRIO_CODE", length = 8)
	private String prioCode;

	// CHK_CYCLE NUMBER(5),is '检查周期(单位：月)：用于存放客户检查周期信息，便于周期检查计划制定时，获取参数。';
	@Column(name = "CHK_CYCLE", length = 5)
	private Long chkCycle;

	// POWEROFF_CODE VARCHAR2(8),is '停电标志：01 已停电 02 未停电，反映客户当前是否处于停电状态';
	@Column(name = "POWEROFF_CODE", length = 8)
	private String poweroffCode;

	// MR_SECT_NO VARCHAR2(16) not null,is '抄表段标识,用于表示用电客户所属的抄表段';
	@Column(name = "MR_SECT_NO", length = 16)
	private String mrSectNo;

	@Column(name = "LASTTIME_STAMP")
	@Temporal(TemporalType.TIMESTAMP)
	// LASTTIME_STAMP DATE default SYSDATE ,最后表结构修改时间戳
	private Date lasttimeStamp;

	// PINYIN_CODE VARCHAR2(16)
	@Column(name = "PINYIN_CODE", length = 16)
	private String pingyinCode;

	/**
	 * @return the buildDate
	 */
	public Date getBuildDate() {
		return buildDate;
	}

	/**
	 * @return the cancelDate
	 */
	public Date getCancelDate() {
		return cancelDate;
	}

	/**
	 * @return the chkCycle
	 */
	public Long getChkCycle() {
		return chkCycle;
	}

	/**
	 * @return the consId
	 */
	public Long getConsId() {
		return consId;
	}

	/**
	 * @return the consName
	 */
	public String getConsName() {
		return consName;
	}

	/**
	 * @return the consNo
	 */
	public String getConsNo() {
		return consNo;
	}

	/**
	 * @return the consSortCode
	 */
	public String getConsSortCode() {
		return consSortCode;
	}

	/**
	 * @return the contractCap
	 */
	public Double getContractCap() {
		return contractCap;
	}

	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * @return the elecAddr
	 */
	public String getElecAddr() {
		return elecAddr;
	}

	/**
	 * @return the elecTypeCode
	 */
	public String getElecTypeCode() {
		return elecTypeCode;
	}

	/**
	 * @return the hecIndustryCode
	 */
	public String getHecIndustryCode() {
		return hecIndustryCode;
	}

	/**
	 * @return the holiday
	 */
	public String getHoliday() {
		return holiday;
	}

	/**
	 * @return the lasttimeStamp
	 */
	public Date getLasttimeStamp() {
		return lasttimeStamp;
	}

	/**
	 * @return the lodeAttrCode
	 */
	public String getLodeAttrCode() {
		return lodeAttrCode;
	}

	/**
	 * @return the mrSectNo
	 */
	public String getMrSectNo() {
		return mrSectNo;
	}

	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @return the pingyinCode
	 */
	public String getPingyinCode() {
		return pingyinCode;
	}

	/**
	 * @return the poweroffCode
	 */
	public String getPoweroffCode() {
		return poweroffCode;
	}

	/**
	 * @return the prioCode
	 */
	public String getPrioCode() {
		return prioCode;
	}

	/**
	 * @return the psDate
	 */
	public Date getPsDate() {
		return psDate;
	}

	/**
	 * @return the runCap
	 */
	public String getRunCap() {
		return runCap;
	}

	/**
	 * @return the shiftNo
	 */
	public String getShiftNo() {
		return shiftNo;
	}

	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * @return the tradeCode
	 */
	public String getTradeCode() {
		return tradeCode;
	}

	/**
	 * @return the voltCode
	 */
	public String getVoltCode() {
		return voltCode;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/**
	 * @param buildDate the buildDate to set
	 */
	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
	}

	/**
	 * @param cancelDate the cancelDate to set
	 */
	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	/**
	 * @param chkCycle the chkCycle to set
	 */
	public void setChkCycle(Long chkCycle) {
		this.chkCycle = chkCycle;
	}

	/**
	 * @param consId the consId to set
	 */
	public void setConsId(Long consId) {
		this.consId = consId;
	}

	/**
	 * @param consName the consName to set
	 */
	public void setConsName(String consName) {
		this.consName = consName;
	}

	/**
	 * @param consNo the consNo to set
	 */
	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	/**
	 * @param consSortCode the consSortCode to set
	 */
	public void setConsSortCode(String consSortCode) {
		this.consSortCode = consSortCode;
	}

	/**
	 * @param contractCap the contractCap to set
	 */
	public void setContractCap(Double contractCap) {
		this.contractCap = contractCap;
	}

	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * @param elecAddr the elecAddr to set
	 */
	public void setElecAddr(String elecAddr) {
		this.elecAddr = elecAddr;
	}

	/**
	 * @param elecTypeCode the elecTypeCode to set
	 */
	public void setElecTypeCode(String elecTypeCode) {
		this.elecTypeCode = elecTypeCode;
	}

	/**
	 * @param hecIndustryCode the hecIndustryCode to set
	 */
	public void setHecIndustryCode(String hecIndustryCode) {
		this.hecIndustryCode = hecIndustryCode;
	}

	/**
	 * @param holiday the holiday to set
	 */
	public void setHoliday(String holiday) {
		this.holiday = holiday;
	}

	/**
	 * @param lasttimeStamp the lasttimeStamp to set
	 */
	public void setLasttimeStamp(Date lasttimeStamp) {
		this.lasttimeStamp = lasttimeStamp;
	}

	/**
	 * @param lodeAttrCode the lodeAttrCode to set
	 */
	public void setLodeAttrCode(String lodeAttrCode) {
		this.lodeAttrCode = lodeAttrCode;
	}

	/**
	 * @param mrSectNo the mrSectNo to set
	 */
	public void setMrSectNo(String mrSectNo) {
		this.mrSectNo = mrSectNo;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * @param pingyinCode the pingyinCode to set
	 */
	public void setPingyinCode(String pingyinCode) {
		this.pingyinCode = pingyinCode;
	}

	/**
	 * @param poweroffCode the poweroffCode to set
	 */
	public void setPoweroffCode(String poweroffCode) {
		this.poweroffCode = poweroffCode;
	}

	/**
	 * @param prioCode the prioCode to set
	 */
	public void setPrioCode(String prioCode) {
		this.prioCode = prioCode;
	}

	/**
	 * @param psDate the psDate to set
	 */
	public void setPsDate(Date psDate) {
		this.psDate = psDate;
	}

	/**
	 * @param runCap the runCap to set
	 */
	public void setRunCap(String runCap) {
		this.runCap = runCap;
	}

	/**
	 * @param shiftNo the shiftNo to set
	 */
	public void setShiftNo(String shiftNo) {
		this.shiftNo = shiftNo;
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @param tradeCode the tradeCode to set
	 */
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	/**
	 * @param voltCode the voltCode to set
	 */
	public void setVoltCode(String voltCode) {
		this.voltCode = voltCode;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
