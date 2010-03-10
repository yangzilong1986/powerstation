package peis.interfaces.hicollect.datacell;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import peis.common.DataConverter;
import peis.interfaces.hicollect.CommandItem;

/**
 * 
 * @author dingzj
 * @version 1.0
 * Create Date : 20091023
 */
public class TaskParamGridZJ implements SendDataCell {
    private static Logger logger = Logger.getLogger(TaskParamGridZJ.class);
    private static final String COMMON_TASK 	= "01"; 			//普通任务
    private static final String RELAY_TASK 		= "02"; 			//中继任务
    private static final String EXCEPTION_TASK 	= "04"; 			//异常任务
    
    private static final String COMMANDITEM_HEADER = "200081";      //命令项标识头，需要和taskId合成命令项标识
    private static final String DATAITEM_01 = "DI_2000810101";      //任务类型(01:普通任务; 02:中继任务; 04:异常任务)
    private static final String DATAITEM_02 = "DI_F00C";       		//采样基准时间
    private static final String DATAITEM_03 = "DI_F00D";       		//采样基准时间单位
    private static final String DATAITEM_04 = "DI_F00E";       		//采样间隔时间
    private static final String DATAITEM_05 = "DI_F00F";       		//采样间隔时间单位
    private static final String DATAITEM_06 = "DI_F015";   			//上送基准时间
    private static final String DATAITEM_07 = "DI_F016";   			//上送基准时间单位
    private static final String DATAITEM_08 = "DI_F00A";   			//上送间隔时间
    private static final String DATAITEM_09 = "DI_F00B";   			//上送间隔时间单位
    private static final String DATAITEM_10 = "DI_F017";   			//上送数据频率
    private static final String DATAITEM_11 = "DI_F018";   			//测量点序号
    private static final String DATAITEM_12 = "DI_F019";   			//任务保存点数
    private static final String DATAITEM_13 = "DI_F01A";   			//任务执行次数
    private static final String DATAITEM_14 = "DI_F01B";   			//数据项个数
    private static final String DATAITEM_15 = "DI_F01C";   			//数据项
    
    
    private int taskId;                         //任务号
    private String taskType;				    //任务类型(01:普通任务; 02:中继任务; 04:异常任务)
    private int collectBaseTime;                //采样基准时间
    private int collectBaseTimeUnit;      	 	//采样基准时间单位
    private int collectIntervalTime;      	 	//采样间隔时间
    private int collectIntervalTimeUnit;      	//采样间隔时间单位
    private int sendupBaseTime;      			//上送基准时间
    private int sendupBaseTimeUnit;      		//上送基准时间单位
    private int sendupIntervalTime;      		//上送间隔时间
    private int sendupIntervalTimeUnit;      	//上送间隔时间单位
    private int sendupDataFreq;      			//上送数据频率
    private int gpSn;      						//测量点序号
    private int taskSavePoint;      			//任务保存点数
    private int taskExecTimes;      			//任务执行次数
    private int execTask;		      			//执行任务
    private String[] commanditems;              //命令项标识
    
    public TaskParamGridZJ() {
        
    }

    /**
	 * @see peis.interfaces.hicollect.datacell.SendDataCell#renderCommandItem()
	 */
    public CommandItem renderCommandItem() {
        CommandItem commandItem = new CommandItem();
        logger.info("---------------------------------------------------");
        if(COMMON_TASK.equals(this.taskType)){ //普通任务
        	String taskIdHex = DataConverter.int2Hex(this.taskId, 1);
            commandItem.setIdentifier(COMMANDITEM_HEADER + taskIdHex);
            logger.info("set commanditem code : " + COMMANDITEM_HEADER + taskIdHex);
            Map datacellParam = new HashMap();
            datacellParam.put(DATAITEM_01, COMMON_TASK);
            logger.info("  set dataitem code : " + DATAITEM_01 + " - " + COMMON_TASK);
            datacellParam.put(DATAITEM_02, "" + collectBaseTime);
            logger.info("  set dataitem code : " + DATAITEM_02 + " - " + collectBaseTime);
            datacellParam.put(DATAITEM_03, "" + getTimeUnitByCode(collectBaseTimeUnit));
            logger.info("  set dataitem code : " + DATAITEM_03 + " - " + getTimeUnitByCode(collectBaseTimeUnit));
            datacellParam.put(DATAITEM_04, "" + collectIntervalTime);
            logger.info("  set dataitem code : " + DATAITEM_04 + " - " + collectIntervalTime);
            datacellParam.put(DATAITEM_05, "" + getTimeUnitByCode(collectIntervalTimeUnit));
            logger.info("  set dataitem code : " + DATAITEM_05 + " - " + getTimeUnitByCode(collectIntervalTimeUnit));
            datacellParam.put(DATAITEM_06, "" + sendupBaseTime);
            logger.info("  set dataitem code : " + DATAITEM_06 + " - " + sendupBaseTime);
            datacellParam.put(DATAITEM_07, "" + getTimeUnitByCode(sendupBaseTimeUnit));
            logger.info("  set dataitem code : " + DATAITEM_07 + " - " + getTimeUnitByCode(sendupBaseTimeUnit));
            datacellParam.put(DATAITEM_08, "" + sendupIntervalTime);
            logger.info("  set dataitem code : " + DATAITEM_08 + " - " + sendupIntervalTime);
            datacellParam.put(DATAITEM_09, "" + getTimeUnitByCode(sendupIntervalTimeUnit));
            logger.info("  set dataitem code : " + DATAITEM_09 + " - " + getTimeUnitByCode(sendupIntervalTimeUnit));
            datacellParam.put(DATAITEM_10, "" + sendupDataFreq);
            logger.info("  set dataitem code : " + DATAITEM_10 + " - " + sendupDataFreq);
            datacellParam.put(DATAITEM_11, "" + gpSn);
            logger.info("  set dataitem code : " + DATAITEM_11 + " - " + gpSn);
            datacellParam.put(DATAITEM_12, "" + taskSavePoint);
            logger.info("  set dataitem code : " + DATAITEM_12 + " - " + taskSavePoint);
            datacellParam.put(DATAITEM_13, "" + taskExecTimes);
            logger.info("  set dataitem code : " + DATAITEM_13 + " - " + taskExecTimes);
            int dataItemNum = commanditems != null ? commanditems.length : 0;
            datacellParam.put(DATAITEM_14, DataConverter.zerofillTops("" + dataItemNum, 2));
            logger.info("  set dataitem code : " + DATAITEM_14 + " - " + DataConverter.zerofillTops("" + dataItemNum, 2));
            String dataItemList = getDataItemList(commanditems);
            datacellParam.put(DATAITEM_15, dataItemList);
            logger.info("  set dataitem code : " + DATAITEM_15 + " - " + dataItemList);
            commandItem.setDatacellParam(datacellParam);
        }
        else if(RELAY_TASK.equals(this.taskType)){ //中继任务
        	//待扩展
        }
        else if(EXCEPTION_TASK.equals(this.taskType)){ //异常任务
        	//待扩展
        }
        return commandItem;
    }

    /**
     * 获取命令项数组中每个元素的后4位组合成的字符串
     * 
     * @param commanditems ：命令项数组
     * @return
     */
    private String getDataItemList(String[] commanditems){
    	String dataItemList = "";
    	if(commanditems == null || commanditems.length <= 0){
    		return dataItemList;
    	}
    	String cmdItem;
    	for(int i = 0; i < commanditems.length; i++){
    		cmdItem = commanditems[i];
    		if(cmdItem != null && cmdItem.length() > 4){//取命令项后4位组合
        		dataItemList += commanditems[i].substring((commanditems[i].length() - 4), commanditems[i].length());
    		}
    	}
    	return dataItemList;
    }
    
    /**
     * 时间单位代号转换
     * 
     * @param code ：代号
     * @return
     */
    private String getTimeUnitByCode(int code){
    	String unit = "04";
    	//code为 0：分 1：时 2：日 3：月
    	//下发时间单位(02：分 03：时 04：日 05：月)
    	if(code == 0){ 
    		unit = "02";
    	}
    	else if(code == 1){ 
    		unit = "03";
    	}
    	else if(code == 2){ 
    		unit = "04";
    	}
    	else if(code == 3){ 
    		unit = "05";
    	}
    	return unit;
    }

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public int getCollectBaseTime() {
		return collectBaseTime;
	}

	public void setCollectBaseTime(int collectBaseTime) {
		this.collectBaseTime = collectBaseTime;
	}

	public int getCollectBaseTimeUnit() {
		return collectBaseTimeUnit;
	}

	public void setCollectBaseTimeUnit(int collectBaseTimeUnit) {
		this.collectBaseTimeUnit = collectBaseTimeUnit;
	}

	public int getCollectIntervalTime() {
		return collectIntervalTime;
	}

	public void setCollectIntervalTime(int collectIntervalTime) {
		this.collectIntervalTime = collectIntervalTime;
	}

	public int getCollectIntervalTimeUnit() {
		return collectIntervalTimeUnit;
	}

	public void setCollectIntervalTimeUnit(int collectIntervalTimeUnit) {
		this.collectIntervalTimeUnit = collectIntervalTimeUnit;
	}

	public int getSendupBaseTime() {
		return sendupBaseTime;
	}

	public void setSendupBaseTime(int sendupBaseTime) {
		this.sendupBaseTime = sendupBaseTime;
	}

	public int getSendupBaseTimeUnit() {
		return sendupBaseTimeUnit;
	}

	public void setSendupBaseTimeUnit(int sendupBaseTimeUnit) {
		this.sendupBaseTimeUnit = sendupBaseTimeUnit;
	}

	public int getSendupIntervalTime() {
		return sendupIntervalTime;
	}

	public void setSendupIntervalTime(int sendupIntervalTime) {
		this.sendupIntervalTime = sendupIntervalTime;
	}

	public int getSendupIntervalTimeUnit() {
		return sendupIntervalTimeUnit;
	}

	public void setSendupIntervalTimeUnit(int sendupIntervalTimeUnit) {
		this.sendupIntervalTimeUnit = sendupIntervalTimeUnit;
	}

	public int getSendupDataFreq() {
		return sendupDataFreq;
	}

	public void setSendupDataFreq(int sendupDataFreq) {
		this.sendupDataFreq = sendupDataFreq;
	}

	public int getTaskSavePoint() {
		return taskSavePoint;
	}

	public void setTaskSavePoint(int taskSavePoint) {
		this.taskSavePoint = taskSavePoint;
	}

	public int getTaskExecTimes() {
		return taskExecTimes;
	}

	public void setTaskExecTimes(int taskExecTimes) {
		this.taskExecTimes = taskExecTimes;
	}

	public int getExecTask() {
		return execTask;
	}

	public void setExecTask(int execTask) {
		this.execTask = execTask;
	}

	public String[] getCommanditems() {
		return commanditems;
	}

	public void setCommanditems(String[] commanditems) {
		this.commanditems = commanditems;
	}

	public int getGpSn() {
		return gpSn;
	}

	public void setGpSn(int gpSn) {
		this.gpSn = gpSn;
	}
    
}
