package com.hisun.hilog4j;

import com.hisun.util.HiICSProperty;

public class HiTrcFileName extends HiAbstractFileName implements IFileName {
    public HiTrcFileName(String name) {

        super(name);
    }

    public HiTrcFileName(String name, int lineLength) {

        super(name, lineLength);
    }

    public HiTrcFileName(String name, int lineLength, boolean fixSizeable) {

        super(name, lineLength, fixSizeable);
    }

    public String get() {

        return HiICSProperty.getTrcDir() + this._name;
    }
}