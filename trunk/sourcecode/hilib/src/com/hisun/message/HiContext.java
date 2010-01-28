package com.hisun.message;


import com.hisun.util.JavaUtils;
import com.hisun.util.LockableHashtable;
import org.apache.commons.lang.StringUtils;

import java.util.Iterator;
import java.util.Set;
import java.util.Stack;


public class HiContext {
    public static String SERVER_PRE = "SVR.";

    public static String APP_PRE = "APP.";

    public static String TRN_PRE = "TRN.";

    public static String ROOT_PRE = "ROOT.";

    protected static ThreadLocal currentContext = new ThreadLocal();

    protected static final HiContext RootContext = new HiContext("RootContext");
    protected String id;
    protected final LockableHashtable bag;
    protected HiContext parent;
    protected HiContext firstChild;
    protected HiContext nextBrother;


    public static void pushCurrentContext(HiContext mc) {

        Stack s;

        if (currentContext.get() == null) {

            s = new Stack();

            currentContext.set(s);

        } else {

            s = (Stack) currentContext.get();

        }

        s.push(mc);

    }


    public static HiContext popCurrentContext() {

        if (currentContext.get() != null) {

            Stack s = (Stack) currentContext.get();

            return ((HiContext) s.pop());

        }

        return null;

    }


    public static HiContext getCurrentContext() {

        if (currentContext.get() != null) {

            Stack s = (Stack) currentContext.get();

            return ((HiContext) s.peek());

        }

        return null;

    }


    public static void setCurrentContext(HiContext mc) {

        Stack s;

        if (currentContext.get() == null) {

            s = new Stack();

            currentContext.set(s);

        } else {

            s = (Stack) currentContext.get();

        }

        if (!(s.isEmpty())) s.pop();

        s.push(mc);

    }


    public static void removeCurrentContext() {

        currentContext.set(null);

    }


    public static HiContext getRootContext() {

        return RootContext;

    }


    public static HiContext createContext(String id, HiContext parent) {

        if (parent == null) parent = RootContext;

        return new HiContext(id, parent);

    }


    public static HiContext createContext(HiContext parent) {

        if (parent == null) parent = RootContext;

        return new HiContext(null, parent);

    }


    public static HiContext createAndPushContext() {

        HiContext parent = getCurrentContext();

        if (parent == null) {

            parent = RootContext;

        }

        HiContext mc = new HiContext(null, parent);

        pushCurrentContext(mc);

        return mc;

    }


    public String getStrProp(String propName) {

        return ((String) getProperty(propName));

    }


    public String getStrProp(String key1, String key2) {

        String key = key1 + "." + key2;

        return ((String) getProperty(key));

    }


    public boolean isPropertyTrue(String propName) {

        return isPropertyTrue(propName, false);

    }


    public boolean isPropertyTrue(String propName, boolean defaultVal) {

        return JavaUtils.isTrue(getProperty(propName), defaultVal);

    }


    public void setProperty(String name, Object value) {

        if ((name == null) || (value == null)) {

            return;

        }


        this.bag.put(name.toUpperCase(), value);

    }


    public void delProperty(String name) {

        this.bag.remove(name.toUpperCase());

    }


    public void setProperty(String key1, String key2, Object value) {

        setProperty(key1 + "." + key2, value);

    }


    public void setProperty(String name, Object value, boolean locked) {

        if ((name == null) || (value == null)) {

            return;

        }


        this.bag.put(name.toUpperCase(), value, locked);

    }


    public void setProperty(String key1, String key2, Object value, boolean locked) {

        setProperty(key1 + key2, value, locked);

    }


    public boolean containsProperty(String name) {

        Object propertyValue = getProperty(name);

        return (propertyValue != null);

    }


    public Iterator getPropertyNames() {

        return this.bag.keySet().iterator();

    }


    public Iterator getAllPropertyNames() {

        return this.bag.getAllKeys().iterator();

    }


    public Object getProperty(String name) {

        if (name != null) {

            if (this.bag == null) {

                return null;

            }

            return this.bag.get(name.toUpperCase());

        }


        return null;

    }


    public Object getProperty(String key1, String key2) {

        String key = key1 + "." + key2;

        return getProperty(key);

    }


    public HiContext getFirstChild() {

        return this.firstChild;

    }


    public HiContext getNextBrother() {

        return this.nextBrother;

    }


    public void setId(String id) {

        this.id = id;

    }


    public String getId() {

        return this.id;

    }


    protected HiContext(String id) {

        this.bag = new LockableHashtable();

        this.parent = null;

        this.id = id;

    }


    protected HiContext(String id, HiContext parent) {

        this.bag = new LockableHashtable();

        if (parent == null) {

            parent = RootContext;

        }

        this.bag.setParent(parent.bag);

        this.parent = parent;

        this.id = id;


        parent.addChild(this);

    }


    protected void addChild(HiContext child) {

        child.nextBrother = null;


        if (this.firstChild == null) {

            this.firstChild = child;

        } else {

            HiContext lastChild = this.firstChild;

            while (lastChild.nextBrother != null) lastChild = lastChild.nextBrother;

            lastChild.nextBrother = child;

        }

    }


    public String toString() {

        StringBuffer rs = new StringBuffer();

        rs.append("id=" + this.id);

        rs.append(";");

        Set set = this.bag.entrySet();

        Iterator iter = set.iterator();

        while (iter.hasNext()) {

            rs.append(iter.next());

            rs.append(";");

        }

        if (this.parent != null) {

            rs.append("parent=" + this.parent.id);

            rs.append(";");

        }

        if (this.firstChild != null) {

            rs.append("firstChild=" + this.firstChild.id);

            rs.append(";");

        }

        if (this.nextBrother != null) {

            rs.append("nextBrother=" + this.nextBrother.id);

            rs.append(";");

        }


        return rs.toString();

    }


    public HiContext getParent() {

        return this.parent;

    }


    public HiContext getServerContext() {

        return getNameContext("SVR.");

    }


    public HiContext getApplicationContext() {

        return getNameContext("APP.");

    }


    public HiContext getTransactionContext() {

        return getNameContext("TRN.");
    }


    private HiContext getNameContext(String id) {

        for (HiContext tmp = this; tmp != null; tmp = tmp.parent)

            if (StringUtils.equalsIgnoreCase(tmp.id.substring(0, 4), id.substring(0, 4))) {

                return tmp;

            }

        return null;

    }


    public void clear() {

        if (this.parent != null) this.parent.delchild(this);

    }


    private void delchild(HiContext context) {

        HiContext before = null;

        if (this.firstChild != null) {

            HiContext lastChild = this.firstChild;

            do {

                if (lastChild == context) {

                    if (before == null) this.firstChild = lastChild.nextBrother;

                    else {

                        before.nextBrother = lastChild.nextBrother;

                    }

                    lastChild.nextBrother = null;

                }

                before = lastChild;

                lastChild = lastChild.nextBrother;
            } while (lastChild != null);

        }

    }

}