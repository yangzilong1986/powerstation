/**
* @Description: 
* @author lijun 
* @date 2011-6-30 0:17:20
*
* Expression tags is undefined on line 6, column 5 in Templates/Classes/Class.java.
*/

package pep.bp.model;

import java.sql.Date;
import java.sql.Time;



public class CommLogDAO {
    private String logicalAddress;
    private String message;
    private Time recordTime;
    private String direction;

    public CommLogDAO(String logicalAddress,String message,Time recordTime,String direction){
        super();
        this.logicalAddress = logicalAddress;
        this.message = message;
        this.recordTime = recordTime;
        this.direction = direction;
    }

    /**
     * @return the logicalAddress
     */
    public String getLogicalAddress() {
        return logicalAddress;
    }

    /**
     * @param logicalAddress the logicalAddress to set
     */
    public void setLogicalAddress(String logicalAddress) {
        this.logicalAddress = logicalAddress;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the recordTime
     */
    public Time getRecordTime() {
        return recordTime;
    }

    /**
     * @param recordTime the recordTime to set
     */
    public void setRecordTime(Time recordTime) {
        this.recordTime = recordTime;
    }

    /**
     * @return the direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

}
