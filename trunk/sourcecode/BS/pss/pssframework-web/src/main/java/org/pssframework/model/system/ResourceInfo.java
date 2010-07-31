/**
 * 
 */
package org.pssframework.model.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.pssframework.base.BaseEntity;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name = "O_FUN")
public class ResourceInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2473918127461686889L;

	/**
	 * SpringSecurity中默认的角色/授权名前缀.
	 */
	public static final String RESOURCE_PREFIX = "ROLE_RESOURCE_";

	@Column(nullable = false, unique = true, name = "FUN_ID")
	@Id
	private Long resourceId;

	@Column(nullable = false, name = "FUN_NAME")
	private String resourceName;

	@Column(nullable = false, name = "FUN_COMMENT")
	private String resourceComment;

	@Column(nullable = false, name = "FUN_TYPE")
	private String resourceType;

	/**
	 * 操作URL
	 */
	@Column(nullable = false, name = "OP_URL")
	private String opUrl;

	/**
	 * 功能URL
	 */
	@Column(nullable = false, name = "FUN_URL")
	private String funUrl;

	/**
	 * 调用方式
	 */
	@Column(nullable = false, name = "OP_WAY")
	private String opWay;

	@Column(nullable = false, name = "FUN_UPPER")
	private Long resourceUpperId;

	/**
	 * @return the resourceComment
	 */
	public String getResourceComment() {
		return resourceComment;
	}

	/**
	 * @param resourceComment the resourceComment to set
	 */
	public void setResourceComment(String resourceComment) {
		this.resourceComment = resourceComment;
	}

	/**
	 * @return the resourceType
	 */
	public String getResourceType() {
		return resourceType;
	}

	/**
	 * @param resourceType the resourceType to set
	 */
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	/**
	 * @return the opUrl
	 */
	public String getOpUrl() {
		return opUrl;
	}

	/**
	 * @param opUrl the opUrl to set
	 */
	public void setOpUrl(String opUrl) {
		this.opUrl = opUrl;
	}

	/**
	 * @return the funUrl
	 */
	public String getFunUrl() {
		return funUrl;
	}

	/**
	 * @param funUrl the funUrl to set
	 */
	public void setFunUrl(String funUrl) {
		this.funUrl = funUrl;
	}

	/**
	 * @return the opWay
	 */
	public String getOpWay() {
		return opWay;
	}

	/**
	 * @param opWay the opWay to set
	 */
	public void setOpWay(String opWay) {
		this.opWay = opWay;
	}

	/**
	 * @return the resourceUpperId
	 */
	public Long getResourceUpperId() {
		return resourceUpperId;
	}

	/**
	 * @param resourceUpperId the resourceUpperId to set
	 */
	public void setResourceUpperId(Long resourceUpperId) {
		this.resourceUpperId = resourceUpperId;
	}

	/**
	 * @return the subSys
	 */
	public String getSubSys() {
		return subSys;
	}

	/**
	 * @param subSys the subSys to set
	 */
	public void setSubSys(String subSys) {
		this.subSys = subSys;
	}

	@Column(nullable = false, name = "SUBSYS")
	private String subSys;

	public ResourceInfo() {
	}

	public ResourceInfo(Long resourceId, String resourceName) {
		this.resourceId = resourceId;
		this.resourceName = resourceName;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	@Transient
	public String getPrefixedName() {
		return RESOURCE_PREFIX + resourceName;
	}

	@Transient
	public String getPrefixedId() {
		return RESOURCE_PREFIX + this.resourceId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Long getResourceId() {
		return resourceId;
	}
}
