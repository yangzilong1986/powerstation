/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.db;

import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

/**
 *
 * @author Thinkpad
 */
public class LouBaoEventStoredProcedure extends StoredProcedure {
    private static final String SPROC_NAME = "prc_insert_loubao_event";
    private static final String LOGICADDRESS_PARA = "p_rtua";
    private static final String LOUBAOADDRESS__PARA = "p_loubao_addr";
    private static final String EX_CODE_PARA = "p_EventCode";
    private static final String TRIG_TIME_PARA = "p_trigTime";
    private static final String RECEIVE_TIME_PARA = "p_receiveTime";
    private static final String CLOSED_PARA = "p_closed";
    private static final String LOCKED_PARA = "p_locked";
    private static final String PHASE_PARA = "p_phase";
    private static final String CURRENTVALUE_PARA = "p_currentValue";
    
    public LouBaoEventStoredProcedure(){

    }
    
    public LouBaoEventStoredProcedure(DataSource dataSource) {
            super(dataSource, SPROC_NAME);
            declareParameter(new SqlParameter(LOGICADDRESS_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(LOUBAOADDRESS__PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(EX_CODE_PARA,Types.NUMERIC));
            declareParameter(new SqlParameter(TRIG_TIME_PARA,Types.DATE));
            declareParameter(new SqlParameter(RECEIVE_TIME_PARA,Types.DATE));
            declareParameter(new SqlParameter(CLOSED_PARA,Types.NUMERIC));
            declareParameter(new SqlParameter(LOCKED_PARA,Types.NUMERIC));
            declareParameter(new SqlParameter(PHASE_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(CURRENTVALUE_PARA,Types.NUMERIC));
            
            compile();
    }


    public Map execute(String logicalAddress,String loubaoAddress,int ex_code,
                        Date trig_time,Date receive_Time,int closed,int locked,
                        String phase,int currentValue) {
            Map<String,Object> inputs = new HashMap<String,Object>();
            inputs.put(LOGICADDRESS_PARA, logicalAddress);
            inputs.put(LOUBAOADDRESS__PARA, loubaoAddress);
            inputs.put(EX_CODE_PARA, ex_code);
            inputs.put(TRIG_TIME_PARA, trig_time);
            inputs.put(RECEIVE_TIME_PARA, receive_Time);
            inputs.put(CLOSED_PARA, closed);
            inputs.put(LOCKED_PARA, locked);
            inputs.put(PHASE_PARA, phase);
            inputs.put(CURRENTVALUE_PARA, currentValue);
            return super.execute(inputs);
        }

}
