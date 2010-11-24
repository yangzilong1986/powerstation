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
public class PSStatus_StoredProcedure extends StoredProcedure {
    private static final String SPROC_NAME = "PRC_INSERT_PS_STATUS";
    private static final String LOGICADDRESS_PARA = "p_logicAddress";
    private static final String SN_PARA = "p_sn";
    private static final String DATETIME_PARA = "p_DateTime";


    private static final String OPENED_PARA = "p_opened";
    private static final String LOCKED_PARA = "p_locked";
    private static final String PHASE_PARA = "p_phase";
    private static final String ACTIONTYPE_PARA = "p_actiontype";

    public PSStatus_StoredProcedure(){
        
    }

    public PSStatus_StoredProcedure(DataSource dataSource) {
            super(dataSource, SPROC_NAME);
            declareParameter(new SqlParameter(LOGICADDRESS_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(SN_PARA,Types.NUMERIC));
            declareParameter(new SqlParameter(DATETIME_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(OPENED_PARA,Types.NUMERIC));
            declareParameter(new SqlParameter(LOCKED_PARA,Types.NUMERIC));
            declareParameter(new SqlParameter(PHASE_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(ACTIONTYPE_PARA,Types.VARCHAR));
            compile();
    }


    public Map execute(String logicalAddress,int gpSn,String dataDate,
                        String opened,String locked,String phase,
                        String actiontype)
    {
            Map<String,Object> inputs = new HashMap<String,Object>();
            inputs.put(LOGICADDRESS_PARA, logicalAddress);
            inputs.put(SN_PARA, gpSn);
            inputs.put(DATETIME_PARA, dataDate);
            inputs.put(OPENED_PARA, opened);
            inputs.put(LOCKED_PARA, locked);
            inputs.put(PHASE_PARA, phase);
            inputs.put(ACTIONTYPE_PARA, actiontype);
            return super.execute(inputs);
     }

 
}
