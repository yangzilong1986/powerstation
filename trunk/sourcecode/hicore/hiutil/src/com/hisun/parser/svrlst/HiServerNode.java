 package com.hisun.parser.svrlst;
 
 import com.hisun.exception.HiException;
 import com.hisun.register.HiRegisterService;
 import com.hisun.register.HiServiceObject;
 import com.hisun.util.HiServiceLocator;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 
 public class HiServerNode
 {
   private HiGroupNode groupNode;
   private String appName;
   private String name;
   private String min;
   private String max;
   private String config_file;
   private String desc;
   private String auto_restart;
   private String operTime;
   private String msg;
   private String type;
   private int status;
 
   public HiServerNode()
   {
     this.operTime = "";
 
     this.status = 0; }
 
   public int getStatus() {
     HiServiceObject service = null;
 
     this.status = 0;
     if (StringUtils.equalsIgnoreCase(this.type, "jms")) {
       return (this.status = 1);
     }
     try
     {
       HiServiceLocator locator = HiServiceLocator.getInstance();
       locator.lookup("ibs/ejb/" + this.name);
     }
     catch (HiException e1) {
       this.status = 4;
     }
 
     if (this.status != 4)
     {
       try {
         service = HiRegisterService.getService(this.name);
       }
       catch (HiException e) {
         this.status = 0;
       }
 
       if (service != null) {
         this.status = NumberUtils.toInt(service.getRunning());
       }
     }
 
     return this.status;
   }
 
   public void setStopStatus() {
     setStatus(0);
   }
 
   public void setErrorStatus() {
     setStatus(0);
   }
 
   public void setStartStatus() {
     setStatus(1);
   }
 
   public void setPauseStatus() {
     setStatus(2);
   }
 
   public void setMsg(String msg) {
     this.msg = msg;
   }
 
   public String getMsg() {
     return this.msg;
   }
 
   public void setStatus(int status)
   {
     try
     {
       HiServiceLocator locator = HiServiceLocator.getInstance();
       HiServiceObject obj = HiRegisterService.getService(this.name);
       obj.setRunning(String.valueOf(status));
 
       locator.rebind(HiRegisterService.getJndiName(this.name), obj);
     } catch (HiException e) {
       e.printStackTrace();
     }
   }
 
   public String getStatusMsg()
   {
     if (this.status == 0)
       return "停止";
     if (this.status == 1)
       return "启动";
     if (this.status == 2)
       return "暂停";
     if (this.status == 3)
       return "失败";
     if (this.status == 4) {
       return "未部署";
     }
     return "停止";
   }
 
   public String getAuto_restart()
   {
     return this.auto_restart;
   }
 
   public void setAuto_restart(String auto_restart) {
     this.auto_restart = auto_restart;
   }
 
   public String getConfig_file() {
     return this.config_file;
   }
 
   public void setConfig_file(String config_file) {
     this.config_file = config_file;
   }
 
   public String getMax() {
     return this.max;
   }
 
   public void setMax(String max) {
     this.max = max;
   }
 
   public String getMin() {
     return this.min;
   }
 
   public void setMin(String min) {
     this.min = min;
   }
 
   public String getName() {
     return this.name;
   }
 
   public void setType(String type) {
     this.type = type;
   }
 
   public String getType() {
     return this.type;
   }
 
   public void setName(String name) {
     this.name = name;
   }
 
   public String toString() {
     StringBuffer result = new StringBuffer();
     result.append(this.name);
     result.append(":");
     result.append(this.min);
     result.append(":");
     result.append(this.max);
     result.append(":");
     result.append(this.config_file);
     result.append(":");
     result.append(this.auto_restart);
     return result.toString();
   }
 
   public String getDesc() {
     if (this.desc == null)
       return this.name;
     return this.desc;
   }
 
   public void setDesc(String desc) {
     this.desc = desc;
   }
 
   public String toHTML()
   {
     StringBuffer buffer = new StringBuffer();
     buffer.append("<tr>");
     buffer.append("<td width='15%'>");
     buffer.append("<input type='checkbox' name='server' value='" + this.name + "'/>");
 
     buffer.append("<a href='/mon/show_server_config.jsp?serverName=");
     buffer.append(this.name);
     buffer.append("'/>");
     buffer.append(this.name + "</td>");
     getStatus();
 
     if (this.status == 0)
       buffer.append("<td bgcolor = 'yellow' width='10%'> ");
     else if (this.status == 1)
       buffer.append("<td  bgcolor = 'green' width='10%'> ");
     else if (this.status == 3)
       buffer.append("<td  bgcolor = 'red' width='10%'> ");
     else if (this.status == 4)
       buffer.append("<td  bgcolor = 'white' width='10%'> ");
     else {
       buffer.append("<td bgcolor = 'white' width='10%'> ");
     }
 
     buffer.append(getStatusMsg());
 
     buffer.append("</td>");
     buffer.append("<td width='25%'> " + getOperTime() + "</td>");
     buffer.append("<td width='40%'> " + getDesc() + " </td> </tr>\n");
     return buffer.toString();
   }
 
   public String getOperTime() {
     return this.operTime;
   }
 
   public void setOperTime(String startTime) {
     this.operTime = startTime;
   }
 
   public String getGrpNam() {
     if (this.groupNode != null) {
       return this.groupNode.getName();
     }
     return "";
   }
 
   public HiGroupNode getGroupNode()
   {
     return this.groupNode;
   }
 
   public void setGroupNode(HiGroupNode groupNode) {
     this.groupNode = groupNode;
   }
 
   public String getAppName()
   {
     return this.appName;
   }
 
   public void setAppName(String appName)
   {
     this.appName = appName;
   }
 }