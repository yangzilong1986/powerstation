/*     */ package com.hisun.atc;
/*     */ 
/*     */ import com.hisun.dispatcher.HiRouterOut;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import com.hisun.message.HiMessage;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiTransJnl
/*     */ {
/*     */   public String logNo;
/*     */   public String tranId;
/*     */   public String serNam;
/*     */   public String expSer;
/*     */   public long oprClk;
/*     */   public String actDat;
/*     */   public int itv;
/*     */   public int tmOut;
/*     */   public int timOut;
/*     */   public int falTms;
/*     */   public int maxTms;
/*     */   public String status;
/*     */   public String data;
/*     */ 
/*     */   public HiTransJnl()
/*     */   {
/*  40 */     this.oprClk = -1L;
/*     */ 
/*  50 */     this.itv = -1;
/*     */ 
/*  55 */     this.tmOut = -1;
/*     */ 
/*  60 */     this.timOut = -1;
/*     */ 
/*  65 */     this.falTms = -1;
/*     */ 
/*  70 */     this.maxTms = -1;
/*     */   }
/*     */ 
/*     */   public void setLogNo(String logNo)
/*     */   {
/*  81 */     this.logNo = logNo;
/*     */   }
/*     */ 
/*     */   public String getData()
/*     */   {
/*  91 */     return this.data;
/*     */   }
/*     */ 
/*     */   public void setData(String data)
/*     */   {
/*  96 */     this.data = data;
/*     */   }
/*     */ 
/*     */   public String getExpSer()
/*     */   {
/* 101 */     return this.expSer;
/*     */   }
/*     */ 
/*     */   public void setExpSer(String expSer)
/*     */   {
/* 106 */     this.expSer = expSer;
/*     */   }
/*     */ 
/*     */   public int getFalTms()
/*     */   {
/* 111 */     return this.falTms;
/*     */   }
/*     */ 
/*     */   public void setFalTms(int falTms)
/*     */   {
/* 116 */     this.falTms = falTms;
/*     */   }
/*     */ 
/*     */   public int getItv()
/*     */   {
/* 121 */     return this.itv;
/*     */   }
/*     */ 
/*     */   public void setItv(int itv)
/*     */   {
/* 126 */     this.itv = itv;
/*     */   }
/*     */ 
/*     */   public int getMaxTms()
/*     */   {
/* 131 */     return this.maxTms;
/*     */   }
/*     */ 
/*     */   public void setMaxTms(int maxTms)
/*     */   {
/* 136 */     this.maxTms = maxTms;
/*     */   }
/*     */ 
/*     */   public String getSerNam()
/*     */   {
/* 142 */     return this.serNam;
/*     */   }
/*     */ 
/*     */   public void setSerNam(String serNam)
/*     */   {
/* 147 */     this.serNam = serNam;
/*     */   }
/*     */ 
/*     */   public String getStatus()
/*     */   {
/* 152 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(String status)
/*     */   {
/* 157 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public int getTmOut()
/*     */   {
/* 162 */     return this.tmOut;
/*     */   }
/*     */ 
/*     */   public void setTmOut(int tmOut)
/*     */   {
/* 167 */     this.tmOut = tmOut;
/*     */   }
/*     */ 
/*     */   public int getTimOut()
/*     */   {
/* 172 */     return this.timOut;
/*     */   }
/*     */ 
/*     */   public void setTimOut(int tmOut)
/*     */   {
/* 177 */     this.timOut = tmOut;
/*     */   }
/*     */ 
/*     */   public String getTranId()
/*     */   {
/* 182 */     return this.tranId;
/*     */   }
/*     */ 
/*     */   public void setTranId(String tranId)
/*     */   {
/* 187 */     this.tranId = tranId;
/*     */   }
/*     */ 
/*     */   public HiMessage register() throws HiException
/*     */   {
/* 192 */     return invoke("BeginWork");
/*     */   }
/*     */ 
/*     */   public HiMessage invoke(String service) throws HiException {
/* 196 */     HiMessage msg = new HiMessage("S.RDOSVR", "DEFAULT");
/* 197 */     msg.setHeadItem("SCH", "rq");
/* 198 */     msg.setHeadItem("STC", service);
/* 199 */     msg.setHeadItem("STF", "1");
/* 200 */     HiETF root = HiETFFactory.createETF();
/* 201 */     msg.setBody(mapToETF(root));
/* 202 */     return HiRouterOut.syncProcess(msg);
/*     */   }
/*     */ 
/*     */   public HiETF mapToETF(HiETF root)
/*     */   {
/* 207 */     if (!(StringUtils.isEmpty(this.tranId)))
/*     */     {
/* 209 */       root.setChildValue("Tran_Id", this.tranId);
/*     */     }
/*     */ 
/* 212 */     if (!(StringUtils.isEmpty(this.serNam)))
/*     */     {
/* 214 */       root.setChildValue("Ser_Nm", this.serNam);
/*     */     }
/*     */ 
/* 217 */     if (!(StringUtils.isEmpty(this.expSer)))
/*     */     {
/* 219 */       root.setChildValue("Exp_Ser", this.expSer);
/*     */     }
/*     */ 
/* 222 */     if (this.oprClk != -1L)
/*     */     {
/* 224 */       root.setChildValue("Opr_Clk", String.valueOf(this.oprClk));
/*     */     }
/*     */ 
/* 232 */     if (this.itv != -1)
/*     */     {
/* 234 */       root.setChildValue("Itv", String.valueOf(this.itv));
/*     */     }
/*     */ 
/* 237 */     if (this.tmOut != -1)
/*     */     {
/* 239 */       root.setChildValue("Tm_Out", String.valueOf(this.tmOut));
/*     */     }
/*     */ 
/* 242 */     if (this.timOut != -1)
/*     */     {
/* 244 */       root.setChildValue("Tim_Out", String.valueOf(this.timOut));
/*     */     }
/*     */ 
/* 247 */     if (this.falTms != -1)
/*     */     {
/* 249 */       root.setChildValue("Fal_Tms", String.valueOf(this.falTms));
/*     */     }
/*     */ 
/* 252 */     if (this.maxTms != -1)
/*     */     {
/* 254 */       root.setChildValue("Max_Tms", String.valueOf(this.maxTms));
/*     */     }
/*     */ 
/* 257 */     if (StringUtils.isNotEmpty(this.logNo))
/*     */     {
/* 259 */       root.setChildValue("Log_No", this.logNo);
/*     */     }
/*     */ 
/* 262 */     if (!(StringUtils.isEmpty(this.status)))
/*     */     {
/* 264 */       root.setChildValue("STS", this.status);
/*     */     }
/*     */ 
/* 267 */     if (!(StringUtils.isEmpty(this.data)))
/*     */     {
/* 269 */       root.setChildValue("data", this.data);
/*     */     }
/* 271 */     return root;
/*     */   }
/*     */ 
/*     */   public String getActDat() {
/* 275 */     return this.actDat;
/*     */   }
/*     */ 
/*     */   public void setActDat(String actDat) {
/* 279 */     this.actDat = actDat;
/*     */   }
/*     */ }