package com.hisun.web.tag;


import com.hisun.common.HiETF2HashMapList;
import com.hisun.message.HiETF;
import com.hisun.message.HiETFFactory;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;


class HiETFUtils {

    public static HashMap toHashMap(HiETF root) {

        return new HiETF2HashMapList(root).map();

    }


    public static HiETF fromHashMap(HashMap map) {

        return null;

    }


    public static HiETF fromJSONStr(HiETF root, String JSONStr) {

        return fromJSON(root, JSONObject.fromObject(JSONStr));

    }


    public static HiETF fromJSONStr(String JSONStr) {

        return fromJSON(JSONObject.fromObject(JSONStr));

    }


    public static HiETF fromJSON(JSONObject object) {

        HiETF root = HiETFFactory.createETF();

        Iterator iter = object.keys();

        while (iter.hasNext()) {

            String key = (String) iter.next();

            Object o = object.get(key);

            if (o instanceof JSONObject) {

                fromJSON(root, object.getJSONObject(key));

            } else if (o instanceof JSONArray) {

                JSONArray array = (JSONArray) o;

                for (int i = 0; i < array.size(); ++i) {

                    HiETF grpNod = root.addNode(key + (i + 1));

                    fromJSON(grpNod, array.getJSONObject(i));

                }

            } else {

                root.setChildValue(key, o.toString());

            }

        }

        return root;

    }


    public static HiETF fromJSON(HiETF root, JSONObject object) {

        Iterator iter = object.keys();

        while (iter.hasNext()) {

            String key = (String) iter.next();

            Object o = object.get(key);

            if (o instanceof JSONObject) {

                HiETF grpNod = root.addNode(key);

                fromJSON(root, object);

            } else {

                root.setChildValue(key, o.toString());

            }

        }

        return null;

    }


    public static JSONObject toJSON(HiETF root) {

        return null;

    }


    public static HashMap JSON2HashMap(JSONObject object) {

        return ((HashMap) JSONObject.toBean(object));

    }


    public static JSONObject HashMap2JSON(HashMap map) {

        return null;

    }

}