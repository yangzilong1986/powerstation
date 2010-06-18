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

/**is '维护操作员与角色的多对多的关系';
 * @author Administrator
 * 
 */
@Entity
@Table(name = "o_user_role")
@SequenceGenerator(sequenceName = "SEQ_O_USER_ROLE", name = "SEQ_O_USER_ROLE", allocationSize = 1)
public class UserRoleInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1712489654205201494L;

	/**
	 * 
	 */
	 

	/*
	 * EMP_ROLE_ID    NUMBER not null,is 'PK，由SEQ_ROLE生成'
	 */
	@Column(nullable = false, unique = true, name = "EMP_ROLE_ID")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_O_USER_ROLE")
	private Long empRoleId;

	  //EMP_NO         VARCHAR2(16),  is '本实体记录的唯一标识';
	@Column(length = 16, name = "EMP_NO")
	private Long empNo;
	  
	  //ROLE_ID        NUMBER, 
	@Column(  name = "ROLE_ID")
	private Long roleId;
	  
	  //GRANTABLE      NUMBER, is '1 - 可以授予别人0 - 不可以授予别人    默认1
	@Column(  name = "GRANTABLE")
	private Long grantable;
	  
	 // LASTTIME_STAMP DATE default SYSDATE
	@Column(name = "LASTTIME_STAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lasttimeStamp;
	  
	/**
	 * @return the empNo
	 */
	public Long getEmpNo() {
		return empNo;
	}

	/**
	 * @return the empRoleId
	 */
	public Long getEmpRoleId() {
		return empRoleId;
	}

	/**
	 * @return the grantable
	 */
	public Long getGrantable() {
		return grantable;
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

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/**
	 * @param empNo the empNo to set
	 */
	public void setEmpNo(Long empNo) {
		this.empNo = empNo;
	}

	/**
	 * @param empRoleId the empRoleId to set
	 */
	public void setEmpRoleId(Long empRoleId) {
		this.empRoleId = empRoleId;
	}

	/**
	 * @param grantable the grantable to set
	 */
	public void setGrantable(Long grantable) {
		this.grantable = grantable;
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
