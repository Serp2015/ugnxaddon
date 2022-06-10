package com.serp.createassemble;

import com.serp.message.WindowOutputStream;
import nxopen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;

@Service
public class Measure {
    @Autowired
    private WindowOutputStream out;

    public MeasureBodies measureBody(Session theSession, Part workPart, Body theBody)
            throws NXException, RemoteException {
        out.setSession(theSession);
        MeasureBodies measureBodies1 = null;
        try {
            NXObject nullNXObject = null;
            MeasureBodyBuilder measureBodyBuilder1 = workPart.measureManager().createMeasureBodyBuilder(nullNXObject);
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
            measureBodies1 = workPart.measureManager().newMassProperties(massUnits1, 0.99, objects1);
        } catch (Exception e) {
            out.printMessage().println("Measure measureBody - " + e.getMessage());
        }
        return measureBodies1;
    }

    public MeasureFaces measureFace(Session theSession, Part workPart, Body theBody)
            throws NXException, RemoteException {
        out.setSession(theSession);
        MeasureFaces measureFaces = null;
        try {
            NXObject nullNXObject = null;
            MeasureFaceBuilder measureFaceBuilder1 = workPart.measureManager().createMeasureFaceBuilder(nullNXObject);
            measureFaceBuilder1.faceObjects().add(theBody);
            Unit[] massUnits1 = new Unit[2];
            Unit unit1 = ((Unit) workPart.unitCollection().findObject("SquareMilliMeter"));
            massUnits1[0] = unit1;
            Unit unit2 = ((Unit) workPart.unitCollection().findObject("MilliMeter"));
            massUnits1[1] = unit2;
            IParameterizedSurface[] objects1 = new IParameterizedSurface[1];
            objects1[0] = theBody.getFaces()[0];
            measureFaces = workPart.measureManager().newFaceProperties(unit1, unit2,0.99, objects1);
        } catch (Exception e) {
            out.printMessage().println("Measure measureFace - " + e.getMessage());
        }
        return measureFaces;
    }
}
