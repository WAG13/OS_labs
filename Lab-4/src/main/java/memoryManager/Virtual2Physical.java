package memoryManager;

public class Virtual2Physical {

    public static int pageNum(long memaddr, int numpages, long block) {
        int i;
        long high;
        long low;

        for (i = 0; i <= numpages; i++) {
            low = block * i;
            high = block * (i + 1);
            if (low <= memaddr && memaddr < high) {
                return i;
            }
        }
        return -1;
    }
}
