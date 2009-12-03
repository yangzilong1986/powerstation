/*     */ package com.hisun.handler;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.framework.event.IServerInitListener;
/*     */ import com.hisun.framework.event.ServerEvent;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.pubinterface.IHandler;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import java.io.StringReader;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.Node;
/*     */ import org.dom4j.io.SAXReader;
/*     */ 
/*     */ public class HiLogIDHandler
/*     */   implements IHandler, IServerInitListener
/*     */ {
/*     */   private static final String DEFAULT_ID = "DUMMY";
/*     */   private static final String LOG_ID_MAP = "__LOG_ID_MAP";
/*     */   private String type;
/*     */   private String name;
/*     */   private int pos;
/*     */   private int length;
/*     */   private int offset;
/*     */   private String deli;
/*     */ 
/*     */   public static synchronized ConcurrentHashMap getLogIDMap()
/*     */   {
/*  41 */     HiContext ctx = HiContext.getRootContext();
/*  42 */     if (!(ctx.containsProperty("__LOG_ID_MAP"))) {
/*  43 */       ctx.setProperty("__LOG_ID_MAP", new ConcurrentHashMap());
/*     */     }
/*  45 */     return ((ConcurrentHashMap)ctx.getProperty("__LOG_ID_MAP"));
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  51 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/*  59 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public int getPos()
/*     */   {
/*  66 */     return this.pos;
/*     */   }
/*     */ 
/*     */   public void setPos(int pos)
/*     */   {
/*  74 */     this.pos = pos;
/*     */   }
/*     */ 
/*     */   public int getLength()
/*     */   {
/*  81 */     return this.length;
/*     */   }
/*     */ 
/*     */   public void setLength(int length)
/*     */   {
/*  89 */     this.length = length;
/*     */   }
/*     */ 
/*     */   public int getOffset()
/*     */   {
/*  96 */     return this.offset;
/*     */   }
/*     */ 
/*     */   public void setOffset(int offset)
/*     */   {
/* 104 */     this.offset = offset;
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext arg0)
/*     */     throws HiException
/*     */   {
/*     */     HiByteBuffer buf;
/* 108 */     String id = "DUMMY";
/* 109 */     HiMessage msg = arg0.getCurrentMsg();
/* 110 */     if ("text/xml".equalsIgnoreCase(this.type)) {
/* 111 */       buf = (HiByteBuffer)msg.getBody();
/* 112 */       SAXReader reader = new SAXReader();
/*     */       try {
/* 114 */         Document doc = reader.read(new StringReader(buf.toString()));
/* 115 */         Element root = doc.getRootElement();
/* 116 */         Node node = root.selectSingleNode(this.name);
/* 117 */         if (node != null) {
/* 118 */           id = node.getText();
/* 119 */           if (id != null) {
/* 120 */             id = id.trim();
/*     */           }
/*     */         }
/*     */ 
/* 124 */         if (id == null)
/* 125 */           id = "DUMMY";
/*     */       }
/*     */       catch (DocumentException e)
/*     */       {
/* 129 */         e.printStackTrace();
/*     */       }
/* 131 */     } else if ("text/etf".equalsIgnoreCase(this.type)) {
/* 132 */       HiETF etf = msg.getETFBody();
/* 133 */       id = etf.getGrandChildValue(this.name);
/* 134 */       if (id == null)
/* 135 */         id = "DUMMY";
/*     */     }
/* 137 */     else if ("msg".equalsIgnoreCase(this.type)) {
/* 138 */       Object o = msg.getHeadItem(this.name);
/* 139 */       if (o != null) {
/* 140 */         id = o.toString();
/*     */       }
/* 142 */       if (id == null)
/* 143 */         id = "DUMMY";
/*     */     }
/*     */     else {
/* 146 */       buf = (HiByteBuffer)msg.getBody();
/* 147 */       if (StringUtils.isNotBlank(this.deli)) {
/* 148 */         String[] tmps = StringUtils.split(buf.toString(), this.deli);
/* 149 */         if (this.pos <= tmps.length) {
/* 150 */           id = tmps[(this.pos - 1)];
/*     */         }
/*     */       }
/* 153 */       else if (this.offset + this.length <= buf.length()) {
/* 154 */         id = buf.substr(this.offset - 1, this.length);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 160 */     ConcurrentHashMap logIDMap = getLogIDMap();
/* 161 */     if (logIDMap.containsKey(id)) {
/* 162 */       String value = (String)logIDMap.get(id);
/* 163 */       if (StringUtils.isNotBlank(value))
/* 164 */         msg.setHeadItem("STF", value);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/* 173 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(String type)
/*     */   {
/* 181 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public String getDeli()
/*     */   {
/* 188 */     return this.deli;
/*     */   }
/*     */ 
/*     */   public void setDeli(String deli)
/*     */   {
/* 196 */     this.deli = deli;
/*     */   }
/*     */ 
/*     */   public void serverInit(ServerEvent arg0) throws HiException {
/* 200 */     if (("text/xml".equalsIgnoreCase(this.type)) || ("text/etf".equalsIgnoreCase(this.type)))
/*     */     {
/* 202 */       if (!(StringUtils.isBlank(this.name))) return;
/* 203 */       throw new HiException("213307", "name");
/*     */     }
/* 205 */     if ("msg".equalsIgnoreCase(this.type)) {
/* 206 */       if (!(StringUtils.isBlank(this.name))) return;
/* 207 */       throw new HiException("213307", "name");
/*     */     }
/*     */ 
/* 210 */     if (StringUtils.isNotBlank(this.deli)) {
/* 211 */       if (this.pos != 0) return;
/* 212 */       throw new HiException("213307", "pos");
/*     */     }
/*     */ 
/* 215 */     if ((this.offset == 0) || (this.length == 0))
/* 216 */       throw new HiException("213307", "offset|length");
/*     */   }
/*     */ }