package com.serp.message;

import javax.swing.*;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ReportException {
    public void report(Exception e) {
        StringWriter s = new StringWriter();
        PrintWriter p = new PrintWriter(s);
        p.println("Caught exception " + e);
        e.printStackTrace(p);
        JOptionPane.showMessageDialog(null, s.getBuffer().toString(), "Exception", JOptionPane.ERROR_MESSAGE);
    }
}
