package com.serp.message;

import org.springframework.stereotype.Service;

import javax.swing.*;
import java.io.PrintWriter;
import java.io.StringWriter;

@Service
public class ReportException {
    public void report(Exception e) {
        StringWriter s = new StringWriter();
        PrintWriter p = new PrintWriter(s);
        p.println("Caught exception " + e);
        e.printStackTrace(p);
        JOptionPane.showMessageDialog(null, s.getBuffer().toString(), "Exception", JOptionPane.ERROR_MESSAGE);
    }
}
