/*     */ package com.hisun.parse8583;
/*     */ 
/*     */ import com.hisun.common.util.HiByteUtil;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileReader;
/*     */ import java.io.PrintStream;
/*     */ import org.apache.log4j.Level;
/*     */ 
/*     */ public class HiParse8583
/*     */ {
/*     */   private HiParser8583Handler _rqHandler;
/*     */   private HiParser8583Handler _rpHandler;
/*     */   private BufferedReader _is;
/*     */   private Logger _log;
/*  23 */   boolean _is_rqpackage = true;
/*     */   private String curline;
/*     */   private String curMsgid;
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/*  29 */     if (args.length < 5) {
/*  30 */       System.out.println("USAGE: parse8583 infile outfile cfgfile version [MSGID]");
/*     */ 
/*  32 */       System.exit(-1);
/*     */     }
/*     */     try {
/*  35 */       HiParse8583 parse = new HiParse8583();
/*  36 */       Params param = new Params();
/*  37 */       param._inFile = args[0];
/*  38 */       param._outFile = args[1];
/*  39 */       param._cfgFile1 = args[2];
/*  40 */       param._cfgFile2 = args[3];
/*  41 */       param._version = args[4];
/*     */ 
/*  43 */       String id = null;
/*  44 */       if (args.length == 6) {
/*  45 */         id = args[5];
/*     */       }
/*  47 */       parse.process(param, id);
/*     */     } catch (Exception e) {
/*  49 */       e.printStackTrace();
/*  50 */       System.exit(-1);
/*     */     }
/*  52 */     System.exit(0);
/*     */   }
/*     */ 
/*     */   public void process(Params param, String id)
/*     */     throws Exception
/*     */   {
/*  59 */     setHandlerParam(param);
/*  60 */     byte[] buffer = null;
/*     */     do { do if ((buffer = readRecord()) == null) return;
/*  62 */       while ((id != null) && (!(id.equals(this.curMsgid))));
/*     */ 
/*  64 */       if (this._log.isInfoEnabled()) {
/*  65 */         this._log.info(this.curline + "|");
/*     */       }
/*  67 */       if (buffer == null)
/*     */         return;
/*     */       try
/*     */       {
/*  71 */         if (this._is_rqpackage)
/*  72 */           processRqRecord(buffer);
/*     */         else
/*  74 */           processRpRecord(buffer);
/*     */       } catch (Throwable t) {
/*  76 */         System.out.println("解析出错:" + this.curMsgid + "," + t);
/*     */       }
/*     */     }
/*  79 */     while (id == null);
/*     */   }
/*     */ 
/*     */   public byte[] readRecord()
/*     */     throws Exception
/*     */   {
/*  85 */     String line = this._is.readLine();
/*  86 */     if (line == null)
/*  87 */       return null;
/*  88 */     this.curline = line;
/*  89 */     int idx = line.lastIndexOf("|");
/*  90 */     if (idx == -1) {
/*  91 */       throw new Exception("非法行:" + line);
/*     */     }
/*  93 */     String[] parts = line.split("\\|");
/*  94 */     if (parts[2].equalsIgnoreCase("rq"))
/*  95 */       this._is_rqpackage = true;
/*     */     else {
/*  97 */       this._is_rqpackage = false;
/*     */     }
/*  99 */     this.curMsgid = parts[1];
/* 100 */     line = line.substring(idx + 1);
/* 101 */     return HiByteUtil.hexToByteArray(line);
/*     */   }
/*     */ 
/*     */   public void setHandlerParam(Params param)
/*     */     throws Exception
/*     */   {
/* 107 */     HiContext ctx1 = HiContext.getRootContext();
/* 108 */     HiContext.setCurrentContext(ctx1);
/* 109 */     this._log = new HiLoggerExt(param._outFile);
/* 110 */     this._log.setLevel(Level.INFO);
/* 111 */     ctx1.setProperty("SVR.log", this._log);
/* 112 */     this._rqHandler = new HiParser8583Handler();
/* 113 */     this._rqHandler.setCFG(param._cfgFile1);
/* 114 */     this._rqHandler.setVersion(param._version);
/* 115 */     this._rqHandler.serverInit(null);
/*     */ 
/* 117 */     this._rpHandler = new HiParser8583Handler();
/* 118 */     this._rpHandler.setCFG(param._cfgFile2);
/* 119 */     this._rpHandler.setVersion(param._version);
/* 120 */     this._rpHandler.serverInit(null);
/*     */ 
/* 122 */     this._is = new BufferedReader(new FileReader(param._inFile));
/*     */   }
/*     */ 
/*     */   public void processRqRecord(byte[] buffer) throws Exception {
/* 126 */     HiMessageContext msgCtx = new HiMessageContext();
/* 127 */     HiMessageContext.setCurrentContext(msgCtx);
/* 128 */     HiMessage msg = new HiMessage("Parse8583", "PLTIN0");
/* 129 */     msg.setBody(new HiByteBuffer(buffer));
/* 130 */     msgCtx.setCurrentMsg(msg);
/*     */ 
/* 132 */     this._rqHandler.process(msgCtx);
/*     */   }
/*     */ 
/*     */   public void processRpRecord(byte[] buffer) throws Exception {
/* 136 */     HiMessageContext msgCtx = new HiMessageContext();
/* 137 */     HiMessageContext.setCurrentContext(msgCtx);
/* 138 */     HiMessage msg = new HiMessage("Parse8583", "PLTIN0");
/* 139 */     msg.setBody(new HiByteBuffer(buffer));
/* 140 */     msgCtx.setCurrentMsg(msg);
/*     */ 
/* 142 */     this._rpHandler.process(msgCtx);
/*     */   }
/*     */ }