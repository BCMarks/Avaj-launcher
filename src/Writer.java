package src;

import java.io.File;
import java.io.FileWriter;

public abstract class Writer {
    static File output = new File("simulation.txt");
    
    public static void newFile() {
        try {
            if (output.exists())
                output.delete();
            output.createNewFile();
        }
        catch (Exception e) {
            System.out.println("Error creating simulation results file.");
        }
        
    }

    public static void newLine(String s) {
        try {
            FileWriter writer = new FileWriter("simulation.txt", true);
            writer.write(s+"\n");
            writer.close();
        }
        catch (Exception e) {
            System.out.println("Error writing simulation results to file.");
        }
    }
}