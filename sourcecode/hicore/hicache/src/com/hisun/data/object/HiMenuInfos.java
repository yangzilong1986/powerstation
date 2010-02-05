package com.hisun.data.object;

import com.hisun.data.cache.HiDataCacheConfig;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;

public class HiMenuInfos {
    private ArrayList<HiMenuItem> list;
    public final String id = "MENU_INFOS";

    public HiMenuInfos() {
        this.list = null;
        this.id = "MENU_INFOS";
    }

    public void init(HiDataCacheConfig config) {
        this.list = config.getDataCache("MENU_INFOS").getDataList();
    }

    public ArrayList getList() {
        return this.list;
    }

    public ArrayList list(String id) {
        ArrayList tmpList = new ArrayList();
        for (int i = 0; i < this.list.size(); ++i) {
            HiMenuItem item = (HiMenuItem) this.list.get(i);
            if (StringUtils.equals(item.pid, id)) {
                tmpList.add(item);
            }
        }
        return tmpList;
    }

    public HiMenuItem item(String id) {
        for (int i = 0; i < this.list.size(); ++i) {
            HiMenuItem item = (HiMenuItem) this.list.get(i);
            if (StringUtils.equals(item.id, id)) {
                return item;
            }
        }
        return null;
    }
}