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
public class PFCURV_StoredProcedure extends StoredProcedure {
    private static final String SPROC_NAME = "PRC_INSERT_PF_CURV";
    private static final String LOGICADDRESS_PARA = "p_logicAddress";
    private static final String SN_PARA = "p_sn";
    private static final String DATETIME_PARA = "p_DateTime";


    private static final String POWER_FACTOR_PARA = "p_power_factor";
    private static final String POWER_FACTOR_A_PARA = "p_power_factor_a";
    private static final String POWER_FACTOR_B_PARA = "p_power_factor_b";
    private static final String POWER_FACTOR_C_PARA = "p_power_factor_c";
    private static final String FN_PARA = "p_fn";


    public PFCURV_StoredProcedure(){
        
    }

    public PFCURV_StoredProcedure(DataSource dataSource) {
            super(dataSource, SPROC_NAME);
            declareParameter(new SqlParameter(LOGICADDRESS_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(SN_PARA,Types.NUMERIC));
            declareParameter(new SqlParameter(DATETIME_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(POWER_FACTOR_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(POWER_FACTOR_A_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(POWER_FACTOR_B_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(POWER_FACTOR_C_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(FN_PARA,Types.VARCHAR));
            compile();
    }


    private Map execute(String logicalAddress,int gpSn,String dataDate,
                        String power_factor,String power_factor_a,String power_factor_b,
                        String power_factor_c,String fn)
    {
            Map<String,Object> inputs = new HashMap<String,Object>();
            inputs.put(LOGICADDRESS_PARA, logicalAddress);
            inputs.put(SN_PARA, gpSn);
            inputs.put(DATETIME_PARA, dataDate);
            inputs.put(POWER_FACTOR_PARA, power_factor);
            inputs.put(POWER_FACTOR_A_PARA, power_factor_a);
            inputs.put(POWER_FACTOR_B_PARA, power_factor_b);
            inputs.put(POWER_FACTOR_C_PARA, power_factor_c);
            inputs.put(FN_PARA, fn);
            return super.execute(inputs);
     }

    public Map insert_power_factor(String logicalAddress,int gpSn,String dataDate,
            String power_factor){
        return this.execute(logicalAddress,gpSn,dataDate,power_factor,"","","","100D0105");
    }

    public Map insert_power_factor_a(String logicalAddress,int gpSn,String dataDate,
            String power_factor_a){
        return this.execute(logicalAddress,gpSn,dataDate,"",power_factor_a,"","","100D0106");
    }

    public Map insert_power_factor_b(String logicalAddress,int gpSn,String dataDate,
            String power_factor_b){
        return this.execute(logicalAddress,gpSn,dataDate,"","",power_factor_b,"","100D0107");
    }

    public Map insert_power_factor_c(String logicalAddress,int gpSn,String dataDate,
            String power_factor_c){
        return this.execute(logicalAddress,gpSn,dataDate,"","","",power_factor_c,"100D0108");
    }

}
