/*    */ package com.hisun.component.db;
/*    */ 
/*    */ import com.hisun.atc.common.HiArgUtils;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.util.HiStringUtils;
/*    */ import java.io.PrintStream;
/*    */ import java.util.ArrayList;
/*    */ import org.apache.commons.codec.DecoderException;
/*    */ import org.apache.commons.codec.EncoderException;
/*    */ import org.apache.commons.codec.binary.Hex;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class QueryMultiPage
/*    */ {
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 39 */     return 0;
/*    */   }
/*    */ 
/*    */   private String getDynSentence(HiMessageContext ctx, String alias, ArrayList list) throws HiException {
/* 43 */     String sqlSentence = HiArgUtils.getStringNotNull(ctx, "SENTENCE." + alias);
/*    */ 
/* 45 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*    */ 
/* 47 */     String strFields = (String)ctx.getProperty("FIELDS." + alias);
/*    */ 
/* 49 */     if (StringUtils.isEmpty(strFields))
/* 50 */       return sqlSentence;
/* 51 */     HiETF curEtf = ctx.getCurrentMsg().getETFBody();
/* 52 */     String[] fields = StringUtils.split(strFields, "|");
/* 53 */     for (int i = 0; i < fields.length; ++i)
/*    */     {
/* 55 */       list.add(ctx.getSpecExpre(curEtf, fields[i]));
/*    */     }
/* 57 */     sqlSentence = HiStringUtils.format(sqlSentence, list);
/* 58 */     return sqlSentence;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args) throws EncoderException, DecoderException {
/* 62 */     Hex codec = new Hex();
/* 63 */     byte[] buf = codec.encode("测333试".getBytes());
/* 64 */     System.out.println(new String(buf));
/* 65 */     System.out.println(new String(codec.decode(buf)));
/*    */   }
/*    */ }