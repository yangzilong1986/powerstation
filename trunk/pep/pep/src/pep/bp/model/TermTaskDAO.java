/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.model;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author Thinkpad
 */
public class TermTaskDAO {
    private String logicAddress;
    private String gp_char;
    private int gp_sn;
    private int taskId;
    private String protocol_No;
    private String system_Object;
    private String startup_Flag;
    private int time_Interval;
    private Date baseTime_gw;
    private int sendup_Circle_gw;
    private int sendup_Unit_gw;
    private int ext_cnt_gw;
    private Date start_time_master;
    private Date end_time_master;
    private int exec_circle_master;
    private int exec_unit_master;
    private byte AFN;
    private List<CommanddItemDAO> commandItemList;

    /**
     * @return the logicAddress
     */
    public String getLogicAddress() {
        return logicAddress;
    }

    /**
     * @param logicAddress the logicAddress to set
     */
    public void setLogicAddress(String logicAddress) {
        this.logicAddress = logicAddress;
    }

    /**
     * @return the gp_char
     */
    public String getGp_char() {
        return gp_char;
    }

    /**
     * @param gp_char the gp_char to set
     */
    public void setGp_char(String gp_char) {
        this.gp_char = gp_char;
    }

    /**
     * @return the gp_sn
     */
    public int getGp_sn() {
        return gp_sn;
    }

    /**
     * @param gp_sn the gp_sn to set
     */
    public void setGp_sn(int gp_sn) {
        this.gp_sn = gp_sn;
    }

    /**
     * @return the taskId
     */
    public int getTaskId() {
        return taskId;
    }

    /**
     * @param taskId the taskId to set
     */
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    /**
     * @return the protocol_No
     */
    public String getProtocol_No() {
        return protocol_No;
    }

    /**
     * @param protocol_No the protocol_No to set
     */
    public void setProtocol_No(String protocol_No) {
        this.protocol_No = protocol_No;
    }

    /**
     * @return the system_Object
     */
    public String getSystem_Object() {
        return system_Object;
    }

    /**
     * @param system_Object the system_Object to set
     */
    public void setSystem_Object(String system_Object) {
        this.system_Object = system_Object;
    }

    /**
     * @return the startup_Flag
     */
    public String getStartup_Flag() {
        return startup_Flag;
    }

    /**
     * @param startup_Flag the startup_Flag to set
     */
    public void setStartup_Flag(String startup_Flag) {
        this.startup_Flag = startup_Flag;
    }

    /**
     * @return the time_Interval
     */
    public int getTime_Interval() {
        return time_Interval;
    }

    /**
     * @param time_Interval the time_Interval to set
     */
    public void setTime_Interval(int time_Interval) {
        this.time_Interval = time_Interval;
    }

    /**
     * @return the baseTime_gw
     */
    public Date getBaseTime_gw() {
        return baseTime_gw;
    }

    /**
     * @param baseTime_gw the baseTime_gw to set
     */
    public void setBaseTime_gw(Date baseTime_gw) {
        this.baseTime_gw = baseTime_gw;
    }

    /**
     * @return the sendup_Circle_gw
     */
    public int getSendup_Circle_gw() {
        return sendup_Circle_gw;
    }

    /**
     * @param sendup_Circle_gw the sendup_Circle_gw to set
     */
    public void setSendup_Circle_gw(int sendup_Circle_gw) {
        this.sendup_Circle_gw = sendup_Circle_gw;
    }

    /**
     * @return the sendup_Unit_gw
     */
    public int getSendup_Unit_gw() {
        return sendup_Unit_gw;
    }

    /**
     * @param sendup_Unit_gw the sendup_Unit_gw to set
     */
    public void setSendup_Unit_gw(int sendup_Unit_gw) {
        this.sendup_Unit_gw = sendup_Unit_gw;
    }

    /**
     * @return the ext_cnt_gw
     */
    public int getExt_cnt_gw() {
        return ext_cnt_gw;
    }

    /**
     * @param ext_cnt_gw the ext_cnt_gw to set
     */
    public void setExt_cnt_gw(int ext_cnt_gw) {
        this.ext_cnt_gw = ext_cnt_gw;
    }

    /**
     * @return the commandItemList
     */
    public List<CommanddItemDAO> getCommandItemList() {
        return commandItemList;
    }

    /**
     * @param commandItemList the commandItemList to set
     */
    public void setCommandItemList(List<CommanddItemDAO> commandItemList) {
        this.setCommandItemList(commandItemList);
    }

    /**
     * @return the start_time_master
     */
    public Date getStart_time_master() {
        return start_time_master;
    }

    /**
     * @param start_time_master the start_time_master to set
     */
    public void setStart_time_master(Date start_time_master) {
        this.start_time_master = start_time_master;
    }

    /**
     * @return the end_time_master
     */
    public Date getEnd_time_master() {
        return end_time_master;
    }

    /**
     * @param end_time_master the end_time_master to set
     */
    public void setEnd_time_master(Date end_time_master) {
        this.end_time_master = end_time_master;
    }

    /**
     * @return the exec_circle_master
     */
    public int getExec_circle_master() {
        return exec_circle_master;
    }

    /**
     * @param exec_circle_master the exec_circle_master to set
     */
    public void setExec_circle_master(int exec_circle_master) {
        this.exec_circle_master = exec_circle_master;
    }

    /**
     * @return the exec_unit_master
     */
    public int getExec_unit_master() {
        return exec_unit_master;
    }

    /**
     * @param exec_unit_master the exec_unit_master to set
     */
    public void setExec_unit_master(int exec_unit_master) {
        this.exec_unit_master = exec_unit_master;
    }

    /**
     * @return the AFN
     */
    public byte getAFN() {
        return AFN;
    }

    /**
     * @param AFN the AFN to set
     */
    public void setAFN(byte AFN) {
        this.AFN = AFN;
    }


}
