package org.pssframework.model.mobile;

import java.io.Serializable;

public class LeakageProtectorParameterObject implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2265554148567542325L;

    private String rcGear;          // 剩余电流档位
    private String cbdGear;         // 漏电分断延迟档位
    private String rcGearValue;     // 剩余电流当前档位值
    private String cbdGearValue;    // 漏电分断延迟时间值
    private String rlcGearValue;    // 额定负载电流档位值

    @Override
    public String toString() {
        return "LeakageProtectorParameterObject [rcGear=" + rcGear + ", cbdGear=" + cbdGear + ", rcGearValue="
                + rcGearValue + ", cbdGearValue=" + cbdGearValue + ", rlcGearValue=" + rlcGearValue + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cbdGear == null) ? 0 : cbdGear.hashCode());
        result = prime * result + ((cbdGearValue == null) ? 0 : cbdGearValue.hashCode());
        result = prime * result + ((rcGear == null) ? 0 : rcGear.hashCode());
        result = prime * result + ((rcGearValue == null) ? 0 : rcGearValue.hashCode());
        result = prime * result + ((rlcGearValue == null) ? 0 : rlcGearValue.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        LeakageProtectorParameterObject other = (LeakageProtectorParameterObject) obj;
        if(cbdGear == null) {
            if(other.cbdGear != null)
                return false;
        }
        else if(!cbdGear.equals(other.cbdGear))
            return false;
        if(cbdGearValue == null) {
            if(other.cbdGearValue != null)
                return false;
        }
        else if(!cbdGearValue.equals(other.cbdGearValue))
            return false;
        if(rcGear == null) {
            if(other.rcGear != null)
                return false;
        }
        else if(!rcGear.equals(other.rcGear))
            return false;
        if(rcGearValue == null) {
            if(other.rcGearValue != null)
                return false;
        }
        else if(!rcGearValue.equals(other.rcGearValue))
            return false;
        if(rlcGearValue == null) {
            if(other.rlcGearValue != null)
                return false;
        }
        else if(!rlcGearValue.equals(other.rlcGearValue))
            return false;
        return true;
    }

    public String getRcGear() {
        return rcGear;
    }

    public void setRcGear(String rcGear) {
        this.rcGear = rcGear;
    }

    public String getCbdGear() {
        return cbdGear;
    }

    public void setCbdGear(String cbdGear) {
        this.cbdGear = cbdGear;
    }

    public String getRcGearValue() {
        return rcGearValue;
    }

    public void setRcGearValue(String rcGearValue) {
        this.rcGearValue = rcGearValue;
    }

    public String getCbdGearValue() {
        return cbdGearValue;
    }

    public void setCbdGearValue(String cbdGearValue) {
        this.cbdGearValue = cbdGearValue;
    }

    public String getRlcGearValue() {
        return rlcGearValue;
    }

    public void setRlcGearValue(String rlcGearValue) {
        this.rlcGearValue = rlcGearValue;
    }
}
