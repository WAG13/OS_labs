package functions;

import util.SocketClient;

import java.io.IOException;
import java.util.function.DoubleUnaryOperator;

public class FuncHandler {

    public static void run(DoubleUnaryOperator function) {
        SocketClient socketClient;
        try {
            socketClient = SocketClient.create();
        } catch (IOException e) {
            System.err.println("Client: can't open client socket");
            return;
        }

        final double x;
        try {
            x = socketClient.get(); // will block until x value is received
        } catch (IOException e) {
            System.err.println("Client: can't get x value");
            return;
        }
        FuncRunnable functionRunnable = new FuncRunnable(x, function, socketClient);
        Thread thread = new Thread(functionRunnable);
        thread.setDaemon(true);
        thread.start();

        try {
            socketClient.get();
        } catch (IOException ignored) { }
    }
}
