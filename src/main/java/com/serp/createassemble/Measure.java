package com.serp.createassemble;

import com.serp.message.WindowOutputStream;
import lombok.Data;
import nxopen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;

@Service
@Data
public class Measure {
    @Autowired
    private WindowOutputStream out;

    public void calculateMeasure(Session theSession, Part workPart, Body theBody) throws NXException, RemoteException {
        out.setSession(theSession);
        try {
            int markId1 = theSession.setUndoMark(nxopen.Session.MarkVisibility.VISIBLE, "Start");
            NXObject nullNXObject = null;
            MeasureBodyBuilder measureBodyBuilder1 = workPart.measureManager().createMeasureBodyBuilder(nullNXObject);
            measureBodyBuilder1.setAnnotationMode(nxopen.MeasureBuilder.AnnotationType.SHOW_DIMENSION);
            theSession.setUndoMarkName(markId1, "Measure Bodies диалог");

            measureBodyBuilder1.bodyObjects().add(theBody);
            Unit[] massUnits1 = new Unit[5];
            Unit unit1 = ((Unit) workPart.unitCollection().findObject("SquareMilliMeter"));
            massUnits1[0] = unit1;
            Unit unit2 = ((Unit) workPart.unitCollection().findObject("CubicMilliMeter"));
            massUnits1[1] = unit2;
            Unit unit3 = ((Unit) workPart.unitCollection().findObject("Kilogram"));
            massUnits1[2] = unit3;
            Unit unit4 = ((Unit) workPart.unitCollection().findObject("MilliMeter"));
            massUnits1[3] = unit4;
            Unit unit5 = ((Unit) workPart.unitCollection().findObject("Newton"));
            massUnits1[4] = unit5;
            IBody[] objects1 = new IBody[1];
            objects1[0] = theBody;
            MeasureBodies measureBodies1 = workPart.measureManager().newMassProperties(massUnits1, 0.99, objects1);

            out.printMessage().println("Volume - " + measureBodies1.volume());
            out.printMessage().println("Centroid - "
                    + measureBodies1.centroid().x + ", "
                    + measureBodies1.centroid().y + ", "
                    + measureBodies1.centroid().z);

            measureBodies1.dispose();
            measureBodies1 = null;
            int markId2 = theSession.setUndoMark(nxopen.Session.MarkVisibility.INVISIBLE, "Measure Bodies");
            measureBodyBuilder1.bodyObjects().clear();
            theSession.deleteUndoMark(markId2, null);
            theSession.setUndoMarkName(markId1, "Measure Bodies");
            measureBodyBuilder1.destroy();
        } catch (Exception e) {
            out.printMessage().println("massCalculate - " + e.getMessage());
        }
    }
}
