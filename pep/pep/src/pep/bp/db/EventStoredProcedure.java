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
public class EventStoredProcedure extends StoredProcedure {
    private static final String SPROC_NAME = "PRC_INSERT_P_ACT";
    private static final String LOGICADDRESS_PARA = "p_logicAddress";
    private static final String SN_PARA = "p_sn";
    private static final String DATETIME_PARA = "p_DateTime";
    private static final String ACT_TOTAL_PARA = "p_act_total";
    private static final String ACT_SHARP_PARA = "p_act_sharp";
    private static final String ACT_PEAK_PARA = "p_act_peak";
    private static final String ACT_LEVEL_PARA = "p_act_level";
    private static final String ACT_VALLEY_PARA = "p_act_valley";
    public EventStoredProcedure(){

    }
    
    public EventStoredProcedure(DataSource dataSource) {
            super(dataSource, SPROC_NAME);
            declareParameter(new SqlParameter(LOGICADDRESS_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(SN_PARA,Types.NUMERIC));
            declareParameter(new SqlParameter(DATETIME_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(ACT_TOTAL_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(ACT_SHARP_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(ACT_PEAK_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(ACT_LEVEL_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(ACT_VALLEY_PARA,Types.VARCHAR));
            compile();
    }


    public Map execute(String logicalAddress,int gpSn,String dataDate,
                        String p_act_total,String p_act_sharp,String p_act_peak
                        ,String p_act_level,String p_act_valley) {
            Map inputs = new HashMap();
            inputs.put(LOGICADDRESS_PARA, logicalAddress);
            inputs.put(SN_PARA, gpSn);
            inputs.put(DATETIME_PARA, dataDate);
            inputs.put(ACT_TOTAL_PARA, p_act_total);
            inputs.put(ACT_SHARP_PARA, p_act_sharp);
            inputs.put(ACT_PEAK_PARA, p_act_peak);
            inputs.put(ACT_LEVEL_PARA, p_act_level);
            inputs.put(ACT_VALLEY_PARA, p_act_valley);
            return super.execute(inputs);
        }

}
