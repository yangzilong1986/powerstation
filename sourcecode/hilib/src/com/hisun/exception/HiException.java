package com.hisun.exception;


import com.hisun.mon.HiMonitorEventInfo;
import com.hisun.util.HiStringManager;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


public class HiException extends Exception {
    private static final long serialVersionUID = 843027728253830766L;
    protected Throwable nestedException = null;

    private String code = "211007";

    private String[] msg = null;

    protected Map msgStack = null;

    private boolean isLog = false;


    protected void clone(HiException e) {

        this.nestedException = e.nestedException;

        this.code = e.code;

        this.msg = e.msg;

        this.msgStack = e.msgStack;

        this.isLog = e.isLog;

    }


    public static HiException makeException(Throwable e) {

        if (e instanceof InvocationTargetException) {

            Throwable t = ((InvocationTargetException) e).getTargetException();

            if (t instanceof Exception) {

                e = (Exception) t;

            }

        }

        HiMonitorEventInfo eventInfo = new HiMonitorEventInfo();

        if (e instanceof HiException) {

            HiException e1 = (HiException) e;

            eventInfo.setId(e1.getCode());

            eventInfo.setMsg(e1.getAppMessage());

            eventInfo.send();

            return e1;

        }


        eventInfo.setId("211007");

        eventInfo.setMsg(e.getMessage());

        eventInfo.send();

        return new HiException(e);

    }


    public static HiException makeException(String code, Throwable e) {

        return makeException(code, new String[0], e);

    }


    public static HiException makeException(String code, String arg0, Throwable e) {

        return makeException(code, new String[]{arg0}, e);

    }


    public static HiException makeException(String code, String arg0, String arg1, Throwable e) {

        return makeException(code, new String[]{arg0, arg1}, e);

    }


    public static HiException makeException(String code, String arg0, String arg1, String arg2, Throwable e) {

        return makeException(code, new String[]{arg0, arg1, arg2}, e);

    }


    public static HiException makeException(String code, String arg0, String arg1, String arg2, String arg3, Throwable e) {

        return makeException(code, new String[]{arg0, arg1, arg2, arg3}, e);

    }


    public static HiException makeException(String code, String[] args, Throwable e) {

        if (e instanceof InvocationTargetException) {

            e = ((InvocationTargetException) e).getTargetException();

        }


        HiMonitorEventInfo eventInfo = new HiMonitorEventInfo();

        if (e instanceof HiException) {

            HiException e1 = (HiException) e;

            eventInfo.setId(e1.getCode());

            eventInfo.setMsg(e1.getAppMessage());

            eventInfo.send();


            return e1;

        }


        eventInfo.setId(code);

        eventInfo.setMsg(e.getMessage());

        eventInfo.send();

        return new HiSysException(code, args, e);

    }


    public HiException() {

        create("000000", null);

    }


    public HiException(String code) {

        super(code);


        create(code, null);

    }


    public HiException(String code, String msg) {

        super(code);

        create(code, new String[]{msg});

    }


    public HiException(String code, String arg0, String arg1) {

        super(code);

        create(code, new String[]{arg0, arg1});

    }


    public HiException(String code, String arg0, String arg1, String arg2) {

        super(code);

        create(code, new String[]{arg0, arg1, arg2});

    }


    public HiException(String code, String arg0, String arg1, String arg2, String arg3) {

        super(code);

        create(code, new String[]{arg0, arg1, arg2, arg3});

    }


    public HiException(String code, String[] args) {

        super(code);

        create(code, args);

    }


    public HiException(Throwable nestedException) {

        super(nestedException.getMessage());


        create(this.code, new String[]{nestedException.getMessage()});


        this.nestedException = nestedException;

    }


    public HiException(String code, String msg, Throwable nestedException) {

        super(code);


        create(code, new String[]{msg});

        this.nestedException = nestedException;

    }


    public HiException(String code, String[] msg, Throwable nestedException) {

        super(code);


        create(code, msg);

        this.nestedException = nestedException;

    }


    protected void create(String code, String[] args) {

        this.code = code;

        this.msg = args;

        if (this.msgStack == null) {

            this.msgStack = new LinkedHashMap();

        }

        this.msgStack.put(code, args);

        HiMonitorEventInfo eventInfo = new HiMonitorEventInfo();

        eventInfo.setId(code);

        eventInfo.setMsg(getAppMessage());

        eventInfo.send();

    }


    public void addMsgStack(String code, String[] args) {

        create(code, args);

    }


    public void addMsgStack(String code, String arg0) {

        create(code, new String[]{arg0});

    }


    public void addMsgStack(String code, String arg0, String arg1) {

        create(code, new String[]{arg0, arg1});

    }


    public void addMsgStack(String code, String arg0, String arg1, String arg2) {

        create(code, new String[]{arg0, arg1, arg2});

    }


    public void addMsgStack(String code, String arg0, String arg1, String arg2, String arg3) {

        create(code, new String[]{arg0, arg1, arg2, arg3});

    }


    public String getCode() {

        return this.code;

    }


    public void setCode(String code) {

        this.code = code;

    }


    public String getMessage() {

        if (this.nestedException == null) {

            return getAppMessage();

        }


        return getAppMessage() + "\n Nested Exception: " + this.nestedException.getMessage();

    }


    public String getAppMessage() {

        return this.code + "-" + HiStringManager.getManager().getString(this.code, this.msg);

    }


    public String getAppStackMessage() {

        HiStringManager mgr = HiStringManager.getManager();

        StringBuffer msgbuf = new StringBuffer();


        Iterator it = this.msgStack.entrySet().iterator();


        while (it.hasNext()) {

            Map.Entry msgEntry = (Map.Entry) it.next();

            String s = ((String) msgEntry.getKey()) + ":" + mgr.getString((String) msgEntry.getKey(), (String[]) (String[]) msgEntry.getValue()) + "\n";


            msgbuf.insert(0, s);

        }

        return msgbuf.toString();

    }


    public String toString() {

        String detail;

        String args = "";

        try {

            detail = getAppMessage();

        } catch (Exception e) {

            detail = "";

        }


        if (this.msg != null) {

            for (int i = 0; i < this.msg.length; ++i) {

                args = args + " : " + this.msg[i];

            }


        }


        return this.code + ":" + detail;

    }


    public Throwable getNestedException() {

        return this.nestedException;

    }


    public void printStackTrace() {

        super.printStackTrace();


        if (this.nestedException != null) {

            System.err.println(" Nested Exception : ");

            this.nestedException.printStackTrace();

        }

    }


    public void printStackTrace(PrintStream out) {

        super.printStackTrace(out);


        if (this.nestedException != null) {

            out.println(" Nested Exception: ");

            this.nestedException.printStackTrace(out);

        }

    }


    public void printStackTrace(PrintWriter writer) {

        super.printStackTrace(writer);


        if (this.nestedException != null) {

            writer.println(" Nested Exception: ");

            this.nestedException.printStackTrace(writer);

        }

    }


    public boolean isLog() {

        return this.isLog;

    }


    public void setLog(boolean isLog) {

        this.isLog = isLog;

    }

}