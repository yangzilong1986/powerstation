/*
 * 针对645规约表的中继通信传输对象
 */

package pep.bp.realinterface.mto;

import java.util.ArrayList;
import java.util.List;
import pep.bp.utils.SerialPortPara;
import pep.common.exception.BPException;

/**
 *
 * @author Thinkpad
 */
public class CollectObject_TransMit {
    private String terminalAddr;            //终端逻辑地址
    private String equipProtocol;           //设备规约号
    private String meterAddr;              //表地址
    private int meterType;            //表计规约
    private int funcode;                  //表计规约功能码
    private int port;                     //终端通信端口号
    private SerialPortPara serialPortPara; //透明转发通信控制字
    private int waitforPacket;//透明转发接收等待报文超时时间
    private int waitforByte;//透明转发接收等待字节超时时间
    private String transmitMsg;//透明转发内容
    private List<CommandItem> commandItems; //命令项列表

    public CollectObject_TransMit() {
        commandItems = new ArrayList<CommandItem>();
    }

    public void addCommandItem(CommandItem commandItem){
        this.commandItems.add(commandItem);
    }

    /**
     * @return the TerminalAddr
     */
    public String getTerminalAddr() {
        return terminalAddr;
    }

    /**
     * @param TerminalAddr the TerminalAddr to set
     */
    public void setTerminalAddr(String TerminalAddr) {
        this.terminalAddr = TerminalAddr;
    }

    /**
     * @return the equipProtocol
     */
    public String getEquipProtocol() {
        return equipProtocol;
    }

    /**
     * @param equipProtocol the equipProtocol to set
     */
    public void setEquipProtocol(String equipProtocol) {
        this.equipProtocol = equipProtocol;
    }

    /**
     * @return the MeterAddr
     */
    public String getMeterAddr() {
        return meterAddr;
    }

    /**
     * @param MeterAddr the MeterAddr to set
     */
    public void setMeterAddr(String MeterAddr) {
        this.meterAddr = MeterAddr;
    }

    /**
     * @return the Port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param Port the Port to set
     */
    public void setPort(byte Port) {
        this.port = Port;
    }

    /**
     * @return the serialPortPara
     */
    public SerialPortPara getSerialPortPara() {
        return serialPortPara;
    }

    /**
     * @param serialPortPara the serialPortPara to set
     */
    public void setSerialPortPara(SerialPortPara serialPortPara) {
        this.serialPortPara = serialPortPara;
    }

    /**
     * @return the waitforPacket
     */
    public int getWaitforPacket() {
        return waitforPacket;
    }
    

    /**
     * @param waitforPacket the waitforPacket to set
     */
    public void setWaitforPacket(byte waitforPacket) throws BPException {
        if((waitforPacket<0)||(waitforPacket > 127))
            throw(new BPException("等待报文返回时间超过允许范围"));
        this.waitforPacket = waitforPacket;
    }

    /**
     * @return the waitforByte
     */
    public int getWaitforByte() {
        return waitforByte;
    }

    /**
     * @param waitforByte the waitforByte to set
     */
    public void setWaitforByte(byte waitforByte) {
        this.waitforByte = waitforByte;
    }

    /**
     * @return the transmitMsg
     */
    public String getTransmitMsg() {
        return transmitMsg;
    }

    /**
     * @param transmitMsg the transmitMsg to set
     */
    public void setTransmitMsg(String transmitMsg) {
        this.transmitMsg = transmitMsg;
    }

    /**
     * @return the CommandItems
     */
    public List<CommandItem> getCommandItems() {
        return commandItems;
    }

    /**
     * @param CommandItems the CommandItems to set
     */
    public void setCommandItems(List<CommandItem> CommandItems) {
        this.commandItems = CommandItems;
    }

    /**
     * @return the meterType
     */
    public int getMeterType() {
        return meterType;
    }

    /**
     * @param meterType the meterType to set
     */
    public void setMeterType(int meterType) {
        this.meterType = meterType;
    }

    /**
     * @return the funcode
     */
    public int getFuncode() {
        return funcode;
    }

    /**
     * @param funcode the funcode to set
     */
    public void setFuncode(byte funcode) {
        this.funcode = funcode;
    }
}
