/*     */ package com.hisun.atmp;
/*     */ 
/*     */ import com.hisun.atmp.crypt.DES;
/*     */ import com.hisun.atmp.crypt.Shuffle;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.RandomUtils;
/*     */ 
/*     */ public class HiEncDec
/*     */ {
/*  19 */   private static final char[] KEY2 = { '\1', '\1', '\1', '\1', '\1', '\1', '\1', '\1' };
/*     */ 
/* 295 */   private static char[] DieboldMaskterKey = { 136, 136, 136, 136, 136, 136, 136, 136 };
/*     */ 
/*     */   public static int MaskKey(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  23 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/*  24 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*     */ 
/*  26 */     String keynode = argsMap.get("key");
/*  27 */     String key = root.getChildValue(keynode);
/*  28 */     char[] m_key = key.toCharArray();
/*  29 */     Shuffle.shufkey(m_key);
/*  30 */     String skey = new String(m_key);
/*     */ 
/*  32 */     if (log.isInfoEnabled()) {
/*  33 */       log.info("maskkey : before = [" + key + "],after=[" + skey + "]");
/*     */     }
/*  35 */     root.setChildValue(keynode, skey);
/*  36 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int Shuffle(HiATLParam argsMap, HiMessageContext ctx) throws HiException
/*     */   {
/*  41 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/*  42 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*     */ 
/*  44 */     String key = argsMap.get("key");
/*  45 */     String data = argsMap.get("data");
/*  46 */     if (log.isInfoEnabled()) {
/*  47 */       log.info("shuffle parameters: key = [" + key + "],data=[" + data + "]");
/*     */     }
/*     */ 
/*  50 */     String outnode = argsMap.get("output");
/*     */ 
/*  52 */     char[] out = new char[data.length()];
/*  53 */     char[] charArray = data.toCharArray();
/*  54 */     char[] charArray2 = key.toCharArray();
/*  55 */     Shuffle.shufkey(charArray2);
/*  56 */     Shuffle.shuffle(charArray, charArray2, out, charArray.length);
/*     */ 
/*  58 */     String ret = new String(out);
/*  59 */     if (log.isInfoEnabled()) {
/*  60 */       log.info("shuffle output:" + ret);
/*     */     }
/*  62 */     root.setChildValue(outnode, ret);
/*  63 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int UnShuffle(HiATLParam argsMap, HiMessageContext ctx) throws HiException
/*     */   {
/*  68 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/*  69 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*     */ 
/*  71 */     String key = argsMap.get("key");
/*  72 */     String data = argsMap.get("data");
/*  73 */     if (log.isInfoEnabled()) {
/*  74 */       log.info("unshuffle parameters: key = [" + key + "],data=[" + data + "]");
/*     */     }
/*     */ 
/*  77 */     String outnode = argsMap.get("output");
/*     */ 
/*  79 */     char[] out = new char[data.length()];
/*  80 */     char[] charArray = key.toCharArray();
/*  81 */     Shuffle.shufkey(charArray);
/*  82 */     Shuffle.unshuffle(data.toCharArray(), charArray, out, out.length);
/*     */ 
/*  84 */     String ret = new String(out);
/*  85 */     if (log.isInfoEnabled()) {
/*  86 */       log.info("unshuffle output:" + ret);
/*     */     }
/*  88 */     root.setChildValue(outnode, ret);
/*  89 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int UnShuffleConvert(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  95 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/*  96 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*     */ 
/*  98 */     String key = argsMap.get("key");
/*  99 */     String data = argsMap.get("data");
/* 100 */     if (log.isInfoEnabled()) {
/* 101 */       log.info("unshuffle parameters: key = [" + key + "],data=[" + data + "]");
/*     */     }
/*     */ 
/* 104 */     String outnode = argsMap.get("output");
/*     */ 
/* 106 */     char[] out = new char[data.length()];
/* 107 */     char[] charArray = key.toCharArray();
/* 108 */     Shuffle.shufkey(charArray);
/* 109 */     Shuffle.unshuffle(data.toCharArray(), charArray, out, out.length);
/*     */ 
/* 111 */     char[] o = new char[16];
/* 112 */     o[0] = '0';
/* 113 */     o[1] = '6';
/* 114 */     for (int i = 2; i < 8; ++i)
/* 115 */       o[i] = out[i];
/* 116 */     for (i = 8; i < 16; ++i) {
/* 117 */       o[i] = 'F';
/*     */     }
/* 119 */     String brno = argsMap.get("brno");
/* 120 */     String crdno = argsMap.get("crdno");
/* 121 */     if (log.isInfoEnabled()) {
/* 122 */       log.info("brno:" + brno + ",crdno:" + crdno);
/*     */     }
/* 124 */     char[] result2 = encry_pass(log, brno.toCharArray(), crdno.toCharArray(), o);
/*     */ 
/* 127 */     String out2 = new String(result2);
/* 128 */     String ret = out2;
/* 129 */     if (log.isInfoEnabled()) {
/* 130 */       log.info("UnShuffleAndDES output:" + ret);
/*     */     }
/* 132 */     root.setChildValue(outnode, ret);
/* 133 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int UnShuffleAccount(HiATLParam argsMap, HiMessageContext ctx) throws HiException
/*     */   {
/* 138 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/* 139 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*     */ 
/* 141 */     String key = argsMap.get("key");
/* 142 */     String data = argsMap.get("data");
/* 143 */     if (log.isInfoEnabled()) {
/* 144 */       log.info("unshuffle parameters: key = [" + key + "],data=[" + data + "]");
/*     */     }
/*     */ 
/* 147 */     String outnode = argsMap.get("output");
/*     */ 
/* 149 */     String pre = ""; String end = "";
/* 150 */     pre = getPre(data);
/* 151 */     end = getEnd(data);
/* 152 */     data = data.substring(2, 18);
/* 153 */     if (log.isInfoEnabled()) {
/* 154 */       log.info("账号:" + data);
/*     */     }
/*     */ 
/* 157 */     char[] out = new char[data.length()];
/* 158 */     char[] charArray = key.toCharArray();
/* 159 */     Shuffle.shufkey(charArray);
/* 160 */     Shuffle.unshuffle(data.toCharArray(), charArray, out, out.length);
/*     */ 
/* 162 */     String ret = new String(out);
/* 163 */     ret = pre + ret + end;
/* 164 */     if (log.isInfoEnabled()) {
/* 165 */       log.info("unshuffle output:" + ret);
/*     */     }
/* 167 */     root.setChildValue(outnode, ret);
/* 168 */     return 0;
/*     */   }
/*     */ 
/*     */   private static String getEnd(String data) {
/* 172 */     int iend = data.length() - 1;
/* 173 */     for (int i = iend; i > 0; --i) {
/* 174 */       if (data.charAt(i) != 'A') {
/* 175 */         iend = i;
/* 176 */         break;
/*     */       }
/*     */     }
/*     */ 
/* 180 */     String end = data.substring(18, iend + 1);
/* 181 */     return end;
/*     */   }
/*     */ 
/*     */   private static String getPre(String data) {
/* 185 */     int istart = 0;
/* 186 */     for (int i = 0; i < data.length(); ++i) {
/* 187 */       if (data.charAt(i) != 'A') {
/* 188 */         istart = i;
/* 189 */         break;
/*     */       }
/*     */     }
/* 192 */     return data.substring(istart, 2);
/*     */   }
/*     */ 
/*     */   public static int UpdateKey(HiATLParam argsMap, HiMessageContext ctx) throws HiException
/*     */   {
/* 197 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/* 198 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*     */ 
/* 200 */     String keynode = argsMap.get("key");
/* 201 */     String keyValue = root.getChildValue(keynode);
/* 202 */     if (log.isInfoEnabled()) {
/* 203 */       log.info("updateKey parameters: key = [" + keyValue + "]");
/*     */     }
/*     */ 
/* 206 */     char[] key = keyValue.toCharArray();
/* 207 */     Shuffle.shufkey(key);
/* 208 */     Shuffle.updatetrm(key);
/* 209 */     Shuffle.shufkey(key);
/*     */ 
/* 211 */     String ret = new String(key);
/* 212 */     if (log.isInfoEnabled()) {
/* 213 */       log.info("updateKey output:" + ret);
/*     */     }
/* 215 */     root.setChildValue(keynode, ret);
/* 216 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int PinConvert(HiATLParam argsMap, HiMessageContext ctx) throws HiException
/*     */   {
/* 221 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/* 222 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*     */     try
/*     */     {
/* 225 */       String keynode = argsMap.get("key");
/* 226 */       char[] key = keynode.toCharArray();
/* 227 */       Shuffle.shufkey(key);
/*     */ 
/* 229 */       String spblock = argsMap.get("pinblock");
/* 230 */       char[] pinblock = spblock.toCharArray();
/*     */ 
/* 232 */       if (log.isInfoEnabled()) {
/* 233 */         log.info("pinConvert parameters: key = [" + keynode + "],pinblock=[" + spblock + "]");
/*     */ 
/* 235 */         log.info("key:" + new String(key));
/*     */       }
/*     */ 
/* 238 */       char[] packed_pin = new char[pinblock.length / 2];
/* 239 */       Shuffle.strToBin(packed_pin, pinblock, packed_pin.length);
/*     */ 
/* 241 */       if (log.isInfoEnabled()) {
/* 242 */         log.info("packed pinblock:" + new String(packed_pin));
/*     */       }
/*     */ 
/* 245 */       char[] encryptKey = PINBlockKey(key);
/*     */ 
/* 247 */       if (log.isInfoEnabled()) {
/* 248 */         log.info("PINBlockKey:" + new String(encryptKey));
/*     */       }
/*     */ 
/* 251 */       char[] result = desEncrypt(encryptKey, packed_pin, 0);
/*     */ 
/* 253 */       if (log.isInfoEnabled()) {
/* 254 */         log.info("des pinblock:" + new String(result));
/*     */       }
/*     */ 
/* 258 */       char[] ret = new char[16];
/* 259 */       Shuffle.binToStr(ret, result, 8);
/*     */ 
/* 262 */       int pinlen = argsMap.getInt("pinlen");
/* 263 */       String atmid = argsMap.get("atmid");
/* 264 */       if (log.isInfoEnabled()) {
/* 265 */         log.info("pinblock:" + new String(ret));
/* 266 */         log.info("checking: pinlen=" + pinlen + ",atmid=" + atmid);
/*     */       }
/*     */ 
/* 269 */       char[] output1 = new char[16];
/* 270 */       int iret = checkPinDate(ret, atmid, pinlen, output1);
/* 271 */       if (iret == 0) {
/* 272 */         String brno = argsMap.get("brno");
/* 273 */         String crdno = argsMap.get("crdno");
/* 274 */         if (log.isInfoEnabled()) {
/* 275 */           log.info("brno:" + brno + ",crdno:" + crdno);
/*     */         }
/* 277 */         char[] result2 = encry_pass(log, brno.toCharArray(), crdno.toCharArray(), output1);
/*     */ 
/* 280 */         String outnode = argsMap.get("output");
/* 281 */         String out2 = new String(result2);
/* 282 */         if (log.isInfoEnabled()) {
/* 283 */           log.info("pinConvert output:" + out2);
/*     */         }
/* 285 */         root.setChildValue(outnode, out2);
/*     */       }
/* 287 */       return iret;
/*     */     } catch (RuntimeException t) {
/* 289 */       log.error(t.toString());
/* 290 */       throw t;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static int DieboldDesPin(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 307 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/* 308 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*     */ 
/* 311 */     char[] key = genRandomKey();
/*     */ 
/* 313 */     if (log.isInfoEnabled()) {
/* 314 */       String pinkey = new String(key);
/* 315 */       log.info("DieboldDesPin parameters: pinkey = [" + pinkey + "]");
/*     */     }
/*     */ 
/* 319 */     char[] packed_pin = new char[key.length / 2];
/* 320 */     Shuffle.strToBCD(packed_pin, key, packed_pin.length);
/*     */ 
/* 323 */     char[] pin_key = desEncrypt(DieboldMaskterKey, packed_pin, 1);
/*     */ 
/* 325 */     char[] d = new char[16];
/* 326 */     Shuffle.bcdToStr(d, pin_key, 8);
/* 327 */     String key16 = new String(d);
/* 328 */     if (log.isInfoEnabled()) {
/* 329 */       log.info("DieboldDesPin output: key16 = [" + key16 + "]");
/*     */     }
/*     */ 
/* 333 */     StringBuffer buf = new StringBuffer(24);
/* 334 */     for (int i = 0; i < 8; ++i) {
/* 335 */       int v = pin_key[i] & 0xFF;
/* 336 */       buf.append(StringUtils.leftPad(String.valueOf(v), 3, '0'));
/*     */     }
/* 338 */     String out_key = buf.toString();
/* 339 */     if (log.isInfoEnabled()) {
/* 340 */       log.info("DieboldDesPin output: key24 = [" + out_key + "]");
/*     */     }
/*     */ 
/* 344 */     String keynode = argsMap.get("key24");
/* 345 */     root.setChildValue(keynode, out_key);
/* 346 */     String keynode16 = argsMap.get("key16");
/* 347 */     root.setChildValue(keynode16, key16);
/* 348 */     return 0;
/*     */   }
/*     */ 
/*     */   private static char[] genRandomKey() {
/* 352 */     char[] key = new char[16];
/* 353 */     for (int i = 0; i < 16; ++i)
/* 354 */       key[i] = genRandomNum();
/* 355 */     return key;
/*     */   }
/*     */ 
/*     */   private static char genRandomNum() {
/* 359 */     return (char)(RandomUtils.nextInt(10) + 48);
/*     */   }
/*     */ 
/*     */   public static int DieboldPinConvert(HiATLParam argsMap, HiMessageContext ctx) throws HiException
/*     */   {
/* 364 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/* 365 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*     */ 
/* 367 */     String keynode = argsMap.get("key");
/* 368 */     char[] key = keynode.toCharArray();
/*     */ 
/* 370 */     String spblock = argsMap.get("pinblock");
/* 371 */     char[] pinblock = spblock.toCharArray();
/*     */ 
/* 373 */     if (log.isInfoEnabled()) {
/* 374 */       log.info("DieboldPinConvert parameters: key = [" + keynode + "],pinblock=[" + spblock + "]");
/*     */     }
/*     */ 
/* 378 */     char[] packed_pin = new char[pinblock.length / 2];
/* 379 */     Shuffle.strToBin(packed_pin, pinblock, packed_pin.length);
/*     */ 
/* 381 */     if (log.isInfoEnabled()) {
/* 382 */       log.info("packed pinblock:" + new String(packed_pin));
/*     */     }
/*     */ 
/* 385 */     char[] packed_key = new char[8];
/* 386 */     Shuffle.strToBCD(packed_key, key, packed_key.length);
/*     */ 
/* 389 */     char[] pin_key = desEncrypt(DieboldMaskterKey, packed_key, 0);
/*     */ 
/* 391 */     char[] ret = desEncrypt(pin_key, packed_pin, 0);
/*     */ 
/* 393 */     char[] d = new char[16];
/* 394 */     Shuffle.bcdToStr(d, ret, 8);
/* 395 */     if (log.isInfoEnabled()) {
/* 396 */       log.info("diebold pinblock:" + new String(d));
/*     */     }
/*     */ 
/* 399 */     int i = 0;
/* 400 */     for (; i < d.length; ++i) {
/* 401 */       if (d[i] == 'F')
/*     */         break;
/*     */     }
/* 404 */     String prestr = String.valueOf(i);
/* 405 */     prestr = StringUtils.leftPad(prestr, 2, "0");
/*     */ 
/* 408 */     String lennode = argsMap.get("pinlen");
/* 409 */     if (lennode != null) {
/* 410 */       root.setChildValue(lennode, prestr);
/* 411 */       if (log.isInfoEnabled()) {
/* 412 */         log.info("set pinlen:" + prestr);
/*     */       }
/*     */     }
/*     */ 
/* 416 */     char[] ret2 = new char[16];
/* 417 */     ret2[0] = prestr.charAt(0);
/* 418 */     ret2[1] = prestr.charAt(1);
/* 419 */     System.arraycopy(d, 0, ret2, 2, 14);
/*     */ 
/* 421 */     String brno = argsMap.get("brno");
/* 422 */     String crdno = argsMap.get("crdno");
/* 423 */     if (log.isInfoEnabled()) {
/* 424 */       log.info("brno:" + brno + ",crdno:" + crdno);
/*     */     }
/* 426 */     char[] result2 = encry_pass(log, brno.toCharArray(), crdno.toCharArray(), ret2);
/*     */ 
/* 429 */     String outnode = argsMap.get("output");
/* 430 */     String out2 = new String(result2);
/* 431 */     if (log.isInfoEnabled()) {
/* 432 */       log.info("DieboldPinConvert output:" + out2);
/*     */     }
/* 434 */     root.setChildValue(outnode, out2);
/*     */ 
/* 437 */     return 0;
/*     */   }
/*     */ 
/*     */   private static char[] encry_pass(Logger log, char[] brno, char[] crdno, char[] pin)
/*     */   {
/* 454 */     if (log.isInfoEnabled()) {
/* 455 */       log.info("pin code:" + new String(pin));
/*     */     }
/*     */ 
/* 458 */     char[] key = new char[16];
/* 459 */     key[0] = '1';
/* 460 */     key[1] = '6';
/* 461 */     System.arraycopy(brno, 0, key, 2, 3);
/* 462 */     System.arraycopy(crdno, crdno.length - 12, key, 5, 11);
/*     */ 
/* 464 */     if (log.isInfoEnabled()) {
/* 465 */       log.info("key:" + new String(key));
/*     */     }
/* 467 */     char[] packed_key = new char[8];
/* 468 */     Shuffle.strToBin(packed_key, key, 8);
/*     */ 
/* 471 */     char[] packed_pin1 = new char[pin.length / 2];
/* 472 */     Shuffle.strToBCD(packed_pin1, pin, packed_pin1.length);
/* 473 */     char[] xor = new char[16];
/* 474 */     for (int i = 0; i < 16; ++i)
/* 475 */       xor[i] = '0';
/* 476 */     System.arraycopy(crdno, crdno.length - 13, xor, 4, 12);
/* 477 */     char[] packed_xor = new char[8];
/* 478 */     Shuffle.strToBCD(packed_xor, xor, packed_xor.length);
/*     */ 
/* 480 */     for (int i = 0; i < 8; ++i) {
/* 481 */       packed_pin1[i] = (char)(packed_pin1[i] ^ packed_xor[i]);
/*     */     }
/* 483 */     if (log.isInfoEnabled()) {
/* 484 */       char[] d = new char[16];
/* 485 */       Shuffle.bcdToStr(d, packed_pin1, 8);
/* 486 */       log.info("data:" + new String(d));
/*     */     }
/*     */ 
/* 489 */     char[] result2 = desEncrypt(packed_key, packed_pin1, 1);
/*     */ 
/* 491 */     char[] result = new char[16];
/* 492 */     Shuffle.bcdToStr(result, result2, 8);
/* 493 */     return result;
/*     */   }
/*     */ 
/*     */   private static int checkPinDate(char[] data, String atmid, int pinlen, char[] output)
/*     */   {
/* 498 */     if (!(new String(data).startsWith(atmid)))
/* 499 */       return 1;
/* 500 */     int start = atmid.length();
/* 501 */     if (start + pinlen > data.length) {
/* 502 */       return 2;
/*     */     }
/* 504 */     boolean ok = true;
/* 505 */     for (int i = start + pinlen; i < data.length; ++i) {
/* 506 */       if (data[i] != '0') {
/* 507 */         ok = false;
/* 508 */         break;
/*     */       }
/*     */     }
/* 511 */     if (!(ok))
/* 512 */       return 2;
/* 513 */     char[] ret = output;
/* 514 */     for (int j = 0; j < ret.length; ++j) {
/* 515 */       ret[j] = 'F';
/*     */     }
/* 517 */     String prestr = String.valueOf(pinlen);
/* 518 */     prestr = StringUtils.leftPad(prestr, 2, "0");
/*     */ 
/* 521 */     ret[0] = prestr.charAt(0);
/* 522 */     ret[1] = prestr.charAt(1);
/*     */ 
/* 525 */     System.arraycopy(data, start, ret, 2, pinlen);
/* 526 */     return 0;
/*     */   }
/*     */ 
/*     */   private static char[] desEncrypt(char[] key, char[] data, int flag) {
/* 530 */     DES des = new DES();
/*     */ 
/* 533 */     byte[] result = des.DesEncrypt(chars2bytes(key), chars2bytes(data), flag);
/*     */ 
/* 535 */     char[] ret = new char[result.length];
/*     */ 
/* 537 */     for (int i = 0; i < key.length; ++i)
/* 538 */       ret[i] = (char)result[i];
/* 539 */     return ret;
/*     */   }
/*     */ 
/*     */   private static byte[] chars2bytes(char[] key) {
/* 543 */     byte[] b_key = new byte[key.length];
/* 544 */     for (int i = 0; i < key.length; ++i)
/* 545 */       b_key[i] = (byte)key[i];
/* 546 */     return b_key;
/*     */   }
/*     */ 
/*     */   private static char[] bytes2chars(byte[] bytes) {
/* 550 */     char[] ret = new char[bytes.length];
/*     */ 
/* 552 */     for (int i = 0; i < bytes.length; ++i)
/* 553 */       ret[i] = (char)bytes[i];
/* 554 */     return ret;
/*     */   }
/*     */ 
/*     */   private static char[] PINBlockKey(char[] key)
/*     */   {
/* 563 */     char[] blockKeyMask = { '1', '1', '2', '2', '4', '4', '7', '7', '8', '8' };
/*     */ 
/* 565 */     char[] key1 = { '0', '0', '0', '0', '0', '0', '0', '0' };
/* 566 */     char[] result = new char[8];
/*     */ 
/* 569 */     System.arraycopy(key, 0, key1, 0, key.length);
/* 570 */     for (int i = 0; i < key1.length; ++i) {
/* 571 */       char c = key1[i];
/* 572 */       if (!(Character.isDigit(c)))
/* 573 */         c = '0';
/* 574 */       result[i] = blockKeyMask[(c - '0')];
/*     */     }
/* 576 */     return result;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*     */   }
/*     */ }