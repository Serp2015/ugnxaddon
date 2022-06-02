package com.serp.createassemble;

import com.serp.message.WindowOutputStream;
import lombok.Data;
import nxopen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.List;

@Service
@Data
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
        List<Body> bodies = findBodies.findBodies(theSession, workPart);
        for (int i = 0; i < bodies.size(); i++) {
            try {
                measure.calculateMeasure(theSession, workPart, bodies.get(i));
                component.createComponent(theSession, workPart, bodies.get(i), i + 1);
            } catch (Exception e) {
                out.printMessage().println("createAssemble error -" + e.getMessage());
            }
        }
    }

    public static final int getUnloadOption() {
        return BaseSession.LibraryUnloadOption.IMMEDIATELY;
    }
}