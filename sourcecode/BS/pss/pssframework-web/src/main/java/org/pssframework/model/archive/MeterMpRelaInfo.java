/**
 * 
 */
package org.pssframework.model.archive;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

	// METER_ID NUMBER(16) not null,
	@Column(name = "METER_ID")
	private Long meterId;

	// MP_ID NUMBER(16)
	// MP_ID VARCHAR2(8),设备的产权说明 01 局属、 02 用户
	@Column(name = "MP_ID")
	private Long mpId;;

	/**
	 * @return the meterMpId
	 */
	public Long getMeterMpId() {
		return meterMpId;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/**
	 * @param meterMpId
	 *            the meterMpId to set
	 */
	public void setMeterMpId(Long meterMpId) {
		this.meterMpId = meterMpId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * @param meterId the meterId to set
	 */
	public void setMeterId(Long meterId) {
		this.meterId = meterId;
	}

	/**
	 * @return the meterId
	 */
	public Long getMeterId() {
		return meterId;
	}

	/**
	 * @param mpId the mpId to set
	 */
	public void setMpId(Long mpId) {
		this.mpId = mpId;
	}

	/**
	 * @return the mpId
	 */
	public Long getMpId() {
		return mpId;
	}
}
