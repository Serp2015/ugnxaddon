package com.serp.createassemble;

import com.serp.message.WindowOutputStream;
import lombok.Data;
import nxopen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
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
                listBodies.add(body);
                out.printMessage().println(body.journalIdentifier());
            }
        } catch (Exception e) {
            out.printMessage().println("FindBodies error");
        }
        return listBodies;
    }
}