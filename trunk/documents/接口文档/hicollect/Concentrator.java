package peis.interfaces.hicollect;

import java.util.List;

/**
 * 集中器对象（广东集抄）
 * @author Zhangyu
 * @version 1.0
 * Create Date : 20091012
 */
public class Concentrator {
    private String logicalAddr;            //终端逻辑地址
    private String equipProtocol;          //设备规约号 : @see peis.interfaces.hicollect.Constant.EQUIP_PROTOCOL_%
    private String channelType;            //通道类型
    private String pwAlgorith;             //密码算法
    private String pwContent;              //密码内容
    private List<ConcMeter> meterObjects;  //集中器表列表
    
    public Concentrator() {
    }

    public String getLogicalAddr() {
        return logicalAddr;
    }

    public void setLogicalAddr(String logicalAddr) {
        this.logicalAddr = logicalAddr;
    }

    public String getEquipProtocol() {
        return equipProtocol;
    }

    public void setEquipProtocol(String equipProtocol) {
        this.equipProtocol = equipProtocol;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getPwAlgorith() {
        return pwAlgorith;
    }

    public void setPwAlgorith(String pwAlgorith) {
        this.pwAlgorith = pwAlgorith;
    }

    public String getPwContent() {
        return pwContent;
    }

    public void setPwContent(String pwContent) {
        this.pwContent = pwContent;
    }

    public List<ConcMeter> getMeterObjects() {
        return meterObjects;
    }

    public void setMeterObjects(List<ConcMeter> meterObjects) {
        this.meterObjects = meterObjects;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((channelType == null) ? 0 : channelType.hashCode());
        result = prime * result + ((equipProtocol == null) ? 0 : equipProtocol.hashCode());
        result = prime * result + ((logicalAddr == null) ? 0 : logicalAddr.hashCode());
        result = prime * result + ((meterObjects == null) ? 0 : meterObjects.hashCode());
        result = prime * result + ((pwAlgorith == null) ? 0 : pwAlgorith.hashCode());
        result = prime * result + ((pwContent == null) ? 0 : pwContent.hashCode());
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
        Concentrator other = (Concentrator) obj;
        if(channelType == null) {
            if(other.channelType != null)
                return false;
        }
        else if(!channelType.equals(other.channelType))
            return false;
        if(equipProtocol == null) {
            if(other.equipProtocol != null)
                return false;
        }
        else if(!equipProtocol.equals(other.equipProtocol))
            return false;
        if(logicalAddr == null) {
            if(other.logicalAddr != null)
                return false;
        }
        else if(!logicalAddr.equals(other.logicalAddr))
            return false;
        if(meterObjects == null) {
            if(other.meterObjects != null)
                return false;
        }
        else if(!meterObjects.equals(other.meterObjects))
            return false;
        if(pwAlgorith == null) {
            if(other.pwAlgorith != null)
                return false;
        }
        else if(!pwAlgorith.equals(other.pwAlgorith))
            return false;
        if(pwContent == null) {
            if(other.pwContent != null)
                return false;
        }
        else if(!pwContent.equals(other.pwContent))
            return false;
        return true;
    }
}
