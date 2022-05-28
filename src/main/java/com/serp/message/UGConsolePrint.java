package com.serp.message;

import nxopen.NXException;
import nxopen.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.rmi.RemoteException;

//class for outputting information to the UGNX console
@Service
public class UGConsolePrint extends PrintStream {
    @Autowired
    private static WindowOutputStream windowOutputStream;

    public UGConsolePrint() {
        super(windowOutputStream);
    }

    public void setSession(Session session) throws NXException, RemoteException {
        windowOutputStream.setSession(session);
    }
}
