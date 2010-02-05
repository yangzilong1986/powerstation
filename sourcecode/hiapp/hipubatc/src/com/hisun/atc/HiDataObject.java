 package com.hisun.atc;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFFactory;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 
 public class HiDataObject
 {
   public int CreateDataObject(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String type = HiArgUtils.getStringNotNull(argsMap, "Type");
     String name = HiArgUtils.getStringNotNull(argsMap, "Name");
     if (HiDataSource.get(ctx, name) != null) {
       throw new HiException("220319", name);
     }
     HiDataSource.create(ctx, name, type);
     return 0;
   }
 
   public int DeleteDataObject(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String name = HiArgUtils.getStringNotNull(argsMap, "Name");
     Object o = null;
     if ((o = HiDataSource.get(ctx, name)) == null) {
       return 2;
     }
 
     if (o == ctx.getResponseMsg().getBody()) {
       throw new HiException("220323", name);
     }
     HiDataSource.delete(ctx, name);
     return 0;
   }
 
   public int SetOutputArea(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String areaName = HiArgUtils.getStringNotNull(argsMap, "AreaName");
     Object ds = HiDataSource.get(ctx, areaName);
     if ((ds != null) && (!(ds instanceof HiETF))) {
       throw new HiException("220321", areaName, "ETF");
     }
 
     if (ds == null) {
       ds = HiDataSource.create(ctx, areaName, "ETF");
     }
     HiMessage msg = new HiMessage(ctx.getCurrentMsg());
     msg.setBody(ds);
     ctx.setResponseMsg(msg);
     return 0;
   }
 
   public int ClearOutputArea(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getResponseMsg();
     if (msg == ctx.getCurrentMsg()) {
       return 2;
     }
 
     Object o = HiETFFactory.createETF();
     String name = HiDataSource.getName(ctx, msg.getBody());
     HiDataSource.set(ctx, name, o);
     msg.setBody(o);
     return 0;
   }
 
   public int ResetOutputArea(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.popResponseMsg();
     if (msg == null) {
       return 2;
     }
     String flag = argsMap.get("DeleteFlag");
     if ("Y".equalsIgnoreCase(flag)) {
       HiDataSource.delete(ctx, HiDataSource.getName(ctx, msg.getBody()));
     }
     return 0;
   }
 }