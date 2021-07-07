package scheduling.processes;

import java.util.LinkedList;

public class ProcessDeque extends LinkedList {
    private int priority;
    private int quantum;

    public ProcessDeque(){}

    public ProcessDeque(int priority,int quantum){
        super();
        this.priority = priority;
        this.quantum = quantum;
    }
    public int getPriority() {
        return priority;
    }

    public int getQuantum() {
        return quantum;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }
}
