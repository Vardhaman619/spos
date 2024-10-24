package com.assembler;

import java.io.*;

class Pass2 {
    private final SymbolTable[] ST; // Reference to symbol table from Pass1
    private final LiteralTable[] LT; // Reference to literal table from Pass1
    private final int STP; // Number of symbols
    private final int LTP; // Number of literals

    // Constructor to accept symbol and literal tables from Pass1
    public Pass2(SymbolTable[] ST, int STP, LiteralTable[] LT, int LTP) {
        this.ST = ST;
        this.STP = STP;
        this.LT = LT;
        this.LTP = LTP;
    }

    // Method to run Pass2 logic
    public void executePass2(File inputFile, File outputFile) throws IOException {
        Logger.log("Pass 2: Starting object code generation.");

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            while ((line = br.readLine()) != null) {
                String machineCode = generateMachineCode(line);
                Logger.log("Intermediate line: " + line + " -> Machine Code: " + machineCode);
                bw.write(machineCode + "\n");
            }
        }

        Logger.log("Pass 2: Completed successfully. Object code written to: " + outputFile.getName());
    }

    // Method to generate machine code from an intermediate line
    private String generateMachineCode(String line) {
        // Example intermediate format: "100 04 S0 01" (location | opcode | symbol | register)
        String[] parts = line.split(" ");
        if (parts.length == 0) {
            return ""; // Handle blank lines
        }

        StringBuilder machineCode = new StringBuilder();
        machineCode.append(parts[0]); // Location

        // Process each part of the line
        for (int i = 1; i < parts.length; i++) {
            if (parts[i].startsWith("S")) {
                // Resolve symbol address
                int symbolIndex = Integer.parseInt(parts[i].substring(1));
                if (symbolIndex < STP && ST[symbolIndex] != null) {
                    machineCode.append(" ").append(ST[symbolIndex].add);
                } else {
                    Logger.log("Error: Undefined symbol at index " + symbolIndex);
                    machineCode.append(" ").append("??"); // Undefined symbol
                }
            } else if (parts[i].startsWith("L")) {
                // Resolve literal address
                int literalIndex = Integer.parseInt(parts[i].substring(1));
                if (literalIndex < LTP && LT[literalIndex] != null) {
                    machineCode.append(" ").append(LT[literalIndex].add);
                } else {
                    Logger.log("Error: Undefined literal at index " + literalIndex);
                    machineCode.append(" ").append("??"); // Undefined literal
                }
            } else {
                machineCode.append(" ").append(parts[i]); // Opcode or register
            }
        }

        return machineCode.toString();
    }
}
