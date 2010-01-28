 package com.hisun.atc;
 
 import com.hisun.dispatcher.HiRouterOut;
 import com.hisun.exception.HiException;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFFactory;
 import com.hisun.message.HiMessage;
 import org.apache.commons.lang.StringUtils;
 
 public class HiTransJnl
 {
   public String logNo;
   public String tranId;
   public String serNam;
   public String expSer;
   public long oprClk;
   public String actDat;
   public int itv;
   public int tmOut;
   public int timOut;
   public int falTms;
   public int maxTms;
   public String status;
   public String data;
 
   public HiTransJnl()
   {
     this.oprClk = -1L;
 
     this.itv = -1;
 
     this.tmOut = -1;
 
     this.timOut = -1;
 
     this.falTms = -1;
 
     this.maxTms = -1;
   }
 
   public void setLogNo(String logNo)
   {
     this.logNo = logNo;
   }
 
   public String getData()
   {
     return this.data;
   }
 
   public void setData(String data)
   {
     this.data = data;
   }
 
   public String getExpSer()
   {
     return this.expSer;
   }
 
   public void setExpSer(String expSer)
   {
     this.expSer = expSer;
   }
 
   public int getFalTms()
   {
     return this.falTms;
   }
 
   public void setFalTms(int falTms)
   {
     this.falTms = falTms;
   }
 
   public int getItv()
   {
     return this.itv;
   }
 
   public void setItv(int itv)
   {
     this.itv = itv;
   }
 
   public int getMaxTms()
   {
     return this.maxTms;
   }
 
   public void setMaxTms(int maxTms)
   {
     this.maxTms = maxTms;
   }
 
   public String getSerNam()
   {
     return this.serNam;
   }
 
   public void setSerNam(String serNam)
   {
     this.serNam = serNam;
   }
 
   public String getStatus()
   {
     return this.status;
   }
 
   public void setStatus(String status)
   {
     this.status = status;
   }
 
   public int getTmOut()
   {
     return this.tmOut;
   }
 
   public void setTmOut(int tmOut)
   {
     this.tmOut = tmOut;
   }
 
   public int getTimOut()
   {
     return this.timOut;
   }
 
   public void setTimOut(int tmOut)
   {
     this.timOut = tmOut;
   }
 
   public String getTranId()
   {
     return this.tranId;
   }
 
   public void setTranId(String tranId)
   {
     this.tranId = tranId;
   }
 
   public HiMessage register() throws HiException
   {
     return invoke("BeginWork");
   }
 
   public HiMessage invoke(String service) throws HiException {
     HiMessage msg = new HiMessage("S.RDOSVR", "DEFAULT");
     msg.setHeadItem("SCH", "rq");
     msg.setHeadItem("STC", service);
     msg.setHeadItem("STF", "1");
     HiETF root = HiETFFactory.createETF();
     msg.setBody(mapToETF(root));
     return HiRouterOut.syncProcess(msg);
   }
 
   public HiETF mapToETF(HiETF root)
   {
     if (!(StringUtils.isEmpty(this.tranId)))
     {
       root.setChildValue("Tran_Id", this.tranId);
     }
 
     if (!(StringUtils.isEmpty(this.serNam)))
     {
       root.setChildValue("Ser_Nm", this.serNam);
     }
 
     if (!(StringUtils.isEmpty(this.expSer)))
     {
       root.setChildValue("Exp_Ser", this.expSer);
     }
 
     if (this.oprClk != -1L)
     {
       root.setChildValue("Opr_Clk", String.valueOf(this.oprClk));
     }
 
     if (this.itv != -1)
     {
       root.setChildValue("Itv", String.valueOf(this.itv));
     }
 
     if (this.tmOut != -1)
     {
       root.setChildValue("Tm_Out", String.valueOf(this.tmOut));
     }
 
     if (this.timOut != -1)
     {
       root.setChildValue("Tim_Out", String.valueOf(this.timOut));
     }
 
     if (this.falTms != -1)
     {
       root.setChildValue("Fal_Tms", String.valueOf(this.falTms));
     }
 
     if (this.maxTms != -1)
     {
       root.setChildValue("Max_Tms", String.valueOf(this.maxTms));
     }
 
     if (StringUtils.isNotEmpty(this.logNo))
     {
       root.setChildValue("Log_No", this.logNo);
     }
 
     if (!(StringUtils.isEmpty(this.status)))
     {
       root.setChildValue("STS", this.status);
     }
 
     if (!(StringUtils.isEmpty(this.data)))
     {
       root.setChildValue("data", this.data);
     }
     return root;
   }
 
   public String getActDat() {
     return this.actDat;
   }
 
   public void setActDat(String actDat) {
     this.actDat = actDat;
   }
 }