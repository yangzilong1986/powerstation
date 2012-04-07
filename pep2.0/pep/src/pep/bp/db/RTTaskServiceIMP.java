/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.bp.db;

import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import pep.bp.model.RTTaskRecvDAO;
import pep.bp.model.RealTimeTaskDAO;
import pep.bp.model.RTTaskRecvRowMapper;
import pep.bp.model.RTTaskRowMapper;

/**
 *
 * @author Thinkpad
 */
@Transactional
public class RTTaskServiceIMP implements RTTaskService {

    private final static Logger log = LoggerFactory.getLogger(RTTaskServiceIMP.class);
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void insertTask(RealTimeTaskDAO task) {
        try {
            jdbcTemplate.update("insert into  R_REALTIME_TASK(TASK_ID,SEQUENCE_CODE,LOGICAL_ADDR,SEND_MSG,GP_MARK,COMMAND_MARK,TASK_TYPE) values(SEQ_REALTIME_TASK.nextval,?,?,?,?,?,?)",
                    new Object[]{task.getSequencecode(), task.getLogicAddress(), task.getSendmsg(),task.getGpMark(),task.getCommandMark(),task.getTask_type()});
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
        }
    }

    /**
     *插入多条任务记录
     * @param Tasks
     */
    @Override
    public void insertTasks(List<RealTimeTaskDAO> Tasks) {
        for (RealTimeTaskDAO task : Tasks) {
            try {
                jdbcTemplate.update("insert into  R_REALTIME_TASK(TASK_ID,SEQUENCE_CODE,LOGICAL_ADDR,SEND_MSG,GP_MARK,COMMAND_MARK,TASK_TYPE) values(SEQ_REALTIME_TASK.nextval,?,?,?,?,?,?)",
                        new Object[]{task.getSequencecode(), task.getLogicAddress(), task.getSendmsg(),task.getGpMark(),task.getCommandMark(),task.getTask_type()});
            } catch (DataAccessException dataAccessException) {
                log.error(dataAccessException.getMessage());
            }
        }
    }

    /**
     * 获取未处理的任务记录
     * @return
     */
    @Override
    public List<RealTimeTaskDAO> getTasks() {
        try {
            //查询未处理任务
            String SQL = "select TASK_ID,SEQUENCE_CODE,LOGICAL_ADDR,SEND_MSG,POST_TIME,TASK_STATUS,GP_MARK,COMMAND_MARK";
            SQL += " from r_realtime_task";
            SQL += " where TASK_STATUS = '0'";
            List<RealTimeTaskDAO> results = (List<RealTimeTaskDAO>) jdbcTemplate.query(SQL, new RTTaskRowMapper());
            //更新任务状态
            for (RealTimeTaskDAO task : results) {
//                SQL = "select TASK_ID,SEQUENCE_CODE,LOGICAL_ADDR,RECV_MSG,RECV_TIME";
//                SQL += " from R_REALTIME_TASK_RECV";
//                SQL += " where TASK_ID = ? AND LOGICAL_ADDR = ?";
//                List<RTTaskRecvDAO> recvs = (List<RTTaskRecvDAO>) jdbcTemplate.query(SQL, new Object[]{task.getTaskId(), task.getLogicAddress()},new RTTaskRecvRowMapper());
//                task.setRecvMsgs(recvs);
                jdbcTemplate.update("update R_REALTIME_TASK set TASK_STATUS=? where TASK_ID=?",
                        new Object[]{"1", task.getTaskId()});
            }
            return results;
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            return null;
        }
    }

    @Override
    public RealTimeTaskDAO getTask(long sequnceCode) {
        try {
            //查询未处理任务
            String SQL = "select TASK_ID,SEQUENCE_CODE,LOGICAL_ADDR,SEND_MSG,POST_TIME,TASK_STATUS,GP_MARK,COMMAND_MARK";
            SQL += " from r_realtime_task";
            SQL += " where SEQUENCE_CODE = ? ";
            List<RealTimeTaskDAO> results = (List<RealTimeTaskDAO>) jdbcTemplate.query(SQL, new Object[]{sequnceCode}, new RTTaskRowMapper());
            if (results.size() > 0) {
                RealTimeTaskDAO task = (RealTimeTaskDAO) (results.get(0));
                SQL = "select TASK_ID,SEQUENCE_CODE,LOGICAL_ADDR,RECV_MSG,RECV_TIME";
                SQL += " from R_REALTIME_TASK_RECV";
                SQL += " where TASK_ID = ? AND LOGICAL_ADDR = ?";
                List<RTTaskRecvDAO> recvs = (List<RTTaskRecvDAO>) jdbcTemplate.query(SQL, new Object[]{task.getTaskId(), task.getLogicAddress()},new RTTaskRecvRowMapper());
                task.setRecvMsgs(recvs);
                return task;
            } else {
                return null;
            }
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            return null;
        }
    }

    @Override
    public List<RealTimeTaskDAO> getTasks(long sequnceCode) {
        try {
            //查询未处理任务
            String SQL = "select TASK_ID,SEQUENCE_CODE,LOGICAL_ADDR,SEND_MSG,POST_TIME,TASK_STATUS,GP_MARK,COMMAND_MARK";
            SQL += " from r_realtime_task";
            SQL += " where SEQUENCE_CODE = ?";
            List<RealTimeTaskDAO> results = (List<RealTimeTaskDAO>) jdbcTemplate.query(SQL, new Object[]{sequnceCode}, new RTTaskRowMapper());
            //更新任务状态
            for (RealTimeTaskDAO task : results) {
                SQL = "select TASK_ID,SEQUENCE_CODE,LOGICAL_ADDR,RECV_MSG,RECV_TIME";
                SQL += " from R_REALTIME_TASK_RECV";
                SQL += " where TASK_ID = ? AND LOGICAL_ADDR = ?";
                List<RTTaskRecvDAO> recvs = (List<RTTaskRecvDAO>) jdbcTemplate.query(SQL, new Object[]{task.getTaskId(), task.getLogicAddress()},new RTTaskRecvRowMapper());
                task.setRecvMsgs(recvs);
            }

            return results;
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            return null;
        }
    }

    @Override
    public void insertRecvMsg(long taskid, String logicAddress, String recvMsg) {
        try {
            jdbcTemplate.update("insert into  R_REALTIME_TASK_RECV(TASK_ID,SEQUENCE_CODE,LOGICAL_ADDR,RECV_MSG) values(?,?,?,?)",
                    new Object[]{taskid, taskid,logicAddress, recvMsg});
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
        }
    }

    @Override
    public int getSequnce() {
        try {
            String SQL = "select SEQ_TASK_SEQUNCE.nextval from DUAL";
            int result = jdbcTemplate.queryForInt(SQL);
            return result;
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            return -1;
        }
    }

    @Override
    public List<RealTimeTaskDAO> getTripTasks(){
         try {
            //清空已经同步的试跳任务记录
            String SQL = "delete from r_realtime_task a";
            SQL += " where exists(select 1 from r_trip_plan b where a.task_id = b.task_id)";
            SQL += " and a.task_type = 2";
            jdbcTemplate.update(SQL.toString());

            //查询当天未同步试跳任务
            SQL = "select TASK_ID,SEQUENCE_CODE,LOGICAL_ADDR,SEND_MSG,POST_TIME,TASK_STATUS,GP_MARK,COMMAND_MARK";
            SQL += " from r_realtime_task a";
            SQL += " where not exists(select 1 from r_trip_plan b where a.task_id = b.task_id)";
            SQL += " and to_char(post_time,'YYYYMMDD') =to_char(sysdate,'YYYYMMDD')";
            SQL += " and a.task_type = 2";
            List<RealTimeTaskDAO> results = (List<RealTimeTaskDAO>) jdbcTemplate.query(SQL, new RTTaskRowMapper());
            for(RealTimeTaskDAO  task : results){
                SQL = "select TASK_ID,SEQUENCE_CODE,LOGICAL_ADDR,RECV_MSG,RECV_TIME";
                SQL += " from R_REALTIME_TASK_RECV";
                SQL += " where TASK_ID = ? AND LOGICAL_ADDR = ?";
                List<RTTaskRecvDAO> recvs = (List<RTTaskRecvDAO>) jdbcTemplate.query(SQL, new Object[]{task.getTaskId(), task.getLogicAddress()},new RTTaskRecvRowMapper());
                task.setRecvMsgs(recvs);
             }
            return results;
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            return null;
        }
    }

    @Override
    public boolean InsertTripTaskInfo(int ps_id,String date,Date postTime,Date acceptTime,String tripResult,int task_Id)
    {
        try {
            //清空已经同步的试跳任务记录
            String SQL = "select count(*) from R_TRIP_PLAN a";
            SQL += " where PS_ID = ?";
            SQL += " and DDATE = ?";
            int recordNum = jdbcTemplate.queryForInt(SQL, new Object[]{ps_id, date});

            if(recordNum > 0 ){
                jdbcTemplate.update("update  R_TRIP_PLAN set TRIP_RESULT = ?,POST_TIME = ?,ACCEPT_TIME = ? where PS_ID=? and DDATE=?",
                    new Object[]{tripResult,postTime,acceptTime,ps_id, date });
            }
            else{
                jdbcTemplate.update("insert into  R_TRIP_PLAN(PS_ID,DDATE,POST_TIME,ACCEPT_TIME,TRIP_RESULT,TASK_ID) values(?,?,?,?,?,?)",
                    new Object[]{ps_id, date,postTime, acceptTime,tripResult, task_Id });
            }
            return true;
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            return false;
        }
    }

}
