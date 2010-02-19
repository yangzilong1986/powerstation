package com.hzjbbis.fk.monitor.biz;

import com.hzjbbis.fk.FasSystem;
import com.hzjbbis.fk.common.spi.IEventHook;
import com.hzjbbis.fk.monitor.biz.eventtrace.EventTracer;
import com.hzjbbis.fk.sockserver.event.ClientCloseEvent;
import com.hzjbbis.fk.sockserver.event.ReceiveMessageEvent;
import com.hzjbbis.fk.utils.HexDump;
import org.apache.log4j.Logger;

import java.nio.ByteBuffer;

public class HandleRtuTrace {
    private static final Logger log = Logger.getLogger(HandleRtuTrace.class);
    private static final HandleRtuTrace handleRtuTrace = new HandleRtuTrace();
    private static EventTracer tracer = null;

    public static final HandleRtuTrace getHandleRtuTrace() {
        return handleRtuTrace;
    }

    public boolean startTraceRtu(ReceiveMessageEvent event, ByteBuffer inputBody) {
        int count = inputBody.remaining() / 4;
        if (count == 0) return false;
        int[] rtus = new int[count];
        for (int i = 0; i < count; ++i) {
            rtus[i] = inputBody.getInt();
            log.info("跟踪：RTUA=" + HexDump.toHex(rtus[i]));
        }

        if (tracer == null) {
            synchronized (this) {
                tracer = new EventTracer(event.getClient());

                for (IEventHook hook : FasSystem.getFasSystem().getEventHooks()) {
                    hook.setEventTrace(tracer);
                }
            }
        }

        tracer.addClient(event.getClient());
        tracer.traceRtus(rtus);
        return true;
    }

    public boolean stopTrace(ReceiveMessageEvent event) {
        if (tracer == null) return false;
        synchronized (this) {
            int monitorCount = tracer.removeClient(event.getClient());
            if (monitorCount == 0) {
                for (IEventHook hook : FasSystem.getFasSystem().getEventHooks()) {
                    hook.setEventTrace(null);
                }
            }
        }
        return true;
    }

    public void onClientClose(ClientCloseEvent event) {
        if (tracer == null) return;
        synchronized (this) {
            int monitorCount = tracer.removeClient(event.getClient());
            if (monitorCount == 0) {
                for (IEventHook hook : FasSystem.getFasSystem().getEventHooks()) {
                    hook.setEventTrace(null);
                }
                tracer = null;
            }
        }
    }
}