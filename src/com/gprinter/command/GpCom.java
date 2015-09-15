//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gprinter.command;

public class GpCom {
    public static final int STATE_NO_ERR = 0;
    public static final int STATE_OFFLINE = 1;
    public static final int STATE_PAPER_ERR = 2;
    public static final int STATE_COVER_OPEN = 4;
    public static final int STATE_ERR_OCCURS = 8;
    public static final int STATE_TIMES_OUT = 16;
    public static final String ACTION_CONNECT_STATUS = "action.connect.status";
    public static final String ACTION_PRINTER_CALLBACK = "action.printer.callback";
    public static final int ESC_COMMAND = 0;
    public static final int TSC_COMMAND = 1;

    public GpCom() {
    }

    public static String getErrorText(GpCom.ERROR_CODE errorcode) {
        String s;
        switch(errorcode.ordinal()) {
        case 1:
            s = "success";
            break;
        case 2:
            s = "Failed";
            break;
        case 3:
            s = "timeout";
            break;
        case 4:
            s = "Invalid device paramters";
            break;
        case 5:
            s = "Device already open";
            break;
        case 6:
            s = "Invalid port number";
            break;
        case 7:
            s = "Invalid ip address";
            break;
        case 8:
            s = "Invalid callback object";
            break;
        case 9:
            s = "Bluetooth is not support by the device";
            break;
        case 10:
            s = "Please open bluetooth";
            break;
        case 11:
            s = "Port is not open";
            break;
        case 12:
            s = "Invalid bluetooth address";
            break;
        case 13:
            s = "Port is disconnect";
            break;
        default:
            s = "Unknown error code";
        }

        return s;
    }

    public static enum ERROR_CODE {
        SUCCESS,
        FAILED,
        TIMEOUT,
        INVALID_DEVICE_PARAMETERS,
        DEVICE_ALREADY_OPEN,
        INVALID_PORT_NUMBER,
        INVALID_IP_ADDRESS,
        INVALID_CALLBACK_OBJECT,
        BLUETOOTH_IS_NOT_SUPPORT,
        OPEN_BLUETOOTH,
        PORT_IS_NOT_OPEN,
        INVALID_BLUETOOTH_ADDRESS,
        PORT_IS_DISCONNECT;

        private ERROR_CODE() {
        }
    }
}
