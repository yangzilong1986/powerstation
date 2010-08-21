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
public class I_ACT_StoredProcedure extends StoredProcedure {
    private static final String SPROC_NAME = "PRC_INSERT_I_REACT";
    private static final String LOGICADDRESS_PARA = "p_logicAddress";
    private static final String SN_PARA = "p_sn";
    private static final String DATETIME_PARA = "p_DateTime";
    private static final String I_REACT_TOTAL_PARA = "p_i_react_total";
    private static final String I_REACT_SHARP_PARA = "p_i_react_sharp";
    private static final String I_REACT_PEAK_PARA = "p_i_react_peak";
    private static final String I_REACT_LEVEL_PARA = "p_i_react_level";
    private static final String I_REACT_VALLEY_PARA = "p_i_react_valley";
    public I_ACT_StoredProcedure(){

    }
    
    public I_ACT_StoredProcedure(DataSource dataSource) {
            super(dataSource, SPROC_NAME);
            declareParameter(new SqlParameter(LOGICADDRESS_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(SN_PARA,Types.NUMERIC));
            declareParameter(new SqlParameter(DATETIME_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(I_REACT_TOTAL_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(I_REACT_SHARP_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(I_REACT_PEAK_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(I_REACT_LEVEL_PARA,Types.VARCHAR));
            declareParameter(new SqlParameter(I_REACT_VALLEY_PARA,Types.VARCHAR));
            compile();
    }


    public Map execute(String logicalAddress,int gpSn,String dataDate,
                        String p_i_react_total,String p_i_react_sharp,String p_i_react_peak
                        ,String p_i_react_level,String p_i_react_valley) {
            Map<String,Object> inputs = new HashMap<String,Object>();
            inputs.put(LOGICADDRESS_PARA, logicalAddress);
            inputs.put(SN_PARA, gpSn);
            inputs.put(DATETIME_PARA, dataDate);
            inputs.put(I_REACT_TOTAL_PARA, p_i_react_total);
            inputs.put(I_REACT_SHARP_PARA, p_i_react_sharp);
            inputs.put(I_REACT_PEAK_PARA, p_i_react_peak);
            inputs.put(I_REACT_LEVEL_PARA, p_i_react_level);
            inputs.put(I_REACT_VALLEY_PARA, p_i_react_valley);
            return super.execute(inputs);
        }

}
