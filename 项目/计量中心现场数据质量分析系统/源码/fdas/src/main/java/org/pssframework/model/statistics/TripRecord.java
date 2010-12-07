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


    /**
     * @return the tg_name
     */
    public String getTg_name() {
        return tg_name;
    }

    /**
     * @param tgName
     *            the tg_name to set
     */
    public void setTg_name(String tgName) {
        tg_name = tgName;
    }



    /**
     * @return the gp_addr
     */
    public String getGp_addr() {
        return gp_addr;
    }

    /**
     * @param gpAddr
     *            the gp_addr to set
     */
    public void setGp_addr(String gpAddr) {
        gp_addr = gpAddr;
    }

    /**
     * @return the ddate
     */
    public String getDdate() {
        return ddate;
    }

    /**
     * @param ddate
     *            the ddate to set
     */
    public void setDdate(String ddate) {
        this.ddate = ddate;
    }

    /**
     * @return the post_time
     */
    public Date getPost_time() {
        return post_time;
    }

    /**
     * @param postTime
     *            the post_time to set
     */
    public void setPost_time(Date postTime) {
        post_time = postTime;
    }

    /**
     * @return the accept_time
     */
    public Date getAccept_time() {
        return accept_time;
    }

    /**
     * @param acceptTime
     *            the accept_time to set
     */
    public void setAccept_time(Date acceptTime) {
        accept_time = acceptTime;
    }

    /**
     * @return the trip_result
     */
    public String getTrip_result() {
        return trip_result;
    }

    /**
     * @param tripResult
     *            the trip_result to set
     */
    public void setTrip_result(String tripResult) {
        trip_result = tripResult;
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

    /**
     * @return the asset_no
     */
    public String getAsset_no() {
        return asset_no;
    }

    /**
     * @param assetNo
     *            the asset_no to set
     */
    public void setAsset_no(String assetNo) {
        asset_no = assetNo;
    }

    private String tg_name;
    private String asset_no;
    private String gp_addr;
    private String ddate;
    private Date post_time;
    private Date accept_time;
    private String trip_result;


}
