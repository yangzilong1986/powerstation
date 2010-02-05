package com.hisun.pipeline;

import java.util.ArrayList;
import java.util.List;

public class PipelineBuilderImpl implements PipelineBuilder {
    private final BridgeBuilder builder;

    public PipelineBuilderImpl(BridgeBuilder builder) {
        this.builder = builder;
    }

    public Object buildPipeline(Object filter, Object terminator) {
        ArrayList list = new ArrayList();
        list.add(filter);
        return buildPipeline(list, terminator);
    }

    public Object buildPipeline(List filters, Object terminator) {
        if (filters.isEmpty()) {
            return terminator;
        }
        Object next = terminator;
        int count = filters.size();

        for (int i = count - 1; i >= 0; --i) {
            Object filter = filters.get(i);
            next = this.builder.instantiateBridge(next, filter);
        }

        return next;
    }
}