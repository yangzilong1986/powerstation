package com.hzjbbis.fas.protocol.zj.viewer;

public class FrameC02 extends AbstractFrame {
    public static final String FUNC_NAME = "终端任务数据";

    public FrameC02() {
    }

    public FrameC02(byte[] frame) {
        super(frame);
    }

    public FrameC02(String data) {
        super(data);
    }

    public String getDescription() {
        if (this.frame != null) {
            StringBuffer sb = new StringBuffer();
            sb.append(super.getBase());
            sb.append("命令类型--").append("终端任务数据");
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
                buffer.append("任务号--").append(this.frame[11] & 0xFF).append("    ");
                buffer.append("起始任务数据采集时间---");
                buffer.append("20").append(Util.ByteToHex(this.frame[12])).append("-").append(Util.ByteToHex(this.frame[13])).append("-").append(Util.ByteToHex(this.frame[14])).append(" ").append(Util.ByteToHex(this.frame[15])).append(":").append(Util.ByteToHex(this.frame[16])).append(":00\n");

                buffer.append("本次上送数据点数--").append(this.frame[17] & 0xFF).append("    ");
                buffer.append("数据点采集间隔时间--").append(this.frame[19] & 0xFF).append(timeUnit(this.frame[18])).append("\n");
                buffer.append("任务数据--").append(Util.BytesToHex(this.frame, 20, this.length - 9));
            }
        } catch (Exception e) {
        }
    }

    private void descMastCmd(StringBuffer buffer) {
        try {
            buffer.append("任务号--").append(this.frame[11] & 0xFF).append("    ");
            buffer.append("起始任务数据采集时间---");
            buffer.append("20").append(Util.ByteToHex(this.frame[12])).append("-").append(Util.ByteToHex(this.frame[13])).append("-").append(Util.ByteToHex(this.frame[14])).append(" ").append(Util.ByteToHex(this.frame[15])).append(":").append(Util.ByteToHex(this.frame[16])).append(":00\n");

            buffer.append("本次召测数据点数--").append(this.frame[17] & 0xFF).append("    ");
            buffer.append("数据点间隔倍率--").append(this.frame[18] & 0xFF).append("\n");
        } catch (Exception e) {
        }
    }
}