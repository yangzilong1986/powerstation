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
 *comment on table C_GP is '采集系统范畴内的测量点概念';
 * 
 * @author baocj
 * @since 1.0.0
 */
@Entity
@Table(name = "C_GP")
@SequenceGenerator(sequenceName = "SEQ_C_GP", name = "SEQ_C_GP")
public class GpInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 29826964346975554L;

	@Column(name = "GP_ID", unique = true, nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_C_GP")
	// GP_ID not null
	private Long gpId;

	@Column(name = "OBJECT_ID", nullable = false)
	// OBJECT_ID NUMBER not null,is
	private Long objectId;

	// TERM_ID NUMBER,
	@Column(name = "TERM_ID")
	private Long termId;

	// MP_ID NUMBER,
	@Column(name = "MP_ID")
	private Long mpId;

	// GM_ID NUMBER,
	@Column(name = "GM_ID")
	private Long gmId;

	// GP_SN NUMBER,
	@Column(name = "GP_SN")
	private Long gpSn;

	// GP_CHAR VARCHAR2(5),'见编码GP_CHAR'
	@Column(name = "GP_CHAR", length = 5)
	private String gpChar;

	// GP_TYPE VARCHAR2(5),is '见编码GP_TYPE';
	@Column(name = "GP_TYPE", length = 5)
	private String gpType;

	// GP_STATUS VARCHAR2(5),is '见编码GP_STATUS，1 - 有效 0 - 无效';
	@Column(name = "GP_STATUS", length = 5)
	private String gpStatus;

	// GP_ADDR VARCHAR2(20),is '通讯地址';
	@Column(name = "GP_ADDR", length = 20)
	private String gpAddr;

	// CT_TIMES NUMBER,
	@Column(name = "CT_TIMES")
	private Long ctTimes;

	// PT_TIMES NUMBER,
	@Column(name = "PT_TIMES")
	private Long ptTimes;

	// PORT VARCHAR2(20),
	@Column(name = "PORT", length = 20)
	private String port;

	// PROTOCOL_NO VARCHAR2(5),is '电表规约号，见编码PROTOCOL_METER';
	@Column(name = "PROTOCOL_NO", length = 5)
	private String protocolNo;

	// COMPUTE_FLAG VARCHAR2(5),is '电量计算标识 1 - 参与计算 0 - 不参与计算';
	@Column(name = "COMPUTE_FLAG", length = 5)
	private String computeFlag;

	// PLUSE_CONSTANT NUMBER,
	@Column(name = "PLUSE_CONSTANT")
	private Long pluseConstant;

	// METER_CONSTANT NUMBER,
	@Column(name = "METER_CONSTANT")
	private Long meterConstant;

	// SUCRAT_CPT_ID VARCHAR2(5),1 - 参与计算 0 - 不参与计算'
	@Column(name = "SUCRAT_CPT_ID", length = 5)
	private String sucratCptId;

	// LINE_ID NUMBER,
	@Column(name = " LINE_ID")
	private Long lineId;

	// MAS_METER_SN NUMBER,
	@Column(name = "MAS_METER_SN ")
	private Long masMeterSn;

	/**
	 * @return the gpId
	 */
	public Long getGpId() {
		return gpId;
	}

	/**
	 * @param gpId
	 *            the gpId to set
	 */
	public void setGpId(Long gpId) {
		this.gpId = gpId;
	}

	/**
	 * @return the objectId
	 */
	public Long getObjectId() {
		return objectId;
	}

	/**
	 * @param objectId
	 *            the objectId to set
	 */
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	/**
	 * @return the termId
	 */
	public Long getTermId() {
		return termId;
	}

	/**
	 * @param termId
	 *            the termId to set
	 */
	public void setTermId(Long termId) {
		this.termId = termId;
	}

	/**
	 * @return the mpId
	 */
	public Long getMpId() {
		return mpId;
	}

	/**
	 * @param mpId
	 *            the mpId to set
	 */
	public void setMpId(Long mpId) {
		this.mpId = mpId;
	}

	/**
	 * @return the gmId
	 */
	public Long getGmId() {
		return gmId;
	}

	/**
	 * @param gmId
	 *            the gmId to set
	 */
	public void setGmId(Long gmId) {
		this.gmId = gmId;
	}

	/**
	 * @return the gpSn
	 */
	public Long getGpSn() {
		return gpSn;
	}

	/**
	 * @param gpSn
	 *            the gpSn to set
	 */
	public void setGpSn(Long gpSn) {
		this.gpSn = gpSn;
	}

	/**
	 * @return the gpChar
	 */
	public String getGpChar() {
		return gpChar;
	}

	/**
	 * @param gpChar
	 *            the gpChar to set
	 */
	public void setGpChar(String gpChar) {
		this.gpChar = gpChar;
	}

	/**
	 * @return the gpType
	 */
	public String getGpType() {
		return gpType;
	}

	/**
	 * @param gpType
	 *            the gpType to set
	 */
	public void setGpType(String gpType) {
		this.gpType = gpType;
	}

	/**
	 * @return the gpStatus
	 */
	public String getGpStatus() {
		return gpStatus;
	}

	/**
	 * @param gpStatus
	 *            the gpStatus to set
	 */
	public void setGpStatus(String gpStatus) {
		this.gpStatus = gpStatus;
	}

	/**
	 * @return the gpAddr
	 */
	public String getGpAddr() {
		return gpAddr;
	}

	/**
	 * @param gpAddr
	 *            the gpAddr to set
	 */
	public void setGpAddr(String gpAddr) {
		this.gpAddr = gpAddr;
	}

	/**
	 * @return the ctTimes
	 */
	public Long getCtTimes() {
		return ctTimes;
	}

	/**
	 * @param ctTimes
	 *            the ctTimes to set
	 */
	public void setCtTimes(Long ctTimes) {
		this.ctTimes = ctTimes;
	}

	/**
	 * @return the ptTimes
	 */
	public Long getPtTimes() {
		return ptTimes;
	}

	/**
	 * @param ptTimes
	 *            the ptTimes to set
	 */
	public void setPtTimes(Long ptTimes) {
		this.ptTimes = ptTimes;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @return the protocolNo
	 */
	public String getProtocolNo() {
		return protocolNo;
	}

	/**
	 * @param protocolNo
	 *            the protocolNo to set
	 */
	public void setProtocolNo(String protocolNo) {
		this.protocolNo = protocolNo;
	}

	/**
	 * @return the computeFlag
	 */
	public String getComputeFlag() {
		return computeFlag;
	}

	/**
	 * @param computeFlag
	 *            the computeFlag to set
	 */
	public void setComputeFlag(String computeFlag) {
		this.computeFlag = computeFlag;
	}

	/**
	 * @return the pluseConstant
	 */
	public Long getPluseConstant() {
		return pluseConstant;
	}

	/**
	 * @param pluseConstant
	 *            the pluseConstant to set
	 */
	public void setPluseConstant(Long pluseConstant) {
		this.pluseConstant = pluseConstant;
	}

	/**
	 * @return the meterConstant
	 */
	public Long getMeterConstant() {
		return meterConstant;
	}

	/**
	 * @param meterConstant
	 *            the meterConstant to set
	 */
	public void setMeterConstant(Long meterConstant) {
		this.meterConstant = meterConstant;
	}

	/**
	 * @return the sucratCptId
	 */
	public String getSucratCptId() {
		return sucratCptId;
	}

	/**
	 * @param sucratCptId
	 *            the sucratCptId to set
	 */
	public void setSucratCptId(String sucratCptId) {
		this.sucratCptId = sucratCptId;
	}

	/**
	 * @return the lineId
	 */
	public Long getLineId() {
		return lineId;
	}

	/**
	 * @param lineId
	 *            the lineId to set
	 */
	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}

	/**
	 * @return the masMeterSn
	 */
	public Long getMasMeterSn() {
		return masMeterSn;
	}

	/**
	 * @param masMeterSn
	 *            the masMeterSn to set
	 */
	public void setMasMeterSn(Long masMeterSn) {
		this.masMeterSn = masMeterSn;
	}

	/**
	 * @return the tranId
	 */
	public Long getTranId() {
		return tranId;
	}

	/**
	 * @param tranId
	 *            the tranId to set
	 */
	public void setTranId(Long tranId) {
		this.tranId = tranId;
	}

	/**
	 * @return the lasttimeStamp
	 */
	public Date getLasttimeStamp() {
		return lasttimeStamp;
	}

	/**
	 * @param lasttimeStamp
	 *            the lasttimeStamp to set
	 */
	public void setLasttimeStamp(Date lasttimeStamp) {
		this.lasttimeStamp = lasttimeStamp;
	}

	// TRAN_ID NUMBER,
	@Column(name = "TRAN_ID ")
	private Long tranId;

	// LASTTIME_STAMP DATE default SYSDATEis '最后表结构修改时间戳';
	@Column(name = "LASTTIME_STAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lasttimeStamp;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
