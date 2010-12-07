/**
 * 
 */
package org.pssframework.model.archive;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.pssframework.base.BaseEntity;

/**
 * @author djs-baocj
 *开关量
 */
@Entity
@Table(name = "C_SWITCH_VALUE")
@SequenceGenerator(sequenceName = "SEQ_C_switch", name = "SEQ_C_switch", allocationSize = 1)
public class SwitchValueInfo extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -416125951250273937L;

	@Column(name = "SWITCH_ID", unique = true, nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_C_switch")
	// SWITCH_ID NUMBER not null,is '开关量(leakage protection switch)';
	private Long switchId;

	@ManyToOne
	@JoinColumn(name = "TERM_ID", nullable = false)
	private TerminalInfo terminalInfo;

	//SWITCH_NO
	@Column(name = "SWITCH_NO", nullable = false)
	private Long switchNo;

	/**
	 * @return the switchId
	 */
	public Long getSwitchId() {
		return switchId;
	}

	/**
	 * @param switchId the switchId to set
	 */
	public void setSwitchId(Long switchId) {
		this.switchId = switchId;
	}

	/**
	 * @return the terminalInfo
	 */
	public TerminalInfo getTerminalInfo() {
		return terminalInfo;
	}

	/**
	 * @param terminalInfo the terminalInfo to set
	 */
	public void setTerminalInfo(TerminalInfo terminalInfo) {
		this.terminalInfo = terminalInfo;
	}

	/**
	 * @return the switchNo
	 */
	public Long getSwitchNo() {
		return switchNo;
	}

	/**
	 * @param switchNo the switchNo to set
	 */
	public void setSwitchNo(Long switchNo) {
		this.switchNo = switchNo;
	}

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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);

	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
