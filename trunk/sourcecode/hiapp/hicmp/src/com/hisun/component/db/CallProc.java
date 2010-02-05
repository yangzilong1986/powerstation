package com.hisun.component.db;

import com.hisun.atc.common.HiArgUtils;
import com.hisun.database.HiDataBaseUtil;
import com.hisun.exception.HiException;
import com.hisun.hilib.HiATLParam;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiETF;
import com.hisun.message.HiETFFactory;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CallProc {
    private Logger log1;

    public CallProc() {
        this.log1 = HiLog.getLogger("callproc.trc");
    }

    public int execute(HiATLParam args, HiMessageContext ctx) throws HiException {
        HiMessage msg = ctx.getCurrentMsg();
        Logger log = HiLog.getLogger(msg);
        String name = HiArgUtils.getStringNotNull(args, "NAME");
        String in = args.get("IN");
        String out = args.get("OUT");
        String[] inArgs = null;
        String[] outArgs = null;
        if (StringUtils.isNotBlank(in)) {
            inArgs = hsplit(in);
        }
        if (StringUtils.isNotBlank(out)) {
            outArgs = hsplit(out);
        }

        if (log.isDebugEnabled()) {
            int tmp = (inArgs != null) ? inArgs.length : 0;
            log.debug(String.format("[%d]", new Object[]{Integer.valueOf(tmp)}));
            for (int i = 0; i < tmp; ++i) {
                log.debug(inArgs[i]);
            }

            tmp = (outArgs != null) ? outArgs.length : 0;

            log.debug(String.format("[%d]", new Object[]{Integer.valueOf(tmp)}));
            for (i = 0; i < tmp; ++i) {
                log.debug(outArgs[i]);
            }
        }
        HiDataBaseUtil db = ctx.getDataBaseUtil();
        Map outValue = db.call(name, inArgs);
        if (outValue == null) {
            return 0;
        }
        HiETF root = msg.getETFBody();
        this.log1.debug("HashMap2ETF: [" + outValue.size() + "]");

        if (outArgs == null) HashMap2ETF(root, outValue);
        else {
            HashMap2ETF(root, outValue, outArgs);
        }
        return 0;
    }

    public void HashMap2ETF(HiETF root, Map value) {
        if ((root == null) || (value == null)) {
            return;
        }
        Iterator iter = value.keySet().iterator();
        while (iter.hasNext()) {
            String tmp1 = (String) iter.next();
            Object tmp2 = value.get(tmp1);
            this.log1.debug("HashMap2ETF0: [" + tmp1 + "][" + tmp2 + "]");
            if (tmp2 instanceof ArrayList) {
                ArrayList list = (ArrayList) tmp2;
                root.setChildValue(tmp1 + "_NUM", String.valueOf(list.size()));
                for (int i = 0; i < list.size(); ++i) {
                    HashMap2ETF(root.addNode(tmp1 + "_" + (i + 1)), (HashMap) list.get(i));
                }

            } else if (tmp2 != null) {
                root.setChildValue(tmp1, tmp2.toString());
            }
        }
    }

    public void HashMap2ETF(HiETF root, Map value, String[] args) {
        if ((root == null) || (value == null)) {
            return;
        }
        Iterator iter = value.keySet().iterator();
        int idx = 0;
        while (iter.hasNext()) {
            String tmp1 = (String) iter.next();
            Object tmp2 = value.get(tmp1);
            this.log1.debug("HashMap2ETF1: [" + tmp1 + "][" + tmp2 + "]");
            if (idx < args.length) {
                tmp1 = args[idx];
            }
            ++idx;
            if (tmp2 instanceof ArrayList) {
                ArrayList list = (ArrayList) tmp2;
                root.setChildValue(tmp1 + "_NUM", String.valueOf(list.size()));
                for (int i = 0; i < list.size(); ++i) {
                    HashMap2ETF(root.addNode(tmp1 + "_" + (i + 1)), (HashMap) list.get(i));
                }

            } else if (tmp2 != null) {
                this.log1.debug("HashMap2ETF1: [" + tmp1 + "][" + tmp2 + "]");
                root.setChildValue(tmp1, tmp2.toString());
            }
        }
    }

    public static void main(String[] args) {
        String[] args1 = hsplit("test|| | || | | | | | | | | || | | | | | || | | | | 3");
        for (int i = 0; i < args1.length; ++i)
            System.out.println("[" + args1[i] + "]");
    }

    public static void testProc() {
        CallProc proc = new CallProc();
        HiETF root = HiETFFactory.createETF();
        HashMap map = new HashMap();
        ArrayList list = new ArrayList();
        map.put("hello01", "value01");
        map.put("hello02", "value02");
        map.put("hello03", "value03");
        map.put("LIST", list);
        for (int i = 0; i < 10; ++i) {
            HashMap map1 = new HashMap();
            map1.put("hello01", "value01");
            map1.put("hello02", "value02");
            map1.put("hello03", "value03");
            list.add(map1);
        }

        proc.HashMap2ETF(root, map);
        System.out.println(root);
    }

    public static String[] hsplit(String value) {
        if (value == null) return null;
        ArrayList list = new ArrayList();
        int idx1 = 0;
        int i = 0;
        for (i = 0; i < value.length(); ++i) {
            if (value.charAt(i) == '|') {
                if (idx1 == i) list.add(null);
                else {
                    list.add(value.substring(idx1, i));
                }
                idx1 = i + 1;
            }
        }
        if (idx1 != i) {
            list.add(value.substring(idx1, i));
        }
        String[] args = new String[list.size()];
        for (int j = 0; j < list.size(); ++j) {
            args[j] = ((String) list.get(j));
        }
        return args;
    }
}