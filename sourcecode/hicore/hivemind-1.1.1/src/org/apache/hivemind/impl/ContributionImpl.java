 package org.apache.hivemind.impl;
 
 import java.util.ArrayList;
 import java.util.Collections;
 import java.util.List;
 import org.apache.hivemind.internal.Contribution;
 import org.apache.hivemind.internal.Module;
 
 public final class ContributionImpl
   implements Contribution
 {
   private Module _contributingModule;
   private List _elements;
 
   public Module getContributingModule()
   {
     return this._contributingModule;
   }
 
   public void setContributingModule(Module module)
   {
     this._contributingModule = module;
   }
 
   public void addElements(List elements)
   {
     if (this._elements == null)
       this._elements = new ArrayList(elements);
     else
       this._elements.addAll(elements);
   }
 
   public List getElements()
   {
     if (this._elements == null) {
       return Collections.EMPTY_LIST;
     }
     return this._elements;
   }
 }