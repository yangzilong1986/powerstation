package org.pssframework.model.entity.security;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.pssframework.model.entity.IdEntity;
import org.springside.modules.utils.ReflectionUtils;

/**
 * 用户.
 * 
 * 使用JPA annotation定义ORM关系.
 * 使用Hibernate annotation定义JPA 1.0未覆盖的部分.
 * 
 * @author calvin
 */
@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "SS_USER")
//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends IdEntity {

	private String loginName;
	private String password;//为简化演示使用明文保存的密码
	private String name;
	private String email;
	private List<Role> roleList = new ArrayList<Role>();//有序的关联对象集合

	//字段非空且唯一, 用于提醒Entity使用者及生成DDL.
	@Column(nullable = false, unique = true)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	//多对多定义
	@ManyToMany
	//中间表定义,表名采用默认命名规则
	@JoinTable(name = "SS_USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	//Fecth策略定义
	@Fetch(FetchMode.SUBSELECT)
	//集合按id排序.
	@OrderBy("id")
	//集合中对象id的缓存.
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	/**
	 * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
	 */
	//非持久化属性.
	@Transient
	public String getRoleNames() {
		return ReflectionUtils.convertElementPropertyToString(roleList, "name", ", ");
	}

	/**
	 * 用户拥有的角色id字符串, 多个角色id用','分隔.
	 */
	//非持久化属性.
	@Transient
	@SuppressWarnings("unchecked")
	public List<Long> getRoleIds() {
		return ReflectionUtils.convertElementPropertyToList(roleList, "id");
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}