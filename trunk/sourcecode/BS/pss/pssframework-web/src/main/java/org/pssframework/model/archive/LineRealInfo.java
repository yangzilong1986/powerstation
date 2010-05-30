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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.pssframework.base.BaseEntity;

/**
 * 1) 描述了线路与线路的多对多关系，但当前生效的只有一个，主要属性：当前线路关系标志
2) 通过线损基础信息管理业务中录入产生记录，或通过与生产系统接口过程产生记录。
3) 该实体主要由线损基础信息管理业务、考核单元管理业务使用
 * @author Administrator
 *
 */
@Entity
@Table(name = "G_LINE_REAL")
public class LineRealInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5114228822304231681L;

	@Column(name = "LINE_RELA_ID", unique = true, nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_G_LINE_REAL")
	// RELA_ID              NUMBER(16)                      not null,
	private Long lineRelaId;

	@Column(name = "LINE_ID", length = 16)
	// LINE_ID             NUMBER(16),
	private Long lineId;

	@Column(name = "LINK_LINE_ID", length = 16)
	// LINK_LINE_ID              NUMBER(16),
	private Long linkLineId;

	@Column(name = "CASCADE_FLAG", length = 8)
	// CASCADE_FLAG              VARCHAR2(8),
	private Long cascadeFlag;

	@Column(name = "LASTTIME_STAMP")
	@Temporal(TemporalType.TIMESTAMP)
	//LASTTIME_STAMP  DATE default SYSDATE
	private Date lasttimeStamp;

	/**
	 * @return the cascadeFlag
	 */
	public Long getCascadeFlag() {
		return cascadeFlag;
	}

	/**
	 * @return the lasttimeStamp
	 */
	public Date getLasttimeStamp() {
		return lasttimeStamp;
	}

	/**
	 * @return the lineId
	 */
	public Long getLineId() {
		return lineId;
	}

	/**
	 * @return the lineRelaId
	 */
	public Long getLineRelaId() {
		return lineRelaId;
	}

	/**
	 * @return the linkLineId
	 */
	public Long getLinkLineId() {
		return linkLineId;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/**
	 * @param cascadeFlag the cascadeFlag to set
	 */
	public void setCascadeFlag(Long cascadeFlag) {
		this.cascadeFlag = cascadeFlag;
	}

	/**
	 * @param lasttimeStamp the lasttimeStamp to set
	 */
	public void setLasttimeStamp(Date lasttimeStamp) {
		this.lasttimeStamp = lasttimeStamp;
	}

	/**
	 * @param lineId the lineId to set
	 */
	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}

	/**
	 * @param lineRelaId the lineRelaId to set
	 */
	public void setLineRelaId(Long lineRelaId) {
		this.lineRelaId = lineRelaId;
	}

	/**
	 * @param linkLineId the linkLineId to set
	 */
	public void setLinkLineId(Long linkLineId) {
		this.linkLineId = linkLineId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
