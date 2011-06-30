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

import pep.bp.model.SMSDAO;
import pep.bp.model.SMSRowMapper;

/**
 *
 * @author Thinkpad
 */
@Transactional
public class SMSServiceIMP implements SmsService {

    private final static Logger log = LoggerFactory.getLogger(SMSServiceIMP.class);
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<SMSDAO> getRecvSMS() {

        try {
            StringBuilder sbSQL = new StringBuilder();
            sbSQL.append("select a.id, d.logical_addr, e.gp_addr");
            sbSQL.append(" from a_sms_receive a, a_sms_send b, e_loubao_event c, c_terminal d, c_gp e");
            sbSQL.append(" where a.telecode=b.telecode and a.msg=b.tag and to_number(b.tag)=c.lbsj_id");
            sbSQL.append(" and c.gp_id=e.gp_id and e.term_id=d.term_id");
            return (List<SMSDAO>) jdbcTemplate.query(sbSQL.toString(), new SMSRowMapper());
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            return null;
        }


    }

    @Override
    public void deleteRecvSMS(int smsid) {
        try {
            StringBuilder sbSQL = new StringBuilder();
            sbSQL.append("delete from a_sms_receive where id=?");

            jdbcTemplate.update(sbSQL.toString(),
                    new Object[]{smsid});
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
        }
    }
}
