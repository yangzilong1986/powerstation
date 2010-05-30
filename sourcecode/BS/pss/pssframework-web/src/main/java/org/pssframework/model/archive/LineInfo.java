/**
 * 
 */
package org.pssframework.model.archive;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.pssframework.base.BaseEntity;

/**
1) 描述线路基本信息，主要属性包括线路编码、线路名称、线损计算方式、单位长度线路电阻、单位长度线路电抗
2) 线损基础信息管理业务中录入产生，或通过与生产系统接口过程产生。
3) 该实体主要由线损基础信息管理业务、考核单元管理业务使用，在电费计算用户专线线路损耗也需要使用。
 *
 * @author baocj
 * @since 1.0.0
 */
@Entity
@Table(name = "g_line")
@SequenceGenerator(sequenceName = "SEQ_G_LINE", name = "SEQ_G_LINE")
public class LineInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2242592900984282828L;

	@Column(name = "LINE_ID", unique = true, nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_G_LINE")
	// LINE_ID              NUMBER(16)                      not null,
	private Long lineId;

	@Column(name = "LINE_NO", length = 16)
	//LINE_NO              VARCHAR2(16),
	private String LineNo;

	@Column(name = "LINE_NAME", length = 256, nullable = false)
	//LINE_NAME            VARCHAR2(256)                   not null,
	private String LineName;

	@Column(name = "ORG_NO", length = 16, nullable = false)
	// ORG_NO          VARCHAR2(16) not null,
	private String orgNo;

	@Column(name = "VOLT_CODE", length = 8)
	//VOLT_CODE            VARCHAR2(8),
	private String voltCode;

	@Column(name = "SUBLINE_FLAG", length = 8)
	//SUBLINE_FLAG         VARCHAR2(8),
	private String sublineFlag;

	@Column(name = "RUN_STATUS_CODE", length = 16)
	//RUN_STATUS_CODE      VARCHAR2(8),
	private String runStatusCode;

	@Column(name = "CONS_ID", length = 16)
	// CONS_ID              NUMBER(16),
	private Long consId;

	@Column(name = "LASTTIME_STAMP")
	@Temporal(TemporalType.TIMESTAMP)
	//LASTTIME_STAMP  DATE default SYSDATE
	private Date lasttimeStamp;

	/**
	 * @return the consId
	 */
	public Long getConsId() {
		return consId;
	}

	/**
	 * @return the lineId
	 */
	public Long getLineId() {
		return lineId;
	}

	/**
	 * @return the lineName
	 */
	public String getLineName() {
		return LineName;
	}

	/**
	 * @return the lineNo
	 */
	public String getLineNo() {
		return LineNo;
	}

	/**
	 * @return the runStatusCode
	 */
	public String getRunStatusCode() {
		return runStatusCode;
	}

	/**
	 * @return the sublineFlag
	 */
	public String getSublineFlag() {
		return sublineFlag;
	}

	/**
	 * @return the voltCode
	 */
	public String getVoltCode() {
		return voltCode;
	}

	/**
	 * @param consId the consId to set
	 */
	public void setConsId(Long consId) {
		this.consId = consId;
	}

	/**
	 * @param lineId the lineId to set
	 */
	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}

	/**
	 * @param lineName the lineName to set
	 */
	public void setLineName(String lineName) {
		LineName = lineName;
	}

	/**
	 * @param lineNo the lineNo to set
	 */
	public void setLineNo(String lineNo) {
		LineNo = lineNo;
	}

	/**
	 * @param runStatusCode the runStatusCode to set
	 */
	public void setRunStatusCode(String runStatusCode) {
		this.runStatusCode = runStatusCode;
	}

	/**
	 * @param sublineFlag the sublineFlag to set
	 */
	public void setSublineFlag(String sublineFlag) {
		this.sublineFlag = sublineFlag;
	}

	/**
	 * @param voltCode the voltCode to set
	 */
	public void setVoltCode(String voltCode) {
		this.voltCode = voltCode;
	}

	/**
	 * @param orgNo the orgNo to set
	 */
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	/**
	 * @return the orgNo
	 */
	public String getOrgNo() {
		return orgNo;
	}

	/**
	 * @param lasttimeStamp the lasttimeStamp to set
	 */
	public void setLasttimeStamp(Date lasttimeStamp) {
		this.lasttimeStamp = lasttimeStamp;
	}

	/**
	 * @return the lasttimeStamp
	 */
	public Date getLasttimeStamp() {
		return lasttimeStamp;
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
