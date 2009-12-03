/*     */ package com.hisun.crypt.mac;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ 
/*     */ public class Base64
/*     */ {
/*     */   static final int CHUNK_SIZE = 76;
/*  22 */   static final byte[] CHUNK_SEPARATOR = "\r\n".getBytes();
/*     */   static final int BASELENGTH = 255;
/*     */   static final int LOOKUPLENGTH = 64;
/*     */   static final int EIGHTBIT = 8;
/*     */   static final int SIXTEENBIT = 16;
/*     */   static final int TWENTYFOURBITGROUP = 24;
/*     */   static final int FOURBYTE = 4;
/*     */   static final int SIGN = -128;
/*     */   static final byte PAD = 61;
/*  66 */   private static byte[] base64Alphabet = new byte[255];
/*  67 */   private static byte[] lookUpBase64Alphabet = new byte[64];
/*     */ 
/*     */   private static boolean isBase64(byte octect)
/*     */   {
/* 104 */     if (octect == 61) {
/* 105 */       return true;
/*     */     }
/* 107 */     return (base64Alphabet[octect] == -1);
/*     */   }
/*     */ 
/*     */   public static boolean isArrayByteBase64(byte[] arrayOctect)
/*     */   {
/* 123 */     arrayOctect = discardWhitespace(arrayOctect);
/*     */ 
/* 125 */     int length = arrayOctect.length;
/* 126 */     if (length == 0)
/*     */     {
/* 129 */       return true;
/*     */     }
/* 131 */     for (int i = 0; i < length; ++i) {
/* 132 */       if (!(isBase64(arrayOctect[i]))) {
/* 133 */         return false;
/*     */       }
/*     */     }
/* 136 */     return true;
/*     */   }
/*     */ 
/*     */   public static byte[] encodeBase64(byte[] binaryData)
/*     */   {
/* 147 */     return encodeBase64(binaryData, false);
/*     */   }
/*     */ 
/*     */   public static byte[] encodeBase64Chunked(byte[] binaryData)
/*     */   {
/* 158 */     return encodeBase64(binaryData, true);
/*     */   }
/*     */ 
/*     */   public static byte[] decode(byte[] pArray)
/*     */   {
/* 169 */     return decodeBase64(pArray);
/*     */   }
/*     */ 
/*     */   public static byte[] encodeBase64(byte[] binaryData, boolean isChunked)
/*     */   {
/*     */     byte val1;
/*     */     byte val2;
/* 182 */     int lengthDataBits = binaryData.length * 8;
/* 183 */     int fewerThan24bits = lengthDataBits % 24;
/* 184 */     int numberTriplets = lengthDataBits / 24;
/* 185 */     byte[] encodedData = null;
/* 186 */     int encodedDataLength = 0;
/* 187 */     int nbrChunks = 0;
/*     */ 
/* 189 */     if (fewerThan24bits != 0)
/*     */     {
/* 191 */       encodedDataLength = (numberTriplets + 1) * 4;
/*     */     }
/*     */     else {
/* 194 */       encodedDataLength = numberTriplets * 4;
/*     */     }
/*     */ 
/* 200 */     if (isChunked)
/*     */     {
/* 202 */       nbrChunks = (CHUNK_SEPARATOR.length == 0) ? 0 : (int)Math.ceil(encodedDataLength / 76.0F);
/*     */ 
/* 204 */       encodedDataLength += nbrChunks * CHUNK_SEPARATOR.length;
/*     */     }
/*     */ 
/* 207 */     encodedData = new byte[encodedDataLength];
/*     */ 
/* 209 */     byte k = 0; byte l = 0; byte b1 = 0; byte b2 = 0; byte b3 = 0;
/*     */ 
/* 211 */     int encodedIndex = 0;
/* 212 */     int dataIndex = 0;
/* 213 */     int i = 0;
/* 214 */     int nextSeparatorIndex = 76;
/* 215 */     int chunksSoFar = 0;
/*     */ 
/* 218 */     for (i = 0; i < numberTriplets; ++i) {
/* 219 */       dataIndex = i * 3;
/* 220 */       b1 = binaryData[dataIndex];
/* 221 */       b2 = binaryData[(dataIndex + 1)];
/* 222 */       b3 = binaryData[(dataIndex + 2)];
/*     */ 
/* 226 */       l = (byte)(b2 & 0xF);
/* 227 */       k = (byte)(b1 & 0x3);
/*     */ 
/* 229 */       val1 = ((b1 & 0xFFFFFF80) == 0) ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 0xC0);
/*     */ 
/* 231 */       val2 = ((b2 & 0xFFFFFF80) == 0) ? (byte)(b2 >> 4) : (byte)(b2 >> 4 ^ 0xF0);
/*     */ 
/* 233 */       byte val3 = ((b3 & 0xFFFFFF80) == 0) ? (byte)(b3 >> 6) : (byte)(b3 >> 6 ^ 0xFC);
/*     */ 
/* 236 */       encodedData[encodedIndex] = lookUpBase64Alphabet[val1];
/*     */ 
/* 240 */       encodedData[(encodedIndex + 1)] = lookUpBase64Alphabet[(val2 | k << 4)];
/*     */ 
/* 242 */       encodedData[(encodedIndex + 2)] = lookUpBase64Alphabet[(l << 2 | val3)];
/*     */ 
/* 244 */       encodedData[(encodedIndex + 3)] = lookUpBase64Alphabet[(b3 & 0x3F)];
/*     */ 
/* 246 */       encodedIndex += 4;
/*     */ 
/* 249 */       if ((!(isChunked)) || 
/* 251 */         (encodedIndex != nextSeparatorIndex)) continue;
/* 252 */       System.arraycopy(CHUNK_SEPARATOR, 0, encodedData, encodedIndex, CHUNK_SEPARATOR.length);
/*     */ 
/* 258 */       ++chunksSoFar;
/* 259 */       nextSeparatorIndex = 76 * (chunksSoFar + 1) + chunksSoFar * CHUNK_SEPARATOR.length;
/*     */ 
/* 262 */       encodedIndex += CHUNK_SEPARATOR.length;
/*     */     }
/*     */ 
/* 268 */     dataIndex = i * 3;
/*     */ 
/* 270 */     if (fewerThan24bits == 8) {
/* 271 */       b1 = binaryData[dataIndex];
/* 272 */       k = (byte)(b1 & 0x3);
/*     */ 
/* 275 */       val1 = ((b1 & 0xFFFFFF80) == 0) ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 0xC0);
/*     */ 
/* 277 */       encodedData[encodedIndex] = lookUpBase64Alphabet[val1];
/* 278 */       encodedData[(encodedIndex + 1)] = lookUpBase64Alphabet[(k << 4)];
/* 279 */       encodedData[(encodedIndex + 2)] = 61;
/* 280 */       encodedData[(encodedIndex + 3)] = 61;
/* 281 */     } else if (fewerThan24bits == 16)
/*     */     {
/* 283 */       b1 = binaryData[dataIndex];
/* 284 */       b2 = binaryData[(dataIndex + 1)];
/* 285 */       l = (byte)(b2 & 0xF);
/* 286 */       k = (byte)(b1 & 0x3);
/*     */ 
/* 288 */       val1 = ((b1 & 0xFFFFFF80) == 0) ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 0xC0);
/*     */ 
/* 290 */       val2 = ((b2 & 0xFFFFFF80) == 0) ? (byte)(b2 >> 4) : (byte)(b2 >> 4 ^ 0xF0);
/*     */ 
/* 293 */       encodedData[encodedIndex] = lookUpBase64Alphabet[val1];
/* 294 */       encodedData[(encodedIndex + 1)] = lookUpBase64Alphabet[(val2 | k << 4)];
/*     */ 
/* 296 */       encodedData[(encodedIndex + 2)] = lookUpBase64Alphabet[(l << 2)];
/* 297 */       encodedData[(encodedIndex + 3)] = 61;
/*     */     }
/*     */ 
/* 300 */     if ((isChunked) && 
/* 302 */       (chunksSoFar < nbrChunks)) {
/* 303 */       System.arraycopy(CHUNK_SEPARATOR, 0, encodedData, encodedDataLength - CHUNK_SEPARATOR.length, CHUNK_SEPARATOR.length);
/*     */     }
/*     */ 
/* 312 */     return encodedData;
/*     */   }
/*     */ 
/*     */   public static byte[] decodeBase64(byte[] base64Data)
/*     */   {
/* 323 */     base64Data = discardNonBase64(base64Data);
/*     */ 
/* 326 */     if (base64Data.length == 0) {
/* 327 */       return new byte[0];
/*     */     }
/*     */ 
/* 330 */     int numberQuadruple = base64Data.length / 4;
/* 331 */     byte[] decodedData = null;
/* 332 */     byte b1 = 0; byte b2 = 0; byte b3 = 0; byte b4 = 0; byte marker0 = 0; byte marker1 = 0;
/*     */ 
/* 336 */     int encodedIndex = 0;
/* 337 */     int dataIndex = 0;
/*     */ 
/* 340 */     int lastData = base64Data.length;
/*     */ 
/* 342 */     while (base64Data[(lastData - 1)] == 61) {
/* 343 */       if (--lastData == 0) {
/* 344 */         return new byte[0];
/*     */       }
/*     */     }
/* 347 */     decodedData = new byte[lastData - numberQuadruple];
/*     */ 
/* 350 */     for (int i = 0; i < numberQuadruple; ++i) {
/* 351 */       dataIndex = i * 4;
/* 352 */       marker0 = base64Data[(dataIndex + 2)];
/* 353 */       marker1 = base64Data[(dataIndex + 3)];
/*     */ 
/* 355 */       b1 = base64Alphabet[base64Data[dataIndex]];
/* 356 */       b2 = base64Alphabet[base64Data[(dataIndex + 1)]];
/*     */ 
/* 358 */       if ((marker0 != 61) && (marker1 != 61))
/*     */       {
/* 360 */         b3 = base64Alphabet[marker0];
/* 361 */         b4 = base64Alphabet[marker1];
/*     */ 
/* 363 */         decodedData[encodedIndex] = (byte)(b1 << 2 | b2 >> 4);
/* 364 */         decodedData[(encodedIndex + 1)] = (byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF);
/*     */ 
/* 366 */         decodedData[(encodedIndex + 2)] = (byte)(b3 << 6 | b4);
/* 367 */       } else if (marker0 == 61)
/*     */       {
/* 369 */         decodedData[encodedIndex] = (byte)(b1 << 2 | b2 >> 4);
/* 370 */       } else if (marker1 == 61)
/*     */       {
/* 372 */         b3 = base64Alphabet[marker0];
/*     */ 
/* 374 */         decodedData[encodedIndex] = (byte)(b1 << 2 | b2 >> 4);
/* 375 */         decodedData[(encodedIndex + 1)] = (byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF);
/*     */       }
/*     */ 
/* 378 */       encodedIndex += 3;
/*     */     }
/* 380 */     return decodedData;
/*     */   }
/*     */ 
/*     */   static byte[] discardWhitespace(byte[] data)
/*     */   {
/* 391 */     byte[] groomedData = new byte[data.length];
/* 392 */     int bytesCopied = 0;
/*     */ 
/* 394 */     for (int i = 0; i < data.length; ++i) {
/* 395 */       switch (data[i])
/*     */       {
/*     */       case 9:
/*     */       case 10:
/*     */       case 13:
/*     */       case 32:
/* 400 */         break;
/*     */       default:
/* 402 */         groomedData[(bytesCopied++)] = data[i];
/*     */       }
/*     */     }
/*     */ 
/* 406 */     byte[] packedData = new byte[bytesCopied];
/*     */ 
/* 408 */     System.arraycopy(groomedData, 0, packedData, 0, bytesCopied);
/*     */ 
/* 410 */     return packedData;
/*     */   }
/*     */ 
/*     */   static byte[] discardNonBase64(byte[] data)
/*     */   {
/* 423 */     byte[] groomedData = new byte[data.length];
/* 424 */     int bytesCopied = 0;
/*     */ 
/* 426 */     for (int i = 0; i < data.length; ++i) {
/* 427 */       if (isBase64(data[i])) {
/* 428 */         groomedData[(bytesCopied++)] = data[i];
/*     */       }
/*     */     }
/*     */ 
/* 432 */     byte[] packedData = new byte[bytesCopied];
/*     */ 
/* 434 */     System.arraycopy(groomedData, 0, packedData, 0, bytesCopied);
/*     */ 
/* 436 */     return packedData;
/*     */   }
/*     */ 
/*     */   public static byte[] encode(byte[] pArray)
/*     */   {
/* 447 */     return encodeBase64(pArray, false);
/*     */   }
/*     */ 
/*     */   public static String encode(String str) throws UnsupportedEncodingException
/*     */   {
/* 452 */     String baseStr = new String(encode(str.getBytes("UTF-8")));
/* 453 */     String tempStr = Digest.digest(str).toUpperCase();
/* 454 */     String result = tempStr + baseStr;
/* 455 */     return new String(encode(result.getBytes("UTF-8")));
/*     */   }
/*     */ 
/*     */   public static String decode(String cryptoStr) throws UnsupportedEncodingException
/*     */   {
/* 460 */     if (cryptoStr.length() < 40)
/* 461 */       return "";
/*     */     try
/*     */     {
/* 464 */       String tempStr = new String(decode(cryptoStr.getBytes("UTF-8")));
/* 465 */       String result = tempStr.substring(40, tempStr.length());
/* 466 */       return new String(decode(result.getBytes("UTF-8")));
/*     */     }
/*     */     catch (ArrayIndexOutOfBoundsException ex) {
/*     */     }
/* 470 */     return "";
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  71 */     for (int i = 0; i < 255; ++i) {
/*  72 */       base64Alphabet[i] = -1;
/*     */     }
/*  74 */     for (i = 90; i >= 65; --i) {
/*  75 */       base64Alphabet[i] = (byte)(i - 65);
/*     */     }
/*  77 */     for (i = 122; i >= 97; --i) {
/*  78 */       base64Alphabet[i] = (byte)(i - 97 + 26);
/*     */     }
/*  80 */     for (i = 57; i >= 48; --i) {
/*  81 */       base64Alphabet[i] = (byte)(i - 48 + 52);
/*     */     }
/*     */ 
/*  84 */     base64Alphabet[43] = 62;
/*  85 */     base64Alphabet[47] = 63;
/*     */ 
/*  87 */     for (i = 0; i <= 25; ++i) {
/*  88 */       lookUpBase64Alphabet[i] = (byte)(65 + i);
/*     */     }
/*     */ 
/*  91 */     i = 26; for (int j = 0; i <= 51; ++j) {
/*  92 */       lookUpBase64Alphabet[i] = (byte)(97 + j);
/*     */ 
/*  91 */       ++i;
/*     */     }
/*     */ 
/*  95 */     i = 52; for (j = 0; i <= 61; ++j) {
/*  96 */       lookUpBase64Alphabet[i] = (byte)(48 + j);
/*     */ 
/*  95 */       ++i;
/*     */     }
/*     */ 
/*  99 */     lookUpBase64Alphabet[62] = 43;
/* 100 */     lookUpBase64Alphabet[63] = 47;
/*     */   }
/*     */ }