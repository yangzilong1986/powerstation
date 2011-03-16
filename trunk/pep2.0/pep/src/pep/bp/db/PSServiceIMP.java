/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.db;

import java.util.List;
import javax.sql.DataSource;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import pep.bp.model.PSDAO;
import pep.bp.model.PSRowMapper;


/**
 *
 * @author Thinkpad
 */
@Transactional
public class PSServiceIMP implements  PSService{
    private final static Logger log = LoggerFactory.getLogger(PSServiceIMP.class);
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
     public List<PSDAO> getTestPSList(String testDay,String testHour){
         try {
            //主站轮召任务
            StringBuilder sbSQL = new StringBuilder();
            sbSQL.append("select logical_addr,gp_sn,gp_addr,test_day,test_time");
            sbSQL.append(" from V_PS");
            sbSQL.append(" where test_day = ?");
            sbSQL.append(" and test_time = ?");
            sbSQL.append(" and auto_test = 1");

            String SQL = sbSQL.toString();
          //  List rs = jdbcTemplate.queryForList(SQL);
            List<PSDAO> results = (List<PSDAO>) jdbcTemplate.query(SQL,new Object[]{testDay,testHour}, new PSRowMapper());

            return results;
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            return null;
        }
     }

    @Override
    public int getPsId(String LogicAddr, int GP_SN){
        try {
            //主站轮召任务
            StringBuilder sbSQL = new StringBuilder();
            sbSQL.append("select a.ps_id");
            sbSQL.append(" from c_ps a,c_gp b,c_terminal c");
            sbSQL.append(" where a.term_id = c.term_id");
            sbSQL.append(" and a.gp_id = b.gp_id");
            sbSQL.append(" and b.gp_sn = ?");
            sbSQL.append(" and c.logical_addr = ?");

            String SQL = sbSQL.toString();
          //  List rs = jdbcTemplate.queryForList(SQL);
            int result = jdbcTemplate.queryForInt(SQL, new Object[]{GP_SN,LogicAddr});

            return result;
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            return -1;
        }
    }
    
}