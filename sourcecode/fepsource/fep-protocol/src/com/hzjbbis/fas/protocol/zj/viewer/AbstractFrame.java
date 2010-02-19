package com.hzjbbis.fas.protocol.zj.viewer;

public abstract class AbstractFrame {
    protected byte A1;
    protected byte A2;
    protected byte B1;
    protected byte B2;
    protected int mast;
    protected int fseq;
    protected int ifseq;
    protected int C;
    protected int fexp;
    protected int direction;
    protected int length;
    protected byte cs;
    protected byte rcs;
    protected byte[] frame;

    public AbstractFrame() {
    }

    public AbstractFrame(byte[] frame) {
        if ((frame != null) && (frame.length >= 13)) {
            this.frame = frame;
            fillinfo();
        }
    }

    public AbstractFrame(String data) {
        if ((data == null) || (!(Util.validHex(data)))) return;
        this.frame = null;
        this.frame = new byte[(data.length() >>> 1) + (data.length() & 0x1)];
        Util.HexsToBytes(this.frame, 0, data);
        fillinfo();
    }

    private void fillinfo() {
        this.A1 = this.frame[1];
        this.A2 = this.frame[2];
        this.B1 = this.frame[3];
        this.B2 = this.frame[4];
        this.mast = (this.frame[5] & 0x3F);
        this.fseq = (((this.frame[6] & 0x1F) << 2) + ((this.frame[5] & 0xC0) >>> 6));
        this.ifseq = ((this.frame[6] & 0xE0) >>> 5);
        this.C = (this.frame[8] & 0x3F);
        this.fexp = (((this.frame[8] & 0x40) == 64) ? 1 : 0);
        this.direction = (((this.frame[8] & 0x80) == 128) ? 1 : 0);
        this.length = (((this.frame[10] & 0xFF) << 8) + (this.frame[9] & 0xFF));
        this.cs = this.frame[(this.frame.length - 2)];
        this.rcs = Util.calculateCS(this.frame, 0, this.frame.length - 2);
    }

    protected String getBase() {
        if (this.frame != null) {
            StringBuffer sb = new StringBuffer();
            sb.append((this.direction > 0) ? "传输方向--终端上行" : "传输方向--主站下行");
            sb.append("    ");
            sb.append("终端逻辑地址--").append(Util.ByteToHex(this.A1)).append(Util.ByteToHex(this.A2)).append(Util.ByteToHex(this.B2)).append(Util.ByteToHex(this.B1));

            sb.append("    ");
            sb.append("主站地址--").append(String.valueOf(this.mast));
            sb.append("    ");
            sb.append("帧序号--").append(String.valueOf(this.fseq));
            sb.append("    ");
            sb.append("帧内序号--").append(String.valueOf(this.ifseq));
            sb.append("    ");
            sb.append("数据长度--").append(String.valueOf(this.length));
            sb.append("\n");
            sb.append("CS--").append(Util.ByteToHex(this.cs));
            sb.append("    DATA CS--").append(Util.ByteToHex(this.rcs));
            sb.append("\n");
            return sb.toString();
        }
        return null;
    }

    public String errCode(byte errcode) {
        String err = null;
        switch (errcode & 0xFF) {
            case 0:
                err = "成功";
                break;
            case 1:
                err = "中继命令无返回";
                break;
            case 2:
                err = "设置内容非法";
                break;
            case 3:
                err = "密码权限不足";
                break;
            case 4:
                err = "无此数据项";
                break;
            case 5:
                err = "命令时间失效";
                break;
            case 17:
                err = "目标地址不存在";
                break;
            case 18:
                err = "发送失败";
                break;
            case 19:
                err = "短消息帧太长";
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
        }
        return err;
    }

    public String timeUnit(byte tunit) {
        String ustr = null;
        switch (tunit & 0xFF) {
            case 2:
                ustr = "分钟";
                break;
            case 3:
                ustr = "小时";
                break;
            case 4:
                ustr = "日";
                break;
            case 5:
                ustr = "月";
                break;
            default:
                ustr = "未知时间单位";
        }

        return ustr;
    }

    public abstract String getDescription();
}