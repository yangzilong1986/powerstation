 package org.apache.hivemind;
 
 public class ApplicationRuntimeException extends RuntimeException
   implements Locatable
 {
   private static final long serialVersionUID = 1L;
   private Throwable _rootCause;
   private transient Location _location;
   private transient Object _component;
 
   public ApplicationRuntimeException(Throwable rootCause)
   {
     this(rootCause.getMessage(), rootCause);
   }
 
   public ApplicationRuntimeException(String message)
   {
     this(message, null, null, null);
   }
 
   public ApplicationRuntimeException(String message, Throwable rootCause)
   {
     this(message, null, null, rootCause);
   }
 
   public ApplicationRuntimeException(String message, Object component, Location location, Throwable rootCause)
   {
     super(message);
 
     this._rootCause = rootCause;
     this._component = component;
 
     this._location = HiveMind.findLocation(new Object[] { location, rootCause, component });
   }
 
   public ApplicationRuntimeException(String message, Location location, Throwable rootCause)
   {
     this(message, null, location, rootCause);
   }
 
   public Throwable getRootCause()
   {
     return this._rootCause;
   }
 
   public Location getLocation()
   {
     return this._location;
   }
 
   public Object getComponent()
   {
     return this._component;
   }
 
   public Throwable getCause()
   {
     return this._rootCause;
   }
 
   public String toString()
   {
     if (this._location == null) {
       return super.toString();
     }
     StringBuffer buffer = new StringBuffer(super.toString());
     buffer.append(" [");
     buffer.append(this._location);
     buffer.append("]");
 
     return buffer.toString();
   }
 }