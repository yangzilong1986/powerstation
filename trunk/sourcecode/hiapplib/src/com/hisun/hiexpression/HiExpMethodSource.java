 package com.hisun.hiexpression;
 
 import [Ljava.lang.String;;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.util.HiICSProperty;
 import com.hisun.util.HiResource;
 import java.lang.reflect.Method;
 import java.util.ArrayList;
 import org.apache.commons.lang.StringUtils;
 
 public class HiExpMethodSource
 {
   private static Logger log = HiLog.getLogger("expression.trc");
 
   public static Method getMethod(String method, int nargs) throws Exception {
     if (nargs <= 0) {
       argClasses = new Class[] { Object.class };
       return getStaticMethod(method, argClasses);
     }
     Class[] argClasses = { Object.class, [Ljava.lang.String.class };
 
     return getStaticMethod(method, argClasses);
   }
 
   private static Method getStaticMethod(String staticMethodName, Class[] argClasses) throws Exception
   {
     Method thisMethod = null;
     ArrayList classes = new ArrayList();
     classes.add(HiExpBasicFunctions.class);
     classes.add(HiExpMath.class);
     String tmp = HiICSProperty.getProperty("expr.extend");
 
     if (!(StringUtils.isEmpty(tmp))) {
       String[] tmps = StringUtils.split(tmp, ", ");
       for (int i = 0; i < tmps.length; ++i) {
         try {
           classes.add(HiResource.loadClass(tmps[i]));
         } catch (Exception e) {
           log.error("load class:[" + tmps[i] + "] failure:[ " + e + "]", e);
         }
       }
 
     }
 
     for (int i = 0; i < classes.size(); ) {
       try {
         Class clazz = (Class)classes.get(i);
         thisMethod = clazz.getMethod(staticMethodName, argClasses);
       }
       catch (SecurityException e) {
         throw e;
       }
       catch (NoSuchMethodException e)
       {
         while (true) {
           ++i;
         }
 
       }
 
     }
 
     if (thisMethod == null)
       throw new NoSuchMethodException(staticMethodName);
     return thisMethod;
   }
 }