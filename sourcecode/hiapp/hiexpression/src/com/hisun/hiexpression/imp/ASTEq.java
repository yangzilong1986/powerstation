package com.hisun.hiexpression.imp;

public class ASTEq extends SimpleNode {
    public ASTEq(int id) {
        super(id);
    }

    public ASTEq(ICSExpParser p, int id) {
        super(p, id);
    }

    public String toString() {
        return this.children[0] + " = " + this.children[1];
    }

    public Object jjtAccept(ICSExpParserVisitor visitor, Object data) throws Exception {
        return visitor.visit(this, data);
    }
}