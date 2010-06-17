package org.pssframework.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.pssframework.base.BaseEntity;

/**
 * @author PSS email:PPT(a)gmail.com
 * @version 1.0
 * @since 1.0
 */

public class UserInfo extends BaseEntity {

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
	private java.lang.Long userId;

	private java.lang.String username;

	private java.lang.String password;

	private java.sql.Date birthDate;

	private java.lang.Byte sex;

	private java.lang.Integer age;

	// columns END

	public UserInfo() {
	}

	public UserInfo(java.lang.Long userId) {
		this.userId = userId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserInfo == false)
			return false;
		if (this == obj)
			return true;
		UserInfo other = (UserInfo) obj;
		return new EqualsBuilder().append(getUserId(), other.getUserId()).append(getUsername(), other.getUsername())
				.append(getPassword(), other.getPassword()).append(getBirthDate(), other.getBirthDate()).append(
						getSex(), other.getSex()).append(getAge(), other.getAge()).isEquals();
	}

	public java.lang.Integer getAge() {
		return this.age;
	}

	public java.sql.Date getBirthDate() {
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

	public void setBirthDate(java.sql.Date value) {
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
