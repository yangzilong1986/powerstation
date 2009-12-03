/*     */ package com.hisun.util;
/*     */ 
/*     */ import com.hisun.common.util.HiByteUtil;
/*     */ import com.hisun.exception.HiException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import org.apache.commons.lang.ArrayUtils;
/*     */ 
/*     */ public class HiByteBuffer
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private byte[] _buffer;
/*     */   private int _length;
/*     */   private int _capacity;
/*     */   private int _scale;
/*     */ 
/*     */   public HiByteBuffer()
/*     */   {
/*  32 */     this(20);
/*     */   }
/*     */ 
/*     */   public HiByteBuffer(int sz)
/*     */   {
/*  17 */     this._buffer = null;
/*     */ 
/*  19 */     this._length = 0;
/*     */ 
/*  21 */     this._capacity = 0;
/*     */ 
/*  23 */     this._scale = 64;
/*     */ 
/*  42 */     this._buffer = new byte[sz];
/*  43 */     this._capacity = sz;
/*  44 */     this._length = 0;
/*     */   }
/*     */ 
/*     */   public HiByteBuffer(int sz, int scale)
/*     */   {
/*  17 */     this._buffer = null;
/*     */ 
/*  19 */     this._length = 0;
/*     */ 
/*  21 */     this._capacity = 0;
/*     */ 
/*  23 */     this._scale = 64;
/*     */ 
/*  56 */     this._buffer = new byte[sz];
/*  57 */     this._capacity = sz;
/*  58 */     this._length = 0;
/*  59 */     this._scale = scale;
/*     */   }
/*     */ 
/*     */   public HiByteBuffer(byte[] data)
/*     */   {
/*  17 */     this._buffer = null;
/*     */ 
/*  19 */     this._length = 0;
/*     */ 
/*  21 */     this._capacity = 0;
/*     */ 
/*  23 */     this._scale = 64;
/*     */ 
/*  68 */     append(data);
/*     */   }
/*     */ 
/*     */   public HiByteBuffer(byte[] data, boolean isRef)
/*     */   {
/*  17 */     this._buffer = null;
/*     */ 
/*  19 */     this._length = 0;
/*     */ 
/*  21 */     this._capacity = 0;
/*     */ 
/*  23 */     this._scale = 64;
/*     */ 
/*  79 */     if (isRef) {
/*  80 */       this._buffer = data;
/*  81 */       this._length = data.length;
/*     */     } else {
/*  83 */       append(data);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void append(byte[] data, int offset, int length)
/*     */   {
/* 100 */     if (length == 0)
/* 101 */       return;
/* 102 */     if (this._capacity <= length) {
/* 103 */       byte[] buffer1 = new byte[this._length + getScaleSize(length)];
/* 104 */       if (this._buffer != null)
/* 105 */         System.arraycopy(this._buffer, 0, buffer1, 0, this._length);
/* 106 */       this._buffer = buffer1;
/*     */     }
/*     */ 
/* 110 */     System.arraycopy(data, offset, this._buffer, this._length, length);
/* 111 */     this._length += length;
/* 112 */     this._capacity = (this._buffer.length - this._length);
/*     */   }
/*     */ 
/*     */   public void append(byte[] data)
/*     */   {
/* 121 */     if (data == null) {
/* 122 */       data = new byte[0];
/*     */     }
/* 124 */     append(data, 0, data.length);
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 132 */     this._length = 0;
/* 133 */     this._capacity = this._buffer.length;
/*     */   }
/*     */ 
/*     */   public void append(String data)
/*     */   {
/* 142 */     append(data.getBytes());
/*     */   }
/*     */ 
/*     */   public void append(byte b)
/*     */   {
/* 151 */     append(new byte[] { b });
/*     */   }
/*     */ 
/*     */   public void append(int b)
/*     */   {
/* 160 */     append((byte)b);
/*     */   }
/*     */ 
/*     */   public void replace(int idx, String data)
/*     */   {
/* 169 */     replace(idx, data.getBytes());
/*     */   }
/*     */ 
/*     */   public void replace(int idx, byte b)
/*     */   {
/* 178 */     replace(idx, new byte[] { b });
/*     */   }
/*     */ 
/*     */   public void replace(int idx, int b)
/*     */   {
/* 187 */     replace(idx, (byte)b);
/*     */   }
/*     */ 
/*     */   public void replace(int idx, byte[] bs)
/*     */   {
/* 196 */     replace(idx, bs, 0, bs.length);
/*     */   }
/*     */ 
/*     */   public void replace(int idx, byte[] bs, int offset, int len) {
/* 200 */     if (len == 0) {
/* 201 */       return;
/*     */     }
/*     */ 
/* 204 */     for (int i = 0; (i < len) && (idx + i < this._length); ++i) {
/* 205 */       this._buffer[(idx + i)] = bs[(i + offset)];
/*     */     }
/* 207 */     append(bs, i + offset, len - i);
/*     */   }
/*     */ 
/*     */   public void insert(int idx, String data)
/*     */   {
/* 216 */     insert(idx, data.getBytes());
/*     */   }
/*     */ 
/*     */   public void insert(int idx, byte b)
/*     */   {
/* 225 */     insert(idx, new byte[] { b });
/*     */   }
/*     */ 
/*     */   public void insert(int idx, int b)
/*     */   {
/* 234 */     insert(idx, (byte)b);
/*     */   }
/*     */ 
/*     */   public void insert(int idx, byte[] bs)
/*     */   {
/* 243 */     insert(idx, bs, 0, bs.length);
/*     */   }
/*     */ 
/*     */   public void insert(int idx, byte[] bs, int offset, int length)
/*     */   {
/* 252 */     if (length == 0) {
/* 253 */       return;
/*     */     }
/* 255 */     int len = ((idx > this._length) ? idx - this._length : 0) + length;
/* 256 */     if (this._capacity <= len) {
/* 257 */       byte[] buffer1 = new byte[this._length + getScaleSize(len)];
/* 258 */       if (this._buffer != null) {
/* 259 */         if (idx <= this._length) {
/* 260 */           System.arraycopy(this._buffer, 0, buffer1, 0, idx);
/* 261 */           System.arraycopy(this._buffer, idx, buffer1, idx + length, this._length - idx);
/*     */         }
/*     */         else {
/* 264 */           System.arraycopy(this._buffer, 0, buffer1, 0, this._length);
/*     */         }
/*     */       }
/* 267 */       this._buffer = buffer1;
/*     */     } else {
/* 269 */       System.arraycopy(this._buffer, idx, this._buffer, idx + length, this._length - idx);
/*     */     }
/*     */ 
/* 272 */     System.arraycopy(bs, offset, this._buffer, idx, length);
/* 273 */     this._length += len;
/* 274 */     this._capacity = (this._buffer.length - len);
/*     */   }
/*     */ 
/*     */   public void writeInt(int i)
/*     */   {
/* 283 */     append(HiByteUtil.intToByteArray(i));
/*     */   }
/*     */ 
/*     */   public void writeShort(short s)
/*     */   {
/* 292 */     append(HiByteUtil.shortToByteArray(s));
/*     */   }
/*     */ 
/*     */   public void writeLong(long l)
/*     */   {
/* 301 */     append(HiByteUtil.longToByteArray(l));
/*     */   }
/*     */ 
/*     */   public byte[] getBytes()
/*     */   {
/* 310 */     if (this._length == 0) {
/* 311 */       return new byte[0];
/*     */     }
/* 313 */     byte[] buffer = new byte[this._length];
/* 314 */     System.arraycopy(this._buffer, 0, buffer, 0, this._length);
/* 315 */     return buffer;
/*     */   }
/*     */ 
/*     */   public int length()
/*     */   {
/* 324 */     return this._length;
/*     */   }
/*     */ 
/*     */   public String substr(int offset, int length)
/*     */   {
/* 337 */     return new String(subbyte(offset, length));
/*     */   }
/*     */ 
/*     */   public byte[] subbyte(int offset, int length)
/*     */   {
/* 350 */     if (offset + length > this._length) {
/* 351 */       throw new IllegalArgumentException(offset + "+" + length + ">" + this._length);
/*     */     }
/*     */ 
/* 354 */     byte[] buffer = new byte[length];
/* 355 */     System.arraycopy(this._buffer, offset, buffer, 0, length);
/* 356 */     return buffer;
/*     */   }
/*     */ 
/*     */   public byte charAt(int offset)
/*     */   {
/* 366 */     return this._buffer[offset];
/*     */   }
/*     */ 
/*     */   public int indexOf(int b, int offset)
/*     */   {
/* 379 */     if (offset > this._length) {
/* 380 */       throw new IllegalArgumentException(offset + ">" + this._length);
/*     */     }
/* 382 */     for (int i = offset; i < this._length; ++i) {
/* 383 */       if (this._buffer[i] == b)
/* 384 */         return i;
/*     */     }
/* 386 */     return -1;
/*     */   }
/*     */ 
/*     */   public int indexOf(byte[] bb, int offset)
/*     */   {
/* 399 */     if ((bb == null) || (bb.length == 0) || (offset < 0)) {
/* 400 */       return -1;
/*     */     }
/*     */     while (true)
/*     */     {
/* 404 */       offset = ArrayUtils.indexOf(this._buffer, bb[0], offset);
/*     */ 
/* 406 */       if (offset == -1) {
/* 407 */         return -1;
/*     */       }
/*     */ 
/* 410 */       if (bb.length > this._length - offset) {
/* 411 */         return -1;
/*     */       }
/*     */ 
/* 414 */       int i = 0;
/* 415 */       for (i = 0; i < bb.length; ++i) {
/* 416 */         if (this._buffer[(offset + i)] != bb[i]) {
/*     */           break;
/*     */         }
/*     */       }
/* 420 */       if (i == bb.length) {
/* 421 */         return offset;
/*     */       }
/*     */ 
/* 424 */       ++offset;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void repeat(byte b, int num)
/*     */   {
/* 435 */     if (num <= 0) {
/* 436 */       return;
/*     */     }
/* 438 */     byte[] nb = new byte[num];
/* 439 */     for (int i = 0; i < num; ++i) {
/* 440 */       nb[i] = b;
/*     */     }
/* 442 */     append(nb, 0, num);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 449 */     if (this._buffer == null)
/* 450 */       return null;
/* 451 */     return new String(this._buffer, 0, this._length);
/*     */   }
/*     */ 
/*     */   public String toString(String encoding)
/*     */     throws HiException
/*     */   {
/*     */     try
/*     */     {
/* 463 */       if (this._buffer == null)
/* 464 */         return null;
/* 465 */       return new String(this._buffer, 0, this._length, encoding);
/*     */     } catch (UnsupportedEncodingException e) {
/* 467 */       throw new HiException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public byte[] remove(int offset, int length) {
/* 472 */     byte[] tmps = subbyte(offset, length);
/*     */ 
/* 474 */     System.arraycopy(this._buffer, offset + length, this._buffer, offset, this._buffer.length - offset - length);
/*     */ 
/* 476 */     return tmps;
/*     */   }
/*     */ 
/*     */   private int getScaleSize(int len) {
/* 480 */     if (len / this._scale == 0) {
/* 481 */       return this._scale;
/*     */     }
/* 483 */     return ((len / this._scale + 1) * this._scale);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 487 */     HiByteBuffer buf = new HiByteBuffer();
/* 488 */     buf.append("333");
/* 489 */     buf.append("444");
/* 490 */     buf.insert(2, "555");
/* 491 */     buf.replace(2, "88888888888".getBytes(), 3, 5);
/* 492 */     System.out.println(buf.toString());
/*     */   }
/*     */ }