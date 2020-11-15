
public class sProcess implements Comparable{
    public int cputime;
    public int ioblocking;
    public int priority;
    public int cpudone;
    public int ionext;
    public int numblocked;

    public sProcess (int cputime, int ioblocking, int priority, int cpudone, int ionext, int numblocked) {
        this.cputime = cputime;
        this.ioblocking = ioblocking;
        this.priority = priority;
        this.cpudone = cpudone;
        this.ionext = ionext;
        this.numblocked = numblocked;
    }

}
