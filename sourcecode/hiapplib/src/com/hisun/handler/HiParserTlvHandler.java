/*     */ package com.hisun.handler;
/*     */ 
/*     */ import com.hisun.cfg.HiLoadTlvCfg;
/*     */ import com.hisun.cfg.HiTlvHelper;
/*     */ import com.hisun.cfg.HiTlvItem;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.framework.event.IServerInitListener;
/*     */ import com.hisun.framework.event.ServerEvent;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.pubinterface.IHandler;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import com.hisun.util.HiResource;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.SAXReader;
/*     */ 
/*     */ public class HiParserTlvHandler
/*     */   implements IHandler, IServerInitListener
/*     */ {
/*     */   private String cfgFile;
/*     */   private String pcfgFile;
/*     */   private Map cfgMap;
/*     */   private HiTlvItem rootTlv;
/*     */   final Logger log;
/*     */   final HiStringManager sm;
/*     */ 
/*     */   public HiParserTlvHandler()
/*     */   {
/*  43 */     this.cfgFile = null;
/*     */ 
/*  47 */     this.pcfgFile = null;
/*     */ 
/*  49 */     this.cfgMap = new HashMap();
/*  50 */     this.rootTlv = new HiTlvItem();
/*     */ 
/*  52 */     this.log = ((Logger)HiContext.getCurrentContext().getProperty("SVR.log"));
/*     */ 
/*  55 */     this.sm = HiStringManager.getManager(); }
/*     */ 
/*     */   public void setCFG(String cfgFile) {
/*  58 */     this.cfgFile = cfgFile;
/*     */   }
/*     */ 
/*     */   public void setPCFG(String pcfgFile) {
/*  62 */     this.pcfgFile = pcfgFile;
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  68 */     HiMessage msg = ctx.getCurrentMsg();
/*  69 */     HiByteBuffer plainBytes = (HiByteBuffer)msg.getBody();
/*     */ 
/*  71 */     if (this.log.isInfoEnabled())
/*     */     {
/*  73 */       this.log.info(this.sm.getString("HiParserTlvHandler.parser0"));
/*  74 */       this.log.info(this.sm.getString("HiParserTlvHandler.parser1", plainBytes.toString()));
/*     */     }
/*     */ 
/*  78 */     HiETF etfBody = HiETFFactory.createXmlETF();
/*     */ 
/*  80 */     ByteArrayInputStream in = new ByteArrayInputStream(plainBytes.getBytes());
/*     */ 
/*  83 */     convertToETF(ctx, in, etfBody, this.cfgMap);
/*     */ 
/*  85 */     msg.setBody(etfBody);
/*     */ 
/*  87 */     if (!(this.log.isInfoEnabled()))
/*     */       return;
/*  89 */     this.log.info(this.sm.getString("HiParserTlvHandler.parsered"));
/*     */   }
/*     */ 
/*     */   private void convertToETF(HiMessageContext ctx, ByteArrayInputStream in, HiETF etfBody, Map cfgMap)
/*     */     throws HiException
/*     */   {
/*  97 */     byte[] retBytes = null;
/*  98 */     retBytes = new byte[this.rootTlv.tag_len];
/*     */ 
/* 101 */     while ((ret = in.read(retBytes, 0, this.rootTlv.tag_len)) != -1)
/*     */     {
/*     */       int ret;
/* 103 */       if (ret < this.rootTlv.tag_len) {
/* 104 */         throw new HiException("231506", String.valueOf(this.rootTlv.tag_len));
/*     */       }
/*     */ 
/* 108 */       makeEtfItem(ctx, in, etfBody, HiTlvHelper.getTag(retBytes, this.rootTlv.tag_type), cfgMap);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void makeEtfItem(HiMessageContext ctx, ByteArrayInputStream in, HiETF etfBody, String tag, Map cfgMap)
/*     */     throws HiException
/*     */   {
/* 116 */     if (this.log.isInfoEnabled()) {
/* 117 */       this.log.info(this.sm.getString("HiParserTlvHandler.parserItem0", tag));
/*     */     }
/*     */ 
/* 120 */     HiTlvItem item = (HiTlvItem)cfgMap.get(tag);
/* 121 */     if (item == null)
/*     */     {
/* 123 */       throw new HiException("231507", tag);
/*     */     }
/*     */ 
/* 126 */     item.unPack(ctx, cfgMap, in, etfBody, this.log);
/*     */   }
/*     */ 
/*     */   public void serverInit(ServerEvent arg0) throws HiException
/*     */   {
/* 131 */     if (this.log.isInfoEnabled()) {
/* 132 */       this.log.info(this.sm.getString("HiParserTlvHandler.init1", this.cfgFile));
/*     */     }
/*     */ 
/* 135 */     Element pcfgRoot = null;
/* 136 */     Element cfgRoot = null;
/* 137 */     URL fileUrl = HiResource.getResource(this.cfgFile);
/* 138 */     SAXReader saxReader = new SAXReader();
/*     */     try
/*     */     {
/* 141 */       cfgRoot = saxReader.read(fileUrl).getRootElement();
/*     */ 
/* 144 */       if (!(StringUtils.isEmpty(this.pcfgFile))) {
/* 145 */         fileUrl = HiResource.getResource(this.pcfgFile);
/*     */ 
/* 147 */         pcfgRoot = saxReader.read(fileUrl).getRootElement();
/*     */       }
/*     */     }
/*     */     catch (DocumentException e) {
/* 151 */       throw new HiException("213319", fileUrl.getFile(), e);
/*     */     }
/*     */ 
/* 156 */     HiLoadTlvCfg.load(cfgRoot, this.cfgMap, this.rootTlv);
/*     */ 
/* 159 */     if (this.rootTlv.tag_len <= 0)
/*     */     {
/* 161 */       throw new HiException("231504", "tag_len");
/*     */     }
/*     */ 
/* 164 */     if (pcfgRoot != null) {
/* 165 */       Map pcfgMap = new HashMap();
/* 166 */       HiTlvItem prootTlv = new HiTlvItem();
/*     */ 
/* 168 */       HiLoadTlvCfg.load(pcfgRoot, pcfgMap, prootTlv);
/*     */ 
/* 171 */       HiContext.getCurrentContext().setProperty("TLV_CFG_NODE", pcfgMap);
/*     */     }
/*     */     else
/*     */     {
/* 175 */       HiContext.getCurrentContext().setProperty("TLV_CFG_NODE", this.cfgMap);
/*     */     }
/*     */ 
/* 178 */     if (this.log.isInfoEnabled())
/* 179 */       this.log.info(this.sm.getString("HiParserTlvHandler.init2", this.cfgFile));
/*     */   }
/*     */ }