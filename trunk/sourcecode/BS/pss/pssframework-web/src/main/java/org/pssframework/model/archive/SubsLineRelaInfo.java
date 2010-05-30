/**
 * 
 */
package org.pssframework.model.archive;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.pssframework.base.BaseEntity;

/**
 * @author Administrator
 *
 */
public class SubsLineRelaInfo extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6405948240752345666L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
