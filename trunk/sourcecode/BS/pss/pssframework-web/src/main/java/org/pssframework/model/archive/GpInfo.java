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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.pssframework.base.BaseEntity;

/**
 *comment on table C_GP is '采集系统范畴内的测量点概念';
 * 
 * @author baocj
 * @since 1.0.0
 */
@Entity
@Table(name = "C_GP")
@SequenceGenerator(sequenceName = "SEQ_C_GP", name = "SEQ_C_GP", allocationSize = 1)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
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

	@OneToOne(mappedBy = "gpInfo")
	private PsInfo psInfo;

	@ManyToOne(targetEntity = TgInfo.class)
	@JoinColumn(name = "OBJECT_ID", referencedColumnName = "TG_ID", insertable = false, updatable = false)
	private TgInfo tgInfo;

	@Column(name = "OBJECT_ID", nullable = false)
	// OBJECT_ID NUMBER not null,is
	private Long objectId;

	// TERM_ID NUMBER,
	@ManyToOne
	@JoinColumn(name = "TERM_ID", referencedColumnName = "TERM_ID")
	private TerminalInfo terminalInfo;

	// MP_ID NUMBER,
	@ManyToOne
	@JoinColumn(name = "MP_ID")
	private MpInfo mpInfo;

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
	@Column(name = "GP_ADDR", length = 12)
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

	// TRAN_ID NUMBER,
	@Column(name = "TRAN_ID ")
	private Long tranId;

	// LASTTIME_STAMP DATE default SYSDATEis '最后表结构修改时间戳';
	@Column(name = "LASTTIME_STAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lasttimeStamp;

	/**
	 * @return the computeFlag
	 */
	public String getComputeFlag() {
		return computeFlag;
	}

	/**
	 * @return the ctTimes
	 */
	public Long getCtTimes() {
		return ctTimes;
	}

	/**
	 * @return the gmId
	 */
	public Long getGmId() {
		return gmId;
	}

	/**
	 * @return the gpAddr
	 */
	public String getGpAddr() {
		return gpAddr;
	}

	/**
	 * @return the gpChar
	 */
	public String getGpChar() {
		return gpChar;
	}

	/**
	 * @return the gpId
	 */
	public Long getGpId() {
		return gpId;
	}

	/**
	 * @return the gpSn
	 */
	public Long getGpSn() {
		return gpSn;
	}

	/**
	 * @return the gpStatus
	 */
	public String getGpStatus() {
		return gpStatus;
	}

	/**
	 * @return the gpType
	 */
	public String getGpType() {
		return gpType;
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
	 * @return the masMeterSn
	 */
	public Long getMasMeterSn() {
		return masMeterSn;
	}

	/**
	 * @return the meterConstant
	 */
	public Long getMeterConstant() {
		return meterConstant;
	}

	/**
	 * @return the mpInfo
	 */
	public MpInfo getMpInfo() {
		return mpInfo;
	}

	/**
	 * @return the objectId
	 */
	public Long getObjectId() {
		return objectId;
	}

	/**
	 * @return the pluseConstant
	 */
	public Long getPluseConstant() {
		return pluseConstant;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @return the protocolNo
	 */
	public String getProtocolNo() {
		return protocolNo;
	}

	/**
	 * @return the ptTimes
	 */
	public Long getPtTimes() {
		return ptTimes;
	}

	/**
	 * @return the sucratCptId
	 */
	public String getSucratCptId() {
		return sucratCptId;
	}

	/**
	 * @return the terminalInfo
	 */
	public TerminalInfo getTerminalInfo() {
		return terminalInfo;
	}

	/**
	 * @return the tranId
	 */
	public Long getTranId() {
		return tranId;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/**
	 * @param computeFlag
	 *            the computeFlag to set
	 */
	public void setComputeFlag(String computeFlag) {
		this.computeFlag = computeFlag;
	}

	/**
	 * @param ctTimes
	 *            the ctTimes to set
	 */
	public void setCtTimes(Long ctTimes) {
		this.ctTimes = ctTimes;
	}

	/**
	 * @param gmId
	 *            the gmId to set
	 */
	public void setGmId(Long gmId) {
		this.gmId = gmId;
	}

	/**
	 * @param gpAddr
	 *            the gpAddr to set
	 */
	public void setGpAddr(String gpAddr) {
		this.gpAddr = gpAddr;
	}

	/**
	 * @param gpChar
	 *            the gpChar to set
	 */
	public void setGpChar(String gpChar) {
		this.gpChar = gpChar;
	}

	/**
	 * @param gpId
	 *            the gpId to set
	 */
	public void setGpId(Long gpId) {
		this.gpId = gpId;
	}

	/**
	 * @param gpSn
	 *            the gpSn to set
	 */
	public void setGpSn(Long gpSn) {
		this.gpSn = gpSn;
	}

	/**
	 * @param gpStatus
	 *            the gpStatus to set
	 */
	public void setGpStatus(String gpStatus) {
		this.gpStatus = gpStatus;
	}

	/**
	 * @param gpType
	 *            the gpType to set
	 */
	public void setGpType(String gpType) {
		this.gpType = gpType;
	}

	/**
	 * @param lasttimeStamp
	 *            the lasttimeStamp to set
	 */
	public void setLasttimeStamp(Date lasttimeStamp) {
		this.lasttimeStamp = lasttimeStamp;
	}

	/**
	 * @param lineId
	 *            the lineId to set
	 */
	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}

	/**
	 * @param masMeterSn
	 *            the masMeterSn to set
	 */
	public void setMasMeterSn(Long masMeterSn) {
		this.masMeterSn = masMeterSn;
	}

	/**
	 * @param meterConstant
	 *            the meterConstant to set
	 */
	public void setMeterConstant(Long meterConstant) {
		this.meterConstant = meterConstant;
	}

	/**
	 * @param mpInfo
	 *            the mpInfo to set
	 */
	public void setMpInfo(MpInfo mpInfo) {
		this.mpInfo = mpInfo;
	}

	/**
	 * @param objectId
	 *            the objectId to set
	 */
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	/**
	 * @param pluseConstant
	 *            the pluseConstant to set
	 */
	public void setPluseConstant(Long pluseConstant) {
		this.pluseConstant = pluseConstant;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @param protocolNo
	 *            the protocolNo to set
	 */
	public void setProtocolNo(String protocolNo) {
		this.protocolNo = protocolNo;
	}

	/**
	 * @param ptTimes
	 *            the ptTimes to set
	 */
	public void setPtTimes(Long ptTimes) {
		this.ptTimes = ptTimes;
	}

	/**
	 * @param sucratCptId
	 *            the sucratCptId to set
	 */
	public void setSucratCptId(String sucratCptId) {
		this.sucratCptId = sucratCptId;
	}

	/**
	 * @param terminalInfo
	 *            the terminalInfo to set
	 */
	public void setTerminalInfo(TerminalInfo terminalInfo) {
		this.terminalInfo = terminalInfo;
	}

	/**
	 * @param tranId
	 *            the tranId to set
	 */
	public void setTranId(Long tranId) {
		this.tranId = tranId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * @param psInfo
	 *            the psInfo to set
	 */
	public void setPsInfo(PsInfo psInfo) {
		this.psInfo = psInfo;
	}

	/**
	 * @return the psInfo
	 */
	public PsInfo getPsInfo() {
		return psInfo;
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
