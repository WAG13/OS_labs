package functions;

import util.SocketClient;

import java.io.IOException;
import java.util.function.DoubleUnaryOperator;

public class FuncRunnable implements Runnable {

    private final DoubleUnaryOperator function;
    private final double x;
    private final SocketClient socket;

    public FuncRunnable(double x, DoubleUnaryOperator function, SocketClient socket) {
        this.x = x;
        this.function = function;
        this.socket = socket;
    }

    @Override
    public void run() {
        double result = function.applyAsDouble(x);
        try {
            socket.send(result);
        } catch (IOException e) {
            System.err.println("Client: can't send f(x) value");
        }
    }
}
