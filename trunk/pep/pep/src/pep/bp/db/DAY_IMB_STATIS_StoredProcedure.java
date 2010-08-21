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
public class DAY_IMB_STATIS_StoredProcedure extends StoredProcedure {
    private static final String SPROC_NAME = "PRC_INSERT_DAY_IMB_STATIS";
    private static final String LOGICADDRESS_PARA = "p_logicAddress";
    private static final String SN_PARA = "p_sn";
    private static final String DATETIME_PARA = "p_DateTime";
    private static final String ECUR_OVER_IMBAL_TIME = "p_ECUR_OVER_IMBAL_TIME";
    private static final String VOLT_OVER_IMBAL_TIME = "p_VOLT_OVER_IMBAL_TIME";
    private static final String ECUR_IMBAL_MAX = "p_ECUR_IMBAL_MAX";
    private static final String ECUR_IMBAL_MAX_TIME = "p_ECUR_IMBAL_MAX_TIME";
    private static final String VOLT_IMBAL_MAX = "p_VOLT_IMBAL_MAX";
    private static final String VOLT_IMBAL_MAX_TIME = "p_VOLT_IMBAL_MAX_TIME";
    public DAY_IMB_STATIS_StoredProcedure(){

    }
    
    public DAY_IMB_STATIS_StoredProcedure(DataSource dataSource) {
            super(dataSource, SPROC_NAME);
            declareParameter(new SqlParameter(LOGICADDRESS_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(SN_PARA,Types.NUMERIC));
            declareParameter(new SqlParameter(DATETIME_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(ECUR_OVER_IMBAL_TIME,Types.VARCHAR));
            declareParameter(new SqlParameter(VOLT_OVER_IMBAL_TIME,Types.VARCHAR));
            declareParameter(new SqlParameter(ECUR_IMBAL_MAX,Types.VARCHAR));
            declareParameter(new SqlParameter(ECUR_IMBAL_MAX_TIME,Types.VARCHAR));
            declareParameter(new SqlParameter(VOLT_IMBAL_MAX,Types.VARCHAR));
            declareParameter(new SqlParameter(VOLT_IMBAL_MAX_TIME,Types.VARCHAR));

            compile();
    }


    public Map execute(String logicalAddress,int gpSn,String dataDate,
                        String p_ecur_over_imbal_time,
                        String p_volt_over_imbal_time,
                        String p_ecur_imbal_max,
                        String p_ecur_imbal_max_time,
                        String p_volt_imbal_max,
                        String p_volt_imbal_max_time) {
            Map<String,Object> inputs = new HashMap<String,Object>();
            inputs.put(LOGICADDRESS_PARA, logicalAddress);
            inputs.put(SN_PARA, gpSn);
            inputs.put(DATETIME_PARA, dataDate);
            inputs.put(ECUR_OVER_IMBAL_TIME, p_ecur_over_imbal_time);
            inputs.put(VOLT_OVER_IMBAL_TIME, p_volt_over_imbal_time);
            inputs.put(ECUR_IMBAL_MAX, p_ecur_imbal_max);
            inputs.put(ECUR_IMBAL_MAX_TIME, p_ecur_imbal_max_time);
            inputs.put(VOLT_IMBAL_MAX, p_volt_imbal_max);
            inputs.put(VOLT_IMBAL_MAX_TIME, p_volt_imbal_max_time);

            return super.execute(inputs);
        }

}
