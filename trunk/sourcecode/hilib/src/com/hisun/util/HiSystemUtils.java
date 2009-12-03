/*    */ package com.hisun.util;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStreamWriter;
/*    */ 
/*    */ public class HiSystemUtils
/*    */ {
/*    */   public static int exec(String cmd, String param1, String param2, boolean wait)
/*    */     throws HiException
/*    */   {
/* 17 */     return exec(cmd, new String[] { param1, param2 }, wait);
/*    */   }
/*    */ 
/*    */   public static int exec(String cmd, String param1, String param2, String param3, boolean wait) throws HiException
/*    */   {
/* 22 */     return exec(cmd, new String[] { param1, param2, param3 }, wait);
/*    */   }
/*    */ 
/*    */   public static int exec(String cmd, String param1, boolean wait) throws HiException
/*    */   {
/* 27 */     return exec(cmd, new String[] { param1 }, wait);
/*    */   }
/*    */ 
/*    */   public static int exec(String cmd, boolean wait) throws HiException {
/* 31 */     return exec(cmd, new String[0], wait);
/*    */   }
/*    */ 
/*    */   public static int exec(String cmd, String[] params, boolean wait) throws HiException
/*    */   {
/* 36 */     Process p = null;
/* 37 */     int ret = 0;
/* 38 */     StringBuffer cmdBuf = new StringBuffer();
/* 39 */     cmdBuf.append(cmd);
/* 40 */     for (int i = 0; (params != null) && (i < params.length); ++i) {
/* 41 */       cmdBuf.append(" ");
/* 42 */       cmdBuf.append(params[i]);
/*    */     }
/* 44 */     Logger log = HiLog.getLogger("SYS.trc");
/* 45 */     if (log.isInfoEnabled())
/* 46 */       log.info("exec cmd:[" + cmdBuf.toString() + "]");
/*    */     try
/*    */     {
/* 49 */       p = Runtime.getRuntime().exec("/bin/sh");
/* 50 */       BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
/*    */ 
/* 52 */       bw.write(cmdBuf.toString(), 0, cmdBuf.length());
/* 53 */       bw.newLine();
/* 54 */       bw.write("exit", 0, 4);
/* 55 */       bw.newLine();
/* 56 */       bw.flush();
/*    */ 
/* 58 */       if (wait) {
/*    */         try {
/* 60 */           ret = p.waitFor();
/*    */         } catch (InterruptedException e) {
/* 62 */           throw new HiException("220309", cmdBuf.toString(), e);
/*    */         }
/*    */       }
/*    */ 
/* 66 */       InputStream errIs = p.getErrorStream();
/* 67 */       InputStream stdIs = p.getInputStream();
/* 68 */       int len = errIs.available();
/* 69 */       byte[] buf = null;
/* 70 */       if (len != 0) {
/* 71 */         buf = new byte[len];
/* 72 */         log.info("============stderr msg============");
/* 73 */         errIs.read(buf);
/* 74 */         log.info(new String(buf, 0, len));
/*    */       }
/*    */ 
/* 77 */       len = stdIs.available();
/* 78 */       if (len != 0) {
/* 79 */         buf = new byte[len];
/* 80 */         log.info("============stdout msg===========");
/* 81 */         stdIs.read(buf);
/* 82 */         log.info(new String(buf, 0, len));
/*    */       }
/*    */     } catch (IOException e) {
/*    */     }
/*    */     finally {
/* 87 */       if (p != null) {
/* 88 */         p.destroy();
/*    */       }
/*    */     }
/* 91 */     return ret;
/*    */   }
/*    */ }