package com.hisun.framework;

import com.hisun.exception.HiException;
import com.hisun.framework.parser.HiDigesterParserImp;
import com.hisun.pipeline.PipelineBuilder;
import com.hisun.pipeline.PipelineBuilderFactory;
import com.hisun.pubinterface.IHandler;
import com.hisun.pubinterface.IHandlerFilter;
import com.hisun.service.HiInfrastructure;
import com.hisun.service.IObjectDecorator;
import com.hisun.util.HiResource;
import org.apache.hivemind.Registry;
import org.apache.hivemind.lib.BeanFactory;

public class HiFrameworkBuilder {
    public static PipelineBuilder handlerBuilder;

    public static Registry getRegistry() {
        return HiInfrastructure.getRegistry();
    }

    public static HiConfigParser getParser() {
        return new HiDigesterParserImp();
    }

    public static BeanFactory getHandlerFactory() {
        return ((BeanFactory) getRegistry().getService("icsframework.handlerFactory", BeanFactory.class));
    }

    public static BeanFactory getCommFactory() {
        return ((BeanFactory) getRegistry().getService("icsframework.commFactory", BeanFactory.class));
    }

    public static PipelineBuilder getPipelineBuilder() {
        if (handlerBuilder == null) {
            PipelineBuilderFactory factory = (PipelineBuilderFactory) getRegistry().getService("icsframework.PipelineBuilderFactory", PipelineBuilderFactory.class);

            handlerBuilder = factory.createPipelineBuilder(IHandler.class, IHandlerFilter.class);
        }
        return handlerBuilder;
    }

    public static IObjectDecorator getObjectDecorator() throws HiException {
        try {
            Class clazz = HiResource.loadClass("com.hisun.framework.decorator.ServerDecorator");

            return ((IObjectDecorator) clazz.newInstance());
        } catch (Exception e) {
            throw HiException.makeException(e);
        }
    }
}