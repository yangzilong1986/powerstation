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
    private static final String SPROC_NAME = "PRC_INSERT_EVENT";
    private static final String LOGICADDRESS_PARA = "p_logicAddress";
    private static final String SN_PARA = "p_sn";
    private static final String EX_CODE_PARA = "p_ex_code";
    private static final String EX_TIME = "p_ex_time";
    private static final String EX_DETAIL = "p_ex_detail";
    
    public EventStoredProcedure(){

    }
    
    public EventStoredProcedure(DataSource dataSource) {
            super(dataSource, SPROC_NAME);
            declareParameter(new SqlParameter(LOGICADDRESS_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(SN_PARA,Types.NUMERIC));
            declareParameter(new SqlParameter(EX_CODE_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(EX_TIME,Types.VARCHAR));
            declareParameter(new SqlParameter(EX_DETAIL,Types.VARCHAR));
            compile();
    }


    public Map execute(String logicalAddress,int gpSn,String ex_code,String ex_time,String ex_detail) {
            Map<String,Object> inputs = new HashMap<String,Object>();
            inputs.put(LOGICADDRESS_PARA, logicalAddress);
            inputs.put(SN_PARA, gpSn);
            inputs.put(EX_CODE_PARA, ex_code);
            inputs.put(EX_TIME, ex_time);
            inputs.put(EX_DETAIL, ex_detail);
            return super.execute(inputs);
        }

}
