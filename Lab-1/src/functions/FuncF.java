package functions;

import java.io.IOException;
import spos.lab1.demo.DoubleOps;

public class FuncF {
    private static long LONG_SPLEEP = 30000;
    private static long[] SLEEP_TIME = {3000, 5000, 3000, LONG_SPLEEP, 3000, LONG_SPLEEP};
    private static double[] RESULT = {3, 3, 0, 0, 3, 3};

    private static double function(double x) {
        int test = (int) x%6;
        double result = RESULT[test];
        try {
            Thread.sleep(SLEEP_TIME[test]);
            //result = DoubleOps.funcF(test);

        } catch (InterruptedException e) {
            return Double.NaN;
        }
        return result;
    }

    public static void main(String[] args) {
        FuncHandler.run(FuncF::function);
    }

    public static void run() {
        try {
            FuncProcess.execute(FuncF.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
