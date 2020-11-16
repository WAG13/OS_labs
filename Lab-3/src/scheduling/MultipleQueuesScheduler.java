package scheduling;

import scheduling.processes.ProcessDeque;
import scheduling.processes.sProcess;

import java.util.*;

public class MultipleQueuesScheduler{
    Vector queues;
    sProcess currentProcess = null;
    private int quantum;

    public MultipleQueuesScheduler(Vector processes,int quantum){
        queues = new Vector();
        this.quantum = quantum;
        splitProcessesToQueues(processes);
    }

    public void splitProcessesToQueues(Vector processVector){
        Collections.sort(processVector, new Comparator<sProcess>() {
                    @Override
                    public int compare(sProcess p1, sProcess p2) {
                        return p1.compareTo(p2);
                    }
                }
        );
        int numberOfProcesses = processVector.size();
        int i = 0;
        while (i < numberOfProcesses){
            sProcess process = (sProcess)processVector.get(i);
            int priority = process.priority;
            Deque<sProcess> queue = new ProcessDeque(priority,quantum*(int) Math.pow(2,queues.size()));
            process.queue = (ProcessDeque)queue;
            queue.addLast(process);
            while(true){
                i++;
                if(i < numberOfProcesses){
                    sProcess currentProcess = (sProcess)processVector.get(i);
                    int currentPriority = currentProcess.priority;
                    if(currentPriority == priority){
                        currentProcess.queue = (ProcessDeque)queue;//++
                        queue.addLast(currentProcess);
                    }else{
                        break;
                    }
                }
                else {
                    break;
                }
            }
            queues.add(queue);
        }
    }

    public sProcess getNextProcess(){
        int numberOfQueues = queues.size();
        for(int i = 0; i < numberOfQueues;i++){
            Deque currentProcessGroup = (Deque) queues.get(i);
            while(currentProcessGroup.peekFirst()!=null){//if Queue isn`t empty
                sProcess currentProcess = (sProcess)currentProcessGroup.peekFirst();
                if(currentProcess.cpudone != currentProcess.cputime){
                    currentProcessGroup.removeFirst();
                    currentProcessGroup.addLast(currentProcess);//set currentProcess to the end of queue
                    this.currentProcess = currentProcess;
                    return currentProcess;
                }else{
                    currentProcessGroup.removeFirst();//removing finished process from queue
                }
            }
        }
        return null;
    }

    //unused method yet
    /*public boolean increaseByOneCurrentProcessPriority(){
        int indexOfProcessQueue = removeCurrentProcessFromHisQueue();
        if(indexOfProcessQueue!=-1){
            currentProcess.priority++;
            currentProcess.usedQuantumOfTime = 0;
            if(indexOfProcessQueue == 0){//there is no queue with higher priority
                currentProcess.quantumOfTime = 1;
                Deque<scheduling.processes.sProcess> queue = new LinkedList<>();
                queue.add(currentProcess);
                queues.add(0,queue);
            }
            else{
                Deque currentProcessGroup = (Deque) queues.get(indexOfProcessQueue - 1);
                currentProcessGroup.addLast(currentProcess);
                currentProcess.quantumOfTime = (int) Math.pow(2,indexOfProcessQueue-1);
            }
            return true;
        }
        else{
            return false;
        }
    }*/

    public boolean decreaseByOneCurrentProcessPriority(){
        int indexOfProcessQueue = removeCurrentProcessFromHisQueue();
        if(indexOfProcessQueue!=-1){
            currentProcess.usedQuantumOfTime = 0;
            if(indexOfProcessQueue == queues.size() - 1){//there is no queue with lower priority
                currentProcess.priority--;
                Deque<sProcess> queue = new ProcessDeque(currentProcess.priority,quantum*(int) Math.pow(2,queues.size()));
                queue.add(currentProcess);
                currentProcess.queue = (ProcessDeque)queue;
                queues.add(queues.size(),queue);
            }
            else{
                Deque currentProcessGroup = (Deque) queues.get(indexOfProcessQueue + 1);
                currentProcess.queue = (ProcessDeque)currentProcessGroup;
                currentProcess.priority = ((ProcessDeque)currentProcessGroup).getPriority();
                currentProcessGroup.addLast(currentProcess);
            }
            return true;
        }
        else {
            return false;
        }
    }

    private int removeCurrentProcessFromHisQueue(){
        if(currentProcess == null)
            return -1;
        int numberOfQueues = queues.size();
        for(int i = 0; i < numberOfQueues;i++){
            Deque currentProcessGroup = (Deque) queues.get(i);
            sProcess process = (sProcess)currentProcessGroup.peekLast();
            if(currentProcess == process) {
                currentProcessGroup.removeLast();
                return i;
            }
        }
        return -1;
    }
}