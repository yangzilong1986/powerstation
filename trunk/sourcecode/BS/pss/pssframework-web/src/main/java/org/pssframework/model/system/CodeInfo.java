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
@Table(name = "s_code")
@SequenceGenerator(sequenceName = "SEQ_S_CODE", name = "SEQ_S_CODE")
public class CodeInfo extends BaseEntity {

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

	/**
	 * @return the codeId
	 */
	public Long getCodeId() {
		return codeId;
	}

	/**
	 * @param codeId the codeId to set
	 */
	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}

	/**
	 * @return the codeCate
	 */
	public String getCodeCate() {
		return codeCate;
	}

	/**
	 * @param codeCate the codeCate to set
	 */
	public void setCodeCate(String codeCate) {
		this.codeCate = codeCate;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the codeType
	 */
	public String getCodeType() {
		return codeType;
	}

	/**
	 * @param codeType the codeType to set
	 */
	public void setCodeType(String codeType) {
		this.codeType = codeType;
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
