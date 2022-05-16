package com.serp.print;

import nxopen.INXObject;
import nxopen.NXException;
import nxopen.Part;
import nxopen.assemblies.Component;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;

@Service
public class ComponentWriter {

    public String execute(Component component) {
        String name = null;
        try {
            INXObject prototype = component.prototype();
            if (prototype instanceof Part) {
                name = component.name();
            }
        } catch (NXException | RemoteException e) {
            e.printStackTrace();
        }
        return name;
    }
}
