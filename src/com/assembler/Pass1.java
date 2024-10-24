package com.assembler;

import java.io.*;
import java.util.HashMap;

class Pass1 {

    private final HashMap<String, Integer> MOT = new HashMap<>();
    private final HashMap<String, Integer> RT = new HashMap<>();
    private final HashMap<String, Integer> DST = new HashMap<>();
    private final LiteralTable[] LT = new LiteralTable[10];
    private final SymbolTable[] ST = new SymbolTable[10];
    private int STP = 0, LTP = 0;
    private int locationCounter = 0;

    // Constructor (initialize tables)
    public Pass1() {
        MOT.put("STOP", 00);
        MOT.put("ADD", 01);
        MOT.put("SUB", 02);
        MOT.put("MULT", 03);
        MOT.put("MOVER", 04);
        MOT.put("MOVEM", 05);
        MOT.put("COMP", 06);
        MOT.put("BC", 07);
        MOT.put("DIV", 8);
        MOT.put("READ", 9);
        MOT.put("PRINT", 10);

        RT.put("AREG", 1);
        RT.put("BREG", 2);
        RT.put("CREG", 3);
        RT.put("DREG", 4);

        DST.put("DC", 11);
        DST.put("DS", 22);
    }

    // Execute Pass1 logic (no changes from previous implementation)
    public void executePass1(File inputFile, File outputFile) throws IOException {
        Logger.log("Pass 1: Starting with input file: " + inputFile.getName());

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile)); BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            while ((line = br.readLine()) != null) {
                String statement = parseLine(line);
                Logger.log("Processed line: " + line + " -> " + statement);
                bw.write(statement + "\n");
            }
        }

        Logger.log("Pass 1: Symbol Table after processing:");
        for (int i = 0; i < STP; i++) {
            Logger.log(i + ": " + ST[i].name + ", Address: " + ST[i].add);
        }

        Logger.log("Pass 1: Completed successfully. Intermediate output written to: " + outputFile.getName());
    }

    // Parse each line of the assembly code and generate intermediate representation
    private String parseLine(String line) {
        String row = "";
        String[] split = line.split(" ");

        if (split.length > 4) {
            Logger.log("Invalid Statement");
            return "Error: Invalid Statement";
        } else {
            row = row + locationCounter;
            if (split[0].equalsIgnoreCase("Start")) {
                locationCounter = Integer.parseInt(split[1]);
                return locationCounter + "";
            } else if (split[0].equalsIgnoreCase("End")) {
                return "";
            } else if (split[0].equalsIgnoreCase("LTORG") || split[0].equalsIgnoreCase("ORIGIN") || split[0].equalsIgnoreCase("EQU")) {
                return "";
            } else {
                for (String token : split) {
                    int opcode = getOpcode(token);
                    if (opcode != 9999) {
                        row = row + " " + opcode;
                    } else {
                        if (token.charAt(0) == '=') {
                            LT[LTP] = new LiteralTable(token, 0);
                            row = row + " L" + LTP;
                            LTP++;
                        } else {
                            if (split[1].equals("DC")) {
                                row = row + " " + token;
                            } else {
                                ST[STP] = new SymbolTable(token, 0);
                                row = row + " S" + STP;
                                STP++;
                            }
                        }
                    }
                }
            }
            locationCounter++;
        }
        return row;
    }

    // Getters for SymbolTable and LiteralTable
    public SymbolTable[] getSymbolTable() {
        return ST;
    }

    public LiteralTable[] getLiteralTable() {
        return LT;
    }

    public int getSymbolTablePointer() {
        return STP;
    }

    public int getLiteralTablePointer() {
        return LTP;
    }

    private int getOpcode(String key) {
        if (MOT.containsKey(key)) {
            return MOT.get(key);
        } else if (RT.containsKey(key)) {
            return RT.get(key);
        } else if (DST.containsKey(key)) {
            return DST.get(key);
        }
        return 9999;
    }
}
