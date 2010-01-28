 package com.hisun.component.web;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import java.io.File;
 import java.text.SimpleDateFormat;
 import java.util.Date;
 import java.util.Iterator;
 import java.util.List;
 import javax.servlet.ServletContext;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpSession;
 import org.apache.commons.fileupload.FileItem;
 import org.apache.commons.fileupload.disk.DiskFileItemFactory;
 import org.apache.commons.fileupload.servlet.ServletFileUpload;
 import org.apache.commons.lang.StringUtils;
 
 public class FileUpload
 {
   public int execute(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     int maxPostSize = args.getInt("MaxSize");
     if (maxPostSize == 0) {
       maxPostSize = 102400;
     }
     maxPostSize *= 1024;
     String uploadPath = args.get("UpLoadPath");
     if (StringUtils.isBlank(uploadPath)) {
       uploadPath = "upload/";
     }
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
 
     HttpServletRequest request = (HttpServletRequest)msg.getObjectHeadItem("_WEB_REQUEST");
 
     String path = request.getSession().getServletContext().getRealPath(uploadPath);
     File f = new File(path);
 
     if (!(f.exists())) {
       f.mkdirs();
     }
 
     try
     {
       DiskFileItemFactory factory = new DiskFileItemFactory();
       factory.setSizeThreshold(4096);
       ServletFileUpload upload = new ServletFileUpload(factory);
       upload.setSizeMax(maxPostSize);
       upload.setHeaderEncoding("GBK");
 
       List fileItems = upload.parseRequest(request);
       Iterator iter = fileItems.iterator();
 
       HiETF root = msg.getETFBody();
       int j = 1;
 
       boolean overFlg = false;
       while (iter.hasNext()) {
         FileItem item = (FileItem)iter.next();
 
         if (item.isFormField()) {
           if (StringUtils.equalsIgnoreCase(item.getFieldName(), "UPLOAD_PATH")) {
             uploadPath = item.getString();
             path = request.getSession().getServletContext().getRealPath(uploadPath);
             f = new File(path);
             if (!(f.exists())) {
               f.mkdirs();
             }
           }
           if (StringUtils.equalsIgnoreCase(item.getFieldName(), "OVR_FLG")) {
             overFlg = true;
           }
           setValue(root, item.getFieldName(), item.getString());
         }
 
         if (!(uploadPath.endsWith("/"))) {
           uploadPath = uploadPath + "/";
         }
 
         String orgName = item.getName();
         String fldName = item.getFieldName();
 
         if (StringUtils.isBlank(orgName)) {
           continue;
         }
 
         int idx = orgName.lastIndexOf(92);
         if (idx != -1) {
           orgName = orgName.substring(idx + 1);
         }
         idx = fldName.indexOf(46);
         String name = null;
         if (idx != -1) {
           name = fldName.substring(idx + 1);
           fldName = fldName.substring(0, idx);
           setValue(root, fldName, orgName);
         } else {
           if (!(overFlg)) {
             SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
             name = orgName + "." + df.format(new Date());
           } else {
             name = orgName;
           }
           setValue(root, fldName, orgName);
         }
 
         File f1 = new File(request.getSession().getServletContext().getRealPath(uploadPath + name));
 
         log.info(f1);
 
         item.write(f1);
         ++j;
         setValue(root, fldName + "_NEW", uploadPath + name);
       }
     } catch (Exception e) {
       throw new HiException(e);
     }
 
     return 0;
   }
 
   private void setValue(HiETF root, String name, String value) {
     if (!(StringUtils.isBlank(value)))
       root.setChildValue(name, value);
   }
 }