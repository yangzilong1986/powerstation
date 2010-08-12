/**
 * 
 */
package org.pssframework.model.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.pssframework.base.BaseEntity;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name = "O_AUTHORITY")
@SequenceGenerator(sequenceName = "SEQ_O_AUTHORITY", name = "SEQ_O_AUTHORITY", allocationSize = 1, initialValue = 10)
public class AuthorityInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2281846078642991412L;

	/**
	 * SpringSecurity中默认的角色/授权名前缀.
	 */
	public static final String AUTHORITY_PREFIX = "ROLE_AUTHORITY_";

	@Column(nullable = false, unique = true, name = "AUTHORITY_NAME")
	private String authorityName;

	@Column(name = "AUTHORITY_REMARK")
	private String authorityRemark;

	@Column(nullable = false, unique = true, name = "AUTHORITY_ID")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_O_AUTHORITY")
	private Long authorityId;

	public AuthorityInfo() {
	}

	public AuthorityInfo(Long authorityId) {
		this.authorityId = authorityId;
	}

	public AuthorityInfo(Long authorityId, String authorityName) {
		this.authorityId = authorityId;
		this.authorityName = authorityName;
	}


	public String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	@Transient
	public String getPrefixedName() {
		return AUTHORITY_PREFIX + authorityName;
	}

	@Transient
	public String getPrefixedId() {
		return AUTHORITY_PREFIX + this.authorityId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public void setAuthorityId(Long authorityId) {
		this.authorityId = authorityId;
	}

	public Long getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityRemark(String authorityRemark) {
		this.authorityRemark = authorityRemark;
	}

	public String getAuthorityRemark() {
		return authorityRemark;
	}

}
