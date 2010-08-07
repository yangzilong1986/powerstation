/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.db;

import java.util.Date;
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
import pep.bp.utils.UtilsBp;
import pep.codec.protocol.gb.gb376.Packet376Event36;
import pep.codec.protocol.gb.gb376.Packet376Event36.Meter;
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
    private PowerCurv_DataStoredProcedure powerCurvStoredProcedure;

    private EventStoredProcedure eventStoredProcedure;
    private LouBaoEventStoredProcedure loubaoEventStoredProcedure;

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
            if(AFN == (byte)0X0C){                             //一类数据
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

                    this.insert_POWER_CRUV(dto.getLogicAddress(),dtoItem.gp,dtoItem.dataTime,
                            dataItemMap.get("2300"),dataItemMap.get("2301"),dataItemMap.get("2302"),
                            dataItemMap.get("2303"),dataItemMap.get("2400"),dataItemMap.get("2401"),
                            dataItemMap.get("2402"),dataItemMap.get("2403"));
                }
            }

            if(AFN == (byte)0X0D){                         //二类数据
                if(commandItemCode.equals("100D0081"))
                    this.insert_POWER_CRUV_F81(dto.getLogicAddress(),dtoItem.gp,dtoItem.dataTime,dataItemMap.get("2300"));


            }
        }
     }

    @Override
    public void insertLBEvent(String rtua, Packet376Event36 event){
        List<Meter> Meters = event.meters;
        for(Meter meter:Meters){
            this.loubaoEventStoredProcedure.execute(rtua, meter.meterAddress,
                    event.GetEventCode(), event.getEventTime(), new Date(), meter.isClosed, meter.isLocked, meter.xiangwei, meter.actValue);
        }
    }

    @Override
     public void insertEvent(String rtua, PmPacket376EventBase event){
        this.eventStoredProcedure.execute(rtua, 0, String.valueOf(event.GetEventCode()), UtilsBp.Date2String(event.getEventTime()));
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

     private void insertData_EC_CURV_F89(String logicalAddress,int gpSn,String dataDate,
            String VOLT_A){
         this.insertData_EC_CURV(logicalAddress, gpSn, dataDate, "", "", "", "", "", VOLT_A, "", "");

     }

     private void insertData_EC_CURV_F90(String logicalAddress,int gpSn,String dataDate,
            String VOLT_B){
         this.insertData_EC_CURV(logicalAddress, gpSn, dataDate, "", "", "", "", "", "", VOLT_B, "");

     }

     private void insertData_EC_CURV_F91(String logicalAddress,int gpSn,String dataDate,
            String VOLT_C){
         this.insertData_EC_CURV(logicalAddress, gpSn, dataDate, "", "", "", "", "", "", "", VOLT_C);

     }

     private void insertData_EC_CURV_F92(String logicalAddress,int gpSn,String dataDate,
            String ECUR_A){
         this.insertData_EC_CURV(logicalAddress, gpSn, dataDate, ECUR_A, "", "", "", "", "", "", "");

     }

     private void insertData_EC_CURV_F93(String logicalAddress,int gpSn,String dataDate,
            String ECUR_B){
         this.insertData_EC_CURV(logicalAddress, gpSn, dataDate, "", ECUR_B, "", "", "", "", "", "");

     }

     private void insertData_EC_CURV_F94(String logicalAddress,int gpSn,String dataDate,
            String ECUR_C){
         this.insertData_EC_CURV(logicalAddress, gpSn, dataDate, "", "", ECUR_C, "", "", "", "", "");

     }

    private void insert_POWER_CRUV_F81(String logicalAddress,int gpSn,String dataDate,String act_power_total){
        this.insert_POWER_CRUV(logicalAddress, gpSn, dataDate, act_power_total, "", "", "", "", "", "", "");
    }

    private void insert_POWER_CRUV_F82(String logicalAddress,int gpSn,String dataDate,String act_power_a){
        this.insert_POWER_CRUV(logicalAddress, gpSn, dataDate, "", act_power_a, "", "", "", "", "", "");
    }

    private void insert_POWER_CRUV_F83(String logicalAddress,int gpSn,String dataDate,String act_power_b){
        this.insert_POWER_CRUV(logicalAddress, gpSn, dataDate, "", "", act_power_b, "", "", "", "", "");
    }

    private void insert_POWER_CRUV_F84(String logicalAddress,int gpSn,String dataDate,String act_power_c){
        this.insert_POWER_CRUV(logicalAddress, gpSn, dataDate, "", "", "", act_power_c, "", "", "", "");
    }

    private void insert_POWER_CRUV_F85(String logicalAddress,int gpSn,String dataDate,String react_power_total){
        this.insert_POWER_CRUV(logicalAddress, gpSn, dataDate, "", "", "", "", react_power_total, "", "", "");
    }

    private void insert_POWER_CRUV_F86(String logicalAddress,int gpSn,String dataDate,String react_power_a){
        this.insert_POWER_CRUV(logicalAddress, gpSn, dataDate, "", "", "", "", "", react_power_a, "", "");
    }

    private void insert_POWER_CRUV_F87(String logicalAddress,int gpSn,String dataDate,String react_power_b){
        this.insert_POWER_CRUV(logicalAddress, gpSn, dataDate, "", "", "", "", "", "", react_power_b, "");
    }

    private void insert_POWER_CRUV_F88(String logicalAddress,int gpSn,String dataDate,String react_power_c){
        this.insert_POWER_CRUV(logicalAddress, gpSn, dataDate, "", "", "", "", "", "", "", react_power_c);
    }

    //功率曲线
    private void insert_POWER_CRUV(String logicalAddress,int gpSn,String dataDate,
            String act_power_total,String act_power_a,String act_power_b,
            String act_power_c,String react_power_total,String react_power_a,
            String react_power_b,String react_power_c)
    {
        try {
            this.powerCurvStoredProcedure.execute(logicalAddress,gpSn, dataDate,
                    act_power_total, act_power_a, act_power_b, act_power_c,
                    react_power_total, react_power_a, react_power_b, react_power_c);
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

    /**
     * @return the eventStoredProcedure
     */
    public EventStoredProcedure getEventStoredProcedure() {
        return eventStoredProcedure;
    }

    /**
     * @param eventStoredProcedure the eventStoredProcedure to set
     */
    public void setEventStoredProcedure(EventStoredProcedure eventStoredProcedure) {
        this.eventStoredProcedure = eventStoredProcedure;
    }

    /**
     * @return the loubaoEventStoredProcedure
     */
    public LouBaoEventStoredProcedure getLoubaoEventStoredProcedure() {
        return loubaoEventStoredProcedure;
    }

    /**
     * @param loubaoEventStoredProcedure the loubaoEventStoredProcedure to set
     */
    public void setLoubaoEventStoredProcedure(LouBaoEventStoredProcedure loubaoEventStoredProcedure) {
        this.loubaoEventStoredProcedure = loubaoEventStoredProcedure;
    }

}
