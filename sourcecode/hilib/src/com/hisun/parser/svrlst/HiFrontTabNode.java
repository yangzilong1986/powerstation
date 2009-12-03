/*     */ package com.hisun.parser.svrlst;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiFrontTabNode
/*     */ {
/*     */   private ArrayList childs;
/*     */ 
/*     */   public HiFrontTabNode()
/*     */   {
/*   8 */     this.childs = new ArrayList(); }
/*     */ 
/*     */   public ArrayList groupNodes() {
/*  11 */     return this.childs;
/*     */   }
/*     */ 
/*     */   public void addGroup(Object o) {
/*  15 */     this.childs.add(o); }
/*     */ 
/*     */   public void setParam() {
/*  18 */     System.out.println("setParam"); }
/*     */ 
/*     */   public int size() {
/*  21 */     return this.childs.size(); }
/*     */ 
/*     */   public void clear() {
/*  24 */     for (int i = 0; i < size(); ++i) {
/*  25 */       HiGroupNode group = getGroup(i);
/*  26 */       group.clear();
/*  27 */       this.childs.clear(); }
/*     */   }
/*     */ 
/*     */   public HiGroupNode getGroup(int idx) {
/*  31 */     return ((HiGroupNode)this.childs.get(idx));
/*     */   }
/*     */ 
/*     */   public HiGroupNode getGroup(String groupName) {
/*  35 */     for (int i = 0; i < size(); ++i) {
/*  36 */       if (StringUtils.equalsIgnoreCase(getGroup(i).getName(), groupName))
/*  37 */         return getGroup(i);
/*     */     }
/*  39 */     return null;
/*     */   }
/*     */ 
/*     */   public HiServerNode getServer(String name) {
/*  43 */     HiServerNode server = null;
/*  44 */     for (int i = 0; i < size(); ++i) {
/*  45 */       server = getGroup(i).getServer(name);
/*  46 */       if (server != null)
/*     */         break;
/*     */     }
/*  49 */     return server;
/*     */   }
/*     */ 
/*     */   public String toHTML()
/*     */   {
/*  87 */     StringBuffer buffer = new StringBuffer();
/*  88 */     for (int i = 0; i < size(); ++i) {
/*  89 */       buffer.append(getGroup(i).toHTML());
/*     */     }
/*  91 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */   public String toGroupHTML() {
/*  95 */     StringBuffer buffer = new StringBuffer();
/*  96 */     buffer.append("<table width = '100%' border='1' cellspacing='0' bordercolor='#003399'> \n");
/*     */ 
/* 100 */     buffer.append("<tr bgcolor='#CCFFFF'> <td width='15%'> <a href='javascript:selectAll()'><font size='2'>全选</font></a> <a href='javascript:clearAll()'><font size='2'>清空</font></a> 组名 </td> <td width='35%'> 描述 </td> <td width='10%'> 组名 </td> <td width='35%'> 描述 </td> </tr>\n");
/*     */ 
/* 102 */     for (int i = 0; i < size(); ++i) {
/* 103 */       HiGroupNode group = getGroup(i);
/* 104 */       buffer.append("<tr>");
/* 105 */       buffer.append("<td width='10%'><input type='checkbox' name='group' value='");
/*     */ 
/* 107 */       buffer.append(group.getName());
/* 108 */       buffer.append("'/>");
/* 109 */       buffer.append("<a href=");
/* 110 */       buffer.append("'/mon/show_server.jsp?groupName=");
/* 111 */       buffer.append(group.getName());
/* 112 */       buffer.append("'>");
/* 113 */       buffer.append(group.getName());
/* 114 */       buffer.append("</a>");
/*     */ 
/* 118 */       buffer.append("</td>");
/* 119 */       buffer.append("<td width='35%'> ");
/* 120 */       buffer.append(group.getDesc());
/* 121 */       buffer.append(" </td>");
/* 122 */       ++i;
/* 123 */       if (i == size()) {
/* 124 */         buffer.append("</tr>\n");
/* 125 */         break;
/*     */       }
/* 127 */       group = getGroup(i);
/*     */ 
/* 129 */       buffer.append("<td width='10%'><input type='checkbox' name='group' value='");
/*     */ 
/* 131 */       buffer.append(group.getName());
/* 132 */       buffer.append("'/>");
/* 133 */       buffer.append("<a href=");
/* 134 */       buffer.append("'/mon/show_server.jsp?groupName=");
/* 135 */       buffer.append(group.getName());
/* 136 */       buffer.append("'>");
/* 137 */       buffer.append(group.getName());
/* 138 */       buffer.append("</a>");
/*     */ 
/* 142 */       buffer.append("</td>");
/* 143 */       buffer.append("<td width='35%'> ");
/* 144 */       buffer.append(group.getDesc());
/* 145 */       buffer.append(" </td>");
/*     */ 
/* 147 */       buffer.append("</tr>\n");
/*     */     }
/* 149 */     buffer.append("</table>");
/* 150 */     return buffer.toString();
/*     */   }
/*     */ }