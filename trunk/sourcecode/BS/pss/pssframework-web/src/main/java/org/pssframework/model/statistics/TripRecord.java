package org.pssframework.model.statistics;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class TripRecord implements Serializable {
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

    public String getLogical_addr() {
        return logical_addr;
    }

    public void setLogical_addr(String logicalAddr) {
        logical_addr = logicalAddr;
    }

    public String getAssetNo() {
        return assetNo;
    }

    public void setAssetNo(String assetNo) {
        this.assetNo = assetNo;
    }

    public String getDdate() {
        return ddate;
    }

    public void setDdate(String ddate) {
        this.ddate = ddate;
    }

    public Date getPost_Time() {
        return Post_Time;
    }

    public void setPost_Time(Date postTime) {
        Post_Time = postTime;
    }

    public Date getAccept_Time() {
        return Accept_Time;
    }

    public void setAccept_Time(Date acceptTime) {
        Accept_Time = acceptTime;
    }

    public String getTripResult() {
        return TripResult;
    }

    public void setTripResult(String tripResult) {
        TripResult = tripResult;
    }

    private String logical_addr;
    private String assetNo;
    private String ddate;
    private Date Post_Time;
    private Date Accept_Time;
    private String TripResult;


}
