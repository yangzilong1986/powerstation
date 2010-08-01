/**
 * 
 */
package org.pssframework.model.system;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
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

import com.google.common.collect.Lists;

/**
 * @author Administrator
 * 
 */
@Entity
@Table(name = "O_ROLE")
@SequenceGenerator(sequenceName = "SEQ_O_ROLE", name = "SEQ_O_ROLE", allocationSize = 1)
public class RoleInfo extends BaseEntity {

	//	public RoleInfo(Long roleId, String roleName) {
	//		this.roleId = roleId;
	//		this.roleName = roleName;
	//
	//	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2306398962448879040L;

	/*
	 * ROLE_ID NUMBER not null,is 'PK，有序列SEQ_ROLE生成';'
	 */
	@Column(nullable = false, unique = true, name = "ROLE_ID ")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_O_ROLE")
	private Long roleId;

	// ROLE_NAME VARCHAR2(50) not null,
	@Column(nullable = false, unique = true, name = "ROLE_NAME")
	private String roleName;

	// ROLE_REMARK VARCHAR2(256),
	@Column(length = 256, name = "ROLE_REMARK")
	private String rolePemark;

	// ROLE_TYPE VARCHAR2(5), is '见编码ROLE_TYPE1 - 岗位角色（主要对应应用功能）2 -
	// 操作权限（如档案查询，档案维护） 3 - 系统对象（所能操作系统对象）
	@Column(length = 5, name = "ROLE_TYPE ")
	private String roleType;

	// CREATOR VARCHAR2(20),is '创建该角色的操作员编号（EMP_NO ）
	@Column(length = 20, name = "CREATOR ")
	private String creator;

	// CREATTIME DATE,
	@Column(name = "CREATTIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creattime;

	@Column(name = "LASTTIME_STAMP")
	@Temporal(TemporalType.TIMESTAMP)
	// LASTTIME_STAMP DATE default SYSDATE is '最后表结构修改时间戳';
	private Date lasttimeStamp;

	@ManyToMany
	@JoinTable(name = "O_ROLE_AUTHORITY", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "AUTHORITY_ID") })
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("authorityId")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<AuthorityInfo> authorityInfoList = Lists.newArrayList();

	public List<AuthorityInfo> getAuthorityInfoList() {
		return authorityInfoList;
	}

	@ManyToMany
	@JoinTable(name = "O_ROLE_FUN", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "FUN_ID") })
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("resourceId")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<ResourceInfo> resourceInfoList = Lists.newArrayList();

	/**
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * @param authorityInfoList the authorityInfoList to set
	 */
	public void setAuthorityInfoList(List<AuthorityInfo> authorityInfoList) {
		this.authorityInfoList = authorityInfoList;
	}

	/**
	 * @param resourceInfoList the resourceInfoList to set
	 */
	public void setResourceInfoList(List<ResourceInfo> resourceInfoList) {
		this.resourceInfoList = resourceInfoList;
	}

	/**
	 * @return the creattime
	 */
	public Date getCreattime() {
		return creattime;
	}

	/**
	 * @return the lasttimeStamp
	 */
	public Date getLasttimeStamp() {
		return lasttimeStamp;
	}

	/**
	 * @return the roleId
	 */
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @return the rolePemark
	 */
	public String getRolePemark() {
		return rolePemark;
	}

	/**
	 * @return the roleType
	 */
	public String getRoleType() {
		return roleType;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/**
	 * @param creator the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * @param creattime the creattime to set
	 */
	public void setCreattime(Date creattime) {
		this.creattime = creattime;
	}

	/**
	 * @param lasttimeStamp the lasttimeStamp to set
	 */
	public void setLasttimeStamp(Date lasttimeStamp) {
		this.lasttimeStamp = lasttimeStamp;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @param rolePemark the rolePemark to set
	 */
	public void setRolePemark(String rolePemark) {
		this.rolePemark = rolePemark;
	}

	/**
	 * @param roleType the roleType to set
	 */
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Transient
	public String getAuthNames() {
		return org.springside.modules.utils.ReflectionUtils.convertElementPropertyToString(authorityInfoList, "name",
				", ");
	}

	@Transient
	@SuppressWarnings("unchecked")
	public List<Long> getAuthIds() {
		return org.springside.modules.utils.ReflectionUtils.convertElementPropertyToList(authorityInfoList, "id");
	}

	public List<ResourceInfo> getResourceInfoList() {
		return this.resourceInfoList;
	}

}
