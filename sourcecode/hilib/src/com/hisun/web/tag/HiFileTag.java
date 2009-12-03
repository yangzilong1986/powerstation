/*     */ package com.hisun.web.tag;
/*     */ 
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyTagSupport;
/*     */ import net.sf.json.JSONArray;
/*     */ import net.sf.json.JSONObject;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
/*     */ 
/*     */ public class HiFileTag extends BodyTagSupport
/*     */ {
/*     */   private Logger log;
/*     */   private String file;
/*     */   private int skipLines;
/*     */   private int tailLines;
/*     */   private JSONObject cols;
/*     */   private String separator;
/*     */   private String charSet;
/*     */   private String groupName;
/*     */ 
/*     */   public HiFileTag()
/*     */   {
/*  38 */     this.log = HiLog.getLogger("SYS.trc");
/*     */ 
/*  59 */     this.separator = " \t";
/*     */ 
/*  67 */     this.groupName = "GRP"; }
/*     */ 
/*     */   public int doEndTag() throws JspException {
/*  70 */     ArrayList tmpList = loadFile();
/*  71 */     HashMap root = (HashMap)this.pageContext.getRequest().getAttribute("ETF");
/*     */ 
/*  73 */     if (root == null) {
/*  74 */       root = new HashMap();
/*  75 */       this.pageContext.getRequest().setAttribute("ETF", root);
/*     */     }
/*  77 */     root.put(this.groupName, tmpList);
/*  78 */     root.put("TOT_REC_NUM", Integer.valueOf(tmpList.size()));
/*  79 */     super.doEndTag();
/*  80 */     return 6;
/*     */   }
/*     */ 
/*     */   public String getFile() {
/*  84 */     return this.file;
/*     */   }
/*     */ 
/*     */   public void setFile(String file) throws JspException {
/*  88 */     this.file = ((String)ExpressionEvaluatorManager.evaluate("file", file, Object.class, this, this.pageContext));
/*     */ 
/*  90 */     if ((file == null) || (file.equals("")))
/*  91 */       throw new JspException("file property is empty");
/*     */   }
/*     */ 
/*     */   public String getCols()
/*     */   {
/*  97 */     return "";
/*     */   }
/*     */ 
/*     */   public void setCols(String cols) throws JspException {
/* 101 */     if (StringUtils.isEmpty(cols)) {
/* 102 */       return;
/*     */     }
/* 104 */     String tmp = (String)ExpressionEvaluatorManager.evaluate("cols", cols, Object.class, this, this.pageContext);
/*     */ 
/* 106 */     if (StringUtils.isEmpty(tmp)) {
/* 107 */       return;
/*     */     }
/*     */ 
/* 110 */     if ((!(tmp.startsWith("cols:{["))) && (!(tmp.endsWith("]}")))) {
/* 111 */       tmp = "cols:{[" + tmp + "]}";
/*     */     }
/*     */ 
/* 114 */     this.cols = JSONObject.fromObject(tmp);
/*     */   }
/*     */ 
/*     */   public String getSeparator()
/*     */   {
/* 123 */     return this.separator;
/*     */   }
/*     */ 
/*     */   public void setSeparator(String separator) throws JspException {
/* 127 */     this.separator = ((String)ExpressionEvaluatorManager.evaluate("separator", separator, Object.class, this, this.pageContext));
/*     */ 
/* 129 */     if (StringUtils.isEmpty(this.separator))
/* 130 */       throw new JspException("separator property is empty");
/*     */   }
/*     */ 
/*     */   public String getGroupName()
/*     */   {
/* 136 */     return this.groupName;
/*     */   }
/*     */ 
/*     */   public void setGroupName(String groupName) throws JspException {
/* 140 */     this.groupName = ((String)ExpressionEvaluatorManager.evaluate("groupName", groupName, Object.class, this, this.pageContext));
/*     */ 
/* 142 */     if (StringUtils.isEmpty(this.groupName))
/* 143 */       throw new JspException("groupName property is empty");
/*     */   }
/*     */ 
/*     */   public String getCharSet()
/*     */   {
/* 148 */     return this.charSet;
/*     */   }
/*     */ 
/*     */   public void setCharSet(String charSet) throws JspException {
/* 152 */     this.charSet = ((String)ExpressionEvaluatorManager.evaluate("charSet", charSet, Object.class, this, this.pageContext));
/*     */ 
/* 154 */     if (StringUtils.isEmpty(this.charSet))
/* 155 */       throw new JspException("charSet property is empty");
/*     */   }
/*     */ 
/*     */   private ArrayList loadFile() throws JspException
/*     */   {
/* 160 */     ArrayList list = new ArrayList();
/* 161 */     BufferedReader br = null;
/*     */     try
/*     */     {
/*     */       String line;
/*     */       HashMap tmpMap;
/* 167 */       File f = new File(HiICSProperty.getWorkDir() + "/" + this.file);
/* 168 */       if (this.charSet != null) {
/* 169 */         br = new BufferedReader(new InputStreamReader(new FileInputStream(f), this.charSet));
/*     */       }
/*     */       else {
/* 172 */         br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
/*     */       }
/*     */ 
/* 176 */       if (this.tailLines <= 0) {
/* 177 */         int i = 0;
/* 178 */         while ((line = br.readLine()) != null) {
/* 179 */           if (line.startsWith("#")) {
/*     */             continue;
/*     */           }
/* 182 */           ++i;
/* 183 */           if (i <= this.skipLines) {
/*     */             continue;
/*     */           }
/* 186 */           tmpMap = accumulate2HashMap(this.cols, line);
/* 187 */           if (tmpMap.size() == 0) {
/*     */             continue;
/*     */           }
/* 190 */           list.add(tmpMap);
/*     */         }
/*     */       } else {
/* 193 */         tmpList = new LinkedList();
/* 194 */         while ((line = br.readLine()) != null) {
/* 195 */           if (line.startsWith("#")) {
/*     */             continue;
/*     */           }
/*     */ 
/* 199 */           if (tmpList.size() == this.tailLines) {
/* 200 */             tmpList.poll();
/*     */           }
/* 202 */           tmpList.offer(line);
/*     */         }
/* 204 */         while (!(tmpList.isEmpty())) {
/* 205 */           line = (String)tmpList.poll();
/*     */ 
/* 207 */           tmpMap = accumulate2HashMap(this.cols, line);
/* 208 */           list.add(tmpMap);
/*     */         }
/*     */       }
/* 211 */       br.close();
/* 212 */       br = null;
/* 213 */       LinkedList tmpList = list;
/*     */ 
/* 224 */       return tmpList;
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*     */     }
/*     */     finally
/*     */     {
/* 217 */       if (br != null)
/*     */         try {
/* 219 */           br.close();
/*     */         } catch (IOException e) {
/* 221 */           e.printStackTrace();
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   public int doStartTag()
/*     */     throws JspException
/*     */   {
/* 229 */     super.doStartTag();
/* 230 */     return 1;
/*     */   }
/*     */ 
/*     */   public int getSkipLines()
/*     */   {
/* 237 */     return this.skipLines;
/*     */   }
/*     */ 
/*     */   public void setSkipLines(int skipLines)
/*     */   {
/* 245 */     this.skipLines = skipLines;
/*     */   }
/*     */ 
/*     */   public String getTailLines()
/*     */   {
/* 252 */     return String.valueOf(this.tailLines);
/*     */   }
/*     */ 
/*     */   public void setTailLines(String tailLines)
/*     */     throws Exception
/*     */   {
/* 260 */     tailLines = (String)ExpressionEvaluatorManager.evaluate("tailLines", tailLines, Object.class, this, this.pageContext);
/*     */ 
/* 262 */     this.tailLines = NumberUtils.toInt(tailLines);
/*     */   }
/*     */ 
/*     */   public static void accumulate(JSONObject obj, String str)
/*     */   {
/*     */     JSONArray array;
/*     */     int j;
/*     */     JSONObject tmpObj;
/* 266 */     if (obj.containsKey("separator"))
/*     */     {
/* 268 */       String[] tmps = StringUtils.split(str, obj.getString("separator"));
/* 269 */       if (tmps.length == 0) {
/* 270 */         return;
/*     */       }
/* 272 */       array = obj.getJSONArray("cols");
/* 273 */       for (j = 0; j < array.size(); ++j) {
/* 274 */         tmpObj = array.getJSONObject(j);
/* 275 */         if (j >= tmps.length)
/* 276 */           obj.accumulate("value", "");
/*     */         else
/* 278 */           obj.accumulate("value", tmps[j]);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 283 */       int offset = 0;
/* 284 */       array = obj.getJSONArray("cols");
/* 285 */       for (j = 0; j < array.size(); ++j) {
/* 286 */         tmpObj = array.getJSONObject(j);
/* 287 */         if (obj.containsKey("offset")) {
/* 288 */           offset = obj.getInt("offset");
/*     */         }
/* 290 */         if (offset + obj.getInt("length") >= str.length())
/* 291 */           obj.accumulate("value", "");
/*     */         else {
/* 293 */           obj.accumulate("value", str.substring(offset, obj.getInt("length")));
/*     */         }
/* 295 */         offset += obj.getInt("length");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static HashMap accumulate2HashMap(JSONObject obj, String str) {
/* 301 */     accumulate(obj, str);
/* 302 */     JSONArray array = obj.getJSONArray("cols");
/* 303 */     HashMap map = new HashMap();
/* 304 */     for (int i = 0; i < array.size(); ++i) {
/* 305 */       JSONObject tmpObj = (JSONObject)array.get(i);
/* 306 */       if (tmpObj.containsKey("value"))
/* 307 */         map.put(tmpObj.get("name"), tmpObj.get("value"));
/*     */       else {
/* 309 */         map.put(tmpObj.get("name"), "");
/*     */       }
/*     */     }
/* 312 */     return map;
/*     */   }
/*     */ }