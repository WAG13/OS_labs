import functions.FuncF;
import functions.FuncG;
import org.jnativehook.keyboard.NativeKeyListener;
import util.ConcurrentBiConsumer;
import manager.Manager;
import prompt.Prompt;

import java.io.IOException;
import java.util.Optional;

public class Main{

    private static final int N = 2;

    public static void main(String[] args) throws IOException {

        double x = Prompt.getX();

        Manager manager = Manager.createOnPort(1234, N);
        /**/
        FuncF.run();
        FuncG.run();
        /*
        for (int i=0; i<N; i++){
            FuncF.run();
        }/**/

        long[] startTime = new long[] {System.nanoTime()};

        manager.run(x, new ConcurrentBiConsumer<Optional<Double>, Manager.Status>() {

            @Override
            public void acceptOnce(Optional<Double> result, Manager.Status status) {
                long duration = (System.nanoTime() - startTime[0]) / 1000000;
                System.out.println("Result: " + (result.isPresent() ? result.get() : "undefined"));
                System.out.println("Status: " + status.name());
                System.out.println("(took " + duration + " ms)");
                System.exit(0);
            }
        });

        Prompt.runMenu(manager);
    }
}
