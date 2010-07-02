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
public class RTTaskRowMapper implements RowMapper {
    public Object mapRow(ResultSet rs, int index) throws SQLException{
        RealTimeTaskDAO task = new RealTimeTaskDAO();
        task.setTaskId(rs.getInt("TASK_ID"));
        task.setSequencecode(rs.getInt("SEQUENCE_CODE"));
        task.setLogicAddress(rs.getString("LOGICAL_ADDR"));
        task.setSendmsg(rs.getString("SEND_MSG"));
        task.setStatestatus(rs.getString("TASK_STATUS"));
        task.setGpMark(rs.getString("GP_MARK"));
        task.setCommandMark(rs.getString("COMMAND_MARK"));
        return task;
    }

}
