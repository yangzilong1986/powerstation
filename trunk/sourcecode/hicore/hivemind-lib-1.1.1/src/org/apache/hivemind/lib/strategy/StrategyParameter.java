 package org.apache.hivemind.lib.strategy;
 
 import java.util.List;
 import org.apache.hivemind.impl.BaseLocatable;
 
 public class StrategyParameter extends BaseLocatable
 {
   private List _contributions;
 
   public List getContributions()
   {
     return this._contributions;
   }
 
   public void setContributions(List configuration)
   {
     this._contributions = configuration;
   }
 }