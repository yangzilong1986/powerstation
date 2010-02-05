 package com.hisun.sqn;
 
 import com.hisun.dispatcher.HiRouterOut;
 import com.hisun.exception.HiException;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import org.apache.commons.lang.StringUtils;
 
 public class HiSqnMng
 {
   private static final String MSGTYPE = "SQNMNG";
 
   public static void updActDat(String oldDat, String newDat)
     throws HiException
   {
     HiMessage msg = new HiMessage("S.SQNSVR", "SQNMNG");
     msg.setType("SQNMNG");
     msg.setHeadItem("SDT", "S.SQNSVR");
     msg.setHeadItem("CMD", "UPDDAT");
     msg.setHeadItem("OLD", oldDat);
     msg.setHeadItem("NEW", newDat);
     msg.setHeadItem("SCH", "rq");
     msg = HiRouterOut.innerSyncProcess(msg);
     if (!(StringUtils.isEmpty(msg.getHeadItem("SSC"))))
       throw new HiException(msg.getHeadItem("SSC"));
   }
 
   public static String getDumTlr(String brNo, String aplCod, String aplSub)
     throws HiException
   {
     HiMessage msg = new HiMessage("S.SQNSVR", "SQNMNG");
     msg.setType("SQNMNG");
     msg.setHeadItem("SDT", "S.SQNSVR");
     msg.setHeadItem("CMD", "GETTLR");
     msg.setHeadItem("BRNO", brNo);
     msg.setHeadItem("APP", aplCod);
     if (!(StringUtils.isEmpty(aplSub))) {
       msg.setHeadItem("SUB", aplSub);
     }
     msg.setHeadItem("SCH", "rq");
     msg = HiRouterOut.innerSyncProcess(msg);
     if (!(StringUtils.isEmpty(msg.getHeadItem("SSC")))) {
       throw new HiException(msg.getHeadItem("SSC"));
     }
     return msg.getHeadItem("TLR");
   }
 
   public static String getLogNo(int num)
     throws HiException
   {
     HiMessage msg = new HiMessage("S.SQNSVR", "SQNMNG");
     msg.setHeadItem("SDT", "S.SQNSVR");
     msg.setHeadItem("CMD", "GETTRC");
     msg.setHeadItem("NUM", String.valueOf(num));
     msg.setHeadItem("SCH", "rq");
     msg = HiRouterOut.innerSyncProcess(msg);
     if (!(StringUtils.isEmpty(msg.getHeadItem("SSC")))) {
       throw new HiException(msg.getHeadItem("SSC"));
     }
     return msg.getHeadItem("LSH");
   }
 
   public static String getLogNo(HiMessage oriMsg, int num)
     throws HiException
   {
     HiMessage newMsg = new HiMessage("S.SQNSVR", "SQNMNG");
     newMsg.setHeadItem("SDT", "S.SQNSVR");
     newMsg.setHeadItem("CMD", "GETTRC");
     newMsg.setHeadItem("NUM", String.valueOf(num));
     newMsg.setHeadItem("SCH", "rq");
     newMsg = HiRouterOut.innerSyncProcess(newMsg);
     if (oriMsg.getBody() instanceof HiETF) {
       HiETF root = oriMsg.getETFBody();
       String accDt = root.getChildValue("ACC_DT");
 
       String actDt = root.getChildValue("ACT_DT");
       if (!(StringUtils.isEmpty(accDt)))
         newMsg.setHeadItem("ACC_DT", accDt);
       else if (!(StringUtils.isEmpty(actDt))) {
         newMsg.setHeadItem("ACC_DT", actDt);
       }
     }
 
     if (!(StringUtils.isEmpty(newMsg.getHeadItem("SSC")))) {
       throw new HiException(newMsg.getHeadItem("SSC"));
     }
     return newMsg.getHeadItem("LSH");
   }
 }