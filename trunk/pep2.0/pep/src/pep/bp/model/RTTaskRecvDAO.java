/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.model;

import java.util.Date;

/**
 *
 * @author Thinkpad
 */
public class RTTaskRecvDAO {
    private int taskId;
    private long sequenceCode;
    private String logicAddress;
    private String recvMsg;
    private Date recvTime;

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
     * @return the sequenceCode
     */
    public long getSequenceCode() {
        return sequenceCode;
    }

    /**
     * @param sequenceCode the sequenceCode to set
     */
    public void setSequenceCode(long sequenceCode) {
        this.sequenceCode = sequenceCode;
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

    /**
     * @return the recvMsg
     */
    public String getRecvMsg() {
        return recvMsg;
    }

    /**
     * @param recvMsg the recvMsg to set
     */
    public void setRecvMsg(String recvMsg) {
        this.recvMsg = recvMsg;
    }

    /**
     * @return the recvTime
     */
    public Date getRecvTime() {
        return recvTime;
    }

    /**
     * @param recvTime the recvTime to set
     */
    public void setRecvTime(Date recvTime) {
        this.recvTime = recvTime;
    }

}
