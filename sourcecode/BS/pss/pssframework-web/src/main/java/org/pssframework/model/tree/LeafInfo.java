/**
 * 
 */
package org.pssframework.model.tree;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.pssframework.base.BaseQuery;

/**
 * @author Baocj
 *
 */
public class LeafInfo extends BaseQuery {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8109595906150835427L;

	/**
	 * String id
	 */
	private Long leafId;

	/**
	 * String type
	 */
	private String leafType;

	/**
	 * String type
	 */
	private String leafName;

	/**
	 * String id
	 */
	private Long leafParentId;

	/**
	 * String type
	 */
	private String leafParentType;

	/**
	 * String type
	 */
	private String leafParentName;

	/**
	 * @return the leafId
	 */
	public Long getLeafId() {
		return leafId;
	}

	/**
	 * @return the leafName
	 */
	public String getLeafName() {
		return leafName;
	}

	/**
	 * @return the leafParentId
	 */
	public Long getLeafParentId() {
		return leafParentId;
	}

	/**
	 * @return the leafParentName
	 */
	public String getLeafParentName() {
		return leafParentName;
	}

	/**
	 * @return the leafParentType
	 */
	public String getLeafParentType() {
		return leafParentType;
	}

	/**
	 * @return the leafType
	 */
	public String getLeafType() {
		return leafType;
	}

	/**
	 * @param leafId the leafId to set
	 */
	public void setLeafId(Long leafId) {
		this.leafId = leafId;
	}

	/**
	 * @param leafName the leafName to set
	 */
	public void setLeafName(String leafName) {
		this.leafName = leafName;
	}

	/**
	 * @param leafParentId the leafParentId to set
	 */
	public void setLeafParentId(Long leafParentId) {
		this.leafParentId = leafParentId;
	}

	/**
	 * @param leafParentName the leafParentName to set
	 */
	public void setLeafParentName(String leafParentName) {
		this.leafParentName = leafParentName;
	}

	/**
	 * @param leafParentType the leafParentType to set
	 */
	public void setLeafParentType(String leafParentType) {
		this.leafParentType = leafParentType;
	}

	/**
	 * @param leafType the leafType to set
	 */
	public void setLeafType(String leafType) {
		this.leafType = leafType;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
