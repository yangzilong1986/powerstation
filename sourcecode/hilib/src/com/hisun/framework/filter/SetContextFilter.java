package com.hisun.framework.filter;


import com.hisun.exception.HiException;
import com.hisun.message.HiContext;
import com.hisun.message.HiMessageContext;
import com.hisun.pubinterface.IHandler;
import com.hisun.pubinterface.IHandlerFilter;

public class SetContextFilter implements IHandlerFilter {
    private final HiContext serverContext;

    public SetContextFilter(HiContext context) {

        this.serverContext = context;
    }

    public void process(HiMessageContext ctx, IHandler handler) throws HiException {

        ctx.pushParent(this.serverContext);

        HiMessageContext.setCurrentMessageContext(ctx);
        try {

            handler.process(ctx);
        } finally {

            ctx.popParent();

            HiMessageContext.setCurrentMessageContext(null);
        }
    }
}