/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.db;

import java.sql.Date;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import pep.bp.model.CommanddItemDAO;
import pep.bp.model.CommandItemRowMapper;
import pep.bp.model.DataItemDAO;
import pep.bp.model.DataItemRowMapper;
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

     public List<TermTaskDAO> getPollingTask(int CircleUnit){
         try {
            //主站轮召任务
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append("select a.logical_addr,a.gp_char,a.gp_sn,a.task_id,a.protocol_no,a.sys_object,a.startup_flag,");
            sbSQL.append("a.time_interval,a.base_time_gw,a.sendup_cycle_gw,a.sendup_unit_gw,a.ext_cnt_gw,");
            sbSQL.append("b.start_time_master,b.end_time_master,b.exec_cycle_master,b.exec_unit_master");
            sbSQL.append(" from g_term_task a,g_task b");
            sbSQL.append(" where a.task_id = b.task_id");
            sbSQL.append(" and a.protocol_no = b.protocol_no");
            sbSQL.append(" and b.task_type = '2'");//主站轮召
            sbSQL.append(" and a.startup_flag = '1'");//启用
            sbSQL.append(" and b.exec_unit_master = "+CircleUnit);//周期单位
            String SQL = sbSQL.toString();
            List<TermTaskDAO> results = (List<TermTaskDAO>) jdbcTemplate.query(SQL, new TermTaskRowMapper());

            //获取任务命令项
            sbSQL.delete(0,sbSQL.length());
            for (TermTaskDAO task : results) {
                sbSQL.append("select b.protocol_no,b.commanditem_code,b.commanditem_name,b.gp_char,b.aux_info");
                sbSQL.append(" from G_TASK_DATAITEM a, G_COMMANDITEM b");
                sbSQL.append(" where a.protocol_no = b.protocol_no");
                sbSQL.append(" and a.commanditem_code = b.commanditem_code");
                sbSQL.append(" and a.task_id = ?");
                List<CommanddItemDAO> commandItemList = (List<CommanddItemDAO>) jdbcTemplate.query(SQL, new Object[]{task.getTaskId()},new CommandItemRowMapper());

                //获取任务命令项的数据项
                sbSQL.delete(0,sbSQL.length());
                for (CommanddItemDAO cmdItem : commandItemList) {
                    sbSQL.append("select a.protocol_no,a.dataitem_code,a.dataitem_format,a.dataitem_name,");
                    sbSQL.append("a.dataitem_unit,a.dataitem_value_type,a.dataitem_value_code ");
                    sbSQL.append(" from g_dataitem a,g_commanditem b");
                    sbSQL.append(" where where a.protocol_no = b.protocol_no");
                    sbSQL.append(" and and a.commanditem_code = b.commanditem_code");
                    sbSQL.append(" and a.protocol_no = ? and a.commanditem_code=?");
                    List<DataItemDAO> dataItemList = (List<DataItemDAO>) jdbcTemplate.query(SQL, new Object[]{cmdItem.getCommandItemCode(),cmdItem.getCommandItemCode()},new DataItemRowMapper());
                    cmdItem.setDataItems(dataItemList);
                }
                task.setCommandItemList(commandItemList);
            }
            return results;
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            return null;
        }
     }

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
