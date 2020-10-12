package functions;

import java.io.IOException;

public class FuncG {

    private static double function(double x) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            return Double.NaN;
        }
        return x + 1;
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
