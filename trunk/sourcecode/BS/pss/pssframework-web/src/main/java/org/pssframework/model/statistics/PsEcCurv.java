package org.pssframework.model.statistics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        数据产生：由采集平台直接写上送数据进来；业务平台负责使用及转到历史表；
 *     
*/
public class PsEcCurv implements Serializable {
    @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6693443019817296538L;

	/** identifier field */
	private BigDecimal gpId;

    /** identifier field */
    private BigDecimal psId;

    /** identifier field */
    private String psName;

	/** identifier field */
	private Date dataTime;

	/** nullable persistent field */
	private String assetNo;

	/** nullable persistent field */
	private String orgNo;

	/** persistent field */
	private String ddate;

	/** persistent field */
	private Date acceptTime;

	/** nullable persistent field */
	private BigDecimal ctTimes;

	/** nullable persistent field */
	private BigDecimal ptTimes;

	/** nullable persistent field */
	private BigDecimal ecurA;

	/** nullable persistent field */
	private BigDecimal ecurB;

	/** nullable persistent field */
	private BigDecimal ecurC;

	/** nullable persistent field */
	private BigDecimal ecurL;

	/** nullable persistent field */
	private BigDecimal ecurS;

	/** nullable persistent field */
	private BigDecimal voltB;

	/** nullable persistent field */
	private BigDecimal voltA;

	/** nullable persistent field */
	private BigDecimal voltC;

    private int opened;

    private int locked;

    private String phase;

    private String actionType;

	/** nullable persistent field */
	private String dataFlag;

	/** nullable persistent field */
	private String dataSource;

	public String getAssetNo() {
		return this.assetNo;
	}

	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getDdate() {
		return this.ddate;
	}

	public void setDdate(String ddate) {
		this.ddate = ddate;
	}

	public Date getAcceptTime() {
		return this.acceptTime;
	}

	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}

	public BigDecimal getCtTimes() {
		return this.ctTimes;
	}

	public void setCtTimes(BigDecimal ctTimes) {
		this.ctTimes = ctTimes;
	}

	public BigDecimal getPtTimes() {
		return this.ptTimes;
	}

	public void setPtTimes(BigDecimal ptTimes) {
		this.ptTimes = ptTimes;
	}

	public BigDecimal getEcurA() {
		return this.ecurA;
	}

	public void setEcurA(BigDecimal ecurA) {
		this.ecurA = ecurA;
	}

	public BigDecimal getEcurB() {
		return this.ecurB;
	}

	public void setEcurB(BigDecimal ecurB) {
		this.ecurB = ecurB;
	}

	public BigDecimal getEcurC() {
		return this.ecurC;
	}

	public void setEcurC(BigDecimal ecurC) {
		this.ecurC = ecurC;
	}

	public BigDecimal getEcurL() {
		return this.ecurL;
	}

	public void setEcurL(BigDecimal ecurL) {
		this.ecurL = ecurL;
	}

	public BigDecimal getEcurS() {
		return this.ecurS;
	}

	public void setEcurS(BigDecimal ecurS) {
		this.ecurS = ecurS;
	}

	public BigDecimal getVoltB() {
		return this.voltB;
	}

	public void setVoltB(BigDecimal voltB) {
		this.voltB = voltB;
	}

	public BigDecimal getVoltA() {
		return this.voltA;
	}

	public void setVoltA(BigDecimal voltA) {
		this.voltA = voltA;
	}

	public BigDecimal getVoltC() {
		return this.voltC;
	}

	public void setVoltC(BigDecimal voltC) {
		this.voltC = voltC;
	}

    public int getOpened() {
        return opened;
    }

    public void setOpened(int opened) {
        this.opened = opened;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getDataFlag() {
		return this.dataFlag;
	}

	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}

	public String getDataSource() {
		return this.dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * @return the gpId
	 */
	public BigDecimal getGpId() {
		return gpId;
	}

	/**
	 * @param gpId the gpId to set
	 */
	public void setGpId(BigDecimal gpId) {
		this.gpId = gpId;
	}

    public BigDecimal getPsId() {
        return psId;
    }

    public void setPsId(BigDecimal psId) {
        this.psId = psId;
    }

    public String getPsName() {
        return psName;
    }

    public void setPsName(String psName) {
        this.psName = psName;
    }

    /**
     * @return the dataTime
     */
	public Date getDataTime() {
		return dataTime;
	}

	/**
	 * @param dataTime the dataTime to set
	 */
	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}

}
