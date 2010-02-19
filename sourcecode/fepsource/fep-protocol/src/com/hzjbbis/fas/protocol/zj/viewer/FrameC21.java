package com.hzjbbis.fas.protocol.zj.viewer;

public class FrameC21 extends AbstractFrame {
    public static final String FUNC_NAME = "登录";

    public FrameC21() {
    }

    public FrameC21(byte[] frame) {
        super(frame);
    }

    public FrameC21(String data) {
        super(data);
    }

    public String getDescription() {
        if (this.frame != null) {
            StringBuffer sb = new StringBuffer();
            sb.append(super.getBase());
            sb.append("命令类型--").append("登录");
            sb.append("\n");
            sb.append("数据--").append(Util.BytesToHex(this.frame, 11, this.length));
            return sb.toString();
        }
        return null;
    }
}