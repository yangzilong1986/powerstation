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

    private P_ACT_StoredProcedure p_actStoredProcedure;
    private P_REACT_StoredProcedure p_reactStoredProcedure;
    private I_REACT_StoredProcedure i_reactStoredProcedure;
    private I_ACT_StoredProcedure i_actStoredProcedure;


    private ECCURV_StoredProcedure eccurvStoredProcedure;
    private ECCURV_StoredProcedure2 eccurvStoredProcedure2;
    private PowerCurv_StoredProcedure powerCurvStoredProcedure;
    private PowerCurv_StoredProcedure2 powerCurvStoredProcedure2;

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
                if(commandItemCode.equals("100C0129")){     //当前正向有功电能示值（总、费率1～M）
                    insertData_P_ACT(dto.getLogicAddress(),dtoItem.gp,dtoItem.dataTime,
                                     dataItemMap.get("0100"),dataItemMap.get("0101"),
                                     dataItemMap.get("0102"),dataItemMap.get("0103"),
                                     dataItemMap.get("0104"));continue;
                }
                if(commandItemCode.equals("100C0130")){   //当前正向无功（组合无功1）电能示值（总、费率1～M）
                    insertData_P_REACT(dto.getLogicAddress(),dtoItem.gp,dtoItem.dataTime,
                                     dataItemMap.get("A000"),dataItemMap.get("A001"),
                                     dataItemMap.get("A002"),dataItemMap.get("A003"),
                                     dataItemMap.get("A004"));continue;
                }

                if(commandItemCode.equals("100C0131")){   //当前反向有功电能示值（总、费率1～M）
                    insertData_I_ACT(dto.getLogicAddress(),dtoItem.gp,dtoItem.dataTime,
                                     dataItemMap.get("0200"),dataItemMap.get("0201"),
                                     dataItemMap.get("0202"),dataItemMap.get("0203"),
                                     dataItemMap.get("0204"));continue;
                }

                if(commandItemCode.equals("100C0132")){   //当前反向无功（组合无功1）电能示值（总、费率1～M）
                    insertData_I_REACT(dto.getLogicAddress(),dtoItem.gp,dtoItem.dataTime,
                                     dataItemMap.get("A100"),dataItemMap.get("A101"),
                                     dataItemMap.get("A102"),dataItemMap.get("A103"),
                                     dataItemMap.get("A104"));continue;
                }

                if(commandItemCode.equals("100C0025")){//当前三相及总有/无功功率、功率因数，三相电压、电流、零序电流、视在功率
                    insertData_EC_CURV(dto.getLogicAddress(),dtoItem.gp,dtoItem.dataTime,   //電壓/電流曲線
                                     dataItemMap.get("2201"),dataItemMap.get("2202"),dataItemMap.get("2203"),
                                     dataItemMap.get("2204"),"",dataItemMap.get("2101"),
                                     dataItemMap.get("2102"),dataItemMap.get("2103"));

                    this.insert_POWER_CRUV(dto.getLogicAddress(),dtoItem.gp,dtoItem.dataTime,  //功率曲線
                            dataItemMap.get("2300"),dataItemMap.get("2301"),dataItemMap.get("2302"),
                            dataItemMap.get("2303"),dataItemMap.get("2400"),dataItemMap.get("2401"),
                            dataItemMap.get("2402"),dataItemMap.get("2403"));continue;
                }
            }

            if(AFN == (byte)0X0D){                         //二类数据
                if(commandItemCode.equals("100D0081")){
                    this.getPowerCurvStoredProcedure2().insert_act_power_total(dto.getLogicAddress(),dtoItem.gp,dtoItem.dataTime,dataItemMap.get("2300"));continue;
                }
                if(commandItemCode.equals("100D0082")){
                    this.getPowerCurvStoredProcedure2().insert_act_power_a(dto.getLogicAddress(),dtoItem.gp,dtoItem.dataTime,dataItemMap.get("2301")); continue;
                }
                if(commandItemCode.equals("100D0083")){
                    this.getPowerCurvStoredProcedure2().insert_act_power_b(dto.getLogicAddress(),dtoItem.gp,dtoItem.dataTime,dataItemMap.get("2302"));continue;
                }
                if(commandItemCode.equals("100D0084")){
                    this.getPowerCurvStoredProcedure2().insert_act_power_c(dto.getLogicAddress(),dtoItem.gp,dtoItem.dataTime,dataItemMap.get("2303")); continue;
                }
                if(commandItemCode.equals("100D0085")){
                    this.getPowerCurvStoredProcedure2().insert_react_power_total(dto.getLogicAddress(),dtoItem.gp,dtoItem.dataTime,dataItemMap.get("2400")); continue;
                }
                if(commandItemCode.equals("100D0086")){
                    this.getPowerCurvStoredProcedure2().insert_react_power_a(dto.getLogicAddress(),dtoItem.gp,dtoItem.dataTime,dataItemMap.get("2401"));continue;
                }
                if(commandItemCode.equals("100D0087")){
                    this.getPowerCurvStoredProcedure2().insert_react_power_b(dto.getLogicAddress(),dtoItem.gp,dtoItem.dataTime,dataItemMap.get("2402"));continue;
                }
                if(commandItemCode.equals("100D0088")){
                    this.getPowerCurvStoredProcedure2().insert_react_power_c(dto.getLogicAddress(),dtoItem.gp,dtoItem.dataTime,dataItemMap.get("2403"));continue;
                }
                if(commandItemCode.equals("100D0089")){
                    this.getEccurvStoredProcedure2().insertVoltA(dto.getLogicAddress(),dtoItem.gp,dtoItem.dataTime,dataItemMap.get("2101"));continue;
                }
                if(commandItemCode.equals("100D0090")){
                    this.getEccurvStoredProcedure2().insertVoltB(dto.getLogicAddress(),dtoItem.gp,dtoItem.dataTime,dataItemMap.get("2102"));continue;
                }
                if(commandItemCode.equals("100D0091")){
                    this.getEccurvStoredProcedure2().insertVoltC(dto.getLogicAddress(),dtoItem.gp,dtoItem.dataTime,dataItemMap.get("2103"));continue;
                }
                if(commandItemCode.equals("100D0092")){
                    this.getEccurvStoredProcedure2().insertEcurA(dto.getLogicAddress(),dtoItem.gp,dtoItem.dataTime,dataItemMap.get("2201"));continue;
                }
                if(commandItemCode.equals("100D0093")){
                    this.getEccurvStoredProcedure2().insertEcurB(dto.getLogicAddress(),dtoItem.gp,dtoItem.dataTime,dataItemMap.get("2202"));continue;
                }
                if(commandItemCode.equals("100D0094")){
                    this.getEccurvStoredProcedure2().insertEcurC(dto.getLogicAddress(),dtoItem.gp,dtoItem.dataTime,dataItemMap.get("2203"));continue;
                }
                if(commandItemCode.equals("100D0095")){
                    this.getEccurvStoredProcedure2().insertEcurC(dto.getLogicAddress(),dtoItem.gp,dtoItem.dataTime,dataItemMap.get("2204"));continue;
                }
                if(commandItemCode.equals("100D0101")){
                    this.p_actStoredProcedure.execute(dto.getLogicAddress(),dtoItem.gp,dtoItem.dataTime,dataItemMap.get("0100"),"","","","");continue;
                }
                if(commandItemCode.equals("100D0102")){
                    this.i_actStoredProcedure.execute(dto.getLogicAddress(),dtoItem.gp,dtoItem.dataTime,dataItemMap.get("A000"),"","","","");continue;
                }
                if(commandItemCode.equals("100D0103")){
                    this.p_reactStoredProcedure.execute(dto.getLogicAddress(),dtoItem.gp,dtoItem.dataTime,dataItemMap.get("0200"),"","","","");continue;
                }
                if(commandItemCode.equals("100D0104")){
                    this.i_reactStoredProcedure.execute(dto.getLogicAddress(),dtoItem.gp,dtoItem.dataTime,dataItemMap.get("A000"),"","","","");continue;
                }
            }
            if(AFN == (byte)0X10){                         //透明转发数据
                if(commandItemCode.equals("8000B66F")){
                    this.insertData_EC_CURV(dto.getLogicAddress(),dtoItem.gp,dtoItem.dataTime,   //電壓/電流曲線
                                     dataItemMap.get("B621"),dataItemMap.get("B622"),dataItemMap.get("B623"),"",
                                     dataItemMap.get("B660"),dataItemMap.get("B611"),
                                     dataItemMap.get("B612"),dataItemMap.get("B613"));
                }
            }
        }
     }

    @Override
    public void insertLBEvent(String rtua, Packet376Event36 event){
        List<Meter> Meters = event.meters;
        for(Meter meter:Meters){
            try {
                this.loubaoEventStoredProcedure.execute(rtua, meter.meterAddress,
                        meter.status, meter.eventTime, new Date(), (meter.isClosed ? 1 :0), (meter.isLocked ? 1 : 0), meter.xiangwei, meter.actValue);
            } catch (Exception e) {
                log.error("错误信息：", e.fillInStackTrace());
            }
        }
    }

    @Override
     public void insertEvent(String rtua, PmPacket376EventBase event){
        try {
            this.eventStoredProcedure.execute(rtua, 0, String.valueOf(event.GetEventCode()), UtilsBp.Date2String(event.getEventTime()));
        } catch (Exception e) {
            log.error("错误信息：", e.fillInStackTrace());
        }
    }


    //当前正向有功电能示值
    private void insertData_P_ACT(String logicalAddress,int gpSn,String dataDate,
            String p_act_total,String p_act_sharp,String p_act_peak,String p_act_level,String p_act_valley)
    {
           try {
            this.p_actStoredProcedure.execute(logicalAddress, gpSn, dataDate, p_act_total, p_act_sharp, p_act_peak, p_act_level, p_act_valley);
        } catch (Exception e) {
            log.error("错误信息：", e.fillInStackTrace());
        }
    }

    //当前正向無功电能示值
    private void insertData_P_REACT(String logicalAddress,int gpSn,String dataDate,
            String p_react_total,String p_react_sharp,String p_react_peak,String p_react_level,String p_react_valley)
    {
           try {
            this.p_reactStoredProcedure.execute(logicalAddress, gpSn, dataDate, p_react_total, p_react_sharp, p_react_peak, p_react_level, p_react_valley);
        } catch (Exception e) {
            log.error("错误信息：", e.fillInStackTrace());
        }
    }

    //当前反向有功电能示值
    private void insertData_I_ACT(String logicalAddress,int gpSn,String dataDate,
            String i_act_total,String i_act_sharp,String i_act_peak,String i_act_level,String i_act_valley)
    {
           try {
            this.i_actStoredProcedure.execute(logicalAddress, gpSn, dataDate, i_act_total, i_act_sharp, i_act_peak, i_act_level, i_act_valley);
        } catch (Exception e) {
            log.error("错误信息：", e.fillInStackTrace());
        }
    }

    //当前反向無功电能示值
    private void insertData_I_REACT(String logicalAddress,int gpSn,String dataDate,
            String i_react_total,String i_react_sharp,String i_react_peak,String i_react_level,String i_react_valley)
    {
           try {
            this.i_reactStoredProcedure.execute(logicalAddress, gpSn, dataDate, i_react_total, i_react_sharp, i_react_peak, i_react_level, i_react_valley);
        } catch (Exception e) {
            log.error("错误信息：", e.fillInStackTrace());
        }
    }

    //电压电流曲线
    private void insertData_EC_CURV(String logicalAddress,int gpSn,String dataDate,
            String ECUR_A,String ECUR_B,String ECUR_C,String ECUR_L,String ECUR_S,
            String VOLT_A,String VOLT_B,String VOLT_C)
    {

        try {
            this.eccurvStoredProcedure.execute(logicalAddress, gpSn, dataDate, ECUR_A, ECUR_B, ECUR_C, ECUR_L, ECUR_S, VOLT_A, VOLT_B, VOLT_C);
        } catch (Exception e) {
            log.error("错误信息：", e.fillInStackTrace());
        }
    }


     

    //功率曲线
    private void insert_POWER_CRUV(String logicalAddress,int gpSn,String dataDate,
            String act_power_total,String act_power_a,String act_power_b,
            String act_power_c,String react_power_total,String react_power_a,
            String react_power_b,String react_power_c)
    {
        try {
            this.getPowerCurvStoredProcedure().execute(logicalAddress,gpSn, dataDate,
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
     * @return the eccurvStoredProcedure
     */
    public ECCURV_StoredProcedure getEccurvStoredProcedure() {
        return eccurvStoredProcedure;
    }

    /**
     * @param eccurvStoredProcedure the eccurvStoredProcedure to set
     */
    public void setEccurvStoredProcedure(ECCURV_StoredProcedure eccurvStoredProcedure) {
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

    /**
     * @return the eccurvStoredProcedure2
     */
    public ECCURV_StoredProcedure2 getEccurvStoredProcedure2() {
        return eccurvStoredProcedure2;
    }

    /**
     * @param eccurvStoredProcedure2 the eccurvStoredProcedure2 to set
     */
    public void setEccurvStoredProcedure2(ECCURV_StoredProcedure2 eccurvStoredProcedure2) {
        this.eccurvStoredProcedure2 = eccurvStoredProcedure2;
    }

    /**
     * @return the powerCurvStoredProcedure2
     */
    public PowerCurv_StoredProcedure2 getPowerCurvStoredProcedure2() {
        return powerCurvStoredProcedure2;
    }

    /**
     * @param powerCurvStoredProcedure2 the powerCurvStoredProcedure2 to set
     */
    public void setPowerCurvStoredProcedure2(PowerCurv_StoredProcedure2 powerCurvStoredProcedure2) {
        this.powerCurvStoredProcedure2 = powerCurvStoredProcedure2;
    }

    /**
     * @return the powerCurvStoredProcedure
     */
    public PowerCurv_StoredProcedure getPowerCurvStoredProcedure() {
        return powerCurvStoredProcedure;
    }

    /**
     * @param powerCurvStoredProcedure the powerCurvStoredProcedure to set
     */
    public void setPowerCurvStoredProcedure(PowerCurv_StoredProcedure powerCurvStoredProcedure) {
        this.powerCurvStoredProcedure = powerCurvStoredProcedure;
    }

    /**
     * @return the p_actStoredProcedure
     */
    public P_ACT_StoredProcedure getP_actStoredProcedure() {
        return p_actStoredProcedure;
    }

    /**
     * @param p_actStoredProcedure the p_actStoredProcedure to set
     */
    public void setP_actStoredProcedure(P_ACT_StoredProcedure p_actStoredProcedure) {
        this.p_actStoredProcedure = p_actStoredProcedure;
    }

    /**
     * @return the p_reactStoredProcedure
     */
    public P_REACT_StoredProcedure getP_reactStoredProcedure() {
        return p_reactStoredProcedure;
    }

    /**
     * @param p_reactStoredProcedure the p_reactStoredProcedure to set
     */
    public void setP_reactStoredProcedure(P_REACT_StoredProcedure p_reactStoredProcedure) {
        this.p_reactStoredProcedure = p_reactStoredProcedure;
    }

    /**
     * @return the i_reactStoredProcedure
     */
    public I_REACT_StoredProcedure getI_reactStoredProcedure() {
        return i_reactStoredProcedure;
    }

    /**
     * @param i_reactStoredProcedure the i_reactStoredProcedure to set
     */
    public void setI_reactStoredProcedure(I_REACT_StoredProcedure i_reactStoredProcedure) {
        this.i_reactStoredProcedure = i_reactStoredProcedure;
    }

    /**
     * @return the i_actStoredProcedure
     */
    public I_ACT_StoredProcedure getI_actStoredProcedure() {
        return i_actStoredProcedure;
    }

    /**
     * @param i_actStoredProcedure the i_actStoredProcedure to set
     */
    public void setI_actStoredProcedure(I_ACT_StoredProcedure i_actStoredProcedure) {
        this.i_actStoredProcedure = i_actStoredProcedure;
    }

}
