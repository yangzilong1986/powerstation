/**
 * 
 */
package org.pssframework.model.tree;

import org.pssframework.base.BaseQuery;

/**
 * @author Administrator
 *
 */

public class LeafQuery extends BaseQuery {
	private String parentId;
	private String parentType;

	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the parentType
	 */
	public String getParentType() {
		return parentType;
	}

	/**
	 * @param parentType the parentType to set
	 */
	public void setParentType(String parentType) {
		this.parentType = parentType;
	}
}
