package com.serp.createassemble;

import com.serp.message.WindowOutputStream;
import nxopen.NXException;
import nxopen.Part;
import nxopen.Point3d;
import nxopen.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;

@Service
public class MoveWorkCoordinateSystem {
    @Autowired
    private WindowOutputStream out;

    public void moveToCentroid(Session theSession, Part workPart, Point3d centroid)
            throws NXException, java.rmi.RemoteException {
        out.setSession(theSession);
        try {
            workPart.wcs().setOrigin(centroid);
            int id1 = theSession.newestVisibleUndoMark();
            theSession.updateManager().doUpdate(id1);
        } catch (Exception e) {
            out.printMessage().println("MoveWorkCoordinateSystem move - " + e.getMessage());
        }
    }

    public void moveToAbsolute(Session theSession, Part workPart) throws NXException, RemoteException {
        out.setSession(theSession);
        try {
            Point3d origin = new Point3d(0.0, 0.0, 0.0);
            workPart.wcs().setOrigin(origin);
            int id1 = theSession.newestVisibleUndoMark();
            theSession.updateManager().doUpdate(id1);
        } catch (Exception e) {
            out.printMessage().println("MoveWorkCoordinateSystem move - " + e.getMessage());
        }
    }
}
