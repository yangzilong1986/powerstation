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
public class RTTaskRecvRowMapper implements RowMapper {

    public Object mapRow(ResultSet rs, int index) throws SQLException {
        RTTaskRecv recv = new RTTaskRecv();
        recv.setTaskId(rs.getInt("TASK_ID"));
        recv.setSequenceCode(rs.getInt("SEQUENCE_CODE"));
        recv.setLogicAddress(rs.getString("LOGICAL_ADDR"));
        recv.setRecvMsg(rs.getString("RECV_MSG"));
        recv.setRecvTime(rs.getDate("RECV_TIME"));
        return recv;
    }
}
