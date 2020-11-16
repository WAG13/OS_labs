// Run() is called from scheduling.Scheduling.main() and is where
// the scheduling algorithm written by the user resides.
// User modification should occur within the Run() function.

package scheduling;

import scheduling.processes.sProcess;

import java.util.Vector;
import java.io.*;

public class SchedulingAlgorithm {

    public static Results firstComeFirstServedAlgorithm(int runtime, Vector processVector, Results result) {
        int i = 0;
        int comptime = 0;
        int currentProcess = 0;
        int previousProcess = 0;
        int size = processVector.size();
        int completed = 0;
        String resultsFile = "Summary-Processes";

        result.schedulingType = "Batch (Nonpreemptive)";
        result.schedulingName = "First-Come First-Served";
        try {
            PrintStream out = new PrintStream(new FileOutputStream(resultsFile));
            sProcess process = (sProcess) processVector.elementAt(currentProcess);
            out.println("Process: " + currentProcess + " registered... (" + process.cputime + " " + process.ioblocking +
                    " " + process.cpudone + " " + process.cpudone + ")");
            while (comptime < runtime) {
                if (process.cpudone == process.cputime) {
                    completed++;
                    out.println("Process: " + currentProcess + " completed... (" + process.cputime + " " + process.ioblocking +
                            " " + process.cpudone + " " + process.cpudone + ")");
                    if (completed == size) {
                        result.computationTime = comptime;
                        out.close();
                        return result;
                    }
                    for (i = size - 1; i >= 0; i--) {
                        process = (sProcess) processVector.elementAt(i);
                        if (process.cpudone < process.cputime) {
                            currentProcess = i;
                        }
                    }
                    process = (sProcess) processVector.elementAt(currentProcess);
                    out.println("scheduling.processes.Process: " + currentProcess + " registered... (" + process.cputime +
                            " " + process.ioblocking + " " + process.cpudone + " " + process.cpudone + ")");
                }
                if (process.ioblocking == process.ionext) {
                    out.println("scheduling.processes.Process: " + currentProcess + " I/O blocked... (" + process.cputime +
                            " " + process.ioblocking + " " + process.cpudone + " " + process.cpudone + ")");
                    process.numblocked++;
                    process.ionext = 0;
                    previousProcess = currentProcess;
                    for (i = size - 1; i >= 0; i--) {
                        process = (sProcess) processVector.elementAt(i);
                        if (process.cpudone < process.cputime && previousProcess != i) {
                            currentProcess = i;
                        }
                    }
                    process = (sProcess) processVector.elementAt(currentProcess);
                    out.println("scheduling.processes.Process: " + currentProcess + " registered... (" + process.cputime +
                            " " + process.ioblocking + " " + process.cpudone + " " + process.cpudone + ")");
                }
                process.cpudone++;
                if (process.ioblocking > 0) {
                    process.ionext++;
                }
                comptime++;
            }
            out.close();
        } catch (IOException e) { /* Handle exceptions */ }
        result.computationTime = comptime;
        return result;
    }

    public static Results multipleQueuesAlgorithm(int runtime, Vector processVector,int quantum, Results result) {
        int comptime = 0;
        int size = processVector.size();
        int completed = 0;
        MultipleQueuesScheduler scheduler = new MultipleQueuesScheduler(processVector,quantum);

        String resultsFile = "Summary-Processes";
        result.schedulingType = "Batch (preemptive)";
        result.schedulingName = "Multiple queues";
        try {
            PrintStream out = new PrintStream(new FileOutputStream(resultsFile));
            sProcess process = scheduler.getNextProcess();
            out.println(getProcessInfo(process,"registered"));
            while (comptime < runtime) {
                if (process.cpudone == process.cputime) {
                    completed++;
                    out.println(getProcessInfo(process,"completed"));
                    if (completed == size) {
                        result.computationTime = comptime;
                        out.close();
                        return result;
                    }

                    process = scheduler.getNextProcess();
                    out.println(getProcessInfo(process,"registered"));
                }
                if (process.ioblocking == process.ionext) {
                    process.numblocked++;
                    process.ionext = 0;
                    out.println(getProcessInfo(process,"I/O blocked"));

                    if(process.usedQuantumOfTime == process.queue.getQuantum()){
                        scheduler.decreaseByOneCurrentProcessPriority();
                    }
                    process = scheduler.getNextProcess();
                    out.println(getProcessInfo(process,"registered"));
                }
                if(process.usedQuantumOfTime == process.queue.getQuantum()){
                    //process.numblocked++;//???
                    out.println(getProcessInfo(process,"used all quantum of time"));
                    scheduler.decreaseByOneCurrentProcessPriority();

                    process = scheduler.getNextProcess();
                    out.println(getProcessInfo(process,"registered"));
                }
                process.usedQuantumOfTime++;
                process.cpudone++;
                if (process.ioblocking > 0) {
                    process.ionext++;
                }
                comptime++;
            }
            out.close();
        } catch (IOException e) { /* Handle exceptions */ }
        result.computationTime = comptime;
        return result;
    }

    public static String getProcessInfo(sProcess process,String state){
        return String.format("Process: %d (priority %d) used %d / %d quantum of time %s... (%d %d %d)",
                process.processIndex, process.priority, process.usedQuantumOfTime, process.queue.getQuantum(),
                state, process.cputime, process.ioblocking, process.cpudone);
    }
}
