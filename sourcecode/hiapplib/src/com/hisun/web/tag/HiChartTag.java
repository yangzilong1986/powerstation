 package com.hisun.web.tag;
 
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import java.awt.Color;
 import java.io.FileNotFoundException;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.util.ArrayList;
 import java.util.Collection;
 import java.util.HashMap;
 import java.util.Iterator;
 import javax.servlet.ServletContext;
 import javax.servlet.ServletRequest;
 import javax.servlet.jsp.JspException;
 import javax.servlet.jsp.JspWriter;
 import javax.servlet.jsp.PageContext;
 import javax.servlet.jsp.tagext.BodyTagSupport;
 import org.apache.commons.lang.math.NumberUtils;
 import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
 import org.jfree.chart.ChartFactory;
 import org.jfree.chart.ChartUtilities;
 import org.jfree.chart.JFreeChart;
 import org.jfree.chart.plot.PlotOrientation;
 import org.jfree.data.category.CategoryDataset;
 import org.jfree.data.category.DefaultCategoryDataset;
 import org.jfree.data.general.DefaultPieDataset;
 
 public class HiChartTag extends BodyTagSupport
 {
   private String type;
   private String cols;
   private String groupName;
   private String categoryAxisLabel;
   private String valueAxisLabel;
   private String title;
   private String imageFile;
   private int width;
   private int height;
   private Logger log;
 
   public HiChartTag()
   {
     this.groupName = "GRP";
 
     this.width = 400;
 
     this.height = 300;
     this.log = HiLog.getLogger("SYS.trc");
   }
 
   public int doEndTag()
     throws JspException
   {
     String tmpFile;
     JspWriter out = this.pageContext.getOut();
     HashMap root = (HashMap)this.pageContext.getRequest().getAttribute("ETF");
 
     if (root == null) {
       throw new JspException("ETF is null");
     }
     ArrayList grpList = (ArrayList)root.get(this.groupName);
     if (grpList == null) {
       super.doEndTag();
       return 6;
     }
     ServletContext context = this.pageContext.getServletContext();
 
     if (!(this.imageFile.startsWith("/")))
       tmpFile = context.getRealPath("/tmp") + "/" + this.imageFile;
     else {
       tmpFile = context.getRealPath(this.imageFile);
     }
 
     if ("bar".equals(this.type))
       doBarChart(grpList, tmpFile);
     else if ("pie".equals(this.type))
       doPieChart(grpList, tmpFile);
     else if ("line".equals(this.type)) {
       doLineChart(grpList, tmpFile);
     }
     super.doEndTag();
     return 6;
   }
 
   public int doStartTag() throws JspException {
     super.doStartTag();
     return 1;
   }
 
   public String getType()
   {
     return this.type;
   }
 
   public void setType(String type)
     throws JspException
   {
     if (type == null) {
       this.type = "";
       return;
     }
     this.type = ((String)ExpressionEvaluatorManager.evaluate("type", type, Object.class, this, this.pageContext));
   }
 
   public String getCols()
   {
     return this.cols;
   }
 
   public void setCols(String cols)
     throws JspException
   {
     if (cols == null) {
       this.cols = "";
       return;
     }
     this.cols = ((String)ExpressionEvaluatorManager.evaluate("cols", cols, Object.class, this, this.pageContext));
   }
 
   public String getGroupName()
   {
     return this.groupName;
   }
 
   public void setGroupName(String groupName)
     throws JspException
   {
     if (groupName == null) {
       this.groupName = "";
       return;
     }
     this.groupName = ((String)ExpressionEvaluatorManager.evaluate("groupName", groupName, Object.class, this, this.pageContext));
   }
 
   public String getCategoryAxisLabel()
   {
     return this.categoryAxisLabel;
   }
 
   public void setCategoryAxisLabel(String categoryAxisLabel)
     throws JspException
   {
     if (categoryAxisLabel == null) {
       this.categoryAxisLabel = "";
       return;
     }
     this.categoryAxisLabel = ((String)ExpressionEvaluatorManager.evaluate("categoryAxisLabel", categoryAxisLabel, Object.class, this, this.pageContext));
   }
 
   public String getValueAxisLabel()
   {
     return this.valueAxisLabel;
   }
 
   public void setValueAxisLabel(String valueAxisLabel)
     throws JspException
   {
     if (valueAxisLabel == null) {
       this.valueAxisLabel = "";
       return;
     }
     this.valueAxisLabel = ((String)ExpressionEvaluatorManager.evaluate("valueAxisLabel", valueAxisLabel, Object.class, this, this.pageContext));
   }
 
   public String getTitle()
   {
     return this.title;
   }
 
   public void setTitle(String title)
     throws JspException
   {
     if (title == null) {
       this.title = "";
       return;
     }
     this.title = ((String)ExpressionEvaluatorManager.evaluate("title", title, Object.class, this, this.pageContext));
   }
 
   public void setImageFile(String imageFile) throws JspException
   {
     if (imageFile == null) {
       this.imageFile = "";
       return;
     }
     this.imageFile = ((String)ExpressionEvaluatorManager.evaluate("imageFile", imageFile, Object.class, this, this.pageContext));
   }
 
   public String getImageFile()
   {
     return this.imageFile;
   }
 
   private void doLineChart(ArrayList grp, String file) throws JspException {
   }
 
   private void doPieChart(ArrayList grp, String file) throws JspException {
     DefaultPieDataset dataset = getPieDataSet(grp);
     JFreeChart chart = ChartFactory.createPieChart3D(this.title, dataset, true, false, false);
 
     chart.setBackgroundPaint(Color.WHITE);
     FileOutputStream fos_jpg = null;
     try {
       fos_jpg = new FileOutputStream(file);
       ChartUtilities.writeChartAsPNG(fos_jpg, chart, this.width, this.height, null);
     }
     catch (FileNotFoundException e) {
     }
     catch (IOException e) {
     }
     finally {
       try {
         if (fos_jpg != null)
           fos_jpg.close();
       }
       catch (Exception e) {
         e.printStackTrace();
       }
     }
   }
 
   private DefaultPieDataset getPieDataSet(ArrayList grp) {
     DefaultPieDataset dataset = new DefaultPieDataset();
     for (int i = 0; i < grp.size(); ++i) {
       HashMap rec = (HashMap)grp.get(i);
       double value = 0.0D;
       String rowKey = null;
       if (this.cols == null) {
         Iterator iter = rec.values().iterator();
         if (!(iter.hasNext())) {
           continue;
         }
         value = NumberUtils.toDouble((String)iter.next());
         if (!(iter.hasNext())) {
           continue;
         }
         rowKey = (String)iter.next();
       } else {
         String[] tmps = this.cols.split("\\|");
         if (tmps.length != 2) {
           continue;
         }
         value = NumberUtils.toDouble((String)rec.get(tmps[0]));
         rowKey = (String)rec.get(tmps[1]);
       }
       dataset.setValue(rowKey, value);
     }
     return dataset;
   }
 
   private void doBarChart(ArrayList grp, String file) throws JspException {
     CategoryDataset dataset = getBarDataSet(grp);
     JFreeChart chart = ChartFactory.createBarChart3D(this.title, this.categoryAxisLabel, this.valueAxisLabel, dataset, PlotOrientation.VERTICAL, true, false, false);
 
     chart.setBackgroundPaint(Color.WHITE);
     FileOutputStream fos_jpg = null;
     try {
       fos_jpg = new FileOutputStream(file);
       ChartUtilities.writeChartAsPNG(fos_jpg, chart, this.width, this.height, null);
     }
     catch (FileNotFoundException e) {
     }
     catch (IOException e) {
     }
     finally {
       try {
         if (fos_jpg != null)
           fos_jpg.close();
       }
       catch (Exception e) {
         e.printStackTrace();
       }
     }
   }
 
   private CategoryDataset getBarDataSet(ArrayList grp) {
     DefaultCategoryDataset dataset = new DefaultCategoryDataset();
     for (int i = 0; i < grp.size(); ++i) {
       HashMap rec = (HashMap)grp.get(i);
       double value = 0.0D;
       String rowKey = null; String colKey = null;
       if (this.cols == null) {
         Iterator iter = rec.values().iterator();
         if (!(iter.hasNext())) {
           continue;
         }
         value = NumberUtils.toDouble((String)iter.next());
         if (!(iter.hasNext())) {
           continue;
         }
         rowKey = (String)iter.next();
         if (!(iter.hasNext())) {
           colKey = rowKey;
           rowKey = null;
         } else {
           colKey = (String)iter.next();
         }
       } else {
         String[] tmps = this.cols.split("\\|");
         int j = 0;
         if (j >= tmps.length) {
           continue;
         }
         value = NumberUtils.toDouble((String)rec.get(tmps[j]));
         ++j;
         if (j >= tmps.length) {
           continue;
         }
         rowKey = (String)rec.get(tmps[j]);
         ++j;
         if (j >= tmps.length) {
           colKey = rowKey;
           rowKey = null;
         } else {
           colKey = (String)rec.get(tmps[j]);
         }
       }
       dataset.addValue(value, rowKey, colKey);
     }
     return dataset;
   }
 }