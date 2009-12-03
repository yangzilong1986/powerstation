/*     */ package com.hisun.atc.rpt.xml;
/*     */ 
/*     */ import com.hisun.atc.rpt.HiRptContext;
/*     */ import com.hisun.atc.rpt.HiRptExp;
/*     */ import com.hisun.hiexpression.HiExpFactory;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.xml.Located;
/*     */ import com.hisun.xml.Location;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class HiFormatNode extends Located
/*     */ {
/*     */   private static final int MAX_PARAMS = 50;
/*     */   private static final char PLACEHOLD = 35;
/*     */   String name;
/*     */   int type;
/*     */   int line;
/*     */   int fmtseq;
/*     */   List params;
/*     */   String format;
/*     */   private HiMsgConvertNode convertor;
/*     */ 
/*     */   public HiFormatNode()
/*     */   {
/*  32 */     this.params = new ArrayList();
/*     */   }
/*     */ 
/*     */   public void addParam(FormatParam para)
/*     */   {
/*  40 */     if (para.label - 1 != this.params.size()) {
/*  41 */       throw new RuntimeException("参数节点序号不连续:" + para.label + ",location:" + getLocation().toString());
/*     */     }
/*     */ 
/*  44 */     para.buildExp(this.convertor);
/*     */ 
/*  46 */     this.params.add(para);
/*     */   }
/*     */ 
/*     */   public void setFormat(String format) {
/*  50 */     this.format = cutFirstEndLine(format);
/*     */   }
/*     */ 
/*     */   public void setConvertor(HiMsgConvertNode convertor) {
/*  54 */     this.convertor = convertor;
/*     */   }
/*     */ 
/*     */   private String cutFirstEndLine(String format)
/*     */   {
/*  63 */     int si = format.indexOf("\n");
/*  64 */     int ei = format.lastIndexOf("\n");
/*  65 */     if (si == -1)
/*  66 */       return format;
/*  67 */     if (si == ei) {
/*  68 */       return format.substring(si + 1);
/*     */     }
/*  70 */     return format.substring(si + 1, ei);
/*     */   }
/*     */ 
/*     */   public String format(HiRptContext ctx, Map vars) {
/*  74 */     StringBuffer ret = new StringBuffer(this.format.length() * 2);
/*  75 */     int vindex = 0;
/*  76 */     char[] cbuf = this.format.toCharArray();
/*  77 */     for (int i = 0; i < cbuf.length; ++i) {
/*  78 */       char c = this.format.charAt(i);
/*  79 */       if (cbuf[i] == '#')
/*  80 */         if (vindex >= this.params.size())
/*     */         {
/*  82 */           ctx.warn("Format节点[" + this.name + "]缺少参数:" + (vindex + 1));
/*  83 */           ret.append("#");
/*  84 */           ++i;
/*     */         } else {
/*  86 */           FormatParam para = (FormatParam)this.params.get(vindex++);
/*  87 */           int nspace = para.formatValue(ctx, cbuf, i, ret, vars);
/*  88 */           i += nspace;
/*     */         }
/*     */       else {
/*  91 */         ret.append(c);
/*     */       }
/*     */     }
/*     */ 
/*  95 */     return ret.toString();
/*     */   }
/*     */ 
/*     */   public void setLine(int line) {
/*  99 */     this.line = line;
/*     */   }
/*     */ 
/*     */   public void setFmtseq(int fmtseq) {
/* 103 */     this.fmtseq = fmtseq; }
/*     */ 
/*     */   public static class FormatParam {
/*     */     int label;
/*     */     String alias;
/*     */     String spread;
/*     */     boolean convert;
/*     */     private HiRptExp exp;
/*     */ 
/*     */     public FormatParam() {
/* 113 */       this.convert = false;
/*     */     }
/*     */ 
/*     */     public void buildExp(HiMsgConvertNode msgconvert)
/*     */     {
/* 119 */       String prelex = HiExpFactory.preLex(this.alias);
/* 120 */       this.exp = new HiRptExp(prelex);
/* 121 */       if (this.convert)
/* 122 */         this.exp.setConvert(msgconvert);
/*     */     }
/*     */ 
/*     */     public String getValue(HiRptContext ctx, Map vars) {
/* 126 */       if (this.exp != null)
/*     */         try {
/* 128 */           return this.exp.getValue(vars);
/*     */         } catch (Exception e) {
/* 130 */           ctx.error("Param计算失败:" + this.alias, e);
/* 131 */           ctx.runtimeException(e);
/*     */         }
/* 133 */       return null;
/*     */     }
/*     */ 
/*     */     public int formatValue(HiRptContext ctx, char[] cbuf, int off, StringBuffer ret, Map vars) {
/* 137 */       String value = getValue(ctx, vars);
/*     */ 
/* 140 */       if (value == null) {
/* 141 */         ctx.warn("Param结果为空:" + this.alias);
/* 142 */         ret.append(" ");
/* 143 */         return 1;
/*     */       }
/*     */ 
/* 146 */       if (ctx.logger.isDebugEnabled()) {
/* 147 */         ctx.logger.debug(this.alias + ":" + value);
/*     */       }
/*     */ 
/* 150 */       int vlen = strLen(value);
/* 151 */       int nspace = 0;
/* 152 */       if ((this.spread == null) || (this.spread.equals("right"))) {
/* 153 */         ret.append(value);
/*     */ 
/* 155 */         while ((off + nspace + 1 < cbuf.length) && (cbuf[(off + nspace + 1)] == ' ')) {
/* 156 */           ++nspace;
/*     */         }
/* 158 */         nspace = (nspace < vlen - 1) ? nspace : vlen - 1;
/*     */       }
/*     */       else
/*     */       {
/*     */         int prenspace;
/* 159 */         if (this.spread.equals("mid")) {
/* 160 */           prenspace = 0;
/*     */ 
/* 162 */           while ((off - prenspace - 1 >= 0) && (cbuf[(off - prenspace - 1)] == ' '))
/*     */           {
/* 164 */             ++prenspace;
/*     */           }
/* 166 */           prenspace = (prenspace < (vlen - 1) / 2) ? prenspace : (vlen - 1) / 2;
/*     */ 
/* 168 */           ret.delete(ret.length() - prenspace, ret.length());
/* 169 */           ret.append(value);
/*     */ 
/* 172 */           while ((off + nspace + 1 < cbuf.length) && (cbuf[(off + nspace + 1)] == ' ')) {
/* 173 */             ++nspace;
/*     */           }
/*     */ 
/* 176 */           nspace = (nspace < vlen - 1 - prenspace) ? nspace : vlen - 1 - prenspace;
/*     */         }
/* 178 */         else if (this.spread.equals("left")) {
/* 179 */           prenspace = 0;
/*     */ 
/* 181 */           while ((off - prenspace - 1 >= 0) && (cbuf[(off - prenspace - 1)] == ' '))
/*     */           {
/* 183 */             ++prenspace;
/*     */           }
/* 185 */           prenspace = (prenspace < vlen - 1) ? prenspace : vlen - 1;
/* 186 */           ret.delete(ret.length() - prenspace, ret.length());
/* 187 */           ret.append(value);
/*     */ 
/* 190 */           while ((off + nspace + 1 < cbuf.length) && (cbuf[(off + nspace + 1)] == ' ')) {
/* 191 */             ++nspace;
/*     */           }
/*     */ 
/* 194 */           nspace = (nspace < vlen - 1 - prenspace) ? nspace : vlen - 1 - prenspace;
/*     */         }
/*     */       }
/* 197 */       return nspace;
/*     */     }
/*     */ 
/*     */     private int strLen(String value) {
/* 201 */       int vlen = value.getBytes().length;
/* 202 */       return vlen;
/*     */     }
/*     */ 
/*     */     public void setLabel(int label) {
/* 206 */       this.label = label;
/*     */     }
/*     */ 
/*     */     public void setAlias(String alias) {
/* 210 */       this.alias = alias;
/*     */     }
/*     */ 
/*     */     public void setSpread(String spread) {
/* 214 */       this.spread = spread;
/*     */     }
/*     */ 
/*     */     public void setConvert(String convert) {
/* 218 */       this.convert = convert.equalsIgnoreCase("YES");
/*     */     }
/*     */   }
/*     */ }