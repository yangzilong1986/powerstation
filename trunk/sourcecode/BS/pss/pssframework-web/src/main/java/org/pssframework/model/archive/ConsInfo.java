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

	@Column(name = "CONS_ID", unique = true, nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_C_CONS")
	//CONS_ID           NUMBER(16) not null,is '本实体记录的唯一标识，产生规则为流水号';
		private Long consId;
	  
	  //CONS_NO           VARCHAR2(16) not null,is '用电客户的外部标识 引用国家电网公司营销管理代码类集:5110.1  用电客户编号规则';
	@Column(name = "CONS_NO", length = 16, nullable = false)
	private String consNo;
	
	
	//CONS_NAME         VARCHAR2(256) not null,is '用户的名称，一般等于客户实体中的客户名称，但也允许附加上一些非自然的信息。如 XXX（东城），便于通过用户名称直接识别。';
	@Column(name = "CONS_NAME", length = 256)
	private String consName;
	
	
	//CONS_SORT_CODE    VARCHAR2(8),is '用户一种常用的分类方式，方便用户的管理01 高压，02 低压非居民，03 低压居民';
	@Column(name = "CONS_SORT_CODE", length = 8)
	private String consSortCode; 
	
	
	
	//ELEC_ADDR         VARCHAR2(256) not null,is '用电客户的用电地址';
	@Column(name = "ELEC_ADDR", length = 256)
	private String elecAddr; 
	
	
	//TRADE_CODE        VARCHAR2(8),is '用电客户的行业分类代码引用国标GB/T 4754-2002';
	@Column(name = "TRADE_CODE", length = 8)
	private String tradeCode;
	
	//ELEC_TYPE_CODE    VARCHAR2(8),is '用电客户的用电类别分类引用国家电网公司营销管理代码类集:5110.4 用电类别大工业用电，中小化肥，居民生活用电，农业生产用电，贫困县农业排灌用电';
	@Column(name = "ELEC_TYPE_CODE", length = 8)
	private String elecTypeCode;  
	
	//CONTRACT_CAP      NUMBER(16,6),is '合同约定的本用户的容量';
	@Column(name = "CONTRACT_CAP", precision = 16,scale =6)
	private String contractCap;
	
	
	//RUN_CAP           NUMBER(16,6),is '用电客户正在使用的合同容量，如暂停客户，在暂停期间其运行容量等于合同容量减去已暂停的容量';
	@Column(name = "RUN_CAP", precision = 16,scale =6)
	private String runCap;
	
	//SHIFT_NO          VARCHAR2(8),is '用电客户的生产班次分类引用国家电网公司营销管理代码类集:5110.6用电客户生产班次代码单班，二班，三班，连续生产;
	@Column(name = "SHIFT_NO", length = 8)
	private String shiftNo;

	//LODE_ATTR_CODE    VARCHAR2(8),is '负荷的重要程度分类引用国家电网公司营销管理代码类集:5110.44负荷类别分类与代码一类，二类，三类';
	@Column(name = "LODE_ATTR_CODE", length = 8)
	private String lodeAttrCode;

	//VOLT_CODE         VARCHAR2(8),is '用电客户的供电电压等级代码，多路电源时取电压等级最高的供电电压等级代码引用《国家电网公司信息分类与代码体系－综合代码类集－电压等级代码表》';
	//HEC_INDUSTRY_CODE VARCHAR2(8),is '依据国家最新的高耗能行业划分';
	//HOLIDAY           VARCHAR2(32),is '周休日通过数字连续表示周休哪几天，类似于飞机航班日期表示，如1.2.3,表示星期一星期二和星期三休息。';
	//BUILD_DATE        DATE,is '电子用户档案的首次建立日期';
	//PS_DATE           DATE,is '用户的首次送电日期';
	//CANCEL_DATE       DATE,is '销户业务信息归档的日期';
	// DUE_DATE          DATE,is '临时用电客户约定的用电到期日期';
	//STATUS_CODE       VARCHAR2(8),is '用电客户的状态说明，说明客户是否处于业扩变更中或已销户引用国家电网公司营销管理代码类集:5110.9 客户状态标志代码正常用电客户，当前新装客户，当前变更客户，已销户客户';
	//ORG_ID            NUMBER,is '供电单位编码，一般是指的用户的直接供电管理单位，也可以是大客户管理中心等由于管理原因产生的客户管理单位';
	//RRIO_CODE         VARCHAR2(8),is '客户重要性等级：特级、一级、二级';
	//CHK_CYCLE         NUMBER(5),is '检查周期(单位：月)：用于存放客户检查周期信息，便于周期检查计划制定时，获取参数。';
	//POWEROFF_CODE     VARCHAR2(8),is '停电标志：01 已停电  02 未停电，反映客户当前是否处于停电状态';
	//MR_SECT_NO        VARCHAR2(16) not null,is '抄表段标识,用于表示用电客户所属的抄表段';
	//LASTTIME_STAMP    DATE default SYSDATE,is '最后表结构修改时间戳';
	//PINYIN_CODE       VARCHAR2(16),'拼音code';'
	  




	//TG_ID              NUMBER(16), 台区标识
	@ManyToOne(targetEntity = org.pssframework.model.archive.TgInfo.class, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "TG_ID", nullable = false)
	private Long tgId;

	// ORG_NO          VARCHAR2(16) not null, 部门


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
	private Long plateCap;

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
	public Long getPlateCap() {
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
	public void setPlateCap(Long plateCap) {
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
