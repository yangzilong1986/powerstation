 package org.apache.hivemind.parse;
 
 import org.apache.hivemind.Occurances;
 import org.apache.hivemind.internal.Visibility;
 import org.apache.hivemind.schema.impl.SchemaImpl;
 import org.apache.hivemind.util.ToStringBuilder;
 
 public final class ConfigurationPointDescriptor extends BaseAnnotationHolder
 {
   private String _id;
   private Occurances _count;
   private SchemaImpl _contributionsSchema;
   private String _contributionsSchemaId;
   private Visibility _visibility;
 
   public ConfigurationPointDescriptor()
   {
     this._count = Occurances.UNBOUNDED;
 
     this._visibility = Visibility.PUBLIC;
   }
 
   public String toString() {
     ToStringBuilder builder = new ToStringBuilder(this);
 
     builder.append("id", this._id);
     builder.append("count", this._count);
     builder.append("contributionsSchema", this._contributionsSchema);
     builder.append("contributionsSchemaId", this._contributionsSchemaId);
     builder.append("visibility", this._visibility);
 
     return builder.toString();
   }
 
   public Occurances getCount()
   {
     return this._count;
   }
 
   public void setCount(Occurances occurances)
   {
     this._count = occurances;
   }
 
   public String getId()
   {
     return this._id;
   }
 
   public void setId(String string)
   {
     this._id = string;
   }
 
   public SchemaImpl getContributionsSchema()
   {
     return this._contributionsSchema;
   }
 
   public void setContributionsSchema(SchemaImpl schema)
   {
     this._contributionsSchema = schema;
   }
 
   public String getContributionsSchemaId()
   {
     return this._contributionsSchemaId;
   }
 
   public void setContributionsSchemaId(String schemaId)
   {
     this._contributionsSchemaId = schemaId;
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