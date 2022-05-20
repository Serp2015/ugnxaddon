package com.serp.print;

import com.serp.message.AssemblyListingWindowOutputStream;
import nxopen.NXException;
import nxopen.Part;
import nxopen.Session;
import nxopen.TaggedObjectCollection;
import nxopen.drawings.DrawingSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OpenSheets {
    @Autowired
    private PrintPdf printPdf;
    @Autowired
    private AssemblyListingWindowOutputStream listing;
    private PrintStream out;

    public void printSheet(Session session, Part workPart, String projectManstr) throws NXException, RemoteException {
        listing.setSession(session);
        out = new PrintStream(listing);

        TaggedObjectCollection.Iterator iterator = workPart.drawingSheets().iterator();
        List<String> sheetNames = new ArrayList<>();
        while (iterator.hasNext()) {
            DrawingSheet drawingSheet = (DrawingSheet) iterator.next();
            sheetNames.add(drawingSheet.name());
        }

        for (int i = 0; i < sheetNames.size(); i++) {
            out.println("Printing: " + sheetNames.get(i));
            String number = (sheetNames.size() == 1) ? "" : ("_" + (i + 1));
            printPdf.print(session, workPart, projectManstr, sheetNames.get(i), number);
        }
    }
}