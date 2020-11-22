// This file contains the main() function for the scheduling.Scheduling
// simulation.  Init() initializes most of the variables by
// reading from a provided file.  scheduling.SchedulingAlgorithm.Run() is
// called from main() to run the simulation.  Summary-scheduling.Results
// is where the summary results are written, and Summary-Processes
// is where the process scheduling summary is written.

// Created by Alexander Reeder, 2001 January 06

package scheduling;

import scheduling.processes.Process;
import utils.Common;

import java.io.*;
import java.util.*;

public class Scheduling {

    private static int processnum = 10;
    private static int meanDev = 1000;
    private static int standardDev = 100;
    private static int runtime = 1000;
    private static int quantum = 100;
    private static Vector processVector = new Vector();
    private static Results result = new Results("null","null",0);
    private static String summary_file = "Summary-Results";
    private static String inputFile = "scheduling.conf";

    private static void init(File inputFile) {

        String line;
        int cputime = 0;
        int ioblocking = 0;
        int priority = 0;
        double X = 0.0;

        try {
            //BufferedReader in = new BufferedReader(new FileReader(f));
            DataInputStream in = new DataInputStream(new FileInputStream(inputFile));
            while ((line = in.readLine()) != null) {
                if (line.startsWith("numprocess")) {
                  StringTokenizer st = new StringTokenizer(line);
                  st.nextToken();
                  processnum = Integer.parseInt(st.nextToken());
                }
                if (line.startsWith("run_time_average")) {
                  StringTokenizer st = new StringTokenizer(line);
                  st.nextToken();
                  meanDev = Integer.parseInt(st.nextToken());
                }
                if (line.startsWith("run_time_stddev")) {
                  StringTokenizer st = new StringTokenizer(line);
                  st.nextToken();
                  standardDev = Integer.parseInt(st.nextToken());
                }
                if (line.startsWith("quantum")) {
                  StringTokenizer st = new StringTokenizer(line);
                  st.nextToken();
                  quantum = Integer.parseInt(st.nextToken());
                }
                if (line.startsWith("process")) {
                  StringTokenizer st = new StringTokenizer(line);
                  st.nextToken();
                  ioblocking = Integer.parseInt(st.nextToken());
                  priority = Integer.parseInt(st.nextToken());
                  X = Common.R1();
                  while (X == -1.0) {
                    X = Common.R1();
                  }
                  X = X * standardDev;
                  cputime = (int) X + meanDev;
                  processVector.addElement(new Process(cputime, ioblocking,priority, 0, 0, 0));
                }
                if (line.startsWith("runtime")) {
                  StringTokenizer st = new StringTokenizer(line);
                  st.nextToken();
                  runtime = Integer.parseInt(st.nextToken());
                }
            }
            in.close();
        } catch (IOException e) { /* Handle exceptions */ }
    }

    public static void main(String[] args) {
        int i = 0;
        File f = new File(inputFile);
        if (!(f.exists())) {
          System.out.println("Scheduling: error, file '" + f.getName() + "' does not exist.");
          System.exit(-1);
        }
        if (!(f.canRead())) {
          System.out.println("Scheduling: error, read of " + f.getName() + " failed.");
          System.exit(-1);
        }
        System.out.println("Working...");

        init(f);

        if (processVector.size() < processnum) {
            i = 0;
            while (processVector.size() < processnum) {
                double X = Common.R1();
                while (X == -1.0) {
                  X = Common.R1();
                }
                X = X * standardDev;
                int cputime = (int) X + meanDev;
                processVector.addElement(new Process(cputime,i*100, i,0,0,0));
                i++;
            }
        }

        result = SchedulingAlgorithm.multipleQueuesAlgorithm(summary_file, runtime, processVector, quantum, result);

        try {
          PrintStream out = new PrintStream(new FileOutputStream(summary_file));
          out.println("Scheduling Type: " + result.schedulingType);
          out.println("Scheduling Name: " + result.schedulingName);
          out.println("Simulation Run Time: " + result.computationTime);
          out.println("Mean: " + meanDev);
          out.println("Standard Deviation: " + standardDev);
          out.println("Process #\tCPU Time\tIO Blocking\tCPU Completed\tCPU Blocked");
          for (i = 0; i < processVector.size(); i++) {
            Process process = (Process) processVector.elementAt(i);
            out.print(Integer.toString(i));
            if (i < 100) { out.print("\t\t\t"); } else { out.print("\t\t"); }
            out.print(Integer.toString(process.cputime));
            if (process.cputime < 100) { out.print(" (ms)\t\t"); } else { out.print(" (ms)\t"); }
            out.print(Integer.toString(process.ioblocking));
            if (process.ioblocking < 100) { out.print(" (ms)\t\t"); } else { out.print(" (ms)\t"); }
            out.print(Integer.toString(process.cpudone));
            if (process.cpudone < 100) { out.print(" (ms)\t\t\t"); } else { out.print(" (ms)\t\t"); }
            out.println(process.numblocked + " times");
          }
          out.close();
        } catch (IOException e) { /* Handle exceptions */ }
        System.out.println("Completed.");
    }
}

