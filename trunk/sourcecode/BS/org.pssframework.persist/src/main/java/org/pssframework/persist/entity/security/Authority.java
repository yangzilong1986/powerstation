package org.pssframework.persist.entity.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.pssframework.persist.entity.IdEntity;

/**
 * 权限.
 * 
 * 注释见{@link User}.
 * 
 * @author calvin
 */
@Entity
@Table(name = "SS_AUTHORITY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Authority extends IdEntity {

	private String name;
	private String displayName;

	public Authority() {
	}

	public Authority(Long id, String name, String displayName) {
		this.id = id;
		this.name = name;
		this.displayName = displayName;
	}

	@Column(nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
