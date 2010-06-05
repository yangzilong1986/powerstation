/**
 * 
 */
package org.pssframework.model.system;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.pssframework.base.BaseEntity;

/**
 * 1)
 * 部门为供电单位中的具体部门，科室、班组等。如：抄表班、营业厅。如果采用第三方组件，则本实体可以转换为视图，供其他数据域实体引用。本实体主要包括部门标识
 * 、部门名称、部门类型、上级部门标识等属性。 2) 通过部门管理，由录入产生记录。
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "O_DEPT")
public class DeptInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3611279996618673259L;

	@Column(name = "DEPT_NO", unique = true, nullable = false)
	@Id
	// DEPT_NO VARCHAR2(16) not null,is '本实体记录的唯一标识，创建部门的唯一编码。';
	private String deptNo; // 标准代码实体联合主键

	// ORG_ID NUMBER,is '部门id';
	@Column(name = "ORG_ID")
	private Long orgId;

	// ABBR VARCHAR2(256) is '部门名称的缩写。';
	@Column(name = "ABBR", length = 256)
	private String abbr;

	// NAME VARCHAR2(256),is '部门的详细名称。';
	@Column(name = "NAME", length = 256)
	private String name;

	// TYPE_CODE VARCHAR2(8),is '部门的类型，可以提供给部门选择使用，方便部门过滤。属于枚举类属性，主要包括：01
	// 抄表班、02营业厅、03 核算班、04 电费班、05 计量班。';
	@Column(name = "TYPE_CODE", length = 8)
	private String typeCode;

	// P_DEPT_NO VARCHAR2(16),is '直接上级部门的部门编号。';
	@Column(name = "P_DEPT_NO", length = 1)
	private String pDeptNo;

	// DISP_SN NUMBER(5),is '部门树形展现时的显示顺序号。';
	@Column(name = "DISP_SN", length = 5)
	private Long dispSn;

	/*
	 * comment on column O_ORG.LASTTIME_STAMP is '最后表结构修改时间戳';
	 */
	@Column(name = "LASTTIME_STAMP")
	@Temporal(TemporalType.TIMESTAMP)
	// LASTTIME_STAMP DATE default SYSDATE
	private Date lasttimeStamp;



	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId
	 *            the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the abbr
	 */
	public String getAbbr() {
		return abbr;
	}

	/**
	 * @param abbr
	 *            the abbr to set
	 */
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	/**
	 * @return the dispSn
	 */
	public Long getDispSn() {
		return dispSn;
	}

	/**
	 * @param dispSn
	 *            the dispSn to set
	 */
	public void setDispSn(Long dispSn) {
		this.dispSn = dispSn;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setpDeptNo(String pDeptNo) {
		this.pDeptNo = pDeptNo;
	}

	public String getpDeptNo() {
		return pDeptNo;
	}

	public void setLasttimeStamp(Date lasttimeStamp) {
		this.lasttimeStamp = lasttimeStamp;
	}

	public Date getLasttimeStamp() {
		return lasttimeStamp;
	}

}
