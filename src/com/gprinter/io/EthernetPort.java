//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
package com.gprinter.io;

import com.gprinter.command.GpCom.ERROR_CODE;
import com.gprinter.io.GpPort;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EthernetPort extends GpPort {

    private static final String DEBUG_TAG = "EthernetService";
    private String mIp;
    private int mPortNumber;
    private EthernetPort.ConnectThread mConnectThread = null;
    private EthernetPort.ConnectedThread mConnectedThread = null;

    public EthernetPort(int id, String Ip, int Port) {
        this.mState = 0;
        this.mPortNumber = Port;
        this.mIp = Ip;
        this.mPrinterId = id;
    }

    @Override
    public synchronized void connect() {

        if (this.mConnectThread != null) {
            this.mConnectThread.cancel();
            this.mConnectThread = null;
        }

        if (this.mConnectedThread != null) {
            this.mConnectedThread.cancel();
            this.mConnectedThread = null;
        }

        this.mConnectThread = new EthernetPort.ConnectThread(this.mIp, this.mPortNumber);
        this.mConnectThread.start();
        this.setState(2);
    }

    public synchronized void connected(Socket socket, String ip) {

        if (this.mConnectThread != null) {
            this.mConnectThread.cancel();
            this.mConnectThread = null;
        }

        if (this.mConnectedThread != null) {
            this.mConnectedThread.cancel();
            this.mConnectedThread = null;
        }

        this.mConnectedThread = new EthernetPort.ConnectedThread(socket);
        this.mConnectedThread.start();

        this.setState(3);

    }

    @Override
    public synchronized void stop() {

        this.setState(0);
        if (this.mConnectThread != null) {
            this.mConnectThread.cancel();
            this.mConnectThread = null;
        }

        if (this.mConnectedThread != null) {
            this.mConnectedThread.cancel();
            this.mConnectedThread = null;
        }

    }

    @Override
    public ERROR_CODE writeDataImmediately(Vector<Byte> data) {
        ERROR_CODE retval = ERROR_CODE.SUCCESS;
        EthernetPort.ConnectedThread r;
        synchronized (this) {
            if (this.mState != 3) {
                return ERROR_CODE.PORT_IS_NOT_OPEN;
            }
            r = this.mConnectedThread;
        }

        retval = r.writeDataImmediately(data);
        return retval;
    }

    private class ConnectThread extends Thread {

        private Socket mmSocket = new Socket();
        private String mmIp;
        InetAddress mmIpAddress;
        SocketAddress mmRemoteAddr;

        public ConnectThread(String Ip, int Port) {
            try {
                this.mmIpAddress = Inet4Address.getByName(Ip);
                this.mmRemoteAddr = new InetSocketAddress(this.mmIpAddress, Port);
                this.mmIp = Ip;
            } catch (UnknownHostException var5) {
                EthernetPort.this.connectionFailed();
            }

        }

        @Override
        public void run() {

            this.setName("ConnectThread");
            try {
                this.mmSocket.connect(this.mmRemoteAddr, 4000);
            } catch (IOException var5) {

                EthernetPort.this.connectionFailed();

                try {
                    this.mmSocket.close();
                } catch (IOException var3) {

                }

                EthernetPort.this.stop();
                return;
            }

            EthernetPort e = EthernetPort.this;
            synchronized (EthernetPort.this) {
                EthernetPort.this.mConnectThread = null;
            }
            //改变连接状态
            EthernetPort.this.connected(this.mmSocket, this.mmIp);
        }

        public void cancel() {
            try {
                this.mmSocket.close();
            } catch (IOException var2) {
                EthernetPort.this.closePortFailed();
            }

        }
    }

    private class ConnectedThread extends Thread {

        private final Socket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(Socket socket) {
            //Log.d("EthernetService", "create ConnectedThread");
            this.mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException var6) {
                //Log.e("EthernetService", "temp sockets not created", var6);
            }

            this.mmInStream = tmpIn;
            this.mmOutStream = tmpOut;
        }

        @Override
        public void run() {
            //Log.i("EthernetService", "BEGIN mConnectedThread");
            EthernetPort.this.mClosePort = false;

            while (!EthernetPort.this.mClosePort) {
                try {
                    EthernetPort.this.mmBytesAvailable = this.mmInStream.available();
                    if (EthernetPort.this.mmBytesAvailable > 0) {
                        byte[] e = new byte[100];
                        int bytes = this.mmInStream.read(e);

                        if (bytes <= 0) {

                            EthernetPort.this.connectionLost();
                            EthernetPort.this.stop();
                            break;
                        }

                    }
                } catch (IOException var5) {
                    EthernetPort.this.connectionLost();

                    break;
                }
            }

        }

        public void cancel() {
            try {
                EthernetPort.this.mClosePort = true;
                this.mmOutStream.flush();
                this.mmSocket.close();
            } catch (IOException var2) {
                EthernetPort.this.closePortFailed();
            }

        }

        public ERROR_CODE writeDataImmediately(Vector<Byte> data) {
            ERROR_CODE retval = ERROR_CODE.SUCCESS;
            if (this.mmSocket != null && this.mmOutStream != null) {
                if (data != null && data.size() > 0) {
                    byte[] sendData = new byte[data.size()];
                    if (data.size() > 0) {
                        for (int e = 0; e < data.size(); ++e) {
                            sendData[e] = ((Byte) data.get(e)).byteValue();
                        }
                        try {
                            this.mmOutStream.write(sendData);
                            this.mmOutStream.flush();

                        } catch (Exception var5) {
                            var5.printStackTrace();
                            retval = ERROR_CODE.FAILED;
                        }
                    }
                }
            } else {
                retval = ERROR_CODE.PORT_IS_NOT_OPEN;
            }

            return retval;
        }
    }
}
