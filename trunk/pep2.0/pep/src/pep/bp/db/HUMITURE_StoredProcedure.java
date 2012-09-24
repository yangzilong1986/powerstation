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
 * @author THINKPAD
 */
public class HUMITURE_StoredProcedure  extends StoredProcedure{
    private static final String SPROC_NAME = "PRC_INSERT_HUMITURE";
    private static final String LOGICADDRESS_PARA = "p_logicAddress";
    private static final String SN_PARA = "p_sn";
    private static final String HUMIDITY_PARA = "p_humidity";
    private static final String TEMPERATURE_PARA = "p_temperature";

    public HUMITURE_StoredProcedure(){

    }

    public HUMITURE_StoredProcedure(DataSource dataSource) {
            super(dataSource, SPROC_NAME);
            declareParameter(new SqlParameter(LOGICADDRESS_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(SN_PARA,Types.NUMERIC));
            declareParameter(new SqlParameter(HUMIDITY_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(TEMPERATURE_PARA,Types.VARCHAR));

            compile();
    }


    public Map execute(String logicalAddress,int gpSn,int humidity,int temperature)
    {
            Map<String,Object> inputs = new HashMap<String,Object>();
            inputs.put(LOGICADDRESS_PARA, logicalAddress);
            inputs.put(SN_PARA, gpSn);
            inputs.put(HUMIDITY_PARA, humidity);
            inputs.put(TEMPERATURE_PARA, temperature);
            
            return super.execute(inputs);
     }
}
