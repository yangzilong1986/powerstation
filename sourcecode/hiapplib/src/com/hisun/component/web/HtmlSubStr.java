 package com.hisun.component.web;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import java.io.PrintStream;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 
 public class HtmlSubStr
 {
   public int execute(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     int offset = NumberUtils.toInt(args.get("off"));
     int length = NumberUtils.toInt(args.get("len"));
     String content = args.get("Content");
     String dstName = HiArgUtils.getStringNotNull(args, "DstNam");
     HiMessage msg = ctx.getCurrentMsg();
     HiETF root = msg.getETFBody();
     Logger log = HiLog.getLogger(msg);
     if (StringUtils.isEmpty(content)) {
       return 0;
     }
     content = HtmlFilter.htmlChanger(content, "");
 
     if (offset < 1) {
       offset = 1;
     }
 
     if (offset >= content.length()) {
       return 0;
     }
 
     if ((length <= 0) && (length >= content.length())) {
       length = content.length();
     }
 
     if (offset + length >= content.length()) {
       length = content.length() - offset;
     }
     root.setChildValue(dstName, content.substring(offset - 1, offset - 1 + length));
 
     return 0;
   }
 
   public static void main(String[] args) {
     String content = "adfasfdasdf";
     int offset = 1;
     int length = content.length();
     System.out.println(content.substring(3, 8));
   }
 }