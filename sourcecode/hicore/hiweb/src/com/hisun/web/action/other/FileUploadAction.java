 package com.hisun.web.action.other;

 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFFactory;
 import com.hisun.web.action.BaseAction;
 import org.apache.struts2.ServletActionContext;

 import javax.servlet.http.HttpServletRequest;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileOutputStream;
 import java.io.InputStream;
 import java.text.DateFormat;
 import java.text.SimpleDateFormat;
 import java.util.Date;
 import java.util.Random;
 
 public class FileUploadAction extends BaseAction
 {
   private static final long serialVersionUID = 1L;
   private static final int BUFFER_SIZE = 16384;
   private File doc;
   private String fileName;
   private String contentType;
   private String tartgetFilePath;
 
   public void setFile(File file)
   {
     this.doc = file;
   }
 
   public void setFileFileName(String fileName) {
     this.fileName = fileName;
   }
 
   public void setFileContentType(String contentType) {
     this.contentType = contentType;
   }
 
   public String execute() throws Exception {
     Logger _log = this.logFactory.getLogger();
     HttpServletRequest request = ServletActionContext.getRequest();
 
     String destpath = request.getParameter("destpath");
     String targetDirectory = destpath;
     String targetFileName = this.fileName;
 
     this.tartgetFilePath = destpath + targetFileName;
     File target = new File(targetDirectory, targetFileName);
 
     copyFile(this.doc, target);
 
     return super.execute();
   }
 
   private void copyFile(File src, File dst) {
     try {
       FileOutputStream fos = null;
       InputStream fis = new FileInputStream(src);
       fos = new FileOutputStream(dst);
       byte[] buffer = new byte[1024];
       while (true) {
         int length = fis.read(buffer);
         if (length < 0) {
           break;
         }
         fos.write(buffer, 0, length);
       }
       fis.close();
       fos.close();
     }
     catch (Exception e) {
       e.printStackTrace();
     }
   }
 
   private String generateFileName(String fileName) {
     DateFormat format = new SimpleDateFormat("yyMMddHHmmss");
     String formatDate = format.format(new Date());
 
     int random = new Random().nextInt(10000);
 
     int position = fileName.lastIndexOf(".");
     String extension = fileName.substring(position);
 
     return formatDate + random + extension;
   }
 
   protected HiETF beforeProcess(HttpServletRequest request, Logger _log)
     throws HiException
   {
     HiETF etf = super.beforeProcess(request, _log);
     if (etf == null) {
       etf = HiETFFactory.createETF();
     }
 
     etf.setChildValue("FIL_PATH", this.tartgetFilePath);
     return etf;
   }
 }