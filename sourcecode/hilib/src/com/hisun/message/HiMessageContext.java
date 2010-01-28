package com.hisun.message;


import com.hisun.database.HiDataBaseUtil;
import com.hisun.exception.HiException;
import com.hisun.pubinterface.HiCloseable;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;


public class HiMessageContext extends HiContext implements Cloneable {
    private HiMessage currentMsg;
    private Stack _responseMsgs;
    private Stack parentStack;
    private HiDataBaseUtil databaseUtil = new HiDataBaseUtil();


    public static void setCurrentMessageContext(HiMessageContext mc) {

        setCurrentContext(mc);

    }


    public static HiMessageContext getCurrentMessageContext() {

        return ((HiMessageContext) getCurrentContext());

    }


    public HiMessageContext() {

        super("MsgContext");

    }


    public HiMessageContext(HiMessageContext ctx) {

        super("MsgContext");

        this.currentMsg = new HiMessage(ctx.getCurrentMsg());


        this.parentStack = ((Stack) ctx.parentStack.clone());

        this.parent = ctx.parent;

        this.bag.setParent(this.parent.bag);

        Iterator iter = ctx.bag.entrySet().iterator();


        while (iter.hasNext()) {

            Map.Entry entry = (Map.Entry) iter.next();

            this.bag.put(entry.getKey(), entry.getValue());

        }

    }


    public HiMessage getCurrentMsg() {

        return this.currentMsg;

    }


    public HiMessage getRequestMsg() {

        return getCurrentMsg();

    }


    public void setRequestMsg(HiMessage msg) {

        setCurrentMsg(msg);

    }


    public HiMessage getResponseMsg() {

        if ((this._responseMsgs == null) || (this._responseMsgs.isEmpty())) return getCurrentMsg();

        return ((HiMessage) this._responseMsgs.peek());

    }


    public HiMessage popResponseMsg() {

        if ((this._responseMsgs == null) || (this._responseMsgs.isEmpty())) return null;

        return ((HiMessage) this._responseMsgs.pop());

    }


    public void setResponseMsg(HiMessage msg) {

        if (this._responseMsgs == null) {

            this._responseMsgs = new Stack();

        }

        this._responseMsgs.push(msg);

    }


    public void setCurrentMsg(HiMessage currentMsg) {

        this.currentMsg = currentMsg;

    }


    public void pushParent(HiContext parent) {

        if (this.parentStack == null) {

            this.parentStack = new Stack();

        }


        if (this.parent != null) this.parentStack.push(this.parent);

        this.parent = parent;

        this.bag.setParent(parent.bag);

    }


    public HiContext popParent() {

        HiContext old = this.parent;


        if ((this.parentStack == null) || (this.parentStack.isEmpty())) {

            this.parent = null;

            return old;

        }


        this.parent = ((HiContext) this.parentStack.pop());

        this.bag.setParent(this.parent.bag);

        return old;

    }


    public void setBaseSource(String strKey, Object strValue) {

        if (strValue == null) {

            strValue = "";

        }

        setProperty(strKey, strValue);

    }


    public Object getBaseSource(String strKey) {

        return getProperty(strKey);

    }


    public Object removeBaseSource(String key) {

        return this.bag.remove(key.toUpperCase());

    }


    public void setPara(String strKey, Object strValue) {

        setProperty("@PARA", strKey, strValue);

    }


    public Object getPara(String strKey) {

        return getProperty("@PARA", strKey);

    }


    public String getETFValue(HiETF etfRoot, String strKey) {

        return etfRoot.getGrandChildValue(strKey);

    }


    public Object getSpecExpre(HiETF etfRoot, String strKey) {

        String strName;

        if (strKey.startsWith("@ETF")) {

            strName = StringUtils.substringAfter(strKey, "@ETF.");


            return getETFValue(etfRoot, strName);
        }

        if (strKey.startsWith("$")) {

            strName = StringUtils.substringAfter(strKey, "$");

            return getETFValue(etfRoot, strName);
        }

        if (strKey.startsWith("@BAS")) {

            strName = StringUtils.substringAfter(strKey, "@BAS.");


            return getBaseSource(strName);
        }

        if (strKey.startsWith("~")) {

            strName = StringUtils.substringAfter(strKey, "~");

            return getBaseSource(strName);
        }

        if (strKey.startsWith("@MSG")) {

            strName = StringUtils.substringAfter(strKey, "@MSG.");


            return this.currentMsg.getHeadItem(strName);
        }

        if (strKey.startsWith("%")) {

            strName = StringUtils.substringAfter(strKey, "%");

            return this.currentMsg.getHeadItem(strName);
        }

        if (strKey.startsWith("@PARA")) {

            strName = StringUtils.substringAfter(strKey, "@PARA.");

            return getPara(strName);

        }

        return getETFValue(etfRoot, strKey);

    }


    public String getBCFG(String strKey) throws HiException {

        HashMap bcfgMap = (HashMap) getProperty("ROOT.BCFG");


        if (bcfgMap == null) {

            return null;

        }

        return ((String) bcfgMap.get(strKey.toUpperCase()));

    }


    public String getJnlTable() {

        return getStrProp("__TXNJNLTBL");

    }


    public HashMap getTableMetaData(String strKey) throws HiException {

        HashMap map = null;


        if (containsProperty("TABLEDECLARE." + strKey)) {

            map = (HashMap) getProperty("TABLEDECLARE", strKey);

        } else {

            HiDataBaseUtil data = getDataBaseUtil();

            map = data.getTableMetaData(strKey, data.getConnection());

            this.parent.setProperty("TABLEDECLARE", strKey, map);

        }


        return map;

    }


    public void setJnlTable(String TableName) {

        setProperty("__TXNJNLTBL", TableName);

    }


    public void clear() {

        if (this.databaseUtil != null) {

            this.databaseUtil.close();

            this.databaseUtil = null;

        }


        Iterator iter = this.bag.values().iterator();

        while (iter.hasNext()) {

            Object o = iter.next();

            if (o instanceof HiCloseable) try {

                ((HiCloseable) o).close();

            } catch (HiException e) {

            }

        }

        this.bag.clear();

    }


    public HiDataBaseUtil getDataBaseUtil() {

        return this.databaseUtil;

    }


    public void setDataBaseUtil(HiDataBaseUtil databaseUtil) {

        this.databaseUtil = databaseUtil;

    }


    public String toString() {

        StringBuffer result = new StringBuffer();

        result.append("\nmsg:" + this.currentMsg.toString());

        result.append("\nparentStack:" + this.parentStack);

        result.append("\nparent: " + this.parent);

        result.append("\nbag: " + this.bag);

        result.append("\ndatabaseUtil:" + this.databaseUtil);

        return result.toString();

    }

}