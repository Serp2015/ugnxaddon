package com.serp.createassemble;

import nxopen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;

@Service
public class BodyComparator {
    @Autowired
    private Measure measure;

    public boolean propertiesChecker(Session theSession, Part workPart, Body o1, Body o2)
            throws NXException, RemoteException {

        boolean edges = o1.getEdges().length == o2.getEdges().length;
        boolean faces = o1.getFaces().length == o2.getFaces().length;
        boolean volume = measure.measureBody(theSession, workPart, o1).volume() ==
                measure.measureBody(theSession, workPart, o2).volume();
        boolean area = measure.measureBody(theSession, workPart, o1).area() ==
                measure.measureBody(theSession, workPart, o2).area();
        boolean centroid = measure.measureBody(theSession, workPart, o1).centroid() ==
                measure.measureBody(theSession, workPart, o2).centroid();
        boolean radiusOfGyration = measure.measureBody(theSession, workPart, o1).radiusOfGyration() ==
                measure.measureBody(theSession, workPart, o2).radiusOfGyration();
        return edges && faces && volume && area && centroid && radiusOfGyration;
    }
}
