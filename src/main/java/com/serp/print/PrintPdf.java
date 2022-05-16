package com.serp.print;

import nxopen.*;
import nxopen.drawings.DrawingSheet;
import nxopen.drawings.DrawingSheetCollection;
import org.springframework.stereotype.Service;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PrintPdf {

    public void print(Session session, Part workPart, String projectManstr) throws NXException, RemoteException {

        DrawingSheetCollection sheets = workPart.drawingSheets();
        TaggedObjectCollection.Iterator iterator = sheets.iterator();
        List<DrawingSheet> drawingSheets = new ArrayList<>();
        if (iterator.hasNext()) {
            drawingSheets.add((DrawingSheet) iterator.next());
        }

        for (int i = 0; i <drawingSheets.size(); i++) {
            String nom = null;
            try {
                nom = workPart.getStringAttribute("NOM");
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (drawingSheets.get(i) != null && nom != null && projectManstr.equals(nom.substring(0, 12))) {
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
                sheets1[0] = drawingSheets.get(i);
                printPDFBuilder1.sourceBuilder().setSheets(sheets1);

                //create folder
                File folder = new File("c:\\UGplot\\" + projectManstr);
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                if (drawingSheets.size() == 1) {
                    printPDFBuilder1.setFilename("C:\\UGplot\\" + projectManstr + "\\" + nom + ".pdf");
                } else {
                    printPDFBuilder1.setFilename("C:\\UGplot\\" + projectManstr + "\\" + nom + "_" + i + ".pdf");
                }

                NXObject nXObject1;
                nXObject1 = printPDFBuilder1.commit();
                session.deleteUndoMark(markId2, null);
                session.setUndoMarkName(markId1, "Export PDF");
                printPDFBuilder1.destroy();
                session.deleteUndoMark(markId1, null);
            }
        }
    }
}
