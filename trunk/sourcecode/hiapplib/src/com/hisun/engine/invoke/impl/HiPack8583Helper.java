 package com.hisun.engine.invoke.impl;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.Logger;
 import com.hisun.util.HiConvHelper;
 import com.hisun.util.HiStringManager;
 import java.nio.ByteBuffer;
 import org.apache.commons.codec.DecoderException;
 import org.apache.commons.codec.binary.Hex;
 import org.apache.commons.lang.ArrayUtils;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Element;
 
 public class HiPack8583Helper
 {
   static final HiStringManager sm = HiStringManager.getManager();
   private final String CFG8583_DATA_TYPE_CHARBINASC = "CharBinASCII";
 
   public HiPack8583Helper()
   {
     this.CFG8583_DATA_TYPE_CHARBINASC = "CharBinASCII";
   }
 
   static byte[] fitPlain(byte[] valByte, Element itemNode, Logger log)
     throws HiException
   {
     String field_id = itemNode.attributeValue("field_id");
 
     String length_type = itemNode.attributeValue("length_type");
 
     String data_type = itemNode.attributeValue("data_type");
 
     if (length_type.equals("CONST")) {
       int length = Integer.parseInt(itemNode.attributeValue("length"));
 
       return fitConstPlain(itemNode, field_id, valByte, data_type, length, log);
     }
     if (length_type.equals("VAR2"))
       return fitVarPlain(itemNode, field_id, valByte, data_type, 2, log);
     if (length_type.equals("VAR3")) {
       return fitVarPlain(itemNode, field_id, valByte, data_type, 3, log);
     }
 
     throw new HiException("213227", "待组包的域,field_id[" + field_id + "], 该长度类型 length_type[" + length_type + "] 有误.");
   }
 
   static byte[] fitConstPlain(Element itemNode, String field_id, byte[] valBytes, String data_type, int length, Logger log)
     throws HiException
   {
     int valLen = valBytes.length;
 
     if (valLen > length) {
       valBytes = ArrayUtils.subarray(valBytes, 0, length);
     }
 
     if ((!(data_type.endsWith("ASCII"))) && (length % 2 != 0)) {
       ++length;
     }
 
     if (valLen < length)
     {
       String fill_asc = itemNode.attributeValue("fill_asc");
 
       if (data_type.equals("CharASCII"))
       {
         valBytes = fillCharRight(valBytes, fill_asc, itemNode.attributeValue("align_mode"), length - valLen);
       }
       else
       {
         valBytes = fillCharLeft(valBytes, fill_asc, itemNode.attributeValue("align_mode"), length - valLen);
       }
 
     }
 
     if (!(data_type.endsWith("ASCII"))) {
       valBytes = ascStr2Bcd(valBytes);
     }
 
     if (log.isDebugEnabled()) {
       log.debug(sm.getString("HiPack8583.packItem0", field_id, new String(valBytes)));
     }
 
     return valBytes;
   }
 
   static byte[] fitVarPlain(Element itemNode, String field_id, byte[] valBytes, String data_type, int varLen, Logger log)
     throws HiException
   {
     byte[] valLenBytes;
     int valueLen = valBytes.length;
 
     int valueAllocLen = valueLen;
     int varAllocLen = varLen;
 
     if (!(data_type.endsWith("ASCII"))) {
       if (valueLen % 2 != 0)
       {
         valBytes = fillCharLeft(valBytes, itemNode.attributeValue("fill_asc"), itemNode.attributeValue("align_mode"), 1);
 
         ++valueAllocLen;
       }
 
       valBytes = ascStr2Bcd(valBytes);
 
       valueAllocLen /= 2;
     } else if (StringUtils.equals(data_type, "CharBinASCII")) {
       valBytes = HiConvHelper.ascStr2Bcd(new String(valBytes));
       valueLen = valBytes.length;
       valueAllocLen = valueLen;
     }
 
     String valLenStr = String.valueOf(valueLen);
     if (valLenStr.length() > varLen) {
       throw new HiException("213228", String.valueOf(varLen), String.valueOf(field_id));
     }
 
     if (StringUtils.equals(itemNode.attributeValue("var_type"), "bin"))
     {
       if (varLen % 2 != 0) {
         ++varAllocLen;
       }
 
       if (valLenStr.length() < varAllocLen) {
         valLenStr = StringUtils.leftPad(valLenStr, varAllocLen, '0');
       }
 
       valLenBytes = HiConvHelper.ascStr2Bcd(valLenStr);
 
       varAllocLen /= 2;
     } else {
       if (valLenStr.length() < varLen) {
         valLenStr = StringUtils.leftPad(valLenStr, varLen, '0');
       }
 
       valLenBytes = valLenStr.getBytes();
     }
 
     ByteBuffer bb = ByteBuffer.allocate(varAllocLen + valueAllocLen);
     bb.put(valLenBytes);
     bb.put(valBytes);
 
     if (log.isDebugEnabled()) {
       log.debug(sm.getString("HiPack8583.packItem1", String.valueOf(varLen), field_id, new String(bb.array())));
     }
 
     return bb.array();
   }
 
   static byte[] fillCharLeft(byte[] value, String fill_asc, String align_mode, int repeat)
   {
     int i;
     if (repeat <= 0) {
       return value;
     }
     if (StringUtils.isEmpty(fill_asc)) {
       fill_asc = "48";
     }
 
     byte b = Integer.valueOf(fill_asc).byteValue();
     byte[] fillValue = new byte[value.length + repeat];
 
     if (StringUtils.equals(align_mode, "left")) {
       System.arraycopy(value, 0, fillValue, 0, value.length);
       for (i = 0; i < repeat; ++i)
         fillValue[(value.length + i)] = b;
     }
     else {
       for (i = 0; i < repeat; ++i) {
         fillValue[i] = b;
       }
       System.arraycopy(value, 0, fillValue, repeat, value.length);
     }
     return fillValue;
   }
 
   static byte[] fillCharRight(byte[] value, String fill_asc, String align_mode, int repeat)
   {
     int i;
     if (repeat <= 0) {
       return value;
     }
     if (StringUtils.isEmpty(fill_asc)) {
       fill_asc = "32";
     }
 
     byte b = Integer.valueOf(fill_asc).byteValue();
     byte[] fillValue = new byte[value.length + repeat];
 
     if (StringUtils.equals(align_mode, "right")) {
       for (i = 0; i < repeat; ++i) {
         fillValue[i] = b;
       }
       System.arraycopy(value, 0, fillValue, repeat, value.length);
     } else {
       System.arraycopy(value, 0, fillValue, 0, value.length);
       for (i = 0; i < repeat; ++i) {
         fillValue[(value.length + i)] = b;
       }
     }
     return fillValue;
   }
 
   static byte[] ascStr2Bcd(byte[] bytes)
     throws HiException
   {
     Hex hex = new Hex();
     try {
       bytes = hex.decode(bytes);
       hex = null;
     }
     catch (DecoderException e) {
       throw new HiException(e);
     }
 
     return bytes;
   }
 }