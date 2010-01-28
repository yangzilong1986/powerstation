 package com.hisun.ccb.atc;
 
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.exception.HiAppException;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 
 public class FunctionMsg
 {
   public int addMsg(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String appMmo = argsMap.get("AP_MMO");
     String msgCode = argsMap.get("MSG_CD");
     String msgInfo = argsMap.get("MSG_INF");
     String errorFieldName = argsMap.get("ERR_FLD");
     String auth_level1 = argsMap.get("ATH_LVL1");
     String auth_level2 = argsMap.get("ATH_LVL2");
     String values = argsMap.get("VALUES");
 
     HiMessage mess = ctx.getCurrentMsg();
     HiETF etfRoot = (HiETF)mess.getBody();
 
     MsgAuth msgAuth = null;
     MsgError msgError = null;
     MsgNormal msgNormal = null;
     MsgSet msgSet = null;
     Object obj = ctx.getBaseSource("_msgSet");
     if (obj != null)
     {
       msgSet = (MsgSet)obj;
     }
     else {
       msgSet = new MsgSet();
       ctx.setBaseSource("_msgSet", msgSet);
     }
     if ((appMmo == null) || (appMmo.equals("")))
     {
       setResult(ctx, "E", "6014");
       throw new HiException("220207", "AP_MMO");
     }
     if ((msgCode == null) || (msgCode.equals("")))
     {
       setResult(ctx, "E", "6014");
       throw new HiException("220207", "MSG_CD");
     }
 
     if ((((auth_level1 == null) || (auth_level1.equals("")) || (auth_level1.equals("0")))) && (auth_level2 != null) && (!(auth_level2.equals(""))) && (!(auth_level2.equals("0"))))
     {
       setResult(ctx, "E", "6014");
       throw new HiException("213309");
     }
 
     if ((auth_level1 != null) && (!(auth_level1.equals(""))))
     {
       msgAuth = new MsgAuth(appMmo, msgCode, msgInfo);
       try
       {
         msgAuth.setAuth_level1(Integer.parseInt(auth_level1));
         if ((auth_level2 != null) && (!(auth_level2.equals(""))));
         return 0;
       }
       catch (NumberFormatException e)
       {
       }
       finally
       {
         setResult(ctx, "A", msgCode);
         msgSet.addMsg(msgAuth);
         return 0; }
     }
     if ((errorFieldName != null) && (!(errorFieldName.equals(""))))
     {
       msgError = new MsgError(appMmo, msgCode, msgInfo);
       msgError.setErrorFieldName(errorFieldName);
       setResult(ctx, "E", msgCode);
       msgSet.addMsg(msgError);
       return 0;
     }
 
     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
     String sql = "select MSG_TYP,MSG_INF,ATH_LVL1,ATH_LVL2,MSG_INF from PUBTMSG where AP_MMO='" + appMmo + "' and " + "MSG_CD" + "='" + msgCode + "'";
 
     List list = dbUtil.execQuery(sql);
     if ((list == null) || (list.size() == 0))
     {
       dbUtil.close();
       setResult(ctx, "E", "6014");
       throw new HiAppException(-1, "record not found");
     }
 
     dbUtil.close();
     Map map = (Map)list.iterator().next();
     auth_level1 = (String)map.get("ATH_LVL1");
     auth_level2 = (String)map.get("ATH_LVL2");
     String msgType = (String)map.get("MSG_TYP");
 
     if ((msgInfo == null) || (msgInfo.equals("")))
       msgInfo = (String)map.get("MSG_INF");
     if ((msgInfo == null) || (msgInfo.equals(""))) {
       msgInfo = "message info was not defined in database 'PUBTMSG'";
     }
     if ((auth_level1 != null) && (!(auth_level1.equals(""))) && (!(auth_level1.equals("0"))))
     {
       msgAuth = new MsgAuth(appMmo, msgCode, msgInfo);
       try
       {
         msgAuth.setAuth_level1(Integer.parseInt(auth_level1));
         if ((auth_level2 != null) && (!(auth_level2.equals(""))));
         return 0;
       }
       catch (NumberFormatException e)
       {
       }
       finally
       {
         msgSet.addMsg(msgAuth);
 
         setResult(ctx, "A", msgCode);
         return (((msgInfo == null) || (msgInfo.equals(""))) ? 1 : 0);
       }
     }
     if ((msgType == null) || ((!(msgType.equals("E"))) && (!(msgType.equals("N")))))
     {
       etfRoot.setChildValue("MSG_TYP", "E");
       msgError = new MsgError(appMmo, msgCode, msgInfo);
       msgSet.addMsg(msgError);
       return -1;
     }
 
     etfRoot.setChildValue("MSG_TYP", msgType);
     etfRoot.setChildValue("MSG_CD", msgCode);
 
     if (msgType.equals("N"))
     {
       return 0;
     }
     msgError = new MsgError(appMmo, msgCode, msgInfo);
 
     setResult(ctx, "E", msgCode);
     msgSet.addMsg(msgError);
     return 0;
   }
 
   public int outputErrorMsg(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     HiETF etfRoot = (HiETF)mess.getBody();
     String msgType = etfRoot.getChildValue("MSG_TYP");
     MsgSet msgSet = null;
     if (msgType.equalsIgnoreCase("E"))
     {
       Object obj = ctx.getBaseSource("_msgSet");
       if (obj != null)
       {
         msgSet = (MsgSet)obj;
 
         etfRoot.setChildValue("OUTP_DAT", msgSet.getMsgError());
       }
     }
 
     return 0;
   }
 
   private void setResult(HiMessageContext ctx, String msgType, String msgCode)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     HiETF etfRoot = (HiETF)mess.getBody();
 
     etfRoot.setChildValue("MSG_TYP", msgType);
     if (msgCode != null) {
       etfRoot.setChildValue("MSG_CD", msgCode);
     }
     HiETF etfGWA = (HiETF)ctx.getBaseSource("GWA");
     if (etfGWA == null)
       return;
     etfGWA.setChildValue("MSG_TYP", msgType);
     if (msgCode != null)
       etfGWA.setChildValue("MSG_CD", msgCode);
   }
 }