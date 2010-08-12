/**
 * 
 */
package org.pssframework.model.system;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.pssframework.base.BaseEntity;
import org.springside.modules.utils.ReflectionUtils;

import com.google.common.collect.Lists;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name = "O_STAFF")
@SequenceGenerator(sequenceName = "SEQ_O_STAFF", name = "SEQ_O_STAFF", allocationSize = 1, initialValue = 10)
public class UserInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3676801718128622302L;

	/*
	 * EMP_NO NUMBER(16) not null, is '本实体记录的唯一标识'
	 */
	@Column(nullable = false, unique = true, name = "EMP_NO")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_O_STAFF")
	private Long empNo;

	@Column(name = "DEPT_NO")
	private Long deptNo;

	@ManyToOne(targetEntity = OrgInfo.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "ORG_ID", nullable = false, referencedColumnName = "ORG_ID")
	// ORG_ID not null,
	private OrgInfo orgInfo;

	// STAFF_NO VARCHAR2(16), is '工号， 营销业务人员的服务工号';
	@Column(length = 16, nullable = false, name = "STAFF_NO")
	private String staffNo;

	// PASSWD VARCHAR2(50),
	@Column(length = 50, nullable = false, name = "PASSWD")
	private String passwd;

	// NAME VARCHAR2(64), is '业务人员姓名 ';
	@Column(length = 64, nullable = false, name = "NAME")
	private String name;

	// GENDER VARCHAR2(8), is '性别。 01 男、 02 女';
	@Column(length = 8, nullable = false, name = "GENDER")
	private String gender;

	// POS_NAME VARCHAR2(256), is '所在职位名称';
	@Column(length = 256, nullable = false, name = "POS_NAME")
	private String posName;

	// POSITION VARCHAR2(8), is '人员所在岗位代码';
	@Column(length = 8, nullable = false, name = "POSITION")
	private String position;

	// WORK_TYPE_CODE VARCHAR2(8),is '工作分工种类： 01 检定人员、 02 修校人员、 03 装表接电';
	@Column(length = 8, nullable = false, name = "WORK_TYPE_CODE")
	private String workTypeCode;

	// MOBILE VARCHAR2(32), is '人员联系手机号码';
	@Column(length = 32, nullable = false, name = "MOBILE")
	private String mobile;

	// REMARK VARCHAR2(256), is '人员的特殊说明 ';
	@Column(length = 256, nullable = false, name = "REMARK")
	private String remark;

	// LASTTIME_STAMP DATE default SYSDATE is '最后表结构修改时间戳';
	@Column(name = "LASTTIME_STAMP")
	@Temporal(TemporalType.TIMESTAMP)
	// LASTTIME_STAMP DATE default SYSDATE
	private Date lasttimeStamp;

	@Column(name = "ENABLE")
	private Integer enable;

	@Column(name = "ACCOUNT_NON_EXPIRED")
	private Integer accountNonExpired;

	@Column(name = "CREDENTIALS_NON_EXPIRED")
	private Integer credentialsNonExpired;

	@Column(name = "ACCOUNT_NON_LOCKED")
	private Integer accountNonLocked;

	//多对多定义
	@ManyToMany
	//中间表定义,表名采用默认命名规则
	@JoinTable(name = "O_USER_ROLE", joinColumns = { @JoinColumn(name = "EMP_NO") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	//Fecth策略定义
	@Fetch(FetchMode.SUBSELECT)
	//集合按id排序.
	@OrderBy("roleId")
	//集合中对象id的缓存.
	//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<RoleInfo> roleInfoList = Lists.newArrayList();//有序的关联对象集合

	@Transient
	private String[] roleNameArray;

	@Transient
	private String roleNames;

	@Transient
	private List<Long> roleIds;

	@Transient
	private boolean showAllAccount;

	@Transient
	private String oldPasswd;

	/**
	 * @return the oldPasswd
	 */
	public String getOldPasswd() {
		return oldPasswd;
	}

	/**
	 * @param oldPasswd the oldPasswd to set
	 */
	public void setOldPasswd(String oldPasswd) {
		this.oldPasswd = oldPasswd;
	}

	/**
	 * @return the newPasswd
	 */
	public String getNewPasswd() {
		return newPasswd;
	}

	/**
	 * @param newPasswd the newPasswd to set
	 */
	public void setNewPasswd(String newPasswd) {
		this.newPasswd = newPasswd;
	}

	@Transient
	private String newPasswd;

	/**
	 * @return the accountNonExpired
	 */
	public Integer getAccountNonExpired() {
		return accountNonExpired;
	}

	/**
	 * @return the accountNonLocked
	 */
	public Integer getAccountNonLocked() {
		return accountNonLocked;
	}

	/**
	 * @return the credentialsNonExpired
	 */
	public Integer getCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public Long getDeptNo() {
		return deptNo;
	}

	/**
	 * @return the empNo
	 */
	public Long getEmpNo() {
		return empNo;
	}

	/**
	 * @return the enable
	 */
	public Integer getEnable() {
		return enable;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @return the lasttimeStamp
	 */
	public Date getLasttimeStamp() {
		return lasttimeStamp;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public OrgInfo getOrgInfo() {
		return orgInfo;
	}

	/**
	 * @return the passwd
	 */
	public String getPasswd() {
		return passwd;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @return the posName
	 */
	public String getPosName() {
		return posName;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 用户拥有的角色id字符串, 多个角色id用','分隔.
	 */

	@SuppressWarnings("unchecked")
	public List<Long> getRoleIds() {
		if (this.roleIds == null) {
			this.roleIds = ReflectionUtils.convertElementPropertyToList(roleInfoList, "roleId");
		}
		return roleIds;
	}

	public List<RoleInfo> getRoleInfoList() {
		return roleInfoList;
	}

	public String[] getRoleNameArray() {
		String[] strRoleNameArray = new String[] {};
		if (getRoleNames() != null) {
			strRoleNameArray = getRoleNames().split(",");
		}
		this.roleNameArray = strRoleNameArray;
		return roleNameArray;
	}

	/**
	 * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
	 */
	//非持久化属性.
	public String getRoleNames() {
		this.roleNames = ReflectionUtils.convertElementPropertyToString(roleInfoList, "roleName", ", ");
		return this.roleNames;
	}

	/**
	 * @return the staffNo
	 */
	public String getStaffNo() {
		return staffNo;
	}

	/**
	 * @return the workTypeCode
	 */
	public String getWorkTypeCode() {
		return workTypeCode;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/**
	 * @param accountNonExpired the accountNonExpired to set
	 */
	public void setAccountNonExpired(Integer accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	/**
	 * @param accountNonLocked the accountNonLocked to set
	 */
	public void setAccountNonLocked(Integer accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	/**
	 * @param credentialsNonExpired the credentialsNonExpired to set
	 */
	public void setCredentialsNonExpired(Integer credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setDeptNo(Long deptNo) {
		this.deptNo = deptNo;
	}

	/**
	 * @param empNo
	 *            the empNo to set
	 */
	public void setEmpNo(Long empNo) {
		this.empNo = empNo;
	}

	/**
	 * @param enable the enable to set
	 */
	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @param lasttimeStamp
	 *            the lasttimeStamp to set
	 */
	public void setLasttimeStamp(Date lasttimeStamp) {
		this.lasttimeStamp = lasttimeStamp;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public void setOrgInfo(OrgInfo orgInfo) {
		this.orgInfo = orgInfo;
	}

	/**
	 * @param passwd
	 *            the passwd to set
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @param posName
	 *            the posName to set
	 */
	public void setPosName(String posName) {
		this.posName = posName;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @param roleIds the roleIds to set
	 */
	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

	/**
	 * @param roleInfoList the roleInfoList to set
	 */
	public void setRoleInfoList(List<RoleInfo> roleInfoList) {
		this.roleInfoList = roleInfoList;
	}

	public void setRoleNameArray(String[] roleNameArray) {
		this.roleNameArray = roleNameArray;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	/**
	 * @param staffNo
	 *            the staffNo to set
	 */
	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	/**
	 * @param workTypeCode
	 *            the workTypeCode to set
	 */
	public void setWorkTypeCode(String workTypeCode) {
		this.workTypeCode = workTypeCode;
	}

	/**
	 * @return the serialversionuid
	 */

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public void setShowAllAccount(boolean showAllAccount) {
		this.showAllAccount = showAllAccount;
	}

	public boolean isShowAllAccount() {
		return showAllAccount;
	}

}
