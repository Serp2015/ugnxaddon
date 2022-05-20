package com.serp.print;

import com.serp.message.AssemblyListingWindowOutputStream;
import nxopen.*;
import nxopen.drawings.DrawingSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.PrintStream;
import java.rmi.RemoteException;

@Service
public class PrintPdf {
    @Autowired
    private AssemblyListingWindowOutputStream listing;
    private PrintStream out;

    public void print(Session session, Part workPart, String projectManstr, String sheetName, String number) throws NXException, RemoteException {
        listing.setSession(session);
        out = new PrintStream(listing);

        DrawingSheet drawingSheet1 = null;
        try {
            drawingSheet1 = workPart.drawingSheets().findObject(sheetName);
        } catch (Exception e) {
            out.println("лист не найден");
        }
        String nom = workPart.getStringAttribute("NOM");

        if (drawingSheet1 != null && nom != null && projectManstr.equals(nom.substring(0, 12))) {
            int markId1 = session.setUndoMark(Session.MarkVisibility.INVISIBLE, "Start");

            PrintPDFBuilder printPDFBuilder1;
            printPDFBuilder1 = workPart.plotManager().createPrintPdfbuilder();
            printPDFBuilder1.setScale(1.0);
            printPDFBuilder1.setColors(PrintPDFBuilder.Color.BLACK_ON_WHITE);
            printPDFBuilder1.setWidths(PrintPDFBuilder.Width.CUSTOM_THREE_WIDTHS);
            printPDFBuilder1.setSize(PrintPDFBuilder.SizeOption.SCALE_FACTOR);
            printPDFBuilder1.setUnits(PrintPDFBuilder.UnitsOption.ENGLISH);
            printPDFBuilder1.setXDimension(8.5);
            printPDFBuilder1.setYDimension(11.0);
            printPDFBuilder1.setOutputText(PrintPDFBuilder.OutputTextOption.POLYLINES);
            printPDFBuilder1.setRasterImages(true);
            session.setUndoMarkName(markId1, "Export PDF диалог");

            int markId2 = session.setUndoMark(Session.MarkVisibility.INVISIBLE, "Export PDF");
            printPDFBuilder1.setWatermark("");
            NXObject[] sheets1 = new NXObject[1];

            //new code
            sheets1[0] = drawingSheet1;
            printPDFBuilder1.sourceBuilder().setSheets(sheets1);

            //create folder
            File folder = new File("c:\\UGplot\\" + projectManstr);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            printPDFBuilder1.setFilename("C:\\UGplot\\" + projectManstr + "\\" + nom + number + ".pdf");

            printPDFBuilder1.commit();
            session.deleteUndoMark(markId2, null);
            session.setUndoMarkName(markId1, "Export PDF");
            printPDFBuilder1.destroy();
            session.deleteUndoMark(markId1, null);
        }
    }
}