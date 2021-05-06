package edu.wpi.MochaManticores.messaging;

import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

public class debugger {
    public static void write(String processOut, String file) {
        try {
            FileWriter myWriter = new FileWriter(file);
            myWriter.write(processOut);
            myWriter.flush();
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}