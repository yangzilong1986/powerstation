 package com.hisun.mon.atc;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFFactory;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import org.apache.commons.lang.StringUtils;
 
 public class SetETFOutList
 {
   public int execute(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
 
     if (log.isDebugEnabled()) {
       log.debug("SetETFOutList start");
     }
 
     String list = HiArgUtils.getStringNotNull(args, "list");
     String[] nodes = StringUtils.split(list, "|");
 
     HiETF etf = msg.getETFBody();
     HiETF newEtf = HiETFFactory.createETF();
     for (int i = 0; i < nodes.length; ++i) {
       newEtf.setGrandChildNode(nodes[i], etf.getGrandChildValue(nodes[i]));
     }
 
     msg.setHeadItem("ETFOUTLIST", newEtf);
     if (log.isDebugEnabled()) {
       log.debug("SetETFOutList end");
     }
     return 0;
   }
 }