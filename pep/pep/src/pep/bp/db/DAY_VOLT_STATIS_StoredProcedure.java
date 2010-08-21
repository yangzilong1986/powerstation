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
public class DAY_VOLT_STATIS_StoredProcedure extends StoredProcedure {
    private static final String SPROC_NAME = "PRC_INSERT_DAY_VOLT_STATIS";
    private static final String LOGICADDRESS_PARA = "p_logicAddress";
    private static final String SN_PARA = "p_sn";
    private static final String DATETIME_PARA = "p_DateTime";
    private static final String OVER_UPUPLIMIT_TIME_A = "p_OVER_UPUPLIMIT_TIME_A";
    private static final String OVER_DOWNDOWNLIMIT_TIME_A = "p_OVER_DOWNDOWNLIMIT_TIME_A";
    private static final String OVER_UPLIMIT_TIME_A = "p_OVER_UPLIMIT_TIME_A";
    private static final String OVER_DOWNLIMIT_TIME_A = "p_OVER_DOWNLIMIT_TIME_A";
    private static final String QUALIFY_TIME_A = "p_QUALIFY_TIME_A";
    private static final String OVER_UPUPLIMIT_TIME_B = "p_OVER_UPUPLIMIT_TIME_B";
    private static final String OVER_DOWNDOWNLIMIT_TIME_B = "p_OVER_DOWNDOWNLIMIT_TIME_B";
    private static final String OVER_UPLIMIT_TIME_B = "p_OVER_UPLIMIT_TIME_B";
    private static final String OVER_DOWNLIMIT_TIME_B = "p_OVER_DOWNLIMIT_TIME_B";
    private static final String QUALIFY_TIME_B = "p_QUALIFY_TIME_B";
    private static final String OVER_UPUPLIMIT_TIME_C = "p_OVER_UPUPLIMIT_TIME_C";
    private static final String OVER_DOWNDOWNLIMIT_TIME_C = "p_OVER_DOWNDOWNLIMIT_TIME_C";
    private static final String OVER_UPLIMIT_TIME_C = "p_OVER_UPLIMIT_TIME_C";
    private static final String OVER_DOWNLIMIT_TIME_C = "p_OVER_DOWNLIMIT_TIME_C";
    private static final String QUALIFY_TIME_C = "p_QUALIFY_TIME_C";
    private static final String PEAK_A = "p_PEAK_A";
    private static final String PEAK_A_TIME = "p_PEAK_A_TIME";
    private static final String VALLEY_A = "p_VALLEY_A";
    private static final String VALLEY_A_TIME = "p_VALLEY_A_TIME";
    private static final String PEAK_B = "p_PEAK_B";
    private static final String PEAK_B_TIME = "p_PEAK_B_TIME";
    private static final String VALLEY_B = "p_VALLEY_B";
    private static final String VALLEY_B_TIME = "p_VALLEY_B_TIME";
    private static final String PEAK_C = "p_PEAK_C";
    private static final String PEAK_C_TIME = "p_PEAK_C_TIME";
    private static final String VALLEY_C = "p_VALLEY_C";
    private static final String VALLEY_C_TIME = "p_VALLEY_C_TIME";
    private static final String AVERAGE_VOLT_A = "p_AVERAGE_VOLT_A";
    private static final String AVERAGE_VOLT_B = "p_AVERAGE_VOLT_B";
    private static final String AVERAGE_VOLT_C = "p_AVERAGE_VOLT_C";
    public DAY_VOLT_STATIS_StoredProcedure(){

    }
    
    public DAY_VOLT_STATIS_StoredProcedure(DataSource dataSource) {
            super(dataSource, SPROC_NAME);
            declareParameter(new SqlParameter(LOGICADDRESS_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(SN_PARA,Types.NUMERIC));
            declareParameter(new SqlParameter(DATETIME_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(OVER_UPUPLIMIT_TIME_A,Types.VARCHAR));
            declareParameter(new SqlParameter(OVER_DOWNDOWNLIMIT_TIME_A,Types.VARCHAR));
            declareParameter(new SqlParameter(OVER_UPLIMIT_TIME_A,Types.VARCHAR));
            declareParameter(new SqlParameter(OVER_DOWNLIMIT_TIME_A,Types.VARCHAR));
            declareParameter(new SqlParameter(QUALIFY_TIME_A,Types.VARCHAR));
            declareParameter(new SqlParameter(OVER_UPUPLIMIT_TIME_B,Types.VARCHAR));
            declareParameter(new SqlParameter(OVER_DOWNDOWNLIMIT_TIME_B,Types.VARCHAR));
            declareParameter(new SqlParameter(OVER_UPLIMIT_TIME_B,Types.VARCHAR));
            declareParameter(new SqlParameter(OVER_DOWNLIMIT_TIME_B,Types.VARCHAR));
            declareParameter(new SqlParameter(QUALIFY_TIME_B,Types.VARCHAR));
            declareParameter(new SqlParameter(OVER_UPUPLIMIT_TIME_C,Types.VARCHAR));
            declareParameter(new SqlParameter(OVER_DOWNDOWNLIMIT_TIME_C,Types.VARCHAR));
            declareParameter(new SqlParameter(OVER_UPLIMIT_TIME_C,Types.VARCHAR));
            declareParameter(new SqlParameter(OVER_DOWNLIMIT_TIME_C,Types.VARCHAR));
            declareParameter(new SqlParameter(QUALIFY_TIME_C,Types.VARCHAR));
            declareParameter(new SqlParameter(PEAK_A,Types.VARCHAR));
            declareParameter(new SqlParameter(PEAK_A_TIME,Types.VARCHAR));
            declareParameter(new SqlParameter(VALLEY_A,Types.VARCHAR));
            declareParameter(new SqlParameter(VALLEY_A_TIME,Types.VARCHAR));
            declareParameter(new SqlParameter(PEAK_B,Types.VARCHAR));
            declareParameter(new SqlParameter(PEAK_B_TIME,Types.VARCHAR));
            declareParameter(new SqlParameter(VALLEY_B,Types.VARCHAR));
            declareParameter(new SqlParameter(VALLEY_B_TIME,Types.VARCHAR));
            declareParameter(new SqlParameter(PEAK_C,Types.VARCHAR));
            declareParameter(new SqlParameter(PEAK_C_TIME,Types.VARCHAR));
            declareParameter(new SqlParameter(VALLEY_C,Types.VARCHAR));
            declareParameter(new SqlParameter(VALLEY_C_TIME,Types.VARCHAR));
            declareParameter(new SqlParameter(AVERAGE_VOLT_A,Types.VARCHAR));
            declareParameter(new SqlParameter(AVERAGE_VOLT_B,Types.VARCHAR));
            declareParameter(new SqlParameter(AVERAGE_VOLT_C,Types.VARCHAR));
            compile();
    }


    public Map execute(String logicalAddress,int gpSn,String dataDate,
                        String p_OVER_UPUPLIMIT_TIME_A,String p_OVER_DOWNDOWNLIMIT_TIME_A,
                        String p_OVER_UPLIMIT_TIME_A,String p_OVER_DOWNLIMIT_TIME_A,
                        String p_QUALIFY_TIME_A,String p_OVER_UPUPLIMIT_TIME_B,
                        String p_OVER_DOWNDOWNLIMIT_TIME_B,
                        String p_OVER_UPLIMIT_TIME_B,String p_OVER_DOWNLIMIT_TIME_B,
                        String p_QUALIFY_TIME_B,String p_OVER_UPUPLIMIT_TIME_C,
                        String p_OVER_DOWNDOWNLIMIT_TIME_C,String p_OVER_UPLIMIT_TIME_C,
                        String p_OVER_DOWNLIMIT_TIME_C,String p_QUALIFY_TIME_C,
                        String p_PEAK_A,String p_PEAK_A_TIME,
                        String p_VALLEY_A,String p_VALLEY_A_TIME,
                        String p_PEAK_B,String p_PEAK_B_TIME,
                        String p_VALLEY_B,String p_VALLEY_B_TIME,
                        String p_PEAK_C,String p_PEAK_C_TIME,
                        String p_VALLEY_C,String p_VALLEY_C_TIME,
                        String p_AVERAGE_VOLT_A,String p_AVERAGE_VOLT_B,
                        String p_AVERAGE_VOLT_C) {
            Map<String,Object> inputs = new HashMap<String,Object>();
            inputs.put(LOGICADDRESS_PARA, logicalAddress);
            inputs.put(SN_PARA, gpSn);
            inputs.put(DATETIME_PARA, dataDate);
            inputs.put(OVER_UPUPLIMIT_TIME_A, p_OVER_UPUPLIMIT_TIME_A);
            inputs.put(OVER_DOWNDOWNLIMIT_TIME_A, p_OVER_DOWNDOWNLIMIT_TIME_A);
            inputs.put(OVER_UPLIMIT_TIME_A, p_OVER_UPLIMIT_TIME_A);
            inputs.put(OVER_DOWNLIMIT_TIME_A, p_OVER_DOWNLIMIT_TIME_A);
            inputs.put(QUALIFY_TIME_A, p_QUALIFY_TIME_A);
            inputs.put(OVER_UPUPLIMIT_TIME_B, p_OVER_UPUPLIMIT_TIME_B);
            inputs.put(OVER_DOWNDOWNLIMIT_TIME_B, p_OVER_DOWNDOWNLIMIT_TIME_B);
            inputs.put(OVER_UPLIMIT_TIME_B, p_OVER_UPLIMIT_TIME_B);
            inputs.put(OVER_DOWNLIMIT_TIME_B, p_OVER_DOWNLIMIT_TIME_B);
            inputs.put(QUALIFY_TIME_B, p_QUALIFY_TIME_B);
            inputs.put(OVER_UPUPLIMIT_TIME_C, p_OVER_UPUPLIMIT_TIME_C);
            inputs.put(OVER_DOWNDOWNLIMIT_TIME_C, p_OVER_DOWNDOWNLIMIT_TIME_C);
            inputs.put(OVER_UPLIMIT_TIME_C, p_OVER_UPLIMIT_TIME_C);
            inputs.put(OVER_DOWNLIMIT_TIME_C, p_OVER_DOWNLIMIT_TIME_C);
            inputs.put(QUALIFY_TIME_C, p_QUALIFY_TIME_C);
            inputs.put(PEAK_A, p_PEAK_A);
            inputs.put(PEAK_A_TIME, p_PEAK_A_TIME);
            inputs.put(VALLEY_A, p_VALLEY_A);
            inputs.put(VALLEY_A_TIME, p_VALLEY_A_TIME);
            inputs.put(PEAK_B, p_PEAK_B);
            inputs.put(PEAK_B_TIME, p_PEAK_B_TIME);
            inputs.put(VALLEY_B, p_VALLEY_B);
            inputs.put(VALLEY_B_TIME, p_VALLEY_B_TIME);
            inputs.put(PEAK_C, p_PEAK_C);
            inputs.put(PEAK_C_TIME, p_PEAK_C_TIME);
            inputs.put(VALLEY_C, p_VALLEY_C);
            inputs.put(VALLEY_C_TIME, p_VALLEY_C_TIME);
            inputs.put(AVERAGE_VOLT_A, p_AVERAGE_VOLT_A);
            inputs.put(AVERAGE_VOLT_B, p_AVERAGE_VOLT_B);
            inputs.put(AVERAGE_VOLT_C, p_AVERAGE_VOLT_C);
            return super.execute(inputs);
        }

}
