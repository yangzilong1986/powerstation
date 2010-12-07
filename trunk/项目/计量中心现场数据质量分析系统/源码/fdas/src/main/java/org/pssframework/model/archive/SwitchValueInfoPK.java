/**
 * 
 */
package org.pssframework.model.archive;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.pssframework.base.BaseEntity;

/**
必须实现Serializable序列化 2、必须提示无参的构造方法 3、必须重写hashCode和equals方法  
 *   
 * @Embeddable 表示该类中所有属性在应用该联合主键的类中作为它的属性（字段）  
 * @author djs-baocj
 *
 */
public class SwitchValueInfoPK extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6481563042067392610L;

	@ManyToOne
	@JoinColumn(name = "TERM_ID", nullable = false)
	private TerminalInfo terminalInfo;

	//SWITCH_NO
	@Column(name = "SWITCH_NO", nullable = false)
	private Long switchNo;

	public SwitchValueInfoPK(TerminalInfo terminalInfo, Long swithNo) {
		this.terminalInfo = terminalInfo;
		this.switchNo = swithNo;
	}

	public SwitchValueInfoPK(TerminalInfo terminalInfo) {
		this.terminalInfo = terminalInfo;
	}

	public SwitchValueInfoPK() {
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);

	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
