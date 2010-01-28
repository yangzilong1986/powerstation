 package com.hisun.mon.atc;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.atc.common.HiDbtSqlHelper;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.mon.HiDataBaseUtilExt;
 import java.util.Iterator;
 import java.util.Map;
 import java.util.Map.Entry;
 import java.util.Set;
 import org.apache.commons.lang.StringUtils;
 
 public class QueryOneRecord
 {
   public int execute(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     if (log.isDebugEnabled()) {
       log.debug("Start: QueryOneRecord");
     }
     if ((args == null) || (args.size() == 0)) {
       throw new HiException("215110");
     }
 
     String sqlCmd = HiArgUtils.getStringNotNull(args, "SqlCmd");
     HiETF etfBody = (HiETF)msg.getBody();
     boolean escape = args.getBoolean("escape");
     String sqlSentence = HiDbtSqlHelper.getDynSentence(ctx, sqlCmd, etfBody, escape);
 
     String varList = args.get("VarList");
 
     String varType = args.get("VarType");
 
     String[] varArr = (String[])null;
     String[] varTypeArr = (String[])null;
     if (StringUtils.isNotBlank(varList)) {
       varArr = StringUtils.split(varList, '|');
       varTypeArr = StringUtils.split(varType, '|');
     }
     if (log.isDebugEnabled()) {
       log.debug(varList);
       log.debug(sqlSentence);
     }
     Map result = HiDataBaseUtilExt.readRecord(ctx, sqlSentence, varArr, varTypeArr);
 
     if ((result == null) || (result.size() == 0)) {
       return 2;
     }
     Map.Entry recEntry = null;
     Iterator recIt = result.entrySet().iterator();
 
     while (recIt.hasNext()) {
       recEntry = (Map.Entry)recIt.next();
       etfBody.setChildValue((String)recEntry.getKey(), 
         (String)recEntry.getValue());
     }
 
     return 0;
   }
 }