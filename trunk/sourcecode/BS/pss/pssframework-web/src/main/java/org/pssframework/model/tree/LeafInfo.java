/**
 * 
 */
package org.pssframework.model.tree;

import org.pssframework.base.BaseEntity;

/**
 * @author Baocj
 *
 */
public class LeafInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8109595906150835427L;

	/**
	 * String id
	 */
	private String leafId;

	/**
	 * String type
	 */
	private String leafType;

	/**
	 * String type
	 */
	private String leafName;

	/**
	 * @return the leafParentId
	 */
	public String getLeafParentId() {
		return leafParentId;
	}

	/**
	 * @param leafParentId the leafParentId to set
	 */
	public void setLeafParentId(String leafParentId) {
		this.leafParentId = leafParentId;
	}

	/**
	 * @return the leafParentType
	 */
	public String getLeafParentType() {
		return leafParentType;
	}

	/**
	 * @param leafParentType the leafParentType to set
	 */
	public void setLeafParentType(String leafParentType) {
		this.leafParentType = leafParentType;
	}

	/**
	 * @return the leafParentName
	 */
	public String getLeafParentName() {
		return leafParentName;
	}

	/**
	 * @param leafParentName the leafParentName to set
	 */
	public void setLeafParentName(String leafParentName) {
		this.leafParentName = leafParentName;
	}

	/**
	 * String id
	 */
	private String leafParentId;

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
	public String getLeafId() {
		return leafId;
	}

	/**
	 * @param leafId the leafId to set
	 */
	public void setLeafId(String leafId) {
		this.leafId = leafId;
	}

	/**
	 * @return the leafType
	 */
	public String getLeafType() {
		return leafType;
	}

	/**
	 * @param leafType the leafType to set
	 */
	public void setLeafType(String leafType) {
		this.leafType = leafType;
	}

	/**
	 * @param leafName the leafName to set
	 */
	public void setLeafName(String leafName) {
		this.leafName = leafName;
	}

	/**
	 * @return the leafName
	 */
	public String getLeafName() {
		return leafName;
	}

}
