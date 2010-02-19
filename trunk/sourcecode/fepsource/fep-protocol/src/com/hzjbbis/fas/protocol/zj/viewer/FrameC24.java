package com.hzjbbis.fas.protocol.zj.viewer;

public class FrameC24 extends AbstractFrame {
    public static final String FUNC_NAME = "心跳";

    public FrameC24() {
    }

    public FrameC24(byte[] frame) {
        super(frame);
    }

    public FrameC24(String data) {
        super(data);
    }

    public String getDescription() {
        if (this.frame != null) {
            StringBuffer sb = new StringBuffer();
            sb.append(super.getBase());
            sb.append("命令类型--").append("心跳");
            sb.append("\n");
            sb.append("数据--").append(Util.BytesToHex(this.frame, 11, this.length));
            return sb.toString();
        }
        return null;
    }
}