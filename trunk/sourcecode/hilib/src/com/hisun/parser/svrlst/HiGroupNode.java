/*     */ package com.hisun.parser.svrlst;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiGroupNode
/*     */ {
/*     */   private ArrayList childs;
/*     */   private String name;
/*     */   private String desc;
/*     */   private String status;
/*     */ 
/*     */   public HiGroupNode()
/*     */   {
/*   8 */     this.childs = new ArrayList();
/*     */ 
/*  13 */     this.status = "0"; }
/*     */ 
/*     */   public void setStatus(String status) {
/*  16 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public String getStatus() {
/*  20 */     return this.status; }
/*     */ 
/*     */   public void setStopStatus() {
/*  23 */     this.status = "0";
/*     */   }
/*     */ 
/*     */   public void setErrorStatus() {
/*  27 */     this.status = "1";
/*     */   }
/*     */ 
/*     */   public void setStartStatus() {
/*  31 */     this.status = "2";
/*     */   }
/*     */ 
/*     */   public void setPauseStatus() {
/*  35 */     this.status = "3";
/*     */   }
/*     */ 
/*     */   public String getStatusMsg() {
/*  39 */     if (this.status.equals("0"))
/*  40 */       return "关闭";
/*  41 */     if (this.status.equals("1"))
/*  42 */       return "失败";
/*  43 */     if (this.status.equals("2"))
/*  44 */       return "启动";
/*  45 */     if (this.status.equals("3")) {
/*  46 */       return "暂停";
/*     */     }
/*  48 */     return "关闭";
/*     */   }
/*     */ 
/*     */   public void addServer(Object o)
/*     */   {
/*  53 */     this.childs.add(o);
/*  54 */     if (o instanceof HiServerNode) {
/*  55 */       HiServerNode node = (HiServerNode)o;
/*  56 */       node.setGroupNode(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int size() {
/*  61 */     return this.childs.size();
/*     */   }
/*     */ 
/*     */   public void clear() {
/*  65 */     this.childs.clear();
/*     */   }
/*     */ 
/*     */   public HiServerNode getServer(int idx) {
/*  69 */     return ((HiServerNode)this.childs.get(idx));
/*     */   }
/*     */ 
/*     */   public HiServerNode getServer(String name) {
/*  73 */     for (int i = 0; i < size(); ++i) {
/*  74 */       if (StringUtils.equalsIgnoreCase(getServer(i).getName(), name))
/*  75 */         return getServer(i);
/*     */     }
/*  77 */     return null;
/*     */   }
/*     */ 
/*     */   public String getDesc() {
/*  81 */     if (this.desc == null)
/*  82 */       return this.name;
/*  83 */     return this.desc;
/*     */   }
/*     */ 
/*     */   public void setDesc(String desc) {
/*  87 */     this.desc = desc;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  91 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/*  95 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String toString() {
/*  99 */     return this.name + ":" + this.desc;
/*     */   }
/*     */ 
/*     */   public String toHTML() {
/* 103 */     StringBuffer buffer = new StringBuffer();
/* 104 */     buffer.append("<table width = '80%' border='1' cellspacing='0' bordercolor='#003399'> \n");
/*     */ 
/* 106 */     buffer.append("<tr bgcolor='#CCFFFF'> <td> <a href='javascript:selectAll()'><font size='2'>全选</font> </a> <a href='javascript:clearAll()'><font size='2'>清空</font> </a>服务名 </td>  <td> 状态 </td>  <td> 操作时间 </td> <td> 描述 </td> </tr>\n");
/*     */ 
/* 108 */     for (int i = 0; i < size(); ++i) {
/* 109 */       buffer.append(getServer(i).toHTML());
/*     */     }
/* 111 */     buffer.append("</table> \n");
/* 112 */     return buffer.toString();
/*     */   }
/*     */ }