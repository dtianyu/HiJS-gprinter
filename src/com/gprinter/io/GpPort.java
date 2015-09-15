//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gprinter.io;


import com.gprinter.command.GpCom.ERROR_CODE;
import java.util.Vector;

public abstract class GpPort {
    
    protected boolean mClosePort;
    protected int mState;
    protected int mmBytesAvailable;
    protected int mPrinterId;

    public GpPort() {
    }

    abstract void connect();

    abstract void stop();

    abstract ERROR_CODE writeDataImmediately(Vector<Byte> var1);

    protected synchronized void setState(int state) {
        this.mState = state;
    }

    protected int getState() {
        return this.mState;
    }

    protected void connectionFailed() {
        this.setState(0);
    }

    protected void closePortFailed() {
        this.setState(0);
    }

    protected void connectionLost() {
        this.setState(0);
    }

    protected void invalidPrinter() {
        this.setState(0);
    }

    protected void connectionToPrinterFailed() {
        this.setState(0);
    }
}
