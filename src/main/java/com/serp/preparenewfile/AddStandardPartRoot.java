package com.serp.preparenewfile;

import nxopen.*;
import org.springframework.stereotype.Service;

@Service
public class AddStandardPartRoot {

    public void addAttributes() throws NXException, java.rmi.RemoteException {
        Session theSession = (Session) SessionFactory.get("Session");
        Part workPart = theSession.parts().work();

        theSession.setUndoMark(nxopen.Session.MarkVisibility.VISIBLE, "Assign Attributes");
        workPart.setAttribute("UM_STANDARD_PART_ROOT", "1");
        theSession.setUndoMark(nxopen.Session.MarkVisibility.VISIBLE, "Assign Attributes");
        workPart.setAttribute("UM_STANDARD_PART", "1");
    }

    public static final int getUnloadOption() {
        return BaseSession.LibraryUnloadOption.IMMEDIATELY;
    }
}
