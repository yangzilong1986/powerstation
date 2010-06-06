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
@Table(name = "o_role")
@SequenceGenerator(sequenceName = "SEQ_O_ROLE", name = "SEQ_O_ROLE")
public class RoleInfo extends BaseEntity {

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
	private Long roleName;

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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * @return the roleId
	 */
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the roleName
	 */
	public Long getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(Long roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the rolePemark
	 */
	public String getRolePemark() {
		return rolePemark;
	}

	/**
	 * @param rolePemark the rolePemark to set
	 */
	public void setRolePemark(String rolePemark) {
		this.rolePemark = rolePemark;
	}

	/**
	 * @return the roleType
	 */
	public String getRoleType() {
		return roleType;
	}

	/**
	 * @param roleType the roleType to set
	 */
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	/**
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * @param creator the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * @return the creattime
	 */
	public Date getCreattime() {
		return creattime;
	}

	/**
	 * @param creattime the creattime to set
	 */
	public void setCreattime(Date creattime) {
		this.creattime = creattime;
	}

	/**
	 * @return the lasttimeStamp
	 */
	public Date getLasttimeStamp() {
		return lasttimeStamp;
	}

	/**
	 * @param lasttimeStamp the lasttimeStamp to set
	 */
	public void setLasttimeStamp(Date lasttimeStamp) {
		this.lasttimeStamp = lasttimeStamp;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
