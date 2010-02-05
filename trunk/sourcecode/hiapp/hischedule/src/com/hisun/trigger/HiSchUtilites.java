 package com.hisun.trigger;
 
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.exception.HiException;
 import com.hisun.parse.HiCfgFile;
 import com.hisun.util.HiResource;
 import java.io.File;
 import java.io.PrintStream;
 import java.net.URL;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import org.apache.commons.lang.StringUtils;
 
 public class HiSchUtilites
 {
   public static HiTimeTrigger loadTimeTrigger(String strFileName)
     throws HiException
   {
     URL fileUrl = HiResource.getResource(strFileName);
     URL ruleUrl = HiSchUtilites.class.getResource("TIME_RULES.XML");
     if (ruleUrl == null) {
       throw new HiException("212004", "Rule File:TIME_RULES.XML");
     }
 
     return ((HiTimeTrigger)HiCfgFile.getDefaultCfgFile(fileUrl, ruleUrl, "Data", "timetrigger").getRootInstance());
   }
 
   public static HiTimeTrigger loadTimeTriggerFormDB()
     throws HiException
   {
     HiTimeTrigger timeTrigger = new HiTimeTrigger();
 
     HiDataBaseUtil dbUtil = new HiDataBaseUtil();
     try {
       String sql = "select t.TASK_ID,TASK_NM,MAX_SQN,IS_TXN,STATUS,COMMAND,PATH,OBJ_SVR,TXN_CD,DATA_TYP,TRACE,DATA,MINUTE,HOUR,MON_DAY,WEEK_DAY,MON_WEEK,MONTH from pubschtsk t,pubschcnd c where t.TASK_ID = c.TASK_ID";
 
       System.out.println("sql :" + sql);
       List results = dbUtil.execQuery(sql);
 
       Iterator iter = results.iterator();
       while (iter.hasNext()) {
         Map result = (Map)iter.next();
         HiSchItem schItem = new HiSchItem();
 
         schItem.setId((String)result.get("TASK_ID"));
         schItem.setIstxn((String)result.get("IS_TXN"));
         schItem.setSndflag((String)result.get("STATUS"));
         schItem.setFuncname((String)result.get("COMMAND"));
         schItem.setFuncpath((String)result.get("PATH"));
         schItem.setTrace((String)result.get("TRACE"));
         schItem.setTxncod((String)result.get("TXN_CD"));
         schItem.setTxndat((String)result.get("DATA"));
         schItem.setTxndatatype((String)result.get("DATA_TYP"));
 
         schItem.setMinute((String)result.get("MINUTE"));
         schItem.setMonth((String)result.get("MONTH"));
         schItem.setMonthday((String)result.get("MON_DAY"));
         schItem.setMonthweek((String)result.get("MON_WEEK"));
         schItem.setHour((String)result.get("HOUR"));
         schItem.setWeekday((String)result.get("WEEK_DAY"));
 
         timeTrigger.addChilds(schItem);
       }
     } catch (HiException he) {
     }
     finally {
       dbUtil.close();
     }
     return timeTrigger;
   }
 
   public static void execFunction(String strFuncationPath, String strFuncationName)
   {
     String strCommand = "";
     if (StringUtils.isNotEmpty(strFuncationPath))
       strCommand = strFuncationPath + File.separator;
     strCommand = strCommand + strFuncationName;
     FuncThread fun = new FuncThread(strCommand);
     fun.start();
   }
 
   public static class FuncThread extends Thread
   {
     String strCommand;
 
     public FuncThread(String strCommand) {
       this.strCommand = strCommand;
     }
 
     public void run() {
       try {
         Process process = Runtime.getRuntime().exec(this.strCommand);
         process.waitFor();
       } catch (Exception e) {
         e.printStackTrace();
       }
     }
   }
 }