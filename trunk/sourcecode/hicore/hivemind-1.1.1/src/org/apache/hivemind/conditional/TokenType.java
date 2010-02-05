 package org.apache.hivemind.conditional;
 
 class TokenType
 {
   static final TokenType OPAREN = new TokenType("OPAREN");
 
   static final TokenType CPAREN = new TokenType("CPAREN");
 
   static final TokenType AND = new TokenType("AND");
 
   static final TokenType OR = new TokenType("OR");
 
   static final TokenType NOT = new TokenType("NOT");
 
   static final TokenType PROPERTY = new TokenType("PROPERTY");
 
   static final TokenType CLASS = new TokenType("CLASS");
 
   static final TokenType SYMBOL = new TokenType("SYMBOL");
   private String _name;
 
   private TokenType(String name)
   {
     this._name = name;
   }
 
   public String toString()
   {
     return this._name;
   }
 }