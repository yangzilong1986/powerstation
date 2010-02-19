package com.hzjbbis.fas.protocol.handler;

import com.hzjbbis.fas.protocol.codec.MessageCodecFactory;
import com.hzjbbis.fas.protocol.conf.CodecFactoryConfig;
import com.hzjbbis.fas.protocol.conf.ProtocolHandlerConfig;
import com.hzjbbis.fas.protocol.conf.ProtocolProviderConfig;
import com.hzjbbis.fas.protocol.meter.MeterProtocolFactory;
import com.hzjbbis.util.CastorUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProtocolHandlerFactory {
    private static final String CONFIG_MAPPING = "/com/hzjbbis/fas/protocol/conf/protocol-provider-config-mapping.xml";
    private static final String CONFIG_RESOURCE = "/com/hzjbbis/fas/protocol/conf/protocol-provider-config.xml";
    private static ProtocolHandlerFactory instance;
    private Map handlers = new HashMap();

    private ProtocolHandlerFactory() {
        init();
    }

    public static ProtocolHandlerFactory getInstance() {
        if (instance == null) {
            synchronized (ProtocolHandlerFactory.class) {
                if (instance == null) {
                    instance = new ProtocolHandlerFactory();
                }
            }
        }
        return instance;
    }

    public ProtocolHandler getProtocolHandler(Class messageType) {
        return ((ProtocolHandler) this.handlers.get(messageType.getName()));
    }

    private void init() {
        ProtocolProviderConfig config = (ProtocolProviderConfig) CastorUtil.unmarshal("/com/hzjbbis/fas/protocol/conf/protocol-provider-config-mapping.xml", "/com/hzjbbis/fas/protocol/conf/protocol-provider-config.xml");

        List handlerConfigs = config.getHandlers();
        for (int i = 0; i < handlerConfigs.size(); ++i) {
            ProtocolHandlerConfig handlerConfig = (ProtocolHandlerConfig) handlerConfigs.get(i);
            ProtocolHandler handler = (ProtocolHandler) newInstance(handlerConfig.getHandlerClass());
            CodecFactoryConfig codecFactoryConfig = handlerConfig.getCodecFactory();
            if (codecFactoryConfig != null) {
                MessageCodecFactory codecFactory = (MessageCodecFactory) newInstance(codecFactoryConfig.getFactoryClass());

                codecFactory.setConfig(codecFactoryConfig);
                handler.setCodecFactory(codecFactory);
            }

            this.handlers.put(handlerConfig.getMessageType(), handler);
        }

        MeterProtocolFactory.createMeterProtocolDataSet("ZJMeter");
        MeterProtocolFactory.createMeterProtocolDataSet("BBMeter");
    }

    private Object newInstance(String className) {
        try {
            Class clazz = Class.forName(className);
            return clazz.newInstance();
        } catch (Exception ex) {
            throw new RuntimeException("Error to instantiating class: " + className, ex);
        }
    }
}