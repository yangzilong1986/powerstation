/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import pep.bp.model.Dto;
import pep.bp.model.Dto.DtoItem;
import pep.codec.protocol.gb.gb376.Packet376Event36;
import pep.codec.protocol.gb.gb376.PmPacket376EventBase;

/**
 *
 * @author Thinkpad
 */
@Transactional
public class DataServiceIMP implements DataService{
    private final static Logger log = LoggerFactory.getLogger(DataServiceIMP.class);
    private JdbcTemplate jdbcTemplate;

    private ACTDataStoredProcedure actStoredProcedure;
    private ECCURV_DataStoredProcedure eccurvStoredProcedure;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public DataSource getDataSource(){
        return this.jdbcTemplate.getDataSource();
    }

    @Override
    public void insertRecvData(Dto dto) {
        byte AFN = dto.getAfn();

        List dtoItems = dto.getDataItems();
        for(int i=0;i<= dtoItems.size()-1;i++){
            DtoItem  dtoItem = (DtoItem)dtoItems.get(i);
            String commandItemCode = dtoItem.commandItemCode;
            Map<String,String> dataItemMap = dtoItem.dataMap;
            if(AFN == (byte)0X0C){
                if(commandItemCode.equals("100C0129")){
                    insertData_P_ACT(dto.getLogicAddress(),dtoItem.gp,dtoItem.dataTime,
                                     dataItemMap.get("0100"),dataItemMap.get("0101"),
                                     dataItemMap.get("0102"),dataItemMap.get("0103"),
                                     dataItemMap.get("0104"));
                }
                if(commandItemCode.equals("100C0025")){
                    insertData_EC_CURV(dto.getLogicAddress(),dtoItem.gp,dtoItem.dataTime,
                                     dataItemMap.get("2201"),dataItemMap.get("2202"),dataItemMap.get("2203"),
                                     dataItemMap.get("2204"),"",dataItemMap.get("2101"),
                                     dataItemMap.get("2102"),dataItemMap.get("2103"));
                }
            }
        }
     }

    @Override
    public void insertLBEvent(Packet376Event36 event){

    }

    @Override
     public void insertEvent(PmPacket376EventBase event){
        
    }


    //当前正向有功电能示值
    private void insertData_P_ACT(String logicalAddress,int gpSn,String dataDate,
            String p_act_total,String p_act_sharp,String p_act_peak,String p_act_level,String p_act_valley)
    {
        try {
            this.actStoredProcedure.execute(logicalAddress, gpSn, dataDate, p_act_total, p_act_sharp, p_act_peak, p_act_level, p_act_valley);

        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
        }
    }

    //电压电流曲线
    private void insertData_EC_CURV(String logicalAddress,int gpSn,String dataDate,
            String ECUR_A,String ECUR_B,String ECUR_C,String ECUR_L,String ECUR_S,
            String VOLT_A,String VOLT_B,String VOLT_C)
    {
        try {
            this.eccurvStoredProcedure.execute(logicalAddress,gpSn, dataDate, ECUR_A, ECUR_B, ECUR_C, ECUR_L, ECUR_S, VOLT_A, VOLT_B, VOLT_C);
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
        }
    }


    //设备事件
    private void insertData_Event_Data(String logicalAddress,byte gpSn,String dataDate,
            String p_act_total,String p_act_sharp,String p_act_peak,String p_act_level,String p_act_valley)
    {
        try {
            
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
        }
    }

    /**
     * @return the actStoredProcedure
     */
    public ACTDataStoredProcedure getActStoredProcedure() {
        return actStoredProcedure;
    }

    /**
     * @param actStoredProcedure the actStoredProcedure to set
     */
    public void setActStoredProcedure(ACTDataStoredProcedure actStoredProcedure) {
        this.actStoredProcedure = actStoredProcedure;
    }

    /**
     * @return the eccurvStoredProcedure
     */
    public ECCURV_DataStoredProcedure getEccurvStoredProcedure() {
        return eccurvStoredProcedure;
    }

    /**
     * @param eccurvStoredProcedure the eccurvStoredProcedure to set
     */
    public void setEccurvStoredProcedure(ECCURV_DataStoredProcedure eccurvStoredProcedure) {
        this.eccurvStoredProcedure = eccurvStoredProcedure;
    }

}