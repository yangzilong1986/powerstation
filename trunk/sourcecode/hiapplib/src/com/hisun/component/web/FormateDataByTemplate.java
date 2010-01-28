 package com.hisun.component.web;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.common.HiETF2HashMapList;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.template.TemplateEngine;
 import com.hisun.template.TemplateFactory;
 import java.io.DataOutputStream;
 import java.io.FileOutputStream;
 import org.apache.commons.lang.StringUtils;
 
 public class FormateDataByTemplate
 {
   private TemplateEngine freemarkerEngine;
 
   public FormateDataByTemplate()
   {
     this.freemarkerEngine = ((TemplateEngine)TemplateFactory.getInstance().getBean("freemarker"));
   }
 
   public int execute(HiATLParam args, HiMessageContext ctx)
     throws HiException, Exception
   {
     String template = HiArgUtils.getStringNotNull(args, "template");
 
     String objEtf = args.get("objEtf");
 
     String objFile = args.get("objFile");
 
     if ((StringUtils.isBlank(objEtf)) && (StringUtils.isBlank(objFile)))
     {
       objEtf = "OBJ_DAT";
     }
 
     HiMessage msg = ctx.getCurrentMsg();
 
     HiETF2HashMapList etf2HashMap = new HiETF2HashMapList(msg.getETFBody());
     StringBuffer result = this.freemarkerEngine.run(etf2HashMap.map(), template);
 
     if (StringUtils.isNotBlank(objFile))
     {
       DataOutputStream dout = new DataOutputStream(new FileOutputStream(objFile));
       dout.write(result.toString().getBytes());
     }
     else
     {
       msg.getETFBody().setChildValue(objEtf, result.toString());
     }
 
     return 0;
   }
 }