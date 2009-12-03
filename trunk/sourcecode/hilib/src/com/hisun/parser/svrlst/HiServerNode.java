/*     */ package com.hisun.parser.svrlst;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.register.HiRegisterService;
/*     */ import com.hisun.register.HiServiceObject;
/*     */ import com.hisun.util.HiServiceLocator;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class HiServerNode
/*     */ {
/*     */   private HiGroupNode groupNode;
/*     */   private String appName;
/*     */   private String name;
/*     */   private String min;
/*     */   private String max;
/*     */   private String config_file;
/*     */   private String desc;
/*     */   private String auto_restart;
/*     */   private String operTime;
/*     */   private String msg;
/*     */   private String type;
/*     */   private int status;
/*     */ 
/*     */   public HiServerNode()
/*     */   {
/*  31 */     this.operTime = "";
/*     */ 
/*  41 */     this.status = 0; }
/*     */ 
/*     */   public int getStatus() {
/*  44 */     HiServiceObject service = null;
/*     */ 
/*  46 */     this.status = 0;
/*  47 */     if (StringUtils.equalsIgnoreCase(this.type, "jms")) {
/*  48 */       return (this.status = 1);
/*     */     }
/*     */     try
/*     */     {
/*  52 */       HiServiceLocator locator = HiServiceLocator.getInstance();
/*  53 */       locator.lookup("ibs/ejb/" + this.name);
/*     */     }
/*     */     catch (HiException e1) {
/*  56 */       this.status = 4;
/*     */     }
/*     */ 
/*  59 */     if (this.status != 4)
/*     */     {
/*     */       try {
/*  62 */         service = HiRegisterService.getService(this.name);
/*     */       }
/*     */       catch (HiException e) {
/*  65 */         this.status = 0;
/*     */       }
/*     */ 
/*  68 */       if (service != null) {
/*  69 */         this.status = NumberUtils.toInt(service.getRunning());
/*     */       }
/*     */     }
/*     */ 
/*  73 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStopStatus() {
/*  77 */     setStatus(0);
/*     */   }
/*     */ 
/*     */   public void setErrorStatus() {
/*  81 */     setStatus(0);
/*     */   }
/*     */ 
/*     */   public void setStartStatus() {
/*  85 */     setStatus(1);
/*     */   }
/*     */ 
/*     */   public void setPauseStatus() {
/*  89 */     setStatus(2);
/*     */   }
/*     */ 
/*     */   public void setMsg(String msg) {
/*  93 */     this.msg = msg;
/*     */   }
/*     */ 
/*     */   public String getMsg() {
/*  97 */     return this.msg;
/*     */   }
/*     */ 
/*     */   public void setStatus(int status)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       HiServiceLocator locator = HiServiceLocator.getInstance();
/* 107 */       HiServiceObject obj = HiRegisterService.getService(this.name);
/* 108 */       obj.setRunning(String.valueOf(status));
/*     */ 
/* 110 */       locator.rebind(HiRegisterService.getJndiName(this.name), obj);
/*     */     } catch (HiException e) {
/* 112 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getStatusMsg()
/*     */   {
/* 118 */     if (this.status == 0)
/* 119 */       return "停止";
/* 120 */     if (this.status == 1)
/* 121 */       return "启动";
/* 122 */     if (this.status == 2)
/* 123 */       return "暂停";
/* 124 */     if (this.status == 3)
/* 125 */       return "失败";
/* 126 */     if (this.status == 4) {
/* 127 */       return "未部署";
/*     */     }
/* 129 */     return "停止";
/*     */   }
/*     */ 
/*     */   public String getAuto_restart()
/*     */   {
/* 134 */     return this.auto_restart;
/*     */   }
/*     */ 
/*     */   public void setAuto_restart(String auto_restart) {
/* 138 */     this.auto_restart = auto_restart;
/*     */   }
/*     */ 
/*     */   public String getConfig_file() {
/* 142 */     return this.config_file;
/*     */   }
/*     */ 
/*     */   public void setConfig_file(String config_file) {
/* 146 */     this.config_file = config_file;
/*     */   }
/*     */ 
/*     */   public String getMax() {
/* 150 */     return this.max;
/*     */   }
/*     */ 
/*     */   public void setMax(String max) {
/* 154 */     this.max = max;
/*     */   }
/*     */ 
/*     */   public String getMin() {
/* 158 */     return this.min;
/*     */   }
/*     */ 
/*     */   public void setMin(String min) {
/* 162 */     this.min = min;
/*     */   }
/*     */ 
/*     */   public String getName() {
/* 166 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setType(String type) {
/* 170 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public String getType() {
/* 174 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/* 178 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 182 */     StringBuffer result = new StringBuffer();
/* 183 */     result.append(this.name);
/* 184 */     result.append(":");
/* 185 */     result.append(this.min);
/* 186 */     result.append(":");
/* 187 */     result.append(this.max);
/* 188 */     result.append(":");
/* 189 */     result.append(this.config_file);
/* 190 */     result.append(":");
/* 191 */     result.append(this.auto_restart);
/* 192 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public String getDesc() {
/* 196 */     if (this.desc == null)
/* 197 */       return this.name;
/* 198 */     return this.desc;
/*     */   }
/*     */ 
/*     */   public void setDesc(String desc) {
/* 202 */     this.desc = desc;
/*     */   }
/*     */ 
/*     */   public String toHTML()
/*     */   {
/* 209 */     StringBuffer buffer = new StringBuffer();
/* 210 */     buffer.append("<tr>");
/* 211 */     buffer.append("<td width='15%'>");
/* 212 */     buffer.append("<input type='checkbox' name='server' value='" + this.name + "'/>");
/*     */ 
/* 214 */     buffer.append("<a href='/mon/show_server_config.jsp?serverName=");
/* 215 */     buffer.append(this.name);
/* 216 */     buffer.append("'/>");
/* 217 */     buffer.append(this.name + "</td>");
/* 218 */     getStatus();
/*     */ 
/* 220 */     if (this.status == 0)
/* 221 */       buffer.append("<td bgcolor = 'yellow' width='10%'> ");
/* 222 */     else if (this.status == 1)
/* 223 */       buffer.append("<td  bgcolor = 'green' width='10%'> ");
/* 224 */     else if (this.status == 3)
/* 225 */       buffer.append("<td  bgcolor = 'red' width='10%'> ");
/* 226 */     else if (this.status == 4)
/* 227 */       buffer.append("<td  bgcolor = 'white' width='10%'> ");
/*     */     else {
/* 229 */       buffer.append("<td bgcolor = 'white' width='10%'> ");
/*     */     }
/*     */ 
/* 232 */     buffer.append(getStatusMsg());
/*     */ 
/* 234 */     buffer.append("</td>");
/* 235 */     buffer.append("<td width='25%'> " + getOperTime() + "</td>");
/* 236 */     buffer.append("<td width='40%'> " + getDesc() + " </td> </tr>\n");
/* 237 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */   public String getOperTime() {
/* 241 */     return this.operTime;
/*     */   }
/*     */ 
/*     */   public void setOperTime(String startTime) {
/* 245 */     this.operTime = startTime;
/*     */   }
/*     */ 
/*     */   public String getGrpNam() {
/* 249 */     if (this.groupNode != null) {
/* 250 */       return this.groupNode.getName();
/*     */     }
/* 252 */     return "";
/*     */   }
/*     */ 
/*     */   public HiGroupNode getGroupNode()
/*     */   {
/* 258 */     return this.groupNode;
/*     */   }
/*     */ 
/*     */   public void setGroupNode(HiGroupNode groupNode) {
/* 262 */     this.groupNode = groupNode;
/*     */   }
/*     */ 
/*     */   public String getAppName()
/*     */   {
/* 269 */     return this.appName;
/*     */   }
/*     */ 
/*     */   public void setAppName(String appName)
/*     */   {
/* 276 */     this.appName = appName;
/*     */   }
/*     */ }