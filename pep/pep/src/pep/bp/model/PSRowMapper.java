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
public class PSRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int index) throws SQLException{
        PSDAO ps = new PSDAO();
        ps.setGp_sn(rs.getInt("gp_sn"));
        ps.setLogicAddress(rs.getString("LOGICAL_ADDR"));
        ps.setMp_addr(rs.getString("mp_addr"));
        ps.setTest_day(rs.getString("test_day"));
        ps.setTest_hour(rs.getString("test_time"));
        return ps;
    }

}
