package com.serp.print;

import nxopen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.List;

@Service
public class OpenParts {

    @Autowired
    private PrintPdf printPdf;
    @Autowired
    private AssemblyListing assemblyListing;

    public void execute() throws RemoteException, NXException {
        Session theSession = (Session) SessionFactory.get("Session");
        Part workPart = theSession.parts().work();

        String projectManstr = workPart.getStringAttribute("MANSTR");

        //Print PDF
        printPdf.print(theSession, workPart, projectManstr);

        List<String> componentList = assemblyListing.getList(theSession);

        for (int i = 0; i < componentList.size(); i++) {
            try {
                Part currentPart = ((Part) theSession.parts().findObject(componentList.get(i)));
                PartCollection.SetDisplayData setDisplayData1;
                setDisplayData1 = theSession.parts().setDisplay(currentPart, true, true);

                setDisplayData1.loadStatus.dispose();
                setDisplayData1.loadStatus = null;

                //Print PDF
                printPdf.print(theSession, currentPart, projectManstr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static final int getUnloadOption() {
        return BaseSession.LibraryUnloadOption.IMMEDIATELY;
    }
}
