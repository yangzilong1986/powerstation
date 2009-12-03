package com.hisun.hiexpression.imp;

public abstract interface ICSExpParserVisitor
{
  public abstract Object visit(SimpleNode paramSimpleNode, Object paramObject);

  public abstract Object visit(ASTEq paramASTEq, Object paramObject)
    throws Exception;

  public abstract Object visit(ASTNotEq paramASTNotEq, Object paramObject)
    throws Exception;

  public abstract Object visit(ASTLess paramASTLess, Object paramObject)
    throws Exception;

  public abstract Object visit(ASTGreater paramASTGreater, Object paramObject)
    throws Exception;

  public abstract Object visit(ASTLessEq paramASTLessEq, Object paramObject)
    throws Exception;

  public abstract Object visit(ASTGreaterEq paramASTGreaterEq, Object paramObject)
    throws Exception;

  public abstract Object visit(ASTIcsVarRef paramASTIcsVarRef, Object paramObject);

  public abstract Object visit(ASTConst paramASTConst, Object paramObject);

  public abstract Object visit(ASTStaticMethod paramASTStaticMethod, Object paramObject)
    throws Exception;
}