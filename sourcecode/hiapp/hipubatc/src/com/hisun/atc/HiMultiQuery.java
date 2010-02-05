 package com.hisun.atc;
 
 import com.hisun.atc.common.HiMultiQueryLib;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 import org.apache.commons.lang.StringUtils;
 
 public class HiMultiQuery
 {
   private static HiStringManager sm = HiStringManager.getManager();
 
   public int MultiQuery(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     if (log.isInfoEnabled())
     {
       log.info(sm.getString("HiMultiQuery.MultiQuery", "MultiQuery"));
     }
     String strTiaTyp = msg.getETFBody().getChildValue("TIA_TYP");
     if (log.isInfoEnabled())
     {
       log.info(sm.getString("HiMultiQuery.MultiQuery.Type", strTiaTyp));
     }
 
     if (!(StringUtils.equalsIgnoreCase(strTiaTyp, "P"))) {
       return HiMultiQueryLib.queryFirst(args, msg, ctx);
     }
     return HiMultiQueryLib.queryNext(args, msg, ctx);
   }
 
   public int QueryExt(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     if (log.isInfoEnabled())
     {
       log.info(sm.getString("HiMultiQuery.MultiQuery", "QueryExt"));
     }
     String strTiaTyp = msg.getETFBody().getChildValue("TIA_TYP");
     if (log.isInfoEnabled())
     {
       log.info(sm.getString("HiMultiQuery.MultiQuery.Type", strTiaTyp));
     }
 
     if (!(StringUtils.equalsIgnoreCase(strTiaTyp, "P"))) {
       return HiMultiQueryLib.QueryFirstExt(args, msg, ctx);
     }
     return HiMultiQueryLib.queryNext(args, msg, ctx);
   }
 
   public int MultiQueryFromFile(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     if (log.isInfoEnabled())
     {
       log.info(sm.getString("HiMultiQuery.MultiQuery", "MultiQuery"));
     }
     String strTiaTyp = msg.getETFBody().getChildValue("TIA_TYP");
     if (log.isInfoEnabled())
     {
       log.info(sm.getString("HiMultiQuery.MultiQuery.Type", strTiaTyp));
     }
 
     if (!(StringUtils.equalsIgnoreCase(strTiaTyp, "P"))) {
       return HiMultiQueryLib.MultiQueryFromFile(args, ctx);
     }
     return HiMultiQueryLib.queryNext(args, msg, ctx);
   }
 }