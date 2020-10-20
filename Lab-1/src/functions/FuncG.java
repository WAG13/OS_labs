package functions;

import java.io.IOException;
import spos.lab1.demo.DoubleOps;

public class FuncG {
    private static long LONG_SPLEEP = 30000;
    private static long[] SLEEP_TIME = {5000, 3000, LONG_SPLEEP, 3000, LONG_SPLEEP, 3000};
    private static double[] RESULT = {5, 5, 0, 0, 5, 5};

    private static double function(double x) {
        int test = (int) x%6;
        double result = RESULT[test];
        try {
            Thread.sleep(SLEEP_TIME[test]);
            //result = DoubleOps.funcG(test);

        } catch (InterruptedException e) {
            return Double.NaN;
        }
        return result;
    }

    public static void main(String[] args) {
        FuncHandler.run(FuncG::function);
    }

    public static void run() {
        try {
            FuncProcess.execute(FuncG.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
