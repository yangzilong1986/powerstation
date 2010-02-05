 package org.apache.hivemind.lib.pipeline;
 
 import org.apache.hivemind.impl.BaseLocatable;
 
 public class FilterContribution extends BaseLocatable
   implements PipelineContribution
 {
   private String _name;
   private Object _filter;
   private String _before;
   private String _after;
 
   public void informAssembler(PipelineAssembler pa)
   {
     pa.addFilter(this._name, this._after, this._before, this._filter, super.getLocation());
   }
 
   public void setAfter(String string)
   {
     this._after = string;
   }
 
   public void setBefore(String string)
   {
     this._before = string;
   }
 
   public void setFilter(Object object)
   {
     this._filter = object;
   }
 
   public void setName(String string)
   {
     this._name = string;
   }
 }