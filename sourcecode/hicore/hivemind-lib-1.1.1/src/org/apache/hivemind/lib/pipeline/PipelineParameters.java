 package org.apache.hivemind.lib.pipeline;
 
 import java.util.List;
 import org.apache.hivemind.impl.BaseLocatable;
 
 public class PipelineParameters extends BaseLocatable
 {
   private Class _filterInterface;
   private Object _terminator;
   private List _pipelineConfiguration;
 
   public Class getFilterInterface()
   {
     return this._filterInterface;
   }
 
   public Object getTerminator()
   {
     return this._terminator;
   }
 
   public void setFilterInterface(Class class1)
   {
     this._filterInterface = class1;
   }
 
   public void setTerminator(Object object)
   {
     this._terminator = object;
   }
 
   public List getPipelineConfiguration()
   {
     return this._pipelineConfiguration;
   }
 
   public void setPipelineConfiguration(List list)
   {
     this._pipelineConfiguration = list;
   }
 }