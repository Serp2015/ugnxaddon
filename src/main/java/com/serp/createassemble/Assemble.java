package com.serp.createassemble;

import com.serp.message.WindowOutputStream;
import nxopen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.LinkedList;
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

        List<Body> bodies = new LinkedList<>(findBodies.findBodies(theSession, workPart));
        sortBodies(theSession, workPart, bodies);
        for (int i = 0; i < bodies.size(); i++) {
            try {
                coordinateSystem.moveToAbsolute(theSession, workPart);
                Point3d centroid = measure.calculateMeasure(theSession, workPart, bodies.get(i)).centroid();
                coordinateSystem.moveToCentroid(theSession, workPart, centroid);
                component.createComponent(theSession, workPart, bodies.get(i));
            } catch (Exception e) {
                out.printMessage().print("createAssemble error - "
                        + bodies.get(i).journalIdentifier() + " " + e.getMessage());
            }
        }
    }

    private void sortBodies(Session theSession, Part thePart, List<Body> bodies) throws NXException, RemoteException {
        out.setSession(theSession);
        bodies.sort((o1, o2) -> {
            try {
                double o1Volume = Math.ceil(measure.calculateMeasure(theSession, thePart, o1).volume() * 100) / 100;
                double o2Volume = Math.ceil(measure.calculateMeasure(theSession, thePart, o2).volume() * 100) / 100;
                if (o1Volume > o2Volume) return 1;
                if (o1Volume < o2Volume) return -1;
                int o1Identifier = Integer.parseInt(o1.journalIdentifier().replaceAll("[^\\d]", ""));
                int o2Identifier = Integer.parseInt(o2.journalIdentifier().replaceAll("[^\\d]", ""));
                if (o1Identifier > o2Identifier) return 1;
                if (o1Identifier < o2Identifier) return -1;
            } catch (NXException | RemoteException e) {
                out.printMessage().println("Assemble sortBodies - " + e.getMessage());
            }
            return 0;
        });
    }

    /*private void sortBodies(Session theSession, Part thePart, List<Body> bodies) throws NXException, RemoteException {
        out.setSession(theSession);
        bodies.sort((o1, o2) -> {
            try {
                return Double.compare(
                        measure.calculateMeasure(theSession, thePart, o1).volume(),
                        measure.calculateMeasure(theSession, thePart, o2).volume());
            } catch (NXException | RemoteException e) {
                out.printMessage().println("Assemble sortBodies - " + e.getMessage());
            }
            return 0;
        });
    }*/

    public static final int getUnloadOption() {
        return BaseSession.LibraryUnloadOption.IMMEDIATELY;
    }
}