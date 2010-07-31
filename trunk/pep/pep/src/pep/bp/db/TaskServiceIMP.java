/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.db;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import pep.bp.model.CommanddItemDAO;
import pep.bp.model.CommandItemRowMapper;
import pep.bp.model.TermTaskDAO;
import pep.bp.model.TermTaskRowMapper;

/**
 *
 * @author Thinkpad
 */
@Transactional
public class TaskServiceIMP implements  TaskService{
    private final static Logger log = LoggerFactory.getLogger(TaskServiceIMP.class);
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
     public List<TermTaskDAO> getPollingTask(int CircleUnit){
         try {
            //主站轮召任务
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append("select a.logical_addr,a.gp_char,a.gp_sn,a.task_id,a.protocol_no,a.sys_object,a.startup_flag,");
            sbSQL.append("a.time_interval,a.base_time_gw,a.sendup_cycle_gw,a.sendup_unit_gw,a.ext_cnt_gw,");
            sbSQL.append("b.start_time_master,b.end_time_master,b.exec_cycle_master,b.exec_unit_master,b.AFN");
            sbSQL.append(" from r_term_task a,r_task b");
            sbSQL.append(" where a.task_id = b.task_id");
            sbSQL.append(" and a.protocol_no = b.protocol_no");
            sbSQL.append(" and b.task_type = '2'");//主站轮召
            sbSQL.append(" and a.startup_flag = '1'");//启用
            sbSQL.append(" and b.exec_unit_master = "+CircleUnit);//周期单位
            String SQL = sbSQL.toString();
          //  List rs = jdbcTemplate.queryForList(SQL);
            List<TermTaskDAO> results = (List<TermTaskDAO>) jdbcTemplate.query(SQL, new TermTaskRowMapper());

            //获取任务命令项
            sbSQL.delete(0,sbSQL.length());
            for (TermTaskDAO task : results) {
                sbSQL.append("select PROTOCOL_NO,COMMANDITEM_CODE");
                sbSQL.append(" from R_TASK_DATAITEM");
                sbSQL.append(" where protocol_no = ?");
                sbSQL.append(" and SYS_OBJECT = ?");
                sbSQL.append(" and task_id = ?");
                List<CommanddItemDAO> commandItemList = (List<CommanddItemDAO>) jdbcTemplate.query(sbSQL.toString(), new Object[]{task.getProtocol_No(),task.getSystem_Object(),task.getTaskId()},new CommandItemRowMapper());
                task.setCommandItemList(commandItemList);
            }
            return results;
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            return null;
        }
     }

    @Override
     public void updateTask(int TaskId,String ProtocolNo,String Sys_Object,Date StartTime,Date EndTime,int PollingNum){
         try {
             StringBuffer sbSQL = new StringBuffer();
             sbSQL.append("update g_task set START_TIME_MASTER = ?,END_TIME_MASTER=?,EXECONCE_TIMES=?");
             sbSQL.append(" where TASK_ID = ? and PROTOCOL_NO=? and SYS_OBJECT = ?");
            jdbcTemplate.update(sbSQL.toString(),
                    new Object[]{StartTime, EndTime, PollingNum,TaskId,ProtocolNo,Sys_Object});
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
        }
     }
}
