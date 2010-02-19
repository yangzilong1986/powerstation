package com.hzjbbis.fas.protocol.zj;

public abstract class ErrorCode {
    public static final byte CMD_OK = 0;
    public static final byte FWD_CMD_NO_RESPONSE = 1;
    public static final byte ILLEGAL_DATA = 2;
    public static final byte INVALID_PASSWORD = 3;
    public static final byte NO_DATA = 4;
    public static final byte CMD_TIMEOUT = 5;
    public static final byte TARGET_NOT_EXISTS = 17;
    public static final byte CMD_SEND_FAILURE = 18;
    public static final byte FRAME_TOO_LONG = 19;
    public static final byte RESPONSE_TIMEOUT = -15;
    public static final byte MST_SEND_FAILURE = -14;
    public static final byte RTU_DISCONNECT = -13;
    public static final byte ILLEGAL_PACKET = -1;

    public static String toHostCommandStatus(byte errorCode) {
        switch (errorCode) {
            case 0:
                return "1";
            case 1:
                return "5";
            case 2:
                return "6";
            case 3:
                return "7";
            case 4:
                return "8";
            case 5:
                return "9";
            case 17:
                return "10";
            case 18:
                return "11";
            case 19:
                return "12";
            case -14:
                return "3";
            case -1:
                return "14";
            case -15:
                return "4";
            case -13:
            case -12:
            case -11:
            case -10:
            case -9:
            case -8:
            case -7:
            case -6:
            case -5:
            case -4:
            case -3:
            case -2:
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
        return "3";
    }
}