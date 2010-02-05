 package com.hisun.atc.rpt.xml;
 
 import com.hisun.atc.rpt.HiRptContext;
 import com.hisun.atc.rpt.HiRptExp;
 import com.hisun.hiexpression.HiExpFactory;
 import com.hisun.hilog4j.Logger;
 import com.hisun.xml.Located;
 import com.hisun.xml.Location;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 
 public class HiFormatNode extends Located
 {
   private static final int MAX_PARAMS = 50;
   private static final char PLACEHOLD = 35;
   String name;
   int type;
   int line;
   int fmtseq;
   List params;
   String format;
   private HiMsgConvertNode convertor;
 
   public HiFormatNode()
   {
     this.params = new ArrayList();
   }
 
   public void addParam(FormatParam para)
   {
     if (para.label - 1 != this.params.size()) {
       throw new RuntimeException("参数节点序号不连续:" + para.label + ",location:" + getLocation().toString());
     }
 
     para.buildExp(this.convertor);
 
     this.params.add(para);
   }
 
   public void setFormat(String format) {
     this.format = cutFirstEndLine(format);
   }
 
   public void setConvertor(HiMsgConvertNode convertor) {
     this.convertor = convertor;
   }
 
   private String cutFirstEndLine(String format)
   {
     int si = format.indexOf("\n");
     int ei = format.lastIndexOf("\n");
     if (si == -1)
       return format;
     if (si == ei) {
       return format.substring(si + 1);
     }
     return format.substring(si + 1, ei);
   }
 
   public String format(HiRptContext ctx, Map vars) {
     StringBuffer ret = new StringBuffer(this.format.length() * 2);
     int vindex = 0;
     char[] cbuf = this.format.toCharArray();
     for (int i = 0; i < cbuf.length; ++i) {
       char c = this.format.charAt(i);
       if (cbuf[i] == '#')
         if (vindex >= this.params.size())
         {
           ctx.warn("Format节点[" + this.name + "]缺少参数:" + (vindex + 1));
           ret.append("#");
           ++i;
         } else {
           FormatParam para = (FormatParam)this.params.get(vindex++);
           int nspace = para.formatValue(ctx, cbuf, i, ret, vars);
           i += nspace;
         }
       else {
         ret.append(c);
       }
     }
 
     return ret.toString();
   }
 
   public void setLine(int line) {
     this.line = line;
   }
 
   public void setFmtseq(int fmtseq) {
     this.fmtseq = fmtseq; }
 
   public static class FormatParam {
     int label;
     String alias;
     String spread;
     boolean convert;
     private HiRptExp exp;
 
     public FormatParam() {
       this.convert = false;
     }
 
     public void buildExp(HiMsgConvertNode msgconvert)
     {
       String prelex = HiExpFactory.preLex(this.alias);
       this.exp = new HiRptExp(prelex);
       if (this.convert)
         this.exp.setConvert(msgconvert);
     }
 
     public String getValue(HiRptContext ctx, Map vars) {
       if (this.exp != null)
         try {
           return this.exp.getValue(vars);
         } catch (Exception e) {
           ctx.error("Param计算失败:" + this.alias, e);
           ctx.runtimeException(e);
         }
       return null;
     }
 
     public int formatValue(HiRptContext ctx, char[] cbuf, int off, StringBuffer ret, Map vars) {
       String value = getValue(ctx, vars);
 
       if (value == null) {
         ctx.warn("Param结果为空:" + this.alias);
         ret.append(" ");
         return 1;
       }
 
       if (ctx.logger.isDebugEnabled()) {
         ctx.logger.debug(this.alias + ":" + value);
       }
 
       int vlen = strLen(value);
       int nspace = 0;
       if ((this.spread == null) || (this.spread.equals("right"))) {
         ret.append(value);
 
         while ((off + nspace + 1 < cbuf.length) && (cbuf[(off + nspace + 1)] == ' ')) {
           ++nspace;
         }
         nspace = (nspace < vlen - 1) ? nspace : vlen - 1;
       }
       else
       {
         int prenspace;
         if (this.spread.equals("mid")) {
           prenspace = 0;
 
           while ((off - prenspace - 1 >= 0) && (cbuf[(off - prenspace - 1)] == ' '))
           {
             ++prenspace;
           }
           prenspace = (prenspace < (vlen - 1) / 2) ? prenspace : (vlen - 1) / 2;
 
           ret.delete(ret.length() - prenspace, ret.length());
           ret.append(value);
 
           while ((off + nspace + 1 < cbuf.length) && (cbuf[(off + nspace + 1)] == ' ')) {
             ++nspace;
           }
 
           nspace = (nspace < vlen - 1 - prenspace) ? nspace : vlen - 1 - prenspace;
         }
         else if (this.spread.equals("left")) {
           prenspace = 0;
 
           while ((off - prenspace - 1 >= 0) && (cbuf[(off - prenspace - 1)] == ' '))
           {
             ++prenspace;
           }
           prenspace = (prenspace < vlen - 1) ? prenspace : vlen - 1;
           ret.delete(ret.length() - prenspace, ret.length());
           ret.append(value);
 
           while ((off + nspace + 1 < cbuf.length) && (cbuf[(off + nspace + 1)] == ' ')) {
             ++nspace;
           }
 
           nspace = (nspace < vlen - 1 - prenspace) ? nspace : vlen - 1 - prenspace;
         }
       }
       return nspace;
     }
 
     private int strLen(String value) {
       int vlen = value.getBytes().length;
       return vlen;
     }
 
     public void setLabel(int label) {
       this.label = label;
     }
 
     public void setAlias(String alias) {
       this.alias = alias;
     }
 
     public void setSpread(String spread) {
       this.spread = spread;
     }
 
     public void setConvert(String convert) {
       this.convert = convert.equalsIgnoreCase("YES");
     }
   }
 }