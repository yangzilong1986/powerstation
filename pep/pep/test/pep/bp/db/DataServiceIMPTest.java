/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.db;

import java.util.Map;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.*;
import pep.bp.model.Dto;
import pep.bp.model.Dto.DtoItem;
import pep.bp.utils.UtilsBp;

/**
 *
 * @author Thinkpad
 */
public class DataServiceIMPTest {
    private DataServiceIMP dataService;
    private ACTDataStoredProcedure actStoredProcedure;
    private ECCURV_DataStoredProcedure eccurv_DataStoredProcedure;
    public DataServiceIMPTest() {
        ApplicationContext app =    new  ClassPathXmlApplicationContext("beans.xml");
        dataService = (DataServiceIMP)app.getBean("dataService");
        actStoredProcedure = (ACTDataStoredProcedure)app.getBean("actDataStoredProcedure");
        eccurv_DataStoredProcedure = (ECCURV_DataStoredProcedure)app.getBean("eccurv_DataStoredProcedure");
        dataService.setActStoredProcedure(actStoredProcedure);
        dataService.setEccurvStoredProcedure(eccurv_DataStoredProcedure);
    }

    @Test
    public void testInsertRecvData() {
        Dto dto = new Dto("96123456",(byte)12);

        DtoItem dtoItem = dto.AddDataItem(5, UtilsBp.getNow(), "100C0025");
        Map<String,String> dataMap = dtoItem.dataMap;
        dataMap.put("F000", "20100723");
        dataMap.put("2300", "4");
        dataMap.put("2301", "200");
        dataMap.put("2302", "100");
        dataMap.put("2303", "");
        dataMap.put("2400", "20");
        dataMap.put("2401", "10");
        dataMap.put("2402", "30");
        dataMap.put("2403", "30");
        dataMap.put("2600", "30");
        dataMap.put("2601", "30");
        dataMap.put("2602", "30");
        dataMap.put("2603", "30");
        dataMap.put("2101", "30");
        dataMap.put("2102", "30");
        dataMap.put("2103", "30");
        dataMap.put("2201", "30");
        dataMap.put("2202", "30");
        dataMap.put("2203", "30");
        dataMap.put("2204", "30");
        dataMap.put("2500", "30");
        dataMap.put("2501", "30");
        dataMap.put("2502", "30");
        dataMap.put("2503", "30");
        dataService.insertRecvData(dto);
    }

    /**
     * @return the dataService
     */
    public DataService getDataService() {
        return dataService;
    }

    /**
     * @param dataService the dataService to set
     */
    public void setDataService(DataServiceIMP dataService) {
        this.dataService = dataService;
    }

}