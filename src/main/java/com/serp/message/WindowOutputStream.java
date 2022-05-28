package com.serp.message;

import nxopen.ListingWindow;
import nxopen.NXException;
import nxopen.Session;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.rmi.RemoteException;

//class for outputting information to the UGNX console
@Service
public class WindowOutputStream extends OutputStream {
    private ListingWindow mLw;
    private StringBuffer mBuf;

    public PrintStream getPrintStream() {
        return new PrintStream(this);
    }

    public void setSession(Session session) throws NXException, RemoteException {
        this.mLw = session.listingWindow();
        mBuf = new StringBuffer();
        if (!mLw.isOpen())
            mLw.open();
    }

    @Override
    public void write(int b) throws IOException {
        char c = (char) b;
        try {
            if (c == '\n') {
                mLw.writeLine(mBuf.toString());
                mBuf.delete(0, mBuf.length());
            } else
                mBuf.append(c);
        } catch (NXException ex) {
            throw new IOException(ex.toString());
        }
    }

    @Override
    public void close() throws IOException {
        try {
            mLw.close();
        } catch (NXException ex) {
            throw new IOException(ex.toString());
        }
    }
}
