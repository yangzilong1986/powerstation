package com.hisun.hilog4j;

import com.hisun.util.HiICSProperty;

public class HiDynTrcFileName extends HiAbstractFileName implements IFileName {
    private String _trcName;

    public HiDynTrcFileName(String name) {
        super(name);
        this._trcName = HiICSProperty.getTrcDir() + this._name + ".trc";
    }

    public HiDynTrcFileName(String name, int lineLength) {
        super(name, lineLength);
        this._trcName = HiICSProperty.getTrcDir() + this._name + ".trc";
    }

    public String get() {
        return this._trcName;
    }
}