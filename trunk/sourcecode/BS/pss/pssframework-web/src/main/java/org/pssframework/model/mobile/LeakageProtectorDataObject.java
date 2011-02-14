package org.pssframework.model.mobile;

import java.io.Serializable;

public class LeakageProtectorDataObject implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2537491065623022237L;

    private String voltA;
    private String voltB;
    private String voltC;
    private String ecurA;
    private String ecurB;
    private String ecurC;
    private String ecurR;
    private String erWorkingValue;
    private String timeBlock;
    private String ecurRating;
    private String estatus;

    @Override
    public String toString() {
        return "LeakageProtectorDataObject [voltA=" + voltA + ", voltB=" + voltB + ", voltC=" + voltC + ", ecurA="
                + ecurA + ", ecurB=" + ecurB + ", ecurC=" + ecurC + ", ecurR=" + ecurR + ", erWorkingValue="
                + erWorkingValue + ", timeBlock=" + timeBlock + ", ecurRating=" + ecurRating + ", estatus=" + estatus
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ecurA == null) ? 0 : ecurA.hashCode());
        result = prime * result + ((ecurB == null) ? 0 : ecurB.hashCode());
        result = prime * result + ((ecurC == null) ? 0 : ecurC.hashCode());
        result = prime * result + ((ecurR == null) ? 0 : ecurR.hashCode());
        result = prime * result + ((ecurRating == null) ? 0 : ecurRating.hashCode());
        result = prime * result + ((erWorkingValue == null) ? 0 : erWorkingValue.hashCode());
        result = prime * result + ((estatus == null) ? 0 : estatus.hashCode());
        result = prime * result + ((timeBlock == null) ? 0 : timeBlock.hashCode());
        result = prime * result + ((voltA == null) ? 0 : voltA.hashCode());
        result = prime * result + ((voltB == null) ? 0 : voltB.hashCode());
        result = prime * result + ((voltC == null) ? 0 : voltC.hashCode());
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
        LeakageProtectorDataObject other = (LeakageProtectorDataObject) obj;
        if(ecurA == null) {
            if(other.ecurA != null)
                return false;
        }
        else if(!ecurA.equals(other.ecurA))
            return false;
        if(ecurB == null) {
            if(other.ecurB != null)
                return false;
        }
        else if(!ecurB.equals(other.ecurB))
            return false;
        if(ecurC == null) {
            if(other.ecurC != null)
                return false;
        }
        else if(!ecurC.equals(other.ecurC))
            return false;
        if(ecurR == null) {
            if(other.ecurR != null)
                return false;
        }
        else if(!ecurR.equals(other.ecurR))
            return false;
        if(ecurRating == null) {
            if(other.ecurRating != null)
                return false;
        }
        else if(!ecurRating.equals(other.ecurRating))
            return false;
        if(erWorkingValue == null) {
            if(other.erWorkingValue != null)
                return false;
        }
        else if(!erWorkingValue.equals(other.erWorkingValue))
            return false;
        if(estatus == null) {
            if(other.estatus != null)
                return false;
        }
        else if(!estatus.equals(other.estatus))
            return false;
        if(timeBlock == null) {
            if(other.timeBlock != null)
                return false;
        }
        else if(!timeBlock.equals(other.timeBlock))
            return false;
        if(voltA == null) {
            if(other.voltA != null)
                return false;
        }
        else if(!voltA.equals(other.voltA))
            return false;
        if(voltB == null) {
            if(other.voltB != null)
                return false;
        }
        else if(!voltB.equals(other.voltB))
            return false;
        if(voltC == null) {
            if(other.voltC != null)
                return false;
        }
        else if(!voltC.equals(other.voltC))
            return false;
        return true;
    }

    public String getVoltA() {
        return voltA;
    }

    public void setVoltA(String voltA) {
        this.voltA = voltA;
    }

    public String getVoltB() {
        return voltB;
    }

    public void setVoltB(String voltB) {
        this.voltB = voltB;
    }

    public String getVoltC() {
        return voltC;
    }

    public void setVoltC(String voltC) {
        this.voltC = voltC;
    }

    public String getEcurA() {
        return ecurA;
    }

    public void setEcurA(String ecurA) {
        this.ecurA = ecurA;
    }

    public String getEcurB() {
        return ecurB;
    }

    public void setEcurB(String ecurB) {
        this.ecurB = ecurB;
    }

    public String getEcurC() {
        return ecurC;
    }

    public void setEcurC(String ecurC) {
        this.ecurC = ecurC;
    }

    public String getEcurR() {
        return ecurR;
    }

    public void setEcurR(String ecurR) {
        this.ecurR = ecurR;
    }

    public String getErWorkingValue() {
        return erWorkingValue;
    }

    public void setErWorkingValue(String erWorkingValue) {
        this.erWorkingValue = erWorkingValue;
    }

    public String getTimeBlock() {
        return timeBlock;
    }

    public void setTimeBlock(String timeBlock) {
        this.timeBlock = timeBlock;
    }

    public String getEcurRating() {
        return ecurRating;
    }

    public void setEcurRating(String ecurRating) {
        this.ecurRating = ecurRating;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
}
