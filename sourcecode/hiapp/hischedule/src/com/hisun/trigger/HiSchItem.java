 package com.hisun.trigger;
 
 import com.hisun.dispatcher.HiRouterOut;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFFactory;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiByteBuffer;
 import java.io.ByteArrayOutputStream;
 import java.util.Date;
 import javax.xml.transform.Transformer;
 import javax.xml.transform.TransformerFactory;
 import javax.xml.transform.dom.DOMSource;
 import javax.xml.transform.stream.StreamResult;
 import org.apache.commons.lang.StringUtils;
 import org.w3c.dom.Node;
 
 public class HiSchItem
 {
   private HiCronExpression cron = null;
 
   private String strRepeat_send = "0";
 
   private String strIstxn = null;
 
   private Date lastDate = null;
 
   private String strFuncName = null;
 
   private String strFuncpath = null;
 
   private String strHour = "*";
 
   private String strID = null;
 
   private String strMinute = "*";
 
   private String strMonitor = "0";
 
   private String strMonth = "*";
 
   private String strMonthday = "*";
 
   private String strMonthweek = "*";
 
   private String strMsgType = "PLTIN0";
 
   private String strObjSvr = null;
 
   private String strSndFlag = null;
 
   private String strTrace = "0";
 
   private String strTxncod = null;
 
   private String strTxnDat = null;
 
   private int tmOut = 30;
 
   private String strTxnDataType = "text/plain";
 
   private String strWeekday = "*";
   private String logId;
   private Node dataNode = null;
 
   private String dataText = null;
 
   public HiCronExpression getCronExpression()
   {
     return this.cron;
   }
 
   public Date getLastDate()
   {
     return this.lastDate;
   }
 
   public void init()
     throws HiException
   {
     this.cron = new HiCronExpression(this.strMinute, this.strHour, this.strMonthday, this.strMonth, this.strWeekday, this.strMonthweek);
 
     Logger log = HiTimeTrigger.log;
     if (log.isDebugEnabled())
       log.debug("SchItem info:" + this.cron);
   }
 
   public void setRepeat_send(String strRepeat_send)
   {
     if (StringUtils.isNotEmpty(strRepeat_send))
       this.strRepeat_send = strRepeat_send;
   }
 
   public boolean isRepeatSend()
   {
     return (!(this.strRepeat_send.equals("1")));
   }
 
   public void setFuncname(String strFuncName)
   {
     if (StringUtils.isNotEmpty(strFuncName))
       this.strFuncName = strFuncName;
   }
 
   public void setFuncpath(String strFuncpath)
   {
     if (StringUtils.isNotEmpty(strFuncpath))
       this.strFuncpath = strFuncpath;
   }
 
   public void setHour(String strHour)
   {
     if (StringUtils.isNotEmpty(strHour))
       this.strHour = strHour;
   }
 
   public void setId(String strID)
   {
     this.strID = strID;
   }
 
   public void setIstxn(String strIstxn)
   {
     this.strIstxn = strIstxn;
   }
 
   public void setLastDate(Date lastDate)
   {
     this.lastDate = lastDate;
   }
 
   public void setMinute(String strMinute)
   {
     if (StringUtils.isNotEmpty(strMinute))
       this.strMinute = strMinute;
   }
 
   public void setMonitor(String strMonitor)
   {
     this.strMonitor = strMonitor;
   }
 
   public void setMonth(String strMonth)
   {
     if (!(StringUtils.isNotEmpty(strMonth)))
       return;
     this.strMonth = strMonth;
   }
 
   public void setMonthday(String strMonthday)
   {
     if (StringUtils.isNotEmpty(strMonthday))
       this.strMonthday = strMonthday;
   }
 
   public void setMonthweek(String strMonthweek)
   {
     if (StringUtils.isNotEmpty(strMonthweek))
       this.strMonthweek = strMonthweek;
   }
 
   public void setMsgtype(String strMsgType)
   {
     this.strMsgType = strMsgType;
   }
 
   public void setObjsvr(String strObjSvr)
   {
     this.strObjSvr = strObjSvr;
   }
 
   public void setProperty(Object value)
     throws HiException
   {
     this.dataNode = ((Node)value);
     Node node = this.dataNode.getFirstChild();
     this.dataText = documentToString(node);
 
     if (HiTimeTrigger.log.isDebugEnabled())
       HiTimeTrigger.log.debug("DATE NODE[" + this.dataText + "]");
   }
 
   public String documentToString(Node doc)
   {
     try
     {
       Transformer transformer = newTransformer();
       DOMSource source = new DOMSource(doc);
       ByteArrayOutputStream sw = new ByteArrayOutputStream();
       StreamResult result = new StreamResult(sw);
       transformer.transform(source, result);
       sw.close();
       String xml = sw.toString();
       return xml;
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     return null;
   }
 
   private Transformer newTransformer()
   {
     try
     {
       Transformer transformer = TransformerFactory.newInstance().newTransformer();
 
       return transformer;
     }
     catch (Exception e)
     {
       e.printStackTrace(); }
     return null;
   }
 
   public void setSndflag(String strSndFlag)
   {
     this.strSndFlag = strSndFlag;
   }
 
   public void setTrace(String strTrace)
   {
     this.strTrace = strTrace;
   }
 
   public void setTxncod(String strTxncod)
   {
     this.strTxncod = strTxncod;
   }
 
   public void setTxndat(String strTxnDat)
   {
     this.strTxnDat = strTxnDat;
   }
 
   public void setTxndatatype(String strTxnDataType)
   {
     this.strTxnDataType = strTxnDataType;
   }
 
   public void setWeekday(String strWeekday)
   {
     if (StringUtils.isNotEmpty(strWeekday))
       this.strWeekday = strWeekday;
   }
 
   public void process(HiMessageContext ctx, Date currentDate)
     throws HiException
   {
     if ("1".equals(this.strSndFlag))
       return;
     Logger log = HiTimeTrigger.log;
     HiMessage mess = ctx.getCurrentMsg();
     if (StringUtils.isNotEmpty(this.logId)) {
       mess.setHeadItem("FID", this.logId);
     }
     mess.setHeadItem("SCH", "rq");
     mess.setHeadItem("ECT", this.strTxnDataType);
     mess.setHeadItem("STC", this.strTxncod);
     mess.setHeadItem("STF", this.strTrace);
     long l = currentDate.getTime();
     mess.setHeadItem("STM", new Long(l));
     mess.setHeadItem("ETM", new Long(l + this.tmOut * 1000));
 
     if (log.isDebugEnabled()) {
       log.debug("MsgType[" + this.strMsgType + "]");
     }
     if (StringUtils.isNotEmpty(this.strMsgType)) {
       mess.setType(this.strMsgType);
     }
     if (log.isDebugEnabled())
     {
       log.debug("istxn[" + this.strIstxn + "]");
       log.debug("setLastDate[" + currentDate + "]");
     }
 
     setLastDate(currentDate);
     if (this.strIstxn.equalsIgnoreCase("0"))
     {
       HiByteBuffer byteBuffer;
       mess.setHeadItem("SYN", "N");
 
       if (StringUtils.isNotEmpty(this.strObjSvr)) {
         mess.setHeadItem("SDT", this.strObjSvr);
       }
       if (log.isDebugEnabled()) {
         log.debug("txndatatype[" + this.strTxnDataType + "]");
       }
       if (this.strTxnDataType.equalsIgnoreCase("text/xml"))
       {
         if (this.dataText != null)
         {
           byteBuffer = new HiByteBuffer(this.dataText.getBytes());
 
           mess.setBody(byteBuffer);
         }
       }
       else if (this.strTxnDataType.equalsIgnoreCase("text/etf"))
       {
         if (this.dataText != null)
         {
           HiETF etf = HiETFFactory.createETF(this.dataText);
           mess.setBody(etf);
         }
 
       }
       else if (this.strTxnDat != null)
       {
         byteBuffer = new HiByteBuffer(this.strTxnDat.getBytes());
 
         mess.setBody(byteBuffer);
       }
 
       HiRouterOut.process(ctx);
 
       if (!(this.strMonitor.equalsIgnoreCase("1"))) {
         return;
       }
 
     }
     else
     {
       HiSchUtilites.execFunction(this.strFuncpath, this.strFuncName);
     }
   }
 
   public int getTmout()
   {
     return this.tmOut;
   }
 
   public void setTmout(int tmOut)
   {
     this.tmOut = tmOut;
   }
 
   public String getLogId() {
     return this.logId;
   }
 
   public void setLogId(String logId)
   {
     this.logId = logId;
   }
 }