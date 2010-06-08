/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.bp.db;

import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import pep.bp.model.RealTimeTask;
import pep.bp.model.TaskRowMapper;

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

    public void insertTask(RealTimeTask task) {
        try {
            jdbcTemplate.update("insert into  R_REALTIME_TASK(TASK_ID,SEQUENCE_CODE,SEND_MSG) values(SEQ_REALTIME_TASK.nextval,?,?)",
                    new Object[]{task.getSequencecode(), task.getSendmsg()});
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
        }
    }

    /**
     *插入多条任务记录
     * @param Tasks
     */
    public void insertTasks(List<RealTimeTask> Tasks) {
        for (RealTimeTask task : Tasks) {
            try {
                jdbcTemplate.update("insert into  R_REALTIME_TASK(TASK_ID,SEQUENCE_CODE,SEND_MSG) values(SEQ_REALTIME_TASK.nextval,?,?)",
                        new Object[]{task.getSequencecode(), task.getSendmsg()});
            } catch (DataAccessException dataAccessException) {
                log.error(dataAccessException.getMessage());
            }
        }
    }

    /**
     * 获取未处理的任务记录
     * @return
     */
    public List<RealTimeTask> getTasks() {
        try {
            //查询未处理任务
            String SQL = "select TASK_ID,SEQUENCE_CODE,SEND_MSG,POST_TIME,TASK_STATUS";
            SQL += " from r_realtime_task";
            SQL += " where TASK_STATUS = '0'";
            List<RealTimeTask> results = (List<RealTimeTask>) jdbcTemplate.query(SQL, new TaskRowMapper());
            //更新任务状态
            for (RealTimeTask task : results) {
                jdbcTemplate.update("update R_REALTIME_TASK set TASK_STATUS=? where TASK_ID=?",
                        new Object[]{"1", task.getTaskId()});
            }
            return results;
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            return null;
        }
    }

    public RealTimeTask getTask(long sequnceCode) {
        try {
            //查询未处理任务
            String SQL = "select TASK_ID,SEQUENCE_CODE,SEND_MSG,POST_TIME,TASK_STATUS";
            SQL += " from r_realtime_task";
            SQL += " where SEQUENCE_CODE = ?";
            List<RealTimeTask> results = (List<RealTimeTask>) jdbcTemplate.query(SQL, new Object[]{sequnceCode}, new TaskRowMapper());
            if (results.size() > 0) {
                RealTimeTask task = (RealTimeTask)(results.get(0));
                //更新任务状态
                jdbcTemplate.update("update R_REALTIME_TASK set TASK_STATUS=? where TASK_ID=?",
                        new Object[]{"1", task.getTaskId()});

                return task;
            }
            else
                return  null;
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            return null;
        }
    }

    public void insertRecvMsg(long sequnceCode, String recvMsg) {
        try {
            jdbcTemplate.update("insert into  R_REALTIME_TASK_RECV(SEQUENCE_CODE,RECV_MSG) values(?,?)",
                    new Object[]{sequnceCode, recvMsg});
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
        }
    }
    
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


}
