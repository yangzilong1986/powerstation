package peis.interfaces.hicollect;

/**
 * 采集对象
 * @author Zhangyu
 * @version 1.0
 * Create Date : 20090625
 */
public class CollectObject {
    private String logicalAddr;            //终端逻辑地址
    private String equipProtocol;          //设备规约号 : @see peis.interfaces.hicollect.Constant.EQUIP_PROTOCOL_%
    private String channelType;            //通道类型
    private String pwAlgorith;             //密码算法
    private String pwContent;              //密码内容
    private int mpExpressMode;             //测量点表示方式 : @see peis.interfaces.hicollect.Constant.MP_EXPRESSMODE_%
    private int[] mpSn;                    //测量点序号组
    
    public CollectObject() {
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

    public int getMpExpressMode() {
        return mpExpressMode;
    }

    public void setMpExpressMode(int mpExpressMode) {
        this.mpExpressMode = mpExpressMode;
    }

    public int[] getMpSn() {
        return mpSn;
    }

    public void setMpSn(int[] mpSn) {
        this.mpSn = mpSn;
    }
}
