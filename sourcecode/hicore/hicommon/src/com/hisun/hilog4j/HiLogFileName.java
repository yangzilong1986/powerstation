package com.hisun.hilog4j;

import com.hisun.util.HiICSProperty;

public class HiLogFileName extends HiAbstractFileName implements IFileName {
    public HiLogFileName(String name, int lineLength) {
        super(name, lineLength);
    }

    public HiLogFileName(String name) {
        super(name);
    }

    public String get() {
        return HiICSProperty.getLogDir() + this._name;
    }
}