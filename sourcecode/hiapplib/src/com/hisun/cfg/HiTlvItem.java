/*     */ package com.hisun.cfg;
/*     */ 
/*     */ import com.hisun.engine.invoke.impl.HiItemHelper;
/*     */ import com.hisun.engine.invoke.impl.HiMethodItem;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiConvHelper;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ import org.dom4j.Element;
/*     */ 
/*     */ public class HiTlvItem
/*     */ {
/*     */   public static final String TLV = "TLV";
/*     */   public static final String TAG = "tag";
/*     */   public static final String TAG_TYPE = "tag_type";
/*     */   public static final String TAG_LEN = "tag_len";
/*     */   public static final String HEAD_LEN = "head_len";
/*     */   public static final String LEN_TYPE = "len_type";
/*     */   public static final String LENGTH = "left";
/*     */   public static final String NAME = "name";
/*     */   public static final String DATA_TYPE = "data_type";
/*     */   public static final String ALIGN_MODE = "align_mode";
/*     */   public static final String FILL_ASC = "fill_asc";
/*     */   public static final String NODE_TYPE = "node_type";
/*     */   public static final String CHILD_TAG_TYPE = "child_tag_type";
/*     */   public static final String CHILD_TAG_LEN = "child_tag_len";
/*     */   public static final String IS_GROUP = "is_group";
/*     */   public static final String REPEAT_NAME = "repeat_name";
/*     */   public static final String PRO_DLL = "pro_dll";
/*     */   public static final String PRO_FUNC = "pro_func";
/*     */   public static final String HEX = "hex";
/*     */   public static final String DEC = "dec";
/*     */   public static final String ASC = "asc";
/*     */   public static final String LEFT = "left";
/*     */   public static final String RIGHT = "right";
/*     */   public static final String END = "end";
/*     */   public static final String PARENT = "parent";
/*     */   public static final String NEXT = "next";
/*     */   public String tag;
/*     */   public int tag_type;
/*     */   public int tag_len;
/*     */   public int head_len;
/*     */   public int len_type;
/*     */   public int length;
/*     */   public String etf_name;
/*     */   public int data_type;
/*     */   public int align_mode;
/*     */   public byte fill_asc;
/*     */   public int node_type;
/*     */   public int child_tag_type;
/*     */   public int child_tag_len;
/*     */   public boolean is_group;
/*     */   public String repeat_name;
/*     */   public String pro_dll;
/*     */   public String pro_func;
/*     */   public HiMethodItem pro_method;
/*     */   final HiStringManager sm;
/*     */ 
/*     */   public HiTlvItem()
/*     */   {
/*  74 */     this.tag_type = 1;
/*     */ 
/*  78 */     this.tag_len = 0;
/*     */ 
/*  83 */     this.head_len = 0;
/*     */ 
/*  90 */     this.len_type = 0;
/*     */ 
/*  96 */     this.length = -1;
/*     */ 
/* 106 */     this.data_type = 2;
/*     */ 
/* 113 */     this.align_mode = 0;
/*     */ 
/* 117 */     this.fill_asc = 32;
/*     */ 
/* 126 */     this.node_type = 0;
/*     */ 
/* 134 */     this.child_tag_type = 1;
/*     */ 
/* 145 */     this.is_group = false;
/*     */ 
/* 150 */     this.repeat_name = "REC_NUM";
/*     */ 
/* 154 */     this.pro_method = null;
/*     */ 
/* 156 */     this.sm = HiStringManager.getManager(); }
/*     */ 
/*     */   public String getTag() {
/* 159 */     return this.tag;
/*     */   }
/*     */ 
/*     */   public void setTag(String tag) throws HiException {
/* 163 */     this.tag = tag; }
/*     */ 
/*     */   public int getTag_type() {
/* 166 */     return this.tag_type; }
/*     */ 
/*     */   public void setTag_type(String tag_type) throws HiException {
/* 169 */     if (StringUtils.equalsIgnoreCase(tag_type, "hex"))
/*     */     {
/* 171 */       this.tag_type = 1;
/*     */     }
/* 173 */     else if (StringUtils.equalsIgnoreCase(tag_type, "dec"))
/*     */     {
/* 175 */       this.tag_type = 0;
/*     */     }
/* 177 */     else if (StringUtils.equalsIgnoreCase(tag_type, "asc"))
/*     */     {
/* 179 */       this.tag_type = 2;
/*     */     }
/*     */     else
/*     */     {
/* 183 */       throw new HiException("231501", new String[] { this.tag, "tag_type", tag_type });
/*     */     }
/*     */ 
/* 186 */     this.child_tag_type = this.tag_type; }
/*     */ 
/*     */   public int getTag_len() {
/* 189 */     return this.tag_len; }
/*     */ 
/*     */   public void setTag_len(String tag_len) throws HiException {
/* 192 */     if (NumberUtils.isNumber(tag_len))
/*     */     {
/* 194 */       setTag_len(Integer.parseInt(tag_len.trim()));
/*     */     }
/*     */     else
/*     */     {
/* 198 */       throw new HiException("231501", new String[] { this.tag, "tag_len", tag_len }); }
/*     */   }
/*     */ 
/*     */   public void setTag_len(int tag_len) {
/* 202 */     this.tag_len = tag_len;
/*     */ 
/* 205 */     this.child_tag_len = this.tag_len; }
/*     */ 
/*     */   public int getHead_len() {
/* 208 */     return this.head_len;
/*     */   }
/*     */ 
/*     */   public void setHead_len(String head_len) throws HiException {
/* 212 */     if (NumberUtils.isNumber(head_len))
/*     */     {
/* 214 */       setHead_len(Integer.parseInt(head_len.trim()));
/*     */     }
/*     */     else
/*     */     {
/* 218 */       throw new HiException("231501", new String[] { this.tag, "head_len", head_len }); }
/*     */   }
/*     */ 
/*     */   public void setHead_len(int head_len) {
/* 222 */     this.head_len = head_len; }
/*     */ 
/*     */   public int getLen_type() {
/* 225 */     return this.len_type; }
/*     */ 
/*     */   public void setLen_type(String len_type) throws HiException {
/* 228 */     if (StringUtils.equalsIgnoreCase(len_type, "hex"))
/*     */     {
/* 230 */       this.len_type = 1;
/*     */     }
/* 232 */     else if (StringUtils.equalsIgnoreCase(len_type, "dec"))
/*     */     {
/* 234 */       this.len_type = 0;
/*     */     }
/* 236 */     else if (StringUtils.equalsIgnoreCase(len_type, "asc"))
/*     */     {
/* 238 */       this.len_type = 2;
/*     */     }
/*     */     else
/*     */     {
/* 242 */       throw new HiException("231501", new String[] { this.tag, "len_type", len_type }); }
/*     */   }
/*     */ 
/*     */   public int getLength() {
/* 246 */     return this.length; }
/*     */ 
/*     */   public void setLength(String length) throws HiException {
/* 249 */     if (NumberUtils.isNumber(length))
/*     */     {
/* 251 */       this.length = Integer.parseInt(length.trim());
/*     */     }
/*     */     else
/*     */     {
/* 255 */       throw new HiException("231501", new String[] { this.tag, "length", length }); }
/*     */   }
/*     */ 
/*     */   public String getEtf_name() {
/* 259 */     return this.etf_name; }
/*     */ 
/*     */   public void setEtf_name(String etf_name) {
/* 262 */     this.etf_name = etf_name; }
/*     */ 
/*     */   public int getData_type() {
/* 265 */     return this.data_type; }
/*     */ 
/*     */   public void setData_type(String data_type) throws HiException {
/* 268 */     if (StringUtils.equalsIgnoreCase(data_type, "hex"))
/*     */     {
/* 270 */       this.data_type = 1;
/*     */     }
/* 272 */     else if (StringUtils.equalsIgnoreCase(data_type, "dec"))
/*     */     {
/* 274 */       this.data_type = 0;
/*     */     }
/* 276 */     else if (StringUtils.equalsIgnoreCase(data_type, "asc"))
/*     */     {
/* 278 */       this.data_type = 2;
/*     */     }
/*     */     else
/*     */     {
/* 282 */       throw new HiException("231501", new String[] { this.tag, "data_type", data_type }); }
/*     */   }
/*     */ 
/*     */   public int getAlign_mode() {
/* 286 */     return this.align_mode; }
/*     */ 
/*     */   public void setAlign_mode(String align_mode) throws HiException {
/* 289 */     if (StringUtils.equalsIgnoreCase(align_mode, "right"))
/*     */     {
/* 291 */       this.align_mode = 1;
/*     */     }
/* 293 */     else if (StringUtils.equalsIgnoreCase(align_mode, "left"))
/*     */     {
/* 295 */       this.align_mode = 0;
/*     */     }
/*     */     else
/*     */     {
/* 299 */       throw new HiException("231501", new String[] { this.tag, "align_mode", align_mode }); }
/*     */   }
/*     */ 
/*     */   public byte getFill_asc() {
/* 303 */     return this.fill_asc;
/*     */   }
/*     */ 
/*     */   public void setFill_asc(String fill_asc) throws HiException {
/* 307 */     Integer deliInt = Integer.valueOf(fill_asc);
/* 308 */     if ((deliInt.intValue() > 255) || (deliInt.intValue() < -128)) {
/* 309 */       throw new HiException("231501", new String[] { this.tag, "fill_asc", fill_asc });
/*     */     }
/*     */ 
/* 312 */     this.fill_asc = deliInt.byteValue(); }
/*     */ 
/*     */   public int getNode_type() {
/* 315 */     return this.node_type; }
/*     */ 
/*     */   public void setNode_type(String node_type) throws HiException {
/* 318 */     if (StringUtils.equalsIgnoreCase(node_type, "next"))
/*     */     {
/* 320 */       this.node_type = 2;
/*     */     }
/* 322 */     else if (StringUtils.equalsIgnoreCase(node_type, "parent"))
/*     */     {
/* 324 */       this.node_type = 1;
/*     */     }
/* 326 */     else if (StringUtils.equalsIgnoreCase(node_type, "end"))
/*     */     {
/* 328 */       this.node_type = 0;
/*     */     }
/*     */     else
/*     */     {
/* 332 */       throw new HiException("231501", new String[] { this.tag, "node_type", node_type });
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getChild_tag_type() {
/* 337 */     return this.child_tag_type; }
/*     */ 
/*     */   public void setChild_tag_type(String child_tag_type) throws HiException {
/* 340 */     if (StringUtils.equalsIgnoreCase(child_tag_type, "hex"))
/*     */     {
/* 342 */       this.child_tag_type = 1;
/*     */     }
/* 344 */     else if (StringUtils.equalsIgnoreCase(child_tag_type, "dec"))
/*     */     {
/* 346 */       this.child_tag_type = 0;
/*     */     }
/* 348 */     else if (StringUtils.equalsIgnoreCase(child_tag_type, "asc"))
/*     */     {
/* 350 */       this.child_tag_type = 2;
/*     */     }
/*     */     else
/*     */     {
/* 354 */       throw new HiException("231501", new String[] { this.tag, "child_tag_type", child_tag_type }); }
/*     */   }
/*     */ 
/*     */   public int getChild_tag_len() {
/* 358 */     return this.child_tag_len; }
/*     */ 
/*     */   public void setChild_tag_len(int child_tag_len) {
/* 361 */     this.child_tag_len = child_tag_len; }
/*     */ 
/*     */   public void setChild_tag_len(String child_tag_len) throws HiException {
/* 364 */     if (NumberUtils.isNumber(child_tag_len))
/*     */     {
/* 366 */       this.child_tag_len = Integer.parseInt(child_tag_len.trim());
/*     */     }
/*     */     else
/*     */     {
/* 370 */       throw new HiException("231501", new String[] { this.tag, "child_tag_len", child_tag_len }); }
/*     */   }
/*     */ 
/*     */   public boolean is_group() {
/* 374 */     return this.is_group; }
/*     */ 
/*     */   public void setIs_group(String is_group) {
/* 377 */     if (!(StringUtils.equalsIgnoreCase(is_group, "yes")))
/*     */       return;
/* 379 */     this.is_group = true;
/*     */   }
/*     */ 
/*     */   public String getRepeat_name() {
/* 383 */     return this.repeat_name; }
/*     */ 
/*     */   public void setRepeat_name(String repeat_name) {
/* 386 */     this.repeat_name = repeat_name; }
/*     */ 
/*     */   public String getPro_dll() {
/* 389 */     return this.pro_dll; }
/*     */ 
/*     */   public void setPro_dll(String pro_dll) {
/* 392 */     this.pro_dll = pro_dll; }
/*     */ 
/*     */   public String getPro_func() {
/* 395 */     return this.pro_func; }
/*     */ 
/*     */   public void setPro_func(String pro_func) {
/* 398 */     this.pro_func = pro_func;
/*     */   }
/*     */ 
/*     */   public void initAttr(HiTlvItem tlvItem)
/*     */     throws HiException
/*     */   {
/* 408 */     if (tlvItem == null)
/*     */     {
/* 410 */       return;
/*     */     }
/* 412 */     this.tag_type = tlvItem.tag_type;
/* 413 */     this.tag_len = tlvItem.tag_len;
/* 414 */     this.head_len = tlvItem.head_len;
/* 415 */     this.len_type = tlvItem.len_type;
/* 416 */     this.length = tlvItem.length;
/* 417 */     this.etf_name = tlvItem.etf_name;
/* 418 */     this.data_type = tlvItem.data_type;
/* 419 */     this.align_mode = tlvItem.align_mode;
/* 420 */     this.fill_asc = tlvItem.fill_asc;
/* 421 */     this.node_type = tlvItem.node_type;
/*     */ 
/* 423 */     this.is_group = tlvItem.is_group;
/* 424 */     this.repeat_name = tlvItem.repeat_name;
/* 425 */     this.pro_dll = tlvItem.pro_dll;
/* 426 */     this.pro_func = tlvItem.pro_func;
/*     */ 
/* 431 */     this.child_tag_type = tlvItem.child_tag_type;
/* 432 */     this.child_tag_len = tlvItem.child_tag_len;
/*     */   }
/*     */ 
/*     */   public void load(Element itemNode)
/*     */     throws HiException
/*     */   {
/* 446 */     setTag(itemNode.attributeValue("tag"));
/*     */ 
/* 449 */     String val = itemNode.attributeValue("tag_type");
/* 450 */     if (val != null)
/*     */     {
/* 452 */       setTag_type(val);
/*     */     }
/*     */ 
/* 455 */     val = itemNode.attributeValue("tag_len");
/* 456 */     if (val != null)
/*     */     {
/* 458 */       setTag_len(val);
/*     */     }
/*     */ 
/* 461 */     val = itemNode.attributeValue("head_len");
/* 462 */     if (val != null)
/*     */     {
/* 464 */       setHead_len(val);
/*     */     }
/*     */ 
/* 468 */     val = itemNode.attributeValue("len_type");
/* 469 */     if (val != null)
/*     */     {
/* 471 */       setLen_type(val);
/*     */     }
/*     */ 
/* 475 */     val = itemNode.attributeValue("left");
/* 476 */     if (val != null)
/*     */     {
/* 478 */       setLength(val);
/*     */     }
/*     */ 
/* 481 */     setEtf_name(itemNode.attributeValue("name"));
/*     */ 
/* 484 */     val = itemNode.attributeValue("data_type");
/* 485 */     if (val != null)
/*     */     {
/* 487 */       setData_type(val);
/*     */     }
/*     */ 
/* 491 */     val = itemNode.attributeValue("align_mode");
/* 492 */     if (val != null)
/*     */     {
/* 494 */       setAlign_mode(val);
/*     */     }
/*     */ 
/* 498 */     val = itemNode.attributeValue("fill_asc");
/* 499 */     if (val != null)
/*     */     {
/* 501 */       setFill_asc(val);
/*     */     }
/*     */ 
/* 505 */     val = itemNode.attributeValue("node_type");
/* 506 */     if (val != null)
/*     */     {
/* 508 */       setNode_type(val);
/*     */     }
/*     */ 
/* 512 */     val = itemNode.attributeValue("child_tag_type");
/* 513 */     if (val != null)
/*     */     {
/* 515 */       setChild_tag_type(val);
/*     */     }
/*     */ 
/* 518 */     val = itemNode.attributeValue("child_tag_len");
/* 519 */     if (val != null)
/*     */     {
/* 521 */       setChild_tag_len(val);
/*     */     }
/*     */ 
/* 525 */     val = itemNode.attributeValue("is_group");
/* 526 */     if (val != null)
/*     */     {
/* 528 */       setIs_group(val);
/*     */     }
/*     */ 
/* 532 */     val = itemNode.attributeValue("repeat_name");
/* 533 */     if (val != null)
/*     */     {
/* 535 */       setRepeat_name(val);
/*     */     }
/*     */ 
/* 539 */     setPro_dll(itemNode.attributeValue("pro_dll"));
/*     */ 
/* 541 */     setPro_func(itemNode.attributeValue("pro_func"));
/*     */   }
/*     */ 
/*     */   public void init(HiTlvItem tlvItem, Element node)
/*     */     throws HiException
/*     */   {
/* 553 */     initAttr(tlvItem);
/*     */ 
/* 555 */     load(node);
/*     */ 
/* 557 */     if (this.tag == null)
/*     */     {
/* 559 */       throw new HiException("231502", this.tag);
/*     */     }
/* 561 */     if (this.tag_len <= 0)
/*     */     {
/* 563 */       throw new HiException("231503", new String[] { this.tag, "tag_len" });
/*     */     }
/* 565 */     if (this.head_len <= 0)
/*     */     {
/* 567 */       throw new HiException("231503", new String[] { this.tag, "head_len" });
/*     */     }
/* 569 */     if (this.etf_name == null)
/*     */     {
/* 571 */       throw new HiException("231503", new String[] { this.tag, "name" });
/*     */     }
/*     */ 
/* 575 */     if ((StringUtils.isNotBlank(this.pro_dll)) && (StringUtils.isNotBlank(this.pro_func)))
/* 576 */       this.pro_method = HiItemHelper.getMethod(this.pro_dll, this.pro_func);
/*     */   }
/*     */ 
/*     */   public void unPack(HiMessageContext ctx, Map cfgMap, ByteArrayInputStream in, HiETF etfBody, Logger log)
/*     */     throws HiException
/*     */   {
/* 582 */     byte[] retBytes = null;
/*     */ 
/* 586 */     int len = getHeadLen(in);
/*     */ 
/* 588 */     if (log.isInfoEnabled()) {
/* 589 */       log.info(this.sm.getString("HiParserTlvHandler.parserItem1", this.tag, String.valueOf(len)));
/*     */     }
/*     */ 
/* 592 */     if (len == 0)
/*     */     {
/* 594 */       etfBody.setGrandChildNode(this.etf_name, "");
/*     */ 
/* 596 */       if (log.isInfoEnabled()) {
/* 597 */         log.info(this.sm.getString("HiParserTlvHandler.parserItem2", this.tag, this.etf_name, ""));
/*     */       }
/* 599 */       return;
/*     */     }
/*     */ 
/* 602 */     retBytes = new byte[len];
/* 603 */     int ret = in.read(retBytes, 0, len);
/*     */ 
/* 605 */     if (ret < len)
/*     */     {
/* 607 */       throw new HiException("231505", " while get data value.");
/*     */     }
/*     */ 
/* 611 */     if (this.pro_method != null) {
/* 612 */       retBytes = HiItemHelper.execMethod(this.pro_method, retBytes, log);
/*     */     }
/*     */ 
/* 615 */     dataProcess(ctx, cfgMap, retBytes, etfBody, log);
/*     */   }
/*     */ 
/*     */   public void dataProcess(HiMessageContext ctx, Map cfgMap, byte[] bytes, HiETF etfBody, Logger log)
/*     */     throws HiException
/*     */   {
/* 621 */     if (this.is_group)
/*     */     {
/* 623 */       String val = etfBody.getChildValue(this.repeat_name);
/* 624 */       int repeat_num = 0;
/* 625 */       if (val != null)
/*     */       {
/* 627 */         repeat_num = Integer.parseInt(val);
/*     */       }
/*     */ 
/* 630 */       if (this.node_type == 0)
/*     */       {
/* 632 */         ++repeat_num;
/*     */ 
/* 634 */         addETFItem(ctx, bytes, etfBody, this.etf_name + "_" + repeat_num, log);
/*     */       }
/*     */       else
/*     */       {
/* 638 */         if (log.isInfoEnabled()) {
/* 639 */           log.info(this.sm.getString("HiParserTlvHandler.parserItem3", this.tag));
/*     */         }
/* 641 */         ++repeat_num;
/*     */ 
/* 644 */         HiETF parent = etfBody.addNode(this.etf_name + "_" + repeat_num);
/*     */ 
/* 646 */         ByteArrayInputStream in = new ByteArrayInputStream(bytes);
/* 647 */         convertToETF(ctx, in, parent, cfgMap, log);
/*     */ 
/* 649 */         if (log.isInfoEnabled()) {
/* 650 */           log.info(this.sm.getString("HiParserTlvHandler.parserItem4", this.tag));
/*     */         }
/*     */       }
/* 653 */       etfBody.setChildValue(this.repeat_name, String.valueOf(repeat_num));
/*     */     }
/* 658 */     else if (this.node_type == 0)
/*     */     {
/* 661 */       addETFItem(ctx, bytes, etfBody, this.etf_name, log);
/*     */     }
/* 663 */     else if (this.node_type == 1)
/*     */     {
/* 665 */       if (log.isInfoEnabled()) {
/* 666 */         log.info(this.sm.getString("HiParserTlvHandler.parserItem3", this.tag));
/*     */       }
/*     */ 
/* 669 */       HiETF parent = etfBody.addNode(this.etf_name);
/*     */ 
/* 671 */       ByteArrayInputStream in = new ByteArrayInputStream(bytes);
/* 672 */       convertToETF(ctx, in, parent, cfgMap, log);
/*     */ 
/* 674 */       if (log.isInfoEnabled())
/* 675 */         log.info(this.sm.getString("HiParserTlvHandler.parserItem4", this.tag));
/*     */     }
/*     */     else {
/* 678 */       if (this.node_type != 2)
/*     */         return;
/* 680 */       if (log.isInfoEnabled()) {
/* 681 */         log.info(this.sm.getString("HiParserTlvHandler.parserItem3", this.tag));
/*     */       }
/*     */ 
/* 684 */       etfBody.setGrandChildNode(this.etf_name, this.tag);
/*     */ 
/* 686 */       ByteArrayInputStream in = new ByteArrayInputStream(bytes);
/* 687 */       convertToETF(ctx, in, etfBody, cfgMap, log);
/*     */ 
/* 689 */       if (log.isInfoEnabled())
/* 690 */         log.info(this.sm.getString("HiParserTlvHandler.parserItem4", this.tag));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addETFItem(HiMessageContext ctx, byte[] bytes, HiETF etfBody, String etf_name, Logger log)
/*     */     throws HiException
/*     */   {
/*     */     String val;
/* 700 */     switch (this.data_type)
/*     */     {
/*     */     case 0:
/* 704 */       val = Long.valueOf(HiConvHelper.bcd2AscStr(bytes), 16).toString();
/* 705 */       break;
/*     */     case 1:
/* 708 */       val = HiConvHelper.bcd2AscStr(bytes);
/* 709 */       break;
/*     */     case 2:
/* 712 */       val = new String(bytes);
/* 713 */       break;
/*     */     default:
/* 716 */       val = new String(bytes);
/*     */     }
/*     */ 
/* 721 */     etfBody.setGrandChildNode(etf_name, val);
/* 722 */     if (log.isInfoEnabled())
/* 723 */       log.info(this.sm.getString("HiParserTlvHandler.parserItem2", this.tag, etf_name, val));
/*     */   }
/*     */ 
/*     */   public int getHeadLen(ByteArrayInputStream in) throws HiException
/*     */   {
/* 728 */     int ret = 0;
/* 729 */     byte[] retBytes = null;
/*     */ 
/* 731 */     retBytes = new byte[this.head_len];
/* 732 */     ret = in.read(retBytes, 0, this.head_len);
/*     */ 
/* 734 */     if (ret < this.head_len)
/*     */     {
/* 736 */       throw new HiException("231505", " while get head len.");
/*     */     }
/*     */ 
/* 739 */     switch (this.len_type)
/*     */     {
/*     */     case 1:
/* 742 */       return Integer.parseInt(new String(retBytes));
/*     */     }
/* 744 */     return Integer.valueOf(HiConvHelper.bcd2AscStr(retBytes), 16).intValue();
/*     */   }
/*     */ 
/*     */   public void convertToETF(HiMessageContext ctx, ByteArrayInputStream in, HiETF etfBody, Map cfgMap, Logger log)
/*     */     throws HiException
/*     */   {
/* 752 */     byte[] retBytes = null;
/* 753 */     retBytes = new byte[this.child_tag_len];
/*     */ 
/* 756 */     while ((ret = in.read(retBytes, 0, this.child_tag_len)) != -1)
/*     */     {
/*     */       int ret;
/* 758 */       if (ret < this.child_tag_len) {
/* 759 */         throw new HiException("231506", String.valueOf(this.child_tag_len));
/*     */       }
/*     */ 
/* 763 */       makeEtfItem(ctx, in, etfBody, HiTlvHelper.getTag(retBytes, this.child_tag_type), cfgMap, log);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void makeEtfItem(HiMessageContext ctx, ByteArrayInputStream in, HiETF etfBody, String tag, Map cfgMap, Logger log)
/*     */     throws HiException
/*     */   {
/* 771 */     if (log.isInfoEnabled()) {
/* 772 */       log.info(this.sm.getString("HiParserTlvHandler.parserItem0", tag));
/*     */     }
/*     */ 
/* 775 */     HiTlvItem item = (HiTlvItem)cfgMap.get(tag);
/* 776 */     if (item == null)
/*     */     {
/* 778 */       throw new HiException("231507", tag);
/*     */     }
/*     */ 
/* 781 */     item.unPack(ctx, cfgMap, in, etfBody, log);
/*     */   }
/*     */ }