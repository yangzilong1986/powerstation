 package com.hisun.atc.rpt.xml;
 
 import com.hisun.atc.rpt.HiDataFile;
 import com.hisun.atc.rpt.HiDataRecord;
 import com.hisun.atc.rpt.HiReportConstants;
 import com.hisun.atc.rpt.HiRptContext;
 import com.hisun.atc.rpt.data.RecordReader;
 import com.hisun.hilog4j.Logger;
 import com.hisun.xml.Located;
 import java.io.FileNotFoundException;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Map;
 
 public class HiFormatDefineNode extends Located
   implements HiReportConstants
 {
   int pagerows;
   boolean bpagesum = false;
   ArrayList formatNodes;
   private int _rec_num;
   private Logger log;
 
   public HiFormatDefineNode()
   {
     this.formatNodes = new ArrayList(9);
     for (int i = 0; i < 9; ++i) {
       this.formatNodes.add(null);
     }
     this.formatNodes.set(4, new HashMap());
   }
 
   public void addSubTotal(HiFormatNode fmtnode) {
     Map map = (Map)this.formatNodes.get(4);
     map.put(new Integer(fmtnode.fmtseq), fmtnode);
   }
 
   public void setPageFooter(HiFormatNode fmtnode) {
     this.formatNodes.set(8, fmtnode);
   }
 
   public void setReportFooter(HiFormatNode fmtnode) {
     this.formatNodes.set(7, fmtnode);
   }
 
   public void setTableFooter(HiFormatNode fmtnode) {
     this.formatNodes.set(6, fmtnode);
   }
 
   public void setSummed(HiFormatNode fmtnode) {
     this.formatNodes.set(5, fmtnode);
   }
 
   public void setIterative(HiFormatNode fmtnode) {
     this.formatNodes.set(3, fmtnode);
   }
 
   public void setTableHeader(HiFormatNode fmtnode) {
     this.formatNodes.set(2, fmtnode);
   }
 
   public void setReportHeader(HiFormatNode fmtnode) {
     this.formatNodes.set(1, fmtnode);
   }
 
   public void setPageHeader(HiFormatNode fmtnode) {
     this.formatNodes.set(0, fmtnode);
   }
 
   public void process(HiRptContext ctx, HiDataFile datafile, String outputfile)
   {
     OutputStream out;
     Map vars = ctx.vars;
     this.log = ctx.logger;
     ctx.info("开始处理FormatDefine节点");
 
     initVars(vars);
     if (this.bpagesum) {
       ctx.info("开始计算总页数");
       format(ctx, datafile.getReader(), null);
       int pagesum = ((Integer)vars.get("PAGENUM")).intValue() - 1;
       vars.put("PAGESUM", new Integer(pagesum));
       vars.put("PAGENUM", new Integer(1));
       ctx.info("总页数：" + pagesum);
     }
 
     try
     {
       out = new FileOutputStream(outputfile);
     } catch (FileNotFoundException e) {
       ctx.error("输出文件无法打开:" + outputfile, e);
       ctx.runtimeException(e);
       return;
     }
 
     ctx.info("开始生成报表文件：" + outputfile);
     format(ctx, datafile.getReader(), out);
     ctx.info("FormatDefine节点处理完毕");
   }
 
   private void format(HiRptContext ctx, RecordReader datafile, OutputStream out) {
     Map vars = ctx.vars;
     int iLastIdx = 8;
     int iPageLine = 0;
     boolean iFlag = true;
 
     HiDataRecord rec = null;
 
     rec = readValidRecord(datafile);
     if (rec == null) {
       datafile.close();
       return;
     }
     try
     {
       while (true)
       {
         int iRet;
         if (out != null) {
           ctx.info(rec.toString());
         }
 
         vars.putAll(rec.getVars());
 
         HiDataRecord nextrec = readValidRecord(datafile);
 
         if ((iLastIdx == 8) && (rec.type != 8)) {
           iPageLine = 0;
 
           iRet = fullFront(ctx, out, 0, rec.type, iFlag, vars);
           if (iRet < 0) {
             out.close();
             return;
           }
           iPageLine += iRet;
         }
         else {
           iRet = fullFront(ctx, out, iLastIdx, rec.type, iFlag, vars);
           if (iRet < 0) {
             out.close();
             return;
           }
           iPageLine += iRet;
         }
 
         int iCurIdx = rec.type;
         int iNextIdx = -99;
         if (nextrec != null)
           iNextIdx = nextrec.type;
         int iTotalLine = this.pagerows;
 
         switch (iCurIdx)
         {
         case 0:
         case 2:
         case 5:
         case 6:
           iRet = dealRecord(ctx, out, iCurIdx, rec.seq, vars);
           if (iRet < 0) {
             out.close();
             return;
           }
           iPageLine += iRet;
           iLastIdx = iCurIdx;
 
           if (iCurIdx != 0)
           {
             iFlag = false;
           }
 
           if (iNextIdx < iCurIdx) {
             if (iCurIdx == 0)
             {
               iRet = dealRecord(ctx, out, 1, 0, vars);
               if (iRet < 0) {
                 out.close();
                 return;
               }
             }
 
             iFlag = true;
 
             iRet = fullEnd(ctx, out, iCurIdx, iFlag, vars);
             if (iRet < 0) {
               out.close();
               return;
             }
             iPageLine += iRet;
             iLastIdx = 8; }
           break;
         case 1:
         case 7:
           iRet = dealRecord(ctx, out, iCurIdx, rec.seq, vars);
           if (iRet < 0) {
             out.close();
             return;
           }
           iPageLine += iRet;
           iLastIdx = iCurIdx;
           iFlag = false;
 
           if (iNextIdx <= iCurIdx)
           {
             iFlag = true;
 
             iRet = fullEnd(ctx, out, iCurIdx, iFlag, vars);
             if (iRet < 0) {
               out.close();
               return;
             }
             iPageLine += iRet;
             iLastIdx = 8; }
           break;
         case 3:
           increaseRec_Num(vars);
           iRet = dealRecord(ctx, out, iCurIdx, rec.seq, vars);
           if (iRet < 0) {
             out.close();
             return;
           }
           iPageLine += iRet;
           iLastIdx = iCurIdx;
           iFlag = false;
 
           if ((iNextIdx < iCurIdx) || ((iPageLine >= iTotalLine) && (iTotalLine > 0)))
           {
             if (iNextIdx < iCurIdx) {
               iFlag = true;
             }
 
             iRet = fullEnd(ctx, out, iCurIdx, iFlag, vars);
             if (iRet < 0) {
               out.close();
               return;
             }
             iPageLine += iRet;
             iLastIdx = 8; }
           break;
         case 4:
           iRet = dealRecord(ctx, out, iCurIdx, rec.seq, vars);
           if (iRet < 0) {
             out.close();
             return;
           }
           iPageLine += iRet;
           iLastIdx = iCurIdx;
           iFlag = false;
 
           if ((iNextIdx < 3) || ((iPageLine >= iTotalLine) && (iTotalLine > 0)))
           {
             if (iNextIdx < 3) {
               iFlag = true;
             }
 
             iRet = fullEnd(ctx, out, iCurIdx, iFlag, vars);
             if (iRet < 0) {
               out.close();
               return;
             }
             iPageLine += iRet;
             iLastIdx = 8; }
           break;
         case 8:
           if (iNextIdx < iCurIdx) {
             iRet = dealRecord(ctx, out, 7, 0, vars);
             if (iRet < 0) {
               out.close();
               return;
             }
             iPageLine += iRet;
           }
 
           iRet = dealRecord(ctx, out, iCurIdx, rec.seq, vars);
           if (iRet < 0) {
             out.close();
             return;
           }
           iPageLine += iRet;
           iLastIdx = iCurIdx;
           iFlag = false;
 
           if (iNextIdx < iCurIdx) {
             iFlag = true;
           }
 
         }
 
         if (nextrec == null) {
           break;
         }
         rec = nextrec;
         iCurIdx = iNextIdx;
       }
     } catch (IOException e) {
       ctx.error("格式化文件时出错", e);
       ctx.runtimeException(e);
     }
 
     datafile.close();
   }
 
   private void increaseRec_Num(Map vars) {
     this._rec_num += 1;
 
     vars.put("RECNUM", new Integer(this._rec_num));
   }
 
   private int fullFront(HiRptContext ctx, OutputStream out, int iLastIdx, int iCurIdx, boolean iFlag, Map vars)
   {
     int iPageLine = 0;
     int i = 0;
     int iRet = 0;
 
     for (i = (iLastIdx >= 0) ? iLastIdx : 0; i < iCurIdx; ++i)
     {
       if ((i == 3) || (i == 4) || (i == 5)) continue; if (i == 8)
       {
         continue;
       }
 
       if (i == 1) {
         if (iFlag) {
           iRet = dealRecord(ctx, out, i, 0, vars);
           if (iRet < 0) {
             return -1;
           }
           iPageLine += iRet;
         }
       } else {
         iRet = dealRecord(ctx, out, i, 0, vars);
         if (iRet < 0) {
           return -1;
         }
         iPageLine += iRet;
       }
     }
 
     return iPageLine;
   }
 
   private int fullEnd(HiRptContext ctx, OutputStream out, int iCurIdx, boolean iFlag, Map vars) {
     int iPageLine = 0;
     int i = 0;
     int iRet = 0;
 
     for (i = iCurIdx + 1; i <= 8; ++i)
     {
       if ((i == 3) || (i == 4) || (i == 5)) continue; if (i == 1)
       {
         continue;
       }
 
       if (i == 7) {
         if (iFlag) {
           iRet = dealRecord(ctx, out, i, 0, vars);
           if (iRet < 0) {
             return -1;
           }
           iPageLine += iRet;
         }
       } else {
         iRet = dealRecord(ctx, out, i, 0, vars);
         if (iRet < 0) {
           return -1;
         }
         iPageLine += iRet;
       }
     }
 
     return iPageLine;
   }
 
   int dealRecord(HiRptContext ctx, OutputStream out, int indicator, int iFmtSeq, Map vars)
   {
     HiFormatNode fmtnode = getFormatNode(indicator, iFmtSeq);
     if (fmtnode == null)
     {
       ctx.info("没有配置格式:" + indicator + ":" + iFmtSeq);
       if ((indicator == 8) && (this.pagerows != 0)) {
         newPage(out, vars);
       }
       return 0;
     }
 
     if (out != null) {
       String fmtstr = fmtnode.format(ctx, vars);
       try {
         out.write(fmtstr.getBytes());
         out.write(10);
       } catch (IOException e) {
         e.printStackTrace();
       }
     }
 
     if ((indicator == 8) && (this.pagerows != 0)) {
       newPage(out, vars);
     }
 
     return fmtnode.line;
   }
 
   private void newPage(OutputStream out, Map vars)
   {
     if (out != null) {
       try {
         out.write(12);
       } catch (IOException e) {
         e.printStackTrace();
       }
     }
 
     addPageNum(vars);
   }
 
   private void addPageNum(Map vars)
   {
     Integer num = (Integer)vars.get("PAGENUM");
     int inum = num.intValue() + 1;
     vars.put("PAGENUM", new Integer(inum));
   }
 
   private HiDataRecord readValidRecord(RecordReader datafile)
   {
     HiDataRecord rec = null;
     do {
       rec = datafile.readRecord();
       if (rec == null)
       {
         return null;
       }
 
       if (rec.type < 0)
         return null; 
     }
     while (rec.type == 99);
 
     return rec;
   }
 
   public HiFormatNode getFormatNode(int type, int seq)
   {
     HiFormatNode fmtnode = null;
     if (type != 4) {
       fmtnode = (HiFormatNode)this.formatNodes.get(type);
     } else {
       Map map = (Map)this.formatNodes.get(type);
       fmtnode = (HiFormatNode)map.get(new Integer(seq));
     }
     if (fmtnode == null);
     return fmtnode;
   }
 
   public void initVars(Map vars)
   {
     vars.put("PAGEROWS", new Integer(this.pagerows));
 
     vars.put("PAGENUM", new Integer(1));
 
     vars.put("PAGESUM", new Integer(1));
 
     this._rec_num = 0;
     vars.put("RECNUM", new Integer(this._rec_num));
   }
 
   public void setPagerows(int pagerows) {
     this.pagerows = pagerows;
   }
 
   public void setPagesum(String pagesum) {
     this.bpagesum = pagesum.equalsIgnoreCase("YES");
   }
 }