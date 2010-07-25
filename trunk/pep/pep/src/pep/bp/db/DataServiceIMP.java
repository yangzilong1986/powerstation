/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.db;


import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import pep.bp.model.Dto;

/**
 *
 * @author Thinkpad
 */
@Transactional
public class DataServiceIMP implements DataService{
    private final static Logger log = LoggerFactory.getLogger(DataServiceIMP.class);
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void insertRecvData(Dto data) {

    }

    //当前正向有功电能示值
    private void postData_P_ACT(String logicalAddress,byte gpSn,String dataDate,
            String p_act_total,String p_act_sharp,String p_act_peak,String p_act_level,String p_act_valley)
    {
        try {
            StringBuffer sql = new StringBuffer("{call PRC_INSERT_P_ACT(");
            sql.append(logicalAddress + ",");
            sql.append(gpSn + ",");
            sql.append(dataDate + ",");
            sql.append(p_act_total + ",");
            sql.append(p_act_sharp + ",");
            sql.append(p_act_peak + ",");
            sql.append(p_act_level + ",");
            sql.append(p_act_valley + ")}");

            this.jdbcTemplate.execute(sql.toString());
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
        }
    }

    //电压电流曲线
    private void postData_EC_CURV(String logicalAddress,byte gpSn,String dataDate,
            String ECUR_A,String ECUR_B,String ECUR_C,String ECUR_L,String ECUR_S,
            String VOLT_A,String VOLT_B,String VOLT_C)
    {
        try {
            StringBuffer sql = new StringBuffer("{call PRC_INSERT_P_ACT(");
            sql.append(logicalAddress + ",");
            sql.append(gpSn + ",");
            sql.append(dataDate + ",");
            sql.append(ECUR_A + ",");
            sql.append(ECUR_B + ",");
            sql.append(ECUR_C + ",");
            sql.append(ECUR_L + ",");
            sql.append(ECUR_S + ",");
            sql.append(VOLT_A + ",");
            sql.append(VOLT_B + ",");
            sql.append(VOLT_C + ")}");

            this.jdbcTemplate.execute(sql.toString());
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
        }
    }


    //设备事件
    private void postData_Event_Data(String logicalAddress,byte gpSn,String dataDate,
            String p_act_total,String p_act_sharp,String p_act_peak,String p_act_level,String p_act_valley)
    {
        try {
            StringBuffer sql = new StringBuffer("{call PRC_INSERT_P_ACT(");
            sql.append(logicalAddress + ",");
            sql.append(gpSn + ",");
            sql.append(dataDate + ",");
            sql.append(p_act_total + ",");
            sql.append(p_act_sharp + ",");
            sql.append(p_act_peak + ",");
            sql.append(p_act_level + ",");
            sql.append(p_act_valley + ")}");

            this.jdbcTemplate.execute(sql.toString());
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
        }
    }

}
