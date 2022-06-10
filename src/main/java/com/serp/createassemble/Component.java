package com.serp.createassemble;

import com.serp.message.WindowOutputStream;
import nxopen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;

@Service
public class Component {
    @Autowired
    private Measure measure;
    @Autowired
    private WindowOutputStream out;

    public void createComponent(Session theSession, Part workPart, Body theBody) throws NXException, RemoteException {
        //preparation component
        theSession.setUndoMark(nxopen.Session.MarkVisibility.VISIBLE, "Create New Component");
        int markId2 = theSession.setUndoMark(nxopen.Session.MarkVisibility.VISIBLE, "Start");
        FileNew fileNew1 = theSession.parts().fileNew();
        theSession.setUndoMarkName(markId2, "New Component File диалог");
        int markId3 = theSession.setUndoMark(nxopen.Session.MarkVisibility.INVISIBLE, "New Component File");
        fileNew1.setTemplateFileName("model-plain-1-mm-template.prt");
        fileNew1.setApplication(FileNewApplication.MODELING);
        fileNew1.setUnits(nxopen.Part.Units.MILLIMETERS);

        //Adding volume and identifier to file name
        MeasureBodies measureSolids = measure.measureBody(theSession, workPart, theBody);
        MeasureFaces measureFaces = measure.measureFace(theSession, workPart, theBody);
        String identifier = theBody.journalIdentifier().replaceAll("[^\\d]", "");
        String solidName = String.format("%.0f", measureSolids.volume()) + "_" + identifier;
        String surfaceName = String.format("%.0f", measureFaces.area()) + "_" + identifier;
        String pathToFolder = workPart.fullPath().replace(workPart.name() + ".prt", "");

        out.setSession(theSession);
        //create file name and file path
        if (theBody.isSolidBody()) {
            fileNew1.setNewFileName(pathToFolder + "Solid_" + solidName + ".prt");
        } else if (theBody.isSheetBody()) {
            fileNew1.setNewFileName(pathToFolder + "Surface_" + surfaceName + ".prt");
        } else {
            fileNew1.setNewFileName(pathToFolder + "Model_" + "empty" + ".prt");
        }
        fileNew1.setMasterFileName("");
        fileNew1.setUseBlankTemplate(false);
        fileNew1.setMakeDisplayedPart(false);
        theSession.deleteUndoMark(markId3, null);
        theSession.setUndoMarkName(markId2, "New Component File");
        int markId4 = theSession.setUndoMark(nxopen.Session.MarkVisibility.VISIBLE, "Start");
        nxopen.assemblies.CreateNewComponentBuilder createNewComponentBuilder1;
        createNewComponentBuilder1 = workPart.assemblyManager().createNewComponentBuilder();
        if (theBody.isSolidBody()) {
            createNewComponentBuilder1.setNewComponentName("SOLID_" + solidName);
        } else if (theBody.isSheetBody()) {
            createNewComponentBuilder1.setNewComponentName("SURFACE_" + surfaceName);
        } else {
            createNewComponentBuilder1.setNewComponentName("MODEL_" + "empty");
        }
        createNewComponentBuilder1.setReferenceSet(nxopen.assemblies.CreateNewComponentBuilder.ComponentReferenceSetType.ENTIRE_PART_ONLY);
        createNewComponentBuilder1.setReferenceSetName("Entire Part");
        theSession.setUndoMarkName(markId4, "Create New Component диалог");
        if (theBody.isSolidBody()) {
            createNewComponentBuilder1.setNewComponentName("SOLID_" + solidName);
        } else if (theBody.isSheetBody()) {
            createNewComponentBuilder1.setNewComponentName("SURFACE_" + surfaceName);
        } else {
            createNewComponentBuilder1.setNewComponentName("MODEL_" + "empty");
        }
        //creating component
        Body body1 = (workPart.bodies().findObject(theBody.journalIdentifier()));
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
}
