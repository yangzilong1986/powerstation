 package com.hisun.sw;
 
 import com.hisun.util.HiByteBuffer;
 
 public class HiByteChunk
 {
   private byte[] _buf = null;
   private int _mark = -1;
   private int _pos = 0;
   private int _limit = 0;
 
   public HiByteChunk(HiByteBuffer buf) {
     this._buf = buf.getBytes();
     this._limit = this._buf.length;
   }
 
   public HiByteChunk(byte[] buf)
   {
     this._buf = buf;
   }
 
   public HiByteChunk(int sz) {
     this._buf = new byte[sz];
   }
 
   public void inc(int idx, int value) {
     set(idx, get(idx) + value);
   }
 
   public void dec(int idx, int value) {
     set(idx, get(idx) - value);
   }
 
   public int next() {
     return get(this._pos++);
   }
 
   public int get() {
     return get(this._pos);
   }
 
   public int getNext() {
     return get(this._pos + 1);
   }
 
   public int get(int idx) {
     return (this._buf[idx] & 0xFF);
   }
 
   public boolean hasNext() {
     return (this._pos < this._limit);
   }
 
   public void append(short value) {
     set(this._pos++, value);
   }
 
   public void append(int value) {
     set(this._pos++, value);
   }
 
   public void append(byte value) {
     set(this._pos++, value);
   }
 
   public void set(int idx, short value) {
     this._buf[idx] = (byte)value;
   }
 
   public void set(int idx, int value) {
     this._buf[idx] = (byte)value;
   }
 
   public void set(int idx, byte value) {
     this._buf[idx] = value;
   }
 
   public int size() {
     return this._buf.length;
   }
 
   public void mark() {
     mark(this._pos);
   }
 
   public void mark(int pos) {
     this._mark = pos;
   }
 
   public void reset() {
     if (this._mark == -1)
       return;
     this._pos = this._mark;
     this._mark = -1;
   }
 
   public int pos() {
     return this._pos;
   }
 
   public void clearMark() {
     this._mark = -1;
   }
 
   public void setlimit(int limit) {
     this._limit = limit;
   }
 
   public void clear() {
     this._limit = this._buf.length;
     this._mark = -1;
   }
 }