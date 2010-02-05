package com.hisun.hiexpression.imp;

import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Vector;

public class ICSExpParser implements ICSExpParserTreeConstants, ICSExpParserConstants {
    protected JJTICSExpParserState jjtree;
    public ICSExpParserTokenManager token_source;
    JavaCharStream jj_input_stream;
    public Token token;
    public Token jj_nt;
    private int jj_ntk;
    private Token jj_scanpos;
    private Token jj_lastpos;
    private int jj_la;
    public boolean lookingAhead;
    private boolean jj_semLA;
    private int jj_gen;
    private final int[] jj_la1;
    private static int[] jj_la1_0;
    private static int[] jj_la1_1;
    private final JJCalls[] jj_2_rtns;
    private boolean jj_rescan;
    private int jj_gc;
    private final LookaheadSuccess jj_ls;
    private Vector jj_expentries;
    private int[] jj_expentry;
    private int jj_kind;
    private int[] jj_lasttokens;
    private int jj_endpos;

    public final Node topLevelExpression() throws ParseException {
        equalityExpression();
        jj_consume_token(0);
        return this.jjtree.rootNode();
    }

    public final void equalityExpression() throws ParseException {
        relationalExpression();
        while (true) {
            switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
                case 1:
                case 2:
                    break;
                default:
                    this.jj_la1[0] = this.jj_gen;
                    break;
            }
            switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
                case 1:
                    jj_consume_token(1);
                    ASTEq jjtn001 = new ASTEq(1);
                    boolean jjtc001 = true;
                    this.jjtree.openNodeScope(jjtn001);
                    try {
                        relationalExpression();
                    } catch (Throwable jjte001) {
                        if (jjtc001) {
                            this.jjtree.clearNodeScope(jjtn001);
                            jjtc001 = false;
                        } else {
                            this.jjtree.popNode();
                        }
                        if (jjte001 instanceof RuntimeException) {
                            throw ((RuntimeException) jjte001);
                        }

                        throw ((Error) jjte001);
                    } finally {
                        if (jjtc001) {
                            this.jjtree.closeNodeScope(jjtn001, 2);
                        }
                    }
                    break;
                case 2:
                    int i;
                    jj_consume_token(2);
                    ASTNotEq jjtn002 = new ASTNotEq(2);
                    int i = 1;
                    this.jjtree.openNodeScope(jjtn002);
                    try {
                        relationalExpression();
                    } catch (Throwable jjte002) {
                        if (i != 0) {
                            this.jjtree.clearNodeScope(jjtn002);
                            i = 0;
                        } else {
                            this.jjtree.popNode();
                        }
                        if (jjte002 instanceof RuntimeException) {
                            throw ((RuntimeException) jjte002);
                        }

                        throw ((Error) jjte002);
                    } finally {
                        if (i != 0) {
                            this.jjtree.closeNodeScope(jjtn002, 2);
                        }
                    }
                    break;
                default:
                    this.jj_la1[1] = this.jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
    }

    public final void relationalExpression() throws ParseException {
        primaryExpression();
        while (true) {
            switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
                case 3:
                case 4:
                case 5:
                case 6:
                    break;
                default:
                    this.jj_la1[2] = this.jj_gen;
                    break;
            }
            switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
                case 3:
                    jj_consume_token(3);
                    ASTLess jjtn001 = new ASTLess(3);
                    boolean jjtc001 = true;
                    this.jjtree.openNodeScope(jjtn001);
                    try {
                        primaryExpression();
                    } catch (Throwable jjte001) {
                        if (jjtc001) {
                            this.jjtree.clearNodeScope(jjtn001);
                            jjtc001 = false;
                        } else {
                            this.jjtree.popNode();
                        }
                        if (jjte001 instanceof RuntimeException) {
                            throw ((RuntimeException) jjte001);
                        }

                        throw ((Error) jjte001);
                    } finally {
                        if (jjtc001) {
                            this.jjtree.closeNodeScope(jjtn001, 2);
                        }
                    }
                    break;
                case 4:
                    int i;
                    jj_consume_token(4);
                    ASTGreater jjtn002 = new ASTGreater(4);
                    int i = 1;
                    this.jjtree.openNodeScope(jjtn002);
                    try {
                        primaryExpression();
                    } catch (Throwable jjte002) {
                        if (i != 0) {
                            this.jjtree.clearNodeScope(jjtn002);
                            i = 0;
                        } else {
                            this.jjtree.popNode();
                        }
                        if (jjte002 instanceof RuntimeException) {
                            throw ((RuntimeException) jjte002);
                        }

                        throw ((Error) jjte002);
                    } finally {
                        if (i != 0) {
                            this.jjtree.closeNodeScope(jjtn002, 2);
                        }
                    }
                    break;
                case 5:
                    int j;
                    jj_consume_token(5);
                    ASTLessEq jjtn003 = new ASTLessEq(5);
                    int j = 1;
                    this.jjtree.openNodeScope(jjtn003);
                    try {
                        primaryExpression();
                    } catch (Throwable jjte003) {
                        if (j != 0) {
                            this.jjtree.clearNodeScope(jjtn003);
                            j = 0;
                        } else {
                            this.jjtree.popNode();
                        }
                        if (jjte003 instanceof RuntimeException) {
                            throw ((RuntimeException) jjte003);
                        }

                        throw ((Error) jjte003);
                    } finally {
                        if (j != 0) {
                            this.jjtree.closeNodeScope(jjtn003, 2);
                        }
                    }
                    break;
                case 6:
                    int k;
                    jj_consume_token(6);
                    ASTGreaterEq jjtn004 = new ASTGreaterEq(6);
                    int k = 1;
                    this.jjtree.openNodeScope(jjtn004);
                    try {
                        primaryExpression();
                    } catch (Throwable jjte004) {
                        if (k != 0) {
                            this.jjtree.clearNodeScope(jjtn004);
                            k = 0;
                        } else {
                            this.jjtree.popNode();
                        }
                        if (jjte004 instanceof RuntimeException) {
                            throw ((RuntimeException) jjte004);
                        }

                        throw ((Error) jjte004);
                    } finally {
                        if (k != 0) {
                            this.jjtree.closeNodeScope(jjtn004, 2);
                        }
                    }
                    break;
                default:
                    this.jj_la1[3] = this.jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
    }

    public final void icsValueRef() throws ParseException {
        Token t;
        switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
            case 7:
                jj_consume_token(7);
                t = jj_consume_token(29);
                ASTIcsVarRef jjtn001 = new ASTIcsVarRef(7);
                boolean jjtc001 = true;
                this.jjtree.openNodeScope(jjtn001);
                try {
                    this.jjtree.closeNodeScope(jjtn001, 0);
                    jjtc001 = false;
                    jjtn001.setItemName(0, t.image);
                } finally {
                    if (jjtc001) {
                        this.jjtree.closeNodeScope(jjtn001, 0);
                    }
                }
                break;
            case 8:
                jj_consume_token(8);
                t = jj_consume_token(29);
                ASTIcsVarRef jjtn002 = new ASTIcsVarRef(7);
                boolean jjtc002 = true;
                this.jjtree.openNodeScope(jjtn002);
                try {
                    this.jjtree.closeNodeScope(jjtn002, 0);
                    jjtc002 = false;
                    jjtn002.setItemName(1, t.image);
                } finally {
                    if (jjtc002) {
                        this.jjtree.closeNodeScope(jjtn002, 0);
                    }
                }
                break;
            case 9:
                jj_consume_token(9);
                t = jj_consume_token(29);
                ASTIcsVarRef jjtn003 = new ASTIcsVarRef(7);
                boolean jjtc003 = true;
                this.jjtree.openNodeScope(jjtn003);
                try {
                    this.jjtree.closeNodeScope(jjtn003, 0);
                    jjtc003 = false;
                    jjtn003.setItemName(2, t.image);
                } finally {
                    if (jjtc003) {
                        this.jjtree.closeNodeScope(jjtn003, 0);
                    }
                }
                break;
            case 10:
                jj_consume_token(10);
                t = jj_consume_token(29);
                ASTIcsVarRef jjtn004 = new ASTIcsVarRef(7);
                boolean jjtc004 = true;
                this.jjtree.openNodeScope(jjtn004);
                try {
                    this.jjtree.closeNodeScope(jjtn004, 0);
                    jjtc004 = false;
                    jjtn004.setItemName(3, t.image);
                } finally {
                    if (jjtc004) {
                        this.jjtree.closeNodeScope(jjtn004, 0);
                    }
                }
                break;
            case 11:
                jj_consume_token(11);
                t = jj_consume_token(29);
                ASTIcsVarRef jjtn005 = new ASTIcsVarRef(7);
                boolean jjtc005 = true;
                this.jjtree.openNodeScope(jjtn005);
                try {
                    this.jjtree.closeNodeScope(jjtn005, 0);
                    jjtc005 = false;
                    jjtn005.setItemName(4, t.image);
                } finally {
                    if (jjtc005) {
                        this.jjtree.closeNodeScope(jjtn005, 0);
                    }
                }
                break;
            case 12:
                jj_consume_token(12);
                t = jj_consume_token(29);
                ASTIcsVarRef jjtn006 = new ASTIcsVarRef(7);
                boolean jjtc006 = true;
                this.jjtree.openNodeScope(jjtn006);
                try {
                    this.jjtree.closeNodeScope(jjtn006, 0);
                    jjtc006 = false;
                    jjtn006.setItemName(6, t.image);
                } finally {
                    if (jjtc006) {
                        this.jjtree.closeNodeScope(jjtn006, 0);
                    }
                }
                break;
            case 13:
                jj_consume_token(13);
                t = jj_consume_token(29);
                ASTIcsVarRef jjtn007 = new ASTIcsVarRef(7);
                boolean jjtc007 = true;
                this.jjtree.openNodeScope(jjtn007);
                try {
                    this.jjtree.closeNodeScope(jjtn007, 0);
                    jjtc007 = false;
                    jjtn007.setItemName(3, t.image);
                } finally {
                    if (jjtc007) {
                        this.jjtree.closeNodeScope(jjtn007, 0);
                    }
                }
                break;
            case 14:
                jj_consume_token(14);
                t = jj_consume_token(29);
                ASTIcsVarRef jjtn008 = new ASTIcsVarRef(7);
                boolean jjtc008 = true;
                this.jjtree.openNodeScope(jjtn008);
                try {
                    this.jjtree.closeNodeScope(jjtn008, 0);
                    jjtc008 = false;
                    jjtn008.setItemName(0, t.image);
                } finally {
                    if (jjtc008) {
                        this.jjtree.closeNodeScope(jjtn008, 0);
                    }
                }
                break;
            case 15:
                jj_consume_token(15);
                t = jj_consume_token(29);
                ASTIcsVarRef jjtn009 = new ASTIcsVarRef(7);
                boolean jjtc009 = true;
                this.jjtree.openNodeScope(jjtn009);
                try {
                    this.jjtree.closeNodeScope(jjtn009, 0);
                    jjtc009 = false;
                    jjtn009.setItemName(2, t.image);
                } finally {
                    if (jjtc009) {
                        this.jjtree.closeNodeScope(jjtn009, 0);
                    }
                }
                break;
            case 16:
                jj_consume_token(16);
                t = jj_consume_token(29);
                ASTIcsVarRef jjtn010 = new ASTIcsVarRef(7);
                boolean jjtc010 = true;
                this.jjtree.openNodeScope(jjtn010);
                try {
                    this.jjtree.closeNodeScope(jjtn010, 0);
                    jjtc010 = false;
                    jjtn010.setItemName(4, t.image);
                } finally {
                    if (jjtc010) {
                        this.jjtree.closeNodeScope(jjtn010, 0);
                    }
                }
                break;
            case 17:
                jj_consume_token(17);
                t = jj_consume_token(29);
                ASTIcsVarRef jjtn011 = new ASTIcsVarRef(7);
                boolean jjtc011 = true;
                this.jjtree.openNodeScope(jjtn011);
                try {
                    this.jjtree.closeNodeScope(jjtn011, 0);
                    jjtc011 = false;
                    jjtn011.setItemName(5, t.image);
                } finally {
                    if (jjtc011) {
                        this.jjtree.closeNodeScope(jjtn011, 0);
                    }
                }
                break;
            default:
                this.jj_la1[4] = this.jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
    }

    public final void primaryExpression() throws ParseException {
        String className = null;
        switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
            case 18:
                jj_consume_token(18);
                ASTConst jjtn001 = new ASTConst(8);
                boolean jjtc001 = true;
                this.jjtree.openNodeScope(jjtn001);
                try {
                    this.jjtree.closeNodeScope(jjtn001, 0);
                    jjtc001 = false;
                    jjtn001.setValue(Boolean.TRUE);
                } finally {
                    if (jjtc001) {
                        this.jjtree.closeNodeScope(jjtn001, 0);
                    }
                }
                break;
            case 19:
                jj_consume_token(19);
                ASTConst jjtn002 = new ASTConst(8);
                boolean jjtc002 = true;
                this.jjtree.openNodeScope(jjtn002);
                try {
                    this.jjtree.closeNodeScope(jjtn002, 0);
                    jjtc002 = false;
                    jjtn002.setValue(Boolean.FALSE);
                } finally {
                    if (jjtc002) {
                        this.jjtree.closeNodeScope(jjtn002, 0);
                    }
                }
                break;
            case 20:
                ASTConst jjtn003 = new ASTConst(8);
                boolean jjtc003 = true;
                this.jjtree.openNodeScope(jjtn003);
                try {
                    jj_consume_token(20);
                } finally {
                    if (jjtc003) {
                        this.jjtree.closeNodeScope(jjtn003, 0);
                    }
                }
                break;
            default:
                this.jj_la1[6] = this.jj_gen;
                if (jj_2_1(2)) {
                    staticMethodCall();
                    return;
                }
                switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
                    case 37:
                    case 40:
                    case 43:
                        switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
                            case 37:
                                jj_consume_token(37);
                                break;
                            case 40:
                                jj_consume_token(40);
                                break;
                            case 43:
                                jj_consume_token(43);
                                break;
                            default:
                                this.jj_la1[5] = this.jj_gen;
                                jj_consume_token(-1);
                                throw new ParseException();
                        }
                        ASTConst jjtn004 = new ASTConst(8);
                        boolean jjtc004 = true;
                        this.jjtree.openNodeScope(jjtn004);
                        try {
                            this.jjtree.closeNodeScope(jjtn004, 0);
                            jjtc004 = false;
                            jjtn004.setValue(this.token_source.literalValue);
                        } finally {
                            if (jjtc004) {
                                this.jjtree.closeNodeScope(jjtn004, 0);
                            }
                        }
                        break;
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                        icsValueRef();
                        break;
                    case 21:
                        jj_consume_token(21);
                        equalityExpression();
                        jj_consume_token(22);
                        break;
                    case 18:
                    case 19:
                    case 20:
                    case 22:
                    case 23:
                    case 24:
                    case 25:
                    case 26:
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                    case 31:
                    case 32:
                    case 33:
                    case 34:
                    case 35:
                    case 36:
                    case 38:
                    case 39:
                    case 41:
                    case 42:
                    default:
                        this.jj_la1[7] = this.jj_gen;
                        jj_consume_token(-1);
                        throw new ParseException();
                }
        }
    }

    public final void staticMethodCall() throws ParseException {
        ASTStaticMethod jjtn000 = new ASTStaticMethod(9);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        String className = "com.hisun.hiexpression.HiExpBasicFunctions";
        try {
            Token t = jj_consume_token(29);
            jj_consume_token(21);
            switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 29:
                case 37:
                case 40:
                case 43:
                    primaryExpression();
                    while (true) {
                        switch ((this.jj_ntk == -1) ? jj_ntk() : this.jj_ntk) {
                            case 23:
                                break;
                            default:
                                this.jj_la1[8] = this.jj_gen;
                                break;
                        }
                        jj_consume_token(23);
                        primaryExpression();
                    }
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 38:
                case 39:
                case 41:
                case 42:
            }
            this.jj_la1[9] = this.jj_gen;

            jj_consume_token(22);
            this.jjtree.closeNodeScope(jjtn000, true);
            jjtc000 = false;
            jjtn000.init(className, t.image);
        } catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw ((RuntimeException) jjte000);
            }

            throw ((Error) jjte000);
        } finally {
            if (jjtc000) this.jjtree.closeNodeScope(jjtn000, true);
        }
    }

    private final boolean jj_2_1(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = (this.jj_scanpos = this.token);
        try {
            int i = (!(jj_3_1())) ? 1 : 0;

            return i;
        } catch (LookaheadSuccess ls) {
            int j = 1;
            return j;
        } finally {
            jj_save(0, xla);
        }
    }

    private final boolean jj_3R_4() {
        if (jj_scan_token(29)) return true;
        return (!(jj_scan_token(21)));
    }

    private final boolean jj_3_1() {
        return (!(jj_3R_4()));
    }

    private static void jj_la1_0() {
        jj_la1_0 = new int[]{6, 6, 120, 120, 262016, 0, 1835008, 2359168, 8388608, 541065088};
    }

    private static void jj_la1_1() {
        jj_la1_1 = new int[]{0, 0, 0, 0, 0, 2336, 0, 2336, 0, 2336};
    }

    public ICSExpParser(InputStream stream) {
        this(stream, null);
    }

    public ICSExpParser(InputStream stream, String encoding) {
        this.jjtree = new JJTICSExpParserState();

        this.lookingAhead = false;

        this.jj_la1 = new int[10];

        this.jj_2_rtns = new JJCalls[1];
        this.jj_rescan = false;
        this.jj_gc = 0;

        this.jj_ls = new LookaheadSuccess(null);

        this.jj_expentries = new Vector();

        this.jj_kind = -1;
        this.jj_lasttokens = new int[100];
        try {
            this.jj_input_stream = new JavaCharStream(stream, encoding, 1, 1);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        this.token_source = new ICSExpParserTokenManager(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
        this.jj_gen = 0;
        for (int i = 0; i < 10; ++i) this.jj_la1[i] = -1;
        for (i = 0; i < this.jj_2_rtns.length; ++i) this.jj_2_rtns[i] = new JJCalls();
    }

    public void ReInit(InputStream stream) {
        ReInit(stream, null);
    }

    public void ReInit(InputStream stream, String encoding) {
        try {
            this.jj_input_stream.ReInit(stream, encoding, 1, 1);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        this.token_source.ReInit(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
        this.jjtree.reset();
        this.jj_gen = 0;
        for (int i = 0; i < 10; ++i) this.jj_la1[i] = -1;
        for (i = 0; i < this.jj_2_rtns.length; ++i) this.jj_2_rtns[i] = new JJCalls();
    }

    public ICSExpParser(Reader stream) {
        this.jjtree = new JJTICSExpParserState();

        this.lookingAhead = false;

        this.jj_la1 = new int[10];

        this.jj_2_rtns = new JJCalls[1];
        this.jj_rescan = false;
        this.jj_gc = 0;

        this.jj_ls = new LookaheadSuccess(null);

        this.jj_expentries = new Vector();

        this.jj_kind = -1;
        this.jj_lasttokens = new int[100];

        this.jj_input_stream = new JavaCharStream(stream, 1, 1);
        this.token_source = new ICSExpParserTokenManager(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
        this.jj_gen = 0;
        for (int i = 0; i < 10; ++i) this.jj_la1[i] = -1;
        for (i = 0; i < this.jj_2_rtns.length; ++i) this.jj_2_rtns[i] = new JJCalls();
    }

    public void ReInit(Reader stream) {
        this.jj_input_stream.ReInit(stream, 1, 1);
        this.token_source.ReInit(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
        this.jjtree.reset();
        this.jj_gen = 0;
        for (int i = 0; i < 10; ++i) this.jj_la1[i] = -1;
        for (i = 0; i < this.jj_2_rtns.length; ++i) this.jj_2_rtns[i] = new JJCalls();
    }

    public ICSExpParser(ICSExpParserTokenManager tm) {
        this.jjtree = new JJTICSExpParserState();

        this.lookingAhead = false;

        this.jj_la1 = new int[10];

        this.jj_2_rtns = new JJCalls[1];
        this.jj_rescan = false;
        this.jj_gc = 0;

        this.jj_ls = new LookaheadSuccess(null);

        this.jj_expentries = new Vector();

        this.jj_kind = -1;
        this.jj_lasttokens = new int[100];

        this.token_source = tm;
        this.token = new Token();
        this.jj_ntk = -1;
        this.jj_gen = 0;
        for (int i = 0; i < 10; ++i) this.jj_la1[i] = -1;
        for (i = 0; i < this.jj_2_rtns.length; ++i) this.jj_2_rtns[i] = new JJCalls();
    }

    public void ReInit(ICSExpParserTokenManager tm) {
        this.token_source = tm;
        this.token = new Token();
        this.jj_ntk = -1;
        this.jjtree.reset();
        this.jj_gen = 0;
        for (int i = 0; i < 10; ++i) this.jj_la1[i] = -1;
        for (i = 0; i < this.jj_2_rtns.length; ++i) this.jj_2_rtns[i] = new JJCalls();
    }

    private final Token jj_consume_token(int kind) throws ParseException {
        Token oldToken;
        if ((oldToken = this.token).next != null) this.token = this.token.next;
        else this.token = (this.token.next = this.token_source.getNextToken());
        this.jj_ntk = -1;
        if (this.token.kind == kind) {
            this.jj_gen += 1;
            if (++this.jj_gc > 100) {
                this.jj_gc = 0;
                for (int i = 0; i < this.jj_2_rtns.length; ++i) {
                    JJCalls c = this.jj_2_rtns[i];
                    while (c != null) {
                        if (c.gen < this.jj_gen) c.first = null;
                        c = c.next;
                    }
                }
            }
            return this.token;
        }
        this.token = oldToken;
        this.jj_kind = kind;
        throw generateParseException();
    }

    private final boolean jj_scan_token(int kind) {
        if (this.jj_scanpos == this.jj_lastpos) {
            this.jj_la -= 1;
            if (this.jj_scanpos.next == null)
                this.jj_lastpos = (this.jj_scanpos = this.jj_scanpos.next = this.token_source.getNextToken());
            else this.jj_lastpos = (this.jj_scanpos = this.jj_scanpos.next);
        } else {
            this.jj_scanpos = this.jj_scanpos.next;
        }
        if (this.jj_rescan) {
            int i = 0;
            Token tok = this.token;
            for (; (tok != null) && (tok != this.jj_scanpos); tok = tok.next) ++i;
            if (tok != null) jj_add_error_token(kind, i);
        }
        if (this.jj_scanpos.kind != kind) return true;
        if ((this.jj_la == 0) && (this.jj_scanpos == this.jj_lastpos)) throw this.jj_ls;
        return false;
    }

    public final Token getNextToken() {
        if (this.token.next != null) this.token = this.token.next;
        else this.token = (this.token.next = this.token_source.getNextToken());
        this.jj_ntk = -1;
        this.jj_gen += 1;
        return this.token;
    }

    public final Token getToken(int index) {
        Token t = (this.lookingAhead) ? this.jj_scanpos : this.token;
        for (int i = 0; i < index; ++i) {
            if (t.next != null) t = t.next;
            else t = t.next = this.token_source.getNextToken();
        }
        return t;
    }

    private final int jj_ntk() {
        if ((this.jj_nt = this.token.next) == null) {
            return (this.jj_ntk = (this.token.next = this.token_source.getNextToken()).kind);
        }
        return (this.jj_ntk = this.jj_nt.kind);
    }

    private void jj_add_error_token(int kind, int pos) {
        if (pos >= 100) return;
        if (pos == this.jj_endpos + 1) {
            this.jj_lasttokens[(this.jj_endpos++)] = kind;
        } else if (this.jj_endpos != 0) {
            this.jj_expentry = new int[this.jj_endpos];
            for (int i = 0; i < this.jj_endpos; ++i) {
                this.jj_expentry[i] = this.jj_lasttokens[i];
            }
            boolean exists = false;
            for (Enumeration e = this.jj_expentries.elements(); e.hasMoreElements();) {
                int[] oldentry = (int[]) (int[]) e.nextElement();
                if (oldentry.length == this.jj_expentry.length) {
                    exists = true;
                    for (int i = 0; i < this.jj_expentry.length; ++i) {
                        if (oldentry[i] != this.jj_expentry[i]) {
                            exists = false;
                            break;
                        }
                    }
                    if (exists) break;
                }
            }
            if (!(exists)) this.jj_expentries.addElement(this.jj_expentry);
            if (pos == 0) return;
            this.jj_lasttokens[((this.jj_endpos = pos) - 1)] = kind;
        }
    }

    public ParseException generateParseException() {
        this.jj_expentries.removeAllElements();
        boolean[] la1tokens = new boolean[50];
        if (this.jj_kind >= 0) {
            la1tokens[this.jj_kind] = true;
            this.jj_kind = -1;
        }
        for (int i = 0; i < 10; ++i) {
            if (this.jj_la1[i] == this.jj_gen) {
                for (int j = 0; j < 32; ++j) {
                    if ((jj_la1_0[i] & 1 << j) != 0) {
                        la1tokens[j] = true;
                    }
                    if ((jj_la1_1[i] & 1 << j) != 0) {
                        la1tokens[(32 + j)] = true;
                    }
                }
            }
        }
        for (i = 0; i < 50; ++i) {
            if (la1tokens[i] != 0) {
                this.jj_expentry = new int[1];
                this.jj_expentry[0] = i;
                this.jj_expentries.addElement(this.jj_expentry);
            }
        }
        this.jj_endpos = 0;
        jj_rescan_token();
        jj_add_error_token(0, 0);
        int[][] exptokseq = new int[this.jj_expentries.size()][];
        for (int i = 0; i < this.jj_expentries.size(); ++i) {
            exptokseq[i] = ((int[]) (int[]) this.jj_expentries.elementAt(i));
        }
        return new ParseException(this.token, exptokseq, tokenImage);
    }

    public final void enable_tracing() {
    }

    public final void disable_tracing() {
    }

    private final void jj_rescan_token() {
        this.jj_rescan = true;
        for (int i = 0; i < 1; ++i)
            try {
                JJCalls p = this.jj_2_rtns[i];
                do {
                    if (p.gen > this.jj_gen) {
                        this.jj_la = p.arg;
                        this.jj_lastpos = (this.jj_scanpos = p.first);
                        switch (i) {
                            case 0:
                                jj_3_1();
                        }
                    }
                    p = p.next;
                } while (p != null);
            } catch (LookaheadSuccess ls) {
            }
        this.jj_rescan = false;
    }

    private final void jj_save(int index, int xla) {
        JJCalls p = this.jj_2_rtns[index];
        while (p.gen > this.jj_gen) {
            if (p.next == null) {
                p = p.next = new JJCalls();
                break;
            }
            p = p.next;
        }
        p.gen = (this.jj_gen + xla - this.jj_la);
        p.first = this.token;
        p.arg = xla;
    }

    static {
        jj_la1_0();
        jj_la1_1();
    }

    static final class JJCalls {
        int gen;
        Token first;
        int arg;
        JJCalls next;
    }

    private static final class LookaheadSuccess extends Error {
        private LookaheadSuccess() {
        }

        LookaheadSuccess(ICSExpParser.1x0) {
        }
    }
}