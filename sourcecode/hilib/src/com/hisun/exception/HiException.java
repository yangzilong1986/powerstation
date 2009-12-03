/*     */ package com.hisun.exception;
/*     */ 
/*     */ import com.hisun.mon.HiMonitorEventInfo;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class HiException extends Exception
/*     */ {
/*     */   private static final long serialVersionUID = 843027728253830766L;
/*  25 */   protected Throwable nestedException = null;
/*     */ 
/*  28 */   private String code = "211007";
/*     */ 
/*  31 */   private String[] msg = null;
/*     */ 
/*  34 */   protected Map msgStack = null;
/*     */ 
/*  37 */   private boolean isLog = false;
/*     */ 
/*     */   protected void clone(HiException e) {
/*  40 */     this.nestedException = e.nestedException;
/*  41 */     this.code = e.code;
/*  42 */     this.msg = e.msg;
/*  43 */     this.msgStack = e.msgStack;
/*  44 */     this.isLog = e.isLog;
/*     */   }
/*     */ 
/*     */   public static HiException makeException(Throwable e) {
/*  48 */     if (e instanceof InvocationTargetException) {
/*  49 */       Throwable t = ((InvocationTargetException)e).getTargetException();
/*  50 */       if (t instanceof Exception) {
/*  51 */         e = (Exception)t;
/*     */       }
/*     */     }
/*  54 */     HiMonitorEventInfo eventInfo = new HiMonitorEventInfo();
/*  55 */     if (e instanceof HiException) {
/*  56 */       HiException e1 = (HiException)e;
/*  57 */       eventInfo.setId(e1.getCode());
/*  58 */       eventInfo.setMsg(e1.getAppMessage());
/*  59 */       eventInfo.send();
/*  60 */       return e1;
/*     */     }
/*     */ 
/*  63 */     eventInfo.setId("211007");
/*  64 */     eventInfo.setMsg(e.getMessage());
/*  65 */     eventInfo.send();
/*  66 */     return new HiException(e);
/*     */   }
/*     */ 
/*     */   public static HiException makeException(String code, Throwable e) {
/*  70 */     return makeException(code, new String[0], e);
/*     */   }
/*     */ 
/*     */   public static HiException makeException(String code, String arg0, Throwable e)
/*     */   {
/*  75 */     return makeException(code, new String[] { arg0 }, e);
/*     */   }
/*     */ 
/*     */   public static HiException makeException(String code, String arg0, String arg1, Throwable e)
/*     */   {
/*  80 */     return makeException(code, new String[] { arg0, arg1 }, e);
/*     */   }
/*     */ 
/*     */   public static HiException makeException(String code, String arg0, String arg1, String arg2, Throwable e)
/*     */   {
/*  85 */     return makeException(code, new String[] { arg0, arg1, arg2 }, e);
/*     */   }
/*     */ 
/*     */   public static HiException makeException(String code, String arg0, String arg1, String arg2, String arg3, Throwable e)
/*     */   {
/*  90 */     return makeException(code, new String[] { arg0, arg1, arg2, arg3 }, e);
/*     */   }
/*     */ 
/*     */   public static HiException makeException(String code, String[] args, Throwable e)
/*     */   {
/*  95 */     if (e instanceof InvocationTargetException) {
/*  96 */       e = ((InvocationTargetException)e).getTargetException();
/*     */     }
/*     */ 
/* 101 */     HiMonitorEventInfo eventInfo = new HiMonitorEventInfo();
/* 102 */     if (e instanceof HiException) {
/* 103 */       HiException e1 = (HiException)e;
/* 104 */       eventInfo.setId(e1.getCode());
/* 105 */       eventInfo.setMsg(e1.getAppMessage());
/* 106 */       eventInfo.send();
/*     */ 
/* 108 */       return e1;
/*     */     }
/*     */ 
/* 111 */     eventInfo.setId(code);
/* 112 */     eventInfo.setMsg(e.getMessage());
/* 113 */     eventInfo.send();
/* 114 */     return new HiSysException(code, args, e);
/*     */   }
/*     */ 
/*     */   public HiException()
/*     */   {
/* 165 */     create("000000", null);
/*     */   }
/*     */ 
/*     */   public HiException(String code) {
/* 169 */     super(code);
/*     */ 
/* 171 */     create(code, null);
/*     */   }
/*     */ 
/*     */   public HiException(String code, String msg)
/*     */   {
/* 183 */     super(code);
/* 184 */     create(code, new String[] { msg });
/*     */   }
/*     */ 
/*     */   public HiException(String code, String arg0, String arg1) {
/* 188 */     super(code);
/* 189 */     create(code, new String[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */   public HiException(String code, String arg0, String arg1, String arg2) {
/* 193 */     super(code);
/* 194 */     create(code, new String[] { arg0, arg1, arg2 });
/*     */   }
/*     */ 
/*     */   public HiException(String code, String arg0, String arg1, String arg2, String arg3)
/*     */   {
/* 199 */     super(code);
/* 200 */     create(code, new String[] { arg0, arg1, arg2, arg3 });
/*     */   }
/*     */ 
/*     */   public HiException(String code, String[] args) {
/* 204 */     super(code);
/* 205 */     create(code, args);
/*     */   }
/*     */ 
/*     */   public HiException(Throwable nestedException)
/*     */   {
/* 215 */     super(nestedException.getMessage());
/*     */ 
/* 217 */     create(this.code, new String[] { nestedException.getMessage() });
/*     */ 
/* 219 */     this.nestedException = nestedException;
/*     */   }
/*     */ 
/*     */   public HiException(String code, String msg, Throwable nestedException)
/*     */   {
/* 233 */     super(code);
/*     */ 
/* 235 */     create(code, new String[] { msg });
/* 236 */     this.nestedException = nestedException;
/*     */   }
/*     */ 
/*     */   public HiException(String code, String[] msg, Throwable nestedException) {
/* 240 */     super(code);
/*     */ 
/* 242 */     create(code, msg);
/* 243 */     this.nestedException = nestedException;
/*     */   }
/*     */ 
/*     */   protected void create(String code, String[] args) {
/* 247 */     this.code = code;
/* 248 */     this.msg = args;
/* 249 */     if (this.msgStack == null) {
/* 250 */       this.msgStack = new LinkedHashMap();
/*     */     }
/* 252 */     this.msgStack.put(code, args);
/* 253 */     HiMonitorEventInfo eventInfo = new HiMonitorEventInfo();
/* 254 */     eventInfo.setId(code);
/* 255 */     eventInfo.setMsg(getAppMessage());
/* 256 */     eventInfo.send();
/*     */   }
/*     */ 
/*     */   public void addMsgStack(String code, String[] args)
/*     */   {
/* 268 */     create(code, args);
/*     */   }
/*     */ 
/*     */   public void addMsgStack(String code, String arg0) {
/* 272 */     create(code, new String[] { arg0 });
/*     */   }
/*     */ 
/*     */   public void addMsgStack(String code, String arg0, String arg1) {
/* 276 */     create(code, new String[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */   public void addMsgStack(String code, String arg0, String arg1, String arg2) {
/* 280 */     create(code, new String[] { arg0, arg1, arg2 });
/*     */   }
/*     */ 
/*     */   public void addMsgStack(String code, String arg0, String arg1, String arg2, String arg3)
/*     */   {
/* 285 */     create(code, new String[] { arg0, arg1, arg2, arg3 });
/*     */   }
/*     */ 
/*     */   public String getCode()
/*     */   {
/* 294 */     return this.code;
/*     */   }
/*     */ 
/*     */   public void setCode(String code)
/*     */   {
/* 304 */     this.code = code;
/*     */   }
/*     */ 
/*     */   public String getMessage()
/*     */   {
/* 313 */     if (this.nestedException == null)
/*     */     {
/* 315 */       return getAppMessage();
/*     */     }
/*     */ 
/* 318 */     return getAppMessage() + "\n Nested Exception: " + this.nestedException.getMessage();
/*     */   }
/*     */ 
/*     */   public String getAppMessage()
/*     */   {
/* 329 */     return this.code + "-" + HiStringManager.getManager().getString(this.code, this.msg);
/*     */   }
/*     */ 
/*     */   public String getAppStackMessage()
/*     */   {
/* 338 */     HiStringManager mgr = HiStringManager.getManager();
/* 339 */     StringBuffer msgbuf = new StringBuffer();
/*     */ 
/* 341 */     Iterator it = this.msgStack.entrySet().iterator();
/*     */ 
/* 343 */     while (it.hasNext()) {
/* 344 */       Map.Entry msgEntry = (Map.Entry)it.next();
/* 345 */       String s = ((String)msgEntry.getKey()) + ":" + mgr.getString((String)msgEntry.getKey(), (String[])(String[])msgEntry.getValue()) + "\n";
/*     */ 
/* 349 */       msgbuf.insert(0, s);
/*     */     }
/* 351 */     return msgbuf.toString();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*     */     String detail;
/* 360 */     String args = "";
/*     */     try
/*     */     {
/* 363 */       detail = getAppMessage();
/*     */     } catch (Exception e) {
/* 365 */       detail = "";
/*     */     }
/*     */ 
/* 368 */     if (this.msg != null) {
/* 369 */       for (int i = 0; i < this.msg.length; ++i) {
/* 370 */         args = args + " : " + this.msg[i];
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 375 */     return this.code + ":" + detail;
/*     */   }
/*     */ 
/*     */   public Throwable getNestedException()
/*     */   {
/* 384 */     return this.nestedException;
/*     */   }
/*     */ 
/*     */   public void printStackTrace()
/*     */   {
/* 391 */     super.printStackTrace();
/*     */ 
/* 393 */     if (this.nestedException != null) {
/* 394 */       System.err.println(" Nested Exception : ");
/* 395 */       this.nestedException.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void printStackTrace(PrintStream out)
/*     */   {
/* 403 */     super.printStackTrace(out);
/*     */ 
/* 405 */     if (this.nestedException != null) {
/* 406 */       out.println(" Nested Exception: ");
/* 407 */       this.nestedException.printStackTrace(out);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void printStackTrace(PrintWriter writer)
/*     */   {
/* 415 */     super.printStackTrace(writer);
/*     */ 
/* 417 */     if (this.nestedException != null) {
/* 418 */       writer.println(" Nested Exception: ");
/* 419 */       this.nestedException.printStackTrace(writer);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isLog() {
/* 424 */     return this.isLog;
/*     */   }
/*     */ 
/*     */   public void setLog(boolean isLog) {
/* 428 */     this.isLog = isLog;
/*     */   }
/*     */ }