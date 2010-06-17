/**
 * 
 */
package org.pssframework.model.system;

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
 * @author Administrator
 * 
 */
@Entity
@Table(name = "o_org")
@SequenceGenerator(sequenceName = "SEQ_O_ORG", name = "SEQ_O_ORG")
public class OrgInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3702250520720629508L;

	/*
	 * comment on column O_ORG.ORG_ID is '本实体记录的唯一标识， 系统 唯一编码 '
	 */
	@Column(nullable = false, unique = true, name = "ORG_ID")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_O_ORG")
	private Long orgId;

	// @OneToMany(targetEntity = TgInfo.class, mappedBy = "orgInfo")
	// private List<TgInfo> tgInfos;

	/*
	 * VARCHAR2(16) not null, comment on column O_ORG.ORG_NO is '本实体记录的唯一标识，
	 * 创建供电单位的 唯一编码 '
	 */
	@Column(length = 16, nullable = false, name = "ORG_NO")
	private String orgNo;

	/*
	 * comment on column O_ORG.ORG_NAME is '供电单位详细的名称'; VARCHAR2(256),
	 */
	@Column(length = 256, name = "ORG_NAME")
	private String orgName;

	/*
	 * comment on column O_ORG.P_ORG_ID is '直接上级供电单位编号';NUMBER(16)
	 */
	@Column(name = "P_ORG_ID")
	private Long pOrgId;

	/*
	 * comment on column O_ORG.ORG_TYPE VARCHAR2(8), is '单位类别：国网公司、
	 * 省公司、地市公司、区县公司、分公司、 供电所等。01 国网公司、 02 省公司、 03 地市公司 、 04 区县公司、 05 分公司、 06
	 * 供电所。';
	 */
	@Column(length = 8, name = "ORG_TYPE")
	private String orgType;

	/*
	 * comment on column O_ORG.SORT_NO NUMBER(5) is '在同级中的排列顺序的序号， 用自然数标 识，如， 1
	 * 、 2 、 3 。
	 */
	@Column(length = 5, name = "SORT_NO")
	private Long sortNo;

	/*
	 * comment on column O_ORG.LASTTIME_STAMP is '最后表结构修改时间戳';
	 */
	@Column(name = "LASTTIME_STAMP")
	@Temporal(TemporalType.TIMESTAMP)
	// LASTTIME_STAMP DATE default SYSDATE
	private Date lasttimeStamp;

	/**
	 * @return the lasttimeStamp
	 */
	public Date getLasttimeStamp() {
		return lasttimeStamp;
	}

	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @return the orgNo
	 */
	public String getOrgNo() {
		return orgNo;
	}

	/**
	 * @return the orgType
	 */
	public String getOrgType() {
		return orgType;
	}

	/**
	 * @return the pOrgId
	 */
	public Long getpOrgId() {
		return pOrgId;
	}

	/**
	 * @return the sortNo
	 */
	public Long getSortNo() {
		return sortNo;
	}

	/**
	 * @return the tgInfos
	 */
	// public List<TgInfo> getTgInfos() {
	// return tgInfos;
	// }

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/**
	 * @param lasttimeStamp
	 *            the lasttimeStamp to set
	 */
	public void setLasttimeStamp(Date lasttimeStamp) {
		this.lasttimeStamp = lasttimeStamp;
	}

	/**
	 * @param orgId
	 *            the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * @param orgName
	 *            the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @param orgNo
	 *            the orgNo to set
	 */
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	/**
	 * @param orgType
	 *            the orgType to set
	 */
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	/**
	 * @param pOrgNo
	 *            the pOrgNo to set
	 */
	public void setpOrgNo(Long pOrgId) {
		this.pOrgId = pOrgId;
	}

	/**
	 * @param sortNo
	 *            the sortNo to set
	 */
	public void setSortNo(Long sortNo) {
		this.sortNo = sortNo;
	}

	/**
	 * @param tgInfos the tgInfos to set
	 */
	// public void setTgInfos(List<TgInfo> tgInfos) {
	// this.tgInfos = tgInfos;
	// }

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
