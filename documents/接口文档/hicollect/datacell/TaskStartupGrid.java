package peis.interfaces.hicollect.datacell;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import peis.interfaces.hicollect.CommandItem;

/**
 * 
 * @author Zhangyu
 * @version 1.0
 * Create Date : 20090707
 */
public class TaskStartupGrid implements SendDataCell {
    private static Logger logger = Logger.getLogger(TaskStartupGrid.class);
    
    private static final String COMMANDITEM_DSONE = "10040067";     //定时发送1类数据任务启动/停止设置命令项标识
    private static final String DATAITEM_DSONE_01 = "DI_1004006701";   //启动/停止标志
    private static final String COMMANDITEM_DSTWO = "10040068";     //定时发送2类数据任务启动/停止设置命令项标识
    private static final String DATAITEM_DSTWO_01 = "DI_1004006801";   //启动/停止标志
    
    private int dataSort;                       //数据分类 : 1 - 1类数据 2 - 2类数据
    private boolean startup;                    //启动标志 : true - 启动; false - 停止
    
    public TaskStartupGrid() {
        
    }
    
    public CommandItem renderCommandItem() throws Exception {
        CommandItem commandItem = new CommandItem();
        logger.info("---------------------------------------------------");
        logger.info("dataSort : " + dataSort);
        logger.info("startup : " + startup);
        String dataCellContent = null;
        if(this.startup) {
            dataCellContent = "55";
        }
        else {
            dataCellContent = "AA";
        }
        
        if(dataSort == 1) {
            commandItem.setIdentifier(COMMANDITEM_DSONE);
            logger.info("set commanditem code : " + COMMANDITEM_DSONE);
            Map datacellParam = new HashMap();
            datacellParam.put(DATAITEM_DSONE_01, dataCellContent);
            logger.info("  set dataitem code : " + DATAITEM_DSONE_01 + " - " + dataCellContent);
            commandItem.setDatacellParam(datacellParam);
        }
        else if(dataSort == 2) {
            commandItem.setIdentifier(COMMANDITEM_DSTWO);
            logger.info("set commanditem code : " + COMMANDITEM_DSTWO);
            Map datacellParam = new HashMap();
            datacellParam.put(DATAITEM_DSTWO_01, dataCellContent);
            logger.info("  set dataitem code : " + DATAITEM_DSTWO_01 + " - " + dataCellContent);
            commandItem.setDatacellParam(datacellParam);
        }
        logger.info("---------------------------------------------------");
        
        return commandItem;
    }

    /**
     * @return the dataSort
     */
    public int getDataSort() {
        return dataSort;
    }

    /**
     * @param dataSort the dataSort to set
     */
    public void setDataSort(int dataSort) {
        this.dataSort = dataSort;
    }

    /**
     * @return the startup
     */
    public boolean isStartup() {
        return startup;
    }

    /**
     * @param startup the startup to set
     */
    public void setStartup(boolean startup) {
        this.startup = startup;
    }
}
