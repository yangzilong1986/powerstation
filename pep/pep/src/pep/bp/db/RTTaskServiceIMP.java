/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.db;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import pep.bp.model.RealTimeTask;
/**
 *
 * @author Thinkpad
 */
public class RTTaskServiceIMP implements RTTaskService {
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void save(RealTimeTask task){
        /*String SQL = "insert into  R_REALTIME_TASK values(SEQ_REALTIME_TASK.nextval,?,?);";
        jdbcTemplate.update(SQL, new Object[]{task.getSequence_code(),task.getMSN()},new int[]{java.sql.Types.INTEGER,java.sql.Types.VARCHAR});*/

        jdbcTemplate.update("insert into  R_REALTIME_TASK(TASK_ID,SEQUENCE_CODE,MSN) values(1,?,?)",
                            new Object[]{task.getSequencecode(),task.getSendmsg()});
    }
}
