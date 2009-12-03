/*    */ package com.hisun.component.no;
/*    */ 
/*    */ import com.hisun.database.HiDataBaseUtil;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import java.util.HashMap;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class GetUniqueNo
/*    */ {
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 20 */     HashMap map = ctx.getDataBaseUtil().readRecord("select PUBSIDN.NEXTVAL ID from dual");
/*    */ 
/* 22 */     String name = args.get("name");
/* 23 */     if (StringUtils.isBlank(name)) {
/* 24 */       name = "ID";
/*    */     }
/* 26 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/* 27 */     root.setChildValue(name, (String)map.get("ID"));
/* 28 */     return 0;
/*    */   }
/*    */ }