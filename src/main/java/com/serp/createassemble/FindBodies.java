package com.serp.createassemble;

import com.serp.message.WindowOutputStream;
import nxopen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FindBodies {

    @Autowired
    private Measure measure;
    @Autowired
    private WindowOutputStream out;

    public List<Body> findBodies(Session theSession, Part workPart) throws java.rmi.RemoteException, NXException {
        List<Body> listBodies = new ArrayList<>();
        out.setSession(theSession);
        try {
            BodyCollection bodyCollection = workPart.bodies();
            TaggedObjectCollection.Iterator iterator = bodyCollection.iterator();
            while (iterator.hasNext()) {
                Body body = (Body) iterator.next();
                if (body.layer() == 1) {
                    listBodies.add(body);
                }
            }
        } catch (Exception e) {
            out.printMessage().println("FindBodies findBodies error - " + e.getMessage());
        }
        sort(theSession, workPart, listBodies);
        return listBodies;
    }

    private void sort(Session theSession, Part workPart, List<Body> listBodies) {
        listBodies.sort((o1, o2) -> {
            try {
                double o1Volume = Math.round(measure.measureBody(theSession, workPart, o1).volume() * 100) / 100;
                double o2Volume = Math.round(measure.measureBody(theSession, workPart, o2).volume() * 100) / 100;
                int o1Identifier = Integer.parseInt(o1.journalIdentifier().replaceAll("[^\\d]", ""));
                int o2Identifier = Integer.parseInt(o2.journalIdentifier().replaceAll("[^\\d]", ""));
                int compare = Double.compare(o1Volume, o2Volume);
                return compare != 0
                        ? compare
                        : Integer.compare(o1Identifier, o2Identifier);
            } catch (NXException | RemoteException e) {
                out.printMessage().println("Assemble sortBodies - " + e.getMessage());
            }
            return 0;
        });
    }
}