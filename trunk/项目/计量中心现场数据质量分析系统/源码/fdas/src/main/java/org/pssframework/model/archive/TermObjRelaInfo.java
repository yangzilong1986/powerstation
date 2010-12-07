/**
 * 
 */
package org.pssframework.model.archive;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.pssframework.base.BaseEntity;

/**
 *
 * @author baocj
 * @since 1.0.0
 */
@Entity
@Table(name = "C_TERM_OBJ_RELA")
@SequenceGenerator(sequenceName = "SEQ_C_TERM_OBJ_RELA", name = "SEQ_C_TERM_OBJ_RELA", allocationSize = 1)
public class TermObjRelaInfo extends BaseEntity {

	private static final long serialVersionUID = 2597820481102051021L;

	@Column(name = "TERM_OBJ_ID", unique = true, nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_C_TERM_OBJ_RELA")
	// TERM_OBJ_ID NUMBER(16) not null,
	private Long termObjId;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "TERM_ID", referencedColumnName = "TERM_ID")
	private TerminalInfo terminalInfo;

	@Column(name = "OBJ_TYPE")
	private String objType;

	@Column(name = "OBJ_ID")
	private Long objId;

	/**
	 * @return the objId
	 */
	public Long getObjId() {
		return objId;
	}

	/**
	 * @return the objType
	 */
	public String getObjType() {
		return objType;
	}

	/**
	 * @return the terminalInfo
	 */
	public TerminalInfo getTerminalInfo() {
		return terminalInfo;
	}

	/**
	 * @return the termObjId
	 */
	public Long getTermObjId() {
		return termObjId;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/**
	 * @param objId the objId to set
	 */
	public void setObjId(Long objId) {
		this.objId = objId;
	}

	/**
	 * @param objType the objType to set
	 */
	public void setObjType(String objType) {
		this.objType = objType;
	}

	/**
	 * @param terminalInfo the terminalInfo to set
	 */
	public void setTerminalInfo(TerminalInfo terminalInfo) {
		this.terminalInfo = terminalInfo;
	}

	/**
	 * @param termObjId the termObjId to set
	 */
	public void setTermObjId(Long termObjId) {
		this.termObjId = termObjId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
