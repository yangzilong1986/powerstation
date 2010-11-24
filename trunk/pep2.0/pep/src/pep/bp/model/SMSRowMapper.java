/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Thinkpad
 */
public class SMSRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int index) throws SQLException{
        SMSDAO sms = new SMSDAO();
        sms.setSmsid(rs.getInt("id"));
        sms.setLogicAddress(rs.getString("LOGICAL_ADDR"));
        sms.setGp_addr(rs.getString("gp_addr"));
        return sms;
    }

}
