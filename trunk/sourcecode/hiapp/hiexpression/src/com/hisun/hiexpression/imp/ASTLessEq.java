package com.hisun.hiexpression.imp;

public class ASTLessEq extends SimpleNode {
    public ASTLessEq(int id) {
        super(id);
    }

    public ASTLessEq(ICSExpParser p, int id) {
        super(p, id);
    }

    public Object jjtAccept(ICSExpParserVisitor visitor, Object data) throws Exception {
        return visitor.visit(this, data);
    }
}