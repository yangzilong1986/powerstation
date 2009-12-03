/*    */ package com.hisun.parse8583;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ 
/*    */ public class HiByteArrayInputStream extends ByteArrayInputStream
/*    */ {
/*    */   public HiByteArrayInputStream(byte[] buf)
/*    */   {
/*  8 */     super(buf);
/*    */   }
/*    */ 
/*    */   public int getPos() {
/* 12 */     return this.pos;
/*    */   }
/*    */ }