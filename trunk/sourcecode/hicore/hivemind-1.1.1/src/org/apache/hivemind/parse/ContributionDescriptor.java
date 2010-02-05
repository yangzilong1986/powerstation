 package org.apache.hivemind.parse;
 
 import java.util.ArrayList;
 import java.util.Collections;
 import java.util.List;
 import org.apache.hivemind.Element;
 import org.apache.hivemind.util.ToStringBuilder;
 
 public final class ContributionDescriptor extends BaseAnnotationHolder
 {
   private String _configurationId;
   private List _elements;
   private String _conditionalExpression;
 
   public String getConfigurationId()
   {
     return this._configurationId;
   }
 
   public void setConfigurationId(String string)
   {
     this._configurationId = string;
   }
 
   public String toString()
   {
     ToStringBuilder builder = new ToStringBuilder(this);
     builder.append("configurationId", this._configurationId);
     builder.append("conditionalExpression", this._conditionalExpression);
 
     return builder.toString();
   }
 
   public void addElement(Element element)
   {
     if (this._elements == null) {
       this._elements = new ArrayList();
     }
     this._elements.add(element);
   }
 
   public List getElements()
   {
     if (this._elements == null) {
       return Collections.EMPTY_LIST;
     }
     return this._elements;
   }
 
   public String getConditionalExpression()
   {
     return this._conditionalExpression;
   }
 
   public void setConditionalExpression(String conditionalExpression)
   {
     this._conditionalExpression = conditionalExpression;
   }
 }