package com.serp.message;

import nxopen.ListingWindow;
import nxopen.NXException;
import nxopen.Session;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;

@Service
public class ListingWindowImpl {

    private Session session;

    public void setSession(Session session) {
        this.session = session;
    }

    public void print(String report) throws NXException, RemoteException {
        ListingWindow listingWindow = session.listingWindow();
        listingWindow.open();
        listingWindow.writeLine(report);
        listingWindow.close();
    }
}
