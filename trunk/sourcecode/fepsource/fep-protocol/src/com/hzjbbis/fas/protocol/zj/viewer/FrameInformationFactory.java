package com.hzjbbis.fas.protocol.zj.viewer;

public class FrameInformationFactory {
    public static String getFrameInformation(byte[] frame) {
        if ((frame != null) && (frame.length >= 13)) {
            int func = frame[8] & 0x3F;
            AbstractFrame aframe = null;
            switch (func) {
                case 0:
                    aframe = new FrameC00(frame);
                    break;
                case 1:
                    aframe = new FrameC01(frame);
                    break;
                case 2:
                    aframe = new FrameC02(frame);
                    break;
                case 4:
                    aframe = new FrameC04(frame);
                    break;
                case 7:
                    aframe = new FrameC07(frame);
                    break;
                case 8:
                    aframe = new FrameC08(frame);
                    break;
                case 9:
                    aframe = new FrameC09(frame);
                    break;
                case 10:
                    aframe = new FrameC0A(frame);
                    break;
                case 33:
                    aframe = new FrameC21(frame);
                    break;
                case 36:
                    aframe = new FrameC24(frame);
                case 3:
                case 5:
                case 6:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 34:
                case 35:
            }
            if (aframe != null) {
                return aframe.getDescription();
            }
        }
        return null;
    }

    public static String getFrameInformation(String frame) {
        if (frame != null) {
            String data = frame.replaceAll(" ", "");
            int index = data.indexOf("68");
            if (index > 0) {
                data = data.substring(index);
            }
            if (Util.validHex(data)) {
                byte[] bframe = new byte[(data.length() >>> 1) + (data.length() & 0x1)];
                Util.HexsToBytes(bframe, 0, data);
                return getFrameInformation(bframe);
            }
        }
        return null;
    }
}