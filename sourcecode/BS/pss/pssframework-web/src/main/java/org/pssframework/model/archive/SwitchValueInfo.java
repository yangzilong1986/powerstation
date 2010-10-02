/**
 * 
 */
package org.pssframework.model.archive;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.pssframework.base.BaseEntity;

/**
 * @author djs-baocj
 *开关量
 */
@Entity
@Table(name = "C_SWITCH_VALUE")
public class SwitchValueInfo extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -416125951250273937L;

	// 联合主键   
	@EmbeddedId
	private SwitchValueInfoPK switchValueId;

	//开关量属性：置1常开触点。置0：常闭触点。SWITCH_TYPE
	@Column(name = "SWITCH_TYPE")
	private String switchType;

	//开关量名称SWITCH_VALUE_NAME
	@Column(name = "SWITCH_VALUE_NAME")
	private String switchValueName;

	@Transient
	private Long termIdOld;

	@Transient
	private Long switchNoOld;

	/**
	 * @return the switchNoOld
	 */
	public Long getSwitchNoOld() {
		return switchNoOld;
	}

	/**
	 * @return the switchType
	 */
	public String getSwitchType() {
		return switchType;
	}

	/**
	 * @return the switchValueId
	 */
	public SwitchValueInfoPK getSwitchValueId() {
		return switchValueId;
	}

	/**
	 * @return the switchValueName
	 */
	public String getSwitchValueName() {
		return switchValueName;
	}

	/**
	 * @return the switchType
	 */
	public String getSwithType() {
		return switchType;
	}

	/**
	 * @return the switchValueName
	 */
	public String getSwithValueName() {
		return switchValueName;
	}

	/**
	 * @return the termIdOld
	 */
	public Long getTermIdOld() {
		return termIdOld;
	}

	/**
	 * @param switchNoOld the switchNoOld to set
	 */
	public void setSwitchNoOld(Long switchNoOld) {
		this.switchNoOld = switchNoOld;
	}

	/**
	 * @param switchType the switchType to set
	 */
	public void setSwitchType(String switchType) {
		this.switchType = switchType;
	}

	/**
	 * @param switchValueId the switchValueId to set
	 */
	public void setSwitchValueId(SwitchValueInfoPK switchValueId) {
		this.switchValueId = switchValueId;
	}

	/**
	 * @param switchValueName the switchValueName to set
	 */
	public void setSwitchValueName(String switchValueName) {
		this.switchValueName = switchValueName;
	}

	/**
	 * @param switchType the switchType to set
	 */
	public void setSwithType(String switchType) {
		this.switchType = switchType;
	}

	/**
	 * @param switchValueId the switchValueId to set
	 */
	public void setSwithValueId(SwitchValueInfoPK switchValueId) {
		this.switchValueId = switchValueId;
	}

	/**
	 * @param switchValueName the switchValueName to set
	 */
	public void setSwithValueName(String switchValueName) {
		this.switchValueName = switchValueName;
	}

	/**
	 * @param termIdOld the termIdOld to set
	 */
	public void setTermIdOld(Long termIdOld) {
		this.termIdOld = termIdOld;
	}

}
