package com.hzjbbis.fas.protocol.zj.viewer;

public class FrameC00 extends AbstractFrame {
    public static final String FUNC_NAME = "中继";

    public FrameC00() {
    }

    public FrameC00(byte[] frame) {
        super(frame);
    }

    public FrameC00(String data) {
        super(data);
    }

    public String getDescription() {
        if (this.frame != null) {
            StringBuffer sb = new StringBuffer();
            sb.append(super.getBase());
            sb.append("命令类型--").append("中继");
            sb.append("\n");

            if (this.direction > 0) descRtuReply(sb);
            else {
                descMastCmd(sb);
            }
            return sb.toString();
        }
        return null;
    }

    private void descMastCmd(StringBuffer buffer) {
        try {
            buffer.append("端口号--").append(Util.BytesToHex(this.frame, 11, 1));
            buffer.append("    ");
            buffer.append("超时时间--").append(this.frame[12] & 0xFF);
            buffer.append("    ");
            buffer.append("特征字节--").append(Util.BytesToHex(this.frame, 13, 1));
            buffer.append("    ");
            int loc = (this.frame[14] & 0xFF) + ((this.frame[15] & 0xFF) << 8);
            buffer.append("截取开始--").append(loc);
            buffer.append("    ");
            loc = (this.frame[16] & 0xFF) + ((this.frame[17] & 0xFF) << 8);
            buffer.append("截取长度--").append(loc);
            buffer.append("    ");
            buffer.append("中继命令--").append(Util.BytesToHex(this.frame, 18, this.length - 7));
        } catch (Exception e) {
        }
    }

    private void descRtuReply(StringBuffer buffer) {
        try {
            if (this.fexp > 0) {
                buffer.append("中继失败--").append(errCode(this.frame[11]));
            } else {
                buffer.append("端口号--").append(Util.BytesToHex(this.frame, 11, 1));
                buffer.append("\n");
                buffer.append("中继返回--").append(Util.BytesToHex(this.frame, 12, this.length - 1));
            }
        } catch (Exception e) {
        }
    }
}