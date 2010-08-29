package org.pssframework.query.psmanage;

import org.pssframework.base.BaseQuery;

public class PSMonitorQuery extends BaseQuery {
    /**
     * 
     */
    private static final long serialVersionUID = 2718759317255416470L;

    private Long psId;

    private String ddate;

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
}
