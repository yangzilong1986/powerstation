package com.hisun.hiexpression.imp;

public class ASTLess extends SimpleNode {
    public ASTLess(int id) {
        super(id);
    }

    public ASTLess(ICSExpParser p, int id) {
        super(p, id);
    }

    public Object jjtAccept(ICSExpParserVisitor visitor, Object data) throws Exception {
        return visitor.visit(this, data);
    }
}