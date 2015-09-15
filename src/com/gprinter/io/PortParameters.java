//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gprinter.io;

public class PortParameters {
    public static final int SERIAL = 0;
    public static final int PARALLEL = 1;
    public static final int USB = 2;
    public static final int ETHERNET = 3;
    public static final int BLUETOOTH = 4;
    public static final int UNDEFINE = 5;
    private boolean mbPortOpen = false;
    private String mBluetoothAddr = null;
    private String mUsbDeviceName = null;
    private String mIpAddr = null;
    private int mPortType = 0;
    private int mPortNumber = 0;

    public PortParameters() {
        this.mBluetoothAddr = "";
        this.mUsbDeviceName = "";
        this.mIpAddr = "192.168.123.100";
        this.mPortNumber = 9100;
        this.mPortType = 5;
    }

    public void setBluetoothAddr(String adr) {
        this.mBluetoothAddr = adr;
    }

    public String getBluetoothAddr() {
        return this.mBluetoothAddr;
    }

    public void setUsbDeviceName(String name) {
        this.mUsbDeviceName = name;
    }

    public String getUsbDeviceName() {
        return this.mUsbDeviceName;
    }

    public void setIpAddr(String adr) {
        this.mIpAddr = adr;
    }

    public String getIpAddr() {
        return this.mIpAddr;
    }

    public void setPortType(int PortType) {
        this.mPortType = PortType;
    }

    public int getPortType() {
        return this.mPortType;
    }

    public void setPortNumber(int number) {
        this.mPortNumber = number;
    }

    public int getPortNumber() {
        return this.mPortNumber;
    }

    public void setPortOpenState(boolean state) {
        this.mbPortOpen = state;
    }

    public boolean getPortOpenState() {
        return this.mbPortOpen;
    }
}
