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
public class RealTimeTask {
    private int taskId;
    private int sequenceCode;
    private String logicAddress;
    private String sendMsg;
    private List<String> recvMsgs;
    private Date postTime;
    private String taskStatus = "0"; //默认未处理

    public RealTimeTask(){
        this.recvMsgs = new ArrayList<String>();
    }

    public void setSendmsg(String sendMsg) {
        this.sendMsg = sendMsg;
    }

    public void addRecvmsg(String recvMsg) {
        this.recvMsgs.add(recvMsg);
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

    public long getTaskId() {
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


}
