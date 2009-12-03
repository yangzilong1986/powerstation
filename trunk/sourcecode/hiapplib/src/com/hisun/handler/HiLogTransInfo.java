/*     */ package com.hisun.handler;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.framework.event.IServerDestroyListener;
/*     */ import com.hisun.framework.event.IServerInitListener;
/*     */ import com.hisun.framework.event.ServerEvent;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.pubinterface.IHandler;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import com.hisun.util.HiResource;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.SystemUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ import org.apache.commons.lang.time.DateFormatUtils;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.SAXReader;
/*     */ 
/*     */ public class HiLogTransInfo
/*     */   implements IHandler, IServerInitListener, IServerDestroyListener
/*     */ {
/*     */   private String _fmtFile;
/*     */   private ArrayList _fmtInfos;
/*     */   private String _logFile;
/*     */   private Logger log;
/*  55 */   private static HiStringManager sm = HiStringManager.getManager();
/*     */   private StringBuffer _headInfo;
/*     */   private Logger log1;
/*     */ 
/*     */   public HiLogTransInfo()
/*     */   {
/*  51 */     this._fmtFile = "etc/TXNTRC_FMT.XML";
/*  52 */     this._fmtInfos = new ArrayList();
/*  53 */     this._logFile = "TXNTRC.lst";
/*  54 */     this.log = null;
/*     */ 
/*  56 */     this._headInfo = new StringBuffer();
/*  57 */     this.log1 = null; }
/*     */ 
/*     */   public void process(HiMessageContext arg0) throws HiException {
/*  60 */     HiETF root = arg0.getCurrentMsg().getETFBody();
/*     */     try
/*     */     {
/*  64 */       HiByteBuffer buf = new HiByteBuffer(100);
/*  65 */       for (int i = 0; i < this._fmtInfos.size(); ++i) {
/*  66 */         HiLogFmtInfo info = (HiLogFmtInfo)this._fmtInfos.get(i);
/*  67 */         String value = getValue(arg0, info.name);
/*  68 */         if (value == null)
/*  69 */           value = "";
/*  70 */         buf.append(StringUtils.rightPad(value, info.length));
/*  71 */         buf.append(info.deli);
/*     */       }
/*  73 */       buf.append(SystemUtils.LINE_SEPARATOR);
/*  74 */       this.log1.info(buf);
/*     */     }
/*     */     catch (Throwable e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public void serverInit(ServerEvent arg0)
/*     */     throws HiException
/*     */   {
/*     */     Document doc;
/*     */     HiLogFmtInfo info;
/*  90 */     URL url = HiResource.getResource(this._fmtFile);
/*  91 */     if (url == null) {
/*  92 */       throw new HiException("212004", this._fmtFile);
/*     */     }
/*  94 */     SAXReader reader = new SAXReader();
/*     */     try
/*     */     {
/*  97 */       doc = reader.read(url);
/*     */     } catch (DocumentException e) {
/*  99 */       throw new HiException("212005", this._fmtFile, e);
/*     */     }
/* 101 */     Element root = doc.getRootElement();
/* 102 */     root = root.element("Format");
/* 103 */     List items = root.elements("Item");
/* 104 */     for (int i = 0; i < items.size(); ++i) {
/* 105 */       Element node = (Element)items.get(i);
/* 106 */       info = new HiLogFmtInfo();
/* 107 */       info.name = node.attributeValue("name");
/* 108 */       if (info.name == null)
/*     */         continue;
/* 110 */       info.desc = node.attributeValue("desc");
/* 111 */       if (info.desc == null) {
/* 112 */         info.desc = info.name;
/*     */       }
/* 114 */       info.length = NumberUtils.toInt(node.attributeValue("length"));
/* 115 */       if (info.length < 0) {
/* 116 */         info.length = 0;
/*     */       }
/* 118 */       info.deli = (char)NumberUtils.toInt(node.attributeValue("deli"));
/* 119 */       this._fmtInfos.add(info);
/*     */     }
/*     */ 
/* 122 */     FileWriter _fw = null;
/*     */     try {
/* 124 */       _fw = new FileWriter(HiICSProperty.getTrcDir() + this._logFile, true);
/* 125 */       this._headInfo.append('#');
/* 126 */       for (int i = 0; i < this._fmtInfos.size(); ++i) {
/* 127 */         info = (HiLogFmtInfo)this._fmtInfos.get(i);
/* 128 */         this._headInfo.append(StringUtils.rightPad(info.desc, info.length));
/* 129 */         this._headInfo.append(info.deli);
/*     */       }
/* 131 */       this._headInfo.append(SystemUtils.LINE_SEPARATOR);
/* 132 */       _fw.write(this._headInfo.toString());
/* 133 */       _fw.close();
/*     */     } catch (Throwable e) {
/* 135 */       if (_fw != null)
/*     */         try {
/* 137 */           _fw.close();
/*     */         }
/*     */         catch (IOException e1) {
/*     */         }
/* 141 */       throw new HiException("220079", HiICSProperty.getTrcDir() + this._logFile, e);
/*     */     }
/* 143 */     this.log = arg0.getLog();
/* 144 */     this.log1 = Logger.getLogger(this._logFile);
/* 145 */     this.log1.setHasOfHead(false);
/*     */   }
/*     */ 
/*     */   public String getFmtFile() {
/* 149 */     return this._fmtFile;
/*     */   }
/*     */ 
/*     */   public void setFmtFile(String fmtFile) {
/* 153 */     this._fmtFile = fmtFile;
/*     */   }
/*     */ 
/*     */   public String getLogFile() {
/* 157 */     return this._logFile;
/*     */   }
/*     */ 
/*     */   public void setLogFile(String file) {
/* 161 */     this._logFile = file;
/*     */   }
/*     */ 
/*     */   public void serverDestroy(ServerEvent arg0)
/*     */     throws HiException
/*     */   {
/*     */   }
/*     */ 
/*     */   private String getValue(HiMessageContext ctx, String name)
/*     */   {
/* 174 */     HiMessage msg = ctx.getCurrentMsg();
/* 175 */     HiETF etf = msg.getETFBody();
/*     */ 
/* 177 */     if (!(name.startsWith("@"))) {
/* 178 */       return name;
/*     */     }
/*     */ 
/* 181 */     int idx = name.indexOf(46);
/* 182 */     if (idx == -1) {
/* 183 */       return name;
/*     */     }
/* 185 */     String id = name.substring(0, idx);
/* 186 */     String key = name.substring(idx + 1);
/* 187 */     if ("@MSG".equalsIgnoreCase(id))
/*     */     {
/*     */       Long l;
/* 188 */       if ("SID".equalsIgnoreCase(key))
/* 189 */         return msg.getRequestId();
/* 190 */       if ("ECT".equalsIgnoreCase(key))
/* 191 */         return msg.getType();
/* 192 */       if ("ETM".equalsIgnoreCase(key)) {
/* 193 */         l = (Long)msg.getObjectHeadItem(key);
/* 194 */         if (l == null)
/* 195 */           return null;
/* 196 */         return DateFormatUtils.format(l.longValue(), "hh:mm:ss"); }
/* 197 */       if ("STM".equalsIgnoreCase(key)) {
/* 198 */         l = (Long)msg.getObjectHeadItem(key);
/* 199 */         if (l == null)
/* 200 */           return null;
/* 201 */         return DateFormatUtils.format(l.longValue(), "hh:mm:ss");
/*     */       }
/* 203 */       Object o = msg.getObjectHeadItem(key);
/* 204 */       if (o == null)
/* 205 */         return null;
/* 206 */       return o.toString();
/*     */     }
/*     */ 
/* 209 */     if ("@ETF".equalsIgnoreCase(id)) {
/* 210 */       return etf.getChildValue(key);
/*     */     }
/* 212 */     return ctx.getStrProp(key);
/*     */   }
/*     */ }