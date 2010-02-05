 package org.apache.hivemind.lib.pipeline;
 
 import org.apache.hivemind.impl.BaseLocatable;
 
 public class TerminatorContribution extends BaseLocatable
   implements PipelineContribution
 {
   private Object _terminator;
 
   public void informAssembler(PipelineAssembler pa)
   {
     pa.setTerminator(this._terminator, super.getLocation());
   }
 
   public void setTerminator(Object object)
   {
     this._terminator = object;
   }
 }