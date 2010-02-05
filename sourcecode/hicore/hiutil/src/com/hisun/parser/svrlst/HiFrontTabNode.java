 package com.hisun.parser.svrlst;
 
 import java.io.PrintStream;
 import java.util.ArrayList;
 import org.apache.commons.lang.StringUtils;
 
 public class HiFrontTabNode
 {
   private ArrayList childs;
 
   public HiFrontTabNode()
   {
     this.childs = new ArrayList(); }
 
   public ArrayList groupNodes() {
     return this.childs;
   }
 
   public void addGroup(Object o) {
     this.childs.add(o); }
 
   public void setParam() {
     System.out.println("setParam"); }
 
   public int size() {
     return this.childs.size(); }
 
   public void clear() {
     for (int i = 0; i < size(); ++i) {
       HiGroupNode group = getGroup(i);
       group.clear();
       this.childs.clear(); }
   }
 
   public HiGroupNode getGroup(int idx) {
     return ((HiGroupNode)this.childs.get(idx));
   }
 
   public HiGroupNode getGroup(String groupName) {
     for (int i = 0; i < size(); ++i) {
       if (StringUtils.equalsIgnoreCase(getGroup(i).getName(), groupName))
         return getGroup(i);
     }
     return null;
   }
 
   public HiServerNode getServer(String name) {
     HiServerNode server = null;
     for (int i = 0; i < size(); ++i) {
       server = getGroup(i).getServer(name);
       if (server != null)
         break;
     }
     return server;
   }
 
   public String toHTML()
   {
     StringBuffer buffer = new StringBuffer();
     for (int i = 0; i < size(); ++i) {
       buffer.append(getGroup(i).toHTML());
     }
     return buffer.toString();
   }
 
   public String toGroupHTML() {
     StringBuffer buffer = new StringBuffer();
     buffer.append("<table width = '100%' border='1' cellspacing='0' bordercolor='#003399'> \n");
 
     buffer.append("<tr bgcolor='#CCFFFF'> <td width='15%'> <a href='javascript:selectAll()'><font size='2'>全选</font></a> <a href='javascript:clearAll()'><font size='2'>清空</font></a> 组名 </td> <td width='35%'> 描述 </td> <td width='10%'> 组名 </td> <td width='35%'> 描述 </td> </tr>\n");
 
     for (int i = 0; i < size(); ++i) {
       HiGroupNode group = getGroup(i);
       buffer.append("<tr>");
       buffer.append("<td width='10%'><input type='checkbox' name='group' value='");
 
       buffer.append(group.getName());
       buffer.append("'/>");
       buffer.append("<a href=");
       buffer.append("'/mon/show_server.jsp?groupName=");
       buffer.append(group.getName());
       buffer.append("'>");
       buffer.append(group.getName());
       buffer.append("</a>");
 
       buffer.append("</td>");
       buffer.append("<td width='35%'> ");
       buffer.append(group.getDesc());
       buffer.append(" </td>");
       ++i;
       if (i == size()) {
         buffer.append("</tr>\n");
         break;
       }
       group = getGroup(i);
 
       buffer.append("<td width='10%'><input type='checkbox' name='group' value='");
 
       buffer.append(group.getName());
       buffer.append("'/>");
       buffer.append("<a href=");
       buffer.append("'/mon/show_server.jsp?groupName=");
       buffer.append(group.getName());
       buffer.append("'>");
       buffer.append(group.getName());
       buffer.append("</a>");
 
       buffer.append("</td>");
       buffer.append("<td width='35%'> ");
       buffer.append(group.getDesc());
       buffer.append(" </td>");
 
       buffer.append("</tr>\n");
     }
     buffer.append("</table>");
     return buffer.toString();
   }
 }