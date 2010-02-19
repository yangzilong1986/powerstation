package com.hzjbbis.fas.protocol.conf;

import java.util.List;

public class CodecFactoryConfig {
    private String factoryClass;
    private String dataConfigMapping;
    private String dataConfigResource;
    private List codecs;

    public String getFactoryClass() {
        return this.factoryClass;
    }

    public void setFactoryClass(String factoryClass) {
        this.factoryClass = factoryClass;
    }

    public String getDataConfigMapping() {
        return this.dataConfigMapping;
    }

    public void setDataConfigMapping(String dataConfigMapping) {
        this.dataConfigMapping = dataConfigMapping;
    }

    public String getDataConfigResource() {
        return this.dataConfigResource;
    }

    public void setDataConfigResource(String dataConfigResource) {
        this.dataConfigResource = dataConfigResource;
    }

    public List getCodecs() {
        return this.codecs;
    }

    public void setCodecs(List codecs) {
        this.codecs = codecs;
    }
}