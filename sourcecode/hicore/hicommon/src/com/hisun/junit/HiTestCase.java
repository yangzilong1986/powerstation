package com.hisun.junit;

import com.hisun.hilib.HiATLParam;
import com.hisun.hilog4j.HiLog;
import com.hisun.message.*;
import com.hisun.util.HiICSProperty;
import junit.framework.TestCase;

public class HiTestCase extends TestCase {
    protected HiMessage msg;
    protected HiMessageContext ctx;
    protected HiETF root;
    protected HiATLParam args;

    public HiTestCase() {
        this.msg = null;
        this.ctx = null;
        this.root = null;
        this.args = null;
    }

    protected void setUp() throws Exception {
        super.setUp();
        System.setProperty("_ICS_ENV", "_ICS_JUNIT_ENV");
        System.setProperty("work.dir", "./");

        HiICSProperty.getBakDir();
        HiContext.getRootContext().setProperty("SVR.log", HiLog.getLogger("server.trc"));

        this.msg = new HiMessage("test", "plain");
        this.msg.setHeadItem("ECT", "text/plain");
        this.msg.setHeadItem("SCH", "rq");
        this.msg.setHeadItem("STF", "INFO");
        HiContext.pushCurrentContext(HiContext.getRootContext());

        this.root = HiETFFactory.createETF();
        this.msg.setBody(this.root);

        this.ctx = new HiMessageContext();
        this.ctx.pushParent(HiContext.getCurrentContext());
        HiMessageContext.setCurrentMessageContext(this.ctx);
        this.ctx.setCurrentMsg(this.msg);
        this.args = new HiATLParam();
        HiContext.getCurrentContext();
    }
}