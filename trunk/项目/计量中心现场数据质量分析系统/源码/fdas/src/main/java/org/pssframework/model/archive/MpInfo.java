/**
 * 
 */
package org.pssframework.model.archive;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.pssframework.base.BaseEntity;

/**
 * 1)用于记录需要安装计量装置的位置点的信息，可以解决一个正反向表被两个户分别使用，这时计量点定义成两个；可以解决三个单相表代替一个三相表的功能，这时计量点定义成一个；可以解决主副表问题，这时计量点可以定义成一个。
 * 定义了计量点的自然属性，本实体主要包括计量点编号、计量点名称、计量点地址、计量点分类、计量点性质等属性。 2)通过新装、增容及变更用电归档、关口计量点新装及变更归档等业务，由实体转入产生记录。
 * 3)该实体主要由查询计量点相关信息等业务使用。
 * 
 * @author baocj
 * @since 1.0.0
 */
@Entity
@Table(name = "C_MP")
@SequenceGenerator(sequenceName = "SEQ_C_MP", name = "SEQ_C_MP", allocationSize = 1)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "gpInfos" })
public class MpInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7955591493012641936L;

	@Column(name = "MP_ID", unique = true, nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_C_MP")
	// MP_ID NUMBER(16) not null, 容器所属的计量点唯一标识号
	private Long mpId;

	@OneToMany(mappedBy = "mpInfo", cascade = CascadeType.ALL)
	private List<GpInfo> gpInfos;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "METER_ID")
	private MeterInfo meterInfo;

	@OneToOne(mappedBy = "mpInfo")
	private MpUseInfo mpUseInfo;

	@Column(name = "MP_NO", length = 16, nullable = false)
	// MP_NO VARCHAR2(256),用户自己编写的编号，默认跟标识一致'
	private String mpNo;

	@Column(name = "MP_NAME", length = 256)
	// MP_NAME VARCHAR2(256),计量点名称
	private String mpName;

	@Column(name = "MP_ADDR", length = 256)
	// MP_ADDR VARCHAR2(256),计量点地址
	private String mpAddr;

	@Column(name = "TYPE_CODE", length = 256)
	// TYPE_CODE VARCHAR2(256),'定义计量点的主要分类，包括：01用电客户、02关口等';
	private String typeCode;

	@Column(name = "MP_ATTR_CODE", length = 8)
	// MP_ATTR_CODE VARCHAR2(8), is '定义计量点的主要性质，包括：01结算、02考核等';
	private String mpAttrCode;

	@Column(name = "USAGE_TYPE_CODE", length = 8)
	// USAGE_TYPE_CODE VARCHAR2(8), is
	// '定义计量点的主要用途，引用国家电网公司营销管理代码类集:5110.19电能计量点类型分类与代码（01售电侧结算、02台区供电考核、03线路供电考核、04指标分析、05趸售供电关口、06地市供电关口、07省级供电关口、08跨省输电关口、09跨区输电关口、10跨国输电关口、11发电上网关口......）';
	private String usageTypeCode;

	@Column(name = "SIDE_CODE", length = 8)
	// SIDE_CODE VARCHAR2(8),is '标明计量点所属的具体位置，包括：01变电站内、02变电站外、03高压侧、04低压侧等';
	private String sideCode;

	@Column(name = "VOLT_CODE", length = 8)
	// VOLT_CODE VARCHAR2(8),is
	// '标明计量点的电压等级，引用国家电网公司信息分类与代码体系－综合代码类集－电压等级代码表，包括：01 10KV、02 110KV、03
	// 220KV、04 35KV、05 220V、06 6KV、07 380V、08 500KV等';
	private String voltCode;

	@Column(name = "APP_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	// APP_DATE DATE,is '标明计量点申请设立的日期';
	private Date appDate;

	@Column(name = "RUN_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	// APP_DATE DATE, is '投运日期';
	private Date runDate;

	@Column(name = "WIRING_MODE", length = 8)
	// SIDE_CODE VARCHAR2(8), is
	// '引用国家电网公司营销管理代码类集:5110.84电能表接线方式分类与代码（1单相、2三相三线、3三相四线）';
	private String wiringMode;

	// MEAS_MODE VARCHAR2(8), is
	// '引用国家电网公司营销管理代码类集:5110.33电能计量方式代码（1高供高计、2高供低计、3低供低计）';
	@Column(name = "MEAS_MODE", length = 8)
	private String measMode;

	// ORG_ID NUMBER,is '供电单位编码，一般是指的用户的直接供电管理单位，也可以是大客户管理中心等由于管理原因产生的客户管理单位';
	@Column(name = "ORG_ID", length = 16)
	private Long orgId;

	// SWITCH_NO VARCHAR2(32),is '标明计量点所属的开关编号';
	@Column(name = "SWITCH_NO", length = 32)
	private String switchNo;

	// MR_SECT_NO VARCHAR2(16),is '标明计量点所属的抄表段编号';
	@Column(name = "MR_SECT_NO", length = 16)
	private String mrSectNo;

	// LINE_ID NUMBER(16), is '线路的系统内部唯一标识';
	// @Column(name = "LINE_ID", length = 16)
	// private Long lineId;

	// TG_ID NUMBER(16),is '台区的唯一标识';

	//	@ManyToOne
	//	@JoinColumn(name = "TG_ID")
	//	private TgInfo tgInfo;

	@Column(name = "TG_ID")
	private Long tgId;

	// EXCHG_TYPE_CODE VARCHAR2(8),is
	// '标明计量点的电量交换对象，包括：01发电企业、02区域电网、03省级企业、04地市企业、05趸售单位等';
	@Column(name = "EXCHG_TYPE_CODE", length = 8)
	private String exchgTypeCode;

	// MD_TYPE_CODE VARCHAR2(8),is
	// '引用国家电网公司营销管理代码类集:5110.32电能计量装置分类与代码（1Ⅰ类计量装置、2Ⅱ类计量装置、3Ⅲ类计量装置、4Ⅳ类计量装置、5Ⅴ类计量装置......）';
	@Column(name = "MD_TYPE_CODE", length = 8)
	private String mdTypeCoe;

	// MR_SN NUMBER(5),is '定义计量点的抄表顺序';
	@Column(name = "MR_SN", length = 8)
	private Long mrSn;

	// METER_FLAG VARCHAR2(8),is '标明计量点是否安装计量装置，包括：01是、02否';
	@Column(name = "METER_FLAG", length = 8)
	private String meterFlag;

	// STATUS_CODE VARCHAR2(8),is '标明计量点的当前状态，包括：01设立、02在用、03停用、04撤销等';
	@Column(name = "STATUS_CODE", length = 8)
	private String statusCode;

	// LC_FLAG VARCHAR2(8),is '是否安装负控设备，01是、02否';
	@Column(name = "LC_FLAG", length = 8)
	private String lcFlag;

	// CONS_ID NUMBER(16),is '用户标识';
	@Column(name = "CONS_ID", length = 16)
	private Long consId;

	@Column(name = "LASTTIME_STAMP")
	@Temporal(TemporalType.TIMESTAMP)
	// LASTTIME_STAMP DATE default SYSDATE ,最后表结构修改时间戳
	private Date lasttimeStamp;

	// PINYIN_CODE VARCHAR2(16)
	@Column(name = "PINYIN_CODE", length = 16)
	private String pingyinCode;

	/**
	 * @return the appDate
	 */
	public Date getAppDate() {
		return appDate;
	}

	/**
	 * @return the consId
	 */
	public Long getConsId() {
		return consId;
	}

	/**
	 * @return the exchgTypeCode
	 */
	public String getExchgTypeCode() {
		return exchgTypeCode;
	}

	/**
	 * @return the lasttimeStamp
	 */
	public Date getLasttimeStamp() {
		return lasttimeStamp;
	}

	/**
	 * @return the lcFlag
	 */
	public String getLcFlag() {
		return lcFlag;
	}

	/**
	 * @return the mdTypeCoe
	 */
	public String getMdTypeCoe() {
		return mdTypeCoe;
	}

	/**
	 * @return the measMode
	 */
	public String getMeasMode() {
		return measMode;
	}

	/**
	 * @return the meterFlag
	 */
	public String getMeterFlag() {
		return meterFlag;
	}

	/**
	 * @return the mpNo
	 */
	public String getMpNo() {
		return mpNo;
	}

	/**
	 * @return the mpAddr
	 */
	public String getMpAddr() {
		return mpAddr;
	}

	/**
	 * @return the mpAttrCode
	 */
	public String getMpAttrCode() {
		return mpAttrCode;
	}

	/**
	 * @return the mpId
	 */
	public Long getMpId() {
		return mpId;
	}

	/**
	 * @return the mpName
	 */
	public String getMpName() {
		return mpName;
	}

	/**
	 * @return the mrSectNo
	 */
	public String getMrSectNo() {
		return mrSectNo;
	}

	/**
	 * @return the mrSn
	 */
	public Long getMrSn() {
		return mrSn;
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
	 * @return the runDate
	 */
	public Date getRunDate() {
		return runDate;
	}

	/**
	 * @return the sideCode
	 */
	public String getSideCode() {
		return sideCode;
	}

	/**
	 * @return the switchNo
	 */
	public String getSwitchNo() {
		return switchNo;
	}

	/**
	 * @return the typeCode
	 */
	public String getTypeCode() {
		return typeCode;
	}

	/**
	 * @return the usageTypeCode
	 */
	public String getUsageTypeCode() {
		return usageTypeCode;
	}

	/**
	 * @return the voltCode
	 */
	public String getVoltCode() {
		return voltCode;
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
	 * @param appDate
	 *            the appDate to set
	 */
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}

	/**
	 * @param consId
	 *            the consId to set
	 */
	public void setConsId(Long consId) {
		this.consId = consId;
	}

	/**
	 * @param exchgTypeCode
	 *            the exchgTypeCode to set
	 */
	public void setExchgTypeCode(String exchgTypeCode) {
		this.exchgTypeCode = exchgTypeCode;
	}

	/**
	 * @param lasttimeStamp
	 *            the lasttimeStamp to set
	 */
	public void setLasttimeStamp(Date lasttimeStamp) {
		this.lasttimeStamp = lasttimeStamp;
	}

	/**
	 * @param lcFlag
	 *            the lcFlag to set
	 */
	public void setLcFlag(String lcFlag) {
		this.lcFlag = lcFlag;
	}

	/**
	 * @param mdTypeCoe
	 *            the mdTypeCoe to set
	 */
	public void setMdTypeCoe(String mdTypeCoe) {
		this.mdTypeCoe = mdTypeCoe;
	}

	/**
	 * @param measMode
	 *            the measMode to set
	 */
	public void setMeasMode(String measMode) {
		this.measMode = measMode;
	}

	/**
	 * @param meterFlag
	 *            the meterFlag to set
	 */
	public void setMeterFlag(String meterFlag) {
		this.meterFlag = meterFlag;
	}

	/**
	 * @param mpNo
	 *            the mpNo to set
	 */
	public void setMpNo(String mpNo) {
		this.mpNo = mpNo;
	}

	/**
	 * @param mpAddr
	 *            the mpAddr to set
	 */
	public void setMpAddr(String mpAddr) {
		this.mpAddr = mpAddr;
	}

	/**
	 * @param mpAttrCode
	 *            the mpAttrCode to set
	 */
	public void setMpAttrCode(String mpAttrCode) {
		this.mpAttrCode = mpAttrCode;
	}

	/**
	 * @param mpId
	 *            the mpId to set
	 */
	public void setMpId(Long mpId) {
		this.mpId = mpId;
	}

	/**
	 * @param mpName
	 *            the mpName to set
	 */
	public void setMpName(String mpName) {
		this.mpName = mpName;
	}

	/**
	 * @param mrSectNo
	 *            the mrSectNo to set
	 */
	public void setMrSectNo(String mrSectNo) {
		this.mrSectNo = mrSectNo;
	}

	/**
	 * @param mrSn
	 *            the mrSn to set
	 */
	public void setMrSn(Long mrSn) {
		this.mrSn = mrSn;
	}

	/**
	 * @param orgId
	 *            the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * @param pingyinCode
	 *            the pingyinCode to set
	 */
	public void setPingyinCode(String pingyinCode) {
		this.pingyinCode = pingyinCode;
	}

	/**
	 * @param runDate
	 *            the runDate to set
	 */
	public void setRunDate(Date runDate) {
		this.runDate = runDate;
	}

	/**
	 * @param sideCode
	 *            the sideCode to set
	 */
	public void setSideCode(String sideCode) {
		this.sideCode = sideCode;
	}

	/**
	 * @param switchNo
	 *            the switchNo to set
	 */
	public void setSwitchNo(String switchNo) {
		this.switchNo = switchNo;
	}

	/**
	 * @param typeCode
	 *            the typeCode to set
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	/**
	 * @param usageTypeCode
	 *            the usageTypeCode to set
	 */
	public void setUsageTypeCode(String usageTypeCode) {
		this.usageTypeCode = usageTypeCode;
	}

	/**
	 * @param voltCode
	 *            the voltCode to set
	 */
	public void setVoltCode(String voltCode) {
		this.voltCode = voltCode;
	}

	/**
	 * @param wiringMode
	 *            the wiringMode to set
	 */
	public void setWiringMode(String wiringMode) {
		this.wiringMode = wiringMode;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * @param mpUseInfo
	 *            the mpUseInfo to set
	 */
	public void setMpUseInfo(MpUseInfo mpUseInfo) {
		this.mpUseInfo = mpUseInfo;
	}

	/**
	 * @return the mpUseInfo
	 */
	public MpUseInfo getMpUseInfo() {
		return mpUseInfo;
	}

	/**
	 * @param meterInfo the meterInfo to set
	 */
	public void setMeterInfo(MeterInfo meterInfo) {
		this.meterInfo = meterInfo;
	}

	/**
	 * @return the meterInfo
	 */
	public MeterInfo getMeterInfo() {
		return meterInfo;
	}

	/**
	 * @param gpInfos the gpInfos to set
	 */
	public void setGpInfos(List<GpInfo> gpInfos) {
		this.gpInfos = gpInfos;
	}

	/**
	 * @return the gpInfos
	 */
	public List<GpInfo> getGpInfos() {
		return gpInfos;
	}

	/**
	 * @param tgInfo the tgInfo to set
	 */
	//	public void setTgInfo(TgInfo tgInfo) {
	//		this.tgInfo = tgInfo;
	//	}

	/**
	 * @return the tgInfo
	 */
	//	public TgInfo getTgInfo() {
	//		return tgInfo;
	//	}

	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the tgId
	 */
	public Long getTgId() {
		return tgId;
	}

	/**
	 * @param tgId the tgId to set
	 */
	public void setTgId(Long tgId) {
		this.tgId = tgId;
	}

	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}
}
