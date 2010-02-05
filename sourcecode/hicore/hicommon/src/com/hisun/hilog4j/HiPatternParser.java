package com.hisun.hilog4j;

import org.apache.log4j.helpers.FormattingInfo;
import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.helpers.PatternParser;
import org.apache.log4j.spi.LoggingEvent;

public class HiPatternParser extends PatternParser {
    public HiPatternParser(String pattern) {
        super(pattern);
    }

    public void finalizeConverter(char c) {
        if (c == '#') {
            addConverter(new UserDirPatternConverter(this.formattingInfo));
            this.currentLiteral.setLength(0);
        } else {
            super.finalizeConverter(c);
        }
    }

    private static class UserDirPatternConverter extends PatternConverter {
        UserDirPatternConverter(FormattingInfo formattingInfo) {
            super(formattingInfo);
        }

        public String convert(LoggingEvent event) {
            StringBuffer sb = new StringBuffer();
            Runtime rt = Runtime.getRuntime();
            sb.append("max mem:[");
            sb.append(rt.maxMemory() / 1024L);
            sb.append("k];total mem:[");
            sb.append(rt.totalMemory() / 1024L);
            sb.append("k];free mem:[");
            sb.append(rt.freeMemory() / 1024L);
            sb.append("k]");
            return sb.toString();
        }
    }
}