package com.serp.print;

import nxopen.NXException;
import nxopen.Part;
import nxopen.Session;
import nxopen.assemblies.Component;
import nxopen.assemblies.ComponentAssembly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssemblyListing {

    @Autowired
    private ComponentWriter componentWriter;
    private List<String> componentList = new ArrayList<>();

    public List<String> getList(Session theSession) {
        try {
            Part part1 = theSession.parts().work();
            if (part1 != null) {
                ComponentAssembly cAssembly = part1.componentAssembly();
                if (cAssembly.rootComponent() == null) {
                    return Collections.emptyList();
                }
                walk(cAssembly.rootComponent(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return componentList
                .stream()
                .distinct()
                .collect(Collectors.toList());
    }

    public void walk(Component component, int level) {
        try {
            String name = componentWriter.execute(component);
            if (name != null) {
                componentList.add(name);
            }
            Component[] children = component.getChildren();
            for (int i = 0; i < children.length; i++) {
                walk(children[i], level + 1);
            }
        } catch (NXException | RemoteException e) {
            e.printStackTrace();
        }
    }
}