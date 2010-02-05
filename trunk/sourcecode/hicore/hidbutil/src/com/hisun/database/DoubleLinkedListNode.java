 package com.hisun.database;
 
 import java.io.Serializable;
 
 public class DoubleLinkedListNode
   implements Serializable
 {
   private static final long serialVersionUID = -1114934407695836097L;
   private Object payload;
   public DoubleLinkedListNode prev;
   public DoubleLinkedListNode next;
 
   public DoubleLinkedListNode(Object payloadP)
   {
     this.payload = payloadP;
   }
 
   public Object getPayload()
   {
     return this.payload;
   }
 }