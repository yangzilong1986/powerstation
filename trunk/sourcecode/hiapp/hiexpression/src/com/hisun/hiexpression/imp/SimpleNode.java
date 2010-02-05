package com.hisun.hiexpression.imp;

public class SimpleNode implements Node {
    protected Node parent;
    protected Node[] children;
    protected int id;
    protected ICSExpParser parser;

    public SimpleNode(int i) {
        this.id = i;
    }

    public SimpleNode(ICSExpParser p, int i) {
        this(i);
        this.parser = p;
    }

    public void jjtOpen() {
    }

    public void jjtClose() {
    }

    public void jjtSetParent(Node n) {
        this.parent = n;
    }

    public Node jjtGetParent() {
        return this.parent;
    }

    public void jjtAddChild(Node n, int i) {
        if (this.children == null) {
            this.children = new Node[i + 1];
        } else if (i >= this.children.length) {
            Node[] c = new Node[i + 1];
            System.arraycopy(this.children, 0, c, 0, this.children.length);
            this.children = c;
        }
        this.children[i] = n;
    }

    public Node jjtGetChild(int i) {
        return this.children[i];
    }

    public int jjtGetNumChildren() {
        return ((this.children == null) ? 0 : this.children.length);
    }

    public Object jjtAccept(ICSExpParserVisitor visitor, Object data) throws Exception {
        return visitor.visit(this, data);
    }

    public Object childrenAccept(ICSExpParserVisitor visitor, Object data) throws Exception {
        if (this.children != null) {
            for (int i = 0; i < this.children.length; ++i) {
                this.children[i].jjtAccept(visitor, data);
            }
        }
        return data;
    }

    public String toString() {
        return ICSExpParserTreeConstants.jjtNodeName[this.id];
    }

    public String toString(String prefix) {
        return prefix + toString();
    }

    public void dump(String prefix) {
        if (this.children != null) for (int i = 0; i < this.children.length; ++i) {
            SimpleNode n = (SimpleNode) this.children[i];
            if (n != null) n.dump(prefix + " ");
        }
    }
}