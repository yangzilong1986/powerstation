 package com.hisun.web.tag;
 
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.util.HiICSProperty;
 import java.io.BufferedReader;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.IOException;
 import java.io.InputStreamReader;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.LinkedList;
 import javax.servlet.ServletRequest;
 import javax.servlet.jsp.JspException;
 import javax.servlet.jsp.PageContext;
 import javax.servlet.jsp.tagext.BodyTagSupport;
 import net.sf.json.JSONArray;
 import net.sf.json.JSONObject;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
 
 public class HiFileTag extends BodyTagSupport
 {
   private Logger log;
   private String file;
   private int skipLines;
   private int tailLines;
   private JSONObject cols;
   private String separator;
   private String charSet;
   private String groupName;
 
   public HiFileTag()
   {
     this.log = HiLog.getLogger("SYS.trc");
 
     this.separator = " \t";
 
     this.groupName = "GRP"; }
 
   public int doEndTag() throws JspException {
     ArrayList tmpList = loadFile();
     HashMap root = (HashMap)this.pageContext.getRequest().getAttribute("ETF");
 
     if (root == null) {
       root = new HashMap();
       this.pageContext.getRequest().setAttribute("ETF", root);
     }
     root.put(this.groupName, tmpList);
     root.put("TOT_REC_NUM", Integer.valueOf(tmpList.size()));
     super.doEndTag();
     return 6;
   }
 
   public String getFile() {
     return this.file;
   }
 
   public void setFile(String file) throws JspException {
     this.file = ((String)ExpressionEvaluatorManager.evaluate("file", file, Object.class, this, this.pageContext));
 
     if ((file == null) || (file.equals("")))
       throw new JspException("file property is empty");
   }
 
   public String getCols()
   {
     return "";
   }
 
   public void setCols(String cols) throws JspException {
     if (StringUtils.isEmpty(cols)) {
       return;
     }
     String tmp = (String)ExpressionEvaluatorManager.evaluate("cols", cols, Object.class, this, this.pageContext);
 
     if (StringUtils.isEmpty(tmp)) {
       return;
     }
 
     if ((!(tmp.startsWith("cols:{["))) && (!(tmp.endsWith("]}")))) {
       tmp = "cols:{[" + tmp + "]}";
     }
 
     this.cols = JSONObject.fromObject(tmp);
   }
 
   public String getSeparator()
   {
     return this.separator;
   }
 
   public void setSeparator(String separator) throws JspException {
     this.separator = ((String)ExpressionEvaluatorManager.evaluate("separator", separator, Object.class, this, this.pageContext));
 
     if (StringUtils.isEmpty(this.separator))
       throw new JspException("separator property is empty");
   }
 
   public String getGroupName()
   {
     return this.groupName;
   }
 
   public void setGroupName(String groupName) throws JspException {
     this.groupName = ((String)ExpressionEvaluatorManager.evaluate("groupName", groupName, Object.class, this, this.pageContext));
 
     if (StringUtils.isEmpty(this.groupName))
       throw new JspException("groupName property is empty");
   }
 
   public String getCharSet()
   {
     return this.charSet;
   }
 
   public void setCharSet(String charSet) throws JspException {
     this.charSet = ((String)ExpressionEvaluatorManager.evaluate("charSet", charSet, Object.class, this, this.pageContext));
 
     if (StringUtils.isEmpty(this.charSet))
       throw new JspException("charSet property is empty");
   }
 
   private ArrayList loadFile() throws JspException
   {
     ArrayList list = new ArrayList();
     BufferedReader br = null;
     try
     {
       String line;
       HashMap tmpMap;
       File f = new File(HiICSProperty.getWorkDir() + "/" + this.file);
       if (this.charSet != null) {
         br = new BufferedReader(new InputStreamReader(new FileInputStream(f), this.charSet));
       }
       else {
         br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
       }
 
       if (this.tailLines <= 0) {
         int i = 0;
         while ((line = br.readLine()) != null) {
           if (line.startsWith("#")) {
             continue;
           }
           ++i;
           if (i <= this.skipLines) {
             continue;
           }
           tmpMap = accumulate2HashMap(this.cols, line);
           if (tmpMap.size() == 0) {
             continue;
           }
           list.add(tmpMap);
         }
       } else {
         tmpList = new LinkedList();
         while ((line = br.readLine()) != null) {
           if (line.startsWith("#")) {
             continue;
           }
 
           if (tmpList.size() == this.tailLines) {
             tmpList.poll();
           }
           tmpList.offer(line);
         }
         while (!(tmpList.isEmpty())) {
           line = (String)tmpList.poll();
 
           tmpMap = accumulate2HashMap(this.cols, line);
           list.add(tmpMap);
         }
       }
       br.close();
       br = null;
       LinkedList tmpList = list;
 
       return tmpList;
     }
     catch (IOException e)
     {
     }
     finally
     {
       if (br != null)
         try {
           br.close();
         } catch (IOException e) {
           e.printStackTrace();
         }
     }
   }
 
   public int doStartTag()
     throws JspException
   {
     super.doStartTag();
     return 1;
   }
 
   public int getSkipLines()
   {
     return this.skipLines;
   }
 
   public void setSkipLines(int skipLines)
   {
     this.skipLines = skipLines;
   }
 
   public String getTailLines()
   {
     return String.valueOf(this.tailLines);
   }
 
   public void setTailLines(String tailLines)
     throws Exception
   {
     tailLines = (String)ExpressionEvaluatorManager.evaluate("tailLines", tailLines, Object.class, this, this.pageContext);
 
     this.tailLines = NumberUtils.toInt(tailLines);
   }
 
   public static void accumulate(JSONObject obj, String str)
   {
     JSONArray array;
     int j;
     JSONObject tmpObj;
     if (obj.containsKey("separator"))
     {
       String[] tmps = StringUtils.split(str, obj.getString("separator"));
       if (tmps.length == 0) {
         return;
       }
       array = obj.getJSONArray("cols");
       for (j = 0; j < array.size(); ++j) {
         tmpObj = array.getJSONObject(j);
         if (j >= tmps.length)
           obj.accumulate("value", "");
         else
           obj.accumulate("value", tmps[j]);
       }
     }
     else
     {
       int offset = 0;
       array = obj.getJSONArray("cols");
       for (j = 0; j < array.size(); ++j) {
         tmpObj = array.getJSONObject(j);
         if (obj.containsKey("offset")) {
           offset = obj.getInt("offset");
         }
         if (offset + obj.getInt("length") >= str.length())
           obj.accumulate("value", "");
         else {
           obj.accumulate("value", str.substring(offset, obj.getInt("length")));
         }
         offset += obj.getInt("length");
       }
     }
   }
 
   public static HashMap accumulate2HashMap(JSONObject obj, String str) {
     accumulate(obj, str);
     JSONArray array = obj.getJSONArray("cols");
     HashMap map = new HashMap();
     for (int i = 0; i < array.size(); ++i) {
       JSONObject tmpObj = (JSONObject)array.get(i);
       if (tmpObj.containsKey("value"))
         map.put(tmpObj.get("name"), tmpObj.get("value"));
       else {
         map.put(tmpObj.get("name"), "");
       }
     }
     return map;
   }
 }