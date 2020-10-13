package functions;

import java.io.IOException;
import spos.lab1.demo.DoubleOps;

public class FuncG {

    private static double function(double x) {
        double result;
        try {
            //Thread.sleep(5000);
            result = DoubleOps.funcG((int)x);

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
