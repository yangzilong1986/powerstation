/*    */ package com.hisun.database;
/*    */ 
/*    */ import com.hisun.common.util.pattern.CharPredicates;
/*    */ import com.hisun.common.util.pattern.Pattern;
/*    */ import com.hisun.common.util.pattern.Patterns;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashSet;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class HiSqlParser
/*    */ {
/* 71 */   private static Pattern table = Patterns.many1(CharPredicates.isAlphaNumeric());
/*    */ 
/* 73 */   private static Pattern ws = Patterns.many1(CharPredicates.isWhitespace());
/* 74 */   private static Pattern update = Patterns.isStringCI("UPDATE").seq(ws);
/* 75 */   private static Pattern delete = Patterns.isStringCI("DELETE").seq(ws).seq(Patterns.isStringCI("FROM")).seq(ws);
/* 76 */   private static Pattern insert = Patterns.isStringCI("INSERT").seq(ws).seq(Patterns.isStringCI("INTO")).seq(ws);
/*    */ 
/* 78 */   private static Pattern exec = Patterns.or(new Pattern[] { update, delete, insert });
/*    */ 
/*    */   public static List parse_select(String src)
/*    */   {
/* 23 */     Set ret = new HashSet();
/*    */ 
/* 25 */     int idx = 0;
/* 26 */     while (idx >= 0) {
/* 27 */       idx = getTables(src, ret, idx);
/*    */     }
/* 29 */     ArrayList lst = new ArrayList();
/* 30 */     lst.addAll(ret);
/* 31 */     return lst;
/*    */   }
/*    */ 
/*    */   public static String parse_exec(String src)
/*    */   {
/* 39 */     int len = src.length();
/* 40 */     int at = exec.match(src, len, 0);
/* 41 */     int mlen = table.match(src, len, at);
/*    */ 
/* 43 */     return src.substring(at, at + mlen).toUpperCase();
/*    */   }
/*    */ 
/*    */   private static int getTables(String src, Set ret, int idx)
/*    */   {
/* 48 */     int from = StringUtils.indexOf(src, "FROM", idx) + 4;
/* 49 */     if (from < 4)
/* 50 */       return -1;
/* 51 */     int where = StringUtils.indexOf(src, "WHERE", from);
/* 52 */     if (where < 0)
/* 53 */       where = src.length();
/* 54 */     String tabledec = StringUtils.substring(src, from, where);
/*    */ 
/* 57 */     String[] tables = StringUtils.split(tabledec, ',');
/* 58 */     for (int i = 0; i < tables.length; ++i) {
/* 59 */       ret.add(getTableName(StringUtils.trim(tables[i])));
/*    */     }
/* 61 */     return (where + 5);
/*    */   }
/*    */ 
/*    */   private static String getTableName(String str) {
/* 65 */     int len = str.length();
/* 66 */     int mlen = table.match(str, len, 0);
/*    */ 
/* 68 */     return str.substring(0, mlen).toUpperCase();
/*    */   }
/*    */ }