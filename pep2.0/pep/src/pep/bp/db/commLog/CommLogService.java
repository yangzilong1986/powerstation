/**
 * @Description:
 * @author lijun
 * @date 2011-6-30 0:20:52
 *
 * Expression tags is undefined on line 6, column 5 in Templates/Classes/Class.java.
 */
package pep.bp.db.commLog;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import pep.bp.db.DataServiceIMP;
import pep.bp.model.CommLogDAO;

public class CommLogService  {

    private final static Logger log = LoggerFactory.getLogger(DataServiceIMP.class);
    private JdbcTemplate jdbcTemplate;
    


    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public DataSource getDataSource() {
        return this.jdbcTemplate.getDataSource();
    }

    public synchronized void insertLogs(final List<CommLogDAO> commLogList) {

            String sql = "INSERT INTO R_COMM_LOG(logical_addr,Message,record_time,direction) VALUES(?,?,?,?)";
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    CommLogDAO commLog = commLogList.get(i);
                    ps.setString(1, commLog.getLogicalAddress());
                    ps.setString(2, commLog.getMessage());
                    ps.setDate(3, (Date) commLog.getRecordTime());
                    ps.setString(4, commLog.getDirection());
                }

                @Override
                public int getBatchSize() {
                    return commLogList.size();
                }
            });
            log.info("本次插入通信日志"+commLogList.size()+"条");

    }

}
