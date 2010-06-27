/**
 * 
 */
package org.pssframework.model.archive;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.pssframework.base.BaseEntity;
import org.pssframework.model.system.OrgInfo;

/**
 *1) 描述台区基本信息,专变也做为台区管理，包括台区编码、台区名称、台区地址、公专变标志等信息 2) 通过线损基础信息管理业务中录入产生，或通过新装增容与变更用电归档过程产生；或通过与生产系统接口过程产生。 3)
 * 该实体主要由线损基础信息管理业务、考核单元管理业务使用，在电费计算用户专线线路损耗也需要使用。
 * 
 * @author baocj
 * @since 1.0.0
 */
@Entity
@Table(name = "G_TG")
@SequenceGenerator(sequenceName = "SEQ_G_TG", name = "SEQ_G_TG", allocationSize = 1)
// @JsonIgnoreProperties(value = { "hibernateLazyInitializer", "tranInfos" })
public class TgInfo extends BaseEntity {

	private static final long serialVersionUID = -3795917072464107754L;

	@Column(name = "TG_ID", unique = true, nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_G_TG")
	// TG_ID NUMBER(16) not null,
	private Long tgId;

	@OneToMany(mappedBy = "tgInfo", cascade = CascadeType.ALL)
	private List<TranInfo> tranInfos;

	@OneToMany(targetEntity = MpInfo.class, mappedBy = "tgInfo")
	private List<MpInfo> mpInfos;

	@OneToMany(targetEntity = GpInfo.class, mappedBy = "tgInfo")
	private List<GpInfo> gpInfos;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "tgInfo")
	private List<LineTgRelaInfo> lineTgRelaInfos;

	@ManyToOne(targetEntity = OrgInfo.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "ORG_ID", nullable = false, referencedColumnName = "ORG_ID")
	// ORG_ID not null,
	private OrgInfo orgInfo;

	@Column(name = "TG_NO", length = 16, nullable = false)
	// TG_NO VARCHAR2(16) not null,
	private String tgNo;

	@Column(name = "TG_NAME", length = 256, nullable = false)
	// TG_NAME VARCHAR2(256) not null,
	private String tgName;

	@Column(name = "TG_CAP", precision = 16, scale = 6)
	// TG_CAP NUMBER(16,6)
	private Double tgCap;

	@Column(name = "INST_ADDR", length = 256)
	// INST_ADDR VARCHAR2(256)
	private String instAddr;

	@Column(name = "CHG_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	// CHG_DATE DATE,
	private Date chaDate;

	@Column(name = "PUB_PRIV_FLAG", length = 8)
	// PUB_PRIV_FLAG VARCHAR2(8),
	private String pubPrivFlag;

	@Column(name = "RUN_STATUS_CODE", length = 8)
	// RUN_STATUS_CODE VARCHAR2(8),
	private String runStatusCode;

	@Column(name = "LASTTIME_STAMP")
	@Temporal(TemporalType.TIMESTAMP)
	// LASTTIME_STAMP DATE default SYSDATE
	private Date lasttimeStamp;

	/**
	 * @return the chaDate
	 */
	public Date getChaDate() {
		return chaDate;
	}

	/**
	 * @return the instAddr
	 */
	public String getInstAddr() {
		return instAddr;
	}

	/**
	 * @return the lasttimeStamp
	 */
	public Date getLasttimeStamp() {
		return lasttimeStamp;
	}

	/**
	 * @return the orgInfo
	 */
	public OrgInfo getOrgInfo() {
		return orgInfo;
	}

	/**
	 * @return the pubPrivFlag
	 */
	public String getPubPrivFlag() {
		return pubPrivFlag;
	}

	/**
	 * @return the runStatusCode
	 */
	public String getRunStatusCode() {
		return runStatusCode;
	}

	/**
	 * @return the tgCap
	 */
	public Double getTgCap() {
		return tgCap;
	}

	/**
	 * @return the tgId
	 */
	public Long getTgId() {
		return tgId;
	}

	/**
	 * @return the tgName
	 */
	public String getTgName() {
		return tgName;
	}

	/**
	 * @return the tgNo
	 */
	public String getTgNo() {
		return tgNo;
	}

	/**
	 * @return the tranInfos
	 */
	// public List<TranInfo> getTranInfos() {
	// return tranInfos;
	// }

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/**
	 * @param chaDate
	 *            the chaDate to set
	 */
	public void setChaDate(Date chaDate) {
		this.chaDate = chaDate;
	}

	/**
	 * @param instAddr
	 *            the instAddr to set
	 */
	public void setInstAddr(String instAddr) {
		this.instAddr = instAddr;
	}

	/**
	 * @param lasttimeStamp
	 *            the lasttimeStamp to set
	 */
	public void setLasttimeStamp(Date lasttimeStamp) {
		this.lasttimeStamp = lasttimeStamp;
	}

	/**
	 * @param orgInfo
	 *            the orgInfo to set
	 */
	public void setOrgInfo(OrgInfo orgInfo) {
		this.orgInfo = orgInfo;
	}

	/**
	 * @param pubPrivFlag
	 *            the pubPrivFlag to set
	 */
	public void setPubPrivFlag(String pubPrivFlag) {
		this.pubPrivFlag = pubPrivFlag;
	}

	/**
	 * @param runStatusCode
	 *            the runStatusCode to set
	 */
	public void setRunStatusCode(String runStatusCode) {
		this.runStatusCode = runStatusCode;
	}

	/**
	 * @param tgCap
	 *            the tgCap to set
	 */
	public void setTgCap(Double tgCap) {
		this.tgCap = tgCap;
	}

	/**
	 * @param tgId
	 *            the tgId to set
	 */
	public void setTgId(Long tgId) {
		this.tgId = tgId;
	}

	/**
	 * @param tgName
	 *            the tgName to set
	 */
	public void setTgName(String tgName) {
		this.tgName = tgName;
	}

	/**
	 * @param tgNo
	 *            the tgNo to set
	 */
	public void setTgNo(String tgNo) {
		this.tgNo = tgNo;
	}

	/**
	 * @param tranInfos
	 *            the tranInfos to set
	 */
	// public void setTranInfos(List<TranInfo> tranInfos) {
	// this.tranInfos = tranInfos;
	// }

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * @param mpInfos
	 *            the mpInfos to set
	 */
	public void setMpInfos(List<MpInfo> mpInfos) {
		this.mpInfos = mpInfos;
	}

	/**
	 * @return the mpInfos
	 */
	public List<MpInfo> getMpInfos() {
		return mpInfos;
	}

	/**
	 * @param gpInfos
	 *            the gpInfos to set
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
	 * @param lineTgRelaInfos the lineTgRelaInfos to set
	 */
	public void setLineTgRelaInfos(List<LineTgRelaInfo> lineTgRelaInfos) {
		this.lineTgRelaInfos = lineTgRelaInfos;
	}

	/**
	 * @return the lineTgRelaInfos
	 */
	public List<LineTgRelaInfo> getLineTgRelaInfos() {
		return lineTgRelaInfos;
	}

	/**
	 * @param tranInfos the tranInfos to set
	 */
	public void setTranInfos(List<TranInfo> tranInfos) {
		this.tranInfos = tranInfos;
	}

	/**
	 * @return the tranInfos
	 */
	public List<TranInfo> getTranInfos() {
		return tranInfos;
	}

}
