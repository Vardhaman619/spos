# JAVA Assembler Package

This repository contains a two-pass assembler that translates assembly language into machine code. The assembler logic is packaged into [assembler.jar](./assembler.jar), which provides a clean API for running Pass1 and Pass2 assemblers. The package allows flexibility by enabling users to run each pass separately.

## Getting Started

### Prerequisites

### Ensure you have the following installed:

- **Java 8 or higher**
- **A terminal or command-line environment to compile and run Java files**

### Usage Instructions

You can integrate the assembler functionality by using the provided `assembler.jar`.

#### Step 1: Download `assembler.jar` file

👉 [`Download assembler.jar file`](./assembler.jar)

#### Step 2: Craete Asm File for Input

(here is sample assembly_code.asm file)

```asm assembly_code.asm
START 100
MOVER AREG BREG
ADD BREG =10
SUB DREG A
DIV AREG B
A DC 10
B DC 20
END
```

#### Step 3 : Compile and Run the Your Java Program With `assembler.jar`

(replace Demo.java with your java file name)

```bash
javac -cp assembler.jar Demo.java
java -cp .:assembler.jar Demo
```

## Sample Java Program

```java
import com.assembler.Assembler;
import java.io.File;

public class Demo {
    public static void main(String[] args) {
        try {
            // Create an assembler instance
            Assembler assembler = new Assembler();

            // Input assembly file
            File inputAssembly = new File("assembly_code.asm");

            // Output for Pass1 (intermediate code)
            File intermediateFile = new File("intermediate.txt");

            // Output for Pass2 (final object code)
            File objectCodeFile = new File("output.obj");

            // Run Pass1 Assembler
            assembler.runPass1(inputAssembly, intermediateFile);

            // Run Pass2 Assembler
            assembler.runPass2(intermediateFile, objectCodeFile);

            System.out.println("Assembly completed successfully!");
        } catch (Exception e) {
            System.err.println("Error during assembly: " + e.getMessage());
        }
    }
}
```

## Features

- **Pass1 Assembler**: Converts assembly code into an intermediate representation, handling symbols, literals, and location counters.
- **Pass2 Assembler**: Resolves addresses for symbols and literals from the intermediate file, generating the final machine code.
- **Logging**: Logs each step during assembly for easy debugging and traceability.

## Project Structure

- **assembler.jar**: Contains the precompiled Java classes for the assembler logic (Pass1, Pass2, Logger, etc.).
- **src/com/assembler**: The Java source files for Pass1 and Pass2, along with utility classes such as `Logger`, `SymbolTable`, and `LiteralTable`.
- **Demo.java**: An example file showing how to use the assembler in a Java application.
