/*    */ package com.hisun.hiexpression.imp;
/*    */ 
/*    */ public class SimpleNode
/*    */   implements Node
/*    */ {
/*    */   protected Node parent;
/*    */   protected Node[] children;
/*    */   protected int id;
/*    */   protected ICSExpParser parser;
/*    */ 
/*    */   public SimpleNode(int i)
/*    */   {
/* 12 */     this.id = i;
/*    */   }
/*    */ 
/*    */   public SimpleNode(ICSExpParser p, int i) {
/* 16 */     this(i);
/* 17 */     this.parser = p; }
/*    */ 
/*    */   public void jjtOpen() {
/*    */   }
/*    */ 
/*    */   public void jjtClose() {
/*    */   }
/*    */ 
/*    */   public void jjtSetParent(Node n) {
/* 26 */     this.parent = n; } 
/*    */   public Node jjtGetParent() { return this.parent; }
/*    */ 
/*    */   public void jjtAddChild(Node n, int i) {
/* 30 */     if (this.children == null) {
/* 31 */       this.children = new Node[i + 1];
/* 32 */     } else if (i >= this.children.length) {
/* 33 */       Node[] c = new Node[i + 1];
/* 34 */       System.arraycopy(this.children, 0, c, 0, this.children.length);
/* 35 */       this.children = c;
/*    */     }
/* 37 */     this.children[i] = n;
/*    */   }
/*    */ 
/*    */   public Node jjtGetChild(int i) {
/* 41 */     return this.children[i];
/*    */   }
/*    */ 
/*    */   public int jjtGetNumChildren() {
/* 45 */     return ((this.children == null) ? 0 : this.children.length);
/*    */   }
/*    */ 
/*    */   public Object jjtAccept(ICSExpParserVisitor visitor, Object data)
/*    */     throws Exception
/*    */   {
/* 51 */     return visitor.visit(this, data);
/*    */   }
/*    */ 
/*    */   public Object childrenAccept(ICSExpParserVisitor visitor, Object data)
/*    */     throws Exception
/*    */   {
/* 57 */     if (this.children != null) {
/* 58 */       for (int i = 0; i < this.children.length; ++i) {
/* 59 */         this.children[i].jjtAccept(visitor, data);
/*    */       }
/*    */     }
/* 62 */     return data;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 71 */     return ICSExpParserTreeConstants.jjtNodeName[this.id]; } 
/*    */   public String toString(String prefix) { return prefix + toString();
/*    */   }
/*    */ 
/*    */   public void dump(String prefix)
/*    */   {
/* 79 */     if (this.children != null)
/* 80 */       for (int i = 0; i < this.children.length; ++i) {
/* 81 */         SimpleNode n = (SimpleNode)this.children[i];
/* 82 */         if (n != null)
/* 83 */           n.dump(prefix + " ");
/*    */       }
/*    */   }
/*    */ }