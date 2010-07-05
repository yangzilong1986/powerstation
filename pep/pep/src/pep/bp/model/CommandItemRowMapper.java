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
public class CommandItemRowMapper implements RowMapper{
public Object mapRow(ResultSet rs, int index) throws SQLException{
        CommanddItemDAO cmdItem = new CommanddItemDAO();
        cmdItem.setProtocol_no(rs.getString("protocol_no"));
        cmdItem.setCommandItemCode(rs.getString("commanditem_code"));
        return cmdItem;
    }
}
