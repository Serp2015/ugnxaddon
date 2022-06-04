package com.serp.createassemble;

import com.serp.message.WindowOutputStream;
import nxopen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
public class FindBodies {

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
        return listBodies;
    }
}