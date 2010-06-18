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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.pssframework.base.BaseEntity;

/**
 * 1)用于记录电能表和计量点之间关系的信息，定义了电能表与计量点的唯一标识属性，本实体主要包括电能表计量点关系标识、电能表资产编号、计量点编号等属性。
 * 2)通过新装、增容及变更用电归档、关口计量点新装及变更归档等业务，由实体转入产生记录。 3)该实体主要由查询计量点相关信息等业务使用。
 * 
 * @author baocj
 * @since 1.0.0
 */
@Entity
@Table(name = "C_METER_MP_RELA")
@SequenceGenerator(sequenceName = "SEQ_C_METER_MP_RELA", name = "SEQ_C_METER_MP_RELA", allocationSize = 1)
public class MeterMpRelaInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8779041510217455242L;

	@Column(name = "METER_MP_ID", unique = true, nullable = false, length = 16)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_C_METER_MP_RELA")
	// METER_MP_ID NUMBER(16) not null,
	private Long meterMpId;

	@OneToOne(targetEntity = MeterInfo.class)
	@JoinColumn(name = "METER_ID", referencedColumnName = "METER_ID")
	// METER_ID NUMBER(16) not null,
	private MeterInfo meterInfo;

	// MP_ID NUMBER(16)
	@OneToOne(targetEntity = MpInfo.class)
	@JoinColumn(name = "MP_ID", referencedColumnName = "MP_ID")
	// MP_ID VARCHAR2(8),设备的产权说明 01 局属、 02 用户
	private MpInfo mpInfo;

	/**
	 * @return the meterInfo
	 */
	public MeterInfo getMeterInfo() {
		return meterInfo;
	}

	/**
	 * @return the meterMpId
	 */
	public Long getMeterMpId() {
		return meterMpId;
	}

	/**
	 * @return the mpInfo
	 */
	public MpInfo getMpInfo() {
		return mpInfo;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/**
	 * @param meterInfo the meterInfo to set
	 */
	public void setMeterInfo(MeterInfo meterInfo) {
		this.meterInfo = meterInfo;
	}

	/**
	 * @param meterMpId
	 *            the meterMpId to set
	 */
	public void setMeterMpId(Long meterMpId) {
		this.meterMpId = meterMpId;
	}

	/**
	 * @param mpInfo the mpInfo to set
	 */
	public void setMpInfo(MpInfo mpInfo) {
		this.mpInfo = mpInfo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
