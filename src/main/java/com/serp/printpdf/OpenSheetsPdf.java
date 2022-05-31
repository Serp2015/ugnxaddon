package com.serp.printpdf;

import com.serp.message.WindowOutputStream;
import nxopen.NXException;
import nxopen.Part;
import nxopen.Session;
import nxopen.TaggedObjectCollection;
import nxopen.drawings.DrawingSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

//class for iterating sheets
@Service
public class OpenSheetsPdf {
    @Autowired
    private PrintPdf printPdf;
    @Autowired
    private WindowOutputStream out;

    public void printSheet(Session session, Part workPart, String projectManstr) throws NXException, RemoteException {
        out.setSession(session);

        TaggedObjectCollection.Iterator iterator = workPart.drawingSheets().iterator();
        List<String> sheetNames = new ArrayList<>();
        while (iterator.hasNext()) {
            DrawingSheet drawingSheet = (DrawingSheet) iterator.next();
            sheetNames.add(drawingSheet.name());
        }

        for (int i = 0; i < sheetNames.size(); i++) {
            String number = (sheetNames.size() == 1) ? "" : ("_" + (i + 1));
            printPdf.print(session, workPart, projectManstr, sheetNames.get(i), number);
            out.getPrintStream().println("Printing: " + sheetNames.get(i));
        }
    }
}