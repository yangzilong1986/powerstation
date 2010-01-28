 package com.hisun.cfg;
 
 import com.hisun.engine.invoke.impl.HiItemHelper;
 import com.hisun.engine.invoke.impl.HiMethodItem;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiConvHelper;
 import com.hisun.util.HiStringManager;
 import java.io.ByteArrayInputStream;
 import java.util.Map;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 import org.dom4j.Element;
 
 public class HiTlvItem
 {
   public static final String TLV = "TLV";
   public static final String TAG = "tag";
   public static final String TAG_TYPE = "tag_type";
   public static final String TAG_LEN = "tag_len";
   public static final String HEAD_LEN = "head_len";
   public static final String LEN_TYPE = "len_type";
   public static final String LENGTH = "left";
   public static final String NAME = "name";
   public static final String DATA_TYPE = "data_type";
   public static final String ALIGN_MODE = "align_mode";
   public static final String FILL_ASC = "fill_asc";
   public static final String NODE_TYPE = "node_type";
   public static final String CHILD_TAG_TYPE = "child_tag_type";
   public static final String CHILD_TAG_LEN = "child_tag_len";
   public static final String IS_GROUP = "is_group";
   public static final String REPEAT_NAME = "repeat_name";
   public static final String PRO_DLL = "pro_dll";
   public static final String PRO_FUNC = "pro_func";
   public static final String HEX = "hex";
   public static final String DEC = "dec";
   public static final String ASC = "asc";
   public static final String LEFT = "left";
   public static final String RIGHT = "right";
   public static final String END = "end";
   public static final String PARENT = "parent";
   public static final String NEXT = "next";
   public String tag;
   public int tag_type;
   public int tag_len;
   public int head_len;
   public int len_type;
   public int length;
   public String etf_name;
   public int data_type;
   public int align_mode;
   public byte fill_asc;
   public int node_type;
   public int child_tag_type;
   public int child_tag_len;
   public boolean is_group;
   public String repeat_name;
   public String pro_dll;
   public String pro_func;
   public HiMethodItem pro_method;
   final HiStringManager sm;
 
   public HiTlvItem()
   {
     this.tag_type = 1;
 
     this.tag_len = 0;
 
     this.head_len = 0;
 
     this.len_type = 0;
 
     this.length = -1;
 
     this.data_type = 2;
 
     this.align_mode = 0;
 
     this.fill_asc = 32;
 
     this.node_type = 0;
 
     this.child_tag_type = 1;
 
     this.is_group = false;
 
     this.repeat_name = "REC_NUM";
 
     this.pro_method = null;
 
     this.sm = HiStringManager.getManager(); }
 
   public String getTag() {
     return this.tag;
   }
 
   public void setTag(String tag) throws HiException {
     this.tag = tag; }
 
   public int getTag_type() {
     return this.tag_type; }
 
   public void setTag_type(String tag_type) throws HiException {
     if (StringUtils.equalsIgnoreCase(tag_type, "hex"))
     {
       this.tag_type = 1;
     }
     else if (StringUtils.equalsIgnoreCase(tag_type, "dec"))
     {
       this.tag_type = 0;
     }
     else if (StringUtils.equalsIgnoreCase(tag_type, "asc"))
     {
       this.tag_type = 2;
     }
     else
     {
       throw new HiException("231501", new String[] { this.tag, "tag_type", tag_type });
     }
 
     this.child_tag_type = this.tag_type; }
 
   public int getTag_len() {
     return this.tag_len; }
 
   public void setTag_len(String tag_len) throws HiException {
     if (NumberUtils.isNumber(tag_len))
     {
       setTag_len(Integer.parseInt(tag_len.trim()));
     }
     else
     {
       throw new HiException("231501", new String[] { this.tag, "tag_len", tag_len }); }
   }
 
   public void setTag_len(int tag_len) {
     this.tag_len = tag_len;
 
     this.child_tag_len = this.tag_len; }
 
   public int getHead_len() {
     return this.head_len;
   }
 
   public void setHead_len(String head_len) throws HiException {
     if (NumberUtils.isNumber(head_len))
     {
       setHead_len(Integer.parseInt(head_len.trim()));
     }
     else
     {
       throw new HiException("231501", new String[] { this.tag, "head_len", head_len }); }
   }
 
   public void setHead_len(int head_len) {
     this.head_len = head_len; }
 
   public int getLen_type() {
     return this.len_type; }
 
   public void setLen_type(String len_type) throws HiException {
     if (StringUtils.equalsIgnoreCase(len_type, "hex"))
     {
       this.len_type = 1;
     }
     else if (StringUtils.equalsIgnoreCase(len_type, "dec"))
     {
       this.len_type = 0;
     }
     else if (StringUtils.equalsIgnoreCase(len_type, "asc"))
     {
       this.len_type = 2;
     }
     else
     {
       throw new HiException("231501", new String[] { this.tag, "len_type", len_type }); }
   }
 
   public int getLength() {
     return this.length; }
 
   public void setLength(String length) throws HiException {
     if (NumberUtils.isNumber(length))
     {
       this.length = Integer.parseInt(length.trim());
     }
     else
     {
       throw new HiException("231501", new String[] { this.tag, "length", length }); }
   }
 
   public String getEtf_name() {
     return this.etf_name; }
 
   public void setEtf_name(String etf_name) {
     this.etf_name = etf_name; }
 
   public int getData_type() {
     return this.data_type; }
 
   public void setData_type(String data_type) throws HiException {
     if (StringUtils.equalsIgnoreCase(data_type, "hex"))
     {
       this.data_type = 1;
     }
     else if (StringUtils.equalsIgnoreCase(data_type, "dec"))
     {
       this.data_type = 0;
     }
     else if (StringUtils.equalsIgnoreCase(data_type, "asc"))
     {
       this.data_type = 2;
     }
     else
     {
       throw new HiException("231501", new String[] { this.tag, "data_type", data_type }); }
   }
 
   public int getAlign_mode() {
     return this.align_mode; }
 
   public void setAlign_mode(String align_mode) throws HiException {
     if (StringUtils.equalsIgnoreCase(align_mode, "right"))
     {
       this.align_mode = 1;
     }
     else if (StringUtils.equalsIgnoreCase(align_mode, "left"))
     {
       this.align_mode = 0;
     }
     else
     {
       throw new HiException("231501", new String[] { this.tag, "align_mode", align_mode }); }
   }
 
   public byte getFill_asc() {
     return this.fill_asc;
   }
 
   public void setFill_asc(String fill_asc) throws HiException {
     Integer deliInt = Integer.valueOf(fill_asc);
     if ((deliInt.intValue() > 255) || (deliInt.intValue() < -128)) {
       throw new HiException("231501", new String[] { this.tag, "fill_asc", fill_asc });
     }
 
     this.fill_asc = deliInt.byteValue(); }
 
   public int getNode_type() {
     return this.node_type; }
 
   public void setNode_type(String node_type) throws HiException {
     if (StringUtils.equalsIgnoreCase(node_type, "next"))
     {
       this.node_type = 2;
     }
     else if (StringUtils.equalsIgnoreCase(node_type, "parent"))
     {
       this.node_type = 1;
     }
     else if (StringUtils.equalsIgnoreCase(node_type, "end"))
     {
       this.node_type = 0;
     }
     else
     {
       throw new HiException("231501", new String[] { this.tag, "node_type", node_type });
     }
   }
 
   public int getChild_tag_type() {
     return this.child_tag_type; }
 
   public void setChild_tag_type(String child_tag_type) throws HiException {
     if (StringUtils.equalsIgnoreCase(child_tag_type, "hex"))
     {
       this.child_tag_type = 1;
     }
     else if (StringUtils.equalsIgnoreCase(child_tag_type, "dec"))
     {
       this.child_tag_type = 0;
     }
     else if (StringUtils.equalsIgnoreCase(child_tag_type, "asc"))
     {
       this.child_tag_type = 2;
     }
     else
     {
       throw new HiException("231501", new String[] { this.tag, "child_tag_type", child_tag_type }); }
   }
 
   public int getChild_tag_len() {
     return this.child_tag_len; }
 
   public void setChild_tag_len(int child_tag_len) {
     this.child_tag_len = child_tag_len; }
 
   public void setChild_tag_len(String child_tag_len) throws HiException {
     if (NumberUtils.isNumber(child_tag_len))
     {
       this.child_tag_len = Integer.parseInt(child_tag_len.trim());
     }
     else
     {
       throw new HiException("231501", new String[] { this.tag, "child_tag_len", child_tag_len }); }
   }
 
   public boolean is_group() {
     return this.is_group; }
 
   public void setIs_group(String is_group) {
     if (!(StringUtils.equalsIgnoreCase(is_group, "yes")))
       return;
     this.is_group = true;
   }
 
   public String getRepeat_name() {
     return this.repeat_name; }
 
   public void setRepeat_name(String repeat_name) {
     this.repeat_name = repeat_name; }
 
   public String getPro_dll() {
     return this.pro_dll; }
 
   public void setPro_dll(String pro_dll) {
     this.pro_dll = pro_dll; }
 
   public String getPro_func() {
     return this.pro_func; }
 
   public void setPro_func(String pro_func) {
     this.pro_func = pro_func;
   }
 
   public void initAttr(HiTlvItem tlvItem)
     throws HiException
   {
     if (tlvItem == null)
     {
       return;
     }
     this.tag_type = tlvItem.tag_type;
     this.tag_len = tlvItem.tag_len;
     this.head_len = tlvItem.head_len;
     this.len_type = tlvItem.len_type;
     this.length = tlvItem.length;
     this.etf_name = tlvItem.etf_name;
     this.data_type = tlvItem.data_type;
     this.align_mode = tlvItem.align_mode;
     this.fill_asc = tlvItem.fill_asc;
     this.node_type = tlvItem.node_type;
 
     this.is_group = tlvItem.is_group;
     this.repeat_name = tlvItem.repeat_name;
     this.pro_dll = tlvItem.pro_dll;
     this.pro_func = tlvItem.pro_func;
 
     this.child_tag_type = tlvItem.child_tag_type;
     this.child_tag_len = tlvItem.child_tag_len;
   }
 
   public void load(Element itemNode)
     throws HiException
   {
     setTag(itemNode.attributeValue("tag"));
 
     String val = itemNode.attributeValue("tag_type");
     if (val != null)
     {
       setTag_type(val);
     }
 
     val = itemNode.attributeValue("tag_len");
     if (val != null)
     {
       setTag_len(val);
     }
 
     val = itemNode.attributeValue("head_len");
     if (val != null)
     {
       setHead_len(val);
     }
 
     val = itemNode.attributeValue("len_type");
     if (val != null)
     {
       setLen_type(val);
     }
 
     val = itemNode.attributeValue("left");
     if (val != null)
     {
       setLength(val);
     }
 
     setEtf_name(itemNode.attributeValue("name"));
 
     val = itemNode.attributeValue("data_type");
     if (val != null)
     {
       setData_type(val);
     }
 
     val = itemNode.attributeValue("align_mode");
     if (val != null)
     {
       setAlign_mode(val);
     }
 
     val = itemNode.attributeValue("fill_asc");
     if (val != null)
     {
       setFill_asc(val);
     }
 
     val = itemNode.attributeValue("node_type");
     if (val != null)
     {
       setNode_type(val);
     }
 
     val = itemNode.attributeValue("child_tag_type");
     if (val != null)
     {
       setChild_tag_type(val);
     }
 
     val = itemNode.attributeValue("child_tag_len");
     if (val != null)
     {
       setChild_tag_len(val);
     }
 
     val = itemNode.attributeValue("is_group");
     if (val != null)
     {
       setIs_group(val);
     }
 
     val = itemNode.attributeValue("repeat_name");
     if (val != null)
     {
       setRepeat_name(val);
     }
 
     setPro_dll(itemNode.attributeValue("pro_dll"));
 
     setPro_func(itemNode.attributeValue("pro_func"));
   }
 
   public void init(HiTlvItem tlvItem, Element node)
     throws HiException
   {
     initAttr(tlvItem);
 
     load(node);
 
     if (this.tag == null)
     {
       throw new HiException("231502", this.tag);
     }
     if (this.tag_len <= 0)
     {
       throw new HiException("231503", new String[] { this.tag, "tag_len" });
     }
     if (this.head_len <= 0)
     {
       throw new HiException("231503", new String[] { this.tag, "head_len" });
     }
     if (this.etf_name == null)
     {
       throw new HiException("231503", new String[] { this.tag, "name" });
     }
 
     if ((StringUtils.isNotBlank(this.pro_dll)) && (StringUtils.isNotBlank(this.pro_func)))
       this.pro_method = HiItemHelper.getMethod(this.pro_dll, this.pro_func);
   }
 
   public void unPack(HiMessageContext ctx, Map cfgMap, ByteArrayInputStream in, HiETF etfBody, Logger log)
     throws HiException
   {
     byte[] retBytes = null;
 
     int len = getHeadLen(in);
 
     if (log.isInfoEnabled()) {
       log.info(this.sm.getString("HiParserTlvHandler.parserItem1", this.tag, String.valueOf(len)));
     }
 
     if (len == 0)
     {
       etfBody.setGrandChildNode(this.etf_name, "");
 
       if (log.isInfoEnabled()) {
         log.info(this.sm.getString("HiParserTlvHandler.parserItem2", this.tag, this.etf_name, ""));
       }
       return;
     }
 
     retBytes = new byte[len];
     int ret = in.read(retBytes, 0, len);
 
     if (ret < len)
     {
       throw new HiException("231505", " while get data value.");
     }
 
     if (this.pro_method != null) {
       retBytes = HiItemHelper.execMethod(this.pro_method, retBytes, log);
     }
 
     dataProcess(ctx, cfgMap, retBytes, etfBody, log);
   }
 
   public void dataProcess(HiMessageContext ctx, Map cfgMap, byte[] bytes, HiETF etfBody, Logger log)
     throws HiException
   {
     if (this.is_group)
     {
       String val = etfBody.getChildValue(this.repeat_name);
       int repeat_num = 0;
       if (val != null)
       {
         repeat_num = Integer.parseInt(val);
       }
 
       if (this.node_type == 0)
       {
         ++repeat_num;
 
         addETFItem(ctx, bytes, etfBody, this.etf_name + "_" + repeat_num, log);
       }
       else
       {
         if (log.isInfoEnabled()) {
           log.info(this.sm.getString("HiParserTlvHandler.parserItem3", this.tag));
         }
         ++repeat_num;
 
         HiETF parent = etfBody.addNode(this.etf_name + "_" + repeat_num);
 
         ByteArrayInputStream in = new ByteArrayInputStream(bytes);
         convertToETF(ctx, in, parent, cfgMap, log);
 
         if (log.isInfoEnabled()) {
           log.info(this.sm.getString("HiParserTlvHandler.parserItem4", this.tag));
         }
       }
       etfBody.setChildValue(this.repeat_name, String.valueOf(repeat_num));
     }
     else if (this.node_type == 0)
     {
       addETFItem(ctx, bytes, etfBody, this.etf_name, log);
     }
     else if (this.node_type == 1)
     {
       if (log.isInfoEnabled()) {
         log.info(this.sm.getString("HiParserTlvHandler.parserItem3", this.tag));
       }
 
       HiETF parent = etfBody.addNode(this.etf_name);
 
       ByteArrayInputStream in = new ByteArrayInputStream(bytes);
       convertToETF(ctx, in, parent, cfgMap, log);
 
       if (log.isInfoEnabled())
         log.info(this.sm.getString("HiParserTlvHandler.parserItem4", this.tag));
     }
     else {
       if (this.node_type != 2)
         return;
       if (log.isInfoEnabled()) {
         log.info(this.sm.getString("HiParserTlvHandler.parserItem3", this.tag));
       }
 
       etfBody.setGrandChildNode(this.etf_name, this.tag);
 
       ByteArrayInputStream in = new ByteArrayInputStream(bytes);
       convertToETF(ctx, in, etfBody, cfgMap, log);
 
       if (log.isInfoEnabled())
         log.info(this.sm.getString("HiParserTlvHandler.parserItem4", this.tag));
     }
   }
 
   public void addETFItem(HiMessageContext ctx, byte[] bytes, HiETF etfBody, String etf_name, Logger log)
     throws HiException
   {
     String val;
     switch (this.data_type)
     {
     case 0:
       val = Long.valueOf(HiConvHelper.bcd2AscStr(bytes), 16).toString();
       break;
     case 1:
       val = HiConvHelper.bcd2AscStr(bytes);
       break;
     case 2:
       val = new String(bytes);
       break;
     default:
       val = new String(bytes);
     }
 
     etfBody.setGrandChildNode(etf_name, val);
     if (log.isInfoEnabled())
       log.info(this.sm.getString("HiParserTlvHandler.parserItem2", this.tag, etf_name, val));
   }
 
   public int getHeadLen(ByteArrayInputStream in) throws HiException
   {
     int ret = 0;
     byte[] retBytes = null;
 
     retBytes = new byte[this.head_len];
     ret = in.read(retBytes, 0, this.head_len);
 
     if (ret < this.head_len)
     {
       throw new HiException("231505", " while get head len.");
     }
 
     switch (this.len_type)
     {
     case 1:
       return Integer.parseInt(new String(retBytes));
     }
     return Integer.valueOf(HiConvHelper.bcd2AscStr(retBytes), 16).intValue();
   }
 
   public void convertToETF(HiMessageContext ctx, ByteArrayInputStream in, HiETF etfBody, Map cfgMap, Logger log)
     throws HiException
   {
     byte[] retBytes = null;
     retBytes = new byte[this.child_tag_len];
 
     while ((ret = in.read(retBytes, 0, this.child_tag_len)) != -1)
     {
       int ret;
       if (ret < this.child_tag_len) {
         throw new HiException("231506", String.valueOf(this.child_tag_len));
       }
 
       makeEtfItem(ctx, in, etfBody, HiTlvHelper.getTag(retBytes, this.child_tag_type), cfgMap, log);
     }
   }
 
   private void makeEtfItem(HiMessageContext ctx, ByteArrayInputStream in, HiETF etfBody, String tag, Map cfgMap, Logger log)
     throws HiException
   {
     if (log.isInfoEnabled()) {
       log.info(this.sm.getString("HiParserTlvHandler.parserItem0", tag));
     }
 
     HiTlvItem item = (HiTlvItem)cfgMap.get(tag);
     if (item == null)
     {
       throw new HiException("231507", tag);
     }
 
     item.unPack(ctx, cfgMap, in, etfBody, log);
   }
 }