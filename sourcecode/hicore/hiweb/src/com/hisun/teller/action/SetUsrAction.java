 package com.hisun.teller.action;

 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFFactory;
 import com.hisun.web.action.BaseAction;
 import org.apache.commons.lang.math.NumberUtils;
 import org.apache.struts2.ServletActionContext;

 import javax.servlet.ServletContext;
 import javax.servlet.http.HttpServletRequest;
 import java.util.HashSet;
 import java.util.Set;
 
 public class SetUsrAction extends BaseAction
 {
   private static final long serialVersionUID = 1L;
   private Set selset;
 
   public SetUsrAction()
   {
     this.selset = new HashSet();
   }
 
   protected boolean endProcess(HttpServletRequest request, HiETF rspetf, Logger _log)
   {
     ServletContext context = ServletActionContext.getServletContext();
     HiETF etf = HiETFFactory.createETF();
     etf.setChildValue("ID", rspetf.getChildValue("ID"));
     HiETF etf2 = null;
     String txncode = "MNG090938";
     try {
       etf2 = getCallHostService().callhost(txncode, etf, context);
 
       int num = NumberUtils.toInt(etf2.getChildValue("REC_NUM"));
 
       for (int j = 0; j < num; ++j) {
         HiETF node = etf2.getChildNode("GROUP_" + (j + 1));
         if (node == null)
           break;
         String grpid = node.getChildValue("GRP_ID");
         this.selset.add(grpid);
       }
 
       request.setAttribute("ROLESET", this.selset);
     }
     catch (HiException e) {
       e.printStackTrace();
     }
 
     return super.endProcess(request, rspetf, _log);
   }
 }