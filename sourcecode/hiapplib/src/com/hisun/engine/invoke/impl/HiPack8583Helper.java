/*     */ package com.hisun.engine.invoke.impl;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.util.HiConvHelper;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.nio.ByteBuffer;
/*     */ import org.apache.commons.codec.DecoderException;
/*     */ import org.apache.commons.codec.binary.Hex;
/*     */ import org.apache.commons.lang.ArrayUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.Element;
/*     */ 
/*     */ public class HiPack8583Helper
/*     */ {
/*  21 */   static final HiStringManager sm = HiStringManager.getManager();
/*     */   private final String CFG8583_DATA_TYPE_CHARBINASC = "CharBinASCII";
/*     */ 
/*     */   public HiPack8583Helper()
/*     */   {
/*  22 */     this.CFG8583_DATA_TYPE_CHARBINASC = "CharBinASCII";
/*     */   }
/*     */ 
/*     */   static byte[] fitPlain(byte[] valByte, Element itemNode, Logger log)
/*     */     throws HiException
/*     */   {
/*  35 */     String field_id = itemNode.attributeValue("field_id");
/*     */ 
/*  37 */     String length_type = itemNode.attributeValue("length_type");
/*     */ 
/*  40 */     String data_type = itemNode.attributeValue("data_type");
/*     */ 
/*  43 */     if (length_type.equals("CONST")) {
/*  44 */       int length = Integer.parseInt(itemNode.attributeValue("length"));
/*     */ 
/*  46 */       return fitConstPlain(itemNode, field_id, valByte, data_type, length, log);
/*     */     }
/*  48 */     if (length_type.equals("VAR2"))
/*  49 */       return fitVarPlain(itemNode, field_id, valByte, data_type, 2, log);
/*  50 */     if (length_type.equals("VAR3")) {
/*  51 */       return fitVarPlain(itemNode, field_id, valByte, data_type, 3, log);
/*     */     }
/*     */ 
/*  54 */     throw new HiException("213227", "待组包的域,field_id[" + field_id + "], 该长度类型 length_type[" + length_type + "] 有误.");
/*     */   }
/*     */ 
/*     */   static byte[] fitConstPlain(Element itemNode, String field_id, byte[] valBytes, String data_type, int length, Logger log)
/*     */     throws HiException
/*     */   {
/*  74 */     int valLen = valBytes.length;
/*     */ 
/*  77 */     if (valLen > length) {
/*  78 */       valBytes = ArrayUtils.subarray(valBytes, 0, length);
/*     */     }
/*     */ 
/*  82 */     if ((!(data_type.endsWith("ASCII"))) && (length % 2 != 0)) {
/*  83 */       ++length;
/*     */     }
/*     */ 
/*  86 */     if (valLen < length)
/*     */     {
/*  88 */       String fill_asc = itemNode.attributeValue("fill_asc");
/*     */ 
/*  91 */       if (data_type.equals("CharASCII"))
/*     */       {
/*  93 */         valBytes = fillCharRight(valBytes, fill_asc, itemNode.attributeValue("align_mode"), length - valLen);
/*     */       }
/*     */       else
/*     */       {
/*  99 */         valBytes = fillCharLeft(valBytes, fill_asc, itemNode.attributeValue("align_mode"), length - valLen);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 105 */     if (!(data_type.endsWith("ASCII"))) {
/* 106 */       valBytes = ascStr2Bcd(valBytes);
/*     */     }
/*     */ 
/* 109 */     if (log.isDebugEnabled()) {
/* 110 */       log.debug(sm.getString("HiPack8583.packItem0", field_id, new String(valBytes)));
/*     */     }
/*     */ 
/* 116 */     return valBytes;
/*     */   }
/*     */ 
/*     */   static byte[] fitVarPlain(Element itemNode, String field_id, byte[] valBytes, String data_type, int varLen, Logger log)
/*     */     throws HiException
/*     */   {
/*     */     byte[] valLenBytes;
/* 136 */     int valueLen = valBytes.length;
/*     */ 
/* 138 */     int valueAllocLen = valueLen;
/* 139 */     int varAllocLen = varLen;
/*     */ 
/* 142 */     if (!(data_type.endsWith("ASCII"))) {
/* 143 */       if (valueLen % 2 != 0)
/*     */       {
/* 145 */         valBytes = fillCharLeft(valBytes, itemNode.attributeValue("fill_asc"), itemNode.attributeValue("align_mode"), 1);
/*     */ 
/* 148 */         ++valueAllocLen;
/*     */       }
/*     */ 
/* 152 */       valBytes = ascStr2Bcd(valBytes);
/*     */ 
/* 154 */       valueAllocLen /= 2;
/* 155 */     } else if (StringUtils.equals(data_type, "CharBinASCII")) {
/* 156 */       valBytes = HiConvHelper.ascStr2Bcd(new String(valBytes));
/* 157 */       valueLen = valBytes.length;
/* 158 */       valueAllocLen = valueLen;
/*     */     }
/*     */ 
/* 162 */     String valLenStr = String.valueOf(valueLen);
/* 163 */     if (valLenStr.length() > varLen) {
/* 164 */       throw new HiException("213228", String.valueOf(varLen), String.valueOf(field_id));
/*     */     }
/*     */ 
/* 170 */     if (StringUtils.equals(itemNode.attributeValue("var_type"), "bin"))
/*     */     {
/* 174 */       if (varLen % 2 != 0) {
/* 175 */         ++varAllocLen;
/*     */       }
/*     */ 
/* 178 */       if (valLenStr.length() < varAllocLen) {
/* 179 */         valLenStr = StringUtils.leftPad(valLenStr, varAllocLen, '0');
/*     */       }
/*     */ 
/* 182 */       valLenBytes = HiConvHelper.ascStr2Bcd(valLenStr);
/*     */ 
/* 184 */       varAllocLen /= 2;
/*     */     } else {
/* 186 */       if (valLenStr.length() < varLen) {
/* 187 */         valLenStr = StringUtils.leftPad(valLenStr, varLen, '0');
/*     */       }
/*     */ 
/* 190 */       valLenBytes = valLenStr.getBytes();
/*     */     }
/*     */ 
/* 194 */     ByteBuffer bb = ByteBuffer.allocate(varAllocLen + valueAllocLen);
/* 195 */     bb.put(valLenBytes);
/* 196 */     bb.put(valBytes);
/*     */ 
/* 198 */     if (log.isDebugEnabled()) {
/* 199 */       log.debug(sm.getString("HiPack8583.packItem1", String.valueOf(varLen), field_id, new String(bb.array())));
/*     */     }
/*     */ 
/* 204 */     return bb.array();
/*     */   }
/*     */ 
/*     */   static byte[] fillCharLeft(byte[] value, String fill_asc, String align_mode, int repeat)
/*     */   {
/*     */     int i;
/* 218 */     if (repeat <= 0) {
/* 219 */       return value;
/*     */     }
/* 221 */     if (StringUtils.isEmpty(fill_asc)) {
/* 222 */       fill_asc = "48";
/*     */     }
/*     */ 
/* 225 */     byte b = Integer.valueOf(fill_asc).byteValue();
/* 226 */     byte[] fillValue = new byte[value.length + repeat];
/*     */ 
/* 228 */     if (StringUtils.equals(align_mode, "left")) {
/* 229 */       System.arraycopy(value, 0, fillValue, 0, value.length);
/* 230 */       for (i = 0; i < repeat; ++i)
/* 231 */         fillValue[(value.length + i)] = b;
/*     */     }
/*     */     else {
/* 234 */       for (i = 0; i < repeat; ++i) {
/* 235 */         fillValue[i] = b;
/*     */       }
/* 237 */       System.arraycopy(value, 0, fillValue, repeat, value.length);
/*     */     }
/* 239 */     return fillValue;
/*     */   }
/*     */ 
/*     */   static byte[] fillCharRight(byte[] value, String fill_asc, String align_mode, int repeat)
/*     */   {
/*     */     int i;
/* 266 */     if (repeat <= 0) {
/* 267 */       return value;
/*     */     }
/* 269 */     if (StringUtils.isEmpty(fill_asc)) {
/* 270 */       fill_asc = "32";
/*     */     }
/*     */ 
/* 273 */     byte b = Integer.valueOf(fill_asc).byteValue();
/* 274 */     byte[] fillValue = new byte[value.length + repeat];
/*     */ 
/* 277 */     if (StringUtils.equals(align_mode, "right")) {
/* 278 */       for (i = 0; i < repeat; ++i) {
/* 279 */         fillValue[i] = b;
/*     */       }
/* 281 */       System.arraycopy(value, 0, fillValue, repeat, value.length);
/*     */     } else {
/* 283 */       System.arraycopy(value, 0, fillValue, 0, value.length);
/* 284 */       for (i = 0; i < repeat; ++i) {
/* 285 */         fillValue[(value.length + i)] = b;
/*     */       }
/*     */     }
/* 288 */     return fillValue;
/*     */   }
/*     */ 
/*     */   static byte[] ascStr2Bcd(byte[] bytes)
/*     */     throws HiException
/*     */   {
/* 316 */     Hex hex = new Hex();
/*     */     try {
/* 318 */       bytes = hex.decode(bytes);
/* 319 */       hex = null;
/*     */     }
/*     */     catch (DecoderException e) {
/* 322 */       throw new HiException(e);
/*     */     }
/*     */ 
/* 325 */     return bytes;
/*     */   }
/*     */ }