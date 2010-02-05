 package com.hisun.mon.atc;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import org.apache.commons.lang.StringUtils;
 
 public class ReplaceWith
 {
   public int execute(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String template = HiArgUtils.getStringNotNull(args, "template");
 
     String repl = args.get("repl");
     if (StringUtils.isEmpty(repl)) {
       repl = "#";
     }
 
     String objNam = HiArgUtils.getStringNotNull(args, "objNam");
 
     String text = HiArgUtils.getStringNotNull(args, "text");
     String separator = args.get("sepr");
     if (StringUtils.isEmpty(separator)) {
       separator = "|";
     }
     String[] textArr = StringUtils.splitPreserveAllTokens(text, separator);
 
     for (int i = 0; i < textArr.length; ++i) {
       template = StringUtils.replaceOnce(template, repl, textArr[i]);
     }
 
     ctx.getCurrentMsg().getETFBody().setGrandChildNode(objNam, template);
 
     return 0;
   }
 }