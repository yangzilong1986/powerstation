package com.hzjbbis.fk.common.events.event;

import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.IProfile;
import com.hzjbbis.fk.message.IMessage;

import java.text.DecimalFormat;

public class MemoryProfileEvent implements IEvent, IProfile {
    private EventType type = EventType.SYS_MEMORY_PROFILE;
    public static long maxMemory = Runtime.getRuntime().maxMemory() >> 10;
    public static long totalMemory = Runtime.getRuntime().totalMemory() >> 10;
    public static long freeMemory = Runtime.getRuntime().freeMemory() >> 10;
    private final DecimalFormat decimalFormat = new DecimalFormat("#,###");

    private void update() {
        maxMemory = Runtime.getRuntime().maxMemory() >> 10;
        totalMemory = Runtime.getRuntime().totalMemory() >> 10;
        freeMemory = Runtime.getRuntime().freeMemory() >> 10;
    }

    public IMessage getMessage() {
        return null;
    }

    public Object getSource() {
        return null;
    }

    public EventType getType() {
        return this.type;
    }

    public void setSource(Object src) {
    }

    public String profile() {
        update();
        StringBuffer sb = new StringBuffer(128);
        sb.append("<memory-profile type=\"memory\">");
        sb.append("\r\n        <totalMemory>");
        if (totalMemory > 1024L) sb.append(this.decimalFormat.format(totalMemory >> 10)).append("M</totalMemory>");
        else {
            sb.append(this.decimalFormat.format(totalMemory)).append("K</totalMemory>");
        }
        sb.append("\r\n        <freeMemory>");
        if (freeMemory > 1024L) sb.append(this.decimalFormat.format(freeMemory >> 10)).append("M</freeMemory>");
        else {
            sb.append(this.decimalFormat.format(freeMemory)).append("K</freeMemory>");
        }
        sb.append("\r\n        <maxMemory>");
        if (maxMemory > 1024L) sb.append(this.decimalFormat.format(maxMemory >> 10)).append("M</maxMemory>");
        else sb.append(this.decimalFormat.format(maxMemory)).append("K</maxMemory>");
        sb.append("\r\n    </memory-profile>");
        return sb.toString();
    }

    public String toString() {
        return profile();
    }
}