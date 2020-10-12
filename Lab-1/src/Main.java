import functions.FuncF;
import functions.FuncG;
import util.ConcurrentBiConsumer;
import manager.Manager;

import java.io.IOException;
import java.util.Optional;

public class Main {

    public static void main(String[] args) throws IOException {

        double x = 5;

        Manager manager = Manager.createOnPort(1234, 2);

        FuncF.run();
        FuncG.run();

        manager.run(x, new ConcurrentBiConsumer<Optional<Double>, Manager.Status>() {

            @Override
            public void acceptOnce(Optional<Double> result, Manager.Status status) {
                System.out.println("Result: " + (result.isPresent() ? result.get() : "undefined"));
                System.out.println("Status: " + status.name());
                System.exit(0);
            }
        });

    }
}
