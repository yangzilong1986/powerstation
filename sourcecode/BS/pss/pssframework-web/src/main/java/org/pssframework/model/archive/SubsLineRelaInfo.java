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
  1) 描述了变电站与线路的多对多关系,是变电站与线路多对多关系转化的实体，但当前生效的只有一个
2) 线损基础信息管理业务中录入产生，或通过与生产系统接口过程产生。
3) 该实体主要由线损基础信息管理业务、考核单元管理业务使用。
 * @author Administrator
 *
 */
@Entity
@Table(name = "G_SUBS_LINE_RELA")
@SequenceGenerator(sequenceName = "SEQ_G_SUBS_LINE_RELA", name = "SEQ_G_SUBS_LINE_RELA", allocationSize = 1)
public class SubsLineRelaInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6405948240752345666L;

	@Column(name = "RELA_ID", unique = true, nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_G_SUBS_LINE_RELA")
	// RELA_ID NUMBER(16) not null,
	private Long relaId;

	@Column(name = "EQUIP_ID", length = 16)
	// EQUIP_ID NUMBER(16),
	private Long equioId;

	@Column(name = "LINE_ID", length = 16)
	// LINE_ID NUMBER(16),
	private Long lineId;

	@Column(name = "SUBS_ID", length = 16)
	// SUBS_ID NUMBER(16),
	private Long subsId;

	@Column(name = "RELA_FLAG", length = 8)
	// RELA_FLAG VARCHAR2(8),
	private String relaFlag;

	@Column(name = "LASTTIME_STAMP")
	@Temporal(TemporalType.TIMESTAMP)
	// LASTTIME_STAMP DATE default SYSDATE
	private Date lasttimeStamp;

	/**
	 * @return the equioId
	 */
	public Long getEquioId() {
		return equioId;
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
	 * @return the relaFlag
	 */
	public String getRelaFlag() {
		return relaFlag;
	}

	/**
	 * @return the relaId
	 */
	public Long getRelaId() {
		return relaId;
	}

	/**
	 * @return the subsId
	 */
	public Long getSubsId() {
		return subsId;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/**
	 * @param equioId the equioId to set
	 */
	public void setEquioId(Long equioId) {
		this.equioId = equioId;
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
	 * @param relaFlag the relaFlag to set
	 */
	public void setRelaFlag(String relaFlag) {
		this.relaFlag = relaFlag;
	}

	/**
	 * @param relaId the relaId to set
	 */
	public void setRelaId(Long relaId) {
		this.relaId = relaId;
	}

	/**
	 * @param subsId the subsId to set
	 */
	public void setSubsId(Long subsId) {
		this.subsId = subsId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
