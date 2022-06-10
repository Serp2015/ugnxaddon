package com.serp.createassemble;

import com.serp.message.WindowOutputStream;
import nxopen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

@Service
public class Assemble {
    @Autowired
    private FindBodies findBodies;
    @Autowired
    private Measure measure;
    @Autowired
    private Component component;
    @Autowired
    private WindowOutputStream out;
    @Autowired
    private MoveWorkCoordinateSystem coordinateSystem;

    public void createAssemble() throws NXException, RemoteException {
        Session theSession = (Session) SessionFactory.get("Session");
        Part workPart = theSession.parts().work();
        out.setSession(theSession);

        List<Body> bodies = new ArrayList<>(findBodies.findBodies(theSession, workPart));

        for (Body body : bodies) {
            try {
                coordinateSystem.moveToAbsolute(theSession, workPart);
                Point3d centroid = measure.measureBody(theSession, workPart, body).centroid();
                coordinateSystem.moveToCentroid(theSession, workPart, centroid);
                component.createComponent(theSession, workPart, body);
            } catch (Exception e) {
                out.printMessage().print("createAssemble error - "
                        + body.journalIdentifier() + " " + e.getMessage());
            }
        }
    }

    public static final int getUnloadOption() {
        return BaseSession.LibraryUnloadOption.IMMEDIATELY;
    }
}