package com.serp.createassemble;

import com.serp.message.WindowOutputStream;
import nxopen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class FindBodies {

    @Autowired
    private WindowOutputStream out;

    public List<Body> findBodies(Session theSession, Part workPart) throws java.rmi.RemoteException, NXException {
        List<Body> listBodies = new LinkedList<>();
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
        return listBodies;
    }
}