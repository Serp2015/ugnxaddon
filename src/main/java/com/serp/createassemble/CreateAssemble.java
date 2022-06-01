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
        List<Body> bodies = findBodies.findBodies(theSession, workPart);
        for (int i = 0; i < bodies.size(); i++) {
            try {
                createComponent(theSession, workPart, bodies.get(i), i + 1);
            } catch (Exception e) {
                out.printMessage().println("Creating component error -" + e.getMessage());
            }
        }
    }

    private void createComponent(Session theSession, Part workPart, Body theBody, int fileNumber) throws NXException, RemoteException {
        //preparation component
        theSession.setUndoMark(nxopen.Session.MarkVisibility.VISIBLE, "Create New Component");
        int markId2 = theSession.setUndoMark(nxopen.Session.MarkVisibility.VISIBLE, "Start");
        FileNew fileNew1 = theSession.parts().fileNew();
        theSession.setUndoMarkName(markId2, "New Component File диалог");
        int markId3 = theSession.setUndoMark(nxopen.Session.MarkVisibility.INVISIBLE, "New Component File");
        fileNew1.setTemplateFileName("model-plain-1-mm-template.prt");
//        fileNew1.setTemplateFileName("m:\\Templates 7.5\\Pust.prt");
        fileNew1.setApplication(FileNewApplication.MODELING);
        fileNew1.setUnits(nxopen.Part.Units.MILLIMETERS);
        fileNew1.setNewFileName("C:\\UGplot\\Model_" + fileNumber + ".prt");
        fileNew1.setMasterFileName("");
        fileNew1.setUseBlankTemplate(false);
        fileNew1.setMakeDisplayedPart(false);
        theSession.deleteUndoMark(markId3, null);
        theSession.setUndoMarkName(markId2, "New Component File");
        int markId4 = theSession.setUndoMark(nxopen.Session.MarkVisibility.VISIBLE, "Start");
        nxopen.assemblies.CreateNewComponentBuilder createNewComponentBuilder1;
        createNewComponentBuilder1 = workPart.assemblyManager().createNewComponentBuilder();
        createNewComponentBuilder1.setNewComponentName("MODEL_" + fileNumber);
        createNewComponentBuilder1.setReferenceSet(nxopen.assemblies.CreateNewComponentBuilder.ComponentReferenceSetType.ENTIRE_PART_ONLY);
        createNewComponentBuilder1.setReferenceSetName("Entire Part");
        theSession.setUndoMarkName(markId4, "Create New Component диалог");
        createNewComponentBuilder1.setNewComponentName("MODEL_" + fileNumber);
        //creating component
        Body body1 = (workPart.bodies().findObject(theBody.name()));
        createNewComponentBuilder1.objectForNewComponent().add(body1);
        int markId5 = theSession.setUndoMark(nxopen.Session.MarkVisibility.INVISIBLE, "Create New Component");
        createNewComponentBuilder1.setNewFile(fileNew1);
        int markId6 = theSession.setUndoMark(nxopen.Session.MarkVisibility.VISIBLE, "Create New component");
        createNewComponentBuilder1.commit();
        theSession.deleteUndoMark(markId5, null);
        theSession.setUndoMarkName(markId4, "Create New Component");
        createNewComponentBuilder1.destroy();
        theSession.deleteUndoMark(markId6, null);
        theSession.deleteUndoMarksUpToMark(markId2, null, false);
    }

    public static final int getUnloadOption() {
        return BaseSession.LibraryUnloadOption.IMMEDIATELY;
    }
}