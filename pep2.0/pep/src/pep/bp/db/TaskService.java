/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.db;


import java.sql.Date;
import java.util.List;
import pep.bp.model.TermTask;

/**
 *
 * @author Thinkpad
 */
public interface TaskService {
    public List<TermTask> getPollingTask(int CircleUnit);
    public void updateTask(int TaskId,String ProtocolNo,String Sys_Object,Date StartTime,Date EndTime,int PollingNum);

}