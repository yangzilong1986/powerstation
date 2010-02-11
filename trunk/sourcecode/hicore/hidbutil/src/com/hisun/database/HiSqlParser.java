package com.hisun.database;

import com.hisun.common.util.pattern.CharPredicates;
import com.hisun.common.util.pattern.Pattern;
import com.hisun.common.util.pattern.Patterns;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HiSqlParser {
    private static Pattern table = Patterns.many1(CharPredicates.isAlphaNumeric());

    private static Pattern ws = Patterns.many1(CharPredicates.isWhitespace());
    private static Pattern update = Patterns.isStringCI("UPDATE").seq(ws);
    private static Pattern delete = Patterns.isStringCI("DELETE").seq(ws).seq(Patterns.isStringCI("FROM")).seq(ws);
    private static Pattern insert = Patterns.isStringCI("INSERT").seq(ws).seq(Patterns.isStringCI("INTO")).seq(ws);

    private static Pattern exec = Patterns.or(new Pattern[]{update, delete, insert});

    public static List parse_select(String src) {
        Set ret = new HashSet();

        int idx = 0;
        while (idx >= 0) {
            idx = getTables(src, ret, idx);
        }
        ArrayList lst = new ArrayList();
        lst.addAll(ret);
        return lst;
    }

    public static String parse_exec(String src) {
        int len = src.length();
        int at = exec.match(src, len, 0);
        int mlen = table.match(src, len, at);

        return src.substring(at, at + mlen).toUpperCase();
    }

    private static int getTables(String src, Set ret, int idx) {
        int from = StringUtils.indexOf(src, "FROM", idx) + 4;
        if (from < 4) return -1;
        int where = StringUtils.indexOf(src, "WHERE", from);
        if (where < 0) where = src.length();
        String tabledec = StringUtils.substring(src, from, where);

        String[] tables = StringUtils.split(tabledec, ',');
        for (int i = 0; i < tables.length; ++i) {
            ret.add(getTableName(StringUtils.trim(tables[i])));
        }
        return (where + 5);
    }

    private static String getTableName(String str) {
        int len = str.length();
        int mlen = table.match(str, len, 0);

        return str.substring(0, mlen).toUpperCase();
    }
}