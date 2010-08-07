/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.db;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

/**
 *
 * @author Thinkpad
 */
public class PowerCurv_DataStoredProcedure extends StoredProcedure {
    private static final String SPROC_NAME = "PRC_INSERT_POWER_CRUV";
    private static final String LOGICADDRESS_PARA = "p_logicAddress";
    private static final String SN_PARA = "p_sn";
    private static final String DATETIME_PARA = "p_DateTime";


    private static final String ACT_POWER_TOTAL_PARA = "p_act_power_total";
    private static final String ACT_POWER_A_PARA = "p_act_power_a";
    private static final String ACT_POWER_B_PARA = "p_act_power_b";
    private static final String ACT_POWER_C_PARA = "p_act_power_c";
    private static final String REACT_POWER_TOTAL_PARA = "p_react_power_total";
    private static final String REACT_POWER_A_PARA = "p_react_power_a";
    private static final String REACT_POWER_B_PARA = "p_react_power_b";
    private static final String REACT_POWER_C_PARA = "p_react_power_c";

    public PowerCurv_DataStoredProcedure(){
        
    }

    public PowerCurv_DataStoredProcedure(DataSource dataSource) {
            super(dataSource, SPROC_NAME);
            declareParameter(new SqlParameter(LOGICADDRESS_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(SN_PARA,Types.NUMERIC));
            declareParameter(new SqlParameter(DATETIME_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(ACT_POWER_TOTAL_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(ACT_POWER_A_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(ACT_POWER_B_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(ACT_POWER_C_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(REACT_POWER_TOTAL_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(REACT_POWER_A_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(REACT_POWER_B_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(REACT_POWER_C_PARA,Types.VARCHAR));
            compile();
    }


    public Map execute(String logicalAddress,int gpSn,String dataDate,
            String act_power_total,String act_power_a,String act_power_b,
            String act_power_c,String react_power_total,String react_power_a,
            String react_power_b,String react_power_c)
    {
            Map inputs = new HashMap();
            inputs.put(LOGICADDRESS_PARA, logicalAddress);
            inputs.put(SN_PARA, gpSn);
            inputs.put(DATETIME_PARA, dataDate);
            inputs.put(ACT_POWER_TOTAL_PARA, act_power_total);
            inputs.put(ACT_POWER_A_PARA, act_power_a);
            inputs.put(ACT_POWER_B_PARA, act_power_b);
            inputs.put(ACT_POWER_C_PARA, act_power_c);
            inputs.put(REACT_POWER_TOTAL_PARA, react_power_total);
            inputs.put(REACT_POWER_A_PARA, react_power_a);
            inputs.put(REACT_POWER_B_PARA, react_power_b);
            inputs.put(REACT_POWER_C_PARA, react_power_c);
            return super.execute(inputs);
     }



}
