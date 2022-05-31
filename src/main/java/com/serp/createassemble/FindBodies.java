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

    public List<NXObject> findBodies(Session theSession, Part workPart) throws java.rmi.RemoteException, NXException {
        List<NXObject> listBodies = new ArrayList<>();
        out.setSession(theSession);
        try {
            BodyCollection bodyCollection = workPart.bodies();
            TaggedObjectCollection.Iterator iterator = bodyCollection.iterator();
            while (iterator.hasNext()) {
                listBodies.add((NXObject) iterator.next());
            }
        } catch (Exception e) {
            out.getPrintStream().println("FindBodies error");
        }
        return listBodies;
    }
}
