/**
 * 
 */
package org.pssframework.model.archive;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.pssframework.base.BaseEntity;

/**
1) 当前线路与台区间是多对多关系，此实体是多对多关系的转化，但当前生效的只有一个，主要属性：当前线路台区关系标志
2) 通过线损基础信息管理业务中录入产生记录，或通过与生产系统接口过程产生记录。
3) 该实体主要由线损基础信息管理业务、考核单元管理业务使用。
 *
 * @author baocj
 * @since 1.0.0
 */
@Entity
@Table(name = "G_LINE_TG_RELA")
@SequenceGenerator(sequenceName = "SEQ_G_LINE_TG_RELA", name = "SEQ_G_LINE_TG_RELA", allocationSize = 1)
public class LineTgRelaInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2825980935242589812L;

	@Column(name = "LINE_TG_ID", unique = true, nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_G_LINE_TG_RELA")
	// LINE_TQ_ID NUMBER(16) not null,
	private Long lineTgId;

	@ManyToOne(targetEntity = TgInfo.class, cascade = CascadeType.REFRESH)
	// TG_ID NUMBER(16),
	@JoinColumn(name = "TG_ID")
	private TgInfo tgInfo;

	@Column(name = "LINE_ID", length = 8, nullable = false)
	// LINE_ID NUMBER(16),
	private Long lineId;

	@Column(name = "RELA_FLAG", length = 8, nullable = false)
	// RELA_FLAG VARCHAR2(8),
	private Long relaFlag;

	@Column(name = "LASTTIME_STAMP")
	@Temporal(TemporalType.TIMESTAMP)
	// LASTTIME_STAMP DATE default SYSDATE
	private Date lasttimeStamp;

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
	 * @return the lineTgId
	 */
	public Long getLineTgId() {
		return lineTgId;
	}

	/**
	 * @return the relaFlag
	 */
	public Long getRelaFlag() {
		return relaFlag;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
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
	 * @param lineTgId the lineTgId to set
	 */
	public void setLineTgId(Long lineTgId) {
		this.lineTgId = lineTgId;
	}

	/**
	 * @param relaFlag the relaFlag to set
	 */
	public void setRelaFlag(Long relaFlag) {
		this.relaFlag = relaFlag;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * @param tgInfo the tgInfo to set
	 */
	public void setTgInfo(TgInfo tgInfo) {
		this.tgInfo = tgInfo;
	}

	/**
	 * @return the tgInfo
	 */
	public TgInfo getTgInfo() {
		return tgInfo;
	}
}
