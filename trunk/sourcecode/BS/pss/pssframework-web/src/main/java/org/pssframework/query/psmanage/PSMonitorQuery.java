package org.pssframework.query.psmanage;

import org.pssframework.base.BaseQuery;

public class PSMonitorQuery extends BaseQuery {
    /**
     * 
     */
    private static final long serialVersionUID = 2718759317255416470L;

    private Long psId;

    private String ddate;

    private String sdate;

    private String edate;

    public Long getPsId() {
        return psId;
    }

    public void setPsId(Long psId) {
        this.psId = psId;
    }

    public String getDdate() {
        return ddate;
    }

    public void setDdate(String ddate) {
        this.ddate = ddate;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }
}
