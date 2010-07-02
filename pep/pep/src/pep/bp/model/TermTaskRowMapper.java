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
public class TermTaskRowMapper implements RowMapper{
    public Object mapRow(ResultSet rs, int index) throws SQLException{
        TermTaskDAO task = new TermTaskDAO();
        task.setLogicAddress(rs.getString("LOGICAL_ADDR"));
        task.setGp_char(rs.getString("GP_CHAR"));
        task.setGp_sn(rs.getInt("GP_SN"));
        task.setTaskId(rs.getInt("TASK_ID"));
        task.setProtocol_No(rs.getString("PROTOCOL_NO"));
        task.setSystem_Object(rs.getString("SYS_OBJECT"));
        task.setStartup_Flag(rs.getString("STARTUP_FLAG"));
        task.setTime_Interval(rs.getInt("TIME_INTERVAL"));
        task.setBaseTime_gw(rs.getDate("BASE_TIME_GW"));
        task.setSendup_Circle_gw(rs.getInt("SENDUP_CYCLE_GW"));
        task.setSendup_Unit_gw(rs.getInt("SENDUP_UNIT_GW"));
        task.setExt_cnt_gw(rs.getInt("EXT_CNT_GW"));
        return task;
    }
}
