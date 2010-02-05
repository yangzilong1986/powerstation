 package com.hisun.mon.atc;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiMessageContext;
 import java.util.HashMap;
 import java.util.Map;
 import org.apache.commons.lang.StringUtils;
 
 public class SetLocalValue
 {
   private static final String LOCAL_DATA_INDEX = "_LOCAL_DATA";
 
   public int execute(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     String index = HiArgUtils.getStringNotNull(args, "index");
 
     String[] keyName = StringUtils.split(HiArgUtils.getStringNotNull(args, "keyName"), "|");
     String[] keyValue = StringUtils.split(HiArgUtils.getStringNotNull(args, "keyValue"), "|");
 
     Map dataMap = getCurrentDataMap(ctx, index);
     for (int i = 0; i < keyValue.length; ++i) {
       log.info(keyName[i] + ":" + keyValue[i]);
       dataMap.put(keyName[i], keyValue[i]);
     }
     return 0;
   }
 
   private Map getCurrentDataMap(HiMessageContext ctx, String index) {
     Map localMap = (HashMap)ctx.getProperty("_LOCAL_DATA");
     if (localMap == null) {
       localMap = new HashMap();
       HiMessageContext.getRootContext().setProperty("_LOCAL_DATA", localMap);
     }
     Map curMap = (HashMap)localMap.get(index);
     if (curMap == null) {
       curMap = new HashMap();
       localMap.put(index, curMap);
     }
 
     return curMap;
   }
 }