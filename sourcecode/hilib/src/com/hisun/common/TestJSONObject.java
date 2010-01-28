package com.hisun.common;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Iterator;

public class TestJSONObject {
    public static void main(String[] args) {

        JSONObject object = JSONObject.fromObject("{items:[{'value': 'New', 'onclick': 'CreateNewDoc()'}, {'value': 'Open', 'onclick': 'OpenDoc()'},{'value': 'Close', 'onclick': 'CloseDoc()'}]}");


        JSONArray array = object.getJSONArray("items");

        for (int i = 0; i < array.size(); ++i) {

            JSONObject obj = (JSONObject) array.get(i);

            obj.accumulate("hello", "world");

            System.out.println(obj.getInt("value"));

            System.out.println(obj);
        }
    }

    public static void main01(String[] args) {

        JSONObject object = JSONObject.fromObject("{'menu': { 'id': 'file', 'value': 'File:', 'popup': { 'menuitem': [ {'onclick': 'CreateNewDoc()'}, {'value': 'Open', 'onclick': 'OpenDoc()'}, {'value': 'Close', 'onclick': 'CloseDoc()'} ]} }}");


        dumpObject(object);
    }

    public static void dumpObject(JSONObject object) {

        Iterator iter = object.keys();

        while (iter.hasNext()) {

            String key = (String) iter.next();

            Object o = object.get(key);

            if (o instanceof JSONObject) {

                System.out.println(key + "is object");

                dumpObject((JSONObject) o);

            } else if (o instanceof JSONArray) {

                System.out.println(key + "is array");

                JSONArray array = (JSONArray) o;

                for (int i = 0; i < array.size(); ++i)

                    dumpObject(array.getJSONObject(i));
            } else {

                System.out.println(key + ":" + o.toString());
            }
        }
    }

    public static void main021(String[] args) {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("a", Integer.valueOf(1));

        jsonObject.put("b", Double.valueOf(1.1D));

        jsonObject.put("c", Long.valueOf(1L));

        jsonObject.put("d", "test");

        jsonObject.put("e", Boolean.valueOf(true));

        System.out.println(jsonObject);


        JSONObject object = JSONObject.fromObject("{d:'test',e:true,b:1.1,c:1,a:1}");


        System.out.println(object);

        System.out.println(object.getInt("a"));

        System.out.println(object.getDouble("b"));

        System.out.println(object.getLong("c"));

        System.out.println(object.getString("d"));

        System.out.println(object.getBoolean("e"));
    }
}