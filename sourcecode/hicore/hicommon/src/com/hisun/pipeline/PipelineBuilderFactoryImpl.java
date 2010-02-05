package com.hisun.pipeline;

import org.apache.hivemind.service.ClassFactory;
import org.apache.hivemind.service.impl.ClassFactoryImpl;

public class PipelineBuilderFactoryImpl implements PipelineBuilderFactory {
    public PipelineBuilder createPipelineBuilder(Class serviceInterface, Class filterInterface) {
        ClassFactory _classFactory = new ClassFactoryImpl();
        BridgeBuilder builder = new BridgeBuilder(null, "pipeline", serviceInterface, filterInterface, _classFactory);

        return new PipelineBuilderImpl(builder);
    }
}