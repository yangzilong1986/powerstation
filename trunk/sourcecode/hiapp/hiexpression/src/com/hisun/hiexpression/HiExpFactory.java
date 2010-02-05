package com.hisun.hiexpression;

import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import org.apache.commons.lang.StringUtils;

public class HiExpFactory {
    private static Logger log = HiLog.getLogger("expression.trc");

    public static HiExpression createExp(String exp) {
        return new HiExpression(preLex(exp));
    }

    public static HiExpression createIfExp(String exp) {
        return new HiExpression(preIfLex1(exp));
    }

    public static String preprocess1(String exp) {
        return null;
    }

    public static String preLex2(String exp) {
        int j = 0;
        int k = 0;
        boolean function_flag = false;
        int idx1 = 0;
        int idx2 = 0;
        int pos1 = -1;
        int pos2 = 0;
        int pos3 = 0;

        while ((pos1 = exp.indexOf(41, pos1 + 1)) != -1) {
            if ((pos2 = exp.lastIndexOf(40, pos1)) == -1) {
                break;
            }
            if ((pos3 = exp.lastIndexOf(40, pos2 - 1)) == -1) ;
            pos3 = 0;
        }

        return null;
    }

    public static String preLex0(String exp) {
        return null;
    }

    public static String preIfLex1(String exp) {
        int j;
        int k;
        boolean function_flag;
        int i;
        String s;
        StringBuffer result = new StringBuffer();
        if (log.isDebugEnabled()) {
            log.debug("prelex:[" + exp + "]");
        }
        int pos = exp.indexOf("(");
        String value = null;
        if (pos != -1) {
            value = exp.substring(0, pos);
        }
        if (StringUtils.equalsIgnoreCase(value, "and")) {
            j = 0;
            k = 0;
            k = pos + 1;
            function_flag = false;
            result.append(exp.substring(0, pos + 1));
            for (i = pos + 1; i < exp.length(); ++i) {
                if (exp.charAt(i) == '(') {
                    function_flag = true;
                    ++j;
                }

                if (exp.charAt(i) == ')') {
                    --j;
                }

                if ((j == 0) && (function_flag)) {
                    function_flag = false;
                    s = preIfLex1(exp.substring(k, i + 1));
                    result.append(s);
                    for (; i < exp.length(); ++i) {
                        if (exp.charAt(i) == ',') {
                            break;
                        }
                    }
                    k = i + 1;
                    if (i < exp.length()) result.append(",");
                }
            }
            result.append(")");
        } else if (StringUtils.equalsIgnoreCase(value, "or")) {
            j = 0;
            k = 0;
            k = pos + 1;
            function_flag = false;
            result.append(exp.substring(0, pos + 1));
            for (i = pos + 1; i < exp.length(); ++i) {
                if (exp.charAt(i) == '(') {
                    function_flag = true;
                    ++j;
                }

                if (exp.charAt(i) == ')') {
                    --j;
                }

                if ((j == 0) && (function_flag)) {
                    function_flag = false;
                    s = preIfLex1(exp.substring(k, i + 1));
                    for (; i < exp.length(); ++i) {
                        if (exp.charAt(i) == ',') break;
                    }
                    k = i + 1;
                    result.append(s);
                    if (i < exp.length()) result.append(",");
                }
            }
            result.append(")");
        } else {
            result.append(preIfLex(exp));
        }
        if (log.isDebugEnabled()) {
            log.debug("prelex:[" + exp + "]:[" + result + "]");
        }
        return result.toString();
    }

    public static String preIfLex(String exp) {
        String tmp;
        int idx;
        StringBuffer result = new StringBuffer(exp.length());

        if ((idx = exp.indexOf("!=")) != -1) {
            tmp = preLex(exp.substring(0, idx).trim());
            result.append(tmp);
            result.append('!');
            result.append('=');
            tmp = preLex(exp.substring(idx + 2).trim());
            result.append(tmp);
        } else if ((idx = exp.indexOf("<=")) != -1) {
            tmp = preLex(exp.substring(0, idx).trim());
            result.append(tmp);
            result.append('<');
            result.append('=');
            tmp = preLex(exp.substring(idx + 2).trim());
            result.append(tmp);
        } else if ((idx = exp.indexOf(">=")) != -1) {
            tmp = preLex(exp.substring(0, idx).trim());
            result.append(tmp);
            result.append('>');
            result.append('=');
            tmp = preLex(exp.substring(idx + 2).trim());
            result.append(tmp);
        } else if ((idx = exp.indexOf(61)) != -1) {
            tmp = preLex(exp.substring(0, idx).trim());
            result.append(tmp);
            result.append('=');
            result.append('=');
            tmp = preLex(exp.substring(idx + 1).trim());
            result.append(tmp);
        } else if ((idx = exp.indexOf("<")) != -1) {
            tmp = preLex(exp.substring(0, idx).trim());
            result.append(tmp);
            result.append('<');
            tmp = preLex(exp.substring(idx + 1).trim());
            result.append(tmp);
        } else if ((idx = exp.indexOf(">")) != -1) {
            tmp = preLex(exp.substring(0, idx).trim());
            result.append(tmp);
            result.append('>');
            tmp = preLex(exp.substring(idx + 1).trim());
            result.append(tmp);
        } else {
            tmp = preLex(exp);
            result.append(tmp);
        }
        if (log.isDebugEnabled()) log.debug("prelex:[" + exp + "]:[" + result + "]");
        return result.toString();
    }

    public static String preLex(String exp) {
        int state = 0;

        int lastIndex = 0;
        int count = 0;
        StringBuffer result = new StringBuffer(exp.length());
        boolean isFunction = false;

        int i = 0;
        if (i < exp.length()) {
            if (Character.isWhitespace(exp.charAt(i))) {
                switch (exp.charAt(i)) {
                    case '!':
                    case '<':
                    case '=':
                    case '>':
                        result.append("\"");
                        result.append(exp);
                        result.append("\"");
                        return result.toString();
                }

            }

        }

        for (int index = 0; index < exp.length(); ++index) {
            int j;
            switch (state) {
                case 0:
                    if (isSpecialChar(exp, index, '"')) {
                        result.append(exp.charAt(index));
                        state = 5;
                    } else if (isVar(exp, index)) {
                        --index;
                        state = 1;
                    } else if (isSpecialChar(exp, index, '(')) {
                        isFunction = true;
                        state = 2;
                        --index;
                        ++count;
                    } else if (isSeperater(exp, index, isFunction)) {
                        if (isSpecialChar(exp, index, ')')) {
                            --count;
                        }
                        --index;
                        state = 3;
                    } else if (index == exp.length() - 1) {
                        --index;
                        state = 4;
                    }
                    break;
                case 1:
                    if (isSeperater(exp, index, isFunction)) {
                        --index;
                        state = 0;
                    } else {
                        result.append(exp.charAt(index));
                        lastIndex = index + 1;
                    }
                    break;
                case 2:
                    result.append(exp.substring(lastIndex, index + 1));
                    lastIndex = index + 1;
                    state = 0;
                    break;
                case 3:
                    if (lastIndex != index) {
                        result.append('"');
                        for (j = lastIndex; j < index; ++j) {
                            if ((exp.charAt(j) == '\'') || (exp.charAt(j) == '"')) result.append('\\');
                            result.append(exp.charAt(j));
                        }
                        result.append('"');
                    }
                    result.append(exp.charAt(index));
                    lastIndex = index + 1;
                    state = 0;
                    break;
                case 4:
                    if ((isFunction) && (count == 0)) {
                        continue;
                    }

                    if ((lastIndex != index) || (index == 0)) {
                        result.append('"');
                        for (j = lastIndex; j < index + 1; ++j) {
                            if (exp.charAt(j) == '\\') continue;
                            if ((exp.charAt(j) == '\'') || (exp.charAt(j) == '"')) result.append('\\');
                            result.append(exp.charAt(j));
                        }
                        result.append('"');
                        lastIndex = index + 1;
                    }
                    break;
                case 5:
                    result.append(exp.charAt(index));
                    if (isSpecialChar(exp, index, '"')) {
                        state = 0;
                    }
                    lastIndex = index + 1;
            }

        }

        if (log.isDebugEnabled()) log.debug("prelex:[" + exp + "]:[" + result + "]");
        return result.toString();
    }

    private static boolean isVar(String exp, int idx) {
        boolean vared = false;

        switch (exp.charAt(idx)) {
            case '#':
            case '$':
            case '%':
            case '@':
            case '~':
                vared = true;
        }

        if (vared) {
            return ((idx != 0) && (exp.charAt(idx - 1) == '\\'));
        }

        return false;
    }

    private static boolean isSeperater(String exp, int idx, boolean isFunction) {
        boolean isSeperatered = false;

        switch (exp.charAt(idx)) {
            case ',':
                if (isFunction) isSeperatered = true;
                break;
            case ')':
                isSeperatered = true;
        }

        if (isSeperatered) {
            if ((idx == 0) || (exp.charAt(idx - 1) != '\\')) {
                return isSeperatered;
            }
            return false;
        }

        return isSeperatered;
    }

    private static boolean isSpecialChar(String exp, int idx, char c) {
        if (exp.charAt(idx) == c) {
            return ((idx != 0) && (exp.charAt(idx - 1) == '\\'));
        }

        return false;
    }
}