/*     */ package com.hisun.sw;
/*     */ 
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ 
/*     */ public class HiByteChunk
/*     */ {
/*  11 */   private byte[] _buf = null;
/*  12 */   private int _mark = -1;
/*  13 */   private int _pos = 0;
/*  14 */   private int _limit = 0;
/*     */ 
/*     */   public HiByteChunk(HiByteBuffer buf) {
/*  17 */     this._buf = buf.getBytes();
/*  18 */     this._limit = this._buf.length;
/*     */   }
/*     */ 
/*     */   public HiByteChunk(byte[] buf)
/*     */   {
/*  26 */     this._buf = buf;
/*     */   }
/*     */ 
/*     */   public HiByteChunk(int sz) {
/*  30 */     this._buf = new byte[sz];
/*     */   }
/*     */ 
/*     */   public void inc(int idx, int value) {
/*  34 */     set(idx, get(idx) + value);
/*     */   }
/*     */ 
/*     */   public void dec(int idx, int value) {
/*  38 */     set(idx, get(idx) - value);
/*     */   }
/*     */ 
/*     */   public int next() {
/*  42 */     return get(this._pos++);
/*     */   }
/*     */ 
/*     */   public int get() {
/*  46 */     return get(this._pos);
/*     */   }
/*     */ 
/*     */   public int getNext() {
/*  50 */     return get(this._pos + 1);
/*     */   }
/*     */ 
/*     */   public int get(int idx) {
/*  54 */     return (this._buf[idx] & 0xFF);
/*     */   }
/*     */ 
/*     */   public boolean hasNext() {
/*  58 */     return (this._pos < this._limit);
/*     */   }
/*     */ 
/*     */   public void append(short value) {
/*  62 */     set(this._pos++, value);
/*     */   }
/*     */ 
/*     */   public void append(int value) {
/*  66 */     set(this._pos++, value);
/*     */   }
/*     */ 
/*     */   public void append(byte value) {
/*  70 */     set(this._pos++, value);
/*     */   }
/*     */ 
/*     */   public void set(int idx, short value) {
/*  74 */     this._buf[idx] = (byte)value;
/*     */   }
/*     */ 
/*     */   public void set(int idx, int value) {
/*  78 */     this._buf[idx] = (byte)value;
/*     */   }
/*     */ 
/*     */   public void set(int idx, byte value) {
/*  82 */     this._buf[idx] = value;
/*     */   }
/*     */ 
/*     */   public int size() {
/*  86 */     return this._buf.length;
/*     */   }
/*     */ 
/*     */   public void mark() {
/*  90 */     mark(this._pos);
/*     */   }
/*     */ 
/*     */   public void mark(int pos) {
/*  94 */     this._mark = pos;
/*     */   }
/*     */ 
/*     */   public void reset() {
/*  98 */     if (this._mark == -1)
/*  99 */       return;
/* 100 */     this._pos = this._mark;
/* 101 */     this._mark = -1;
/*     */   }
/*     */ 
/*     */   public int pos() {
/* 105 */     return this._pos;
/*     */   }
/*     */ 
/*     */   public void clearMark() {
/* 109 */     this._mark = -1;
/*     */   }
/*     */ 
/*     */   public void setlimit(int limit) {
/* 113 */     this._limit = limit;
/*     */   }
/*     */ 
/*     */   public void clear() {
/* 117 */     this._limit = this._buf.length;
/* 118 */     this._mark = -1;
/*     */   }
/*     */ }