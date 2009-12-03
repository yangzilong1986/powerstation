/*     */ package com.hisun.web.tag;
/*     */ 
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import java.awt.Color;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyTagSupport;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
/*     */ import org.jfree.chart.ChartFactory;
/*     */ import org.jfree.chart.ChartUtilities;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.data.category.DefaultCategoryDataset;
/*     */ import org.jfree.data.general.DefaultPieDataset;
/*     */ 
/*     */ public class HiChartTag extends BodyTagSupport
/*     */ {
/*     */   private String type;
/*     */   private String cols;
/*     */   private String groupName;
/*     */   private String categoryAxisLabel;
/*     */   private String valueAxisLabel;
/*     */   private String title;
/*     */   private String imageFile;
/*     */   private int width;
/*     */   private int height;
/*     */   private Logger log;
/*     */ 
/*     */   public HiChartTag()
/*     */   {
/*  52 */     this.groupName = "GRP";
/*     */ 
/*  73 */     this.width = 400;
/*     */ 
/*  77 */     this.height = 300;
/*  78 */     this.log = HiLog.getLogger("SYS.trc");
/*     */   }
/*     */ 
/*     */   public int doEndTag()
/*     */     throws JspException
/*     */   {
/*     */     String tmpFile;
/*  81 */     JspWriter out = this.pageContext.getOut();
/*  82 */     HashMap root = (HashMap)this.pageContext.getRequest().getAttribute("ETF");
/*     */ 
/*  84 */     if (root == null) {
/*  85 */       throw new JspException("ETF is null");
/*     */     }
/*  87 */     ArrayList grpList = (ArrayList)root.get(this.groupName);
/*  88 */     if (grpList == null) {
/*  89 */       super.doEndTag();
/*  90 */       return 6;
/*     */     }
/*  92 */     ServletContext context = this.pageContext.getServletContext();
/*     */ 
/*  95 */     if (!(this.imageFile.startsWith("/")))
/*  96 */       tmpFile = context.getRealPath("/tmp") + "/" + this.imageFile;
/*     */     else {
/*  98 */       tmpFile = context.getRealPath(this.imageFile);
/*     */     }
/*     */ 
/* 101 */     if ("bar".equals(this.type))
/* 102 */       doBarChart(grpList, tmpFile);
/* 103 */     else if ("pie".equals(this.type))
/* 104 */       doPieChart(grpList, tmpFile);
/* 105 */     else if ("line".equals(this.type)) {
/* 106 */       doLineChart(grpList, tmpFile);
/*     */     }
/* 108 */     super.doEndTag();
/* 109 */     return 6;
/*     */   }
/*     */ 
/*     */   public int doStartTag() throws JspException {
/* 113 */     super.doStartTag();
/* 114 */     return 1;
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/* 121 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(String type)
/*     */     throws JspException
/*     */   {
/* 129 */     if (type == null) {
/* 130 */       this.type = "";
/* 131 */       return;
/*     */     }
/* 133 */     this.type = ((String)ExpressionEvaluatorManager.evaluate("type", type, Object.class, this, this.pageContext));
/*     */   }
/*     */ 
/*     */   public String getCols()
/*     */   {
/* 141 */     return this.cols;
/*     */   }
/*     */ 
/*     */   public void setCols(String cols)
/*     */     throws JspException
/*     */   {
/* 149 */     if (cols == null) {
/* 150 */       this.cols = "";
/* 151 */       return;
/*     */     }
/* 153 */     this.cols = ((String)ExpressionEvaluatorManager.evaluate("cols", cols, Object.class, this, this.pageContext));
/*     */   }
/*     */ 
/*     */   public String getGroupName()
/*     */   {
/* 161 */     return this.groupName;
/*     */   }
/*     */ 
/*     */   public void setGroupName(String groupName)
/*     */     throws JspException
/*     */   {
/* 169 */     if (groupName == null) {
/* 170 */       this.groupName = "";
/* 171 */       return;
/*     */     }
/* 173 */     this.groupName = ((String)ExpressionEvaluatorManager.evaluate("groupName", groupName, Object.class, this, this.pageContext));
/*     */   }
/*     */ 
/*     */   public String getCategoryAxisLabel()
/*     */   {
/* 181 */     return this.categoryAxisLabel;
/*     */   }
/*     */ 
/*     */   public void setCategoryAxisLabel(String categoryAxisLabel)
/*     */     throws JspException
/*     */   {
/* 190 */     if (categoryAxisLabel == null) {
/* 191 */       this.categoryAxisLabel = "";
/* 192 */       return;
/*     */     }
/* 194 */     this.categoryAxisLabel = ((String)ExpressionEvaluatorManager.evaluate("categoryAxisLabel", categoryAxisLabel, Object.class, this, this.pageContext));
/*     */   }
/*     */ 
/*     */   public String getValueAxisLabel()
/*     */   {
/* 203 */     return this.valueAxisLabel;
/*     */   }
/*     */ 
/*     */   public void setValueAxisLabel(String valueAxisLabel)
/*     */     throws JspException
/*     */   {
/* 211 */     if (valueAxisLabel == null) {
/* 212 */       this.valueAxisLabel = "";
/* 213 */       return;
/*     */     }
/* 215 */     this.valueAxisLabel = ((String)ExpressionEvaluatorManager.evaluate("valueAxisLabel", valueAxisLabel, Object.class, this, this.pageContext));
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 224 */     return this.title;
/*     */   }
/*     */ 
/*     */   public void setTitle(String title)
/*     */     throws JspException
/*     */   {
/* 232 */     if (title == null) {
/* 233 */       this.title = "";
/* 234 */       return;
/*     */     }
/* 236 */     this.title = ((String)ExpressionEvaluatorManager.evaluate("title", title, Object.class, this, this.pageContext));
/*     */   }
/*     */ 
/*     */   public void setImageFile(String imageFile) throws JspException
/*     */   {
/* 241 */     if (imageFile == null) {
/* 242 */       this.imageFile = "";
/* 243 */       return;
/*     */     }
/* 245 */     this.imageFile = ((String)ExpressionEvaluatorManager.evaluate("imageFile", imageFile, Object.class, this, this.pageContext));
/*     */   }
/*     */ 
/*     */   public String getImageFile()
/*     */   {
/* 250 */     return this.imageFile;
/*     */   }
/*     */ 
/*     */   private void doLineChart(ArrayList grp, String file) throws JspException {
/*     */   }
/*     */ 
/*     */   private void doPieChart(ArrayList grp, String file) throws JspException {
/* 257 */     DefaultPieDataset dataset = getPieDataSet(grp);
/* 258 */     JFreeChart chart = ChartFactory.createPieChart3D(this.title, dataset, true, false, false);
/*     */ 
/* 264 */     chart.setBackgroundPaint(Color.WHITE);
/* 265 */     FileOutputStream fos_jpg = null;
/*     */     try {
/* 267 */       fos_jpg = new FileOutputStream(file);
/* 268 */       ChartUtilities.writeChartAsPNG(fos_jpg, chart, this.width, this.height, null);
/*     */     }
/*     */     catch (FileNotFoundException e) {
/*     */     }
/*     */     catch (IOException e) {
/*     */     }
/*     */     finally {
/*     */       try {
/* 276 */         if (fos_jpg != null)
/* 277 */           fos_jpg.close();
/*     */       }
/*     */       catch (Exception e) {
/* 280 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private DefaultPieDataset getPieDataSet(ArrayList grp) {
/* 286 */     DefaultPieDataset dataset = new DefaultPieDataset();
/* 287 */     for (int i = 0; i < grp.size(); ++i) {
/* 288 */       HashMap rec = (HashMap)grp.get(i);
/* 289 */       double value = 0.0D;
/* 290 */       String rowKey = null;
/* 291 */       if (this.cols == null) {
/* 292 */         Iterator iter = rec.values().iterator();
/* 293 */         if (!(iter.hasNext())) {
/*     */           continue;
/*     */         }
/* 296 */         value = NumberUtils.toDouble((String)iter.next());
/* 297 */         if (!(iter.hasNext())) {
/*     */           continue;
/*     */         }
/* 300 */         rowKey = (String)iter.next();
/*     */       } else {
/* 302 */         String[] tmps = this.cols.split("\\|");
/* 303 */         if (tmps.length != 2) {
/*     */           continue;
/*     */         }
/* 306 */         value = NumberUtils.toDouble((String)rec.get(tmps[0]));
/* 307 */         rowKey = (String)rec.get(tmps[1]);
/*     */       }
/* 309 */       dataset.setValue(rowKey, value);
/*     */     }
/* 311 */     return dataset;
/*     */   }
/*     */ 
/*     */   private void doBarChart(ArrayList grp, String file) throws JspException {
/* 315 */     CategoryDataset dataset = getBarDataSet(grp);
/* 316 */     JFreeChart chart = ChartFactory.createBarChart3D(this.title, this.categoryAxisLabel, this.valueAxisLabel, dataset, PlotOrientation.VERTICAL, true, false, false);
/*     */ 
/* 325 */     chart.setBackgroundPaint(Color.WHITE);
/* 326 */     FileOutputStream fos_jpg = null;
/*     */     try {
/* 328 */       fos_jpg = new FileOutputStream(file);
/* 329 */       ChartUtilities.writeChartAsPNG(fos_jpg, chart, this.width, this.height, null);
/*     */     }
/*     */     catch (FileNotFoundException e) {
/*     */     }
/*     */     catch (IOException e) {
/*     */     }
/*     */     finally {
/*     */       try {
/* 337 */         if (fos_jpg != null)
/* 338 */           fos_jpg.close();
/*     */       }
/*     */       catch (Exception e) {
/* 341 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private CategoryDataset getBarDataSet(ArrayList grp) {
/* 347 */     DefaultCategoryDataset dataset = new DefaultCategoryDataset();
/* 348 */     for (int i = 0; i < grp.size(); ++i) {
/* 349 */       HashMap rec = (HashMap)grp.get(i);
/* 350 */       double value = 0.0D;
/* 351 */       String rowKey = null; String colKey = null;
/* 352 */       if (this.cols == null) {
/* 353 */         Iterator iter = rec.values().iterator();
/* 354 */         if (!(iter.hasNext())) {
/*     */           continue;
/*     */         }
/* 357 */         value = NumberUtils.toDouble((String)iter.next());
/* 358 */         if (!(iter.hasNext())) {
/*     */           continue;
/*     */         }
/* 361 */         rowKey = (String)iter.next();
/* 362 */         if (!(iter.hasNext())) {
/* 363 */           colKey = rowKey;
/* 364 */           rowKey = null;
/*     */         } else {
/* 366 */           colKey = (String)iter.next();
/*     */         }
/*     */       } else {
/* 369 */         String[] tmps = this.cols.split("\\|");
/* 370 */         int j = 0;
/* 371 */         if (j >= tmps.length) {
/*     */           continue;
/*     */         }
/* 374 */         value = NumberUtils.toDouble((String)rec.get(tmps[j]));
/* 375 */         ++j;
/* 376 */         if (j >= tmps.length) {
/*     */           continue;
/*     */         }
/* 379 */         rowKey = (String)rec.get(tmps[j]);
/* 380 */         ++j;
/* 381 */         if (j >= tmps.length) {
/* 382 */           colKey = rowKey;
/* 383 */           rowKey = null;
/*     */         } else {
/* 385 */           colKey = (String)rec.get(tmps[j]);
/*     */         }
/*     */       }
/* 388 */       dataset.addValue(value, rowKey, colKey);
/*     */     }
/* 390 */     return dataset;
/*     */   }
/*     */ }