/*    */ package com.hisun.component.web;
/*    */ 
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ public class HtmlFilter
/*    */ {
/*    */   public static String htmlChanger(String htmlTag, String replaceStr)
/*    */   {
/* 14 */     String ret = " ";
/*    */     try {
/* 16 */       String regEx = "(<\\s*[a-zA-Z][^>]*>)|(</\\s*[a-zA-Z][^>]*>)";
/* 17 */       Pattern p = Pattern.compile(regEx);
/* 18 */       Matcher m = p.matcher(htmlTag);
/* 19 */       boolean rs = m.find();
/* 20 */       ret = p.matcher(htmlTag).replaceAll(replaceStr);
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/*    */     }
/* 25 */     return ret;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 33 */     String htmlToChange = "<font color=#00000> Hello </font> ";
/*    */ 
/* 35 */     String yourRegEx = "(<\\s*[a-zA-Z][^> ]*> )|( </\\s*[a-zA-Z][^> ]*> ) ";
/*    */ 
/* 37 */     String replaceStr = "";
/*    */ 
/* 39 */     HtmlFilter fliter = new HtmlFilter();
/*    */   }
/*    */ }