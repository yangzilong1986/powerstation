package com.hzjbbis.fas.protocol.zj.viewer;

public class FrameC09 extends AbstractFrame {
    public static final String FUNC_NAME = "异常告警";

    public FrameC09() {
    }

    public FrameC09(byte[] frame) {
        super(frame);
    }

    public FrameC09(String data) {
        super(data);
    }

    public String getDescription() {
        if (this.frame != null) {
            StringBuffer sb = new StringBuffer();
            sb.append(super.getBase());
            sb.append("命令类型--").append("异常告警");
            sb.append("\n");
            if (this.direction > 0) descRtuReply(sb);
            else {
                descMastCmd(sb);
            }
            return sb.toString();
        }
        return null;
    }

    private void descRtuReply(StringBuffer buffer) {
        try {
            if (this.fexp > 0) {
                buffer.append("异常应答--").append(errCode(this.frame[11]));
            } else {
                buffer.append("本次上送告警条数--").append(this.frame[11] & 0xFF).append("\n");
                buffer.append("告警发生的测量点号--").append(this.frame[12] & 0xFF).append("    ");
                buffer.append("告警发生时间---");
                buffer.append("20").append(Util.ByteToHex(this.frame[13])).append("-").append(Util.ByteToHex(this.frame[14])).append("-").append(Util.ByteToHex(this.frame[15])).append(" ").append(Util.ByteToHex(this.frame[16])).append(":").append(Util.ByteToHex(this.frame[17])).append(":00    ");

                buffer.append("告警编码--").append(Util.ByteToHex(this.frame[19])).append(Util.ByteToHex(this.frame[18])).append("\n");
                buffer.append("告警数据--").append(Util.BytesToHex(this.frame, 20, this.length - 9));
            }
        } catch (Exception e) {
        }
    }

    private void descMastCmd(StringBuffer buffer) {
        try {
            buffer.append("告警发生的测量点号--").append(Util.ByteToHex(this.frame[11])).append("    ");
            buffer.append("告警编码--").append(Util.ByteToHex(this.frame[13])).append(Util.ByteToHex(this.frame[12])).append("    ");
            buffer.append("告警发生时间---");
            buffer.append("20").append(Util.ByteToHex(this.frame[14])).append("-").append(Util.ByteToHex(this.frame[15])).append("-").append(Util.ByteToHex(this.frame[16])).append(" ").append(Util.ByteToHex(this.frame[17])).append(":").append(Util.ByteToHex(this.frame[18])).append(":00\n");

            buffer.append("本次召测告警条数--").append(this.frame[19] & 0xFF).append("\n");
        } catch (Exception e) {
        }
    }
}