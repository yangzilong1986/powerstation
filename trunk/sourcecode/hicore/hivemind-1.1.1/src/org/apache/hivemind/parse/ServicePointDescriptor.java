 package org.apache.hivemind.parse;
 
 import org.apache.hivemind.Occurances;
 import org.apache.hivemind.internal.Visibility;
 import org.apache.hivemind.schema.impl.SchemaImpl;
 import org.apache.hivemind.util.ToStringBuilder;
 
 public final class ServicePointDescriptor extends AbstractServiceDescriptor
 {
   private String _id;
   private String _interfaceClassName;
   private SchemaImpl _parametersSchema;
   private String _parametersSchemaId;
   private Occurances _parametersCount;
   private Visibility _visibility;
 
   public ServicePointDescriptor()
   {
     this._parametersCount = Occurances.REQUIRED;
 
     this._visibility = Visibility.PUBLIC;
   }
 
   public String getId() {
     return this._id;
   }
 
   public String getInterfaceClassName()
   {
     return this._interfaceClassName;
   }
 
   public void setId(String string)
   {
     this._id = string;
   }
 
   public void setInterfaceClassName(String string)
   {
     this._interfaceClassName = string;
   }
 
   protected void extendDescription(ToStringBuilder builder)
   {
     builder.append("id", this._id);
     builder.append("interfaceClassName", this._interfaceClassName);
     builder.append("parametersSchema", this._parametersSchema);
     builder.append("parametersSchemaId", this._parametersSchemaId);
     builder.append("parametersCount", this._parametersCount);
     builder.append("visibility", this._visibility);
   }
 
   public SchemaImpl getParametersSchema()
   {
     return this._parametersSchema;
   }
 
   public void setParametersSchema(SchemaImpl schema)
   {
     this._parametersSchema = schema;
   }
 
   public String getParametersSchemaId()
   {
     return this._parametersSchemaId;
   }
 
   public void setParametersSchemaId(String schemaId)
   {
     this._parametersSchemaId = schemaId;
   }
 
   public Occurances getParametersCount()
   {
     return this._parametersCount;
   }
 
   public void setParametersCount(Occurances occurances)
   {
     this._parametersCount = occurances;
   }
 
   public Visibility getVisibility()
   {
     return this._visibility;
   }
 
   public void setVisibility(Visibility visibility)
   {
     this._visibility = visibility;
   }
 }