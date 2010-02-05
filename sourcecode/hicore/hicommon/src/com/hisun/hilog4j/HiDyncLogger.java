package com.hisun.hilog4j;

import com.hisun.util.HiICSProperty;
import org.apache.commons.lang.math.NumberUtils;

public class HiDyncLogger extends Logger implements HiCloseable {
    protected int maxQueueSize;

    public HiDyncLogger(String name) {
        super(name);
    }

    public HiDyncLogger(String name, String level) {
        super(name, level);
    }

    public HiDyncLogger(String name, Level level) {
        super(name, level);
    }

    public HiDyncLogger(IFileName name) {
        super(name);
    }

    public HiDyncLogger(IFileName name, String level) {
        super(name, level);
    }

    public HiDyncLogger(IFileName name, Level level) {
        super(name, level);
    }

    protected void construct(IFileName name, Level level) {
        String tmp2 = HiICSProperty.getProperty("log.limits_lines", "20");
        this.limitsLines = NumberUtils.toInt(tmp2);
        tmp2 = HiICSProperty.getProperty("log.limits_size", "20");
        this.limitsSize = (NumberUtils.toInt(tmp2) * 1024 * 1024);
        if (this.limitsSize <= 0) {
            this.limitsSize = DEFAULT_LIMIT_SIZE;
        }
        this.level = level;
        this.fileName = name;
        tmp2 = HiICSProperty.getProperty("log.max_queue_size");
        this.maxQueueSize = NumberUtils.toInt(tmp2);
        if (this.maxQueueSize <= 0) {
            this.maxQueueSize = DEFAULT_QUEUE_MAX_SIZE;
        }

        this.logCache = new HiDirectLogCache(this.limitsSize);
    }
}