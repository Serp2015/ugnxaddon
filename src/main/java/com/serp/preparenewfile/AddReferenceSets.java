package com.serp.preparenewfile;

import nxopen.*;
import org.springframework.stereotype.Service;

@Service
public class AddReferenceSets {
    public void addReference() throws NXException, java.rmi.RemoteException {
        Session theSession = (Session) SessionFactory.get("Session");
        Part workPart = theSession.parts().work();

        int markId1 = theSession.setUndoMark(nxopen.Session.MarkVisibility.VISIBLE, "Start");
        theSession.setUndoMarkName(markId1, "Reference Sets диалог");
        int markId2 = theSession.setUndoMark(nxopen.Session.MarkVisibility.VISIBLE, "Create New Reference Set");
        ReferenceSet referenceSet1 = workPart.createReferenceSet();
        theSession.setUndoMark(nxopen.Session.MarkVisibility.VISIBLE, "Add Components to Reference Set");
        theSession.updateManager().doUpdate(markId2);
        int markId4 = theSession.setUndoMark(nxopen.Session.MarkVisibility.VISIBLE, "Edit Name of Reference Set");
        referenceSet1.setName("false");
        theSession.updateManager().doUpdate(markId4);
        int markId5 = theSession.setUndoMark(nxopen.Session.MarkVisibility.VISIBLE, "Create New Reference Set");
        ReferenceSet referenceSet2 = workPart.createReferenceSet();
        theSession.setUndoMark(nxopen.Session.MarkVisibility.VISIBLE, "Add Components to Reference Set");
        theSession.updateManager().doUpdate(markId5);
        int markId7 = theSession.setUndoMark(nxopen.Session.MarkVisibility.VISIBLE, "Edit Name of Reference Set");
        referenceSet2.setName("true");
        theSession.updateManager().doUpdate(markId7);
    }

    public static final int getUnloadOption() {
        return BaseSession.LibraryUnloadOption.IMMEDIATELY;
    }
}
