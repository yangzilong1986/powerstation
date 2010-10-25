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

    public String getlogical_addr() {
        return logical_addr;
    }

    public void setlogical_addr(String logicalAddr) {
        logical_addr = logicalAddr;
    }

    public String getassetNo() {
        return assetNo;
    }

    public void setassetNo(String assetNo) {
        this.assetNo = assetNo;
    }

    public String getddate() {
        return ddate;
    }

    public void setddate(String ddate) {
        this.ddate = ddate;
    }

    public Date getpost_time() {
        return post_time;
    }

    public void setpost_time(Date postTime) {
        post_time = postTime;
    }

    public Date getaccept_time() {
        return accept_time;
    }

    public void setaccept_time(Date acceptTime) {
        accept_time = acceptTime;
    }

    public String gettrip_result() {
        return trip_result;
    }

    public void settrip_result(String tripResult) {
        trip_result = tripResult;
    }

    private String logical_addr;
    private String assetNo;
    private String ddate;
    private Date post_time;
    private Date accept_time;
    private String trip_result;


}
