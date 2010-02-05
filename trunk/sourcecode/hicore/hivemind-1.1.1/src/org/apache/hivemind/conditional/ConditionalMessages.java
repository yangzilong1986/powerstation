 package org.apache.hivemind.conditional;
 
 import org.apache.hivemind.impl.AbstractMessages;
 import org.apache.hivemind.impl.MessageFormatter;
 
 class ConditionalMessages
 {
   protected static MessageFormatter _formatter = new MessageFormatter(ConditionalMessages.class);
 
   public static String unexpectedCharacter(int position, char[] input)
   {
     return _formatter.format("unexpected-character", new Character(input[position]), new Integer(position + 1), new String(input));
   }
 
   public static String unexpectedEndOfInput(String input)
   {
     return _formatter.format("unexpected-end-of-input", input);
   }
 
   public static String unexpectedToken(TokenType expected, TokenType actual, String input)
   {
     return _formatter.format("unexpected-token", expected, actual, input);
   }
 
   public static String unparsedToken(Token token, String input)
   {
     return _formatter.format("unparsed-token", token, input);
   }
 }