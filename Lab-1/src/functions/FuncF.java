package functions;

import java.io.IOException;
import spos.lab1.demo.DoubleOps;

public class FuncF {

    private static double function(double x) {
        double result;
        try {
            Thread.sleep(3000);
            //result = DoubleOps.funcF((int)x);

        } catch (InterruptedException e) {
            return Double.NaN;
        }
        result = x + 1;
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
