/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.db;
import java.util.List;
import pep.bp.model.RealTimeTaskDAO;
/**
 *
 * @author Thinkpad
 */
public interface RTTaskService {
    /**
     * 插入一条任务记录
     * @param task
     */
    public void insertTask(RealTimeTaskDAO task);

    /**
     *插入多条任务记录
     * @param Tasks
     */
    public void insertTasks(List<RealTimeTaskDAO> Tasks);

    /**
     * 获取未处理的任务记录
     * @return
     */
    public List<RealTimeTaskDAO> getTasks();

    /**
     * 
     * @param sequnceCode
     * @return
     */
    public RealTimeTaskDAO getTask(long sequnceCode);

    public List<RealTimeTaskDAO> getTasks(long sequnceCode);
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
}
