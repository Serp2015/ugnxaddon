package com.serp.createassemble;

import com.serp.message.WindowOutputStream;
import com.serp.service.AssemblyListing;
import nxopen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.List;

@Service
public class BodyChange {
    @Autowired
    private WindowOutputStream out;
    @Autowired
    private AssemblyListing assemblyListing;
    @Autowired
    private BodyComparator bodyComparator;

    public void change(Session theSession, Part workPart, Body theBody) throws NXException, RemoteException {

        theSession.setUndoMark(nxopen.Session.MarkVisibility.VISIBLE, "Заменить компонент");
        int markId2 = theSession.setUndoMark(nxopen.Session.MarkVisibility.INVISIBLE, "Start");
        nxopen.assemblies.ReplaceComponentBuilder replaceComponentBuilder1;
        replaceComponentBuilder1 = workPart.assemblyManager().createReplaceComponentBuilder();
        replaceComponentBuilder1.setComponentNameType(nxopen.assemblies.ReplaceComponentBuilder.ComponentNameOption.AS_SPECIFIED);
        replaceComponentBuilder1.setComponentName("MODEL1");
        theSession.setUndoMarkName(markId2, "Replace Component диалог");
        replaceComponentBuilder1.setComponentName("");
        nxopen.assemblies.Component component1 = ((nxopen.assemblies.Component)workPart.componentAssembly().rootComponent().findObject("COMPONENT model2 1"));
        replaceComponentBuilder1.componentsToReplace().add(component1);
        replaceComponentBuilder1.setComponentName("MODEL2");
        replaceComponentBuilder1.setComponentName("MODEL1");
        int markId3 = theSession.setUndoMark(nxopen.Session.MarkVisibility.INVISIBLE, "Replace Component");
        replaceComponentBuilder1.setReplacementPart("C:\\UGplot\\new\\model1.prt");
        replaceComponentBuilder1.setComponentReferenceSetType(nxopen.assemblies.ReplaceComponentBuilder.ComponentReferenceSet.MAINTAIN, null);
        PartLoadStatus partLoadStatus1;
        partLoadStatus1 = replaceComponentBuilder1.registerReplacePartLoadStatus();
        replaceComponentBuilder1.commit();
        partLoadStatus1.dispose();
        partLoadStatus1 = null;
        theSession.deleteUndoMark(markId3, null);
        theSession.setUndoMarkName(markId2, "Replace Component");
        replaceComponentBuilder1.destroy();
        theSession.deleteUndoMark(markId2, null);
    }

    public void comparison(Session theSession) {
        List<String> componentList = assemblyListing.getList(theSession);
    }
}
