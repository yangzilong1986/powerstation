 package com.hisun.atc.rpt.xml;
 
 import com.hisun.atc.rpt.HiRptContext;
 import com.hisun.xml.Located;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 
 public class HiVarDefNode extends Located
 {
   List vars;
 
   public HiVarDefNode()
   {
     this.vars = new ArrayList(); }
 
   public void addVarDef(VarDefNode v) {
     this.vars.add(v);
   }
 
   public void init(HiRptContext ctx)
   {
     Iterator it = this.vars.iterator();
     while (it.hasNext()) {
       VarDefNode node = (VarDefNode)it.next();
       String alias = node.alias;
       String value = null;
       if (node.pos != -1)
       {
         value = ctx.getValueByPos(node.pos);
       } else if (node.name != null)
       {
         value = ctx.getValueByName(node.name.substring(1));
       }
       if (value != null)
       {
         ctx.vars.put(alias.toUpperCase(), value);
         ctx.info("VarDef [" + alias + ":" + value + "]");
       } else {
         ctx.warn("VarDef [" + alias + "," + node.pos + "," + node.name + "] has no value!");
       }
     }
   }
 
   public static class VarDefNode
   {
     String alias;
     String name;
     int pos;
 
     public VarDefNode()
     {
       this.pos = -1; }
 
     public void setAlias(String alias) {
       this.alias = alias;
     }
 
     public void setName(String name) {
       this.name = name;
     }
 
     public void setPos(int pos) {
       this.pos = pos;
     }
   }
 }