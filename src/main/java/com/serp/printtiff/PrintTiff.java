package com.serp.printtiff;

import com.serp.message.WindowOutputStream;
import nxopen.*;
import nxopen.drawings.DrawingSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

//the class creates a folder with the project, assigns names to the drawings, prints the drawing in tiff
@Service
public class PrintTiff {
    @Autowired
    private WindowOutputStream out;

    public void print(Session session, Part workPart, String projectManstr, String sheetName, String number) throws NXException, java.rmi.RemoteException {
        out.setSession(session);

        DrawingSheet drawingSheet1 = null;
        try {
            drawingSheet1 = workPart.drawingSheets().findObject(sheetName);
        } catch (Exception e) {
            out.printMessage().println("лист не найден");
        }
        String nom = workPart.getStringAttribute("NOM");

        if (drawingSheet1 != null && nom != null && projectManstr.equals(nom.substring(0, 12))) {
            int markId1 = session.setUndoMark(nxopen.Session.MarkVisibility.INVISIBLE, "Start");
            PlotBuilder plotBuilder1 = workPart.plotManager().createPlotBuilder();
            plotBuilder1.setCopies(1);
            plotBuilder1.setTolerance(0.001);
            plotBuilder1.setRasterImages(true);
            plotBuilder1.setImageResolution(nxopen.PlotBuilder.ImageResolutionOption.MEDIUM);
            plotBuilder1.setUnits(nxopen.PlotBuilder.UnitsOption.ENGLISH);
            plotBuilder1.setXDisplay(nxopen.PlotBuilder.XdisplayOption.RIGHT);
            plotBuilder1.setXOffset(3.8);
            plotBuilder1.setCharacterSize(1.52);
            plotBuilder1.setRotation(nxopen.PlotBuilder.RotationOption.DEGREE90);
            plotBuilder1.setJobName("User_0_sb");
            session.setUndoMarkName(markId1, "##04Plot диалог");

            int markId2 = session.setUndoMark(nxopen.Session.MarkVisibility.INVISIBLE, "##04Plot");
            NXObject[] sheets1 = new NXObject[1];

            sheets1[0] = drawingSheet1;
            plotBuilder1.sourceBuilder().setSheets(sheets1);
            plotBuilder1.setPlotterText("TIFF");
            plotBuilder1.setProfileText("<System Profile>");
            plotBuilder1.colorsWidthsBuilder().setColors(nxopen.PlotColorsWidthsBuilder.Color.BLACK_ON_WHITE);
            plotBuilder1.colorsWidthsBuilder().setWidths(nxopen.PlotColorsWidthsBuilder.Width.CUSTOM_PALETTE);

            //create folder
            File folder = new File("c:\\UGplot\\" + projectManstr);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            //create file
            String[] filenames1 = new String[1];
            filenames1[0] = "C:\\UGplot\\" + projectManstr + "\\" + nom + number + ".tiff";
            plotBuilder1.setGraphicFilenames(filenames1);
            String[] filenames2 = new String[1];
            filenames2[0] = "C:\\UGplot\\" + projectManstr + "\\" + nom + number + ".cgm";
            plotBuilder1.setFilenames(filenames2);

            plotBuilder1.commit();
            session.deleteUndoMark(markId2, null);
            session.setUndoMarkName(markId1, "##04Plot");
            plotBuilder1.destroy();
            session.deleteUndoMark(markId1, null);
        }
    }
}