 package com.hisun.atc;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.atc.common.HiDbtSqlHelper;
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import java.util.HashMap;
 import java.util.List;
 
 public class HiLoadData
 {
   public int QueryLoadData(HiATLParam args, HiMessageContext ctx)
   {
     return 0;
   }
 
   public int LoadData(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiContext ctx1;
     boolean escape = args.getBoolean("escape");
     String sqlCmd = HiArgUtils.getStringNotNull(args, "sqlCmd");
     String storeKey = HiArgUtils.getStringNotNull(args, "storeKey");
     String type = args.get("type");
     String scope = args.get("scope");
     String keyNam = args.get("keyNam");
     String valNam = args.get("valNam");
 
     HiMessage msg = ctx.getCurrentMsg();
     HiETF root = msg.getETFBody();
     Logger log = HiLog.getLogger(msg);
     String sqlSentence = HiDbtSqlHelper.getDynSentence(ctx, sqlCmd, root, escape);
 
     List queryRs = ctx.getDataBaseUtil().execQuery(sqlSentence);
 
     if ("GLOBAL".equalsIgnoreCase(scope))
       ctx1 = HiMessageContext.getRootContext();
     else {
       ctx1 = ctx.getServerContext();
     }
     if ("HashMap".equalsIgnoreCase(type)) {
       ctx1.setProperty(storeKey, listHashMap2HashMap(queryRs, keyNam, valNam));
     }
     else if ("ListHashMap".equalsIgnoreCase(type)) {
       ctx1.setProperty(storeKey, queryRs);
     }
     return 0;
   }
 
   private HashMap listHashMap2HashMap(List listHashMap, String keyNam, String valNam)
   {
     HashMap map = new HashMap();
     for (int i = 0; i < listHashMap.size(); ++i) {
       HashMap tmpMap = (HashMap)listHashMap.get(i);
       map.put(tmpMap.get(keyNam), tmpMap.get(valNam));
     }
     return map;
   }
 }