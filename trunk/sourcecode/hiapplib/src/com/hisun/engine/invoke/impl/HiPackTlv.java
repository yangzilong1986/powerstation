 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.exception.HiException;
 import com.hisun.exception.HiSysException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiConvHelper;
 import com.hisun.util.HiStringManager;
 import com.hisun.util.HiXmlHelper;
 import java.io.ByteArrayOutputStream;
 import java.util.HashMap;
 import java.util.HashSet;
 import java.util.Iterator;
 import java.util.Map;
 import java.util.Map.Entry;
 import java.util.Set;
 import java.util.StringTokenizer;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Element;
 
 public class HiPackTlv extends HiEngineModel
 {
   private String field_id;
   private String must_fields;
   private String opt_fields;
   private boolean isInit;
   private Map tlvNodeMap;
   private HashSet mustSet;
   private HashSet optSet;
   private final Logger logger;
 
   public HiPackTlv()
   {
     this.isInit = false;
 
     this.tlvNodeMap = new HashMap();
     this.mustSet = new HashSet();
 
     this.optSet = new HashSet();
 
     this.logger = ((Logger)HiContext.getCurrentContext().getProperty("SVR.log"));
   }
 
   public String getNodeName()
   {
     return "PackTlv";
   }
 
   public void setMust_fields(String must_fields)
   {
     this.must_fields = must_fields;
   }
 
   public void setOpt_fields(String opt_fields)
   {
     this.opt_fields = opt_fields;
   }
 
   public void setField_id(String field_id)
   {
     this.field_id = field_id;
   }
 
   public String toString()
   {
     return super.toString() + ":PackTlv[" + this.field_id + "]";
   }
 
   public void loadAfter()
     throws HiException
   {
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("loadAfter() - start");
     }
     if (StringUtils.isEmpty(this.field_id))
     {
       throw new HiException("213231", "所属域 field_id,不允许为空.");
     }
 
     if (StringUtils.isBlank(this.must_fields))
     {
       throw new HiException("213232", "必需组包的子域号列表 must_fields,不允许为空.");
     }
     checkValidField(this.must_fields, true);
 
     if (StringUtils.isNotBlank(this.opt_fields))
     {
       checkValidField(this.opt_fields, false);
     }
 
     if (this.logger.isDebugEnabled())
       this.logger.debug("loadAfter() - end");
   }
 
   public void process(HiMessageContext ctx) throws HiException
   {
     try
     {
       HiMessage msg = ctx.getCurrentMsg();
       Logger log = HiLog.getLogger(msg);
       if (log.isInfoEnabled()) {
         log.info(sm.getString("HiTlv.process00", HiEngineUtilities.getCurFlowStep(), this.field_id));
       }
 
       doProcess(ctx);
     } catch (HiException e) {
       throw e;
     } catch (Throwable te) {
       throw new HiSysException("213234", this.field_id, te);
     }
   }
 
   public void doProcess(HiMessageContext ctx) throws HiException {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     if (log.isDebugEnabled()) {
       log.debug("doProcess(HiMessageContext) - start.\nTlv process start.");
     }
 
     initCfgNode(ctx);
 
     ByteArrayOutputStream out = new ByteArrayOutputStream();
     convertToTlv(msg, out);
 
     saveTLV(ctx, this.field_id, out.toByteArray());
 
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiTlv.processOk", this.field_id, out.toString()));
     }
     if (log.isDebugEnabled()) {
       log.debug("Tlv: OK =======================");
 
       log.debug("doProcess(HiMessageContext) - end.");
     }
   }
 
   private void convertToTlv(HiMessage msg, ByteArrayOutputStream out)
     throws HiException
   {
     HiETF etfBody = msg.getETFBody();
     Logger log = HiLog.getLogger(msg);
 
     Iterator it = this.tlvNodeMap.entrySet().iterator();
 
     while (it.hasNext())
     {
       Map.Entry itemEntry = (Map.Entry)it.next();
       String tlvTag = (String)itemEntry.getKey();
       Element tlvNode = (Element)itemEntry.getValue();
       String etfName = tlvNode.attributeValue("etf_name");
       String etfVal = etfBody.getGrandChildValue(etfName);
 
       if (etfVal == null)
       {
         if (!(this.mustSet.contains(tlvTag)))
           continue;
         throw new HiException("213235", this.field_id, tlvTag, etfName);
       }
 
       fitTlv(etfVal, tlvTag, tlvNode, out);
 
       if (!(log.isInfoEnabled()))
         continue;
       log.info(sm.getString("HiTlv.packTlvOk", etfName, String.valueOf(etfVal.length()), etfVal, tlvTag));
     }
   }
 
   private void fitTlv(String value, String tag, Element itemNode, ByteArrayOutputStream out)
     throws HiException
   {
     byte[] valBytes;
     String data_type = itemNode.attributeValue("data_type");
 
     if (!(data_type.endsWith("ASCII")))
     {
       valBytes = HiConvHelper.ascStr2Bcd(value);
     }
     else
     {
       valBytes = value.getBytes();
     }
 
     byte[] tagBytes = HiConvHelper.ascStr2Bcd(tag);
     out.write(tagBytes, 0, tagBytes.length);
 
     int valLen = valBytes.length;
     if (valLen < 128)
     {
       out.write(valLen);
     }
     else if (valLen < 256)
     {
       out.write(129);
       out.write(valLen);
     }
     else if (valLen < 65536)
     {
       out.write(130);
 
       byte[] bp = new byte[2];
       bp[0] = (byte)(valLen >> 8 & 0xFF);
       bp[1] = (byte)(valLen & 0xFF);
 
       out.write(bp, 0, 2);
     }
     else
     {
       throw new HiException("213236", tag, String.valueOf(valLen));
     }
 
     out.write(valBytes, 0, valLen);
   }
 
   private void saveTLV(HiMessageContext ctx, String field_id, byte[] tlvBytes)
   {
     Map tlvMap = (Map)ctx.getProperty("8583_PACKTLV_VAL");
     if (tlvMap == null)
     {
       tlvMap = new HashMap();
       ctx.setProperty("8583_PACKTLV_VAL", tlvMap);
     }
 
     tlvMap.put(field_id, tlvBytes);
   }
 
   private synchronized void initCfgNode(HiMessageContext ctx) throws HiException
   {
     if (this.isInit)
     {
       return;
     }
 
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     if (log.isDebugEnabled()) {
       log.debug("first initCfgNode - start.");
     }
 
     Element cfgRoot = (Element)ctx.getProperty("8583_CFG_NODE");
     if (cfgRoot == null)
     {
       throw new HiException("213222", "");
     }
 
     this.tlvNodeMap.clear();
 
     putMapNode(cfgRoot, this.mustSet);
     putMapNode(cfgRoot, this.optSet);
 
     this.isInit = true;
 
     if (log.isDebugEnabled()) {
       log.debug("must_fields [" + this.must_fields + "]");
       log.debug("opt_fields [" + this.opt_fields + "]");
       log.debug("first initCfgNode - end.");
     }
   }
 
   private void putMapNode(Element cfgRoot, Set tlvSet)
     throws HiException
   {
     Element itemNode = HiXmlHelper.selectSingleNode(cfgRoot, "Item", "field_id", this.field_id);
     if (itemNode == null)
     {
       throw new HiException("213223", this.field_id);
     }
 
     Iterator it = tlvSet.iterator();
     while (it.hasNext())
     {
       String tlvTag = (String)it.next();
 
       Element tlvNode = HiXmlHelper.selectSingleNode(itemNode, "Tlv", "tag", tlvTag);
 
       if (tlvNode == null)
       {
         throw new HiException("213233", this.field_id, tlvTag);
       }
 
       this.tlvNodeMap.put(tlvTag, tlvNode);
     }
   }
 
   private void checkValidField(String fields, boolean isMust) throws HiException
   {
     StringTokenizer tokenizer = new StringTokenizer(fields, "|");
     while (tokenizer.hasMoreElements())
     {
       if (isMust)
       {
         this.mustSet.add(tokenizer.nextToken());
       }
 
       this.optSet.add(tokenizer.nextToken());
     }
   }
 }