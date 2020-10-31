package functions;

import util.SocketClient;

import java.io.IOException;
import java.util.function.DoubleUnaryOperator;

public class FuncRunnable implements Runnable {

    private final DoubleUnaryOperator function;
    private final double x;
    private final SocketClient socket;
    private final char funcName;

    public FuncRunnable(double x, DoubleUnaryOperator function, char funcName, SocketClient socket) {
        this.x = x;
        this.function = function;
        this.funcName = funcName;
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
