package com.hisun.hiexpression.imp;

public abstract interface Node {
    public abstract void jjtOpen();

    public abstract void jjtClose();

    public abstract void jjtSetParent(Node paramNode);

    public abstract Node jjtGetParent();

    public abstract void jjtAddChild(Node paramNode, int paramInt);

    public abstract Node jjtGetChild(int paramInt);

    public abstract int jjtGetNumChildren();

    public abstract Object jjtAccept(ICSExpParserVisitor paramICSExpParserVisitor, Object paramObject) throws Exception;
}