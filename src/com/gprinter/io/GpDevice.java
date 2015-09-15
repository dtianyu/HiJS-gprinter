//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
package com.gprinter.io;

import com.gprinter.command.GpCom.ERROR_CODE;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;
import org.apache.commons.codec.binary.Base64;

public final class GpDevice {

    private static final String DEBUG_TAG = "GpDevice";
    public static final String CONNECT_ERROR = "connect error";

    private GpPort mPort = null;
    public static final int STATE_NONE = 0;
    public static final int STATE_LISTEN = 1;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_CONNECTED = 3;
    public static final int STATE_INVALID_PRINTER = 4;
    public static final int STATE_VALID_PRINTER = 5;
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    public static final String DEVICE_STATUS = "device_status";
    public static final String DEVICE_READ = "device.read";
    public static final String DEVICE_READ_CNT = "device.readcnt";
    private PortParameters mPortParam = null;
    private int mCommandType = 0;
    public Queue<Byte> mReceiveQueue = null;
    private boolean mReceiveDataEnable = false;

    public GpDevice() {
        this.mPort = null;
        this.mPortParam = new PortParameters();
        this.mCommandType = 0;
        this.mReceiveQueue = new LinkedList();
        this.mReceiveDataEnable = false;
    }

    public void setCommandType(int command) {
        this.mCommandType = command;
    }

    public int getCommandType() {
        return this.mCommandType;
    }

    public PortParameters getPortParameters() {
        return this.mPortParam;
    }

    public void setReceiveDataEnable(boolean b) {
        this.mReceiveDataEnable = b;
    }

    public boolean getReceiveDataEnable() {
        return this.mReceiveDataEnable;
    }

    public int getConnectState() {
        int state = 0;
        if (this.mPort != null) {
            state = this.mPort.getState();
        }
        return state;
    }

    public ERROR_CODE openEthernetPort(int id, String ip, int port) {
        ERROR_CODE retval = ERROR_CODE.SUCCESS;
        this.mPortParam.setPortType(3);
        this.mPortParam.setIpAddr(ip);
        this.mPortParam.setPortNumber(port);
        if (port <= 0) {
            retval = ERROR_CODE.INVALID_PORT_NUMBER;
        } else if (ip.length() != 0) {
            try {
                InetAddress.getByName(ip);
                if (this.mPort != null) {
                    if (this.mPort.getState() == 3) {
                        return ERROR_CODE.DEVICE_ALREADY_OPEN;
                    }
                    this.mPort.stop();
                    this.mPort = null;
                }

                this.mPort = new EthernetPort(id, ip, port);
                this.mPort.connect();
            } catch (Exception var7) {

                retval = ERROR_CODE.INVALID_IP_ADDRESS;
            }
        } else {
            //Log.e("GpDevice", "IpAddress is invalid");
            retval = ERROR_CODE.INVALID_IP_ADDRESS;
        }

        return retval;
    }

    public void closePort() {
        if (this.mPort != null) {
            this.mPort.stop();
            this.mPort = null;
        }

    }

    public ERROR_CODE sendDataImmediately(Vector<Byte> Command) {
        ERROR_CODE retval = ERROR_CODE.SUCCESS;
        Vector data = new Vector(Command.size());
        if (this.mPort != null) {
            if (this.mPort.getState() == 3) {
                for (int k = 0; k < Command.size(); ++k) {
                    if (data.size() >= 1024) {
                        retval = this.mPort.writeDataImmediately(data);
                        data.clear();
                        if (retval != ERROR_CODE.SUCCESS) {
                            return retval;
                        }
                    }

                    data.add((Byte) Command.get(k));
                }

                retval = this.mPort.writeDataImmediately(data);
            } else {
                retval = ERROR_CODE.PORT_IS_DISCONNECT;
                //Log.e("GpDevice", "Port is disconnect");
            }
        } else {
            retval = ERROR_CODE.PORT_IS_NOT_OPEN;
            //Log.e("GpDevice", "Port is not open");
        }

        return retval;
    }

    public int sendEscCommand(int PrinterId, String b64) throws RemoteException {
        //Log.d("GpPrintService", "sendEscCommand");
        ERROR_CODE retval = ERROR_CODE.SUCCESS;
        if (this.getCommandType() == 0) {
            byte[] printData = Base64.decodeBase64(b64);
            Vector vector = new Vector();
            byte[] var9 = printData;
            int var8 = printData.length;

            for (int var7 = 0; var7 < var8; ++var7) {
                byte b = var9[var7];
                vector.add(Byte.valueOf(b));
            }
            retval = this.sendDataImmediately(vector);
        } else {
            retval = ERROR_CODE.FAILED;
        }

        return retval.ordinal();
    }
}
