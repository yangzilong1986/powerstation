package com.hisun.hiexpression.imp;

public abstract interface ICSExpParserTreeConstants {
    public static final int JJTVOID = 0;
    public static final int JJTEQ = 1;
    public static final int JJTNOTEQ = 2;
    public static final int JJTLESS = 3;
    public static final int JJTGREATER = 4;
    public static final int JJTLESSEQ = 5;
    public static final int JJTGREATEREQ = 6;
    public static final int JJTICSVARREF = 7;
    public static final int JJTCONST = 8;
    public static final int JJTSTATICMETHOD = 9;
    public static final String[] jjtNodeName = {"void", "Eq", "NotEq", "Less", "Greater", "LessEq", "GreaterEq", "IcsVarRef", "Const", "StaticMethod"};
}