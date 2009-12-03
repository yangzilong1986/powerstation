/*     */ package com.hisun.trigger;
/*     */ 
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.parse.HiCfgFile;
/*     */ import com.hisun.util.HiResource;
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URL;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiSchUtilites
/*     */ {
/*     */   public static HiTimeTrigger loadTimeTrigger(String strFileName)
/*     */     throws HiException
/*     */   {
/*  20 */     URL fileUrl = HiResource.getResource(strFileName);
/*  21 */     URL ruleUrl = HiSchUtilites.class.getResource("TIME_RULES.XML");
/*  22 */     if (ruleUrl == null) {
/*  23 */       throw new HiException("212004", "Rule File:TIME_RULES.XML");
/*     */     }
/*     */ 
/*  26 */     return ((HiTimeTrigger)HiCfgFile.getDefaultCfgFile(fileUrl, ruleUrl, "Data", "timetrigger").getRootInstance());
/*     */   }
/*     */ 
/*     */   public static HiTimeTrigger loadTimeTriggerFormDB()
/*     */     throws HiException
/*     */   {
/*  38 */     HiTimeTrigger timeTrigger = new HiTimeTrigger();
/*     */ 
/*  40 */     HiDataBaseUtil dbUtil = new HiDataBaseUtil();
/*     */     try {
/*  42 */       String sql = "select t.TASK_ID,TASK_NM,MAX_SQN,IS_TXN,STATUS,COMMAND,PATH,OBJ_SVR,TXN_CD,DATA_TYP,TRACE,DATA,MINUTE,HOUR,MON_DAY,WEEK_DAY,MON_WEEK,MONTH from pubschtsk t,pubschcnd c where t.TASK_ID = c.TASK_ID";
/*     */ 
/*  45 */       System.out.println("sql :" + sql);
/*  46 */       List results = dbUtil.execQuery(sql);
/*     */ 
/*  48 */       Iterator iter = results.iterator();
/*  49 */       while (iter.hasNext()) {
/*  50 */         Map result = (Map)iter.next();
/*  51 */         HiSchItem schItem = new HiSchItem();
/*     */ 
/*  53 */         schItem.setId((String)result.get("TASK_ID"));
/*  54 */         schItem.setIstxn((String)result.get("IS_TXN"));
/*  55 */         schItem.setSndflag((String)result.get("STATUS"));
/*  56 */         schItem.setFuncname((String)result.get("COMMAND"));
/*  57 */         schItem.setFuncpath((String)result.get("PATH"));
/*  58 */         schItem.setTrace((String)result.get("TRACE"));
/*  59 */         schItem.setTxncod((String)result.get("TXN_CD"));
/*  60 */         schItem.setTxndat((String)result.get("DATA"));
/*  61 */         schItem.setTxndatatype((String)result.get("DATA_TYP"));
/*     */ 
/*  63 */         schItem.setMinute((String)result.get("MINUTE"));
/*  64 */         schItem.setMonth((String)result.get("MONTH"));
/*  65 */         schItem.setMonthday((String)result.get("MON_DAY"));
/*  66 */         schItem.setMonthweek((String)result.get("MON_WEEK"));
/*  67 */         schItem.setHour((String)result.get("HOUR"));
/*  68 */         schItem.setWeekday((String)result.get("WEEK_DAY"));
/*     */ 
/*  70 */         timeTrigger.addChilds(schItem);
/*     */       }
/*     */     } catch (HiException he) {
/*     */     }
/*     */     finally {
/*  75 */       dbUtil.close();
/*     */     }
/*  77 */     return timeTrigger;
/*     */   }
/*     */ 
/*     */   public static void execFunction(String strFuncationPath, String strFuncationName)
/*     */   {
/*  82 */     String strCommand = "";
/*  83 */     if (StringUtils.isNotEmpty(strFuncationPath))
/*  84 */       strCommand = strFuncationPath + File.separator;
/*  85 */     strCommand = strCommand + strFuncationName;
/*  86 */     FuncThread fun = new FuncThread(strCommand);
/*  87 */     fun.start();
/*     */   }
/*     */ 
/*     */   public static class FuncThread extends Thread
/*     */   {
/*     */     String strCommand;
/*     */ 
/*     */     public FuncThread(String strCommand) {
/*  95 */       this.strCommand = strCommand;
/*     */     }
/*     */ 
/*     */     public void run() {
/*     */       try {
/* 100 */         Process process = Runtime.getRuntime().exec(this.strCommand);
/* 101 */         process.waitFor();
/*     */       } catch (Exception e) {
/* 103 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ }