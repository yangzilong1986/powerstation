/**
 * 
 */
package org.pssframework.model.archive;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.pssframework.base.BaseEntity;
import org.pssframework.model.system.SmsNoInfo;

/**
 * 1)用于记录需要安装计量装置的位置点的信息，可以解决一个正反向表被两个户分别使用，这时计量点定义成两个；可以解决三个单相表代替一个三相表的功能，这时计量点定义成一个；可以解决主副表问题，这时计量点可以定义成一个。
 * 定义了计量点的自然属性，本实体主要包括计量点编号、计量点名称、计量点地址、计量点分类、计量点性质等属性。 2)通过新装、增容及变更用电归档、关口计量点新装及变更归档等业务，由实体转入产生记录。
 * 3)该实体主要由查询计量点相关信息等业务使用。
 * 
 * @author baocj
 * @since 1.0.0
 */
@Entity
@Table(name = "C_PS")
@SequenceGenerator(sequenceName = "SEQ_C_PS", name = "SEQ_C_PS", allocationSize = 1)
public class PsInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8867190707604766699L;

	@Column(name = "PS_ID", unique = true, nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_C_PS")
	// PS_ID NUMBER not null,is '漏点保护开关(leakage protection switch)';
	private Long psId;

	@ManyToOne(targetEntity = TerminalInfo.class)
	@JoinColumn(name = "TERM_ID", referencedColumnName = "TERM_ID")
	// TERM_ID NUMBER,is'终端号'
	private TerminalInfo terminalInfo;

	// ASSET_NO VARCHAR2(20),is '资产号';
	@Column(name = "ASSET_NO", length = 20)
	private String assetNo;

	// @Column(name = "TERM_ID")
	// // TERM_ID NUMBER,is'终端号'
	// private Long termId;

	// MODEL_CODE VARCHAR2(5)is '漏保型号 : 见编码PS_MODEL';
	@Column(name = "MODEL_CODE", length = 5)
	private String modelCode;

	// COMM_MODE_GM VARCHAR2(5),is '通讯方式 : 见编码COMM_MODE_GM';
	@Column(name = "COMM_MODE_GM", length = 5)
	private String commModeGm;

	// BTL VARCHAR2(5),is '见编码BTL';
	@Column(name = "BTL", length = 5)
	private String btl;

	// RATED_EC VARCHAR2(5),is '见编码RATED_EC';
	@Column(name = "RATED_EC", length = 5)
	private String ratedEc;

	// REMC_GEAR VARCHAR2(5),is '1-5档 （5 为自动档）';
	@Column(name = "REMC_GEAR", length = 5)
	private String remcGear;

	// REMC_GEAR_VALUE VARCHAR2(5),is '见编码REM_EC';
	@Column(name = "REMC_GEAR_VALUE", length = 5)
	private String remcGearValue;

	// OFF_DELAY_GEAR VARCHAR2(5),is '（1—2）';
	@Column(name = "OFF_DELAY_GEAR", length = 5)
	private String offDelayGear;

	// OFF_DELAY_VALUE VARCHAR2(5),is '见编码OFF_DELAY_VALUE';
	@Column(name = "OFF_DELAY_VALUE", length = 5)
	private String offDelayValue;

	@Column(name = "FUNCTION_CODE")
	private String functionCode;

	// PS_TYPE VARCHAR2(5),is '1：总保；2：二级保';
	@Column(name = "PS_TYPE", length = 5)
	private String psType;

	// FUNCTION_CODE VARCHAR2(5),is '功能号'

	@Column(name = "LASTTIME_STAMP")
	@Temporal(TemporalType.TIMESTAMP)
	// LASTTIME_STAMP DATE default SYSDATE ,最后表结构修改时间戳
	private Date lasttimeStamp;

	// PINYIN_CODE VARCHAR2(16)
	@Column(name = "PINYIN_CODE", length = 16)
	private String pingyinCode;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "GP_ID")
	private GpInfo gpInfo;

	@Transient
	private Map<String, Integer> functionMap;

	@Transient
	private int[] functionsChecked;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "C_PS_SMS_RELA", joinColumns = { @JoinColumn(name = "PS_ID") }, inverseJoinColumns = { @JoinColumn(name = "SMS_ID") })
	@Fetch(FetchMode.SUBSELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<SmsNoInfo> smsNoInfoList;

	public List<SmsNoInfo> getSmsNoInfoList() {
		//		SmsNoInfo smsNoInfo = new SmsNoInfo();
		//		if (smsNoInfoList != null) {
		//			int size = smsNoInfoList.size();
		//			if (size == 3)
		//				return smsNoInfoList;
		//
		//			for (int i = 0; i < 3 - size; i++) {
		//				smsNoInfoList.add(smsNoInfo);
		//			}
		//		} else {
		//			smsNoInfoList = Lists.newArrayListWithCapacity(3);
		//			for (int i = 0; i < 3; i++) {
		//				smsNoInfoList.add(smsNoInfo);
		//			}
		//		}

		return smsNoInfoList;
	}

	/**
	 * @param smsNoInfoList the smsNoInfoList to set
	 */
	public void setSmsNoInfoList(List<SmsNoInfo> smsNoInfoList) {
		this.smsNoInfoList = smsNoInfoList;
	}

	/**
	 * @return the functionMap
	 */
	public Map<String, Integer> getFunctionMap() {

		Map<String, Integer> functionMap = new LinkedHashMap<String, Integer>();
		functionMap.put("欠压保护", 0);
		functionMap.put("过压保护", 1);
		functionMap.put("突变保护", 2);
		functionMap.put("缓变保护 ", 3);
		functionMap.put("特波保护", 4);
		functionMap.put("自动跟踪", 5);
		functionMap.put("告警功能", 6);
		functionMap.put("特波30mA", 7);

		return functionMap;
	}

	/**
	 * @param functionMap the functionMap to set
	 */
	public void setFunctionMap(Map<String, Integer> functionMap) {

		this.functionMap = functionMap;
	}

	/**
	 * @return the assetNo
	 */
	public String getAssetNo() {
		return assetNo;
	}

	/**
	 * @return the btl
	 */
	public String getBtl() {
		return btl;
	}

	/**
	 * @return the commModeGm
	 */
	public String getCommModeGm() {
		return commModeGm;
	}

	/**
	 * @return the functionCode
	 */
	public String getFunctionCode() {
		return functionCode;
	}

	/**
	 * @return the gpInfo
	 */
	public GpInfo getGpInfo() {
		return gpInfo;
	}

	/**
	 * @return the lasttimeStamp
	 */
	public Date getLasttimeStamp() {
		return lasttimeStamp;
	}

	/**
	 * @return the modelCode
	 */
	public String getModelCode() {
		return modelCode;
	}

	/**
	 * @return the offDelayGear
	 */
	public String getOffDelayGear() {
		return offDelayGear;
	}

	/**
	 * @return the offDelayValue
	 */
	public String getOffDelayValue() {
		return offDelayValue;
	}

	/**
	 * @return the pingyinCode
	 */
	public String getPingyinCode() {
		return pingyinCode;
	}

	/**
	 * @return the psId
	 */
	public Long getPsId() {
		return psId;
	}

	/**
	 * @return the psType
	 */
	public String getPsType() {
		return psType;
	}

	/**
	 * @return the ratedEc
	 */
	public String getRatedEc() {
		return ratedEc;
	}

	/**
	 * @return the remcGear
	 */
	public String getRemcGear() {
		return remcGear;
	}

	/**
	 * @return the remcGearValue
	 */
	public String getRemcGearValue() {
		return remcGearValue;
	}

	/**
	 * @return the terminalInfo
	 */
	public TerminalInfo getTerminalInfo() {
		return terminalInfo;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/**
	 * @param assetNo
	 *            the assetNo to set
	 */
	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}

	/**
	 * @param btl
	 *            the btl to set
	 */
	public void setBtl(String btl) {
		this.btl = btl;
	}

	/**
	 * @param commModeGm
	 *            the commModeGm to set
	 */
	public void setCommModeGm(String commModeGm) {
		this.commModeGm = commModeGm;
	}

	/**
	 * @param functionCode
	 *            the functionCode to set
	 */
	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	/**
	 * @param gpInfo
	 *            the gpInfo to set
	 */
	public void setGpInfo(GpInfo gpInfo) {
		this.gpInfo = gpInfo;
	}

	/**
	 * @param lasttimeStamp
	 *            the lasttimeStamp to set
	 */
	public void setLasttimeStamp(Date lasttimeStamp) {
		this.lasttimeStamp = lasttimeStamp;
	}

	/**
	 * @param modelCode
	 *            the modelCode to set
	 */
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	/**
	 * @param offDelayGear
	 *            the offDelayGear to set
	 */
	public void setOffDelayGear(String offDelayGear) {
		this.offDelayGear = offDelayGear;
	}

	/**
	 * @param offDelayValue
	 *            the offDelayValue to set
	 */
	public void setOffDelayValue(String offDelayValue) {
		this.offDelayValue = offDelayValue;
	}

	/**
	 * @param pingyinCode
	 *            the pingyinCode to set
	 */
	public void setPingyinCode(String pingyinCode) {
		this.pingyinCode = pingyinCode;
	}

	/**
	 * @param psId
	 *            the psId to set
	 */
	public void setPsId(Long psId) {
		this.psId = psId;
	}

	/**
	 * @param psType
	 *            the psType to set
	 */
	public void setPsType(String psType) {
		this.psType = psType;
	}

	/**
	 * @param ratedEc
	 *            the ratedEc to set
	 */
	public void setRatedEc(String ratedEc) {
		this.ratedEc = ratedEc;
	}

	/**
	 * @param remcGear
	 *            the remcGear to set
	 */
	public void setRemcGear(String remcGear) {
		this.remcGear = remcGear;
	}

	/**
	 * @param remcGearValue
	 *            the remcGearValue to set
	 */
	public void setRemcGearValue(String remcGearValue) {
		this.remcGearValue = remcGearValue;
	}

	/**
	 * @param terminalInfo
	 *            the terminalInfo to set
	 */
	public void setTerminalInfo(TerminalInfo terminalInfo) {
		this.terminalInfo = terminalInfo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);

	}

	/**
	 * @param functionsChecked the functionsChecked to set
	 */
	public void setFunctionsChecked(int[] functionsChecked) {

		this.functionsChecked = functionsChecked;
	}

	/**
	 * @return the functionsChecked
	 */
	public int[] getFunctionsChecked() {
		if (this.functionsChecked != null && this.functionsChecked.length > 0)
			return this.functionsChecked;

		int[] strChecked = new int[] { -1, -1, -1, -1, -1, -1, -1, -1 };
		if (this.getFunctionCode() != null && this.getFunctionCode().length() > 0) {
			char[] by = this.getFunctionCode().toCharArray();
			for (int i = 0; i < by.length; i++) {
				char j = by[i];
				if ('1' == j) {
					strChecked[i] = i;
				}
			}
		}
		return strChecked;

	}
}
