package functions;

import java.io.IOException;

public class FuncF {

    private static double function(double x) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            return Double.NaN;
        }
        return x - 1;
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
