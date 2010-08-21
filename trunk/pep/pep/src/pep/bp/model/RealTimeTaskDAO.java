/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 *
 * @author Thinkpad
 */
public class RealTimeTaskDAO {
    private int taskId;
    private int sequenceCode;
    private String logicAddress;
    private String sendMsg;
    private List<RTTaskRecvDAO> recvMsgs;
    private Date postTime;
    private String taskStatus = "0"; //默认未处理
    private String gpMark;//测量点标志：1#2#3#
    private String commandMark;//命令项标志：10040009#10040010
    private String task_type;

    public RealTimeTaskDAO(){
        this.recvMsgs = new ArrayList<RTTaskRecvDAO>();
    }

    public void setSendmsg(String sendMsg) {
        this.sendMsg = sendMsg;
    }

    public void addRecvmsg(RTTaskRecvDAO recvMsg) {
        this.getRecvMsgs().add(recvMsg);
    }

    public void setPosttime(Date postTime) {
        this.postTime = postTime;
    }

    public void setSequencecode(int sequenceCode) {
        this.sequenceCode = sequenceCode;
    }

    public void setStatestatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getSendmsg() {
        return this.sendMsg;
    }


    public Date getPosttime() {
        return postTime;
    }

    public int getSequencecode() {
        return sequenceCode;
    }

    public String getTaskstatus() {
        return taskStatus;
    }

    public int getTaskId() {
        return taskId;
    }

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

    public void setRecvMsgs(List<RTTaskRecvDAO> recvs){
        this.recvMsgs = recvs;
    }

    /**
     * @return the recvMsgs
     */
    public List<RTTaskRecvDAO> getRecvMsgs() {
        return recvMsgs;
    }

    /**
     * @return the gpMark
     */
    public String getGpMark() {
        return gpMark;
    }

    /**
     * @param gpMark the gpMark to set
     */
    public void setGpMark(String gpMark) {
        this.gpMark = gpMark;
    }

    /**
     * @return the commandMark
     */
    public String getCommandMark() {
        return commandMark;
    }

    /**
     * @param commandMark the commandMark to set
     */
    public void setCommandMark(String commandMark) {
        this.commandMark = commandMark;
    }

    /**
     * @return the task_type
     */
    public String getTask_type() {
        return task_type;
    }

    /**
     * @param task_type the task_type to set
     */
    public void setTask_type(String task_type) {
        this.task_type = task_type;
    }


}
