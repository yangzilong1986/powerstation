 package com.hisun.web.action.other;

 import com.opensymphony.xwork2.ActionSupport;
 import org.apache.struts2.ServletActionContext;
 import sun.net.TelnetInputStream;
 import sun.net.ftp.FtpClient;

 import javax.servlet.ServletContext;
 import java.io.File;
 import java.io.FileOutputStream;
 import java.io.InputStream;
 
 public class DownloadAction extends ActionSupport
 {
   private String serverIP;
   private String strUsername;
   private String strPassword;
   private String strServPath;
   private String localFileName;
   private String prefilename;
   private String date1;
 
   public DownloadAction()
   {
     this.serverIP = "";
     this.strUsername = "";
     this.strPassword = "";
     this.strServPath = "";
 
     this.prefilename = "";
 
     this.date1 = null; }
 
   public void setDate1(String dwDate) { this.date1 = dwDate;
   }
 
   public String getDate1() {
     return this.date1;
   }
 
   public void setServerIP(String serverIP) {
     this.serverIP = serverIP;
   }
 
   public void setStrUsername(String strUsername) {
     this.strUsername = strUsername;
   }
 
   public void setStrPassword(String strPassword) {
     this.strPassword = strPassword;
   }
 
   public void setStrServPath(String strServPath) {
     this.strServPath = strServPath;
   }
 
   public void setLocalFileName(String localFileName) {
     this.localFileName = localFileName;
   }
 
   public void setPrefilename(String prefilename) {
     this.prefilename = prefilename;
   }
 
   public InputStream getInputStream() throws Exception {
     return ServletActionContext.getServletContext().getResourceAsStream("/conf/" + this.prefilename + ".tar");
   }
 
   public String execute() throws Exception
   {
     String filename = this.prefilename + "_" + this.date1 + ".tar";
     this.localFileName = ServletActionContext.getServletContext().getRealPath("/conf/" + this.prefilename + ".tar");
 
     System.out.println("filename : " + filename);
     System.out.println("localFileName : " + this.localFileName);
     try
     {
       FtpClient ftpClient = new FtpClient();
 
       ftpClient.openServer(this.serverIP);
 
       ftpClient.login(this.strUsername, this.strPassword);
 
       if (this.strServPath.length() != 0) {
         ftpClient.cd(this.strServPath);
       }
       ftpClient.binary();
 
       TelnetInputStream tis = ftpClient.get(filename);
       File fileBuffer = new File(this.localFileName);
       FileOutputStream fos = new FileOutputStream(fileBuffer);
       byte[] bytes = new byte[1024];
 
       while ((c = tis.read(bytes)) != -1)
       {
         int c;
         fos.write(bytes, 0, c);
       }
       tis.close();
       fos.close();
       ftpClient.closeServer();
 
       ftpClient.closeServer();
     }
     catch (Exception ex)
     {
       ex.printStackTrace();
       return "";
     }
 
     return "success";
   }
 }