/**
 * 
 */
package org.pssframework.model.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.pssframework.base.BaseEntity;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name = "a_code")
@SequenceGenerator(sequenceName = "SEQ_S_CODE", name = "SEQ_S_CODE", allocationSize = 1)
public class CodeInfo extends BaseEntity {
	public static final String CODECATE = "codecate";
	public static final String CODE = "code";
	/**
	 * 
	 */
	private static final long serialVersionUID = -3125450081674249228L;

	@Column(name = "CODE_ID", unique = true, nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_S_CODE")
	private Long codeId; // 标准代码实体联合主键

	@Column(name = "CODE_CATE")
	private String codeCate; // 代码类别

	@Column(name = "CODE")
	private String code; // 代码值

	@Column(name = "NAME")
	private String name; // 标准代码名称

	@Column(name = "REMARK")
	private String remark; // 备注

	@Column(name = "CODE_TYPE")
	private String codeType; // 代码类型 : 0 – 系统编码 1 – 用户编码

	@Column(name = "VALUE")
	private String value; // 代码类型 : 0 – 系统编码 1 – 用户编码

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the codeCate
	 */
	public String getCodeCate() {
		return codeCate;
	}

	/**
	 * @return the codeId
	 */
	public Long getCodeId() {
		return codeId;
	}

	/**
	 * @return the codeType
	 */
	public String getCodeType() {
		return codeType;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @param codeCate the codeCate to set
	 */
	public void setCodeCate(String codeCate) {
		this.codeCate = codeCate;
	}

	/**
	 * @param codeId the codeId to set
	 */
	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}

	/**
	 * @param codeType the codeType to set
	 */
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
