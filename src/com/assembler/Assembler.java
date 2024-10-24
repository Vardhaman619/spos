package com.assembler;

import java.io.File;
import java.io.IOException;

public class Assembler {
    private SymbolTable[] ST = new SymbolTable[10];
    private LiteralTable[] LT = new LiteralTable[10];
    private int STP = 0, LTP = 0;

    // Method to run Pass 1
    public void runPass1(File inputFile, File outputFile) throws IOException {
        Logger.log("Assembler: Running Pass 1.");
        Pass1 pass1 = new Pass1();
        pass1.executePass1(inputFile, outputFile);
        this.ST = pass1.getSymbolTable();
        this.LT = pass1.getLiteralTable();
        this.STP = pass1.getSymbolTablePointer();
        this.LTP = pass1.getLiteralTablePointer();
    }

    // Method to run Pass 2
    public void runPass2(File inputFile, File outputFile) throws IOException {
        Logger.log("Assembler: Running Pass 2.");
        Pass2 pass2 = new Pass2(ST, STP, LT, LTP);
        pass2.executePass2(inputFile, outputFile);
    }
}
