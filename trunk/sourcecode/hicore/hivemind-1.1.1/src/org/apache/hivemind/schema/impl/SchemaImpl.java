 package org.apache.hivemind.schema.impl;
 
 import java.util.ArrayList;
 import java.util.Collections;
 import java.util.Iterator;
 import java.util.List;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.Locatable;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.internal.Visibility;
 import org.apache.hivemind.parse.BaseAnnotationHolder;
 import org.apache.hivemind.schema.AttributeModel;
 import org.apache.hivemind.schema.ElementModel;
 import org.apache.hivemind.schema.Schema;
 
 public class SchemaImpl extends BaseAnnotationHolder
   implements Schema
 {
   private List _elementModels;
   private List _shareableElementModels;
   private Visibility _visibility;
   private Module _module;
   private String _id;
 
   public SchemaImpl()
   {
     this._visibility = Visibility.PUBLIC;
   }
 
   public String getModuleId()
   {
     return this._module.getModuleId();
   }
 
   public String getId()
   {
     return this._id;
   }
 
   public Visibility getVisibility()
   {
     return this._visibility;
   }
 
   public boolean visibleToModule(String moduleId)
   {
     if (this._visibility == Visibility.PUBLIC) {
       return true;
     }
     return getModuleId().equals(moduleId);
   }
 
   public void addElementModel(ElementModel model)
   {
     if (this._elementModels == null) {
       this._elementModels = new ArrayList();
     }
     this._elementModels.add(model);
     this._shareableElementModels = null;
   }
 
   public List getElementModel()
   {
     if (this._shareableElementModels == null) {
       this._shareableElementModels = ((this._elementModels == null) ? Collections.EMPTY_LIST : Collections.unmodifiableList(this._elementModels));
     }
 
     return this._shareableElementModels;
   }
 
   public boolean canInstancesBeKeyed()
   {
     boolean emptyModel = (this._elementModels == null) || (this._elementModels.isEmpty());
 
     if (emptyModel) {
       return false;
     }
     for (Iterator i = this._elementModels.iterator(); i.hasNext(); )
     {
       ElementModel model = (ElementModel)i.next();
 
       if (model.getKeyAttribute() == null) {
         return false;
       }
     }
     return true;
   }
 
   public void validateKeyAttributes()
   {
     if (this._elementModels == null) {
       return;
     }
     for (Iterator i = this._elementModels.iterator(); i.hasNext(); )
     {
       ElementModel em = (ElementModel)i.next();
 
       String key = em.getKeyAttribute();
 
       if (key == null) {
         continue;
       }
       AttributeModel keyAm = em.getAttributeModel(key);
 
       if (keyAm == null)
         throw new ApplicationRuntimeException("Key attribute '" + key + "' of element '" + em.getElementName() + "' never declared.", em.getLocation(), null);
     }
   }
 
   public void setVisibility(Visibility visibility)
   {
     this._visibility = visibility;
   }
 
   public void setModule(Module module)
   {
     this._module = module;
   }
 
   public void setId(String id)
   {
     this._id = id;
   }
 
   public Module getDefiningModule()
   {
     return this._module;
   }
 }