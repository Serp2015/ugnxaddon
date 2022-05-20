package com.serp.message;

import nxopen.ListingWindow;
import nxopen.NXException;
import nxopen.Session;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.rmi.RemoteException;

@Service
public class AssemblyListingWindowOutputStream extends OutputStream {
    private ListingWindow m_lw;
    private StringBuffer m_buf;

    public void setSession(Session session) throws NXException, RemoteException {
        this.m_lw = session.listingWindow();
        m_buf = new StringBuffer();
        if (!m_lw.isOpen())
            m_lw.open();
    }

    public void write(int b) throws IOException {
        char c = (char) b;
        try {
            if (c == '\n') {
                m_lw.writeLine(m_buf.toString());
                m_buf.delete(0, m_buf.length());
            } else
                m_buf.append(c);
        } catch (NXException ex) {
            throw new IOException(ex.toString());
        }
    }

    public void close() throws IOException {
        try {
            m_lw.close();
        } catch (NXException ex) {
            throw new IOException(ex.toString());
        }
    }
}
