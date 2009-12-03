/*     */ package com.hisun.cnaps.model;
/*     */ 
/*     */ import com.hisun.cnaps.HiCnapsCodeTable;
/*     */ import com.hisun.cnaps.messages.HiCnapsMessage;
/*     */ import com.hisun.cnaps.messages.HiCnapsMessageArea;
/*     */ import com.hisun.engine.HiEngineModel;
/*     */ import com.hisun.engine.invoke.impl.HiItemHelper;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.message.HiMessageHelper;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiCnapsModel extends HiEngineModel
/*     */ {
/*     */   static final String NAME = "Tag";
/*     */   private String mustField;
/*     */   private String optFiled;
/*     */   private HiCnapsCodeTable codeTable;
/*  31 */   final Logger log = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
/*     */ 
/*  33 */   final HiStringManager sm = HiStringManager.getManager();
/*     */ 
/*     */   public String getNodeName()
/*     */   {
/*  40 */     return "Tag";
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx) throws HiException {
/*  44 */     HiMessage msg = ctx.getCurrentMsg();
/*  45 */     StringTokenizer m_st = new StringTokenizer(this.mustField, "\\|");
/*  46 */     String[] packFields = new String[m_st.countTokens()];
/*  47 */     String[] optFileds = null;
/*  48 */     for (int i = 0; m_st.hasMoreTokens(); ++i)
/*  49 */       packFields[i] = m_st.nextToken();
/*  50 */     if (StringUtils.isNotBlank(this.optFiled)) {
/*  51 */       StringTokenizer o_st = new StringTokenizer(this.optFiled, "\\|");
/*  52 */       for (int p = 0; o_st.hasMoreTokens(); ++p) {
/*  53 */         optFileds[p] = o_st.nextToken();
/*     */       }
/*     */     }
/*  56 */     if (!(HiMessageHelper.isInnerMessage(msg)))
/*     */     {
/*  58 */       String plianText = HiItemHelper.getPlainText(msg).toString();
/*  59 */       if (this.log.isInfoEnabled()) {
/*  60 */         this.log.info(this.sm.getString("HiCnapsHandler.unpack"));
/*     */       }
/*  62 */       HiCnapsMessage cnapsMessage = (HiCnapsMessage)HiContext.getCurrentContext().getProperty("cnaps_message");
/*     */ 
/*  65 */       int batchNo = 1;
/*  66 */       if (cnapsMessage == null) {
/*  67 */         cnapsMessage = new HiCnapsMessage(this.codeTable);
/*  68 */         cnapsMessage.unpack(plianText);
/*     */       }
/*     */       else {
/*  71 */         batchNo = ((Integer)HiContext.getCurrentContext().getProperty("cnpas_batch")).intValue() + 1;
/*     */       }
/*     */ 
/*  75 */       HiCnapsMessageArea body = cnapsMessage.getMessageBusArea(batchNo);
/*  76 */       if (body == null) {
/*  77 */         throw new HiException("241102");
/*     */       }
/*  79 */       HiETF etf = getCurrLevelETF(msg);
/*  80 */       body.unpack2Etf(packFields, etf, true);
/*  81 */       if (optFileds != null) {
/*  82 */         body.unpack2Etf(optFileds, etf, false);
/*     */       }
/*     */ 
/*  85 */       HiContext.getCurrentContext().setProperty("cnpas_batch", new Integer(batchNo));
/*     */     }
/*     */     else
/*     */     {
/*  90 */       if (this.log.isInfoEnabled())
/*  91 */         this.log.info(this.sm.getString("HiCnapsHandler.unpack"));
/*  92 */       HiCnapsMessage cnapsMessage = (HiCnapsMessage)HiContext.getCurrentContext().getProperty("cnaps_message");
/*     */ 
/*  95 */       if (cnapsMessage == null) {
/*  96 */         cnapsMessage = new HiCnapsMessage(this.codeTable);
/*     */       }
/*  98 */       HiETF etf = getCurrLevelETF(msg);
/*  99 */       HiCnapsMessageArea area = cnapsMessage.createMessageBodyArea(packFields, optFileds, etf);
/*     */ 
/* 101 */       HiItemHelper.getPlainText(msg).append(area.getString());
/* 102 */       HiContext.getCurrentContext().setProperty("cnaps_message", cnapsMessage);
/*     */     }
/*     */   }
/*     */ 
/*     */   public HiETF getCurrLevelETF(HiMessage msg)
/*     */   {
/* 109 */     String currLevel = HiItemHelper.getCurEtfLevel(msg);
/* 110 */     if (StringUtils.isEmpty(currLevel)) {
/* 111 */       return msg.getETFBody();
/*     */     }
/* 113 */     return msg.getETFBody().getGrandChildNode(currLevel.substring(0, currLevel.length() - 1));
/*     */   }
/*     */ 
/*     */   public void initCodeTable(HiCnapsCodeTable codeTable)
/*     */   {
/* 120 */     this.codeTable = codeTable;
/*     */   }
/*     */ 
/*     */   public void setMust_fields(String fields)
/*     */   {
/* 126 */     this.mustField = fields;
/*     */   }
/*     */ 
/*     */   public void setOpt_fileds(String fields) {
/* 130 */     this.optFiled = fields;
/*     */   }
/*     */ 
/*     */   public void loadAfter() throws HiException {
/* 134 */     if (this.log.isInfoEnabled())
/* 135 */       this.log.info(this.sm.getString("HiCnapsModell.loadAfter"));
/* 136 */     if (StringUtils.isBlank(this.mustField)) {
/* 137 */       throw new HiException("241097", "");
/*     */     }
/* 139 */     this.codeTable = ((HiCnapsCodeTable)HiContext.getCurrentContext().getProperty("cnaps_code"));
/*     */   }
/*     */ 
/*     */   public static void main(String[] args1)
/*     */   {
/*     */   }
/*     */ }