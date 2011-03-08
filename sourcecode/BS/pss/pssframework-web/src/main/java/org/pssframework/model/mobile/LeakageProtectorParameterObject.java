package org.pssframework.model.mobile;

import java.io.Serializable;

public class LeakageProtectorParameterObject implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2265554148567542325L;

    private String tsStatus;        // 8000C04F01 分合状态
    private String isAtresia;       // 8000C04F02 是否闭锁
    private String phase;           // 8000C04F03 相位
    private String actionType;      // 8000C04F04 动作类型
    private String rlcGearValue;    // 8000C04F05 额定负载电流档位值
    private String rcGear;          // 8000C04F06 剩余电流档位
    private String rcGearValue;     // 8000C04F07 剩余电流当前档位值
    private String cbdGear;         // 8000C04F08 漏电分断延迟档位
    private String cbdGearValue;    // 8000C04F09 漏电分断延迟时间值
    private String funcSetupBytes;  // 8000C04F10 开关功能设定字
    private String funcSetupByte1;  // 8000C04F10 开关功能设定字 - 欠压保护功能
    private String funcSetupByte2;  // 8000C04F10 开关功能设定字 - 过压保护功能
    private String funcSetupByte3;  // 8000C04F10 开关功能设定字 - 突变保护功能
    private String funcSetupByte4;  // 8000C04F10 开关功能设定字 - 缓变保护功能
    private String funcSetupByte5;  // 8000C04F10 开关功能设定字 - 特波保护功能
    private String funcSetupByte6;  // 8000C04F10 开关功能设定字 - 自动跟踪功能
    private String funcSetupByte7;  // 8000C04F10 开关功能设定字 - 告警功能
    private String funcSetupByte8;  // 8000C04F10 开关功能设定字 - 特波动作值
    private String lpModelId;       // 8000C04F11 保护器型号ID

    @Override
    public String toString() {
        return "LeakageProtectorParameterObject [tsStatus=" + tsStatus + ", isAtresia=" + isAtresia + ", phase="
                + phase + ", actionType=" + actionType + ", rlcGearValue=" + rlcGearValue + ", rcGear=" + rcGear
                + ", rcGearValue=" + rcGearValue + ", cbdGear=" + cbdGear + ", cbdGearValue=" + cbdGearValue
                + ", funcSetupBytes=" + funcSetupBytes + ", funcSetupByte1=" + funcSetupByte1 + ", funcSetupByte2="
                + funcSetupByte2 + ", funcSetupByte3=" + funcSetupByte3 + ", funcSetupByte4=" + funcSetupByte4
                + ", funcSetupByte5=" + funcSetupByte5 + ", funcSetupByte6=" + funcSetupByte6 + ", funcSetupByte7="
                + funcSetupByte7 + ", funcSetupByte8=" + funcSetupByte8 + ", lpModelId=" + lpModelId + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((actionType == null) ? 0 : actionType.hashCode());
        result = prime * result + ((cbdGear == null) ? 0 : cbdGear.hashCode());
        result = prime * result + ((cbdGearValue == null) ? 0 : cbdGearValue.hashCode());
        result = prime * result + ((funcSetupByte1 == null) ? 0 : funcSetupByte1.hashCode());
        result = prime * result + ((funcSetupByte2 == null) ? 0 : funcSetupByte2.hashCode());
        result = prime * result + ((funcSetupByte3 == null) ? 0 : funcSetupByte3.hashCode());
        result = prime * result + ((funcSetupByte4 == null) ? 0 : funcSetupByte4.hashCode());
        result = prime * result + ((funcSetupByte5 == null) ? 0 : funcSetupByte5.hashCode());
        result = prime * result + ((funcSetupByte6 == null) ? 0 : funcSetupByte6.hashCode());
        result = prime * result + ((funcSetupByte7 == null) ? 0 : funcSetupByte7.hashCode());
        result = prime * result + ((funcSetupByte8 == null) ? 0 : funcSetupByte8.hashCode());
        result = prime * result + ((funcSetupBytes == null) ? 0 : funcSetupBytes.hashCode());
        result = prime * result + ((isAtresia == null) ? 0 : isAtresia.hashCode());
        result = prime * result + ((lpModelId == null) ? 0 : lpModelId.hashCode());
        result = prime * result + ((phase == null) ? 0 : phase.hashCode());
        result = prime * result + ((rcGear == null) ? 0 : rcGear.hashCode());
        result = prime * result + ((rcGearValue == null) ? 0 : rcGearValue.hashCode());
        result = prime * result + ((rlcGearValue == null) ? 0 : rlcGearValue.hashCode());
        result = prime * result + ((tsStatus == null) ? 0 : tsStatus.hashCode());
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
        if(actionType == null) {
            if(other.actionType != null)
                return false;
        }
        else if(!actionType.equals(other.actionType))
            return false;
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
        if(funcSetupByte1 == null) {
            if(other.funcSetupByte1 != null)
                return false;
        }
        else if(!funcSetupByte1.equals(other.funcSetupByte1))
            return false;
        if(funcSetupByte2 == null) {
            if(other.funcSetupByte2 != null)
                return false;
        }
        else if(!funcSetupByte2.equals(other.funcSetupByte2))
            return false;
        if(funcSetupByte3 == null) {
            if(other.funcSetupByte3 != null)
                return false;
        }
        else if(!funcSetupByte3.equals(other.funcSetupByte3))
            return false;
        if(funcSetupByte4 == null) {
            if(other.funcSetupByte4 != null)
                return false;
        }
        else if(!funcSetupByte4.equals(other.funcSetupByte4))
            return false;
        if(funcSetupByte5 == null) {
            if(other.funcSetupByte5 != null)
                return false;
        }
        else if(!funcSetupByte5.equals(other.funcSetupByte5))
            return false;
        if(funcSetupByte6 == null) {
            if(other.funcSetupByte6 != null)
                return false;
        }
        else if(!funcSetupByte6.equals(other.funcSetupByte6))
            return false;
        if(funcSetupByte7 == null) {
            if(other.funcSetupByte7 != null)
                return false;
        }
        else if(!funcSetupByte7.equals(other.funcSetupByte7))
            return false;
        if(funcSetupByte8 == null) {
            if(other.funcSetupByte8 != null)
                return false;
        }
        else if(!funcSetupByte8.equals(other.funcSetupByte8))
            return false;
        if(funcSetupBytes == null) {
            if(other.funcSetupBytes != null)
                return false;
        }
        else if(!funcSetupBytes.equals(other.funcSetupBytes))
            return false;
        if(isAtresia == null) {
            if(other.isAtresia != null)
                return false;
        }
        else if(!isAtresia.equals(other.isAtresia))
            return false;
        if(lpModelId == null) {
            if(other.lpModelId != null)
                return false;
        }
        else if(!lpModelId.equals(other.lpModelId))
            return false;
        if(phase == null) {
            if(other.phase != null)
                return false;
        }
        else if(!phase.equals(other.phase))
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
        if(tsStatus == null) {
            if(other.tsStatus != null)
                return false;
        }
        else if(!tsStatus.equals(other.tsStatus))
            return false;
        return true;
    }

    public String getTsStatus() {
        return tsStatus;
    }

    public void setTsStatus(String tsStatus) {
        this.tsStatus = tsStatus;
    }

    public String getIsAtresia() {
        return isAtresia;
    }

    public void setIsAtresia(String isAtresia) {
        this.isAtresia = isAtresia;
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

    public String getRlcGearValue() {
        return rlcGearValue;
    }

    public void setRlcGearValue(String rlcGearValue) {
        this.rlcGearValue = rlcGearValue;
    }

    public String getRcGear() {
        return rcGear;
    }

    public void setRcGear(String rcGear) {
        this.rcGear = rcGear;
    }

    public String getRcGearValue() {
        return rcGearValue;
    }

    public void setRcGearValue(String rcGearValue) {
        this.rcGearValue = rcGearValue;
    }

    public String getCbdGear() {
        return cbdGear;
    }

    public void setCbdGear(String cbdGear) {
        this.cbdGear = cbdGear;
    }

    public String getCbdGearValue() {
        return cbdGearValue;
    }

    public void setCbdGearValue(String cbdGearValue) {
        this.cbdGearValue = cbdGearValue;
    }

    public String getFuncSetupBytes() {
        return funcSetupBytes;
    }

    public void setFuncSetupBytes(String funcSetupBytes) {
        this.funcSetupBytes = funcSetupBytes;
    }

    public String getFuncSetupByte1() {
        return funcSetupByte1;
    }

    public void setFuncSetupByte1(String funcSetupByte1) {
        this.funcSetupByte1 = funcSetupByte1;
    }

    public String getFuncSetupByte2() {
        return funcSetupByte2;
    }

    public void setFuncSetupByte2(String funcSetupByte2) {
        this.funcSetupByte2 = funcSetupByte2;
    }

    public String getFuncSetupByte3() {
        return funcSetupByte3;
    }

    public void setFuncSetupByte3(String funcSetupByte3) {
        this.funcSetupByte3 = funcSetupByte3;
    }

    public String getFuncSetupByte4() {
        return funcSetupByte4;
    }

    public void setFuncSetupByte4(String funcSetupByte4) {
        this.funcSetupByte4 = funcSetupByte4;
    }

    public String getFuncSetupByte5() {
        return funcSetupByte5;
    }

    public void setFuncSetupByte5(String funcSetupByte5) {
        this.funcSetupByte5 = funcSetupByte5;
    }

    public String getFuncSetupByte6() {
        return funcSetupByte6;
    }

    public void setFuncSetupByte6(String funcSetupByte6) {
        this.funcSetupByte6 = funcSetupByte6;
    }

    public String getFuncSetupByte7() {
        return funcSetupByte7;
    }

    public void setFuncSetupByte7(String funcSetupByte7) {
        this.funcSetupByte7 = funcSetupByte7;
    }

    public String getFuncSetupByte8() {
        return funcSetupByte8;
    }

    public void setFuncSetupByte8(String funcSetupByte8) {
        this.funcSetupByte8 = funcSetupByte8;
    }

    public String getLpModelId() {
        return lpModelId;
    }

    public void setLpModelId(String lpModelId) {
        this.lpModelId = lpModelId;
    }
}
