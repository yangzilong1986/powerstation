/*     */ package com.hisun.hiexpression.imp;
/*     */ 
/*     */ import java.util.Stack;
/*     */ 
/*     */ class JJTICSExpParserState
/*     */ {
/*     */   private Stack nodes;
/*     */   private Stack marks;
/*     */   private int sp;
/*     */   private int mk;
/*     */   private boolean node_created;
/*     */ 
/*     */   JJTICSExpParserState()
/*     */   {
/*  14 */     this.nodes = new Stack();
/*  15 */     this.marks = new Stack();
/*  16 */     this.sp = 0;
/*  17 */     this.mk = 0;
/*     */   }
/*     */ 
/*     */   boolean nodeCreated()
/*     */   {
/*  24 */     return this.node_created;
/*     */   }
/*     */ 
/*     */   void reset()
/*     */   {
/*  30 */     this.nodes.removeAllElements();
/*  31 */     this.marks.removeAllElements();
/*  32 */     this.sp = 0;
/*  33 */     this.mk = 0;
/*     */   }
/*     */ 
/*     */   Node rootNode()
/*     */   {
/*  39 */     return ((Node)this.nodes.elementAt(0));
/*     */   }
/*     */ 
/*     */   void pushNode(Node n)
/*     */   {
/*  44 */     this.nodes.push(n);
/*  45 */     this.sp += 1;
/*     */   }
/*     */ 
/*     */   Node popNode()
/*     */   {
/*  51 */     if (--this.sp < this.mk) {
/*  52 */       this.mk = ((Integer)this.marks.pop()).intValue();
/*     */     }
/*  54 */     return ((Node)this.nodes.pop());
/*     */   }
/*     */ 
/*     */   Node peekNode()
/*     */   {
/*  59 */     return ((Node)this.nodes.peek());
/*     */   }
/*     */ 
/*     */   int nodeArity()
/*     */   {
/*  65 */     return (this.sp - this.mk);
/*     */   }
/*     */ 
/*     */   void clearNodeScope(Node n)
/*     */   {
/*  70 */     while (this.sp > this.mk) {
/*  71 */       popNode();
/*     */     }
/*  73 */     this.mk = ((Integer)this.marks.pop()).intValue();
/*     */   }
/*     */ 
/*     */   void openNodeScope(Node n)
/*     */   {
/*  78 */     this.marks.push(new Integer(this.mk));
/*  79 */     this.mk = this.sp;
/*  80 */     n.jjtOpen();
/*     */   }
/*     */ 
/*     */   void closeNodeScope(Node n, int num)
/*     */   {
/*  89 */     this.mk = ((Integer)this.marks.pop()).intValue();
/*  90 */     while (num-- > 0) {
/*  91 */       Node c = popNode();
/*  92 */       c.jjtSetParent(n);
/*  93 */       n.jjtAddChild(c, num);
/*     */     }
/*  95 */     n.jjtClose();
/*  96 */     pushNode(n);
/*  97 */     this.node_created = true;
/*     */   }
/*     */ 
/*     */   void closeNodeScope(Node n, boolean condition)
/*     */   {
/* 107 */     if (condition) {
/* 108 */       int a = nodeArity();
/* 109 */       this.mk = ((Integer)this.marks.pop()).intValue();
/* 110 */       while (a-- > 0) {
/* 111 */         Node c = popNode();
/* 112 */         c.jjtSetParent(n);
/* 113 */         n.jjtAddChild(c, a);
/*     */       }
/* 115 */       n.jjtClose();
/* 116 */       pushNode(n);
/* 117 */       this.node_created = true;
/*     */     } else {
/* 119 */       this.mk = ((Integer)this.marks.pop()).intValue();
/* 120 */       this.node_created = false;
/*     */     }
/*     */   }
/*     */ }