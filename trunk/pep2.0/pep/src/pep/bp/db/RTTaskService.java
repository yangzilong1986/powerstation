/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.db;
import java.util.Date;
import java.util.List;
import pep.bp.model.RealTimeTask;
/**
 *
 * @author Thinkpad
 */
public interface RTTaskService {
    /**
     * 插入一条任务记录
     * @param task
     */
    public void insertTask(RealTimeTask task);

    /**
     *插入多条任务记录
     * @param Tasks
     */
    public void insertTasks(List<RealTimeTask> Tasks);

    /**
     * 获取未处理的任务记录
     * @return
     */
    public List<RealTimeTask> getTasks();

    /**
     * 
     * @param sequnceCode
     * @return
     */
    public RealTimeTask getTask(long sequnceCode);

    public List<RealTimeTask> getTasks(long sequnceCode);
    /**
     * 更新接收报文
     * @param sequnceCode
     * @param recvMsg
     */
    public void insertRecvMsg(long taskid,String logicAddress ,String recvMsg);


    /**
     * 获取回执码
     * @return
     */
    public int getSequnce();

    /**
     * 获取未同步的试跳任务记录
     * @return
     */
    public List<RealTimeTask> getTripTasks();

    /**
     * 同步的试跳任务记录
     * @return
     */
    public boolean InsertTripTaskInfo(int ps_id,String date,Date postTime,Date acceptTime,String tripResult,int task_Id);
}
