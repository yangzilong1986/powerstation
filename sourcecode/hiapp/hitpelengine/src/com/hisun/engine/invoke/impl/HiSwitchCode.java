 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.engine.invoke.load.HiTableInfo;
 import com.hisun.exception.HiException;
 import com.hisun.hiexpression.HiExpFactory;
 import com.hisun.hiexpression.HiExpression;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 import org.apache.commons.lang.StringUtils;
 
 public class HiSwitchCode extends HiEngineModel
 {
   private String strName;
   private String strNew_Name;
   private String strTab_Name;
   private HiExpression tab_NameExpr;
   private String strCol_Name;
   private String strCCol_Name;
 
   public String getNodeName()
   {
     return "SwitchCode ";
   }
 
   public String toString()
   {
     return super.toString() + ":name[" + this.strName + "]";
   }
 
   public void setName(String strName)
   {
     this.strName = strName;
   }
 
   public void setNew_name(String strNewName) {
     this.strNew_Name = strNewName;
   }
 
   public void setTab_name(String strTableName) {
     this.strTab_Name = strTableName;
     this.tab_NameExpr = HiExpFactory.createExp(this.strTab_Name);
   }
 
   public void setCol_name(String strColName) {
     this.strCol_Name = strColName;
   }
 
   public void setCcol_name(String strCColName) {
     this.strCCol_Name = strCColName;
   }
 
   public void process(HiMessageContext messContext)
     throws HiException
   {
     HiMessage mess = messContext.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
 
     HiETF etf = (HiETF)mess.getBody();
 
     String strOldValue = HiItemHelper.getEtfItem(mess, this.strName);
     String tabName = this.tab_NameExpr.getValue(messContext);
     if (log.isDebugEnabled()) {
       log.debug("SwitchCode TabName:[" + tabName + "]");
     }
 
     HiTableInfo tableInfo = getTableInfo(messContext, tabName);
     if (tableInfo == null) {
       throw new HiException("241046", tabName);
     }
 
     String strNewValue = null;
     if (!(StringUtils.isEmpty(this.strCCol_Name))) {
       strNewValue = tableInfo.getColValue(this.strCol_Name, strOldValue, this.strCCol_Name);
     }
     else {
       strNewValue = tableInfo.getColValue(this.strCol_Name, strOldValue);
     }
 
     if (StringUtils.isEmpty(this.strNew_Name))
       this.strNew_Name = this.strName;
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiSwitchCode.process00", HiEngineUtilities.getCurFlowStep(), this.strName, strOldValue, this.strNew_Name, strNewValue));
     }
 
     if (strNewValue == null) {
       throw new HiException("241044", tabName, this.strName, this.strCol_Name);
     }
     HiItemHelper.addEtfItem(mess, this.strNew_Name, strNewValue);
   }
 
   public HiTableInfo getTableInfo(HiMessageContext messContext, String tabName)
     throws HiException
   {
     HiTableInfo tableInfo = (HiTableInfo)messContext.getProperty("CODESWITCHING", tabName);
 
     return tableInfo;
   }
 
   public void loadAfter()
     throws HiException
   {
   }
 }