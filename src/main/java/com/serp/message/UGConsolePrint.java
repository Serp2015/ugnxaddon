package com.serp.message;

import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.io.PrintStream;

//class for outputting information to the UGNX console
@Service
public class UGConsolePrint extends PrintStream {

    public UGConsolePrint(OutputStream out) {
        super(out);
    }

    public static void main(String[] args) {
        UGConsolePrint windowOutputStreamTest = new UGConsolePrint(System.out);
        windowOutputStreamTest.println("test");
    }
}
