 package com.hisun.engine.invoke.load;
 
 import com.hisun.engine.invoke.HiIEngineModel;
 import com.hisun.engine.invoke.impl.HiAbstractApplication;
 import com.hisun.exception.HiException;
 import java.lang.reflect.Method;
 import java.util.HashMap;
 import org.apache.commons.beanutils.MethodUtils;
 
 public class HiDelegate extends HiAbstractApplication
 {
   private String strNodeName = null;
 
   private HashMap childMaps = null;
 
   public String getNodeName()
   {
     if (this.strNodeName == null) {
       return super.getNodeName();
     }
     return this.strNodeName;
   }
 
   public void setNodeName(String strNodeName)
   {
     this.strNodeName = strNodeName;
   }
 
   public void addChilds(HiIEngineModel child)
     throws HiException
   {
     Method method = MethodUtils.getAccessibleMethod(child.getClass(), "getName", new Class[0]);
 
     if (method != null)
     {
       if (this.childMaps == null) {
         this.childMaps = new HashMap();
       }
       try
       {
         Object name = method.invoke(child, new Object[0]);
         this.childMaps.put(name, child);
       }
       catch (Throwable t)
       {
       }
     }
 
     super.addChilds(child);
   }
 
   public HashMap getChildsMap()
   {
     return this.childMaps;
   }
 }