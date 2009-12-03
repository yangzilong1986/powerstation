/*      */ package com.hisun.message;
/*      */ 
/*      */ import com.hisun.exception.HiException;
/*      */ import com.hisun.util.HiByteBuffer;
/*      */ import java.io.Serializable;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ 
/*      */ public class HiMessage
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 1509358588794274761L;
/*      */   public static final String DEFAULT_ENCODING = "UTF-8";
/*      */   public static final String MESSAGE_TYPE_MNG = "SYSMNG";
/*      */   public static final String REQUEST_RESPONSE = "SCH";
/*      */   public static final String TYPE_RESPONSE = "rp";
/*      */   public static final String TYPE_REQUEST = "rq";
/*      */   public static final String TEXT_TYPE = "ECT";
/*      */   public static final String TEXT_TYPE_XML = "text/xml";
/*      */   public static final String TEXT_TYPE_PLAIN = "text/plain";
/*      */   public static final String TEXT_TYPE_ETF = "text/etf";
/*      */   public static final String SSC = "SSC";
/*      */   public static final String CMD = "CMD";
/*      */   public static final String SRT = "SRT";
/*      */   public static final String SDT = "SDT";
/*      */   public static final String MON = "MON";
/*      */   public static final String SRN = "SRN";
/*      */   public static final String SIP = "SIP";
/*      */   public static final String OIP = "OIP";
/*      */   public static final String OPT = "OPT";
/*      */   public static final String SFD = "SFD";
/*      */   public static final String SKY = "SKY";
/*      */   public static final String SNC = "SNC";
/*      */   public static final String STC = "STC";
/*      */   public static final String PAS = "PAS";
/*      */   public static final String APP = "APP";
/*      */   public static final String STM = "STM";
/*      */   public static final String ETM = "ETM";
/*      */   public static final String TMO = "TMO";
/*      */   public static final String NTF = "NTF";
/*      */   public static final String STF = "STF";
/*      */   public static final String OFN = "OFN";
/*      */   public static final String LSH = "LSH";
/*      */   public static final String BCH = "BCH";
/*      */   public static final String SUB = "SUB";
/*      */   public static final String TLR = "TLR";
/*      */   public static final String TRAN_RDO = "RDO";
/*      */   public static final String OLD = "OLD";
/*      */   public static final String NEW = "NEW";
/*      */   public static final String SID = "SID";
/*      */   public static final String STP = "STP";
/*      */   public static final String SPI = "SPI";
/*      */   public static final String SSP = "SSP";
/*      */   public static final String SMD = "SMD";
/*      */   public static final String SSZ = "SSZ";
/*      */   public static final String RSP = "RSP";
/*      */   public static final String FID = "FID";
/*      */   public static final int FLD_NAME_LEN = 3;
/*      */   public static final int SRV_NAME_LEN = 8;
/*      */   protected String requestId;
/*      */   protected String type;
/*      */   protected String priority;
/*      */   protected String splited;
/*      */   protected String msgId;
/*      */   protected String server;
/*  295 */   protected String status = "N";
/*      */   protected ConcurrentHashMap head;
/*      */   protected Object body;
/*      */ 
/*      */   public HiMessage()
/*      */   {
/*  312 */     this.head = new ConcurrentHashMap();
/*      */   }
/*      */ 
/*      */   public HiMessage(String server, String type)
/*      */   {
/*  326 */     this.server = server;
/*  327 */     this.requestId = createRequestId(server);
/*  328 */     this.type = type;
/*      */ 
/*  330 */     this.head = new ConcurrentHashMap();
/*      */   }
/*      */ 
/*      */   public HiMessage(String server, String type, Object body)
/*      */   {
/*  346 */     this.server = server;
/*  347 */     this.requestId = createRequestId(server);
/*  348 */     this.type = type;
/*  349 */     this.body = body;
/*      */ 
/*  351 */     this.head = new ConcurrentHashMap();
/*      */   }
/*      */ 
/*      */   public HiMessage(HiMessage hiMsg)
/*      */   {
/*  361 */     this.requestId = hiMsg.requestId;
/*  362 */     this.type = hiMsg.type;
/*  363 */     this.msgId = hiMsg.msgId;
/*  364 */     this.priority = hiMsg.priority;
/*  365 */     this.splited = hiMsg.splited;
/*  366 */     this.server = hiMsg.server;
/*  367 */     this.status = hiMsg.status;
/*  368 */     this.head = new ConcurrentHashMap();
/*  369 */     Iterator headIt = hiMsg.head.entrySet().iterator();
/*      */ 
/*  373 */     LinkedList valList = null; LinkedList newValList = null;
/*      */ 
/*  375 */     while (headIt.hasNext())
/*      */     {
/*  377 */       Map.Entry headEntry = (Map.Entry)headIt.next();
/*      */ 
/*  379 */       valList = (LinkedList)headEntry.getValue();
/*  380 */       newValList = (LinkedList)valList.clone();
/*      */ 
/*  382 */       this.head.put(headEntry.getKey(), newValList);
/*      */     }
/*  384 */     this.body = hiMsg.body;
/*      */   }
/*      */ 
/*      */   public HiMessage(String msgStr)
/*      */     throws HiException
/*      */   {
/*  394 */     if (StringUtils.isEmpty(msgStr))
/*      */     {
/*  396 */       return;
/*      */     }
/*  398 */     this.head = new ConcurrentHashMap();
/*      */ 
/*  402 */     int pos = 0;
/*  403 */     int headIdx = msgStr.indexOf(10);
/*  404 */     int bodyLen = 0;
/*  405 */     if (headIdx == -1)
/*      */     {
/*  407 */       headStr = msgStr + '\t';
/*      */     }
/*      */     else
/*      */     {
/*  411 */       headStr = msgStr.substring(0, headIdx) + '\t';
/*  412 */       if (msgStr.length() > headIdx + 1)
/*      */       {
/*  414 */         this.body = msgStr.substring(headIdx + 1);
/*      */       }
/*      */     }
/*      */ 
/*  418 */     headIdx = headStr.indexOf(9);
/*  419 */     while (headIdx != -1)
/*      */     {
/*  421 */       String fld = headStr.substring(pos, headIdx);
/*  422 */       if (fld.length() < 3)
/*      */       {
/*  424 */         throw new HiException("215014", String.valueOf(3), fld, msgStr);
/*      */       }
/*  426 */       String fldNam = fld.substring(0, 3);
/*      */ 
/*  428 */       if (fldNam.equals("SID"))
/*      */       {
/*  430 */         this.requestId = fld.substring(3);
/*  431 */         if (this.requestId.length() > 8)
/*      */         {
/*  433 */           this.server = this.requestId.substring(0, 8);
/*      */         }
/*      */         else
/*      */         {
/*  437 */           this.server = this.requestId;
/*      */         }
/*      */       }
/*  440 */       else if (fldNam.equals("STP"))
/*      */       {
/*  442 */         this.type = fld.substring(3);
/*      */       }
/*  444 */       else if (fldNam.equals("SPI"))
/*      */       {
/*  446 */         this.priority = fld.substring(3);
/*      */       }
/*  448 */       else if (fldNam.equals("SSP"))
/*      */       {
/*  450 */         this.splited = fld.substring(3);
/*      */       }
/*  452 */       else if (fldNam.equals("SMD"))
/*      */       {
/*  454 */         this.msgId = fld.substring(3);
/*      */       }
/*      */       else
/*      */       {
/*  458 */         setHeadItem(fldNam, fld.substring(3));
/*      */       }
/*  460 */       pos = headIdx + 1;
/*  461 */       headIdx = headStr.indexOf(9, pos);
/*      */     }
/*      */ 
/*  464 */     if (this.body == null)
/*      */       return;
/*  466 */     String bodyType = getHeadItem("ECT");
/*  467 */     if (StringUtils.equalsIgnoreCase(bodyType, "text/plain")) {
/*  468 */       setBody(new HiByteBuffer(((String)this.body).getBytes()));
/*      */     }
/*      */     else
/*      */     {
/*  472 */       this.body = HiETFFactory.createETF((String)this.body);
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getHeadItem(String fldName)
/*      */   {
/*  485 */     LinkedList valList = (LinkedList)this.head.get(fldName);
/*  486 */     if ((valList == null) || (valList.size() == 0))
/*      */     {
/*  488 */       return null;
/*      */     }
/*  490 */     return ((String)valList.getLast());
/*      */   }
/*      */ 
/*      */   public String getHeadItemRoot(String fldName)
/*      */   {
/*  502 */     LinkedList valList = (LinkedList)this.head.get(fldName);
/*  503 */     if ((valList == null) || (valList.size() == 0))
/*      */     {
/*  505 */       return null;
/*      */     }
/*  507 */     return ((String)valList.getFirst());
/*      */   }
/*      */ 
/*      */   public Object getObjectHeadItem(String fldName)
/*      */   {
/*  517 */     LinkedList valList = (LinkedList)this.head.get(fldName);
/*  518 */     if ((valList == null) || (valList.size() == 0))
/*      */     {
/*  520 */       return null;
/*      */     }
/*  522 */     return valList.getLast();
/*      */   }
/*      */ 
/*      */   public void addHeadItem(String fldName, Object fldValue)
/*      */   {
/*  544 */     List valList = (List)this.head.get(fldName);
/*  545 */     if (valList == null)
/*      */     {
/*  547 */       valList = new LinkedList();
/*  548 */       valList.add(fldValue);
/*  549 */       this.head.put(fldName, valList);
/*      */     }
/*      */     else
/*      */     {
/*  553 */       valList.add(fldValue);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setHeadItem(String fldName, Object fldValue)
/*      */   {
/*  567 */     long endit = 0L; long it = System.currentTimeMillis();
/*      */ 
/*  569 */     List valList = (LinkedList)this.head.get(fldName);
/*  570 */     if (valList == null)
/*      */     {
/*  572 */       valList = new LinkedList();
/*  573 */       valList.add(fldValue);
/*  574 */       this.head.put(fldName, valList);
/*      */     }
/*  576 */     else if (valList.size() == 0)
/*      */     {
/*  578 */       valList.add(fldValue);
/*      */     }
/*      */     else
/*      */     {
/*  582 */       valList.set(valList.size() - 1, fldValue);
/*      */     }
/*      */ 
/*  585 */     endit = System.currentTimeMillis();
/*  586 */     if (endit - it <= 200L)
/*      */       return;
/*      */   }
/*      */ 
/*      */   public boolean hasHeadItem(String fldName)
/*      */   {
/*  608 */     return this.head.containsKey(fldName);
/*      */   }
/*      */ 
/*      */   public int getHeadItemValSize(String fldName)
/*      */   {
/*  620 */     List valList = (List)this.head.get(fldName);
/*  621 */     if (valList == null)
/*      */     {
/*  623 */       return 0;
/*      */     }
/*      */ 
/*  627 */     return valList.size();
/*      */   }
/*      */ 
/*      */   public void delHeadItem(String fldName)
/*      */   {
/*  639 */     List valList = (LinkedList)this.head.get(fldName);
/*  640 */     if (valList != null)
/*      */     {
/*  642 */       valList.clear();
/*      */     }
/*  644 */     this.head.remove(fldName);
/*      */   }
/*      */ 
/*      */   public Object delHeadItemVal(String fldName)
/*      */   {
/*  668 */     List valList = (List)this.head.get(fldName);
/*  669 */     if ((valList == null) || (valList.size() == 0))
/*      */     {
/*  679 */       return null;
/*      */     }
/*      */ 
/*  690 */     return ((LinkedList)valList).removeLast();
/*      */   }
/*      */ 
/*      */   public void setHead(ConcurrentHashMap head)
/*      */   {
/*  699 */     this.head = head;
/*      */   }
/*      */ 
/*      */   public ConcurrentHashMap getHead()
/*      */   {
/*  708 */     return this.head;
/*      */   }
/*      */ 
/*      */   public void clearHead()
/*      */   {
/*  717 */     List valList = null;
/*  718 */     Iterator it = this.head.values().iterator();
/*  719 */     while (it.hasNext())
/*      */     {
/*  721 */       valList = (List)it.next();
/*  722 */       valList.clear();
/*      */     }
/*      */ 
/*  725 */     this.head.clear();
/*      */   }
/*      */ 
/*      */   public Object getBody()
/*      */   {
/*  744 */     return this.body;
/*      */   }
/*      */ 
/*      */   public void setBody(Object body)
/*      */   {
/*  755 */     this.body = body;
/*      */   }
/*      */ 
/*      */   public String getType()
/*      */   {
/*  765 */     return this.type;
/*      */   }
/*      */ 
/*      */   public void setType(String type)
/*      */   {
/*  776 */     this.type = type;
/*      */   }
/*      */ 
/*      */   public String getStatus()
/*      */   {
/*  788 */     return this.status;
/*      */   }
/*      */ 
/*      */   public void setStatus(String status)
/*      */   {
/*  801 */     this.status = status;
/*      */   }
/*      */ 
/*      */   public boolean isNormal()
/*      */   {
/*  810 */     return this.status.equalsIgnoreCase("N");
/*      */   }
/*      */ 
/*      */   public String getRequestId()
/*      */   {
/*  820 */     return this.requestId;
/*      */   }
/*      */ 
/*      */   public String genRequestId(String id)
/*      */   {
/*  830 */     return createRequestId(id);
/*      */   }
/*      */ 
/*      */   public void setRequestId(String requestId)
/*      */   {
/*  840 */     this.requestId = requestId;
/*      */   }
/*      */ 
/*      */   public HiMessage cloneNoBody()
/*      */   {
/*  849 */     HiMessage cloneMsg = new HiMessage();
/*      */ 
/*  851 */     cloneMsg.requestId = this.requestId;
/*  852 */     cloneMsg.type = this.type;
/*  853 */     cloneMsg.msgId = this.msgId;
/*  854 */     cloneMsg.priority = this.priority;
/*  855 */     cloneMsg.splited = this.splited;
/*  856 */     cloneMsg.server = this.server;
/*  857 */     cloneMsg.status = this.status;
/*  858 */     cloneMsg.head = new ConcurrentHashMap();
/*      */ 
/*  860 */     Iterator headIt = this.head.entrySet().iterator();
/*      */ 
/*  864 */     LinkedList valList = null; LinkedList newValList = null;
/*      */ 
/*  866 */     while (headIt.hasNext())
/*      */     {
/*  868 */       Map.Entry headEntry = (Map.Entry)headIt.next();
/*      */ 
/*  870 */       valList = (LinkedList)headEntry.getValue();
/*  871 */       newValList = (LinkedList)valList.clone();
/*      */ 
/*  873 */       cloneMsg.head.put(headEntry.getKey(), newValList);
/*      */     }
/*      */ 
/*  876 */     cloneMsg.body = null;
/*      */ 
/*  878 */     return cloneMsg;
/*      */   }
/*      */ 
/*      */   public HiMessage cloneNoBodyAndRqID()
/*      */   {
/*  883 */     HiMessage cloneMsg = cloneNoBody();
/*      */ 
/*  885 */     cloneMsg.requestId = createRequestId(this.server);
/*      */ 
/*  887 */     return cloneMsg;
/*      */   }
/*      */ 
/*      */   public HiETF getETFBody()
/*      */   {
/*  896 */     return ((HiETF)this.body);
/*      */   }
/*      */ 
/*      */   public String toString()
/*      */   {
/*  941 */     StringBuffer msgbuf = new StringBuffer();
/*      */ 
/*  943 */     msgbuf.append("SID");
/*  944 */     msgbuf.append(this.requestId);
/*  945 */     msgbuf.append("\t");
/*  946 */     msgbuf.append("STP");
/*  947 */     msgbuf.append(this.type);
/*  948 */     msgbuf.append("\t");
/*      */ 
/*  950 */     msgbuf.append("SSZ");
/*  951 */     if (this.body != null)
/*      */     {
/*  953 */       msgbuf.append(StringUtils.leftPad(String.valueOf(this.body.toString().length()), 12, "0"));
/*      */     }
/*      */     else
/*      */     {
/*  957 */       msgbuf.append(StringUtils.repeat("0", 12));
/*      */     }
/*  959 */     msgbuf.append("\t");
/*      */ 
/*  962 */     if (this.head != null)
/*      */     {
/*  964 */       Iterator headIt = this.head.entrySet().iterator();
/*      */ 
/*  967 */       LinkedList valList = null;
/*      */ 
/*  970 */       while (headIt.hasNext())
/*      */       {
/*  972 */         Map.Entry headEntry = (Map.Entry)headIt.next();
/*      */ 
/*  975 */         valList = (LinkedList)headEntry.getValue();
/*  976 */         if (valList.isEmpty())
/*      */           continue;
/*  978 */         if (StringUtils.equals((String)headEntry.getKey(), "SSZ"))
/*      */           continue;
/*  980 */         msgbuf.append(headEntry.getKey());
/*      */ 
/*  982 */         msgbuf.append(String.valueOf(valList.getLast()));
/*      */ 
/*  984 */         msgbuf.append("\t");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  989 */     msgbuf.setCharAt(msgbuf.length() - 1, '\n');
/*      */ 
/*  991 */     if (this.body != null)
/*      */     {
/*  993 */       msgbuf.append(this.body);
/*      */     }
/*  995 */     return msgbuf.toString();
/*      */   }
/*      */ 
/*      */   private String createRequestId(String server)
/*      */   {
/* 1007 */     return HiMsgIdManager.getRequestId(server);
/*      */   }
/*      */ }