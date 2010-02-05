package com.hisun.component.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlFilter {
    public static String htmlChanger(String htmlTag, String replaceStr) {
        String ret = " ";
        try {
            String regEx = "(<\\s*[a-zA-Z][^>]*>)|(</\\s*[a-zA-Z][^>]*>)";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(htmlTag);
            boolean rs = m.find();
            ret = p.matcher(htmlTag).replaceAll(replaceStr);
        } catch (Exception e) {
        }
        return ret;
    }

    public static void main(String[] args) {
        String htmlToChange = "<font color=#00000> Hello </font> ";

        String yourRegEx = "(<\\s*[a-zA-Z][^> ]*> )|( </\\s*[a-zA-Z][^> ]*> ) ";

        String replaceStr = "";

        HtmlFilter fliter = new HtmlFilter();
    }
}