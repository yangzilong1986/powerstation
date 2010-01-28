 package com.hisun.hiexpression.imp;
 
 import java.util.Stack;
 
 class JJTICSExpParserState
 {
   private Stack nodes;
   private Stack marks;
   private int sp;
   private int mk;
   private boolean node_created;
 
   JJTICSExpParserState()
   {
     this.nodes = new Stack();
     this.marks = new Stack();
     this.sp = 0;
     this.mk = 0;
   }
 
   boolean nodeCreated()
   {
     return this.node_created;
   }
 
   void reset()
   {
     this.nodes.removeAllElements();
     this.marks.removeAllElements();
     this.sp = 0;
     this.mk = 0;
   }
 
   Node rootNode()
   {
     return ((Node)this.nodes.elementAt(0));
   }
 
   void pushNode(Node n)
   {
     this.nodes.push(n);
     this.sp += 1;
   }
 
   Node popNode()
   {
     if (--this.sp < this.mk) {
       this.mk = ((Integer)this.marks.pop()).intValue();
     }
     return ((Node)this.nodes.pop());
   }
 
   Node peekNode()
   {
     return ((Node)this.nodes.peek());
   }
 
   int nodeArity()
   {
     return (this.sp - this.mk);
   }
 
   void clearNodeScope(Node n)
   {
     while (this.sp > this.mk) {
       popNode();
     }
     this.mk = ((Integer)this.marks.pop()).intValue();
   }
 
   void openNodeScope(Node n)
   {
     this.marks.push(new Integer(this.mk));
     this.mk = this.sp;
     n.jjtOpen();
   }
 
   void closeNodeScope(Node n, int num)
   {
     this.mk = ((Integer)this.marks.pop()).intValue();
     while (num-- > 0) {
       Node c = popNode();
       c.jjtSetParent(n);
       n.jjtAddChild(c, num);
     }
     n.jjtClose();
     pushNode(n);
     this.node_created = true;
   }
 
   void closeNodeScope(Node n, boolean condition)
   {
     if (condition) {
       int a = nodeArity();
       this.mk = ((Integer)this.marks.pop()).intValue();
       while (a-- > 0) {
         Node c = popNode();
         c.jjtSetParent(n);
         n.jjtAddChild(c, a);
       }
       n.jjtClose();
       pushNode(n);
       this.node_created = true;
     } else {
       this.mk = ((Integer)this.marks.pop()).intValue();
       this.node_created = false;
     }
   }
 }