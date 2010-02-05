 package com.hisun.common;
 
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.web.filter.HiDataConvert;
 import java.util.Enumeration;
 import javax.servlet.ServletRequest;
 import org.apache.commons.lang.StringUtils;
 
 public class HiForm2Etf
 {
   private HiDataConvert _dataConvert;
   private Logger log = HiLog.getLogger("form2etf.trc");
   private boolean ignoreCase = true;
 
   public void setIgnoreCase(boolean ignoreCase) { this.ignoreCase = ignoreCase;
   }
 
   public HiForm2Etf(HiDataConvert dataConvert) {
     this._dataConvert = dataConvert;
   }
 
   public HiETF process(ServletRequest request, HiETF etf) throws Exception {
     Enumeration en = request.getParameterNames();
     int idx1 = 0;
 
     while (en.hasMoreElements()) {
       String name = (String)en.nextElement();
       String[] values = request.getParameterValues(name);
       for (int i = 0; i < values.length; ++i) {
         this.log.info("[" + name + "]:[" + values[i] + "]");
       }
 
       String groupName = "GROUP";
       String varName = name;
       idx1 = name.indexOf(46);
 
       if (values.length == 1) {
         if (StringUtils.isBlank(values[0]))
           continue;
         if (idx1 == -1) {
           setValue(etf, name, values[0]);
         }
         groupName = name.substring(0, idx1);
         varName = name.substring(idx1 + 1);
         HiETF group = null;
         if (!(this.ignoreCase))
           group = etf.addNodeBase(groupName + "_1", "");
         else {
           group = etf.addNode(groupName + "_1");
         }
 
         setValue(group, varName, values[0]);
 
         if (!(this.ignoreCase))
           etf.setChildValueBase(groupName + "_NUM", "1");
         else {
           etf.setChildValue(groupName + "_NUM", "1");
         }
 
       }
 
       if (idx1 == -1)
       {
         setValue(etf, name, values[0]);
       }
 
       if (idx1 != -1) {
         groupName = name.substring(0, idx1);
         varName = name.substring(idx1 + 1);
       }
       int count = 0;
       for (int i = 0; i < values.length; ++i) {
         if (StringUtils.isBlank(values[i]))
           continue;
         String tmp = groupName + "_" + (count + 1);
         HiETF group = null;
         if (!(this.ignoreCase))
           group = etf.getChildNodeBase(tmp);
         else {
           group = etf.getChildNode(tmp);
         }
         if (group == null) {
           if (!(this.ignoreCase))
             group = etf.addNodeBase(tmp, "");
           else {
             group = etf.addNode(tmp);
           }
         }
         setValue(group, varName, values[i]);
         ++count;
       }
 
       if (count != 0) {
         if (!(this.ignoreCase))
           etf.setChildValueBase(groupName + "_NUM", String.valueOf(count));
         else {
           etf.setChildValue(groupName + "_NUM", String.valueOf(count));
         }
       }
     }
     return etf;
   }
 
   private void setValue(HiETF node, String name, String value) throws Exception
   {
     if (StringUtils.isNotBlank(value)) {
       int idx = name.lastIndexOf("__");
       if (idx != -1) {
         value = this._dataConvert.convert(name.substring(idx + 2), value);
         name = name.substring(0, idx);
       }
       this.log.info("ignoreCase:[" + this.ignoreCase + "]" + name + ":" + value);
       if (!(this.ignoreCase))
         node.setChildValueBase(name, value);
       else
         node.setChildValue(name, value);
     }
   }
 
   private void setValue01(HiETF node, String name, String value)
     throws Exception
   {
     if (value.indexOf(38) == -1) {
       if (StringUtils.isNotBlank(value)) {
         int idx = name.lastIndexOf("__");
         if (idx != -1) {
           value = this._dataConvert.convert(name.substring(idx + 2), value);
 
           name = name.substring(0, idx);
         }
         if (!(this.ignoreCase))
           node.setChildValueBase(name, value);
         else {
           node.setChildValue(name, value);
         }
       }
       return;
     }
 
     String[] nameValuePairs = value.split("&");
     int count = 0;
     for (int j = 0; j < nameValuePairs.length; ++j) {
       String tmp = name + "_" + (j + 1);
       HiETF group = null;
       if (!(this.ignoreCase))
         group = node.getChildNodeBase(tmp);
       else {
         group = node.getChildNode(tmp);
       }
 
       if (group == null) {
         if (!(this.ignoreCase))
           group = node.addNodeBase(tmp, "");
         else {
           group = node.addNode(tmp);
         }
       }
 
       String[] nameValuesPair = nameValuePairs[j].split("=");
       if (nameValuesPair.length == 2) {
         int idx = name.lastIndexOf("__");
         if (idx != -1) {
           tmp = this._dataConvert.convert(name.substring(idx + 2), value);
           name = name.substring(0, idx);
         }
         if (!(this.ignoreCase))
           group.setChildValueBase(nameValuesPair[0], tmp);
         else {
           group.setChildValue(nameValuesPair[0], tmp);
         }
         ++count;
       }
     }
 
     if (count != 0)
       if (!(this.ignoreCase))
         node.setChildValueBase(name + "_NUM", String.valueOf(count));
       else
         node.setChildValue(name + "_NUM", String.valueOf(count));
   }
 }