import com.assembler.*;
import java.io.File;
import java.io.IOException; // Assembler Package for Pass1 & Pass2

public class Demo {
    public static void main(String[] args) {
        try {
            Assembler assembler = new Assembler();

            // Example input and output files
            File input1 = new File("assembly_code.asm");
            File output1 = new File("intermediate.txt");
            File output2 = new File("output.obj");

            // Run Pass 1 (generates intermediate file)
            assembler.runPass1(input1, output1);

            // Run Pass 2 (generates object code)
            assembler.runPass2(output1, output2);

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
