package com.hisun.client;

import com.hisun.message.HiETF;

import java.util.ArrayList;
import java.util.List;

public class HiETF2HashMapList {
    private HiIgnoreHashMap _map = new HiIgnoreHashMap();

    public String toString() {
        return this._map.toString();
    }

    public HiIgnoreHashMap map() {
        return this._map;
    }

    public HiETF2HashMapList(HiETF etf) {
        toHashMapList(etf.getChildNodes(), this._map, this._map);
    }

    private void toHashMapList(List list, HiIgnoreHashMap map, HiIgnoreHashMap parentMap) {
        for (int i = 0; i < list.size(); ++i) {
            HiETF node = (HiETF) list.get(i);
            List list1 = node.getChildNodes();
            if (list1.size() != 0) {
                ArrayList tmpList;
                parentMap = map;
                String tmp = node.getName();
                int idx = tmp.lastIndexOf(95);
                if (idx == -1) {
                    idx = tmp.length();
                }
                tmp = tmp.substring(0, idx);

                if ((tmpList = (ArrayList) parentMap.get(tmp)) == null) {
                    tmpList = new ArrayList();
                    parentMap.put(tmp, tmpList);
                }
                HiIgnoreHashMap tmpMap = new HiIgnoreHashMap();
                tmpList.add(tmpMap);
                toHashMapList(list1, tmpMap, parentMap);
            } else {
                String value = node.getValue();
                if ("null".equals(value)) map.put(node.getName(), "");
                else map.put(node.getName(), value);
            }
        }
    }
}