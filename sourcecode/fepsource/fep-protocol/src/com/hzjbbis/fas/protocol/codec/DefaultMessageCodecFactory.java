package com.hzjbbis.fas.protocol.codec;

import com.hzjbbis.fas.protocol.conf.CodecConfig;
import com.hzjbbis.fas.protocol.conf.CodecFactoryConfig;
import com.hzjbbis.fas.protocol.conf.ProtocolDataConfig;
import com.hzjbbis.util.CastorUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultMessageCodecFactory implements MessageCodecFactory {
    private static final Log log = LogFactory.getLog(DefaultMessageCodecFactory.class);
    private ProtocolDataConfig dataConfig;
    private Map encoders;
    private Map decoders;

    public void setConfig(CodecFactoryConfig config) {
        init(config);
    }

    public MessageEncoder getEncoder(int funCode) {
        return ((MessageEncoder) this.encoders.get(new Integer(funCode)));
    }

    public MessageDecoder getDecoder(int funCode) {
        return ((MessageDecoder) this.decoders.get(new Integer(funCode)));
    }

    private void init(CodecFactoryConfig config) {
        try {
            this.dataConfig = ((ProtocolDataConfig) CastorUtil.unmarshal(config.getDataConfigMapping(), config.getDataConfigResource()));

            this.dataConfig.fillMap();
            this.encoders = new HashMap();
            this.decoders = new HashMap();
            List codecConfigs = config.getCodecs();
            if (codecConfigs != null) for (int i = 0; i < codecConfigs.size(); ++i) {
                CodecConfig codecConfig = (CodecConfig) codecConfigs.get(i);
                Integer funCode = new Integer(codecConfig.getFunCode());

                if (codecConfig.getEncoderClass() != null) {
                    MessageEncoder encoder = (MessageEncoder) newInstance(codecConfig.getEncoderClass());

                    encoder.setDataConfig(this.dataConfig);
                    this.encoders.put(funCode, encoder);
                }

                if (codecConfig.getDecoderClass() != null) {
                    MessageDecoder decoder = (MessageDecoder) newInstance(codecConfig.getDecoderClass());

                    decoder.setDataConfig(this.dataConfig);
                    this.decoders.put(funCode, decoder);
                }
            }
        } catch (Exception e) {
            log.error("load protocol setting", e);
        }
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