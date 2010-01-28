 package com.hisun.client;
 
 public class HiRquestParam
   implements RequestParam
 {
   private String name;
   private int index;
   private String type;
   private String value;
 
   public String getName()
   {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
 
   public int getIndex() {
     return this.index;
   }
 
   public void setIndex(int index) {
     this.index = index;
   }
 
   public String getType() {
     return this.type;
   }
 
   public void setType(String type) {
     this.type = type;
   }
 
   public Object getValue() {
     return this.value;
   }
 
   public void setValue(String value) {
     this.value = value;
   }
 
   public String toString() {
     StringBuilder sb = new StringBuilder("REQUEST PARAM [");
     sb.append("index=" + this.index);
     sb.append(";name=" + this.name);
     sb.append(";type=" + this.type);
     sb.append(";value=" + this.value);
     return " ]";
   }
 }