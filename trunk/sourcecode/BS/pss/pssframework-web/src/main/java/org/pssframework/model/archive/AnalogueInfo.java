/**
 * 
 */
package org.pssframework.model.archive;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.pssframework.base.BaseEntity;

/**
 * @author djs-baocj
 *模拟量
 */
@Entity
@Table(name = "C_ANALOGUE")
public class AnalogueInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4469888225478750148L;

	@Id
	@Column(name = "GP_ID")
	@GeneratedValue(generator = "pkGenerator")
	@GenericGenerator(name = "pkGenerator", strategy = "foreign", parameters = @Parameter(name = "property", value = "gpInfo"))
	private Long gpId;

	/**
	 * @return the gpId
	 */
	public Long getGpId() {
		return gpId;
	}

	/**
	 * @param gpId the gpId to set
	 */
	public void setGpId(Long gpId) {
		this.gpId = gpId;
	}

	@OneToOne(cascade = { CascadeType.ALL })
	@PrimaryKeyJoinColumn
	private GpInfo gpInfo;

	//ANALOGUE_NAME名称
	@Column(name = "ANALOGUE_NAME")
	private String analogueName;

	//量程起始值RANGE_BEGIN
	@Column(name = "RANGE_BEGIN")
	private String rangeBegin;

	//量程结束值RANGE_END
	@Column(name = "RANGE_END")
	private String rangeEnd;

	//模拟量上限MAX_VALUE
	@Column(name = "MAX_VALUE")
	private String maxValue;

	//模拟量下限MIN_VALUE
	@Column(name = "MIN_VALUE")
	private String minValue;

	//冻结密度FREEZ_DENSITY
	@Column(name = "FREEZ_DENSITY")
	private String freezDensity;

	/**
	 * @return the freezDensity
	 */
	public String getFreezDensity() {
		return freezDensity;
	}

	/**
	 * @return the gpInfo
	 */
	public GpInfo getGpInfo() {
		return gpInfo;
	}

	/**
	 * @return the maxValue
	 */
	public String getMaxValue() {
		return maxValue;
	}

	/**
	 * @return the minValue
	 */
	public String getMinValue() {
		return minValue;
	}

	/**
	 * @return the rangeBegin
	 */
	public String getRangeBegin() {
		return rangeBegin;
	}

	/**
	 * @return the rangeEnd
	 */
	public String getRangeEnd() {
		return rangeEnd;
	}

	/**
	 * @return the analogueName
	 */
	public String getAnalogueName() {
		return analogueName;
	}

	/**
	 * @param analogueName the analogueName to set
	 */
	public void setAnalogueName(String analogueName) {
		this.analogueName = analogueName;
	}

	/**
	 * @param freezDensity the freezDensity to set
	 */
	public void setFreezDensity(String freezDensity) {
		this.freezDensity = freezDensity;
	}

	/**
	 * @param gpInfo the gpInfo to set
	 */
	public void setGpInfo(GpInfo gpInfo) {
		this.gpInfo = gpInfo;
	}

	/**
	 * @param maxValue the maxValue to set
	 */
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	/**
	 * @param minValue the minValue to set
	 */
	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	/**
	 * @param rangeBegin the rangeBegin to set
	 */
	public void setRangeBegin(String rangeBegin) {
		this.rangeBegin = rangeBegin;
	}

	/**
	 * @param rangeEnd the rangeEnd to set
	 */
	public void setRangeEnd(String rangeEnd) {
		this.rangeEnd = rangeEnd;
	}

}
