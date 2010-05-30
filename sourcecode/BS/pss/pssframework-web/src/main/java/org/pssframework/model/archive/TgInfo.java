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
*1) 描述台区基本信息,专变也做为台区管理，包括台区编码、台区名称、台区地址、公专变标志等信息
*2) 通过线损基础信息管理业务中录入产生，或通过新装增容与变更用电归档过程产生；或通过与生产系统接口过程产生。
*3) 该实体主要由线损基础信息管理业务、考核单元管理业务使用，在电费计算用户专线线路损耗也需要使用。
 *
 * @author baocj
 * @since 1.0.0
 */
@Entity
@Table(name = "g_tg")
@SequenceGenerator(sequenceName = "SEQ_G_TG", name = "SEQ_G_TG")
public class TgInfo extends BaseEntity {

	private static final long serialVersionUID = -3795917072464107754L;

	@Column(name = "TG_ID", unique = true, nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_G_TG")
	// TG_ID           NUMBER(16) not null,
	private Long tgId;

	@Column(name = "ORG_NO", length = 16, nullable = false)
	// ORG_NO          VARCHAR2(16) not null,
	private String orgNo;

	@Column(name = "TG_NO", length = 16, nullable = false)
	//TG_NO           VARCHAR2(16) not null,
	private String tgNo;

	@Column(name = "TG_NAME", length = 256, nullable = false)
	// TG_NAME         VARCHAR2(256) not null,
	private String tgName;

	@Column(name = "TG_CAP", precision = 16, scale = 6)
	//TG_CAP          NUMBER(16,6)
	private Long tgCap;

	@Column(name = "INST_ADDR", length = 256)
	//INST_ADDR       VARCHAR2(256),
	private String instAddr;

	@Column(name = "CHG_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	//CHG_DATE        DATE,
	private Date chaDate;

	@Column(name = "PUB_PRIV_FLAG", length = 8)
	//PUB_PRIV_FLAG   VARCHAR2(8),
	private String pubPrivFlag;

	@Column(name = "RUN_STATUS_CODE", length = 8)
	//RUN_STATUS_CODE VARCHAR2(8),
	private String statusCode;

	@Column(name = "LASTTIME_STAMP")
	@Temporal(TemporalType.TIMESTAMP)
	//LASTTIME_STAMP  DATE default SYSDATE
	private Date lasttimeStamp;

	/**
	 * @return the chaDate
	 */
	public Date getChaDate() {
		return chaDate;
	}

	/**
	 * @return the lasttimeStamp
	 */
	public Date getLasttimeStamp() {
		return lasttimeStamp;
	}

	/**
	 * @return the orgNo
	 */
	public String getOrgNo() {
		return orgNo;
	}

	/**
	 * @return the pubPrivFlag
	 */
	public String getPubPrivFlag() {
		return pubPrivFlag;
	}

	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * @return the tgCap
	 */
	public Long getTgCap() {
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
	 * @param chaDate the chaDate to set
	 */
	public void setChaDate(Date chaDate) {
		this.chaDate = chaDate;
	}

	/**
	 * @param lasttimeStamp the lasttimeStamp to set
	 */
	public void setLasttimeStamp(Date lasttimeStamp) {
		this.lasttimeStamp = lasttimeStamp;
	}

	/**
	 * @param orgNo the orgNo to set
	 */
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	/**
	 * @param pubPrivFlag the pubPrivFlag to set
	 */
	public void setPubPrivFlag(String pubPrivFlag) {
		this.pubPrivFlag = pubPrivFlag;
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @param tgCap the tgCap to set
	 */
	public void setTgCap(Long tgCap) {
		this.tgCap = tgCap;
	}

	/**
	 * @param tgId the tgId to set
	 */
	public void setTgId(Long tgId) {
		this.tgId = tgId;
	}

	/**
	 * @param tgName the tgName to set
	 */
	public void setTgName(String tgName) {
		this.tgName = tgName;
	}

	/**
	 * @param tgNo the tgNo to set
	 */
	public void setTgNo(String tgNo) {
		this.tgNo = tgNo;
	}

	/**
	 * @param instAddr the instAddr to set
	 */
	public void setInstAddr(String instAddr) {
		this.instAddr = instAddr;
	}

	/**
	 * @return the instAddr
	 */
	public String getInstAddr() {
		return instAddr;
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
