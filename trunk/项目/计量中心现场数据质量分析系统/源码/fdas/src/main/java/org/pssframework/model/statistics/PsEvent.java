package org.pssframework.model.statistics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PsEvent implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1191218069819208203L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    private BigDecimal lbsjId;
    private BigDecimal gpId;
    private BigDecimal psId;
    private String psName;
    private String assetNo;
    private BigDecimal tgId;
    private String tgNo;
    private String tgName;
    private int eventCode;
    private String eventName;
    private Date trigTime;
    private Date receiveTime;
    private int closed;
    private int locked;
    private String phase;
    private BigDecimal currentValue;
    private int resetSent;

    public BigDecimal getLbsjId() {
        return lbsjId;
    }

    public void setLbsjId(BigDecimal lbsjId) {
        this.lbsjId = lbsjId;
    }

    public BigDecimal getGpId() {
        return gpId;
    }

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

    public String getAssetNo() {
        return assetNo;
    }

    public void setAssetNo(String assetNo) {
        this.assetNo = assetNo;
    }

    public BigDecimal getTgId() {
        return tgId;
    }

    public void setTgId(BigDecimal tgId) {
        this.tgId = tgId;
    }

    public String getTgNo() {
        return tgNo;
    }

    public void setTgNo(String tgNo) {
        this.tgNo = tgNo;
    }

    public String getTgName() {
        return tgName;
    }

    public void setTgName(String tgName) {
        this.tgName = tgName;
    }

    public int getEventCode() {
        return eventCode;
    }

    public void setEventCode(int eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getTrigTime() {
        return trigTime;
    }

    public void setTrigTime(Date trigTime) {
        this.trigTime = trigTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public int getClosed() {
        return closed;
    }

    public void setClosed(int closed) {
        this.closed = closed;
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

    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }

    public int getResetSent() {
        return resetSent;
    }

    public void setResetSent(int resetSent) {
        this.resetSent = resetSent;
    }
}
