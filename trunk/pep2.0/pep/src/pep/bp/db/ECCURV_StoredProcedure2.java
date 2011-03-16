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
public class ECCURV_StoredProcedure2 extends StoredProcedure {
    private static final String SPROC_NAME = "PRC_INSERT_EC_CURV_2";
    private static final String LOGICADDRESS_PARA = "p_logicAddress";
    private static final String SN_PARA = "p_sn";
    private static final String DATETIME_PARA = "p_DateTime";


    private static final String ECUR_A_PARA = "p_ecur_a";
    private static final String ECUR_B_PARA = "p_ecur_b";
    private static final String ECUR_C_PARA = "p_ecur_c";
    private static final String ECUR_L_PARA = "p_ecur_l";
    private static final String ECUR_S_PARA = "p_ecur_s";
    private static final String VOLT_A_PARA = "p_volt_a";
    private static final String VOLT_B_PARA = "p_volt_b";
    private static final String VOLT_C_PARA = "p_volt_c";
    private static final String FN_PARA = "p_fn";

    public ECCURV_StoredProcedure2(){
        
    }

    public ECCURV_StoredProcedure2(DataSource dataSource) {
            super(dataSource, SPROC_NAME);
            declareParameter(new SqlParameter(LOGICADDRESS_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(SN_PARA,Types.NUMERIC));
            declareParameter(new SqlParameter(DATETIME_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(ECUR_A_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(ECUR_B_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(ECUR_C_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(ECUR_L_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(ECUR_S_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(VOLT_A_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(VOLT_B_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(VOLT_C_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(FN_PARA,Types.VARCHAR));
            compile();
    }


    private Map execute(String logicalAddress,int gpSn,String dataDate,
                        String ECUR_A,String ECUR_B,String ECUR_C,String ECUR_L,String ECUR_S,
                        String VOLT_A,String VOLT_B,String VOLT_C,String FN)
    {
            Map<String,Object> inputs = new HashMap<String,Object>();
            inputs.put(LOGICADDRESS_PARA, logicalAddress);
            inputs.put(SN_PARA, gpSn);
            inputs.put(DATETIME_PARA, dataDate);
            inputs.put(ECUR_A_PARA, ECUR_A);
            inputs.put(ECUR_B_PARA, ECUR_B);
            inputs.put(ECUR_C_PARA, ECUR_C);
            inputs.put(ECUR_L_PARA, ECUR_L);
            inputs.put(ECUR_S_PARA, ECUR_S);
            inputs.put(VOLT_A_PARA, VOLT_A);
            inputs.put(VOLT_B_PARA, VOLT_B);
            inputs.put(VOLT_C_PARA, VOLT_C);
            inputs.put(FN_PARA, FN);
            return super.execute(inputs);
     }

    public Map insertVoltA(String logicalAddress,int gpSn,String dataDate,String VOLT_A){
        Map<String,Object> inputs = new HashMap<String,Object>();
            inputs.put(LOGICADDRESS_PARA, logicalAddress);
            inputs.put(SN_PARA, gpSn);
            inputs.put(DATETIME_PARA, dataDate);
            inputs.put(ECUR_A_PARA, "");
            inputs.put(ECUR_B_PARA, "");
            inputs.put(ECUR_C_PARA, "");
            inputs.put(ECUR_L_PARA, "");
            inputs.put(ECUR_S_PARA, "");
            inputs.put(VOLT_A_PARA, VOLT_A);
            inputs.put(VOLT_B_PARA, "");
            inputs.put(VOLT_C_PARA, "");
            inputs.put(FN_PARA, "100D0089");
            return super.execute(inputs);
    }

    public Map insertVoltB(String logicalAddress,int gpSn,String dataDate,String VOLT_B){
        Map<String,Object> inputs = new HashMap<String,Object>();
            inputs.put(LOGICADDRESS_PARA, logicalAddress);
            inputs.put(SN_PARA, gpSn);
            inputs.put(DATETIME_PARA, dataDate);
            inputs.put(ECUR_A_PARA, "");
            inputs.put(ECUR_B_PARA, "");
            inputs.put(ECUR_C_PARA, "");
            inputs.put(ECUR_L_PARA, "");
            inputs.put(ECUR_S_PARA, "");
            inputs.put(VOLT_A_PARA, "");
            inputs.put(VOLT_B_PARA, VOLT_B);
            inputs.put(VOLT_C_PARA, "");
            inputs.put(FN_PARA, "100D0090");
            return super.execute(inputs);
    }

    public Map insertVoltC(String logicalAddress,int gpSn,String dataDate,String VOLT_C){
        Map<String,Object> inputs = new HashMap<String,Object>();
            inputs.put(LOGICADDRESS_PARA, logicalAddress);
            inputs.put(SN_PARA, gpSn);
            inputs.put(DATETIME_PARA, dataDate);
            inputs.put(ECUR_A_PARA, "");
            inputs.put(ECUR_B_PARA, "");
            inputs.put(ECUR_C_PARA, "");
            inputs.put(ECUR_L_PARA, "");
            inputs.put(ECUR_S_PARA, "");
            inputs.put(VOLT_A_PARA, "");
            inputs.put(VOLT_B_PARA, "");
            inputs.put(VOLT_C_PARA, VOLT_C);
            inputs.put(FN_PARA, "100D0091");
            return super.execute(inputs);
    }

    public Map insertEcurA(String logicalAddress,int gpSn,String dataDate,String Ecur_A){
        Map<String,Object> inputs = new HashMap<String,Object>();
            inputs.put(LOGICADDRESS_PARA, logicalAddress);
            inputs.put(SN_PARA, gpSn);
            inputs.put(DATETIME_PARA, dataDate);
            inputs.put(ECUR_A_PARA, Ecur_A);
            inputs.put(ECUR_B_PARA, "");
            inputs.put(ECUR_C_PARA, "");
            inputs.put(ECUR_L_PARA, "");
            inputs.put(ECUR_S_PARA, "");
            inputs.put(VOLT_A_PARA, "");
            inputs.put(VOLT_B_PARA, "");
            inputs.put(VOLT_C_PARA, "");
            inputs.put(FN_PARA, "100D0092");
            return super.execute(inputs);
    }

    public Map insertEcurB(String logicalAddress,int gpSn,String dataDate,String Ecur_B){
        Map<String,Object> inputs = new HashMap<String,Object>();
            inputs.put(LOGICADDRESS_PARA, logicalAddress);
            inputs.put(SN_PARA, gpSn);
            inputs.put(DATETIME_PARA, dataDate);
            inputs.put(ECUR_A_PARA, "");
            inputs.put(ECUR_B_PARA, Ecur_B);
            inputs.put(ECUR_C_PARA, "");
            inputs.put(ECUR_L_PARA, "");
            inputs.put(ECUR_S_PARA, "");
            inputs.put(VOLT_A_PARA, "");
            inputs.put(VOLT_B_PARA, "");
            inputs.put(VOLT_C_PARA, "");
            inputs.put(FN_PARA, "100D0093");
            return super.execute(inputs);
    }

    public Map insertEcurC(String logicalAddress,int gpSn,String dataDate,String Ecur_C){
        Map<String,Object> inputs = new HashMap<String,Object>();
            inputs.put(LOGICADDRESS_PARA, logicalAddress);
            inputs.put(SN_PARA, gpSn);
            inputs.put(DATETIME_PARA, dataDate);
            inputs.put(ECUR_A_PARA, "");
            inputs.put(ECUR_B_PARA, "");
            inputs.put(ECUR_C_PARA, Ecur_C);
            inputs.put(ECUR_L_PARA, "");
            inputs.put(ECUR_S_PARA, "");
            inputs.put(VOLT_A_PARA, "");
            inputs.put(VOLT_B_PARA, "");
            inputs.put(VOLT_C_PARA, "");
            inputs.put(FN_PARA, "100D0094");
            return super.execute(inputs);
    }

    public Map insertEcurL(String logicalAddress,int gpSn,String dataDate,String Ecur_L){
        Map<String,Object> inputs = new HashMap<String,Object>();
            inputs.put(LOGICADDRESS_PARA, logicalAddress);
            inputs.put(SN_PARA, gpSn);
            inputs.put(DATETIME_PARA, dataDate);
            inputs.put(ECUR_A_PARA, "");
            inputs.put(ECUR_B_PARA, "");
            inputs.put(ECUR_C_PARA, "");
            inputs.put(ECUR_L_PARA, Ecur_L);
            inputs.put(ECUR_S_PARA, "");
            inputs.put(VOLT_A_PARA, "");
            inputs.put(VOLT_B_PARA, "");
            inputs.put(VOLT_C_PARA, "");
            inputs.put(FN_PARA, "100D0095");
            return super.execute(inputs);
    }



}
