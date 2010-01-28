 package com.hisun.mng;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.register.HiRegisterService;
 import com.hisun.register.HiServiceObject;
 import java.util.ArrayList;
 import org.apache.commons.lang.StringUtils;
 
 public class HiServiceManage
 {
   public int serviceMonSwitch(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     String tmp = HiArgUtils.getStringNotNull(args, "flag");
     boolean flag = false;
     if ("1".equals(tmp)) {
       flag = true;
     }
     String svrTyp = args.get("svrTyp");
 
     ArrayList list = getServiceList(args, ctx);
     for (int i = 0; i < list.size(); ++i) {
       HiServiceObject serviceObject = (HiServiceObject)list.get(i);
       if ((!(StringUtils.isBlank(svrTyp))) && (!(StringUtils.equalsIgnoreCase(svrTyp, serviceObject.getServerType())))) {
         continue;
       }
       HiRegisterService.setMonSwitch(serviceObject.getServiceCode(), flag);
     }
 
     return 0;
   }
 
   public int serviceLogSwitch(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     String type = HiArgUtils.getStringNotNull(args, "type");
     String logLvl = HiArgUtils.getStringNotNull(args, "flag");
     String svrTyp = args.get("svrTyp");
 
     ArrayList list = getServiceList(args, ctx);
     for (int i = 0; i < list.size(); ++i) {
       HiServiceObject serviceObject = (HiServiceObject)list.get(i);
       if ((!(StringUtils.isBlank(svrTyp))) && (!(StringUtils.equalsIgnoreCase(svrTyp, serviceObject.getServerType())))) {
         continue;
       }
       HiRegisterService.setLogSwitch(serviceObject.getServiceCode(), logLvl);
     }
 
     return 0;
   }
 
   public int oneServiceQuery(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String code = HiArgUtils.getStringNotNull(args, "Code");
     String curNod = args.get("curNod");
     if (StringUtils.isBlank(curNod)) {
       curNod = "ROOT";
     }
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     HiETF root = ctx.getCurrentMsg().getETFBody();
     try {
       HiServiceObject serviceObject = HiRegisterService.getService(code);
       root.setGrandChildNode(curNod + ".LOG_LVL", serviceObject.getLogLevel());
 
       root.setGrandChildNode(curNod + ".MON_SW", serviceObject.getMonSwitch());
 
       root.setGrandChildNode(curNod + ".RUN_FLG", serviceObject.getRunning());
     }
     catch (HiException e) {
       root.setGrandChildNode(curNod + ".LOG_LVL", "0");
       root.setGrandChildNode(curNod + ".MON_SW", "0");
       root.setGrandChildNode(curNod + ".RUN_FLG", "0");
     }
     return 0;
   }
 
   public int serviceQuery(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String svrTyp = args.get("svrTyp");
     String grpNam = args.get("grpNam");
     if (StringUtils.isBlank(grpNam)) {
       grpNam = "GRP";
     }
 
     ArrayList list = getServiceList(args, ctx);
 
     HiETF root = ctx.getCurrentMsg().getETFBody();
     int k = 1;
     for (int i = 0; i < list.size(); ++i) {
       HiServiceObject serviceObject = (HiServiceObject)list.get(i);
       if ((!(StringUtils.isBlank(svrTyp))) && (!(StringUtils.equalsIgnoreCase(svrTyp, serviceObject.getServerType())))) {
         continue;
       }
       HiETF grpNode = root.getChildNode(grpNam + "_" + k);
       if (grpNode == null) {
         grpNode = root.addNode(grpNam + "_" + k);
       }
       oneServiceQuery(serviceObject, grpNode);
       ++k;
     }
 
     root.setChildValue(grpNam + "_NUM", String.valueOf(k - 1));
     return 0;
   }
 
   public void oneServiceQuery(HiServiceObject serviceObject, HiETF node)
   {
     node.setChildValue("APP_CD", serviceObject.getAppCode());
     node.setChildValue("APP_NM", serviceObject.getAppName());
     node.setChildValue("DESC", serviceObject.getDesc());
     node.setChildValue("LOG_LVL", serviceObject.getLogLevel());
     node.setChildValue("MON_SW", serviceObject.getMonSwitch());
     node.setChildValue("RUN_FLG", serviceObject.getRunning());
     node.setChildValue("SVR_NM", serviceObject.getServerName());
     node.setChildValue("SVR_TYP", serviceObject.getServerType());
     node.setChildValue("SVC_CD", serviceObject.getServiceCode());
     node.setChildValue("SVC_TM", serviceObject.getTime());
   }
 
   public int serviceManage(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String svrTyp = args.get("svrTyp");
 
     String mode = HiArgUtils.getStringNotNull(args, "mode");
 
     ArrayList list = getServiceList(args, ctx);
 
     HiETF root = ctx.getCurrentMsg().getETFBody();
     int k = 1;
     try {
       for (int i = 0; i < list.size(); ++i) {
         HiServiceObject serviceObject = (HiServiceObject)list.get(i);
         if ((!(StringUtils.isBlank(svrTyp))) && (!(StringUtils.equalsIgnoreCase(svrTyp, serviceObject.getServerType())))) {
           continue;
         }
         if (StringUtils.equalsIgnoreCase(mode, "start"))
           HiRegisterService.start(serviceObject.getServiceCode());
         else
           HiRegisterService.stop(serviceObject.getServiceCode());
       }
     }
     finally
     {
       HiMessageContext.setCurrentMessageContext(ctx);
     }
     return 0;
   }
 
   private ArrayList getServiceList(HiATLParam args, HiMessageContext ctx) throws HiException
   {
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     ArrayList list = null;
     String type = HiArgUtils.getStringNotNull(args, "type");
 
     if (StringUtils.equalsIgnoreCase(type, "a")) {
       list = HiRegisterService.listServices("");
     }
     else
     {
       String name;
       String[] tmps;
       int i;
       ArrayList list1;
       if (StringUtils.equalsIgnoreCase(type, "g")) {
         name = HiArgUtils.getStringNotNull(args, "name");
         tmps = name.split("\\|");
         list = new ArrayList();
         for (i = 0; i < tmps.length; ++i) {
           list1 = HiRegisterService.listServicesByAppName(tmps[i]);
 
           if (list1 == null) {
             log.info("application:[" + tmps[i] + "] not existed!");
           }
           else
             list.addAll(list1);
         }
       } else if (StringUtils.equalsIgnoreCase(type, "s")) {
         name = HiArgUtils.getStringNotNull(args, "name");
         tmps = name.split("\\|");
         list = new ArrayList();
         for (i = 0; i < tmps.length; ++i) {
           list1 = HiRegisterService.listServicesBySvrName(tmps[i]);
 
           if (list1 == null) {
             log.info("server:[" + tmps[i] + "] not existed!");
           }
           else
             list.addAll(list1);
         }
       } else if (StringUtils.equalsIgnoreCase(type, "t")) {
         name = HiArgUtils.getStringNotNull(args, "name");
         tmps = name.split("\\|");
         list = new ArrayList();
         for (i = 0; i < tmps.length; ++i) {
           HiServiceObject serviceObject = HiRegisterService.getService(tmps[i]);
 
           if (serviceObject == null) {
             log.info("transaction:[" + tmps[i] + "] not existed!");
           }
           else
             list.add(HiRegisterService.getService(tmps[i])); 
         }
       }
     }
     return list;
   }
 }