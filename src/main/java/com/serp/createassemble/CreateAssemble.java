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
public class CreateAssemble {
    @Autowired
    private FindBodies findBodies;
    @Autowired
    private WindowOutputStream out;

    public void create() throws NXException, RemoteException {
        Session theSession = (Session) SessionFactory.get("Session");
        Part workPart = theSession.parts().work();

        out.setSession(theSession);
        List<NXObject> bodies = findBodies.findBodies(theSession, workPart);
        for (NXObject nxObject : bodies) {
            try {
                out.getPrintStream().println("Component name - " + nxObject.name());
            } catch (Exception e) {
                out.getPrintStream().println("Adding component error");
            }
        }
    }

    public static final int getUnloadOption() {
        return BaseSession.LibraryUnloadOption.IMMEDIATELY;
    }
}