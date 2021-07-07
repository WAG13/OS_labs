package memoryManager;

import java.io.*;

public class MemoryManagement {

  private static void checkInputFile(final String fileName){
    File file = new File(fileName);   //file with instruction - commands

    if (!(file.exists())) {
      System.out.println("MemoryM: error, file '" + file.getName() + "' does not exist.");
      System.exit(-1);
    }
    if (!(file.canRead())) {
      System.out.println("MemoryM: error, read of " + file.getName() + " failed.");
      System.exit(-1);
    }
  }

  public static void main(String[] args) {

    ControlPanel controlPanel = new ControlPanel("Memory Management");;
    Kernel kernel = new Kernel();

    if (args.length < 1 || args.length > 2) {
      System.out.println("Usage: 'java MemoryManagement <COMMAND FILE> <PROPERTIES FILE>'");
      System.exit(-1);
    }
    checkInputFile(args[0]);   //file with instruction

    if (args.length == 2) {
      checkInputFile(args[1]); //config file
    }

    if (args.length == 1) {
      controlPanel.init(kernel, args[0], null);
    } else {
      controlPanel.init(kernel, args[0], args[1]);
    }
  }
}