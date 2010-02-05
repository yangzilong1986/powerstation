package com.hisun.hilog4j;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.helpers.PatternParser;

public class HiPatternLayout extends PatternLayout {
    public HiPatternLayout() {
        this("%m%n");
    }

    public HiPatternLayout(String pattern) {
        super(pattern);
    }

    public PatternParser createPatternParser(String pattern) {
        return new HiPatternParser((pattern == null) ? "%m%n" : pattern);
    }
}