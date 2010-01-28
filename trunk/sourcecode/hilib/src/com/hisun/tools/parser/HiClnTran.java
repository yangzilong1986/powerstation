package com.hisun.tools.parser;


import com.hisun.tools.HiClnParam;

import java.util.Iterator;
import java.util.LinkedHashMap;

public class HiClnTran {
    private LinkedHashMap _clnGroupMap;

    public HiClnTran() {

        this._clnGroupMap = new LinkedHashMap();
    }

    public void process(HiClnParam param) throws Exception {
        Iterator iter = this._clnGroupMap.values().iterator();

        while (iter.hasNext()) ((HiClnGroup) iter.next()).process(param);
    }

    public void addClnGroup(HiClnGroup clnGroup) {

        this._clnGroupMap.put(clnGroup.getId(), clnGroup);
    }

    public void process(String id, HiClnParam param) throws Exception {

        if (!(this._clnGroupMap.containsKey(id))) {

            throw new Exception("not found:[" + id + "] group");
        }

        ((HiClnGroup) this._clnGroupMap.get(id)).process(param);
    }
}