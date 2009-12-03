/*     */ package com.hisun.atc.rpt.xml;
/*     */ 
/*     */ import com.hisun.atc.rpt.HiDataFile;
/*     */ import com.hisun.atc.rpt.HiDataRecord;
/*     */ import com.hisun.atc.rpt.HiReportConstants;
/*     */ import com.hisun.atc.rpt.HiRptContext;
/*     */ import com.hisun.atc.rpt.data.RecordReader;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.xml.Located;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class HiFormatDefineNode extends Located
/*     */   implements HiReportConstants
/*     */ {
/*     */   int pagerows;
/*  30 */   boolean bpagesum = false;
/*     */   ArrayList formatNodes;
/*     */   private int _rec_num;
/*     */   private Logger log;
/*     */ 
/*     */   public HiFormatDefineNode()
/*     */   {
/*  41 */     this.formatNodes = new ArrayList(9);
/*  42 */     for (int i = 0; i < 9; ++i) {
/*  43 */       this.formatNodes.add(null);
/*     */     }
/*  45 */     this.formatNodes.set(4, new HashMap());
/*     */   }
/*     */ 
/*     */   public void addSubTotal(HiFormatNode fmtnode) {
/*  49 */     Map map = (Map)this.formatNodes.get(4);
/*  50 */     map.put(new Integer(fmtnode.fmtseq), fmtnode);
/*     */   }
/*     */ 
/*     */   public void setPageFooter(HiFormatNode fmtnode) {
/*  54 */     this.formatNodes.set(8, fmtnode);
/*     */   }
/*     */ 
/*     */   public void setReportFooter(HiFormatNode fmtnode) {
/*  58 */     this.formatNodes.set(7, fmtnode);
/*     */   }
/*     */ 
/*     */   public void setTableFooter(HiFormatNode fmtnode) {
/*  62 */     this.formatNodes.set(6, fmtnode);
/*     */   }
/*     */ 
/*     */   public void setSummed(HiFormatNode fmtnode) {
/*  66 */     this.formatNodes.set(5, fmtnode);
/*     */   }
/*     */ 
/*     */   public void setIterative(HiFormatNode fmtnode) {
/*  70 */     this.formatNodes.set(3, fmtnode);
/*     */   }
/*     */ 
/*     */   public void setTableHeader(HiFormatNode fmtnode) {
/*  74 */     this.formatNodes.set(2, fmtnode);
/*     */   }
/*     */ 
/*     */   public void setReportHeader(HiFormatNode fmtnode) {
/*  78 */     this.formatNodes.set(1, fmtnode);
/*     */   }
/*     */ 
/*     */   public void setPageHeader(HiFormatNode fmtnode) {
/*  82 */     this.formatNodes.set(0, fmtnode);
/*     */   }
/*     */ 
/*     */   public void process(HiRptContext ctx, HiDataFile datafile, String outputfile)
/*     */   {
/*     */     OutputStream out;
/*  86 */     Map vars = ctx.vars;
/*  87 */     this.log = ctx.logger;
/*  88 */     ctx.info("开始处理FormatDefine节点");
/*     */ 
/*  90 */     initVars(vars);
/*  91 */     if (this.bpagesum) {
/*  92 */       ctx.info("开始计算总页数");
/*  93 */       format(ctx, datafile.getReader(), null);
/*  94 */       int pagesum = ((Integer)vars.get("PAGENUM")).intValue() - 1;
/*  95 */       vars.put("PAGESUM", new Integer(pagesum));
/*  96 */       vars.put("PAGENUM", new Integer(1));
/*  97 */       ctx.info("总页数：" + pagesum);
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 102 */       out = new FileOutputStream(outputfile);
/*     */     } catch (FileNotFoundException e) {
/* 104 */       ctx.error("输出文件无法打开:" + outputfile, e);
/* 105 */       ctx.runtimeException(e);
/* 106 */       return;
/*     */     }
/*     */ 
/* 109 */     ctx.info("开始生成报表文件：" + outputfile);
/* 110 */     format(ctx, datafile.getReader(), out);
/* 111 */     ctx.info("FormatDefine节点处理完毕");
/*     */   }
/*     */ 
/*     */   private void format(HiRptContext ctx, RecordReader datafile, OutputStream out) {
/* 115 */     Map vars = ctx.vars;
/* 116 */     int iLastIdx = 8;
/* 117 */     int iPageLine = 0;
/* 118 */     boolean iFlag = true;
/*     */ 
/* 120 */     HiDataRecord rec = null;
/*     */ 
/* 122 */     rec = readValidRecord(datafile);
/* 123 */     if (rec == null) {
/* 124 */       datafile.close();
/* 125 */       return;
/*     */     }
/*     */     try
/*     */     {
/*     */       while (true)
/*     */       {
/*     */         int iRet;
/* 131 */         if (out != null) {
/* 132 */           ctx.info(rec.toString());
/*     */         }
/*     */ 
/* 135 */         vars.putAll(rec.getVars());
/*     */ 
/* 138 */         HiDataRecord nextrec = readValidRecord(datafile);
/*     */ 
/* 142 */         if ((iLastIdx == 8) && (rec.type != 8)) {
/* 143 */           iPageLine = 0;
/*     */ 
/* 145 */           iRet = fullFront(ctx, out, 0, rec.type, iFlag, vars);
/* 146 */           if (iRet < 0) {
/* 147 */             out.close();
/* 148 */             return;
/*     */           }
/* 150 */           iPageLine += iRet;
/*     */         }
/*     */         else {
/* 153 */           iRet = fullFront(ctx, out, iLastIdx, rec.type, iFlag, vars);
/* 154 */           if (iRet < 0) {
/* 155 */             out.close();
/* 156 */             return;
/*     */           }
/* 158 */           iPageLine += iRet;
/*     */         }
/*     */ 
/* 161 */         int iCurIdx = rec.type;
/* 162 */         int iNextIdx = -99;
/* 163 */         if (nextrec != null)
/* 164 */           iNextIdx = nextrec.type;
/* 165 */         int iTotalLine = this.pagerows;
/*     */ 
/* 167 */         switch (iCurIdx)
/*     */         {
/*     */         case 0:
/*     */         case 2:
/*     */         case 5:
/*     */         case 6:
/* 172 */           iRet = dealRecord(ctx, out, iCurIdx, rec.seq, vars);
/* 173 */           if (iRet < 0) {
/* 174 */             out.close();
/* 175 */             return;
/*     */           }
/* 177 */           iPageLine += iRet;
/* 178 */           iLastIdx = iCurIdx;
/*     */ 
/* 180 */           if (iCurIdx != 0)
/*     */           {
/* 182 */             iFlag = false;
/*     */           }
/*     */ 
/* 185 */           if (iNextIdx < iCurIdx) {
/* 186 */             if (iCurIdx == 0)
/*     */             {
/* 189 */               iRet = dealRecord(ctx, out, 1, 0, vars);
/* 190 */               if (iRet < 0) {
/* 191 */                 out.close();
/* 192 */                 return;
/*     */               }
/*     */             }
/*     */ 
/* 196 */             iFlag = true;
/*     */ 
/* 198 */             iRet = fullEnd(ctx, out, iCurIdx, iFlag, vars);
/* 199 */             if (iRet < 0) {
/* 200 */               out.close();
/* 201 */               return;
/*     */             }
/* 203 */             iPageLine += iRet;
/* 204 */             iLastIdx = 8; }
/* 205 */           break;
/*     */         case 1:
/*     */         case 7:
/* 209 */           iRet = dealRecord(ctx, out, iCurIdx, rec.seq, vars);
/* 210 */           if (iRet < 0) {
/* 211 */             out.close();
/* 212 */             return;
/*     */           }
/* 214 */           iPageLine += iRet;
/* 215 */           iLastIdx = iCurIdx;
/* 216 */           iFlag = false;
/*     */ 
/* 219 */           if (iNextIdx <= iCurIdx)
/*     */           {
/* 222 */             iFlag = true;
/*     */ 
/* 224 */             iRet = fullEnd(ctx, out, iCurIdx, iFlag, vars);
/* 225 */             if (iRet < 0) {
/* 226 */               out.close();
/* 227 */               return;
/*     */             }
/* 229 */             iPageLine += iRet;
/* 230 */             iLastIdx = 8; }
/* 231 */           break;
/*     */         case 3:
/* 235 */           increaseRec_Num(vars);
/* 236 */           iRet = dealRecord(ctx, out, iCurIdx, rec.seq, vars);
/* 237 */           if (iRet < 0) {
/* 238 */             out.close();
/* 239 */             return;
/*     */           }
/* 241 */           iPageLine += iRet;
/* 242 */           iLastIdx = iCurIdx;
/* 243 */           iFlag = false;
/*     */ 
/* 246 */           if ((iNextIdx < iCurIdx) || ((iPageLine >= iTotalLine) && (iTotalLine > 0)))
/*     */           {
/* 248 */             if (iNextIdx < iCurIdx) {
/* 249 */               iFlag = true;
/*     */             }
/*     */ 
/* 252 */             iRet = fullEnd(ctx, out, iCurIdx, iFlag, vars);
/* 253 */             if (iRet < 0) {
/* 254 */               out.close();
/* 255 */               return;
/*     */             }
/* 257 */             iPageLine += iRet;
/* 258 */             iLastIdx = 8; }
/* 259 */           break;
/*     */         case 4:
/* 265 */           iRet = dealRecord(ctx, out, iCurIdx, rec.seq, vars);
/* 266 */           if (iRet < 0) {
/* 267 */             out.close();
/* 268 */             return;
/*     */           }
/* 270 */           iPageLine += iRet;
/* 271 */           iLastIdx = iCurIdx;
/* 272 */           iFlag = false;
/*     */ 
/* 275 */           if ((iNextIdx < 3) || ((iPageLine >= iTotalLine) && (iTotalLine > 0)))
/*     */           {
/* 277 */             if (iNextIdx < 3) {
/* 278 */               iFlag = true;
/*     */             }
/*     */ 
/* 281 */             iRet = fullEnd(ctx, out, iCurIdx, iFlag, vars);
/* 282 */             if (iRet < 0) {
/* 283 */               out.close();
/* 284 */               return;
/*     */             }
/* 286 */             iPageLine += iRet;
/* 287 */             iLastIdx = 8; }
/* 288 */           break;
/*     */         case 8:
/* 292 */           if (iNextIdx < iCurIdx) {
/* 293 */             iRet = dealRecord(ctx, out, 7, 0, vars);
/* 294 */             if (iRet < 0) {
/* 295 */               out.close();
/* 296 */               return;
/*     */             }
/* 298 */             iPageLine += iRet;
/*     */           }
/*     */ 
/* 301 */           iRet = dealRecord(ctx, out, iCurIdx, rec.seq, vars);
/* 302 */           if (iRet < 0) {
/* 303 */             out.close();
/* 304 */             return;
/*     */           }
/* 306 */           iPageLine += iRet;
/* 307 */           iLastIdx = iCurIdx;
/* 308 */           iFlag = false;
/*     */ 
/* 310 */           if (iNextIdx < iCurIdx) {
/* 311 */             iFlag = true;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 318 */         if (nextrec == null) {
/*     */           break;
/*     */         }
/* 321 */         rec = nextrec;
/* 322 */         iCurIdx = iNextIdx;
/*     */       }
/*     */     } catch (IOException e) {
/* 325 */       ctx.error("格式化文件时出错", e);
/* 326 */       ctx.runtimeException(e);
/*     */     }
/*     */ 
/* 329 */     datafile.close();
/*     */   }
/*     */ 
/*     */   private void increaseRec_Num(Map vars) {
/* 333 */     this._rec_num += 1;
/*     */ 
/* 335 */     vars.put("RECNUM", new Integer(this._rec_num));
/*     */   }
/*     */ 
/*     */   private int fullFront(HiRptContext ctx, OutputStream out, int iLastIdx, int iCurIdx, boolean iFlag, Map vars)
/*     */   {
/* 340 */     int iPageLine = 0;
/* 341 */     int i = 0;
/* 342 */     int iRet = 0;
/*     */ 
/* 344 */     for (i = (iLastIdx >= 0) ? iLastIdx : 0; i < iCurIdx; ++i)
/*     */     {
/* 346 */       if ((i == 3) || (i == 4) || (i == 5)) continue; if (i == 8)
/*     */       {
/*     */         continue;
/*     */       }
/*     */ 
/* 352 */       if (i == 1) {
/* 353 */         if (iFlag) {
/* 354 */           iRet = dealRecord(ctx, out, i, 0, vars);
/* 355 */           if (iRet < 0) {
/* 356 */             return -1;
/*     */           }
/* 358 */           iPageLine += iRet;
/*     */         }
/*     */       } else {
/* 361 */         iRet = dealRecord(ctx, out, i, 0, vars);
/* 362 */         if (iRet < 0) {
/* 363 */           return -1;
/*     */         }
/* 365 */         iPageLine += iRet;
/*     */       }
/*     */     }
/*     */ 
/* 369 */     return iPageLine;
/*     */   }
/*     */ 
/*     */   private int fullEnd(HiRptContext ctx, OutputStream out, int iCurIdx, boolean iFlag, Map vars) {
/* 373 */     int iPageLine = 0;
/* 374 */     int i = 0;
/* 375 */     int iRet = 0;
/*     */ 
/* 377 */     for (i = iCurIdx + 1; i <= 8; ++i)
/*     */     {
/* 379 */       if ((i == 3) || (i == 4) || (i == 5)) continue; if (i == 1)
/*     */       {
/*     */         continue;
/*     */       }
/*     */ 
/* 385 */       if (i == 7) {
/* 386 */         if (iFlag) {
/* 387 */           iRet = dealRecord(ctx, out, i, 0, vars);
/* 388 */           if (iRet < 0) {
/* 389 */             return -1;
/*     */           }
/* 391 */           iPageLine += iRet;
/*     */         }
/*     */       } else {
/* 394 */         iRet = dealRecord(ctx, out, i, 0, vars);
/* 395 */         if (iRet < 0) {
/* 396 */           return -1;
/*     */         }
/* 398 */         iPageLine += iRet;
/*     */       }
/*     */     }
/*     */ 
/* 402 */     return iPageLine;
/*     */   }
/*     */ 
/*     */   int dealRecord(HiRptContext ctx, OutputStream out, int indicator, int iFmtSeq, Map vars)
/*     */   {
/* 415 */     HiFormatNode fmtnode = getFormatNode(indicator, iFmtSeq);
/* 416 */     if (fmtnode == null)
/*     */     {
/* 418 */       ctx.info("没有配置格式:" + indicator + ":" + iFmtSeq);
/* 419 */       if ((indicator == 8) && (this.pagerows != 0)) {
/* 420 */         newPage(out, vars);
/*     */       }
/* 422 */       return 0;
/*     */     }
/*     */ 
/* 425 */     if (out != null) {
/* 426 */       String fmtstr = fmtnode.format(ctx, vars);
/*     */       try {
/* 428 */         out.write(fmtstr.getBytes());
/* 429 */         out.write(10);
/*     */       } catch (IOException e) {
/* 431 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */ 
/* 435 */     if ((indicator == 8) && (this.pagerows != 0)) {
/* 436 */       newPage(out, vars);
/*     */     }
/*     */ 
/* 439 */     return fmtnode.line;
/*     */   }
/*     */ 
/*     */   private void newPage(OutputStream out, Map vars)
/*     */   {
/* 444 */     if (out != null) {
/*     */       try {
/* 446 */         out.write(12);
/*     */       } catch (IOException e) {
/* 448 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */ 
/* 452 */     addPageNum(vars);
/*     */   }
/*     */ 
/*     */   private void addPageNum(Map vars)
/*     */   {
/* 457 */     Integer num = (Integer)vars.get("PAGENUM");
/* 458 */     int inum = num.intValue() + 1;
/* 459 */     vars.put("PAGENUM", new Integer(inum));
/*     */   }
/*     */ 
/*     */   private HiDataRecord readValidRecord(RecordReader datafile)
/*     */   {
/* 469 */     HiDataRecord rec = null;
/*     */     do {
/* 471 */       rec = datafile.readRecord();
/* 472 */       if (rec == null)
/*     */       {
/* 474 */         return null;
/*     */       }
/*     */ 
/* 477 */       if (rec.type < 0)
/* 478 */         return null; 
/*     */     }
/* 479 */     while (rec.type == 99);
/*     */ 
/* 482 */     return rec;
/*     */   }
/*     */ 
/*     */   public HiFormatNode getFormatNode(int type, int seq)
/*     */   {
/* 495 */     HiFormatNode fmtnode = null;
/* 496 */     if (type != 4) {
/* 497 */       fmtnode = (HiFormatNode)this.formatNodes.get(type);
/*     */     } else {
/* 499 */       Map map = (Map)this.formatNodes.get(type);
/* 500 */       fmtnode = (HiFormatNode)map.get(new Integer(seq));
/*     */     }
/* 502 */     if (fmtnode == null);
/* 507 */     return fmtnode;
/*     */   }
/*     */ 
/*     */   public void initVars(Map vars)
/*     */   {
/* 517 */     vars.put("PAGEROWS", new Integer(this.pagerows));
/*     */ 
/* 519 */     vars.put("PAGENUM", new Integer(1));
/*     */ 
/* 521 */     vars.put("PAGESUM", new Integer(1));
/*     */ 
/* 523 */     this._rec_num = 0;
/* 524 */     vars.put("RECNUM", new Integer(this._rec_num));
/*     */   }
/*     */ 
/*     */   public void setPagerows(int pagerows) {
/* 528 */     this.pagerows = pagerows;
/*     */   }
/*     */ 
/*     */   public void setPagesum(String pagesum) {
/* 532 */     this.bpagesum = pagesum.equalsIgnoreCase("YES");
/*     */   }
/*     */ }