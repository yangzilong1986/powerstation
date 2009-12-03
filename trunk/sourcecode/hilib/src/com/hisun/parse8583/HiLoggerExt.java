/*    */ package com.hisun.parse8583;
/*    */ 
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import java.io.FileWriter;
/*    */ import java.io.IOException;
/*    */ import org.apache.commons.lang.SystemUtils;
/*    */ import org.apache.log4j.Level;
/*    */ 
/*    */ public class HiLoggerExt extends Logger
/*    */ {
/*    */   public HiLoggerExt(String name)
/*    */   {
/* 16 */     super(name);
/*    */   }
/*    */ 
/*    */   protected void println(FileWriter fw, Level level, Object[] msgs)
/*    */     throws IOException
/*    */   {
/* 29 */     for (int i = 0; i < msgs.length; ++i) {
/* 30 */       if (msgs[i] instanceof byte[])
/*    */       {
/* 32 */         byte[] msg1 = (byte[])(byte[])msgs[i];
/* 33 */         for (int j = 0; j < msg1.length; ++j) {
/* 34 */           fw.write(msg1[j]);
/*    */         }
/*    */       }
/* 37 */       else if (msgs[i] != null) {
/* 38 */         fw.write(msgs[i].toString());
/*    */       } else {
/* 40 */         fw.write("null");
/*    */       }
/* 42 */       if (i != msgs.length - 1) {
/* 43 */         fw.write(58);
/*    */       }
/*    */     }
/* 46 */     fw.write(SystemUtils.LINE_SEPARATOR);
/* 47 */     fw.flush();
/* 48 */     fw = null;
/*    */   }
/*    */ }