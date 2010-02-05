 package org.apache.hivemind.conditional;
 
 import org.apache.hivemind.util.Defense;
 
 class Lexer
 {
   private char[] _input;
   private int _cursor = 0;
 
   private static final Token OPAREN = new Token(TokenType.OPAREN);
 
   private static final Token CPAREN = new Token(TokenType.CPAREN);
 
   private static final Token AND = new Token(TokenType.AND);
 
   private static final Token OR = new Token(TokenType.OR);
 
   private static final Token NOT = new Token(TokenType.NOT);
 
   private static final Token PROPERTY = new Token(TokenType.PROPERTY);
 
   private static final Token CLASS = new Token(TokenType.CLASS);
 
   Lexer(String input)
   {
     Defense.notNull(input, "input");
 
     this._input = input.toCharArray();
   }
 
   Token next()
   {
     char ch;
     while (true)
     {
       if (this._cursor >= this._input.length)
         break label114;
       ch = this._input[this._cursor];
 
       if (ch == ')')
       {
         this._cursor += 1;
         return CPAREN;
       }
 
       if (ch == '(')
       {
         this._cursor += 1;
         return OPAREN;
       }
 
       if (!(Character.isWhitespace(ch)))
         break;
       this._cursor += 1;
     }
 
     if (isSymbolChar(ch)) {
       return readSymbol();
     }
     throw new RuntimeException(ConditionalMessages.unexpectedCharacter(this._cursor, this._input));
 
     label114: return null;
   }
 
   private boolean isSymbolChar(char ch)
   {
     return (((ch >= 'a') && (ch <= 'z')) || ((ch >= 'A') && (ch <= 'Z')) || ((ch >= '0') && (ch <= '9')) || (ch == '-') || (ch == '.') || (ch == '_'));
   }
 
   private Token readSymbol()
   {
     int start = this._cursor;
     do
     {
       this._cursor += 1;
 
       if (this._cursor >= this._input.length)
         break;
     }
     while (isSymbolChar(this._input[this._cursor]));
 
     String symbol = new String(this._input, start, this._cursor - start);
 
     if (symbol.equalsIgnoreCase("and")) {
       return AND;
     }
     if (symbol.equalsIgnoreCase("or")) {
       return OR;
     }
     if (symbol.equalsIgnoreCase("not")) {
       return NOT;
     }
     if (symbol.equalsIgnoreCase("property")) {
       return PROPERTY;
     }
     if (symbol.equalsIgnoreCase("class")) {
       return CLASS;
     }
     return new Token(TokenType.SYMBOL, symbol);
   }
 }