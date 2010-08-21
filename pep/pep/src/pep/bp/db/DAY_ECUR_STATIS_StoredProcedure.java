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
public class DAY_ECUR_STATIS_StoredProcedure extends StoredProcedure {
    private static final String SPROC_NAME = "PRC_INSERT_DAY_ECUR_STATIS";
    private static final String LOGICADDRESS_PARA = "p_logicAddress";
    private static final String SN_PARA = "p_sn";
    private static final String DATETIME_PARA = "p_DateTime";
    private static final String ECUR_OVER_UPUPLIMIT_TIME_A = "p_ECUR_OVER_UPUPLIMIT_TIME_A";
    private static final String ECUR_OVER_UPLIMIT_TIME_A = "p_ECUR_OVER_UPLIMIT_TIME_A";
    private static final String ECUR_OVER_UPUPLIMIT_TIME_B = "p_ECUR_OVER_UPUPLIMIT_TIME_B";
    private static final String ECUR_OVER_UPLIMIT_TIME_B = "p_ECUR_OVER_UPLIMIT_TIME_B";
    private static final String ECUR_OVER_UPUPLIMIT_TIME_C = "p_ECUR_OVER_UPUPLIMIT_TIME_C";
    private static final String ECUR_OVER_UPLIMIT_TIME_C = "p_ECUR_OVER_UPLIMIT_TIME_C";
    private static final String ECUR_OVER_UPLIMIT_TIME_O = "p_ECUR_OVER_UPLIMIT_TIME_O";
    private static final String ECUR_PEAK_A = "p_ECUR_PEAK_A";
    private static final String ECUR_PEAK_A_TIME = "p_ECUR_PEAK_A_TIME";
    private static final String ECUR_PEAK_B = "p_ECUR_PEAK_B";
    private static final String ECUR_PEAK_B_TIME = "p_ECUR_PEAK_B_TIME";
    private static final String ECUR_PEAK_C = "p_ECUR_PEAK_C";
    private static final String ECUR_PEAK_C_TIME = "p_ECUR_PEAK_C_TIME";
    private static final String ECUR_PEAK_O = "p_ECUR_PEAK_O";
    private static final String ECUR_PEAK_O_TIME = "p_ECUR_PEAK_O_TIME";
    public DAY_ECUR_STATIS_StoredProcedure(){

    }
    
    public DAY_ECUR_STATIS_StoredProcedure(DataSource dataSource) {
            super(dataSource, SPROC_NAME);
            declareParameter(new SqlParameter(LOGICADDRESS_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(SN_PARA,Types.NUMERIC));
            declareParameter(new SqlParameter(DATETIME_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(ECUR_OVER_UPUPLIMIT_TIME_A,Types.VARCHAR));
            declareParameter(new SqlParameter(ECUR_OVER_UPLIMIT_TIME_A,Types.VARCHAR));
            declareParameter(new SqlParameter(ECUR_OVER_UPUPLIMIT_TIME_B,Types.VARCHAR));
            declareParameter(new SqlParameter(ECUR_OVER_UPLIMIT_TIME_B,Types.VARCHAR));
            declareParameter(new SqlParameter(ECUR_OVER_UPUPLIMIT_TIME_C,Types.VARCHAR));
            declareParameter(new SqlParameter(ECUR_OVER_UPLIMIT_TIME_C,Types.VARCHAR));
            declareParameter(new SqlParameter(ECUR_OVER_UPLIMIT_TIME_O,Types.VARCHAR));
            declareParameter(new SqlParameter(ECUR_PEAK_A,Types.VARCHAR));
            declareParameter(new SqlParameter(ECUR_PEAK_A_TIME,Types.VARCHAR));
            declareParameter(new SqlParameter(ECUR_PEAK_B,Types.VARCHAR));
            declareParameter(new SqlParameter(ECUR_PEAK_B_TIME,Types.VARCHAR));
            declareParameter(new SqlParameter(ECUR_PEAK_C,Types.VARCHAR));
            declareParameter(new SqlParameter(ECUR_PEAK_C_TIME,Types.VARCHAR));
            declareParameter(new SqlParameter(ECUR_PEAK_O,Types.VARCHAR));
            declareParameter(new SqlParameter(ECUR_PEAK_O_TIME,Types.VARCHAR));
            compile();
    }


    public Map execute(String logicalAddress,int gpSn,String dataDate,
                        String p_ecur_over_upuplimit_time_a,String p_ecur_over_uplimit_time_a,
                        String p_ecur_over_upuplimit_time_b,String p_ecur_over_uplimit_time_b,
                        String p_ecur_over_upuplimit_time_c,String p_ecur_over_uplimit_time_c,
                        String p_ecur_over_uplimit_time_o,
                        String p_ecur_peak_a,String p_ecur_peak_a_time,
                        String p_ecur_peak_b,String p_ecur_peak_b_time,
                        String p_ecur_peak_c,String p_ecur_peak_c_time,
                        String p_ecur_peak_o,String p_ecur_peak_o_time) {
            Map<String,Object> inputs = new HashMap<String,Object>();
            inputs.put(LOGICADDRESS_PARA, logicalAddress);
            inputs.put(SN_PARA, gpSn);
            inputs.put(DATETIME_PARA, dataDate);
            inputs.put(ECUR_OVER_UPUPLIMIT_TIME_A, p_ecur_over_upuplimit_time_a);
            inputs.put(ECUR_OVER_UPLIMIT_TIME_A, p_ecur_over_uplimit_time_a);
            inputs.put(ECUR_OVER_UPUPLIMIT_TIME_B, p_ecur_over_upuplimit_time_b);
            inputs.put(ECUR_OVER_UPLIMIT_TIME_B, p_ecur_over_uplimit_time_b);
            inputs.put(ECUR_OVER_UPUPLIMIT_TIME_C, p_ecur_over_upuplimit_time_c);
            inputs.put(ECUR_OVER_UPLIMIT_TIME_C, p_ecur_over_uplimit_time_c);
            inputs.put(ECUR_OVER_UPLIMIT_TIME_O, p_ecur_over_uplimit_time_o);
            inputs.put(ECUR_PEAK_A, p_ecur_peak_a);
            inputs.put(ECUR_PEAK_A_TIME, p_ecur_peak_a_time);
            inputs.put(ECUR_PEAK_B, p_ecur_peak_b);
            inputs.put(ECUR_PEAK_B_TIME, p_ecur_peak_b_time);
            inputs.put(ECUR_PEAK_C, p_ecur_peak_c);
            inputs.put(ECUR_PEAK_C_TIME, p_ecur_peak_c_time);
            inputs.put(ECUR_PEAK_O, p_ecur_peak_o);
            inputs.put(ECUR_PEAK_O_TIME, p_ecur_peak_o_time);
            return super.execute(inputs);
        }

}
