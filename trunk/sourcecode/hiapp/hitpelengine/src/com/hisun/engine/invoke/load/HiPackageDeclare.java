 package com.hisun.engine.invoke.load;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.engine.invoke.impl.HiFunction;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.parse.HiCfgFile;
 import com.hisun.parse.HiResourceRule;
 import com.hisun.util.HiResource;
 import java.net.URL;
 import java.util.HashMap;
 import java.util.List;
 
 public class HiPackageDeclare extends HiEngineModel
 {
   private final Logger logger = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
 
   public String getNodeName()
   {
     return "PackageDeclare";
   }
 
   public void setPackageInfo(String strPackageName, String strFileName)
     throws HiException
   {
     if (this.logger.isDebugEnabled())
     {
       this.logger.debug("setPackageInfo(String, String) - start");
     }
 
     String strNodeName = "PACKAGEDECLARE";
 
     HiEngineModel rootInstance = (HiEngineModel)loadFile(strFileName);
 
     List childs = rootInstance.getChilds();
     HashMap childsMap = new HashMap();
     for (int i = 0; i < childs.size(); ++i)
     {
       HiFunction child = (HiFunction)childs.get(i);
       childsMap.put(child.getName(), child);
     }
 
     HiContext.getCurrentContext().setProperty(strNodeName + "." + strPackageName.toUpperCase(), childsMap);
 
     if (!(this.logger.isDebugEnabled()))
       return;
     this.logger.debug("setPackageInfo(String, String) - end");
   }
 
   public Object loadFile(String strFileName)
     throws HiException
   {
     if (this.logger.isDebugEnabled())
     {
       this.logger.debug("loadFile(String) - start");
     }
 
     URL u = HiResource.getResource(strFileName);
     if (u == null)
     {
       throw new HiException("213318", strFileName);
     }
 
     Object returnObject = HiResourceRule.getCTLCfgFile(u).getRootInstance();
     if (this.logger.isDebugEnabled())
     {
       this.logger.debug("loadFile(String) - end");
     }
     return returnObject;
   }
 }