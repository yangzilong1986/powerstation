 package org.apache.hivemind.conditional;
 
 import org.apache.hivemind.util.Defense;
 
 class Token
 {
   private TokenType _type;
   private String _value;
 
   Token(TokenType type)
   {
     this(type, null);
   }
 
   Token(TokenType type, String value)
   {
     Defense.notNull(type, "type");
 
     this._type = type;
     this._value = value;
   }
 
   public TokenType getType()
   {
     return this._type;
   }
 
   public String getValue()
   {
     return this._value;
   }
 
   public String toString()
   {
     StringBuffer buffer = new StringBuffer("<");
 
     buffer.append(this._type);
 
     if (this._value != null)
     {
       buffer.append("(");
       buffer.append(this._value);
       buffer.append(")");
     }
 
     buffer.append(">");
 
     return buffer.toString();
   }
 }