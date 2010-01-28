 package com.hisun.component.no;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 
 public class Split
 {
   public int execute(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     HiETF etfBody = msg.getETFBody();
     Logger log = HiLog.getLogger(msg);
 
     String sValue = HiArgUtils.getStringNotNull(args, "Value");
     String grpNam = args.get("GrpNam");
     String paraNam = args.get("ParaNam");
     String sepChar = args.get("SepChar");
 
     if (StringUtils.isEmpty(grpNam)) {
       grpNam = "PARA";
     }
 
     if (StringUtils.isEmpty(paraNam)) {
       paraNam = "NAM";
     }
 
     if (StringUtils.isEmpty(sepChar)) {
       sepChar = " ";
     }
 
     String tmp = args.get("Type");
     int sepTyp = 0;
     sepTyp = NumberUtils.toInt(tmp);
     if ((sepTyp != 0) || (sepTyp != 1)) {
       sepTyp = 0;
     }
 
     if (log.isInfoEnabled()) {
       log.info(String.format("Value[%s]GrpNam[%s]SepChar[%s]Type[%d]", new Object[] { sValue, grpNam, sepChar, Integer.valueOf(sepTyp) }));
     }
 
     String[] paras = null;
 
     if (StringUtils.isEmpty(sValue)) {
       etfBody.setChildValue(grpNam + "_NUM", "0");
     } else {
       if (sepTyp == 1)
         paras = StringUtils.split(sValue, sepChar);
       else if (sepTyp == 0) {
         paras = StringUtils.splitByWholeSeparator(sValue, sepChar);
       }
       for (int i = 0; i < paras.length; ++i) {
         HiETF grp = etfBody.getChildNode(grpNam + "_" + (i + 1));
         if (grp == null) {
           grp = etfBody.addNode(grpNam + "_" + (i + 1));
         }
 
         if ((paras[i].indexOf(38) != -1) || (paras[i].indexOf(61) != -1)) {
           String[] tmp1s = paras[i].split("&");
           for (int j = 0; j < tmp1s.length; ++j) {
             String[] tmp2s = tmp1s[j].split("=");
             grp.setChildValue(tmp2s[0], tmp2s[1]);
           }
         } else {
           grp.setChildValue(paraNam, paras[i]);
         }
       }
       etfBody.setChildValue(grpNam + "_NUM", String.valueOf(paras.length));
     }
 
     return 0;
   }
 }