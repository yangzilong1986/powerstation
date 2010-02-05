 package org.apache.hivemind.internal.ser;
 
 import java.io.DataInput;
 import java.io.DataOutput;
 import java.io.Externalizable;
 import java.io.IOException;
 import java.io.ObjectInput;
 import java.io.ObjectOutput;
 import org.apache.hivemind.util.Defense;
 
 public class ServiceToken
   implements Externalizable
 {
   private static final long serialVersionUID = 1L;
   private String _serviceId;
 
   public ServiceToken()
   {
   }
 
   public ServiceToken(String serviceId)
   {
     Defense.notNull(serviceId, "serviceId");
 
     this._serviceId = serviceId;
   }
 
   public String getServiceId()
   {
     return this._serviceId;
   }
 
   public void writeExternal(ObjectOutput out) throws IOException
   {
     out.writeUTF(this._serviceId);
   }
 
   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
   {
     this._serviceId = in.readUTF();
   }
 
   Object readResolve()
   {
     return ServiceSerializationHelper.getServiceSerializationSupport().getServiceFromToken(this);
   }
 }