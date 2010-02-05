 package org.apache.hivemind;
 
 public class SymbolSourceContribution
   implements Orderable
 {
   private String _name;
   private String _precedingNames;
   private String _followingNames;
   private SymbolSource _source;
 
   public SymbolSource getSource()
   {
     return this._source;
   }
 
   public void setSource(SymbolSource source)
   {
     this._source = source;
   }
 
   public String getFollowingNames()
   {
     return this._followingNames;
   }
 
   public String getName()
   {
     return this._name;
   }
 
   public String getPrecedingNames()
   {
     return this._precedingNames;
   }
 
   public void setFollowingNames(String string)
   {
     this._followingNames = string;
   }
 
   public void setName(String string)
   {
     this._name = string;
   }
 
   public void setPrecedingNames(String string)
   {
     this._precedingNames = string;
   }
 }