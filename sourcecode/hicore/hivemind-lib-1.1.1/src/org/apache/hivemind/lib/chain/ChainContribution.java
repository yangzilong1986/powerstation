 package org.apache.hivemind.lib.chain;
 
 public class ChainContribution
 {
   private String _id;
   private String _before;
   private String _after;
   private Object _command;
 
   public String getAfter()
   {
     return this._after;
   }
 
   public void setAfter(String after)
   {
     this._after = after;
   }
 
   public String getBefore()
   {
     return this._before;
   }
 
   public void setBefore(String before)
   {
     this._before = before;
   }
 
   public Object getCommand()
   {
     return this._command;
   }
 
   public void setCommand(Object command)
   {
     this._command = command;
   }
 
   public String getId()
   {
     return this._id;
   }
 
   public void setId(String id)
   {
     this._id = id;
   }
 }