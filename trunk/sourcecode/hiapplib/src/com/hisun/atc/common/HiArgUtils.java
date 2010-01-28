 package com.hisun.atc.common;
 
 import com.hisun.exception.HiAppException;
 import com.hisun.exception.HiException;
 import com.hisun.hiexpression.HiExpFactory;
 import com.hisun.hiexpression.HiExpression;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiICSProperty;
 import com.hisun.util.HiStringManager;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.SystemUtils;
 
 public class HiArgUtils
 {
   private static HiStringManager sm = HiStringManager.getManager();
 
   public static String getString(HiETF root, String name) {
     String value = root.getChildValue(name);
     if (value != null) {
       return value.trim();
     }
 
     return value;
   }
 
   public static String getStringNull(HiETF root, String name)
   {
     return root.getChildValue(name);
   }
 
   public static String getStringNotNull(HiATLParam args, String name) throws HiException
   {
     String value = args.get(name);
     if (StringUtils.isEmpty(value)) {
       throw new HiException("220026", name);
     }
     return value.trim();
   }
 
   public static String getStringNotNull(HiATLParam args, String name, int retcode) throws HiException
   {
     String value = args.get(name.toUpperCase());
     if (StringUtils.isEmpty(value)) {
       throw new HiAppException(retcode, "220026", name);
     }
     return value.trim();
   }
 
   public static String getStringNotNull(HiETF root, String name) throws HiException
   {
     String value = root.getChildValue(name);
     if (StringUtils.isEmpty(value)) {
       throw new HiException("220082", name);
     }
     return value.trim();
   }
 
   public static String getStringNotNull(HiATLParam args, int argNum) throws HiException
   {
     if (args.size() < argNum) {
       throw new HiException("220026", "参数个数不足.");
     }
 
     String value = null;
 
     Iterator argIt = args.values().iterator();
 
     for (int i = 0; i < argNum; ++i) {
       if (i == argNum - 1)
         value = (String)argIt.next();
       else {
         argIt.next();
       }
     }
     return value;
   }
 
   public static String getString(HiMessageContext ctx, String name)
     throws HiException
   {
     return ctx.getStrProp(name);
   }
 
   public static String getStringNotNull(HiMessageContext ctx, String name) throws HiException
   {
     String value = (String)ctx.getProperty(name);
     if (StringUtils.isEmpty(value)) {
       throw new HiException("220060", "临时区找不到指定的存贮域 " + name + ", 或不可为null.");
     }
 
     return value;
   }
 
   public static String getFirstNotNull(HiATLParam args) throws HiException {
     String value = (String)args.values().iterator().next();
     if (StringUtils.isEmpty(value)) {
       throw new HiException("220026", "参数值不可为空或为null");
     }
     return value;
   }
 
   public static void judgeArgsEnough(HiATLParam args, int num, int retcode)
     throws HiException
   {
     if (args.size() <= num)
       throw new HiAppException(retcode, "220026", "参数个数不正确");
   }
 
   public static void judgeArgsEnough(HiATLParam args, int num)
     throws HiException
   {
     if (args.size() <= num)
       throw new HiException("220026", "参数个数不正确");
   }
 
   public static Map getEtfFields(HiETF etfBody, String fldLst, String deli)
   {
     String[] fields = StringUtils.split(fldLst, deli);
     Map etfFld = new HashMap();
 
     for (int i = 0; i < fields.length; ++i)
     {
       etfFld.put(fields[i], etfBody.getChildValue(fields[i]));
     }
 
     return etfFld;
   }
 
   public static List getFldValues(HiETF etfBody, String fldLst, String deli)
     throws HiException
   {
     String[] fields = StringUtils.split(fldLst, deli);
     List etfFld = new ArrayList();
 
     for (int i = 0; i < fields.length; ++i) {
       String Value = etfBody.getChildValue(fields[i]);
       if (Value != null)
       {
         etfFld.add(Value);
       }
       else throw new HiException("220044", "ETF树上无此节点" + fields[i]);
 
     }
 
     return etfFld;
   }
 
   public static String getRealValue(String VarName, HiMessageContext ctx)
     throws HiException
   {
     String realValue = VarName;
 
     HiExpression exp = HiExpFactory.createExp(VarName);
     if (exp != null) {
       realValue = exp.getValue(ctx);
       if (realValue == null) {
         throw new HiException("", "参数值不正确：" + VarName);
       }
     }
 
     return realValue;
   }
 
   public static String absoutePath(String name) throws HiException
   {
     if (!(name.startsWith(SystemUtils.FILE_SEPARATOR))) {
       name = HiICSProperty.getWorkDir() + SystemUtils.FILE_SEPARATOR + name;
     }
 
     return name;
   }
 }