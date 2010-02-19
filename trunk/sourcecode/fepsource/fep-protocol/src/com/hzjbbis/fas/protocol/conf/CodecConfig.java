package com.hzjbbis.fas.protocol.conf;

public class CodecConfig {
    private int funCode;
    private String encoderClass;
    private String decoderClass;

    public int getFunCode() {
        return this.funCode;
    }

    public void setFunCode(int funCode) {
        this.funCode = funCode;
    }

    public String getEncoderClass() {
        return this.encoderClass;
    }

    public void setEncoderClass(String encoderClass) {
        this.encoderClass = encoderClass;
    }

    public String getDecoderClass() {
        return this.decoderClass;
    }

    public void setDecoderClass(String decoderClass) {
        this.decoderClass = decoderClass;
    }
}