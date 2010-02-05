 package org.apache.hivemind.parse;
 
 import org.apache.hivemind.impl.BaseLocatable;
 
 public abstract class BaseAnnotationHolder extends BaseLocatable
   implements AnnotationHolder
 {
   private String _annotation;
 
   public String getAnnotation()
   {
     return this._annotation;
   }
 
   public void setAnnotation(String annotation)
   {
     this._annotation = annotation;
   }
 }