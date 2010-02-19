package com.hzjbbis.fas.protocol.zj.viewer;

public class FrameC0A extends AbstractFrame {
    public static final String FUNC_NAME = "告警确认";

    public FrameC0A() {
    }

    public FrameC0A(byte[] frame) {
        super(frame);
    }

    public FrameC0A(String data) {
        super(data);
    }

    public String getDescription() {
        if (this.frame != null) {
            StringBuffer sb = new StringBuffer();
            sb.append(super.getBase());
            sb.append("命令类型--").append("告警确认");
            sb.append("\n");
            sb.append("数据--").append(Util.BytesToHex(this.frame, 11, this.length));
            return sb.toString();
        }
        return null;
    }
}