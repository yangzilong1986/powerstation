/*     */ package com.hisun.atmp.handler;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.framework.event.IServerDestroyListener;
/*     */ import com.hisun.framework.event.IServerInitListener;
/*     */ import com.hisun.framework.event.ServerEvent;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.pubinterface.IHandler;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class HiSaveFile
/*     */   implements IHandler, IServerInitListener, IServerDestroyListener
/*     */ {
/*     */   private String type;
/*     */   private String file;
/*     */   private Logger log;
/*  38 */   private static HiStringManager sm = HiStringManager.getManager();
/*     */ 
/*     */   public HiSaveFile()
/*     */   {
/*  35 */     this.type = "bin";
/*  36 */     this.file = "TXN.trc";
/*  37 */     this.log = null;
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext arg0) throws HiException {
/*  41 */     HiMessage msg = arg0.getCurrentMsg();
/*  42 */     HiByteBuffer buf = (HiByteBuffer)msg.getBody();
/*  43 */     byte[] Input = buf.getBytes();
/*  44 */     SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
/*  45 */     FileOutputStream out = null;
/*     */     try {
/*  47 */       ByteArrayOutputStream bos = new ByteArrayOutputStream();
/*     */ 
/*  49 */       bos.write(df.format(new Date()).getBytes());
/*  50 */       bos.write("|".getBytes());
/*  51 */       bos.write(msg.getRequestId().getBytes());
/*  52 */       bos.write("|".getBytes());
/*  53 */       bos.write(msg.getHeadItem("SCH").getBytes());
/*  54 */       bos.write("|".getBytes());
/*  55 */       bos.write(String.valueOf(Input.length).getBytes());
/*  56 */       bos.write("|".getBytes());
/*  57 */       if (this.type.equals("bin"))
/*  58 */         writeBinByte(bos, Input);
/*  59 */       else if (this.type.equals("hex")) {
/*  60 */         writeHexByte(bos, Input);
/*     */       }
/*  62 */       bos.write("\n".getBytes());
/*  63 */       out = new FileOutputStream(HiICSProperty.getTrcDir() + this.file, true);
/*  64 */       out.write(bos.toByteArray());
/*  65 */       out.flush();
/*  66 */       out.close();
/*     */     } catch (Throwable e) {
/*  68 */       if (out == null) return;
/*     */       try {
/*  70 */         out.close();
/*     */       }
/*     */       catch (IOException e1) {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void writeHexByte(OutputStream w, byte[] b) throws IOException {
/*  78 */     for (int i = 0; i < b.length; ++i) {
/*  79 */       w.write(bin2char((b[i] & 0xF0) >> 4));
/*  80 */       w.write(bin2char(b[i] & 0xF));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void writeBinByte(OutputStream out, byte[] b) throws IOException {
/*  85 */     out.write(b);
/*     */   }
/*     */ 
/*     */   private static char bin2char(int bin) {
/*  89 */     return (char)((bin < 10) ? bin + 48 : bin - 10 + 65);
/*     */   }
/*     */ 
/*     */   public void setType(String type) {
/*  93 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public void setFile(String file) {
/*  97 */     this.file = file;
/*     */   }
/*     */ 
/*     */   public String getFile() {
/* 101 */     return this.file;
/*     */   }
/*     */ 
/*     */   public void serverInit(ServerEvent arg0) throws HiException {
/* 105 */     this.log = arg0.getLog();
/*     */   }
/*     */ 
/*     */   public void serverDestroy(ServerEvent arg0)
/*     */     throws HiException
/*     */   {
/*     */   }
/*     */ }