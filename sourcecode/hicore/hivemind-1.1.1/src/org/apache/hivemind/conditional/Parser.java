 package org.apache.hivemind.conditional;
 
 import org.apache.hivemind.util.Defense;
 
 public class Parser
 {
   private String _input;
   private Lexer _lexer;
   private Token _nextToken;
   private boolean _onDeck;
   private static final Evaluator NOT_EVALUATOR = new NotEvaluator();
 
   private static final Evaluator OR_EVALUATOR = new OrEvaluator();
 
   private static final Evaluator AND_EVALUATOR = new AndEvaluator();
 
   public Node parse(String input)
   {
     Defense.notNull(input, "input");
     try
     {
       this._input = input;
       this._lexer = new Lexer(input);
 
       Node result = expression();
 
       Token token = next();
 
       if (token != null) {
         throw new RuntimeException(ConditionalMessages.unparsedToken(token, this._input));
       }
       Node localNode1 = result;
 
       return localNode1;
     }
     finally
     {
       this._input = null;
       this._nextToken = null;
       this._lexer = null;
       this._onDeck = false;
     }
   }
 
   private Token next()
   {
     Token result = (this._onDeck) ? this._nextToken : this._lexer.next();
 
     this._onDeck = false;
     this._nextToken = null;
 
     return result;
   }
 
   private Token match(TokenType expected)
   {
     Token actual = next();
 
     if (actual == null) {
       throw new RuntimeException(ConditionalMessages.unexpectedEndOfInput(this._input));
     }
     if (actual.getType() != expected) {
       throw new RuntimeException(ConditionalMessages.unexpectedToken(expected, actual.getType(), this._input));
     }
 
     return actual;
   }
 
   private Token peek()
   {
     if (!(this._onDeck))
     {
       this._nextToken = this._lexer.next();
       this._onDeck = true;
     }
 
     return this._nextToken;
   }
 
   private TokenType peekType()
   {
     Token next = peek();
 
     return ((next == null) ? null : next.getType());
   }
 
   private boolean isPeek(TokenType type)
   {
     return (peekType() == type);
   }
 
   private Node expression()
   {
     Node rnode;
     Node lnode = term();
 
     if (isPeek(TokenType.OR))
     {
       next();
 
       rnode = expression();
 
       return new NodeImpl(lnode, rnode, OR_EVALUATOR);
     }
 
     if (isPeek(TokenType.AND))
     {
       next();
 
       rnode = expression();
 
       return new NodeImpl(lnode, rnode, AND_EVALUATOR);
     }
 
     return lnode;
   }
 
   private Node term()
   {
     Token symbolToken;
     Evaluator ev;
     if (isPeek(TokenType.OPAREN))
     {
       next();
 
       Node result = expression();
 
       match(TokenType.CPAREN);
 
       return result;
     }
 
     if (isPeek(TokenType.NOT))
     {
       next();
 
       match(TokenType.OPAREN);
 
       Node expression = expression();
 
       match(TokenType.CPAREN);
 
       return new NodeImpl(expression, null, NOT_EVALUATOR);
     }
 
     if (isPeek(TokenType.PROPERTY))
     {
       next();
 
       symbolToken = match(TokenType.SYMBOL);
 
       ev = new PropertyEvaluator(symbolToken.getValue());
 
       return new NodeImpl(ev);
     }
 
     if (isPeek(TokenType.CLASS))
     {
       next();
 
       symbolToken = match(TokenType.SYMBOL);
 
       ev = new ClassNameEvaluator(symbolToken.getValue());
 
       return new NodeImpl(ev);
     }
 
     throw new RuntimeException(ConditionalMessages.unparsedToken(next(), this._input));
   }
 }