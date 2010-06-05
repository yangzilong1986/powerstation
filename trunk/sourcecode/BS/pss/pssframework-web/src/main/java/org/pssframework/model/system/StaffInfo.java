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
@Table(name = "o_staff")
@SequenceGenerator(sequenceName = "SEQ_O_STAFF", name = "SEQ_O_STAFF")
public class StaffInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3676801718128622302L;

	/**
	 * 
	 */
 

	/*
	 * EMP_NO NUMBER(16) not null, is '本实体记录的唯一标识'
	 */
	@Column(nullable = false, unique = true, name = "EMP_NO")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_O_STAFF")
	private Long empNo;

	// DEPT_NO VARCHAR2(16), is '本实体记录的唯一标识， 创建部门的唯一编码';
	@Column(length = 16, nullable = false, name = "DEPT_NO")
	private String deptNo;

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
	@Column(length =256, nullable = false, name = "REMARK")
	private String remark;

	// LASTTIME_STAMP DATE default SYSDATE is '最后表结构修改时间戳';
	@Column(name = "LASTTIME_STAMP")
	@Temporal(TemporalType.TIMESTAMP)
	// LASTTIME_STAMP DATE default SYSDATE
	private Date lasttimeStamp;

	/**
	 * @return the empNo
	 */
	public Long getEmpNo() {
		return empNo;
	}

	/**
	 * @param empNo
	 *            the empNo to set
	 */
	public void setEmpNo(Long empNo) {
		this.empNo = empNo;
	}

	/**
	 * @return the deptNo
	 */
	public String getDeptNo() {
		return deptNo;
	}

	/**
	 * @param deptNo
	 *            the deptNo to set
	 */
	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	/**
	 * @return the staffNo
	 */
	public String getStaffNo() {
		return staffNo;
	}

	/**
	 * @param staffNo
	 *            the staffNo to set
	 */
	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	/**
	 * @return the passwd
	 */
	public String getPasswd() {
		return passwd;
	}

	/**
	 * @param passwd
	 *            the passwd to set
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the posName
	 */
	public String getPosName() {
		return posName;
	}

	/**
	 * @param posName
	 *            the posName to set
	 */
	public void setPosName(String posName) {
		this.posName = posName;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the workTypeCode
	 */
	public String getWorkTypeCode() {
		return workTypeCode;
	}

	/**
	 * @param workTypeCode
	 *            the workTypeCode to set
	 */
	public void setWorkTypeCode(String workTypeCode) {
		this.workTypeCode = workTypeCode;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the lasttimeStamp
	 */
	public Date getLasttimeStamp() {
		return lasttimeStamp;
	}

	/**
	 * @param lasttimeStamp
	 *            the lasttimeStamp to set
	 */
	public void setLasttimeStamp(Date lasttimeStamp) {
		this.lasttimeStamp = lasttimeStamp;
	}

	/**
	 * @return the serialversionuid
	 */
 

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
