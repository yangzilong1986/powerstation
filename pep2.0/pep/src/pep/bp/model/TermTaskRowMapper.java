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
    @Override
    public Object mapRow(ResultSet rs, int index) throws SQLException{
        TermTask task = new TermTask();
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
        task.setStart_time_master(rs.getDate("START_TIME_MASTER"));
        task.setEnd_time_master(rs.getDate("END_TIME_MASTER"));
        task.setExec_circle_master(rs.getInt("EXEC_CYCLE_MASTER"));
        task.setExec_unit_master(rs.getInt("EXEC_UNIT_MASTER"));
        task.setAFN((byte)rs.getInt("AFN"));
        task.setGp_addr(rs.getString("gp_addr"));
        return task;
    }
}
