/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.db;
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
    /**
     * 更新接收报文
     * @param sequnceCode
     * @param recvMsg
     */
    public void insertRecvMsg(long sequnceCode,String recvMsg);
}
