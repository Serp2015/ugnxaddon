package com.serp.createassemble;

import com.serp.message.WindowOutputStream;
import lombok.Data;
import lombok.SneakyThrows;
import nxopen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.*;

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

    public void createAssemble() throws NXException, RemoteException {
        Session theSession = (Session) SessionFactory.get("Session");
        Part workPart = theSession.parts().work();
        out.setSession(theSession);

        List<Body> bodies = new LinkedList<>(findBodies.findBodies(theSession, workPart));
        for (int i = 0; i < bodies.size(); i++) {
            try {
                component.createComponent(theSession, workPart, bodies.get(i));
            } catch (Exception e) {
                out.printMessage().print("createAssemble error - "
                        + bodies.get(i).journalIdentifier() + " " + e.getMessage());
            }
        }
    }

    private void sortBodies(Session theSession, Part thePart, List<Body> bodies) {
        bodies.sort((o1, o2) -> {
            try {
                return Double.compare(
                        measure.calculateMeasure(theSession, thePart, o1).volume(),
                        measure.calculateMeasure(theSession, thePart, o2).volume());
            } catch (NXException | RemoteException e) {
                e.printStackTrace();
            }
            return 0;
        });
    }

    public static final int getUnloadOption() {
        return BaseSession.LibraryUnloadOption.IMMEDIATELY;
    }
}