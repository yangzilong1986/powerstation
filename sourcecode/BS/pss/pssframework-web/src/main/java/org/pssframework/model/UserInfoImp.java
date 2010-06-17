package org.pssframework.model;

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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.pssframework.base.BaseEntity;

/**
 * @author PSS email:PPT(a)gmail.com
 * @version 1.0
 * @since 1.0
 */

@Entity
@Table(name = "user_info")
@SequenceGenerator(sequenceName = "SEQ_USER_ID", name = "TOP_SEQ_USER_ID")
public class UserInfoImp extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8286753093121813992L;

	// alias
	public static final String TABLE_ALIAS = "UserInfo";

	public static final String ALIAS_USER_ID = "userId";

	public static final String ALIAS_USERNAME = "username";

	public static final String ALIAS_PASSWORD = "password";

	public static final String ALIAS_BIRTH_DATE = "birthDate";

	public static final String ALIAS_SEX = "sex";

	public static final String ALIAS_AGE = "age";

	// date formats
	public static final String FORMAT_BIRTH_DATE = DATE_TIME_FORMAT;

	// columns START
	@Column(name = "user_id", unique = true)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TOP_SEQ_USER_ID")
	// @GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;

	@Column(name = "username", length = 50)
	private String username;

	@Column(name = "password", length = 20)
	private String password;

	@Column(name = "birth_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date birthDate;

	@Column(name = "sex")
	private Byte sex;

	@Column(name = "age")
	private Integer age;

	// columns END

	public UserInfoImp() {
	}

	public UserInfoImp(java.lang.Long userId) {
		this.userId = userId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserInfoImp == false)
			return false;
		if (this == obj)
			return true;
		UserInfoImp other = (UserInfoImp) obj;
		return new EqualsBuilder().append(getUserId(), other.getUserId()).append(getUsername(), other.getUsername())
				.append(getPassword(), other.getPassword()).append(getBirthDate(), other.getBirthDate()).append(
						getSex(), other.getSex()).append(getAge(), other.getAge()).isEquals();
	}

	public java.lang.Integer getAge() {
		return this.age;
	}

	public Date getBirthDate() {
		return this.birthDate;
	}

	public String getBirthDateString() {
		return date2String(getBirthDate(), FORMAT_BIRTH_DATE);
	}

	public java.lang.String getPassword() {
		return this.password;
	}

	public java.lang.Byte getSex() {
		return this.sex;
	}

	public java.lang.Long getUserId() {
		return this.userId;
	}

	public java.lang.String getUsername() {
		return this.username;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getUserId()).append(getUsername()).append(getPassword()).append(
				getBirthDate()).append(getSex()).append(getAge()).toHashCode();
	}

	public void setAge(java.lang.Integer value) {
		this.age = value;
	}

	public void setBirthDate(Date value) {
		this.birthDate = value;
	}

	public void setBirthDateString(String value) {
		setBirthDate(string2Date(value, FORMAT_BIRTH_DATE, java.sql.Date.class));
	}

	public void setPassword(java.lang.String value) {
		this.password = value;
	}

	public void setSex(java.lang.Byte value) {
		this.sex = value;
	}

	public void setUserId(java.lang.Long value) {
		this.userId = value;
	}

	public void setUsername(java.lang.String value) {
		this.username = value;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("UserId", getUserId()).append("Username", getUsername()).append(
				"Password", getPassword()).append("BirthDate", getBirthDate()).append("Sex", getSex()).append("Age",
				getAge()).toString();
	}
}
